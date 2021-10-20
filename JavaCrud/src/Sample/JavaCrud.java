package Sample;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/************************
@Author: Azat Erol
Always happy coding!
************************/

public class JavaCrud {

	private JFrame frame;
	private JTextField txtTitel;
	private JTextField txtVersion;
	private JTextField txtPreis;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
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
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;
	
	
	private void Connect() {
		// TODO Auto-generated method stub
		try
		{
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection("jdbc:sqlserver://azaterol.database.windows.net:1433;"
                 + "database=BuchDB;"
                 + "user=AzatErol@azaterol.database.windows.net;"
                 + "password=xxx;"
                 + "encrypt=true;"
                 + "trustServerCertificate=false;"
                 + "loginTimeout=30;");  
		}
		catch(ClassNotFoundException ex)
		{
			
		}
		catch(SQLException ex)
		{
			
		}           
	}
	void table_load()
	{
		try {
			pst = con.prepareStatement("select * from buch");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 815, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(189, 6, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registrierung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 37, 376, 161);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Titel");
		lblNewLabel_1.setBounds(22, 32, 61, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Version");
		lblNewLabel_2.setBounds(22, 73, 61, 16);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Preis");
		lblNewLabel_3.setBounds(22, 121, 61, 16);
		panel.add(lblNewLabel_3);
		
		txtTitel = new JTextField();
		txtTitel.setBounds(96, 27, 252, 26);
		panel.add(txtTitel);
		txtTitel.setColumns(10);
		
		txtVersion = new JTextField();
		txtVersion.setColumns(10);
		txtVersion.setBounds(95, 68, 253, 26);
		panel.add(txtVersion);
		
		txtPreis = new JTextField();
		txtPreis.setColumns(10);
		txtPreis.setBounds(96, 116, 252, 26);
		panel.add(txtPreis);
		
		JButton btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String titel, version, preis,id;
			
			titel = txtTitel.getText();
			version = txtVersion.getText();
			preis =  txtPreis.getText();
			id = txtId.getText();
				
			try {
				pst = con.prepareStatement("insert into Buch(titel, versionBuch,preis,id)values(?,?,?,?)");
				pst.setString(1,titel);
				pst.setString(2,version);
				pst.setString(3,preis);
				pst.setString(4, id);
				
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null,"Buch hinzugefügt");
				table_load();
				
				txtTitel.setText("");
				txtVersion.setText("");
				txtPreis.setText("");
				txtId.setText("");
				txtTitel.requestFocus();
			}
			catch(SQLException e1) {
				e1.printStackTrace();	
			}		
			}
		});
		btnSpeichern.setBounds(16, 205, 117, 63);
		frame.getContentPane().add(btnSpeichern);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titel, version, preis,id;
				
				titel = txtTitel.getText();
				version = txtVersion.getText();
				preis =  txtPreis.getText();
				id = txtId.getText();
					
				try {
					pst = con.prepareStatement("update buch set titel= ?, versionBuch= ?, preis= ? where id= ?");
					pst.setString(1,titel);
					pst.setString(2,version);
					pst.setString(3,preis);
					pst.setString(4,id);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Datensatz geändert");
					table_load();
					
					txtTitel.setText("");
					txtVersion.setText("");
					txtPreis.setText("");
					txtId.setText("");
					txtTitel.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
					
				}				
				
			}
		});
		btnUpdate.setBounds(485, 348, 103, 63);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnBeenden = new JButton("Beenden");
		btnBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnBeenden.setBounds(145, 205, 117, 63);
		frame.getContentPane().add(btnBeenden);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Suchen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(6, 274, 376, 95);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("Buch ID");
		lblNewLabel_2_1.setBounds(23, 37, 61, 16);
		panel_1.add(lblNewLabel_2_1);
		
		txtId = new JTextField();
		txtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try
				{
					String id = txtId.getText();
					pst = con.prepareStatement("select titel,versionBuch,preis from buch where id= ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true)
					{
						String title = rs.getString(1);
						String versionBuch = rs.getString(2);
						String preis = rs.getString(3);
						txtTitel.setText(title);
						txtVersion.setText(versionBuch);
						txtPreis.setText(preis);
					}
					else
					{
						txtTitel.setText("");
						txtVersion.setText("");
						txtPreis.setText("");
					}
				}
				catch(SQLException ex) {
					
				}
			}
		});
		txtId.setColumns(10);
		txtId.setBounds(96, 32, 253, 26);
		panel_1.add(txtId);
		
		JButton btnLoeschen = new JButton("Löschen");
		btnLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id;
				
				id = txtId.getText();
					
				try {
					pst = con.prepareStatement("delete from buch where id= ? ");
					pst.setString(1,id);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Datensatz gelöscht");
					table_load();
					
					txtTitel.setText("");
					txtVersion.setText("");
					txtPreis.setText("");
					txtId.setText("");
					txtTitel.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
					
				}
			}
		});
		btnLoeschen.setBounds(614, 348, 103, 63);
		frame.getContentPane().add(btnLoeschen);
		
		JButton btnZurcksetzen = new JButton("Zurücksetzen");
		btnZurcksetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTitel.setText("");
				txtVersion.setText("");
				txtPreis.setText("");
				txtId.setText("");
				txtTitel.requestFocus();
			}
		});
		btnZurcksetzen.setBounds(274, 205, 103, 63);
		frame.getContentPane().add(btnZurcksetzen);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(404, 38, 369, 279);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
