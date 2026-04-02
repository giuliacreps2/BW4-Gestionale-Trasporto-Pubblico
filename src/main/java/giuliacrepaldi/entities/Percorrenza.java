package giuliacrepaldi.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "percorrenze")
public class Percorrenza {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "percorrenza_id", nullable = false)
    private UUID percorrenzaId;
    
    @Column(name = "tempo_effettivo_percorrenza", nullable = false)
    private Long tempoEffettivoPercorrenza;
    
    @Column(name = "data_percorrenza")
    private LocalDateTime dataPercorrenza;

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "mezzo_di_trasporto_id")
    private MezzoTrasporto mezzoTrasporto;

    //Costruttore
    protected Percorrenza() {

    }

    public Percorrenza(long tempoEffettivoPercorrenza) {
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
    }

    public Percorrenza(Long tempoEffettivoPercorrenza, LocalDateTime dataPercorrenza, Tratta tratta, MezzoTrasporto mezzoTrasporto) {
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
        this.dataPercorrenza = dataPercorrenza;
        this.tratta = tratta;
        this.mezzoTrasporto = mezzoTrasporto;
    }

    //Getter & Setter
    public UUID getPercorrenzaId() {
        return percorrenzaId;
    }

    public Long getTempoEffettivoPercorrenza() {
        return tempoEffettivoPercorrenza;
    }

    public void setTempoEffettivoPercorrenza(Long tempoEffettivoPercorrenza) {
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
    }

    public LocalDateTime getDataPercorrenza() {
        return dataPercorrenza;
    }

    public void setDataPercorrenza(LocalDateTime dataPercorrenza) {
        this.dataPercorrenza = dataPercorrenza;
    }

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
                '}';
    }
}
