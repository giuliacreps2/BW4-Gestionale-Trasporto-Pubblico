package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.biglietto.BigliettoGiaObliteratoException;
import giuliacrepaldi.exceptions.biglietto.BigliettoNonTrovatoException;
import giuliacrepaldi.exceptions.biglietto.BigliettoRimozioneException;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonTrovatoException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.vendita_trasporto.VenditaTrasportoSalvataggioException;
import jakarta.persistence.*;
import jakarta.transaction.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BigliettiDAO {

    private final EntityManager entityManager;

    public BigliettiDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Trova un biglietto per ID.
     */
    public Biglietto trovaPerId(String targetId) throws BigliettoNonTrovatoException, StringaUUIDNonValidaException {

        TypedQuery<Biglietto> query = entityManager.createQuery(
                "SELECT b FROM Biglietto b WHERE b.venditaTrasportoId = :targetId",
                Biglietto.class
        );

        // pass query params
        try {

            query.setParameter("targetId", UUID.fromString(targetId));

        } catch (IllegalArgumentException ex) {
            throw new StringaUUIDNonValidaException(targetId);
        }

        // execute query
        try {

            return query.getSingleResult();

        } catch (NoResultException ex) {
            throw new BigliettoNonTrovatoException(targetId, "ID");
        }

    }

    /**
     * Salva un biglietto.
     */
    public void salva(Biglietto biglietto) throws VenditaTrasportoSalvataggioException {
        new VenditeTrasportiDAO(entityManager).salva(biglietto);
    }

    
    /**
     * Oblitera un biglietto su un mezzo di trasporto.
     */
    public void obliteraBiglietto(Biglietto biglietto, MezzoTrasporto mezzoTrasporto) throws BigliettoGiaObliteratoException, VenditaTrasportoSalvataggioException {

        // obliterare un biglietto su un mezzo di trasporto,
        // significa che il campo obliteratoDa del biglietto,
        // punta all'id del mezzo di trasporto

        biglietto.setObliteratoDa(mezzoTrasporto);
        salva(biglietto);

    }

    /**
     * Oblitera un biglietto su un mezzo di trasporto.
     */
    public void obliteraBiglietto(String bigliettoId, String mezzoTrasportoId) throws BigliettoNonTrovatoException, MezzoTrasportoNonTrovatoException, BigliettoGiaObliteratoException, VenditaTrasportoSalvataggioException, StringaUUIDNonValidaException {
        
        Biglietto biglietto = trovaPerId(bigliettoId);
        MezzoTrasporto mezzoTrasporto = new MezziTrasportoDAO(entityManager).trovaPerId(mezzoTrasportoId);
        
        obliteraBiglietto(biglietto, mezzoTrasporto);

    }


    /**
     * Ottieni il totale dei biglietti vidimati sul mezzo di trasporto in input.
     */
    public long contaBigliettiVidimatiSuMezzoTrasporto(MezzoTrasporto mezzoTrasporto) {

        // trova i biglietti il cui mezzo di trasporto
        // su cui sono stati obliterati, è uguale al 
        // mezzo di trasporto in input. conta quanti
        // sono questi biglietti.

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(b) AS totale FROM Biglietto b WHERE b.obliteratoDa = :mezzoTrasporto",
                Long.class
        );

        query.setParameter("mezzoTrasporto", mezzoTrasporto);

        Long result = query.getSingleResult();

        return result;
    }

    
    /**
     * Ottieni il totale dei biglietti vidimati sul mezzo di trasporto in input.
     */
    public long contaBigliettiVidimatiSuMezzoTrasporto(String mezzoTrasportoId) throws MezzoTrasportoNonTrovatoException, StringaUUIDNonValidaException {

        MezzoTrasporto mezzoTrasporto = new MezziTrasportoDAO(entityManager).trovaPerId(mezzoTrasportoId);     
       
        return contaBigliettiVidimatiSuMezzoTrasporto(mezzoTrasporto);
    }
    

    /**
     * Ottieni il totale dei biglietti vidimati nel periodo dato.
     * (data e ora)
     */
    public long contaBigliettiVidimatiInPeriodo(LocalDateTime dataEOraInizio, LocalDateTime dataEOraFine) {

        // trova i biglietti vidimati la cui data di inizio obliterazione,
        // rientrano nel periodo in input. siccome i biglietti devono essere vidimati,
        // il campo obliteratoDa non deve essere nullo
        // per filtrare per periodo:
        //   DIOB = data inizio obliterazione biglietto
        //   dataInizioInput   <= DIOB <=  dataFineInput

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(b) AS totale " +
                        "FROM Biglietto b " +
                        "WHERE " +
                        "   b.dataEOraInizioObliterazione BETWEEN :dataEOraInizio AND :dataEOraFine" +
                        "   AND b.obliteratoDa IS NOT NULL",
                Long.class
        );

        query.setParameter("dataEOraInizio", dataEOraInizio);
        query.setParameter("dataEOraFine", dataEOraFine);

        // execute query
        Long result = query.getSingleResult();

        return result;
    }

    
    /**
     * Ottieni il totale dei biglietti vidimati nel periodo dato.
     * (solo data)
     */
    public long contaBigliettiVidimatiInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        
        // ora 00:00:00 (mezzanotte) della data di inizio
        LocalDateTime inizioGiornoDiDataInizio = dataInizio.atStartOfDay();     
        // ora 23:59:59 del giorno di fine
        LocalDateTime fineGiornoDiDataFine = dataFine.atTime(23, 59, 59, 999_999_999);

        return contaBigliettiVidimatiInPeriodo(inizioGiornoDiDataInizio, fineGiornoDiDataFine);
    }
    

    public List<Biglietto> findAll() {
        Query query = entityManager.createQuery("SELECT b FROM Biglietto b");
        List<Biglietto> tuttiBiglietti = query.getResultList();
        return tuttiBiglietti;
    }

    /**
     * Rimuovi un biglietto per ID.
     */
    public void rimuoviPerId(String targetId) throws BigliettoNonTrovatoException, BigliettoRimozioneException, StringaUUIDNonValidaException {

        EntityTransaction transaction = entityManager.getTransaction();
        
        Query query = entityManager.createQuery(
                "DELETE FROM Biglietto b WHERE b.venditaTrasportoId = :targetId"
        );

        // pass query params
        try {

            query.setParameter("targetId", UUID.fromString(targetId));

        } catch (IllegalArgumentException ex) {
            throw new StringaUUIDNonValidaException(targetId);
        }

        // execute query
        try {
            
            transaction.begin();
            int affectedRows = query.executeUpdate();
            
            if (affectedRows == 0) {
                throw new BigliettoNonTrovatoException(targetId, "ID");
            }
            
            transaction.commit();
                    
        } catch (RuntimeException ex) {
            transaction.rollback();
            if (ex instanceof BigliettoNonTrovatoException) {
                throw ex;
            }
            throw new BigliettoRimozioneException(targetId, "ID");
        }
        
    }
    

}
