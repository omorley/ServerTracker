package ca.bcit.comp2613.a00251471.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


















//Log4j
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ca.bcit.comp2613.servertracker.model.*;

import java.util.List;


public class Helper {
	private final static String firstIpOctets = "192.168.0.";
	
	public static void main(String[] args) {
		//log.debug("This is a debug message");
		//log.error("ERROR", new Exception("Doh"));
	}

	
	public static Logger log = Logger.getLogger(Helper.class);
	static {
//		PropertyConfigurator.configure(
//			//Helper.class.getResourceAsStream("log4j.properties")
//				"log4j.properties"
//			);
	}

	// http://www.wines.com/pronunciation-wine-names.html + grep \( test | sed 's/\(.*\) (.*/\1/' | awk '{print $1}' | grep -v \\. | grep -vE ^A$ | grep -v The | sort | uniq | sed ':a;N;$!ba;s/\n/ /g'
	public static String WINELIST = "Agrafe Alsace An Anjou Appellation Assemblage Asti Auslese Ausone Avignon Bacchus Ban Bandol Barbaresco Barbera Bardolino Barolo Barrique Beaujolais Beaune Beerenauslese Bellet Bereich Bernkastel Beycheville Bianco Bienvenues Blanc Blanchot Bocksbeutel Bonnes-Mares Bordeaux Bottled Bougros Bourgogne Bourgueil Brane-Cantenac Brouilly Brut Cabernet Calon-Segur Campania Canon Carbonnieux Cassis Certan-de-May Chablis Chaintre Chambertin Chambolle-Musigny Champagne Charmes Chassagne-Montrachet Chasselas Chasse-Spleen Chateau Chenas Cheval Chianti Chinon Chiroubles Classified Climat Climens Clos Commune Condrieu Corton Cos Cos-Labory Cote Coteaux Cotes Coutet Criots Cuvee Degorgement Deuxieme Dionysus Dom Dominode Dordogne Dosage Ducru-Beaucaillou Durfort-Vivens Echezeaux Eigene Einsellage Eiswein Emilia-Romagna En Enkirch Entre-Deux-Mers Epenots Epluchage Erbach Est! Falerno Falfas Feves Figeac Finest Fixin Flagey-Echezeaux Fleurie Fleur-Pourret Frascati Frecciarossa Fuder Fuisse Gamay Garonne Gazin Gebeit Gevrey-Chambertin Gewurztraminer Gironde Givry Goldtropfchen Goutte Graach Grand Grands Graves Gravieres Grenouille Greves Grillet Grosslage Gruaud-Larose Grumello Guiraud Haut-Bailly Haut-Brion Hermitage Himmelreich Hipping Hochheim Ile Inferno Issan Johannisberg Julienas Kabinett Karthauserhofberg Kupfergrube La Lacryma Lafite-Rothschild Lafon-Rochet Lake Lambrusco Lascombes Latium Latour Le Leiwen Lenchen Leognan Leoville-Barton Leoville-Lascases Leoville-Poyfere Les Liebfraumilch Liqueur Lirac Loire Lombardy Macon Macon-Villages Magdelaine Malbec Marbuzet Marc Marche Marcobrunn Marconnets Margaux Marsala Marseillaise Martillac Maximin Mazis-Chambertin Medoc Mercurey Merlot Meursault Mission-Haut-Brion Mit Montagny Montefiascone Monthelie Montrachet Montrose Monts Morgon Moselblumchen Most Moulin-a-Vent Mouton-Rothschild Muscadet Musigny Nahe Native Nebbiolo Negociant Nierstein Nuits Oestrich Olivier Oppenheim Orvieto PalatinateRheinpfalz Palette Palmer Paniers Pape Passe-tous-Grains Pauillac Pavie Pernand-Vergelesses Pessac Petit Petit-Village Petrus Phelan-Segur Phylloxera Pichon-Lalande Pichon-Longueville Piedmont Pinot Piron Pomerol Pommard Pouilly-Fuisse Pouilly-Fume Pouilly-Loche Pouilly-sur-Loire Pouilly-Vinzelles Premeaux Premiere Preuses Provence Puligny-Montrachet Pupitres Qualitatswein Quarts Quincy Rausan-Segla Rauzan-Gassies Rayne-Vigneau Rebeche Remuage Reuilly Rheims Rheingau Rheinhesse Rheinpfalz/Palatinate Richebourg Riesling Rieussec Romanee Romanee-Conti Rose Rotenfels Rudesheim Rudesheimer Rugiens Rully Ruwer Saar Saint-Amour Saint-Marc Sancerre Santenay Santenote Sassella Saumur Sauternes Sauvignon Savennieres Savigny-les-Beaune Scharzhof Schloss Sec Semillon Sicily Slanting Small Soave Solutre Sonnenuhr Spatlese Steinberg Steinwein Strasbourg Sylvaner Syrah Tafelwein Talbot Talence Tastes Tavel Teurons Tiergarten Traminer Trier Trockenbeerenauslese Tuscany Umbria Valmur Valpolicella Valtellina Vaudesir Verdicchio Vergisson Verona Vieux-Chateau-Certan Villefranche Vin Vino Voignier Volnay Volnay-Santenots Vosges Vosne-Romanee Vougeot Vouvray Wehlen White Wurzburg Yquem Zell Zeller Zeltinger";
	// http://en.wikipedia.org/wiki/List_of_vodkas + cat test2 | awk '{print $1}' | grep -v \\. | grep -vi the | sort | uniq | sed ':a;N;$!ba;s/\n/ /g'
	public static String VODKALIST = "42 Absolut Absolwent Adnams Alberta AnestasiA Artic Belaya Belvedere Blavod Bols Bombora Bong Boru Bowman's Boyd Brand Cape Chase Chinggis Chopin Cirrus Ck Clique Cooranbong Cracovia C�roc Crystal Danzka Deep DOT Double Dovgan Downunder Dragon Dubra Eristoff Explorer Finlandia Firefly Fleischmann's Fr�s Glen's Gold Grand Grey Han Hangar Hooghoudt Hrenovuha Iceberg Isensua Jean-Marc Karlsson's Kauffman Keglevich Ketel Khortytsa Kihnu Kissui Kleiner Korski Koskenkorva Krupnik Kryshtal Kubanskaya Latvijas L'Chaim Leopold Level Lokka Lotus Luksusowa Magic Minskaya Monopolowa Monte Moskovskaya Murree Narodnaya Nemiroff Nikolai (originally Orloff Oso Pinky Pinnacle Platinka Polar Polonaise Popov Potato Putinka Pyatizvyozdnaya Rachmaninoff Rain Renat Reyka Rodnik Rokk Ruskova Russian SAVVY Schramm Shustov Siwucha SKYY Smirnoff Snow Sobieski Soplica Soyuz-Viktan Spendrups Square Starka Stolichnaya Stolnaya Stumbras Svedka Taaka Three Tito's Ultimat U'Luvka Ursus UV V44 Van Vikingfjord Villa Viru VKA Vladivar Vodka Vox White Wisent Wyborowa Xan Xellent Youri Zaranoff Zodiac";
	// http://en.wikipedia.org/wiki/List_of_beer_styles + cat test3 | awk '{print $1}' | grep -v \\. | grep -vi the | sort | uniq | sed ':a;N;$!ba;s/\n/ /g'
	public static String BEERLIST = "Altbier Amber American Barley Berliner Bi�re Bitter Blonde Bock Bohemian Brown California Classic Cream Doppelbock Dortmunder D�sseldorf Dunkel Dunkelweizen Eisbock Fruit Golden Gose Gueuze Hefeweizen Helles Imperial India K�lsch Lambic Light Maibock/Helles Malt Mild Oktoberfestbier/M�rzenbier Old Oud Pale Pilsener/Pilsner/Pils Porter Red Roggenbier Saison Schwarzbier Scotch Steam Stout Term Vienna Weissbier Weizenbock Witbier";
	/**
	 * Create max number of servers
	 */
	public static ArrayList<Server> createServers() {
		ArrayList<Server> servers = new ArrayList<>();
		String[] nameList = WINELIST.split("\\s");
		int maxCount = nameList.length;
		doCreateServers(servers,maxCount,nameList);
		return servers;
	}

