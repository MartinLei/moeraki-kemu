package de.htwg.se.moerakikemu.controller.impl;

import java.awt.Point;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Field;
import de.htwg.se.moerakikemu.model.impl.Person;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;
import de.htwg.se.moerakikemu.util.observer.Observable;

/**
 * The controller implementation
 */
@Singleton
public class Controller extends Observable implements IController {
	private IField gameField;
	private IFieldDAO fieldDAO;
	
	private ControllerHelper helper;
	private int xCoordinateStartDot;
	private int yCoordinateStartDot;


	@Inject
	public Controller(IFieldDAO fieldDAO) {
		this.fieldDAO = fieldDAO;
		
		gameField = new Field();
		xCoordinateStartDot = 0;
		yCoordinateStartDot = 0;

		gameField.setState(State.GET_FIRST_PLAYER_NAME);
	}

	@Override
	public void newGame() {
		gameField = new Field();
		gameField.setState(State.GET_FIRST_PLAYER_NAME);

		notifyObservers();
	}

	@Override
	public void newGameQuickStart() {
		gameField = new Field();
		
		String player1Name = "Andrew";
		String player2Name = "Walter";
		gameField.setPlayer1Name(player1Name);
		gameField.setPlayer2Name(player2Name);

		gameField.setState(State.SET_START_DOT);
		Point starDotPosisiton = new Point(4, 4);
		setStartDot(starDotPosisiton);
	}

	@Override
	public Person getIsOccupiedBy(int x, int y) {
		return gameField.getIsOccupiedFrom(x, y);
	}

	@Override
	public int getEdgeLength() {
		return gameField.getEdgeLength();
	}

	private int occupy(int x, int y) {
		if (!gameField.getIsOccupiedFrom(x, y).equals(Person.NONE)) 
			return -1;
		

		Person actPerson = getActualPerson();
		gameField.occupy(x, y, actPerson);
		helper = new ControllerHelper(x, y, getEdgeLength() - 1);
		helper.testSquare();
		testListOfSquares();
		testAllInLine(x, y);
		
		helper.resetSquareTest();

		if (gameField.isFilled()) {
			gameField.setState(State.GAME_FINISHED);
			notifyObservers();
		}

		return 0;
	}

	@Override
	public Person getActualPerson() {
		State sate = gameField.getState();
		if (sate.equals(State.TURN_PLAYER1))
			return Person.PLAYER1;
		else if (sate.equals(State.TURN_PLAYER2))
			return Person.PLAYER2;
		else if (sate.equals(State.SET_START_DOT))
			return Person.STARTDOT;

		return null;
	}

	private boolean setStartDot(int xCoordinate, int yCoordinate) {
		int radiusLow;
		int radiusUp;
		int length = getEdgeLength() - 1;
		if (getEdgeLength() % 2 != 0) {
			radiusLow = (length / 2) - 1;
			radiusUp = (length / 2) + 1;
		} else {
			radiusLow = (length / 2) - 1;
			radiusUp = (length / 2) + 2;
		}

		boolean isInMidX = xCoordinate >= radiusLow && xCoordinate <= radiusUp;
		boolean isInMidY = yCoordinate >= radiusLow && yCoordinate <= radiusUp;
		if (isInMidX && isInMidY) {
			xCoordinateStartDot = xCoordinate;
			yCoordinateStartDot = yCoordinate;
			return true;
		}
		return false;
	}

	private void testListOfSquares() {
		int number = 17;
		int[] squareArray = new int[number];
		squareArray = helper.getSquareArray();
		if (squareArray[0] == 1) {
			testSquare(squareArray[1], squareArray[2], squareArray[3], squareArray[4]);
		} else if (squareArray[0] == 2) {
			testSquare(squareArray[1], squareArray[2], squareArray[3], squareArray[4]);
			testSquare(squareArray[5], squareArray[6], squareArray[7], squareArray[8]);
		} else if (squareArray[0] == 4) {
			for (int i = 1; i < 17; i += 4) {
				testSquare(squareArray[i], squareArray[i + 1], squareArray[i + 2], squareArray[i + 3]);
			}
		}
	}

	private void testSquare(int xMin, int yMin, int xMax, int yMax) {
		int[] counterForPlayers = { 0, 0 };

		int index;
		index = checkOccupationReturnPlayerGettingPoint(xMin, yMin);
		if (index != -1) {
			counterForPlayers[index]++;
		}
		index = checkOccupationReturnPlayerGettingPoint(xMin, yMax);
		if (index != -1) {
			counterForPlayers[index]++;
		}
		index = checkOccupationReturnPlayerGettingPoint(xMax, yMin);
		if (index != -1) {
			counterForPlayers[index]++;
		}
		index = checkOccupationReturnPlayerGettingPoint(xMax, yMax);
		if (index != -1) {
			counterForPlayers[index]++;
		}

		setPointsOfPlayer(counterForPlayers[0], counterForPlayers[1]);
	}

