import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.toedter.calendar.JCalendar;

public class followup extends JFrame 
{
	private String olddate;
	private JCalendar calendar;
	private String selecteddate; 
	private String hospitalid;
	private String pid;
	private String hospitalname;
	private String name;
	

    public followup(String ID ,String aid , String hid) throws FileNotFoundException 
    {
        super("Brunel University Hospital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);

        JPanel backgroundPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                try 
                {
                    final Image backgroundImage = ImageIO.read(new File("appointmentbg.png"));
                    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        ImageIcon imageIcon = new ImageIcon("brunel-university-london2872-removebg.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(200, 120, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(786, 0, 200, 151);
        backgroundPanel.add(imageLabel);

        ImageIcon titleIcon = new ImageIcon("BRUNEL UNIVERSITY Hospital.png");
        Image titleImage = titleIcon.getImage();
        Image scaledTitleImage = titleImage.getScaledInstance(300, 120, java.awt.Image.SCALE_SMOOTH);
        titleIcon = new ImageIcon(scaledTitleImage);
        JLabel titleImageLabel = new JLabel(titleIcon);
        titleImageLabel.setBounds(-73, 0, 443, 131);
        backgroundPanel.add(titleImageLabel);
         
        hospitalid = hid;
        readcsv(aid);  
        hospitalcsv();
        patientcsv();
        
        JLabel followUpLabel = new JLabel("Follow up Appointment:");
        followUpLabel.setBounds(10, 141, 221, 30);
        backgroundPanel.add(followUpLabel);
        
        JLabel followUpHospitalLabel = new JLabel("Follow-up Hospital:");
        followUpHospitalLabel.setBounds(10, 181, 136, 20);
        backgroundPanel.add(followUpHospitalLabel);

        JLabel followUpAppointmentIDLabel = new JLabel("Patient ID:");
        followUpAppointmentIDLabel.setBounds(10, 211, 136, 20);
        backgroundPanel.add(followUpAppointmentIDLabel);
        
        JButton saveButton = new JButton("Save your appointment");
        saveButton.setBounds(10, 623, 200, 30);
        backgroundPanel.add(saveButton);
        saveButton.addActionListener(e -> 
		{ 
			if(hasUserSelectedDate()==true)
			{
				selecteddate = getFormattedDate();
				if(diffrencecheck(olddate, selecteddate)==true)
				{
					JOptionPane.showMessageDialog(this, "Follow up appointment created");
					try 
					{
						savecsv(aid);
					}
					catch (IOException e1)
					{
						JOptionPane.showMessageDialog(this,"Error in saving File");
						e1.printStackTrace();
					}
					System.out.println();
				}
				else
				{
					JOptionPane.showMessageDialog(this,"Follow up needs to be 1 month after the intial appointment");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Please Select a Date from the Calender.");
			}
		});
        

        JLabel hospitalLbl = new JLabel(hospitalname);
        hospitalLbl.setBounds(156, 181, 136, 20);
        backgroundPanel.add(hospitalLbl);

        JButton changeButton = new JButton("change");
        changeButton.setBounds(302, 181, 85, 21);
        backgroundPanel.add(changeButton);
        changeButton.addActionListener(e -> 
		{
			try 
			{
				followupsearch objfs = new followupsearch(ID,aid);
				objfs.setVisible(true);
				setVisible(false);
			}
			catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}
		});

        JLabel patientIDLabel = new JLabel(pid);
        patientIDLabel.setBounds(156, 211, 136, 20);
        backgroundPanel.add(patientIDLabel);

        calendar = new JCalendar();
        calendar.setBounds(113, 320, 206, 152);
        backgroundPanel.add(calendar);
        

        JLabel selectDateLabel = new JLabel("Select the Date:");
        selectDateLabel.setBounds(10, 310, 93, 30);
        backgroundPanel.add(selectDateLabel);

        JLabel noteLabel = new JLabel("Note: Follow up can only be 1 month after the initial appointment");
        noteLabel.setBounds(113, 463, 400, 40);
        backgroundPanel.add(noteLabel);
        
        JLabel lblPatientName = new JLabel("Patient Name:");
        lblPatientName.setBounds(10, 241, 136, 20);
        backgroundPanel.add(lblPatientName);
        
        JLabel patientnameLabel = new JLabel(name);
        patientnameLabel.setBounds(156, 241, 136, 20);
        backgroundPanel.add(patientnameLabel);
        
        JLabel lblIntialAppointmentDate = new JLabel("Intial Appointment Date");
        lblIntialAppointmentDate.setBounds(10, 271, 136, 20);
        backgroundPanel.add(lblIntialAppointmentDate);
        
        JLabel lblHello = new JLabel(olddate);
        lblHello.setBounds(156, 271, 136, 20);
        backgroundPanel.add(lblHello);
        
        JButton btnNewButton_2 = new JButton("Log out");
		btnNewButton_2.setBounds(850, 29, 85, 21);
		backgroundPanel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				LoginScreen obj3 = new LoginScreen();
				obj3.setVisible(true);
				setVisible(false);
			}
		});   
    	JButton btnNewButton_1 = new JButton("Followup Details");
		btnNewButton_1.setBounds(804, 60, 182, 21);
		backgroundPanel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				followupdetails obj2 = new followupdetails(ID);
				obj2.setVisible(true);
				setVisible(false);
			}
		});
        
    }
	public void readcsv(String id) throws FileNotFoundException 
	{
		File f = new File("appointments.csv");
		Scanner reader = new Scanner(f);
		while (reader.hasNext()) 
		{
			String row = reader.nextLine();
			String[] data = row.split(",");
			if (id.equals(data[0])) 
			{
				pid = data[1];
				olddate=data[3];
				break;
			}
		}
		reader.close();
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
	 public static boolean diffrencecheck(String Date1, String Date2) 
	 {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

	        // Parse the dates
	        LocalDate date1 = LocalDate.parse(Date1, formatter);
	        LocalDate date2 = LocalDate.parse(Date2, formatter);

	        // Calculate the difference in days
	        long daysBetween = ChronoUnit.DAYS.between(date1, date2);

	        // Check if the difference is greater than or equal to 30
	        return daysBetween >= 30;
	    }
	 public void savecsv(String appointmentid) throws IOException 
		{
			String filepath = "follow-up.csv";
			File file = new File(filepath);
			String HospitalID = hospitalid;
			String PatientID = pid;
			boolean append = file.exists() && file.isFile();
			try (FileWriter fileWriter = new FileWriter(filepath, append)) 
			{
				if (!append) 
				{
					fileWriter.append("Appointment ID,Patient ID,Hospital ID,Follow-up Appointment Date\n");
				}
				fileWriter.append(appointmentid).append(",").append(PatientID).append(",").append(HospitalID).append(",").append(selecteddate).append("\n");
				JOptionPane.showMessageDialog(this, "Follow up Appointment created! \n" + "Your Appointment ID is: " + appointmentid + "\nFollow-up Date is: " + selecteddate);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error saving information.");
			}
			
		}
		public void hospitalcsv() throws FileNotFoundException 
		{
			File f = new File("hospital-info.csv");
			Scanner reader = new Scanner(f);
			while (reader.hasNext()) 
			{
				String row = reader.nextLine();
				String[] data = row.split(",");
				if (hospitalid.equals(data[0])) 
				{
					hospitalname = data[1];
					break;
				}
			}
			reader.close();
		}
		public void patientcsv() throws FileNotFoundException
		{
			File f = new File("patient.csv");
			Scanner reader = new Scanner(f);

			while (reader.hasNext()) 
			{
				String row = reader.nextLine();
				String[] data = row.split(",");
				if (pid.equals(data[0])) 
				{
					name = data[1];
					break;
				}
			}
			reader.close();
		}
		
}
