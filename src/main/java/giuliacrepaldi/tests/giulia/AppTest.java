package giuliacrepaldi.tests.giulia;

import giuliacrepaldi.dao.MezziTrasportoDAO;
import giuliacrepaldi.dao.PercorrenzeDAO;
import giuliacrepaldi.dao.TratteDAO;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.Percorrenza;
import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;

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

        //Mezzi
        MezzoTrasporto mezzo1 = new MezzoTrasporto(TipoMezzo.TRAM);
        MezzoTrasporto mezzo2 = new MezzoTrasporto(TipoMezzo.AUTOBUS);

        //Tratte
        Tratta tratta1 = new Tratta(12.5, 40L, "Piazza Duomo", "Stazione Centrale");
        Tratta tratta2 = new Tratta(7.3, 25L, "Porta Romana", "Navigli");

        //Percorrenze
        Percorrenza percorrenza1 = new Percorrenza(38L, LocalDateTime.of(2026, 3, 15, 8, 30), tratta1, mezzo1);
        Percorrenza percorrenza2 = new Percorrenza(27L, LocalDateTime.of(2026, 3, 15, 9, 0), tratta2, mezzo2);


        // ****** DAO

        mezzoTrasportoDAO.save(mezzo1);
        mezzoTrasportoDAO.save(mezzo2);
        tratteDAO.saveTratta(tratta1);
        tratteDAO.saveTratta(tratta2);
        percorrenzeDAO.savePercorrenza(percorrenza1);
        percorrenzeDAO.savePercorrenza(percorrenza2);


        entityManager.close();
        entityManagerFactory.close();
    }
}
