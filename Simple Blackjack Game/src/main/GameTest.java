/**
 * This is the main class to run the game.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package main;

import javax.swing.SwingUtilities;

import model.GameEngineImpl;
import model.SimplePlayer;
import validate.Validator;
import view.HomeView;

public class GameTest {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				// Validator PASSED
				Validator.validate(false);

				GameEngineImpl newGameEngine = new GameEngineImpl();

				// Load default players into the game
				SimplePlayer player1 = new SimplePlayer("1", "johnwick1", 1000);
				SimplePlayer player2 = new SimplePlayer("2", "thecrazyfox", 1000);

				newGameEngine.addPlayer(player1);
				newGameEngine.addPlayer(player2);

				new HomeView(newGameEngine);
			}
		});
	}
}
