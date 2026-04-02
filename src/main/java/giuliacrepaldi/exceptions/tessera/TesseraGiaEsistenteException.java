package giuliacrepaldi.exceptions.tessera;

import giuliacrepaldi.entities.Tessera;
import giuliacrepaldi.interfaces.exceptions.TesseraGenericException;

public class TesseraGiaEsistenteException extends RuntimeException implements TesseraGenericException {
    public TesseraGiaEsistenteException(Tessera tessera) {
        super("La tessera di questo utente esiste già, quindi non si può creare una nuova tessera. TESSERA: " + tessera);
    }
}
