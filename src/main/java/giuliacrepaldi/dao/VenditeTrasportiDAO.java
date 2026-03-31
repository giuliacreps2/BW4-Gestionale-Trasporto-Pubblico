package giuliacrepaldi.dao;

import giuliacrepaldi.entities.VenditaTrasporto;
import giuliacrepaldi.exceptions.vendita_trasporto.VenditaTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VenditeTrasportiDAO {

    private final EntityManager entityManager;
    
    public VenditeTrasportiDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public void salva(VenditaTrasporto venditaTrasporto) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            entityManager.persist(venditaTrasporto);
            transaction.commit();

        } catch (RuntimeException ex) {
            transaction.rollback();
            throw new VenditaTrasportoSalvataggioException(venditaTrasporto);
        }
        
    }
    
    
    
}
