package de.htwg.se.moerakikemu.model.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

	private static final String NONE = " ";
	private static final String PLAYER1 = "+";
	private static final String PLAYER2 = "o";
	private static final String STARTDOT = "#";
	
	@Before
	public void setUp() {
	
	}

	@Test
	public void test_toString() {
		assertEquals(NONE, Person.NONE.toString());
		assertEquals(PLAYER1, Person.PLAYER1.toString());
		assertEquals(PLAYER2, Person.PLAYER2.toString());
		assertEquals(STARTDOT, Person.STARTDOT.toString());
	}
	
}
