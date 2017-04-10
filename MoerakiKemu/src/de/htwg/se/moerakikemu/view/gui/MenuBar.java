package de.htwg.se.moerakikemu.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.htwg.se.moerakikemu.controller.IController;

public class MenuBar extends JMenuBar {
	private transient IController controller;

	private JMenu fileMenu;
	private JMenuItem newMenuItem;
	private JMenuItem quitMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem loadMenuItem;

	private JMenu infoMenu;
	private JMenuItem helpMenuItem;

	public MenuBar(IController controller) {
		this.controller = controller;
		createFileMenu(controller);
		createInfoMenu();
	}

	private void createFileMenu(IController controller) {
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		newMenuItem = new JMenuItem("new game");
		newMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				String[] yesNoOptions = { "Yes", "No" };
				int n = JOptionPane.showOptionDialog(null, "Do you want to start a new game?", "New game?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesNoOptions, yesNoOptions[0]);

				if (n == JOptionPane.YES_OPTION) {
					controller.newGame();
					;
				}

			}
		});
		newMenuItem.setMnemonic(KeyEvent.VK_N);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(newMenuItem);
		
		saveMenuItem = new JMenuItem("save game");
		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.saveToDB();
			}
		});
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(saveMenuItem);
		
		loadMenuItem = new JMenuItem("load game");
		loadMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.loadDB();
			}
		});
		loadMenuItem.setMnemonic(KeyEvent.VK_L);
		loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(loadMenuItem);

		quitMenuItem = new JMenuItem("quit");
		quitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.quitGame();
			}
		});
		quitMenuItem.setMnemonic(KeyEvent.VK_Q);
		quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(quitMenuItem);

		this.add(fileMenu);
	}

	private void createInfoMenu() {
		infoMenu = new JMenu("Info");
		infoMenu.setMnemonic(KeyEvent.VK_F);

		helpMenuItem = new JMenuItem("Help");
		helpMenuItem.setMnemonic(KeyEvent.VK_H);
		helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		helpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				final String rules = "Spielregeln: \n " + "Ziel: \n"
						+ "    Das Ziel dieses Spiels ist es so viele Punkte bei Beenden des Spiels zu haben wie moeglich.\n"
						+ "Punkte:\n"
						+ "    Einen Punkt bekommt man in dem man bei einem Quadrat mehr Eckpunkte eingenommen hat als der Gegenspieler\n"
						+ "    Der Startpunkt wird als neutral betrachtet und gibt nimanden Punkte\n" + "Spielende:\n"
						+ "    Sobald ein Spieler vier Eckpunkte eines Quadrates eingenommen hat oder das komplette Spielfeld belegt ist, endet das Spiel\n"
						+ "    Alternativ ist es auch moeglich sich vom Startstein in gerader Richtung zu einem beliebigen Rand zu \"bewegen\" um das SPiel zu beenden";

				JOptionPane.showMessageDialog(null, rules);
			}
		});

		infoMenu.add(helpMenuItem);
		this.add(infoMenu);
	}

}
