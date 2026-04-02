package giuliacrepaldi.tests.giulia;

import giuliacrepaldi.dao.ManutenzioniDAO;
import giuliacrepaldi.dao.MezziTrasportoDAO;
import giuliacrepaldi.dao.PercorrenzeDAO;
import giuliacrepaldi.dao.TratteDAO;
import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.Percorrenza;
import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-giulia");

    public static void main(String[] args) {


        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppTest di Giulia) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        MezziTrasportoDAO mezzoTrasportoDAO = new MezziTrasportoDAO(entityManager);
        TratteDAO tratteDAO = new TratteDAO(entityManager);
        PercorrenzeDAO percorrenzeDAO = new PercorrenzeDAO(entityManager);
        ManutenzioniDAO manutenzioniDAO = new ManutenzioniDAO(entityManager);

        //Mezzi
        MezzoTrasporto mezzo1 = new MezzoTrasporto(TipoMezzo.TRAM);
        MezzoTrasporto mezzo2 = new MezzoTrasporto(TipoMezzo.AUTOBUS);

        //Tratte
        Tratta tratta1 = new Tratta(12.5, 40L, "Piazza Duomo", "Stazione Centrale");
        Tratta tratta2 = new Tratta(7.3, 25L, "Porta Romana", "Navigli");

        //Percorrenze
        Percorrenza percorrenza1 = new Percorrenza(38L, LocalDateTime.of(2026, 3, 15, 8, 30), tratta1, mezzo1);
        Percorrenza percorrenza2 = new Percorrenza(27L, LocalDateTime.of(2026, 3, 15, 9, 0), tratta2, mezzo2);

        MezzoTrasporto mezzoT1 = mezzoTrasportoDAO.findByiD(UUID.fromString("104c146f-24b0-45f5-b5c2-422e606b1ed5"));
        Manutenzione manutenzione1 = new Manutenzione(mezzoT1, LocalDate.of(2026, 1, 15), LocalDate.of(2026, 2, 18), 30);
        //Manutenzione manutenzione2 = new Manutenzione(mezzo2, LocalDate.of(2025, 12, 15), LocalDate.of(2026, 1, 18), 230);
        // ****** DAO

//        mezzoTrasportoDAO.save(mezzo1);
//        mezzoTrasportoDAO.save(mezzo2);
//        tratteDAO.saveTratta(tratta1);
//        tratteDAO.saveTratta(tratta2);
//        percorrenzeDAO.savePercorrenza(percorrenza1);
//        percorrenzeDAO.savePercorrenza(percorrenza2);
//
//        percorrenzeDAO.getTempoMedioEffettivo(UUID.fromString("30c1bee1-ceab-4873-85dd-7d88d56e6ebc"), UUID.fromString("389d2efe-1404-4f49-a865-4ef3be8f665f"));
//        percorrenzeDAO.countByMezzoAndTratta(UUID.fromString("30c1bee1-ceab-4873-85dd-7d88d56e6ebc"), UUID.fromString("389d2efe-1404-4f49-a865-4ef3be8f665f"), LocalDateTime.of(2026, 3, 15, 8, 30), LocalDateTime.of(2026, 3, 15, 15, 30));
//        percorrenzeDAO.findPercorrenzaById(UUID.fromString("b3020f15-de53-40b2-bc97-c46f6200e5f9"));
//        percorrenzeDAO.findPercorrenzaByMezzo(UUID.fromString("e4385a13-a720-4090-a747-ff30117a743a"));
//        percorrenzeDAO.findPercorrenzaByTratta(UUID.fromString("b65152a2-782e-4402-8dcd-71de776baef0"));

        // tratteDAO.findTrattaById(UUID.fromString("b65152a2-782e-4402-8dcd-71de776baef0"));
//        tratteDAO.findByZonaPartenza("Piaz");
//        tratteDAO.findAll();

//        tratta1.setZonaPartenza("Viale Redi");
//        tratta1.setTrattaKm(15.0);
//        tratteDAO.update(tratta1);


        manutenzioniDAO.save(manutenzione1);
        //manutenzioniDAO.save(manutenzione2);

        //System.out.println("UUID tratta1: " + tratta1.getTrattaId());
        //tratteDAO.deleteById(UUID.fromString("35ff4907-0f3b-44b1-b254-931efb755929"));


        entityManager.close();
        entityManagerFactory.close();
    }
}
