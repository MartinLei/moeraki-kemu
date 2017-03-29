package de.htwg.se.moerakikemu.controller.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.htwg.se.moerakikemu.model.impl.Cell;

public final class Rule {

	private final List<Point> allowedStartPosition;
	private final List<Point> templateCells;
	private final List<Point> templateIslands;
	private final List<List<Integer>> cells;

	public Rule() {
		allowedStartPosition = new ArrayList<>();
		templateCells = new ArrayList<>();
		templateIslands = new ArrayList<>();
		cells = new ArrayList<>();

		initAllowedStartPosition();
		initTemplateCell();
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

	private void initTemplateCell() {

		templateCells.add(new Point(0, -2)); // 0
		templateCells.add(new Point(-1, -1)); // 1
		templateCells.add(new Point(1, -1)); // 2
		templateCells.add(new Point(-2, 0)); // 3
		templateCells.add(new Point(0, 0)); // 4
		templateCells.add(new Point(2, 0)); // 5
		templateCells.add(new Point(-1, 1)); // 6
		templateCells.add(new Point(1, 1)); // 7
		templateCells.add(new Point(0, 2)); // 8

		templateIslands.add(new Point(-1, 0)); // left
		templateIslands.add(new Point(0, -1)); // up
		templateIslands.add(new Point(1, 0)); // right
		templateIslands.add(new Point(0, 1)); // down

		List<Integer> leftCell = new ArrayList<>();
		leftCell.add(3);
		leftCell.add(1);
		leftCell.add(4);
		leftCell.add(6);
		cells.add(leftCell);

		List<Integer> upCell = new ArrayList<>();
		upCell.add(1);
		upCell.add(0);
		upCell.add(2);
		upCell.add(4);
		cells.add(upCell);

		List<Integer> rightCell = new ArrayList<>();
		rightCell.add(4);
		rightCell.add(2);
		rightCell.add(5);
		rightCell.add(7);
		cells.add(rightCell);

		List<Integer> bottomCell = new ArrayList<>();
		bottomCell.add(6);
		bottomCell.add(4);
		bottomCell.add(7);
		bottomCell.add(8);
		cells.add(bottomCell);

	}

	public List<List<Integer>> getCells() {
		return cells;
	}

	public List<Point> getTemplateIslands() {
		return templateIslands;
	}

	public List<Point> getTemplateCells() {
		return templateCells;
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

	public boolean isPositionPossibleIsland(Point position) {
		if (position.x < 0 || position.x > 12 || position.y < 0 || position.y > 12)
			return false;

		if (evenNumber(position.x) && evenNumber(position.y) || !evenNumber(position.x) && !evenNumber(position.y))
			return false;

		return true;
	}

	private static boolean evenNumber(int number) {
		return number % 2 == 0;
	}

	public List<Point> getShiftedPositions(List<Point> shiftTemplate, Point position) {
		List<Point> shifted = new ArrayList<>();
		for (Point shift : shiftTemplate) {
			Point newPosition = new Point(position.x + shift.x, position.y + shift.y);

			if (isPositionPossibleInput(newPosition))
				shifted.add(newPosition);
			else
				shifted.add(null);
		}
		return shifted;
	}

	public List<Point> getShiftedPositionsIsland(List<Point> shiftTemplate, Point position) {
		List<Point> shifted = new ArrayList<>();
		for (Point shift : shiftTemplate) {
			Point newPosition = new Point(position.x + shift.x, position.y + shift.y);

			shifted.add(newPosition);

		}
		return shifted;
	}

}
