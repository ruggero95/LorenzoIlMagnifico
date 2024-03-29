package it.polimi.ingsw.gc12.model.board.occupiable;

import it.polimi.ingsw.gc12.model.player.familymember.FamilyMember;
import it.polimi.ingsw.gc12.model.card.CardDevelopment;
import it.polimi.ingsw.gc12.model.card.CardType;
import it.polimi.ingsw.gc12.model.effect.Effect;

import java.util.List;

/**
 * Represent a floor on a tower, contains cards and all the floor are numerated
 */
public class TowerFloor extends Occupiable{

	private int floorNum;
	private boolean anyFloor;//(used by cards)
	private CardDevelopment card;
	private CardType cardType;

    /**
     * Constructor
     * @param floorNum number of the floor
     * @param requiredValue required value for place
     * @param cardType type of the card contained
     * @param effects effects of the floor, if you put a family member
     *                on some floor they gave you resources
     */
	public TowerFloor(int floorNum, int requiredValue, CardType cardType, List<Effect> effects){
		super(requiredValue, effects);
		this.floorNum = floorNum;
		this.cardType = cardType;
		this.anyFloor = false;
	}

    /**
     * Constructor with no effects
     * @param floorNum number of the floor
     * @param requiredValue required value for place
     * @param cardType type of the card contained
     */
	public TowerFloor(int floorNum, int requiredValue, CardType cardType){
		this(floorNum, requiredValue, cardType, null);
	}

    /**
     * Constructor with 0 required value and no effects but any floor to true.
     * Any floor gave you the opportunity to place the family member on any floor for particolar effects
     */
	public TowerFloor(){
		this(0, 0, null);
		this.anyFloor = true;
	}

    /**
     * Constructor with 0 required value
     * @param floorNum floor number
     * @param cardType card type on the floor
     */
	public TowerFloor(int floorNum, CardType cardType){
		this(floorNum, 0, cardType, null);
	}

	public TowerFloor(int floorNum){
		this(floorNum, 0, null, null);
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

	public void removeCard(){
		this.card = null;
	}

	public boolean isOccupied() {
		return !occupiers.isEmpty();
	}


	@Override
	public void placeFamilyMember(FamilyMember occupier) {
		this.occupiers.add(occupier);
		System.out.println(occupiers);
	}

	public CardType getType() {
		return cardType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TowerFloor)) return false;

		TowerFloor that = (TowerFloor) o;

		if(that.anyFloor == true) return true;

		if (floorNum != that.floorNum) return false;

		if(this.cardType == null || that.cardType == null)
			return true;

		return cardType == that.cardType;
	}

	@Override
	public int hashCode() {
		int result = floorNum;
		result = 31 * result + cardType.hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getType() + " Tower, Floor n." + floorNum + " (required value " + requiredValue + ")");
		sb.append(super.toString()).append(System.getProperty("line.separator"));
		sb.append(card);
		return sb.toString();
	}
}
