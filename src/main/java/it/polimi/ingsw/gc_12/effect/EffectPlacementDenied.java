package it.polimi.ingsw.gc_12.effect;

import it.polimi.ingsw.gc_12.event.Event;

/**
 * Created by feder on 2017-06-12.
 */
public class EffectPlacementDenied extends Effect {

    public EffectPlacementDenied(Event event) {
        super(event);
    }

    @Override
    public void execute(Event event) throws RuntimeException {
        throw new RuntimeException("A permanent effect prevents you from executing this placement!");
    }

    @Override
    public void discard(Event event) throws RuntimeException {

    }
}