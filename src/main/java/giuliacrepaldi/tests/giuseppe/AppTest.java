package giuliacrepaldi.tests.giuseppe;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.UUID;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-giuseppe");
    
    public static void main(String[] args) {


        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppTest di Giuseppe) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // ****** DAO
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);
        BigliettiDAO bigliettiDAO = new BigliettiDAO(entityManager);
        AbbonamentiDAO abbonamentiDAO = new AbbonamentiDAO(entityManager);
        UtentiDAO utentiDAO = new UtentiDAO(entityManager);
        TessereDAO tessereDAO = new TessereDAO(entityManager);

        // PuntoEmissione puntoEmissione1 = new PuntoEmissione(
        //         "Roma",
        //         TipologiaPuntoEmissione.RIVENDITORE_AUTORIZZATO,
        //         true
        // );
        
        Utente utente1 = new Utente(
                "Giuseppe",
                "Tavella",
                29,
                "xyz@gmail.com"
        );
        
        // puntiEmissioneDAO.save(puntoEmissione1);
        // utentiDAO.save(utente1);
        
        // Utente utente1FromDB = utentiDAO.
        // PuntoEmissione puntoEmissione1FromDB = puntiEmissioneDAO.findById(UUID.fromString("ea8feaae-62d2-4f5e-a73f-13838321ec58"));
        //
        // System.out.println(puntoEmissione1FromDB);
        //
        //
        // Biglietto biglietto1 = new Biglietto(
        //         puntoEmissione1FromDB,
        //         23.45
        // );

        // Abbonamento abbonamento1 = new Abbonamento(
        //        puntoEmissione1FromDB,
        //         43.23
        // );
        
        //
        // bigliettiDAO.salva(biglietto1);
        

        entityManager.close();
        entityManagerFactory.close();
    }
}
