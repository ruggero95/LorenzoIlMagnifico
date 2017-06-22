package it.polimi.ingsw.gc_12;

import it.polimi.ingsw.gc_12.action.ActionHandler;
import it.polimi.ingsw.gc_12.card.Card;
import it.polimi.ingsw.gc_12.card.CardDeckSet;
import it.polimi.ingsw.gc_12.card.CardType;
import it.polimi.ingsw.gc_12.effect.EffectHandler;
import it.polimi.ingsw.gc_12.event.*;
import it.polimi.ingsw.gc_12.excommunication.ExcommunicationTile;
import it.polimi.ingsw.gc_12.json.loader.*;
import it.polimi.ingsw.gc_12.occupiables.Occupiable;
import it.polimi.ingsw.gc_12.occupiables.TowerFloor;
import it.polimi.ingsw.gc_12.personal_board.BonusTile;
import it.polimi.ingsw.gc_12.server.model.State;
import it.polimi.ingsw.gc_12.server.observer.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Match extends Observable<Event> implements Serializable{
	private List<Player> players = new ArrayList<>();
	private List<BonusTile> bonusTiles;
	private List<Card> cards = new ArrayList<>();
	private transient List<ExcommunicationTile> excommunicationTiles = new ArrayList<>();
	private transient CardDeckSet cardDeckSet;
	private Board board;
	private int roundNum;
	private transient EffectHandler effectHandler;
	private transient ActionHandler actionHandler;
	public transient final static GameMode DEFAULT_GAME_MODE = GameMode.NORMAL;
	public transient final static int DEFAULT_ROUND_NUM = 6;
	public transient final static int DEFAULT_PERIODS_LEN = 2;
	public transient final static int DEFAULT_TOTAL_PERIODS_NUM = DEFAULT_ROUND_NUM / DEFAULT_PERIODS_LEN;
	private State gameState;
	private boolean isFMPlaced;
	private int numReady;

	public Match() {
		this.roundNum = 1;
		this.cards = new LoaderCard().get(this);
		this.cardDeckSet = new CardDeckSet(cards, DEFAULT_ROUND_NUM / DEFAULT_PERIODS_LEN);
		this.effectHandler = new EffectHandler();
		this.actionHandler = new ActionHandler(this);

		this.gameState = State.PENDING;
	}

	public void init(List<Player> players) {
		this.players = players;

		this.bonusTiles = new LoaderBonusTile().get(this);
		Collections.shuffle(bonusTiles);

		for (Player player : players){
			player.getPersonalBoard().setBonusTile(bonusTiles.get(players.indexOf(player)));
		}
		cardDeckSet.shuffle();


		createBoard();

		for (Player player : players) {
			player.init();
			player.getPersonalBoard().setCardsSpaces(new LoaderCardsSpace().get(this));
		}
	}

	private void createBoard() {
		System.out.println("Match: Creating the board");
		board = new Board(players);
		board.setTowerSet(new LoaderTowerSet().get(this));
		board.getTowerSet().setCards(cardDeckSet);
		board.setMarket(new LoaderMarket().get(this));
		System.out.println(board.getMarket().getSpaceMarkets());
		board.getTowerSet().setCards(cardDeckSet);
	}

	public void start() {
		this.gameState = State.RUNNING;
		System.out.println("Match: notifying EventStartMatch");
		this.notifyObserver(new EventStartMatch(this));
		newTurn();
	}

	//Increments turn counter in TrackTurnOrder
	public void newTurn() {
		System.out.println("Match: Starting new turn");
		board.getTrackTurnOrder().newTurn();
		System.out.println("Match: notifying EventStartTurn to ServerRMIView");
		EventStartTurn event = new EventStartTurn(board.getTrackTurnOrder().getCurrentPlayer());
		actionHandler.update(event);
		this.notifyObserver(event);
	}
	
	public void placeFamilyMember(Occupiable occupiable, FamilyMember familyMember) {

		occupiable.placeFamilyMember(familyMember);
		if(familyMember.getColor() != null)
			familyMember.setBusy(true);

		if(familyMember.getColor() != null) {
			EventPlaceFamilyMember event = new EventPlaceFamilyMember(board.getTrackTurnOrder().getCurrentPlayer(), occupiable, familyMember);
			actionHandler.update(event);
			this.notifyObserver(event);
		}

		if(occupiable instanceof TowerFloor){
			EventPickCard eventPickCard = new EventPickCard(board.getTrackTurnOrder().getCurrentPlayer(), ((TowerFloor) occupiable).getCard());
			this.notifyObserver(eventPickCard);
		}
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}


	public List<Player> getPlayers() {
		return players;
	}

	public Player getPlayer(String name) {
		List<Player> playersFiltered = players.stream().filter(player -> name.equals(player.getName())).collect(Collectors.toList());
		if(playersFiltered.size() > 0)
			return playersFiltered.get(0);
		else
			return null;
	}

	public List<BonusTile> getBonusTyles() {
		return bonusTiles;
	}

	public List<Card> getCards(CardType cardType) {
		return cards;
	}

	public Board getBoard() {
		return board;
	}

	public int getRoundNum() {
		return roundNum;
	}

	public int getPeriodNum() {
		return roundNum / 2 + roundNum % 2;
	}

	@Override
	public String toString() {
		return board.toString();
	}

	public EffectHandler getEffectHandler() {
		return effectHandler;
	}

	public ActionHandler getActionHandler() {
		return actionHandler;
	}

	public void addReady() {
		numReady++;
		if(numReady == 2) {
			start();
		}
	}
}
