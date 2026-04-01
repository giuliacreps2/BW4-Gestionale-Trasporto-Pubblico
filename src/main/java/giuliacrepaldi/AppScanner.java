package giuliacrepaldi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// qui va la richiesta del progetto: solo lo scanner
public class AppScanner {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI");
    
    public static void main(String[] args) {

        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppScanner) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        
        
        
        entityManager.close();
        entityManagerFactory.close();
    }
}
