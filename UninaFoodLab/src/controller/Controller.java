package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dao.*;

import entity.*;

import exceptions.*;

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
    
    private Chef loggedChef;
    
    public Controller() {

    }
    
    public static Controller getInstance() {
        if (istanziato == null) {
            istanziato = new Controller();
        }
        return istanziato;
    }
    
    
    //---------------- CHEF ----------------
    public Chef loginChef(String email, String password) 
            throws SQLException, ValidationException, EntityNotFoundException {

        if (email == null || email.isBlank())
            throw new ValidationException("Email obbligatoria");
        if (password == null || password.isBlank())
            throw new ValidationException("Password obbligatoria");

        Chef chef = chefDAO.login(email, password);

        if (chef == null)
            throw new EntityNotFoundException("Credenziali non valide");
        
        loggedChef = chef;

        return chef;
    }
    
    public Chef getLoggedChef() {
        return loggedChef;
    }
    
 // Restituisce tutte le sessioni del chef (sia online che pratiche)
    public List<Object[]> getSessioniChef() throws SQLException {
        List<Object[]> listaSessioni = new ArrayList<>();

        // Sessioni online
        for (SessioneOnline s : sessioneOnlineDAO.findByChef(loggedChef.getId())) {
            listaSessioni.add(new Object[]{
                s.getId(),
                s.getCorso().getNome(),
                "Online",
                s.getData(),
                s.getDurata()
            });
        }

        // Sessioni pratiche
        for (SessionePratica s : sessionePraticaDAO.findByChef(loggedChef.getId())) {
            listaSessioni.add(new Object[]{
                s.getId(),
                s.getCorso().getNome(),
                "Pratica",
                s.getData(),
                s.getDurata()
            });
        }

        return listaSessioni;
    }
    
 // Restituisce sessioni online del chef
    public List<SessioneOnline> getSessioniOnlineChef(int idChef) throws SQLException {
        return sessioneOnlineDAO.findByChef(idChef);
    }

    // Restituisce sessioni pratiche del chef
    public List<SessionePratica> getSessioniPraticheChef(int idChef) throws SQLException {
        return sessionePraticaDAO.findByChef(idChef);
    }

    // Restituisce tutte le ricette dei corsi del chef
    public List<Ricetta> getRicetteChef() throws SQLException {
        List<Ricetta> ricette = new ArrayList<>();
        for (Corso c : getCorsiChef()) {
            ricette.addAll(ricettaDAO.findByCorso(c.getId()));
        }
        return ricette;
    }
    
    public void registraChef(String nome, String cognome, String telefono,
            String email, String password,
            String specializzazione, int anniEsperienza) throws SQLException, ValidationException {
		
		if (nome == null || nome.isBlank())
		throw new ValidationException("Il nome è obbligatorio");
		
		if (email == null || email.isBlank())
		throw new ValidationException("Email obbligatoria");
		
		if (password == null || password.length() < 4)
		    throw new ValidationException("Password troppo corta");
		
		if (anniEsperienza < 0)
		throw new ValidationException("Anni di esperienza non validi");
		
		if (telefono == null || telefono.isBlank())
		    throw new ValidationException("Telefono obbligatorio");
		
		Chef chef = new Chef(0, nome, cognome, telefono, email, password, specializzazione, anniEsperienza);
		
		chefDAO.create(chef);
	}
    
 // ---------------- CORSI ----------------
    public void creaCorso(Corso corso) throws SQLException, ValidationException {
        if (corso == null)
            throw new ValidationException("Corso non valido");
        corsoDAO.create(corso);
    }

    public List<Corso> getTuttiCorsi() throws SQLException {
        return corsoDAO.findAll();
    }

    public List<Corso> filtraCorsiPerArgomento(String argomento) throws SQLException {
        return corsoDAO.findByArgomento(argomento);
    }

    public List<SessioneOnline> getSessioniOnlineCorso(int idCorso) throws SQLException, EntityNotFoundException {
        Corso c = corsoDAO.read(idCorso);
        if (c == null)
            throw new EntityNotFoundException("Corso non trovato");
        return sessioneOnlineDAO.findByCorso(idCorso);
    }
    
    public List<Corso> getCorsiChef() throws SQLException {
        return corsoDAO.findByChef(loggedChef);
    }
    
    

    
 // ---------------- SESSIONI ONLINE ----------------
    public void aggiungiSessioneOnline(int id, int durata, LocalDate data, LocalTime ora, 
                                        String link, Corso corso , int max_partecipanti)
            throws SQLException, ValidationException {

        if (data.isBefore(LocalDate.now()))
            throw new ValidationException("La data non può essere nel passato");

        if (max_partecipanti <= 0)
            throw new ValidationException("Numero massimo partecipanti non valido");

        if (link == null || link.isBlank())
            throw new ValidationException("Link non valido");

        if (corso == null)
            throw new ValidationException("Corso non valido");

        SessioneOnline sessioneOnline = new SessioneOnline(id, durata, data, ora, corso, link, max_partecipanti);
        sessioneOnlineDAO.create(sessioneOnline);
    }

    public List<SessioneOnline> getSessioniOnlineFuture(int idCorso) throws SQLException, EntityNotFoundException {
        Corso c = corsoDAO.read(idCorso);
        if (c == null)
            throw new EntityNotFoundException("Corso non trovato");

        return sessioneOnlineDAO.findFutureByCorso(idCorso);
    }

    public void iscriviASessioneOnline(int idAllievo, int idSessione)
            throws SQLException, EntityNotFoundException, DuplicateEntityException, InvalidOperationException {

        Allievo a = allievoDAO.read(idAllievo);
        if (a == null) throw new EntityNotFoundException("Allievo non trovato");

        SessioneOnline s = sessioneOnlineDAO.read(idSessione);
        if (s == null) throw new EntityNotFoundException("Sessione non trovata");

        if (s.getData().isBefore(LocalDate.now()))
            throw new InvalidOperationException("Sessione già passata");

        // Controllo duplicato
        int count = adesioneDAO.countAdesioniByAllievoAndSessioneOnline(idAllievo, idSessione);
        if (count > 0)
            throw new DuplicateEntityException("Allievo già iscritto alla sessione");

        adesioneDAO.iscriviSessioneOnline(idAllievo, idSessione);
    }
    
    
    
 // ---------------- SESSIONI PRATICHE ----------------
    public void aggiungiSessionePratica(int id, int durata, LocalDate data, LocalTime ora, 
                                        String laboratorio, String utensili, Corso corso , int max_partecipanti)
            throws SQLException, ValidationException {

        if (laboratorio == null || laboratorio.isBlank())
            throw new ValidationException("Laboratorio obbligatorio");

        if (utensili == null || utensili.isBlank())
            throw new ValidationException("Utensili obbligatori");

        if (max_partecipanti <= 0)
            throw new ValidationException("Numero massimo partecipanti non valido");

        if (corso == null)
            throw new ValidationException("Corso non valido");

        SessionePratica sessionePratica = new SessionePratica(id, durata, data, ora, corso, max_partecipanti, utensili, laboratorio);
        sessionePraticaDAO.create(sessionePratica);
    }

    public List<SessionePratica> getSessioniPraticheFuture() throws SQLException {
        return sessionePraticaDAO.findAll().stream()
                .filter(s -> s.getData().isAfter(LocalDate.now()))
                .toList();
    }

    public void associaRicettaASessionePratica(int idRicetta, int idSessione)
            throws SQLException, EntityNotFoundException, InvalidOperationException {

        Ricetta r = ricettaDAO.read(idRicetta);
        if (r == null) throw new EntityNotFoundException("Ricetta non trovata");

        SessionePratica s = sessionePraticaDAO.read(idSessione);
        if (s == null) throw new EntityNotFoundException("Sessione pratica non trovata");

        if (s.getData().isBefore(LocalDate.now()))
            throw new InvalidOperationException("Non puoi modificare sessioni passate");

        ricettaDAO.associaASessionePratica(idRicetta, idSessione);
    }
    
    
    
    
 // ---------------- ISCRIZIONI ----------------
    public void iscriviAllievoACorso(int idAllievo, int idCorso)
            throws SQLException, EntityNotFoundException, DuplicateEntityException {

        Allievo a = allievoDAO.read(idAllievo);
        if (a == null) throw new EntityNotFoundException("Allievo non trovato");

        Corso c = corsoDAO.read(idCorso);
        if (c == null) throw new EntityNotFoundException("Corso non trovato");

        int count = adesioneDAO.countAdesioniByAllievoAndCorso(idAllievo, idCorso);
        if (count > 0)
            throw new DuplicateEntityException("Allievo già iscritto al corso");

        allievoDAO.iscriviACorso(idAllievo, idCorso);
    }

    public int numeroIscrittiCorso(int idCorso) throws SQLException, EntityNotFoundException {
        Corso c = corsoDAO.read(idCorso);
        if (c == null) throw new EntityNotFoundException("Corso non trovato");
        return adesioneDAO.countAdesioniByCorso(idCorso);
    }

    

    // ---------------- NOTIFICHE ----------------
    public void creaNotifica(Notifica n) throws SQLException, ValidationException {
        if (n == null) throw new ValidationException("Notifica non valida");
        notificaDAO.create(n);
    }

    public List<Notifica> getNotificheChef(int idChef) throws SQLException {
        return notificaDAO.findByChef(idChef);
    }
    
    public void inviaNotificaCorso(Corso corso, String testo) throws SQLException, ValidationException {
        if (corso == null) throw new ValidationException("Seleziona un corso");
        if (testo == null || testo.isBlank()) throw new ValidationException("Testo non valido");

        // crea la notifica associata a quel corso
        Notifica n = new Notifica(0, testo, getLoggedChef(), corso);
        creaNotifica(n);
    }

    public void inviaNotificaATutti(String testo) throws SQLException, ValidationException {
        if (testo == null || testo.isBlank()) throw new ValidationException("Testo non valido");

        // notifica generale, corso = null
        Notifica n = new Notifica(0, testo, getLoggedChef(), null);
        creaNotifica(n);
    }
    
    
    
 // ---------------- ALLERGIE ----------------
    public void aggiungiAllergiaAAllievo(int idAllievo, int idAllergia)
            throws SQLException, EntityNotFoundException {

        Allievo a = allievoDAO.read(idAllievo);
        if (a == null) throw new EntityNotFoundException("Allievo non trovato");

        Allergia all = allergiaDAO.read(idAllergia);
        if (all == null) throw new EntityNotFoundException("Allergia non trovata");

        allievoDAO.aggiungiAllergia(idAllievo, idAllergia);
    }
 
    
 // ---------------- RICETTE ----------------
    public List<Ricetta> getRicetteSessione(int idSessione) throws SQLException, EntityNotFoundException {
        SessionePratica s = sessionePraticaDAO.read(idSessione);
        if (s == null) throw new EntityNotFoundException("Sessione non trovata");
        return ricettaDAO.findBySessionePratica(idSessione);
    }

    public void aggiungiIngredienteARicetta(int idRicetta, int idIngrediente, double quantita)
            throws SQLException, EntityNotFoundException, ValidationException {

        if (quantita <= 0)
            throw new ValidationException("Quantità deve essere maggiore di zero");

        Ricetta r = ricettaDAO.read(idRicetta);
        if (r == null) throw new EntityNotFoundException("Ricetta non trovata");

        Ingrediente i = ingredienteDAO.read(idIngrediente);
        if (i == null) throw new EntityNotFoundException("Ingrediente non trovato");

        ricettaDAO.aggiungiIngrediente(idRicetta, idIngrediente, quantita);
    }

    public boolean ricettaCompatibileConAllievo(int idRicetta, int idAllievo)
            throws SQLException, EntityNotFoundException {

        Ricetta r = ricettaDAO.read(idRicetta);
        if (r == null) throw new EntityNotFoundException("Ricetta non trovata");

        Allievo a = allievoDAO.read(idAllievo);
        if (a == null) throw new EntityNotFoundException("Allievo non trovato");

        List<Ingrediente> ingredienti = ingredienteDAO.findByRicetta(idRicetta);
        List<Allergia> allergie = allergiaDAO.findByAllievo(idAllievo);

        for (Ingrediente i : ingredienti) {
            for (Allergia al : allergie) {
                if (i.getNome().equalsIgnoreCase(al.getNome()))
                    return false;
            }
        }
        return true;
    }
    
    
    
 // ---------------- ALLIEVO ---------------- 
    public void registraAllievo(String nome, String cognome, String telefono,
            String email, String password, String livelloAbilita) throws SQLException, ValidationException {

		if (nome == null || nome.isBlank()) 
			throw new ValidationException("Il nome è obbligatorio");
		
		if (email == null || email.isBlank()) 
			throw new ValidationException("L'email è obbligatoria");
		
		if (password == null || password.length() < 4)
			throw new ValidationException("Password troppo corta");
		
		if (telefono == null || telefono.isBlank())
		    throw new ValidationException("Telefono obbligatorio");
		
		Allievo allievo = new Allievo(0, nome, cognome, telefono, email, password, livelloAbilita);
		
		allievoDAO.create(allievo);
	}
    
    // ---------------- INGREDIENTE ----------------
    public int getIngredienteByName(String nome) throws SQLException, EntityNotFoundException {
        List<Ingrediente> lista = ingredienteDAO.findByNome(nome);

        if (lista.isEmpty()) {
            throw new EntityNotFoundException("Ingrediente non trovato: " + nome);
        }

        return lista.get(0).getId();
    }
    
    public int creaIngrediente(String nome, String tipologia, java.time.LocalDate scadenza, double calorie) 
            throws SQLException, ValidationException {

        if (nome == null || nome.isBlank()) 
            throw new ValidationException("Nome ingrediente non valido");
        if (calorie < 0) 
            throw new ValidationException("Calorie non valide");

        Ingrediente i = new Ingrediente(0, nome, tipologia, scadenza, calorie);
        i.setNome(nome);
        i.setTipologiaConservazione(tipologia);
        i.setDataScadenza(scadenza);
        i.setCalorie(calorie);

        ingredienteDAO.create(i);
        return i.getId();
    }
    
    
    
}
