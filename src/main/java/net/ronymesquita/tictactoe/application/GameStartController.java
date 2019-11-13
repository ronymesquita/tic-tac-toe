package net.ronymesquita.tictactoe.application;

import static net.ronymesquita.tictactoe.model.PlayerSymbol.PLAYER_O;
import static net.ronymesquita.tictactoe.model.PlayerSymbol.PLAYER_X;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.ronymesquita.tictactoe.application.WindowAlternator.ApplicationWindows;
import net.ronymesquita.tictactoe.model.Player;
import net.ronymesquita.tictactoe.model.TicTacToe;

@Controller
public class GameStartController implements Initializable {

	@FXML
	private HBox gameStartHBox;

	@FXML
	private VBox playersConfigurationVBox;

	@FXML
	private TextField playerXNameTextField;

	@FXML
	private TextField playerONameTextField;

	@FXML
	private Button gamePlayButton;

	@FXML
	private ImageView gameStartImageView;

	@Autowired
	private TicTacToe ticTacToeGame;

	private WindowAlternator windowAlternator;

	@Autowired
	@Lazy
	public GameStartController(WindowAlternator windowAlternator) {
		this.windowAlternator = windowAlternator;
	}

	@FXML
	void configureGamePlayers(ActionEvent gamePlayButtonEvent) {
		var playerXName = playerXNameTextField.getText().isBlank() ?
				"α" :
				playerXNameTextField.getText();
		var playerX = new Player(playerXName, PLAYER_X);

		var playerOName = playerONameTextField.getText().isBlank() ?
				"β" :
				playerONameTextField.getText();
		var playerO = new Player(playerOName, PLAYER_O);

		ticTacToeGame.newGame(playerX, playerO);

		windowAlternator.navigateTo(ApplicationWindows.TIC_TAC_TOE_GAME);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameStartImageView.fitWidthProperty()
				.bind(gameStartHBox
						.widthProperty()
						.multiply(.66));
		gameStartImageView.fitHeightProperty()
				.bind(gameStartHBox
						.heightProperty()
						.multiply(.9999));
	}

}
