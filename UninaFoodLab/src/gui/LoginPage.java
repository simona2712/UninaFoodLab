package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entity.Chef;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	
	private Controller theController;


	/**
	 * Create the frame.
	 */
	public LoginPage(Controller c) {
		
		theController = c;
		
		setResizable(false);
		setTitle("UninaFoodLab - Login");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 17));
		lblEmail.setBackground(new Color(240, 240, 240));
		lblEmail.setBounds(403, 82, 78, 32);
		contentPane.add(lblEmail);
		
		textField = new JTextField();
		textField.setBounds(531, 83, 127, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 17));
		lblPassword.setBackground(UIManager.getColor("Button.background"));
		lblPassword.setBounds(403, 138, 88, 32);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(531, 138, 127, 31);
		contentPane.add(passwordField);
		
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/U_F_L.png"));

        Image img = icon.getImage();
        Image imgRidimensionata = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);

        ImageIcon iconRidimensionata = new ImageIcon(imgRidimensionata);

        JLabel label = new JLabel(iconRidimensionata);
        label.setBounds(33, 58, 307, 216);

        getContentPane().add(label);
        
        JButton btnAccedi = new JButton("Accedi");
        btnAccedi.addActionListener(e -> {
            String email = textField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if(email.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(this, "Inserisci sia email che password!");
                return;
            }

            try {
                Chef loggedChef = theController.loginChef(email, password);

                JOptionPane.showMessageDialog(this, "Login effettuato");

                new ChefDashboard(theController, loggedChef).setVisible(true);
                dispose();

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore login: " + ex.getMessage());
            }
        });
        btnAccedi.setBackground(new Color(253, 171, 117));
        btnAccedi.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btnAccedi.setBounds(403, 214, 115, 40);
        contentPane.add(btnAccedi);
        
        JButton btnRegistrati = new JButton("Registrati");
        btnRegistrati.setBackground(new Color(253, 171, 117));
        btnRegistrati.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new RegistrazionePage(theController).setVisible(true);
        	    dispose();
        	}
        });
        btnRegistrati.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btnRegistrati.setBounds(543, 214, 115, 40);
        contentPane.add(btnRegistrati);
        
        setVisible(true);
		
	}
}
