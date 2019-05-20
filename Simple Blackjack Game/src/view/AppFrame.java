/**
 * This class defines the default frame for all views of the application.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package view;

import javax.swing.JFrame;

public abstract class AppFrame extends JFrame {
	public void showFrame(int width, int height) {
		setBounds(200, 100, width, height);
		setVisible(true);
	}
}