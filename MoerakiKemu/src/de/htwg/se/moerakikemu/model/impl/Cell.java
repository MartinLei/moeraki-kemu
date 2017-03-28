package de.htwg.se.moerakikemu.model.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Cell {
	private final List<Point> playerPosition;
	private final Point islandPosition;

	public Cell(List<Point> playerPosition, Point islandPosition) {
		this.playerPosition = playerPosition;
		this.islandPosition = islandPosition;
	}

	public List<Point> getPlayerPosition() {
		return playerPosition;
	}

	public Point getIslandPosition() {
		return islandPosition;
	}

}
