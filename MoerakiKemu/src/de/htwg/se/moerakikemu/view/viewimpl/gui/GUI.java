package de.htwg.se.moerakikemu.view.viewimpl.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.util.observer.ObserverObserver;

public class GUI extends JFrame implements UserInterface, ObserverObserver {
	private static final long serialVersionUID = 2078463309153663728L;

	private IController myController;
	private IControllerPlayer myPlayerController;
	
	private MainPanel myMainPanel;
	private MessagePanel myMessagePanel;

	@Inject
	public GUI(IController newController, IControllerPlayer playerController) {
		super("Moeraki Kemu");
		this.myController = newController;
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

	@Override
	public void update() {
		State controllerState = myController.getState();
		
		switch (controllerState) {
		case game_finished:
			myController.setEnd(true);
			this.quit();
			break;
		case player_occupied:
			drawCurrentState();
			break;
		case query_player_name:
			queryPlayerName();
			break;
		case player_won:
			this.printWinnerPopup();
			myController.newGame();
			break;
		default:
			break;
		}
	}

	@Override
	public void drawCurrentState() {
		this.myMainPanel.updateField();
		this.repaint();
	}

	@Override
	public void queryPlayerName() {
		String player1Name = "";
		String player2Name = "";

		while ("".equals(player1Name)) {
			player1Name = JOptionPane.showInputDialog("Name fuer Spieler 1 eigeben:", "Spieler 1");
		}

		while ("".equals(player2Name)) {
			player2Name = JOptionPane.showInputDialog("Name fuer Spieler 2 eigeben:", "Spieler 2");
		}

		myPlayerController.setName(player1Name, player2Name);
		myMessagePanel.setPlayerNames(player1Name, player2Name);
	}

	private void printWinnerPopup() {
		String winner = myController.getWinner();
		String display = ("".equals(winner)) ?  "Ein Unentschieden!" :
												"Der Gewinner ist: " + winner + "!!!";
		JOptionPane.showMessageDialog(null, display);
	}
	
	@Override
	public void quit(){
		printWinnerPopup();
		this.dispose();
	}

	@Override
	public void printMessage(String msg) {
		myMessagePanel.printMessage(msg);		
	}
	
	@Override
	public void addPoints(int pointsPlayer1, int pointsPlayer2){
		myMessagePanel.setPlayerPoints(pointsPlayer1, pointsPlayer2);
	}

}
