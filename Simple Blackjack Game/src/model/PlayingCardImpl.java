package model;

import model.interfaces.PlayingCard;

/**
 * This class contains the implementations of PlayingCard interface.
 * 
 * @author Tuan M. Nguyen 
 * 
 */

public class PlayingCardImpl implements PlayingCard {
	
	// Attributes
	private Suit suit;
	private Value value;
	private int score;
	
	// Constructor
	public PlayingCardImpl(Suit suit, Value value, int score) {
		this.suit = suit;
		this.value = value;
		this.score = score;
	}

	@Override
	public Suit getSuit() {
		return this.suit;
	}

	@Override
	public Value getValue() {
		return this.value;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public boolean equals(PlayingCard card) {
		return (this.suit == card.getSuit() && this.value == card.getValue()) ? true: false;
	}

	@Override
	public String toString() {
		return String.format("Suit: %s, Value: %s, Score: %d", suit, value, score);
	}

}
