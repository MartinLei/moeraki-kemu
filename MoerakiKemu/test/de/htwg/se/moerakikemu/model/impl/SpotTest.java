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
		assertTrue(spot.occupy( Person.PLAYER1));
		assertTrue(spot.isOccupied());
		assertEquals(Person.PLAYER1, spot.getOccupiedBy());
	}
	
	@Test
	public void test_isOccupied_alreadyOccupied_returnsFalse() {
		assertTrue(spot.occupy( Person.PLAYER1));
		assertTrue(spot.isOccupied());
		
		assertFalse(spot.occupy(Person.PLAYER2));
		assertEquals(Person.PLAYER1, spot.getOccupiedBy());
	}
}