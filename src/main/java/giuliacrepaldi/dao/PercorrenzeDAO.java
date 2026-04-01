package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Percorrenza;
import giuliacrepaldi.exceptions.tratta.TrattaNonTrovata;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PercorrenzeDAO {
    private final EntityManager em;

    public PercorrenzeDAO(EntityManager em) {
        this.em = em;
    }

    // Un amministratore del sistema deve poter calcolare il tempo medio effettivo di percorrenza
    // di una tratta da parte di un mezzo.
    //TEMPO MEDIO EFFETTIVO

    //Metodi

    //1. save(Percorrenza p)
    public void savePercorrenza(Percorrenza percorrenza) {
        EntityTransaction transaction = em.getTransaction();
        em.persist(percorrenza);
        transaction.commit();
        System.out.println("Percorrenza salvata con successo");
    }

    //2. findById(UUID id)
    public Percorrenza findPercorrenzaById(UUID percorrenzaId) {
        Percorrenza found = em.find(Percorrenza.class, UUID.fromString(String.valueOf(percorrenzaId)));
        if (found == null) throw new TrattaNonTrovata(percorrenzaId);
        return found;
    }

    //3. findByMezzo(UUID mezzoId)
    public List<Percorrenza> findPercorrenzaByMezzo(UUID Id) {
        if (Id == null) {
            System.out.println("Percorrenza non trovata");
            return null;
        }
        Query query = em.createQuery("SELECT p FROM Percorrenza p WHERE p.mezzoTrasportoId.Id", Percorrenza.class);
        query.setParameter("Id", Id);
        List<Percorrenza> percorrenzeMezzi = query.getResultList();
        return percorrenzeMezzi;
    }

    //4. findByTratta(UUID trattaId)
    public List<Percorrenza> findPercorrenzaByTratta(UUID trattaId) {
        if (trattaId == null) {
            System.out.println("Percorrenza non trovata");
            return null;
        }
        Query query = em.createQuery("SELECT p FROM Percorrenza p WHERE p.trattaId.trattaId", Percorrenza.class);
        query.setParameter("trattaId", trattaId);
        List<Percorrenza> percorrenzeTratte = query.getResultList();
        return percorrenzeTratte;
    }

    //5. findByMezzoAndTratta(UUID mezzoId, UUID trattaId)
//    public List<Percorrenza> findByMezzoAndTratta(UUID mezzoId, UUID trattaId) {
//
//    }


    //6. countByMezzoAndTratta(UUID mezzoId, UUID trattaId) --->  quante volte un mezzo ha percorso una tratta
    public Long countByMezzoAndTratta(UUID Id, UUID trattaId, LocalDateTime dataInizio, LocalDateTime dataFine) {
        if (trattaId == null) {
            System.out.println("Tratta non trovata");
            return null;
        }
        if (Id == null) {
            System.out.println("Mezzo non trovato");
            return null;
        }
        Query query = em.createQuery("SELECT COUNT(p) FROM Percorrenza p WHERE p.mezzoTrasportoId.Id AND p.trattaId.trattaId AND p.dataPercorrenza BETWEEN :dataInizio AND :dataFine ");
        query.setParameter("Id", Id);
        query.setParameter("trattaId", trattaId);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        Long volte = (Long) query.getSingleResult();
        return volte;
    }

    //7. getTempoMedioEffettivo(UUID mezzoId, UUID trattaId) ---> calcolare il tempo di una percorrenza
    //dato un mezzo x
    //data una tratta y
    //calcola il tempo medio effettivo di una percorrenza

    
}
