import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.toedter.calendar.JCalendar;

public class appointmentdetails extends JFrame 
{
	private String patientid;
	private String appointmentID;
	private String patientname;
	private String appointmentdate;
	private String nameNOX;
	private String hospitalid;
	private String hospitalname;
	private JTextField searchtextField;
	private JLabel appointmentIDLabel;
	private JLabel patientIDLabel;
	private JLabel patientNameLabel;
	private JLabel appointmentDateLabel;
	private JLabel hospitalNameLabel;
	private JCalendar calendar;
	private String selecteddate; 
	private boolean flag;

	public appointmentdetails(String ID) 
	{
		super("Brunel University Hospital");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLayeredPane layeredPane = getLayeredPane(); 

		ImageIcon backgroundIcon = new ImageIcon("appointmentdetailsbg.png");
		Image backgroundImage = backgroundIcon.getImage();
		Image scaledBackgroundImage = backgroundImage.getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
		backgroundIcon = new ImageIcon(scaledBackgroundImage);
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, 1000, 700);

		layeredPane.add(backgroundLabel, Integer.valueOf(JLayeredPane.DEFAULT_LAYER));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setOpaque(false); 
		mainPanel.setBounds(0, 0, 1000, 700); 

		JLabel lblHeading = new JLabel("BRUNEL UNIVERSITY HOSPITAL");
		lblHeading.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblHeading.setBounds(20, 32, 322, 50);
		mainPanel.add(lblHeading);

		ImageIcon imageIcon = new ImageIcon("brunel-university-london2872-removebg.png"); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(200, 120, java.awt.Image.SCALE_SMOOTH); 
		imageIcon = new ImageIcon(newimg);  
		JLabel lblImage = new JLabel(imageIcon);
		lblImage.setBounds(786, 0, 200, 120); 
		mainPanel.add(lblImage);

		JLabel lblAppointmentDetails = new JLabel("Appointment Details:");
		lblAppointmentDetails.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAppointmentDetails.setBounds(20, 234, 200, 30);
		mainPanel.add(lblAppointmentDetails);

		ImageIcon universityLogo = new ImageIcon("logo.png"); 
		JLabel lblUniversityLogo = new JLabel(universityLogo);
		lblUniversityLogo.setBounds(301, 23, 105, 85);
		mainPanel.add(lblUniversityLogo);

		JLabel lblAppointmentID = new JLabel("Appointment ID:");
		JLabel lblFollowupAppointment = new JLabel("Patient ID:");
		JLabel lblPatient = new JLabel("Patient Name:");
		JLabel lblPastAppointments = new JLabel("Appointment Date: ");
		JLabel lblHospitalName = new JLabel("Hospital Name:");

		lblAppointmentID.setBounds(20, 272, 160, 30);
		lblFollowupAppointment.setBounds(20, 312, 160, 30);
		lblPatient.setBounds(20, 352, 160, 30);
		lblPastAppointments.setBounds(20, 392, 160, 30);
		lblHospitalName.setBounds(20, 428, 160, 30);

		mainPanel.add(lblAppointmentID);
		mainPanel.add(lblFollowupAppointment);
		mainPanel.add(lblPatient);
		mainPanel.add(lblPastAppointments);
		mainPanel.add(lblHospitalName);

		appointmentIDLabel = new JLabel("");
		patientIDLabel = new JLabel("");
		patientNameLabel = new JLabel("");
		appointmentDateLabel = new JLabel("");
		hospitalNameLabel = new JLabel("");

		appointmentIDLabel.setBounds(190, 272, 160, 30);
		patientIDLabel.setBounds(190, 312, 259, 30);
		patientNameLabel.setBounds(190, 352, 259, 30);
		appointmentDateLabel.setBounds(190, 392, 259, 30);
		hospitalNameLabel.setBounds(190, 428, 259, 30);


		mainPanel.add(appointmentIDLabel);
		mainPanel.add(patientIDLabel);
		mainPanel.add(patientNameLabel);
		mainPanel.add(appointmentDateLabel);
		mainPanel.add(hospitalNameLabel);

