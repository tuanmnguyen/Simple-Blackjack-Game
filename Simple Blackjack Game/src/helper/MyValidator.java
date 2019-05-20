/**
 * This is a custom class for validation methods.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package helper;

import java.util.ArrayList;

import model.interfaces.PlayingCard;

public class MyValidator {

	/***
	 * Check if all players are dealt with cards or not.
	 * 
	 * @param playerCards - a list storing all players' cards
	 * @return true - if all players received their cards
	 */
	public boolean isLastPlayer(ArrayList<PlayingCard>[] playerCards) {
		int playersNotDealtCount = 0;
		
		for (ArrayList<PlayingCard> cards : playerCards) {
			if (cards.size() == 0)
				playersNotDealtCount += 1;
		}

		if (playersNotDealtCount == 0)
			return true;
		else
			return false;
	}

	/***
	 * Validates if the player details are filled.
	 * 
	 * @param nameValue - player name
	 * @param pointsValue - player points
	 * @return true - if the details are entered
	 */
	public boolean arePlayerDetailsFilled(String nameValue, String pointsValue) {
		return (nameValue.length() > 0 && pointsValue.length() > 0);
	}
}
