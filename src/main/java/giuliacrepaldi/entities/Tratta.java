package giuliacrepaldi.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    private LocalDateTime tempoPrevistoTratta;
    @Column(name = "zona_partenza", nullable = false)
    private String zonaPartenza;
    @Column(name = "zona_arrivo", nullable = false)
    private String zonaArrivo;

    //Costruttore
    protected Tratta() {

    }

    public Tratta(double trattaKm, LocalDateTime tempoPrevistoTratta, String zonaPartenza, String zonaArrivo) {
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

    public LocalDateTime getTempoPrevistoTratta() {
        return tempoPrevistoTratta;
    }

    public void setTempoPrevistoTratta(LocalDateTime tempoPrevistoTratta) {
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
