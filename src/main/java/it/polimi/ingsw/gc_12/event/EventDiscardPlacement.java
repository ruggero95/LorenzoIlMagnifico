package it.polimi.ingsw.gc_12.event;

import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.effect.EffectProvider;

import java.util.List;

/**
 * Created by feder on 2017-06-17.
 */
public class EventDiscardPlacement extends  Event {

    public EventDiscardPlacement(Player player) {
        super(player);
    }

    @Override
    public List<Object> getAttributes() {
        return null;
    }

    @Override
    public List<EffectProvider> getEffectProviders() {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }
}
