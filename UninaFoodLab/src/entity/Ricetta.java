package entity;

import java.util.ArrayList;
import java.util.List;

public class Ricetta {

	private int id;
    private int durata;
    private String descrizione;
    private String preparazione;

    private List<Utilizzo> ingredienti;
    private List<String> allergeni;

    public Ricetta(int id, int durata, String descrizione, String preparazione) {
        this.id = id;
        this.durata = durata;
        this.descrizione = descrizione;
        this.preparazione = preparazione;
        this.ingredienti = new ArrayList<>();
        this.allergeni = new ArrayList<>();
    }
    
    public int getId() { 
    	return id; 
    }
    
    public void setId(int id) {
    	this.id=id;
    }

    public int getDurata() { 
    	return durata; 
    }
    
    public void setDurata(int durata) {
    	this.durata=durata;
    }

    public String getDescrizione() { 
    	return descrizione; 
    }
    
    public void setDescrizione(String descrizione) {
    	this.descrizione=descrizione;
    }

    public String getPreparazione() { 
    	return preparazione; 
    }
    
    public void setPreparazione(String preparazione) {
    	this.preparazione=preparazione;
    }

    
    public List<String> getAllergeni() {
        return allergeni;
    }
    
    public void setAllergeni(List<String> allergeni) {
    	this.allergeni=allergeni;
    }
    
    public List<Utilizzo> getIngredienti() { 
    	return ingredienti; 
    }
    
    public void setIngredienti(List<Utilizzo> ingredienti) {
    	this.ingredienti=ingredienti;
    }

    
    public void aggiungiIngrediente(Utilizzo u) {
        ingredienti.add(u);
    }

    public void aggiungiAllergene(String a) {
        if (!allergeni.contains(a.toLowerCase())) {
            allergeni.add(a.toLowerCase());
        }
    }
    
    public void rimuoviIngrediente(Utilizzo u) {
        ingredienti.remove(u);
    }

    public void rimuoviAllergene(String a) {
        allergeni.remove(a.toLowerCase());
    }
    
    public int getNumeroIngredienti() {
        return ingredienti.size();
    }

    public boolean contieneAllergene(String allergene) {
        return allergeni.contains(allergene.toLowerCase());
    }

    public boolean compatibileCon(Allievo a) {
        for (String allergene : allergeni) {
            for (Allergia allergiaAllievo : a.getAllergie()) {
                if (allergiaAllievo.getNome().equalsIgnoreCase(allergene)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public double calcolaCalorieTotali() {
        double totale = 0;
        for (Utilizzo u : ingredienti) {
            totale += u.calcolaCalorie();
        }
        return totale;
    }
    
    public boolean contieneAllergeniDi(Allievo a) {
        for (Allergia allergia : a.getAllergie()) {
            if (contieneAllergene(allergia.getNome())) {
                return true;
            }
        }
        return false;
    }
   
    
    public double getQuantitaTotale() {
        double totale = 0;
        for (Utilizzo u : ingredienti) {
            totale += u.getQuantita();
        }
        return totale;
    }

    @Override
    public String toString() {
        return "Ricetta: " + descrizione +
               " | Durata: " + durata + " min" +
               " | Calorie: " + calcolaCalorieTotali() + " kcal" +
               " | Ingredienti: " + ingredienti.size();
    }

}