package de.htwg.se.moerakikemu.controller.controllerimpl;

import java.awt.Point;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.modellayer.IField;
import de.htwg.se.moerakikemu.modellayer.modellayerimpl.Field;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.util.observer.IObservable;
import de.htwg.se.util.observer.Observable;

@Singleton
public class Controller extends Observable implements IController {

	private IField gameField;
	private int fieldLength;

	private State state;

	private ControllerHelper helper;
	private IControllerPlayer playerController;

	private int xCoordinateStartDot, yCoordinateStartDot;

	@Inject
	public Controller(@Named("fieldLength") int fieldLength, IControllerPlayer playerCon) {
		super();
		gameField = new Field(fieldLength);
		this.fieldLength = fieldLength;
		this.playerController = playerCon;
		xCoordinateStartDot = 0;
		yCoordinateStartDot = 0;

		state = State.GET_FIRST_PLAYER_NAME;
	}

	@Override
	public void newGame() {
		gameField = new Field(fieldLength);
		playerController.newGame();
		state = State.GET_FIRST_PLAYER_NAME;

		quicStartForTest();

		notifyObservers();
	}

	private void quicStartForTest() {
		String player1Name = "Andrew";
		String player2Name = "Walter";
		playerController.setName(player1Name, player2Name);

		Point starDotPosisiton = new Point(4, 4);
		setStartDot(starDotPosisiton);

		state = State.TURN_PLAYER1;
	}

	@Override
	public String getIsOccupiedByPlayer(int x, int y) {
		return gameField.getIsOccupiedFrom(x, y);
	}

	@Override
	public int getEdgeLength() {
		return fieldLength;
	}

	private boolean noProperStartDot(final int x, final int y) {
		return !playerController.startDotSet() && !setStartDot(x, y);
	}

	private int occupy(int x, int y) {
		if (gameField.getIsOccupiedFrom(x, y) != "" || noProperStartDot(x, y)) {
			return -1;
		}

		gameField.occupy(x, y, playerController.getCurrentPlayerName());
		helper = new ControllerHelper(x, y, fieldLength - 1);
		helper.testSquare();
		testListOfSquares();
		if (playerController.getCurrentPlayerName() != "StartDot") {
			testAllInLine(x, y);
		}
		helper.resetSquareTest();
		playerController.selectNextPlayer();

		if (gameField.isFilled()) {
			state = State.GAME_FINISHED;
			notifyObservers();
		}

		return 0;
	}

	private boolean setStartDot(int xCoordinate, int yCoordinate) {
		int radiusLow;
		int radiusUp;
		int length = fieldLength - 1;
		if (fieldLength % 2 != 0) {
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
		int[] squareArray = new int[17];
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
		if (!"".equals(gameField.getIsOccupiedFrom(x, y))) {
			if (gameField.getIsOccupiedFrom(x, y).equals(playerController.getPlayer1Name())) {
				return 0;
			} else if (gameField.getIsOccupiedFrom(x, y).equals(playerController.getPlayer2Name())) {
				return 1;
			}
		}
		return -1;
	}

	private void setPointsOfPlayer(int counter1, int counter2) {
		if (counter1 == 3 && counter2 == 1) {
			playerController.addAPointPlayer1();
		}
		if (counter1 == 4) {
			playerController.addAPointPlayer1();

			state = State.PLAYER1_WON;
			notifyObservers();
		}
		if (counter2 == 3 && counter1 == 1) {
			playerController.addAPointPlayer2();
		}
		if (counter2 == 4) {
			playerController.addAPointPlayer2();

			state = State.PLAYER2_WON;
			notifyObservers();
		}
	}

	private void testAllInLine(int x, int y) {
		if (!testIfNearStartDot(x, y)) {
			return;
		}
		int distanceTop = xCoordinateStartDot;
		int distanceBot = fieldLength;
		int distanceRight = fieldLength;
		int distanceLeft = yCoordinateStartDot;

		if (y == yCoordinateStartDot) {
			if (x > xCoordinateStartDot) {
				testInLine("x", xCoordinateStartDot, distanceBot, y, fieldLength - xCoordinateStartDot - 1);
			} else {

				testInLine("x", 0, distanceTop, y, distanceTop);
			}
		} else if (x == xCoordinateStartDot) {
			if (y < yCoordinateStartDot) {
				testInLine("y", 0, distanceLeft, x, distanceLeft);
			} else {
				testInLine("y", yCoordinateStartDot, distanceRight, x, fieldLength - yCoordinateStartDot - 1);
			}
		}
	}

	private boolean isOccupiedByCurrentPlayer(final int x, final int y) {
		return gameField.getIsOccupiedFrom(x, y).equals(playerController.getCurrentPlayerName());
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
			// System.out.println(counter + " " + counterEnd + " " +
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
		return state;
	}

	@Override
	public void quitGame() {
		state = State.EXIT_GAME;
		notifyObservers();
	}

	@Override
	public void setPlayer1Name(String name) {
		playerController.setPlayer1Name(name);
		state = State.GET_SECOND_PLAYER_NAME;
		notifyObservers();
	}

	@Override
	public void setPlayer2Name(String name) {
		playerController.setPlayer2Name(name);
		state = State.SET_START_DOT;
		notifyObservers();
	}

	@Override
	public String getPlayer1Name() {
		return playerController.getPlayer1Name();
	}

	@Override
	public int getPlayer1Point() {
		return playerController.getPlayer1Points();
	}

	@Override
	public String getPlayer2Name() {
		return playerController.getPlayer2Name();
	}

	@Override
	public int getPlayer2Point() {
		return playerController.getPlayer2Points();
	}

	@Override
	public boolean setStartDot(Point position) {
		if (position == null)
			return false;

		if (!setStartDot(position.x, position.y))
			return false;

		state = State.TURN_PLAYER1;
		occupy(position.x, position.y);

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
		if (state.equals(State.TURN_PLAYER1)) {
			state = State.TURN_PLAYER2;
			notifyObservers();
		} else if (state.equals(State.TURN_PLAYER2)) {
			state = State.TURN_PLAYER1;
			notifyObservers();
		}
	}

}
