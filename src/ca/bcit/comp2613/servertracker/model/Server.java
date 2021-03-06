/**
 * 
 */
package ca.bcit.comp2613.servertracker.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Owen
 *
 */
@Entity
public class Server {
	// Server's name
	private String name;
	// IP of server
	private String ip;
	// Use for server
	private String purpose;
	// Logical owner of server
	private String owner;
	// Projected power utilization
	private double projectedPower = 0;
	// Number of filled sockets in server
	private int processors = 0;
	// Number of cores per processor
	private int cores = 0;
	// Gigabytes of ram in server
	private int memory = 0;
	// Service tag of server
	private String serviceTag;
	// Date of warranty expiration
	private String warrantyExpiration;
	// String id because I'm told to have it
	@Id
	private String id;
//	// External storage assigned to server
//	private ExternalStorage externalStorage;
	// Power cct used for server
	@ManyToOne
	private PowerCCT powerCCT;
	
	private ServerStatus serverStatus;
	
	
	
	/**
	 * 
	 */
	public Server() {
		super();
	}
	/**
	 * @param name
	 */
	public Server(String name) {
		super();
		setName(name);
	}
	/**
	 * @param id
	 * @param name
	 * @param ip
	 */
	public Server(String id,String name,String ip) {
		super();
		setId(id);
		setName(name);
		setIp(ip);
	}
	/**
	 * @param id
	 * @param name
	 * @param ip
	 * @param powerCCT
	 */
	public Server(String id,String name,String ip,PowerCCT powerCCT) {
		super();
		setId(id);
		setName(name);
		setIp(ip);
		setPowerCCT(powerCCT);
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
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
	 * @return the processors
	 */
	public int getProcessors() {
		return processors;
	}
	/**
	 * @param processors the processors to set
	 */
	public void setProcessors(int processors) {
		this.processors = processors;
	}
	/**
	 * @return the cores
	 */
	public int getCores() {
		return cores;
	}
	/**
	 * @param cores the cores to set
	 */
	public void setCores(int cores) {
		this.cores = cores;
	}
	/**
	 * @return the memory
	 */
	public int getMemory() {
		return memory;
	}
	/**
	 * @param memory the memory to set
	 */
	public void setMemory(int memory) {
		this.memory = memory;
	}
	/**
	 * @return the serviceTag
	 */
	public String getServiceTag() {
		return serviceTag;
	}
	/**
	 * @param serviceTag the serviceTag to set
	 */
	public void setServiceTag(String serviceTag) {
		this.serviceTag = serviceTag;
	}
	/**
	 * @return the warrantyExpiration
	 */
	public String getWarrantyExpiration() {
		return warrantyExpiration;
	}
	/**
	 * @param warrantyExpiration the warrantyExpiration to set
	 */
	public void setWarrantyExpiration(String warrantyExpiration) {
		this.warrantyExpiration = warrantyExpiration;
	}
//	/**
//	 * @return the externalStorage
//	 */
//	public ExternalStorage getExternalStorage() {
//		return externalStorage;
//	}
//	/**
//	 * @param externalStorageStorage the externalStorage to set
//	 */
//	public void setExternalStorage(ExternalStorage externalStorage) {
//		this.externalStorage = externalStorage;
//	}
	/**
	 * @return the powerCCT
	 */
	public PowerCCT getPowerCCT() {
		return powerCCT;
	}
	/**
	 * @param powerCCT the powerCCT to set
	 */
	public void setPowerCCT(PowerCCT powerCCT) {
		this.powerCCT = powerCCT;
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
	
	
	
	public ServerStatus getServerStatus() {
		return serverStatus;
	}
	public void setServerStatus(ServerStatus serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
//		return "Server [getName()=" + getName() + ", getCabinet()="
//				//+ getCabinet() ???? 
//				+ ", getIp()=" + getIp() + ", getPurpose()="
//				+ getPurpose() + ", getOwner()=" + getOwner()
//				+ ", getProjectedPower()=" + getProjectedPower()
//				+ ", getProcessors()=" + getProcessors() + ", getCores()="
//				+ getCores() + ", getMemory()=" + getMemory()
//				+ ", getServiceTag()=" + getServiceTag()
//				+ ", getWarrantyExpiration()=" + getWarrantyExpiration()
////				+ ", getExternalStorage()=" + getExternalStorage()
//				+ ", getPowerCCT()=" + getPowerCCT() + ", getId()=" + getId()
//				+ "]";
	}



	
}
