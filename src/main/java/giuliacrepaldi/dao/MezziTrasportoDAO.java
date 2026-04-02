package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoSalvataggioException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Type;
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
    public boolean eInManutenzione(MezzoTrasporto mezzoTrasporto){
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

    public boolean inServizio(MezzoTrasporto mezzoTrasporto) {
        return !eInManutenzione(mezzoTrasporto);
    }
}