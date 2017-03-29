package de.htwg.se.moerakikemu.controller.imp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.impl.Controller;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;
import de.htwg.se.moerakikemu.persistence.db4o.FieldDb4oDAO;

public class ControllerTest {
	IController controller;

	private final static Point STARTDOT = new Point(6, 6);
	private final IFieldDAO fieldDAO = new FieldDb4oDAO();

	@Before
	public void setUp() {
		controller = new Controller(fieldDAO);
	}

	@Test
	public void test_newGame() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		controller.newGame();
		
		assertEquals(State.SET_START_DOT, controller.getState());
	}

	@Test
	public void test_newGameQickStart() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		controller.newGameQuickStart();

		assertEquals(State.TURN_PLAYER1, controller.getState());
	}

	@Test
	public void test_setStartDot() {
		assertEquals(State.SET_START_DOT, controller.getState());
		assertFalse(controller.setStartDot(null));
		assertEquals(State.SET_START_DOT, controller.getState());
		assertFalse(controller.setStartDot(new Point(0, 0)));
		assertEquals(State.SET_START_DOT, controller.getState());

		assertTrue(controller.setStartDot(STARTDOT));
		assertEquals(State.TURN_PLAYER1, controller.getState());
	}
	
	@Test
	public void test_setDot(){
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());
		
		assertFalse(controller.setDot(null));
		assertFalse(controller.setDot(new Point(0,0)));
		assertFalse(controller.setDot(STARTDOT));
	}

	@Test
	public void test_isOccupiedIsland_1() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 4 Stone Player1 middle, 3 Stone Player2 left wall
		assertTrue(controller.setDot(new Point(4, 4)));
		assertTrue(controller.setDot(new Point(1, 5)));
		assertTrue(controller.setDot(new Point(3, 5)));
		assertTrue(controller.setDot(new Point(1, 7)));
		assertTrue(controller.setDot(new Point(4, 6)));
		assertTrue(controller.setDot(new Point(2, 6)));

		// test left player2 half point
		assertEquals(Element.HALF_POINT_PLAYER2, controller.getFieldElement(1, 6));

		assertTrue(controller.setDot(new Point(5, 5)));
		// player1 win
		assertEquals(Element.POINT_PLAYER1, controller.getFieldElement(4, 5));
		assertEquals(State.WON, controller.getState());
	}

	@Test
	public void test_isOccupiedIsland_2() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 4 Stone Player2 middle, 3 Stone Player1 down wall
		assertTrue(controller.setDot(new Point(3, 11)));
		assertTrue(controller.setDot(new Point(8, 2)));
		assertTrue(controller.setDot(new Point(4, 10)));
		assertTrue(controller.setDot(new Point(9, 3)));
		assertTrue(controller.setDot(new Point(5, 11)));

		assertEquals(Element.HALF_POINT_PLAYER1, controller.getFieldElement(4, 11));

		assertTrue(controller.setDot(new Point(7, 3)));
		assertTrue(controller.setDot(new Point(2, 10)));
		assertTrue(controller.setDot(new Point(8, 4)));

		assertEquals(Element.POINT_PLAYER2, controller.getFieldElement(8, 3));
		assertEquals(State.WON, controller.getState());
	}

	@Test
	public void test_isOccupiedIsland_3() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 3 Stone Player1 middle, 1 Stone Player2 to the island
		assertTrue(controller.setDot(new Point(4, 4)));
		assertTrue(controller.setDot(new Point(1, 5)));
		assertTrue(controller.setDot(new Point(2, 6)));
		assertTrue(controller.setDot(new Point(1, 7)));
		assertTrue(controller.setDot(new Point(4, 6)));
		assertTrue(controller.setDot(new Point(3, 5)));
		assertTrue(controller.setDot(new Point(5, 5)));

		assertEquals(Element.ISLAND, controller.getFieldElement(1, 6));

		// player1 point
		assertEquals(Element.POINT_PLAYER1, controller.getFieldElement(4, 5));
		assertEquals(State.TURN_PLAYER2, controller.getState());
	}

	@Test
	public void test_isOccupiedIsland_4() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 2 Stone Player1 middle, 2 Stone Player2 to the island
		assertTrue(controller.setDot(new Point(1, 5)));
		assertTrue(controller.setDot(new Point(4, 4)));
		assertTrue(controller.setDot(new Point(2, 6)));
		assertTrue(controller.setDot(new Point(1, 7)));
		assertTrue(controller.setDot(new Point(4, 6)));
		assertTrue(controller.setDot(new Point(3, 5)));
		assertTrue(controller.setDot(new Point(5, 5)));

		assertEquals(Element.ISLAND, controller.getFieldElement(1, 6));
		assertEquals(Element.ISLAND, controller.getFieldElement(4, 5));
		assertEquals(State.TURN_PLAYER2, controller.getState());
	}

	@Test
	public void test_isOccupiedIsland_5() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 3 Stone Player1 around StartDot upp, 2 Stone Player2 left upper
		// corner
		assertTrue(controller.setDot(new Point(5, 5)));
		assertTrue(controller.setDot(new Point(2, 2)));
		assertTrue(controller.setDot(new Point(7, 5)));
		assertTrue(controller.setDot(new Point(3, 1)));
		assertTrue(controller.setDot(new Point(6, 4)));
		assertEquals(Element.POINT_PLAYER1, controller.getFieldElement(6, 5));
		assertEquals(State.WON, controller.getState());
	}

	@Test
	public void test_isOccupiedIsland_6() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 3 Stone Player2 around StartDot down, 3 Stone Player1 left upper
		// corner
		assertTrue(controller.setDot(new Point(3, 3)));
		assertTrue(controller.setDot(new Point(5, 7)));
		assertTrue(controller.setDot(new Point(4, 4)));
		assertTrue(controller.setDot(new Point(6, 8)));
		assertTrue(controller.setDot(new Point(2, 4)));
		assertTrue(controller.setDot(new Point(7, 7)));

		assertEquals(Element.POINT_PLAYER2, controller.getFieldElement(6, 7));
		assertEquals(State.WON, controller.getState());
	}

	@Test
	public void test_isOccupiedIsland_7() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 3 Stone Player1 upper wall, set 3 Stone Player2 right wall
		// corner
		assertTrue(controller.setDot(new Point(3, 1)));
		assertTrue(controller.setDot(new Point(11, 3)));
		assertTrue(controller.setDot(new Point(4, 2)));
		assertTrue(controller.setDot(new Point(10, 4)));
		assertTrue(controller.setDot(new Point(5, 1)));

		assertEquals(Element.HALF_POINT_PLAYER1, controller.getFieldElement(4, 1));
		assertTrue(controller.setDot(new Point(11, 5)));
		assertEquals(Element.HALF_POINT_PLAYER2, controller.getFieldElement(11, 4));
	}

	@Test
	public void test_isOccupiedIsland_8() {
		controller.setStartDot(STARTDOT);
		assertEquals(State.TURN_PLAYER1, controller.getState());

		// Set 3 Stone Player1 middle, 1 Stone Player2 on the island
		assertTrue(controller.setDot(new Point(3, 7)));
		assertTrue(controller.setDot(new Point(7, 3)));
		assertTrue(controller.setDot(new Point(2, 8)));
		assertTrue(controller.setDot(new Point(8, 2)));
		assertTrue(controller.setDot(new Point(4, 8)));
		assertTrue(controller.setDot(new Point(3, 9)));

		assertEquals(Element.POINT_PLAYER1, controller.getFieldElement(3, 8));

		assertTrue(controller.setDot(new Point(8, 4)));
		assertTrue(controller.setDot(new Point(9, 3)));
		assertEquals(Element.POINT_PLAYER2, controller.getFieldElement(8, 3));
	}
}
