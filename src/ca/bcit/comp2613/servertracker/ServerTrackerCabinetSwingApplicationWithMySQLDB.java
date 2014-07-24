package ca.bcit.comp2613.servertracker;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.text.Document;

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
	private JTable powerTable;

	private JComboBox cabinetComboBox;
	private JComboBox powerCCTComboBox;
	private JComboBox cabinetModifyList;
	private JComboBox powerCCTModifyList;

	private JLabel lblServerIp;
	private JLabel lblServerId;
	private JLabel lblCabinetId;
	private JLabel lblPowerCCTId;
	private JLabel lblServerCPUCores;
	private JLabel lblServerGigabytesMemory;
	private JLabel lblServerServiceTag;
	private JLabel lblServer;
	private JLabel lblServerWarrantyExpiration;
	private JLabel lblModifyCabinets;

	private JTextField serverNameTextField;
	private JTextField serverIPTextField;
	private JTextField serverPurposeTextField;
	private JTextField serverOwnerTextField;

	private JFormattedTextField serverProjectedPowerTextField;
	private JFormattedTextField serverCPUSocketsTextField;
	private JFormattedTextField serverCPUCoresTextField;
	private JFormattedTextField serverMemoryTextField;

	private JTextField serverServiceTagTextField;
	private JTextField serverWarrantyDateTextField;
	private JTextField cabinetAddNewTextField;
	private JTextField powerAddNewTextField;

	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnAddNewCabinet;
	private JButton btnNewButton;
	private JButton btnDestroyCabinet;
	private JButton btnDestroyPowerCCT;
	private JButton btnAddNewPowerCCT;
	
	private JPanel serverPanel;
	private JPanel cabinetPanel;
	private JPanel powerCCTPanel;
	
	private String userFeedback = "";
	
	private SwingServerTrackerModel swingServerTrackerModel;
	public String[] columnNames = new String[] { "id", "Name", "IP", "Purpose", "Owner", "Power Usage", "CPU Sockets", "CPU Cores", "Memory", "Service Tag", "Warranty Date", "Cabinet", "PowerCCT" };
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
	private boolean undoButton = false;

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
	 * 1 the contents of the frame.
	 */
	private void initialize() {
		try {
		frame = new JFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setBounds(100, 100, 1202, 877);
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

		JLabel lblServerName = new JLabel("Name");
		lblServerName.setBounds(44, 352, 103, 14);
		frame.getContentPane().add(lblServerName);

		serverNameTextField = new JTextField();
		serverNameTextField.setBounds(159, 349, 325, 20);
		frame.getContentPane().add(serverNameTextField);
		serverNameTextField.setColumns(10);

		serverIPTextField = new JTextField();
		serverIPTextField.setBounds(159, 382, 325, 20);
		frame.getContentPane().add(serverIPTextField);
		serverIPTextField.setColumns(10);

		lblServerIp = new JLabel("IP");
		lblServerIp.setBounds(44, 385, 77, 14);
		frame.getContentPane().add(lblServerIp);

		lblServerId = new JLabel("id");
		lblServerId.setEnabled(false);
		lblServerId.setBounds(44, 277, 46, 14);
		frame.getContentPane().add(lblServerId).setVisible(false);

		cabinetModifyList = new JComboBox();
		cabinetModifyList.setBounds(556, 349, 325, 20);
		frame.getContentPane().add(cabinetModifyList);
		
//		cabinetTextField = new JTextField();
//		cabinetTextField.setBounds(159, 412, 325, 20);
//		frame.getContentPane().add(cabinetTextField);
//		cabinetTextField.setColumns(10);
		cabinetComboBox = new JComboBox();
		cabinetComboBox.setBounds(159, 703, 325, 20);
		updateCabinetList();
		frame.getContentPane().add(cabinetComboBox);

		cabinetComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePowerCCTList();
				checkButtons();
			}
		});
		
		lblCabinetId = new JLabel("Cabinet");
		lblCabinetId.setBounds(44, 703, 77, 14);
		frame.getContentPane().add(lblCabinetId);
		
