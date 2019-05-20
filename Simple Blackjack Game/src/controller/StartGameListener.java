/**
 * This class contains the event listener for "Start Game" function.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import view.DealPlayersView;
import view.HomeView;

public class StartGameListener implements ActionListener {

	private HomeView currentView;
	private GameEngine gameEngine;

	public StartGameListener(HomeView currentView, GameEngine gameEngine) {
		this.currentView = currentView;
		this.gameEngine = gameEngine;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gameEngine.getShuffledDeck();
				new DealPlayersView(gameEngine);
			}
		});

		currentView.setVisible(false);
		currentView.dispose();
	}

}
