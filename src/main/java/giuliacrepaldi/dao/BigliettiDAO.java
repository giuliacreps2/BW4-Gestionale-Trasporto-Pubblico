package giuliacrepaldi.dao;

import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.MezzoTrasporto;
import giuliacrepaldi.exceptions.biglietto.BigliettoGiaObliteratoException;
import giuliacrepaldi.exceptions.vendita_trasporto.VenditaTrasportoSalvataggioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class BigliettiDAO {

    private final EntityManager entityManager;
    
    public BigliettiDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Salva un biglietto.
     */
    public void salva(Biglietto biglietto) throws VenditaTrasportoSalvataggioException {
        new VenditeTrasportiDAO(entityManager).salva(biglietto);
    }

    /**
     * Oblitera un biglietto su un mezzo di trasporto.
     */
    public void obliteraBiglietto(Biglietto biglietto, MezzoTrasporto mezzoTrasporto) throws BigliettoGiaObliteratoException {
        
        // obliterare un biglietto su un mezzo di trasporto,
        // significa che il campo obliteratoDa del biglietto,
        // punta all'id del mezzo di trasporto

        biglietto.setObliteratoDa(mezzoTrasporto);
        salva(biglietto);
        
    }
    
    
    /**
     * Ottieni il totale dei biglietti vidimati sul mezzo di trasporto in input.
     */
    // public int contaBigliettiVidimati(MezzoTrasporto mezzoTrasporto) {
    //    
    // }

    /**
     * Ottieni il totale dei biglietti vidimati nel periodo dato. 
     */
    // public int contaBigliettiVidimati(LocalDate dataInizio, LocalDate dataFine) {
    //    
    // }
    
}
