package giuliacrepaldi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "biglietti")
public class Biglietto extends VenditaTrasporto  {
    
    
    
    protected Biglietto() {}
    
}
