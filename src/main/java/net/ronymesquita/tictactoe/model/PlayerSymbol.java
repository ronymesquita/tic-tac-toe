package net.ronymesquita.tictactoe.model;

public enum PlayerSymbol {

	PLAYER_X("×"),
	PLAYER_O("◯");

	private final String symbol;

	PlayerSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

}
