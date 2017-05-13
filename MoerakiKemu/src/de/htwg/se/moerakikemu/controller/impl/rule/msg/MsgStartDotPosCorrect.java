package de.htwg.se.moerakikemu.controller.impl.rule.msg;

import java.awt.Point;

public class MsgStartDotPosCorrect {
	private final Point position;
	private final boolean result;

	public MsgStartDotPosCorrect(final Point position,final boolean result){
		this.position = position;
		this.result = result;
	}
	
	public MsgStartDotPosCorrect(final Point position){
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
