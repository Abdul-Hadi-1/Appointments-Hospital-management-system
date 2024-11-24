import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
public class search extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane_1;
	private JTextField user_input;
	private JTable table;
	private JScrollPane scrollPane;
	public String tableval; 

	/**
	 * Launch the application.	
	 * @throws FileNotFoundException 
	 */
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public search(String patientID) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


		try 
		{
			final Image backgroundImage = ImageIO.read(new File("hospitalbg.png"));
			contentPane_1 = new JPanel() 
			{
				@Override
				protected void paintComponent(Graphics g) 
				{
					super.paintComponent(g);
					g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
				}
			};
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}

		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setLayout(null);
		setContentPane(contentPane_1);


		contentPane_1.setLayout(null);

		user_input = new JTextField();

		user_input.setBounds(10, 72, 418, 20);
		contentPane_1.add(user_input);
		user_input.setColumns(10);
		user_input.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
		         performsearch();
		    }
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 102, 966, 551);
		contentPane_1.add(scrollPane);

		table = new JTable() 
		{
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) 
			{                
				return false;               
			};
		};
		table.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int rowID = table.getSelectedRow();

				int modelRow = table.convertRowIndexToModel(rowID);

				Object modelRowValue = table.getModel().getValueAt(modelRow, 0);

				tableval= (String) modelRowValue;

				appointment obj2 = new appointment(patientID,tableval);
				obj2.setVisible(true);
				setVisible(false);
			}
		});
		scrollPane.setViewportView(table);
		JButton search = new JButton("search");
		search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==search)
				{
					performsearch();
				}
			}
		}
				);
		search.setBounds(440, 71, 92, 20);
		contentPane_1.add(search);



		JButton btnNewButton = new JButton("Account");
		btnNewButton.setBounds(891, 28, 85, 21);
		contentPane_1.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("BRUNEL UNIVERSITY HOSPITAL");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 10, 347, 53);
		contentPane_1.add(lblNewLabel);
		btnNewButton.addActionListener(new ActionListener() 
		{

			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					account obj1 = new account(patientID);
					obj1.setVisible(true);
					setVisible(false);
				} 
				catch (FileNotFoundException e1) 
				{

					e1.printStackTrace();
				}
			}
		});
	}
	public void performsearch()
	{
		try 
		{
			File f = new File("hospital-info.csv");
			Scanner reader = new Scanner(f);

			DefaultTableModel table_model = new DefaultTableModel();
			table.setModel(table_model);

			table_model.addColumn("ID"); 
			table_model.addColumn("Name"); 
			table_model.addColumn("Address"); 
			table_model.addColumn("City"); 
			table_model.addColumn("State");
			table_model.addColumn("ZipCode");
			table_model.addColumn("County Name");
			table_model.addColumn("Phone Number");
			table_model.addColumn("Hospital Type");
			table_model.addColumn("Hospital Ownership");
			table_model.addColumn("Emergency Services");
			table_model.addColumn("Hospital overall rating");

			while (reader.hasNext()) 
			{
				String row = reader.nextLine(); 

				String id = row.split(",")[0].toLowerCase();
				String name = row.split(",")[1].toLowerCase();
				String address = row.split(",")[2].toLowerCase();
				String city = row.split(",")[3].toLowerCase();
				String state = row.split(",")[4].toLowerCase();
				String zipcode = row.split(",")[5].toLowerCase();
				String countyname = row.split(",")[6].toLowerCase();
				String phonenum = row.split(",")[7].toLowerCase();
				String type = row.split(",")[8].toLowerCase();
				String owner = row.split(",")[9].toLowerCase();
				String er = row.split(",")[10].toLowerCase();
				String rating = row.split(",")[11].toLowerCase();

				//Check if the description contains the user input 
				if(id.equals(user_input.getText())
						||name.contains(user_input.getText().toLowerCase())
						||address.contains(user_input.getText().toLowerCase())
						||city.contains(user_input.getText().toLowerCase())
						||state.contains(user_input.getText().toLowerCase())
						||zipcode.contains(user_input.getText().toLowerCase())
						||countyname.contains(user_input.getText().toLowerCase())
						||phonenum.contains(user_input.getText().toLowerCase())
						||type.contains(user_input.getText().toLowerCase())
						||owner.contains(user_input.getText().toLowerCase())
						||er.contains(user_input.getText().toLowerCase())
						||rating.contains(user_input.getText().toLowerCase()))  
				{								
					table_model.addRow(new Object[]{id , name, address , city , state , zipcode , countyname , phonenum , type , owner , er , rating});
				}
			}	
			reader.close();
		} catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
	}
}