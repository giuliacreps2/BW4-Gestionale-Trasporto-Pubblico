package giuliacrepaldi.entities;

import jakarta.persistence.*;

import java.time.Duration;
import java.util.UUID;

@Entity
@Table(name = "tratte")

public class Tratta {
    @Id
    @GeneratedValue

    @Column(name = "tratta_id")
    private UUID trattaId;
    @Column(name = "tratta_km", nullable = false)
    private double trattaKm;
    @Column(name = "tempo_previsto_tratta", nullable = false)
    private Duration tempoPrevistoTratta;
    @Column(name = "zona_partenza", nullable = false)
    private String zonaPartenza;
    @Column(name = "zona_arrivo", nullable = false)
    private String zonaArrivo;

    //Costruttore
    public Tratta() {

    }

    public Tratta(UUID trattaId, double trattaKm, Duration tempoPrevistoTratta, String zonaPartenza, String zonaArrivo) {
        this.trattaId = trattaId;
        this.trattaKm = trattaKm;
        this.tempoPrevistoTratta = tempoPrevistoTratta;
        this.zonaPartenza = zonaPartenza;
        this.zonaArrivo = zonaArrivo;
    }


    //Getter & Setter

    public UUID getTrattaId() {
        return trattaId;
    }

    public double getTrattaKm() {
        return trattaKm;
    }

    public void setTrattaKm(double trattaKm) {
        this.trattaKm = trattaKm;
    }

    public Duration getTempoPrevistoTratta() {
        return tempoPrevistoTratta;
    }

    public void setTempoPrevistoTratta(Duration tempoPrevistoTratta) {
        this.tempoPrevistoTratta = tempoPrevistoTratta;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getZonaArrivo() {
        return zonaArrivo;
    }

    public void setZonaArrivo(String zonaArrivo) {
        this.zonaArrivo = zonaArrivo;
    }
}
