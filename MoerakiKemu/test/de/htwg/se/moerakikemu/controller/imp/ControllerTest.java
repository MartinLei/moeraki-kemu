package de.htwg.se.moerakikemu.controller.imp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.impl.Controller;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;
import de.htwg.se.moerakikemu.persistence.db4o.FieldDb4oDAO;

public class ControllerTest {
	IController controller;

	private final String PLAYER1NAME = "Andrew";
	private final String PLAYER2NAME = "Walter";
	
	private final IFieldDAO fieldDAO = new FieldDb4oDAO();
	@Before
	public void setUp() {
		controller = new Controller(fieldDAO);

		assertEquals(State.GET_FIRST_PLAYER_NAME, controller.getState());
		controller.setPlayer1Name(PLAYER1NAME);
		assertEquals(State.GET_SECOND_PLAYER_NAME, controller.getState());
		controller.setPlayer2Name(PLAYER2NAME);
		assertEquals(State.SET_START_DOT, controller.getState());
	}

	@Test
	public void test_newGame() {
		controller.setStartDot(new Point(5, 5));
		assertEquals(State.TURN_PLAYER1, controller.getState());

		controller.newGame();

		assertEquals(State.GET_FIRST_PLAYER_NAME, controller.getState());
		controller.setPlayer1Name(PLAYER1NAME);
		assertEquals(State.GET_SECOND_PLAYER_NAME, controller.getState());
		controller.setPlayer2Name(PLAYER2NAME);
		assertEquals(State.SET_START_DOT, controller.getState());
	}

	@Test
	public void test_newGameQickStart() {
		controller.setStartDot(new Point(5, 5));
		assertEquals(State.TURN_PLAYER1, controller.getState());

		controller.newGameQuickStart();

		assertEquals(State.TURN_PLAYER1, controller.getState());
	}

	@Test
	public void test_setStartDot() {
		assertEquals(State.SET_START_DOT, controller.getState());
		assertFalse(controller.setStartDot(null));
		assertEquals(State.SET_START_DOT, controller.getState());
		assertFalse(controller.setStartDot(new Point(0, 0)));
		assertEquals(State.SET_START_DOT, controller.getState());

		assertTrue(controller.setStartDot(new Point(5, 5)));
		assertEquals(State.TURN_PLAYER1, controller.getState());
	}

	@Test
	public void test_setDot() {
		assertTrue(controller.setStartDot(new Point(5, 5)));
		assertEquals(State.TURN_PLAYER1, controller.getState());

		assertFalse(controller.setDot(null));
		assertEquals(State.TURN_PLAYER1, controller.getState());
		assertTrue(controller.setDot(new Point(0, 0)));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		assertTrue(controller.setDot(new Point(0, 1)));
		assertEquals(State.TURN_PLAYER1, controller.getState());

		assertFalse(controller.setDot(new Point(0, 1)));
		assertEquals(State.TURN_PLAYER1, controller.getState());
	}

	@Test
	public void test_player1_getPointr() {
		assertTrue(controller.setStartDot(new Point(5, 5)));
		
		assertTrue(controller.setDot(new Point(0, 0)));
		assertTrue(controller.setDot(new Point(0, 1)));
		assertTrue(controller.setDot(new Point(1, 0)));
		assertTrue(controller.setDot(new Point(3, 0)));
		assertTrue(controller.setDot(new Point(1, 1)));

		assertEquals(1, controller.getPlayer1Point());
		assertEquals(0, controller.getPlayer2Point());
	}

//TODO fix rules for set a row and win
//	@Test
//	public void test_getLineOccupied() {
//		assertTrue(controller.setStartDot(new Point(5, 5)));
//		
//		assertTrue(controller.setDot(new Point(0, 0)));
//		assertTrue(controller.setDot(new Point(0, 1)));
//		assertTrue(controller.setDot(new Point(1, 0)));
//		assertTrue(controller.setDot(new Point(0, 2)));
//		assertTrue(controller.setDot(new Point(2, 0)));
//		assertTrue(controller.setDot(new Point(0, 3)));
//		assertTrue(controller.setDot(new Point(3, 0)));
//		assertTrue(controller.setDot(new Point(0, 4)));
//		assertTrue(controller.setDot(new Point(4, 0)));
//		assertTrue(controller.setDot(new Point(0, 5)));
//		assertTrue(controller.setDot(new Point(5, 0)));
//		assertTrue(controller.setDot(new Point(0, 6)));
//		assertTrue(controller.setDot(new Point(6, 0)));
//		assertTrue(controller.setDot(new Point(0, 7)));
//		assertTrue(controller.setDot(new Point(7, 0)));
//		assertTrue(controller.setDot(new Point(0, 8)));
//		assertTrue(controller.setDot(new Point(8, 0)));
//		assertTrue(controller.setDot(new Point(0, 9)));
//		assertTrue(controller.setDot(new Point(9, 0)));
//		assertTrue(controller.setDot(new Point(0, 10)));
//		assertTrue(controller.setDot(new Point(10, 0)));
//		assertTrue(controller.setDot(new Point(0, 11)));
//		assertTrue(controller.setDot(new Point(11, 0)));
//		
//		assertEquals(State.PLAYER1_WON, controller.getState());
//	}

