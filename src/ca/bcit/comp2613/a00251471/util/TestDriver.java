/**
 * 
 */
package ca.bcit.comp2613.a00251471.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.List;

import ca.bcit.comp2613.a00251471.util.Helper;
import ca.bcit.comp2613.a00251471.util.FillCabinetsException;
import ca.bcit.comp2613.servertracker.model.*;

/**
 * @author Owen
 *
 */
public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		//nameSearch();
		//random100();
		try {
			fillCabinets(20,5);
		} catch (FillCabinetsException e) {
			Helper.log.error(e.getMessage());
		} catch (CriticalFillCabinetsException e) {
			e.printStackTrace();
		}
//		nameSearch();
//		sortServers();
		}
	
	/**
	 * Return list of servers, sorted in reverse order by power utilization
	 * 
	 * Outputs results to ServerReport.txt
	 */
	public static void sortServers() {
		ArrayList<Server> servers = Helper.createServers();
		Comparator<Server> serverComparator = new Comparator<Server>() {
			@Override
			public int compare(Server o1,Server o2) {
				int retval = Double.compare(o1.getProjectedPower(), o2.getProjectedPower());
				retval *= -1;
				if (retval == 0) {
					retval = o1.getName().compareTo(o2.getName());
				}
				return retval;
			}
		};
		Collections.sort(servers, serverComparator);
		Report.serverList(servers);
//		for (Server server: servers) {
//			Helper.log.info("Server Name: " + server.getName() + ", Projected Power: " + server.getProjectedPower());
//		}

	}
	
	
	/**
	 * @param numberOfCabinets number of cabinets to create
	 * @param serversPerCab number of servers per cabinet
	 */
	public static void fillCabinets(int numberOfCabinets,int serversPerCab) throws FillCabinetsException,CriticalFillCabinetsException {
		int currentServer = 0;
//		Random failing is sooooooo last assignment ;)
//		Random rand = new Random();
//		int randomFail = rand.nextInt(); 
//		if (randomFail % 100 == 0) {
//			throw new CriticalFillCabinetsException("Error, we are the 1%!");
//		} else if (randomFail % 20 == 0) {
//			throw new FillCabinetsException("Error, we are the 5%!");
//		}
		ArrayList<Server> servers = Helper.createServers();
		ArrayList<Cabinet> cabinets = Helper.createCabinets(numberOfCabinets);
//		for (Cabinet cabinet:cabinets) {
//			for (int i = 0; i < serversPerCab; i++) {
//				cabinet.addServer(servers.get(currentServer));
//				servers.get(currentServer).setPowerCCT(getRandomCCT(cabinet));
//				currentServer++;
//			}
//		}
		Helper.doCreateCabinets(cabinets,servers,numberOfCabinets,serversPerCab);
		for (Cabinet cabinet:cabinets) {
			Helper.log.info("Servers in " + cabinet.getName() + ":\n" + cabinet.listServers() + "\n");
		}
	}
	
	public static PowerCCT getRandomCCT(Cabinet cabinet) {		
		if (cabinet.numberOfCCTs() <= 0) {
			return null;
		}
		Random rand = new Random();
		int randomCCT = rand.nextInt() % cabinet.numberOfCCTs();
		if (randomCCT < 0) {
			randomCCT = randomCCT * -1;
		}
		return cabinet.getPowerCCT(randomCCT);
	}
	
	// Test searching for name in objects
	public static void nameSearch() {
		String printResults = "";
		Random rand = new Random();
		ArrayList<Server> servers = Helper.createServers(rand.nextInt(200));
		try {
			printResults = "";
			Helper.log.info("Searching servers for Asace:");
			for (Server server : Helper.findServerExactName(servers,"Asace")) {
				printResults = printResults + server.getName() + "\n";
			}
			if (printResults.isEmpty()) {
				throw new NameSearchException("No results.");
			}
			Helper.log.info(printResults);
		} catch (NameSearchException e) {
			Helper.log.error(e.getMessage());
		}
		try {
			printResults = "";
			Helper.log.info("Searching servers for Alsace:");
			for (Server server : Helper.findServerExactName(servers,"Alsace")) {
				printResults = printResults + server.getName() + "\n";
			} 
			if (printResults.isEmpty()) {
				throw new NameSearchException("No results.");
			}
			Helper.log.info(printResults);
		} catch (NameSearchException e) {
			Helper.log.error(e.getMessage());
		}
		try {
			printResults = "";
			Helper.log.info("Searching servers for G.*:");
			for (Server server : Helper.findServerRegexName(servers,"G.*")) {
				printResults = printResults + server.getName() + "\n";
			} 
			if (printResults.isEmpty()) {
				throw new NameSearchException("No results.");
			}
			Helper.log.info(printResults);
		} catch (NameSearchException e) {
			Helper.log.error(e.getMessage());
		}
		ArrayList<Cabinet> cabinets = Helper.createCabinets(rand.nextInt(200));

		try {
			printResults = "";
			Helper.log.info("Searching cabinets for Adnams:");
			for (Cabinet cabinet : Helper.findCabinetExactName(cabinets,"Adnams")) {
				printResults = printResults + cabinet.getName() + "\n";
			} 
			if (printResults.isEmpty()) {
				throw new CabinetSearchException("No results.");
			}
			Helper.log.info(printResults);
		} catch (CabinetSearchException e) {
			Helper.log.error(e.getMessage());
		}
		
		try {
			printResults = "";
			Helper.log.info("Searching cabinets for Anams:");
			for (Cabinet cabinet : Helper.findCabinetExactName(cabinets,"Anams")) {
				printResults = printResults + cabinet.getName() + "\n";
			} 
			if (printResults.isEmpty()) {
				throw new CabinetSearchException("No results.");
			}
			Helper.log.info(printResults);
		} catch (CabinetSearchException e) {
			Helper.log.error(e.getMessage());
		}

		try {
			printResults = "";
			Helper.log.info("Searching cabinets for M.*:");
			for (Cabinet cabinet : Helper.findCabinetRegexName(cabinets,"M.*")) {
				printResults = printResults + cabinet.getName() + "\n";
			} 
			if (printResults.isEmpty()) {
				throw new CabinetSearchException("No results.");
			}
			Helper.log.info(printResults);
		} catch (CabinetSearchException e) {
			Helper.log.error(e.getMessage());
		}
	}


}
