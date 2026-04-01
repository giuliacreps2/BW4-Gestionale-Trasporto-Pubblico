package giuliacrepaldi.dao;

import jakarta.persistence.EntityManager;

public class PercorrenzeDAO {
    private final EntityManager entityManager;

    public PercorrenzeDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
