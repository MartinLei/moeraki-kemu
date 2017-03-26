package de.htwg.se.moerakikemu.controller;

import java.awt.Point;

import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.IObservable;

/**
 * Controller interface
 */
public interface IController extends IObservable {

	/**
	 * Returns the element on the cell of the field
	 * @param x
	 *            X-Coordinate.
	 * @param y
	 *            Y-Coordinate.
	 * @return element
	 */
	Element getFieldElement(int x, int y);

	/**
	 * Returns the length of one edge of the game field.
	 *
	 * @return Number > 0.
	 */
	int getEdgeLength();

	/**
	 * quit the game and set the sate to exit game
	 */
	void quitGame();

	/**
	 * Returns the current State for the game
	 * 
	 * @return a constant from the enum State.
	 */
	State getState();

	/**
	 * Start a new game and set the state to name player1
	 */
	void newGame();
	
	/**
	 * Start a new game and set the state to turn player1
	 * Give the player names and set the start stone for a quick start
	 */
	void newGameQuickStart();
	

	/**
	 * Set the name of player1 After setting, it change the sate to set the name
	 * of player2
	 * 
	 * @param name
	 *            of player1
	 */
	void setPlayer1Name(String name);

	/**
	 * Set the name of player2 After setting, it change the sate to set the
	 * start dot
	 * 
	 * @param name
	 *            of player2
	 */
	void setPlayer2Name(String name);

	/**
	 * Give the name of player1
	 * 
	 * @return name
	 */
	String getPlayer1Name();

	/**
	 * Give the points of player1
	 * 
	 * @return points
	 */
	int getPlayer1Point();

	/**
	 * Give the name of player2
	 * 
	 * @return name
	 */
	String getPlayer2Name();

	/**
	 * Give the points of player2
	 * 
	 * @return points
	 */
	int getPlayer2Point();

	/**
	 * Set the start stone on the given position After setting the stone, it
	 * change the state to the first player
	 * 
	 * @param position
	 * @return false if the position is not on the allowed position
	 */
	boolean setStartDot(Point position);

	/**
	 * Set a player stone on the given position After setting the stone, it
	 * change the sate to the next player
	 * 
	 * @param position
	 * @return false if the position is already occupied
	 */
	boolean setDot(Point position);

	/**
	 * Get the actual Person
	 * @return person
	 */
	Element getActualPerson();

	/**
	 * Save game to db
	 */
	void saveToDB();
	
	/**
	 * Load game from db
	 * @return true if load was no problem
	 */
	boolean loadDB();
}
