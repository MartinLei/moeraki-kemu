package de.htwg.se.moerakikemu.model.impl;

public class Spot {

	private Person occupiedBy = Person.NONE;

	public Spot() {
		occupiedBy = Person.NONE;
	}

	public boolean occupy(final Person person) {
		if (isOccupied())
			return false;

		occupiedBy = person;
		return true;
	}

	public boolean isOccupied() {
		return occupiedBy != Person.NONE;
	}

	public Person getOccupiedBy() {
		return occupiedBy;
	}

}