	/**
	 * Builds cabinets, fills them with servers and returns an arraylist of them
	 * 
	 * @return ArrayList of fully populated cabinets
	 */

	public static ArrayList<Cabinet> fillCabinets(int numberOfCabinets, int serversPerCab) {
		ArrayList<Server> servers = createServers();
		ArrayList<Cabinet> cabinets = createCabinets(numberOfCabinets);
		doPopulateCabinets(cabinets,servers,numberOfCabinets,serversPerCab);
		return cabinets;
	}


	/**
	 * @param instances the number of instances to create in list
	 */	
	public static ArrayList<Server> createServers(int instances) {
		int maxCount;
		ArrayList<Server> servers = new ArrayList<>();
		String[] nameList = WINELIST.split("\\s");
		if (instances < nameList.length) {
			maxCount = instances;
		} else {
			maxCount = nameList.length;
		}
		doCreateServers(servers,maxCount,nameList);
		return servers;
	}

	/**
	 * @param servers
	 * @param maxCount
	 * @param nameList
	 */
	public static void doCreateServers(ArrayList<Server> servers,int maxCount,String[] nameList) {
		for (int i = 0; i < maxCount; i++) {
			Server server = new Server();
			server.setId(Integer.toString(i));
			server.setName(nameList[i]);
			server.setProjectedPower(generateRandomPower(100,500));
			server.setServerStatus(randomServerStatus());
			server.setIp(firstIpOctets + Integer.toString(i));
			servers.add(server);
		}
	}

