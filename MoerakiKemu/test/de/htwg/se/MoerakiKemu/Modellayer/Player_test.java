package de.htwg.se.MoerakiKemu.Modellayer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.MoerakiKemu.Modellayer.Player;


public class Player_test {
	Player player;

	@Before
	public void setUp(){
		player = new Player("alpha");
	}
	
	@Test
	public void test_getName_returnNotNullString(){
		assertEquals("alpha", player.getName());
	}
	
	@Test
	public void test_getPoints_returnTwo(){
		player.addPoints(2);
		assertEquals(2, player.getPoints());
	}
	
	@Test
	public void test_addPoints_negativeValueDoesNotLowerValue() {
		player.addPoints(-2);
		assertEquals(0, player.getPoints());
	}
}
