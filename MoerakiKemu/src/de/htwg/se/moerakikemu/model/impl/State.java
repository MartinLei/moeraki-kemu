package de.htwg.se.moerakikemu.model.impl;

/**
 * This enum represents the State of the controller.
 */
public enum State {

	SET_START_DOT,
	TURN_PLAYER1,
	TURN_PLAYER2,		

	WON,
	GAME_FINISHED,		// No one win the game

	EXIT_GAME			// Exit the game

	;
	
	@Override
	public String toString() {
		switch (this) {
		case SET_START_DOT:
			return "Setzen sie den Start Stein";
		case TURN_PLAYER1:
			return "Player1 sie sind dran";
		case TURN_PLAYER2:
			return "Player2 sie sind dran";
		case WON:
			return "Spiel beendet";
		case GAME_FINISHED:
			return "Spiel unentschieden beendet";
		case EXIT_GAME:
			return "Adios";
		default:
			throw new IllegalArgumentException();
		}
	}
}
