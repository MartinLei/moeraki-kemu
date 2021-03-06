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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

public class PitchPanel extends JPanel implements IObserver {
	private transient IController controller;
	
	private static final Point PITZ_SIZE = new Point(520, 520);
	private static final Point CELL_SIZE = new Point(40, 40);
	private static final Point OFFSET_PTICH = new Point(15, 15);

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

		double px = mouse.getX() - OFFSET_PTICH.getX();
		double py = mouse.getY() - OFFSET_PTICH.getY();

		px = px / CELL_SIZE.getX();
		py = py / CELL_SIZE.getY();

		return new Point((int) px, (int) py);
	}

	private boolean isPosInPitch(Point mouse) {
		Rectangle inPitch = new Rectangle();
		inPitch.setBounds(OFFSET_PTICH.x, OFFSET_PTICH.y, PITZ_SIZE.x, PITZ_SIZE.y);

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
			int posX = (int) (x * CELL_SIZE.x + OFFSET_PTICH.getX());
			int posY = (int) (y * CELL_SIZE.y + OFFSET_PTICH.getX());

			Color elementColor = getElementColor(element);
			g2d.setColor(elementColor);
			g2d.fill(new Rectangle2D.Double(posX, posY, CELL_SIZE.x, CELL_SIZE.y));

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
	}

}
