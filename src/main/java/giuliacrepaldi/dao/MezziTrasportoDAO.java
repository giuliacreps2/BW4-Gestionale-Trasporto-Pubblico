package giuliacrepaldi.dao;

import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MezziTrasportoDAO {

    private final EntityManager em;

    public MezziTrasportoDAO(EntityManager em){
        this.em = em;
    }

    public void save(MezzoTrasporto newMezzoTrasporto){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(newMezzoTrasporto);

        transaction.commit();

        System.out.println("Il mezzo di trsporto" + newMezzoTrasporto.getId() + "è stato salvato correttamente!");
    }
}
