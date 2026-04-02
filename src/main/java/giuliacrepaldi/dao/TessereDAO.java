package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraGiaEsistenteException;
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

    /**
     * Aggiungi/aggiorna una tessera. 
     */
    public void salva(Tessera newTessera) throws TesseraSalvataggioException, TesseraGiaEsistenteException {
        
        // la tessera di questo utente esiste già 
        if(utenteHaTessera(newTessera.getUtente())) {
            throw new TesseraGiaEsistenteException(newTessera);
        }
        
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
                "SELECT t FROM Tessera t WHERE t.venditaTrasportoId = :targetId",
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