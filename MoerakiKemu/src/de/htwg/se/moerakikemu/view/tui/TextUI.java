package de.htwg.se.moerakikemu.view.tui;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

/**
 * Text User Interface
 */
public class TextUI implements IObserver {

	private static final Logger LOGGER = (Logger) LogManager.getLogger(TextUI.class);
	private IController controller;
	private PlotMap plotMap;
	/**
	 * Constructor
	 * @param controller
	 */
	@Inject
	public TextUI(IController controller, PlotMap plotMap) {
		this.controller = controller;
		controller.addObserver(this);
		this.plotMap = plotMap;
	}

	private void printMap() {
		LOGGER.info("\n"+plotMap.getMapAsString());
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
			LOGGER.info("Hier kannst du dein Stein nicht setzen");
			return;
		}
		
		if (controller.getState().equals(State.SET_START_DOT)) {
			if (!controller.setStartDot(position)) {
				LOGGER.info("Deine Koordinaten waren nicht im Bereich :(");
			}
		} else {
			if (!controller.setDot(position)) {
				LOGGER.info("Das geht nicht :(");
			}
		}
	}

	private Point getPosition(String coordinate) {
		String[] parts = coordinate.split("-");
		int x = Integer.parseInt(parts[0]);
		int y = Integer.parseInt(parts[1]);

		return new Point(x, y);
	}

	@Override
	public void update(Event e) {
		State state = controller.getState();

		printMap();

		if (state.equals(State.EXIT_GAME)) {
			LOGGER.info("Exit MoerakiKemu");
			LOGGER.info("Have a nice day :)");
		} else if (state.equals(State.SET_START_DOT)) {
			LOGGER.info("Setzt nun den StartStein:: ");
		} else if (state.equals(State.TURN_PLAYER1)) {
			String player1Name = controller.getPlayerName(Element.PLAYER1);
			LOGGER.info(player1Name + " du bist dran:: ");
		} else if (state.equals(State.TURN_PLAYER2)) {
			String player2Name = controller.getPlayerName(Element.PLAYER2);
			LOGGER.info(player2Name + " du bist dran:: ");
		} else if (state.equals(State.WON)) {
			String playerName = controller.getPlayerNameWithMostPoints();
			LOGGER.info("Gewonnen hat " + playerName);
		} 
	}
}
