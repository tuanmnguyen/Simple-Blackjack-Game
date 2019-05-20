/**
 * This class defines the view components for the Home page of the application.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.AddPlayerListener;
import controller.ExitListener;
import controller.NewGameListener;
import controller.StartGameListener;
import model.SummaryPanelObservable;
import model.interfaces.GameEngine;

public class HomeView extends AppFrame {

	private GameEngine gameEngine;
	private JLabel statusLabel;

	// Constructor
	public HomeView(GameEngine gameEngine) {
		this.gameEngine = gameEngine;

		// Add callback for console logging
		GameEngineCallbackImpl gameEngineCallbackConsole = new GameEngineCallbackImpl();
		gameEngine.addGameEngineCallback(gameEngineCallbackConsole);

		setLayout(new BorderLayout());
		super.showFrame(650, 350);
		super.setTitle("Card Game");

		// 1. WEST component - Player summary panel

		SummaryPanel playerSummaryPanel = new SummaryPanel(gameEngine);
		add(playerSummaryPanel, BorderLayout.CENTER);

		// 2. SOUTH component - Status bar

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

		statusLabel = new JLabel("Status: New game");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

		statusPanel.add(statusLabel);

		super.add(statusPanel, BorderLayout.SOUTH);

		// 3. NORTH component - Buttons toolbar

		JToolBar buttonsToolbar = new JToolBar();

		JButton playGameButton = new JButton("Start Game >");
		JButton addPlayerButton = new JButton("+ Add Player");

		final SummaryPanelObservable observable = new SummaryPanelObservable();
		observable.addObserver(playerSummaryPanel);

		StartGameListener playGameListener = new StartGameListener(this, gameEngine);
		AddPlayerListener addPlayerListener = new AddPlayerListener(observable, gameEngine, this);

		addPlayerButton.addActionListener(addPlayerListener);
		playGameButton.addActionListener(playGameListener);

		buttonsToolbar.add(addPlayerButton);
		buttonsToolbar.addSeparator();
		buttonsToolbar.add(playGameButton);

		super.add(buttonsToolbar, BorderLayout.NORTH);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		CreateMenu();
	}

	/***
	 * Create a pull-down menu for the frame.
	 */
	private void CreateMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		JMenuItem newItem = new JMenuItem("New Game", KeyEvent.VK_N);
		JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);

		NewGameListener newGameListener = new NewGameListener(this);
		ExitListener exitListener = new ExitListener(this);

		exitItem.addActionListener(exitListener);
		newItem.addActionListener(newGameListener);

		fileMenu.add(newItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		menubar.add(fileMenu);
		setJMenuBar(menubar);

	}
	
	/***
	 * Update the status in the status bar.
	 * 
	 * @param status - the status message
	 */
	public void updateStatus(String status) {
		statusLabel.setText("STATUS: " + status);
	}

}
