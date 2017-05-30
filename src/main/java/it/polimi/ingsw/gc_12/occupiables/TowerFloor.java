package it.polimi.ingsw.gc_12.occupiables;

import java.util.List;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.Occupiable;
import it.polimi.ingsw.gc_12.card.CardDevelopment;
import it.polimi.ingsw.gc_12.card.CardType;
import it.polimi.ingsw.gc_12.effect.Effect;

public class TowerFloor extends Occupiable {

	private final int floorNum;
	private CardDevelopment card;
	private CardType cardType;

	public TowerFloor(int floorNum, int requiredValue, CardType cardType, List<Effect> effects){
		super(requiredValue, effects);
		this.floorNum = floorNum;
		this.cardType = cardType;
	}
		
	public TowerFloor(int floorNum, int requiredValue, CardType cardType){
		this(floorNum, requiredValue, cardType, null);
	}

	public int getFloorNum() {
		return floorNum;
	}

	public CardDevelopment getCard() {
		return card;
	}

	public void setCard(CardDevelopment card) {
		this.card = card;
	}

	public boolean isOccupied() {
		return !occupiers.isEmpty();
	}


	@Override
	public void placeFamilyMember(FamilyMember occupier) {
		this.occupiers.add(occupier);
	}

	@Override
	public String toString() {
		return "Floor " + floorNum + " of " + cardType + " tower - required value: " + super.requiredValue ;
	}

	public CardType getType() {
		return cardType;
	}
}
