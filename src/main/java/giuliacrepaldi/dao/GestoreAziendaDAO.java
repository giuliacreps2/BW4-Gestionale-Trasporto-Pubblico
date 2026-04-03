package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Abbonamento;
import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoSalvataggioException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.tessera.TesseraGiaEsistenteException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import giuliacrepaldi.exceptions.tessera.TesseraRinnovoException;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
import giuliacrepaldi.exceptions.vendita_trasporto.VenditaTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class GestoreAziendaDAO {

    private final EntityManager entityManager;
    
    private AbbonamentiDAO abbonamentiDAO;
    private BigliettiDAO bigliettiDAO;
    private ManutenzioniDAO manutenzioniDAO;
    private MezziTrasportoDAO mezziTrasportoDAO;
    private PercorrenzeDAO percorrenzeDAO;
    private PuntiEmissioneDAO puntiEmissioneDAO;
    private TessereDAO tessereDAO;
    private TratteDAO tratteDAO;
    private UtentiDAO utentiDAO;
    private VenditeTrasportiDAO venditeTrasportiDAO;
    
    
    public GestoreAziendaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.abbonamentiDAO = new AbbonamentiDAO(entityManager);
        this.bigliettiDAO = new BigliettiDAO(entityManager);
        this.manutenzioniDAO = new ManutenzioniDAO(entityManager);
        this.percorrenzeDAO = new PercorrenzeDAO(entityManager);
        this.puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);
        this.tessereDAO = new TessereDAO(entityManager);
        this.tratteDAO = new TratteDAO(entityManager);
        this.utentiDAO = new UtentiDAO(entityManager);
        this.venditeTrasportiDAO = new VenditeTrasportiDAO(entityManager);
    }

    
    /**
     * Aggiungi/aggiorna un biglietto.
     */
    public void creaBiglietto(Biglietto biglietto) throws VenditaTrasportoSalvataggioException {
        bigliettiDAO.salva(biglietto);
    }

    
    /**
     * Aggiungi/aggiorna un abbonamento.
     */
    public void creaAbbonamento(Abbonamento abbonamento) throws AbbonamentoSalvataggioException {
        abbonamentiDAO.salva(abbonamento);
    }


    /**
     * Aggiungi/aggiorna una tessera.
     */
    public void creaTessera(Tessera tessera) throws TesseraSalvataggioException, TesseraGiaEsistenteException {
        tessereDAO.salva(tessera);
    }

    
    /**
     * Rinnova una tessera. 
     * Verifica quali eccezioni vengono lanciate nel metodo finale. 
     */
    public void rinnovaTessera(String tesseraId) throws TesseraNonTrovataException, TesseraRinnovoException, StringaUUIDNonValidaException {
        tessereDAO.rinnovaTessera(tesseraId);
    }


    /**
     * Ottieni quanti biglietti e abbonamenti 
     * sono stati emessi nel periodo dato.
     */
    public long ottieniQuantiBigliettiEAbbonamentiEmessiInPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        return venditeTrasportiDAO.ottieniQuantiBigliettiEAbbonamentiEmessiInPeriodo(dataInizio, dataFine);
    }
    
    // public calcolaQuanteVenditeTrasportoInPuntoEmissione() {}
    //
    // public mettiFuoriServizioDistributoreAutomatico() {}
    //
    // public mettiInServizioDistributoreAutomatico() {}
    //
    // public abbonamentoEValido() {}
    //
    // public mezzoEInServizio() {}
    //
    // public mezzoEInManutenzione() {}
    //
    // public ottieniTuttiPeriodiManutenzioneDiMezzo() {}
    //
    // public ottieniTuttiPeriodiServizioDiMezzo() {}
    //
    // public obliteraBiglietto() {}
    //
    // public calcolaQuantiBigliettiVidimatiSuMezzo() {}
    //
    // public calcolaQuantiBigliettiVidimatiInPeriodo() {}
    
}
