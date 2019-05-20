/**
 * This class contains the event listener for "New Player" function.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AddPlayerView;

public class NewPlayerListener implements ActionListener {

	private AddPlayerView view;

	public NewPlayerListener(AddPlayerView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.addNewPLayer();
	}

}
