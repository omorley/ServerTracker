package ca.bcit.comp2613.servertracker.model;
public enum ServerStatus {
	PROD("Production"), DEV("Development"), STG("Staging");
	
	ServerStatus(String description) {
		this.description = description;
	}
	private String description;
	public String getDescription() {
		return description;
	}
}