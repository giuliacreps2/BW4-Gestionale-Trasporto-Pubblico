package giuliacrepaldi.exceptions.mezzo_trasporto;

import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.interfaces.exceptions.MezzoTrasportoGenericException;

public class MezzoTrasportoSalvataggioException extends RuntimeException implements MezzoTrasportoGenericException {
    public MezzoTrasportoSalvataggioException(MezzoTrasporto mezzoTrasporto) {
        super("Errore durante salvataggio del mezzo " + mezzoTrasporto);
    }
}
