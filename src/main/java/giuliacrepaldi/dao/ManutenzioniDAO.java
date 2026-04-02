package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Abbonamento;
import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoNonTrovatoException;
import giuliacrepaldi.exceptions.manutenzione.ManutenzioneSalvataggioException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


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
public Manutenzione findByMezzo(String manutenzioneID) {
 Manutenzione found;

    try {

        found = em.find(Manutenzione.class, UUID.fromString(manutenzioneID));
    } catch (IllegalArgumentException e){
        throw new IllegalArgumentException("UUID non valido " + manutenzioneID);
    }
    if (found == null){
        throw new IllegalArgumentException("Manutenzione non trovata con id: " + manutenzioneID);
    }
    return found;
}

public MezzoTrasporto findMezzoByManutenzioneID(String manutenzioneID){
        Manutenzione manutenzione= findByMezzo(manutenzioneID);
        return manutenzione.getMezzoTrasporto();
}

public List<Manutenzione> findAll(String mezzoID, LocalDate dataInizio, LocalDate dataFine){
        try {
            UUID uuid = UUID.fromString(mezzoID);
            return em.createQuery("SELECT m FROM Manutenzione m " +
                            "WHERE m.mezzoTrasporto.mezzoDiTrasportoId = :mezzoID AND m.dataInizioManutenzione BETWEEN :dataInizio " +
                            "AND :dataFine", Manutenzione.class)
                    .setParameter("mezzoID", uuid)
                    .setParameter("dataInizio", dataInizio)
                    .setParameter("dataFine", dataFine)
                    .getResultList();
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("UUID non valido " + mezzoID);
}


    //Metodi
    //1.findByMezzo
    //2.findAll con BETWEEN — manutenzioni in un periodo
    //3.findManutenzioniInCorso(UUID mezzoId) ---> con data di riferimento oggi
    //4.boolean eInManutenzione(UUID mezzoId), dove somma manutenzioni in corso == 0
    //5.boolean inServizio(UUID mezzoId)---> non in eInManutenzione()
}
