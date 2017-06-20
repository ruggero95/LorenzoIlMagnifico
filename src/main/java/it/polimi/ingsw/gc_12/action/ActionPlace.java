package it.polimi.ingsw.gc_12.action;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Match;

import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.effect.Effect;
import it.polimi.ingsw.gc_12.event.Event;
import it.polimi.ingsw.gc_12.event.EventPlaceFamilyMember;
import it.polimi.ingsw.gc_12.event.EventServantsRequested;
import it.polimi.ingsw.gc_12.exceptions.RequiredValueNotSatisfiedException;
import it.polimi.ingsw.gc_12.resource.ResourceType;
import it.polimi.ingsw.gc_12.resource.Servant;
import it.polimi.ingsw.gc_12.occupiables.Occupiable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ActionPlace extends Action {

	protected FamilyMember familyMember;
	protected Servant servant;
	protected Occupiable occupiable;
	protected boolean complete;

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
	public void start(Match match) throws IOException {
		setup(match);
		if(!complete) {
			EventServantsRequested eventServants = new EventServantsRequested(player, occupiable, familyMember);
			match.getActionHandler().update(eventServants);
			//Notifies the ServerRMIView
			match.notifyObserver(eventServants);
		}
		else {
			Event event = new EventPlaceFamilyMember(player, occupiable, familyMember);

			List<Effect> executedEffects = new ArrayList<>();
			try{
				//Can throw exceptions (in which case effects are discarded directly in EffectHandler)
				executedEffects = match.getEffectHandler().executeEffects(match, event);
				familyMember.setValue(familyMember.getValue()+servant.getValue());
				canBeExecuted(match);
				execute(match);
			}
			catch (RequiredValueNotSatisfiedException e) {
				Event eventException = new EventServantsRequested(player, occupiable, familyMember);
				match.getActionHandler().update(eventException);
				match.notifyObserver(eventException);
			}
			catch(Exception e) {
				match.getEffectHandler().discardEffects(executedEffects, event);
				System.out.println("Effects discarded due to " + e);
				e.printStackTrace();
			} finally {
				familyMember.setValue(familyMember.getValue()-servant.getValue());
			}
		}

	}

	@Override
	public boolean isValid(Match match) throws IOException {
		setup(match);
		Event event = new EventPlaceFamilyMember(player, occupiable, familyMember);

		List<Effect> executedEffects = new ArrayList<>();
		try{
			//Can throw exceptions (in which case effects are discarded directly in EffectHandler)
			executedEffects = match.getEffectHandler().executeEffects(match, event);
			familyMember.setValue(familyMember.getValue() + player.getResourceValue(ResourceType.SERVANT));
			this.canBeExecuted(match);
		}
		catch (Exception e) {
			familyMember.setValue(familyMember.getValue() - player.getResourceValue(ResourceType.SERVANT));
			match.getEffectHandler().discardEffects(executedEffects, event);
			return false;
		}
		familyMember.setValue(familyMember.getValue()-player.getResourceValue(ResourceType.SERVANT));
		match.getEffectHandler().discardEffects(executedEffects, event);
		return true;
	}
	
	protected FamilyMember getRealFamilyMember(Match match){
    	return match.getBoard().getTrackTurnOrder().getCurrentPlayer().getFamilyMember(familyMember.getColor());
    }

    protected Integer getServants(Match match) {
		Integer ownedServants =  match.getBoard().getTrackTurnOrder().getCurrentPlayer().getResourceValue(ResourceType.SERVANT);
		if(servant.getValue() > ownedServants)
			return ownedServants;
		return servant.getValue();
	}

	protected abstract void setup(Match match);
	protected abstract void canBeExecuted(Match match) throws RequiredValueNotSatisfiedException;
	protected abstract void execute(Match match) throws IOException;

	@Override
	public String toString() {
		return occupiable.toString();
	}

}
