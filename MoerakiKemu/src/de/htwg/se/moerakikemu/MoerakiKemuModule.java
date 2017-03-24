package de.htwg.se.moerakikemu;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.controllerimpl.Controller;
import de.htwg.se.moerakikemu.controller.controllerimpl.ControllerPlayer;

public class MoerakiKemuModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IController.class).to(Controller.class);
		
		//bind(Integer.class).annotatedWith(Names.named("fieldLength")).toInstance(12);
		//bind(IControllerPlayer.class).to(ControllerPlayer.class);
	}
}
