package giuliacrepaldi.dao;

import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MezziTrasportoDAO {

    private final EntityManager em;

    public MezziTrasportoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(MezzoTrasporto newMezzoTrasporto) throws MezzoTrasportoSalvataggioException {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(newMezzoTrasporto);

            transaction.commit();
            System.out.println("Il mezzo di trasporto è stato salvato con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new MezzoTrasportoSalvataggioException(newMezzoTrasporto);
        }
    }
}