	@Test
	public void test_getEdgeLength_sixAsInitialized() {
		assertEquals(controller.getEdgeLength(), 12);
	}

	@Test
	public void test_game_finished() {
		assertEquals(State.SET_START_DOT, controller.getState());
		controller.setStartDot(new Point(5, 5));
		assertEquals(State.TURN_PLAYER1, controller.getState());

		for (int i = 0; i < controller.getEdgeLength(); i++) {
			for (int j = 0; j < controller.getEdgeLength(); j++) {
				controller.setDot(new Point(i, j));
			}
		}

		assertEquals(State.GAME_FINISHED, controller.getState());
	}

	@Test
	public void test_player1_won() {
		assertEquals(State.SET_START_DOT, controller.getState());
		controller.setStartDot(new Point(5, 5));
		assertEquals(State.TURN_PLAYER1, controller.getState());

		controller.setDot(new Point(1, 1));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		controller.setDot(new Point(1, 4));
		assertEquals(State.TURN_PLAYER1, controller.getState());
		controller.setDot(new Point(1, 2));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		controller.setDot(new Point(1, 5));
		assertEquals(State.TURN_PLAYER1, controller.getState());
		controller.setDot(new Point(2, 1));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		controller.setDot(new Point(2, 4));
		assertEquals(State.TURN_PLAYER1, controller.getState());
		controller.setDot(new Point(2, 2));
		assertEquals(State.PLAYER1_WON, controller.getState());
	}

	@Test
	public void test_player2_won() {
		assertEquals(State.SET_START_DOT, controller.getState());
		controller.setStartDot(new Point(5, 5));
		assertEquals(State.TURN_PLAYER1, controller.getState());

		controller.setDot(new Point(1, 1));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		controller.setDot(new Point(1, 4));
		assertEquals(State.TURN_PLAYER1, controller.getState());
		controller.setDot(new Point(1, 2));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		controller.setDot(new Point(1, 5));
		assertEquals(State.TURN_PLAYER1, controller.getState());
		controller.setDot(new Point(2, 1));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		controller.setDot(new Point(2, 4));
		assertEquals(State.TURN_PLAYER1, controller.getState());
		controller.setDot(new Point(2, 3));
		assertEquals(State.TURN_PLAYER2, controller.getState());
		controller.setDot(new Point(2, 5));
		assertEquals(State.PLAYER2_WON, controller.getState());
	}

	@Test
	public void test_getState_returnsStates() {
		// Player occupies a square.
		controller.setStartDot(new Point(5, 5));
		assertTrue(controller.setDot(new Point(1, 1)));
		assertTrue(controller.setDot(new Point(2, 3)));
		assertTrue(controller.setDot(new Point(1, 2)));
		assertTrue(controller.setDot(new Point(3, 2)));
		assertTrue(controller.setDot(new Point(2, 1)));
		assertTrue(controller.setDot(new Point(3, 4)));
		assertTrue(controller.setDot(new Point(2, 2)));

		assertEquals(State.PLAYER1_WON, controller.getState());
	}

	@Test
	public void test_quitGamee() {
		controller.quitGame();
		assertEquals(State.EXIT_GAME, controller.getState());
	}

	@Test
	public void test_getPlayer1Name() {
		assertEquals(PLAYER1NAME, controller.getPlayer1Name());
	}

	@Test
	public void test_getPlayer2Name() {
		assertEquals(PLAYER2NAME, controller.getPlayer2Name());
	}

	@Test
	public void test_getPlayer1Point() {
		assertEquals(0, controller.getPlayer1Point());
	}

	@Test
	public void test_getPlayer2Point() {
		assertEquals(0, controller.getPlayer2Point());
	}
	
	@Test
	public void test_getIsOccupiedBy(){
		controller.setStartDot(new Point(5, 5));
		assertTrue(controller.setDot(new Point(1, 1)));
		assertEquals(Element.PLAYER1, controller.getFieldElement(1, 1));
	}
}
