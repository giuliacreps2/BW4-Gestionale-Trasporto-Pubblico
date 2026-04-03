package giuliacrepaldi.exceptions.tessera;

import giuliacrepaldi.interfaces.exceptions.TesseraGenericException;

public class TesseraRinnovoException extends RuntimeException implements TesseraGenericException {
    public TesseraRinnovoException(String tesseraId) {
        super("Il rinnovo della tessera con ID " + tesseraId + " non è andato a buon fine. Forse non si poteva rinnovare o era già valida?");
    }
}
