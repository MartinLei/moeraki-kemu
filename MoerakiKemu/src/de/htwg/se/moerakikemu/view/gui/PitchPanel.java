package de.htwg.se.moerakikemu.view.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

public class PitchPanel extends JPanel implements IObserver {
	private static final Logger LOGGER = LogManager.getLogger(PitchPanel.class.getName());

	IController controller;

	BufferedImage pitchImage;
	Point pitchSizePoint = new Point(520, 520);
	Point figureSize = new Point(40, 40);
	Point offsetPitch = new Point(15, 15);

	public PitchPanel(IController controller) {
		this.controller = controller;
		controller.addObserver(this);

		initGUI();
	}

	private void initGUI() {
		this.setBorder(BorderFactory.createTitledBorder("Pitch"));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Point mouse = new Point(e.getX(), e.getY());
				mouseReleasedHandler(mouse);
			}
		});

	}

	private void mouseReleasedHandler(Point mouse) {
		Point position = getCell(mouse);

		if (position != null) {
			State state = controller.getState();
			if (state.equals(State.SET_START_DOT))
				controller.setStartDot(position);
			else if (state.equals(State.TURN_PLAYER1) || state.equals(State.TURN_PLAYER2))
				controller.setDot(position);
		}

		this.repaint();
	}

	private Point getCell(Point mouse) {
		if (!isPosInPitch(mouse))
			return null;

		double px = mouse.getX() - offsetPitch.getX();
		double py = mouse.getY() - offsetPitch.getY();

		px = px / figureSize.getX();
		py = py / figureSize.getY();

		return new Point((int) px, (int) py);
	}

	private boolean isPosInPitch(Point mouse) {
		Rectangle inPitch = new Rectangle();
		inPitch.setBounds(offsetPitch.x, offsetPitch.y, pitchSizePoint.x, pitchSizePoint.y);

		if (inPitch.contains(mouse))
			return true;

		return false;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Paint the background.
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		drawPitch(g2d);

	}

	private void drawPitch(Graphics2D g2d) {
		List<Element> pitch = controller.getField();

		int x = 0;
		int y = 0;
		for (Element element : pitch) {
			int posX = (int) (x * figureSize.x + offsetPitch.getX());
			int posY = (int) (y * figureSize.y + offsetPitch.getX());

			Color elementColor = getElementColor(element);
			g2d.setColor(elementColor);
			g2d.fill(new Rectangle2D.Double(posX, posY, figureSize.x, figureSize.y));

			if (x == 12) {
				x = 0;
				y++;
			} else
				x++;
		}
	}

	private Color getElementColor(Element element) {
		Color color = Color.cyan;
		switch (element) {
		case NONE:
			color = new Color(100, 150, 0, 100);
			break;
		case WALL:
			color = new Color(51, 0, 0, 180);
			break;
		case ISLAND:
			color = new Color(100, 150, 0, 255);
			break;
		case PLAYER1:
			color = new Color(0, 0, 255, 255);
			break;
		case PLAYER2:
			color = new Color(255, 0, 0, 255);
			break;
		case POINT_PLAYER1:
			color = new Color(0, 0, 255, 100);
			break;
		case POINT_PLAYER2:
			color = new Color(255, 0, 0, 100);
			break;
		case HALF_POINT_PLAYER1:
			color = new Color(0, 0, 255, 50);
			break;
		case HALF_POINT_PLAYER2:
			color = new Color(255, 0, 0, 50);
			break;
		case STARTDOT:
			color = new Color(0, 255, 0, 255);
			break;
		default:
			break;
		}

		return color;
	}

	@Override
	public void update(Event e) {
		this.repaint();

		// Status state = controller.getGameStatus();
		// if (gs.equals(GameStatus.FINISH)) {
		// JOptionPane.showMessageDialog(null, controller.getStatusText(), "Some
		// one has won the game",
		// JOptionPane.INFORMATION_MESSAGE);
		// }
	}

}
