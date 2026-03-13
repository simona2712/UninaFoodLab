package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import controller.Controller;

public class ReportFrame extends JFrame {

    private Controller controller;
    private JPanel contentPane;

    public ReportFrame(Controller c) {

        controller = c;

        setTitle("Report UninaFoodLab");
        setSize(900,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/img/U_F_L_1.png")).getImage());

        contentPane = new JPanel();
        contentPane.setBackground(new Color(244,233,216));
        contentPane.setBorder(new EmptyBorder(10,10,10,10));
        contentPane.setLayout(new BorderLayout());

        setContentPane(contentPane);

        // =====================
        // TITOLO
        // =====================

        JLabel titolo = new JLabel("Report Statistici Mensili", JLabel.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titolo.setForeground(new Color(0,128,0));

        contentPane.add(titolo, BorderLayout.NORTH);

        // =====================
        // PANEL GRAFICI
        // =====================

        JPanel chartPanel = new JPanel();
        chartPanel.setBackground(new Color(244,233,216));
        chartPanel.setLayout(new GridLayout(1,3,15,15));

        chartPanel.add(createCorsiTotaliChart());
        chartPanel.add(createSessioniChart());
        chartPanel.add(createStatisticheRicetteChart());

        contentPane.add(chartPanel, BorderLayout.CENTER);

        setVisible(true);
    }


    // =========================
    // CORSI TOTALI
    // =========================

    private ChartPanel createCorsiTotaliChart() {

        DefaultPieDataset dataset = new DefaultPieDataset();

        try {

            int totale = controller.getNumeroCorsiTotali();

            dataset.setValue("Corsi Totali", totale);

        } catch(Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Numero totale corsi",
                dataset,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);

        return panel;
    }


    // =========================
    // SESSIONI ONLINE VS PRATICHE
    // =========================

    private ChartPanel createSessioniChart() {

        DefaultPieDataset dataset = new DefaultPieDataset();

        try {

            Map<String,Integer> dati = controller.getNumeroSessioniOnlinePratiche();

            dataset.setValue("Online", dati.get("Online"));
            dataset.setValue("Pratiche", dati.get("Pratiche"));

        } catch(Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Sessioni Online vs Pratiche",
                dataset,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);

        return panel;
    }


    // =========================
    // MEDIA / MAX / MIN RICETTE
    // =========================

    private ChartPanel createStatisticheRicetteChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {

            Map<String,Double> dati = controller.getStatisticheRicetteSessione();

            dataset.addValue(dati.get("Media"), "Ricette", "Media");
            dataset.addValue(dati.get("Max"), "Ricette", "Max");
            dataset.addValue(dati.get("Min"), "Ricette", "Min");

        } catch(Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ricette per Sessione",
                "Statistica",
                "Numero Ricette",
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);

        return panel;
    }

}