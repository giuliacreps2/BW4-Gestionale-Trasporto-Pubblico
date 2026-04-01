package giuliacrepaldi.entities;


import giuliacrepaldi.enums.TipoMezzo;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "mezzi_di_trasporto")
public class MezzoTrasporto {

    @Id
    @GeneratedValue
    @Column(name = "mezzo_di_trasporto_id")
    private UUID mezzoDiTrasportoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mezzo", nullable = false)
    private TipoMezzo tipoMezzo;

    // @OneToMany(mappedBy = "mezzo_id")
    // private List <Manutenzione> manutenzioni = new ArrayList<>();
    //
    // @OneToMany(mappedBy = "obliteratoDa")
    // private List <Biglietto> biglietti = new ArrayList<>();
    //
    // @OneToMany(mappedBy = "obliterato_da")
    // private List <Tessera> tessere = new ArrayList<>();
    //
    // @OneToMany(mappedBy = "mezzo_di_trasporto_id")
    // private List <Percorrenza> percorrenze = new ArrayList<>();

    //Costruttore
    public MezzoTrasporto() {
    }

    public MezzoTrasporto(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    //Getter & Setter
    public UUID getMezzoDiTrasportoId() {
        return mezzoDiTrasportoId;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }


    // public List<Manutenzione> getManutenzioni() {
    //     return manutenzioni;
    // }
    //
    // public List<Biglietto> getBiglietti() {
    //     return biglietti;
    // }
    //
    // public List<Tessera> getTessere() {
    //     return tessere;
    // }
    //
    // public List<Percorrenza> getPercorrenze() {
    //     return percorrenze;
    // }

    @Override
    public String toString() {
        return "MezzoTrasporto{" +
                "mezzoDiTrasportoId=" + mezzoDiTrasportoId +
                '}';
    }
}
