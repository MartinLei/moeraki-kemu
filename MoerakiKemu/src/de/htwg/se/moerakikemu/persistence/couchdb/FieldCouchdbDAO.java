package de.htwg.se.moerakikemu.persistence.couchdb;

import java.net.MalformedURLException;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.model.impl.Element;
import de.htwg.se.moerakikemu.model.impl.Field;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;

public class FieldCouchdbDAO implements IFieldDAO {

	private CouchDbConnector db = null;
	private static final String DB_ADRESS = "http://lenny2.in.htwg-konstanz.de:5984";
	private static final String DB_NAME = "moerakikemu_db";
	private static final String ID = "field";

	public FieldCouchdbDAO() {
		HttpClient client = null;
		try {
			client = new StdHttpClient.Builder().url(DB_ADRESS).build();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		CouchDbInstance dbInstance = new StdCouchDbInstance(client);
		db = dbInstance.createConnector(DB_NAME, true);
	}

	@Override
	public void saveField(IField field) {
		PersistentField persistentFieldLoad = getPersistenField(ID);

		if (persistentFieldLoad == null) {
			PersistentField persistentField = generatePersitentField(field);
			db.create(ID, persistentField);
		} else {
			persistentFieldLoad = generatePersitentField(field, persistentFieldLoad);
			db.update(persistentFieldLoad);
		}

	}

	private PersistentField generatePersitentField(IField field) {
		PersistentField persistentField = new PersistentField();
		return generatePersitentField(field, persistentField);
	}

	private PersistentField generatePersitentField(IField field, PersistentField persistenField) {
		List<Element> mapElement = field.getField();
		State state = field.getState();
		Element actualPlayer = field.getCurrentPlayer();

		persistenField.setMapElement(mapElement);
		persistenField.setState(state);
		persistenField.setActualPlayer(actualPlayer);

		return persistenField;
	}

	@Override
	public IField loadField() {
		PersistentField persistentField = getPersistenField(ID);

		if (persistentField == null)
			return null;

		return createFieldClass(persistentField);
	}

	private PersistentField getPersistenField(String id) {
		PersistentField persistentField = db.find(PersistentField.class, ID);

		if (persistentField == null)
			return null;

		return persistentField;
	}

	private IField createFieldClass(PersistentField persistentField) {
		List<Element> mapElement = persistentField.getMapElement();
		State state = persistentField.getState();
		Element actualPlayer = persistentField.getActualPlayer();
		return new Field(mapElement, state, actualPlayer);
	}
}
