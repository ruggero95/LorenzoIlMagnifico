package it.polimi.ingsw.gc_12.event;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.action.*;
import it.polimi.ingsw.gc_12.effect.EffectProvider;
import it.polimi.ingsw.gc_12.occupiables.Occupiable;
import it.polimi.ingsw.gc_12.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class EventFreeAction extends Event {

    private FamilyMember familyMember;
    private List<Occupiable> occupiables;
    private List<Resource> discounts = new ArrayList<>();

    public EventFreeAction(Player player, FamilyMember familyMember, List<Occupiable> occupiables, List<Action> actions) {
        super(player);
        this.familyMember = familyMember;
        this.occupiables = occupiables;
        this.actions = actions;
    }

    public EventFreeAction(Player player, FamilyMember familyMember, List<Occupiable> occupiables, List<Action> actions, List<Resource> discounts) {
        this(player, familyMember, occupiables, actions);
        if(discounts != null)
            this.discounts = discounts;
    }

    public EventFreeAction(Player player, FamilyMember familyMember, List<Occupiable> occupiables){
        this(player, familyMember, occupiables, new ArrayList<>());
    }

    public void setDiscounts(List<Resource> discounts){
        this.discounts = discounts;
    }

    @Override
    public void setActions(ActionHandler actionHandler, Match match) {
        actions = new ArrayList<>();
        for(Occupiable occupiable: occupiables) {
            ActionPlace action = ActionFactory.createActionPlace(player, familyMember, occupiable, discounts);
            if(action.isValid(match))
                actions.add(action);
        }
        actions.add(new DiscardAction(player));
    }

    public List<Occupiable> getOccupiables() {
        return occupiables;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("line.separator"));
        sb.append(player.getName()+ " has received a free action!").append(System.getProperty("line.separator"));
        if(discounts.size() > 0)
            sb.append("(This free action comes with a discount: " + discounts + " )");
        return sb.toString();
    }
}
