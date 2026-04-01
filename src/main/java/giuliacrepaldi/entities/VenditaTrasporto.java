package giuliacrepaldi.entities;

import giuliacrepaldi.exceptions.miscellanous.PrezzoNonValidoException;
import giuliacrepaldi.exceptions.miscellanous.QuantitaNonValidaException;
import giuliacrepaldi.helpers.Validatore;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    
    @ManyToOne
    @JoinColumn(name = "punto_emissione_id", nullable = false)
    private PuntoEmissione puntoEmissione;
    
    @Column(nullable = false)
    private double prezzo;
    
    @Column(name = "data_vendita", nullable = false)
    private LocalDate dataVendita;
    
    protected VenditaTrasporto() {}

    public VenditaTrasporto(PuntoEmissione puntoEmissione, double prezzo) {
        this.puntoEmissione = puntoEmissione;
        // verifica che quantità sia valida
        if(!Validatore.ePrezzoValido(prezzo)) {
            throw new PrezzoNonValidoException(prezzo);
        }
        this.prezzo = prezzo;
        this.dataVendita = LocalDate.now();
    }

    public LocalDate getDataVendita() {
        return dataVendita;
    }
    

    public double getPrezzo() {
        return prezzo;
    }
    

    public UUID getVenditaTrasportoId() {
        return venditaTrasportoId;
    }
    

    public PuntoEmissione getPuntoEmissione() {
        return puntoEmissione;
    }

    @Override
    public String toString() {
        return "VenditaTrasporto{" +
                "prezzo=" + prezzo +
                ", dataVendita=" + dataVendita +
                ", venditaTrasportoId=" + venditaTrasportoId +
                '}';
    }
}
