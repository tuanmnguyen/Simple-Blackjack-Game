/**
 * This class contains the event listener for "Player Selection" function.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.DealPlayersView;

public class PlayerSelectionListener implements ActionListener {

	private DealPlayersView dealPlayersView;

	public PlayerSelectionListener(DealPlayersView dealPlayersView) {
		this.dealPlayersView = dealPlayersView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		dealPlayersView.displayDealtCards();
		dealPlayersView.displayBetAmount();

		if (dealPlayersView.isPlayerDealt()) {
			dealPlayersView.updateStatus("The player is dealt");
			dealPlayersView.enableDealButton(false);
			dealPlayersView.setEditableBetAmount(false);
		} else {
			dealPlayersView.updateStatus("The player is not dealt");
			dealPlayersView.enableDealButton(true);
			dealPlayersView.setEditableBetAmount(true);

		}

	}

}
