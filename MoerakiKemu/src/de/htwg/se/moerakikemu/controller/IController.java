package de.htwg.se.moerakikemu.controller;

import java.awt.Point;
import java.util.List;

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
	 * give the point of player1 or player2
	 * @param player player1 or player2
	 * @return points of given player
	 */
	int getPlayerPoint(Element player);

	/**
	 * Give the name of player
	 * 
	 * @return name
	 */
	String getPlayerName(Element player);

	/**
	 * tell if the position is a possible position for a player
	 * 
	 * @param position
	 * @return if the position is possible to set a dot
	 */
	//boolean isPositionPossibleInput(Point position);
	
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
	 * get current player element
	 * @return
	 */
	Element getCurrentPlayer();
	
	/**
	 * Name of current player
	 * @return name
	 */
	String getCurrentPlayerName();
	
	/**
	 * get Field as List
	 * @return field 
	 */
	List<Element> getField();
	
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
