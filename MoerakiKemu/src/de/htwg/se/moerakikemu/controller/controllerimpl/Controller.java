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
import de.htwg.se.util.observerNEW.IObservable;
import de.htwg.se.util.observerNEW.Observable;

@Singleton
public class Controller extends Observable implements IController {

	private IField gameField;
	private int fieldLength;

	private State state;

	private ControllerHelper helper;
	private IControllerPlayer playerController;

	private int xCoordinateStartDot, yCoordinateStartDot;

	private String playerWin;
	private boolean quitGame;
	private boolean winner;

	@Inject
	public Controller(@Named("fieldLength") int fieldLength, IControllerPlayer playerCon) {
		super();
		gameField = new Field(fieldLength);
		this.fieldLength = fieldLength;
		this.playerController = playerCon;
		quitGame = false;
		playerWin = "";
		xCoordinateStartDot = 0;
		yCoordinateStartDot = 0;

		state = State.GET_FIRST_PLAYER_NAME; // todo none state?
	}

	public void newGame() {
		gameField = new Field(fieldLength);
		// for (ObserverObserver ui : observers) {
		// ((UserInterface) ui).printMessage("");
		// }
		// for (ObserverObserver ui : observers) {
		// ((UserInterface) ui).addPoints(0, 0);
		// }
		playerController.newGame();
		playerWin = "";
		quitGame = false;
		winner = false;

		state = State.GET_FIRST_PLAYER_NAME;
		notifyObservers();
	}

	public String getIsOccupiedByPlayer(int x, int y) {
		return gameField.getIsOccupiedFrom(x, y);
	}

	public int getEdgeLength() {
		return fieldLength;
	}

	private boolean noProperStartDot(final int x, final int y) {
		return !playerController.startDotSet() && !setStartDot(x, y);
	}

	public int occupy(int x, int y) {
		printInfoAllUIs(x, y);

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
			setEnd(true);
		}

		notifyObservers();

		return 0;
	}

	public boolean setStartDot(int xCoordinate, int yCoordinate) {
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
			printInfoALLUIs(playerController.getPlayer1Name());
		}
		if (counter1 == 4) {
			playerController.addAPointPlayer1();
			playerWin = playerController.getPlayer1Name();
			printInfoALLUIs(playerController.getPlayer1Name());
			setWinner(true);
		}
		if (counter2 == 3 && counter1 == 1) {
			playerController.addAPointPlayer2();
			printInfoALLUIs(playerController.getPlayer2Name());
		}
		if (counter2 == 4) {
			playerController.addAPointPlayer2();
			playerWin = playerController.getPlayer2Name();
			printInfoALLUIs(playerController.getPlayer2Name());
			setWinner(true);
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
			setWinner(true);
		}
	}

	private boolean testIfNearStartDot(int x, int y) {
		if (xCoordinateStartDot == x || yCoordinateStartDot == y) {
			return true;
		}
		return false;
	}

	public String getWinner() {
		if ("".equals(playerWin)) {
			if (playerController.getPlayer1Points() > playerController.getPlayer2Points()) {
				playerWin = playerController.getPlayer1Name();
			} else if (playerController.getPlayer1Points() < playerController.getPlayer2Points()) {
				playerWin = playerController.getPlayer2Name();
			}
		}
		return playerWin;
	}

	public boolean testIfWinnerExists() {
		return winner;
	}

	private void setWinner(boolean win) {
		winner = win;
	}

	public void setEnd(boolean end) {
		quitGame = end;
		printInfoALLUIs();
	}

	public boolean testIfEnd() {
		return quitGame;
	}

	private void printInfoALLUIs() {
		// String pointString = "Das Spiel endet";
		// for (ObserverObserver ui : observers) {
		// ((UserInterface) ui).printMessage(pointString);
		// }
	}

	private void printInfoALLUIs(String player) {
		// String pointString = "Ein Punkt fuer " + player;
		// for (ObserverObserver ui : observers) {
		// ((UserInterface) ui).addPoints(playerController.getPlayer1Points(),
		// playerController.getPlayer2Points());
		// }
		// for (ObserverObserver ui : observers) {
		// ((UserInterface) ui).printMessage(pointString);
		// }
	}

	private void printInfoAllUIs(int x, int y) {
		// int a = x + 1;
		// int b = y + 1;
		// String xValue = String.valueOf(a);
		// String yValue = String.valueOf(b);
		// String pointString = "Gewaehlter Punkt: " + xValue + "/" + yValue;
		// for (ObserverObserver ui : observers) {
		// ((UserInterface) ui).printMessage(pointString);
		// }
	}

	@Override
	public State getState() {
		// if ("".equals(playerController.getPlayer1Name()) ||
		// "".equals(playerController.getPlayer2Name())) {
		// return State.QUERY_PLAYER_NAME;
		// } else if (quitGame) {
		// return State.GAME_FINISHED;
		// } else if (winner) {
		// return State.PLAYER_WON;
		// } else {
		// return State.PLAYER_OCCUPIED;
		// }
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
		if(position == null)
			return false;
		
		return setStartDot(position.x, position.y);
	}

}
