package giuliacrepaldi.exceptions.vendita_trasporto;

import giuliacrepaldi.entities.VenditaTrasporto;
import giuliacrepaldi.interfaces.exceptions.VenditaTrasportoGenericException;

public class VenditaTrasportoSalvataggioException extends RuntimeException implements VenditaTrasportoGenericException {
    public VenditaTrasportoSalvataggioException(VenditaTrasporto venditaTrasporto) {
        super("Errore durante salvataggio di una vendita trasporto: " + venditaTrasporto);
    }
}
