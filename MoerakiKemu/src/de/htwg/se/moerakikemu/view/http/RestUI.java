package de.htwg.se.moerakikemu.view.http;

import java.util.concurrent.CompletionStage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.inject.Inject;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.model.impl.State;
import de.htwg.se.moerakikemu.util.observer.Event;
import de.htwg.se.moerakikemu.util.observer.IObserver;

/**
 * REST Interface
 */
public class RestUI extends AllDirectives implements IObserver {
	private static final Logger LOGGER = (Logger) LogManager.getLogger(RestUI.class);

	private final String IP = "localhost";
	private final int PORT = 8080;

	private IController controller;
	private volatile Object shutdownSwitch = new Object();

	/**
	 * Constructor
	 * 
	 * @param controller
	 */
	@Inject
	public RestUI(IController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	public void startHTTPServer() {
		// boot up server using the route as defined below
		ActorSystem system = ActorSystem.create();

		final Http http = Http.get(system);
		final ActorMaterializer materializer = ActorMaterializer.create(system);

		// In order to access all directives we need an instance where the
		// routes are define.
		final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = this.createRoute().flow(system, materializer);
		final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow, ConnectHttp.toHost(IP, PORT),
				materializer);

		LOGGER.info("REST Server online at http://" + IP + ":" + PORT);

		Thread runner = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					synchronized (shutdownSwitch) {
						shutdownSwitch.wait();
					}
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage());
				}

				binding.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());
				LOGGER.info("REST Server shutdown");
			}

		});
		runner.start();
	}

	private Route createRoute() {
		return route(path("new", () -> get(() -> {
			LOGGER.info("hello request");
			return complete("new");
		})), path("q", () -> get(() -> {
			LOGGER.info("q request");
			
			synchronized (shutdownSwitch) {
				shutdownSwitch.notify();
			}
			controller.quitGame();
			return complete("<h1>quitGame</h1>");
		}))

		);
	}

	@Override
	public void update(Event e) {
		State state = controller.getState();

		if (state.equals(State.EXIT_GAME)) {
			synchronized (shutdownSwitch) {
				shutdownSwitch.notify();
			}
		}
	}

}
