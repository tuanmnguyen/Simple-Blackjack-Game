package model;

import java.util.List;
import java.util.Collection;
import java.util.Deque;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

import model.PlayingCardImpl;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

import java.lang.Thread;

/**
 * This class contains the implementations of the GameEngine interface.
 * 
 * @author Tuan M. Nguyen 
 * 
 */

public class GameEngineImpl implements GameEngine {

	// Attributes
	private List<Player> players;
	private Deque<PlayingCard> cards;
	private List<GameEngineCallback> gameEngineCallBacks;
	private static final String HOUSE = "HOUSE"; 
	private static final String PLAYER = "PLAYER"; 
	
	// Constructor
	public GameEngineImpl() {
		cards = getShuffledDeck();
		players = new ArrayList<>();
		gameEngineCallBacks = new ArrayList<>();
	}
	
	@Override
	public void dealPlayer(Player player, int delay) {
		
		// Deal cards to the player
		int playerResult = deal(PLAYER, delay, player);
		
		player.setResult(playerResult);
		
		// Update final result for player to callback(s)
		for(GameEngineCallback thisCallBack : gameEngineCallBacks)
			thisCallBack.result(player, playerResult, this);
		
		// Update final result for player in player list
		Player playerToUpdate = getPlayer(player.getPlayerId());
		playerToUpdate.setResult(playerResult);
		
	}

	@Override
	public void dealHouse(int delay) {
		
		int houseResult = deal(HOUSE, delay, null);
		
		// Update win/lost values to players
		for(Player thisPlayer : players) {
			int playerPoints = thisPlayer.getPoints();
			int playerBet = thisPlayer.getBet();
			
			// Verify whether the player wins, loses or draws
			if(thisPlayer.getResult() > houseResult)
				thisPlayer.setPoints(playerPoints+playerBet);
			else if(thisPlayer.getResult() < houseResult)
				thisPlayer.setPoints(playerPoints-playerBet);
			else
				thisPlayer.setPoints(playerPoints);
			
			thisPlayer.resetBet();
		}
		
		// Update house result to callback(s)
		for(GameEngineCallback thisCallBack : gameEngineCallBacks)
			thisCallBack.houseResult(houseResult, this);
		
	}

	@Override
	public void addPlayer(Player player) {
		boolean exists = false;
		
		// Search if the player ID already exists in the player list
		for(int i=0; i<players.size(); i++) {
			if(players.get(i).getPlayerId().equals(player.getPlayerId())) {
				players.set(i, player);
				exists = true;
			}
		}
		
		// If the player ID does not exist, add the player to the game
		if(exists == false) {
			players.add(player);
		}
		
	} 

	@Override
	public Player getPlayer(String id) {
		for(Player thisPlayer : players) {
			if(thisPlayer.getPlayerId().equals(id))
				return thisPlayer;
		}
		
		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		return players.remove(player);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gameEngineCallBacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		return gameEngineCallBacks.remove(gameEngineCallback);
	}

	@Override
	public Collection<Player> getAllPlayers() {
		List<Player> copyOfPlayers = new ArrayList<>(); 
		
		// Make a copy of the player list
		for(int i=0; i<players.size(); i++) {
			Player player = players.get(i);
			copyOfPlayers.add(player);
		}
		
		return copyOfPlayers;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		for(Player thisPlayer : players) {
			if(thisPlayer.getPlayerId().equals(player.getPlayerId()))
				return thisPlayer.placeBet(bet);
		}
		
		return false;
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		
		List<PlayingCard> tempDeck = new ArrayList<>();
		
		// Create a new deck of cards
		for(PlayingCard.Suit suit: PlayingCard.Suit.values()) {
			for(PlayingCard.Value value: PlayingCard.Value.values()) {
				switch(value) {
					case ACE:
						tempDeck.add(new PlayingCardImpl(suit,value,1));
						break;
						
					case TWO:
						tempDeck.add(new PlayingCardImpl(suit,value,2));
						break;
						
					case THREE:
						tempDeck.add(new PlayingCardImpl(suit,value,3));
						break;
						
					case FOUR:
						tempDeck.add(new PlayingCardImpl(suit,value,4));
						break;
						
					case FIVE:
						tempDeck.add(new PlayingCardImpl(suit,value,5));
						break;
						
					case SIX:
						tempDeck.add(new PlayingCardImpl(suit,value,6));
						break;
						
					case SEVEN:
						tempDeck.add(new PlayingCardImpl(suit,value,7));
						break;
						
					case EIGHT:
						tempDeck.add(new PlayingCardImpl(suit,value,8));
						break;
					case NINE:
						
						tempDeck.add(new PlayingCardImpl(suit,value,9));
						break;
						
					default:
						tempDeck.add(new PlayingCardImpl(suit,value,10));
						break;
				}	
			}	
		}	
		
		// Shuffle the deck of cards
		Collections.shuffle(tempDeck);
		
		// Convert the deck to Deque
		cards = new LinkedList<PlayingCard>(tempDeck);
		
		return cards;
	}
	
	/**
	 * This method deals cards to the player/house by looping until the bust value,
	 * and returns the pre-bust total. To be used by dealPlayer(...) & dealHouse(...)
	 *
	 *
	 * @param recipient 
	 * 			the person who will receive the result at the end of the hand (PLAYER or HOUSE)
	 * 
	 * @param delay 
	 * 			the delay between cards being dealt
	 * 
	 * @param player 
	 * 			the player's object if the recipient is PLAYER, otherwise specifies null for HOUSE
	 * 
	 * @return the result (pre-bust total)
	 * 
	 */
	private int deal(String recipient, int delay, Player player) {
		int result = 0;
		Deque<PlayingCard> recipientCards = new LinkedList<PlayingCard>();
		
		// Loop until the player busts (default value of GameEngine.BUST_TOTAL=21)
		while(result <= GameEngine.BUST_LEVEL) {
			
			// Add delay
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}				
			
			// Deal a card to house
			PlayingCard currentCard = cards.pop();
			recipientCards.push(currentCard);
			
			// Update house's score
			result += currentCard.getScore();
			
			if(result <= GameEngine.BUST_LEVEL) {
				// Update card details to callback(s)
				for(GameEngineCallback thisCallBack : gameEngineCallBacks) {
					if(recipient.equals(PLAYER))
						thisCallBack.nextCard(player, currentCard, this);
					else
						thisCallBack.nextHouseCard(currentCard, this);
				}
			}
		}
		
		PlayingCard bustedCard = recipientCards.pop();
		
		// Update busted card details to callback(s)
		for(GameEngineCallback thisCallBack : gameEngineCallBacks) {
			if(recipient.equals(PLAYER))
				thisCallBack.bustCard(player, bustedCard, this);
			else
				thisCallBack.houseBustCard(bustedCard, this);
		}
		
		// Calculate final result (pre-bust total)
		result = result - bustedCard.getScore();
		
		return result;
	}

}
