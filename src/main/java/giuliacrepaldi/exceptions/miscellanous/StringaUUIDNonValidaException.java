package giuliacrepaldi.exceptions.miscellanous;

import java.util.UUID;

public class StringaUUIDNonValidaException extends RuntimeException {
    public StringaUUIDNonValidaException(String stringaID) {
        super("La stringa " + stringaID + " non può essere convertita in valido UUID.");
    }
}
