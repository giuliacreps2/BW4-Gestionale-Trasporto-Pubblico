package giuliacrepaldi.tests.giulia;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipoAbbonamento;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class AppScannerTest {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-francesco");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        BigliettiDAO bd = new BigliettiDAO(em);

        System.out.println("GESTIONALE-TRASPORTI-PUBBLICI");
        System.out.println("Seleziona ruolo: ");
        System.out.println("1 Utente");
        System.out.println("2 Amministratore");

        int sceltaRuolo = Integer.parseInt(scanner.nextLine());
        if (sceltaRuolo == 1) {
            menuUtente();
        } else if (sceltaRuolo == 2) {
            menuAmministratore();
        } else {
            System.out.println("Scelta non valida");
        }
    }

    public static void creaBiglietto(EntityManager em, Scanner scanner) {

        BigliettiDAO bigliettiDAO = new BigliettiDAO(em);
        PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);

        try {
            // 1. Mostra punti disponibili
            System.out.println(" PUNTI EMISSIONE DISPONIBILI ");
            puntiDAO.findAll().forEach(p ->
                    System.out.println(p.getPuntoEmissioneId() + " - " + p.getTipologiaPuntoEmissione())
            );

            // 2. Input ID
            System.out.print("Inserisci ID punto emissione: ");
            UUID idPunto = UUID.fromString(scanner.nextLine());

            // 3. Recupero dal DB
            PuntoEmissione punto;

            try {
                punto = puntiDAO.trovaPerId(idPunto);
            } catch (PuntoEmissioneNonTrovatoException e) {
                System.out.println("Punto emissione non trovato!");
                return;
            }

            //Per questioni di usabilità proporrei:
            // System.out.print("Vuoi una corsa da 2,50 o il giornaliero da 5,60? ");
            System.out.print("Inserisci prezzo: ");
            double prezzo = Double.parseDouble(scanner.nextLine());

            Biglietto biglietto = new Biglietto(punto, prezzo);

            bigliettiDAO.salva(biglietto);

            System.out.println("Biglietto creato!");
            System.out.println(biglietto);

        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static void creaAbbonamento(EntityManager em, Scanner scanner) {
        AbbonamentiDAO abbonamentiDAO = new AbbonamentiDAO(em);
        PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);
        UtentiDAO utenteDAO = new UtentiDAO(em);
        TessereDAO tessereDAO = new TessereDAO(em);

            //1.Inserisci i dati per l'abbonamento
            System.out.print("Per l'abbonamento devi avere la tessera: 1. crea una nuova tessera, 2. cerca la mia tessera. 0 per uscire");
            int scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta) {
                case 1:
                    System.out.println("Inserisci il tuo nome");
                    String nome = scanner.nextLine();
                    System.out.println("Inserisci il tuo cognome");
                    String cognome = scanner.nextLine();
                    //inserire un comando per capire se è maggiorenne o è minorenne
                    System.out.println("Inserisci la tua età");
                    int eta = Integer.parseInt(scanner.nextLine());
                    System.out.println("Inserisci la tua email");

                    //catch errore per capire se la mail è valida
                    String email = scanner.nextLine();
                    do {
                        System.out.println("Inserisci la tua email");
                        email = scanner.nextLine();
                        if (!email.contains("@") || !email.contains(".")) {
                            System.out.println("Email non valida, riprova");
                        }
                    } while (!email.contains("@") || !email.contains("."));

                    Utente utente = new Utente(nome, cognome, eta, email);

                    utenteDAO.salva(utente);

                    System.out.println("Ti mancano solo altri 2 passaggi per creare la tua tessera");
                    System.out.println("Inserisci la data di oggi: il formato da inserire è ANNO-MM-GG");

                    //catch eventuali errori nel formato data
                    LocalDate dataInzioTessera =  LocalDate.parse(scanner.nextLine());
                    while (dataInzioTessera == null) {
                        System.out.println("Inserisci la data (formato: AAAA-MM-GG, esempio: 2026-04-02): ");
                        try {
                            dataInzioTessera = LocalDate.parse(scanner.nextLine());
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato data non valido, riprova");
                        }
                    }

                    try {
                        //1. Mostra punti disponibili
                        System.out.println(" PUNTI EMISSIONE DISPONIBILI ");
                        puntiDAO.findAll().forEach(p ->
                                System.out.println(p.getPuntoEmissioneId() + " - " + p.getTipologiaPuntoEmissione())
                        );

                        //2. Input ID
                        System.out.print("Inserisci ID punto emissione: ");
                        UUID idPunto = UUID.fromString(scanner.nextLine());

                        //3. Recupero dal DB
                        PuntoEmissione punto;

                        try{
                            punto = puntiDAO.trovaPerId(idPunto);
                        } catch (PuntoEmissioneNonTrovatoException e) {
                            System.out.println("Punto emissione non trovato!");
                            return;
                        }

                        Tessera tessera = new Tessera(punto, utente, dataInzioTessera);
                        tessereDAO.salva(tessera);

                        System.out.println("La tua tessera è stata creata correttamente, ora puoi procedere con la scelta dell'abbonamento");

                        //scelta enum TipoAbbonamento
                        System.out.println("Che tipo di abbonamento vuoi acqustare?");
                        TipoAbbonamento[] tipi = TipoAbbonamento.values();
                        for (int i = 0; i < tipi.length; i++){
                            System.out.println((i+1) + ". " + tipi[i].toString());
                        }
                        int sceltaTipo = 0;
                        while (sceltaTipo < 1 || sceltaTipo > tipi.length){
                            try {
                                sceltaTipo = Integer.parseInt(scanner.nextLine());
                                if (sceltaTipo < 1 || sceltaTipo > tipi.length){
                                    System.out.println("Scelta non valida, riprova");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Inserisci un numero valido, riprova");
                            }
                        }
                        TipoAbbonamento tipoScelto = tipi[sceltaTipo-1];

                        switch (tipoScelto) {
                            case 1:
                                System.out.println("Scegli tra: 25.90 o ...");
                                double prezzo = Integer.parseInt(scanner.nextLine());
                                System.out.println("Da che data vuoi che inizi il tuo abbonamento? 1. Oggi, 2. Inserisci la data personalizzata");
                                int scelta3 = Integer.parseInt(scanner.nextLine());
                                switch (scelta3) {
                                    case 1:
                                        LocalDate dataInizioTessera = LocalDate.now();
                                        System.out.println("La tua data è stata inserita correttamente: " + dataInizioTessera);
                                        break;
                                    case 2:

                                        System.out.println("Il formato della data deve essere: AA-MM-GG");
                                        LocalDate dataInizioTessera2 = LocalDate.parse(scanner.nextLine());

                                        //catch eventuali errori nel formato data
                                        while (dataInizioTessera2 == null) {
                                            System.out.println("Inserisci la data (formato: AAAA-MM-GG, esempio: 2026-04-02): ");
                                            try {
                                                dataInizioTessera2 = LocalDate.parse(scanner.nextLine());
                                            } catch (DateTimeParseException e) {
                                                System.out.println("Formato data non valido, riprova");
                                            }
                                        }
                                        break;
                                }

                                Abbonamento abbonamento1 = new Abbonamento(punto, prezzo, tessera, tipoAbbonamento  );
                                abbonamentiDAO.salva(abbonamento1);

                                System.out.println("Il tuo abbonamento è stato creato con successo. Ecco il riepilogo dei tuoi dati: " + abbonamento1);
                        }

                    break;
                    case 2:
                        //Fare in modo che se sbagliato lo UUID si
                        System.out.println("Inserisci il tuo numero di tessera: (suggerimento: fjiefgjdfjgndjfn");
                        UUID idTessera = UUID.fromString(scanner.nextLine());

                        //Recupero dal DB

                        try{
                            idTessera = tessereDAO.tr
                        }

                        Tessera tessera;






            }

        }
    }

    public static void menuUtente() {
        int scelta;
        do {
            System.out.println("MENU UTENTE");
            System.out.println("Scelta: ");
            System.out.println("1. Crea biglietto");
            System.out.println("2. Crea abbonamento");
            System.out.println("3. Ottieni zona partenza/arrivo");
            System.out.println("4. Verifica se un mezzo è in servizio");
            System.out.println("5. Verifica se un distributore è in servizio");
            System.out.println("0. Esci");
            scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta) {
                case 1:
                    EntityManager em = entityManagerFactory.createEntityManager();
                    creaBiglietto(em, scanner);
                    break;
            }
        } while (scelta != 0);
    }

    public static void menuAmministratore() {
        int scelta;
        do {
            System.out.println("Menu Amministratore");
            System.out.println("1. Gestigli Biglietto");
            System.out.println("2. Gestisci Abbonamento");
            System.out.println("3. Crea punto di emissione");
            System.out.println("4. Crea Tessera");
            System.out.println("5. Statistiche Biglietti");
            System.out.println("6. Statistiche abbonamenti");
            System.out.println("7. Statistiche punti vendita");
            System.out.println("8. Statistiche mezzo");
            System.out.println("9. Ottieni tempo percorrenza");
            System.out.println("10. Ottieni zona partenza/arrivo");
            System.out.println("11. Verifica se un mezzo è in servizio");
            System.out.println("12. Verifica se un distributore è in servizio");
            System.out.println("13. Trova i mezzi dell'azienda");
            System.out.println("0. Esci");
            scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta) {

            }
        } while (scelta != 0);
    }
}



