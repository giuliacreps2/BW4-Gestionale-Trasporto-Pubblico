package giuliacrepaldi.tests.cristian;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipoAbbonamento;
import giuliacrepaldi.enums.TipoMezzo;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AppScanner {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-cristian");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        BigliettiDAO bd = new BigliettiDAO(em);
        PuntiEmissioneDAO ped = new PuntiEmissioneDAO(em);
        AbbonamentiDAO ad = new AbbonamentiDAO(em);
        UtentiDAO ud = new UtentiDAO(em);
        TessereDAO td = new TessereDAO(em);
        MezziTrasportoDAO md = new MezziTrasportoDAO(em);

        PuntoEmissione p1 = new PuntoEmissione("Napoli", TipologiaPuntoEmissione.DISTRIBUTORE_AUTOMATICO, true);
//        ped.salva(p1);

        PuntoEmissione p1DB = ped.trovaPerId("d2c20c0c-1068-4e3f-b094-a65ffd5cffb4");
//
        Biglietto b1 = new Biglietto(p1DB, 20.50);
//        bd.salva(b1);

        Utente u1 = new Utente("Cristian", "Cicale", 20, "cicacri");
//        ud.salva(u1);
        Utente u1DB = ud.trovaPerId("49659281-0070-43ff-9183-deb559eb5309");

        Tessera t1 = new Tessera(p1DB, 10, u1DB, LocalDate.now());
//        td.salva(t1);
        Tessera t1DB = td.trovaPerId("795b384c-0aa6-498d-8929-64951c3e4c4a");

        Abbonamento a1 = new Abbonamento(p1DB, 30, t1DB, TipoAbbonamento.SETTIMANALE);
//        ad.salva(a1);

        MezzoTrasporto m1 = new MezzoTrasporto(TipoMezzo.TRAM);
//        md.salva(m1);

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
            System.out.println(" PUNTI EMISSIONE DISPONIBILI ");
            puntiDAO.findAll().forEach(p ->
                    System.out.println(p.getPuntoEmissioneId() + " - " + p.getTipologiaPuntoEmissione())
            );

            System.out.print("Inserisci ID punto emissione: ");
            UUID idPunto = UUID.fromString(scanner.nextLine());

            PuntoEmissione punto;

            try {
                punto = puntiDAO.trovaPerId(idPunto);
            } catch (PuntoEmissioneNonTrovatoException e) {
                System.out.println("Punto emissione non trovato!");
                return;
            }

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
                long vidimatiSuMezzo = bigliettiDAO.contaBigliettiVidimatiSuMezzoTrasporto(mezzo);
                System.out.println("Biglietti vidimati sul mezzo " + mezzo.getTipoMezzo() + " (" + mezzo.getMezzoDiTrasportoId() + "): " + vidimatiSuMezzo);
            }

            LocalDate oggi = LocalDate.now();
            LocalDate trentaGiorniFa = oggi.minusDays(30);
            long vidimatiUltimi30Giorni = bigliettiDAO.contaBigliettiVidimatiInPeriodo(trentaGiorniFa, oggi);
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

    public static void statistichePuntoEmissione(EntityManager em, Scanner scanner) {
        try {
            PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);

            System.out.println("Inserisci ID del Punto Emissione:");
            String idPunto = scanner.nextLine();

            PuntoEmissione punto = puntiDAO.trovaPerId(idPunto);

            System.out.println("Statistiche Punto Emissione:");
            System.out.println("Città: " + punto.getCitta());
            System.out.println("Tipologia: " + punto.getTipologiaPuntoEmissione());
            System.out.println("Stato: " + (punto.isAttivo() ? "Attivo" : "Disattivo"));

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static void statisticheMezzoTrasporto(EntityManager em, Scanner scanner) {
        try {
            MezziTrasportoDAO mezziDAO = new MezziTrasportoDAO(em);

            System.out.println("Inserisci ID del Mezzo di Trasporto:");
            String idMezzo = scanner.nextLine();

            MezzoTrasporto mezzo = mezziDAO.trovaPerId(idMezzo);

            boolean inManutenzione = mezziDAO.eInManutenzione(mezzo);
            boolean inServizio = mezziDAO.inServizio(mezzo);

            List<Manutenzione> manutenzioni = mezziDAO.findAllManutenzioneDiMezzo(mezzo);

            System.out.println("Statistiche Mezzo Trasporto:");
            System.out.println("Tipo Mezzo: " + mezzo.getTipoMezzo());
            System.out.println("Stato: " + (inServizio ? "In Servizio" : "In Manutenzione"));
            System.out.println("Manutenzioni:");
            if (manutenzioni.isEmpty()) {
                System.out.println("  Nessuna manutenzione registrata");
            } else {
                for (Manutenzione m : manutenzioni) {
                    System.out.println("  - Da " + m.getDataInizioManutenzione() + " a " + m.getDataFineManutenzione());
                }
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
                        statisticheAbbonamento(em6, scanner);
                    } finally {
                        em6.close();
                    }
                    break;

                case 7:
                    EntityManager em7 = entityManagerFactory.createEntityManager();
                    try {
                        statistichePuntoEmissione(em7, scanner);
                    } finally {
                        em7.close();
                    }
                    break;

                case 8:
                    EntityManager em8 = entityManagerFactory.createEntityManager();
                    try {
                        statisticheMezzoTrasporto(em8, scanner);
                    } finally {
                        em8.close();
                    }
                    break;
            }
        } while (scelta != 0);
    }
}