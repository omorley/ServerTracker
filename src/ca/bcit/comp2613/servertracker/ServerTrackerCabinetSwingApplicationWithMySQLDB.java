package ca.bcit.comp2613.servertracker;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.UUID;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ca.bcit.comp2613.a00251471.util.Helper;
import ca.bcit.comp2613.servertracker.model.Cabinet;
import ca.bcit.comp2613.servertracker.model.PowerCCT;
import ca.bcit.comp2613.servertracker.model.Server;
import ca.bcit.comp2613.servertracker.model.SwingServerTrackerModel;
import ca.bcit.comp2613.servertracker.repository.CabinetRepository;
import ca.bcit.comp2613.servertracker.repository.CustomQueryHelper;
import ca.bcit.comp2613.servertracker.repository.PowerCCTRepository;
import ca.bcit.comp2613.servertracker.repository.ServerRepository;

public class ServerTrackerCabinetSwingApplicationWithMySQLDB {
	public JFrame frame;
	private JTable table;
	private JTextField serverNameTextField;
	private JTextField serverIPTextField;
	private JTextField cabinetTextField;
	private JComboBox cabinetComboBox;
	private JComboBox powerCCTComboBox;
	private JTextField powerCCTTextField;
	private JLabel lblServerIp;
	private JLabel lblServerId;
	private JLabel lblCabinetId;
	private JLabel lblPowerCCTId;
	private JButton btnDelete;
	private JButton btnSave;
	private SwingServerTrackerModel swingServerTrackerModel;
	public String[] columnNames = new String[] { "id", "Server Name",
			"Server IP", "Cabinet", "PowerCCT" };
	private JTextField idTextField;
	private static List<Cabinet> cabinets;
	private static List<Server> servers;
	private static List<PowerCCT> powerCCTs;
	private static ConfigurableApplicationContext context;
	public static CabinetRepository cabinetRepository;
	public static PowerCCTRepository powerCCTRepository;
	public static ServerRepository serverRepository;
	private static EntityManagerFactory emf;
	private static CustomQueryHelper customQueryHelper;
	public static boolean useInMemoryDB = true;
	public static final String addNew = "..Add New..";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerTrackerCabinetSwingApplicationWithMySQLDB window = new ServerTrackerCabinetSwingApplicationWithMySQLDB();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			}
		});
	}

	public static <T> List<T> copyIterator(Iterator<T> iter) {
	    List<T> copy = new ArrayList();
	    while (iter.hasNext())
	        copy.add(iter.next());
	    return copy;
	}

	
	/**
	 * Create the application.
	 */
	public ServerTrackerCabinetSwingApplicationWithMySQLDB() {

		if (useInMemoryDB) {
			context = SpringApplication.run(H2Config.class);
			try {
				org.h2.tools.Server.createWebServer(null).start();
				DataSource dataSource = (DataSource) context
						.getBean("dataSource");
				// org.apache.tomcat.jdbc.pool.DataSource tomcatDataSource =
				// (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
				// int a = 5;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			context = SpringApplication.run(TestDriverWithMySQLDB.class);
		}

		cabinetRepository = context
				.getBean(CabinetRepository.class);
		powerCCTRepository = context
				.getBean(PowerCCTRepository.class);
		serverRepository = context
				.getBean(ServerRepository.class);
		
		emf = (EntityManagerFactory) context.getBean("entityManagerFactory");

		customQueryHelper = new CustomQueryHelper(emf);
		
		ArrayList<Cabinet> cabinetList = Helper.fillCabinets(2,2);
		for (Cabinet cabinet : cabinetList) {
			System.out.println("Name: " + cabinet);
			for (PowerCCT powercct : cabinet.getPowerCCTArray()) {
				powerCCTRepository.save(powercct);
			}
			for (Server server : cabinet.getServersArray()) {
				serverRepository.save(server);
			}
			cabinetRepository.save(cabinet);
		}
		cabinets = copyIterator(cabinetRepository.findAll().iterator());
		setCabinets(cabinets);
		initialize();
		initTable();
	}

	private void initTable() {
		// table = new JTable(swingTeacherModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							populateFields();
							checkButtons();
						}
					}
				});
		refreshTable();
		doNew();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
		frame = new JFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setBounds(100, 100, 1202, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// table = new JTable();
		swingServerTrackerModel = new SwingServerTrackerModel();
//		swingTeacherModel = new SwingTeacherModel();

		table = new JTable(swingServerTrackerModel);

		// table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		// table.setBounds(0, 11, 585, 247);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 11, 1170, 247);
		frame.getContentPane().add(scrollPane);
		// scrollPane.add(table);
		// frame.getContentPane().add(table);

		JLabel lblServerName = new JLabel("Server Name");
		lblServerName.setBounds(44, 330, 103, 14);
		frame.getContentPane().add(lblServerName);

		serverNameTextField = new JTextField();
		serverNameTextField.setBounds(159, 327, 325, 20);
		frame.getContentPane().add(serverNameTextField);
		serverNameTextField.setColumns(10);

		serverIPTextField = new JTextField();
		serverIPTextField.setBounds(159, 371, 325, 20);
		frame.getContentPane().add(serverIPTextField);
		serverIPTextField.setColumns(10);

		lblServerIp = new JLabel("Server IP");
		lblServerIp.setBounds(44, 374, 77, 14);
		frame.getContentPane().add(lblServerIp);

		lblServerId = new JLabel("id");
		lblServerId.setBounds(44, 288, 46, 14);
		frame.getContentPane().add(lblServerId);

//		cabinetTextField = new JTextField();
//		cabinetTextField.setBounds(159, 412, 325, 20);
//		frame.getContentPane().add(cabinetTextField);
//		cabinetTextField.setColumns(10);
		cabinetComboBox = new JComboBox();
		cabinetComboBox.setBounds(159, 412, 325, 20);
		updateCabinetList();
		frame.getContentPane().add(cabinetComboBox);

		cabinetComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePowerCCTList();
				checkButtons();
			}
		});
		
		lblCabinetId = new JLabel("Cabinet");
		lblCabinetId.setBounds(44, 412, 77, 14);
		frame.getContentPane().add(lblCabinetId);
		
