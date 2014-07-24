/**
 * 
 */
package ca.bcit.comp2613.servertracker.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import java.util.UUID;

/**
 * @author Owen
 *
 */

public class AggregatePower {

	private PowerCCT powerCCT;
	private double totalPower;

	/**
	 * @param name
	 */
	public AggregatePower(PowerCCT powerCCT) {
		super();
		setCircuit(powerCCT);
	}
	
	public PowerCCT getCircuit() {
		return powerCCT;
	}

	public void setCircuit(PowerCCT newPowerCCT) {
		powerCCT = newPowerCCT;
	}

	public void clearPower() {
		totalPower = 0;
	}
	
	public void addPower(double power) {
		totalPower += power;
	}

	public void subtractPower(double power) {
		totalPower -= power;
	}
	
	public double getTotalPower() {
		return totalPower;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return getName();
//		return "PowerCCT [getName()=" + getName() + ", getUps()=" + getUps()
//				+ ", getAmperage()=" + getAmperage() + ", getId()=" + getId()
//				+ "]";
}
	



