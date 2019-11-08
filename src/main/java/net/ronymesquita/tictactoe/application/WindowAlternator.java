package net.ronymesquita.tictactoe.application;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.GenericApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowAlternator {

	private Stage applicationStage;
	private GenericApplicationContext springContext;

	public enum ApplicationWindows {
		GAME_START("/fxml/game-start.fxml", "Tic-Tac-Toe"),
		TIC_TAC_TOE_GAME("/fxml/tic-tac-toe.fxml", "Tic-Tac-Toe – Play");

		private String fxmlPath;
		private String windowTitle;

		ApplicationWindows(String fxmlPath, String windowTitle) {
			this.fxmlPath = fxmlPath;
			this.windowTitle = windowTitle;
		}

		public String getFxmlPath() {
			return fxmlPath;
		}

		public String getTitle() {
			return windowTitle;
		}
	}

	public WindowAlternator(
			@Lazy(true) Stage applicationStage,
			GenericApplicationContext springApplicationContext) {
		this.applicationStage = applicationStage;
		this.springContext = springApplicationContext;
	}

	public void navigateTo(ApplicationWindows windowToLoad) {
		var fxmlFilePath = windowToLoad.getFxmlPath();

		try {
			var fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
			fxmlLoader.setControllerFactory(springContext::getBean);
			Parent newRootComponent = fxmlLoader.load();
			var newApplicationScene = prepareApplicationScene(newRootComponent);
			applicationStage.setScene(newApplicationScene);
			applicationStage.setTitle(windowToLoad.getTitle());
		} catch (IOException ioException) {
			throw new ApplicationFxmlLoadException(ioException);
		}
	}

	private Scene prepareApplicationScene(Parent rootComponent) {
		var newScene = applicationStage.getScene();

		if (newScene == null) {
			newScene = new Scene(rootComponent);
		}

		newScene.setRoot(rootComponent);
		return newScene;
	}

}
