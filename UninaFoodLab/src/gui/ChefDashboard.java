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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Chef;
import entity.Corso;
import entity.Notifica;
import entity.Ricetta;
import entity.SessioneOnline;
import entity.SessionePratica;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.ActionEvent;

public class ChefDashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JTable tableCorsi;
	private JTable tableSessioni;
	private JTable tableRicette;
	private JTable tableNotifiche;
	private JTextArea reportArea;
	
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
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));

		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		// ==============================
        // TAB 1 - GESTIONE CORSI
        // ==============================
		
		JPanel panelCorsi = new JPanel(new BorderLayout());
        panelCorsi.setBackground(new Color(244, 233, 216));

        JLabel lblCorsi = new JLabel("Gestione Corsi");
        lblCorsi.setForeground(new Color(0, 128, 0));
        lblCorsi.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblCorsi.setHorizontalAlignment(SwingConstants.CENTER);
        panelCorsi.add(lblCorsi, BorderLayout.NORTH);

        String[] colonneCorsi = {"ID", "Titolo", "Argomento", "Data Inizio"};
        tableCorsi = new JTable(new DefaultTableModel(colonneCorsi, 0));
        panelCorsi.add(new JScrollPane(tableCorsi), BorderLayout.CENTER);

        JPanel panelBottoniCorsi = new JPanel();

        JButton btnAggiungiCorso = new JButton("Aggiungi Corso");
        btnAggiungiCorso.addActionListener(e -> {
            setVisible(false);
            new AggiungiCorsoFrame(theController, loggedChef, ChefDashboard.this).setVisible(true);
        });

        JButton btnGestisciSessioni = new JButton("Gestisci Sessioni");
        btnGestisciSessioni.addActionListener(e -> {
            new GestioneSessioniFrame(theController).setVisible(true);
        });

        JButton btnFiltraArgomento = new JButton("Filtra Argomento");
        btnFiltraArgomento.addActionListener(e -> {
            try {

                List<Corso> corsi = theController.getCorsiChef();

                java.util.Set<String> argomentiSet = new java.util.HashSet<>();

                for (Corso cor : corsi) {
                    argomentiSet.add(cor.getArgomento());
                }

                String[] argomenti = argomentiSet.toArray(new String[0]);

                String scelta = (String) JOptionPane.showInputDialog(
                        this,
                        "Seleziona un argomento",
                        "Filtra Corsi",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        argomenti,
                        argomenti[0]
                );

                if (scelta != null) {

                    DefaultTableModel model = (DefaultTableModel) tableCorsi.getModel();
                    model.setRowCount(0);

                    List<Corso> corsiFiltrati = theController.filtraCorsiPerArgomento(scelta);

                    for (Corso cor : corsiFiltrati) {
                        model.addRow(new Object[]{
                                cor.getId(),
                                cor.getNome(),
                                cor.getArgomento(),
                                cor.getDataInizio()
                        });
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        
        

        panelBottoniCorsi.add(btnAggiungiCorso);
        panelBottoniCorsi.add(btnGestisciSessioni);
        panelBottoniCorsi.add(btnFiltraArgomento);
        panelCorsi.add(panelBottoniCorsi, BorderLayout.SOUTH);

        tabbedPane.addTab("Gestione Corsi", panelCorsi);
        caricaCorsi();
		
		
		// ==============================
        // TAB 2 - GESTIONE SESSIONI
        // ==============================
        
	    JPanel panelSessioni = new JPanel(new BorderLayout());
	    panelSessioni.setBackground(new Color(244, 233, 216));
	
	     JLabel lblSessioni = new JLabel("Gestione Sessioni");
	     lblSessioni.setForeground(new Color(0, 128, 0));
	     lblSessioni.setFont(new Font("SansSerif", Font.BOLD, 20));
	     lblSessioni.setHorizontalAlignment(SwingConstants.CENTER);
	     panelSessioni.add(lblSessioni, BorderLayout.NORTH);
	
	     String[] colonneSessioni = {"ID", "Corso", "Tipo", "Data", "Durata"};
	     tableSessioni = new JTable(new DefaultTableModel(colonneSessioni, 0));
	     panelSessioni.add(new JScrollPane(tableSessioni), BorderLayout.CENTER);
	
	     JPanel panelBottoniSessioni = new JPanel();
	
	     JButton btnSessioneOnline = new JButton("Aggiungi Sessione Online");
	     btnSessioneOnline.addActionListener(e -> new AggiungiSessioneOnlineFrame(theController, null, ChefDashboard.this).setVisible(true));
	
	     JButton btnSessionePratica = new JButton("Aggiungi Sessione Pratica");
	     btnSessionePratica.addActionListener(e -> new AggiungiSessionePraticaFrame(theController, null, ChefDashboard.this).setVisible(true));
	
	     JButton btnGestisciRicette = new JButton("Gestisci Ricette");
	     btnGestisciRicette.addActionListener(e -> new GestioneRicetteFrame(theController, ChefDashboard.this).setVisible(true));
	
	     panelBottoniSessioni.add(btnSessioneOnline);
	     panelBottoniSessioni.add(btnSessionePratica);
	     panelBottoniSessioni.add(btnGestisciRicette);
	     panelSessioni.add(panelBottoniSessioni, BorderLayout.SOUTH);
	
	     tabbedPane.addTab("Sessioni", panelSessioni);
	     
	     caricaSessioni();
	     
	     JButton btnApriReport = new JButton("Apri Report Grafici");
	     btnApriReport.addActionListener(e -> new ReportFrame(theController));
	     panelBottoniCorsi.add(btnApriReport);
	    
		
		// ==============================
        // TAB 3 - GESTIONE RICETTE
        // ==============================
		
        JPanel panelRicette = new JPanel(new BorderLayout());
        panelRicette.setBackground(new Color(244, 233, 216));

        JLabel lblRicette = new JLabel("Gestione Ricette");
        lblRicette.setForeground(new Color(0, 128, 0));
        lblRicette.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblRicette.setHorizontalAlignment(SwingConstants.CENTER);
        panelRicette.add(lblRicette, BorderLayout.NORTH);

        String[] colonneRicette = {"ID", "Descrizione", "Durata", "Preparazione", "Allergeni"};
        tableRicette = new JTable(new DefaultTableModel(colonneRicette, 0));
        panelRicette.add(new JScrollPane(tableRicette), BorderLayout.CENTER);

        JPanel panelBottoniRicette = new JPanel();
        
        JButton btnAggiungiRicetta = new JButton("Aggiungi Ricetta");
        btnAggiungiRicetta.addActionListener(e -> 
            new AggiungiRicettaFrame(theController, loggedChef, ChefDashboard.this).setVisible(true)
        );

        JButton btnAggiungiIngrediente = new JButton("Aggiungi Ingrediente");
        btnAggiungiIngrediente.addActionListener(e -> {
            int row = tableRicette.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una ricetta");
                return;
            }
            int idRicetta = (int) tableRicette.getValueAt(row, 0);
            new AggiungiIngredienteFrame(theController, idRicetta).setVisible(true);
        });

        JButton btnAssociaSessione = new JButton("Associa a Sessione");
        btnAssociaSessione.addActionListener(e -> {
            int row = tableRicette.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una ricetta");
                return;
            }
            int idRicetta = (int) tableRicette.getValueAt(row, 0);
            new AssociaRicettaSessioneFrame(theController, idRicetta).setVisible(true);
        });

        panelBottoniRicette.add(btnAggiungiRicetta);
        panelBottoniRicette.add(btnAggiungiIngrediente);
        panelBottoniRicette.add(btnAssociaSessione);
        panelRicette.add(panelBottoniRicette, BorderLayout.SOUTH);

        tabbedPane.addTab("Ricette", panelRicette);
        caricaRicette();
		
		// ==============================
        // TAB 4 - NOTIFICHE
        // ==============================
		
		JPanel panelNotifiche = new JPanel(new BorderLayout());
        panelNotifiche.setBackground(new Color(244, 233, 216));

        JLabel lblNotifiche = new JLabel("Notifiche");
        lblNotifiche.setForeground(new Color(0, 128, 0));
        lblNotifiche.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblNotifiche.setHorizontalAlignment(SwingConstants.CENTER);
        panelNotifiche.add(lblNotifiche, BorderLayout.NORTH);

        String[] colonneNotifiche = {"ID", "Testo", "Data"};
        tableNotifiche = new JTable(new DefaultTableModel(colonneNotifiche, 0));
        panelNotifiche.add(new JScrollPane(tableNotifiche), BorderLayout.CENTER);

        JPanel panelBottoniNotifiche = new JPanel();

        JButton btnNuovaNotifica = new JButton("Crea Notifica");
        btnNuovaNotifica.addActionListener(e -> new NotificheFrame(theController, ChefDashboard.this).setVisible(true));

        panelBottoniNotifiche.add(btnNuovaNotifica);
        panelNotifiche.add(panelBottoniNotifiche, BorderLayout.SOUTH);

        tabbedPane.addTab("Notifiche", panelNotifiche);
        caricaNotifiche();
        
        tableNotifiche.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {

                int riga = tableNotifiche.rowAtPoint(evt.getPoint());

                if (riga >= 0) {

                    int idNotifica = (int) tableNotifiche.getValueAt(riga, 0);

                    new NotificaDettaglioFrame(theController, idNotifica);

                }
            }
        });
        
	}
	
	
	// ------------------------------
    // METODI DI CARICAMENTO DATI
    // ------------------------------
	
	private void caricaCorsi() {
	    try {
	        DefaultTableModel model = (DefaultTableModel) tableCorsi.getModel();
	        model.setRowCount(0);

	        for (Corso c : theController.getCorsiChef()) {
	            model.addRow(new Object[]{
	                c.getId(),
	                c.getNome(),
	                c.getArgomento(),
	                c.getDataInizio()
	            });
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	private void caricaSessioni() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableSessioni.getModel();
            model.setRowCount(0);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (SessioneOnline s : theController.getSessioniOnlineChef(loggedChef.getId())) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getCorso().getNome(),
                        "Online",
                        s.getData().format(dtf),
                        s.getDurata() + " min"
                });
            }
            for (SessionePratica s : theController.getSessioniPraticheChef(loggedChef.getId())) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getCorso().getNome(),
                        "Pratica",
                        s.getData().format(dtf),
                        s.getDurata() + " min"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
		
	
	private void caricaRicette() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableRicette.getModel();
            model.setRowCount(0);
            for (Ricetta r : theController.getRicetteChef()) {
                model.addRow(new Object[]{
                        r.getId(),
                        r.getDescrizione(),
                        r.getDurata() + " min",
                        r.getPreparazione(),
                        String.join(", ", r.getAllergeni())
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	private void caricaNotifiche() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableNotifiche.getModel();
            model.setRowCount(0);
            
            for (Notifica n : theController.getNotificheChef(loggedChef.getId())) {
            	String testo = n.getTesto();

                String preview = testo.length() > 40
                        ? testo.substring(0,40) + "..."
                        : testo;
                
                model.addRow(new Object[]{
                        n.getId(),
                        preview,
                        n.getDataCreazione()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void aggiornaTabellaCorsi() {
	    caricaCorsi();
	}
	
	public void aggiornaTabellaSessioni() {
	    caricaSessioni();
	}
	
	public void aggiornaTabellaRicette() {
	    caricaRicette();
	}
	
	public void aggiornaTabellaNotifiche() {
	    caricaNotifiche();
	}
}
