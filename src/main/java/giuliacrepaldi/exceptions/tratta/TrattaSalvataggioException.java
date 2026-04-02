package giuliacrepaldi.exceptions.tratta;

import giuliacrepaldi.entities.Tratta;
import giuliacrepaldi.interfaces.exceptions.TrattaGenericException;

public class TrattaSalvataggioException extends RuntimeException implements TrattaGenericException {
    public TrattaSalvataggioException(Tratta tratta) {
        super("Errore durante il salvataggio della tratta" + tratta);
    }
}
