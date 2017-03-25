package de.htwg.se.moerakikemu.view.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

/**
 * Graphic Interface for the game
 */
public class GUI extends JFrame implements IObserver {
	private static final long serialVersionUID = 2078463309153663728L;

	private transient IController myController;

	private MainPanel myMainPanel;
	private MessagePanel myMessagePanel;

	@Inject
	public GUI(IController newController) {
		super("Moeraki Kemu");
		this.myController = newController;
		myController.addObserver(this);

		this.myMainPanel = new MainPanel(myController, myController.getEdgeLength());
		this.myMessagePanel = new MessagePanel(myController);

		this.setJMenuBar(new MainMenu(myController));

		this.setLayout(new BorderLayout());
		this.add(myMainPanel, BorderLayout.CENTER);
		this.add(myMessagePanel, BorderLayout.EAST);

		this.setSize(1024, 768);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * redraw the window
	 */
	public void drawCurrentState() {
		myMainPanel.updateField();
		myMessagePanel.updateView();
		repaint();
	}

	private void printWinnerPopup(String name) {
		String display = "Der Gewinner ist: " + name + "!!!";
		JOptionPane.showMessageDialog(null, display);
	}

	@Override
	public void update(Event e) {
		State state = myController.getState();

		drawCurrentState();

		if (state.equals(State.EXIT_GAME)) {
			this.setVisible(false);
			this.dispose();
			return;
		} else if (state.equals(State.GET_FIRST_PLAYER_NAME)) {
			String player1Name = JOptionPane.showInputDialog("Name fuer Spieler 1 eigeben:", "Andrew");
			if (player1Name == null)
				player1Name = "Andrew";

			myController.setPlayer1Name(player1Name);
			myMessagePanel.printMessage("Spieler1 heist: " + player1Name);
		} else if (state.equals(State.GET_SECOND_PLAYER_NAME)) {
			String player2Name = JOptionPane.showInputDialog("Name fuer Spieler 2 eigeben:", "Walter");
			if (player2Name == null)
				player2Name = "Walter";

			myController.setPlayer2Name(player2Name);
			myMessagePanel.printMessage("Spieler2 heist: " + player2Name);
		} else if (state.equals(State.SET_START_DOT)) {
			myMessagePanel.printMessage("Setzt nun den StartStein");
		} else if (state.equals(State.TURN_PLAYER1)) {
			String player1Name = myController.getPlayer1Name();
			myMessagePanel.printMessage(player1Name + " du bist dran");
		} else if (state.equals(State.TURN_PLAYER2)) {
			String player2Name = myController.getPlayer2Name();
			myMessagePanel.printMessage(player2Name + " du bist dran");
		} else if (state.equals(State.PLAYER1_WON)) {
			String playerName = myController.getPlayer1Name();
			myMessagePanel.printMessage("Der Gewinnder ist Spieler1 aka ->" + playerName);
			this.printWinnerPopup(playerName);
		} else if (state.equals(State.PLAYER2_WON)) {
			String playerName = myController.getPlayer2Name();
			myMessagePanel.printMessage("Der Gewinnder ist Spieler1 aka ->" + playerName);
			this.printWinnerPopup(playerName);
		} else if (state.equals(State.GAME_FINISHED)) {
			myMessagePanel.printMessage("Ende keiner hat gewonnen");
		}

	}

}
