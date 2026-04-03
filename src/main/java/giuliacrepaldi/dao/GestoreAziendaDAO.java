package giuliacrepaldi.dao;

import giuliacrepaldi.entities.*;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoNonTrovatoException;
import giuliacrepaldi.exceptions.abbonamento.AbbonamentoSalvataggioException;
import giuliacrepaldi.exceptions.biglietto.BigliettoGiaObliteratoException;
import giuliacrepaldi.exceptions.biglietto.BigliettoNonTrovatoException;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonInServizioException;
import giuliacrepaldi.exceptions.mezzo_trasporto.MezzoTrasportoNonTrovatoException;
import giuliacrepaldi.exceptions.miscellanous.StringaUUIDNonValidaException;
import giuliacrepaldi.exceptions.percorrenza.PercorrenzaSalvataggioException;
import giuliacrepaldi.exceptions.punto_emissione.PuntoEmissioneNonTrovatoException;
import giuliacrepaldi.exceptions.tessera.TesseraGiaEsistenteException;
import giuliacrepaldi.exceptions.tessera.TesseraNonTrovataException;
import giuliacrepaldi.exceptions.tessera.TesseraRinnovoException;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
import giuliacrepaldi.exceptions.tratta.TrattaNonTrovataException;
import giuliacrepaldi.exceptions.vendita_trasporto.VenditaTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        this.mezziTrasportoDAO = new MezziTrasportoDAO(entityManager);
    }

    /**
     * **************************************************++
     *  CONSEGNE DEL PROGETTO
     * **************************************************++
     * */
    
    
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

    
    /**
     * Ottieni quanti biglietti e abbonamenti 
     * sono stati emessi in un punto emissione.
     */
    public long ottieniQuantiBigliettiEAbbonamentiEmessiInPuntoEmissione(String puntoEmissioneId)  throws PuntoEmissioneNonTrovatoException, StringaUUIDNonValidaException {
        return venditeTrasportiDAO.ottieniQuantiBigliettiEAbbonamentiEmessiInPuntoEmissione(puntoEmissioneId);
    }
    
    
    /**
     * Oblitera un biglietto su un mezzo di trasporto.
     */
    public void obliteraBiglietto(String bigliettoId, String mezzoTrasportoId)  throws BigliettoNonTrovatoException, MezzoTrasportoNonTrovatoException, BigliettoGiaObliteratoException, VenditaTrasportoSalvataggioException, StringaUUIDNonValidaException {
        bigliettiDAO.obliteraBiglietto(bigliettoId, mezzoTrasportoId);
    }


    /**
     * Ottieni quanti biglietti sono stati vidimati
     * sul dato mezzo.
     */
    public long ottieniQuantiBigliettiVidimatiSuMezzoTrasporto(String mezzoTrasportoId) {
        return bigliettiDAO.contaBigliettiVidimatiSuMezzoTrasporto(mezzoTrasportoId);
    }

    
    /**
     * Ottieni quanti biglietti sono stati vidimati
     * in un dato periodo.
     */
    public long ottieniQuantiBigliettiVidimatiInPeriodo(LocalDateTime dataEOraInizio, LocalDateTime dataEOraFine) {
        return bigliettiDAO.contaBigliettiVidimatiInPeriodo(dataEOraInizio, dataEOraFine);
    }


    /**
     * Verifica se un abbonamento è valido (si/no).
     */
    public boolean abbonamentoEValido(String abbonamentoId) throws AbbonamentoNonTrovatoException, StringaUUIDNonValidaException {
        return abbonamentiDAO.abbonamentoValido(abbonamentoId);
    }

    
    /**
     * Verifica se il mezzo dato è in servizio (si/no).
     */
    public boolean mezzoEInServizio(String mezzoTrasportoId) {
        return mezziTrasportoDAO.inServizio(mezzoTrasportoId);
    }

    
    /**
     * Verifica se il mezzo dato è in manutenzione (si/no).
     */
    public boolean mezzoEInManutenzione(String mezzoTrasportoId) {
        return mezziTrasportoDAO.eInManutenzione(mezzoTrasportoId);
    }

    
    /**
     * Ottieni la flotta mezzi/parco mezzi.
     */
    public List<MezzoTrasporto> ottieniTuttiMezziTrasporto() {
        return mezziTrasportoDAO.findAll();
    }

    
    /**
     * Associa una tratta a un mezzo di trasporto in servizio. 
     */
    public void associaTrattaAMezzoTrasportoInServizio(String trattaId, String mezzoTrasportoId, long tempoEffettivoPercorrenza, LocalDateTime dataEOraPercorrenza) throws MezzoTrasportoNonTrovatoException, TrattaNonTrovataException, MezzoTrasportoNonInServizioException, PercorrenzaSalvataggioException, StringaUUIDNonValidaException {
        mezziTrasportoDAO.associaTrattaAMezzoTrasportoInServizio(trattaId, mezzoTrasportoId, tempoEffettivoPercorrenza, dataEOraPercorrenza);
    }


    /**
     * Metti un mezzo di trasporto in servizio.
     */
    public void mettiInServizio(String mezzoTrasportoId) throws MezzoTrasportoNonTrovatoException, StringaUUIDNonValidaException {
        mezziTrasportoDAO.mettiInServizio(mezzoTrasportoId);
    }

    
    /**
     * Ottieni tutti i periodi di manutenzione del mezzo di trasporto dato.
     */
    public List<Manutenzione> ottieniTutteManutenzioniDiMezzo(String mezzoTrasportoId, LocalDate dataInizio, LocalDate dataFine) throws MezzoTrasportoNonTrovatoException, StringaUUIDNonValidaException {
        return manutenzioniDAO.findAll(mezzoTrasportoId, dataInizio, dataFine);    
    }
    

    // public mettiFuoriServizioDistributoreAutomatico() {}
    //
    // public mettiInServizioDistributoreAutomatico() {}
    //
    //
    //
    //
    // public ottieniTuttiPeriodiServizioDiMezzo() {}
    //
    //


    /**
     * **************************************************++
     *  SALVA
     * **************************************************++
     * */

    // public void salvaBiglietto() {}
    //
    // public void salvaAbbonamento() {}
    //
    // public void salvaTessera() {}
    //
    // public void salvaMezzoTrasporto() {}
    //
    // ....

    /**
     * **************************************************++
     *  TROVA PER ID
     * **************************************************++
     * */
    
    // public Biglietto trovaBigliettoPerId() {}
    //
    // public Abbonamento trovaAbbonamentoPerId() {}
    //
    // public Tessera trovaTesseraPerId() {}
    
    // .....     
    
}