//		powerCCTTextField = new JTextField();
//		powerCCTTextField.setBounds(159, 459, 325, 20);
//		frame.getContentPane().add(powerCCTTextField);
//		powerCCTTextField.setColumns(10);
		powerCCTComboBox = new JComboBox();
		powerCCTComboBox.setBounds(159, 459, 325, 20);
		updatePowerCCTList();
		frame.getContentPane().add(powerCCTComboBox);
		powerCCTComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkButtons();
			}
		});

		
		lblPowerCCTId = new JLabel("Power CCT");
		lblPowerCCTId.setBounds(44, 459, 77, 14);
		frame.getContentPane().add(lblPowerCCTId);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
				checkButtons();
			}
		});
		btnSave.setEnabled(false);
		
		btnSave.setBounds(44, 506, 89, 23);
		frame.getContentPane().add(btnSave);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDelete();
				checkButtons();
			}
		});
		btnDelete.setBounds(169, 506, 89, 23);
		frame.getContentPane().add(btnDelete);
		btnDelete.setEnabled(false);
		
		JButton btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doNew();
				checkButtons();
			}
		});
		btnNewButton.setBounds(496, 260, 89, 23);
		frame.getContentPane().add(btnNewButton);

		idTextField = new JTextField();
		idTextField.setEditable(false);
		idTextField.setBounds(159, 285, 325, 20);
		frame.getContentPane().add(idTextField);
		idTextField.setColumns(10);
	}
	
	public void checkButtons() {
		System.out.println("Start....");
		System.out.println("Table... " + table.getSelectedRow());
		if (table.getSelectedRow() >= 0) {
			btnDelete.setEnabled(true);
		} else {
			btnDelete.setEnabled(false);
		}
		System.out.println("powercct: " + powerCCTComboBox.getSelectedIndex());
		System.out.println("cabinet: " + cabinetComboBox.getSelectedIndex());
		System.out.println("End....");
		if (powerCCTComboBox.getSelectedIndex() != 0 && cabinetComboBox.getSelectedIndex() != 0) {
			btnSave.setEnabled(true);
		} else {
			btnSave.setEnabled(false);
		}
	}
	
	public void updatePowerCCTList() {
		powerCCTComboBox.removeAllItems();
		powerCCTComboBox.addItem(addNew);
		if (cabinetComboBox.getModel().getSelectedItem().getClass() == Cabinet.class) {
			for (PowerCCT powerCCT: ((Cabinet) cabinetComboBox.getModel().getSelectedItem()).getPowerCCTArray()) {
				System.out.println("Adding cct: " + powerCCT);
				powerCCTComboBox.addItem(powerCCT);
			}
		}
		System.out.println("Done?");
	}

	public void updateCabinetList() {
		cabinetComboBox.removeAllItems();
		cabinetComboBox.addItem(addNew);
		for (Cabinet cabinet: cabinets) {
			cabinetComboBox.addItem(cabinet);
		}
	}
	public static List<Cabinet> getCabinets() {
		return copyIterator(cabinetRepository.findAll().iterator());
	}

	public static List<Server> getServers() {
		return copyIterator(serverRepository.findAll().iterator());
	}

	public static List<PowerCCT> getPowerCCTs() {
		return copyIterator(powerCCTRepository.findAll().iterator());
	}

	public static void setCabinets(List<Cabinet> cabinets) {
		ServerTrackerCabinetSwingApplicationWithMySQLDB.cabinets = cabinets;
	}

	public static void setServers(List<Server> servers) {
		ServerTrackerCabinetSwingApplicationWithMySQLDB.servers = servers;
	}

	public static void setPowerCCTs(List<PowerCCT> powerCCTs) {
		ServerTrackerCabinetSwingApplicationWithMySQLDB.powerCCTs = powerCCTs;
	}

	public static void refreshLocalLists() {
		setPowerCCTs(getPowerCCTs());
		setServers(getServers());
		setCabinets(getCabinets());
	}
	
	// Save the data to database
	public void doSave() {
		String id = idTextField.getText();
		String serverName = serverNameTextField.getText();
		String serverIP = serverIPTextField.getText();
		PowerCCT powerCircuit;
		Cabinet serverCabinet;  
		Server server;
//		if (Helper.findFirstPowerCCTExactName(cabinets, powerCCTComboBox.getSelectedItem().toString()) != null) {
//			powerCircuit = Helper.findFirstPowerCCTExactName(cabinets, powerCCTComboBox.getSelectedItem().toString());
		if (powerCCTComboBox.getSelectedItem() != null && powerCCTComboBox.getSelectedIndex() != 0) {
			powerCircuit = Helper.findFirstPowerCCTExactName(cabinets, powerCCTComboBox.getSelectedItem().toString());
		} else {
			powerCircuit = new PowerCCT();
			powerCircuit.setName(powerCCTTextField.getText());
			powerCCTs.add(powerCircuit);
			powerCCTRepository.save(powerCircuit);
		}
		if (CustomQueryHelper.getServerWithId(id) != null) {
			server = CustomQueryHelper.getServerWithId(id);
		} else {
			server = new Server(id, serverName, serverIP, powerCircuit);
//			serverRepository.save(server);
		}
		
		// populate server from Swing UI
		server.setName(serverName);
		server.setIp(serverIP);
		server.setPowerCCT(powerCircuit);
//		if (Helper.findFirstCabinetExactName(cabinets, cabinetComboBox.getSelectedItem().toString()) != null) {
//			serverCabinet = Helper.findFirstCabinetExactName(cabinets, cabinetComboBox.getSelectedItem().toString());
		if (cabinetComboBox.getSelectedItem() != null && powerCCTComboBox.getSelectedIndex() != 0) {
			serverCabinet = (Cabinet) cabinetComboBox.getSelectedItem();
		} else {
			serverCabinet = new Cabinet();
			serverCabinet.setId(getNextCabinetId());
			serverCabinet.addPowerCCT(powerCircuit);
			serverCabinet.setName(cabinetTextField.getText());
//			serverCabinet.addServer(server);
//			cabinetRepository.save(serverCabinet);
		}
		save(serverCabinet, server);
		doCleanCabinets();
		table.clearSelection();
		checkButtons();
		refreshTable();
	}
	

	/**
	 * Update entry of a server, add entry as needed
	 * @param servers
	 * @param server
	 */
	public static void save(Cabinet newCabinet, Server server) {
		boolean foundUpdate = false;
		if (! cabinets.contains(newCabinet)) {
//			servers.add(server);
//			serverRepository.save(server);
			cabinets.add(newCabinet);
			System.out.println("Cabinet Name: " + newCabinet);
			System.out.println(" Power array: " + newCabinet.getPowerCCTArray());
			System.out.println(" Servers array: " + newCabinet.getServersArray());
			cabinetRepository.save(newCabinet);
			debugPrintServers();
		}
		outerloop:
		for (Cabinet cabinet : cabinets) {
//			Iterator<Server> iter = cabinet.getServersArray().iterator();
			Iterator<Server> iter = cabinet.getServersArray().iterator();
			while (iter.hasNext()) {
				Server serverLoop = iter.next();
				if (serverLoop.getId().equals(server.getId())) {
					if (cabinet.getId().equals(newCabinet.getId())) {
						serverLoop.setName(server.getName());
						serverLoop.setIp(server.getIp());
						serverLoop.setPowerCCT(server.getPowerCCT());
						debugPrintServers();
						serverRepository.save(serverLoop);
						foundUpdate = true;
						break outerloop;
					} else {
						//updating local data
						iter.remove();
						cabinet.removeServer(server);
						newCabinet.addServer(server);
						//updating database
						cabinets.add(newCabinet);
						serverRepository.save(server);
						cabinetRepository.save(cabinet);
						System.out.println("Cabinet Name: " + newCabinet);
						System.out.println(" Power array: " + newCabinet.getPowerCCTArray());
						System.out.println(" Servers array: " + newCabinet.getServersArray());
						cabinetRepository.save(newCabinet);
						foundUpdate = true;
						break outerloop;
					}
				}
			}
		}
		if (!foundUpdate) { // do an insert
			newCabinet.addServer(server);
			cabinets.add(newCabinet);
			cabinetRepository.save(newCabinet);
		}
		debugPrintServers();
	}
	
	public static void debugPrintServers() {
		for (Cabinet cabinet: getCabinets()) {
			System.out.println("Cabinet: " + cabinet);
			for (Server server: cabinet.getServersArray()) {
				System.out.println("Server: " + server.getName() + " ID: " + server.getId());
			}
			for (PowerCCT powerCCT: cabinet.getPowerCCTArray()) {
				System.out.println("PowerCCT: " + powerCCT.getName() + " ID: " + powerCCT.getId());
				
			}
		}
		for (Server server: getServers()) {
			System.out.println("Server: " + server.getName() + " ID: " + server.getId());
		}
	}
	
	// Delete item from database
	public void doDelete() {
		String id = idTextField.getText();
		Server server = Helper.findServerExactName(servers, serverNameTextField.getText());
		Helper.delete(getCabinets(), server);
		serverRepository.delete(server.getId());
		table.clearSelection();
		refreshTable();
		btnDelete.setEnabled(false);
	}

	// Clear text fields
	public void doNew() {
		String id = getNextId();
		idTextField.setText(id);
		serverNameTextField.setText("");
		serverIPTextField.setText("");
//		cabinetTextField.setText("");
//		powerCCTTextField.setText("");
	}
	
	/**
	 * Clear out empty cabinets
	 */
	public void doCleanCabinets() {
		Iterator<Cabinet> iter = getCabinets().iterator();
		while (iter.hasNext()) {
			Cabinet cabinetLoop = iter.next();
			if (cabinetLoop.getServersArray().size() < 1) {
				iter.remove();
			}
		}
	}
	
	/**
	 * 
	 * @return next available ID based on highest in use
	 */
	public String getNextId() {
		int searchLocation;
		if (getCabinets().size() > 0) {
			searchLocation = getCabinets().size()-1;
		} else {
			searchLocation = 0;
		}
		
		boolean foundId = false;
		while (! foundId) {
			searchLocation++;
			if (findId(String.valueOf(searchLocation)) == null) {
				foundId = true;
			}
		}
		return String.valueOf(searchLocation);
	}

	/**
	 * Search for a specific ID in the server's list
	 * @param id
	 * @return
	 */
	public String findId(String id) {
		for (Cabinet cabinet : getCabinets()) {
			for (Server server : cabinet.getServersArray()) {
				if (server.getId().contentEquals(id)) {
					return id;
				}
			}
		}

		return null;
	}
	
	/**
	 * 
	 * @return next available cabinet ID based on highest in use
	 */
	public String getNextCabinetId() {
		int searchLocation;
		if (getCabinets().size() > 0) {
			searchLocation = getCabinets().size()-1;
		} else {
			searchLocation = 0;
		}
		
		boolean foundId = false;
		while (! foundId) {
			searchLocation++;
			if (findId(String.valueOf(searchLocation)) == null) {
				foundId = true;
			}
		}
		return String.valueOf(searchLocation);
	}

	/**
	 * Search for a specific ID in the cabinet's list
	 * @param id
	 * @return
	 */
	public String findCabinetId(String id) {
		for (Cabinet cabinet : getCabinets()) {
			if (cabinet.getId().contentEquals(id)) {
				return id;
			}
		}

		return null;
	}
	
	private void populateFields() {
		try {
			idTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 0).toString());
			serverNameTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 1).toString());
			serverIPTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 2).toString());
			cabinetComboBox.getModel().setSelectedItem(table.getModel().getValueAt(table.getSelectedRow(), 3));
			updatePowerCCTList();
			powerCCTComboBox.getModel().setSelectedItem(table.getModel().getValueAt(table.getSelectedRow(), 4));

		} catch (Exception e) {}
	}
	
	private void refreshTable() {
		refreshLocalLists();
		// swingTeacherModel = new SwingTeacherModel();
		Object[][] data = null;
		data = new Object[countServers(cabinets)][5];
		int i = 0;
		
		for (Cabinet cabinet : cabinets) {
			List<Server> currentCabinet = cabinet.getServersArray();
			for (Server server : currentCabinet) {
				data[i][0] = server.getId();
				data[i][1] = server.getName();
				data[i][2] = server.getIp();
				data[i][3] = cabinet;
				data[i][4] = server.getPowerCCT();
				i++;
			}
		}
		swingServerTrackerModel.setDataVector(data, columnNames);
		table.repaint();
		checkButtons();
	}
	
	private int countServers(List<Cabinet> cabinets) {
		int serverCount = 0;
		for (Cabinet cabinet : cabinets) {
			serverCount+= cabinet.getServersArray().size();
		}
		return serverCount;
	}
	

}
