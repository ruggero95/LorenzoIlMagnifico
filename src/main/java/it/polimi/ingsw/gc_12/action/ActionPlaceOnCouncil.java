package it.polimi.ingsw.gc_12.action;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.effect.Effect;
import it.polimi.ingsw.gc_12.event.Event;
import it.polimi.ingsw.gc_12.event.EventPlaceFamilyMember;
import it.polimi.ingsw.gc_12.exceptions.RequiredValueNotSatisfiedException;
import it.polimi.ingsw.gc_12.occupiables.CouncilPalace;

import java.util.List;

public class ActionPlaceOnCouncil extends ActionPlace {

    private CouncilPalace councilPalace;

    public ActionPlaceOnCouncil(FamilyMember familyMember, CouncilPalace councilPalace) {
        super(familyMember);
        this.councilPalace = councilPalace;
    }

    public void canBeExecuted() throws RequiredValueNotSatisfiedException {

        if (!councilPalace.isRequiredValueSatisfied(familyMember))
            throw new RequiredValueNotSatisfiedException();
    }

    @Override
    public void start(Match match) throws RequiredValueNotSatisfiedException {
        System.out.println("ActionPlaceOnCouncil: starting...");
        Player player = match.getBoard().getTrackTurnOrder().getCurrentPlayer();
    	familyMember = getRealFamilyMember(match);
    	councilPalace = getRealCouncilPalace(match);
        Event event = new EventPlaceFamilyMember(player, councilPalace, familyMember);

        //Can throw exceptions (in which case effects are discarded directly in EffectHandler)
        List<Effect> executedEffects = player.getEffectHandler().executeEffects(event);
        System.out.println("Created the event.");
        try{
            this.canBeExecuted();
            System.out.println("Event can be executed.");
            match.placeFamilyMember(councilPalace, familyMember);
            System.out.println("FamilyMember placed!");
        }catch(Exception e) {
            player.getEffectHandler().discardEffects(executedEffects, event);
            System.out.println("Effects discarded due to " + e);
            throw e;
        }
    }

    private CouncilPalace getRealCouncilPalace(Match match) {
        return match.getBoard().getCouncilPalace();
    }
}