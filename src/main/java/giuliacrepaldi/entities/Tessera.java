package giuliacrepaldi.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tessere")
public class Tessera {

    @Id
    @GeneratedValue

    @Column(name = "tessera_id")
    private UUID id;
    @OneToMany(mappedBy = "tessera_id")
    private List <Abbonamento> abbonamenti = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "vendite_trasporti_id")
    private VenditaTrasporto venditaTrasporto;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "mezzo_di_trasporto_id")
    private MezzoTrasporto mezzoTrasporto;

    @Column(name = "data_inizio_tessera", nullable = false)
    private LocalDate dataInizioTessera;

    @Column(name = "data_fine_tessera", nullable = false)
    private LocalDate dataFineTessera;

    protected Tessera(){}

    public Tessera(Utente utente, LocalDate dataInizioTessera, LocalDate dataFineTessera) {
        if (dataInizioTessera.isAfter(dataFineTessera)) {
            throw new IllegalArgumentException("La data di inizio deve essere precedente alla data di fine");
        }
        this.utente = utente;
        this.dataInizioTessera = dataInizioTessera;
        this.dataFineTessera = dataFineTessera;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public VenditaTrasporto getVenditaTrasporto() {
        return venditaTrasporto;
    }

    public Utente getUtente() {
        return utente;
    }

    public MezzoTrasporto getMezzoTrasporto() {
        return mezzoTrasporto;
    }

    public LocalDate getDataInizioTessera() {
        return dataInizioTessera;
    }

    public LocalDate getDataFineTessera() {
        return dataFineTessera;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "abbonamenti=" + abbonamenti +
                ", venditaTrasporto=" + venditaTrasporto +
                ", utente=" + utente +
                ", mezzoTrasporto=" + mezzoTrasporto +
                ", dataInizioTessera=" + dataInizioTessera +
                ", dataFineTessera=" + dataFineTessera +
                '}';
    }
}
