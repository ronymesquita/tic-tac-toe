package net.ronymesquita.tictactoe.application;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.StatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import net.ronymesquita.tictactoe.model.AlreadyOccupiedSpace;
import net.ronymesquita.tictactoe.model.TicTacToe;

@Controller
public class GameBoardController implements Initializable {

	@FXML
	private GridPane gameBoardGridPane;

	@FXML
	private Label playerXNameLabel;

	@FXML
	private Label playerONameLabel;

	@FXML
	private StatusBar statusBar;

	@Autowired
	private TicTacToe ticTacToeGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurePlayers();
	}

	@FXML
	void configureGamePlay(MouseEvent mouseEvent) {
		if (gameIsTied()) {
			setStatusBarMessage("Empate.");
		} else if (!ticTacToeGame.isEndOfGame()) {
			for (var gridCell : gameBoardGridPane.getChildrenUnmodifiable()) {
				if (gridCell instanceof Label) {
					var playerSymbolLabel = (Label) gridCell;
					if (gameBoardPlaceWasSelected(mouseEvent, playerSymbolLabel)) {
						var xAxis = GridPane.getRowIndex(playerSymbolLabel) + 1;
						var yAxis = GridPane.getColumnIndex(playerSymbolLabel) + 1;

						configureGameRound(playerSymbolLabel, xAxis, yAxis);
					}
				}
			}
		}
	}

	private void configureGameRound(Label playerSymbolLabel, int xAxis, int yAxis) {
		try {
			if (ticTacToeGame.getWinner().isEmpty() && !gameIsTied()) {
				ticTacToeGame.play(xAxis, yAxis);
				configureTurnSymbolLabel(playerSymbolLabel, true);

				if (ticTacToeGame.getWinner().isPresent()) {
					setStatusBarMessage(getWinnerMessage());
					configureWinnerLine();
				} else if (gameIsTied()) {
					setStatusBarMessage("Empate.");
				}
			}
		} catch (AlreadyOccupiedSpace alreadyOccupiedSpaceException) {
			setStatusBarMessage("Espaço já ocupado.");
			configureTurnSymbolLabel(playerSymbolLabel, false);
			clearStatusBarText();
		}
	}

	private boolean gameBoardPlaceWasSelected(MouseEvent mouseEvent, Label playerSymbolLabel) {
		return playerSymbolLabel.getBoundsInParent().contains(
				mouseEvent.getSceneX(),
				mouseEvent.getSceneY());
	}

	private void configureWinnerLine() {
		int[][] winnerGridPlace = ticTacToeGame.getWinnerGridPlace();
		var linePane = new Pane();
		for (int[] line : winnerGridPlace) {
			int columnIndex = line[0] - 1;
			int rowIndex = line[1] - 1;
			var symbolCellBounds = gameBoardGridPane
					.getCellBounds(
							rowIndex,
							columnIndex);

			Line winLine;
			if (isTopRightToBottomLeftLine(winnerGridPlace)) {
				winLine = new Line(
						symbolCellBounds.getMinX() + symbolCellBounds.getWidth(),
						symbolCellBounds.getMinY() ,
						symbolCellBounds.getMaxX() - symbolCellBounds.getWidth(),
						symbolCellBounds.getMaxY());
			} else if (isFirstLineHorizontal(winnerGridPlace)) {
				winLine = getHorizontalLine(symbolCellBounds);
			} else if (isSecondLineHorizontal(winnerGridPlace)) {
				winLine = getHorizontalLine(symbolCellBounds);
			} else if (isThirdLineHorizontal(winnerGridPlace)) {
				winLine = getHorizontalLine(symbolCellBounds);
			} else if (isFirstColumnVertical(winnerGridPlace)) {
				winLine = getVerticalLine(symbolCellBounds);
			} else if (isSecondColumnVertical(winnerGridPlace)) {
				winLine = getVerticalLine(symbolCellBounds);
			} else if (isThirdColumnVertical(winnerGridPlace)) {
				winLine = getVerticalLine(symbolCellBounds);
			} else {
				winLine = new Line(
						symbolCellBounds.getMinX(),
						symbolCellBounds.getMinY(),
						symbolCellBounds.getMaxX(),
						symbolCellBounds.getMaxY());
			}
			winLine.setFill(Paint.valueOf("#ffffff"));
			winLine.setStroke(Color.WHITE);
			winLine.setStrokeWidth(5);
			winLine.setStrokeLineCap(StrokeLineCap.ROUND);
			linePane.getChildren().add(winLine);
		}

		gameBoardGridPane.add(linePane, 0, 0);
	}

	private Line getVerticalLine(Bounds symbolCellBounds) {
		return new Line(
				symbolCellBounds.getMinX() + symbolCellBounds.getWidth() / 2,
				symbolCellBounds.getMinY(),
				symbolCellBounds.getMaxX() - symbolCellBounds.getWidth() / 2,
				symbolCellBounds.getMaxY());
	}

	private Line getHorizontalLine(Bounds symbolCellBounds) {
		return new Line(
				symbolCellBounds.getMinX(),
				symbolCellBounds.getMinY() + symbolCellBounds.getHeight() / 2,
				symbolCellBounds.getMaxX(),
				symbolCellBounds.getMinY() + symbolCellBounds.getHeight() / 2);
	}

	private boolean isFirstLineHorizontal(int[][] winnerGridPlace) {
		return winnerGridPlace[0][0] == 1
				&& winnerGridPlace[0][1] == 1
				&& winnerGridPlace[1][0] == 1
				&& winnerGridPlace[1][1] == 2
				&& winnerGridPlace[2][0] == 1
				&& winnerGridPlace[2][1] == 3;
	}

	private boolean isSecondLineHorizontal(int[][] winnerGridPlace) {
		return winnerGridPlace[0][0] == 2
				&& winnerGridPlace[0][1] == 1
				&& winnerGridPlace[1][0] == 2
				&& winnerGridPlace[1][1] == 2
				&& winnerGridPlace[2][0] == 2
				&& winnerGridPlace[2][1] == 3;
	}

	private boolean isThirdLineHorizontal(int[][] winnerGridPlace) {
		return winnerGridPlace[0][0] == 3
				&& winnerGridPlace[0][1] == 1
				&& winnerGridPlace[1][0] == 3
				&& winnerGridPlace[1][1] == 2
				&& winnerGridPlace[2][0] == 3
				&& winnerGridPlace[2][1] == 3;
	}

	private boolean isFirstColumnVertical(int[][] winnerGridPlace) {
		return winnerGridPlace[0][0] == 1
				&& winnerGridPlace[0][1] == 1
				&& winnerGridPlace[1][0] == 2
				&& winnerGridPlace[1][1] == 1
				&& winnerGridPlace[2][0] == 3
				&& winnerGridPlace[2][1] == 1;
	}

	private boolean isSecondColumnVertical(int[][] winnerGridPlace) {
		return winnerGridPlace[0][0] == 1
				&& winnerGridPlace[0][1] == 2
				&& winnerGridPlace[1][0] == 2
				&& winnerGridPlace[1][1] == 2
				&& winnerGridPlace[2][0] == 3
				&& winnerGridPlace[2][1] == 2;
	}

	private boolean isThirdColumnVertical(int[][] winnerGridPlace) {
		return winnerGridPlace[0][0] == 1
				&& winnerGridPlace[0][1] == 3
				&& winnerGridPlace[1][0] == 2
				&& winnerGridPlace[1][1] == 3
				&& winnerGridPlace[2][0] == 3
				&& winnerGridPlace[2][1] == 3;
	}

	private boolean isTopRightToBottomLeftLine(int[][] winnerGridPlace) {
		return winnerGridPlace[0][0] == 1
				&& winnerGridPlace[0][1] == 3
				&& winnerGridPlace[1][0] == 2
				&& winnerGridPlace[1][1] == 2
				&& winnerGridPlace[2][0] == 3
				&& winnerGridPlace[2][1] == 1;
	}


	/**
	 * Configures the current player symbol in the grid according with the turn's player.
	 * @param playerSymbolLabel The label to be configured
	 * @param shouldChangePlayerSymbol Controls the change of the label
	 */
	private void configureTurnSymbolLabel(Label playerSymbolLabel, boolean shouldChangePlayerSymbol) {
		if (shouldChangePlayerSymbol) {
			var playerSymbol = ticTacToeGame
					.getPreviousPlayer();
			playerSymbolLabel.setText(
					playerSymbol.getPlayerSymbol().getSymbol());
		}
	}

	private boolean gameIsTied() {
		return ticTacToeGame.isEndOfGame() && ticTacToeGame.getWinner().isEmpty();
	}

	private void configurePlayers() {
		playerXNameLabel.setText(ticTacToeGame.getPlayerX().getName());
		playerONameLabel.setText(ticTacToeGame.getPlayerO().getName());
	}

	private void clearStatusBarText() {
		var pauseTransition = new PauseTransition(Duration.seconds(3));
		pauseTransition.setOnFinished(actionEvent -> setStatusBarMessage(""));
		pauseTransition.play();
	}

	private void setStatusBarMessage(String message) {
		var statusMessageLabel = new Label(message);
		statusMessageLabel.getStyleClass().add("status-bar-message");
		statusBar.getLeftItems().clear();
		statusBar.getLeftItems().add(statusMessageLabel);
	}

	private String getWinnerMessage() {
		return String.format("%s ganhou!", ticTacToeGame.getWinner().get().getName());
	}

}