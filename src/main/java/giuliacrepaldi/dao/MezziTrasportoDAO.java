package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonTrovatoException;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MezziTrasportoDAO {

    private final EntityManager em;

    public MezziTrasportoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(MezzoTrasporto newMezzoTrasporto) throws MezzoTrasportoSalvataggioException {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(newMezzoTrasporto);

            transaction.commit();
            System.out.println("Il mezzo di trasporto è stato salvato con successo");
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
        // TODO: verificare che il mezzo esista
        LocalDate today = LocalDate.now();

        TypedQuery<Integer> query = em.createQuery("SELECT COUNT(m) FROM Manutenzione m " +
                "WHERE m.mezzoTrasporto = :mezzoTrasporto " +
                "AND :today BETWEEN m.dataInizioManutenzione AND m.dataFineManutenzione", Integer.class);

        query.setParameter("mezzoTrasporto", mezzoTrasporto);
        query.setParameter("today", today);
        Integer quanteManutenzioni = query.getSingleResult();

        return quanteManutenzioni > 0;
    }

    //3.boolean inServizio(UUID mezzoId)---> non in eInManutenzione()
    public boolean inServizio(MezzoTrasporto mezzoTrasporto) {
        return !eInManutenzione(mezzoTrasporto);
    }

    //4. findById
    public MezzoTrasporto findByiD(UUID mezzoDiTrasportoId) {
        try {
            MezzoTrasporto found = em.find(MezzoTrasporto.class, mezzoDiTrasportoId);
            System.out.println("Mezzo di trasporto con id: " + mezzoDiTrasportoId + " , è stato trovato con successo!");
            return found;
        } catch (NoResultException e) {
            throw new MezzoTrasportoNonTrovatoException(mezzoDiTrasportoId);
        }
    }
}