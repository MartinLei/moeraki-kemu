package de.htwg.se.moerakikemu.model.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.model.impl.Spot;

public class SpotTest {
	Spot spot;

	@Before
	public void setUp(){
		spot = new Spot();
	}
	
	@Test
	public void test_isOccupied_firstOccupation_returnsNotEmptyString(){
		assertTrue(spot.occupy( Element.PLAYER1));
		assertTrue(spot.isOccupied());
		assertEquals(Element.PLAYER1, spot.getElement());
	}
	
	@Test
	public void test_isOccupied_alreadyOccupied_returnsFalse() {
		assertTrue(spot.occupy( Element.PLAYER1));
		assertTrue(spot.isOccupied());
		
		assertFalse(spot.occupy(Element.PLAYER2));
		assertEquals(Element.PLAYER1, spot.getElement());
	}
}