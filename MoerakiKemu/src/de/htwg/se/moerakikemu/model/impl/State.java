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
	
}
