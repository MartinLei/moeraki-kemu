package de.htwg.se.moerakikemu.controller.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Cell;
import de.htwg.se.moerakikemu.model.impl.Element;

public final class Rule {

	private static final List<Point> allowedStartPosition = new ArrayList<>();

	private static final List<Cell> testCells = new ArrayList<>();

	public Rule() {
		initAllowedStartPosition();
		initTestCell();
	}

	private void initAllowedStartPosition() {
		allowedStartPosition.add(new Point(6, 4));
		allowedStartPosition.add(new Point(5, 5));
		allowedStartPosition.add(new Point(7, 5));
		allowedStartPosition.add(new Point(4, 6));
		allowedStartPosition.add(new Point(6, 6));
		allowedStartPosition.add(new Point(8, 6));
		allowedStartPosition.add(new Point(5, 7));
		allowedStartPosition.add(new Point(7, 7));
		allowedStartPosition.add(new Point(6, 8));

	}

	private void initTestCell() {
		List<Point> shiftLeft = new ArrayList<>();
		shiftLeft.add(new Point(-1, 1));// top
		shiftLeft.add(new Point(-1, -1));// bottom
		shiftLeft.add(new Point(0, 0));// right
		shiftLeft.add(new Point(-2, 0));// left
		Point shiftLeftMiddel = new Point(-1, 0); // middle
		testCells.add(new Cell(shiftLeft, shiftLeftMiddel));

		List<Point> shiftRight = new ArrayList<>();
		shiftRight.add(new Point(1, -1));// top
		shiftRight.add(new Point(-1, 1));// bottom
		shiftRight.add(new Point(+2, 0));// right
		shiftRight.add(new Point(0, 0));// left
		Point shiftRightMiddel = new Point(1, 0); // middle
		testCells.add(new Cell(shiftRight, shiftRightMiddel));

		List<Point> shiftUp = new ArrayList<>();
		shiftUp.add(new Point(0, 0));// top
		shiftUp.add(new Point(0, 2));// bottom
		shiftUp.add(new Point(1, 1));// right
		shiftUp.add(new Point(-1, 1));// left
		Point shiftUpMiddel = new Point(0, 1); // middle
		testCells.add(new Cell(shiftUp, shiftUpMiddel));

		List<Point> shiftDown = new ArrayList<>();
		shiftDown.add(new Point(0, 2));// top
		shiftDown.add(new Point(0, 0));// bottom
		shiftDown.add(new Point(1, -1));// right
		shiftDown.add(new Point(-1, -1));// left
		Point shiftDownMiddel = new Point(0, -1); // middle
		testCells.add(new Cell(shiftDown, shiftDownMiddel));
	}

	public boolean isStartDotPosCorrect(Point position) {
		for (Point p : allowedStartPosition)
			if (p.equals(position))
				return true;

		return false;
	}

	/**
	 * tell if the position is a possible position for a player
	 * 
	 * @param position
	 * @return if the position is possible to set a dot
	 */
	public boolean isPositionPossibleInput(Point position) {
		if (position.x < 0 || position.x > 12 || position.y < 0 || position.y > 12)
			return false;

		if (!evenNumber(position.x) && evenNumber(position.y) || evenNumber(position.x) && !evenNumber(position.y))
			return false;

		return true;
	}

	private static boolean evenNumber(int number) {
		return number % 2 == 0;
	}

	public List<Cell> getTestCells() {
		return testCells;
	}

	public List<Point> getShiftedPositions(List<Point> shiftTemplate, Point position) {
		List<Point> shifted = new ArrayList<>();
		for (Point shift : shiftTemplate) {
			Point newPosition = new Point(position.x + shift.x, position.y + shift.y);

			if (isPositionPossibleInput(newPosition))
				shifted.add(newPosition);
		}
		return shifted;
	}

}
