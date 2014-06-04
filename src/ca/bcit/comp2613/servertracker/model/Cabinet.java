/**
 * 
 */
package ca.bcit.comp2613.servertracker.model;
import java.util.ArrayList;

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
	// Arraylist of servers
	private ArrayList<Server> servers;

	public static void main(String[] args) {
		
	}

	/**
	 * 
	 */
	public Cabinet() {
		super();
		servers = new ArrayList<Server>();
	}
	/**
	 * @param name
	 */
	public Cabinet(String name) {
		super();
		setName(name);
		servers = new ArrayList<Server>();
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
	}	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cabinet [getName()=" + getName() + ", getFacility()="
				+ getFacility() + ", getPowerCircuits()=" + getPowerCircuits()
				+ ", getId()=" + getId() + "]";
	}
	/*
	 * Method to add a Server to servers ArrayList
	 * @param Server the Server to add
	 */
	public void addServer(Server server) {
		if (! servers.contains(server)) {
			servers.add(server);
		}
	}
	/*
	 * Method to remove a Server from servers ArrayList
	 * @param Server the Server to remove
	 */
	public void removeServer(Server server) {
		if (servers.contains(server)) {
			servers.remove(server);
		}
	}
	/*
	 * @return the list of servers
	 */
	public String listServers() {
		String returnValue = "";
		if (servers.isEmpty()) {
			return null;
		}
		for (Server server:servers) {
			returnValue = returnValue + " " + server.getName() + "(" + server.getServerStatus().getDescription() + ")" + "\n";
		}
		return returnValue;
	}
	
	
}
