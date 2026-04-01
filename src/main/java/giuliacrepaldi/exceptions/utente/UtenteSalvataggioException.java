package giuliacrepaldi.exceptions.utente;

import giuliacrepaldi.entities.Utente;

public class UtenteSalvataggioException extends RuntimeException {
    public UtenteSalvataggioException(Utente utente) {
        super("Errore durante salvataggio dell'utente " + utente);
    }
}
