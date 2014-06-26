package ca.bcit.comp2613.servertracker.model;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import ca.bcit.comp2613.servertracker.model.Server;
import ca.bcit.comp2613.a00251471.util.Helper;

public class ServerTrackerSwingApplication {
	private JFrame frame;
	private JTable table;
	private JTextField serverNameTextField;
	private JTextField serverIPTextField;
	private JLabel lblServerIp;
	private JLabel lblServerId;
	private SwingServerTrackerModel swingServerTrackerModel;
	public String[] columnNames = new String[] { "id", "Server Name",
			"Server IP" };
	private JTextField idTextField;
	public static List<Server> servers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerTrackerSwingApplication window = new ServerTrackerSwingApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerTrackerSwingApplication() {
		servers = Helper.createServers(100);
		initialize();
		initTable();
	}
	
	public void doSave() {
		String id = idTextField.getText();
		String serverName = serverNameTextField.getText();
		String serverIP = serverIPTextField.getText();
		Server server = new Server(id, serverName, serverIP);
		Helper.save(servers, server);
		//table.clearSelection();
		refreshTable();
	}
	
	public void doDelete() {
		String id = idTextField.getText();
		Server server = new Server(id, null, null);
		Helper.delete(servers, server);
		refreshTable();
	}
	
	public void doNew() {
		String id = UUID.randomUUID().toString();
		idTextField.setText(id);
		serverNameTextField.setText("");
		serverIPTextField.setText("");
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
		} catch (Exception e) {}
	}
	
	private void refreshTable() {
		// swingTeacherModel = new SwingTeacherModel();
		Object[][] data = null;

		data = new Object[servers.size()][3];
		int i = 0;
		for (Server server : servers) {
			data[i][0] = server.getId();
			data[i][1] = server.getName();
			data[i][2] = server.getIp();
			i++;
		}
		swingServerTrackerModel.setDataVector(data, columnNames);
		table.repaint();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 601, 499);
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
		scrollPane.setBounds(0, 11, 585, 247);
		frame.getContentPane().add(scrollPane);
		// scrollPane.add(table);
		// frame.getContentPane().add(table);

		JLabel lblFirstName = new JLabel("Server Name");
		lblFirstName.setBounds(44, 330, 103, 14);
		frame.getContentPane().add(lblFirstName);

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

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		btnSave.setBounds(44, 412, 89, 23);
		frame.getContentPane().add(btnSave);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDelete();
			}
		});
		btnDelete.setBounds(169, 412, 89, 23);
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
}
