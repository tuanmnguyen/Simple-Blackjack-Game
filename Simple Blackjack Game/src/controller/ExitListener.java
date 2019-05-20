/**
 * This class contains the event listener for "Exit" function.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ExitListener implements ActionListener {

	JFrame currentView;

	public ExitListener(JFrame currentView) {
		this.currentView = currentView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
