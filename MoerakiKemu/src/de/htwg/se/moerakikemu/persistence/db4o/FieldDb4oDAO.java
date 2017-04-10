package de.htwg.se.moerakikemu.persistence.db4o;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import de.htwg.se.moerakikemu.model.IField;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;

/**
 * DB implementation with db4O
 *
 */
public class FieldDb4oDAO implements IFieldDAO {

	private ObjectContainer db;

	public FieldDb4oDAO() {
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "field.data");
	}

	@Override
	public void saveField(final IField field) {
		List<IField> oldSavedField = db.queryByExample(IField.class);

		if (oldSavedField.size() != 0)
			db.delete(oldSavedField.get(0));

		db.store(field);
	}

	@Override
	public IField loadField() {
		List<IField> fields = db.queryByExample(IField.class);

		if (fields.size() > 0)
			return fields.get(0);

		return null;
	}

}
