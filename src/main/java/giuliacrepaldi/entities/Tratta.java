package giuliacrepaldi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tratte")

public class Tratta {
    private UUID trattaId;
    private double trattaKm;
    private LocalDateTime tempoPrevistoTratta;
    private String zonaPartenza;
    private String zonaArrivo;

    //Costruttore
    public Tratta() {

    }

    public Tratta(UUID trattaId, double trattaKm, LocalDateTime tempoPrevistoTratta, String zonaPartenza, String zonaArrivo) {
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
