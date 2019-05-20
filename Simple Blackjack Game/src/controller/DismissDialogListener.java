/**
 * This class contains the event listener for the dismiss dialog.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class DismissDialogListener implements ActionListener {

	private JDialog dialog;

	public DismissDialogListener(JDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.setVisible(false);
		dialog.dispose();
	}

}
