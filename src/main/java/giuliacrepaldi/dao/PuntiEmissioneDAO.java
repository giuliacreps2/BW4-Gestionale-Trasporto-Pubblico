package giuliacrepaldi.dao;

import giuliacrepaldi.entities.PuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class PuntiEmissioneDAO {

    private EntityManager entityManager;

    public PuntiEmissioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(PuntoEmissione puntoEmissione) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(puntoEmissione);
            transaction.commit();
            System.out.println("Punto di emissione salvato correttamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante il salvataggio del punto di emissione.");
            e.printStackTrace();
        }
    }

    public PuntoEmissione findById(UUID id) {
        return entityManager.find(PuntoEmissione.class, id);
    }

    public void findByIdAndDelete(UUID id) {
        PuntoEmissione puntoEmissioneTrovato = entityManager.find(PuntoEmissione.class, id);

        if (puntoEmissioneTrovato == null) {
            System.out.println("Punto di emissione non trovato.");
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.remove(puntoEmissioneTrovato);
            transaction.commit();
            System.out.println("Punto di emissione eliminato correttamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'eliminazione del punto di emissione.");
            e.printStackTrace();
        }
    }

    public void findByIdAndUpdate(UUID id, String nuovaCitta, boolean nuovoStato) {
        PuntoEmissione puntoEmissioneTrovato = entityManager.find(PuntoEmissione.class, id);

        if (puntoEmissioneTrovato == null) {
            System.out.println("Punto di emissione non trovato.");
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            puntoEmissioneTrovato.setCitta(nuovaCitta);
            puntoEmissioneTrovato.setAttivo(nuovoStato);
            entityManager.merge(puntoEmissioneTrovato);
            transaction.commit();
            System.out.println("Punto di emissione aggiornato correttamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'aggiornamento del punto di emissione.");
            e.printStackTrace();
        }
    }
}