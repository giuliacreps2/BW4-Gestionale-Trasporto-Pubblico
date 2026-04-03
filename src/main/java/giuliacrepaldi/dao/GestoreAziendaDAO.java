package giuliacrepaldi.dao;

import jakarta.persistence.EntityManager;

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
    
    // public emettiBiglietto() {
    //    
    // }
    
    // public emettiAbbonamentoSoloSeUtenteHaTessera() {
    //    
    // }
    //
    // public associaTesseraAUtente() {}
    //
    // public rinnovaTessera() {
    //    
    // }
    //
    // public calcolaQuanteVenditeTrasportoInPeriodo() {}
    //
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
