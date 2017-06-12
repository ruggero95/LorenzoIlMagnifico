package it.polimi.ingsw.gc_12.action;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.exceptions.RequiredValueNotSatisfiedException;

public class ActionPlace extends Action{

	protected FamilyMember familyMember;

	public ActionPlace(FamilyMember familyMember) {
		super();
		this.familyMember = familyMember;
	}

	public FamilyMember getFamilyMember() {
		return familyMember;
	}

	@Override
	public void start(Match match) throws RuntimeException, RequiredValueNotSatisfiedException {
		FamilyMember realFamilyMember = match.getBoard().getTrackTurnOrder().getCurrentPlayer().getFamilyMember(familyMember.getColor());
		if(realFamilyMember.isBusy())
			throw new RuntimeException("This FamilyMember is already busy!");
		match.chooseFamilyMember(this);
	}
	
	protected FamilyMember getRealFamilyMember(Match match){
    	return match.getBoard().getTrackTurnOrder().getCurrentPlayer().getFamilyMember(familyMember.getColor());
    }
}
