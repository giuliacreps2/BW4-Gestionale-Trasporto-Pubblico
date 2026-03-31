package giuliacrepaldi.exceptions.miscellanous;

public class QuantitaNonValidaException extends RuntimeException {
    public QuantitaNonValidaException(double quantita) {
        super("La quantità " + quantita + " deve essere una quantità valida, quindi un numero intero e > 0.");
    }

    public QuantitaNonValidaException(String quantita) {
        super("La quantità " + quantita + " deve essere una quantità valida, quindi un numero intero e > 0.");
    }
}
