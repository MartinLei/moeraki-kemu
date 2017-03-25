package de.htwg.se.moerakikemu.model.impl;

import de.htwg.se.moerakikemu.model.IField;

public class Field implements IField {
	private int edgeLength;
	private int occupiedSpots;
	private Spot[][] array;

	private State state;

	public Field(int edgeLength) throws IllegalArgumentException {
		if (edgeLength < 1) {
			throw new IllegalArgumentException("Edgelength too small: " + edgeLength);
		}
		this.edgeLength = edgeLength;
		array = new Spot[edgeLength][edgeLength];
		for (int i = 0; i < edgeLength; i++) {
			for (int j = 0; j < edgeLength; j++) {
				array[i][j] = new Spot();
			}
		}
		occupiedSpots = 0;
	}

	@Override
	public int getEdgeLength() {
		return this.edgeLength;
	}

	@Override
	public boolean getIsOccupied(int x, int y) {
		return array[x][y].isOccupied();
	}

	@Override
	public boolean occupy(int x, int y, Person person) {
		if (array[x][y].isOccupied())
			return false;

		array[x][y].occupy(person);
		occupiedSpots++;
		return true;
	}

	@Override
	public Person getIsOccupiedFrom(int xCoordinate, int yCoordinate) {
		return array[xCoordinate][yCoordinate].getOccupiedBy();
	}

	@Override
	public boolean isFilled() {
		return occupiedSpots == (edgeLength * edgeLength);
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State newState) {
		state = newState;
	}

}
