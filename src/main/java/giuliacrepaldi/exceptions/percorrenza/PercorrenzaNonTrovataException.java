package giuliacrepaldi.exceptions.percorrenza;

import giuliacrepaldi.interfaces.exceptions.PercorrenzaGenericException;

import java.util.UUID;

public class PercorrenzaNonTrovataException extends RuntimeException implements PercorrenzaGenericException {
    public PercorrenzaNonTrovataException(UUID percorrenzaId) {
        super("La percorrenza con id: " + percorrenzaId + ", non è stata trovata");
    }
}
