package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.exceptions.manutenzione.ManutenzioneSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ManutenzioniDAO {
    private final EntityManager em;

    public ManutenzioniDAO(EntityManager em){
        this.em = em;
    }

    public void save(Manutenzione newManutenzione) throws ManutenzioneSalvataggioException{
        EntityTransaction transaction = em.getTransaction();

        try {
        transaction.begin();

        em.persist(newManutenzione);

        transaction.commit();
        } catch (Exception e){
            throw new ManutenzioneSalvataggioException(newManutenzione);
        }

    }
}
