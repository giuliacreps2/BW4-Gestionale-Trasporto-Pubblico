package giuliacrepaldi.exceptions.miscellanous;

public class DataManutenzioneNonValidaException extends RuntimeException {
    public DataManutenzioneNonValidaException(String message) {
        super("La data di inizio o fine manutenzione non è valida. DETTAGLI: " + message);
    }
}
