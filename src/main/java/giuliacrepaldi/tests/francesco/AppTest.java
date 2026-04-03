package giuliacrepaldi.tests.francesco;


import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.Percorrenza;
import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonTrovatoException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import giuliacrepaldi.exceptions.tratta.TrattaNonTrovataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static giuliacrepaldi.tests.cristian.AppScanner.menuUtente;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

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

    // public static void creaBiglietto(EntityManager em, Scanner scanner) {
//
//     BigliettiDAO bigliettiDAO = new BigliettiDAO(em);
//     PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);
//
//     try {

    /// /              // 1. Mostra punti disponibili
    /// /              System.out.println("=== PUNTI EMISSIONE DISPONIBILI ===");
    /// /              puntiDAO.findAll().forEach(p ->
    /// /                      System.out.println(p.getId() + " - " + p.getTipologia())
    /// /              );
//
//         // 2. Input ID
//         System.out.print("Inserisci ID punto emissione: ");
//         UUID idPunto = UUID.fromString(scanner.nextLine());
//
//         // 3. Recupero dal DB
//         PuntoEmissione punto;
//
//         try {
//             punto = puntiDAO.trovaPerId(idPunto);
//         } catch (PuntoEmissioneNonTrovatoException e){
//             System.out.println("Punto emissione non trovato!");
//             return;
//         }
//
//         System.out.print("Inserisci prezzo: ");
//         double prezzo = Double.parseDouble(scanner.nextLine());
//
//         Biglietto biglietto = new Biglietto(punto, prezzo);
//
//         bigliettiDAO.salva(biglietto);
//
//         System.out.println("Biglietto creato!");
//         System.out.println(biglietto);
//
//     } catch (RuntimeException e) {
//         System.out.println("Errore: " + e.getMessage());
//     }
//}
//    public static void menuUtente(){
//        int scelta;
//        do {
//            System.out.println("MENU UTENTE");
//            System.out.println("Scelta: ");
//            System.out.println("1. Crea biglietto");
//            System.out.println("2. Crea abbonamento");
//            System.out.println("3. Ottieni zona partenza/arrivo");
//            System.out.println("4. Verifica se un mezzo è in servizio");
//            System.out.println("5. Verifica se un distributore è in servizio");
//            System.out.println("0. Esci");
//            scelta = Integer.parseInt(scanner.nextLine());
//             switch (scelta) {
//                 case 1:
//                     EntityManager em = entityManagerFactory.createEntityManager();
//                     creaBiglietto(em, scanner);
//                     break;
//             }
//        }while (scelta != 0);
//    }


    //INIZIO METODI MENU AMMINISTRATORE DA 8 A 13+ ESCI
    private static UUID leggiUUID(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;
            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.err.println("Formato UUID non valido! Riprova");
            }
        }
    }

    // 8
    public static void statisticheMezzo(EntityManager em, Scanner scanner) {
        MezziTrasportoDAO mezziDAO = new MezziTrasportoDAO(em);
        BigliettiDAO bigliettiDAO = new BigliettiDAO(em);
        PercorrenzeDAO percorrenzeDAO = new PercorrenzeDAO(em);

        try {
            System.out.print("Inserisci ID mezzo: ");
            UUID idMezzo = leggiUUID(scanner);

            MezzoTrasporto mezzo;
            try {
                mezzo = mezziDAO.trovaPerId(idMezzo);
            } catch (MezzoTrasportoNonTrovatoException e) {
                System.out.println("Mezzo non trovato!");
                return;
            }

            int numeroBigliettiVidimati = Math.toIntExact(bigliettiDAO.contaBigliettiVidimatiSuMezzoTrasporto(mezzo));
            List<Percorrenza> percorrenze = percorrenzeDAO.findPercorrenzaByMezzo(idMezzo);
            int numeroPercorrenze = percorrenze.size();
            boolean inManutenzione = mezziDAO.eInManutenzione(mezzo);
            boolean inServizio = mezziDAO.inServizio(mezzo);

            System.out.println("STATISTICHE MEZZO ");
            System.out.println("Mezzo: " + mezzo);
            System.out.println("Numero biglietti vidimati: " + numeroBigliettiVidimati);
            System.out.println("Numero percorrenze effettuate: " + numeroPercorrenze);
            System.out.println("In manutenzione: " + inManutenzione);
            System.out.println("In servizio: " + inServizio);

        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // 9
    public static void ottieniTempoPercorrenza(EntityManager em, Scanner scanner) {
        PercorrenzeDAO percorrenzeDAO = new PercorrenzeDAO(em);
        MezziTrasportoDAO mezziDAO = new MezziTrasportoDAO(em);
        TratteDAO tratteDAO = new TratteDAO(em);

        try {
            System.out.print("Inserisci ID mezzo: ");
            UUID idMezzo = leggiUUID(scanner);

            System.out.print("Inserisci ID tratta: ");
            UUID idTratta = leggiUUID(scanner);

            MezzoTrasporto mezzo;
            Tratta tratta;
            try {
                mezzo = mezziDAO.trovaPerId(idMezzo);
            } catch (MezzoTrasportoNonTrovatoException e) {
                System.out.println("Mezzo non trovato!");
                return;
            }
            try {
                tratta = tratteDAO.trovaPerId(idTratta.toString());
            } catch (TrattaNonTrovataException e) {
                System.out.println("Tratta non trovata!");
                return;
            }

            Duration tempoMedio = percorrenzeDAO.getTempoMedioEffettivo(idMezzo, idTratta);
            long ore = tempoMedio.toHours();
            long minuti = tempoMedio.toMinutesPart();

            System.out.println("TEMPO PERCORRENZA");
            System.out.println("Mezzo: " + mezzo);
            System.out.println("Tratta: " + tratta);
            System.out.println("Tempo medio effettivo: " + ore + " ore e " + minuti + " minuti");

        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // 10
    public static void ottieniZonaPartenzaArrivo(EntityManager em, Scanner scanner) {
        TratteDAO tratteDAO = new TratteDAO(em);
        try {
            System.out.print("Inserisci ID tratta: ");
            UUID idTratta = leggiUUID(scanner);

            Tratta tratta;
            try {
                tratta = tratteDAO.trovaPerId(idTratta.toString());
            } catch (TrattaNonTrovataException e) {
                System.out.println("Tratta non trovata!");
                return;
            }
            System.out.println(" ZONA PARTENZA / ARRIVO ");
            System.out.println("Zona di partenza: " + tratta.getZonaPartenza());
            System.out.println("Zona di arrivo: " + tratta.getZonaArrivo());

        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // 11
    public static void verificaMezzoInServizio(EntityManager em, Scanner scanner) {
        MezziTrasportoDAO mezziDAO = new MezziTrasportoDAO(em);
        try {
            System.out.print("Inserisci ID mezzo: ");
            UUID idMezzo = leggiUUID(scanner);

            MezzoTrasporto mezzo;
            try {
                mezzo = mezziDAO.trovaPerId(idMezzo);
            } catch (MezzoTrasportoNonTrovatoException e) {
                System.out.println("Mezzo non trovato!");
                return;
            }

            boolean inServizio = mezziDAO.inServizio(mezzo);
            System.out.println("=== STATO MEZZO ===");
            System.out.println("Mezzo: " + mezzo);
            if (inServizio) {
                System.out.println("Il mezzo è IN SERVIZIO");
            } else {
                System.out.println("Il mezzo NON è in servizio (in manutenzione)");
            }
        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // 12
    public static void verificaDistributoreInServizio(EntityManager em, Scanner scanner) {
        PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);
        try {
            System.out.print("Inserisci ID punto emissione: ");
            UUID idPunto = leggiUUID(scanner);

            PuntoEmissione punto;
            try {
                punto = puntiDAO.trovaPerId(idPunto);
            } catch (PuntoEmissioneNonTrovatoException e) {
                System.out.println("Punto di emissione non trovato!");
                return;
            }
            System.out.println("STATO DISTRIBUTORE");
            System.out.println("Punto: " + punto);
            if (punto.isAttivo()) {
                System.out.println("Il distributore è IN SERVIZIO");
            } else {
                System.out.println("Il distributore NON è in servizio");
            }

        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    // 13
    public static void trovaMezziAzienda(EntityManager em) {
        MezziTrasportoDAO mezziDAO = new MezziTrasportoDAO(em);
        try {
            List<MezzoTrasporto> mezzi = mezziDAO.findAll();
            if (mezzi.isEmpty()) {
                System.out.println("Nessun mezzo trovato!");
                return;
            }
            System.out.println("MEZZI DELL'AZIENDA");
            for (MezzoTrasporto mezzo : mezzi) {
                System.out.println(mezzo);
            }
        } catch (RuntimeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
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
                case 8:
                    EntityManager em8 = entityManagerFactory.createEntityManager();
                    try {
                        statisticheMezzo(em8, scanner);
                    } finally {
                        em8.close();
                    }
                    break;
                case 9:
                    EntityManager em9 = entityManagerFactory.createEntityManager();
                    try {
                        ottieniTempoPercorrenza(em9, scanner);
                    } finally {
                        em9.close();
                    }
                    break;
                case 10:
                    EntityManager em10 = entityManagerFactory.createEntityManager();
                    try {
                        ottieniZonaPartenzaArrivo(em10, scanner);
                    } finally {
                        em10.close();
                    }
                    break;
                case 11:
                    EntityManager em11 = entityManagerFactory.createEntityManager();
                    try {
                        verificaMezzoInServizio(em11, scanner);
                    } finally {
                        em11.close();
                    }
                    break;
                case 12:
                    EntityManager em12 = entityManagerFactory.createEntityManager();
                    try {
                        verificaDistributoreInServizio(em12, scanner);
                    } finally {
                        em12.close();
                    }
                    break;
                case 13:
                    EntityManager em13 = entityManagerFactory.createEntityManager();
                    try {
                        trovaMezziAzienda(em13);
                    } finally {
                        em13.close();
                    }
                    break;

            }
        } while (scelta != 0);
    }

}



