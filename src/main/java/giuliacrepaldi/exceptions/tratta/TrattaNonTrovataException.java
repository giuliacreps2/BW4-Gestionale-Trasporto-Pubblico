package giuliacrepaldi.exceptions.tratta;

import giuliacrepaldi.interfaces.exceptions.TrattaGenericException;

import java.util.UUID;

public class TrattaNonTrovataException extends RuntimeException implements TrattaGenericException {
    public TrattaNonTrovataException(String targetIdentifier, String identifierType) {
        super("La tratta con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovata.");
    }
    
    public TrattaNonTrovataException(UUID trattaId) {
        this(trattaId.toString(), "UUID");
    }
    
}
