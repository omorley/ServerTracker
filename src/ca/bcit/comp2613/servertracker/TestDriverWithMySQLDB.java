package ca.bcit.comp2613.servertracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.List;

import ca.bcit.comp2613.servertracker.model.*;
import ca.bcit.comp2613.servertracker.repository.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@EnableAutoConfiguration
@ImportResource("application.xml")

public class TestDriverWithMySQLDB {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TestDriverWithMySQLDB.class); 
		// TODO Auto-generated method stub
		CabinetRepository cabinetRepository = context.getBean(CabinetRepository.class);
		
		Cabinet cabinet = new Cabinet();
		cabinet.setId("1");
		cabinet.setName("test");
		cabinetRepository.save(cabinet);
		
		context.close();
	}

}
