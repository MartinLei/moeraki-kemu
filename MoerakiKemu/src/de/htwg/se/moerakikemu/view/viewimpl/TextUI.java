package de.htwg.se.moerakikemu.view.viewimpl;

import java.awt.Point;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.util.observerNEW.Event;
import de.htwg.se.util.observerNEW.IObserver;

public class TextUI implements IObserver {

	private static final Logger LOGGER = (Logger) LogManager.getLogger(TextUI.class);
	IController myController;

	@Inject
	public TextUI(IController controller) {
		myController = controller;
		myController.addObserver(this);
	}

	/**
	 * Draws all Spots as squares with an Player-identifying character in it.
	 * ------ |1||2|... ------ . . . Player 1: x points Player 2: x points
	 */
	public void drawCurrentState() {
		int edgeLength = myController.getEdgeLength();

		printColumnIdentifiers(edgeLength);
		for (int i = 0; i < edgeLength; i++) {
			printLine(edgeLength);

			StringBuilder line = new StringBuilder(printLeadingNumber(i + 1, edgeLength));
			for (int j = 0; j < edgeLength; j++) {
				String playerString = myController.getIsOccupiedByPlayer(i, j);
				char id = playerString.isEmpty() ? ' ' : playerString.charAt(0);
				line.append(drawSpot(id));
			}
			LOGGER.info(line);
		}
		printLine(edgeLength);
		printPoints();

	}

	private String printLeadingNumber(final int currentNumber, final int edgeLength) {
		int offset = offset(edgeLength).length();

		StringBuilder builder = new StringBuilder(Integer.toString(currentNumber));

		while (builder.length() < offset) {
			builder.append(" ");
		}

		return builder.toString();
	}

	/**
	 * Prints the points for both players.
	 */
	private void printPoints() {
		String player1Name = myController.getPlayer1Name();
		int player1Point = myController.getPlayer1Point();
		String player2Name = myController.getPlayer2Name();
		int player2Point = myController.getPlayer2Point();

		LOGGER.info(player1Name + ": " + player1Point + " Punkte");
		LOGGER.info(player2Name + ": " + player2Point + " Punkte\n");
	}

	/**
	 * Draws a Spot with the Player identifier (i.e. number).
	 *
	 * @param playerChar
	 *            Identifier for the player or ' ' if not occupied by player.
	 */
	private String drawSpot(final char playerChar) {
		return "|" + playerChar + "|";
	}

	/**
	 * Calculates the offset for the game field according to the length of the
	 * number representing the edge length.
	 *
	 * @param edgeLength
	 *            Length of the edge of the game field.
	 * @return The empty spaces for offset as String, not null.
	 */
	private String offset(int edgeLength) {
		int numDigits = String.valueOf(edgeLength).length();

		StringBuilder offsetBuilder = new StringBuilder();
		for (int l = 0; l < numDigits; l++) {
			offsetBuilder.append(" ");
		}
		return offsetBuilder.toString();
	}

	/**
	 * Prints a horizontal line for separating lines of Spots.
	 *
	 * @param edgeLength
	 *            Number of Spots per edge.
	 */
	private void printLine(int edgeLength) {
		StringBuilder line = new StringBuilder(offset(edgeLength));
		for (int i = 0; i < edgeLength; i++) {
			line.append("---");
		}
		LOGGER.info(line.toString());
	}

	/**
	 * Prints a line with numbers above the game field that identifies the
	 * columns.
	 *
	 * @param edgeLength
	 *            Length of the game field.
	 */
	private void printColumnIdentifiers(final int edgeLength) {
		StringBuilder headlineBuilder = new StringBuilder(offset(edgeLength));
		for (int i = 1; i <= edgeLength; i++) {
			if (i < 10) {
				headlineBuilder.append(" ").append(i).append(" ");
			} else {
				headlineBuilder.append(i).append(" ");
			}
		}
		LOGGER.info(headlineBuilder.toString());
	}

	public void printMessage(String msg) {
		LOGGER.error(msg + "\n");
	}

	/**
	 * Prints the winner and ends the game.
	 *
	 * @return the boolean - value for the MoerakiKemu - class to finish the
	 *         game.
	 */

	public void quit() {
		String winner = myController.getWinner();
		if (!"".equals(winner)) {
			LOGGER.error("Der Gewinner ist " + winner + "!!!\n");
		} else {
			LOGGER.error("Unentschieden");
		}
	}

	public void printWelcome() {
		LOGGER.info("Willkommen zu MoerakiKemu :)");
	}

	public void processInputLine(String inputLine) {
		if (inputLine.matches("q")) {
			myController.quitGame();
		} else if (myController.getState().equals(State.GET_FIRST_PLAYER_NAME)) {
			// TODO input check
			myController.setPlayer1Name(inputLine);
		} else if (myController.getState().equals(State.GET_SECOND_PLAYER_NAME)) {
			// TODO input check
			myController.setPlayer2Name(inputLine);
		} else if (inputLine.matches("([1-9][0-9]|[1-9])-([1-9][0-9]|[1-9])")) {
			Point position = getPosition(inputLine);

			if (position == null)
				LOGGER.info("Deine Koordinaten waren nicht im Bereich :(");

			if (setStone(position))
				drawCurrentState();
		} else {
			LOGGER.info("Keine Ahnung :/ [h Hilfe] ");
		}

	}

	private boolean setStone(Point position) {
		if (myController.getState().equals(State.SET_START_DOT))
			if (myController.setStartDot(position) == false) {
				LOGGER.info("Deine Koordinaten waren nicht im Bereich :(");
				return false;
			} else {
				LOGGER.info("Moege der Bessere gewinnen:");
			}

		return true;
	}

	private Point getPosition(String coordinate) {
		String[] parts = coordinate.split("-");
		int x = Integer.parseInt(parts[0]);
		int y = Integer.parseInt(parts[1]);

		if (x <= 12 && y <= 12)
			return new Point(x, y);

		return null;
	}

	@Override
	public void update(Event e) {
		State state = myController.getState();
		if (state.equals(State.EXIT_GAME)) {
			LOGGER.info("Exit MoerakiKemu");
			LOGGER.info("Have a nice day :)");
			return;
		} else if (state.equals(State.GET_FIRST_PLAYER_NAME)) {
			LOGGER.info("Spieler1 bitte gebe dein Name ein:: ");
		} else if (state.equals(State.GET_SECOND_PLAYER_NAME)) {
			LOGGER.info("Spieler2 bitte gebe dein Name ein:: ");
		} else if (state.equals(State.SET_START_DOT)) {
			drawCurrentState();
			LOGGER.info("Spieler2: bitte setze den StartStein:: ");
		}
	}

	// @Override
	// public void update() {
	// State controllerState = myController.getState();
	// if (controllerState == State.PLAYER_OCCUPIED) {
	// drawCurrentState();
	// } else if (controllerState == State.GAME_FINISHED) {
	// LOGGER.info("Spiel ist beendet");
	// } else if (controllerState == State.QUERY_PLAYER_NAME) {
	// queryPlayerName();
	// } else if (controllerState == State.PLAYER_WON) {
	// String winner = myController.getWinner();
	// String display = ("".equals(winner)) ? "Ein Unentschieden!" : "Der
	// Gewinner ist: " + winner + "!!!";
	// LOGGER.error(display);
	// }
	// }

}
