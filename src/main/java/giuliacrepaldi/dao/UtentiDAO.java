package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import giuliacrepaldi.exceptions.utente.UtenteNonTrovatoException;
import giuliacrepaldi.exceptions.utente.UtenteSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.UUID;

public class UtentiDAO {

    private final EntityManager entityManager;

    public UtentiDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Aggiungi/aggiorna un utente.
     */
    public void save(Utente newUtente) throws UtenteSalvataggioException{
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            
            transaction.begin();
            entityManager.persist(newUtente);
            transaction.commit();
        
        } catch (RuntimeException e){
            throw new UtenteSalvataggioException(newUtente);
        }
    }

    /**
     * Trova un utente per il suo ID.
     */
    public Utente trovaPerId(String targetId) throws UtenteNonTrovatoException, StringaUUIDNonValidaException {
        
        TypedQuery<Utente> query = entityManager.createQuery(
                "SELECT u FROM Utente u WHERE u.utenteId = :targetId",
                Utente.class
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
            throw new UtenteNonTrovatoException(targetId, "ID");
        }
        
    }

    /**
     * Verifica se la tessera di quest'utente esiste già o no.
     */
    public boolean utenteHaTessera(Utente utente)  {
        
        TypedQuery<Boolean> query = entityManager.createQuery(
                "SELECT " +
                        "(COUNT(t) > 0) " +
                        "   AS esiste_tessera " + 
                        "FROM Tessera t " +
                        "WHERE t.utente = :utente",
                Boolean.class
        );

        // pass query params
        query.setParameter("utente", utente);

        // execute query
        return query.getSingleResult();
    }
    
}
