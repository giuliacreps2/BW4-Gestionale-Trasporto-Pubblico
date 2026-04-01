package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
import giuliacrepaldi.exceptions.utente.UtenteNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.UUID;

public class TessereDAO {

    private final EntityManager entityManager;

    public TessereDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void save(Tessera newTessera) throws TesseraSalvataggioException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            entityManager.persist(newTessera);

            transaction.commit();
        } catch (RuntimeException e) {
            throw new TesseraSalvataggioException(newTessera, e.getMessage());
        }
    }

    /**
     * Trova una tessera per il suo ID.
     */
    public Tessera trovaPerId(String targetId) throws TesseraNonTrovataException, StringaUUIDNonValidaException {

        TypedQuery<Tessera> query = entityManager.createQuery(
                "SELECT t FROM Tessera t WHERE t.tesseraId = :targetId",
                Tessera.class
        );

        // pass query params
        try {

            query.setParameter("targetId", UUID.fromString(targetId));

        } catch(IllegalArgumentException ex) {
            throw new StringaUUIDNonValidaException(targetId);
        }

        // execute query
        try {

            return query.getSingleResult();

        } catch (NoResultException ex) {
            throw new TesseraNonTrovataException(targetId, "ID");
        }

    }
    
}