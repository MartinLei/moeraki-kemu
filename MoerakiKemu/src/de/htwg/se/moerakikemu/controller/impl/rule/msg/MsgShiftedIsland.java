package de.htwg.se.moerakikemu.controller.impl.rule.msg;

import java.awt.Point;
import java.util.List;

public class MsgShiftedIsland {

	private final Point position;
	private final List<Point> result;

	public MsgShiftedIsland(final Point position) {
		this.position = position;
		this.result = null;
	}
	
	public MsgShiftedIsland(final Point position, final List<Point> result) {
		this.position = position;
		this.result = result;
	}

	public Point getPosition() {
		return position;
	}

	public List<Point> getResult() {
		return result;
	}

}
