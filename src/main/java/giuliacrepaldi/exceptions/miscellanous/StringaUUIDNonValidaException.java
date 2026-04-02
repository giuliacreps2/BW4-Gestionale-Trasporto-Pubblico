package giuliacrepaldi.exceptions.miscellanous;

import java.util.UUID;

public class StringaUUIDNonValidaException extends RuntimeException {
    public StringaUUIDNonValidaException(UUID stringaUUID) {
        super("La stringa " + stringaUUID + " non può essere convertita in valido UUID.");
    }

    public StringaUUIDNonValidaException(UUID mezzoID) {
    }
}
