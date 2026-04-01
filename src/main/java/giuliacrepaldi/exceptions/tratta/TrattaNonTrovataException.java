package giuliacrepaldi.exceptions.tratta;

import giuliacrepaldi.interfaces.exceptions.TrattaGenericException;

import java.util.UUID;

public class TrattaNonTrovataException extends RuntimeException implements TrattaGenericException {
    public TrattaNonTrovataException(UUID trattaId) {
        super("La tratta con id: " + trattaId + ", non è stata trovata");
    }
}
