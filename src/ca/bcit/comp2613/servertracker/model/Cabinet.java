/**
 * 
 */
package ca.bcit.comp2613.servertracker.model;

/**
 * @author Owen
 *
 */
public class Cabinet {
	// Name of cabinet
	private String name;
	// Facility cabinet resides in
	private String facility;
	// Power ccts installed in cabinet
	private PowerCCT powerCircuits;
	// String id because I'm told to have it
	private String id;

	public static void main(String[] args) {
		
	}

	/**
	 * 
	 */
	public Cabinet() {
		super();
	}
	/**
	 * @param name
	 */
	public Cabinet(String name) {
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
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}
	/**
	 * @return the powerCircuits
	 */
	public PowerCCT getPowerCircuits() {
		return powerCircuits;
	}
	/**
	 * @param powerCircuits the powerCircuits to set
	 */
	public void setPowerCircuits(PowerCCT powerCircuits) {
		this.powerCircuits = powerCircuits;
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
		return "Cabinet [getName()=" + getName() + ", getFacility()="
				+ getFacility() + ", getPowerCircuits()=" + getPowerCircuits()
				+ ", getId()=" + getId() + "]";
	}
	

	
	
}
