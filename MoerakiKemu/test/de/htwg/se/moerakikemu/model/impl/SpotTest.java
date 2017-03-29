package de.htwg.se.moerakikemu.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SpotTest {
	Spot spot;

	@Before
	public void setUp() {
		spot = new Spot();
	}

	@Test
	public void test_isOccupied() {
		spot.setElement(Element.PLAYER1);
		assertTrue(spot.isOccupiedByPlayer());
		spot.setElement(Element.PLAYER2);
		assertTrue(spot.isOccupiedByPlayer());
		
		spot.setElement(Element.WALL);
		assertFalse(spot.isOccupiedByPlayer());
	}

	@Test
	public void test_isOccupied_alreadyOccupied_returnsFalse() {
		assertTrue(spot.occupy(Element.PLAYER1));
		assertTrue(spot.isOccupiedByPlayer());

		assertFalse(spot.occupy(Element.PLAYER2));
		assertEquals(Element.PLAYER1, spot.getElement());
	}
}