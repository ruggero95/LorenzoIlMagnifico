package it.polimi.ingsw.gc_12.ActionTests;

import it.polimi.ingsw.gc_12.InstanceCreator;
import it.polimi.ingsw.gc12.model.match.Match;
import it.polimi.ingsw.gc12.model.player.Player;

public class ActivateLeaderCard {

    Match match = InstanceCreator.createMatch(2);
    Player player = match.getPlayer("p0");
}
