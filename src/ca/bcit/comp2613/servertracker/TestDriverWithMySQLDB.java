package ca.bcit.comp2613.servertracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TestDriverWithMySQLDB.class); 

		CabinetRepository cabinetRepository = context.getBean(CabinetRepository.class);

////		// Ghetto method, for the assignment only
		String[] nameList = VODKALIST.split("\\s");
		int maxCount = 100; //nameList.length;
		for (int i = 0; i < maxCount; i++) {
			Cabinet cabinet = new Cabinet();
			cabinet.setId(Integer.toString(i));
			cabinet.setName(nameList[i]);
			cabinetRepository.save(cabinet);
		}
		
//		// Ideal method, to really go full speed on putting these in the database
//		ArrayList<Cabinet> cabinetList = Helper.createCabinets();
//		for (Cabinet cabinet:cabinetList) {
//			System.out.println("Name: " + cabinet.getName());
//			cabinetRepository.save(cabinet);
//		}
		
		// Testing for bare functionality.
//		Cabinet cabinet = new Cabinet();
//		cabinet.setId("1");
//		cabinet.setName("test");
//		cabinetRepository.save(cabinet);
		EntityManagerFactory emf = (EntityManagerFactory) context.getBean("entityManagerFactory");
		CustomQueryHelper customQueryHelper = new CustomQueryHelper(emf);
		Cabinet test1 = customQueryHelper.getCabinetWithId("1");
		System.out.println("Name: " + test1.getName());

		Cabinet test2 = customQueryHelper.getCabinetWithId("2");
		System.out.println("Name: " + test2.getName());

		Cabinet test3 = customQueryHelper.getCabinetWithId("15");
		System.out.println("Name: " + test3.getName());

		Cabinet test5 = customQueryHelper.getCabinetWithId("30");
		System.out.println("Name: " + test5.getName());

		Cabinet test6 = customQueryHelper.getCabinetWithId("99");
		System.out.println("Name: " + test6.getName());

		context.close();
	}

}
