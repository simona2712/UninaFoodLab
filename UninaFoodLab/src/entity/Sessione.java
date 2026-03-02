package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Sessione {

	protected int id;
    protected int durata;
    protected LocalDate data;
    protected LocalTime ora;

    protected Corso corso;

    public Sessione(int id, int durata, LocalDate data, LocalTime ora, Corso corso) {
        this.id = id;
        this.durata = durata;
        this.data = data;
        this.ora = ora;
        this.corso = corso;
    }

    
    public int getId() { 
    	return id; 
    }
    
    public int getDurata() { 
    	return durata; 
    }
    
    public LocalDate getData() { 
    	return data; 
    }
    
    public LocalTime getOra() { 
    	return ora; 
    }
    
    public Corso getCorso() { 
    	return corso; 
    }

    public void setDurata(int durata) { 
    	this.durata = durata; 
    }
    
    public void setData(LocalDate data) { 
    	this.data = data; 
    }
    
    public void setOra(LocalTime ora) { 
    	this.ora = ora; 
    }
    
    public void setCorso(Corso corso) { 
    	this.corso = corso; 
    }
    
    public boolean isFutura() {
        return data.isAfter(LocalDate.now());
    }
    
    @Override
    public String toString() {
        return "[ID: " + id +
               ", Data: " + data +
               ", Ora: " + ora +
               ", Durata: " + durata + " min]";
    }
}
