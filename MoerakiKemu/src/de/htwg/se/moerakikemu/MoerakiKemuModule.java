package de.htwg.se.moerakikemu;

import com.google.inject.AbstractModule;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.impl.Controller;
import de.htwg.se.moerakikemu.persistence.IFieldDAO;
import de.htwg.se.moerakikemu.persistence.db4o.FieldDb4oDAO;

public class MoerakiKemuModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IController.class).to(Controller.class);
		bind(IFieldDAO.class).to(FieldDb4oDAO.class);
	}
}
