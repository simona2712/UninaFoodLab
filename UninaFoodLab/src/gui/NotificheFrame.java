package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entity.Corso;

import java.awt.Font;
import java.awt.Image;
import java.util.List;

public class NotificheFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextArea textMessaggio;
	private JComboBox<Corso> comboCorsi;
	private JRadioButton radioSingolo;
	private JRadioButton radioTutti;
	
	private Controller theController;


	/**
	 * Create the frame.
	 */
	public NotificheFrame(Controller c) {
		
		theController= c;
		
		setResizable(false);
		setTitle("Invia Notifica");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715,450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGestisciSessioni = new JLabel("Invia Notifica");
		lblGestisciSessioni.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblGestisciSessioni.setForeground(new Color(0, 128, 0));
		lblGestisciSessioni.setBounds(200, 22, 272, 37);
		contentPane.add(lblGestisciSessioni);

		JLabel lblMessaggio = new JLabel("Messaggio");
		lblMessaggio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblMessaggio.setBounds(40,80,100,25);
		contentPane.add(lblMessaggio);

		textMessaggio = new JTextArea();
		JScrollPane scroll = new JScrollPane(textMessaggio);
		scroll.setBounds(140,80,300,120);
		contentPane.add(scroll);

		radioSingolo = new JRadioButton("Singolo corso");
		radioSingolo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		radioSingolo.setBounds(140,220,150,25);

		radioTutti = new JRadioButton("Tutti i corsi");
		radioTutti.setFont(new Font("SansSerif", Font.PLAIN, 14));
		radioTutti.setBounds(300,220,150,25);

		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(radioSingolo);
		gruppo.add(radioTutti);

		contentPane.add(radioSingolo);
		contentPane.add(radioTutti);

		comboCorsi = new JComboBox<>();
		comboCorsi.setBounds(140,260,200,25);
		contentPane.add(comboCorsi);
		
		radioSingolo.setSelected(true);
        comboCorsi.setEnabled(true);

        radioSingolo.addActionListener(e -> comboCorsi.setEnabled(true));
        radioTutti.addActionListener(e -> comboCorsi.setEnabled(false));

		JButton btnInvia = new JButton("Invia notifica");
		btnInvia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInvia.setBounds(230,309,150,35);
		contentPane.add(btnInvia);

		btnInvia.addActionListener(e -> inviaNotifica());
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/U_F_L_1.png"));

	     Image img = icon.getImage();
	     Image imgRidimensionata = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

	     ImageIcon iconRidimensionata = new ImageIcon(imgRidimensionata);

	     JLabel label = new JLabel(iconRidimensionata);
	     label.setBounds(513, 74, 166, 143);

	     getContentPane().add(label);
	     
	     caricaCorsi();
	}
	
	private void inviaNotifica() {
        String testo = textMessaggio.getText().trim();
        if (testo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci un messaggio");
            return;
        }

        try {
            if (radioTutti.isSelected()) {
                theController.inviaNotificaATutti(testo);
            } else {
                Corso corso = (Corso) comboCorsi.getSelectedItem();
                theController.inviaNotificaCorso(corso, testo);
            }
            JOptionPane.showMessageDialog(this, "Notifica inviata");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
        }
    }

    private void caricaCorsi() {
        try {
            List<Corso> corsi = theController.getCorsiChef();
            for (Corso c : corsi) {
                comboCorsi.addItem(c);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore caricamento corsi: " + e.getMessage());
        }
    }

}
