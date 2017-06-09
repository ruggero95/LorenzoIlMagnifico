package it.polimi.ingsw.gc_12.action;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.MatchRemote;
import it.polimi.ingsw.gc_12.occupiables.*;

public class ActionFactory {

	public static ActionPlace getActionPlace(Occupiable occupiable, FamilyMember familyMember, MatchRemote match) throws RemoteException {
		if(occupiable instanceof TowerFloor) {
			return new ActionPlaceOnTower(familyMember, match.getTower(((TowerFloor) occupiable).getType()), (TowerFloor) occupiable);
		}else if(occupiable instanceof SpaceWork){
			return new ActionPlaceOnSpaceWork(familyMember, match.getSpaceWorkZone(((SpaceWork) occupiable).getWorkType()), (SpaceWork) occupiable);
		}else if(occupiable instanceof SpaceMarket){
			return new ActionPlaceOnMarket(familyMember, (SpaceMarket) occupiable);
		}else if(occupiable instanceof CouncilPalace){
			return new ActionPlaceOnCouncil(familyMember, (CouncilPalace) occupiable);
		}
		else
			return null;
	}
}