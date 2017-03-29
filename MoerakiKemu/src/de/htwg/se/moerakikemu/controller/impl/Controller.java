package de.htwg.se.moerakikemu.controller.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.Field;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;
import de.htwg.se.moerakikemu.util.observer.Observable;

/**
 * The controller implementation
 */
@Singleton
public class Controller extends Observable implements IController {
	private Rule rule;
	private IField gameField;
	private IFieldDAO fieldDAO;

	@Inject
	public Controller(IFieldDAO fieldDAO) {
		rule = new Rule();
		this.fieldDAO = fieldDAO;

		gameField = new Field();
	}

	@Override
	public void newGame() {
		gameField = new Field();

		notifyObservers();
	}

	@Override
	public void newGameQuickStart() {
		gameField = new Field();

		Point starDotPosisiton = new Point(6, 6);
		setStartDot(starDotPosisiton);
	}

	@Override
	public Element getFieldElement(int x, int y) {
		return gameField.getElement(new Point(x, y));
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
	public String getPlayer1Name() {
		return gameField.getPlayer1Name();
	}

	@Override
	public int getPlayerPoint(Element player) {
		return gameField.getPoints(player);
	}

	@Override
	public String getPlayer2Name() {
		return gameField.getPlayer2Name();
	}

	@Override
	public boolean setStartDot(Point position) {
		if (position == null)
			return false;

		if (!rule.isStartDotPosCorrect(position))
			return false;

		gameField.occupy(position, Element.STARTDOT);
		gameField.setState(State.TURN_PLAYER1);

		notifyObservers();
		return true;
	}

	@Override
	public boolean setDot(Point position) {
		if (position == null)
			return false;

		if (!rule.isPositionPossibleInput(position))
			return false;

		if (!gameField.occupy(position, gameField.getCurrentPlayer()))
			return false;

		analyzeField(position);

		return true;
	}

	private void analyzeField(Point position) {

		State newState = actIslands(position);

		if (newState != null) {
			gameField.setState(newState);
			notifyObservers();
			return;

		}
		if (!isGameFinish())
			changePlayer();
	}

	private State actIslands(Point position) {
		List<Point> testCells = rule.getShiftedPositions(rule.getTemplateCells(), position);
		List<Point> testIslands = rule.getShiftedPositions(rule.getTemplateIslands(), position);
		List<Element> elementList = new ArrayList<>();

		for (Point cell : testCells) {
			Element element = gameField.getElement(cell);
			elementList.add(element);
		}

		for (int i = 0; i < 4; i++) {
			Point islandPosition = testIslands.get(i);
			List<Integer> cellNummer = rule.getCells().get(i);
			State state = actIslandCell(elementList, cellNummer, islandPosition);

			if (state != null)
				return state;
		}

		return null;
	}

	private State actIslandCell(List<Element> elementList, List<Integer> cellNummer, Point islandPosition) {
		int countPlayer1 = 0;
		int countPlayer2 = 0;
		int countStartDot = 0;
		int countWall = 0;

		for (Integer nummer : cellNummer) {
			Element element = elementList.get(nummer.intValue());
			if (element == null)
				continue;

			if (element.equals(Element.PLAYER1))
				countPlayer1++;
			else if (element.equals(Element.PLAYER2))
				countPlayer2++;
			else if (element.equals(Element.STARTDOT))
				countStartDot++;
			else if (element.equals(Element.WALL))
				countWall++;
		}

		Element newIslandElement = null;
		State newState = null;
		if (countPlayer1 == 3 && countPlayer2 == 0 && countStartDot == 0 && countWall == 1) {
			newIslandElement = Element.HALF_POINT_PLAYER1;
		} else if (countPlayer1 == 0 && countPlayer2 == 3 && countStartDot == 0 && countWall == 1) {
			newIslandElement = Element.HALF_POINT_PLAYER2;
		} else if (countPlayer1 == 3 && countPlayer2 == 0 && countStartDot == 1 && countWall == 0) {
			newIslandElement = Element.POINT_PLAYER1;
			newState = State.WON;
		} else if (countPlayer1 == 0 && countPlayer2 == 3 && countStartDot == 1 && countWall == 0) {
			newIslandElement = Element.POINT_PLAYER2;
			newState = State.WON;
		} else if (countPlayer1 == 3 && countPlayer2 == 1 && countStartDot == 0 && countWall == 0) {
			newIslandElement = Element.POINT_PLAYER1;
		} else if (countPlayer1 == 1 && countPlayer2 == 3 && countStartDot == 0 && countWall == 0) {
			newIslandElement = Element.POINT_PLAYER2;
		} else if (countPlayer1 == 4 && countPlayer2 == 0 && countStartDot == 0 && countWall == 0) {
			newIslandElement = Element.POINT_PLAYER1;
			newState = State.WON;
		} else if (countPlayer1 == 0 && countPlayer2 == 4 && countStartDot == 0 && countWall == 0) {
			newIslandElement = Element.POINT_PLAYER2;
			newState = State.WON;
		}

		if (newIslandElement != null)
			gameField.occupy(islandPosition, newIslandElement);

		return newState;
	}

	private boolean isGameFinish() {
		// if (gameField.isFilled()) {
		// gameField.setState(State.GAME_FINISHED);
		// notifyObservers();
		// return true;
		// }

		return false;
	}

	private void changePlayer() {
		gameField.changeActPlayer();
		notifyObservers();
	}

	@Override
	public void saveToDB() {
		fieldDAO.saveField(gameField);
	}

	@Override
	public boolean loadDB() {
		IField loadField = fieldDAO.getField();

		if (loadField == null)
			return false;

		gameField = loadField; // deep copy ?
		return true;
	}

	// REMOVE
	@Override
	public String getFieldAsString() {
		return gameField.getFieldAsString();
	}

	@Override
	public String getCurrentPlayerName() {
		Element player = gameField.getCurrentPlayer();

		if (player.equals(Element.PLAYER1))
			return gameField.getPlayer1Name();
		else if (player.equals(Element.PLAYER2))
			return gameField.getPlayer1Name();
		
		return null;
	}
}
