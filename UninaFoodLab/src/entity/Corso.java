package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Corso {
	private int id;
    private String nome;
    private String argomento;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String frequenza;
    private Chef chef;
    private List<Sessione> sessioni;

    public Corso(int id, String nome, String argomento, LocalDate dataInizio, LocalDate dataFine, String frequenza, Chef chef) {
    	if (dataFine.isBefore(dataInizio))
    	    throw new IllegalArgumentException("Date non valide");
    	
    	this.id = id;
        this.nome = nome;
        this.argomento = argomento;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.frequenza = frequenza;
        this.chef = chef;
        this.sessioni = new ArrayList<>();
    }

    
    public void aggiungiSessione(Sessione s) {
        sessioni.add(s);
    }
    
    public void rimuoviSessione(Sessione s) {
        sessioni.remove(s);
    }

    public List<Sessione> getSessioni() { 
    	return sessioni; 
    }
    
    public List<SessionePratica> getSessioniPratiche() {
        List<SessionePratica> pratiche = new ArrayList<>();
        for (Sessione s : sessioni) {
            if (s instanceof SessionePratica) {
                pratiche.add((SessionePratica) s);
            }
        }
        return pratiche;
    }

    public List<SessioneOnline> getSessioniOnline() {
        List<SessioneOnline> online = new ArrayList<>();
        for (Sessione s : sessioni) {
            if (s instanceof SessioneOnline) {
                online.add((SessioneOnline) s);
            }
        }
        return online;
    }
    
    public int getNumeroSessioni() {
        return sessioni.size();
    }
    
    public int getNumeroSessioniPratiche() {
        return getSessioniPratiche().size();
    }

    public int getNumeroSessioniOnline() {
        return getSessioniOnline().size();
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
    
    public String getArgomento() { 
    	return argomento; 
    }
    
    public LocalDate getDataInizio() { 
    	return dataInizio; 
    }
    
    public LocalDate getDataFine() { 
    	return dataFine; 
    }
    
    public String getFrequenza() { 
    	return frequenza; 
    }
    
    public Chef getChef() { 
    	return chef; 
    }
    
   
    public void setChef(Chef chef) {
        this.chef = chef;
    }
    
    public void setNome(String nome) { this.nome = nome; }
    public void setArgomento(String argomento) { this.argomento = argomento; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
    public void setFrequenza(String frequenza) { this.frequenza = frequenza; }
    public void setSessioni(List<Sessione> sessioni) { this.sessioni = sessioni; }
    
    public boolean isInCorso() {
        LocalDate oggi = LocalDate.now();
        return !oggi.isBefore(dataInizio) && !oggi.isAfter(dataFine);
    }
    
    @Override
    public String toString() {
        return "Corso: " + nome +
               " | Argomento: " + argomento +
               " | Periodo: " + dataInizio + " - " + dataFine +
               " | Chef: " + (chef != null ? chef.getFullName() : "N/D");
    }
}