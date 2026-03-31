package giuliacrepaldi.entities;

import giuliacrepaldi.enums.TipoAbbonamento;
import giuliacrepaldi.exceptions.abbonamento.TipoAbbonamentoNonDefinitoException;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
@DiscriminatorValue("abbonamento")
public class Abbonamento extends VenditaTrasporto {
    
    @ManyToOne
    @JoinColumn(name = "tessera_id", nullable = false)
    private Tessera tessera;
    
    @Column(name = "tipo_abbonamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;
    
    private boolean attivo;
    
    @Column(name = "data_inizio_abbonamento", nullable = false)
    private LocalDate dataInizioAbbonamento;
    
    @Column(name = "data_fine_abbonamento", nullable = false)
    private LocalDate dataFineAbbonamento;
    
    protected Abbonamento() {}
    
    public Abbonamento(PuntoEmissione puntoEmissione, double prezzo, Tessera tessera, TipoAbbonamento tipoAbbonamento) {
        super(puntoEmissione, prezzo);
        this.tessera = tessera;
        this.tipoAbbonamento = tipoAbbonamento;
        
        LocalDate dataOggi = LocalDate.now();
        // l'abbonamento comincia adesso
        this.dataInizioAbbonamento = dataOggi;
        this.dataFineAbbonamento = calcolaDataFineAbbonamentoDa(dataOggi);
    }
    
    private LocalDate calcolaDataFineAbbonamentoDa(LocalDate dataInizio) {
        if(tipoAbbonamento == null) {
            throw new TipoAbbonamentoNonDefinitoException(this);
        }
        if (tipoAbbonamento == TipoAbbonamento.MENSILE) {
            return dataInizio.plusMonths(1);
        } else if (tipoAbbonamento == TipoAbbonamento.SETTIMANALE) {
            return dataInizio.plusWeeks(1);
        }
        throw new RuntimeException("C'è almeno un tipo abbonamento che non è stato coperto.");
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public LocalDate getDataFineAbbonamento() {
        return dataFineAbbonamento;
    }
    

    public LocalDate getDataInizioAbbonamento() {
        return dataInizioAbbonamento;
    }
    

    public Tessera getTessera() {
        return tessera;
    }
    
    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "tipoAbbonamento=" + tipoAbbonamento +
                ", dataFineAbbonamento=" + dataFineAbbonamento +
                ", attivo=" + attivo +
                ", dataInizioAbbonamento=" + dataInizioAbbonamento +
                '}';
    }
}
