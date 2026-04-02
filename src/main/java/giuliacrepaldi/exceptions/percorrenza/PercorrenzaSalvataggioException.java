package giuliacrepaldi.exceptions.percorrenza;

import giuliacrepaldi.entities.Percorrenza;
import giuliacrepaldi.interfaces.exceptions.PercorrenzaGenericException;

public class PercorrenzaSalvataggioException extends RuntimeException implements PercorrenzaGenericException {
    public PercorrenzaSalvataggioException(Percorrenza percorrenza) {

        super("Errore durante salvataggio del mezzo " + percorrenza);
    }
}
