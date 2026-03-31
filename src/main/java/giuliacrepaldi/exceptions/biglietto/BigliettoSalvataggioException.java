package giuliacrepaldi.exceptions.biglietto;

import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.interfaces.exceptions.BigliettoGenericException;

public class BigliettoSalvataggioException extends RuntimeException implements BigliettoGenericException {
    public BigliettoSalvataggioException(Biglietto biglietto) {
        super("Errore durante salvataggio di biglietto " + biglietto);
    }
}
