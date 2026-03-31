package giuliacrepaldi.exceptions.abbonamento;

import giuliacrepaldi.entities.Abbonamento;
import giuliacrepaldi.interfaces.exceptions.AbbonamentoGenericException;

public class TipoAbbonamentoNonDefinitoException extends RuntimeException implements AbbonamentoGenericException  {
    public TipoAbbonamentoNonDefinitoException(Abbonamento abbonamento) {
        super("Il tipo di abbonamento (mensile, settimanale, ecc.) di " + abbonamento + " non è definito/non esiste.");
    }
}
