package de.htwg.se.moerakikemu.view.viewimpl.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;

public class GUI extends JFrame implements IObserver {
	private static final long serialVersionUID = 2078463309153663728L;

	private IController myController;
	private IControllerPlayer myPlayerController;

	private MainPanel myMainPanel;
	private MessagePanel myMessagePanel;

	@Inject
	public GUI(IController newController, IControllerPlayer playerController) {
		super("Moeraki Kemu");
		this.myController = newController;
		myController.addObserver(this);

		this.myPlayerController = playerController;
		this.myMainPanel = new MainPanel(myController, myPlayerController, myController.getEdgeLength());
		this.myMessagePanel = new MessagePanel(myController, myPlayerController);

		this.setJMenuBar(new MainMenu(myController));

		this.setLayout(new BorderLayout());
		this.add(myMainPanel, BorderLayout.CENTER);
		this.add(myMessagePanel, BorderLayout.EAST);

		this.setSize(1024, 768);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void drawCurrentState() {
		myMainPanel.updateField();
		myMessagePanel.updateView();
		repaint();
	}

	private void printWinnerPopup() {
		String winner = myController.getWinner();
		String display = ("".equals(winner)) ? "Ein Unentschieden!" : "Der Gewinner ist: " + winner + "!!!";
		JOptionPane.showMessageDialog(null, display);
	}

	public void addPoints(int pointsPlayer1, int pointsPlayer2) {
		myMessagePanel.setPlayerPoints(pointsPlayer1, pointsPlayer2);
	}

	public void update(Event e) {
		State state = myController.getState();
		if (state.equals(State.EXIT_GAME)) {
			this.setVisible(false);
			this.dispose();
			return;
		} else if (state.equals(State.GET_FIRST_PLAYER_NAME)) {
			String player1Name = JOptionPane.showInputDialog("Name fuer Spieler 1 eigeben:", "Andrew");
			if (player1Name != null) 
				myController.setPlayer1Name(player1Name);
			// LOGGER.info("Spieler1 bitte gebe dein Name ein:: ");
		} else if (state.equals(State.GET_SECOND_PLAYER_NAME)) {
			String player2Name = JOptionPane.showInputDialog("Name fuer Spieler 2 eigeben:", "Walter");
			if (player2Name != null) 
				myController.setPlayer2Name(player2Name);
			// LOGGER.info("Spieler2 bitte gebe dein Name ein:: ");
		} else if (state.equals(State.SET_START_DOT)) {
			// drawCurrentState();
			// LOGGER.info("Setzt nun den StartStein:: ");
		} else if (state.equals(State.TURN_PLAYER1)) {
			// drawCurrentState();
			// String player1Name = myController.getPlayer1Name();
			// LOGGER.info(player1Name + " du bist dran:: ");
		} else if (state.equals(State.TURN_PLAYER2)) {
			// drawCurrentState();
			String player2Name = myController.getPlayer2Name();
			// LOGGER.info(player2Name + " du bist dran:: ");
		} else if (state.equals(State.PLAYER_WON)) {
			// TODO test ?
			String winner = myController.getWinner();
			String display = ("".equals(winner)) ? "Ein Unentschieden!" : "Der Gewinner ist: " + winner + "!!!";
			this.printWinnerPopup();
			// LOGGER.info(display);
		} else if (state.equals(State.GAME_FINISHED)) {
			// TODO test ?
			// LOGGER.info("Spiel ist beendet");
		}
		
		drawCurrentState();
	}

	// public void update() {
	// State controllerState = myController.getState();
	//
	// switch (controllerState) {
	// case GAME_FINISHED:
	// myController.setEnd(true);
	// this.quit();
	// break;
	// case PLAYER_OCCUPIED:
	// drawCurrentState();
	// break;
	// case QUERY_PLAYER_NAME:
	// queryPlayerName();
	// break;
	// case PLAYER_WON:
	// this.printWinnerPopup();
	// myController.newGame();
	// break;
	// default:
	// break;
	// }
	// }

}
