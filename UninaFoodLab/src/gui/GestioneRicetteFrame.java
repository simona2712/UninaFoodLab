package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Ricetta;

public class GestioneRicetteFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableRicette;
	private DefaultTableModel tableModel;
	
	private Controller theController;
	private ChefDashboard chefDashboard;


	/**
	 * Create the frame.
	 */
	public GestioneRicetteFrame(Controller c, ChefDashboard dashboard) {
		
		this.theController= c;
		this.chefDashboard=dashboard;
		
		setResizable(false);
		setTitle("Gestione Ricette");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titolo = new JLabel("Gestione Ricette");
		titolo.setForeground(new Color(0, 128, 0));
		titolo.setFont(new Font("SansSerif",Font.BOLD,20));
		titolo.setBounds(280,15,200,30);
		contentPane.add(titolo);

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"ID",
						"Descrizione",
						"Durata",
						"Calorie"
				}
		);

		tableRicette = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tableRicette);
		scroll.setBounds(50,70,640,200);
		contentPane.add(scroll);

		JButton btnIngrediente = new JButton("Aggiungi Ingrediente");
		btnIngrediente.setBackground(new Color(253, 171, 117));
		btnIngrediente.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnIngrediente.setBounds(150,300,200,35);
		contentPane.add(btnIngrediente);

		btnIngrediente.addActionListener(e -> {

			int row = tableRicette.getSelectedRow();

			if(row == -1) {
				JOptionPane.showMessageDialog(this,"Seleziona una ricetta");
				return;
			}

			int idRicetta = (int) tableModel.getValueAt(row,0);

			new AggiungiIngredienteFrame(theController,idRicetta).setVisible(true);

		});

		JButton btnAssocia = new JButton("Associa a Sessione");
		btnAssocia.setBackground(new Color(253, 171, 117));
		btnAssocia.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnAssocia.setBounds(400,300,200,35);
		contentPane.add(btnAssocia);

		btnAssocia.addActionListener(e -> {
			int row = tableRicette.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(this,"Seleziona una ricetta");
				return;
			}
			int idRicetta = (int) tableModel.getValueAt(row,0);
			new AssociaRicettaSessioneFrame(theController,idRicetta).setVisible(true);
		});

		setVisible(true);
		caricaRicette();
	}
	
	private void caricaRicette() {
		try {
            tableModel.setRowCount(0);
            List<Ricetta> ricette = theController.getRicetteChef();
            for(Ricetta r : ricette) {
                tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getDescrizione(),
                    r.getDurata(),
                    r.calcolaCalorieTotali()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore caricamento ricette: " + e.getMessage());
        }
    }
	
	public void aggiornaTabellaRicette() {
	    caricaRicette();
	}

}
