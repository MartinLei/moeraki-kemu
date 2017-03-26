package de.htwg.se.moerakikemu.model.impl;

/**
 * Enum for the state of a spot
 */
public enum Element {
	NONE, WALL, PLAYER1, PLAYER2, STARTDOT;
	
	
	@Override
	public String toString() {
		switch (this) {
		case NONE:
			return "-";
		case WALL:
			return "W";
		case PLAYER1:
			return "+";
		case PLAYER2:
			return "o";
		case STARTDOT:
			return "#";
		default:
			throw new IllegalArgumentException();
		}
	}
}
