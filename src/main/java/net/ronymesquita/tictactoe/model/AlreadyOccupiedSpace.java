package net.ronymesquita.tictactoe.model;

public class AlreadyOccupiedSpace extends RuntimeException {

	private static final long serialVersionUID = -4823320664071888488L;

	public AlreadyOccupiedSpace() {
		super("O espaço do tabuleiro já está ocupado");
	}

	public AlreadyOccupiedSpace(String message) {
		super(message);
	}

}
