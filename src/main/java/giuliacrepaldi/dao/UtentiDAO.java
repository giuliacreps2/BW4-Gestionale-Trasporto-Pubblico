package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.exceptions.utente.UtenteSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UtentiDAO {

    private final EntityManager em;

    public UtentiDAO(EntityManager em){
        this.em = em;
    }

    public void save(Utente newUtente) throws UtenteSalvataggioException{
        EntityTransaction transaction = em.getTransaction();

        try {
        transaction.begin();

        em.persist(newUtente);

        transaction.commit();

        System.out.println("L'utente" + newUtente.getId() + "è stato salvato correttamente!");
        } catch (Exception e){
            throw new UtenteSalvataggioException(newUtente);
        }
    }
}
