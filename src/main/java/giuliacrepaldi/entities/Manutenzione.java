package giuliacrepaldi.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "manutenzioni")
public class Manutenzione {

    @Id
    @GeneratedValue

    @Column(name = "manutenzione_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "mezzo_di_trasporto_id", nullable = false)
    private MezzoTrasporto mezzoTrasporto;

    @Column(name = "data_inizio_manutenzione", nullable = false)
    private LocalDate dataInizioManutenzione;

    @Column(name = "data_fine_manutenzione", nullable = false)
    private LocalDate dataFineManutenzione;

    @Column(name = "costo_manutenzione", nullable = false)
    private int manutenzione;

    protected Manutenzione (){}

    public Manutenzione(MezzoTrasporto mezzoTrasporto, LocalDate dataInizioManutenzione, LocalDate dataFineManutenzione,
                        int manutenzione) {
        if (dataInizioManutenzione.isAfter(dataFineManutenzione)) {
            throw new IllegalArgumentException("La data di inizio deve essere precedente alla data di fine");
        }
        this.mezzoTrasporto = mezzoTrasporto;
        this.dataInizioManutenzione = dataInizioManutenzione;
        this.dataFineManutenzione = dataFineManutenzione;
        this.manutenzione = manutenzione;
    }

    public UUID getId() {
        return id;
    }

    public MezzoTrasporto getMezzoTrasporto() {
        return mezzoTrasporto;
    }

    public LocalDate getDataInizioManutenzione() {
        return dataInizioManutenzione;
    }

    public LocalDate getDataFineManutenzione() {
        return dataFineManutenzione;
    }

    public int getManutenzione() {
        return manutenzione;
    }

    @Override
    public String toString() {
        return "Manutenzione{" +
                "id=" + id +
                ", mezzoTrasporto=" + mezzoTrasporto +
                ", dataInizioManutenzione=" + dataInizioManutenzione +
                ", dataFineManutenzione=" + dataFineManutenzione +
                ", manutenzione=" + manutenzione +
                '}';
    }
}
