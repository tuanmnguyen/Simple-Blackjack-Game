/**
 * This class defines the view components for Add Player page.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.NewPlayerListener;
import helper.MyValidator;
import model.SimplePlayer;
import model.SummaryPanelObservable;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class AddPlayerView extends AppFrame {

	// View components
	private GameEngine gameEngine;
	private SummaryPanelObservable observable;
	private HomeView homeView;
	private JTextField nameInput;
	private JTextField pointsInput;
	private JLabel inputValidationMessage;

	// Constructor
	public AddPlayerView(GameEngine gameEngine, SummaryPanelObservable observable, HomeView homeView) {
		this.gameEngine = gameEngine;
		this.observable = observable;
		this.homeView = homeView;

		JPanel playerDetailsPanel = new JPanel(new GridLayout(6, 1));
		playerDetailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel nameLabel = new JLabel("Name");
		nameInput = new JTextField();
		JLabel pointsLabel = new JLabel("Initial points");
		pointsInput = new JTextField();
		JButton addButton = new JButton("ADD");

		inputValidationMessage = new JLabel();
		inputValidationMessage.setForeground(Color.RED);

		NewPlayerListener addNewPlayerListener = new NewPlayerListener(this);
		addButton.addActionListener(addNewPlayerListener);

		playerDetailsPanel.add(nameLabel);
		playerDetailsPanel.add(nameInput);
		playerDetailsPanel.add(pointsLabel);
		playerDetailsPanel.add(pointsInput);
		playerDetailsPanel.add(inputValidationMessage);
		playerDetailsPanel.add(addButton);

		super.add(playerDetailsPanel);

		super.showFrame(300, 250);
		super.setTitle("Add New Player");
	}

	/***
	 * Add a new player to the to the game engine 
	 */
	public void addNewPLayer() {

		MyValidator validator = new MyValidator();

		// Validate if the data are entered, if true, add the player to the game engine
		if (validator.arePlayerDetailsFilled(nameInput.getText(), pointsInput.getText())) {
			int playerListSize = gameEngine.getAllPlayers().size();

			String newPlayerID = Integer.toString(playerListSize + 1);
			String newPlayerName = nameInput.getText();
			int newPlayerPoints = Integer.parseInt(pointsInput.getText());

			Player newPlayer = new SimplePlayer(newPlayerID, newPlayerName, newPlayerPoints);

			gameEngine.addPlayer(newPlayer);

			observable.notifyDataChanged(null);
			homeView.updateStatus(newPlayerName + " added");

			clearScreen();
		} 
		else
			inputValidationMessage.setText("Please enter player name & initial points.");

	}

	/***
	 * Clear all data on the panel
	 */
	private void clearScreen() {
		inputValidationMessage.setText("");
		nameInput.setText("");
		pointsInput.setText("");
	}

}
