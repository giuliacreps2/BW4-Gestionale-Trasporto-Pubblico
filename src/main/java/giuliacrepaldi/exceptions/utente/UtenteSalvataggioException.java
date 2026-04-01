package giuliacrepaldi.exceptions.utente;

import giuliacrepaldi.entities.Utente;
import giuliacrepaldi.interfaces.exceptions.UtenteGenericException;

public class UtenteSalvataggioException extends RuntimeException implements UtenteGenericException {
    public UtenteSalvataggioException(Utente utente) {
        super("Errore durante salvataggio dell'utente " + utente);
    }
}