		searchtextField = new JTextField();
		searchtextField.setBounds(20, 163, 231, 30);
		mainPanel.add(searchtextField);
		searchtextField.setColumns(10);
		searchtextField.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					readCsv();
					readhospitalname();
					flag = true;
				} 
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(271, 172, 85, 21);
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					readCsv();
					readhospitalname();
					flag = true;
				} 
				catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		mainPanel.add(btnSearch);

		calendar = new JCalendar();
		calendar.setBounds(576, 270, 206, 152);
		mainPanel.add(calendar);
		calendar.setVisible(false);

		JLabel lblNewLabel_1 = new JLabel("Select Modified Date:");
		lblNewLabel_1.setBounds(485, 236, 169, 30);
		mainPanel.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false); 

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(hasUserSelectedDate()==true)
				{
					selecteddate = getFormattedDate();   
					String path = "appointments.csv";
					edit(path,appointmentID,selecteddate);
					JOptionPane.showMessageDialog(null, "Appointment Date update. \n New date: " + selecteddate);
					calendar.setVisible(false);
					lblNewLabel_1.setVisible(false);
					btnNewButton.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please Select a Date from the Calender.");
				}

			}
		});
		btnNewButton.setBounds(787, 433, 85, 21);
		mainPanel.add(btnNewButton);
		btnNewButton.setVisible(false);

		JButton btnUpdateAppointment = new JButton("Modify Date");
		btnUpdateAppointment.setBounds(20, 479, 119, 30);
		mainPanel.add(btnUpdateAppointment);
		btnUpdateAppointment.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(flag==true)
				{
					calendar.setVisible(true);
					lblNewLabel_1.setVisible(true);
					btnNewButton.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please Search for your appointment first");
				}
			}
		});

		JButton btnScheduleFollowup = new JButton("Schedule Followup");
		btnScheduleFollowup.setBounds(65, 536, 160, 30);
		mainPanel.add(btnScheduleFollowup);
		btnScheduleFollowup.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(flag==true)
				{
					try 
					{
						followup objf = new followup(ID, appointmentID, hospitalid);
						objf.setVisible(true);
						setVisible(false);
					}
					catch (FileNotFoundException e1) 
					{
						JOptionPane.showMessageDialog(null,"Initial Appointment not found");
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please Search for your appointment first");
				}
			}
		});

		getContentPane().add(mainPanel);

		JButton cancelbutton = new JButton("Cancel Appointment");
		cancelbutton.setBounds(149, 479, 160, 30);
		mainPanel.add(cancelbutton);
		cancelbutton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String path = "appointments.csv";
				removeterm(path,appointmentID);
			}
		});

		JLabel lblNewLabel = new JLabel("Search with Appointment ID , Patient ID , Name , Next of Kin Name , DOB (DD/MM/YY)");
		lblNewLabel.setBounds(20, 203, 429, 21);
		mainPanel.add(lblNewLabel);

		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.setBounds(831, 32, 135, 21);
		mainPanel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				LoginScreen objs = new LoginScreen();
				objs.setVisible(true);
				setVisible(false);
			}
		});

		JButton accButton = new JButton("Back to Account");
		accButton.setBounds(816, 61, 160, 21);
		mainPanel.add(accButton);
		accButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					account obja = new account(ID);
					obja.setVisible(true);
					setVisible(false);
				} 
				catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		setVisible(true);


		getContentPane().add(mainPanel);
		setVisible(true);
		layeredPane.add(mainPanel, Integer.valueOf(JLayeredPane.PALETTE_LAYER));
		
		

	}

	public void readCsv() throws FileNotFoundException 
	{
		String searchText = searchtextField.getText().toLowerCase().trim();
		Map<String, String[]> patientMap = new HashMap<>();
		boolean matchFound = false;

		// Load patient data into a map with patientId as the key
		try (Scanner patientScanner = new Scanner(new File("patient.csv"))) {
			while (patientScanner.hasNext()) {
				String line = patientScanner.nextLine();
				String[] data = line.split(",");
				if (data.length > 4) 
				{
					patientMap.put(data[0], data);
				}
			}
		}
		// First, try finding a direct match by appointment ID in appointments.csv
		try (Scanner appointmentsScanner = new Scanner(new File("appointments.csv"))) 
		{
			while (appointmentsScanner.hasNext() && !matchFound) 
			{
				String line = appointmentsScanner.nextLine();
				String[] data = line.split(",");
				if (data.length > 3 && data[0].toLowerCase().equals(searchText)) 
				{
					// Found by appointment ID, now find patient details
					if (patientMap.containsKey(data[1])) {
						String[] patientInfo = patientMap.get(data[1]);
						updateUI(patientInfo, data); // Method to update UI elements
						matchFound = true;
					}
				}
			}
		}

		// If not found by appointment ID, search patient details for a match
		if (!matchFound)
		{
			for (String[] patientInfo : patientMap.values()) 
			{
				if (patientInfo[0].equals(searchText) 
						|| patientInfo[1].toLowerCase().contains(searchText) 
						|| patientInfo[2].toLowerCase().contains(searchText) || 
						patientInfo[4].toLowerCase().contains(searchText)) 
				{ 
					try (Scanner appointmentsScanner = new Scanner(new File("appointments.csv"))) 
					{
						while (appointmentsScanner.hasNext()) {
							String line = appointmentsScanner.nextLine();
							String[] appointmentData = line.split(",");
							if (appointmentData.length > 3 && appointmentData[1].equals(patientInfo[0])) 
							{
								updateUI(patientInfo, appointmentData);
								matchFound = true;
								break; 
							}
						}
					}
					if (matchFound) break; 
				}
			}
		}

		if (!matchFound) {
			JOptionPane.showMessageDialog(this, "Appointment not found. Check your details and try again.");
		}
	}

	private void updateUI(String[] patientInfo, String[] appointmentData) 
	{
		this.patientid = patientInfo[0];
		this.patientname = patientInfo[1];
		this.nameNOX = patientInfo[4];
		this.appointmentID = appointmentData[0];
		this.hospitalid = appointmentData[2];
		this.appointmentdate = appointmentData[3];

		patientIDLabel.setText(patientInfo[0]);
		patientNameLabel.setText(patientInfo[1]);
		appointmentIDLabel.setText(appointmentData[0]);
		appointmentDateLabel.setText(appointmentData[3]);
	}
	public void readhospitalname() throws FileNotFoundException
	{
		File file = new File("hospital-info.csv");
		Scanner reader = new Scanner(file);
		try 
		{
			while(reader.hasNext())
			{
				String [] data = reader.nextLine().split(",");
				String hpid = data[0];
				if(hpid.equals(hospitalid))
				{
					hospitalname = data[1];
					hospitalNameLabel.setText(this.hospitalname); 
					break;
				}      		  
			}
		}
		finally 
		{
			reader.close();
		}
	}
	public void removeterm(String fpath, String rval)
	{
		String tempFile = "temp.csv";
		File oldFile = new File(fpath);
		File newFile = new File(tempFile);
		try
		{
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			// Use a FileReader and BufferedReader to read the file line by line
			FileReader fr = new FileReader(fpath);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null)
			{
				// Split the line into parts based on the comma
				String[] parts = line.split(",");
				// Check if the array has at least 4 parts (to avoid ArrayIndexOutOfBoundsException)
				if (parts.length >= 4) 
				{
					String aID = parts[0];
					String pid = parts[1];
					String hid = parts[2];	                
					String date = parts[3];
					if (!aID.equals(rval))
					{
						pw.println(aID + "," + pid + "," + hid + "," + date);
					}
				}
			}

			// Close all resources
			br.close();
			pw.flush();
			pw.close();
			JOptionPane.showMessageDialog(this, "Appointment Canceled");
			oldFile.delete();
			File dump = new File(fpath);
			newFile.renameTo(dump);
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Error in deleting File");
		}
	}
	public void edit(String fp, String eval, String newdate)
	{
		String temp = "temp.csv";
		File oldfile = new File(fp);
		File newfile = new File(temp);

		try 
		{
			FileWriter fw = new FileWriter(temp, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			BufferedReader br = new BufferedReader(new FileReader(fp));
			String line;

			while ((line = br.readLine()) != null) 
			{
				String[] parts = line.split(",");
				if (parts.length >= 4) 
				{ 
					if (parts[0].equals(eval)) 
					{
						line = parts[0] + "," + parts[1] + "," + parts[2] + "," + newdate;
					}
					pw.println(line);
				}
			}
			br.close();
			pw.flush();
			pw.close();
			oldfile.delete();
			File dump = new File(fp);
			newfile.renameTo(dump);
		}
		catch(Exception e) 
		{
			System.out.println("Error: " + e.getMessage());
		}
	}
	public String getFormattedDate() 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		return dateFormat.format(calendar.getDate());
	}
	public boolean hasUserSelectedDate() 
	{
		Date currentDate = new Date(); 
		SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyy");
		return !fmt.format(calendar.getDate()).equals(fmt.format(currentDate));
	}
}