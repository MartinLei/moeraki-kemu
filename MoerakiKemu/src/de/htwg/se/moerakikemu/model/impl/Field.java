package de.htwg.se.moerakikemu.model.impl;

import java.awt.Point;
import java.util.List;

import de.htwg.se.moerakikemu.controller.impl.Rule;
import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.IPlayer;

/**
 * The implementation of field
 */
public class Field implements IField {
	private static final int MAP_LENGTH = 13;

	private static final int EDGE_LENGTH = 12;

	private Spot[][] map;

	private State state;

	private IPlayer player1;
	private IPlayer player2;

	private Element actualPlayer;

	/**
	 * Constructor
	 */
	public Field() {
		generateMap();

		player1 = new Player();
		player2 = new Player();

		actualPlayer = Element.PLAYER1;
	}

	private void generateMap() {
		map = new Spot[MAP_LENGTH][MAP_LENGTH];
		generateMapclean();
		generateMapWall();
	}

	private void generateMapclean() {
		for (int y = 0; y < MAP_LENGTH; y++) {
			for (int x = 0; x < MAP_LENGTH; x++) {
				map[x][y] = new Spot();

				if (x % 2 == 1 && y % 2 == 0 || x % 2 == 0 && y % 2 == 1)
					map[x][y].setElement(Element.ISLAND);

			}
		}
	}

	private void generateMapWall() {
		for (int i = 0; i < MAP_LENGTH; i++) {
			// top row
			map[i][0].setElement(Element.WALL);
			// bottom row
			map[i][12].setElement(Element.WALL);
			// left side
			map[0][i].setElement(Element.WALL);
			// right side
			map[12][i].setElement(Element.WALL);
		}

		// top left corner
		map[1][1].setElement(Element.WALL);
		map[2][1].setElement(Element.WALL);
		map[1][2].setElement(Element.WALL);

		// top right corner
		map[10][1].setElement(Element.WALL);
		map[11][1].setElement(Element.WALL);
		map[11][2].setElement(Element.WALL);

		// bottom left corner
		map[1][10].setElement(Element.WALL);
		map[1][11].setElement(Element.WALL);
		map[2][11].setElement(Element.WALL);

		// bottom right corner
		map[11][10].setElement(Element.WALL);
		map[10][11].setElement(Element.WALL);
		map[11][11].setElement(Element.WALL);
	}

	@Override
	public int getEdgeLength() {
		return EDGE_LENGTH;
	}

	@Override
	public boolean isOccupied(Point position) {
		return map[position.x][position.y].isOccupied();
	}

	@Override
	public boolean occupy(Point position, Element person) {
		if (isOccupied(position))
			return false;

		map[position.x][position.y].occupy(person);
		return true;
	}
	
	@Override
	public int getOccupiedCount(List<Point> cells, Element player) {
		int count = 0;
		for (Point cell : cells) {
			Element cellElement = getElement(cell);
			if (cellElement != null && cellElement.equals(player))
				count++;
		}
		return count;
	}


	@Override
	public Element getElement(Point position) {
		if (position == null)
			return null;

		return map[position.x][position.y].getElement();
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

	@Override
	public Element getCurrentPlayer() {
		return actualPlayer;
	}

	@Override
	public Element getNextPlayer() {
		if (actualPlayer.equals(Element.PLAYER1))
			return Element.PLAYER2;
		else
			return Element.PLAYER1;
	}

	@Override
	public void changeActPlayer() {
		if (actualPlayer.equals(Element.PLAYER1)){
			actualPlayer = Element.PLAYER2;
			setState(State.TURN_PLAYER2);
		}else{
			actualPlayer = Element.PLAYER1;
			setState(State.TURN_PLAYER1);
		}
	}

	@Override
	public Element getCurrentPlayerPointElement() {
		Element currentPlayer = getCurrentPlayer();
		if(currentPlayer.equals(Element.PLAYER1))
			return Element.POINT_PLAYER1;
		else 
			return Element.POINT_PLAYER2;
	}

	@Override
	public Element getCurrentPlayerHalfPointElement() {
		Element currentPlayer = getCurrentPlayer();
		if(currentPlayer.equals(Element.PLAYER1))
			return Element.HALF_POINT_PLAYER1;
		else 
			return Element.HALF_POINT_PLAYER2;
	}
}
