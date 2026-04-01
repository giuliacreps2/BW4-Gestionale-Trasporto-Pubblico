package giuliacrepaldi.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "percorrenze")

public class Percorrenza {
    @Id
    @GeneratedValue

    @Column(name = "percorrenza_id", nullable = false)
    private UUID percorrenzaId;
    @Column(name = "tempo_effettivo_percorrenza", nullable = false)
    private LocalDateTime tempoEffettivoPercorrenza;
    @Column(name = "data_percorrenza", nullable = true)
    private LocalDateTime dataPercorrenza;

    @ManyToOne
    @JoinColumn(name = "tratta")
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "mezzo_di_trasporto")
    private MezzoTrasporto mezzoTrasporto;

    //Costruttore
    protected Percorrenza() {

    }

    public Percorrenza(LocalDateTime tempoEffettivoPercorrenza) {
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
    }

    public Percorrenza(UUID percorrenzaId, LocalDateTime tempoEffettivoPercorrenza, LocalDateTime dataPercorrenza) {
        this.percorrenzaId = percorrenzaId;
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
        this.dataPercorrenza = dataPercorrenza;
    }

    //Getter & Setter
    public UUID getPercorrenzaId() {
        return percorrenzaId;
    }

    public LocalDateTime getTempoEffettivoPercorrenza() {
        return tempoEffettivoPercorrenza;
    }

    public void setTempoEffettivoPercorrenza(LocalDateTime tempoEffettivoPercorrenza) {
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
    }

    public LocalDateTime getDataPercorrenza() {
        return dataPercorrenza;
    }

    public void setDataPercorrenza(LocalDateTime dataPercorrenza) {
        this.dataPercorrenza = dataPercorrenza;
    public MezzoTrasporto getMezzoTrasporto() {
        return mezzoTrasporto;
    }

    public Tratta getTratta() {
        return tratta;
    }

    @Override
    public String toString() {
        return "Percorrenza{" +
                "percorrenzaId=" + percorrenzaId +
                ", tempoEffettivoPercorrenza=" + tempoEffettivoPercorrenza +
                ", dataPercorrenza=" + dataPercorrenza +
                ", tratta=" + tratta +
                ", mezzoTrasporto=" + mezzoTrasporto +
                ", percorrenzaId=" + percorrenzaId +
                ", tempoEffettivoPercorrenza=" + tempoEffettivoPercorrenza +
                '}';
    }
}
