package de.htwg.se.moerakikemu.controller.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.rules.RunRules;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.util.rule.Rule;
import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Field;
import de.htwg.se.moerakikemu.model.impl.Element;
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
		Point starDotPosisiton = new Point(6, 6);
		setStartDot(starDotPosisiton);
	}

	@Override
	public Element getFieldElement(int x, int y) {
		return gameField.getElement(x, y);
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

		if (!rule.isStartDotPosCorrect(position))
			return false;

		gameField.occupy(position, Element.STARTDOT);
		gameField.setState(State.TURN_PLAYER1);

		notifyObservers();
		return true;
	}

	@Override
	public boolean setDot(Point position) {
		if (position == null || gameField.isOccupied(position))
			return false;

		gameField.occupy(position, Element.STARTDOT);

		analyzeField();
		
		return true;
	}

	private void analyzeField() {

		actIslands();

		if (!isGameFinish())
			changePlayer();
	}

	private void actIslands() {

	}

	private boolean isGameFinish() {
//		if (gameField.isFilled()) {
//			gameField.setState(State.GAME_FINISHED);
//			notifyObservers();
//			return true;
//		}
		
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

}
