package giuliacrepaldi.exceptions.manutenzione;

import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.interfaces.exceptions.ManutenzioneGenericException;

public class ManutenzioneDiMezzoNonEsisteException extends RuntimeException implements ManutenzioneGenericException {
    public ManutenzioneDiMezzoNonEsisteException(MezzoTrasporto mezzoTrasporto) {
        super("Ci si aspettava che la manutenzione del mezzo esistesse invece non esiste; dettagli: il mezzo in questione era: " + mezzoTrasporto);
    }
}