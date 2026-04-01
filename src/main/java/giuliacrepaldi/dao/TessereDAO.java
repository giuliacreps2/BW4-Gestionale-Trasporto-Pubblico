package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TessereDAO {

    private final EntityManager em;

    public TessereDAO(EntityManager em){
        this.em = em;
    }

    public void save(Tessera newTessera) throws TesseraSalvataggioException {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            em.persist(newTessera);

            transaction.commit();
        } catch (Exception e) {
            throw new TesseraSalvataggioException(newTessera);
        }
    }
}