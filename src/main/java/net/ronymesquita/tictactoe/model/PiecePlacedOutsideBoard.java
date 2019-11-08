package net.ronymesquita.tictactoe.model;

public class PiecePlacedOutsideBoard extends RuntimeException {

	private static final long serialVersionUID = 4029989932604597995L;

	public PiecePlacedOutsideBoard() {
		super("Peça colocada fora do tabuleiro");
	}

	public PiecePlacedOutsideBoard(String message) {
		super(message);
	}

}
