package de.htwg.se.moerakikemu;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.view.gui.GUI;
import de.htwg.se.moerakikemu.view.tui.TextUI;

public class MoerakiKemu {

	private static Scanner scanner;
	private TextUI tui;
	private IController controller;
	private static MoerakiKemu instance = null;

	public static MoerakiKemu getInstance() {
		if (instance == null)
			instance = new MoerakiKemu();
		return instance;
	}

	private MoerakiKemu() {
		Injector injector = Guice.createInjector(new MoerakiKemuModule());

		controller = injector.getInstance(IController.class);
		@SuppressWarnings("unused")
		GUI gui = injector.getInstance(GUI.class);
		tui = injector.getInstance(TextUI.class);
		tui.printWelcome();

		// controller.newGame();
		controller.newGameQuickStart();
	}

	public boolean isExitGame() {
		return controller.getState().equals(State.EXIT_GAME);
	}

	public static void main(String[] args) {
		MoerakiKemu moerakiKemu = MoerakiKemu.getInstance();

		scanner = new Scanner(System.in);
		while (!moerakiKemu.isExitGame()) {
			moerakiKemu.tui.processInputLine(scanner.next());
		}
	}

}