	/**
	 * @param cabinet
	 * @return
	 */
	public static PowerCCT getRandomCCT(Cabinet cabinet) {		
		if (cabinet.numberOfCCTs() <= 0) {
			return null;
		}
		Random rand = new Random();
		int randomCCT = rand.nextInt() % cabinet.numberOfCCTs();
		if (randomCCT < 0) {
			randomCCT = randomCCT * -1;
		}
		return cabinet.getPowerCCTArray().get(randomCCT);
	}
	

	
	/**
	 * @return random ServerStatus enum
	 */
	public static ServerStatus randomServerStatus() {
		Random rand = new Random();
		int randomEnum = rand.nextInt();
		if (randomEnum % 3 == 0) {
			return ServerStatus.PROD;
		} else if (randomEnum % 3 == 1) {
			return ServerStatus.STG;
		} 
		return ServerStatus.STG;
	}

	/**
	 * Create max number of cabinets
	 */
	public static ArrayList<Cabinet> createCabinets() {
		ArrayList<Cabinet> cabinets = new ArrayList<>();
		String[] nameList = VODKALIST.split("\\s");
		int maxCount = nameList.length;
		doBuildCabinets(cabinets,maxCount,nameList);
		return cabinets;
	}
	
	/**
	 * @param instances the number of instances to create in list
	 */	
	public static ArrayList<Cabinet> createCabinets(int instances) {
		int maxCount;
		ArrayList<Cabinet> cabinets = new ArrayList<>();
		String[] nameList = VODKALIST.split("\\s");
		if (instances < nameList.length) {
			maxCount = instances;
		} else {
			maxCount = nameList.length;
		}
		doBuildCabinets(cabinets,maxCount,nameList);
		return cabinets;
	}
	

	/**
	 * @param cabinets
	 * @param maxCount
	 * @param nameList
	 */
	public static void doBuildCabinets(ArrayList<Cabinet> cabinets,int maxCount,String[] nameList) {
		int circuitNumber = 1;
		for (int i = 0; i < maxCount; i++) {
			Cabinet cabinet = new Cabinet();
			cabinet.setId(Integer.toString(i));
			cabinet.setName(nameList[i]);
			PowerCCT cctOne = new PowerCCT();
			cctOne.setName(String.valueOf(circuitNumber));
			cabinet.addPowerCCT(cctOne);
			circuitNumber++;
			PowerCCT cctTwo = new PowerCCT();
			cctTwo.setName(String.valueOf(circuitNumber));
			cabinet.addPowerCCT(cctTwo);
			circuitNumber++;
			cabinets.add(cabinet);
		}
	}
	
	/**
	 * @param cabinets
	 * @param servers
	 * @param numberOfCabinets
	 * @param serversPerCab
	 */
	public static void doPopulateCabinets(ArrayList<Cabinet> cabinets,ArrayList<Server> servers,int numberOfCabinets,int serversPerCab) {
		int currentServer = 0;
		for (Object cabinet:cabinets) {
			for (int i = 0; i < serversPerCab; i++) {
				((Cabinet) cabinet).addServer((Server) servers.get(currentServer));
				((Server) servers.get(currentServer)).setPowerCCT(getRandomCCT(((Cabinet) cabinet)));
				currentServer++;
			}
		}
	}
	
	/**
	 * List all servers	
	 * @param servers
	 */
	public static void printServers(ArrayList<Server> servers) {
		for (Server server : servers) {
			System.out.println(server);
		}
	}

	/**
	 * Find server by exact name match
	 * @param servers
	 * @param serverName
	 * @return
	 */
	public static Server findServerExactName(List<Server> servers,String serverName) {
		for (Server server : servers) {
			if (server.getName().equals(serverName)) {
				return server;
			}
		}
		return null;
	}

	/**
	 * Find server by exact name match
	 * @param servers
	 * @param serverName
	 * @return-
	 */
	public static ArrayList<Server> findServerExactName(ArrayList<Server> servers,String serverName) {
		ArrayList<Server> foundServers = new ArrayList<>();
		for (Server server : servers) {
			if (server.getName().equals(serverName)) {
				foundServers.add(server);
			}
		}
		return foundServers;
	}

