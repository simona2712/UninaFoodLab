package entity;

import java.util.ArrayList;
import java.util.List;

public class SessionePratica extends Sessione{

	private String stato;
    private String laboratorio;
    private String utensili;
    private int maxPartecipanti;
    private List<Ricetta> ricette;
    private List<Adesione> adesioni;

    public SessionePratica(int id, int durata, java.time.LocalDate data, java.time.LocalTime ora, Corso corso, int maxPartecipanti, String laboratorio, String utensili) {
        super(id, durata, data, ora, corso);
        this.maxPartecipanti = maxPartecipanti;
        this.laboratorio = laboratorio;
        this.utensili = utensili;
        this.ricette = new ArrayList<>();
        this.adesioni = new ArrayList<>();
    }
    
    public List<Ricetta> getRicette() {
        return ricette;
    }

    public List<Adesione> getAdesioni() {
        return adesioni;
    }

    public int getMaxPartecipanti() {
        return maxPartecipanti;
    }
    
    public void setMaxPartecipanti(int maxPartecipanti) { 
    	this.maxPartecipanti = maxPartecipanti; 
    }

    public String getLaboratorio() {
        return laboratorio;
    }
    
    public void setLaboratorio(String laboratorio) { 
    	this.laboratorio = laboratorio; 
    }

    public String getUtensili() {
        return utensili;
    }
    
    public void setUtensili(String utensili) { 
    	this.utensili = utensili; 
    }
    
    public String getStato() {
        return stato;
    }
    
    public void setStato(String stato) { 
    	this.stato = stato; 
    }
    
    public boolean isPiena() {
        return adesioni.size() >= maxPartecipanti;
    }
    
    public int getNumeroPartecipanti() {
        return adesioni.size();
    }

    public void aggiungiRicetta(Ricetta r) {
        ricette.add(r);
    }

    public boolean aggiungiAdesione(Adesione a) {
        if (!isPiena() && !adesioni.contains(a)) {
            adesioni.add(a);
            return true;
        }
        return false;
    }
    
    public boolean rimuoviAdesione(Adesione a) {
        return adesioni.remove(a);
    }
    
    public int getPartecipantiPresenti() {
        int presenti = 0;
        for (Adesione a : adesioni) {
            if (a.isPresenza()) presenti++;
        }
        return presenti;
    }
    
    public int getNumeroRicette() {
        return ricette.size();
    }
    
    @Override
    public String toString() {
        return "Sessione Pratica | " +
               super.toString() +
               " | Partecipanti: " + adesioni.size() + "/" + maxPartecipanti +
               " | Laboratorio: " + laboratorio;
    }

}
