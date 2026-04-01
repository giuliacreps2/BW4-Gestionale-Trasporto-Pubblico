package giuliacrepaldi.tests.giuseppe;

import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.VenditaTrasporto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-giuseppe");
    
    public static void main(String[] args) {


        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppTest di Giuseppe) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // ****** DAO

        // Biglietto biglietto1 = new Biglietto();

        System.out.println(LocalDateTime.now());
        

        entityManager.close();
        entityManagerFactory.close();
    }
}
