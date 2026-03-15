package gui;

import javax.swing.*;
import java.awt.*;

import controller.Controller;
import entity.Notifica;

public class NotificaDettaglioFrame extends JFrame {

    public NotificaDettaglioFrame(Controller controller, int idNotifica) {

        setTitle("Dettaglio Notifica");
        setResizable(false);
        setSize(500,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(244,233,216));
        contentPane.setLayout(new BorderLayout());

        setContentPane(contentPane);

        JLabel titolo = new JLabel("Notifica");
        titolo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titolo.setForeground(new Color(0,128,0));
        titolo.setHorizontalAlignment(SwingConstants.CENTER);

        contentPane.add(titolo, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setBackground(Color.WHITE);

        try {

            Notifica n = controller.getNotificaById(idNotifica);
            textArea.setText(n.getTesto());

        } catch (Exception e) {
            textArea.setText("Errore nel caricamento della notifica");
        }

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        contentPane.add(scroll, BorderLayout.CENTER);

        JButton btnChiudi = new JButton("Chiudi");
        btnChiudi.addActionListener(e -> dispose());

        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(new Color(244,233,216));
        panelBottom.add(btnChiudi);

        contentPane.add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}