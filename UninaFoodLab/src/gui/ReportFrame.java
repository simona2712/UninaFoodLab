package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import controller.Controller;

public class ReportFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblCorsi;
	private JLabel lblOnline;
	private JLabel lblPratiche;
	private JLabel lblMedia;
	
	private Controller theController;


	/**
	 * Create the frame.
	 */
	public ReportFrame(Controller c) {
		
		theController = c;
		
		setTitle("Report Statistiche");
		setBounds(100,100,650,450);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(244,233,216));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel titolo = new JLabel("Report piattaforma");
		titolo.setForeground(new Color(0, 128, 0));
		titolo.setFont(new Font("SansSerif",Font.BOLD,20));
		titolo.setBounds(230,20,250,30);
		contentPane.add(titolo);

		lblCorsi = new JLabel("Numero corsi:");
		lblCorsi.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCorsi.setBounds(80,100,200,25);
		contentPane.add(lblCorsi);

		lblOnline = new JLabel("Sessioni online:");
		lblOnline.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblOnline.setBounds(80,140,200,25);
		contentPane.add(lblOnline);

		lblPratiche = new JLabel("Sessioni pratiche:");
		lblPratiche.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblPratiche.setBounds(80,180,200,25);
		contentPane.add(lblPratiche);

		lblMedia = new JLabel("Media ricette:");
		lblMedia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblMedia.setBounds(80,220,200,25);
		contentPane.add(lblMedia);

		JPanel chartPanel = new JPanel();
		chartPanel.setBounds(300,100,300,250);
		chartPanel.setBackground(Color.WHITE);
		contentPane.add(chartPanel);

		setVisible(true);
	}

}
