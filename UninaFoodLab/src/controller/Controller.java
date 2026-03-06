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

        SessionePratica sessionePratica = new SessionePratica(id, durata, data, ora, corso, max_partecipanti, laboratorio, utensili);
        sessionePraticaDAO.create(sessionePratica);
    }
    
    public void iscriviAllievoACorso(int idAllievo, int idCorso) throws SQLException {

        Allievo a = allievoDAO.read(idAllievo);
        Corso c = corsoDAO.read(idCorso);

        if (a == null)
            throw new IllegalArgumentException("Allievo non esistente");

        if (c == null)
            throw new IllegalArgumentException("Corso non esistente");

        allievoDAO.iscriviACorso(idAllievo, idCorso);
    }
    
    public void associaRicettaASessionePratica(int idRicetta, int idSessione)
            throws SQLException {

        Ricetta r = ricettaDAO.read(idRicetta);
        SessionePratica s = sessionePraticaDAO.read(idSessione);

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

        Allievo a = allievoDAO.read(idAllievo);
        Allergia all = allergiaDAO.read(idAllergia);

        if (a == null)
            throw new IllegalArgumentException("Allievo non trovato");

        if (all == null)
            throw new IllegalArgumentException("Allergia non trovata");

        allievoDAO.aggiungiAllergia(idAllievo, idAllergia);
    }
    
    public int numeroIscrittiCorso(int idCorso) throws SQLException {

        Corso c = corsoDAO.read(idCorso);
        if (c == null)
            throw new IllegalArgumentException("Corso non trovato");

        return adesioneDAO.countAdesioniByCorso(idCorso);
    }
    
    public List<SessionePratica> getSessioniPraticheFuture(int idCorso)
            throws SQLException {

        return sessionePraticaDAO.findFutureByCorso(idCorso);
    }
    
    public List<Corso> getTuttiCorsi() throws SQLException {
        return corsoDAO.findAll();
    }
    
    public List<SessioneOnline> getSessioniOnlineCorso(int idCorso) throws SQLException {

        Corso c = corsoDAO.read(idCorso);

        if (c == null)
            throw new IllegalArgumentException("Corso non trovato");

        return sessioneOnlineDAO.findByCorso(idCorso);
    }
    
    public void iscriviASessioneOnline(int idAllievo, int idSessione) throws SQLException {

        Allievo a = allievoDAO.read(idAllievo);
        SessioneOnline s = sessioneOnlineDAO.read(idSessione);

        if (a == null)
            throw new IllegalArgumentException("Allievo non trovato");

        if (s == null)
            throw new IllegalArgumentException("Sessione non trovata");

        if (s.getData().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Sessione già passata");

        adesioneDAO.iscriviSessioneOnline(idAllievo, idSessione);
    }
    
    public List<Ricetta> getRicetteSessione(int idSessione) throws SQLException {

        SessionePratica s = sessionePraticaDAO.read(idSessione);

        if (s == null)
            throw new IllegalArgumentException("Sessione non trovata");

        return ricettaDAO.findBySessionePratica(idSessione);
    }
    
    
    public boolean ricettaCompatibileConAllievo(int idRicetta, int idAllievo)
            throws SQLException {

        List<Ingrediente> ingredienti = ingredienteDAO.findByRicetta(idRicetta);
        List<Allergia> allergie = allergiaDAO.findByAllievo(idAllievo);

        for (Ingrediente i : ingredienti) {
            for (Allergia a : allergie) {
                if (i.getNome().equalsIgnoreCase(a.getNome()))
                    return false;
            }
        }

        return true;
    }
    
    public List<SessioneOnline> getSessioniOnlineFuture(int idCorso)
            throws SQLException {

        return sessioneOnlineDAO.findFutureByCorso(idCorso);
    }
    
    public void aggiungiIngredienteARicetta(int idRicetta, int idIngrediente, double quantita)
            throws SQLException {

        Ricetta r = ricettaDAO.read(idRicetta);
        Ingrediente i = ingredienteDAO.read(idIngrediente);

        if (r == null)
            throw new IllegalArgumentException("Ricetta non trovata");

        if (i == null)
            throw new IllegalArgumentException("Ingrediente non trovato");

        ricettaDAO.aggiungiIngrediente(idRicetta, idIngrediente, quantita);
    }

}
