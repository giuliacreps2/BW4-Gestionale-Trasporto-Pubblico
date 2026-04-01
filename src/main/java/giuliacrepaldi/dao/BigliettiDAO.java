package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.exceptions.biglietto.BigliettoGiaObliteratoException;
import giuliacrepaldi.exceptions.biglietto.BigliettoNonTrovatoException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import giuliacrepaldi.exceptions.vendita_trasporto.VenditaTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
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

        } catch(IllegalArgumentException ex) {
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
     * Ottieni il totale dei biglietti vidimati sul mezzo di trasporto in input.
     */
    public int contaBigliettiVidimatiSuMezzoTrasporto(MezzoTrasporto mezzoTrasporto) {

        // trova i biglietti il cui mezzo di trasporto
        // su cui sono stati obliterati, è uguale al 
        // mezzo di trasporto in input. conta quanti
        // sono questi biglietti.

        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT COUNT(b.venditaTrasportoId) AS totale FROM Biglietto b WHERE b.obliteratoDa = :mezzoTrasporto", 
                Integer.class
        );

        query.setParameter("mezzoTrasporto", mezzoTrasporto);

        Integer result = query.getSingleResult();
        
        return result;
    }

    /**
     * Ottieni il totale dei biglietti vidimati nel periodo dato. 
     */
    public int contaBigliettiVidimatiInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        
        // trova i biglietti vidimati la cui data di inizio obliterazione,
        // rientrano nel periodo in input. siccome i biglietti devono essere vidimati,
        // il campo obliteratoDa non deve essere nullo
        // per filtrare per periodo:
        //   DIOB = data inizio obliterazione biglietto
        //   dataInizioInput   <= DIOB <=  dataFineInput
        
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT COUNT(b.venditaTrasportoId) AS totale " +
                        "FROM Biglietto b " +
                        "WHERE " +
                        "   b.dataEOraInizioObliterazione BETWEEN :dataInizio AND :dataFine" +
                        "   AND b.obliteratoDa IS NULL",
                Integer.class
        );

        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);

        // execute query
        Integer result = query.getSingleResult();

        return result;
    }
    
}
