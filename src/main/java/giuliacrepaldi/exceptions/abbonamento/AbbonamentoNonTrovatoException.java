package giuliacrepaldi.exceptions.abbonamento;

import giuliacrepaldi.interfaces.exceptions.AbbonamentoGenericException;

public class AbbonamentoNonTrovatoException extends RuntimeException implements AbbonamentoGenericException {
    public AbbonamentoNonTrovatoException(String targetIdentifier, String identifierType) {
        super("L'abbonamento con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovato.");
    }
}
