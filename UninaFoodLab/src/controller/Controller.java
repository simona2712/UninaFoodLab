package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import dao.*;

import entity.*;

public class Controller {
	
	private static Controller istanziato = null;
	private ChefDAO chefDAO = new ChefImpl();
    private AllievoDAO allievoDAO = new AllievoImpl();
    private CorsoDAO corsoDAO = new CorsoImpl();
    private SessioneOnlineDAO sessioneOnlineDAO = new SessioneOnlineImpl();
    private SessionePraticaDAO sessionePraticaDAO = new SessionePraticaImpl();
    private RicettaDAO ricettaDAO = new RicettaImpl();
    private NotificaDAO notificaDAO = new NotificaImpl();
    private IngredienteDAO ingredienteDAO = new IngredienteImpl();
    private AllergiaDAO allergiaDAO = new AllergiaImpl();
    private AdesioneDAO adesioneDAO = new AdesioneImpl();
    
    public Controller() {

    }
    
    public static Controller getInstance() {
        if (istanziato == null) {
            istanziato = new Controller();
        }
        return istanziato;
    }
    
    public Chef loginChef(String email, String password) throws SQLException {

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email obbligatoria");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password obbligatoria");

        Chef chef = chefDAO.login(email, password);

        if (chef == null)
            throw new IllegalArgumentException("Credenziali non valide");

        return chef;
    }
    
    public void creaCorso(Corso corso) throws SQLException {
        corsoDAO.create(corso);
    }
    
    public List<Corso> filtraCorsiPerArgomento(String argomento) throws SQLException {
        return corsoDAO.findByArgomento(argomento);
    }
    
    public void creaNotifica(Notifica n) throws SQLException {
        notificaDAO.create(n);
    }

    public List<Notifica> getNotificheChef(int idChef) throws SQLException {
        return notificaDAO.findByChef(idChef);
    }
    
    public void aggiungiSessioneOnline(int id, int durata, LocalDate data, LocalTime ora, 
    		String link, Corso corso , int max_partecipanti) throws SQLException {
    	
    	if (data.isBefore(LocalDate.now()))
    	    throw new IllegalArgumentException("La data non può essere nel passato");

    	if (max_partecipanti <= 0)
    	    throw new IllegalArgumentException("Numero massimo partecipanti non valido");

    	if (link == null || link.isBlank())
    	    throw new IllegalArgumentException("Link non valido");
    	
        if (corso == null)
            throw new IllegalArgumentException("Corso non valido");

        SessioneOnline sessioneOnline = new SessioneOnline(id, durata, data, ora, corso, link, max_partecipanti);
        sessioneOnlineDAO.create(sessioneOnline);
    }
    
    public void aggiungiSessionePratica(int id, int durata, LocalDate data, LocalTime ora, 
    		String laboratorio, String utensili, Corso corso , int max_partecipanti) throws SQLException {
    	
    	if (laboratorio == null || laboratorio.isBlank())
    	    throw new IllegalArgumentException("Laboratorio obbligatorio");

    	if (utensili == null || utensili.isBlank())
    	    throw new IllegalArgumentException("Utensili obbligatori");

    	if (max_partecipanti <= 0)
    	    throw new IllegalArgumentException("Numero massimo partecipanti non valido");
    	
        if (corso == null)
            throw new IllegalArgumentException("Corso non valido");

        SessionePratica sessionePratica = new SessionePratica(id, durata, data, ora, corso, max_partecipanti, utensili, laboratorio);
        sessionePraticaDAO.create(sessionePratica);
    }
    
    public void iscriviAllievoACorso(int idAllievo, int idCorso) throws SQLException {

        Object a = allievoDAO.read(idAllievo);
        Object c = corsoDAO.read(idCorso);

        if (a == null)
            throw new IllegalArgumentException("Allievo non esistente");

        if (c == null)
            throw new IllegalArgumentException("Corso non esistente");

        allievoDAO.iscriviACorso(idAllievo, idCorso);
    }
    
    public void associaRicettaASessionePratica(int idRicetta, int idSessione)
            throws SQLException {

        Object r = ricettaDAO.read(idRicetta);
        Object s = sessionePraticaDAO.read(idSessione);

        if (r == null)
            throw new IllegalArgumentException("Ricetta non trovata");

        if (s == null)
            throw new IllegalArgumentException("Sessione pratica non trovata");

        if (s.getData().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Non puoi modificare sessioni passate");

        ricettaDAO.associaASessionePratica(idRicetta, idSessione);
    }
    
    public void aggiungiAllergiaAAllievo(int idAllievo, int idAllergia)
            throws SQLException {

        Object a = allievoDAO.read(idAllievo);
        Object all = allergiaDAO.read(idAllergia);

        if (a == null)
            throw new IllegalArgumentException("Allievo non trovato");

        if (all == null)
            throw new IllegalArgumentException("Allergia non trovata");

        allievoDAO.aggiungiAllergia(idAllievo, idAllergia);
    }
    
    public int numeroIscrittiCorso(int idCorso) throws SQLException {

        Object c = corsoDAO.read(idCorso);
        if (c == null)
            throw new IllegalArgumentException("Corso non trovato");

        return adesioneDAO.countByCorso(idCorso);
    }
    
    public List<SessionePratica> getSessioniPraticheFuture(int idCorso)
            throws SQLException {

        return sessionePraticaDAO.findFutureByCorso(idCorso);
    }

}
