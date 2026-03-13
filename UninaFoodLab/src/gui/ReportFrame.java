package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

import controller.Controller;

public class ReportFrame extends JFrame {

    private Controller theController;
    private JPanel contentPane;

    public ReportFrame(Controller c) {

        theController = c;

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
        chartPanel.setLayout(new GridLayout(2,2,20,20));

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

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            int totale = theController.getNumeroCorsiTotali();
            dataset.addValue(totale, "Corsi", "Totali");
        } catch(Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Numero totale corsi",
                "",
                "Numero corsi",
                dataset
        );

        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));
        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setOutlineVisible(false);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(76,175,80));
        renderer.setShadowVisible(false);
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),1));

        return panel;
    }


    // =========================
    // SESSIONI ONLINE VS PRATICHE
    // =========================

    private ChartPanel createSessioniChart() {

        DefaultPieDataset dataset = new DefaultPieDataset();

        try {

            Map<String,Integer> dati = theController.getNumeroSessioniOnlinePratiche();

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
        
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));
        chart.setBackgroundPaint(Color.white);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Online", new Color(76,175,80));
        plot.setSectionPaint("Pratiche", new Color(255,167,38));
        plot.setBackgroundPaint(Color.white);
        plot.setOutlineVisible(false);

        plot.setLabelGenerator(
                new StandardPieSectionLabelGenerator("{0}: {1} ({2})")
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);
        panel.setMouseWheelEnabled(true);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),1));

        return panel;
    }


    // =========================
    // MEDIA / MAX / MIN RICETTE
    // =========================

    private ChartPanel createStatisticheRicetteChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {

            Map<String,Double> dati = theController.getStatisticheRicetteSessione();

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
        
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));
        chart.setBackgroundPaint(Color.white);
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setOutlineVisible(false);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(76,175,80));
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setShadowVisible(false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);
        panel.setMouseWheelEnabled(true);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),1));

        return panel;
    }


}