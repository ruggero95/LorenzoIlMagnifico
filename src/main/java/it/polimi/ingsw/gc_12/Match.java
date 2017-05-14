package it.polimi.ingsw.gc_12;
import java.util.List;
import java.util.ArrayList;

public class Match {
	private final List<Player> players = new ArrayList<>();
	private final List<BonusTile> bonusTiles = new ArrayList<>();
	private List<Card> cards = new ArrayList<>();
	private int roundNum;
	private final GameMode gameMode; 
	private Board board;
	
	public Match(GameMode gameMode){		
		this.gameMode = gameMode;		
		this.roundNum = 1;
	}

	public void start(){
		//Import Deck and players from external file
		
	}
	
	private void setInitialResources(){
		
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	public List<BonusTile> getBonusTyles(){
		return bonusTiles;
	}
	public List<Card> getCards(){
		return cards;
	}
	public GameMode getGameMode(){
		return gameMode;
	}
	public Board getBoard(){
		return board;
	}
	public int getRoundNum(){
		return roundNum;
	}
	public int getPeriodNum(){
		return roundNum / 2 + roundNum % 2;
	}
	
}
