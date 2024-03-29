package net.ronymesquita.tictactoe.model;

import static net.ronymesquita.tictactoe.model.PlayerSymbol.PLAYER_O;
import static net.ronymesquita.tictactoe.model.PlayerSymbol.PLAYER_X;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeTest {
	
	private TicTacToe ticTacToeGame;
	private Player playerX = new Player("Marcos", PLAYER_X);
	private Player playerO = new Player("Gabriel", PLAYER_O);
	
	@BeforeEach
	void init() {		
		ticTacToeGame = new TicTacToe(playerX, playerO);
	}
	
	@Test
	void whenBoardIsFullThenGameIsEnd() {
		ticTacToeGame.play(1, 1); // X
		ticTacToeGame.play(1, 2); // O
		ticTacToeGame.play(1, 3); // X
		ticTacToeGame.play(2, 1); // O
		ticTacToeGame.play(2, 2); // X
		ticTacToeGame.play(3, 1); // O
		ticTacToeGame.play(3, 2); // X
		ticTacToeGame.play(3, 3); // O
		ticTacToeGame.play(2, 3); // X
		assertTrue(ticTacToeGame.isEndOfGame());
	}
	
	@Test
	void whenHaveAWinnerThenGameIsEnd() {
		ticTacToeGame.play(1, 1); // X
		ticTacToeGame.play(2, 1); // O
		ticTacToeGame.play(1, 2); // X
		ticTacToeGame.play(2, 2); // O
		ticTacToeGame.play(1, 3); // X
		assertTrue(ticTacToeGame.isEndOfGame());
	}
	
	@Test
	void whenGameStartPlayersAreNotEquals() {
		assertNotEquals(ticTacToeGame.getPlayerX(), ticTacToeGame.getPlayerO());
	}
	
	@Test
	void whenPieceIsOutsideBoardThenThrowsPiecePlacedOutsideGridException() {
		assertThrows(PiecePlacedOutsideBoardException.class, () -> 
				ticTacToeGame.play(4, 1));
    }
	
	@Test
	void whenPieceIsOutsideYThenThrowsPiecePlacedOutsideGridException() {
		assertThrows(PiecePlacedOutsideBoardException.class, () -> 
				ticTacToeGame.play(1, 4));
	}
	
	@Test
	void whenPieceIsPlacedAtOccupiedSpaceThenThrowsAlreadyOccupiedSpaceException() {
		ticTacToeGame.play(1, 1);
		assertThrows(AlreadyOccupiedSpaceException.class, () -> 
				ticTacToeGame.play(1, 1));
	}
	
	@Test
	void whenPieceIsPlacedAtOccupiedSpaceThenThrowsAlreadyOccupiedSpaceExceptionAndNextPieceIsTheSame() {
		ticTacToeGame.play(1, 1); // Player by X
		var playerO = ticTacToeGame.nextPlayer();
		assertThrows(AlreadyOccupiedSpaceException.class, () -> ticTacToeGame.play(1, 1));
		assertEquals(playerO.getPlayerSymbol(), ticTacToeGame.nextPlayer().getPlayerSymbol());
	}
	
	@Test
	void whenGameIsNotOverThenNextPlayerShouldWorks() {
		ticTacToeGame.play(1, 1); // Played by PLAYER_X
		assertEquals(PLAYER_O, ticTacToeGame.getCurrentPlayer().getPlayerSymbol());
		
		ticTacToeGame.play(2, 2); // Played by PLAYER_O
		assertEquals(PLAYER_X, ticTacToeGame.getCurrentPlayer().getPlayerSymbol());
	}
	
	@Test
	void whenPlayThenNoWinner() {
		var actualWinner = ticTacToeGame.play(1, 1);
		assertEquals(Optional.empty(), actualWinner);
	}
	
	@Test
	void whenPlayAndWholeHorizontalLineThenWinner() {
		ticTacToeGame.play(1, 1); // X
		ticTacToeGame.play(2, 1); // O
		ticTacToeGame.play(1, 2); // X
		ticTacToeGame.play(2, 2); // O
		ticTacToeGame.play(1, 3); // X
		var winnerGridPlace = new int[][]{
				{ 1, 1 },
				{ 1, 2 },
				{ 1, 3 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
		
		ticTacToeGame.restart();
		ticTacToeGame.play(2, 1); // X
		ticTacToeGame.play(1, 1); // O
		ticTacToeGame.play(2, 2); // X
		ticTacToeGame.play(1, 2); // O
		ticTacToeGame.play(2, 3); // X
		winnerGridPlace = new int[][]{
			{ 2, 1 },
			{ 2, 2 },
			{ 2, 3 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
		
		ticTacToeGame.restart();
		ticTacToeGame.play(3, 1); // X
		ticTacToeGame.play(1, 1); // O
		ticTacToeGame.play(3, 2); // X
		ticTacToeGame.play(1, 2); // O
		ticTacToeGame.play(3, 3); // X
		winnerGridPlace = new int[][]{
			{ 3, 1 },
			{ 3, 2 },
			{ 3, 3 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
	}
	
	@Test
	void whenPlayAndWholeVerticalLineThenWinner() {
		ticTacToeGame.play(1, 1); // X
		ticTacToeGame.play(2, 2); // O
		ticTacToeGame.play(1, 2); // X
		ticTacToeGame.play(3, 2); // O
		ticTacToeGame.play(1, 3); // X
		var winnerGridPlace = new int[][]{
			{ 1, 1 },
			{ 1, 2 },
			{ 1, 3 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
		
		ticTacToeGame.restart();
		ticTacToeGame.play(2, 1); // X
		ticTacToeGame.play(1, 1); // O
		ticTacToeGame.play(2, 2); // X
		ticTacToeGame.play(3, 1); // O
		ticTacToeGame.play(2, 3); // X
		winnerGridPlace = new int[][]{
			{ 2, 1 },
			{ 2, 2 },
			{ 2, 3 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
		
		ticTacToeGame.restart();
		ticTacToeGame.play(3, 1); // X
		ticTacToeGame.play(1, 1); // O
		ticTacToeGame.play(3, 2); // X
		ticTacToeGame.play(1, 2); // O
		ticTacToeGame.play(3, 3); // X
		winnerGridPlace = new int[][]{
			{ 3, 1 },
			{ 3, 2 },
			{ 3, 3 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
	}
	
	@Test
	void whenPlayAndTopBottomDiagonalLineThenWinner() {
		ticTacToeGame.play(1, 1); // X
		ticTacToeGame.play(2, 3); // O
		ticTacToeGame.play(2, 2); // X
		ticTacToeGame.play(3, 1); // O
		ticTacToeGame.play(3, 3); // X
		var winnerGridPlace = new int[][]{
			{ 1, 1 },
			{ 2, 2 },
			{ 3, 3 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
	}
	
	@Test
	void whenPlayAndBottomTopDiagonalLineThenWinner() {
		ticTacToeGame.play(1, 3); // X
		ticTacToeGame.play(2, 3); // O
		ticTacToeGame.play(2, 2); // X
		ticTacToeGame.play(1, 2); // O
		ticTacToeGame.play(3, 1); // X
		var winnerGridPlace = new int[][]{
			{ 1, 3 },
			{ 2, 2 },
			{ 3, 1 }
		};
		assertEquals(PLAYER_X, ticTacToeGame.getWinner().get().getPlayerSymbol());
		assertArrayEquals(winnerGridPlace, ticTacToeGame.getWinnerGridPlace());
	}
	
	@Test
	void whenRestartPlayersShouldBeTheSame() {
		ticTacToeGame.restart();
		assertEquals(ticTacToeGame.getPlayerX(), playerX);
		assertEquals(ticTacToeGame.getPlayerO(), playerO);
	}
	
	@Test
	void whenStartNewGamePlayersShouldBeDifferent() {
		var newPlayerX = new Player("Rafael", PLAYER_X);
		var newPlayerO = new Player("Pedro", PLAYER_O);
		ticTacToeGame.newGame(newPlayerX, newPlayerO);
		assertNotEquals(ticTacToeGame.getPlayerX(), playerX);
		assertNotEquals(ticTacToeGame.getPlayerO(), playerO);
	}

}
