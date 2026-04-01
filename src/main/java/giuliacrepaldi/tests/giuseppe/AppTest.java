package giuliacrepaldi.tests.giuseppe;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
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

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-giuseppe");
    
    public static void main(String[] args) {


        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppTest di Giuseppe) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // ****** DAO
        AbbonamentiDAO abbonamentiDAO = new AbbonamentiDAO(entityManager);
        BigliettiDAO bigliettiDAO = new BigliettiDAO(entityManager);
        ManutenzioniDAO manutenzioniDAO = new ManutenzioniDAO(entityManager);
        MezziTrasportoDAO mezziTrasportoDAO = new MezziTrasportoDAO(entityManager);
        PercorrenzeDAO percorrenzeDAO = new PercorrenzeDAO(entityManager);
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);
        TessereDAO tessereDAO = new TessereDAO(entityManager);
        TratteDAO tratteDAO = new TratteDAO(entityManager);
        UtentiDAO utentiDAO = new UtentiDAO(entityManager);
        VenditeTrasportiDAO venditeTrasportiDAO = new VenditeTrasportiDAO(entityManager);

        PuntoEmissione puntoEmissione1 = new PuntoEmissione(
                "Roma",
                TipologiaPuntoEmissione.RIVENDITORE_AUTORIZZATO,
                true
        );
        
        Utente utente1 = new Utente(
                "Giuseppe",
                "Tavella",
                29,
                "xyz@gmail.com"
        );
        
        
        // puntiEmissioneDAO.save(puntoEmissione1);
        // utentiDAO.save(utente1);
        
        Utente utente1FromDB = utentiDAO.trovaPerId("f216c1c9-d335-4859-9315-ca82984bd8dc");
        PuntoEmissione puntoEmissione1FromDB = puntiEmissioneDAO.findById(UUID.fromString("908d932c-e9ab-4c0a-9d04-929f3d7897bd"));
        // PuntoEmissione puntoEmissione1FromDB = puntiEmissioneDAO.findById(UUID.fromString("ea8feaae-62d2-4f5e-a73f-13838321ec58"));
        //
        // System.out.println(puntoEmissione1FromDB);
        // System.out.println(utente1FromDB);

        // System.out.println(puntoEmissione1FromDB);

        // Tessera
        Tessera tessera1 = new Tessera(
                puntoEmissione1FromDB,
            45.231,
            utente1FromDB, 
                LocalDate.now()
        );

        // System.out.println(tessera1);
        
        // try {
        //     tessereDAO.save(tessera1);
        // } catch (RuntimeException ex) {
        //     if(ex instanceof TesseraSalvataggioException) {
        //         System.out.println(ex.getMessage());
        //     }
        // }
        //
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
