package de.htwg.se.moerakikemu.persistence;

import de.htwg.se.moerakikemu.model.IField;

public interface IFieldDAO {
	
	void saveField(IField field);
	
	IField getField();
}
