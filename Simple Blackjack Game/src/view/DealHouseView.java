/**
 * This class defines the view components for Deal House page.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package view;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import controller.BackHomeListener;
import model.SummaryPanelObservable;
import model.interfaces.GameEngine;
import model.interfaces.PlayingCard;

public class DealHouseView extends AppFrame {

	// Delay in milliseconds
	private final int DELAY = 1000;
	
	private GameEngine gameEngine;
	private GameEngineCallbackGUI gameEngineCallback;
	private JLabel statusLabel;
	private JLabel houseResultLabel;
	private JButton returnHomeButton;
	private JTextArea houseCards;
	private int currentHouseResult = 0;

	private final SummaryPanelObservable observable;

	// Constructor
	public DealHouseView(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		gameEngineCallback = new GameEngineCallbackGUI(this);
		gameEngine.addGameEngineCallback(gameEngineCallback);

		super.setLayout(new BorderLayout());

		
		// 1. WEST component - Player summary panel

		SummaryPanel playerSummaryPanel = new SummaryPanel(gameEngine);
		super.add(playerSummaryPanel, BorderLayout.WEST);

		observable = new SummaryPanelObservable();
		observable.addObserver(playerSummaryPanel);

		
		// 2. SOUTH component - Status bar

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

		statusLabel = new JLabel("Status: Please deal cards for all players");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

		statusPanel.add(statusLabel);

		super.add(statusPanel, BorderLayout.SOUTH);

		
		// 3. NORTH component - Button toolbar
		
		JToolBar buttonsToolbar = new JToolBar();
		returnHomeButton = new JButton("Return Home");
		returnHomeButton.setEnabled(false);
		buttonsToolbar.add(returnHomeButton);

		BackHomeListener backHomeListener = new BackHomeListener(this, gameEngine);
		returnHomeButton.addActionListener(backHomeListener);

		super.add(buttonsToolbar, BorderLayout.NORTH);

		
		// 4. CENTER component - Cards panel
		JPanel dealHousePanel = new JPanel(new BorderLayout());
		dealHousePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 10));

		houseResultLabel = new JLabel("House result: 0");
		houseResultLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		houseCards = new JTextArea();
		houseCards.setEditable(false);

		dealHousePanel.add(houseResultLabel, BorderLayout.NORTH);
		dealHousePanel.add(houseCards, BorderLayout.CENTER);

		super.add(dealHousePanel, BorderLayout.CENTER);

		super.showFrame(700,350);
		super.setTitle("Deal House");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		dealHouse();
	}

	/***
	 * Create a background thread to execute the dealHouse(...) method of the game engine.
	 */
	private void dealHouse() {
		SwingWorker<Boolean, Integer> dealHouseThread = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				gameEngine.dealHouse(DELAY);
				return true;
			}

			@Override
			protected void done() {
				returnHomeButton.setEnabled(true);
				observable.notifyDataChanged(null);
			}
		};

		dealHouseThread.execute();
	}

	/***
	 * This method is called by gameEngineCallbackGUI to update the next card on the panel. 
	 * 
	 * @param card - the current card
	 */
	public void updateHouseNextCard(PlayingCard card) {

		// Update house result
		currentHouseResult = currentHouseResult + card.getScore();
		houseResultLabel.setText("House result: " + Integer.toString(currentHouseResult));

		updateStatus("Dealing cards for house. Please wait ...");

		String output = String.format("%s\n", card.toString());
		houseCards.append(output);
	}

	/***
	 * This method is called by gameEngineCallbackGUI to update the busted card on the panel. 
	 * 
	 * @param card - the busted card
	 */
	public void updateHouseBustedCard(PlayingCard card) {

		updateStatus(card.toString() + ". HOUSE BUSTED!");

		String output = String.format("%s [BUSTED]", card.toString());
		houseCards.append(output);
	}

	/***
	 * Update the status message in the status bar.
	 * 
	 * @param status
	 */
	public void updateStatus(String status) {
		statusLabel.setText("Status: " + status);
	}

	/***
	 * Remove the current gameEngineCallBackGUI from the game engine.
	 */
	public void removeCurrentGameCallback() {
		gameEngine.removeGameEngineCallback(gameEngineCallback);
	}

}
