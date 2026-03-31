package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.UUID;

public class TessereDAO {

    private final EntityManager em;

    public TessereDAO(EntityManager em){
        this.em = em;
    }

    public void save(Tessera newTessera) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            em.persist(newTessera);

            transaction.commit();

            System.out.println("La tessera " + newTessera.getId() + " è stata salvata correttamente!");

        } catch (Exception e) {
            throw new TesseraSalvataggioException(newTessera);
        }
    }

    public Tessera findTesseraValidaByAbbonamento(UUID abbonamentoId) {
        String jpql = "SELECT a.tessera FROM Abbonamento a " +
                "WHERE a.abbonamentoId = :id " +
                "AND CURRENT_DATE BETWEEN a.tessera.dataInizioTessera AND a.tessera.dataFineTessera";

        try {
            return em.createQuery(jpql, Tessera.class)
                    .setParameter("id", abbonamentoId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new TesseraNonTrovataException(abbonamentoId.toString(), "abbonamentoId");
        }
    }
}