	private int checkOccupationReturnPlayerGettingPoint(final int x, final int y) {
		Person person = gameField.getIsOccupiedFrom(x, y);

		if (person.equals(Person.PLAYER1)) {
			return 0;
		} else if (person.equals(Person.PLAYER2)) {
			return 1;
		} else {
			return -1;
		}
	}

	private void setPointsOfPlayer(int counter1, int counter2) {
		if (counter1 == 3 && counter2 == 1) {
			gameField.addAPointPlayer1();
		}
		if (counter1 == 4) {
			gameField.addAPointPlayer1();

			gameField.setState(State.PLAYER1_WON);
			notifyObservers();
		}
		if (counter2 == 3 && counter1 == 1) {
			gameField.addAPointPlayer2();
		}
		if (counter2 == 4) {
			gameField.addAPointPlayer2();

			gameField.setState(State.PLAYER2_WON);
			notifyObservers();
		}
	}

	private void testAllInLine(int x, int y) {
		if (!testIfNearStartDot(x, y)) {
			return;
		}
		int distanceTop = xCoordinateStartDot;
		int distanceBot = getEdgeLength();
		int distanceRight = getEdgeLength();
		int distanceLeft = yCoordinateStartDot;

		if (y == yCoordinateStartDot) {
			if (x > xCoordinateStartDot) {
				testInLine("x", xCoordinateStartDot, distanceBot, y, getEdgeLength() - xCoordinateStartDot - 1);
			} else {

				testInLine("x", 0, distanceTop, y, distanceTop);
			}
		} else if (x == xCoordinateStartDot) {
			if (y < yCoordinateStartDot) {
				testInLine("y", 0, distanceLeft, x, distanceLeft);
			} else {
				testInLine("y", yCoordinateStartDot, distanceRight, x, getEdgeLength() - yCoordinateStartDot - 1);
			}
		}
	}

	private boolean isOccupiedByCurrentPlayer(final int x, final int y) {
		Person actualPerson = getActualPerson();
		Person fieldPerson =  gameField.getIsOccupiedFrom(x, y);
		return fieldPerson.equals(actualPerson);
	}

	private void testInLine(String xy, int start, int end, int secondValue, int counterEnd) {
		int counter = 0;
		for (int i = start; i < end; i++) {
			if ("x".equals(xy) && isOccupiedByCurrentPlayer(i, secondValue)) {
				counter++;
			} else if ("y".equals(xy) && isOccupiedByCurrentPlayer(secondValue, i)) {
				counter++;
			}
		}
		if (counter == counterEnd) {

			// TODO LINE strike
			// getState());
			// if (getState().equals(State.TURN_PLAYER1))
			// state = State.PLAYER1_WON;
			// else
			// state = State.PLAYER2_WON;
			// notifyObservers();
		}
	}

	private boolean testIfNearStartDot(int x, int y) {
		if (xCoordinateStartDot == x || yCoordinateStartDot == y) {
			return true;
		}
		return false;
	}

	@Override
	public State getState() {
		return gameField.getState();
	}

	@Override
	public void quitGame() {
		gameField.setState(State.EXIT_GAME);
		notifyObservers();
	}

	@Override
	public void setPlayer1Name(String name) {
		gameField.setPlayer1Name(name);
		gameField.setState(State.GET_SECOND_PLAYER_NAME);
		notifyObservers();
	}

	@Override
	public void setPlayer2Name(String name) {
		gameField.setPlayer2Name(name);
		gameField.setState(State.SET_START_DOT);
		notifyObservers();
	}

	@Override
	public String getPlayer1Name() {
		return gameField.getPlayer1Name();
	}

	@Override
	public int getPlayer1Point() {
		return gameField.getPlayer1Points();
	}

	@Override
	public String getPlayer2Name() {
		return gameField.getPlayer2Name();
	}

	@Override
	public int getPlayer2Point() {
		return gameField.getPlayer2Points();
	}

	@Override
	public boolean setStartDot(Point position) {
		if (position == null)
			return false;

		if (!setStartDot(position.x, position.y))
			return false;

		occupy(position.x, position.y);
		gameField.setState(State.TURN_PLAYER1);
		

		notifyObservers();
		return true;
	}

	@Override
	public boolean setDot(Point position) {
		if (position == null)
			return false;

		if (occupy(position.x, position.y) != 0)
			return false;

		changePlayer();
		return true;
	}

	private void changePlayer() {
		State sate = getState();
		if (sate.equals(State.TURN_PLAYER1)) {
			gameField.setState(State.TURN_PLAYER2);
			notifyObservers();
		} else if (sate.equals(State.TURN_PLAYER2)) {
			gameField.setState(State.TURN_PLAYER1);
			notifyObservers();
		}
	}

	@Override
	public void saveToDB() {
		fieldDAO.saveField(gameField);
	}

	@Override
	public boolean loadDB() {
		IField loadField = fieldDAO.getField();
		
		if(loadField == null)
			return false;
		
		gameField = loadField; // deep copy ?
		return true;
	}

}
