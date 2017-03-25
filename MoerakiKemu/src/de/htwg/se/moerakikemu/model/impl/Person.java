package de.htwg.se.moerakikemu.model.impl;

public enum Person {
	NONE, PLAYER1, PLAYER2, STARTDOT;
	
	
	@Override
	public String toString() {
		switch (this) {
		case NONE:
			return " ";
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
