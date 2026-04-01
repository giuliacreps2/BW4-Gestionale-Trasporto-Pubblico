package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ManutenzioniDAO {
    private final EntityManager em;

    public ManutenzioniDAO(EntityManager em){
        this.em = em;
    }

    public void save(Manutenzione newManutenzione){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(newManutenzione);

        transaction.commit();

        System.out.println("La manutenzione" + newManutenzione.getId() + "è stata salvata correttamente!");
    }
}
