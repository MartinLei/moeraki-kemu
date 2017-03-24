package de.htwg.se.moerakikemu.controller;

/**
 * This enum represents the State of the controller.
 */
public enum State {

	PLAYER_WON,			// A Player won the game
	GAME_FINISHED,		

	GET_FIRST_PLAYER_NAME,
	GET_SECOND_PLAYER_NAME,
	SET_START_DOT,
	TURN_PLAYER1,
	TURN_PLAYER2,		
	EXIT_GAME			// Exit the game
	;
	
}
