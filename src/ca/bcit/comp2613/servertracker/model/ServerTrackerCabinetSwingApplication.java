package ca.bcit.comp2613.servertracker.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Iterator;
//import java.util.UUID;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;
import ca.bcit.comp2613.servertracker.model.Server;
import ca.bcit.comp2613.a00251471.util.Helper;

public class ServerTrackerCabinetSwingApplication {
	public JFrame frame;
	private JTable table;
	private JTextField serverNameTextField;
	private JTextField serverIPTextField;
	private JTextField cabinetTextField;
	private JTextField powerCCTTextField;
	private JLabel lblServerIp;
	private JLabel lblServerId;
	private JLabel lblCabinetId;
	private JLabel lblPowerCCTId;
	private SwingServerTrackerModel swingServerTrackerModel;
	public String[] columnNames = new String[] { "id", "Server Name",
			"Server IP", "Cabinet", "PowerCCT" };
	private JTextField idTextField;
	private static List<Cabinet> cabinets;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ServerTrackerCabinetSwingApplication window = new ServerTrackerCabinetSwingApplication();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the application.
	 */
	public ServerTrackerCabinetSwingApplication() {
		setCabinets(Helper.createCabinets(100));
		initialize();
		initTable();
	}

	public ServerTrackerCabinetSwingApplication(ArrayList<Cabinet> cabinets) {
		setCabinets(cabinets);
		initialize();
		initTable();
	}

	public void doSave() {
		String id = idTextField.getText();
		String serverName = serverNameTextField.getText();
		String serverIP = serverIPTextField.getText();
		PowerCCT powerCircuit;
		Cabinet serverCabinet;
		if (Helper.findFirstPowerCCTExactName(getCabinets(), powerCCTTextField.getText()) != null) {
			powerCircuit = Helper.findFirstPowerCCTExactName(getCabinets(), powerCCTTextField.getText());
		} else {
			powerCircuit = new PowerCCT();
			powerCircuit.setName(powerCCTTextField.getText());
		}
		if (Helper.findFirstCabinetExactName(getCabinets(), cabinetTextField.getText()) != null) {
			serverCabinet = Helper.findFirstCabinetExactName(getCabinets(), cabinetTextField.getText());
		} else {
			serverCabinet = new Cabinet();
			serverCabinet.setId(getNextCabinetId());
			serverCabinet.addPowerCCT(powerCircuit);
			serverCabinet.setName(cabinetTextField.getText());
		}
//		System.out.println("Cabinet is..." + serverCabinet.getName() + "New server..." + serverName);
		Server server = new Server(id, serverName, serverIP, powerCircuit);
		Helper.save(getCabinets(), serverCabinet, server);
		doCleanCabinets();
		table.clearSelection();
		refreshTable();
		}
	
	public void doDelete() {
		String id = idTextField.getText();
		Server server = new Server(id, null, null);
		Helper.delete(getCabinets(), server);
		table.clearSelection();
		refreshTable();
	}
	
	public void doNew() {
		String id = getNextId();
		idTextField.setText(id);
		serverNameTextField.setText("");
		serverIPTextField.setText("");
		cabinetTextField.setText("");
		powerCCTTextField.setText("");
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
		
		System.out.println("Test: " + searchLocation);
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
	
	private void initTable() {
		// table = new JTable(swingTeacherModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							populateFields();
						}
					}
				});
		refreshTable();

	}
	
	private void populateFields() {
		try {
			idTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 0).toString());
			serverNameTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 1).toString());
			serverIPTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 2).toString());
			cabinetTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 3).toString());
			powerCCTTextField.setText(table.getModel()
					.getValueAt(table.getSelectedRow(), 4).toString());
		} catch (Exception e) {}
	}
	
	private void refreshTable() {
		// swingTeacherModel = new SwingTeacherModel();
		Object[][] data = null;
		data = new Object[countServers(getCabinets())][5];
		int i = 0;
		
		for (Cabinet cabinet : getCabinets()) {
			ArrayList<Server> currentCabinet = cabinet.getServersArray();
			for (Server server : currentCabinet) {
				data[i][0] = server.getId();
				data[i][1] = server.getName();
				data[i][2] = server.getIp();
				data[i][3] = cabinet.getName();
				data[i][4] = server.getPowerCCT().getName();
				i++;
			}
		}
		swingServerTrackerModel.setDataVector(data, columnNames);
		table.repaint();
	}
	
	private int countServers(List<Cabinet> cabinets) {
		int serverCount = 0;
		for (Cabinet cabinet : cabinets) {
			serverCount+= cabinet.getServersArray().size();
		}
		return serverCount;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
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

		cabinetTextField = new JTextField();
		cabinetTextField.setBounds(159, 412, 325, 20);
		frame.getContentPane().add(cabinetTextField);
		cabinetTextField.setColumns(10);

		lblCabinetId = new JLabel("Cabinet");
		lblCabinetId.setBounds(44, 412, 77, 14);
		frame.getContentPane().add(lblCabinetId);

		powerCCTTextField = new JTextField();
		powerCCTTextField.setBounds(159, 459, 325, 20);
		frame.getContentPane().add(powerCCTTextField);
		powerCCTTextField.setColumns(10);

		lblPowerCCTId = new JLabel("Power CCT");
		lblPowerCCTId.setBounds(44, 459, 77, 14);
		frame.getContentPane().add(lblPowerCCTId);

		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		btnSave.setBounds(44, 506, 89, 23);
		frame.getContentPane().add(btnSave);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDelete();
			}
		});
		btnDelete.setBounds(169, 506, 89, 23);
		frame.getContentPane().add(btnDelete);

		JButton btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doNew();
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

	public static List<Cabinet> getCabinets() {
		return cabinets;
	}

	public static void setCabinets(List<Cabinet> cabinets) {
		ServerTrackerCabinetSwingApplication.cabinets = cabinets;
	}
}
