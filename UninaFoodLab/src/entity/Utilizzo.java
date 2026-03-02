package entity;

public class Utilizzo {

	private Ingrediente ingrediente;
    private double quantita;
    private String unitaMisura;

    public Utilizzo(Ingrediente ingrediente, double quantita, String unitaMisura) {
        if (ingrediente == null)
            throw new IllegalArgumentException("Ingrediente non valido");

        if (quantita <= 0)
            throw new IllegalArgumentException("Quantità deve essere > 0");

        this.ingrediente = ingrediente;
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
    }
    
    public double getQuantita() {
    	return quantita;
    }
    
    public String getUnitaMisura() {
    	return unitaMisura;
    }

    public Ingrediente getIngrediente() { 
    	return ingrediente; 
    }
    
    public double calcolaCalorie() {
        return ingrediente.getCalorie() * quantita / 100;
    }
    
    @Override
    public String toString() {
        return ingrediente.getNome() +
               " - " + quantita + " " + unitaMisura;
    }
}
