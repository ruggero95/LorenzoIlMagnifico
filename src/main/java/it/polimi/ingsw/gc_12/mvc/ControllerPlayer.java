package it.polimi.ingsw.gc_12.mvc;

import java.util.*;

import it.polimi.ingsw.gc_12.*;
import it.polimi.ingsw.gc_12.action.*;
import it.polimi.ingsw.gc_12.occupiables.*;

public class ControllerPlayer{

	private Map<Player, ViewCLI> views = new HashMap<>();
	private Match match;
	private Action action;
	private Player currentPlayer;
	private FamilyMember familyMember; //TODO: find another way to store this

	public ControllerPlayer(Match match){
		this.match = match;
		createViews();
	}

	private void createViews() {
		List<Player> players = match.getPlayers();
		for(Player player : players) {
			ViewCLI view = new ViewCLI(this, match);
			views.put(player, view);
		}
	}

	public void start() {
		currentPlayer = match.getBoard().getTrackTurnOrder().getCurrentPlayer();
		views.get(currentPlayer).askAction();
	}

	public void setFamilyMember(FamilyMemberColor familyMemberColor) {
		this.familyMember = currentPlayer.getFamilyMember(familyMemberColor);
		views.get(currentPlayer).askOccupiable();
	}

	public void setOccupiable(Occupiable occupiable) {
		//TODO: improve this switch case scenario
		if(occupiable instanceof TowerFloor) {
			action = new ActionPlaceOnTower(familyMember, match.getBoard().getTowerSet().getTower(((TowerFloor) occupiable).getType()), (TowerFloor) occupiable);
		}else if(occupiable instanceof SpaceWork){
			action = new ActionPlaceOnSpaceWork(familyMember, match.getBoard().getSpaceWorks().get(((SpaceWork) occupiable).getWorkType()), (SpaceWork) occupiable);
		}else if(occupiable instanceof SpaceMarket){
			action = new ActionPlaceOnMarket(familyMember, (SpaceMarket) occupiable);
		}else if(occupiable instanceof CouncilPalace){
			action = new ActionPlaceOnCouncil(familyMember, (CouncilPalace) occupiable);
		}
		try{
			action.start();
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
			views.get(currentPlayer).askOccupiable();
		}
	}

	public void vaticanReport(Player player) {
		boolean support = views.get(player).supportChurch();
		if (support) {
			player.supportChurch();
		}else{
			player.receiveExcommunication();
		}
	}

}
