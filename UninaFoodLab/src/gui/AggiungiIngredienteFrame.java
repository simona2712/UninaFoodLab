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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entity.Ingrediente;
import exceptions.EntityNotFoundException;

public class AggiungiIngredienteFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controller theController;
	private JTextField textNome;
	
	private int idRicetta;


	/**
	 * Create the frame.
	 */
	public AggiungiIngredienteFrame(Controller c, int idRicetta) {
		
		theController = c;
		this.idRicetta= idRicetta;
		
		setResizable(false);
		setTitle("Aggiungi Ingrediente alla Ricetta");
		setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());
		setBounds(100, 100, 715, 388);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 233, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titolo = new JLabel("Aggiungi ingrediente alla Ricetta");
		titolo.setForeground(new Color(0, 128, 0));
		titolo.setFont(new Font("SansSerif",Font.BOLD,20));
		titolo.setBounds(169,33,329,30);
		contentPane.add(titolo);
		
		JLabel lblIngrediente = new JLabel("Nome");
		lblIngrediente.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblIngrediente.setBounds(30, 108, 74, 25);
        contentPane.add(lblIngrediente);


        JLabel lblQuantita = new JLabel("Quantità");
        lblQuantita.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblQuantita.setBounds(30, 143, 100, 25);
        contentPane.add(lblQuantita);

        JTextField txtQuantita = new JTextField();
        txtQuantita.setBounds(180, 145, 100, 25);
        contentPane.add(txtQuantita);

        JLabel lblUnita = new JLabel("Unità misura");
        lblUnita.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUnita.setBounds(30, 178, 100, 25);
        contentPane.add(lblUnita);

        JTextField txtUnita = new JTextField();
        txtUnita.setBounds(180, 180, 100, 25);
        contentPane.add(txtUnita);
        
        JLabel lblCalorie = new JLabel("Calorie");
        lblCalorie.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCalorie.setBounds(30, 213, 100, 25);
        contentPane.add(lblCalorie);

        JTextField textCalorie = new JTextField();
        textCalorie.setBounds(180, 215, 100, 25);
        contentPane.add(textCalorie);

        JButton btnAggiungi = new JButton("Aggiungi");
        btnAggiungi.setBackground(new Color(253, 171, 117));
        btnAggiungi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnAggiungi.setBounds(218, 264, 120, 30);
        contentPane.add(btnAggiungi);
        
        textNome = new JTextField();
        textNome.setBounds(180, 110, 100, 25);
        contentPane.add(textNome);

        btnAggiungi.addActionListener(e -> {
            try {
                String nomeIngrediente = textNome.getText().trim();
                if (nomeIngrediente.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Inserisci il nome dell'ingrediente");
                    return;
                }

                double quantita;
                try {
                    quantita = Double.parseDouble(txtQuantita.getText());
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantità non valida");
                    return;
                }

                double calorie;
                try {
                    calorie = Double.parseDouble(textCalorie.getText());
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Calorie non valide");
                    return;
                }

                int idIngrediente;
                try {
                    idIngrediente = theController.getIngredienteByName(nomeIngrediente);
                } catch(EntityNotFoundException ex) {
                    idIngrediente = theController.creaIngrediente(nomeIngrediente, "", java.time.LocalDate.now().plusYears(1), calorie);
                }

                theController.aggiungiIngredienteARicetta(idRicetta, idIngrediente, quantita);
                JOptionPane.showMessageDialog(this, "Ingrediente aggiunto correttamente!");
                dispose();

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        });
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/U_F_L_1.png"));

        Image img = icon.getImage();
        Image imgRidimensionata = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        ImageIcon iconRidimensionata = new ImageIcon(imgRidimensionata);

        JLabel label = new JLabel(iconRidimensionata);
        label.setBounds(501, 92, 131, 139);

        getContentPane().add(label);

        setVisible(true);
		
		
	}
}
