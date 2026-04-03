package giuliacrepaldi.entities;


import giuliacrepaldi.exceptions.miscellanous.DataManutenzioneNonValidaException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "manutenzioni")
public class Manutenzione {

    @Id
    @GeneratedValue
    @Column(name = "manutenzione_id")
    private UUID manutenzioneId;

    @ManyToOne
    @JoinColumn(name = "mezzo_di_trasporto_id", nullable = false)
    private MezzoTrasporto mezzoTrasporto;

    @Column(name = "data_inizio_manutenzione", nullable = false)
    private LocalDate dataInizioManutenzione;

    @Column(name = "data_fine_manutenzione", nullable = false)
    private LocalDate dataFineManutenzione;

    @Column(name = "costo_manutenzione", nullable = false)
    private double costoManutenzione;
    
    @Column(name = "manutenzione_e_attiva", nullable = false)
    private boolean manutenzioneEAttiva;

    protected Manutenzione (){}

    public Manutenzione(MezzoTrasporto mezzoTrasporto, 
                        LocalDate dataInizioManutenzione, 
                        LocalDate dataFineManutenzione,
                        double costoManutenzione) throws DataManutenzioneNonValidaException
    {
        if (dataInizioManutenzione.isAfter(dataFineManutenzione)) {
            throw new DataManutenzioneNonValidaException("La data di inizio deve essere precedente alla data di fine");
        }
        
        this.mezzoTrasporto = mezzoTrasporto;
        this.dataInizioManutenzione = dataInizioManutenzione;
        this.dataFineManutenzione = dataFineManutenzione;
        this.costoManutenzione = costoManutenzione;
        this.setManutenzioneEAttiva(true);
    }
    
    public void setManutenzioneEAttiva(boolean manutenzioneEAttiva) {
        this.manutenzioneEAttiva = manutenzioneEAttiva;
    }

    public UUID getManutenzioneId() {
        return manutenzioneId;
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

    public double getCostoManutenzione() {
        return costoManutenzione;
    }

    @Override
    public String toString() {
        return "Manutenzione{" +
                "manutenzioneId=" + manutenzioneId +
                ", dataInizioManutenzione=" + dataInizioManutenzione +
                ", dataFineManutenzione=" + dataFineManutenzione +
                ", costoManutenzione=" + costoManutenzione +
                ", manutenzioneEAttiva=" + manutenzioneEAttiva +
                '}';
    }
}
