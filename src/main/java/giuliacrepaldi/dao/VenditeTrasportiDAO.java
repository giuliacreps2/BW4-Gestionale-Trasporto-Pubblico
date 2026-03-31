package giuliacrepaldi.dao;

import jakarta.persistence.EntityManager;

public class VenditeTrasportiDAO {

    private final EntityManager entityManager;
    
    public VenditeTrasportiDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    
    
}
