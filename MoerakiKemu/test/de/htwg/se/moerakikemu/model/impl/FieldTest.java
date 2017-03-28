package de.htwg.se.moerakikemu.model.impl;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.model.IField;

public class FieldTest {
	private static final int EDGE_LENGTH = 12;

	IField errorField;
	IField field;
	Player player1;
	Player player2;

	private static final int MAP_LENGTH = 13;

	@Before
	public void setUp() {
		field = new Field();
		player1 = new Player();
		player2 = new Player();
	}

	@Test
	public void test_newField() {
		for (int i = 0; i < MAP_LENGTH; i++) {
			// top row
			assertEquals(Element.WALL, field.getElement(new Point(i, 0)));
			// bottom row
			assertEquals(Element.WALL, field.getElement(new Point(i, 12)));
			// left side
			assertEquals(Element.WALL, field.getElement(new Point(0, i)));
			// right side
			assertEquals(Element.WALL, field.getElement(new Point(12, i)));
		}

		// upper left corner
		assertEquals(Element.WALL, field.getElement(new Point(1, 1)));
		assertEquals(Element.WALL, field.getElement(new Point(2, 1)));
		assertEquals(Element.WALL, field.getElement(new Point(1, 2)));

		// upper right corner
		assertEquals(Element.WALL, field.getElement(new Point(10, 1)));
		assertEquals(Element.WALL, field.getElement(new Point(11, 1)));
		assertEquals(Element.WALL, field.getElement(new Point(11, 2)));

		// bottom left corner
		assertEquals(Element.WALL, field.getElement(new Point(1, 10)));
		assertEquals(Element.WALL, field.getElement(new Point(2, 11)));
		assertEquals(Element.WALL, field.getElement(new Point(1, 11)));

		// bottom right corner
		assertEquals(Element.WALL, field.getElement(new Point(11, 10)));
		assertEquals(Element.WALL, field.getElement(new Point(11, 11)));
		assertEquals(Element.WALL, field.getElement(new Point(10, 11)));

	}

	@Test
	public void test_getElement() {
		assertEquals(Element.WALL, field.getElement(new Point(0, 0)));
	}

	@Test
	public void test_getActPlayer() {
		assertEquals(Element.PLAYER1, field.getCurrentPlayer());
	}

	@Test
	public void test_changeActPlayer() {
		assertEquals(Element.PLAYER1, field.getCurrentPlayer());
		field.changeActPlayer();
		assertEquals(Element.PLAYER2, field.getCurrentPlayer());
		field.changeActPlayer();
		assertEquals(Element.PLAYER1, field.getCurrentPlayer());
	}

	@Test
	public void test_occupy() {
		Point postition = new Point(1, 1);
		assertTrue(field.occupy(postition, Element.PLAYER1));
		assertEquals(Element.PLAYER1, field.getElement(postition));
		assertFalse(field.occupy(postition, Element.PLAYER2));
		assertEquals(Element.PLAYER1, field.getElement(postition));
	}

	@Test
	public void test_getOccupiedCount() {
		List<Point> cells = new ArrayList<>();
		cells.add(new Point(4, 4));
		cells.add(new Point(3, 5));
		cells.add(new Point(4, 6));
		cells.add(new Point(5, 5));
		cells.add(null);

		assertTrue(field.occupy(new Point(4, 4), Element.PLAYER1));
		assertTrue(field.occupy(new Point(3, 5), Element.PLAYER2));
		assertTrue(field.occupy(new Point(4, 6), Element.NONE));
		assertTrue(field.occupy(new Point(5, 5), Element.PLAYER1));

		assertEquals(2, field.getOccupiedCount(cells, Element.PLAYER1));
		assertEquals(1, field.getOccupiedCount(cells, Element.PLAYER2));
		assertEquals(1, field.getOccupiedCount(cells, Element.NONE));
	}

	@Test
	public void test_getCurrentPlayerPointElement() {
		assertEquals(Element.POINT_PLAYER1, field.getCurrentPlayerPointElement());
		field.changeActPlayer();
		assertEquals(Element.POINT_PLAYER2, field.getCurrentPlayerPointElement());

	}

	@Test
	public void test_getCurrentPlayerHalfPointElement() {
		assertEquals(Element.HALF_POINT_PLAYER1, field.getCurrentPlayerHalfPointElement());
		field.changeActPlayer();
		assertEquals(Element.HALF_POINT_PLAYER2, field.getCurrentPlayerHalfPointElement());

	}


	@Test
	public void test_getNextPlayer() {
		assertEquals(Element.PLAYER2, field.getNextPlayer());
		field.changeActPlayer();
		assertEquals(Element.PLAYER1, field.getNextPlayer());
	}
}
