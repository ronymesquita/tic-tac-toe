package net.ronymesquita.tictactoe.model;

import java.util.Objects;

public class Player implements Comparable<Player> {

	private final String name;
	private final PlayerSymbol playerSymbol;

	public Player(String name, PlayerSymbol playerSymbol) {
		this.name = name;
		this.playerSymbol = playerSymbol;
	}

	public String getName() {
		return name;
	}

	public PlayerSymbol getPlayerSymbol() {
		return playerSymbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, playerSymbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player) obj;
		return Objects.equals(name, other.name) && playerSymbol == other.playerSymbol;
	}

	@Override
	public int compareTo(Player otherPlayer) {
		return name.compareTo(otherPlayer.name);
	}
}
