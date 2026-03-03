package entity;

import java.util.ArrayList;
import java.util.List;

public class Allievo extends Utente{

    private String livelloAbilita;
    private List<Allergia> allergie;
    private List<Corso> corsiSeguiti;

    public Allievo(int id, String nome, String cognome, String numeroTelefono, String email, String password, String livelloAbilita) {
        super(id, nome, cognome, numeroTelefono, email, password);
        this.livelloAbilita = livelloAbilita;
        this.allergie = new ArrayList<>();
        this.corsiSeguiti = new ArrayList<>();
    }

    public List<Allergia> getAllergie() { 
        return allergie; 
    }

    public List<Corso> getCorsiSeguiti() { 
        return corsiSeguiti; 
    }
    
    public String getLivelloAbilita() {
    	return livelloAbilita;
    }
    
    public void setLivelloAbilita(String livelloAbilita) { 
    	this.livelloAbilita = livelloAbilita; 
    }

    public void iscriviACorso(Corso corso) {
        corsiSeguiti.add(corso);
    }

    public void aggiungiAllergia(Allergia a) {
        if (!allergie.contains(a)) {
            allergie.add(a);
        }
    }

    public boolean haAllergia(Allergia a) {
        return allergie.contains(a);
    }


    public boolean haAllergia(String nome) {
        for (Allergia a : allergie) {
            if (a.getNome().equalsIgnoreCase(nome)) return true;
        }
        return false;
    }
   

    @Override
    public String toString() {
        return "Allievo: " + getFullName() +
               " | Livello: " + livelloAbilita +
               " | Allergie: " + allergie.size();
    }

}
