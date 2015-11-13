package de.htwg.se.moeraki_kemu;

import de.htwg.se.moeraki_kemu.controller.Controller;
import de.htwg.se.moeraki_kemu.view.TextUI;

public class MoerakiKemu {

	/**
	 * Starts the game with TUI, GUI.
	 *
	 * @param args Unused parameters.
	 */
	public static void main(String[] args) {
		// TODO Replace Comments with code

		Controller controller = new Controller();

		TextUI tui = new TextUI(controller);

		while (true) {
			tui.drawCurrentState();
			tui.processInputLine();
		}

	}

}
