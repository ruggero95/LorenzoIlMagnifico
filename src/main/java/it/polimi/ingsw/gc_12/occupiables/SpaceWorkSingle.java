package it.polimi.ingsw.gc_12.occupiables;

import it.polimi.ingsw.gc_12.WorkType;
import it.polimi.ingsw.gc_12.effect.Effect;

import java.util.ArrayList;
import java.util.List;

public class SpaceWorkSingle extends SpaceWork{
	
	private static final int DEFAULT_REQUIRED_VALUE = 1;
	private static final List<Effect> DEFAULT_EFFECTS = new ArrayList<>();

	public SpaceWorkSingle(WorkType workType, int requiredValue, List<Effect> effects) {
		super(workType, requiredValue, effects);
	}

	public SpaceWorkSingle(WorkType workType) {
		super(workType, DEFAULT_REQUIRED_VALUE, DEFAULT_EFFECTS);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getWorkType() + " SpaceWorkSingle");//.append(System.getProperty("line.separator"));
		sb.append(super.toString());
		return sb.toString();
	}


}
