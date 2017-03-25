package de.htwg.se.moerakikemu.modellayer.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.modellayer.impl.Field;
import de.htwg.se.moerakikemu.modellayer.impl.Player;

public class Field_Test {
	
	private static final int EDGELENGTH = 6;

	Field errorField;
	Field field;
	Player player1;
	Player player2;

	@Before
	public void setUp() {
		field = new Field(EDGELENGTH);
		player1 = new Player();
		player2 = new Player();
	}

	@Test
	public void test_getIsOccupied_unoccupiedSpotReturnsFalse() {
		assertEquals("", field.getIsOccupiedFrom(1, 2));
	}
	@Test
	public void test_occupy_occupyEmptySpotReturnsNoPoints() {
		assertTrue(field.occupy(2, 2, player1.getName()));
	}

	@Test
	public void test_occupy_occupyNotEmptySpotReturnsFalse() {
		assertTrue(field.occupy(2, 2, player1.getName()));
		assertFalse(field.occupy(2, 2, player1.getName()));
	}
	
	@Test
	public void test_getEdgeLength_sixAsInitialized() {
		assertEquals(field.getEdgeLength(), EDGELENGTH);
	}
	

	@Test
	public void test_getIsOccupied_occupyReturnsTrue() {
		field.occupy(3, 4, "Player");
		assertTrue(field.getIsOccupied(3, 4));
	}
	
	@Test
	public void test_getIsOccupied_notOccupiedReturnsFalse() {
		assertFalse(field.getIsOccupied(1, 5));
	}
	
	@Test
	public void test_isFilled_notAllOccupiedReturnsFalse() {
		field.occupy(2, 3, "P1");
		field.occupy(2, 4, "P1");
		field.occupy(4, 3, "P1");
		field.occupy(5, 3, "P1");
		assertFalse(field.isFilled());
	}
	
	@Test
	public void test_isFilled_allOccupiedReturnsTrue() {
		for(int i = 0; i < EDGELENGTH; i++) {
			for(int j = 0; j < EDGELENGTH; j++) {
				field.occupy(i, j, "Spieler");
			}
		}
		assertTrue(field.isFilled());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_constructor_negativeEdgelengthThrowsException() {
		errorField = new Field(-1);
	}
}
