package entity;

public class Allergia {

	private int id;
    private String nome;

    public Allergia(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { 
    	return id;
    }
    
    public String getNome() { 
    	return nome; 
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @Override 
    public String toString() { 
    	return "Allergia: "+ nome; 
    }

}
