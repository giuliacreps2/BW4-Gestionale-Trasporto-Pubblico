package giuliacrepaldi.exceptions.abbonamento;

import giuliacrepaldi.interfaces.exceptions.AbbonamentoGenericException;

public class AbbonamentoRimozioneException extends RuntimeException implements AbbonamentoGenericException {
    public AbbonamentoRimozioneException(String targetIdentifier, String identifierType) {
        super("Non si è potuto rimuovere l'abbonamento con tipo identificatore " + identifierType + " con valore " + targetIdentifier);
    }
}
