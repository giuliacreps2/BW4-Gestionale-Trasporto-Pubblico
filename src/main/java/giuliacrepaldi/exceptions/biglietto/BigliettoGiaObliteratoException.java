package giuliacrepaldi.exceptions.biglietto;

import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.interfaces.exceptions.BigliettoGenericException;

public class BigliettoGiaObliteratoException extends RuntimeException implements BigliettoGenericException {
    public BigliettoGiaObliteratoException(Biglietto biglietto) {
        super("Il biglietto " + biglietto + " non può essere obliterato di nuovo, perché era già obliterato.");
    }
}
