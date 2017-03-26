package de.htwg.se.moerakikemu.view.tui;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.Spot;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

/**
 * Text User Interface
 */
public class TextUI implements IObserver {

	private static final Logger LOGGER = (Logger) LogManager.getLogger(TextUI.class);
	IController controller;

	@Inject
	public TextUI(IController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	/**
	 * Draws all Spots as squares with an Player-identifying character in it.
	 * ------ |1||2|... ------ . . . Player 1: x points Player 2: x points
	 */
	private static final int MAP_LENGTH = 13;

	private void printMap(){
		String mapString = "\n" + getMap();
		LOGGER.info(mapString);
	}
	private String getMap() {

		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < MAP_LENGTH; y++) {
			
				String line = getMapLine( y);
				sb.append(line);
				sb.append("\n");
			
		}
		
		return sb.toString();

		// int edgeLength = myController.getEdgeLength();
		//
		// printColumnIdentifiers(edgeLength);
		// for (int i = 0; i < edgeLength; i++) {
		// printLine(edgeLength);
		//
		// StringBuilder line = new StringBuilder(printLeadingNumber(i + 1,
		// edgeLength));
		// for (int j = 0; j < edgeLength; j++) {
		// Element person = myController.getIsOccupiedBy(i, j);
		// String name = person.toString();
		// line.append(drawSpot(name));
		// }
		// LOGGER.info(line);
		// }
		// printLine(edgeLength);
		// printPoints();

	}

	private String getMapLine( int y) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < MAP_LENGTH; i++) {
			Element element = controller.getFieldElement(i, y);
			sb.append(element.toString());
		}

		return sb.toString();
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
		String player1Name = controller.getPlayer1Name();
		int player1Point = controller.getPlayer1Point();
		String player2Name = controller.getPlayer2Name();
		int player2Point = controller.getPlayer2Point();

		LOGGER.info(player1Name + " aka " + Element.PLAYER1 + ": " + player1Point + " Punkte");
		LOGGER.info(player2Name + " aka " + Element.PLAYER2 + ": " + player2Point + " Punkte\n");
	}

	/**
	 * Draws a Spot with the Player identifier (i.e. number).
	 *
	 * @param playerName
	 *            Identifier for the player or ' ' if not occupied by player.
	 */
	private String drawSpot(final String playerName) {
		return "|" + playerName + "|";
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

	/**
	 * Print the intro
	 */
	public void printWelcome() {
		LOGGER.info("Willkommen zu MoerakiKemu :)");
		getMap();
	}

	/**
	 * Process a input from the user
	 * 
	 * @param inputLine
	 *            input from keyboard
	 */
	public void processInputLine(String inputLine) {
		if (inputLine.matches("q")) {
			controller.quitGame();
		} else if (inputLine.matches("h")) {
			printHelp();
		} else if (controller.getState().equals(State.GET_FIRST_PLAYER_NAME)) {
			// TODO input check
			controller.setPlayer1Name(inputLine);
		} else if (controller.getState().equals(State.GET_SECOND_PLAYER_NAME)) {
			// TODO input check
			controller.setPlayer2Name(inputLine);
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

		if (controller.getState().equals(State.SET_START_DOT)) {
			if (!controller.setStartDot(position)) {
				LOGGER.info("Deine Koordinaten waren nicht im Bereich :(");
			}
		} else {
			if (!controller.setDot(position)) {
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
		State state = controller.getState();
		if (state.equals(State.EXIT_GAME)) {
			LOGGER.info("Exit MoerakiKemu");
			LOGGER.info("Have a nice day :)");
		} else if (state.equals(State.GET_FIRST_PLAYER_NAME)) {
			LOGGER.info("Spieler1 bitte gebe dein Name ein:: ");
		} else if (state.equals(State.GET_SECOND_PLAYER_NAME)) {
			LOGGER.info("Spieler2 bitte gebe dein Name ein:: ");
		} else if (state.equals(State.SET_START_DOT)) {
			LOGGER.info("Setzt nun den StartStein:: ");
		} else if (state.equals(State.TURN_PLAYER1)) {
			String player1Name = controller.getPlayer1Name();
			LOGGER.info(player1Name + " du bist dran:: ");
		} else if (state.equals(State.TURN_PLAYER2)) {
			String player2Name = controller.getPlayer2Name();
			LOGGER.info(player2Name + " du bist dran:: ");
		} else if (state.equals(State.PLAYER1_WON)) {
			String playerName = controller.getPlayer1Name();
			LOGGER.info("Der Gewinnder ist Spieler1 aka ->" + playerName);
		} else if (state.equals(State.PLAYER2_WON)) {
			String playerName = controller.getPlayer2Name();
			LOGGER.info("Der Gewinnder ist Spieler1 aka ->" + playerName);
		} else if (state.equals(State.GAME_FINISHED)) {
			LOGGER.info("Ende keiner hat gewonnen");
		}

		printMap();
	}
}
