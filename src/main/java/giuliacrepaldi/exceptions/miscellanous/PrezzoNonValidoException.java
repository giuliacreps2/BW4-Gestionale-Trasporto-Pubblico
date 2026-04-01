package giuliacrepaldi.exceptions.miscellanous;

public class PrezzoNonValidoException extends RuntimeException {
    public PrezzoNonValidoException(double prezzo) {
        super("Il prezzo " + prezzo + " deve essere un prezzo valido.");
    }
}
