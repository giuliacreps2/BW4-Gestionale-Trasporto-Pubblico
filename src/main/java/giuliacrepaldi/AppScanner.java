package giuliacrepaldi;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipoAbbonamento;
import giuliacrepaldi.enums.TipoMezzo;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

// qui va la richiesta del progetto: solo lo scanner
public class AppScanner {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        //Oggetti
        MezziTrasportoDAO mezzoTrasportoDAO = new MezziTrasportoDAO(em);
        TratteDAO tratteDAO = new TratteDAO(em);
        PercorrenzeDAO percorrenzeDAO = new PercorrenzeDAO(em);
        ManutenzioniDAO manutenzioniDAO = new ManutenzioniDAO(em);
        BigliettiDAO bd = new BigliettiDAO(em);
        PuntiEmissioneDAO ped = new PuntiEmissioneDAO(em);
        AbbonamentiDAO ad = new AbbonamentiDAO(em);
        UtentiDAO ud = new UtentiDAO(em);
        TessereDAO td = new TessereDAO(em);

        //Mezzi
        MezzoTrasporto mezzo1 = new MezzoTrasporto(TipoMezzo.TRAM);
        MezzoTrasporto mezzo2 = new MezzoTrasporto(TipoMezzo.AUTOBUS);

        //Tratte
        Tratta tratta1 = new Tratta(12.5, 40L, "Piazza Duomo", "Stazione Centrale");
        Tratta tratta2 = new Tratta(7.3, 25L, "Porta Romana", "Navigli");

        //Percorrenze
        Percorrenza percorrenza1 = new Percorrenza(38L, LocalDateTime.of(2026, 3, 15, 8, 30), tratta1, mezzo1);
        Percorrenza percorrenza2 = new Percorrenza(27L, LocalDateTime.of(2026, 3, 15, 9, 0), tratta2, mezzo2);

        //Manutenzioni
        Manutenzione manutenzione1 = new Manutenzione(mezzo1, LocalDate.of(2026, 1, 15), LocalDate.of(2026, 2, 18), 30);
        Manutenzione manutenzione2 = new Manutenzione(mezzo2, LocalDate.of(2025, 12, 15), LocalDate.of(2026, 1, 18), 230);

        //PuntoEmissione
        PuntoEmissione p1 = new PuntoEmissione("Napoli", TipologiaPuntoEmissione.DISTRIBUTORE_AUTOMATICO, true);
        PuntoEmissione p1DB = ped.trovaPerId("d2c20c0c-1068-4e3f-b094-a65ffd5cffb4");

        //Biglietti
        Biglietto b1 = new Biglietto(p1DB, 20.50);

        //Utenti
        Utente u1 = new Utente("Cristian", "Cicale", 20, "cicacri");
        Utente u1DB = ud.trovaPerId("49659281-0070-43ff-9183-deb559eb5309");

        //Tessere
        Tessera t1 = new Tessera(p1DB, 10, u1DB, LocalDate.now());
        Tessera t1DB = td.trovaPerId("795b384c-0aa6-498d-8929-64951c3e4c4a");

        //Abbonamenti
        Abbonamento a1 = new Abbonamento(p1DB, 30, t1DB, TipoAbbonamento.SETTIMANALE);

        
//        ped.salva(p1);
//        bd.salva(b1);
//        ud.salva(u1);
//        td.salva(t1);
//        ad.salva(a1);
//        mezzoTrasportoDAO.salva(mezzo1);
//        mezzoTrasportoDAO.salva(mezzo2);
//        tratteDAO.salva(tratta1);
//        tratteDAO.salva(tratta2);
//        percorrenzeDAO.salva(percorrenza1);
//        percorrenzeDAO.salva(percorrenza2);
//        manutenzioniDAO.salva(manutenzione1);
        //manutenziniDAO.salva(manutenzione2);


        System.out.println("SCEGLI MENU");
        System.out.println("1. Utente");
        System.out.println("2. Amministratore");

        int scelta = Integer.parseInt(scanner.nextLine());
        switch (scelta) {
            case 1:
                MenuUtente.mostraMenu(em, scanner);
                break;
            case 2:
                MenuAmministratore.mostraMenu(em, scanner);
                break;
            default:
                System.out.println("Scelta non valida");
        }

        em.close();
        emf.close();
    }
}
