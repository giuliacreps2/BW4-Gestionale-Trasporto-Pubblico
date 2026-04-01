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

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta trattaId;

    @ManyToOne
    @JoinColumn(name = "mezzo_di_trasporto")
    private MezzoTrasporto mezzoTrasportoId;

    //Costruttore
    public Percorrenza() {

    }

    public Percorrenza(UUID percorrenzaId, LocalDateTime tempoEffettivoPercorrenza) {
        this.percorrenzaId = percorrenzaId;
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
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
}
