package ca.bcit.comp2613.servertracker.model;

import javax.swing.table.DefaultTableModel;

public class SwingServerTrackerModel extends DefaultTableModel {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1923383067214977391L;

	@Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }

}
