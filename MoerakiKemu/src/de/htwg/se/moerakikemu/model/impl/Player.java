package de.htwg.se.moerakikemu.model.impl;

import de.htwg.se.moerakikemu.model.IPlayer;

/**
 * Implementation of the player
 */
public class Player implements IPlayer {

	private int points;
	private String name;

	/**
	 * Initial a new Player with no points and non name
	 */
	public Player() {
		name = "";
		points = 0;
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addPoints(int amount) {
		if (amount >= 0) {
			points += amount;
		}
	}

	@Override
	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	@Override
	public void refreshPoints() {
		points = 0;
	}
}
