package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.*;

import entity.*;

import exceptions.*;

public class Controller {
	
	private ChefDAO chefDAO = new ChefImpl();
    private AllievoDAO allievoDAO = new AllievoImpl();
    private CorsoDAO corsoDAO = new CorsoImpl();
    private SessioneOnlineDAO sessioneOnlineDAO = new SessioneOnlineImpl();
    private SessionePraticaDAO sessionePraticaDAO = new SessionePraticaImpl();
    private RicettaDAO ricettaDAO = new RicettaImpl();
    private NotificaDAO notificaDAO = new NotificaImpl();
    private IngredienteDAO ingredienteDAO = new IngredienteImpl();
    private AdesioneDAO adesioneDAO = new AdesioneImpl();
    
    private Chef loggedChef;
    
    /* Costruttore del Controller. Inizializza il controller principale dell'applicazione. */
    public Controller() {

    }
   
    
    //---------------- CHEF ----------------
    
    /* Effettua il login di un chef tramite email e password. Salva lo chef autenticato come utente corrente. */
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
    
    /* Restituisce lo chef attualmente loggato nel sistema. */
    public Chef getLoggedChef() {
        return loggedChef;
    }
    
    /* Restituisce tutte le sessioni (online e pratiche) dello chef loggato, formattate per essere visualizzate nella GUI. */
    public List<Object[]> getSessioniChef() throws SQLException {
        List<Object[]> listaSessioni = new ArrayList<>();

        for (SessioneOnline s : sessioneOnlineDAO.findByChef(loggedChef.getId())) {
            listaSessioni.add(new Object[]{
                s.getId(),
                s.getCorso().getNome(),
                "Online",
                s.getData(),
                s.getDurata()
            });
        }

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
    
    /* Restituisce tutte le sessioni online associate a uno specifico chef. */
    public List<SessioneOnline> getSessioniOnlineChef(int idChef) throws SQLException {
        return sessioneOnlineDAO.findByChef(idChef);
    }

    /* Restituisce tutte le sessioni pratiche associate a uno specifico chef. */
    public List<SessionePratica> getSessioniPraticheChef(int idChef) throws SQLException {
        return sessionePraticaDAO.findByChef(idChef);
    }

    /* Restituisce tutte le ricette presenti nel sistema. */
    public List<Ricetta> getRicetteChef() throws SQLException {
        return ricettaDAO.findAll();
    }
    
    /* Registra un nuovo chef nel sistema dopo aver validato i dati inseriti. */
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
    
    /* Crea un nuovo corso. */
    public void creaCorso(Corso corso) throws SQLException, ValidationException {
        if (corso == null)
            throw new ValidationException("Corso non valido");
        corsoDAO.create(corso);
    }

    /* Restituisce i corsi filtrati per argomento. */
    public List<Corso> filtraCorsiPerArgomento(String argomento) throws SQLException {
        return corsoDAO.findByArgomento(argomento);
    }
    
    /* Restituisce tutti i corsi associati allo chef loggato. */
    public List<Corso> getCorsiChef() throws SQLException {
        return corsoDAO.findByChef(loggedChef);
    }

    /* Restituisce il numero totale dei corsi presenti nel sistema. */
    public int getNumeroCorsiTotali() throws SQLException {
        return corsoDAO.countCorsiTotali();
    }
   
    
 // ---------------- SESSIONI ONLINE ----------------
    
    /* Aggiunge una nuova sessione online dopo aver validato i dati. */
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


    /* Iscrive un allievo ad una sessione online, controllando duplicati e validità della sessione. */
    public void iscriviASessioneOnline(int idAllievo, int idSessione)
            throws SQLException, EntityNotFoundException, DuplicateEntityException, InvalidOperationException {

        Allievo a = allievoDAO.read(idAllievo);
        if (a == null) throw new EntityNotFoundException("Allievo non trovato");

        SessioneOnline s = sessioneOnlineDAO.read(idSessione);
        if (s == null) throw new EntityNotFoundException("Sessione non trovata");

        if (s.getData().isBefore(LocalDate.now()))
            throw new InvalidOperationException("Sessione già passata");

        int count = adesioneDAO.countAdesioniByAllievoAndSessioneOnline(idAllievo, idSessione);
        if (count > 0)
            throw new DuplicateEntityException("Allievo già iscritto alla sessione");

        adesioneDAO.iscriviSessioneOnline(idAllievo, idSessione);
    }
    
    /* Restituisce il numero totale di sessioni online e pratiche. */
    public Map<String,Integer> getNumeroSessioniOnlinePratiche() throws SQLException {

        Map<String,Integer> result = new HashMap<>();

        int online = sessioneOnlineDAO.countSessioniOnline();
        int pratiche = sessionePraticaDAO.countSessioniPratiche();

        result.put("Online", online);
        result.put("Pratiche", pratiche);

        return result;
    }
    
    
 // ---------------- SESSIONI PRATICHE ----------------
    
    /* Aggiunge una nuova sessione pratica dopo aver validato i dati. */
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

    /* Restituisce tutte le sessioni pratiche future. */
    public List<SessionePratica> getSessioniPraticheFuture() throws SQLException {
        return sessionePraticaDAO.findAll().stream()
                .filter(s -> s.getData().isAfter(LocalDate.now()))
                .toList();
    }

    /* Associa una ricetta a una sessione pratica, verificando che la sessione non sia già passata. */
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
    
    /* Restituisce il numero di sessioni pratiche per ciascun corso. */
    public Map<String,Integer> getNumeroSessioniPerCorso() throws SQLException {

        Map<String,Integer> result = new HashMap<>();

        List<Corso> corsi = corsoDAO.findAll();

        for(Corso c : corsi) {

            int count = sessionePraticaDAO.countByCorso(c.getId());
            result.put(c.getNome(), count);

        }

        return result;
    }
    
    /* Restituisce il numero totale di presenze e assenze. */
    public Map<String,Integer> getPresenzeAssenze() throws SQLException {

        Map<String,Integer> result = new HashMap<>();

        int presenti = adesioneDAO.countPresenti();
        int assenti = adesioneDAO.countAssenti();

        result.put("presenti", presenti);
        result.put("assenti", assenti);

        return result;
    }
    
    /* Restituisce il numero di partecipanti per ogni sessione pratica. */
    public Map<String,Integer> getPartecipantiPerSessione() throws SQLException {

        Map<String,Integer> result = new HashMap<>();
        List<SessionePratica> sessioni = sessionePraticaDAO.findAll();

        for(SessionePratica s : sessioni) {
            int count = adesioneDAO.countBySessione(s.getId());
            String nome = "Sessione " + s.getId() + " - " + s.getData();
            result.put(nome, count);
        }
        return result;
    }


    // ---------------- NOTIFICHE ----------------
    
    /* Crea una nuova notifica nel sistema. */
    public void creaNotifica(Notifica n) throws SQLException, ValidationException {
        if (n == null) throw new ValidationException("Notifica non valida");
        notificaDAO.create(n);
    }

    /* Restituisce tutte le notifiche associate ad uno chef. */
    public List<Notifica> getNotificheChef(int idChef) throws SQLException {
        return notificaDAO.findByChef(idChef);
    }
    
    /* Invia una notifica associata ad un corso specifico. */
    public void inviaNotificaCorso(Corso corso, String testo) throws SQLException, ValidationException {
        if (corso == null) throw new ValidationException("Seleziona un corso");
        if (testo == null || testo.isBlank()) throw new ValidationException("Testo non valido");

        Notifica n = new Notifica(0, testo, getLoggedChef(), corso);
        creaNotifica(n);
    }

    /* Invia una notifica generale a tutti gli utenti. */
    public void inviaNotificaATutti(String testo) throws SQLException, ValidationException {
        if (testo == null || testo.isBlank()) throw new ValidationException("Testo non valido");

        Notifica n = new Notifica(0, testo, getLoggedChef(), null);
        creaNotifica(n);
    }
    
    /* Restituisce una notifica dato il suo id. */
    public Notifica getNotificaById(int id) throws Exception {
        return notificaDAO.read(id);
    }
 
    
 // ---------------- RICETTE ----------------

    /* Aggiunge un ingrediente ad una ricetta, specializzando la quantià e l'unità di misura. */
    public void aggiungiIngredienteARicetta(int idRicetta, int idIngrediente, double quantita, String unita)
            throws SQLException, EntityNotFoundException, ValidationException {

    	if (quantita <= 0)
            throw new ValidationException("Quantità deve essere maggiore di zero");

        if (unita == null || unita.isBlank())
            throw new ValidationException("Unità di misura obbligatoria");

        Ricetta r = ricettaDAO.read(idRicetta);
        if (r == null) throw new EntityNotFoundException("Ricetta non trovata");

        Ingrediente i = ingredienteDAO.read(idIngrediente);
        if (i == null) throw new EntityNotFoundException("Ingrediente non trovato");

        ricettaDAO.aggiungiIngrediente(idRicetta, idIngrediente, quantita, unita);
    }
    
    /* Restituisce le ricette più utilizzate nelle sessioni. */
    public Map<String,Integer> getTopRicetteUsate() throws SQLException {
        return ricettaDAO.getTopRicetteUsate();
    }

    /* Restituisce statistiche sulle ricette per sessione (media, massimo, minimo). */
    public Map<String,Double> getStatisticheRicetteSessione() throws SQLException {

        Map<String,Double> result = new HashMap<>();

        result.put("Media", ricettaDAO.getMediaRicettePerSessione());
        result.put("Max", (double) ricettaDAO.getMaxRicettePerSessione());
        result.put("Min", (double) ricettaDAO.getMinRicettePerSessione());

        return result;
    }
    
    /* Aggiunge una nuova ricetta al sistema. */
    public void aggiungiRicetta(Ricetta r) throws Exception {
        ricettaDAO.create(r);
    }
    
    
 // ---------------- ALLIEVO ---------------- 
    
    /* Registra un nuovo allievo nel sistema dopo aver validato i dati. */
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
    
    /* Restituisce l'id di un ingrediente dato il nome. */
    public int getIngredienteByName(String nome) throws SQLException, EntityNotFoundException {
        List<Ingrediente> lista = ingredienteDAO.findByNome(nome);

        if (lista.isEmpty()) {
            throw new EntityNotFoundException("Ingrediente non trovato: " + nome);
        }
        return lista.get(0).getId();
    }
    
    /* Crea un nuovo ingrediente nel sistema. */
    public int creaIngrediente(String nome, String tipologia, LocalDate scadenza, double calorie) 
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
