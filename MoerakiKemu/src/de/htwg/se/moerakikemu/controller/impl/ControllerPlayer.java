package de.htwg.se.moerakikemu.controller.impl;

import com.google.inject.Singleton;

import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.model.IPlayer;
import de.htwg.se.moerakikemu.model.impl.Player;

@Singleton
public class ControllerPlayer implements IControllerPlayer {
	private IPlayer player1;
	private IPlayer player2;
	private IPlayer startDot;

	private IPlayer currentPlayer;

	public ControllerPlayer() {
		player1 = new Player();
		player2 = new Player();
		startDot = new Player();
		currentPlayer = startDot;
		startDot.setName("StartDot");
	}

	@Override
	public void newGame() {
		player1.refreshPoints();
		player2.refreshPoints();
		currentPlayer = startDot;
	}

	@Override
	public void setName(String player1name, String player2name) {
		player1.setName(player1name);
		player2.setName(player2name);
	}

	@Override
	public String getPlayer1Name() {
		return player1.getName();
	}

	@Override
	public String getPlayer2Name() {
		return player2.getName();
	}

	@Override
	public int getPlayer1Points() {
		return player1.getPoints();
	}

	@Override
	public int getPlayer2Points() {
		return player2.getPoints();
	}

	@Override
	public void addAPointPlayer1() {
		player1.addPoints(1);
	}

	@Override
	public void addAPointPlayer2() {
		player2.addPoints(1);
	}

	@Override
	public void selectNextPlayer() {
		if (currentPlayer == player1) {
			currentPlayer = player2;
		} else if (currentPlayer == player2) {
			currentPlayer = player1;
		} else {
			currentPlayer = player1;
		}
	}

	@Override
	public boolean startDotSet() {
		if ("StartDot".equals(currentPlayer.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public String getCurrentPlayerName() {
		return currentPlayer.getName();
	}

	@Override
	public void setPlayer1Name(String name) {
		player1.setName(name);
	}

	@Override
	public void setPlayer2Name(String name) {
		player2.setName(name);
	}

}
