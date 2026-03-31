package giuliacrepaldi.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mezzi_di_trasporto")
public class MezzoTrasporto {

    @Id
    @GeneratedValue

    @Column(name = "mezzo_di_trasporto_id")
    private UUID id;

    @OneToMany(mappedBy = "mezzo_id")
    private List <Manutenzione> manutenzioni = new ArrayList<>();

    @OneToMany(mappedBy = "obliterato_da")
    private List <Biglietto> biglietti = new ArrayList<>();

    @OneToMany(mappedBy = "obliterato_da")
    private List <Tessera> tessere = new ArrayList<>();

    @OneToMany(mappedBy = "mezzo_di_trasporto_id")
    private List <Percorrenza> percorrenze = new ArrayList<>();

    protected MezzoTrasporto (){}

    public UUID getId() {
        return id;
    }

    public List<Manutenzione> getManutenzioni() {
        return manutenzioni;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public List<Tessera> getTessere() {
        return tessere;
    }

    public List<Percorrenza> getPercorrenze() {
        return percorrenze;
    }

    @Override
    public String toString() {
        return "MezzoTrasporto{" +
                "id=" + id +
                ", manutenzioni=" + manutenzioni +
                ", biglietti=" + biglietti +
                ", tessere=" + tessere +
                ", percorrenze=" + percorrenze +
                '}';
    }
}
