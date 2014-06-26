package ca.bcit.comp2613.servertracker.model;

import javax.swing.table.DefaultTableModel;

public class SwingServerTrackerModel extends DefaultTableModel {

	 @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }

}
