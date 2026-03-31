package giuliacrepaldi.entities;

import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "punti_emissione")
public class PuntoEmissione {

    @Id
    @GeneratedValue
    private UUID puntoEmissioneId;

    @Column(nullable = false)
    private String citta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipologiaPuntoEmissione tipologiaPuntoEmissione;

    @Column(nullable = false)
    private boolean attivo;

    @OneToMany(mappedBy = "puntoEmissione", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<VenditaTrasporto> vendite = new ArrayList<>();
    public PuntoEmissione() {
    }
    public PuntoEmissione(String citta, TipologiaPuntoEmissione tipologiaPuntoEmissione, boolean attivo) {
        this.citta = citta;
        this.tipologiaPuntoEmissione = tipologiaPuntoEmissione;
        this.attivo = attivo;
    }

    public UUID getPuntoEmissioneId() {
        return puntoEmissioneId;
    }
    public void setPuntoEmissioneId(UUID puntoEmissioneId) {
        this.puntoEmissioneId = puntoEmissioneId;
    }
    public String getCitta() {
        return citta;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }
    public TipologiaPuntoEmissione getTipologiaPuntoEmissione() {
        return tipologiaPuntoEmissione;
    }
    public void setTipologiaPuntoEmissione(TipologiaPuntoEmissione tipologiaPuntoEmissione) {
        this.tipologiaPuntoEmissione = tipologiaPuntoEmissione;
    }
    public boolean isAttivo() {
        return attivo;
    }
    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }
    public List<VenditaTrasporto> getVendite() {
        return vendite;
    }
    public void setVendite(List<VenditaTrasporto> vendite) {
        this.vendite = vendite;
    }

    @Override
    public String toString() {
        return "PuntoEmissione{" +
                "puntoEmissioneId=" + puntoEmissioneId +
                ", citta='" + citta + '\'' +
                ", tipologiaPuntoEmissione=" + tipologiaPuntoEmissione +
                ", attivo=" + attivo +
                '}';
    }
}
