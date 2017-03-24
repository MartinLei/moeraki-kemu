package de.htwg.se.util.observerNEW;

public interface IObservable {

	void addObserver(IObserver s);

	void removeObserver(IObserver s);

	void removeAllObservers();

	void notifyObservers();

	void notifyObservers(Event e);
}