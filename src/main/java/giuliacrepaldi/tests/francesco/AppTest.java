package giuliacrepaldi.tests.francesco;



import giuliacrepaldi.dao.BigliettiDAO;
import giuliacrepaldi.dao.PuntiEmissioneDAO;
import giuliacrepaldi.entities.Biglietto;
import giuliacrepaldi.entities.PuntoEmissione;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;
import java.util.UUID;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

    static Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-francesco");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
    BigliettiDAO bd = new BigliettiDAO(em);

        System.out.println("GESTIONALE-TRASPORTI-PUBBLICI");
        System.out.println("Seleziona ruolo: ");
        System.out.println("1 Utente");
        System.out.println("2 Amministratore");

        int sceltaRuolo = Integer.parseInt(scanner.nextLine());
        if (sceltaRuolo==1){
            menuUtente();
        } else if (sceltaRuolo==2) {
            menuAmministratore();
        } else {
            System.out.println("Scelta non valida");
        }
    }

     public static void creaBiglietto(EntityManager em, Scanner scanner) {

     BigliettiDAO bigliettiDAO = new BigliettiDAO(em);
     PuntiEmissioneDAO puntiDAO = new PuntiEmissioneDAO(em);

     try {
         //     // 1. Mostra punti disponibili
         //     System.out.println("=== PUNTI EMISSIONE DISPONIBILI ===");
         //     puntiDAO.findAll().forEach(p ->
         //             System.out.println(p.getId() + " - " + p.getTipologia())
         //     );

         // 2. Input ID
         System.out.print("Inserisci ID punto emissione: ");
         UUID idPunto = UUID.fromString(scanner.nextLine());

         // 3. Recupero dal DB
         PuntoEmissione punto = puntiDAO.findById(idPunto);

         if (punto == null) {
             System.out.println("Punto emissione non trovato!");
             return;
         }

         System.out.print("Inserisci prezzo: ");
         double prezzo = Double.parseDouble(scanner.nextLine());

         Biglietto biglietto = new Biglietto(punto, prezzo);

         bigliettiDAO.salva(biglietto);

         System.out.println("Biglietto creato!");
         System.out.println(biglietto);

     } catch (Exception e) {
         System.out.println("Errore: " + e.getMessage());
     }
}
    public static void menuUtente(){
        int scelta;
        do {
            System.out.println("MENU UTENTE");
            System.out.println("Scelta: ");
            System.out.println("1. Crea biglietto");
            System.out.println("2. Crea abbonamento");
            System.out.println("3. Ottieni zona partenza/arrivo");
            System.out.println("4. Verifica se un mezzo è in servizio");
            System.out.println("5. Verifica se un distributore è in servizio");
            System.out.println("0. Esci");
            scelta = Integer.parseInt(scanner.nextLine());
             switch (scelta) {
                 case 1:
                     EntityManager em = entityManagerFactory.createEntityManager();
                     creaBiglietto(em, scanner);
                     break;
             }
        }while (scelta != 0);
    }
    public static void menuAmministratore(){
        int scelta;
        do {
            System.out.println("Menu Amministratore");
            System.out.println("1. Gestigli Biglietto");
            System.out.println("2. Gestisci Abbonamento");
            System.out.println("3. Crea punto di emissione");
            System.out.println("4. Crea Tessera");
            System.out.println("5. Statistiche Biglietti");
            System.out.println("6. Statistiche abbonamenti");
            System.out.println("7. Statistiche punti vendita");
            System.out.println("8. Statistiche mezzo");
            System.out.println("9. Ottieni tempo percorrenza");
            System.out.println("10. Ottieni zona partenza/arrivo");
            System.out.println("11. Verifica se un mezzo è in servizio");
            System.out.println("12. Verifica se un distributore è in servizio");
            System.out.println("13. Trova i mezzi dell'azienda");
            System.out.println("0. Esci");
            scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta){

            }
        }while (scelta != 0);
    }

}

