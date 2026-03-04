package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ingrediente {

    private int id;
    private String nome;
    private String tipologiaConservazione;
    private LocalDate dataScadenza;
    private double calorie;

    public Ingrediente(int id, String nome, String tipologiaConservazione, LocalDate dataScadenza, double calorie) {
        this.id = id;
        this.nome = nome;
        this.tipologiaConservazione = tipologiaConservazione;
        this.dataScadenza = dataScadenza;
        this.calorie = calorie;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        if (calorie >= 0)
            this.calorie = calorie;
    }

    public int getId() { 
        return id; 
    }
    
    public void setId(int id) {
    	this.id=id;
    }

    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) {
    	this.nome=nome;
    }
    
    public String getTipologiaConservazione() { 
        return tipologiaConservazione; 
    }
    
    public void setTipologiaConservazione(String tipologia) { 
    	this.tipologiaConservazione = tipologia; 
    }

    public LocalDate getDataScadenza() { 
        return dataScadenza; 
    }


    public long giorniAllaScadenza() {
        return ChronoUnit.DAYS.between(LocalDate.now(), dataScadenza);
    }

    public boolean isScaduto() {
        return LocalDate.now().isAfter(dataScadenza);
    }

    public boolean staPerScadere(int giorni) {
        return !isScaduto() && giorniAllaScadenza() <= giorni;
    }

    public void setDataScadenza(LocalDate nuovaData) {
        this.dataScadenza = nuovaData;
    }

    @Override
    public String toString() {
        return nome +
               " | Scadenza: " + dataScadenza +
               " | Calorie: " + calorie + " kcal/100g";
    }
}
