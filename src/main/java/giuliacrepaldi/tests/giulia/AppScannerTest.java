package giuliacrepaldi.tests.giulia;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipoAbbonamento;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import giuliacrepaldi.helpers.ConvertitoreUUID;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
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
        ConvertitoreUUID convertitore = new ConvertitoreUUID(em);

        //Oggetti per il DB
        PuntoEmissione punto1 = new PuntoEmissione("Milano", TipologiaPuntoEmissione.DISTRIBUTORE_AUTOMATICO, true);
        PuntoEmissione punto2 = new PuntoEmissione("Roma", TipologiaPuntoEmissione.RIVENDITORE_AUTORIZZATO, true);
        PuntoEmissione punto3 = new PuntoEmissione("Venezia", TipologiaPuntoEmissione.RIVENDITORE_AUTORIZZATO, false);

//        puntiDAO.salva(punto1);
//        puntiDAO.salva(punto2);
//        puntiDAO.salva(punto3);

        try {
            // 1. Mostra punti disponibili
            System.out.println(" PUNTI EMISSIONE DISPONIBILI ");
            Map<Integer, UUID> mappaPuntiEmissione = convertitore.mapPuntiEmissione();

            // 2. Input ID

            int sceltaPuntoEmissione = 0;
            //UUID idPunto = UUID.fromString(scanner.nextLine());
            while (!mappaPuntiEmissione.containsKey(sceltaPuntoEmissione)) {
                System.out.print("Inserisci il numero del punto emissione: ");
                try {
                    sceltaPuntoEmissione = Integer.parseInt(scanner.nextLine());
                    if (!mappaPuntiEmissione.containsKey(sceltaPuntoEmissione)) {
                        System.out.println("Scelta non valida, riprova");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido");
                    return;
                }
            }
            // 3. Recupero dal DB
            // PuntoEmissione sceltaPuntoEmissione;
            UUID idPunto = mappaPuntiEmissione.get(sceltaPuntoEmissione);
            PuntoEmissione punto = puntiDAO.trovaPerId(idPunto);

            //Per questioni di usabilità proporrei:
            // System.out.print("Vuoi una corsa da 2,50 o il giornaliero da 5,60? ");
            System.out.println("Scegli il tipo di biglietto:");
            System.out.println("1. Corsa singola - 1.90");
            System.out.println("2. Andata e ritorno - 3.50");
            System.out.println("3. Giornaliero - 5.60");

            double prezzo = 0;
            while (prezzo == 0) {
                try {
                    int sceltaPrezzo = Integer.parseInt(scanner.nextLine());
                    switch (sceltaPrezzo) {
                        case 1:
                            prezzo = 1.90;
                            break;
                        case 2:
                            prezzo = 3.50;
                            break;
                        case 3:
                            prezzo = 5.60;
                            break;
                        default:
                            System.out.println("Scelta non valida, riprova");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido");
                }
            }

            Biglietto biglietto = new Biglietto(punto, prezzo);

            bigliettiDAO.salva(biglietto);

            System.out.println("Biglietto creato! Data acquisto: " + biglietto.getDataVendita());

        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static void creaAbbonamento(EntityManager em, Scanner scanner) {
        AbbonamentiDAO abbonamentiDAO = new AbbonamentiDAO(em);
        PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);
        UtentiDAO utenteDAO = new UtentiDAO(em);
        TessereDAO tessereDAO = new TessereDAO(em);
        ConvertitoreUUID convertitore = new ConvertitoreUUID(em);

        //1.Inserisci i dati per l'abbonamento
        System.out.println("Per l'abbonamento devi avere la tessera:");
        System.out.println("1. Crea una nuova tessera");
        System.out.println("2. Cerca la mia tessera");
        System.out.println("0. Esci");

        int scelta = Integer.parseInt(scanner.nextLine());
//        while (scelta < 0 || scelta > 2) {
//            try {
//                scelta = Integer.parseInt(scanner.nextLine());
//                if (scelta < 0 || scelta > 2)
//                    System.out.println("Inserisci una scelta valida");
//            } catch (NumberFormatException e) {
//                System.out.println("Inserisci un numero valido");
//            }
//        }
//
//        if (scelta == 0) return;
//        Tessera tessera = null;


        //Crea nuova tessera
        Tessera tessera = null;
        switch (scelta) {
            case 0:
                return;
            case 1:

                System.out.println("Inserisci il tuo nome");
                String nome = scanner.nextLine();

                System.out.println("Inserisci il tuo cognome");
                String cognome = scanner.nextLine();

                System.out.println("Inserisci la tua età");
                int eta = 0;
                while (eta == 0) {
                    try {
                        eta = Integer.parseInt(scanner.nextLine());
                        if (eta <= 0) System.out.println("Età non valida, riprova");
                    } catch (NumberFormatException e) {
                        System.out.println("Inserisci un numero valido");
                    }
                }


                String email = null;
                while (email == null || !email.contains("@") || !email.contains(".")) {
                    System.out.println("Inserisci la tua email");
                    email = scanner.nextLine();
                    if (!email.contains("@") || !email.contains(".")) {
                        System.out.println("Email non valida, riprova");
                    }
                }

                Utente utente = new Utente(nome, cognome, eta, email);
                utenteDAO.salva(utente);

//Data inzio tessera
                System.out.println("Ti mancano solo altri 2 passaggi per creare la tua tessera");
                LocalDate dataInzioTessera = null;
                while (dataInzioTessera == null) {
                    System.out.println("Inserisci la data di oggi (formato: AAAA-MM-GG, esempio: 2026-04-02): ");
                    try {
                        dataInzioTessera = LocalDate.parse(scanner.nextLine());
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato data non valido, riprova");
                    }
                }

                //Scelta punto di emissione
                System.out.println("PUNTI EMISSIONE DISPONIBILI");
                Map<Integer, UUID> mappaPunti = convertitore.mapPuntiEmissione();

                int sceltaPunto = 0;
                while (!mappaPunti.containsKey(sceltaPunto)) {
                    System.out.print("Inserisci il numero del punto emissione: ");
                    try {
                        sceltaPunto = Integer.parseInt(scanner.nextLine());
                        if (!mappaPunti.containsKey(sceltaPunto)) System.out.println("Scelta non valida, riprova");
                    } catch (NumberFormatException e) {
                        System.out.println("Inserisci un numero valido");
                    }
                }

                PuntoEmissione punto = null;
                try {
                    punto = puntiDAO.trovaPerId(mappaPunti.get(sceltaPunto));
                } catch (PuntoEmissioneNonTrovatoException e) {
                    System.out.println("Punto emissione non trovato!");
                    return;
                }

                tessera = new Tessera(punto, utente, dataInzioTessera);
                tessereDAO.salva(tessera);

                System.out.println("La tua tessera è stata creata correttamente, ora puoi procedere con la scelta dell'abbonamento");
                break;

            case 2:
                break;

        }
        //Scelta tipologia abbonamento comune a crea la tessera e trova la tessera
        System.out.println("Che tipo di abbonamento vuoi acquistare?");
        TipoAbbonamento[] tipi = TipoAbbonamento.values();
        for (int i = 0; i < tipi.length; i++) {
            System.out.println((i + 1) + ". " + tipi[i]);
        }

        int sceltaTipo = 0;
        while (sceltaTipo < 1 || sceltaTipo > tipi.length) {
            try {
                sceltaTipo = Integer.parseInt(scanner.nextLine());
                if (sceltaTipo < 1 || sceltaTipo > tipi.length) System.out.println("Scelta non valida, riprova");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        }
        TipoAbbonamento tipoScelto = tipi[sceltaTipo - 1];

        // scelta prezzo
        System.out.println("Scegli il prezzo:");
        System.out.println("1. 25.90");
        System.out.println("2. 40.00");
        System.out.println("3. 55.50");

        double prezzo = 0;
        while (prezzo == 0) {
            try {
                int sceltaPrezzo = Integer.parseInt(scanner.nextLine());
                switch (sceltaPrezzo) {
                    case 1:
                        prezzo = 25.90;
                        break;
                    case 2:
                        prezzo = 40.00;
                        break;
                    case 3:
                        prezzo = 55.50;
                        break;
                    default:
                        System.out.println("Scelta non valida, riprova");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        }

        // punto emissione per l'abbonamento
        System.out.println("PUNTI EMISSIONE DISPONIBILI");
        Map<Integer, UUID> mappaPuntiAbbonamento = convertitore.mapPuntiEmissione();

        int sceltaPuntoAbb = 0;
        while (!mappaPuntiAbbonamento.containsKey(sceltaPuntoAbb)) {
            System.out.print("Inserisci il numero del punto emissione: ");
            try {
                sceltaPuntoAbb = Integer.parseInt(scanner.nextLine());
                if (!mappaPuntiAbbonamento.containsKey(sceltaPuntoAbb))
                    System.out.println("Scelta non valida, riprova");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        }

        PuntoEmissione puntoAbbonamento = null;
        try {
            puntoAbbonamento = puntiDAO.trovaPerId(mappaPuntiAbbonamento.get(sceltaPuntoAbb));
        } catch (PuntoEmissioneNonTrovatoException e) {
            System.out.println("Punto emissione non trovato!");
            return;
        }

        Abbonamento abbonamento = new Abbonamento(puntoAbbonamento, prezzo, tessera, tipoScelto);
        abbonamentiDAO.salva(abbonamento);

        System.out.println("Abbonamento creato con successo!");
        System.out.println(abbonamento);
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
            System.out.println("6. Oblitera il biglietto");
            System.out.println("0. Esci");
            scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta) {
                case 1:
                    EntityManager em = entityManagerFactory.createEntityManager();
                    creaBiglietto(em, scanner);
                    break;
                case 2:
                    EntityManager em2 = entityManagerFactory.createEntityManager();
                    creaAbbonamento(em2, scanner);
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



