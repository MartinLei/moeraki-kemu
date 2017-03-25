package de.htwg.se.moerakikemu.model.impl;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.IPlayer;

/**
 * The implementation of field
 */
public class Field implements IField {
	private static final int EDGE_LENGTH = 12;
	private int occupiedSpots;
	private Spot[][] array;

	private State state;
	
	private IPlayer player1;
	private IPlayer player2;
	
	public Field() {
		array = new Spot[EDGE_LENGTH][EDGE_LENGTH];
		for (int i = 0; i < EDGE_LENGTH; i++) {
			for (int j = 0; j < EDGE_LENGTH; j++) {
				array[i][j] = new Spot();
			}
		}
		occupiedSpots = 0;

		player1 = new Player();
		player2 = new Player();
	}
	
	@Override
	public int getEdgeLength() {
		return EDGE_LENGTH;
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
		return occupiedSpots == (EDGE_LENGTH * EDGE_LENGTH);
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State newState) {
		state = newState;
	}

	@Override
	public String getPlayer1Name() {
		return player1.getName();
	}

	@Override
	public String getPlayer2Name() {
		return player2.getName();
	}

	@Override
	public int getPlayer1Points() {
		return player1.getPoints();
	}

	@Override
	public int getPlayer2Points() {
		return player2.getPoints();
	}

	@Override
	public void addAPointPlayer1() {
		player1.addPoints(1);
	}

	@Override
	public void addAPointPlayer2() {
		player2.addPoints(1);
	}

	@Override
	public void setPlayer1Name(String name) {
		player1.setName(name);
	}

	@Override
	public void setPlayer2Name(String name) {
		player2.setName(name);
	}
}
