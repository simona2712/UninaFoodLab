package entity;

import java.util.ArrayList;
import java.util.List;

public class Chef extends Utente{

    private String specializzazione;
    private int anniEsperienza;
    private List<Corso> listacorsi;
    private List<Notifica> listanotifiche;

    public Chef(int id, String nome, String cognome, String numeroTelefono, String email, String password, String specializzazione, int anniEsperienza) {
        super(id, nome, cognome, numeroTelefono, email, password);
        this.specializzazione = specializzazione;
        this.anniEsperienza = anniEsperienza;
        this.listacorsi = new ArrayList<>();
        this.listanotifiche = new ArrayList<>();
    }

    public List<Corso> getCorsi() { 
        return listacorsi; 
    }
    
    public void setCorsi(List<Corso> corsi) { 
    	this.listacorsi = corsi; 
    }

    public String getSpecializzazione() { 
        return specializzazione; 
    }
    
    public void setSpecializzazione(String specializzazione) { 
    	this.specializzazione = specializzazione; 
    }

    public int getAnniEsperienza() { 
        return anniEsperienza; 
    }
    
    public void setAnniEsperienza(int anniEsperienza) { 
    	this.anniEsperienza = anniEsperienza; 
    }

    public void aggiungiCorso(Corso corso) {
        if (!listacorsi.contains(corso)) {
            listacorsi.add(corso);
            corso.setChef(this);
        }
    }

    public void inserisciNotifica(Notifica n) {
        listanotifiche.add(n);
    }

    public void rimuoviCorso(Corso corso) {
        listacorsi.remove(corso);
    }

    public void rimuoviNotifica(Notifica n) {
        listanotifiche.remove(n);
    }
   
    public List<Notifica> getNotifiche() {
        return listanotifiche;
    }
    
    public void setNotifiche(List<Notifica> notifiche) { 
    	this.listanotifiche = notifiche; 
    }

    @Override
    public String toString() {
        return "Chef: " + getFullName() +
               " | Specializzazione: " + specializzazione +
               " | Esperienza: " + anniEsperienza + " anni";
    }
}