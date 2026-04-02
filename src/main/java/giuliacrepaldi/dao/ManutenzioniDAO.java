package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.exceptions.manutenzione.ManutenzioneDiMezzoNonEsisteException;
import giuliacrepaldi.exceptions.manutenzione.ManutenzioneNonTrovataException;
import giuliacrepaldi.exceptions.manutenzione.ManutenzioneSalvataggioException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class ManutenzioniDAO {
    private final EntityManager em;

    public ManutenzioniDAO(EntityManager em) {
        this.em = em;
    }

    //1. save Manutenzione
    public void save(Manutenzione newManutenzione) throws ManutenzioneSalvataggioException {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(newManutenzione);

            transaction.commit();
        } catch (Exception e) {
            throw new ManutenzioneSalvataggioException(newManutenzione);
        }
    }


    /**
     * Trova una manutenzione per il suo ID.
     */
    public Manutenzione trovaPerId(String targetId) throws ManutenzioneNonTrovataException, StringaUUIDNonValidaException {

        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.manutenzioneId = :targetId",
                Manutenzione.class
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
            throw new ManutenzioneNonTrovataException(targetId, "ID");
        }

    }
    

    //2.findAll con BETWEEN — manutenzioni in un periodo
    public List<Manutenzione> findAll(String mezzoID, LocalDate dataInizio, LocalDate dataFine) {
        try {
            UUID uuid = UUID.fromString(mezzoID);
            return em.createQuery("SELECT m FROM Manutenzione m " +
                            "WHERE m.mezzoTrasporto.mezzoDiTrasportoId = :mezzoID AND m.dataInizioManutenzione BETWEEN :dataInizio " +
                            "AND :dataFine", Manutenzione.class)
                    .setParameter("mezzoID", uuid)
                    .setParameter("dataInizio", dataInizio)
                    .setParameter("dataFine", dataFine)
                    .getResultList();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("UUID non valido " + mezzoID);
        }
    }


    //3.findManutenzioniInCorso(UUID mezzoId) ---> con data di riferimento oggi
    public List<Manutenzione> findManutenzioniInCorso(MezzoTrasporto mezzoTrasporto) throws ManutenzioneDiMezzoNonEsisteException {
        LocalDate today = LocalDate.now();

        TypedQuery<Manutenzione> query = em.createQuery("SELECT m FROM Manutenzione m " +
                        "WHERE m.mezzoTrasporto = :mezzoTrasporto " +
                        "AND :today BETWEEN m.dataInizioManutenzione AND m.dataFineManutenzione",
                Manutenzione.class);

        query.setParameter("mezzoTrasporto", mezzoTrasporto);
        query.setParameter("today", today);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            throw new ManutenzioneDiMezzoNonEsisteException(mezzoTrasporto);
        }

        //4.findByMezzo


    }


}
