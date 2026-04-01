package giuliacrepaldi.exceptions.utente;

import giuliacrepaldi.interfaces.exceptions.UtenteGenericException;

public class UtenteNonTrovatoException extends RuntimeException implements UtenteGenericException {
    public UtenteNonTrovatoException(String targetIdentifier, String identifierType) {
      super("L'utente con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovato.");
    }
}
