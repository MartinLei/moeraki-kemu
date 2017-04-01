package de.htwg.se.moerakikemu.view.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

/**
 * Graphic Interface for the game
 */
public class GUI extends JFrame implements IObserver {
	private transient IController controller;
	
	private static final String GAME_TITLE = "Moeraki Kemu"; 

	private static final int DEFAULT_Y = 580;
	private static final int DEFAULT_X = 720;

	private PitchPanel pitchPanel;
	private Container pane;
	
	@Inject
	public GUI(final IController controller) {
		this.controller = controller;
		controller.addObserver(this);

		this.setTitle(GAME_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(DEFAULT_X, DEFAULT_Y);
		this.setJMenuBar(new MenuBar(controller));
		pane = getContentPane();
		pane.setLayout(new BorderLayout());

		constructArimaaPane(controller);

		// Closing window handler
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				controller.quitGame();
			}
		});
	}

	private void constructArimaaPane(IController controller) {
		JPanel centerPanel = new JPanel();
		pane.add(centerPanel, BorderLayout.CENTER);

		JPanel leftPanel = new JPanel(new GridLayout(1, 0));

		leftPanel.setPreferredSize(new Dimension(550, 550));
		centerPanel.add(leftPanel);

		pitchPanel = new PitchPanel(controller);
		leftPanel.add(pitchPanel);

//		JPanel rightPanel = new JPanel();
//		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
//		centerPanel.add(rightPanel);
//
//		infoPanel = new InfoPanel(controller);
//		rightPanel.add(infoPanel);
//
//		moveStatusPanel = new MoveHistoryPanel(controller);
//		rightPanel.add(moveStatusPanel);
//
//		buttonPanel = new ButtonPanel(controller);
//		rightPanel.add(buttonPanel);
//
//		statusPanel = new StatusPanel();
//		pane.add(statusPanel, BorderLayout.SOUTH);

		setVisible(true);
		repaint();

	}

	@Override
	public void update(Event e) {
		//statusPanel.setText(controller.getGameStatus(), controller.getStatusText());

		State state = controller.getState();
		if (state.equals(State.EXIT_GAME)) {
			this.setVisible(false);
			this.dispose();
			return;
		}

		repaint();
	}

}
