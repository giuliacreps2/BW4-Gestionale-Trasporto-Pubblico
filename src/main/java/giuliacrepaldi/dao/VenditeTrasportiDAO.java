package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.VenditaTrasporto;
import giuliacrepaldi.exceptions.biglietto.BigliettoSalvataggioException;
import giuliacrepaldi.exceptions.vendita_trasporto.VenditaTrasportoSalvataggioException;
import giuliacrepaldi.interfaces.exceptions.BigliettoGenericException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;

public class VenditeTrasportiDAO {

    private final EntityManager entityManager;
    
    public VenditeTrasportiDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Salva una vendita trasporto (biglietto, abbonamento, tessera).
     */
    public void salva(VenditaTrasporto venditaTrasporto) throws VenditaTrasportoSalvataggioException {

        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            entityManager.persist(venditaTrasporto);
            transaction.commit();

        } catch (RuntimeException ex) {
            transaction.rollback();
            throw new VenditaTrasportoSalvataggioException(venditaTrasporto);
        }
        
    }

    /**
     * Oblitera un biglietto su un mezzo di trasporto.
     */
    public void obliteraBiglietto(Biglietto biglietto, MezzoTrasporto mezzoTrasporto) {
        new BigliettiDAO(entityManager).obliteraBiglietto(biglietto, mezzoTrasporto);
    }

    
    /**
     * Ottieni quanti biglietti e abbonamenti 
     * sono stati emessi nel periodo dato.
     */
    public long ottieniQuantiBigliettiEAbbonamentiEmessiInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) AS totale " +
                        "FROM VenditaTrasporto v " +
                        "WHERE " +
                        "   (v.dataVendita BETWEEN :dataInizio AND :dataFine)" +
                        "   AND (TYPE(v) IN (Abbonamento, Biglietto))",
                Long.class
        );

        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);

        Long result = query.getSingleResult();

        return result;
        
    }
    
}
