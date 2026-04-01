package giuliacrepaldi.exceptions.manutenzione;

import giuliacrepaldi.entities.Manutenzione;
import giuliacrepaldi.interfaces.exceptions.ManutenzioneGenericException;

public class ManutenzioneSalvataggioException extends RuntimeException implements ManutenzioneGenericException {
    public ManutenzioneSalvataggioException(Manutenzione manutenzione) {
        super("Errore durante salvataggio della manutenzione " + manutenzione);
    }
}
