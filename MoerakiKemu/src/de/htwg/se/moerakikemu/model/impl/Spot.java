package de.htwg.se.moerakikemu.model.impl;

import java.io.Serializable;

/**
 * The Spot contains which player occupied it
 */
public class Spot implements Serializable{

	private Element occupiedBy;

	/**
	 * Initial a spot with no person who occupied it
	 */
	public Spot() {
		occupiedBy = Element.NONE;
	}

	public void setElement(Element element) {
		occupiedBy = element;
	}

	/**
	 * occupy the spot
	 * 
	 * @param person
	 *            a person
	 * @return true if the cell is not occupied
	 */
	public boolean occupy(final Element person) {
		if (isOccupiedByPlayer())
			return false;

		occupiedBy = person;
		return true;
	}

	/**
	 * Tell if a player has occupied the cell
	 * 
	 * @return false if free
	 */
	public boolean isOccupiedByPlayer() {
		return occupiedBy == Element.PLAYER1 || occupiedBy == Element.PLAYER2 || occupiedBy == Element.STARTDOT;
	}

	/**
	 * Get the person who occupied it
	 * 
	 * @return
	 */
	public Element getElement() {
		return occupiedBy;
	}

}
