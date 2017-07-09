package it.polimi.ingsw.gc12.model.action;

import it.polimi.ingsw.gc12.model.card.LeaderCard;
import it.polimi.ingsw.gc12.model.player.familymember.FamilyMember;
import it.polimi.ingsw.gc12.model.match.Match;
import it.polimi.ingsw.gc12.model.player.Player;
import it.polimi.ingsw.gc12.model.effect.Effect;
import it.polimi.ingsw.gc12.model.event.Event;
import it.polimi.ingsw.gc12.model.event.EventPlaceFamilyMember;
import it.polimi.ingsw.gc12.model.event.EventPlacementEnded;
import it.polimi.ingsw.gc12.model.event.EventServantsRequested;
import it.polimi.ingsw.gc12.misc.exception.ActionNotAllowedException;
import it.polimi.ingsw.gc12.misc.exception.ActionDeniedException;
import it.polimi.ingsw.gc12.misc.exception.RequiredValueNotSatisfiedException;
import it.polimi.ingsw.gc12.model.board.occupiable.Occupiable;
import it.polimi.ingsw.gc12.model.player.resource.Resource;
import it.polimi.ingsw.gc12.model.player.resource.ResourceType;
import it.polimi.ingsw.gc12.model.player.resource.Servant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ActionPlace extends Action {

	protected FamilyMember familyMember;
	protected Servant servant;
	protected Occupiable occupiable;
	protected boolean complete;
	protected int multiplier;
	protected List<Resource> discounts = new ArrayList<>();

	public ActionPlace(Player player, FamilyMember familyMember, Occupiable occupiable, Servant servant, boolean complete) {
		super(player);
		this.familyMember = familyMember;
		this.occupiable = occupiable;
		this.servant = servant;
		this.complete = complete;
	}

	public ActionPlace(Player player, FamilyMember familyMember, Occupiable occupiable) {
		this(player,familyMember, occupiable, new Servant(0), false);
	}

	public FamilyMember getFamilyMember() {
		return familyMember;
	}

	@Override
	public void start(Match match) {
		if(player.getName() == null)
			throw new IllegalArgumentException("Cannot start an action without a real player.");

		setup(match);
		if(!complete) {
			EventServantsRequested eventServants = new EventServantsRequested(player, occupiable, familyMember);
			if(discounts.size() > 0)
				eventServants.setDiscounts(discounts);

			try {
				match.getEffectHandler().executeEffects(match, eventServants);
			} catch (ActionDeniedException e) {
				throw new IllegalStateException("ActionPlace: starting an action that has been denied.");
			}

			match.getActionHandler().update(eventServants, match);
			//Notifies the ServerRMIView
			match.notifyObserver(eventServants);
		}
		else {
			Event event = new EventPlaceFamilyMember(player, occupiable, familyMember);

			int increment = (multiplier > 1 ? (servant.getValue() / multiplier) : servant.getValue());

			try{
				familyMember.setValue(familyMember.getValue() + increment);
				canBeExecuted(match);
				match.getEffectHandler().executeEffects(match, event);
				execute(match);
			}
			catch (RequiredValueNotSatisfiedException | ActionDeniedException | ActionNotAllowedException e) {
				throw new IllegalStateException("ActionPlace the action cannot be performed even if it has been considered valid");
			} finally {
				familyMember.setValue(familyMember.getValue() - increment);
				multiplier = 1; //TODO: CHECK THIS
			}
		}

	}

	@Override
	public boolean isValid(Match match) {
		if(player.getName() == null)
			throw new IllegalArgumentException("Cannot call isValid on an action without a real player.");

		setup(match);
		Event event = new EventPlaceFamilyMember(player, occupiable, familyMember);
		int originalValue = familyMember.getValue();

		try{
			familyMember.setValue(originalValue + player.getResourceValue(ResourceType.SERVANT));

			//Can throw exceptions (in which case all effects are discarded directly in EffectHandler)
			match.getEffectHandler().executeEffects(match, event, true);
			this.canBeExecuted(match);
		}
		catch (RequiredValueNotSatisfiedException | ActionDeniedException | ActionNotAllowedException e) {
			familyMember.setValue(originalValue);
			return false;
		}
		familyMember.setValue(originalValue);
		return true;
	}
	
	protected FamilyMember getRealFamilyMember(Match match){
    	return match.getBoard().getTrackTurnOrder().getCurrentPlayer().getFamilyMember(familyMember.getColor());
    }

	public void setServants(Servant servant) {
		this.servant = servant;
	}

	protected void execute(Match match) {
		player.removeResources(Collections.singletonList(servant));
		match.placeFamilyMember(occupiable, familyMember);
		EventPlacementEnded event = new EventPlacementEnded(player);
		match.getActionHandler().update(event, match);
		match.notifyObserver(event);
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	protected abstract void setup(Match match);
	protected abstract void canBeExecuted(Match match) throws RequiredValueNotSatisfiedException, ActionNotAllowedException;

	public List<Resource> getDiscounts() {
		return discounts;
	}

	public Servant getServant() {
		return servant;
	}

	public Occupiable getOccupiable() {
		return occupiable;
	}

	public boolean isComplete() {
		return complete;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setDiscounts(List<Resource> discounts) {
		this.discounts = discounts;
	}

	@Override
	public String toString() {
		return occupiable.toString();
	}
}
