/**
 * 
 */
package ca.bcit.comp2613.a00251471.util;

import java.util.ArrayList;
import java.util.Random;

import ca.bcit.comp2613.a00251471.util.Helper;
import ca.bcit.comp2613.servertracker.model.Cabinet;
import ca.bcit.comp2613.servertracker.model.Server;

/**
 * @author Owen
 *
 */
public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//testSearch();
		//random100();
		fillCabinets();
	}

	// Test population of 10 cabinets
	public static void fillCabinets() {
		int serversPerCab = 15;
		int currentServer = 0;
		ArrayList<Server> servers = Helper.createServers();
		ArrayList<Cabinet> cabinets = Helper.createCabinets(10);
		for (Cabinet cabinet:cabinets) {
			for (int i = 0; i < serversPerCab; i++) {
				cabinet.addServer(servers.get(currentServer));
				currentServer++;
			}
		}
		for (Cabinet cabinet:cabinets) {
System.out.println("Servers in " + cabinet.getName() + ":\n" + cabinet.listServers() + "\n");
		}
	}
	
//	// Test creation of 100 random objects
//	public static void random100() {
//		Random rand = new Random();
//		ArrayList<Server> servers = Helper.createServers(rand.nextInt(200));
//	}
	// Test searching for name in objects
	public static void nameSearch() {
		Random rand = new Random();
		ArrayList<Server> servers = Helper.createServers(rand.nextInt(200));
		System.out.println("Searching servers for Asace:");
		for (Server server : Helper.findServerExactName(servers,"Asace")) {
			System.out.println(server.getName());		
		} 
		System.out.println("Searching servers for Alsace:");
		for (Server server : Helper.findServerExactName(servers,"Alsace")) {
			System.out.println(server.getName());		
		} 
		System.out.println("Searching servers for G.*:");
		for (Server server : Helper.findServerRegexName(servers,"G.*")) {
			System.out.println(server.getName());		
		}
		ArrayList<Cabinet> cabinets = Helper.createCabinets(rand.nextInt(200));
		System.out.println("Searching cabinets for Adnams:");
		for (Cabinet cabinet : Helper.findCabinetExactName(cabinets,"Adnams")) {
			System.out.println(cabinet.getName());
		}
		System.out.println("Searching cabinets for Anams:");
		for (Cabinet cabinet : Helper.findCabinetExactName(cabinets,"Anams")) {
			System.out.println(cabinet.getName());
		}
		System.out.println("Searching cabinets for M.*:");
		for (Cabinet cabinet : Helper.findCabinetRegexName(cabinets,"M.*")) {
			System.out.println(cabinet.getName());
		}
	}


}
