package giuliacrepaldi.dao;

import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MezziTrasportoDAO {

    private final EntityManager em;

    public MezziTrasportoDAO(EntityManager em){
        this.em = em;
    }

    public void save(MezzoTrasporto newMezzoTrasporto) throws MezzoTrasportoSalvataggioException{
        EntityTransaction transaction = em.getTransaction();

        try {
        transaction.begin();

        em.persist(newMezzoTrasporto);

        transaction.commit();
        } catch (Exception e) {
            throw new MezzoTrasportoSalvataggioException(newMezzoTrasporto);
        }
    }
}
