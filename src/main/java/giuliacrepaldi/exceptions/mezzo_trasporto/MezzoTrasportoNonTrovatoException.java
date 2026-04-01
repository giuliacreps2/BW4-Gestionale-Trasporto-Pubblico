package giuliacrepaldi.exceptions.mezzo_trasporto;

import giuliacrepaldi.interfaces.exceptions.MezzoTrasportoGenericException;

import java.util.UUID;

public class MezzoTrasportoNonTrovatoException extends RuntimeException implements MezzoTrasportoGenericException {
    public MezzoTrasportoNonTrovatoException(UUID mezzoDiTrasportoId) {
        super("Il mezzo di trasporto con id: " + mezzoDiTrasportoId + " non è stato trovato");
    }
}
