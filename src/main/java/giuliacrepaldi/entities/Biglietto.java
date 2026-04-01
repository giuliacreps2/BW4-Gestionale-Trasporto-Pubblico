package giuliacrepaldi.entities;

import giuliacrepaldi.exceptions.biglietto.BigliettoGiaObliteratoException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "biglietti")
@DiscriminatorValue("biglietto")
public class Biglietto extends VenditaTrasporto  {
    
    @ManyToOne
    @JoinColumn(name = "obliterato_da")
    private MezzoTrasporto obliteratoDa;
    
    // TODO: FIX: data e ora deve contenere data e ora, non solo ora.
    //  quale tipo java serve? (stessa cosa per fine obliterazione)
    //  fatto questo, nel metodo setObliteratoDa, va cambiato 
    //  anche l'assegnamento di inizio e fine obliterazione
    @Column(name = "data_e_ora_inizio_obliterazione")
    private LocalDateTime dataEOraInizioObliterazione;

    @Column(name = "data_e_ora_fine_obliterazione")
    private LocalDateTime dataEOraFineObliterazione;
    
    protected Biglietto() {}

    public Biglietto(PuntoEmissione puntoEmissione, double prezzo) {
        super(puntoEmissione, prezzo);
    }
    
    public MezzoTrasporto getObliteratoDa() {
        return obliteratoDa;
    }

    /**
     * Modifica questo biglietto e lo imposta come obliterato.
     * Imposta anche le date di inizio e fine obliterazione.
     */
    public void setObliteratoDa(MezzoTrasporto obliteratoDa) throws BigliettoGiaObliteratoException {
        // se il biglietto è stato già obliterato, non 
        // può essere di nuovo obliterato
        if(this.obliteratoDa != null) {
            throw new BigliettoGiaObliteratoException(this);
        }
        
        this.obliteratoDa = obliteratoDa;
        
        LocalDateTime adesso = LocalDateTime.now();
        
        // imposta data inizio obliterazione
        this.dataEOraInizioObliterazione = adesso;
        //
        // // imposta data fine obliterazione
        this.dataEOraFineObliterazione =  calcolaDataEOraFineObliterazioneDa(adesso);
    }
    
    private LocalDateTime calcolaDataEOraFineObliterazioneDa(LocalDateTime momentoInizio) {
        return momentoInizio.plusHours(1);
    }

    public LocalDateTime getDataEOraFineObliterazione() {
        return dataEOraFineObliterazione;
    }
    

    public LocalDateTime getDataEOraInizioObliterazione() {
        return dataEOraInizioObliterazione;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "dataEOraFineObliterazione=" + dataEOraFineObliterazione +
                ", dataEOraInizioObliterazione=" + dataEOraInizioObliterazione +
                '}';
    }
}
