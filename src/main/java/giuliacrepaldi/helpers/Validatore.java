package giuliacrepaldi.helpers;

public class Validatore {
    
    public static boolean eQuantitaValida(double x) {
        return x > 0 && (x == (int) x);
    }
    
}
