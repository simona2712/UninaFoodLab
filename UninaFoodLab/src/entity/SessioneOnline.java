package entity;

public class SessioneOnline extends Sessione{

	private String linkRiunione;
    private int maxPartecipanti;

    public SessioneOnline(int id, int durata, java.time.LocalDate data, java.time.LocalTime ora, Corso corso, String linkRiunione, int maxPartecipanti) {
        super(id, durata, data, ora, corso);
        this.linkRiunione = linkRiunione;
        this.maxPartecipanti = maxPartecipanti;
    }

    public String getLinkRiunione() {
        return linkRiunione;
    }

    public void setLinkRiunione(String linkRiunione) {
        this.linkRiunione = linkRiunione;
    }

    public int getMaxPartecipanti() {
        return maxPartecipanti;
    }

    public void setMaxPartecipanti(int maxPartecipanti) {
        this.maxPartecipanti = maxPartecipanti;
    }
    
    public boolean linkValido() {
        return linkRiunione != null && linkRiunione.startsWith("http");
    }
    
    @Override
    public String toString() {
        return "Sessione Online | " +
               super.toString() +
               " | Max partecipanti: " + maxPartecipanti;
    }
}