//		powerCCTTextField = new JTextField();
//		powerCCTTextField.setBounds(159, 459, 325, 20);
//		frame.getContentPane().add(powerCCTTextField);
//		powerCCTTextField.setColumns(10);
		powerCCTComboBox = new JComboBox();
		powerCCTComboBox.setBounds(159, 739, 325, 20);
		updatePowerCCTList();
		frame.getContentPane().add(powerCCTComboBox);
		powerCCTComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkButtons();
			}
		});

		
		lblPowerCCTId = new JLabel("Power CCT");
		lblPowerCCTId.setBounds(44, 739, 120, 14);
		frame.getContentPane().add(lblPowerCCTId);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
				checkButtons();
			}
		});
		btnSave.setEnabled(false);
		
		btnSave.setBounds(395, 775, 89, 23);
		frame.getContentPane().add(btnSave);

		btnDelete = new JButton("Delete Selected");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDelete();
				checkButtons();
			}
		});
		btnDelete.setBounds(1026, 274, 144, 23);
		frame.getContentPane().add(btnDelete);
		btnDelete.setEnabled(false);
		
		btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doNew();
				checkButtons();
			}
		});
		btnNewButton.setBounds(44, 775, 89, 23);
		frame.getContentPane().add(btnNewButton);

		idTextField = new JTextField();
		idTextField.setEnabled(false);
		idTextField.setEditable(false);
		idTextField.setBounds(159, 274, 325, 20);
		frame.getContentPane().add(idTextField).setVisible(false);
		idTextField.setColumns(10);
		
		serverPurposeTextField = new JTextField();
		serverPurposeTextField.setColumns(10);
		serverPurposeTextField.setBounds(159, 418, 325, 20);
		frame.getContentPane().add(serverPurposeTextField);
		
		JLabel lblServerPurpose = new JLabel("Purpose");
		lblServerPurpose.setBounds(44, 421, 77, 14);
		frame.getContentPane().add(lblServerPurpose);
		
		serverOwnerTextField = new JTextField();
		serverOwnerTextField.setColumns(10);
		serverOwnerTextField.setBounds(159, 454, 325, 20);
		frame.getContentPane().add(serverOwnerTextField);

		JLabel lblServerOwner = new JLabel("Owner");
		lblServerOwner.setBounds(44, 457, 77, 14);
		frame.getContentPane().add(lblServerOwner);
		
		serverProjectedPowerTextField = new JFormattedTextField();
		serverProjectedPowerTextField.setValue(new Double(0.00));
		serverProjectedPowerTextField.setColumns(10);
		serverProjectedPowerTextField.setBounds(159, 490, 325, 20);
		frame.getContentPane().add(serverProjectedPowerTextField);
		
		JLabel lblServerProjectedPower = new JLabel("Power Usage");
		lblServerProjectedPower.setBounds(44, 493, 135, 17);
		frame.getContentPane().add(lblServerProjectedPower);
		
		serverCPUSocketsTextField = new JFormattedTextField();
		serverCPUSocketsTextField.setValue(new Integer(0));
		serverCPUSocketsTextField.setColumns(10);
		serverCPUSocketsTextField.setBounds(159, 523, 325, 20);
		frame.getContentPane().add(serverCPUSocketsTextField);
		
		JLabel lblServerSockets = new JLabel("CPU Sockets");
		lblServerSockets.setBounds(44, 526, 103, 14);
		frame.getContentPane().add(lblServerSockets);
		
		serverCPUCoresTextField = new JFormattedTextField();
		serverCPUCoresTextField.setValue(new Integer(0));
		serverCPUCoresTextField.setColumns(10);
		serverCPUCoresTextField.setBounds(159, 559, 325, 20);
		frame.getContentPane().add(serverCPUCoresTextField);
		
		lblServerCPUCores = new JLabel("CPU Cores");
		lblServerCPUCores.setBounds(44, 562, 77, 14);
		frame.getContentPane().add(lblServerCPUCores);
		
		serverMemoryTextField = new JFormattedTextField();
		serverMemoryTextField.setValue(new Integer(0));
		serverMemoryTextField.setColumns(10);
		serverMemoryTextField.setBounds(159, 595, 325, 20);
		frame.getContentPane().add(serverMemoryTextField);
		
		lblServerGigabytesMemory = new JLabel("Memory");
		lblServerGigabytesMemory.setBounds(44, 598, 77, 17);
		frame.getContentPane().add(lblServerGigabytesMemory);
		
		serverServiceTagTextField = new JTextField();
		serverServiceTagTextField.setColumns(10);
		serverServiceTagTextField.setBounds(159, 631, 325, 20);
		frame.getContentPane().add(serverServiceTagTextField);
		
		lblServerServiceTag = new JLabel("Service Tag");
		lblServerServiceTag.setBounds(44, 631, 103, 20);
		frame.getContentPane().add(lblServerServiceTag);
		
		serverWarrantyDateTextField = new JTextField();
		serverWarrantyDateTextField.setColumns(10);
		serverWarrantyDateTextField.setBounds(159, 667, 325, 20);
		frame.getContentPane().add(serverWarrantyDateTextField);
		
		lblServerWarrantyExpiration = new JLabel("Warranty Date");
		lblServerWarrantyExpiration.setBounds(44, 670, 103, 17);
		frame.getContentPane().add(lblServerWarrantyExpiration);
		
		JLabel lblModifyCabinets = new JLabel("Cabinet");
		lblModifyCabinets.setBounds(556, 313, 144, 23);
		frame.getContentPane().add(lblModifyCabinets);
		
		btnDestroyCabinet = new JButton("Destroy");
		btnDestroyCabinet.setEnabled(true);
		btnDestroyCabinet.setBounds(898, 348, 103, 23);
		frame.getContentPane().add(btnDestroyCabinet);
		btnDestroyCabinet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destroyCabinet((Cabinet) cabinetModifyList.getSelectedItem());
			}
		});

		JLabel label_1 = new JLabel("Power CCT");
		label_1.setBounds(556, 454, 120, 14);
		frame.getContentPane().add(label_1);
		
		powerCCTModifyList = new JComboBox();
		powerCCTModifyList.setBounds(556, 487, 325, 20);
		frame.getContentPane().add(powerCCTModifyList);
		
		btnDestroyPowerCCT = new JButton("Destroy");
		btnDestroyPowerCCT.setEnabled(false);
		btnDestroyPowerCCT.setBounds(898, 486, 103, 23);
		frame.getContentPane().add(btnDestroyPowerCCT);
		
		cabinetAddNewTextField = new JTextField();
		cabinetAddNewTextField.setColumns(10);
		cabinetAddNewTextField.setBounds(556, 382, 325, 20);
		frame.getContentPane().add(cabinetAddNewTextField);
		cabinetAddNewTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {}
			@Override
			public void insertUpdate(DocumentEvent e) {
				btnAddNewCabinet.setEnabled(true);
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if ( cabinetAddNewTextField.getText().length() < 1) {
					btnAddNewCabinet.setEnabled(false);
				}
			}
			
		});
		
		btnAddNewCabinet = new JButton("Add New");
		btnAddNewCabinet.setEnabled(false);
		btnAddNewCabinet.setBounds(898, 381, 103, 23);
		frame.getContentPane().add(btnAddNewCabinet);
		btnAddNewCabinet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCabinet(cabinetAddNewTextField.getText());
				cabinetAddNewTextField.setText("");
				checkButtons();
				btnAddNewCabinet.setEnabled(false);
			}
		});

		btnAddNewPowerCCT = new JButton("Add New");
		btnAddNewPowerCCT.setEnabled(false);
		btnAddNewPowerCCT.setBounds(898, 526, 103, 23);
		frame.getContentPane().add(btnAddNewPowerCCT);
		btnAddNewPowerCCT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPowerCCT(powerAddNewTextField.getText());
				powerAddNewTextField.setText("");
				checkButtons();
				btnAddNewPowerCCT.setEnabled(false);
			}
		});
		
		powerAddNewTextField = new JTextField();
		powerAddNewTextField.setColumns(10);
		powerAddNewTextField.setBounds(556, 526, 325, 20);
		frame.getContentPane().add(powerAddNewTextField);
		powerAddNewTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {}
			@Override
			public void insertUpdate(DocumentEvent e) {
				btnAddNewPowerCCT.setEnabled(true);
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if ( powerAddNewTextField.getText().length() < 1) {
					btnAddNewPowerCCT.setEnabled(false);
				}
			}
			
		});

		
		lblServer = new JLabel("Server");
		lblServer.setBounds(35, 313, 144, 23);
		frame.getContentPane().add(lblServer);
		
		cabinetPanel = new JPanel();
		cabinetPanel.setBounds(541, 295, 470, 137);
		frame.getContentPane().add(cabinetPanel);
		
		powerCCTPanel = new JPanel();
		powerCCTPanel.setBounds(540, 439, 470, 137);
		frame.getContentPane().add(powerCCTPanel);
		
		powerTable = new JTable((TableModel) null);
		powerTable.setFillsViewportHeight(true);
		powerTable.setBounds(540, 631, 625, 178);
		frame.getContentPane().add(powerTable);
		
		JLabel lblPowerLoad = new JLabel("Power Load");
		lblPowerLoad.setBounds(540, 598, 120, 14);
		frame.getContentPane().add(lblPowerLoad);
		
		serverPanel = new JPanel();
		serverPanel.setBounds(15, 295, 502, 514);
		frame.getContentPane().add(serverPanel);
		updatePowerCCTModifyList();
	}
	
	public void checkButtons() {
		if (undoButton) {
			btnSave.setText("Undo");
//			undoButton = false;
		} else {
			btnSave.setText("Save");
		}
		if (table.getSelectedRow() >= 0) {
			btnDelete.setEnabled(true);
		} else {
			btnDelete.setEnabled(false);
		}
		if (powerCCTComboBox.getSelectedIndex() != 0 && cabinetComboBox.getSelectedIndex() != 0) {
			btnSave.setEnabled(true);
		} else {
			btnSave.setEnabled(false);
		}
	}
	
	public void addCabinet(String cabinetName) {
		//userFeedback
		if (Helper.findCabinetExactName(cabinets, cabinetName).size() == 0) {
			Cabinet newCabinet = new Cabinet(cabinetName);
			newCabinet.setId(UUID.randomUUID().toString());
			cabinetRepository.save(newCabinet);
			cabinets.add(newCabinet);
			updateCabinetList();
			updatePowerCCTModifyList();
		} else {
			userFeedback = "Error, cabinet with this name already exists.";
		}
	}
	

	public void destroyCabinet(Cabinet cabinet) {
		refreshLocalLists();
		while (cabinet.getServersArray().size() > 0) {
			System.out.println("Running...");
			Server server = cabinet.getServersArray().get(0);
			server.setPowerCCT(null);
			serverRepository.save(server);
			cabinet.removeServer(server);
			
		}

		//		for (Server server:cabinet.getServersArray()) {
		//			server.setPowerCCT(null);
		//			serverRepository.save(server);
		//		}
		//		cabinet.clearServersArray();
		while (cabinet.getPowerCCTArray().size() > 0) {
			PowerCCT powerCCT = cabinet.getPowerCCTArray().get(0);
			cabinet.removePowerCCT(powerCCT);
			powerCCTRepository.save(powerCCT);
		}
//		for (PowerCCT powerCCT:cabinet.getPowerCCTArray()) {
//			cabinet.removePowerCCT(powerCCT);
//			powerCCTRepository.save(powerCCT);
//		}
		cabinetRepository.delete(cabinet);
		cabinets.remove(cabinet);
		refreshLocalLists();
		updateCabinetList();
		updatePowerCCTModifyList();
		refreshTable();
	}
	
	public void addPowerCCT(String powerCCTName) {
		if(Helper.findPowerCCTsExactName(powerCCTs, powerCCTName).size() == 0) {
			PowerCCT newPowerCCT = new PowerCCT(powerCCTName);
			powerCCTRepository.save(newPowerCCT);
			powerCCTs.add(newPowerCCT);
			updateCabinetList();
			updatePowerCCTModifyList();
		} else {
			userFeedback = "Error, power circuit with this name already exists.";
		}
	}

	public void destroyPowerCCT(PowerCCT powerCCT) {
		System.out.println("Please flesh this out...");
		updateCabinetList();
		updatePowerCCTModifyList();
	}
	
	public void updatePowerCCTList() {
		refreshLocalLists();
		powerCCTComboBox.removeAllItems();
		powerCCTComboBox.addItem(addNew);
		if (cabinetComboBox.getModel().getSelectedItem() != null) {
			if (cabinetComboBox.getModel().getSelectedItem().getClass() == Cabinet.class) {
				for (PowerCCT powerCCT: ((Cabinet) cabinetComboBox.getModel().getSelectedItem()).getPowerCCTArray()) {
					powerCCTComboBox.addItem(powerCCT);
				}
				for (PowerCCT powerCCT: powerCCTs) {
					System.out.println("updatePowerCCTList deadline: " + Helper.findPowerCCTInCabinet(cabinets,powerCCT));
					System.out.println("PowerCCT: " + powerCCT);
					if (Helper.findPowerCCTInCabinet(cabinets,powerCCT) == null) {
						System.out.println("Empty.. adding: " + powerCCT);
						powerCCTComboBox.addItem(powerCCT);
					}
				}
			}
		}
	}

	public void updateCabinetList() {
		cabinetComboBox.removeAllItems();
		cabinetModifyList.removeAllItems();
		cabinetComboBox.addItem(addNew);
		for (Cabinet cabinet: cabinets) {
			cabinetComboBox.addItem(cabinet);
			cabinetModifyList.addItem(cabinet);
		}
	}

	public void updatePowerCCTModifyList() {
		setPowerCCTs(getPowerCCTs());
		powerCCTModifyList.removeAllItems();
		for (PowerCCT powerCCT: powerCCTs) {
			powerCCTModifyList.addItem(powerCCT);
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
		undoButton = false;
		String id = idTextField.getText();
		String serverName;
		String serverIP;
		String serverPurpose;
		String serverOwner;
		double serverProjectedPower;
		int serverCores;
		int serverProcessors;;
		int serverMemory;
		String serverServiceTag;
		String serverWarranty;
		System.out.println("Save Start: 0");
		if (serverNameTextField.getText() != null) {
			serverName = serverNameTextField.getText();
		} else {
			serverName = "";
		}
		System.out.println("1");
		if (serverIPTextField.getText() != null) {
			serverIP = serverIPTextField.getText();
		} else {
			serverIP = "";
		}
		System.out.println("2");
		if (serverPurposeTextField.getText() != null) {
			serverPurpose = serverPurposeTextField.getText();
		} else {
			serverPurpose = "";
		}
		System.out.println("3");
		if (serverOwnerTextField.getText() != null) {
			serverOwner = serverOwnerTextField.getText();
		} else {
			serverOwner = "";
		}
		System.out.println("4");
		if (serverProjectedPowerTextField.getValue() != null) {
			serverProjectedPower = (double) serverProjectedPowerTextField.getValue();
		} else {
			serverProjectedPower = 0;
		}
		System.out.println("5");
		if (serverCPUCoresTextField.getValue() != null) {
			serverCores = (int) serverCPUCoresTextField.getValue();
		} else {
			serverCores = 0;
		}
		System.out.println("6");
		if (serverCPUSocketsTextField.getValue() != null) {
			serverProcessors = (int) serverCPUSocketsTextField.getValue();
		} else {
			serverProcessors = 0;
		}
		System.out.println("7");
		if (serverMemoryTextField.getValue() != null) {
			serverMemory = (int) serverMemoryTextField.getValue();
		} else {
			serverMemory = 0;
		}
		System.out.println("8");
		if (serverServiceTagTextField.getText() != null) {
			serverServiceTag = serverServiceTagTextField.getText();
		} else {
			serverServiceTag = "";
		}
		System.out.println("9");
		if (serverWarrantyDateTextField.getText() != null) {
			serverWarranty = serverWarrantyDateTextField.getText();
		} else {
			serverWarranty = "";
		}
		System.out.println("Save end...");
		PowerCCT powerCircuit;
		Cabinet serverCabinet;  
		Server server;
		powerCircuit = (PowerCCT) powerCCTComboBox.getSelectedItem();
		//Depreciated by new method for data entry
//		if (powerCCTComboBox.getSelectedItem() != null && powerCCTComboBox.getSelectedIndex() != 0) {
//			powerCircuit = (PowerCCT) powerCCTComboBox.getSelectedItem();
//		} else {
//			powerCircuit = new PowerCCT();
//			powerCircuit.setName(powerCCTTextField.getText());
//			powerCCTs.add(powerCircuit);
//			powerCCTRepository.save(powerCircuit);
//		}
		if (CustomQueryHelper.getServerWithId(id) != null) {
			server = CustomQueryHelper.getServerWithId(id);
		} else {
			server = new Server(id, serverName, serverIP, powerCircuit);
		}
		server.setName(serverName);
		server.setIp(serverIP);
		server.setPowerCCT(powerCircuit);
		server.setPurpose(serverPurpose);
		server.setOwner(serverOwner);
		server.setProjectedPower(serverProjectedPower);
		server.setCores(serverCores);
		server.setProcessors(serverProcessors);
		server.setMemory(serverMemory);
		server.setServiceTag(serverServiceTag);
		server.setWarrantyExpiration(serverWarranty);
		serverCabinet = (Cabinet) cabinetComboBox.getSelectedItem();
		if (! serverCabinet.getPowerCCTArray().contains(powerCircuit)) {
			serverCabinet.addPowerCCT(powerCircuit);
		}
		//Depreciated by new method for data entry
//		if (cabinetComboBox.getSelectedItem() != null && powerCCTComboBox.getSelectedIndex() != 0) {
//			serverCabinet = (Cabinet) cabinetComboBox.getSelectedItem();
//		} else {
//			serverCabinet = new Cabinet();
//			serverCabinet.setId(getNextCabinetId());
//			serverCabinet.addPowerCCT(powerCircuit);
//			serverCabinet.setName(cabinetTextField.getText());
//		}
		save(serverCabinet, server);
		doCleanCabinets();
		refreshLocalLists();
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
//		if (! cabinets.contains(newCabinet)) {
//			debugPrintServers();
//			System.out.println("And new cabinet is... " + newCabinet);
//			cabinets.add(newCabinet);
//			cabinetRepository.save(newCabinet);
//			debugPrintServers();
//		}
		outerloop:
		for (Cabinet cabinet : cabinets) {
			Iterator<Server> iter = cabinet.getServersArray().iterator();
			while (iter.hasNext()) {
				Server serverLoop = iter.next();
				if (serverLoop.getId().equals(server.getId())) {
					if (cabinet.getId().equals(newCabinet.getId())) {
						serverLoop.setName(server.getName());
						serverLoop.setIp(server.getIp());
						serverLoop.setPowerCCT(server.getPowerCCT());
						serverLoop.setPurpose(server.getPurpose());
						serverLoop.setOwner(server.getOwner());
						serverLoop.setProjectedPower(server.getProjectedPower());
						serverLoop.setCores(server.getCores());
						serverLoop.setProcessors(server.getProcessors());
						serverLoop.setMemory(server.getMemory());
						serverLoop.setServiceTag(server.getServiceTag());
						serverLoop.setWarrantyExpiration(server.getWarrantyExpiration());
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
						cabinetRepository.save(newCabinet);
						foundUpdate = true;
						break outerloop;
					}
				}
			}
		}
		if (!foundUpdate) { 
			// do an insert
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
		Server server = (Server) table.getModel().getValueAt(table.getSelectedRow(), 1);
		Cabinet cabinet = (Cabinet) table.getModel().getValueAt(table.getSelectedRow(), 3);
		cabinet.removeServer(server);
//		Helper.delete(getCabinets(), server);
		cabinetRepository.save(cabinet);
		serverRepository.delete(server.getId());
		table.clearSelection();
		refreshTable();
		btnDelete.setEnabled(false);
		undoButton = true;
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
			idTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
			System.out.println("1");
			if (table.getModel().getValueAt(table.getSelectedRow(), 1) == null) {
				serverNameTextField.setText("");
			} else {
				serverNameTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
			}
			System.out.println("2");
			if (table.getModel().getValueAt(table.getSelectedRow(), 2) == null) {
				serverIPTextField.setText("");
			} else {
				serverIPTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 2).toString());
			}
			System.out.println("3");
			if (table.getModel().getValueAt(table.getSelectedRow(), 3) == null) {
				serverPurposeTextField.setText("");
			} else {
				serverPurposeTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 3).toString());
			}
			System.out.println("4");
			if (table.getModel().getValueAt(table.getSelectedRow(), 4) == null) {
				serverOwnerTextField.setText("");
			} else {
				serverOwnerTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 4).toString());
			}
			System.out.println("5");
			if (table.getModel().getValueAt(table.getSelectedRow(), 4) == null) {
				serverOwnerTextField.setText("");
			} else {
				serverOwnerTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 4).toString());
			}
			System.out.println("6");
			if (table.getModel().getValueAt(table.getSelectedRow(), 5) == null) {
				serverProjectedPowerTextField.setValue(0);
			} else {
				serverProjectedPowerTextField.setValue((double) table.getModel().getValueAt(table.getSelectedRow(), 5));
			}
			System.out.println("7");
			if (table.getModel().getValueAt(table.getSelectedRow(), 6) == null) {
				serverCPUSocketsTextField.setValue(0);
			} else {
				serverCPUSocketsTextField.setValue((int) table.getModel().getValueAt(table.getSelectedRow(), 6));
			}
			System.out.println("8");
			if (table.getModel().getValueAt(table.getSelectedRow(), 7) == null) {
				serverCPUCoresTextField.setValue(0);
			} else {
				serverCPUCoresTextField.setValue((int) table.getModel().getValueAt(table.getSelectedRow(), 7));
			}
			System.out.println("9");
			if (table.getModel().getValueAt(table.getSelectedRow(), 8) == null) {
				serverMemoryTextField.setValue(0);
			} else {
				serverMemoryTextField.setValue(table.getModel().getValueAt(table.getSelectedRow(), 8));
			}
			System.out.println("10");
			if (table.getModel().getValueAt(table.getSelectedRow(), 9) == null) {
				serverServiceTagTextField.setText("");
			} else {
				serverServiceTagTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 9).toString());
			}
			System.out.println("11");
			if (table.getModel().getValueAt(table.getSelectedRow(), 10) == null) {
				serverWarrantyDateTextField.setText("");
			} else {
				serverWarrantyDateTextField.setText(table.getModel().getValueAt(table.getSelectedRow(), 10).toString());
			}
			System.out.println("12");
			if (table.getModel().getValueAt(table.getSelectedRow(), 11) != null) {
				cabinetComboBox.getModel().setSelectedItem(table.getModel().getValueAt(table.getSelectedRow(), 11));
				updatePowerCCTList();
			} else {
				cabinetComboBox.setSelectedIndex(0);
				updatePowerCCTList();
			}
			System.out.println("13");
			if (table.getModel().getValueAt(table.getSelectedRow(), 12) != null) {
				powerCCTComboBox.getModel().setSelectedItem(table.getModel().getValueAt(table.getSelectedRow(), 12));
			} else {
				powerCCTComboBox.setSelectedIndex(0);
			}

		} catch (Exception e) {}
	}
	
	private void refreshTable() {
		refreshLocalLists();
		System.out.println("Server count 3: " + servers.size());
		// swingTeacherModel = new SwingTeacherModel();
		Object[][] data = null;
		data = new Object[servers.size()][13];
		int i = 0;
		
		for (Server server : servers) {
			data[i][0] = server.getId();
			data[i][1] = server;
			data[i][2] = server.getIp();
			data[i][3] = server.getPurpose();
			data[i][4] = server.getOwner();
			data[i][5] = server.getProjectedPower();
			data[i][6] = server.getProcessors();
			data[i][7] = server.getCores();
			data[i][8] = server.getMemory();
			data[i][9] = server.getServiceTag();
			data[i][10] = server.getWarrantyExpiration();
			System.out.println("Searching for cabinet!!!!!");
			data[i][11] = Helper.findServerInCabinet(cabinets,server);
			data[i][12] = server.getPowerCCT();
			i++;
		}
		swingServerTrackerModel.setDataVector(data, columnNames);
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.repaint();
//		updateCabinetList();
//		updatePowerCCTModifyList();
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
