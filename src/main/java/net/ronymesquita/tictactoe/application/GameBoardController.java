package net.ronymesquita.tictactoe.application;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.StatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import net.ronymesquita.tictactoe.application.WindowAlternator.ApplicationWindows;
import net.ronymesquita.tictactoe.model.AlreadyOccupiedSpaceException;
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

	private WindowAlternator windowAlternator;

	@Autowired
	@Lazy
	public GameBoardController(WindowAlternator windowAlternator) {
		this.windowAlternator = windowAlternator;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurePlayers();

		configureGamePlayOptions();
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
		} catch (AlreadyOccupiedSpaceException alreadyOccupiedSpaceException) {
			setStatusBarMessage("Espaço já ocupado.");
			configureTurnSymbolLabel(playerSymbolLabel, false);
			clearStatusBarText(5);
		}
	}

	private boolean gameBoardPlaceWasSelected(MouseEvent mouseEvent, Label playerSymbolLabel) {
		return playerSymbolLabel.getBoundsInParent().contains(
				mouseEvent.getSceneX(),
				mouseEvent.getSceneY());
	}

	/**
	 * Creates and draw the line when a player wins the game.
	 */
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

	/**
	 * Creates a vertical line based on the cell bounds.
	 * @param symbolCellBounds The bounds of the cell to determine the coordinates.
	 * @return vertical line based on the cell bounds.
	 */
	private Line getVerticalLine(Bounds symbolCellBounds) {
		return new Line(
				symbolCellBounds.getMinX() + symbolCellBounds.getWidth() / 2,
				symbolCellBounds.getMinY(),
				symbolCellBounds.getMaxX() - symbolCellBounds.getWidth() / 2,
				symbolCellBounds.getMaxY());
	}

	/**
	 * Creates a horizontal line based on the cell bounds.
	 * @param symbolCellBounds The bounds of the cell to determine the coordinates.
	 * @return horizontal line based on the cell bounds.
	 */
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

	/**
	 * Configures the window to show the names of the players.
	 */
	private void configurePlayers() {
		playerXNameLabel.setText(ticTacToeGame.getPlayerX().getName());
		playerONameLabel.setText(ticTacToeGame.getPlayerO().getName());
	}

	/**
	 * Cleans the status bar in 3 seconds by default.
	 */
	private void clearStatusBarText() {
		clearStatusBarText(3);
	}
	
	/**
	 * Cleans the status bar in {@code timeInSeconds} seconds by default.
	 * @param timeInSeconds time in seconds before clear the status bar
	 */
	private void clearStatusBarText(int timeInSeconds) {
		var pauseTransition = new PauseTransition(Duration.seconds(timeInSeconds));
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
	
	/**
	 * Configures the buttons on the status bar to start a new match 
	 * or restart with the current players.
	 */
	private void configureGamePlayOptions() {
		var restartGameButton = new Button("Reiniciar");
		var newGameButton = new Button("Nova partida");
		var gameOptionsHBox = new HBox(restartGameButton, newGameButton);
		gameOptionsHBox.getStyleClass().add("game-options-box");
		gameOptionsHBox.getChildren().forEach(button -> button.getStyleClass().add("game-option-button"));
		
		newGameButton.setOnAction(
				actionEvent -> 
				windowAlternator.navigateTo(ApplicationWindows.GAME_START));
		
		restartGameButton.setOnAction(actionEvent -> {
			ticTacToeGame.restart();
			
			cleanSymbolCells();
			if (hasWinnerLineDrawed()) {
				gameBoardGridPane.getChildren().remove(9);
			}
			clearStatusBarText();
		});
		
		statusBar.getRightItems().add(gameOptionsHBox);
	}

	/**
	 * Checks the game board to determine if this has the winner line draw,
	 * situation when there are more than 9 cells on the board.
	 * @return <code>true</code> if the game board has the winner line draw
	 */
	private boolean hasWinnerLineDrawed() {
		return gameBoardGridPane.getChildren().size() > 9;
	}

	/**
	 * Clean all cell of the game board.
	 */
	private void cleanSymbolCells() {
		gameBoardGridPane.getChildren()
			.filtered(gameBoardChildren -> gameBoardChildren.getClass().equals(Label.class))
			.forEach(symbolLabel -> ((Label) symbolLabel).setText(""));
	}

}
