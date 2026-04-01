package giuliacrepaldi.exceptions.manutenzione;

import giuliacrepaldi.entities.Manutenzione;

public class ManutenzioneSalvataggioException extends RuntimeException {
    public ManutenzioneSalvataggioException(Manutenzione manutenzione) {
        super("Errore durante salvataggio della manutenzione " + manutenzione);
    }
}
