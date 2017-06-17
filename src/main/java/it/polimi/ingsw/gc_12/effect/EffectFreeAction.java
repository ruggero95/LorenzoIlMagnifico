package it.polimi.ingsw.gc_12.effect;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.card.CardType;
import it.polimi.ingsw.gc_12.event.Event;
import it.polimi.ingsw.gc_12.event.EventFreeAction;
import it.polimi.ingsw.gc_12.occupiables.Occupiable;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;


public class EffectFreeAction extends Effect {


    private List<Occupiable> occupiables;
    private FamilyMember familyMember;
    int value;

    public EffectFreeAction(Event event, List<Occupiable> occupiables, int value) {
        super(event);
        this.occupiables = occupiables;
        this.value = value;
    }

    @Override
    public void execute(Match match, Event event) {
        this.familyMember = new FamilyMember(match.getBoard().getTrackTurnOrder().getCurrentPlayer(), value);
        match.notifyObserver(new EventFreeAction(match.getBoard().getTrackTurnOrder().getCurrentPlayer(), familyMember, occupiables));
    }

    @Override
    public void discard(Event event) throws RuntimeException {
        //TODO: CHECK THIS
    }

    @Override
    public String toString() {
        return event.getClass().getSimpleName() + ": " + this.getClass().getSimpleName() + " of value " + value;
    }
}
