package giuliacrepaldi.helpers;

import giuliacrepaldi.dao.AbbonamentiDAO;
import giuliacrepaldi.dao.BigliettiDAO;
import giuliacrepaldi.dao.PuntiEmissioneDAO;
import giuliacrepaldi.dao.TessereDAO;
import giuliacrepaldi.entities.Abbonamento;
import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.entities.Tessera;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class ConvertitoreUUID {
    private final EntityManager em;
    TessereDAO tessereDAO;
    BigliettiDAO bigliettiDAO;
    AbbonamentiDAO abbonamentiDAO;
    PuntiEmissioneDAO puntiEmissioneDAO;

    public ConvertitoreUUID(EntityManager em) {
        this.em = em;
        this.tessereDAO = new TessereDAO(em);
        this.bigliettiDAO = new BigliettiDAO(em);
        this.abbonamentiDAO = new AbbonamentiDAO(em);
        this.puntiEmissioneDAO = new PuntiEmissioneDAO(em);
    }

    public static <T> Map<Integer, UUID> costruisciMappa(List<T> lista, Function<T, UUID> estraiId, Function<T, String> estraiLabel) {
        Map<Integer, UUID> mappa = new HashMap<>();
        for (int i = 0; i < lista.size(); i++) {
            mappa.put(i + 1, estraiId.apply(lista.get(i)));
            System.out.println((i + 1) + ": " + estraiLabel.apply(lista.get(i)));
        }
        return mappa;
    }

    //7. Per le tessere
    public Map<Integer, UUID> mapTessere() {
        List<Tessera> tessere = tessereDAO.findAll();
        return costruisciMappa(
                tessere,
                Tessera::getVenditaTrasportoId,
                tessera -> tessera.getUtente().getNome() + " " + tessera.getUtente().getCognome());
    }

    //2. Per i biglietti
    public Map<Integer, UUID> mapBiglietti() {
        List<Biglietto> biglietti = bigliettiDAO.findAll();
        return costruisciMappa(
                biglietti,
                Biglietto::getVenditaTrasportoId,
                biglietto -> String.valueOf(biglietto.getDataEOraInizioObliterazione()));
    }


    //1. Per gli abbonamenti
    public Map<Integer, UUID> mapAbbonamenti() {
        List<Abbonamento> abbonamenti = abbonamentiDAO.findAll();
        return costruisciMappa(
                abbonamenti,
                Abbonamento::getVenditaTrasportoId,
                abbonamento -> abbonamento.getTessera() + " " + String.valueOf(abbonamento.getDataInizioAbbonamento()));
    }

    //3. Per le manutenzioni
    //4. Per i mezzi di trasporto
    //5. Per le percorrenze
    //6. Per i punti d'emissione
    public Map<Integer, UUID> mapPuntiEmissione() {
        List<PuntoEmissione> puntiEmissione = puntiEmissioneDAO.findAll();
        return costruisciMappa(
                puntiEmissione,
                PuntoEmissione::getPuntoEmissioneId,
                puntiCittaEmissione -> puntiCittaEmissione.getCitta());
    }


    //8. Per le tratte
    //9. Per gli utenti
    //10. Per le vendite trasporti
}
