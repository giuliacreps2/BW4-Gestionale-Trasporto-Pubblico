package giuliacrepaldi.dao;

import jakarta.persistence.EntityManager;

public class PercorrenzeDAO {
    private final EntityManager entityManager;

    public PercorrenzeDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Un amministratore del sistema deve poter calcolare il tempo medio effettivo di percorrenza
// di una tratta da parte di un mezzo.
    //TEMPO MEDIO EFFETTIVO

    //Metodi
    //1. save(Percorrenza p)
    //2. findById(UUID id)
    //3. findByMezzo(UUID mezzoId)
    //4. findByTratta(UUID trattaId)
    //5. findByMezzoAndTratta(UUID mezzoId, UUID trattaId)
    //6. countByMezzoAndTratta(UUID mezzoId, UUID trattaId)
    //7. getTempoMedioEffettivo(UUID mezzoId, UUID trattaId)

}
