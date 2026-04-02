package giuliacrepaldi.tests.cristian;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

    static Scanner scanner = new Scanner(System.in);

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-cristian");
    
    public static void main(String[] args) {


        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppTest di Cristian) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("GESTIONALE-TRASPORTO-PUBBLICO");
        


        entityManager.close();
        entityManagerFactory.close();
    }
}
