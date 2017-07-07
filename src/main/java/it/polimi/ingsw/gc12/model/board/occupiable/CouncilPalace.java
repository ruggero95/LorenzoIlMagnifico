package it.polimi.ingsw.gc12.model.board.occupiable;

import it.polimi.ingsw.gc12.model.player.familymember.FamilyMember;
import it.polimi.ingsw.gc12.model.effect.EffectChangeResource;
import it.polimi.ingsw.gc12.model.event.Event;
import it.polimi.ingsw.gc12.model.event.EventPlaceFamilyMember;
import it.polimi.ingsw.gc12.model.player.resource.CouncilPrivilege;
import it.polimi.ingsw.gc12.model.player.resource.ResourceExchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CouncilPalace extends Occupiable implements Zone, Serializable {

    public CouncilPalace(int requiredValue){
        super(requiredValue, new ArrayList<>());
        Event event = new EventPlaceFamilyMember(new ArrayList<>(Collections.singletonList(this)));
        ResourceExchange resourceExchange = new ResourceExchange(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new CouncilPrivilege(1))));
        this.effects.add(new EffectChangeResource(event, resourceExchange, false));
    }

    public void placeFamilyMember(FamilyMember familyMember){
        this.occupiers.add(familyMember);
    }

    @Override
    public boolean canBeOccupiedBy(FamilyMember familyMember) {
        return true;
    }

    @Override
    public List<Occupiable> getOccupiables() {
        return new ArrayList<>(Collections.singletonList(this));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Council Palace            ");//.append(System.getProperty("line.separator"));
        sb.append(super.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CouncilPalace;
    }
}