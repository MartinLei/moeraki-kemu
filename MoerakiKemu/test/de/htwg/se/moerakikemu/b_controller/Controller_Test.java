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

	@Before
	public void setUp() {
		playerController = new ControllerPlayer();
		controller = new Controller(12, playerController);
		controller.setPlayer1Name("Andrew");
		controller.setPlayer2Name("Walter");
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
	}

	@Test
	public void test_getPointsOfPlayer_returnPointsofCurrentPlayer() {
		controller = new Controller(5, playerController);

		controller.occupy(1, 1);
		controller.occupy(3, 3);
		controller.occupy(1, 2);
		controller.occupy(3, 4);
		controller.occupy(2, 1);
		controller.occupy(2, 2);

		assertEquals("Andrew", playerController.getPlayer1Name());
		assertEquals("Walter", playerController.getPlayer2Name());
	}

	@Test
	public void test_selectNextPlayer_wasPlayerOneIsPlayerTwoNextPlayerOne() {
		assertEquals(playerController.getCurrentPlayerName(), "StartDot");
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), "Andrew");
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), "Walter");
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), "Andrew");
	}

	@Test
	public void test_setName_isPlayer1Player2() {
		playerController.setPlayer1Name("Andrew");
		playerController.setPlayer2Name("Walter");
		assertEquals(playerController.getPlayer1Name(), "Andrew");
		assertEquals(playerController.getPlayer2Name(), "Walter");
	}

	@Test
	public void test_getEdgeLength_sixAsInitialized() {
		assertEquals(controller.getEdgeLength(), 12);
	}

	@Test
	public void test_getQuit_gamefinished() {
		controller = new Controller(5, playerController);
		assertFalse(controller.testIfWinnerExists());

		controller.occupy(3, 3); // Set start Spot

		controller.occupy(0, 0);
		controller.occupy(1, 1);
		controller.occupy(0, 1);
		controller.occupy(1, 2);
		controller.occupy(1, 0);
		assertEquals("Andrew", controller.getWinner());

		playerController = new ControllerPlayer();
		playerController.setPlayer1Name("Andrew");
		playerController.setPlayer2Name("Walter");
		controller = new Controller(5, playerController);
		assertEquals("", controller.getWinner());

		controller.occupy(2, 2); // Set start Spot

		controller.occupy(4, 4);
		controller.occupy(0, 0);
		controller.occupy(3, 3);
		controller.occupy(1, 0);
		controller.occupy(2, 3);
		controller.occupy(0, 1);
		controller.occupy(1, 1);

		assertEquals("Walter", controller.getWinner());
	}

	@Test
	public void test_getIsOccupiedByPlayer_ifOccupiedFromAPlayer() {
		controller = new Controller(5, playerController);

		controller.occupy(2, 2); // Set start Spot
		controller.occupy(0, 0);

		// 0,0 because occupy get the direkt input from a player, so there is a
		// -1, -1 needed
		String a = controller.getIsOccupiedByPlayer(0, 0);
		assertEquals("Andrew", a);
	}

	@Test
	public void test_newGame_resetController() {
		controller.newGame();
		assertEquals(false, controller.testIfEnd());
		assertEquals(false, controller.testIfWinnerExists());
		assertTrue("".equals(controller.getWinner()));
	}

	@Test
	public void test_getState_returnsStates() {
		playerController = new ControllerPlayer();
		controller = new Controller(6, playerController);

		// Test player names
		assertEquals(State.GET_FIRST_PLAYER_NAME, controller.getState());
		controller.setPlayer1Name("Andrew");
		assertEquals(State.GET_SECOND_PLAYER_NAME, controller.getState());
		controller.setPlayer2Name("Walter");
		assertEquals(State.SET_START_DOT, controller.getState());

		// Player occupies a square.
		controller.occupy(3, 3); // Set start Spot
		controller.occupy(1, 1);
		controller.occupy(2, 3);
		controller.occupy(1, 2);
		controller.occupy(3, 2);
		controller.occupy(2, 1);
		controller.occupy(3, 4);
		controller.occupy(2, 2);
		controller.occupy(4, 3);
		assertEquals(State.PLAYER_WON, controller.getState());

	}

	@Test
	public void test_setEnde() {
		controller.setEnd(true);
		assertEquals(State.GAME_FINISHED, controller.getState());
	}

	@Test
	public void test_quitGamee() {
		controller.quitGame();
		;
		assertEquals(State.EXIT_GAME, controller.getState());
	}
}
