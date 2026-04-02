package giuliacrepaldi.exceptions.punto_emissione;

import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.interfaces.exceptions.PuntoEmissioneGenericException;

public class PuntoEmissioneSalvataggioException extends RuntimeException implements PuntoEmissioneGenericException {
    public PuntoEmissioneSalvataggioException(PuntoEmissione puntoEmissione) {
        super("Errore durante salvataggio del punto di emissione " + puntoEmissione);
    }
}