	/**
	 * Find server by regex name match
	 * @param servers
	 * @param serverName
	 * @return
	 */
	public static ArrayList<Server> findServerRegexName(ArrayList<Server> servers,String serverName) {
		ArrayList<Server> foundServers = new ArrayList<>();
		for (Server server : servers) {
			if (server.getName().matches(serverName)) {
				foundServers.add(server);
			}
		}
		return foundServers;
	}

	/**
	 * List all cabinets
	 * @param cabinets
	 */
	public static void printCabinet(ArrayList<Cabinet> cabinets) {
		for (Cabinet cabinet : cabinets) {
			System.out.println(cabinet);
		}
	}

	/**
	 * Find power cct by exact name match
	 * @param cabinets
	 * @param circuitName
	 * @return
	 */
	public static ArrayList<PowerCCT> findPowerCCTsExactName(List<PowerCCT> powerCCTs,String circuitName) {
		ArrayList<PowerCCT> foundCCTs = new ArrayList<>();
		for (PowerCCT powerCCT : powerCCTs) {
			if (powerCCT.getName().equals(circuitName)) {
				foundCCTs.add(powerCCT);
			}
		}
		return foundCCTs;
	}

	
	public static Cabinet findServerInCabinet(List<Cabinet> cabinets,Server server) {
		for (Cabinet cabinet:cabinets) {
			for (Server serverInCabinet:cabinet.getServersArray()) {
				if (serverInCabinet.getId() == server.getId()) {
					System.out.println("Cabinet found: " + cabinet);
					return cabinet; 
				}
			}
		}
		System.out.println("No cabinet found...");
		return null;
	}

	public static Cabinet findPowerCCTInCabinet(List<Cabinet> cabinets,PowerCCT powerCCT) {
		for (Cabinet cabinet:cabinets) {
			System.out.println("Checking cabinet..." + cabinet);
			for (PowerCCT cabinetPowerCCT: cabinet.getPowerCCTArray()) {
				if (cabinetPowerCCT.getId() == powerCCT.getId()) {
					System.out.println("Found in " + cabinet);
					return cabinet;
				}
			}
		}
		return null;
	}

	/**
	 * Find power cct by exact name match
	 * @param cabinets
	 * @param circuitName
	 * @return
	 */
	public static PowerCCT findFirstPowerCCTExactName(List<Cabinet> cabinets,String circuitName) {
		for (Cabinet cabinet : cabinets) {
			for (PowerCCT powerCCT : cabinet.getPowerCCTArray()) {
				if (powerCCT.getName().equals(circuitName)) {
					return powerCCT;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Find first cabinet by exact name match
	 * @param cabinets
	 * @param cabinetName
	 * @return
	 */
	public static Cabinet findFirstCabinetExactName(List<Cabinet> cabinets,String cabinetName) {
		for (Cabinet cabinet : cabinets) {
			if (cabinet.getName().equals(cabinetName)) {
				return cabinet;
			}
		}
		return null;
	}

	/**
	 * Find cabinet by exact name match
	 * @param cabinets
	 * @param cabinetName
	 * @return
	 */
	public static ArrayList<Cabinet> findCabinetExactName(List<Cabinet> cabinets,String cabinetName) {
		ArrayList<Cabinet> foundCabinets = new ArrayList<>();
		for (Cabinet cabinet : cabinets) {
			if (cabinet.getName().equals(cabinetName)) {
				foundCabinets.add(cabinet);
			}
		}
		return foundCabinets;
	}

	/**
	 * Find cabinet by regex name match
	 * @param cabinets
	 * @param cabinetName
	 * @return
	 */
	public static ArrayList<Cabinet> findCabinetRegexName(ArrayList<Cabinet> cabinets,String cabinetName) {
		ArrayList<Cabinet> foundCabinets = new ArrayList<>();
		for (Cabinet cabinet : cabinets) {
			if (cabinet.getName().matches(cabinetName)) {
				foundCabinets.add(cabinet);
			}
		}
		return foundCabinets;
	}
	
	/**
	 * Generate random power utilization for a server
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generateRandomPower(int min,int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	
	/**
	 * Remove entry of a server
	 * @param servers
	 * @param server
	 */
	public static void delete(List<Cabinet> cabinets, Server server) {
		for (Cabinet cabinet : cabinets) {
			Iterator<Server> iter = cabinet.getServersArray().iterator();
			while (iter.hasNext()) {
				Server serverLoop = iter.next();
				if (serverLoop.getId().equals(server.getId())) {
					iter.remove();
					break;
				}
			}
		}
	}

}
