package it.polimi.ingsw.gc_12.action;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.event.*;
import it.polimi.ingsw.gc_12.json.loader.LoaderConfig;
import it.polimi.ingsw.gc_12.json.loader.LoaderConfigPlayers;
import it.polimi.ingsw.gc_12.occupiables.*;
import it.polimi.ingsw.gc_12.resource.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ActionHandler {
	private List<Action> actions = new ArrayList<>();
	private LinkedList<Event> events = new LinkedList<>();
	private Match match;
	private int offset;
	private int multiplier;
	private boolean hasPlaced = false;
	private int councilPrivileges = 0;
	private List<List<Resource>> councilPrivilegeResources = new ArrayList<>();
	private List<Player> players;

	public ActionHandler(Match match) {
		this.match = match;
		this.actions = new ArrayList<>(Collections.singletonList(new ActionReady(null)));
		this.multiplier = 1;
	}

	public Action getAvailableAction(int input) {
		int inputReal = (input - offset * multiplier > 0 ? input - offset*multiplier : 0);
		Action action = actions.get(inputReal);

		if(events.size() > 0) {
			if(councilPrivileges <= 1) {
				events.removeFirst();
				if(events.size() > 0)
					actions = events.getFirst().getActions();
			}
			else{
				councilPrivileges--;
				actions.remove(inputReal);
			}
		}
		offset = 0;
		multiplier = 1;
		return action;
	}


	public void update(Event event, Match match) {
		events.addLast(event);
		event.setActions(this, match);
		saveActions(event);
	}

	private void saveActions(Event event) {
		if(events.size() > 0 && events.getFirst() == event) {
			actions = event.getActions();
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void removeLastEvent() {
		events.removeLast();
	}

	public void flushEvents() {
		events = new LinkedList<>();
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public void setHasPlaced(boolean hasPlaced) {
		this.hasPlaced = hasPlaced;
	}

	public boolean hasPlaced() {
		return this.hasPlaced;
	}

	public List<List<Resource>> getCouncilPrivilegeResources() {
		return councilPrivilegeResources;
	}

	public void setCouncilPrivilegeResources(List<List<Resource>> councilPrivilegeResources) {
		this.councilPrivilegeResources = councilPrivilegeResources;
	}

	public void setCouncilPrivileges(int councilPrivileges) {
		this.councilPrivileges = councilPrivileges;
	}
}
