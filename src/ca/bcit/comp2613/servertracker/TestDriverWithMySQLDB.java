package ca.bcit.comp2613.servertracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import ca.bcit.comp2613.servertracker.model.*;
import ca.bcit.comp2613.servertracker.repository.*;
import ca.bcit.comp2613.a00251471.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@EnableAutoConfiguration
@ImportResource("application.xml")
public class TestDriverWithMySQLDB {
	public static String VODKALIST = "42 Absolut Absolwent Adnams Alberta AnestasiA Artic Belaya Belvedere Blavod Bols Bombora Bong Boru Bowman's Boyd Brand Cape Chase Chinggis Chopin Cirrus Ck Clique Cooranbong Cracovia C�roc Crystal Danzka Deep DOT Double Dovgan Downunder Dragon Dubra Eristoff Explorer Finlandia Firefly Fleischmann's Fr�s Glen's Gold Grand Grey Han Hangar Hooghoudt Hrenovuha Iceberg Isensua Jean-Marc Karlsson's Kauffman Keglevich Ketel Khortytsa Kihnu Kissui Kleiner Korski Koskenkorva Krupnik Kryshtal Kubanskaya Latvijas L'Chaim Leopold Level Lokka Lotus Luksusowa Magic Minskaya Monopolowa Monte Moskovskaya Murree Narodnaya Nemiroff Nikolai (originally Orloff Oso Pinky Pinnacle Platinka Polar Polonaise Popov Potato Putinka Pyatizvyozdnaya Rachmaninoff Rain Renat Reyka Rodnik Rokk Ruskova Russian SAVVY Schramm Shustov Siwucha SKYY Smirnoff Snow Sobieski Soplica Soyuz-Viktan Spendrups Square Starka Stolichnaya Stolnaya Stumbras Svedka Taaka Three Tito's Ultimat U'Luvka Ursus UV V44 Van Vikingfjord Villa Viru VKA Vladivar Vodka Vox White Wisent Wyborowa Xan Xellent Youri Zaranoff Zodiac";
	private static ConfigurableApplicationContext context = null;
	public static CabinetRepository cabinetRepository;
	public static PowerCCTRepository powerCCTRepository;
	public static ServerRepository serverRepository;
	private static EntityManagerFactory emf = null;
	private static CustomQueryHelper customQueryHelper;
	public static List<Cabinet> cabinets;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication
				.run(TestDriverWithMySQLDB.class);
			CabinetRepository cabinetRepository = context
				.getBean(CabinetRepository.class);
			PowerCCTRepository powerCCTRepository = context
				.getBean(PowerCCTRepository.class);
			ServerRepository serverRepository = context
				.getBean(ServerRepository.class);
		
//		ArrayList<Cabinet> cabinetList = Helper.fillCabinets(5,5);
//		for (Cabinet cabinet : cabinetList) {
//			System.out.println("Name: " + cabinet.getName());
//			for (PowerCCT powercct : cabinet.getPowerCCTArray()) {
//				powerCCTRepository.save(powercct);
//			}
//			for (Server server : cabinet.getServersArray()) {
//				serverRepository.save(server);
//			}
//			cabinetRepository.save(cabinet);
//		}
		cabinets = copyIterator(cabinetRepository.findAll().iterator());

		ServerTrackerCabinetSwingApplicationWithMySQLDB newWindow = new ServerTrackerCabinetSwingApplicationWithMySQLDB((ArrayList) cabinets);
		newWindow.frame.setVisible(true);

//		context.close();

	}

	/**
	 * Update entry of a server, add entry as needed
	 * @param servers
	 * @param server
	 */
	public static void save(List<Cabinet> cabinets, Cabinet newCabinet, Server server) {
		boolean foundUpdate = false;
		if (! cabinets.contains(newCabinet)) {
			cabinets.add(newCabinet);
			cabinetRepository.save(newCabinet);
		}
		cabinets = copyIterator(cabinetRepository.findAll().iterator());

		for (Cabinet cabinet : cabinets) {
//			Iterator<Server> iter = cabinet.getServersArray().iterator();
			Iterator<Server> iter = cabinet.getServersArray().iterator();
			while (iter.hasNext()) {
				Server serverLoop = iter.next();
				if (serverLoop.getId().equals(server.getId())) {
					if (cabinet == newCabinet) {
						serverLoop.setName(server.getName());
						serverLoop.setIp(server.getIp());
						serverLoop.setPowerCCT(server.getPowerCCT());
						foundUpdate = true;
						break;
					} else {
						iter.remove();
						newCabinet.addServer(server);
					}
					
				}
			}
		}
		if (!foundUpdate) { // do an insert
			newCabinet.addServer(server);
		}
	}

	public static <T> List<T> copyIterator(Iterator<T> iter) {
		List<T> copy = new ArrayList();
		while (iter.hasNext())
			copy.add(iter.next());
		return copy;
	}	
	
}
