package giuliacrepaldi.dao;

import jakarta.persistence.EntityManager;

public class TratteDAO {
    private final EntityManager entityManager;

    public TratteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
