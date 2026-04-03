```
GESTIONALE AZIENDALE TRASPORTO PUBBLICO

Progetto di gestione integrata: Java, JPA & PostgreSQL
Questo progetto nasce dall'esigenza di automatizzare e monitorare le operazioni di una compagnia di trasporti pubblici. 
L'obiettivo principale è stato creare un'architettura solida capace di gestire la complessità dei titoli di viaggio, 
la logistica dei mezzi e la reportistica aziendale, garantendo coerenza tra i dati e facilità d'uso.

---------------------------------------------------------------------------------------------------------------------------------------------------

Architettura e Scelte Tecniche

Core Stack
- Java 17 & JPA (Hibernate): Gestione della persistenza tramite Object-Relational Mapping (ORM).
- PostgreSQL: Database relazionale per una gestione robusta e transazionale dei dati.
- Trello: Utilizzato come strumento di Project Management per il coordinamento del workflow di gruppo.

Caratteristiche di Rilievo
- UUID Management: Utilizzo di identificativi univoci universali per garantire l'integrità dei dati. 
  Abbiamo implementato un Converter UUID/Integer custom per semplificare l'interazione via CLI, rendendo l'input dei dati rapido 
  senza compromettere la struttura del DB.
- DAO Pattern: Separazione netta tra la logica di business e l'accesso ai dati, facilitando la manutenibilità e il testing del codice.
- Validazione Dinamica: Controllo in tempo reale della validità di tessere/abbonamenti e dello stato operativo dei mezzi (Servizio vs Manutenzione).

---------------------------------------------------------------------------------------------------------------------------------------------------

Robustezza e Gestione delle Eccezioni

Per garantire la stabilità del sistema ed evitare il crash dell'applicazione durante l'interazione con l'utente (Scanner CLI) o il Database, 
abbiamo implementato un sistema gerarchico di gestione degli errori:

- Input Validation: Gestione delle eccezioni di tipo InputMismatchException per prevenire errori di inserimento dati nello Scanner.
- Data Integrity: Gestione delle eccezioni JPA/Hibernate (es. EntityNotFoundException o PersistenceException) per gestire correttamente 
  tentativi di accesso a dati inesistenti o violazioni di vincoli sul database.
- Graceful Shutdown: Ogni blocco critico è protetto da strutture try-catch localizzate, assicurando che un errore in una 
  singola operazione (es. una vidimazione fallita) non interrompa l'intera esecuzione del programma, ma restituisca un feedback chiaro all'utente.

---------------------------------------------------------------------------------------------------------------------------------------------------

Organizzazione delle Funzionalità
L'applicazione implementa un sistema di Role-Based Access, garantendo sicurezza e separazione delle responsabilità.

Utente Semplice
Focalizzato sull'accessibilità ai servizi:

- Emissione: Creazione di biglietti e abbonamenti nominativi.
- Consultazione: Verifica dello stato dei mezzi, orari e dettagli delle tratte.
- Validazione: Vidimazione dei titoli a bordo tramite associazione dinamica tra mezzo e biglietto.

Amministratore di Sistema
- Focalizzato sulla gestione e l'analisi strategica:
- Asset Management: Gestione completa (CRUD) di punti vendita, tessere e parco mezzi.
- Manutenzione: Monitoraggio dei periodi di fermo tecnico e storico degli interventi sui veicoli.
- Analytics: Reportistica su vendite totali, emissioni per punto vendita e calcolo del tempo medio di percorrenza effettivo per ogni tratta.

------------------------------------------------------------------------------------------------------------------------------------------------

Struttura del Database (ERD)
Il modello poggia su un'architettura relazionale ottimizzata:

- Anagrafica: Utenti ➔ Tessere.
- Vendite: Punti_di_Emissione ➔ Biglietti / Abbonamenti.
- Logistica: Mezzi ➔ Tratte ➔ Percorrenze (per il tracciamento dei KPI di performance).

------------------------------------------------------------------------------------------------------------------------------------------------
        
         
         ****** DAO
                
         distingui tipi di utente diversi
         un tipo di utente può essere amministratore o utente
         
         PRIVILEGI USER:
              amministratore
              utente
        
         --------------------------------------------------------
          FUNZIONALITA       |   UTENTE    |   AMMINISTRATORE
         --------------------------------------------------------
          crea un biglietto        SI             NO
         -----------------------------------------------------------
          gestire biglietto        NO             SI 
         -----------------------------------------------------------
          creare abbonamento       SI             NO
         -----------------------------------------------------------
          gestire abbonamento      NO             SI
         -----------------------------------------------------------
          creare punto di          NO             SI 
                emissione
         -----------------------------------------------------------
          creare tessera           NO             SI
         -----------------------------------------------------------
          statistiche 
              biglietti            NO             SI
         -----------------------------------------------------------
         statistiche 
              abbonamenti          NO             SI  
         -----------------------------------------------------------
         statistiche 
            punti vendita          NO             SI
         -----------------------------------------------------------
          statistiche              NO             SI
              mezzo                       
         -----------------------------------------------------------
          ottieni tempo
               percorrenza         NO              SI
         -----------------------------------------------------------
          ottieni zona arrivo      SI              SI
                 /partenza
         -----------------------------------------------------------
          verifica se un mezzo    SI                SI 
              è in servizio
         -----------------------------------------------------------
          verifica se un mezzo    NO                SI 
              è in manutenzione
         -----------------------------------------------------------
          verificare se 
              distributore        SI                SI 
              è in servizio
         -----------------------------------------------------------
          trova i mezzi           NO                SI 
             dell'azienda   
         -----------------------------------------------------------


        
         SIGNIFICA:
              gestire biglietto = update, cambiare attributi, delete
              gestire abbonamento = update, cambiare attributi, delete
              statistiche mezzo = dato un mezzo, calcola il tempo medio effettivo di percorrenza
              trova i mezzi dell'azienda = trova tutti i mezzi dell'azienda (quali, non quanti, quindi una lista)
        
         NON DERIVANO
         utenti
         punti_di_emissione
         mezzi_di_trasporto
         tratte
        
         DERIVANO
         biglietti -> punti_di_emissione
         abbonamenti -> punti_di_emissione & tessere
         tessere -> utenti & punti_di_emissione
         manutenzioni -> mezzi_di_trasporto
         percorrenze -> tratte & mezzi_di_trasporto
                        
        
```