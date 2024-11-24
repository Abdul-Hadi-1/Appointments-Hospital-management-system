import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class account extends JFrame 
{
	private String Name;
	private String DOB;
	private String address;
	private String NameofNOX;
	private String relationwithNOX;
	private String addressofNOX;
	private String email;

	@SuppressWarnings("serial")
	public account(String ID) throws FileNotFoundException 
	{
		setTitle("Brunel University Hospital");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(1000,700);
		setLocationRelativeTo(null);
		
		// Positioning calculations
		int frameWidth = getWidth();
		int labelWidth = 180;
		int fieldWidth = 300;
		int height = 20;
		int totalComponentWidth = labelWidth + 10 + fieldWidth; // 10 pixels space between label and field
		int startingXLabel = ((frameWidth - totalComponentWidth) / 2) + 90;
		int startingXField = startingXLabel + labelWidth + 10; // 10 pixels space between label and field
		int verticalSpacing = 45;
		int initialY = 100; // Adjusted to position elements more centrally vertically

		try 
		{
			final Image backgroundImage = ImageIO.read(new File("pi.png"));
			JPanel backgroundPanel = new JPanel() 
			{
				@Override
				protected void paintComponent(Graphics g) {
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
		    Titleimage.setLocation(-12, 0);
		    Titleimage.setSize(443,131);
		    backgroundPanel.add(Titleimage);

		    ImageIcon image2 = new ImageIcon("brunel-university-london2872-removebg.png");
		    Image image3 = image2.getImage();
		    Image newimg = image3.getScaledInstance(200,120, java.awt.Image.SCALE_SMOOTH);
		    image2 = new ImageIcon(newimg);
		    JLabel image = new JLabel(image2);
		    image.setSize(200, 151);
		    image.setLocation(786, 0);
		    backgroundPanel.add(image);
			
			JButton btnNewButton = new JButton("Back to Search");
			btnNewButton.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					search obj = new search(ID);
					obj.setVisible(true);
					setVisible(false);
					
				}
			});
			btnNewButton.setBounds(400, 452, 125, 21);
			backgroundPanel.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("Followup Details");
			btnNewButton_1.setBounds(765, 483, 182, 21);
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
			
			JButton btnNewButton_2 = new JButton("Log out");
			btnNewButton_2.setBounds(891, 29, 85, 21);
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
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		JButton btnNewButton = new JButton("Appointment Details");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				appointmentdetails obj = new appointmentdetails(ID);
				obj.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(767, 452, 180, 21);
		getContentPane().add(btnNewButton);

		readcsv(ID);

		addLabelAndField("Patient ID:", ID, startingXLabel, initialY, labelWidth, height, startingXField, fieldWidth);
		addLabelAndField("Patient DOB:", DOB, startingXLabel, initialY + verticalSpacing, labelWidth, height, startingXField, fieldWidth);
		addLabelAndField("Patient Name:", Name, startingXLabel, initialY + 2 * verticalSpacing, labelWidth, height, startingXField, fieldWidth);
		addLabelAndField("Patient Address:", address, startingXLabel, initialY + 3 * verticalSpacing, labelWidth, height, startingXField, fieldWidth);
		addLabelAndField("Name Next of Kin:", NameofNOX, startingXLabel, initialY + 4 * verticalSpacing, labelWidth, height, startingXField, fieldWidth);
		addLabelAndField("Relation with Next of Kin:", relationwithNOX, startingXLabel, initialY + 5 * verticalSpacing, labelWidth, height, startingXField, fieldWidth);
		addLabelAndField("Address of Next of Kin:", addressofNOX, startingXLabel, initialY + 6 * verticalSpacing, labelWidth, height, startingXField, fieldWidth);
		addLabelAndField("Email:", email, startingXLabel, initialY + 7 * verticalSpacing, labelWidth, height, startingXField, fieldWidth);
	}

	private void addLabelAndField(String labelText, String fieldValue, int labelX, int labelY, int labelWidth, int labelHeight, int fieldX, int fieldWidth) 
	{
        JLabel label = new JLabel(labelText);
        label.setBounds(labelX, labelY, labelWidth, labelHeight);
        getContentPane().add(label);

        JLabel fieldLabel = new JLabel(fieldValue);
        fieldLabel.setBounds(fieldX, labelY, fieldWidth, labelHeight);
        getContentPane().add(fieldLabel);
    }

	public void readcsv(String pID) throws FileNotFoundException
	{
		File f = new File("patient.csv");
		Scanner reader = new Scanner(f);

		while (reader.hasNext()) 
		{
			String row = reader.nextLine();
			String[] data = row.split(",");
			if (pID.equals(data[0])) 
			{
				Name = data[1];
				DOB = data[2];
				address = data[3];
				NameofNOX = data[4];
				relationwithNOX = data[5];
				addressofNOX = data[6];
				email= data[7];
				break;
			}
		}
		reader.close();
	}
}
