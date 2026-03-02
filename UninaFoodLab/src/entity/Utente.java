package entity;

public abstract class Utente {
	protected int id;
    protected String nome;
    protected String cognome;
    protected String numeroTelefono;
    protected String email;
    protected String password;

    public Utente(int id, String nome, String cognome, String numeroTelefono, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = numeroTelefono;
        this.email = email;
        this.password = password;
    }

    public int getId() { 
    	return id; 
    }
    
    public String getNome() { 
    	return nome; 
    }
    
    public String getCognome() { 
    	return cognome; 
    }
    
    public String getEmail() { 
    	return email; 
    }
    
    public String getNumeroTelefono() { 
    	return numeroTelefono; 
    }
    
    public String getFullName() {
        return nome + " " + cognome;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String nuovaPassword) {
        if (nuovaPassword != null && !nuovaPassword.isBlank()) {
            this.password = nuovaPassword;
        }
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public boolean autentica(String passwordInserita) {
    	return password != null && password.equals(passwordInserita);
    }

    @Override
    public String toString() {
    	return getFullName() + " (" + email + ")";
    }


}
