package it.polimi.ingsw.gc12.model.effect;

import it.polimi.ingsw.gc12.model.match.Match;
import it.polimi.ingsw.gc12.model.player.Player;
import it.polimi.ingsw.gc12.model.event.Event;
import it.polimi.ingsw.gc12.model.event.EventChooseExchange;
import it.polimi.ingsw.gc12.misc.exception.ActionDeniedException;
import it.polimi.ingsw.gc12.model.player.resource.Resource;
import it.polimi.ingsw.gc12.model.player.resource.ResourceExchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EffectChangeResource extends Effect {

	private List<ResourceExchange> exchanges;
	private boolean choice;
	private ResourceExchange exchangeChosen;
	
	public EffectChangeResource(Event event, List<ResourceExchange> exchanges, boolean choice) {
		super(event);
		this.exchanges = exchanges;
		this.choice = choice;
	}
	
	public EffectChangeResource(Event event, ResourceExchange exchange, boolean choice) {
		this(event, new ArrayList<>(Arrays.asList(exchange)), choice);
	}
	
	public void execute(Match match, Event event, boolean validation) throws ActionDeniedException {
		Player player = event.getPlayer();
		if(!validation) {
			if(!choice) {
				for(ResourceExchange exchange : exchanges) {
					player.removeResources(exchange.getCost());

					List<Resource> newBonus = applyResourceBonus(exchange, match, player);
					match.addResources(player, newBonus);
				}
			}
			else {
				List<ResourceExchange> possibleExchanges = new ArrayList<>();
				for (ResourceExchange exchange : exchanges) {
					if (player.hasResources(exchange.getCost()))
						possibleExchanges.add(exchange);
				}
				EventChooseExchange eventExchange = new EventChooseExchange(player, possibleExchanges);
				match.getActionHandler().update(eventExchange, match);
				match.notifyObserver(eventExchange);
			}
		}
		else {
			List<ResourceExchange> possibleExchanges = new ArrayList<>();
			for (ResourceExchange exchange : exchanges) {
				if (player.hasResources(exchange.getCost()))
					possibleExchanges.add(exchange);
			}
			if(possibleExchanges.size() <= 0)
				throw new ActionDeniedException("You don't have enough resources to perform an exchange.");
		}


	}
	
	public void discard(Match match, Event event) {
		Player player = event.getPlayer();

		if(exchangeChosen == null) {
			for(ResourceExchange exchange : exchanges) {
				match.addResources(player, exchange.getCost());
				List<Resource> newBonus = applyResourceBonus(exchange, match, player);
				player.removeResources(newBonus);
			}
		}
		else {
			match.addResources(player, exchangeChosen.getCost());
			player.removeResources(exchangeChosen.getBonus());
		}
	}

	public List<ResourceExchange> getExchanges() {
		return exchanges;
	}

	public boolean hasChoice(){
		return choice;
	}

	@Override
	public String toString() {
		return event.getClass().getSimpleName() + ": " + this.getClass().getSimpleName() + ": " + String.valueOf(exchanges);
	}
}