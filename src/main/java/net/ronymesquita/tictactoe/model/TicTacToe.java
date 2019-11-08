package net.ronymesquita.tictactoe.model;

import static net.ronymesquita.tictactoe.model.PlayerSymbol.PLAYER_O;

import java.util.Optional;

public class TicTacToe {

	private String[][] board = {
			{ "", "", "" },
			{ "", "", "" },
			{ "", "", "" }
	};
	private int[][] winnerGridPlace;

	private Player currentPlayer;

	private Player playerX;
	private Player playerO;

	public TicTacToe(Player playerX, Player playerO) {
		this.playerX = playerX;
		this.playerO = playerO;

		currentPlayer = playerX;
	}

	public Optional<Player> play(int xAxis, int yAxis) {
		checkXAxis(xAxis);
		checkOAxis(yAxis);

		var xAxisPosition = xAxis - 1;
		var yAxisPosition = yAxis - 1;
		setBox(xAxisPosition, yAxisPosition);

		currentPlayer = nextPlayer();

		return getWinner();
	}

	/**
	 * Checks if the place in the grid is not occupied and set the next player position.
	 * @param xAxisPosition X's position
	 * @param yAxisPosition O's position
	 */
	private void setBox(int xAxisPosition, int yAxisPosition) {
		if (board[xAxisPosition][yAxisPosition] != null
				&& !board[xAxisPosition][yAxisPosition].isBlank()) {
			throw new AlreadyOccupiedSpace();
		}

		board[xAxisPosition][yAxisPosition] = currentPlayer
				.getPlayerSymbol()
			.getSymbol();
	}

	private void checkOAxis(int yAxis) {
		if (yAxis < 0 || yAxis > 3) {
			throw new PiecePlacedOutsideBoard("O is outside board.");
		}
	}

	private void checkXAxis(int xAxis) {
		if (xAxis < 0 || xAxis > 3) {
			throw new PiecePlacedOutsideBoard("X is outside board.");
		}
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getPreviousPlayer() {
		return nextPlayer();
	}

	public Player nextPlayer() {
		if (currentPlayer.getPlayerSymbol().equals(PLAYER_O)) {
			return playerX;
		}

		return playerO;
	}

	public Optional<Player> getWinner() {
		if (isTheWinner(playerX)) {
			return Optional.of(playerX);
		}

		if (isTheWinner(playerO)) {
			return Optional.of(playerO);
		}

		return Optional.empty();
	}

	public void restart(Player playerX, Player playerO) {
		board = new String[][]{
				{ "", "", "" },
				{ "", "", "" },
				{ "", "", "" }
		};
		this.playerX = playerX;
		this.playerO = playerO;
		currentPlayer = playerX;
		winnerGridPlace = null;
	}

	private boolean isTheWinner(Player player) {
		var symbolText = player.getPlayerSymbol().getSymbol();

		if (board[0][0].equals(symbolText)
				&& board[0][1].equals(symbolText)
				&& board[0][2].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 1, 1 },
				{ 1, 2 },
				{ 1, 3 }
			};
			return true;
		} else if (board[1][0].equals(symbolText)
				&& board[1][1].equals(symbolText)
				&& board[1][2].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 2, 1 },
				{ 2, 2 },
				{ 2, 3 }
			};
			return true;
		} else if (board[2][0].equals(symbolText)
				&& board[2][1].equals(symbolText)
				&& board[2][2].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 3, 1 },
				{ 3, 2 },
				{ 3, 3 }
			};
			return true;
		} else if (board[0][0].equals(symbolText)
				&& board[1][0].equals(symbolText)
				&& board[2][0].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 1, 1 },
				{ 2, 1 },
				{ 3, 1 }
			};
			return true;
		} else if (board[0][1].equals(symbolText)
				&& board[1][1].equals(symbolText)
				&& board[2][1].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 1, 2 },
				{ 2, 2 },
				{ 3, 2 }
			};
			return true;
		} else if (board[0][2].equals(symbolText)
				&& board[1][2].equals(symbolText)
				&& board[2][2].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 1, 3 },
				{ 2, 3 },
				{ 3, 3 }
			};
			return true;
		} else if (board[0][0].equals(symbolText)
				&& board[1][1].equals(symbolText)
				&& board[2][2].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 1, 1 },
				{ 2, 2 },
				{ 3, 3 }
			};
			return true;
		} else if (board[0][2].equals(symbolText)
				&& board[1][1].equals(symbolText)
				&& board[2][0].equals(symbolText)) {
			winnerGridPlace = new int[][] {
				{ 1, 3 },
				{ 2, 2 },
				{ 3, 1 }
			};
			return true;
		}

		return false;
	}

	public Player getPlayerX() {
		return playerX;
	}

	public Player getPlayerO() {
		return playerO;
	}

	public int[][] getWinnerGridPlace() {
		return winnerGridPlace;
	}

	public boolean isEndOfGame() {
		if (getWinner().isPresent()) {
			return true;
		}

		var occupiedSpacesCount = 0;
		for (String[] lines : board) {
			for (String gridPosition : lines) {
				if (! gridPosition.isBlank()) {
					occupiedSpacesCount++;
				}
			}
		}

		return occupiedSpacesCount == 9;
	}

}

