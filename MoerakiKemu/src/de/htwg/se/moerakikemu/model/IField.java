package de.htwg.se.moerakikemu.model;

import java.awt.Point;
import java.util.List;

import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;

/**
 * The field of the game
 */
public interface IField {

	/**
	 * Tries to occupy the Spot with the given coordinates. If another player
	 * already occupied the given Spot, another can not re-occupy this field.
	 * 
	 * @param position
	 * @param person
	 *            The person which occupy the cell.
	 * @return True if the Spot was empty and is now successfully occupied,
	 *         false if the Spot was already occupied.
	 */
	boolean occupy(Point position, Element person);

	/**
	 * get the count of how many cells occupied given player
	 * 
	 * @param cells
	 *            to test
	 * @param player
	 *            witch search of
	 * @return amount of given player on the cells
	 */
	int getOccupiedCount(List<Point> cells, Element player);

	/**
	 * Determines if the Spot with the given coordinates is currently occupied.
	 * 
	 * @param position
	 * @return True if the current Spot is occupied, else false.
	 */
	boolean isOccupied(Point position);

	/**
	 * Returns the element of the cell
	 * 
	 * @param position
	 * @return Element
	 */
	Element getElement(Point position);

	/**
	 * Returns the length of one edge of the (quare) field.
	 * 
	 * @return
	 */
	int getEdgeLength();

	/**
	 * Get the game state
	 * 
	 * @return state
	 */
	State getState();

	/**
	 * Set the game state
	 * 
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
	 * 
	 * @param A
	 *            String, not null
	 */
	void addAPointPlayer1();

	/**
	 * adds a Point to Player 2
	 * 
	 * @param A
	 *            String, not null
	 */
	void addAPointPlayer2();

	/**
	 * set player1 name
	 * 
	 * @param name
	 */
	void setPlayer1Name(String name);

	/**
	 * set player2 name
	 * 
	 * @param name
	 */
	void setPlayer2Name(String name);

	/**
	 * get current player
	 * 
	 * @return player element
	 */
	Element getCurrentPlayer();

	/**
	 * get next player
	 * 
	 * @return player element
	 */
	Element getNextPlayer();

	/**
	 * get the player point element of the current player
	 * 
	 * @return player point element
	 */
	Element getCurrentPlayerPointElement();

	/**
	 * change actual player
	 */
	void changeActPlayer();

}
