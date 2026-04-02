package giuliacrepaldi.exceptions.manutenzione;

import giuliacrepaldi.interfaces.exceptions.ManutenzioneGenericException;

public class ManutenzioneNonTrovataException extends RuntimeException implements ManutenzioneGenericException {
    public ManutenzioneNonTrovataException(String targetIdentifier, String identifierType) {
        super("La manutenzione con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovata.");
    }
}
