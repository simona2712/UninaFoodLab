package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
import entity.Corso;

public class AggiungiSessioneOnlineFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Corso> comboCorso;
    private JTextField textData;
    private JTextField textOra;
    private JTextField textDurata;
    private JTextField textMax;
    private JTextField textLink;
	
	
	private Controller theController;


	/**
	 * Create the frame.
	 */
	public AggiungiSessioneOnlineFrame(Controller c) {
		
		theController = c;
		
		setResizable(false);
		setTitle("Aggiungi Sessione Online");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715,450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGestisciSessioni = new JLabel("Nuova Sessione Online");
		lblGestisciSessioni.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblGestisciSessioni.setForeground(new Color(0, 128, 0));
		lblGestisciSessioni.setBounds(200, 22, 272, 37);
		contentPane.add(lblGestisciSessioni);
		
		JLabel lblCorso = new JLabel("Corso");
		lblCorso.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCorso.setBounds(60,80,120,25);
        contentPane.add(lblCorso);

        comboCorso = new JComboBox<>();
        comboCorso.setBounds(230,82,200,25);
        contentPane.add(comboCorso);

        JLabel lblData = new JLabel("Data (YYYY-MM-DD)");
        lblData.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblData.setBounds(60,120,150,25);
        contentPane.add(lblData);

        textData = new JTextField();
        textData.setBounds(230,122,200,25);
        contentPane.add(textData);

        JLabel lblOra = new JLabel("Ora (HH:MM)");
        lblOra.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblOra.setBounds(60,160,120,25);
        contentPane.add(lblOra);

        textOra = new JTextField();
        textOra.setBounds(230,160,200,25);
        contentPane.add(textOra);

        JLabel lblDurata = new JLabel("Durata (min)");
        lblDurata.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDurata.setBounds(60,200,120,25);
        contentPane.add(lblDurata);

        textDurata = new JTextField();
        textDurata.setBounds(230,200,200,25);
        contentPane.add(textDurata);


        JLabel lblMax = new JLabel("Max partecipanti");
        lblMax.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblMax.setBounds(60,235,120,25);
        contentPane.add(lblMax);

        textMax = new JTextField();
        textMax.setBounds(230,237,200,25);
        contentPane.add(textMax);
        
        JLabel lblLink = new JLabel("Link meeting");
        lblLink.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblLink.setBounds(60,280,120,25);
        contentPane.add(lblLink);

        textLink = new JTextField();
        textLink.setBounds(230,280,200,25);
        contentPane.add(textLink);

        JButton btnCrea = new JButton("Crea sessione");
        btnCrea.setBackground(new Color(253, 171, 117));
        btnCrea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnCrea.setBounds(248,331,166,37);
        contentPane.add(btnCrea);

		btnCrea.addActionListener(e -> creaSessioneOnline());
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/U_F_L_1.png"));

	     Image img = icon.getImage();
	     Image imgRidimensionata = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

	     ImageIcon iconRidimensionata = new ImageIcon(imgRidimensionata);

	     JLabel label = new JLabel(iconRidimensionata);
	     label.setBounds(499, 117, 166, 143);

	     getContentPane().add(label);
	     
	     caricaCorsi();
	}
	
	 private void creaSessioneOnline() {

	        try {
	            Corso corso = (Corso) comboCorso.getSelectedItem();
	            int durata = Integer.parseInt(textDurata.getText());
	            int max = Integer.parseInt(textMax.getText());

	            LocalDate data = LocalDate.parse(textData.getText());
	            LocalTime ora = LocalTime.parse(textOra.getText());
	            String link = textLink.getText();

	            if(corso == null){
	                JOptionPane.showMessageDialog(this,"Seleziona un corso");
	                return;
	            }

	            theController.aggiungiSessioneOnline(0, durata, data, ora, link, corso, max);

	            JOptionPane.showMessageDialog(this,"Sessione online creata!");
	            dispose();

	        } catch(Exception ex) {
	            JOptionPane.showMessageDialog(this,"Errore: "+ex.getMessage());
	        }
	    }
	 
	 private void caricaCorsi() {

		    try {
		        List<Corso> corsi = theController.getCorsiChef();

		        for(Corso c : corsi) {
		            comboCorso.addItem(c);
		        }

		    } catch(Exception e) {
		        JOptionPane.showMessageDialog(this, e.getMessage());
		    }
	 }

}
