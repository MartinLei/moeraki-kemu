package de.htwg.se.moerakikemu.controller;

import de.htwg.se.util.observerNEW.IObservable;

public interface IController extends IObservable {

	/**
	 * Returns the name of the player that occupies the field with the coordinates.
	 *
	 * @param x X-Coordinate.
	 * @param y Y-Coordinate.
	 * @return The name of the player or an empty String. 
	 */
	String getIsOccupiedByPlayer(int x, int y);
	
	/**
	 * Returns the length of one edge of the game field.
	 *
	 * @return Number > 0.
	 */
	int getEdgeLength();
	
	/**
	 * Occupation of a Spot by a player. Returns -1 if the Spot is already occupied.
	 * @param xCoordinate X coordinate of the spot beginning from 1 to edgeLength.
	 * @param yCoordinate Y coordinate of the spot beginning from 1 to edgeLength.
	 * @param playerName Name of the Player.
	 * @return returns 0 if the current player occupied the field and got points;
	 * -1 if the spot already was occupied.
	 */
	int occupy(int xCoordinate, int yCoordinate);
	
	/**
	 * Returns the name of the player if a player has won, else an empty String.
	 * @return A String, not null.
	 */
	String getWinner();
	
	/**
	 * Tests if there is a winner:
	 * @return True if there is a winner, false when there isn't one;
	 */
	boolean testIfWinnerExists();
	
	/**
	 * Test if the game is over os must be terminated.
	 * @return true if there is a winner, false when there isn't one.
	 */
	boolean testIfEnd();
	
	/**
	 * set if the game ends
	 * @param a boolean Value
	 */
	void setEnd(boolean end);

	/**
	 * exit game handler
	 */
	void quitGame();
	
	/**
	 * Returns the current State for the Controller to determine the correct UI response.
	 * @return A constant from the enum State.
	 */
	public abstract State getState();
	
	/**
	 * Reset all values without the names of the players to zero.
	 */
	void newGame();

}
