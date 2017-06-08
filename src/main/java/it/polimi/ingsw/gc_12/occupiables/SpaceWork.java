package it.polimi.ingsw.gc_12.occupiables;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.gc_12.WorkType;
import it.polimi.ingsw.gc_12.effect.Effect;

public abstract class SpaceWork extends Occupiable implements Serializable {
	
	protected WorkType workType;

	public SpaceWork(WorkType workType, int requiredValue, List<Effect> effects){
		super(requiredValue, effects);
		this.workType = workType;
	}

	public SpaceWork(WorkType workType, int requiredValue, SpaceWorkZone spaceWorkZone, List<Effect> effects){
		this(workType, requiredValue, effects);
		spaceWorkZone.addSpaceWork(this);
	}

	public boolean isOccupied() {
		return !occupiers.isEmpty();
	}
	
	public WorkType getWorkType() {
		return workType;
	}
}
