package entity;

import java.time.LocalDate;

public class Adesione {

	private int id;
    private boolean presenza;
    private LocalDate dataAdesione;
    private Allievo allievo;
    private SessionePratica sessione;

    public Adesione(int id, Allievo allievo, SessionePratica sessione) {
        if (allievo == null || sessione == null)
            throw new IllegalArgumentException("Allievo o Sessione non validi");
        
        if (sessione.isPiena())
            throw new IllegalStateException("Sessione piena");

        this.id = id;
        this.allievo = allievo;
        this.sessione = sessione;
        this.presenza = false;
        this.dataAdesione = LocalDate.now();
    }
    
    public int getId() { 
    	return id; 
    }
    
    public void setId(int id) {
    	this.id=id;
    }

    public boolean isPresenza() { 
    	return presenza;
    }
    
    public void setPresenza(boolean presenza) { 
    	this.presenza = presenza; 
    }

    public LocalDate getDataAdesione() { 
    	return dataAdesione; 
    }
    
    public void setDataAdesione(LocalDate dataAdesione) {
    	this.dataAdesione=dataAdesione;
    }

    public Allievo getAllievo() { 
    	return allievo; 
    }
    
    public void setAllievo(Allievo allievo) { 
    	this.allievo = allievo; 
    }

    public SessionePratica getSessione() { 
    	return sessione; 
    }
    
    public void setSessione(SessionePratica sessione) { 
    	this.sessione = sessione; 
    }

    public void confermaPresenza() { 
    	this.presenza = true; 
    }
    
    public void annullaPresenza() {
        this.presenza = false;
    }
    
    public boolean appartieneA(Allievo a) {
        return this.allievo.equals(a);
    }
    
    public boolean riguardaSessione(SessionePratica s) {
        return this.sessione.equals(s);
    }
    
    @Override
    public String toString() {
        return "Adesione ID: " + id +
               " | Allievo: " + allievo.getFullName() +
               " | Sessione: " + sessione.getId() +
               " | Presenza: " + (presenza ? "Confermata" : "Non confermata");
    }
}
