/**
 * This class defines the view components for Deal Players page.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import controller.DealPlayerListener;
import controller.DismissDialogListener;
import controller.PlayerSelectionListener;
import helper.MyValidator;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class DealPlayersView extends AppFrame {

	// Delay in milliseconds
	private final int DELAY = 1000;
	
	private ArrayList<Player> players;
	private GameEngineCallbackGUI gameEngineCallback;
	private GameEngine gameEngine;

	private JLabel playerResultLabel;
	private JLabel statusLabel;
	private ArrayList<PlayingCard>[] playerCards;
	private JComboBox<String> playerSelection;
	private JButton dealPlayerButton;
	private JTextField betAmount;
	private JTextArea dealtCards;

	// Constructor
	public DealPlayersView(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		gameEngineCallback = new GameEngineCallbackGUI(this);
		gameEngine.addGameEngineCallback(gameEngineCallback);

		fetchPlayerListFromEngine();

		InitialiseCardList();

		super.setLayout(new BorderLayout());

		// 1. WEST component - Player summary panel

		SummaryPanel playerSummaryPanel = new SummaryPanel(gameEngine);
		super.add(playerSummaryPanel, BorderLayout.WEST);

		// 2. SOUTH component - Status bar

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

		statusLabel = new JLabel("Status: The player is not dealt");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

		statusPanel.add(statusLabel);

		super.add(statusPanel, BorderLayout.SOUTH);

		// 2. CENTER component - Player deal panel
		JPanel dealPlayerPanel = new JPanel(new BorderLayout());
		dealPlayerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 10));

		dealtCards = new JTextArea();
		dealtCards.setEditable(false);

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		JPanel playerBet = new JPanel(flowLayout);
		JLabel betLabel = new JLabel("Enter bet amount: ");
		betAmount = new JTextField();
		betAmount.setColumns(9);

		playerBet.add(betLabel);
		playerBet.add(betAmount);

		playerResultLabel = new JLabel("0");

		JPanel resultLayout = new JPanel(flowLayout);
		resultLayout.add(new JLabel("Player result: "));
		resultLayout.add(playerResultLabel);

		dealPlayerPanel.add(playerBet, BorderLayout.NORTH);
		dealPlayerPanel.add(dealtCards, BorderLayout.CENTER);
		dealPlayerPanel.add(resultLayout, BorderLayout.SOUTH);
		super.add(dealPlayerPanel, BorderLayout.CENTER);

		// NORTH component - Button tool bar
		JToolBar buttonsToolbar = new JToolBar();

		dealPlayerButton = new JButton("PLACE BET & DEAL");

		DealPlayerListener dealPlayerListener = new DealPlayerListener(this);
		dealPlayerButton.addActionListener(dealPlayerListener);

		// 2.2 - Player selection ComboBox

		String[] playerNames = new String[players.size()];

		for (int i = 0; i < playerNames.length; i++)
			playerNames[i] = players.get(i).getPlayerName();

		playerSelection = new JComboBox<>(playerNames);
		playerSelection.setSelectedIndex(0);

		PlayerSelectionListener playerSelectionListener = new PlayerSelectionListener(this);
		playerSelection.addActionListener(playerSelectionListener);

		buttonsToolbar.add(dealPlayerButton);
		buttonsToolbar.addSeparator();
		buttonsToolbar.add(playerSelection);

		super.add(buttonsToolbar, BorderLayout.NORTH);

		// Create window frame
		super.showFrame(780, 380);
		super.setTitle("Deal Players");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	
	// --- Section 1: PRIVATE METHODS ---

	/***
	 * Initialise an array to hold the cards dealt to the players.
	 */
	private void InitialiseCardList() {
		playerCards = new ArrayList[players.size()];

		for (int i = 0; i < playerCards.length; i++)
			playerCards[i] = new ArrayList<PlayingCard>();
	}

	/***
	 * Get the player list from the game engine.
	 */
	private void fetchPlayerListFromEngine() {
		players = new ArrayList<Player>(gameEngine.getAllPlayers());
	}

	/***
	 * Open a new frame for dealing house
	 */
	private void runDealHouse() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DealHouseView(gameEngine);
			}
		});

		this.removeCurrentGameCallback();
		this.setVisible(false);
		this.dispose();
	}

	/***
	 * Display the dialog box for invalid bet.
	 * 
	 * @param message - the message to be displayed
	 */
	private void displayInvalidBetDialog(String message) {
		JDialog invalidBetDialog = new JDialog(this, "Invalid bet");
		JPanel dialogLayout = new JPanel();
		JLabel dialogMessage = new JLabel(message);
		dialogMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton okButton = new JButton("OK");
		okButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		dialogLayout.add(dialogMessage);
		dialogLayout.add(okButton);
		invalidBetDialog.add(dialogLayout);
		invalidBetDialog.setSize(280, 90);
		invalidBetDialog.setLocation(350, 200);
		invalidBetDialog.setVisible(true);

		DismissDialogListener okButtonListener = new DismissDialogListener(invalidBetDialog);
		okButton.addActionListener(okButtonListener);
	}

	// --- Section 2: PUBLIC METHODS ---

	/***
	 * This method is called by gameEngineCallbackGUI to update next card on the panel.
	 * 
	 * @param player - the current player
	 * @param card - the current card
	 */
	public void updateNextCard(Player player, PlayingCard card) {
		updateStatus("Dealing cards for the player. Please wait ...");
		int selectedIndex = players.indexOf(player);
		playerCards[selectedIndex].add(card);

		if (selectedIndex == playerSelection.getSelectedIndex())
			repaintCardPanel();
	}

	/***
	 * This method is called by gameEngineCallbackGUI to update next card on the panel.
	 * 
	 * @param player - the current player
	 * @param card - the busted card
	 */
	public void updateBustedCard(Player player, PlayingCard card) {
		updateStatus(card.toString() + ". YOU BUSTED!");
		int selectedIndex = players.indexOf(player);
		playerCards[selectedIndex].add(card);

		if (selectedIndex == playerSelection.getSelectedIndex())
			displayDealtCards();
	}

	/***
	 * Update the status in the status bar. 
	 * 
	 * @param status - the status message
	 */
	public void updateStatus(String status) {
		statusLabel.setText("Status: " + status);
	}

	/***
	 * Refresh the card panel for each player when dealing the cards to a player.
	 */
	public void repaintCardPanel() {
		int selectedIndex = playerSelection.getSelectedIndex();
		dealtCards.setText("");
		int playerResult = 0;

		if (playerCards[selectedIndex].size() > 0) {
			for (PlayingCard card : playerCards[selectedIndex]) {

				String output = String.format("%s\n", card.toString());
				dealtCards.append(output);

				playerResult += card.getScore();
			}
			playerResult -= playerCards[selectedIndex].get(playerCards[selectedIndex].size() - 1).getScore();
		}

		playerResultLabel.setText(Integer.toString(playerResult));
	}

	/***
	 * Display all dealt cards for the current player once dealing is completed
	 */
	public void displayDealtCards() {
		int selectedIndex = playerSelection.getSelectedIndex();
		dealtCards.setText("");
		int playerResult = 0;

		if (playerCards[selectedIndex].size() > 0) {
			for (PlayingCard card : playerCards[selectedIndex]) {

				if (playerCards[selectedIndex].indexOf(card) != (playerCards[selectedIndex].size() - 1)) {
					String output = String.format("%s\n", card.toString());
					dealtCards.append(output);
				} else {
					String output = String.format("%s [BUSTED]", card.toString());
					dealtCards.append(output);
				}

				playerResult += card.getScore();
			}
			playerResult -= playerCards[selectedIndex].get(playerCards[selectedIndex].size() - 1).getScore();
		}

		playerResultLabel.setText(Integer.toString(playerResult));
	}

	/***
	 * Remove the current gameEngineCallBackGUI from the game engine
	 */
	public void removeCurrentGameCallback() {
		gameEngine.removeGameEngineCallback(gameEngineCallback);
	}

	/***
	 * Checks if the current player is dealt or not.
	 * 
	 * @return true if cards are dealt to the player
	 */
	public boolean isPlayerDealt() {
		int selectedIndex = playerSelection.getSelectedIndex();
		return (playerCards[selectedIndex].size() > 0);
	}

	/***
	 * Make the deal button enabled or disabled.
	 * 
	 * @param isEnabled
	 */
	public void enableDealButton(boolean isEnabled) {
		dealPlayerButton.setEnabled(isEnabled);
	}

	/***
	 * Set whether the bet input to either editable or uneditable.
	 * 
	 * @param isEditable - true for editable
	 */
	public void setEditableBetAmount(boolean isEditable) {
		betAmount.setEditable(isEditable);
	}

	/***
	 * Create a background thread to execute the dealPlayer(...) method of the game engine.
	 */
	public void dealPlayer() {

		try {
			int bet = Integer.parseInt(betAmount.getText());
			int selectedIndex = playerSelection.getSelectedIndex();

			Player currentPlayer = players.get(selectedIndex);
			MyValidator validator = new MyValidator();

			if (currentPlayer.placeBet(bet)) {
				fetchPlayerListFromEngine();

				SwingWorker<Boolean, Integer> dealPlayerThread = new SwingWorker<Boolean, Integer>() {
					@Override
					protected Boolean doInBackground() throws Exception {
						dealPlayerButton.setEnabled(false);
						betAmount.setEditable(false);
						gameEngine.dealPlayer(currentPlayer, DELAY);
						return true;
					}

					@Override
					protected void done() {
						playerSelection.setEnabled(true);

						if (validator.isLastPlayer(playerCards))
							runDealHouse();
					}
				};

				dealPlayerThread.execute();
			} else {
				String errorMessage = "Invalid bet. Please re-enter the amount.";
				displayInvalidBetDialog(errorMessage);
			}
		} catch (NumberFormatException e) {
			String errorMessage = "Invalid format. Please enter numbers only.";
			displayInvalidBetDialog(errorMessage);
		}
	}

	/***
	 * Display the bet amount of the current player.
	 */
	public void displayBetAmount() {
		int selectedIndex = playerSelection.getSelectedIndex();
		Player currentPlayer = players.get(selectedIndex);

		if (currentPlayer.getBet() > 0)
			betAmount.setText(Integer.toString(currentPlayer.getBet()));
		else
			betAmount.setText("");
	}
}
