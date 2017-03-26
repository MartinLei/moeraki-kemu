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

	private static final int MAP_LENGTH = 13;

	private void printMap() {
		String mapString = "\n" + getMap();
		LOGGER.info(mapString);
	}

	private String getMap() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCollumNumber());
		sb.append("\n");
		for (int y = 0; y < MAP_LENGTH; y++) {
			String line = getMapLine(y);
			sb.append(getFormatNumber(y));
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}

	private String getCollumNumber(){
		StringBuilder sb = new StringBuilder();
		sb.append("   ");
		for (int i = 0; i < MAP_LENGTH; i++) {
			String number = getFormatNumber(i);
			sb.append(number);
		}
		return sb.toString();
	}
	private String getFormatNumber(int number) {
		if ( number == 0 || number == 12)
			return "   ";
		else if (number < 10)
			return " " + number + " ";
		return "" + number + " ";
	}

	private String getMapLine(int y) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < MAP_LENGTH; i++) {
			Element element = controller.getFieldElement(i, y);
			sb.append(" ");
			sb.append(element.toString());
			sb.append(" ");
		}

		return sb.toString();
	}

	/**
	 * Print the into
	 */
	public void printWelcome() {
		LOGGER.info("Willkommen zu MoerakiKemu :)");
		printMap();
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
