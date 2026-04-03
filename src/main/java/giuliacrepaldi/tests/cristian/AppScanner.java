package giuliacrepaldi.tests.cristian;


import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AppScanner {

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

    public static void deleteBiglietto(EntityManager em, Scanner scanner) {
        try {
            BigliettiDAO bigliettiDAO = new BigliettiDAO(em);

            System.out.println("Inserisci ID biglietto da eliminare:");
            String idBiglietto = scanner.nextLine();

            // chiama direttamente il metodo rimuoviPerId
            bigliettiDAO.rimuoviPerId(idBiglietto);

            System.out.println("Biglietto eliminato!");

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static void deleteAbbonamento(EntityManager em, Scanner scanner) {
        try {
            AbbonamentiDAO abbonamentiDAO = new AbbonamentiDAO(em);

            System.out.println("Inserisci ID abbonamento da eliminare:");
            String idAbb = scanner.nextLine();

            abbonamentiDAO.rimuoviPerId(idAbb);

            System.out.println("Abbonamento eliminato!");

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static void creaPuntoEmissione(EntityManager em, Scanner scanner) {
        try {
            PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);

            System.out.println("Inserisci città del punto emissione:");
            String citta = scanner.nextLine();

            System.out.println("Seleziona tipologia del punto emissione:");
            for (TipologiaPuntoEmissione t : TipologiaPuntoEmissione.values()) {
                System.out.println(t.ordinal() + 1 + ". " + t);
            }
            int sceltaTipo = Integer.parseInt(scanner.nextLine());
            TipologiaPuntoEmissione tipologia = TipologiaPuntoEmissione.values()[sceltaTipo - 1];

            System.out.println("Il punto emissione è attivo? (true/false):");
            boolean attivo = Boolean.parseBoolean(scanner.nextLine());

            PuntoEmissione nuovoPunto = new PuntoEmissione(citta, tipologia, attivo);

            puntiDAO.salva(nuovoPunto);

            System.out.println("Punto emissione creato con successo! ID: " + nuovoPunto.getPuntoEmissioneId());

        } catch (Exception e) {
            System.out.println("Errore durante la creazione del punto emissione: " + e.getMessage());
        }
    }

    public static void creaTessera(EntityManager em, Scanner scanner) {
        try {
            TessereDAO tessereDAO = new TessereDAO(em);
            PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);
            UtentiDAO utentiDAO = new UtentiDAO(em);

            System.out.println("Inserisci ID utente:");
            String idUtente = scanner.nextLine();
            Utente utente = utentiDAO.trovaPerId(idUtente);

            System.out.println("Inserisci ID punto emissione:");
            String idPunto = scanner.nextLine();
            PuntoEmissione punto = puntiDAO.trovaPerId(idPunto);

            System.out.println("Inserisci prezzo tessera:");
            double prezzo = Double.parseDouble(scanner.nextLine());

            System.out.println("Inserisci data inizio tessera (YYYY-MM-DD):");
            String dataInizioStr = scanner.nextLine();
            LocalDate dataInizio = LocalDate.parse(dataInizioStr);

            Tessera tessera = new Tessera(punto, prezzo, utente, dataInizio);

            tessereDAO.salva(tessera);

            System.out.println("Tessera creata con successo!");
            System.out.println(tessera);

        } catch (Exception e) {
            System.out.println("Errore nella creazione della tessera: " + e.getMessage());
        }
    }

    public static void statisticheBiglietti(EntityManager em) {
        try {
            BigliettiDAO bigliettiDAO = new BigliettiDAO(em);

            System.out.println(" STATISTICHE BIGLIETTI ");

            int totaleBiglietti = bigliettiDAO.findAll().size();
            System.out.println("Totale biglietti emessi: " + totaleBiglietti);

            List<MezzoTrasporto> mezzi = em.createQuery("SELECT m FROM MezzoTrasporto m", MezzoTrasporto.class).getResultList();
            for (MezzoTrasporto mezzo : mezzi) {
                int vidimatiSuMezzo = bigliettiDAO.contaBigliettiVidimatiSuMezzoTrasporto(mezzo);
                System.out.println("Biglietti vidimati sul mezzo " + mezzo.getTipoMezzo() + " (" + mezzo.getMezzoDiTrasportoId() + "): " + vidimatiSuMezzo);
            }

            LocalDate oggi = LocalDate.now();
            LocalDate trentaGiorniFa = oggi.minusDays(30);
            int vidimatiUltimi30Giorni = bigliettiDAO.contaBigliettiVidimatiInPeriodo(trentaGiorniFa, oggi);
            System.out.println("Biglietti vidimati negli ultimi 30 giorni (" + trentaGiorniFa + " - " + oggi + "): " + vidimatiUltimi30Giorni);

        } catch (Exception e) {
            System.out.println("Errore durante il calcolo delle statistiche: " + e.getMessage());
        }
    }

    public static void statisticheAbbonamento(EntityManager em, Scanner scanner) {
        try {
            AbbonamentiDAO abbonamentiDAO = new AbbonamentiDAO(em);

            System.out.println("Inserisci ID abbonamento:");
            String idAbb = scanner.nextLine();

            Abbonamento abbonamento = abbonamentiDAO.trovaPerId(idAbb);

            boolean valido = abbonamentiDAO.abbonamentoValido(idAbb);

            System.out.println("Abbonamento trovato:");
            System.out.println(abbonamento);

            if (valido) {
                System.out.println("L'abbonamento è ATTIVO e valido.");
            } else {
                System.out.println("L'abbonamento NON è valido o è scaduto.");
            }

        } catch (Exception e) {
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
                case 1:
                    EntityManager em = entityManagerFactory.createEntityManager();
                    try {
                        deleteBiglietto(em, scanner);
                    } finally {
                        em.close();
                    }
                    break;

                case 2:
                    EntityManager em2 = entityManagerFactory.createEntityManager();
                    try {
                        deleteAbbonamento(em2, scanner);
                    } finally {
                        em2.close();
                    }
                    break;

                case 3:
                    EntityManager em3 = entityManagerFactory.createEntityManager();
                    try {
                        creaPuntoEmissione(em3, scanner);
                    } finally {
                        em3.close();
                    }
                    break;

                case 4:
                    EntityManager em4 = entityManagerFactory.createEntityManager();
                    try {
                        creaTessera(em4, scanner);
                    } finally {
                        em4.close();
                    }
                    break;

                case 5:
                    EntityManager em5 = entityManagerFactory.createEntityManager();
                    try {
                        statisticheBiglietti(em5);
                    } finally {
                        em5.close();
                    }
                    break;

                case 6:
                    EntityManager em6 = entityManagerFactory.createEntityManager();
                    try {
                        statisticheBiglietti(em6);
                    } finally {
                        em6.close();
                    }
                    break;
            }
        } while (scelta != 0);
    }
}


