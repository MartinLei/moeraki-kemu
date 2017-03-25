package de.htwg.se.moerakikemu.model;

import de.htwg.se.moerakikemu.model.impl.Person;
import de.htwg.se.moerakikemu.model.impl.State;

/**
 * The field of the game
 */
public interface IField {

	
	/**
	 * Tries to occupy the Spot with the given coordinates.
	 * If another player already occupied the given Spot,
	 * another can not re-occupy this field.
	 * @param x The x-coordinate of the Spot to occupy.
	 * @param y The y-coordinate of the Spot to occupy.
	 * @param person The person  which occupy the cell.
	 * @return True if the Spot was empty and is now successfully occupied,
	 * 			false if the Spot was already occupied.
	 */
	boolean occupy(int x, int y,  Person person);
	
	/**
	 * Determines if the Spot with the given coordinates is currently occupied.
	 * @param x The x-coordinate of the Spot to occupy.
	 * @param y The y-coordinate of the Spot to occupy.
	 * @return True if the current Spot is occupied, else false.
	 */
	boolean getIsOccupied(int x, int y);
	
	/**
	 * Returns the name of the player that occupied the given Spot.
	 * @param x The x-coordinate of the Spot to occupy.
	 * @param y The y-coordinate of the Spot to occupy.
	 * @return The name of the player as String, or an empty String if no one occupied it.
	 */
	Person getIsOccupiedFrom(int x, int y);
	
	/**
	 * Determines if all Spots of the field are occupied.
	 * @return True if all Spots are occupied, else false.
	 */
	boolean isFilled();
	
	/**
	 * Returns the length of one edge of the (quare) field.
	 * @return
	 */
	int getEdgeLength();
	
	/**
	 * Get the game state
	 * @return state
	 */
	State getState();
	
	/**
	 * Set the game state
	 * @param newState 
	 */
	void setState(State newState);

	/**
	 * Returns the name of player1.
	 *
	 * @return A String, not null.
	 */
	String getPlayer1Name();
	
	/**
	 * Returns the name of player2.
	 *
	 * @return A String, not null.
	 */
	String getPlayer2Name();
	
	/**
	 * Returns the points of the first player.
	 *
	 * @return Amount of points, 0 or more.
	 */
	int getPlayer1Points();
	
	/**
	 * Returns the points of the second player.
	 *
	 * @return Amount of points, 0 or more.
	 */
	int getPlayer2Points();
	
	/**
	 * adds a Point to Player 1
	 * @param A String, not null
	 */
	void addAPointPlayer1();
	
	/**
	 * adds a Point to Player 2
	 * @param A String, not null
	 */
	void addAPointPlayer2();
	
	/**
	 * set player1 name
	 * @param name
	 */
	void setPlayer1Name(String name);
	
	/**
	 * set player2 name
	 * @param name
	 */
	void setPlayer2Name(String name);
}
