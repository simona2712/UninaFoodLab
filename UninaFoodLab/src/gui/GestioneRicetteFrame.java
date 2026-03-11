package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

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

public class GestioneRicetteFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableRicette;
	private DefaultTableModel tableModel;
	
	private Controller theController;


	/**
	 * Create the frame.
	 */
	public GestioneRicetteFrame(Controller c) {
		
		theController= c;
		
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
						"Nome",
						"Difficoltà",
						"Tempo preparazione"
				}
		);

		tableRicette = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tableRicette);
		scroll.setBounds(50,70,640,200);
		contentPane.add(scroll);

		JButton btnIngrediente = new JButton("Aggiungi Ingrediente");
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
	}

}
