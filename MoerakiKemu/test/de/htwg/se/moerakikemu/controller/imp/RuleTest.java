package de.htwg.se.moerakikemu.controller.imp;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.controller.impl.Rule;
import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Cell;
import de.htwg.se.moerakikemu.model.impl.Field;

public class RuleTest {

	IField field;
	Rule rule;

	@Before
	public void setUp() throws Exception {
		field = new Field();
		rule = new Rule();
	}

	@Test
	public void test_isCorrectPosition() {
		assertFalse(rule.isPositionPossibleInput(new Point(-1, -1)));
		assertFalse(rule.isPositionPossibleInput(new Point(-1, 14)));
		assertFalse(rule.isPositionPossibleInput(new Point(14, -1)));
		assertFalse(rule.isPositionPossibleInput(new Point(5, -1)));
		assertFalse(rule.isPositionPossibleInput(new Point(14, 14)));
		assertFalse(rule.isPositionPossibleInput(new Point(5, 14)));
		assertFalse(rule.isPositionPossibleInput(new Point(14, 5)));

		assertFalse(rule.isPositionPossibleInput(new Point(5, 4)));
		assertFalse(rule.isPositionPossibleInput(new Point(4, 5)));
		assertTrue(rule.isPositionPossibleInput(new Point(4, 4)));
		assertTrue(rule.isPositionPossibleInput(new Point(5, 5)));

		assertTrue(rule.isPositionPossibleInput(new Point(1, 11)));
		assertTrue(rule.isPositionPossibleInput(new Point(11, 1)));

		assertFalse(rule.isPositionPossibleInput(new Point(5, 13)));
		assertFalse(rule.isPositionPossibleInput(new Point(13, 5)));
		assertFalse(rule.isPositionPossibleInput(new Point(0, 13)));
		assertFalse(rule.isPositionPossibleInput(new Point(13, 0)));
		assertFalse(rule.isPositionPossibleInput(new Point(0, 5)));
		assertFalse(rule.isPositionPossibleInput(new Point(5, 0)));
	}

	@Test
	public void test_getShiftedPositions() {
		Point position = new Point(5, 5);
		List<Point> resultShiftExpectet = new ArrayList<>();
		resultShiftExpectet.add(new Point(5, 3));
		resultShiftExpectet.add(new Point(4, 4));
		resultShiftExpectet.add(new Point(6, 4));
		resultShiftExpectet.add(new Point(3, 5));
		resultShiftExpectet.add(new Point(5, 5));
		resultShiftExpectet.add(new Point(7, 5));
		resultShiftExpectet.add(new Point(4, 6));
		resultShiftExpectet.add(new Point(6, 6));
		resultShiftExpectet.add(new Point(5, 7));

		List<Point> resultShift = rule.getShiftedPositions(rule.getTemplateCells(), position);
		assertTrue(resultShift.containsAll(resultShiftExpectet));
		assertTrue(resultShiftExpectet.containsAll(resultShift));
	}

	@Test
	public void test_getShiftedPositions_Wall() {
		Point position = new Point(1, 5);
		List<Point> resultShiftExpectet = new ArrayList<>();
		resultShiftExpectet.add(new Point(1, 3));
		resultShiftExpectet.add(new Point(0,4));
		resultShiftExpectet.add(new Point(2, 4));
		resultShiftExpectet.add(null);
		resultShiftExpectet.add(new Point(1, 5));
		resultShiftExpectet.add(new Point(3, 5));
		resultShiftExpectet.add(new Point(0,6));
		resultShiftExpectet.add(new Point(2, 6));
		resultShiftExpectet.add(new Point(1, 7));

		List<Point> resultShift = rule.getShiftedPositions(rule.getTemplateCells(), position);
		assertTrue(resultShift.containsAll(resultShiftExpectet));
		assertTrue(resultShiftExpectet.containsAll(resultShift));
	}
}
