/**
 * This class defines the callback GUI of the game engine.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package view;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {

	AppFrame view;

	public GameEngineCallbackGUI(AppFrame view) {
		this.view = view;
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		DealPlayersView dealPlayersView = (DealPlayersView) view;
		dealPlayersView.updateNextCard(player,card);
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		DealPlayersView dealPlayersView = (DealPlayersView) view;
		dealPlayersView.updateBustedCard(player,card);
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		DealPlayersView dealPlayersView = (DealPlayersView) view;
		String status = String.format("DEAL COMPLETED. %s, final result=%d", player.getPlayerName(),
				player.getResult());
		dealPlayersView.updateStatus(status);
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		DealHouseView dealHouseView = (DealHouseView) view;
		dealHouseView.updateHouseNextCard(card);
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		DealHouseView dealHouseView = (DealHouseView) view;
		dealHouseView.updateHouseBustedCard(card);
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		DealHouseView dealHouseView = (DealHouseView) view;
		String status = String.format("Deal completed. Final house result: %s, player scores have been updated.",
				result);
		dealHouseView.updateStatus(status);
	}

}
