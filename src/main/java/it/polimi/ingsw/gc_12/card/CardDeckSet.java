package it.polimi.ingsw.gc_12.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDeckSet {

    //TODO: waiting for JSON file
    private List<CardDevelopment> cards = new ArrayList<>();
    private Map<CardType, Map<Integer, CardDeck>> decks = new HashMap<>();

    public CardDeckSet(List<Card> cards, int periods){

        for(Card card : cards)
            this.cards.add((CardDevelopment) card);

        //Creates DevelopmentDecks grouping them by type and period
        for(CardType type: CardType.values()){
            Map<Integer, CardDeck> innerMap = new HashMap<>();
            for(int period = 1; period <= (periods); period++){
                CardDeck cardDeck = new CardDeck(type, period);
                innerMap.put(period, cardDeck);
            }
            decks.put(type, innerMap);
        }

        //Sorts the development cards in the relative decks
        for(CardDevelopment card : this.cards) {
            decks.get(card.getType()).get(card.getPeriod()).putCard(card);
        }
    }

    public void shuffle(){
        for(Map<Integer, CardDeck> map : decks.values())
            for (CardDeck deck : map.values())
                deck.shuffle();
    }

    public Map<CardType, Map<Integer, CardDeck>> getDecks(){
        return decks;
    }

    public Map<Integer, CardDeck> getDeck(CardType cardType){
        return getDecks().get(cardType);
    }
}

