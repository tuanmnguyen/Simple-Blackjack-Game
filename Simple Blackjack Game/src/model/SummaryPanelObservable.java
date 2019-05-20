/**
 * This is the Observable class for the summary panel.
 * 
 * @author Tuan M. Nguyen
 * 
 */
package model;

import java.util.Observable;

public class SummaryPanelObservable extends Observable {

	public SummaryPanelObservable() {
		super();
	}

	public void notifyDataChanged(Object data) {
		setChanged();
		notifyObservers(data);
	}

}
