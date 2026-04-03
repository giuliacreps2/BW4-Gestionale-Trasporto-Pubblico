package giuliacrepaldi.tests.giuseppe;

import giuliacrepaldi.dao.*;
import giuliacrepaldi.entities.*;
import giuliacrepaldi.enums.TipoAbbonamento;
import giuliacrepaldi.enums.TipoMezzo;
import giuliacrepaldi.enums.TipologiaPuntoEmissione;
import giuliacrepaldi.exceptions.tessera.TesseraSalvataggioException;
import giuliacrepaldi.interfaces.exceptions.TesseraGenericException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

// qui vanno diversi tipi di test e sperimenti 
// ad esempio, aggiungi i dati che ti interessano
// e fai gli esperimenti che ti interessano     
public class AppTest {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI-giuseppe");
    
    public static void main(String[] args) {


        System.out.println("*** SUCCESSFULLY CONNECTED TO DB (in AppTest di Giuseppe) ***");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // ****** DAO
        GestoreAziendaDAO gestoreAziendaDAO = new GestoreAziendaDAO(entityManager);
        
        AbbonamentiDAO abbonamentiDAO = new AbbonamentiDAO(entityManager);
        BigliettiDAO bigliettiDAO = new BigliettiDAO(entityManager);
        ManutenzioniDAO manutenzioniDAO = new ManutenzioniDAO(entityManager);
        MezziTrasportoDAO mezziTrasportoDAO = new MezziTrasportoDAO(entityManager);
        PercorrenzeDAO percorrenzeDAO = new PercorrenzeDAO(entityManager);
        PuntiEmissioneDAO puntiEmissioneDAO = new PuntiEmissioneDAO(entityManager);
        TessereDAO tessereDAO = new TessereDAO(entityManager);
        TratteDAO tratteDAO = new TratteDAO(entityManager);
        UtentiDAO utentiDAO = new UtentiDAO(entityManager);
        VenditeTrasportiDAO venditeTrasportiDAO = new VenditeTrasportiDAO(entityManager);

        PuntoEmissione puntoEmissione1 = new PuntoEmissione(
                "Roma",
                TipologiaPuntoEmissione.RIVENDITORE_AUTORIZZATO,
                true
        );
        // puntiEmissioneDAO.salva(puntoEmissione1);
        
        Utente utente1 = new Utente(
                "Giuseppe",
                "Tavella",
                29,
                "xyz@gmail.com"
        );

        MezzoTrasporto mezzoTrasporto1 = new MezzoTrasporto(TipoMezzo.AUTOBUS);
         
        
        // mezziTrasportoDAO.salva(mezzoTrasporto1);
        // puntiEmissioneDAO.salva(puntoEmissione1);
        // utentiDAO.salva(utente1);
        // mezziTrasportoDAO.salva(mezzoTrasporto1);
        
        // ******** TROVA PER ID 
        Utente utente1FromDB = utentiDAO.trovaPerId("f26ea746-96e8-45a6-9361-7ec19bf33558");
        Tessera tessera1FromDB = tessereDAO.trovaPerId("416eb551-7374-4115-b59d-f621ec869c2d");
        PuntoEmissione puntoEmissione1FromDB = puntiEmissioneDAO.trovaPerId("924d0962-c252-46d9-91d1-33f3a6c60a77");
        Abbonamento abbonamento1FromDB = abbonamentiDAO.trovaPerId("5d3869f9-18db-4f88-9bfe-1bf874af3aa6");
        // PuntoEmissione puntoEmissione1FromDB = puntiEmissioneDAO.findById(UUID.fromString("ea8feaae-62d2-4f5e-a73f-13838321ec58"));
        Biglietto biglietto1FromDB = bigliettiDAO.trovaPerId("46ee9149-3e57-4d96-8594-33ee48e7ce8d");
        MezzoTrasporto mezzoTrasporto1FromDB = mezziTrasportoDAO.trovaPerId("c4729d6b-6e27-4613-8d16-739a1b360c43");
        Manutenzione manutenzione1FromDB = manutenzioniDAO.trovaPerId("30cf0185-be3f-4a3c-94e6-d3d841c92954");
        Tratta tratta1FromDB = tratteDAO.trovaPerId("832e1f39-4966-4fe6-bbd6-45d0aaa8e27d");
        Percorrenza percorrenza1FromDB = percorrenzeDAO.trovaPerId("20d53217-fd31-4e27-9bfa-fd4b660f7400");
        
        // ******** RIMOZIONE PER ID
        // bigliettiDAO.rimuoviPerId("1e62a537-a823-452c-9f95-d94a947646b1");
        
        
        // ******** RINNOVA TESSERA
        // tessereDAO.rinnovaTessera("416eb551-7374-4115-b59d-f621ec869c2d");
        
        // ******** STATISTICHE
        long quantiBigliettiEAbbonamentiInPeriodo = venditeTrasportiDAO.ottieniQuantiBigliettiEAbbonamentiEmessiInPeriodo(
                LocalDate.now().minusDays(3), 
                LocalDate.now().plusDays(3)
        );
        
        // boolean utente1HaTessera = utentiDAO.utenteHaTessera(utente1);
        
        // System.out.println(tessera1FromDB);
        // System.out.println(utente1HaTessera);
        // System.out.println(abbonamento1FromDB);
        // System.out.println(biglietto1FromDB);
        // System.out.println(manutenzione1FromDB);
        // System.out.println(tratta1FromDB);
        // System.out.println(percorrenza1FromDB);

        // biglietto1FromDB.setObliteratoDa();
        // System.out.println(puntoEmissione1FromDB);
        // System.out.println(biglietto1FromDB);
                
        //
        // System.out.println(puntoEmissione1FromDB);
        // System.out.println(utente1FromDB);
        // System.out.println(biglietto1FromDB);

        // System.out.println(quantiBigliettiEAbbonamentiInPeriodo);
        

        // System.out.println(puntoEmissione1FromDB);

        // // Tessera
        // Tessera tessera1 = new Tessera(
        //         puntoEmissione1FromDB,
        //     45.231,
        //     utente1FromDB, 
        //         LocalDate.now()
        // );

        // System.out.println(tessera1);
        
        // try {
        //
        //     tessereDAO.salva(tessera1);
        //
        // } catch (RuntimeException ex) {
        //     if(ex instanceof TesseraGenericException) {
        //         System.out.println(ex.getMessage());
        //     }
        // }
        //
        //
        //
        Biglietto biglietto1 = new Biglietto(
                puntoEmissione1FromDB,
                23.45
        );
        
        //
        // Abbonamento abbonamento1 = new Abbonamento(
        //        puntoEmissione1FromDB,
        //         43.23,
        //         tessera1FromDB,
        //         TipoAbbonamento.SETTIMANALE
        // );
        
        // Manutenzione manutenzione1 = new Manutenzione(
        //         mezzoTrasporto1FromDB,
        //         LocalDate.now(),
        //         LocalDate.now().plusMonths(1),
        //         45.23
        // );
        
        // Tratta tratta1 = new Tratta(
        //         234,
        //         44,
        //         "roma",
        //         "milano"
        // );
        
        Percorrenza percorrenza1 = new Percorrenza(
                354,
                LocalDateTime.now(),
                tratta1FromDB,
                mezzoTrasporto1FromDB
        );

        // System.out.println(tratta1);
        
        // abbonamentiDAO.salva(abbonamento1);
        // bigliettiDAO.salva(biglietto1);
        // manutenzioniDAO.salva(manutenzione1);
        // tratteDAO.salva(tratta1);
        // percorrenzeDAO.salva(percorrenza1);
        
        // System.out.println(mezzoTrasporto1);

        // NEXT TASK: PERCORRENZA TROVA PER ID
        
        
        entityManager.close();
        entityManagerFactory.close();
    }
}
