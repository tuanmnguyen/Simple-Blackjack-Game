package model;

import model.interfaces.Player;

/**
 * This class contains the implementations of the PlayingCard interface.
 * 
 * @author Tuan M. Nguyen 
 * 
 */

public class SimplePlayer implements Player {

	// Attributes
	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private int result;
	
	// Constructor
	public SimplePlayer(String playerId, String playerName, int points) { 
		this.playerId = playerId;
		this.playerName = playerName;
		this.points = points;
		this.bet = 0;
		this.result = 0;
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String getPlayerId() {
		return this.playerId;
	}

	@Override
	public boolean placeBet(int bet) {
		if (bet >= 0 && bet <= points) {
			this.bet = bet;
			return true;
		}
		else
			return false;
	}

	@Override
	public int getBet() {
		return this.bet;
	}

	@Override
	public void resetBet() {
		this.bet = 0;
	}

	@Override
	public int getResult() {
		return this.result;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return String.format("Player: id=%s, name=%s, points=%d", this.playerId, this.playerName, this.points);
	}
}
