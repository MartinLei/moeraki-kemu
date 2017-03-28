package de.htwg.se.moerakikemu.model.impl;

/**
 * Enum for the state of a spot
 */
public enum Element {
	NONE, WALL, ISLAND,PLAYER1, PLAYER2, POINT_PLAYER1, POINT_PLAYER2, HALF_POINT_PLAYER1, HALF_POINT_PLAYER2, STARTDOT;
	
	
	@Override
	public String toString() {
		switch (this) {
		case NONE:
			return " ";
		case WALL:
			return "W";
		case ISLAND:
			return "O";
		case PLAYER1:
			return "a";
		case PLAYER2:
			return "b";
		case POINT_PLAYER1:
			return "A";
		case POINT_PLAYER2:
			return "B";
		case HALF_POINT_PLAYER1:
			return "A";
		case HALF_POINT_PLAYER2:
			return "B";
		case STARTDOT:
			return "#";
		default:
			throw new IllegalArgumentException();
		}
	}
}
