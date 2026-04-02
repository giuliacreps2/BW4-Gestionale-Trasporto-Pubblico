package giuliacrepaldi.dao;

import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneRimozioneException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneSalvataggioException;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

public class PuntiEmissioneDAO {

    private final EntityManager entityManager;

    public PuntiEmissioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Salva/aggiorna un punto emissione.
     */
    public void salva(PuntoEmissione puntoEmissione) throws PuntoEmissioneSalvataggioException {
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
    public PuntoEmissione trovaPerId(UUID targetId) throws PuntoEmissioneNonTrovatoException {
        return trovaPerId(targetId.toString());
    }

    
    /**
     * Trova un punto emissione per ID.
     */
    public PuntoEmissione trovaPerId(String targetId) throws PuntoEmissioneNonTrovatoException, StringaUUIDNonValidaException {

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
    public void findByIdAndDelete(UUID id) throws PuntoEmissioneNonTrovatoException, PuntoEmissioneRimozioneException {
        PuntoEmissione puntoEmissioneTrovato = this.trovaPerId(id);

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
    public void findByIdAndUpdate(UUID id, String nuovaCitta, boolean nuovoStato) throws PuntoEmissioneNonTrovatoException, PuntoEmissioneSalvataggioException {
        PuntoEmissione puntoEmissioneTrovato = this.trovaPerId(id);

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

    public List<PuntoEmissione> findAll() {
        Query query = entityManager.createQuery("SELECT p FROM PuntoEmissione p");
        List<PuntoEmissione> tuttiIPuntiDiEmissione = query.getResultList();
        return tuttiIPuntiDiEmissione;
    }
}