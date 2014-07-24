/**
 * 
 */
package ca.bcit.comp2613.servertracker.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * @author Owen
 *
 */

@Entity
public class Cabinet {
	// Name of cabinet
	private String name;
	// Facility cabinet resides in
	private String facility;
	// Power ccts installed in cabinet
	//private PowerCCT powerCircuits;
	
	
	// String id because I'm told to have it - yup -> Henry ;)
	@Id
	private String id;
	// Arraylist of servers
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Server> servers;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<PowerCCT> powerCircuits;

	public static void main(String[] args) {
		
	}

	/**
	 * 
	 */
	public Cabinet() {
		super();
		servers = new ArrayList<Server>();
		powerCircuits = new ArrayList<PowerCCT>();
	}
	/**
	 * @param name
	 */
	public Cabinet(String name) {
		super();
		setName(name);
		servers = new ArrayList<Server>();
		powerCircuits = new ArrayList<PowerCCT>();
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
		return getName();
//		return "Cabinet [getName()=" + getName() + ", getFacility()="
//				+ getFacility() + ", getPowerCircuits()=" + listPowerCCTs()
//				+ ", getId()=" + getId() + "]";
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

	/**
	 * @return ArrayList containing the servers
	 */
	public List<Server> getServersArray() {
		return servers;
	}

	public void clearServersArray() {
		servers.clear();
	}
	/*
	 * Method to add a circuit to PowerCCT ArrayList
	 * @param PowerCCT the circuit to add
	 */
	public void addPowerCCT(PowerCCT circuit) {
		if (! powerCircuits.contains(circuit)) {
			powerCircuits.add(circuit);
		}
	}
	/*
	 * Method to remove a circuit from PowerCCT ArrayList
	 * @param PowerCCT the circuit to remove
	 */
	public void removePowerCCT(PowerCCT circuit) {
		if (powerCircuits.contains(circuit)) {
			powerCircuits.remove(circuit);
		}
	}
	
	/**
	 * @return ArrayList containing the power circuits
	 */
	public List<PowerCCT> getPowerCCTArray() {
		return powerCircuits;
	}

	/*
	 * @return the list of circuits
	 */
	public String listPowerCCTs() {
		String returnValue = "";
		if (powerCircuits.isEmpty()) {
			return null;
		}
		for (PowerCCT circuit: powerCircuits) {
			returnValue = returnValue + " " + circuit.getName() + "\n";
		}
		return returnValue;
	}
	
	/**
	 * @return Number of power circuits installed in cabinet
	 */
	public int numberOfCCTs() {
		return powerCircuits.size();
	}
}
