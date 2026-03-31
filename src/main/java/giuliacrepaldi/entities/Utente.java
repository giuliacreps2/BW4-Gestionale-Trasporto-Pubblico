package giuliacrepaldi.entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "utente")
public class Utente {

    @Id
    @GeneratedValue

    @Column(name = "utente_id")
    private UUID id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cognome", nullable = false)
    private String cognome;
    @Column(name = "età", nullable = false)
    private int eta;
    @Column(name = "email", nullable = false)
    private String email;

    protected Utente(){}

    public Utente(String nome, String cognome, int eta, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.email = email;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", eta=" + eta +
                ", email='" + email + '\'' +
                '}';
    }
}
