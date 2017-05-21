package it.polimi.ingsw.gc_12.effect;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.event.Event;
import it.polimi.ingsw.gc_12.resource.Resource;

// TODO: add the possibility to have FamilyMember.value as a possible cost
public class EffectChangeResource extends Effect {

	private List<Resource> costs = new ArrayList<>();
	private List<Resource> resources = new ArrayList<>();
	
	public EffectChangeResource(Event event, List<Resource> costs, List<Resource> resources) {
		super(event);
		this.costs = costs;
		this.resources = resources;
	}
	
	public EffectChangeResource(Event event, Resource cost, Resource resource) {
		super(event);
		List<Resource> costs =  new ArrayList<>();
		costs.add(cost);
		this.costs = costs;
		
		List<Resource> resources = new ArrayList<>();
		resources.add(resource);
		this.resources = resources;
	}
	
	public void execute(Event event) {
		Player player = event.getPlayer();
		player.removeResources(costs);
		player.addResources(resources);
	}
	
	public void discard(Event event) {
		Player player = event.getPlayer();
		player.addResources(costs);
		player.removeResources(resources);
	}

}
