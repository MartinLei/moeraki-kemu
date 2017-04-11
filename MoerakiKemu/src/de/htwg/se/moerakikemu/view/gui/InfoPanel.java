package de.htwg.se.moerakikemu.view.gui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

/**
 * Right side of the gui
 */
public class InfoPanel extends JPanel implements IObserver {
	private transient IController controller;

	private JLabel lPlayer1;
	private JLabel lPlayer2;
	private JLabel lInfo;

	/**
	 * Constructor
	 * @param controller
	 */
	public InfoPanel(IController controller) {
		this.controller = controller;
		controller.addObserver(this);

		lPlayer1 = new JLabel();
		this.add(lPlayer1);
		lPlayer2 = new JLabel();
		this.add(lPlayer2);
		lInfo = new JLabel();
		this.add(lInfo);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void updateInfoPanel() {
		lPlayer1.setText(getPlayerText(Element.PLAYER1));
		lPlayer1.setForeground (Color.blue);
		lPlayer2.setText(getPlayerText(Element.PLAYER2));
		lPlayer2.setForeground (Color.red);
		lInfo.setText("Info:: " + controller.getState().toString());
	}

	private String getPlayerText(Element player) {
		return controller.getPlayerName(player) + ": " + controller.getPlayerPoint(player);
	}

	@Override
	public void update(Event e) {
		updateInfoPanel();
	}

}
