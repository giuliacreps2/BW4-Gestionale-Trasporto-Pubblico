package giuliacrepaldi.exceptions.punto_emissione;

import giuliacrepaldi.interfaces.exceptions.PuntoEmissioneGenericException;

import java.util.UUID;

public class PuntoEmissioneNonTrovatoException extends RuntimeException implements PuntoEmissioneGenericException {
    public PuntoEmissioneNonTrovatoException(String targetIdentifier, String identifierType) {
        super("Il punto di emissione  con tipo identificatore " + identifierType + " con valore " + targetIdentifier + " non è stato trovato.");
    }
    public PuntoEmissioneNonTrovatoException(UUID targetId) {
        this(targetId.toString(), "UUID");
    }
}
