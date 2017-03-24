package de.htwg.se.moerakikemu;


import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.view.viewimpl.TextUI;
import de.htwg.se.moerakikemu.view.viewimpl.gui.GUI;


public class MoerakiKemu {

	private static Scanner scanner;
	private static TextUI tui;
	private IController controller;
	private static MoerakiKemu instance = null;
	
	public static MoerakiKemu getInstance() {
		if (instance == null) instance = new MoerakiKemu();
		return instance;
	}
		
	private MoerakiKemu() {
		Injector injector = Guice.createInjector(new MoerakiKemuModule());

		controller = injector.getInstance(IController.class);
	   // @SuppressWarnings("unused")
		GUI gui = injector.getInstance(GUI.class);
		tui = injector.getInstance(TextUI.class);
		tui = new TextUI(controller);
		tui.printWelcome();

		// Create an initial game
		//controller.create();
	}

	public boolean isExitGame(){
		return controller.getState().equals(State.EXIT_GAME);
	}
	public static void main(String[] args) {
//		Injector injector = Guice.createInjector(new ControllerModuleWithController());
//		
//		IController controller = injector.getInstance(Controller.class);
//	
//		UserInterface[] interfaces;
//		interfaces = new UserInterface[2];
//		interfaces[0] = injector.getInstance(TextUI.class);
//		interfaces[1] = injector.getInstance(GUI.class);
//
//		for (int i = 0; i < interfaces.length; i++) {
//			((IObserverSubject) controller).attatch((ObserverObserver) interfaces[i]);
//			interfaces[i].drawCurrentState();
//		}
//
//		// Used to query Player names
//		((ObserverObserver) interfaces[1]).update();
//		
//		boolean finished = false;
//		while (!finished) {
//			finished = controller.testIfEnd();
//			interfaces[0].update();
//			interfaces[1].update();
//		}
//		
//		for (UserInterface ui : interfaces) {
//			ui.quit();
//		}
		
		MoerakiKemu moerakiKemu = MoerakiKemu.getInstance();

		scanner = new Scanner(System.in);
		while (!moerakiKemu.isExitGame()) {
			moerakiKemu.tui.processInputLine(scanner.next());
		}
	}

}
