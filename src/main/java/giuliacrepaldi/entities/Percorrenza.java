package giuliacrepaldi.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.UUID;

public class Percorrenza {
    private UUID percorrenzaId;
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

    //Getter e Setter
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
