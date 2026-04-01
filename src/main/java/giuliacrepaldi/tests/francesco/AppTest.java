package giuliacrepaldi.tests.francesco;



import giuliacrepaldi.dao.PuntiEmissioneDAO;
import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.UUID;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-francesco");
    
    public static void main(String[] args) {


        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppTest di Francesco) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);


        PuntoEmissione puntoEmissione = new PuntoEmissione(
                "Roma",
                TipologiaPuntoEmissione.DISTRIBUTORE_AUTOMATICO,
                true
        );


        puntiEmissioneDAO.save(puntoEmissione);


        UUID idSalvato = puntoEmissione.getPuntoEmissioneId();
        System.out.println("ID salvato: " + idSalvato);


        PuntoEmissione puntoTrovato = puntiEmissioneDAO.findById(idSalvato);
        System.out.println("Punto trovato: " + puntoTrovato);


        puntiEmissioneDAO.findByIdAndUpdate(idSalvato, "Milano", false);

        PuntoEmissione puntoAggiornato = puntiEmissioneDAO.findById(idSalvato);
        System.out.println("Punto aggiornato: " + puntoAggiornato);


        puntiEmissioneDAO.findByIdAndDelete(idSalvato);

        PuntoEmissione puntoEliminato = puntiEmissioneDAO.findById(idSalvato);
        System.out.println("Dopo eliminazione: " + puntoEliminato);


        // ****** DAO
        


        entityManager.close();
        entityManagerFactory.close();
    }
}
