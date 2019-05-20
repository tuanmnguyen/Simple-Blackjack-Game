/**
 * This class contains the event listener for BackHome page.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.DealHouseView;
import view.HomeView;

public class BackHomeListener implements ActionListener {

	private DealHouseView view;
	private GameEngine gameEngine;

	public BackHomeListener(DealHouseView view, GameEngine gameEngine) {
		this.view = view;
		this.gameEngine = gameEngine;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Create new game engine
		GameEngine newGameEngine = new GameEngineImpl();

		// Add existing players to new game engine
		ArrayList<Player> currentPlayers = new ArrayList<Player>(gameEngine.getAllPlayers());

		for (Player player : currentPlayers)
			newGameEngine.addPlayer(player);

		new HomeView(newGameEngine);
		view.removeCurrentGameCallback();
		view.setVisible(false);
		view.dispose();
	}

}
