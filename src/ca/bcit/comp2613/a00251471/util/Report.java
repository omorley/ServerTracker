/**
 * 
 */
package ca.bcit.comp2613.a00251471.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

import ca.bcit.comp2613.a00251471.util.Helper;
import ca.bcit.comp2613.a00251471.util.FillCabinetsException;
import ca.bcit.comp2613.servertracker.model.Cabinet;
import ca.bcit.comp2613.servertracker.model.Server;

public class Report {
	public static void main(String[] args) {
		
	}

	public static void serverList(ArrayList<Server> servers) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		try {
			FileUtils.writeStringToFile(new File("ServerReport.txt"),"Writing report on " + dateFormat.format(date),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Server server: servers) {
			try {
				FileUtils.writeStringToFile(new File("ServerReport.txt"),"Server Name: " + server.getName() + ", Projected Power: " + server.getProjectedPower() + "\n",true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileUtils.writeStringToFile(new File("ServerReport.txt"),"\n\n",true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
