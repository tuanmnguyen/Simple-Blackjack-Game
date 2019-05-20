/**
 * This class defines the view components for the Summary panel.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class SummaryPanel extends JPanel implements Observer {

	private ArrayList<Player> players;
	private GameEngine gameEngine;
	GridLayout playerSummaryList;

	// Constructor
	public SummaryPanel(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.players = new ArrayList<Player>(gameEngine.getAllPlayers());
		CreateSummaryTable();
	}

	/***
	 * Create table to display the summary of all players.
	 */
	private void CreateSummaryTable() {
		super.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 10));

		JLabel headerID = new JLabel("ID");
		JLabel headerName = new JLabel("Name");
		JLabel headerPoints = new JLabel("Points");

		Font font = headerID.getFont();
		headerID.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		headerName.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		headerPoints.setFont(font.deriveFont(font.getStyle() | Font.BOLD));

		int noOfPlayers = players.size();
		int noOfRows = noOfPlayers + 1;

		playerSummaryList = new GridLayout(noOfRows, 3);
		playerSummaryList.setHgap(10);

		super.setLayout(playerSummaryList);

		super.add(headerID);
		super.add(headerName);
		super.add(headerPoints);

		for (Player p : players) {
			JLabel playerID = new JLabel(p.getPlayerId());
			JLabel playerName = new JLabel(p.getPlayerName());
			JLabel playerPoints = new JLabel(Integer.toString(p.getPoints()));

			super.add(playerID);
			super.add(playerName);
			super.add(playerPoints);
		}
	}

	/***
	 * Update the data in the summary table.
	 */
	@Override
	public void update(Observable o, Object data) {
		super.removeAll();
		this.players = new ArrayList<Player>(gameEngine.getAllPlayers());
		CreateSummaryTable();
		super.updateUI();
	}

}
