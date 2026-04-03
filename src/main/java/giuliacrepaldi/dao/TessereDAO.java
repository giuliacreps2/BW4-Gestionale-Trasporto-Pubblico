package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraGiaEsistenteException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import giuliacrepaldi.exceptions.tessera.TesseraRinnovoException;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TessereDAO {

    private final EntityManager entityManager;

    public TessereDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Aggiungi/aggiorna una tessera.
     */
    public void salva(Tessera newTessera) throws TesseraSalvataggioException, TesseraGiaEsistenteException {

        // la tessera di questo utente esiste già 
        if (utenteHaTessera(newTessera.getUtente())) {
            throw new TesseraGiaEsistenteException(newTessera);
        }

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(newTessera);

            transaction.commit();
        } catch (RuntimeException e) {
            throw new TesseraSalvataggioException(newTessera, e.getMessage());
        }
    }

    /**
     * Trova una tessera per il suo ID.
     */
    public Tessera trovaPerId(String targetId) throws TesseraNonTrovataException, StringaUUIDNonValidaException {

        TypedQuery<Tessera> query = entityManager.createQuery(
                "SELECT t FROM Tessera t WHERE t.venditaTrasportoId = :targetId",
                Tessera.class
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
            throw new TesseraNonTrovataException(targetId, "ID");
        }

    }


    /**
     * Verifica se la tessera di quest'utente esiste già o no.
     */
    public boolean utenteHaTessera(Utente utente) {

        TypedQuery<Boolean> query = entityManager.createQuery(
                "SELECT " +
                        "(COUNT(t) > 0) " +
                        "   AS esiste_tessera " +
                        "FROM Tessera t " +
                        "WHERE t.utente = :utente",
                Boolean.class
        );

        // pass query params
        query.setParameter("utente", utente);

        // execute query
        return query.getSingleResult();
    }

    public List<Tessera> findAll() {
        Query query = entityManager.createQuery("SELECT t FROM Tessera t");
        List<Tessera> tutteLeTessere = query.getResultList();
        return tutteLeTessere;
    }

    /**
     * Rinnova la tessera con l'ID dato, solo se è scaduta.
     * Se la tessera non era scaduta, lancia un'eccezione dicendo che
     * la tessera non si può rinnovare se non è scaduta.
     */
    public void rinnovaTessera(String tesseraId) throws TesseraNonTrovataException, TesseraRinnovoException, StringaUUIDNonValidaException {

        // trova la tessera
        Tessera tessera = trovaPerId(tesseraId);
        
        EntityTransaction transaction = entityManager.getTransaction();

        LocalDate oggi = LocalDate.now();
        LocalDate oggiTraUnAnno = oggi.plusYears(1);

        Query query = entityManager.createQuery(
                "UPDATE Tessera t " +
                        "SET t.dataInizioTessera = :oggi, t.dataFineTessera = :oggiTraUnAnno " +
                        "WHERE t.venditaTrasportoId = :tesseraId AND t.dataFineTessera < :oggi"
        );

        // pass query params
        try {

            query.setParameter("tesseraId", UUID.fromString(tesseraId));
            query.setParameter("oggi", oggi);
            query.setParameter("oggiTraUnAnno", oggiTraUnAnno);

        } catch (IllegalArgumentException ex) {
            throw new StringaUUIDNonValidaException(tesseraId);
        }

        // execute query
        try {
            
            transaction.begin();
            int affectedRows = query.executeUpdate();
            
            if (affectedRows == 0) {
                throw new TesseraRinnovoException(tesseraId);
            }
            
            transaction.commit();
            
        } catch (RuntimeException ex) {
            transaction.rollback();
            System.out.println(ex);
            if(ex instanceof TesseraRinnovoException) {
                throw ex;
            }
            throw new TesseraSalvataggioException(tessera);
        }

    }

}