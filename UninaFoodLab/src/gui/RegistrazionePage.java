package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class RegistrazionePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controller theController;

	/**
	 * Create the frame.
	 */
	public RegistrazionePage(Controller c) {
		
		theController = c;
		
		
		setResizable(false);
		setTitle("UninaFoodLab - Registrazione");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\gigli\\Desktop\\Simona\\uni\\primo anno\\Progetto\\U_F_L_1.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 388);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

}
