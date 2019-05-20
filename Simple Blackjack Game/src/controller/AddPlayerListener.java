/**
 * This class contains the event listener for AddPlayer page.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import model.SummaryPanelObservable;
import model.interfaces.GameEngine;
import view.AddPlayerView;
import view.HomeView;

public class AddPlayerListener implements ActionListener {

	private SummaryPanelObservable observableSubject;
	private GameEngine gameEngine;
	private HomeView homeView;

	public AddPlayerListener(SummaryPanelObservable observableSubject, GameEngine gameEngine, HomeView homeView) {
		this.observableSubject = observableSubject;
		this.gameEngine = gameEngine;
		this.homeView = homeView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AddPlayerView(gameEngine, observableSubject, homeView);
			}
		});
	}

}
