package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Chef;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChefDashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controller theController;
	private Chef loggedChef;


	/**
	 * Create the frame.
	 */
	public ChefDashboard(Controller c, Chef chefLoggato) {
		
		theController = c;
		this.loggedChef = chefLoggato;
		
		setTitle("Chef Dashboard");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));

		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		//Gestione Corsi
		
		JPanel panelCorsi = new JPanel();
		panelCorsi.setBackground(new Color(244,233,216));
		panelCorsi.setLayout(new BorderLayout());

		JLabel lblCorsi = new JLabel("Gestione Corsi");
		lblCorsi.setForeground(new Color(0, 128, 0));
		lblCorsi.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblCorsi.setHorizontalAlignment(SwingConstants.CENTER);

		panelCorsi.add(lblCorsi, BorderLayout.NORTH);
		
		String[] colonneCorsi = {"ID", "Titolo", "Argomento", "Durata"};
		JTable tableCorsi = new JTable(new DefaultTableModel(colonneCorsi,0));
		JScrollPane scrollCorsi = new JScrollPane(tableCorsi);

		panelCorsi.add(scrollCorsi, BorderLayout.CENTER);
		
		JPanel panelBottoniCorsi = new JPanel();

		JButton btnAggiungiCorso = new JButton("Aggiungi Corso");
		btnAggiungiCorso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AggiungiCorsoFrame(theController, loggedChef).setVisible(true);
			}
		});
		JButton btnGestisciSessioni = new JButton("Gestisci Sessioni");
		btnGestisciSessioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GestioneSessioniFrame(theController).setVisible(true);
			}
		});
		JButton btnFiltraArgomento = new JButton("Filtra Argomento");

		panelBottoniCorsi.add(btnAggiungiCorso);
		panelBottoniCorsi.add(btnGestisciSessioni);
		panelBottoniCorsi.add(btnFiltraArgomento);

		panelCorsi.add(panelBottoniCorsi, BorderLayout.SOUTH);

		tabbedPane.addTab("Gestione Corsi", panelCorsi);
		
		
		//Gestione Sessioni
		
		JPanel panelSessioni = new JPanel(new BorderLayout());
		panelSessioni.setBackground(new Color(244,233,216));

		JLabel lblSessioni = new JLabel("Gestione Sessioni");
		lblSessioni.setForeground(new Color(0, 128, 0));
		lblSessioni.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblSessioni.setHorizontalAlignment(SwingConstants.CENTER);

		panelSessioni.add(lblSessioni, BorderLayout.NORTH);

		String[] colonneSessioni = {"ID","Data","Tipo","Durata"};
		JTable tableSessioni = new JTable(new DefaultTableModel(colonneSessioni,0));

		panelSessioni.add(new JScrollPane(tableSessioni), BorderLayout.CENTER);

		JPanel panelBottoniSessioni = new JPanel();

		JButton btnSessioneOnline = new JButton("Aggiungi Sessione Online");
		JButton btnSessionePratica = new JButton("Aggiungi Sessione Pratica");
		JButton btnGestisciRicette = new JButton("Gestisci Ricette");
		btnGestisciRicette.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GestioneRicetteFrame(theController).setVisible(true);
			}
		});

		panelBottoniSessioni.add(btnSessioneOnline);
		panelBottoniSessioni.add(btnSessionePratica);
		panelBottoniSessioni.add(btnGestisciRicette);

		panelSessioni.add(panelBottoniSessioni, BorderLayout.SOUTH);

		tabbedPane.addTab("Sessioni", panelSessioni);
		
		
		
		//Gestione Ricette
		
		JPanel panelRicette = new JPanel(new BorderLayout());
		panelRicette.setBackground(new Color(244,233,216));

		JLabel lblRicette = new JLabel("Gestione Ricette");
		lblRicette.setForeground(new Color(0, 128, 0));
		lblRicette.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblRicette.setHorizontalAlignment(SwingConstants.CENTER);

		panelRicette.add(lblRicette, BorderLayout.NORTH);

		String[] colonneRicette = {"ID","Nome","Categoria"};
		JTable tableRicette = new JTable(new DefaultTableModel(colonneRicette,0));

		panelRicette.add(new JScrollPane(tableRicette), BorderLayout.CENTER);

		JPanel panelBottoniRicette = new JPanel();

		JButton btnAggiungiIngrediente = new JButton("Aggiungi Ingrediente");
		JButton btnAssociaSessione = new JButton("Associa a Sessione");

		panelBottoniRicette.add(btnAggiungiIngrediente);
		panelBottoniRicette.add(btnAssociaSessione);

		panelRicette.add(panelBottoniRicette, BorderLayout.SOUTH);

		tabbedPane.addTab("Ricette", panelRicette);
		
		
		
		//Gestione Notifiche
		
		JPanel panelNotifiche = new JPanel(new BorderLayout());
		panelNotifiche.setBackground(new Color(244,233,216));

		JLabel lblNotifiche = new JLabel("Notifiche");
		lblNotifiche.setForeground(new Color(0, 128, 0));
		lblNotifiche.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblNotifiche.setHorizontalAlignment(SwingConstants.CENTER);

		panelNotifiche.add(lblNotifiche, BorderLayout.NORTH);

		String[] colonneNotifiche = {"ID","Messaggio","Data"};
		JTable tableNotifiche = new JTable(new DefaultTableModel(colonneNotifiche,0));

		panelNotifiche.add(new JScrollPane(tableNotifiche), BorderLayout.CENTER);

		JPanel panelBottoniNotifiche = new JPanel();

		JButton btnNuovaNotifica = new JButton("Crea Notifica");
		btnNuovaNotifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NotificheFrame(theController).setVisible(true);
			}
		});

		panelBottoniNotifiche.add(btnNuovaNotifica);

		panelNotifiche.add(panelBottoniNotifiche, BorderLayout.SOUTH);

		tabbedPane.addTab("Notifiche", panelNotifiche);
		
		
		//Gestione Report
		
		JPanel panelReport = new JPanel(new BorderLayout());
		panelReport.setBackground(new Color(244,233,216));

		JLabel lblReport = new JLabel("Report Mensile");
		lblReport.setForeground(new Color(0, 128, 0));
		lblReport.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblReport.setHorizontalAlignment(SwingConstants.CENTER);

		panelReport.add(lblReport, BorderLayout.NORTH);

		JTextArea reportArea = new JTextArea();
		reportArea.setEditable(false);

		panelReport.add(new JScrollPane(reportArea), BorderLayout.CENTER);

		tabbedPane.addTab("Report", panelReport);
        
        
	}
}
