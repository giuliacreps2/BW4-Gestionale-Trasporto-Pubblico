package giuliacrepaldi.exceptions.punto_emissione;

import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.interfaces.exceptions.PuntoEmissioneGenericException;

import java.util.UUID;

public class PuntoEmissioneRimozioneException extends RuntimeException implements PuntoEmissioneGenericException {
    public PuntoEmissioneRimozioneException(String targetIdentifier, String identifierType) {
        super("Non si è potuto rimuovere il punto emissione con tipo identificatore " + identifierType + " con valore " + targetIdentifier);
    }
    public PuntoEmissioneRimozioneException(UUID targetId) {
        this(targetId.toString(), "UUID");
    }
}
