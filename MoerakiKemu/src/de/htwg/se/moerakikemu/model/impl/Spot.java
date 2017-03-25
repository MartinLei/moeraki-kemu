package de.htwg.se.moerakikemu.model.impl;

/**
 * The Spot contains which player occupied it
 */
public class Spot {

	private Person occupiedBy;

	/**
	 * Initial a spot with no person who occupied it
	 */
	public Spot() {
		occupiedBy = Person.NONE;
	}

	/**
	 * occupy the spot 
	 * @param person a person
	 * @return true if the cell is not occupied
	 */
	public boolean occupy(final Person person) {
		if (isOccupied())
			return false;

		occupiedBy = person;
		return true;
	}

	/**
	 * Tell if a player has occupied the cell
	 * @return false if free
	 */
	public boolean isOccupied() {
		return occupiedBy != Person.NONE;
	}

	/**
	 * Get the person who occupied it
	 * @return
	 */
	public Person getOccupiedBy() {
		return occupiedBy;
	}

}
