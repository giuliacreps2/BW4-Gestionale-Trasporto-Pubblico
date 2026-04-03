package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonTrovatoException;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoSalvataggioException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MezziTrasportoDAO {

    private final EntityManager em;

    public MezziTrasportoDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(MezzoTrasporto newMezzoTrasporto) throws MezzoTrasportoSalvataggioException {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(newMezzoTrasporto);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new MezzoTrasportoSalvataggioException(newMezzoTrasporto);
        }
    }

    // un mezzo è in manutenzione se il conteggio delle sue manutenzioni in corso è > 0
    // un mezzo è in servizio se non è in manutenzione

    //1.Lista tutte le manutenzioni
    public List<Manutenzione> findAllManutenzioneDiMezzo(MezzoTrasporto mezzoTrasporto) {

        TypedQuery<Manutenzione> query = em.createQuery("SELECT man FROM Manutenzione man " +
                "WHERE man.mezzoTrasporto = :mezzoTrasporto", Manutenzione.class);

        query.setParameter("mezzoTrasporto", mezzoTrasporto);
        List<Manutenzione> manutenzioni = query.getResultList();
        return manutenzioni;
    }

    /**
     * dato un mezzo ritorna se il mezzo è in manutenzione oppure no
     */
    //2.boolean eInManutenzione(UUID mezzoId), dove somma manutenzioni in corso == 0
    public boolean eInManutenzione(MezzoTrasporto mezzoTrasporto) {
        LocalDate today = LocalDate.now();

        TypedQuery<Boolean> query = em.createQuery(
                "SELECT COUNT(man) > 0 FROM Manutenzione man " +
                "       WHERE man.mezzoTrasporto = :mezzoTrasporto " +
                "       AND :today BETWEEN man.dataInizioManutenzione AND man.dataFineManutenzione", 
                Boolean.class
        );

        query.setParameter("mezzoTrasporto", mezzoTrasporto);
        query.setParameter("today", today);
        
        Boolean haManutenzione = query.getSingleResult();

        return haManutenzione;
    }

    public boolean eInManutenzione(String mezzoTrasportoId) throws MezzoTrasportoNonTrovatoException, StringaUUIDNonValidaException {
        MezzoTrasporto mezzoTrasporto = trovaPerId(mezzoTrasportoId);
        return eInManutenzione(mezzoTrasporto);
    }

    //3.boolean inServizio(UUID mezzoId)---> non in eInManutenzione()
    public boolean inServizio(MezzoTrasporto mezzoTrasporto) {
        return !eInManutenzione(mezzoTrasporto);
    }

    public boolean inServizio(String mezzoTrasportoId) throws MezzoTrasportoNonTrovatoException, StringaUUIDNonValidaException {
        MezzoTrasporto mezzoTrasporto = trovaPerId(mezzoTrasportoId);
        return inServizio(mezzoTrasporto);
    }


    //4. findById
    public MezzoTrasporto trovaPerId(String targetId) throws MezzoTrasportoNonTrovatoException, StringaUUIDNonValidaException {
        try {
            return trovaPerId(UUID.fromString(targetId));
        } catch (IllegalArgumentException ex) {
            throw new StringaUUIDNonValidaException(targetId);
        }
    }

    public MezzoTrasporto trovaPerId(UUID mezzoDiTrasportoId) throws MezzoTrasportoNonTrovatoException {
        MezzoTrasporto found = em.find(MezzoTrasporto.class, mezzoDiTrasportoId);

        if (found == null) {
            throw new MezzoTrasportoNonTrovatoException(mezzoDiTrasportoId);
        }

        return found;
    }


    /**
     * Ottieni tutti i mezzi di trasporto.
     */
    public List<MezzoTrasporto> findAll() {
        TypedQuery<MezzoTrasporto> query = em.createQuery(
                "SELECT m FROM MezzoTrasporto m", 
                MezzoTrasporto.class
        );
        return query.getResultList();
    }
    
}