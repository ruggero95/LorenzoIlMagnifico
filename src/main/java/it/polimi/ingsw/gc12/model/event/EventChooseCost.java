package it.polimi.ingsw.gc12.model.event;

import it.polimi.ingsw.gc12.model.player.Player;
import it.polimi.ingsw.gc12.model.player.resource.Resource;
import it.polimi.ingsw.gc12.model.player.resource.ResourceExchange;

import java.util.List;

public class EventChooseCost extends Event {

    private ResourceExchange militaryExchange;
    private List<Resource> requirements;

    public EventChooseCost(Player player, ResourceExchange militaryExchange, List<Resource> requirements) {
        super(player);
        this.militaryExchange = militaryExchange;
        this.requirements = requirements;
    }

    public EventChooseCost(ResourceExchange militaryExchange, List<Resource> requirements) {
        this.militaryExchange = militaryExchange;
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("line.separator"));
        sb.append("How would you like to pay for this card?");
        return sb.toString();
    }}