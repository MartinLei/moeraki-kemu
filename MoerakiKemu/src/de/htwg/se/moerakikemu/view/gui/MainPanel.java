package de.htwg.se.moerakikemu.view.gui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.modellayer.impl.State;

import java.awt.Color;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	IController myController;
	IControllerPlayer myPlayerController;

	ImageIcon blackIcon;
	ImageIcon redIcon;
	ImageIcon greenIcon;

	GridLayout gridForSpots;
	JButton[][] field;

	private MouseListener listener = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent me) {
			if (myController.getState() != State.TURN_PLAYER1 && myController.getState() != State.TURN_PLAYER2
					&& myController.getState() != State.SET_START_DOT)
				return;

			JButton pressedButton = (JButton) me.getSource();

			// Occupy Spot
			int[] coordinates = getButtonCoordinates(pressedButton);
			String name = myController.getIsOccupiedByPlayer(coordinates[0] - 1, coordinates[1] - 1);
			if ("".equals(name)) {
				setSpotColor(pressedButton, myPlayerController.getCurrentPlayerName());
				
				Point mousePosition = new Point(coordinates[0] - 1, coordinates[1] - 1);
				if (myController.getState().equals(State.SET_START_DOT))
					myController.setStartDot(mousePosition);
				else
					myController.setDot(mousePosition);
			}

		}
	};

	public MainPanel(IController controller, IControllerPlayer playerController, final int fieldLength) {
		super();
		this.myController = controller;
		this.myPlayerController = playerController;
		this.setBackground(new Color(200, 120, 40));

		// Read and scale images for occupied spots
		blackIcon = new ImageIcon("Spot_black.png");
		Image blackIconImg = blackIcon.getImage();
		blackIcon.setImage(blackIconImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		redIcon = new ImageIcon("Spot_blue.png");
		Image redIconImg = redIcon.getImage();
		redIcon.setImage(redIconImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		greenIcon = new ImageIcon("Spot_green.png");
		Image greenIconImg = greenIcon.getImage();
		greenIcon.setImage(greenIconImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH));

		gridForSpots = new GridLayout(fieldLength, fieldLength);
		this.setLayout(gridForSpots);
		field = new JButton[fieldLength][fieldLength];
		for (int i = 0; i < fieldLength; i++) {
			for (int j = 0; j < fieldLength; j++) {
				field[i][j] = new JButton();
				this.add(field[i][j]);
				field[i][j].addMouseListener(listener);
				field[i][j].setToolTipText("(" + (i + 1) + "/" + (j + 1) + ")");
				field[i][j].setText("+");
				field[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
				field[i][j].setVerticalTextPosition(SwingConstants.CENTER);
				field[i][j].setOpaque(false);
				field[i][j].setContentAreaFilled(false);
				field[i][j].setBorderPainted(false);
			}
		}
	}

	public void updateField() {
		int fieldLength = myController.getEdgeLength();
		for (int i = 0; i < fieldLength; i++) {
			for (int j = 0; j < fieldLength; j++) {
				setSpotColor(field[i][j], myController.getIsOccupiedByPlayer(i, j));
			}
		}
		this.repaint();
	}

	private int[] getButtonCoordinates(JButton button) {
		int[] xyCoordinates = new int[2];

		Scanner getNumbers = new Scanner(button.getToolTipText());
		getNumbers.useDelimiter("[()/]");
		xyCoordinates[0] = getNumbers.nextInt();
		xyCoordinates[1] = getNumbers.nextInt();
		getNumbers.close();

		return xyCoordinates;
	}

	private void setSpotColor(JButton buttonToChange, String playerNameOnSpot) {
		if (playerNameOnSpot == null || "".equals(playerNameOnSpot)) {
			buttonToChange.setText("+");
			buttonToChange.setIcon(null);
		} else if (myPlayerController.getPlayer1Name().equals(playerNameOnSpot)) {
			buttonToChange.setText("");
			buttonToChange.setIcon(blackIcon);
		} else if (myPlayerController.getPlayer2Name().equals(playerNameOnSpot)) {
			buttonToChange.setText("");
			buttonToChange.setIcon(redIcon);
		} else if ("StartDot".equals(playerNameOnSpot)) {
			buttonToChange.setText("");
			buttonToChange.setIcon(greenIcon);

		}
	}

}
