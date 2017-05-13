package de.htwg.se.moerakikemu.controller.impl.rule.msg;

import java.awt.Point;

public class MsgPossibleInput {
	private final Point position;
	private final boolean result;

	public MsgPossibleInput(final Point position,final boolean result){
		this.position = position;
		this.result = result;
	}
	
	public MsgPossibleInput(final Point position){
		this.position = position;
		this.result = false;
	}

	public Point getPosition() {
		return position;
	}

	public boolean getResult() {
		return result;
	}

}
