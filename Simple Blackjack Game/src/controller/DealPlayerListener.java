/**
 * This class contains the event listener for DealPlayer page.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.DealPlayersView;

public class DealPlayerListener implements ActionListener {

	private DealPlayersView view;

	public DealPlayerListener(DealPlayersView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.dealPlayer();
	}

}
