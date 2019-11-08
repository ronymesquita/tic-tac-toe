package net.ronymesquita.tictactoe;

import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.ronymesquita.tictactoe.application.WindowAlternator.ApplicationWindows;

@SpringBootApplication
public class TicTacToeApplication extends Application {

	private GenericApplicationContext springContext;
	private Parent rootNode;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = (GenericApplicationContext) SpringApplication.run(TicTacToeApplication.class);
		var gameStartFxmlPath = ApplicationWindows.GAME_START.getFxmlPath();
		var fxmlLoader = new FXMLLoader(getClass().getResource(gameStartFxmlPath));
		fxmlLoader.setControllerFactory(springContext::getBean);
		rootNode = fxmlLoader.load();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		springContext.registerBean(Stage.class, () -> primaryStage);

		var scene = new Scene(rootNode);
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		var logoImages = Set.of(
				new Image(getClass().getResourceAsStream("/image/logo/logo_16.png")),
				new Image(getClass().getResourceAsStream("/image/logo/logo_32.png")),
				new Image(getClass().getResourceAsStream("/image/logo/logo_64.png")),
				new Image(getClass().getResourceAsStream("/image/logo/logo_128.png")),
				new Image(getClass().getResourceAsStream("/image/logo/logo_256.png")),
				new Image(getClass().getResourceAsStream("/image/logo/logo_512.png"))
		);
		primaryStage.getIcons().addAll(logoImages);
		primaryStage.setTitle("Tic-tac-toe");
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
		Platform.exit();
	}

}
