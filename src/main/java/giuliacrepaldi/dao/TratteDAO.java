package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tratta.TrattaNonTrovataException;
import giuliacrepaldi.exceptions.tratta.TrattaSalvataggioException;
import giuliacrepaldi.exceptions.utente.UtenteNonTrovatoException;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

public class TratteDAO {
    private final EntityManager em;

    public TratteDAO(EntityManager em) {
        this.em = em;
    }


    //Metodi
    //1. save tratta
    public void salva(Tratta tratta) throws TrattaSalvataggioException {
        EntityTransaction transaction = em.getTransaction();
        
        try {
            
            transaction.begin();
            em.persist(tratta);
            transaction.commit();
            
        } catch (RuntimeException e) {
            transaction.rollback();
            throw new TrattaSalvataggioException(tratta);
        }
    }
    

    //Per cancellare la tratta è necessario cancellare prima la percorrenza collegata attraverso
    //il metodo deleteById apposito
//    public void deleteById(UUID trattaId) {
//        EntityTransaction transaction = em.getTransaction();
//        transaction.begin();
//        Tratta tratta = em.find(Tratta.class, trattaId);
//        if (tratta == null) throw new TrattaNonTrovataException(trattaId);
//        em.remove(tratta);
//        transaction.commit();
//        System.out.println("Tratta eliminata con successo!");
//    }

    //2. findById trova tratta
    public Tratta trovaPerId(String targetId) throws TrattaNonTrovataException, StringaUUIDNonValidaException {
        
        TypedQuery<Tratta> query = em.createQuery(
                "SELECT t FROM Tratta t WHERE t.trattaId = :targetId",
                Tratta.class
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
            throw new TrattaNonTrovataException(targetId, "ID");
        }
        
        
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


