import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import com.toedter.calendar.JCalendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class appointment extends JFrame 
{

	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String county;
	private String phone;
	private String type;
	private String owner;
	private String ers;
	private String rating;
	private static final int MAX_IDS = 100000;
	private static boolean[] generated = new boolean[MAX_IDS];
	private static int count = 0;
	private JCalendar calendar;
	private String selecteddate; 

	public appointment(String patientid ,String hospitalid) 
	{
		setTitle("Brunel University Hospital - Book Your Appointment");
		setSize(1000, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		try 
		{
			final Image backgroundImage = ImageIO.read(new File("appointmentbg.png"));
			JPanel backgroundPanel = new JPanel()
			{
				@Override
				protected void paintComponent(Graphics g) 
				{
					super.paintComponent(g);
					g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
				}
			};
			backgroundPanel.setLayout(null);
			setContentPane(backgroundPanel);

			ImageIcon img = new ImageIcon("BRUNEL UNIVERSITY Hospital.png");
			Image img2 = img.getImage();
			Image img3 = img2.getScaledInstance(300,120, java.awt.Image.SCALE_SMOOTH);
			img = new ImageIcon(img3);
			JLabel Titleimage = new JLabel(img);
			Titleimage.setLocation(-66, -11);
			Titleimage.setSize(443,131);
			backgroundPanel.add(Titleimage);

			ImageIcon image2 = new ImageIcon("brunel-university-london2872-removebg.png");
			Image image3 = image2.getImage();
			Image newimg = image3.getScaledInstance(200,120, java.awt.Image.SCALE_SMOOTH);
			image2 = new ImageIcon(newimg);
			JLabel image = new JLabel(image2);
			image.setSize(200, 151);
			image.setLocation(722, -11);
			backgroundPanel.add(image);

		} 

		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			readcsv(hospitalid);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace(); 
		}

		int labelX = 20;
		int valueX = 140;
		int labelWidth = 120;
		int valueWidth = 200;
		int height = 20;
		int startY = 165;
		int incrementY = 35;

		JLabel lblHospitalId = new JLabel("Hospital ID:");
		lblHospitalId.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblHospitalId);

		JLabel idlbl = new JLabel(hospitalid);
		idlbl.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(idlbl);

		startY += incrementY;

		JLabel hospitalNameLabel = new JLabel("Hospital Name:");
		hospitalNameLabel.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(hospitalNameLabel);

		JLabel namelbl = new JLabel(name);
		namelbl.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(namelbl);

		startY += incrementY;

		JLabel lblAddress = new JLabel("Address: ");
		lblAddress.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblAddress);

		JLabel addresslbl_1 = new JLabel(address);
		addresslbl_1.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(addresslbl_1);

		startY += incrementY;

		JLabel lblCity = new JLabel("City: ");
		lblCity.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblCity);

		JLabel citylbl = new JLabel(city);
		citylbl.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(citylbl);

		startY += incrementY;

		JLabel lblState = new JLabel("State: ");
		lblState.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblState);

		JLabel statelbl_1 = new JLabel(state);
		statelbl_1.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(statelbl_1);

		startY += incrementY;

		JLabel lblZipcode = new JLabel("Zipcode: ");
		lblZipcode.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblZipcode);

		JLabel zipcodelbl = new JLabel(zip);
		zipcodelbl.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(zipcodelbl);

		startY += incrementY;

		JLabel lblCounty = new JLabel("County Name: ");
		lblCounty.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblCounty);

		JLabel countylbl = new JLabel(county);
		countylbl.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(countylbl);

		startY += incrementY;

		JLabel lblphone = new JLabel("Phone: ");
		lblphone.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblphone);

		JLabel phonelbl = new JLabel(phone);
		phonelbl.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(phonelbl);

		startY += incrementY;

		JLabel typeLabel = new JLabel("Hospital Type:");
		typeLabel.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(typeLabel);

		JLabel hospitaltypelbl = new JLabel(type);
		hospitaltypelbl.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(hospitaltypelbl);

		startY += incrementY;

		JLabel lblOwnership = new JLabel("Ownership:");
		lblOwnership.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblOwnership);

		JLabel erslbl_1 = new JLabel(owner);
		erslbl_1.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(erslbl_1);

		startY += incrementY;

		JLabel lblEmergency = new JLabel("Emergency");
		lblEmergency.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblEmergency);

		JLabel hospitaltypelbl_1 = new JLabel(ers);
		hospitaltypelbl_1.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(hospitaltypelbl_1);

		startY += incrementY;

		JLabel lblRating = new JLabel("Rating:");
		lblRating.setBounds(labelX, startY, labelWidth, height);
		getContentPane().add(lblRating);

		JLabel ratinglbl_1_1_1 = new JLabel(rating);
		ratinglbl_1_1_1.setBounds(valueX, startY, valueWidth, height);
		getContentPane().add(ratinglbl_1_1_1);

		calendar = new JCalendar();
		calendar.setBounds(617, 172, 206, 152);
		getContentPane().add(calendar);

		JButton bookAppointmentButton = new JButton("Book your appointment");
		bookAppointmentButton.setBounds(420, 600, 180, 50);
		getContentPane().add(bookAppointmentButton);
		bookAppointmentButton.addActionListener(e -> 
		{ 
			if(hasUserSelectedDate()==true)
			{
				selecteddate = getFormattedDate();  
				if(isSelectedDateValid(selecteddate)==true)
				{
				try 
				{
					appointmentcsv(patientid, hospitalid);
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				search obj = new search(patientid);
				obj.setVisible(true);
				setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "The Date you selected has already passed. Please Select a future date");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Please Select a Date from the Calender.");
			}
		});


		JLabel lblSelectTheDate = new JLabel("Select the Date:");
		lblSelectTheDate.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblSelectTheDate.setBounds(465, 130, 149, 25);
		getContentPane().add(lblSelectTheDate);


		JButton btnNewButton = new JButton("Account");
		btnNewButton.setBounds(891, 28, 85, 21);
		getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					account obj1 = new account(patientid);
					obj1.setVisible(true);
					setVisible(false);

				} catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
			}
		});

	}
	public void readcsv(String id) throws FileNotFoundException 
	{
		File f = new File("hospital-info.csv");
		Scanner reader = new Scanner(f);
		while (reader.hasNext()) 
		{
			String row = reader.nextLine();
			String[] data = row.split(",");
			if (id.equals(data[0])) 
			{
				name = data[1];
				address = data[2];
				city = data[3];
				state = data[4];
				zip = data[5];
				county = data[6];
				phone = data[7];
				type = data[8];
				owner = data[9];
				ers = data[10];
				rating = data[11];
				break;
			}
		}
		reader.close();
	}
	public void appointmentcsv(String PatientID, String HospitalID) throws IOException 
	{
		int appointmentid = generateUniqueId();
		String filepath = "appointments.csv";
		File file = new File(filepath);
		boolean append = file.exists() && file.isFile();

		// Use try-with-resources to ensure the FileWriter is closed properly.
		try (FileWriter fileWriter = new FileWriter(filepath, append)) 
		{
			// If the file doesn't exist or isn't a file, write the header.
			if (!append) 
			{
				fileWriter.append("Appointment ID,Patient ID,Hospital ID,Appointment Date\n");
			}
			// Write the appointment information in both cases (new file or appending).
			fileWriter.append(String.valueOf(appointmentid)).append(",").append(PatientID).append(",").append(HospitalID).append(",").append(selecteddate).append("\n");
			JOptionPane.showMessageDialog(this, "Appointment created! \n" + "Your Appointment ID is: " + appointmentid + "\nAppointment Date is: " + selecteddate);
			this.dispose();
		} catch (IOException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving information.");
		}
		
	}

	public static int generateUniqueId() 
	{
		if (count >= MAX_IDS) 
		{
			throw new IllegalStateException("No more unique 5-digit IDs available.");
		}
		Random random = new Random();
		int uniqueId;
		do 
		{
			uniqueId = random.nextInt(MAX_IDS);
		} 
		while (generated[uniqueId]);

		generated[uniqueId] = true; 
		count++; 

		return uniqueId;
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
    public static boolean isSelectedDateValid(String selectedDateStr) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate selectedDate = LocalDate.parse(selectedDateStr, formatter);
        LocalDate currentDate = LocalDate.now();
        return !selectedDate.isBefore(currentDate);
    }
}
