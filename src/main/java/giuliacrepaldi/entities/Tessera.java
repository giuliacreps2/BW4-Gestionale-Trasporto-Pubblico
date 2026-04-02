package giuliacrepaldi.entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tessere")
@DiscriminatorValue("tessera")
public class Tessera extends VenditaTrasporto {

    // la tessera è di un utente; un utente ha una tessera.
    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @Column(name = "data_inizio_tessera", nullable = false)
    private LocalDate dataInizioTessera;

    @Column(name = "data_fine_tessera", nullable = false)
    private LocalDate dataFineTessera;

    protected Tessera() {
    }

    public Tessera(PuntoEmissione puntoEmissione, double prezzo, Utente utente, LocalDate dataInizioTessera) {
        // siccome la tessera è una vendita trasporto, 
        // chiama il costruttore di vendita trasporto
        super(puntoEmissione, prezzo);
        this.utente = utente;
        this.dataInizioTessera = dataInizioTessera;
        // la tessera ha validità annuale
        this.dataFineTessera = dataInizioTessera.plusYears(1);
    }


    public Tessera(PuntoEmissione puntoEmissione, Utente utente, LocalDate dataInizioTessera) {
        // siccome la tessera è una vendita trasporto,
        // chiama il costruttore di vendita trasporto
        super(puntoEmissione);
        this.utente = utente;
        this.dataInizioTessera = dataInizioTessera;
        // la tessera ha validità annuale
        this.dataFineTessera = dataInizioTessera.plusYears(1);
    }


    public Utente getUtente() {
        return utente;
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
                "dataInizioTessera=" + dataInizioTessera +
                ", dataFineTessera=" + dataFineTessera +
                ", nomeUtente=" + utente.getNome() + " " + utente.getCognome() +
                '}';
    }
}
