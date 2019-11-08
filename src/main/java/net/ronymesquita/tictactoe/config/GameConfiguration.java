package net.ronymesquita.tictactoe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.GenericApplicationContext;

import javafx.stage.Stage;
import net.ronymesquita.tictactoe.application.WindowAlternator;
import net.ronymesquita.tictactoe.model.TicTacToe;

@Configuration
public class GameConfiguration {

	@Autowired
	private GenericApplicationContext springApplicationContext;

	@Bean
	public TicTacToe ticTacToe() {
		return new TicTacToe(null, null);
	}

	@Bean
	@Lazy
	public WindowAlternator windowAlternator(Stage applicationStage) {
		return new WindowAlternator(applicationStage, springApplicationContext);
	}
}
