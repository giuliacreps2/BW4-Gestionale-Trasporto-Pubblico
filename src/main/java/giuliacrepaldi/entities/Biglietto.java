package giuliacrepaldi.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "biglietti")
@DiscriminatorValue("biglietto")
public class Biglietto extends VenditaTrasporto  {
    
    // @ManyToOne
    // @JoinColumn(name = "obliterato_da")
    // private MezzoTrasporto obliteratoDa;
    
    @Column(name = "data_e_ora_inizio_obliterazione")
    private LocalDate dataEOraInizioObliterazione;

    @Column(name = "data_e_ora_fine_obliterazione")
    private LocalDate dataEOraFineObliterazione;
    
    protected Biglietto() {}
    
    
    
}
