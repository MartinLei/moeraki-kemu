package de.htwg.se.moerakikemu.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	public void test_newField(){
		for(int i = 0; i< MAP_LENGTH; i++){
			//top row
			assertEquals(Element.WALL, field.getElement(i, 0));
			//bottom row
			assertEquals(Element.WALL, field.getElement(i,12));
			//left side
			assertEquals(Element.WALL, field.getElement(0, i));
			//right side
			assertEquals(Element.WALL, field.getElement(12, i));
		}
		
		// upper left corner
		assertEquals(Element.WALL, field.getElement(1, 1));
		assertEquals(Element.WALL, field.getElement(2, 1));
		assertEquals(Element.WALL, field.getElement(1, 2));
		
		// upper right corner
		assertEquals(Element.WALL, field.getElement(10, 1));
		assertEquals(Element.WALL, field.getElement(11, 1));
		assertEquals(Element.WALL, field.getElement(11, 2));

		// bottom left corner
		assertEquals(Element.WALL, field.getElement(1, 10));
		assertEquals(Element.WALL, field.getElement(2, 11));
		assertEquals(Element.WALL, field.getElement(1, 11));
		
		// bottom right corner
		assertEquals(Element.WALL, field.getElement(11, 10));
		assertEquals(Element.WALL, field.getElement(11, 11));
		assertEquals(Element.WALL, field.getElement(10, 11));
		
	}
	
	@Test
	public void test_getElement(){
		assertEquals(Element.WALL, field.getElement(0,0));
	}
	
	@Test
	public void test_getActPlayer(){
		assertEquals(Element.PLAYER1, field.getActPlayer());
	}
	
	@Test
	public void test_changeActPlayer(){
		assertEquals(Element.PLAYER1, field.getActPlayer());
		field.changeActPlayer();
		assertEquals(Element.PLAYER2, field.getActPlayer());
		field.changeActPlayer();
		assertEquals(Element.PLAYER1, field.getActPlayer());
	}

}
