package giuliacrepaldi.dao;

import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneRimozioneException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.UUID;

public class PuntiEmissioneDAO {

    private final EntityManager entityManager;

    public PuntiEmissioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Salva/aggiorna un punto emissione.
     */
    public void save(PuntoEmissione puntoEmissione) {
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            entityManager.persist(puntoEmissione);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw new PuntoEmissioneSalvataggioException(puntoEmissione);
        }
        
    }

    /**
     * Trova un punto emissione per ID.
     */
    public PuntoEmissione findById(UUID targetId) throws PuntoEmissioneNonTrovatoException {
        return findById(targetId.toString());
    }

    
    /**
     * Trova un punto emissione per ID.
     */
    public PuntoEmissione findById(String targetId) throws PuntoEmissioneNonTrovatoException, StringaUUIDNonValidaException {

        TypedQuery<PuntoEmissione> query = entityManager.createQuery(
                "SELECT p FROM PuntoEmissione p WHERE p.puntoEmissioneId = :targetId",
                PuntoEmissione.class
        );
        
        // pass params
        try {
            
            query.setParameter("targetId", UUID.fromString(targetId));

        } catch(IllegalArgumentException ex) {
            throw new StringaUUIDNonValidaException(targetId);
        }

        // execute query
        try {
            
            return query.getSingleResult();
            
        } catch (NoResultException ex) {
            throw new PuntoEmissioneNonTrovatoException(targetId, "ID");
        }

    }

    
    /**
     * Trova un punto emissione per ID e rimuovilo. 
     */
    public void findByIdAndDelete(UUID id) {
        PuntoEmissione puntoEmissioneTrovato = this.findById(id);

        if (puntoEmissioneTrovato == null) {
            throw new PuntoEmissioneNonTrovatoException(id);
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(puntoEmissioneTrovato);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw new PuntoEmissioneRimozioneException(id);
        }
    }

    /**
     * Trova un punto di emissione per ID e aggiorna la sua citta e stato.
     */
    public void findByIdAndUpdate(UUID id, String nuovaCitta, boolean nuovoStato) {
        PuntoEmissione puntoEmissioneTrovato = this.findById(id);

        if (puntoEmissioneTrovato == null) {
            throw new PuntoEmissioneNonTrovatoException(id);
        }

        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            
            transaction.begin();
            puntoEmissioneTrovato.setCitta(nuovaCitta);
            puntoEmissioneTrovato.setAttivo(nuovoStato);
            entityManager.persist(puntoEmissioneTrovato);
            transaction.commit();
            
        } catch (RuntimeException e) {
            transaction.rollback();
            throw new PuntoEmissioneSalvataggioException(puntoEmissioneTrovato);
        }
        
    }

    
    
}