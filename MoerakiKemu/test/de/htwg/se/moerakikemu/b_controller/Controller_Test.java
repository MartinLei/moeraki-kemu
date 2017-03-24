package de.htwg.se.moerakikemu.b_controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.controller.*;
import de.htwg.se.moerakikemu.controller.controllerimpl.*;

public class Controller_Test {
	IController controller;
	IControllerPlayer playerController;
	
	@Before
	public void setUp(){
		playerController = new ControllerPlayer();
		controller = new Controller(6, playerController);
		playerController.setName("Player1", "Player2");
	}
	
	@Test
	public void test_occupy_returnNullIfOccupied(){
		
		controller.occupy(3, 3);
		for(int i = 0; i < 4; i++){
			for(int j = 0; j <= 5; j++){
				if(i == 3 && j == 3) {
					assertEquals(-1, controller.occupy(i, j));
				} else {
					assertEquals(0, controller.occupy(i, j));
				}
			}
		}	
		
		assertEquals(0, controller.occupy(4, 0));
		assertEquals(0, controller.occupy(4, 2));
		assertEquals(0, controller.occupy(4, 1));
		assertEquals(0, controller.occupy(4, 3));
		assertEquals(0, controller.occupy(5, 0));
		assertEquals(0, controller.occupy(5, 2));
		assertEquals(0, controller.occupy(5, 1));
		assertEquals(0, controller.occupy(5, 3));
		assertEquals(0, controller.occupy(4, 4));
		assertEquals(0, controller.occupy(4, 5));
		assertEquals(0, controller.occupy(5, 4));
		assertEquals(0, controller.occupy(5, 5));
		
		for(int i = 0; i <= 5; i++){
			for(int j = 0; j <= 5; j++){
					assertEquals(-1, controller.occupy(i, j));
			}
		}
	}
	
	
	@Test
	public void test_getPointsOfPlayer_returnPointsofCurrentPlayer(){
		controller = new Controller(5, playerController);
		
		controller.occupy(1, 1);
		controller.occupy(3, 3);
		controller.occupy(1, 2);
		controller.occupy(3, 4);
		controller.occupy(2, 1);
		controller.occupy(2, 2);
		
		assertEquals("Player1", playerController.getPlayer1Name());
		assertEquals("Player2", playerController.getPlayer2Name());
	}
	
	@Test
	public void test_selectNextPlayer_wasPlayerOneIsPlayerTwoNextPlayerOne() {
		assertEquals(playerController.getCurrentPlayerName(), "StartDot");
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), "Player1");
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), "Player2");
		playerController.selectNextPlayer();
		assertEquals(playerController.getCurrentPlayerName(), "Player1");
	}
	
	@Test
	public void test_setName_isPlayer1Player2() {
		playerController.setName("Player1", "Player2");
		assertEquals(playerController.getPlayer1Name(), "Player1");
		assertEquals(playerController.getPlayer2Name(), "Player2");
	}
	
	@Test
	public void test_getEdgeLength_sixAsInitialized() {
		assertEquals(controller.getEdgeLength(), 6);
	}
	
	@Test
	public void test_getQuit_gamefinished(){
		controller = new Controller(5, playerController);
		assertFalse(controller.testIfWinnerExists());
		
		controller.occupy(3, 3);	// Set start Spot
		
		controller.occupy(0, 0);
		controller.occupy(1, 1);
		controller.occupy(0, 1);
		controller.occupy(1, 2);
		controller.occupy(1, 0);
		
		assertEquals("Player1", controller.getWinner());
		
		playerController = new ControllerPlayer();
		playerController.setName("Player1", "Player2");
		controller = new Controller(5, playerController);
		assertEquals("", controller.getWinner());

		controller.occupy(2, 2);	// Set start Spot
		
		controller.occupy(4, 4);
		controller.occupy(0, 0);
		controller.occupy(3, 3);
		controller.occupy(1, 0);
		controller.occupy(2, 3);
		controller.occupy(0, 1);
		controller.occupy(1, 1);
		
		assertEquals("Player2", controller.getWinner());
	}
	
	@Test
	public void test_getIsOccupiedByPlayer_ifOccupiedFromAPlayer(){
		controller = new Controller(5, playerController);
		
		controller.occupy(2, 2);	// Set start Spot
		controller.occupy(0, 0);
		
		String a = controller.getIsOccupiedByPlayer(0, 0); //0,0 because occupy get the direkt input from a player, so there is a -1, -1 needed
		assertEquals("Player1", a);
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
		playerController.setName("", "");
		assertEquals(State.QUERY_PLAYER_NAME, controller.getState());
		playerController.setName("A", "");
		assertEquals(State.QUERY_PLAYER_NAME, controller.getState());
		
		// Test normal occupation state
		playerController.setName("A", "B");
		assertEquals(State.PLAYER_OCCUPIED, controller.getState());
		
		// Player occupies a square.
		controller.occupy(3, 3);	// Set start Spot
		controller.occupy(1, 1);controller.occupy(2, 3);
		controller.occupy(1, 2);controller.occupy(3, 2);
		controller.occupy(2, 1);controller.occupy(3, 4);
		controller.occupy(2, 2);controller.occupy(4, 3);
		assertEquals(State.PLAYER_WON, controller.getState());
		
		// SetEnd
		controller.setEnd(true);
		assertEquals(State.GAME_FINISHED, controller.getState());
	}
}
