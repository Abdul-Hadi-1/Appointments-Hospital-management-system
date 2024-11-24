import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;

import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class RegistrationForm extends JFrame 
{
	private JTextField patientNameField;
	private JTextField dateOfBirthField;
	private JTextField addressField;
	private JTextField nextOfKinField;
	private JTextField relationNextOfKinField;
	private JTextField addressNextOfKinField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private static final int MAX_IDS = 1000000;
	private static boolean[] generated = new boolean[MAX_IDS];
	private static int count = 0;

	public RegistrationForm() 
	{
		setTitle("Register");
		setSize(1000,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null); // No layout manager			

		try 
		{
			final Image backgroundImage = ImageIO.read(new File("patientinformation.png"));
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
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		JLabel nameLabel = new JLabel("Patient name:");
		nameLabel.setBounds(10, 136, 120, 25);
		getContentPane().add(nameLabel);
		patientNameField = new JTextField();
		((AbstractDocument)patientNameField.getDocument()).setDocumentFilter(new AlphaSpaceFilter());
		patientNameField.setBounds(140, 136, 200, 25);
		getContentPane().add(patientNameField);

		JLabel dobLabel = new JLabel("Date Of Birth:");
		dobLabel.setBounds(10, 171, 120, 25);
		getContentPane().add(dobLabel);
		dateOfBirthField = new JTextField();
		dateOfBirthField.setBounds(140, 171, 200, 25);
		getContentPane().add(dateOfBirthField);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setBounds(10, 206, 120, 25);
		getContentPane().add(addressLabel);
		addressField = new JTextField();
		addressField.setBounds(140, 206, 200, 25);
		getContentPane().add(addressField);

		JLabel kinNameLabel = new JLabel("Next of Kin name:");
		kinNameLabel.setBounds(10, 241, 120, 25);
		getContentPane().add(kinNameLabel);
		nextOfKinField = new JTextField();
		((AbstractDocument)nextOfKinField.getDocument()).setDocumentFilter(new AlphaSpaceFilter());
		nextOfKinField.setBounds(140, 241, 200, 25);
		getContentPane().add(nextOfKinField);

		JLabel relationLabel = new JLabel("Relation of Next of Kin:");
		relationLabel.setBounds(10, 276, 160, 25);
		getContentPane().add(relationLabel);
		relationNextOfKinField = new JTextField();
		((AbstractDocument)relationNextOfKinField.getDocument()).setDocumentFilter(new AlphaSpaceFilter());
		relationNextOfKinField.setBounds(140, 276, 200, 25);
		getContentPane().add(relationNextOfKinField);

		JLabel kinAddressLabel = new JLabel("Address of Next of Kin:");
		kinAddressLabel.setBounds(10, 311, 160, 25);
		getContentPane().add(kinAddressLabel);
		addressNextOfKinField = new JTextField();
		addressNextOfKinField.setBounds(140, 311, 200, 25);
		getContentPane().add(addressNextOfKinField);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(10, 346, 120, 25);
		getContentPane().add(emailLabel);
		emailField = new JTextField();
		emailField.setBounds(140, 346, 200, 25);
		getContentPane().add(emailField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 381, 120, 25);
		getContentPane().add(passwordLabel);
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 381, 200, 25);
		getContentPane().add(passwordField);


		String enteredPass = new String(passwordField.getPassword());
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(140, 444, 100, 25);
		saveButton.addActionListener(e -> 
		{

			if(patientNameField.getText().isEmpty() 
					|| dateOfBirthField.getText().isEmpty() 
					|| addressField.getText().isEmpty()
					|| nextOfKinField.getText().isEmpty()
					|| relationNextOfKinField.getText().isEmpty()
					|| emailField.getText().isEmpty()
					|| new String(passwordField.getPassword()).isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Fields can not be left Empty. Please Fill Out Every Field");
			}
			else
			{
				saveToCSV(e);
				LoginScreen obj = new LoginScreen();
				obj.setVisible(true);
				setVisible(false);
			}
		});
		getContentPane().add(saveButton);

		JLabel Titleimage = new JLabel("");
		Titleimage.setLocation(0, 0);
		Titleimage.setSize(443,131);
		ImageIcon img = new ImageIcon("BRUNEL UNIVERSITY Hospital.png");
		Image img2 = img.getImage();
		Image img3 = img2.getScaledInstance(300,120, java.awt.Image.SCALE_SMOOTH);
		img= new ImageIcon(img3);

		Titleimage.setIcon(img);
		//Titleimage.setBounds(10, 14, 344, 50);
		getContentPane().add(Titleimage);

		setVisible(true);

		JLabel image = new JLabel("");
		image.setSize(200, 151);
		image.setLocation(709, 0);
		ImageIcon image2 = new ImageIcon("brunel-university-london2872-removebg.png");
		//lblNewLabel_1.setBounds(627, 43, 184, 192);
		Image image3 = image2.getImage();
		Image newimg = image3.getScaledInstance(200,120, java.awt.Image.SCALE_SMOOTH);
		image2=new ImageIcon(newimg);

		image.setIcon(image2);

		getContentPane().add(image);
	}
	public static int generateUniqueId() 
	{
		if (count >= MAX_IDS) 
		{
			throw new IllegalStateException("No more unique 6-digit IDs available.");
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


	private void saveToCSV(ActionEvent e) 
	{
		String filepath = "patient.csv";
		File file = new File(filepath);
		boolean append = file.exists() && file.isFile();
		int genpatientid = generateUniqueId();
		try (FileWriter fileWriter = new FileWriter(filepath, append)) 
		{
			if (!append) 
			{
				fileWriter.append("Paitent ID,Name,DOB,Address,Next of Kin Name,Relation of Next of Kin,Address,Email,Password\n");
			}
			fileWriter.append(String.valueOf(genpatientid)).append(",").append(patientNameField.getText()).append(",")
			.append(dateOfBirthField.getText()).append(",")
			.append(addressField.getText()).append(",")
			.append(nextOfKinField.getText()).append(",")
			.append(relationNextOfKinField.getText()).append(",")
			.append(addressNextOfKinField.getText()).append(",")
			.append(emailField.getText()).append(",")
			.append(new String(passwordField.getPassword())).append("\n");
			JOptionPane.showMessageDialog(this, "Information saved! \n" + "Your Unique Paitent ID is: " + genpatientid);
		} 
		catch (IOException ex) 
		{
			JOptionPane.showMessageDialog(this, "Error saving information.");
			ex.printStackTrace();
		}
	}

}



