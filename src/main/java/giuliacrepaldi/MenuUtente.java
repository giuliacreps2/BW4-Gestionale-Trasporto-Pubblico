package giuliacrepaldi;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipoAbbonamento;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import giuliacrepaldi.exceptions.biglietto.BigliettoGiaObliteratoException;
import giuliacrepaldi.exceptions.biglietto.BigliettoNonTrovatoException;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonTrovatoException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import giuliacrepaldi.helpers.ConvertitoreUUID;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.*;

public class MenuUtente {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-francesco");
    static Scanner scanner = new Scanner(System.in);


    public static void obliteraBiglietto(EntityManager em, Scanner scanner) {
        System.out.println("Prima di accedere alle corse, oblitera il biglietto");

        UUID bigliettoId = null;
        while (bigliettoId == null) {
            System.out.println("Inserisci l'ID del biglietto (es: 19c7039d-6f4d-46c7-90aa-c488b2ab437c ) ");
            try {
                bigliettoId = UUID.fromString(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("ID non valido, riprova");
            }
        }

        //Cerco il biglietto nel DB
        BigliettiDAO bigliettiDAO = new BigliettiDAO(em);
        Biglietto biglietto;
        try {
            biglietto = bigliettiDAO.trovaPerId(String.valueOf(bigliettoId));
        } catch (BigliettoNonTrovatoException e) {
            throw new RuntimeException(e);
        }

        //Chiedo il mezzo sul quale oblitera
        System.out.println("MEZZI DISPONIBILI");
        MezziTrasportoDAO mezziTrasportoDAO = new MezziTrasportoDAO(em);
        ConvertitoreUUID convertitore = new ConvertitoreUUID(em);
        Map<Integer, UUID> mapMezziTrasporto = convertitore.mapMezziTrasporto();

        int sceltaMezzo = 0;
        while (!mapMezziTrasporto.containsKey(sceltaMezzo)) {
            System.out.println("Inserisci il numero del mezzo: ");
            try {
                sceltaMezzo = Integer.parseInt(scanner.nextLine());
                if (!mapMezziTrasporto.containsKey(sceltaMezzo)) System.out.println("Scelta non valida");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        }

        MezzoTrasporto mezzoTrasporto;
        try {
            mezzoTrasporto = mezziTrasportoDAO.trovaPerId(mapMezziTrasporto.get(sceltaMezzo));
        } catch (MezzoTrasportoNonTrovatoException e) {
            throw new RuntimeException(e);
        }

        //Obliterazione
        try {
            biglietto.setObliteratoDa(mezzoTrasporto);
            bigliettiDAO.salva(biglietto);
            System.out.println("Biglietto obliterato con successo");
            //convertire in orario umano
            System.out.println("Valido fino alle: " + biglietto.getDataEOraFineObliterazione());
        } catch (BigliettoGiaObliteratoException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        EntityManager em = entityManagerFactory.createEntityManager();
//        BigliettiDAO bd = new BigliettiDAO(em);
//
//    }

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

                System.out.println("Ti mancano solo un altro passaggio per creare la tua tessera");
                LocalDate dataInzioTessera = LocalDate.now();
//                while (dataInzioTessera == null) {
//                    System.out.println("Inserisci la data di oggi (formato: AAAA-MM-GG, esempio: 2026-04-02): ");
//                    try {
//                        dataInzioTessera = LocalDate.parse(scanner.nextLine());
//                    } catch (DateTimeParseException e) {
//                        System.out.println("Formato data non valido, riprova");
//                    }
//                }

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

                PuntoEmissione punto;
                try {
                    punto = puntiDAO.trovaPerId(mappaPunti.get(sceltaPunto));
                } catch (PuntoEmissioneNonTrovatoException e) {
                    System.out.println("Punto emissione non trovato!");
                    return;
                }

                tessera = new Tessera(punto, utente, dataInzioTessera);
//                tessera.setDataVendita(LocalDate.now());
                tessereDAO.salva(tessera);

                System.out.println("La tua tessera è stata creata correttamente:" + tessera);
                System.out.println("Ora puoi procedere con la scelta dell'abbonamento");
                break;

            case 2:
                Tessera tessera1;

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
        System.out.println("1. Abbonamento base - 25.90");
        System.out.println("2. Abbonamento extra-urbano - 40.00");
        System.out.println("3. Abbonamento porta un amico - 55.50");

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

    public static void trovaZonaPartenzaArrivo(EntityManager em, Scanner scanner) {
        System.out.println("CERCA ZONA PARTENZA/ARRIVO");
        System.out.println("1. PARTENZA");
        System.out.println("2. ARRIVO");

        int scelta = Integer.parseInt(scanner.nextLine());
        TratteDAO tratteDAO = new TratteDAO(em);
        List<Tratta> tratta;
        switch (scelta) {
            case 1:
                System.out.println("Digita le prime lettere del luogo da cui vuoi partire");
                String luogo = scanner.nextLine();
                tratta = tratteDAO.findByZonaPartenza(luogo);
                //System.out.println(tratta);
                tratta.stream().forEach(t -> System.out.println(t.getZonaPartenza()));
                if (tratta.isEmpty()) {
                    System.out.println("Non c'è nessuna zona di partenza corrispondente");
                }
                break;
            case 2:

                System.out.println("Digita le prime lettere del luogo in cui vuoi arrivare");
                String luogo2 = scanner.nextLine();
                tratta = tratteDAO.findByZonaPartenza(luogo2);
                tratta.stream().forEach(t -> System.out.println(t.getZonaArrivo()));
                if (tratta.isEmpty()) {
                    System.out.println("Non c'è nessuna zona di arrivo corrispondente. Prova con un'altra ricerca");
                }
                break;
        }

        System.out.println("Ti auguriamo buon viaggio!");


    }

    public static void verificaMezzoInServizio(EntityManager em, Scanner scanner) {
        MezziTrasportoDAO mezziDAO = new MezziTrasportoDAO(em);
        ConvertitoreUUID convertitore = new ConvertitoreUUID(em);

        System.out.println("CERCA MEZZI DISPONIBILI");
        Map<Integer, UUID> mappaMezzi = convertitore.mapMezziTrasporto();

        int scelta = 0;
        while (!mappaMezzi.containsKey(scelta)) {
            System.out.println("Inserisci il numero del mezzo: ");
            try {
                scelta = Integer.parseInt(scanner.nextLine());
                if (!mappaMezzi.containsKey(scelta)) System.out.println("Scelta non valida, riprova");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        }
        try {
            MezzoTrasporto mezzoTrasporto = mezziDAO.trovaPerId(mappaMezzi.get(scelta));
            if (mezziDAO.eInManutenzione(mezzoTrasporto)) {
                System.out.println("Il mezzo con id: " + mezzoTrasporto.getMezzoDiTrasportoId() + ", è in manutenzione!");
            } else {
                System.out.println("Il mezzo con id: " + mezzoTrasporto.getMezzoDiTrasportoId() + ", è in servizio!");
            }
        } catch (MezzoTrasportoNonTrovatoException e) {
            System.out.println("Mezzo trasporto non trovato!");
        }


    }

    public static void verificaDistributoreInServizio(EntityManager em, Scanner scanner) {
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(em);
        ConvertitoreUUID convertitore = new ConvertitoreUUID(em);

        System.out.println("CERCA DISTRIBUTORI DISPONIBILI");
        Map<Integer, UUID> mappaDistributori = convertitore.mapPuntiEmissione();
        List<PuntoEmissione> puntiEmissione = new ArrayList<>();
        puntiEmissione.forEach(PuntoEmissione::getCitta);

        int scelta = 0;
        while (!mappaDistributori.containsKey(scelta)) {
            System.out.println("Inserisci il numero della citta: ");
            try {
                scelta = Integer.parseInt(scanner.nextLine());
                if (!mappaDistributori.containsKey(scelta)) System.out.println("Scelta invalida, riprova");
            } catch (PuntoEmissioneNonTrovatoException e) {
                System.out.println("Distributore non trovato!");
            }
        }
        try {
            PuntoEmissione puntoEmissione = puntiEmissioneDAO.trovaPerId(mappaDistributori.get(scelta));
            if (puntoEmissione.isAttivo()) {
                System.out.println("Il distributore è attivo!");
            } else {
                System.out.println("Il distributore non è attivo!");
            }
        } catch (PuntoEmissioneNonTrovatoException e) {
            System.out.println("Distributore non trovato!");
        }
    }

    public static void mostraMenu(EntityManager em, Scanner scanner) {

        int scelta;
        do {
            System.out.println("MENU UTENTE");
            System.out.println("Scelta: ");
            System.out.println("1. Crea biglietto");
            System.out.println("2. Crea abbonamento");
            System.out.println("3. Oblitera il biglietto");
            System.out.println("4. Cerca zona partenza/arrivo");
            System.out.println("5. Verifica se un mezzo è in servizio");
            System.out.println("6. Verifica se un distributore è in servizio");
            System.out.println("0. Esci");
            scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta) {
                case 1:
                    EntityManager em1 = entityManagerFactory.createEntityManager();
                    creaBiglietto(em1, scanner);
                    break;
                case 2:
                    EntityManager em2 = entityManagerFactory.createEntityManager();
                    creaAbbonamento(em2, scanner);
                    break;
                case 3:
                    EntityManager em3 = entityManagerFactory.createEntityManager();
                    obliteraBiglietto(em3, scanner);
                    break;
                case 4:
                    EntityManager em4 = entityManagerFactory.createEntityManager();
                    trovaZonaPartenzaArrivo(em4, scanner);
                    break;
                case 5:
                    EntityManager em5 = entityManagerFactory.createEntityManager();
                    verificaMezzoInServizio(em5, scanner);
                    break;
                case 6:
                    EntityManager em6 = entityManagerFactory.createEntityManager();
                    verificaDistributoreInServizio(em6, scanner);
                    break;
                case 0:
                    System.out.println("Alla prossima!");
                    break;

            }
        } while (scelta != 0);
    }

}



