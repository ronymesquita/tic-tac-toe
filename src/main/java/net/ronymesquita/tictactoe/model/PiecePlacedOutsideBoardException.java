package net.ronymesquita.tictactoe.model;

public class PiecePlacedOutsideBoardException extends RuntimeException {

	private static final long serialVersionUID = 4029989932604597995L;

	public PiecePlacedOutsideBoardException() {
		super("Piece placed outside board.");
	}

	public PiecePlacedOutsideBoardException(String message) {
		super(message);
	}

}
