package entity;

import java.util.ArrayList;
import java.util.List;

public class Chef extends Utente{

    private String specializzazione;
    private int anniEsperienza;
    private List<Corso> corsi;
    private List<Notifica> notifiche;

    public Chef(int id, String nome, String cognome, String numeroTelefono, String email, String password, String specializzazione, int anniEsperienza) {
        super(id, nome, cognome, numeroTelefono, email, password);
        this.specializzazione = specializzazione;
        this.anniEsperienza = anniEsperienza;
        this.corsi = new ArrayList<>();
        this.notifiche = new ArrayList<>();
    }

    public List<Corso> getCorsi() { 
        return corsi; 
    }

    public String getSpecializzazione() { 
        return specializzazione; 
    }

    public int getAnniEsperienza() { 
        return anniEsperienza; 
    }

    public void aggiungiCorso(Corso corso) {
        if (!corsi.contains(corso)) {
            corsi.add(corso);
            corso.setChef(this);
        }
    }

    public void inserisciNotifica(Notifica n) {
        notifiche.add(n);
    }

    public void rimuoviCorso(Corso corso) {
        corsi.remove(corso);
    }

    public void rimuoviNotifica(Notifica n) {
        notifiche.remove(n);
    }

    public List<Notifica> getNotifiche() {
        return notifiche;
    }

    @Override
    public String toString() {
        return "Chef: " + getFullName() +
               " | Specializzazione: " + specializzazione +
               " | Esperienza: " + anniEsperienza + " anni";
    }
}