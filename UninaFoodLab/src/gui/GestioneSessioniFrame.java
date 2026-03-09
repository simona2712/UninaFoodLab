package gui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class GestioneSessioniFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controller theController;

	/**
	 * Create the frame.
	 */
	public GestioneSessioniFrame(Controller c) {
		
		theController = c;
		
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}
