package it.polimi.ingsw.gc_12.event;

import it.polimi.ingsw.gc_12.client.ClientHandler;
import it.polimi.ingsw.gc_12.java_fx.MainBoard;
import it.polimi.ingsw.gc_12.mvc.ClientView;

public class EventNewName extends Event implements EventView{

	private int unauthorizedId;
	private String name;

	public EventNewName(int unauthorizedId, String name) {
		this.unauthorizedId = unauthorizedId;
		this.name = name;
	}

	public EventNewName(int unauthorizedId) {
		this.unauthorizedId = unauthorizedId;
	}

	@Override
	public void executeClientSide(ClientHandler client) {
		System.out.println("This name is already taken from another active player.");
		System.out.println("Choose another one.");
		client.setUnauthorizedId(unauthorizedId);
	}

	public int getUnauthorizedId() {
		return unauthorizedId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public void executeViewSide(MainBoard view) {
		view.getControllerMainBoard().notifyObservers(0);
	}
}