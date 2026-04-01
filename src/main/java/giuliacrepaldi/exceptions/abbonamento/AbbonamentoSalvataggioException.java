package giuliacrepaldi.exceptions.abbonamento;

import giuliacrepaldi.entities.Abbonamento;
import giuliacrepaldi.interfaces.exceptions.AbbonamentoGenericException;

public class AbbonamentoSalvataggioException extends RuntimeException implements AbbonamentoGenericException {
    public AbbonamentoSalvataggioException(Abbonamento abbonamento) {
        super("Errore durante salvataggio dell'abbonamento " + abbonamento);
    }
}
