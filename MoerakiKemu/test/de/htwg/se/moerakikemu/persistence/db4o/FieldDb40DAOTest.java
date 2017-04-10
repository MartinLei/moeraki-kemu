package de.htwg.se.moerakikemu.persistence.db4o;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.Field;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;

public class FieldDb40DAOTest {

	private final static Point STARTDOT = new Point(6, 6);
	
	private IFieldDAO fieldDAO;
	private IField field;
	@Before
	public void setUp() {
		fieldDAO = new FieldDb4oDAO();
		field = new Field();
	}

	@Test
	public void test_save_load(){
		field.occupy(STARTDOT, Element.STARTDOT);
		fieldDAO.saveField(field);
		
		IField loadedFiled = fieldDAO.loadField();
		Element element = loadedFiled.getElement(STARTDOT);
		assertEquals(element,Element.STARTDOT);
	}
}
