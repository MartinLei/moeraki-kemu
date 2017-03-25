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
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;

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

	public void printWelcome() {
		LOGGER.info("Willkommen zu MoerakiKemu :)");
		drawCurrentState();
	}

	public void processInputLine(String inputLine) {
		if (inputLine.matches("q")) {
			myController.quitGame();
		} else if (inputLine.matches("h")) {
			printHelp();
		} else if (myController.getState().equals(State.GET_FIRST_PLAYER_NAME)) {
			// TODO input check
			myController.setPlayer1Name(inputLine);
		} else if (myController.getState().equals(State.GET_SECOND_PLAYER_NAME)) {
			// TODO input check
			myController.setPlayer2Name(inputLine);
		} else if (inputLine.matches("([1-9][0-9]|[0-9])-([1-9][0-9]|[0-9])")) {
			Point position = getPosition(inputLine);

			setStone(position);
		} else {
			LOGGER.info("Keine Ahnung :/ [h Hilfe] ");
		}

	}

	private void printHelp() {
		LOGGER.info("Hilfe: ");
		LOGGER.info("h Hilfe");
		LOGGER.info("q Beenden");
		LOGGER.info("y-x Koordinaten z.b 1-2");
	}

	private void setStone(Point position) {
		if (position == null) {
			LOGGER.info("Deine Koordinaten waren nicht im Bereich :(");
			return;
		}

		if (myController.getState().equals(State.SET_START_DOT)) {
			if (!myController.setStartDot(position)) {
				LOGGER.info("Deine Koordinaten waren nicht im Bereich :(");
			}
		} else {
			if (!myController.setDot(position)) {
				LOGGER.info("Die Zelle ist schon besetzt :(");
			}
		}
	}

	private Point getPosition(String coordinate) {
		String[] parts = coordinate.split("-");
		int x = Integer.parseInt(parts[0]) - 1;
		int y = Integer.parseInt(parts[1]) - 1;

		if (x >= 0 && x <= 11 && y >= 0 && y <= 11)
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
			LOGGER.info("Setzt nun den StartStein:: ");
		} else if (state.equals(State.TURN_PLAYER1)) {
			drawCurrentState();
			String player1Name = myController.getPlayer1Name();
			LOGGER.info(player1Name + " du bist dran:: ");
		} else if (state.equals(State.TURN_PLAYER2)) {
			drawCurrentState();
			String player2Name = myController.getPlayer2Name();
			LOGGER.info(player2Name + " du bist dran:: ");
		} else if (state.equals(State.PLAYER1_WON)) {
			// TODO test ?
			drawCurrentState();
			String playerName = myController.getPlayer1Name();
			LOGGER.info("Der Gewinnder ist Spieler1 aka ->" + playerName);
		} else if (state.equals(State.PLAYER2_WON)) {
			// TODO test ?
			drawCurrentState();
			String playerName = myController.getPlayer2Name();
			LOGGER.info("Der Gewinnder ist Spieler1 aka ->" + playerName);
		} else if (state.equals(State.GAME_FINISHED)) {
			// TODO test ?
			drawCurrentState();
			LOGGER.info("Ende keiner hat gewonnen");
		}
	}
}
