package de.htwg.se.moerakikemu.model.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	private static final String SET_START_DOT = "Setzen sie den Start Stein";
	private static final String TURN_PLAYER1 = "Player1 sie sind dran";
	private static final String TURN_PLAYER2 = "Player2 sie sind dran";
	private static final String WON = "Spiel beendet";
	private static final String GAME_FINISHED = "Spiel unentschieden beendet";
	private static final String EXIT_GAME = "Adios";
	
	@Before
	public void setUp() {
	
	}

	@Test
	public void test_toString() {
		assertEquals(SET_START_DOT, State.SET_START_DOT.toString());
		assertEquals(TURN_PLAYER1, State.TURN_PLAYER1.toString());
		assertEquals(TURN_PLAYER2, State.TURN_PLAYER2.toString());
		assertEquals(WON, State.WON.toString());
		assertEquals(GAME_FINISHED, State.GAME_FINISHED.toString());
		assertEquals(EXIT_GAME, State.EXIT_GAME.toString());

	}
	
}
