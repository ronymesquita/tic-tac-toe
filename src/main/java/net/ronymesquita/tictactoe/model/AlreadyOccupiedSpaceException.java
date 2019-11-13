package net.ronymesquita.tictactoe.model;

public class AlreadyOccupiedSpaceException extends RuntimeException {

	private static final long serialVersionUID = -4823320664071888488L;

	public AlreadyOccupiedSpaceException() {
		super("O espaço do tabuleiro já está ocupado");
	}

	public AlreadyOccupiedSpaceException(String message) {
		super(message);
	}

}
