package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Abbonamento;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoNonTrovatoException;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoRimozioneException;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoSalvataggioException;
import giuliacrepaldi.exceptions.biglietto.BigliettoNonTrovatoException;
import giuliacrepaldi.exceptions.biglietto.BigliettoRimozioneException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.UUID;

public class AbbonamentiDAO {

    private final EntityManager em;

    public AbbonamentiDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(Abbonamento newAbbonamento) throws AbbonamentoSalvataggioException {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(newAbbonamento);

            transaction.commit();

        } catch (Exception e) {
            throw new AbbonamentoSalvataggioException(newAbbonamento);
        }
    }

    public Abbonamento trovaPerId(String abbonamentoId) throws AbbonamentoNonTrovatoException, StringaUUIDNonValidaException {
        Abbonamento found;

        try {
            found = em.find(Abbonamento.class, UUID.fromString(abbonamentoId));
        } catch (IllegalArgumentException e) {
            throw new StringaUUIDNonValidaException(abbonamentoId);
        }
        if (found == null) {
            throw new AbbonamentoNonTrovatoException(abbonamentoId, "id");
        }
        return found;
    }

    /**
     * Verifica se un abbonamento è valido (si/no).
     */
    public boolean abbonamentoValido(String abbonamentoId) throws AbbonamentoNonTrovatoException, StringaUUIDNonValidaException {
        Abbonamento abbonamento = trovaPerId(abbonamentoId);

        String jpql = "SELECT a FROM Abbonamento a " +
                "WHERE a.venditaTrasportoId = :venditaTrasportoId " +
                "AND CURRENT_DATE BETWEEN a.tessera.dataInizioTessera AND a.tessera.dataFineTessera";

        try {
            em.createQuery(jpql, Abbonamento.class)
                    .setParameter("venditaTrasportoId", abbonamento.getVenditaTrasportoId())
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public List<Abbonamento> findAll() {
        Query query = em.createQuery("SELECT a FROM Abbonamento a");
        List<Abbonamento> tuttiGliAbbonamenti = query.getResultList();
        return tuttiGliAbbonamenti;
    }

    /**
     * Rimuovi un abbonamento per ID.
     */
    public void rimuoviPerId(String targetId) throws AbbonamentoNonTrovatoException, AbbonamentoRimozioneException, StringaUUIDNonValidaException {

        EntityTransaction transaction = em.getTransaction();

        Query query = em.createQuery(
                "DELETE FROM Abbonamento a WHERE a.venditaTrasportoId = :targetId"
        );

        // pass query params
        try {

            query.setParameter("targetId", UUID.fromString(targetId));

        } catch (IllegalArgumentException ex) {
            throw new StringaUUIDNonValidaException(targetId);
        }

        // execute query
        try {

            transaction.begin();
            int affectedRows = query.executeUpdate();

            if (affectedRows == 0) {
                throw new AbbonamentoNonTrovatoException(targetId, "ID");
            }

            transaction.commit();

        } catch (RuntimeException ex) {
            transaction.rollback();
            if (ex instanceof AbbonamentoNonTrovatoException) {
                throw ex;
            }
            throw new AbbonamentoRimozioneException(targetId, "ID");
        }

    }
}