package de.htwg.se.moerakikemu.persistence.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.State;

public class PersistentField extends CouchDbDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3282104756860525664L;

	/**
	 * @TypeDiscriminator is used to mark properties that makes this class's
	 *                    documents unique in the database.
	 */
	@TypeDiscriminator
	public String id;

	public transient List<Element> mapElement= new ArrayList<>();
	public State state = State.SET_START_DOT;
	public Element actualPlayer = Element.NONE;

	public PersistentField(String id,List<Element> mapElement,State state, Element actualPlayer){
		this.id = id;
		this.mapElement = mapElement;
		this.actualPlayer = actualPlayer;
		this.state = state;
	}
	public PersistentField() {
	}
	
	public List<Element> getMapElement() {
		return mapElement;
	}
	public State getState() {
		return state;
	}
	public Element getActualPlayer() {
		return actualPlayer;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMapElement(List<Element> mapElement) {
		this.mapElement = mapElement;
	}
	public void setState(State state) {
		this.state = state;
	}
	public void setActualPlayer(Element actualPlayer) {
		this.actualPlayer = actualPlayer;
	}

}
