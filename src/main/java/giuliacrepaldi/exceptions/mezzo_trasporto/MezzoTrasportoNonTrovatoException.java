package giuliacrepaldi.exceptions.mezzo_trasporto;

import giuliacrepaldi.interfaces.exceptions.MezzoTrasportoGenericException;

import java.util.UUID;

public class MezzoTrasportoNonTrovatoException extends RuntimeException implements MezzoTrasportoGenericException {
    public MezzoTrasportoNonTrovatoException(String targetIdentifier, String identifierType) {
        super("Il mezzo di trasporto con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovata.");
    }
    
    public MezzoTrasportoNonTrovatoException(UUID mezzoDiTrasportoId) {
        this(mezzoDiTrasportoId.toString(), "UUID");
    }
}
