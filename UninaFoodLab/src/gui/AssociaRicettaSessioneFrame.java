package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entity.SessionePratica;

public class AssociaRicettaSessioneFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller theController;
	private int idRicetta;
	private JComboBox<SessionePratica> comboSessioni;
    private JButton btnAssocia;
    
	/**
	 * Create the frame.
	 */
	public AssociaRicettaSessioneFrame(Controller c, int idRicetta) {
		
		theController = c;
		this.idRicetta=idRicetta;
		
		setResizable(false);
		setTitle("Aggiungi Ricetta a Sessione");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titolo = new JLabel("Aggiungi Ricetta a Sessione");
        titolo.setForeground(new Color(0, 128, 0));
        titolo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titolo.setBounds(186, 36, 350, 30);
        contentPane.add(titolo);

        JLabel lblSessioni = new JLabel("Seleziona Sessione Pratica:");
        lblSessioni.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSessioni.setBounds(48, 117, 200, 25);
        contentPane.add(lblSessioni);

        comboSessioni = new JComboBox<>();
        comboSessioni.setBounds(251, 119, 300, 25);
        contentPane.add(comboSessioni);

        btnAssocia = new JButton("Associa");
        btnAssocia.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnAssocia.setBackground(new Color(253, 171, 117));
        btnAssocia.setBounds(257, 173, 120, 30);
        contentPane.add(btnAssocia);

        btnAssocia.addActionListener(e -> {
            SessionePratica s = (SessionePratica) comboSessioni.getSelectedItem();
            if (s != null) {
                try {
                    theController.associaRicettaASessionePratica(idRicetta, s.getId());
                    JOptionPane.showMessageDialog(this, "Ricetta associata correttamente!");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona una sessione!");
            }
        });
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/U_F_L_1.png"));

        Image img = icon.getImage();
        Image imgRidimensionata = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        ImageIcon iconRidimensionata = new ImageIcon(imgRidimensionata);

        JLabel label = new JLabel(iconRidimensionata);
        label.setBounds(513, 192, 134, 113);

        getContentPane().add(label);

        caricaSessioni();
        setVisible(true);
	}
	
	private void caricaSessioni() {
		try {
		    List<SessionePratica> sessioniFuture = theController.getSessioniPraticheFuture();
		    for (SessionePratica s : sessioniFuture) {
		        comboSessioni.addItem(s);
		    }
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(this, e.getMessage());
		}
    }

}
