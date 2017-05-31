package de.htwg.se.moerakikemu.persistence.hibernate;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.Field;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.persistence.hibernate.util.sessionFactory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import de.htwg.se.moerakikemu.persistence.hibernate.util.TransactionWithReturn;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;
import de.htwg.se.moerakikemu.persistence.hibernate.util.TransactionNoReturn;

public class HibernateDAO implements IFieldDAO {
	
	public void saveField(IField field){
		makeTransaction((session)->{
			PersistentField persistentField = copyField(field);
			session.save(persistentField);
		});
	}
	
	public IField loadField(){
		return makeTransaction((session)->{
			PersistentField persistentField = session.get(PersistentField.class, "field");
			return createField(persistentField);
		});
	}
	
	private PersistentField copyField(IField field){	
		
		List<Element> mapElement = field.getField();
		State state = field.getState();
		Element actualPlayer = field.getCurrentPlayer();
		
		return new PersistentField(
				"field",
				mapElement,
				state,
				actualPlayer);
	}
	
	private void makeTransaction(TransactionNoReturn trans){
		Session session;
		Transaction tx = null;
		try {
		session = sessionFactory.getSessionFactory().openSession();
		tx = session.beginTransaction();
		trans.toTransact(session);
		tx.commit();
		}
		catch (Exception e) {
			if(tx != null) tx.rollback();
			throw e;
		}
	}
	
	private IField makeTransaction(TransactionWithReturn trans){
		IField field;
		Session session;
		Transaction tx = null;
		try {
		session = sessionFactory.getSessionFactory().openSession();
		tx = session.beginTransaction();
		field = trans.toTransact(session);
		tx.commit();
		}
		catch (Exception e) {
			if(tx != null) tx.rollback();
			throw e;
		}
		return field;
	}
	
	private IField createField(PersistentField field){
		List<Element> mapElement = field.getMapElement();
		State state = field.getState();
		Element actualPlayer = field.getActualPlayer();
		return new Field(mapElement, state, actualPlayer);
	}
}
