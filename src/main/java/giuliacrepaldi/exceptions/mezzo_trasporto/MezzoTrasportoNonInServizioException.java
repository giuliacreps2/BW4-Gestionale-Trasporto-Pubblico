package giuliacrepaldi.exceptions.mezzo_trasporto;

import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.interfaces.exceptions.MezzoTrasportoGenericException;

public class MezzoTrasportoNonInServizioException extends RuntimeException implements MezzoTrasportoGenericException {
    public MezzoTrasportoNonInServizioException(MezzoTrasporto mezzoTrasporto) {
        super("Il mezzo di trasporto " + mezzoTrasporto + " non è in servizio.");
    }
}
