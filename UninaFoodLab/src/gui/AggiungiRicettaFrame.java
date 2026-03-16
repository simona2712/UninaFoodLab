package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entity.Chef;
import entity.Ricetta;

public class AggiungiRicettaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDescrizione;
    private JTextField txtDurata;
    private JComboBox<String> comboPrep;
    private JComboBox<String> comboAllergeni;

	private Controller theController;
	private ChefDashboard chefDashboard;
	private Chef loggedChef;
	
	/**
	 * Create the frame.
	 */
	public AggiungiRicettaFrame(Controller c, Chef chefLoggato, ChefDashboard dashboard) {
		
		this.theController = c;
		this.loggedChef = chefLoggato;
		this.chefDashboard = dashboard;
		
		setResizable(false);
        setTitle("Aggiungi Ricetta");
        setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
        setBounds(100, 100, 715, 388);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(244,233,216));
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel titolo = new JLabel("Nuova Ricetta");
        titolo.setForeground(new Color(0,128,0));
        titolo.setFont(new Font("SansSerif",Font.BOLD,20));
        titolo.setBounds(236,35,200,30);
        contentPane.add(titolo);

        JLabel lblDescrizione = new JLabel("Descrizione:");
        lblDescrizione.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescrizione.setBounds(40,86,100,25);
        contentPane.add(lblDescrizione);

        txtDescrizione = new JTextField();
        txtDescrizione.setBounds(192,88,300,25);
        contentPane.add(txtDescrizione);

        JLabel lblDurata = new JLabel("Durata (min):");
        lblDurata.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDurata.setBounds(40,121,100,25);
        contentPane.add(lblDurata);

        txtDurata = new JTextField();
        txtDurata.setBounds(192,123,100,25);
        contentPane.add(txtDurata);

        JLabel lblPrep = new JLabel("Tipo preparazione:");
        lblPrep.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPrep.setBounds(40,156,144,25);
        contentPane.add(lblPrep);

        comboPrep = new JComboBox<>(new String[]{
        	    "Padella",
        	    "Forno",
        	    "Pentola",
        	    "Brace",
        	    "Frittura",
        	    "Crudo"
        });
        comboPrep.setFont(new Font("SansSerif", Font.PLAIN, 12));

        comboPrep.setBounds(192,158,150,25);
        contentPane.add(comboPrep);
        
        String[] allergeni = {"Glutine", "Lattosio", "Arachidi", "Uova", "Frutta"};

        comboAllergeni = new JComboBox<>(allergeni);
        comboAllergeni.setFont(new Font("SansSerif", Font.PLAIN, 12));
        comboAllergeni.setBounds(192, 193, 150, 25);

        contentPane.add(comboAllergeni);

        JButton btnSalva = new JButton("Salva Ricetta");
        btnSalva.setBackground(new Color(253,171,117));
        btnSalva.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnSalva.setBounds(192,275,160,35);
        contentPane.add(btnSalva);

        btnSalva.addActionListener(e -> salvaRicetta());
        
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/U_F_L_1.png"));

	     Image img = icon.getImage();
	     Image imgRidimensionata = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

	     ImageIcon iconRidimensionata = new ImageIcon(imgRidimensionata);

	     JLabel label = new JLabel(iconRidimensionata);
	     label.setBounds(508, 86, 166, 143);

	     getContentPane().add(label);
	     
	     JLabel lblAllergeni = new JLabel("Allergeni:");
	     lblAllergeni.setFont(new Font("SansSerif", Font.PLAIN, 14));
	     lblAllergeni.setBounds(40, 191, 144, 25);
	     contentPane.add(lblAllergeni);

        setVisible(true);
	}

	private void salvaRicetta() {

        try {

            String descrizione = txtDescrizione.getText();
            int durata = Integer.parseInt(txtDurata.getText());
            String preparazione = (String)comboPrep.getSelectedItem();
            List<String> allergeniSelezionati = new ArrayList<>();

            allergeniSelezionati.add((String) comboAllergeni.getSelectedItem());
            
            Ricetta r = new Ricetta(0,durata,descrizione,preparazione);
            r.setPreparazione(preparazione);
            r.setAllergeni(allergeniSelezionati);

            theController.aggiungiRicetta(r);

            JOptionPane.showMessageDialog(this,"Ricetta inserita!");

            chefDashboard.aggiornaTabellaRicette();

            dispose();

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Errore: "+ex.getMessage());
        }

    }
}
