package de.htwg.se.moerakikemu.view.tui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

/**
 * Text User Interface
 */
public class TextUI implements IObserver {

	private static final Logger LOGGER = (Logger) LogManager.getLogger(TextUI.class);
	private IController controller;
	private TextHelper textHelper;

	/**
	 * Constructor
	 * 
	 * @param controller
	 */
	@Inject
	public TextUI(IController controller, TextHelper textHelper) {
		this.controller = controller;
		controller.addObserver(this);
		this.textHelper = textHelper;
	}

	private void printMap() {
		LOGGER.info("\n" + textHelper.getMapAsString());
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
			String helpText = textHelper.getHelpText();
			LOGGER.info("\n" + helpText);
		} else if (inputLine.matches("([1-9][0-9]|[0-9])-([1-9][0-9]|[0-9])")) {
			String returnMSG = textHelper.setStone(inputLine);
			if (returnMSG != null)
				LOGGER.info(returnMSG);
		} else {
			LOGGER.info("Keine Ahnung :/ [h Hilfe] ");
		}

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
		} else if (state.equals(State.WON)) {
			String playerName = controller.getPlayerNameWithMostPoints();
			LOGGER.info("Gewonnen hat " + playerName);
		}
	}
}
