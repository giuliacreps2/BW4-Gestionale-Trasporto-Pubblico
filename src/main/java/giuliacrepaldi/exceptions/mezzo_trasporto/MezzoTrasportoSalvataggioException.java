package giuliacrepaldi.exceptions.mezzo_trasporto;

import giuliacrepaldi.entities.MezzoTrasporto;

public class MezzoTrasportoSalvataggioException extends RuntimeException {
    public MezzoTrasportoSalvataggioException(MezzoTrasporto mezzoTrasporto) {
        super("Errore durante salvataggio del mezzo " + mezzoTrasporto);
    }
}
