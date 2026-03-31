package giuliacrepaldi.exceptions.tessera;

import giuliacrepaldi.interfaces.exceptions.TesseraGenericException;

public class TesseraNonTrovataException extends RuntimeException implements TesseraGenericException {
    public TesseraNonTrovataException(String targetIdentifier, String identifierType) {
        super("La tessera con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovato.");
    }
}
