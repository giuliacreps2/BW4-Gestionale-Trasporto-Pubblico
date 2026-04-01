package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.exceptions.tratta.TrattaNonTrovataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;
import java.util.UUID;

public class TratteDAO {
    private final EntityManager em;

    public TratteDAO(EntityManager em) {
        this.em = em;
    }


    //Metodi
    //1. save tratta
    public void saveTratta(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tratta);
        transaction.commit();
        System.out.println("Tratta salvata con successo!");
    }

    public void update(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(tratta);
        transaction.commit();
    }

    public void delete(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(tratta) ? tratta : em.merge(tratta));
        transaction.commit();
    }

    //2. findById trova tratta
    public Tratta findTrattaById(UUID trattaId) {
        if (trattaId == null) throw new TrattaNonTrovataException(trattaId);
        Tratta found = em.find(Tratta.class, UUID.fromString(String.valueOf(trattaId)));
        if (found == null) throw new TrattaNonTrovataException(trattaId);
        return found;
    }

    //3. findByZonaPartenza per cercare le tratte
    public List<Tratta> findByZonaPartenza(String zonaPartenza) {
        Query query = em.createQuery("SELECT t FROM Tratta t WHERE LOWER(t.zonaPartenza) LIKE LOWER(:zona)");
        query.setParameter("zona", "%" + zonaPartenza + "%");
        List<Tratta> zonePartenza = query.getResultList();
        return zonePartenza;
    }


    //4. findAll per cercare tutte le tratte disponibili
    public List<Tratta> findAll() {
        Query query = em.createQuery("SELECT t FROM Tratta t");
        List<Tratta> tutteLeTratte = query.getResultList();
        return tutteLeTratte;
    }

}


