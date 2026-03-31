package giuliacrepaldi.exceptions.tessera;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.interfaces.exceptions.TesseraGenericException;

public class TesseraSalvataggioException extends RuntimeException implements TesseraGenericException {
    public TesseraSalvataggioException(Tessera tessera) {
        super("Errore durante salvataggio di tessera " + tessera);
    }
}
