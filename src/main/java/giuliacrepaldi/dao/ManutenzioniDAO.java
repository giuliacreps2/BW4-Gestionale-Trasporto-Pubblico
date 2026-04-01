package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.exceptions.manutenzione.ManutenzioneSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ManutenzioniDAO {
    private final EntityManager em;

    public ManutenzioniDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Manutenzione newManutenzione) throws ManutenzioneSalvataggioException {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(newManutenzione);

            transaction.commit();
        } catch (Exception e) {
            throw new ManutenzioneSalvataggioException(newManutenzione);
        }

    }

    //Metodi
    //1.findByMezzo
    //2.findAll con BETWEEN — manutenzioni in un periodo
    //3.findManutenzioniInCorso(UUID mezzoId) ---> con data di riferimento oggi

    //TODO: valutare se inserirli qui in ManutenzioniDAO o se in MezzoTrasportoDAO
    //4.boolean eInManutenzione(UUID mezzoId), dove somma manutenzioni in corso == 0
    //5.boolean inServizio(UUID mezzoId)---> non in eInManutenzione()
}
