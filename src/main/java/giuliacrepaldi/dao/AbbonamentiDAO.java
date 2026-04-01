package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Abbonamento;
import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoNonTrovatoException;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoSalvataggioException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.UUID;

public class AbbonamentiDAO {

    private final EntityManager em;

    public AbbonamentiDAO(EntityManager em){
        this.em = em;
    }

    public void save(Abbonamento newAbbonamento) throws AbbonamentoSalvataggioException{
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(newAbbonamento);

            transaction.commit();

        } catch (Exception e){
            throw new AbbonamentoSalvataggioException(newAbbonamento);
        }
    }

    public Abbonamento findAbbonamentoById(String abbonamentoId) throws AbbonamentoNonTrovatoException, StringaUUIDNonValidaException{
        Abbonamento found;

        try {
            found = em.find(Abbonamento.class, UUID.fromString(abbonamentoId));
        } catch (IllegalArgumentException e){
             throw new StringaUUIDNonValidaException(abbonamentoId);
        }
        if (found == null){
            throw new AbbonamentoNonTrovatoException(abbonamentoId, "id");
        }
        return found;
    }

    public boolean abbonamentoValido(String abbonamentoId) {
        Abbonamento abbonamento = findAbbonamentoById(abbonamentoId);

        String jpql = "SELECT a FROM Abbonamento a " +
                "WHERE a.id = :id " +
                "AND CURRENT_DATE BETWEEN a.tessera.dataInizioTessera AND a.tessera.dataFineTessera";

        try {
            em.createQuery(jpql, Abbonamento.class)
                    .setParameter("id", abbonamentoId.getId())
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
