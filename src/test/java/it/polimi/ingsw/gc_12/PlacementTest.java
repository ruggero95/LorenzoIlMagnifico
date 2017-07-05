package it.polimi.ingsw.gc_12;

import it.polimi.ingsw.gc_12.action.Action;
import it.polimi.ingsw.gc_12.action.ActionChooseFamilyMember;
import it.polimi.ingsw.gc_12.action.ActionFactory;
import it.polimi.ingsw.gc_12.action.ActionPlace;
import it.polimi.ingsw.gc_12.card.CardType;
import it.polimi.ingsw.gc_12.client.ClientCLI;
import it.polimi.ingsw.gc_12.event.Event;
import it.polimi.ingsw.gc_12.event.EventServantsRequested;
import it.polimi.ingsw.gc_12.event.EventStartTurn;
import it.polimi.ingsw.gc_12.occupiables.Occupiable;
import it.polimi.ingsw.gc_12.resource.Money;
import it.polimi.ingsw.gc_12.resource.Resource;
import it.polimi.ingsw.gc_12.resource.ResourceType;
import it.polimi.ingsw.gc_12.resource.Servant;
import it.polimi.ingsw.gc_12.server.Server;
import it.polimi.ingsw.gc_12.server.view.ServerRMIView;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlacementTest {

    private Match match;
    private ServerRMIView view = mock(ServerRMIView.class);
    private List<Resource> discounts;


    private void init(){
        match = InstanceCreator.createMatch(4);
        match.registerObserver(view);
        match.start();

        Mockito.doNothing().when(view).update();
    }

    private ActionPlace createActionPlace(Player player, FamilyMember familyMember, Occupiable occupiable){
        ActionPlace action = ActionFactory.createActionPlace(player, familyMember, occupiable);
        discounts = new ArrayList<>();
        discounts.add(new Money(1));
        action.setDiscounts(discounts);
        action.setMultiplier(2);
        action.setServants(new Servant(0));

        return action;
    }

    @Test
    public void test(){

        init();
        assertEquals(3, match.getBoard().getSpaceDie().getDiceNum());
        int numOfOccupiables = match.getBoard().getOccupiables().size();

        for(int i = 0; i < numOfOccupiables; i++){
            init();
            Player player = match.getPlayer("p0");

            EventStartTurn eventStartTurn = (EventStartTurn) match.getActionHandler().getEvents().getFirst();

            Action a1 = match.getActionHandler().getAvailableAction(0);

            ActionChooseFamilyMember chooseFm = (ActionChooseFamilyMember) a1;

            chooseFm.getFamilyMember().setValue(7);


            FamilyMember familyMember = player.getFamilyMember(FamilyMemberColor.BLACK);
            Occupiable occupiable = match.getBoard().getOccupiables().get(i);

            ActionPlace action = createActionPlace(player, familyMember, occupiable);

            assertTrue(action.isValid(match));
            /*
            assertEquals(occupiable, action.getOccupiable());
            assertEquals(familyMember, action.getFamilyMember());
            assertEquals(0, action.getServant().getValue());
            assertEquals(false, action.isComplete());
            assertEquals(0, action.getMultiplier());
            assertEquals(discounts, action.getDiscounts());
            */

            System.out.println("starting action");
            action.start(match);

            assertTrue((occupiable.getRequiredValue() - action.getFamilyMember().getValue()) <= match.getActionHandler().getOffset());
            assertEquals(discounts, action.getDiscounts());
            for(ResourceType resourceType : ResourceType.values()){
                assertEquals(Optional.of(100), Optional.ofNullable(player.getResourceValue(resourceType)));
            }

            Action action1 = match.getActionHandler().getAvailableAction(0);
            action1.start(match);

            System.out.println("placement complete");
            assertTrue(occupiable.getOccupiers().size() == 1);
        }
    }
}