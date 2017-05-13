package de.htwg.se.moerakikemu.controller.impl.rule.msg;

import java.awt.Point;
import java.util.List;

public class MsgGetCells {
	private final List<List<Integer>> result;

	public MsgGetCells(final List<List<Integer>> cells) {
		this.result = cells;
	}

	public MsgGetCells() {
		this.result = null;
	}


	public List<List<Integer>> getResult() {
		return result;
	}

}
