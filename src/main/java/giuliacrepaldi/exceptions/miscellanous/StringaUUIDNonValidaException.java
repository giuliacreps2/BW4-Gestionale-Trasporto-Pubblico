package giuliacrepaldi.exceptions.miscellanous;

public class StringaUUIDNonValidaException extends RuntimeException {
    public StringaUUIDNonValidaException(String stringaUUID) {
        super("La stringa " + stringaUUID + " non può essere convertita in valido UUID.");
    }
}
