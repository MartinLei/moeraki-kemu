package de.htwg.se.moerakikemu.model.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ElementTest {

	private static final String NONE = " ";
	private static final String WALL = "W";
	private static final String ISLAND = "O";
	private static final String PLAYER1 = "a";
	private static final String PLAYER2 = "b";
	private static final String POINT_PLAYER1 = "A";
	private static final String POINT_PLAYER2 = "B";
	private static final String HALF_POINT_PLAYER1 = "A";
	private static final String HALF_POINT_PLAYER2 = "B";
	private static final String STARTDOT = "#";
	

	@Before
	public void setUp() {
	
	}

	@Test
	public void test_toString() {
		assertEquals(NONE, Element.NONE.toString());
		assertEquals(WALL, Element.WALL.toString());
		assertEquals(ISLAND, Element.ISLAND.toString());
		assertEquals(PLAYER1, Element.PLAYER1.toString());
		assertEquals(PLAYER2, Element.PLAYER2.toString());
		assertEquals(POINT_PLAYER1, Element.POINT_PLAYER1.toString());
		assertEquals(POINT_PLAYER2, Element.POINT_PLAYER2.toString());
		assertEquals(HALF_POINT_PLAYER1, Element.HALF_POINT_PLAYER1.toString());
		assertEquals(HALF_POINT_PLAYER2, Element.HALF_POINT_PLAYER2.toString());
		assertEquals(STARTDOT, Element.STARTDOT.toString());
	}
	
}
