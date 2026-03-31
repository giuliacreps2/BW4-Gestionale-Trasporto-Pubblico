package giuliacrepaldi.entities;

import giuliacrepaldi.exceptions.biglietto.BigliettoGiaObliteratoException;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    private LocalDate dataEOraInizioObliterazione;

    @Column(name = "data_e_ora_fine_obliterazione")
    private LocalDate dataEOraFineObliterazione;
    
    protected Biglietto() {}

    public Biglietto(PuntoEmissione puntoEmissione, double prezzo) {
        super(puntoEmissione, prezzo);
    }
    
    public MezzoTrasporto getObliteratoDa() {
        return obliteratoDa;
    }

    public void setObliteratoDa(MezzoTrasporto obliteratoDa) {
        // se il biglietto è stato già obliterato, non 
        // può essere di nuovo obliterato
        if(this.obliteratoDa != null) {
            throw new BigliettoGiaObliteratoException(this);
        }
        
        this.obliteratoDa = obliteratoDa;
        
        // imposta data inizio obliterazione
        this.dataEOraInizioObliterazione = LocalDate.now();
        
        // imposta data fine obliterazione
        this.dataEOraFineObliterazione = LocalDate.now().plusDays(1);
    }


    public LocalDate getDataEOraFineObliterazione() {
        return dataEOraFineObliterazione;
    }
    

    public LocalDate getDataEOraInizioObliterazione() {
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
