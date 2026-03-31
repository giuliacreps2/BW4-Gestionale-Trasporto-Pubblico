package giuliacrepaldi.entities;

import giuliacrepaldi.enums.CategoriaProdoto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vendite_trasporti")
public class VenditaTrasporto {

    @Id
    @GeneratedValue
    private UUID venditaTrasportoId;

    @ManyToOne
    @JoinColumn(name = "punto_emissione_id", nullable = false)
    private PuntoEmissione puntoEmissione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProdotto categoriaProdotto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;

    @Column(nullable = false)
    private LocalDate dataVendita;

    public VenditaTrasporto() {
    }
    public VenditaTrasporto(PuntoEmissione puntoEmissione, CategoriaProdotto categoriaProdotto, BigDecimal prezzo, LocalDate dataVendita) {
        this.puntoEmissione = puntoEmissione;
        this.categoriaProdotto = categoriaProdotto;
        this.prezzo = prezzo;
        this.dataVendita = dataVendita;
    }
    public UUID getVenditaTrasportoId() {
        return venditaTrasportoId;
    }
    public void setVenditaTrasportoId(UUID venditaTrasportoId) {
        this.venditaTrasportoId = venditaTrasportoId;
    }
    public PuntoEmissione getPuntoEmissione() {
        return puntoEmissione;
    }
    public void setPuntoEmissione(PuntoEmissione puntoEmissione) {
        this.puntoEmissione = puntoEmissione;
    }
    public CategoriaProdotto getCategoriaProdotto() {
        return categoriaProdotto;
    }
    public void setCategoriaProdotto(CategoriaProdotto categoriaProdotto) {
        this.categoriaProdotto = categoriaProdotto;
    }
    public BigDecimal getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
    public LocalDate getDataVendita() {
        return dataVendita;
    }
    public void setDataVendita(LocalDate dataVendita) {
        this.dataVendita = dataVendita;
    }
    @Override
    public String toString() {
        return "VenditaTrasporto{" +
                "venditaTrasportoId=" + venditaTrasportoId +
                ", puntoEmissione=" + (puntoEmissione != null ? puntoEmissione.getPuntoEmissioneId() : null) +
                ", categoriaProdotto=" + categoriaProdotto +
                ", prezzo=" + prezzo +
                ", dataVendita=" + dataVendita +
                '}';
    }
}
