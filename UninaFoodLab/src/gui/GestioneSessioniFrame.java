package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;

public class GestioneSessioniFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableSessioni;
	private DefaultTableModel tableModel;
	
	
	private Controller theController;

	/**
	 * Create the frame.
	 */
	public GestioneSessioniFrame(Controller c) {
		
		theController = c;
		
		setResizable(false);
		setTitle("Gestione Sessioni");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGestisciSessioni = new JLabel("Gestisci Sessioni");
		lblGestisciSessioni.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblGestisciSessioni.setForeground(new Color(0, 128, 0));
		lblGestisciSessioni.setBounds(255, 10, 207, 37);
		contentPane.add(lblGestisciSessioni);
		
		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"ID",
						"Corso",
						"Data",
						"Orario",
						"Tipo"
				}
		);

		tableSessioni = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(tableSessioni);
		scrollPane.setBounds(40, 60, 600, 200);
		contentPane.add(scrollPane);
		
		
		JButton btnSessioneOnline = new JButton("Aggiungi sessione online");
		btnSessioneOnline.setBackground(new Color(253, 171, 117));
		btnSessioneOnline.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnSessioneOnline.setBounds(120, 290, 200, 35);
		contentPane.add(btnSessioneOnline);

		btnSessioneOnline.addActionListener(e -> {
			new AggiungiSessioneOnlineFrame(theController).setVisible(true);
		});

		JButton btnSessionePratica = new JButton("Aggiungi sessione pratica");
		btnSessionePratica.setBackground(new Color(253, 171, 117));
		btnSessionePratica.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnSessionePratica.setBounds(360, 290, 200, 35);
		contentPane.add(btnSessionePratica);

		btnSessionePratica.addActionListener(e -> {
			new AggiungiSessionePraticaFrame(theController).setVisible(true);
		});
		
		
	}

}
