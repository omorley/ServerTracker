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

@Entity
public class PowerCCT {
	// Name of cct
	private String name;
	// UPS cct is on
	private String ups;
	// Rating of cct
	private int amperage;
	// String id because I'm told to have it
	@Id
	private String id;
	@ManyToOne
	private Cabinet cabinet;
	
	
	public Cabinet getCabinet() {
		return cabinet;
	}
	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}
	public static void main(String[] args) {
		
	}
	/**
	 * 
	 */
	public PowerCCT() {
		super();
		setId(UUID.randomUUID().toString());
	}
	/**
	 * @param name
	 */
	public PowerCCT(String name) {
		super();
		setName(name);
		setId(UUID.randomUUID().toString());
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the ups
	 */
	public String getUps() {
		return ups;
	}
	/**
	 * @param ups the ups to set
	 */
	public void setUps(String ups) {
		this.ups = ups;
	}
	/**
	 * @return the amperage
	 */
	public int getAmperage() {
		return amperage;
	}
	/**
	 * @param amperage the amperage to set
	 */
	public void setAmperage(int amperage) {
		this.amperage = amperage;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PowerCCT [getName()=" + getName() + ", getUps()=" + getUps()
				+ ", getAmperage()=" + getAmperage() + ", getId()=" + getId()
				+ "]";
	}
	



}
