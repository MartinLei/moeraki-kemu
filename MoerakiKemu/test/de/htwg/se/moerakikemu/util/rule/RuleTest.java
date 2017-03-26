package de.htwg.se.moerakikemu.util.rule;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.Field;

public class RuleTest {

	IField field;
	Rule rule;
	
	@Before
	public void setUp() throws Exception {
		field = new Field();
		rule = new Rule();
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
	
	@Test
	public void test_isOccupiedIsland(){
		//Set 4 Stone middel
		assertTrue(field.occupy(new Point(4,4), Element.PLAYER1));
		assertTrue(field.occupy(new Point(3,5), Element.PLAYER1));
		assertTrue(field.occupy(new Point(4,6), Element.PLAYER1));
		assertTrue(field.occupy(new Point(5,5), Element.PLAYER1));
		
		//test left
		assertTrue(rule.isOccupiedIsland(field, new Point(5,5)));
		assertEquals(Element.POINT_PLAYER1,field.getElement(new Point(4,5)));
	}
}
