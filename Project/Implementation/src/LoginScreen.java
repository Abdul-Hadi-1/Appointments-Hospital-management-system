import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class LoginScreen extends JFrame 
{
	public String ID;
	private JTextField patientIdField;
	private JPasswordField passwordField;

	public LoginScreen()
	{
		setTitle("Brunel University Hospital");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		try {
			final Image backgroundImage = ImageIO.read(new File("login.png")); 
			setContentPane(new JPanel(new BorderLayout()) 
			{
				@Override
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
				}
			});
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}

		getContentPane().setLayout(null);

		JLabel patientIdLabel = new JLabel("Patient ID:");
		patientIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		patientIdLabel.setBounds(112, 91, 80, 25);
		getContentPane().add(patientIdLabel);

		patientIdField = new JTextField();
		patientIdField.setBounds(202, 93, 165, 25);
		getContentPane().add(patientIdField, BorderLayout.CENTER);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordLabel.setBounds(112, 126, 80, 25);
		getContentPane().add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(202, 128, 165, 25);
		getContentPane().add(passwordField, BorderLayout.CENTER);
		
		JLabel logo = new JLabel("");
        logo.setIcon(new ImageIcon("logo.png"));
        logo.setBounds(220, 0, 105, 85);
        getContentPane().add(logo, BorderLayout.NORTH);

		JLabel forgotPasswordLabel = new JLabel("<HTML><U>Forgot your password?</U></HTML>");
		forgotPasswordLabel.setBounds(207, 163, 160, 25);
		forgotPasswordLabel.setForeground(Color.BLUE);
		forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgotPasswordLabel.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				System.out.println("Password recovery.");
			}
		});

		getContentPane().add(forgotPasswordLabel);

		JButton loginButton = new JButton("Login");
		loginButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					readcsv();
				} catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		loginButton.setBounds(178, 198, 100, 25);
		getContentPane().add(loginButton, BorderLayout.SOUTH);

		JButton registerButton = new JButton("Register");
		registerButton.setBounds(288, 198, 100, 25);
		registerButton.addActionListener(e -> {
			RegistrationForm registrationForm = new RegistrationForm();
			registrationForm.setVisible(true);
			setVisible(false);
		});
		getContentPane().add(registerButton);

		JLabel lblNewLabel = new JLabel("Brunel University Hospital ");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Bodoni MT Black", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 10, 268, 51);
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);

		setSize(574, 323);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run() 
			{
				new LoginScreen();
			}
		});
	}

	public void readcsv() throws FileNotFoundException 
	{
		File f = new File("patient.csv");
		Scanner reader = new Scanner(f);
		boolean flag = false;
		String enteredId = patientIdField.getText();
		String enteredPass = new String(passwordField.getPassword());
		while (reader.hasNext()) {
			String row = reader.nextLine();
			String[] data = row.split(",");

			if (data.length >= 9) {
				String id = data[0];
				String pass = data[8];

				if (id.equals(enteredId) && pass.equals(enteredPass))
				{
					flag = true;
					ID = enteredId;
					break;
				}
			}
		}
		reader.close();
		if (flag == true) 
		{
			search obj = new search(ID); // Ensure you have a constructor in `search` class accepting `String patientID`
			obj.setVisible(true);
			this.setVisible(false);
		}
		else 
		{
			JOptionPane.showMessageDialog(this, "Patient not found\nPlease check your details and try again\nIf not registered please register");
		}
	}
}





















