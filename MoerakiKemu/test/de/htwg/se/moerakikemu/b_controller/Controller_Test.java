package de.htwg.se.moerakikemu.b_controller;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.controller.*;
import de.htwg.se.moerakikemu.controller.controllerimpl.*;

public class Controller_Test {
	IController controller;
	IControllerPlayer playerController;

	private final String PLAYER1NAME = "Andrew";
	private final String PLAYER2NAME = "Walter";
	
	@Before
	public void setUp() {
		playerController = new ControllerPlayer();
		controller = new Controller(12, playerController);
		controller.setPlayer1Name(PLAYER1NAME);
		controller.setPlayer2Name(PLAYER2NAME);
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
	public void test_getPointsOfPlayer_returnPointsofCurrentPlayer() {
		controller = new Controller(5, playerController);

		controller.setDot(new Point(1,1));
		controller.setDot(new Point(3,3));
		controller.setDot(new Point(1,2));
		controller.setDot(new Point(3,4));
		controller.setDot(new Point(2,1));
		controller.setDot(new Point(2,2));

		assertEquals(PLAYER1NAME, playerController.getPlayer1Name());
		assertEquals(PLAYER2NAME, playerController.getPlayer2Name());
	}

	@Test
	public void test_selectNextPlayer_wasPlayerOneIsPlayerTwoNextPlayerOne() {
		assertEquals(playerController.getCurrentPlayerName(), "StartDot");
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), PLAYER1NAME);
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), PLAYER2NAME);
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), PLAYER1NAME);
	}

	@Test
	public void test_setName_isPlayer1Player2() {
		playerController.setPlayer1Name(PLAYER1NAME);
		playerController.setPlayer2Name(PLAYER2NAME);
		assertEquals(playerController.getPlayer1Name(), PLAYER1NAME);
		assertEquals(playerController.getPlayer2Name(), PLAYER2NAME);
	}

	@Test
	public void test_getEdgeLength_sixAsInitialized() {
		assertEquals(controller.getEdgeLength(), 12);
	}

	@Test
	public void test_getQuit_gamefinished() {
		controller = new Controller(5, playerController);
		assertNotEquals(controller.getState(), State.PLAYER_WON);

		controller.setStartDot(new Point(3,3));

		controller.setDot(new Point(0,0));
		controller.setDot(new Point(1,1));
		controller.setDot(new Point(0,1));
		controller.setDot(new Point(1,2));
		controller.setDot(new Point(1,0));
		assertEquals(PLAYER1NAME, controller.getWinner());

		playerController = new ControllerPlayer();
		playerController.setPlayer1Name(PLAYER1NAME);
		playerController.setPlayer2Name(PLAYER2NAME);
		controller = new Controller(5, playerController);
		assertEquals("", controller.getWinner());

		controller.setStartDot(new Point(2, 2));

		controller.setDot(new Point(4,4));
		controller.setDot(new Point(0,0));
		controller.setDot(new Point(3,3));
		controller.setDot(new Point(1,0));
		controller.setDot(new Point(2,3));
		controller.setDot(new Point(0,1));
		controller.setDot(new Point(1,1));
		assertEquals(PLAYER2NAME, controller.getWinner());
	}

	@Test
	public void test_getIsOccupiedByPlayer_ifOccupiedFromAPlayer() {
		controller = new Controller(5, playerController);

		controller.setStartDot(new Point(2,2)); // Set start Spot
		controller.setDot(new Point(0,0));

		// 0,0 because occupy get the direkt input from a player, so there is a
		// -1, -1 needed
		String a = controller.getIsOccupiedByPlayer(0, 0);
		assertEquals(PLAYER1NAME, a);
	}

	@Test
	public void test_getState_returnsStates() {
		playerController = new ControllerPlayer();
		controller = new Controller(6, playerController);

		// Test player names
		assertEquals(State.GET_FIRST_PLAYER_NAME, controller.getState());
		controller.setPlayer1Name(PLAYER1NAME);
		assertEquals(State.GET_SECOND_PLAYER_NAME, controller.getState());
		controller.setPlayer2Name(PLAYER2NAME);
		assertEquals(State.SET_START_DOT, controller.getState());

		// Player occupies a square.
		controller.setStartDot(new Point(3,3));
		controller.setDot(new Point(1,1));
		controller.setDot(new Point(2,3));
		controller.setDot(new Point(1,2));
		controller.setDot(new Point(3,2));
		controller.setDot(new Point(2,1));
		controller.setDot(new Point(3,4));
		controller.setDot(new Point(2,2));
		controller.setDot(new Point(4,3));
		assertEquals(State.PLAYER_WON, controller.getState());

	}
	
	@Test
	public void test_quitGamee() {
		controller.quitGame();
		assertEquals(State.EXIT_GAME, controller.getState());
	}
	
	@Test
	public void test_getPlayer1Name(){
		assertEquals(PLAYER1NAME, controller.getPlayer1Name());
	}

	@Test
	public void test_getPlayer2Name(){
		assertEquals(PLAYER2NAME, controller.getPlayer2Name());
	}
	
	@Test
	public void test_getPlayer1Point(){
		assertEquals(0, controller.getPlayer1Point());
	}
	
	@Test
	public void test_getPlayer2Point(){
		assertEquals(0, controller.getPlayer2Point());
	}
}
