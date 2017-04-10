package de.htwg.se.moerakikemu.persistence.db4o;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;

/**
 * DB implementation with db4O
 *
 */
public class FieldDb4oDAO implements IFieldDAO {
	private static final Logger LOGGER = (Logger) LogManager.getLogger(FieldDb4oDAO.class);

	private ObjectContainer db;

	private void openDB() {
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "field.data");
	}

	private void closeDB() {
		db.close();
	}

	@Override
	public void saveField(final IField field) {
		openDB();

		List<IField> oldSavedField = db.queryByExample(IField.class);

		if (oldSavedField.size() != 0)
			db.delete(oldSavedField.get(0));

		db.store(field);
		LOGGER.info("SAVE in db");

		closeDB();
	}

	@Override
	public IField loadField() {
		openDB();

		List<IField> fields = db.queryByExample(IField.class);
		IField field = null;
		
		if (fields.size() > 0) {
			LOGGER.info("LOAD from db");
			field = fields.get(0);
			field = (IField) copy(field);
		}

		closeDB();

		return field;
	}

	/**
	 * deep copy of an object source
	 * :http://javatechniques.com/blog/faster-deep-copies-of-java-objects/
	 * 
	 * @param orig
	 * @return
	 */
	public static Object copy(Object orig) {
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return obj;
	}

}
