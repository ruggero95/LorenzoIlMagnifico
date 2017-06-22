package it.polimi.ingsw.gc_12.effect;

import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.event.Event;
import it.polimi.ingsw.gc_12.event.EventPlaceFamilyMember;
import it.polimi.ingsw.gc_12.event.EventServantsRequested;

public class EffectServantsMultipier extends Effect {

    private int value;

    public EffectServantsMultipier(Event event, int value) {
        super(event);
        this.value = value;
    }

    @Override
    public void execute(Match match, Event event) {
        if(!(event instanceof EventServantsRequested || event instanceof EventPlaceFamilyMember))
            ((EventServantsRequested) event).setMultiplier(value);
        else if(event instanceof EventPlaceFamilyMember)
            ((EventPlaceFamilyMember) event).setMultiplier(value);
        else
            throw new IllegalStateException("EffectServantsMultiplier: did not expect received event. Received: " + event.getClass().getSimpleName());
    }

    @Override
    public void discard(Event event) {

    }
}