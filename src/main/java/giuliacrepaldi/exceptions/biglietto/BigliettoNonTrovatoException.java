package giuliacrepaldi.exceptions.biglietto;

import giuliacrepaldi.interfaces.exceptions.BigliettoGenericException;

public class BigliettoNonTrovatoException extends RuntimeException implements BigliettoGenericException {
    public BigliettoNonTrovatoException(String targetIdentifier, String identifierType) {
        super("Il biglietto con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovato.");
    }
}
