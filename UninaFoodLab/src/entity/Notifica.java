package entity;

import java.time.LocalDate;

public class Notifica {

    private int id;
    private String testo;
    private LocalDate dataCreazione;
    private Chef chef;
    private Corso corso; // può essere null

    public Notifica(int id, String testo, Chef chef, Corso corso) {
        if (testo == null || testo.isBlank())
            throw new IllegalArgumentException("Testo non valido");

        if (chef == null)
            throw new IllegalArgumentException("Chef non valido");

        this.id = id;
        this.testo = testo;
        this.chef = chef;
        this.corso = corso;
        this.dataCreazione = LocalDate.now();
    }

    public int getId() { 
        return id; 
    }

    public String getTesto() {
        return testo; 
    }

    public LocalDate getDataCreazione() { 
        return dataCreazione; 
    }

    public Chef getChef() { 
        return chef; 
    }

    public Corso getCorso() { 
        return corso; 
    }

    public boolean isGenerale() { 
        return corso == null; 
    }

    public boolean riguardaCorso(Corso c) {
        return corso != null && corso.equals(c);
    }

    @Override
    public String toString() {
        String prefisso = isGenerale() ? "[GENERALE]" : "[" + corso.getNome() + "]";
        return prefisso + " " + testo + " | Data: " + dataCreazione;
    }
}