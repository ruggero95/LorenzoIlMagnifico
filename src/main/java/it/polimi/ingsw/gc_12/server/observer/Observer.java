package it.polimi.ingsw.gc_12.server.observer;

public interface Observer<C> {

	public default void update(C o) {
		System.out.println("I am the" + this.getClass().getSimpleName() +
				" I have been notified with the "+o.getClass().getSimpleName());
	}

	public void update();
}