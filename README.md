🍽️ UninaFoodLab

📌 Descrizione del progetto

UninaFoodLab è un sistema software sviluppato per la gestione di corsi di cucina.
L’applicazione consente agli chef di organizzare corsi, pianificare sessioni (online e pratiche), gestire ricette e ingredienti, inviare notifiche agli utenti e visualizzare report statistici.

---

🎯 Obiettivi

- Gestione completa dei corsi di cucina
- Organizzazione delle sessioni (online e pratiche)
- Gestione delle ricette e degli ingredienti
- Interazione con gli allievi
- Generazione di report con rappresentazione grafica

---

🏗️ Architettura

Il sistema è stato progettato seguendo il pattern ECB (Entity-Control-Boundary):

- Entity → rappresentano i dati del dominio (Chef, Corso, Ricetta, ecc.)
- Control → gestisce la logica applicativa (Controller)
- Boundary → interfaccia grafica (GUI in Java Swing)

---

🗄️ Accesso ai dati

È stato utilizzato il pattern DAO (Data Access Object) per separare la logica applicativa dall’accesso al database.

- Interfaccia generica: "GenericDAO<T>" (operazioni CRUD)
- Implementazione base: "GenericImpl<T>"
- DAO specifiche per ogni entità (ChefDAO, CorsoDAO, ecc.)

---

🛠️ Tecnologie utilizzate

- Java (Swing per la GUI)
- PostgreSQL (database relazionale)
- JDBC (connessione al database)
- JFreeChart (generazione grafici)
- Git & GitHub (versionamento)

---

⚙️ Funzionalità principali

👨‍🍳 Gestione chef

- Registrazione e login
- Accesso alla dashboard personale

📚 Gestione corsi

- Creazione corsi
- Visualizzazione e filtro per argomento

🖥️ Sessioni

- Sessioni online (con link e partecipanti)
- Sessioni pratiche (con ricette associate)

🍝 Ricette e ingredienti

- Creazione ricette
- Associazione ingredienti con quantità e unità di misura

🔔 Notifiche

- Invio notifiche a corsi specifici o a tutti gli utenti
- Visualizzazione notifiche nella dashboard

📊 Report

- Numero totale corsi
- Numero sessioni online e pratiche
- Statistiche sulle ricette (media, massimo, minimo)
- Visualizzazione grafica tramite JFreeChart

---

🧠 Scelte progettuali

- Utilizzo del pattern ECB per separare responsabilità
- Uso dei DAO per isolare l’accesso al database
- Introduzione di "GenericDAO" e "GenericImpl" per evitare duplicazione del codice CRUD
- Gestione di alcuni valori (es. frequenza, tipo preparazione) tramite stringhe invece di enum, in accordo con i vincoli del progetto

---

▶️ Come eseguire il progetto

1. Clonare la repository:
   
   git clone https://github.com/tuo-username/uninafoodlab.git

2. Importare il progetto in Eclipse

3. Configurare il database PostgreSQL:
   
   - creare il database "uninafoodlab"
   - aggiornare le credenziali nella classe "DBConnection"

4. Eseguire il progetto

---

👥 Autori

- Simona D'Anna
- Maurizio Davide Caiazzo

---

📎 Note

Questo progetto è stato sviluppato a scopo didattico per il corso di Programmazione ad Oggetti.
