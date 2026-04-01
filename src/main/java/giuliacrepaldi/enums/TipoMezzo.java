package giuliacrepaldi.enums;

public enum TipoMezzo {
    AUTOBUS(19.4, 80), TRAM(11, 200);

    private double velocitàMediaKmh;
    private int capienza;

    TipoMezzo(double velocitàMediaKmh, int capienza) {
        this.velocitàMediaKmh = velocitàMediaKmh;
        this.capienza = capienza;
    }

    public double getVelocitàMediaKmh() {
        return velocitàMediaKmh;
    }

    public int getCapienza() {
        return capienza;
    }
}
