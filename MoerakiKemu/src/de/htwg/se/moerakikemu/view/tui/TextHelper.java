package de.htwg.se.moerakikemu.view.tui;

import java.awt.Point;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;
import scala.collection.mutable.StringBuilder;

public final class TextHelper {
	private static final int MAP_LENGTH = 13;
	private IController controller;

	@Inject
	public TextHelper(IController controller) {
		this.controller = controller;
	}

	public String getMapAsString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getMap());
		sb.append("\n");
		sb.append(getPoints());
		sb.append("\n");
		sb.append(controller.getState());
		return sb.toString();
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

	private String getCollumNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append("   ");
		for (int i = 0; i < MAP_LENGTH; i++) {
			String number = getFormatNumber(i);
			sb.append(number);
		}
		return sb.toString();
	}

	private String getFormatNumber(int number) {
		if (number == 0 || number == 12)
			return "   ";
		else if (number < 10)
			return " " + number + " ";
		return "" + Integer.toString(number) + " ";
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

	private String getPoints() {
		StringBuilder sb = new StringBuilder();

		int player1Points = controller.getPlayerPoint(Element.PLAYER1);
		int player2Points = controller.getPlayerPoint(Element.PLAYER2);
		sb.append("(" + Element.PLAYER1 + ") " + controller.getPlayerName(Element.PLAYER1) + " :" + player1Points
				+ " Punkte\n");
		sb.append("(" + Element.PLAYER2 + ") " + controller.getPlayerName(Element.PLAYER2) + " :" + player2Points
				+ " Punkte\n");
		return sb.toString();
	}

	private Point getPosition(String coordinate) {
		if (!coordinate.matches("([1-9][0-9]|[0-9])-([1-9][0-9]|[0-9])"))
			return null;

		String[] parts = coordinate.split("-");
		int x = Integer.parseInt(parts[0]);
		int y = Integer.parseInt(parts[1]);

		return new Point(x, y);
	}

	public String setStone(String coordinate) {
		
		Point position = getPosition(coordinate);

		if (position == null)
			return "Nicht im format x-y";

		if (controller.getState().equals(State.SET_START_DOT)) {
			if (!controller.setStartDot(position))
				return "Deine Koordinaten waren nicht im Bereich :(";
		} else {
			if (!controller.setDot(position))
				return "Das geht nicht :(";
		}
		
		return null;
	}
	
	public String getHelpText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Hilfe: \n");
		sb.append("h Hilfe\n");
		sb.append("q Beenden\n");
		sb.append("y-x Koordinaten z.b 1-2\n");
		return sb.toString();
	}
}
