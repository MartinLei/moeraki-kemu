package de.htwg.se.moerakikemu.modellayer;

import de.htwg.se.moerakikemu.modellayer.impl.State;

public interface IField {

	/**
	 * Tries to occupy the Spot with the given coordinates.
	 * If another player already occupied the given Spot,
	 * another can not re-occupy this field.
	 * @param x The x-coordinate of the Spot to occupy.
	 * @param y The y-coordinate of the Spot to occupy.
	 * @param getCurrentPlayerName The name of the player that tries to occupy.
	 * @return True if the Spot was empty and is now successfully occupied,
	 * 			false if the Spot was already occupied.
	 */
	boolean occupy(int x, int y, String getCurrentPlayerName);
	
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
	String getIsOccupiedFrom(int x, int y);
	
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
}
