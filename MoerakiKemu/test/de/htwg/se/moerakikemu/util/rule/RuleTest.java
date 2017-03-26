package de.htwg.se.moerakikemu.util.rule;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class RuleTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_isCorrectPosition()
	{
		assertFalse(Rule.isPositionPossibleInput(new Point(0,0)));
		assertFalse(Rule.isPositionPossibleInput(new Point(0,14)));
		assertFalse(Rule.isPositionPossibleInput(new Point(14,0)));
		assertFalse(Rule.isPositionPossibleInput(new Point(14,14)));
		assertFalse(Rule.isPositionPossibleInput(new Point(5,14)));
		assertFalse(Rule.isPositionPossibleInput(new Point(14,5)));
		
		assertFalse(Rule.isPositionPossibleInput(new Point(5,4)));
		assertFalse(Rule.isPositionPossibleInput(new Point(4,5)));
		assertTrue(Rule.isPositionPossibleInput(new Point(4,4)));
		assertTrue(Rule.isPositionPossibleInput(new Point(5,5)));
	}
}
