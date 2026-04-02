package giuliacrepaldi.exceptions.percorrenza;

import giuliacrepaldi.interfaces.exceptions.PercorrenzaGenericException;

import java.util.UUID;

public class PercorrenzaNonTrovataException extends RuntimeException implements PercorrenzaGenericException {
    public PercorrenzaNonTrovataException(String targetIdentifier, String identifierType) {
        super("La percorrenza con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovata.");
    }

    public PercorrenzaNonTrovataException(UUID trattaId) {
        this(trattaId.toString(), "UUID");
    }
}
