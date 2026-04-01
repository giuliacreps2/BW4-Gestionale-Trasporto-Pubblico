package giuliacrepaldi.helpers;

public class Validatore {

    /**
     * Una quantità è valida se è > 0 ed è un numero intero.
     */
    public static boolean eQuantitaValida(double x) {
        return x > 0 && (x == (int) x);
    }

    /**
     * Un prezzo è valido se è > 0.
     */
    public static boolean ePrezzoValido(double x) {
        return x > 0;
    }
    
}
