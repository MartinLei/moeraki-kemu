package de.htwg.se.moerakikemu.view.viewimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.util.observer.ObserverObserver;

public class TextUI implements ObserverObserver {

	private static final Logger LOGGER = (Logger) LogManager.getLogger(TextUI.class);

	IController myController;
	IControllerPlayer myPlayerController;

	@Inject
	public TextUI(IController controller, IControllerPlayer playerController) {
		myController = controller;
		myPlayerController = playerController;
		//queryPlayerName();
	}

	public void queryPlayerName() {
		LOGGER.error("Noch keine Spieler-Namen eingegeben!");
		LOGGER.info("Bitte Namen des ersten Spielers eingeben: ");
		LOGGER.info("Bitte Namen des zweiten Spielers eingeben: ");
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
		LOGGER.info(myPlayerController.getPlayer1Name() + ": " + myPlayerController.getPlayer1Points() + " Punkte");
		LOGGER.info(myPlayerController.getPlayer2Name() + ": " + myPlayerController.getPlayer2Points() + " Punkte\n");
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

	@Override
	public void update() {
		State controllerState = myController.getState();
		if (controllerState == State.PLAYER_OCCUPIED) {
			drawCurrentState();
		} else if (controllerState == State.GAME_FINISHED) {
			LOGGER.info("Spiel ist beendet");
		} else if (controllerState == State.QUERY_PLAYER_NAME) {
			queryPlayerName();
		} else if (controllerState == State.PLAYER_WON) {
			String winner = myController.getWinner();
			String display = ("".equals(winner)) ? "Ein Unentschieden!" : "Der Gewinner ist: " + winner + "!!!";
			LOGGER.error(display);
		}
	}


	public void addPoints(int pointsPlayer1, int pointsPlayer2) {
		LOGGER.error(myPlayerController.getPlayer1Name() + " hat " + pointsPlayer1 + "Punkte");
		LOGGER.error(myPlayerController.getPlayer2Name() + " hat " + pointsPlayer2 + "Punkte");
	}

	public boolean processInputLine(String inputLine) {
		System.out.println("hallo");
		LOGGER.info("Your input was: " + inputLine);
		if (inputLine.matches("q"))
			return false;

		return true;
	}

	
	public void printWelcome() {
		LOGGER.info("Willkommen zu MoerakiKemu :)");
	}
}
