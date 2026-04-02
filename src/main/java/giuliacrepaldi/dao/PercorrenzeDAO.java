package giuliacrepaldi.dao;

import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.Percorrenza;
import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonTrovatoException;
import giuliacrepaldi.exceptions.percorrenza.PercorrenzaNonTrovataException;
import giuliacrepaldi.exceptions.percorrenza.PercorrenzaSalvataggioException;
import giuliacrepaldi.exceptions.tratta.TrattaNonTrovataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PercorrenzeDAO {
    private final EntityManager em;

    public PercorrenzeDAO(EntityManager em) {
        this.em = em;
    }

    //Metodi

    //1. save(Percorrenza p)
    public void salva(Percorrenza percorrenza) {

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(percorrenza);
            transaction.commit();
            System.out.println("Percorrenza salvata con successo");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PercorrenzaSalvataggioException(percorrenza);
        }
    }

    //2. findById(UUID id)
    public Percorrenza trovaPerId(UUID percorrenzaId) {
        if (percorrenzaId == null) throw new PercorrenzaNonTrovataException(percorrenzaId);
        Percorrenza found = em.find(Percorrenza.class, percorrenzaId);
        if (found == null) throw new PercorrenzaNonTrovataException(percorrenzaId);
        System.out.println("Percorrenza con id: " + percorrenzaId + " trovata con successo");
        return found;
    }

    //3. findByMezzo(UUID mezzoId)
    public List<Percorrenza> findPercorrenzaByMezzo(UUID mezzoDiTrasportoId) {
        if (mezzoDiTrasportoId == null) throw new PercorrenzaNonTrovataException(mezzoDiTrasportoId);
        Query query = em.createQuery("SELECT p FROM Percorrenza p WHERE p.mezzoTrasporto.mezzoDiTrasportoId = :mezzoId", Percorrenza.class);
        query.setParameter("mezzoId", mezzoDiTrasportoId);
        List<Percorrenza> percorrenzeMezzi = query.getResultList();
        System.out.println("Lista percorrenze in base al mezzo: " + percorrenzeMezzi.size() + " trovata con successo");
        return percorrenzeMezzi;
    }

    //4. findByTratta(UUID trattaId)
    public List<Percorrenza> findPercorrenzaByTratta(UUID trattaId) {
        if (trattaId == null) throw new PercorrenzaNonTrovataException(trattaId);
        Query query = em.createQuery("SELECT p FROM Percorrenza p WHERE p.tratta.trattaId = :trattaId", Percorrenza.class);
        query.setParameter("trattaId", trattaId);
        List<Percorrenza> percorrenzeTratte = query.getResultList();
        System.out.println("Lista percorrenze in base alla tratta: " + percorrenzeTratte.size() + " trovata con successo");
        return percorrenzeTratte;
    }

    //5. findByMezzoAndTratta(UUID mezzoId, UUID trattaId)

    //5.1 update e delete non sono previsti dal progetto, perchè la percorrenza
    //è un dato storico che non muta, ma potrebbero
    //essere implementati in caso di errore di
    //inserimento da parte dell'amministratore.


    //6. countByMezzoAndTratta(UUID mezzoId, UUID trattaId) --->  quante volte un mezzo ha percorso una tratta
    public Long countByMezzoAndTratta(UUID mezzoDiTrasportoId, UUID trattaId, LocalDateTime dataInizio, LocalDateTime dataFine) {
        if (trattaId == null) throw new PercorrenzaNonTrovataException(trattaId);
        if (mezzoDiTrasportoId == null) throw new PercorrenzaNonTrovataException(mezzoDiTrasportoId);
        Query query = em.createQuery("SELECT COUNT(p) FROM Percorrenza p WHERE p.mezzoTrasporto.mezzoDiTrasportoId = :mezzoId AND p.tratta.trattaId = :trattaId AND p.dataPercorrenza BETWEEN :dataInizio AND :dataFine");
        query.setParameter("mezzoId", mezzoDiTrasportoId);
        query.setParameter("trattaId", trattaId);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        Long volte = (Long) query.getSingleResult();
        System.out.println("Il numero di volte che un mezzo percorre la tratta è " + volte);
        return volte;
    }

    //7. getTempoMedioEffettivo(UUID mezzoId, UUID trattaId) ---> calcolare il tempo di una percorrenza
    //dato un mezzo x
    //data una tratta y
    //calcola il tempo medio effettivo di una percorrenza
    public Duration getTempoMedioEffettivo(UUID mezzoId, UUID trattaId) {
        MezzoTrasporto mezzo = em.find(MezzoTrasporto.class, mezzoId);
        if (mezzo == null) throw new MezzoTrasportoNonTrovatoException(mezzoId);

        Tratta tratta = em.find(Tratta.class, trattaId);
        if (tratta == null) throw new TrattaNonTrovataException(trattaId);
        double velocità = mezzo.getTipoMezzo().getVelocitàMediaKmh();
        double distanzaKm = tratta.getTrattaKm();

        double tempoMedio = distanzaKm / velocità;
        long minuti = Math.round(tempoMedio * 60);
        System.out.println("Il tempo medio effettivo è di: " + minuti + " minuti");
        return Duration.ofMinutes(minuti);

    }

}
