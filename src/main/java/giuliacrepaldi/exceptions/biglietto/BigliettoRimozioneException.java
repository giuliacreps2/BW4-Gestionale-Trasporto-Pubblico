package giuliacrepaldi.exceptions.biglietto;

import giuliacrepaldi.interfaces.exceptions.BigliettoGenericException;

public class BigliettoRimozioneException extends RuntimeException implements BigliettoGenericException {
    public BigliettoRimozioneException(String targetIdentifier, String identifierType) {
        super("Non si è potuto rimuovere il biglietto con tipo identificatore " + identifierType + " con valore " + targetIdentifier);
    }
}
