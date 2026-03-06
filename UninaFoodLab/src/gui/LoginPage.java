package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\gigli\\Desktop\\Simona\\uni\\primo anno\\Progetto\\U_F_L_1.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 388);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblEmail.setBackground(new Color(240, 240, 240));
		lblEmail.setBounds(420, 80, 78, 32);
		contentPane.add(lblEmail);
		
		textField = new JTextField();
		textField.setBounds(531, 83, 127, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblPassword.setBackground(UIManager.getColor("Button.background"));
		lblPassword.setBounds(420, 137, 88, 32);
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
        btnAccedi.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAccedi.setBackground(new Color(255, 255, 255));
        btnAccedi.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnAccedi.setBounds(420, 214, 106, 32);
        contentPane.add(btnAccedi);
        
        JButton btnRegistrati = new JButton("Registrati");
        btnRegistrati.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnRegistrati.setBounds(552, 214, 106, 32);
        contentPane.add(btnRegistrati);
        
        setVisible(true);
		
	}
}
