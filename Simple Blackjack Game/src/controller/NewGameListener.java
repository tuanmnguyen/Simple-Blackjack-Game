/**
 * This class contains the event listener for "New Game" function.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import main.GameTest;

public class NewGameListener implements ActionListener {

	JFrame currentView;

	public NewGameListener(JFrame currentView) {
		this.currentView = currentView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GameTest.main(null);
		currentView.setVisible(false);
		currentView.dispose();
	}

}
