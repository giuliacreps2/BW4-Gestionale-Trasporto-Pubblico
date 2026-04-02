package giuliacrepaldi.exceptions.tessera;

import giuliacrepaldi.interfaces.exceptions.TesseraGenericException;

public class TesseraGiaEsistenteException extends RuntimeException implements TesseraGenericException {
    public TesseraGiaEsistenteException(String message) {
        super("La tessera di questo utente esiste già, quindi non si può creare una nuova tessera");
    }
}
