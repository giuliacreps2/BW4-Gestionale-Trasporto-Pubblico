package giuliacrepaldi.tests.giuseppe;

import giuliacrepaldi.dao.BigliettiDAO;
import giuliacrepaldi.dao.PuntiEmissioneDAO;
import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.entities.VenditaTrasporto;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
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
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);
        BigliettiDAO bigliettiDAO = new BigliettiDAO(entityManager);

        PuntoEmissione puntoEmissione1 = new PuntoEmissione(
                "Roma",
                TipologiaPuntoEmissione.RIVENDITORE_AUTORIZZATO,
                true
        );
        
        // puntiEmissioneDAO.save(puntoEmissione1);
        
        
        
        
        Biglietto biglietto1 = new Biglietto(
                puntoEmissione1,
                23.45
        );
        
        // bigliettiDAO.salva(biglietto1);

        entityManager.close();
        entityManagerFactory.close();
    }
}
