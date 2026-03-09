package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entity.Chef;
import entity.Corso;
import exceptions.ValidationException;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AggiungiCorsoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controller theController;
	private JTextField txtCategoria;
	private JTextField txtDataInizio;
	private JComboBox<String> comboBoxFrequenza;
	private JTextField txtNumSessioni;
	private JTextField textFieldArgomento;
	private JTextField textFieldDataInizio;
	private JLabel lblNumeroSessioni;
	private JTextField textFieldNumeroSessioni;
	private JLabel lblNome;
	private JTextField textFieldNome;
	private Chef loggedChef;
	
	
	/**
	 * Create the frame.
	 */
	public AggiungiCorsoFrame(Controller c, Chef chefLoggato) {
		
		theController = c;
		this.loggedChef = chefLoggato;
		
		setResizable(false);
		setTitle("Aggiungi Corso");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAggiungiCorso = new JLabel("Aggiungi Corso");
		lblAggiungiCorso.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblAggiungiCorso.setForeground(new Color(0, 128, 0));
		lblAggiungiCorso.setBounds(255, 10, 207, 37);
		contentPane.add(lblAggiungiCorso);
		
		JLabel lblArgomento = new JLabel("Argomento");
		lblArgomento.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblArgomento.setBounds(164, 108, 74, 24);
		contentPane.add(lblArgomento);
		
		textFieldArgomento = new JTextField();
		textFieldArgomento.setBounds(366, 113, 96, 19);
		contentPane.add(textFieldArgomento);
		textFieldArgomento.setColumns(10);
		
		JLabel lblDataInizio = new JLabel("Data inizio");
		lblDataInizio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblDataInizio.setBounds(164, 142, 74, 24);
		contentPane.add(lblDataInizio);
		
		textFieldDataInizio = new JTextField();
		textFieldDataInizio.setBounds(366, 147, 96, 19);
		contentPane.add(textFieldDataInizio);
		textFieldDataInizio.setColumns(10);
		
		JLabel lblFrequenza = new JLabel("Frequenza");
		lblFrequenza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblFrequenza.setBounds(164, 176, 74, 24);
		contentPane.add(lblFrequenza);
		
		
		comboBoxFrequenza = new JComboBox<>(new String[]{
		        "Giornaliera",
		        "Ogni 2 giorni",
		        "Settimanale",
		        "Mensile"
		});
		comboBoxFrequenza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		comboBoxFrequenza.setBounds(366, 179, 96, 19);
		contentPane.add(comboBoxFrequenza);
		
		lblNumeroSessioni = new JLabel("Numero Sessioni");
		lblNumeroSessioni.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNumeroSessioni.setBounds(164, 210, 128, 24);
		contentPane.add(lblNumeroSessioni);
		
		textFieldNumeroSessioni = new JTextField();
		textFieldNumeroSessioni.setColumns(10);
		textFieldNumeroSessioni.setBounds(366, 215, 96, 19);
		contentPane.add(textFieldNumeroSessioni);
		
		lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNome.setBounds(164, 74, 74, 24);
		contentPane.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(366, 79, 96, 19);
		contentPane.add(textFieldNome);
		
		JButton btnNewButton = new JButton("Crea Corso");
		btnNewButton.addActionListener(e -> salvaCorso());
		btnNewButton.setBackground(new Color(214, 168, 79));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton.setBounds(255, 267, 111, 37);
		contentPane.add(btnNewButton);
		
		 ImageIcon icon = new ImageIcon(getClass().getResource("/img/U_F_L_1.png"));

	     Image img = icon.getImage();
	     Image imgRidimensionata = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

	     ImageIcon iconRidimensionata = new ImageIcon(imgRidimensionata);

	     JLabel label = new JLabel(iconRidimensionata);
	     label.setBounds(513, 74, 166, 143);

	     getContentPane().add(label);
		       
        
	}
	
	private void salvaCorso() {
	    try {
	        String nomeCorso = textFieldNome.getText().trim();
	        String categoria = textFieldArgomento.getText().trim();
	        String data = textFieldDataInizio.getText().trim();
	        String frequenza = (String) comboBoxFrequenza.getSelectedItem();
	        String numSessioniStr = textFieldNumeroSessioni.getText().trim();

	        if (nomeCorso.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Inserisci il nome del corso!");
	            return;
	        }
	        if (categoria.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Inserisci la categoria!");
	            return;
	        }
	        if (data.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Inserisci la data di inizio (YYYY-MM-DD)!");
	            return;
	        }
	        if (numSessioniStr.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Inserisci il numero di sessioni!");
	            return;
	        }

	        int numSessioni;
	        try {
	            numSessioni = Integer.parseInt(numSessioniStr);
	            if (numSessioni <= 0) {
	                JOptionPane.showMessageDialog(this, "Il numero di sessioni deve essere maggiore di zero!");
	                return;
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Numero di sessioni non valido!");
	            return;
	        }

	        LocalDate dataInizio;
	        try {
	            dataInizio = LocalDate.parse(data);
	        } catch (DateTimeParseException e) {
	            JOptionPane.showMessageDialog(this, "Data non valida! Usa formato YYYY-MM-DD");
	            return;
	        }

	        int giorni = switch(frequenza) {
	            case "Giornaliera" -> 1;
	            case "Ogni 2 giorni" -> 2;
	            case "Settimanale" -> 7;
	            case "Mensile" -> 30;
	            default -> 1;
	        };

	        LocalDate dataFine = dataInizio.plusDays((long) (numSessioni - 1) * giorni);

	        Corso corso = new Corso(0, nomeCorso, categoria, dataInizio, dataFine, frequenza, loggedChef);

	        theController.creaCorso(corso);

	        JOptionPane.showMessageDialog(this, "Corso creato con successo!");
	        dispose();

	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Errore database: " + ex.getMessage());
	    } catch (ValidationException ex) {
	        JOptionPane.showMessageDialog(this, "Errore validazione: " + ex.getMessage());
	    }
	}
}
