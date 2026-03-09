package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class RegistrazionePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controller theController;
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JTextField textFieldNumTel;
	private JTextField textFieldSpec;
	private JTextField textFieldEsperienza;
	private JComboBox<String> comboBoxLivello;

	/**
	 * Create the frame.
	 */
	public RegistrazionePage(Controller c) {
		
		theController = c;
		
		
		setResizable(false);
		setTitle("UninaFoodLab - Registrazione");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRegistrazione = new JLabel("Registrazione utente");
		lblRegistrazione.setForeground(new Color(0, 128, 0));
		lblRegistrazione.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblRegistrazione.setBounds(255, 10, 207, 36);
		contentPane.add(lblRegistrazione);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNome.setBounds(10, 73, 76, 28);
		contentPane.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(154, 80, 104, 19);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCognome.setBounds(10, 111, 76, 28);
		contentPane.add(lblCognome);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setColumns(10);
		textFieldCognome.setBounds(154, 118, 104, 19);
		contentPane.add(textFieldCognome);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblEmail.setBounds(10, 149, 76, 28);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(154, 156, 104, 19);
		contentPane.add(textFieldEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblPassword.setBounds(10, 187, 76, 28);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(154, 194, 104, 19);
		contentPane.add(passwordField);
		
		JRadioButton rdbtnChef = new JRadioButton("Chef");
		rdbtnChef.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnChef.setBounds(121, 275, 104, 28);
		contentPane.add(rdbtnChef);
		
		JRadioButton rdbtnAllievo = new JRadioButton("Allievo");
		rdbtnAllievo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnAllievo.setBounds(255, 275, 104, 28);
		contentPane.add(rdbtnAllievo);
		
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(rdbtnChef);
		gruppo.add(rdbtnAllievo);
		
		JLabel lblNewLabel = new JLabel("Tipo utente");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 275, 86, 28);
		contentPane.add(lblNewLabel);
		
		JButton btnRegistrati = new JButton("Registrati");
		btnRegistrati.addActionListener(e -> registraUtente(rdbtnChef, rdbtnAllievo));
		btnRegistrati.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnRegistrati.setBounds(521, 269, 140, 40);
		contentPane.add(btnRegistrati);
		
		JLabel lblNumeroDiCellulare = new JLabel("Numero di cellulare");
		lblNumeroDiCellulare.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNumeroDiCellulare.setBounds(10, 225, 134, 28);
		contentPane.add(lblNumeroDiCellulare);
		
		textFieldNumTel = new JTextField();
		textFieldNumTel.setColumns(10);
		textFieldNumTel.setBounds(154, 232, 104, 19);
		contentPane.add(textFieldNumTel);
		
		JLabel lblSpecializzazione = new JLabel("Specializzazione");
		lblSpecializzazione.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSpecializzazione.setBounds(304, 73, 121, 28);
		contentPane.add(lblSpecializzazione);
		
		textFieldSpec = new JTextField();
		textFieldSpec.setColumns(10);
		textFieldSpec.setBounds(458, 76, 104, 19);
		contentPane.add(textFieldSpec);
		
		JLabel lblAnniDiEsperienza = new JLabel("Anni di esperienza");
		lblAnniDiEsperienza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblAnniDiEsperienza.setBounds(304, 111, 121, 28);
		contentPane.add(lblAnniDiEsperienza);
		
		textFieldEsperienza = new JTextField();
		textFieldEsperienza.setColumns(10);
		textFieldEsperienza.setBounds(458, 114, 104, 19);
		contentPane.add(textFieldEsperienza);
		
		JLabel lblLivelloAbilit = new JLabel("Livello abilità");
		lblLivelloAbilit.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblLivelloAbilit.setBounds(304, 152, 121, 28);
		contentPane.add(lblLivelloAbilit);
		
		comboBoxLivello = new JComboBox<>(new String[]{
		        "Principiante",
		        "Intermedio",
		        "Avanzato"
		});
		comboBoxLivello.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		comboBoxLivello.setBounds(458, 155, 104, 19);
		contentPane.add(comboBoxLivello);
		
		textFieldSpec.setVisible(false);
		textFieldEsperienza.setVisible(false);
		comboBoxLivello.setVisible(false);
		
		rdbtnChef.addActionListener(e ->{
			textFieldSpec.setVisible(true);
			textFieldEsperienza.setVisible(true);

			comboBoxLivello.setVisible(false);
		});
		
		rdbtnAllievo.addActionListener(e ->{
			comboBoxLivello.setVisible(true);

			textFieldSpec.setVisible(false);
			textFieldEsperienza.setVisible(false);
		});
		
	}
	
	private void registraUtente(JRadioButton chef, JRadioButton allievo) {

	    String nome = textFieldNome.getText();
	    String cognome = textFieldCognome.getText();
	    String email = textFieldEmail.getText();
	    String password = new String(passwordField.getPassword());
	    String telefono = textFieldNumTel.getText();

	    try {

	        if(chef.isSelected()) {
	            String spec = textFieldSpec.getText();
	            int esperienza = Integer.parseInt(textFieldEsperienza.getText());

	            theController.registraChef(nome, cognome, telefono, email, password, spec, esperienza);
	            JOptionPane.showMessageDialog(this, "Chef registrato!");
	        }
	        else if(allievo.isSelected()) {
	        	String livello = (String) comboBoxLivello.getSelectedItem();
	            theController.registraAllievo(nome, cognome, telefono, email, password, livello);
	            JOptionPane.showMessageDialog(this, "Allievo registrato!");
	        }
	        else {
	            JOptionPane.showMessageDialog(this, "Seleziona il tipo utente");
	        }

	    } catch(Exception ex) {
	        JOptionPane.showMessageDialog(this, ex.getMessage());
	    }
	}
}
