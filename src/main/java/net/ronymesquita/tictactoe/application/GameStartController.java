package net.ronymesquita.tictactoe.application;

import static net.ronymesquita.tictactoe.model.PlayerSymbol.PLAYER_O;
import static net.ronymesquita.tictactoe.model.PlayerSymbol.PLAYER_X;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

	private static final Locale DEFAULT_LOCALE = Locale.getDefault();
	
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
	
	@Autowired
	private MessageSource applicationMessages;

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
	public void initialize(URL location, ResourceBundle resourceBundle) {
		gameStartImageView.fitWidthProperty()
				.bind(gameStartHBox
						.widthProperty()
						.multiply(.66));
		gameStartImageView.fitHeightProperty()
				.bind(gameStartHBox
						.heightProperty()
						.multiply(.9999));
		
		configureInternationalizationMessages();
	}

	private void configureInternationalizationMessages() {
		playerXNameTextField.setPromptText(
				getApplicationText("player-x-prompt-text"));
		playerONameTextField.setPromptText(
				getApplicationText("player-o-prompt-text"));
		gamePlayButton.setText(
				getApplicationText("game-play-button"));
	}

	private String getApplicationText(String code) {
		return applicationMessages.getMessage(code, null, DEFAULT_LOCALE);
	}

}
