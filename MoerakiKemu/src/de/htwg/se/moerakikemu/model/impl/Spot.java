package de.htwg.se.moerakikemu.model.impl;

/**
 * The Spot contains which player occupied it
 */
public class Spot {

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
		if (isOccupied())
			return false;

		occupiedBy = person;
		return true;
	}

	/**
	 * Tell if a player has occupied the cell
	 * 
	 * @return false if free
	 */
	public boolean isOccupied() {
		return occupiedBy == Element.PLAYER1 || occupiedBy == Element.PLAYER2;
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
