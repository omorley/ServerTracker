/**
 * 
 */
package ca.bcit.comp2613.servertracker.model;

/**
 * @author Owen
 *
 */
public class ExternalStorage {
	// Name of external storage
	private String name;
	// Projected power usage
	private double projectedPower;
	// String id because I'm told to have it
	private String id;

	
	public static void main(String[] args) {
		
	}
	/**
	 * 
	 */
	public ExternalStorage() {
		super();
	}
	/**
	 * @param name
	 */
	public ExternalStorage(String name) {
		super();
		setName(name);
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
	 * @return the projectedPower
	 */
	public double getProjectedPower() {
		return projectedPower;
	}
	/**
	 * @param projectedPower the projectedPower to set
	 */
	public void setProjectedPower(double projectedPower) {
		this.projectedPower = projectedPower;
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
		return "ExternalStorage [getName()=" + getName()
				+ ", getProjectedPower()=" + getProjectedPower() + ", getId()="
				+ getId() + "]";
	}


}
