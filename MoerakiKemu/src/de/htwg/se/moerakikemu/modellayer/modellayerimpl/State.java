package de.htwg.se.moerakikemu.modellayer.modellayerimpl;

/**
 * This enum represents the State of the controller.
 */
public enum State {

	GET_FIRST_PLAYER_NAME, 
	GET_SECOND_PLAYER_NAME,
	SET_START_DOT,
	TURN_PLAYER1,
	TURN_PLAYER2,		

	PLAYER2_WON,		// player2 win the game
	PLAYER1_WON,		// player1 win the game
	GAME_FINISHED,		// No one win the game

	EXIT_GAME			// Exit the game
	;
	
}
