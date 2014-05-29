/**
 * 
 */
package ca.bcit.comp2613.a00251471.util;

import java.util.ArrayList;
import java.util.Random;

import ca.bcit.comp2613.a00251471.util.helper;
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
		Random rand = new Random();
		ArrayList<Server> servers = helper.createServers(rand.nextInt(200));
		System.out.println("Searching servers for Asace:");
		for (Server server : helper.findServerExactName(servers,"Asace")) {
			System.out.println(server.getName());		
		} 
		System.out.println("Searching servers for Alsace:");
		for (Server server : helper.findServerExactName(servers,"Alsace")) {
			System.out.println(server.getName());		
		} 
		System.out.println("Searching servers for G.*:");
		for (Server server : helper.findServerRegexName(servers,"G.*")) {
			System.out.println(server.getName());		
		}
		ArrayList<Cabinet> cabinets = helper.createCabinets(rand.nextInt(200));
		System.out.println("Searching cabinets for Adnams:");
		for (Cabinet cabinet : helper.findCabinetExactName(cabinets,"Adnams")) {
			System.out.println(cabinet.getName());
		}
		System.out.println("Searching cabinets for Anams:");
		for (Cabinet cabinet : helper.findCabinetExactName(cabinets,"Anams")) {
			System.out.println(cabinet.getName());
		}
		System.out.println("Searching cabinets for M.*:");
		for (Cabinet cabinet : helper.findCabinetRegexName(cabinets,"M.*")) {
			System.out.println(cabinet.getName());
		}

	}

}
