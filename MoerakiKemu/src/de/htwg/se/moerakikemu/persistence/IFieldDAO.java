package de.htwg.se.moerakikemu.persistence;

import de.htwg.se.moerakikemu.model.IField;

/**
 * Interface to save the field in a db
 */
public interface IFieldDAO {
	
	/**
	 * save the field in db
	 * @param field actual game field
	 */
	void saveField(IField field);
	
	/**
	 * load the field from db
	 * @return game field from db
	 */
	IField loadField();
}
