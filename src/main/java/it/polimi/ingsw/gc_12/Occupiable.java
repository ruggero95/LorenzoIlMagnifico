package it.polimi.ingsw.gc_12;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc_12.effect.Effect;
import it.polimi.ingsw.gc_12.exceptions.FamilyMemberAlreadyPresentException;
import it.polimi.ingsw.gc_12.exceptions.OccupiableAlreadyTakenException;
import it.polimi.ingsw.gc_12.exceptions.RequiredValueNotSatisfiedException;

public abstract class Occupiable implements EffectProvider {
	private List<Effect> effects = new ArrayList<>();
	protected transient List<FamilyMember> occupiers = new ArrayList<>();
	public final static int DEFAULT_MAXNUMBEROFPLAYERS = 1;
	public static final int DEFAULT_REQUIRED_VALUE = 1;
	protected final int requiredValue;
	
	public Occupiable(int requiredValue, List<Effect> effects) {
		this.requiredValue = requiredValue;
	}
	
	public Occupiable(List<Effect> effects) {
		this(DEFAULT_REQUIRED_VALUE, effects);
	}
	
	public Occupiable() {
		this(DEFAULT_REQUIRED_VALUE, new ArrayList<>());
	}
	
	public List<FamilyMember> getOccupiers() {
		return occupiers;
	};
	
	public int getRequiredValue() {
		return requiredValue;
	}

	@Override
	public List<Effect> getEffects() {
		return effects;
	}

	public void placeFamilyMember(FamilyMember occupier) throws RequiredValueNotSatisfiedException, FamilyMemberAlreadyPresentException, OccupiableAlreadyTakenException {
		isRequiredValueSatisfied(occupier);
		this.canBeOccupiedBy(occupier);

		//Only executed if two previous calls do not throw exceptions
		this.occupiers.add(occupier);
	}

	protected void isRequiredValueSatisfied(FamilyMember occupier) throws RequiredValueNotSatisfiedException {
		if(requiredValue > occupier.getValue())
			throw new RequiredValueNotSatisfiedException();
	}
	
	public abstract void canBeOccupiedBy(FamilyMember occupier) throws FamilyMemberAlreadyPresentException, OccupiableAlreadyTakenException;
}
