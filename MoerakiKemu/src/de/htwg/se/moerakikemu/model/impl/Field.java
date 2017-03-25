package de.htwg.se.moerakikemu.model.impl;

import de.htwg.se.moerakikemu.model.IField;

public class Field implements IField {
	private int edgeLength;
	private int occupiedSpots;
	private Spot[][] array;
	
	private State state;
	
	
	public Field(int edgeLength) throws IllegalArgumentException {
		if(edgeLength < 1) {
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
	
	public int getEdgeLength() {
		return this.edgeLength;
	}

	public boolean getIsOccupied(int x, int y) {
		return !getIsOccupiedFrom(x, y).isEmpty();
	}

	public boolean occupy(int x, int y, final String playerName) {
		if (array[x][y].isOccupied()) {
			return false;
		} else {
			array[x][y].occupy(playerName);
			occupiedSpots++;
			return true;
		}
	}

	public String getIsOccupiedFrom(int xCoordinate, int yCoordinate) {
		if(array[xCoordinate][yCoordinate].isOccupied()){
			return array[xCoordinate][yCoordinate].getOccupiedByPlayer();
		}
		return "";
	}

	public boolean isFilled(){
		return occupiedSpots == (edgeLength * edgeLength);
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State newState) {
		state  = newState;
	}
	
}
