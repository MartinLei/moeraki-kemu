package de.htwg.se.moerakikemu.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;

public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	IController myController;
	IControllerPlayer myPlayerController;
	
	private JPanel playerPanel;
	
	private JLabel player1;
	private JLabel player2;
	private JTextField pointsPlayer1;
	private JTextField pointsPlayer2;
	
	private JTextArea messages;
	
	public MessagePanel(IController controller, IControllerPlayer playerController) {
		super();
		this.myController = controller;
		this.myPlayerController = playerController;
		this.setPreferredSize(new Dimension(200,200));
		this.setBackground(new Color(180, 100, 10));

		
		playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(0, 2));
		player1 = new JLabel("Spieler 1");
		player2 = new JLabel("Spieler 2");
		pointsPlayer1 = new JTextField("0", 6);
		pointsPlayer2 = new JTextField("0", 6);
		pointsPlayer1.setEditable(false);
		pointsPlayer2.setEditable(false);
		
		playerPanel.add(player1);
		playerPanel.add(pointsPlayer1);
		playerPanel.add(player2);
		playerPanel.add(pointsPlayer2);
		
		messages = new JTextArea("Spiel - Nachrichten", 30, 15);
		messages.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(messages);
		scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		this.add(playerPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.SOUTH);
	}

	public void printMessage(String msg) {
		if("".equals(msg)){
			messages.setText(null);
		}
		messages.append("\n" + msg);
	}	

	public void setPlayerPoints(int player1Points, int player2Points){
		String pointsP1 = String.valueOf(player1Points);
		String pointsP2 = String.valueOf(player2Points);
		pointsPlayer1.setText(pointsP1);
		pointsPlayer2.setText(pointsP2);
	}

	public void updateView() {
		player1.setText(myController.getPlayer1Name());
		player2.setText(myController.getPlayer2Name());
	}

}
