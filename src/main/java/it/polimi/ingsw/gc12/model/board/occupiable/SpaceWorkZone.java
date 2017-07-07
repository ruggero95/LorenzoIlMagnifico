package it.polimi.ingsw.gc12.model.board.occupiable;

import it.polimi.ingsw.gc12.model.player.familymember.FamilyMember;
import it.polimi.ingsw.gc12.model.player.familymember.FamilyMemberColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpaceWorkZone implements Zone, Serializable {

    private List<SpaceWork> spaceWorks = new ArrayList<>();

    public SpaceWorkZone(){
    }

    //Checks whether the workspace is already taken by a member of the same family
    @Override
    public boolean canBeOccupiedBy(FamilyMember familyMember) {
        if(familyMember.getColor() == null || familyMember.getColor().equals(FamilyMemberColor.NEUTRAL))
            return true;

        for(SpaceWork spaceWork : spaceWorks)
            for(FamilyMember occupier: spaceWork.getOccupiers())
                if(!occupier.getColor().equals(FamilyMemberColor.NEUTRAL) && familyMember.getOwner().equals(occupier.getOwner()))
                    return false;
        return true;
    }

    public void addSpaceWork(SpaceWork spaceWork){
        this.spaceWorks.add(spaceWork);
    }

    public List<SpaceWork> getSpaceWorks() {
        return spaceWorks;
    }

    @Override
    public List<Occupiable> getOccupiables() {
        List<Occupiable> occupiables = new ArrayList<>();
        for(SpaceWork spaceWork : spaceWorks) {
            occupiables.add(spaceWork);
        }
        return occupiables;
    }

    public WorkType getType(){
        return spaceWorks.get(0).getWorkType();
    }

    @Override
    public String toString() {
        return "Work Zones of type: " + this.getType();
    }
}