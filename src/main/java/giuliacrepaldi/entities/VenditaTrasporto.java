package giuliacrepaldi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "vendite_trasporti")
@Inheritance(strategy = InheritanceType.JOINED)
// che tipo di vendita? biglietti, abbonamenti, tessere
@DiscriminatorColumn(name = "categoria_prodotto")
public abstract class VenditaTrasporto {

    @Id
    @GeneratedValue
    @Column(name = "vendita_trasporto_id")
    private UUID venditaTrasportoId;
    
    
    protected VenditaTrasporto() {}

}
