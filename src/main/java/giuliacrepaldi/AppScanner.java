package giuliacrepaldi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

// qui va la richiesta del progetto: solo lo scanner
public class AppScanner {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GESTIONALE-TRASPORTI-PUBBLICI");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        System.out.println("SCEGLI MENU");
        System.out.println("1. Utente");
        System.out.println("2. Amministratore");

        int scelta = Integer.parseInt(scanner.nextLine());
        switch (scelta) {
            case 1:
                MenuUtente.mostraMenu(em, scanner);
                break;
            case 2:
                MenuAmministratore.mostraMenu(em, scanner);
                break;
            default:
                System.out.println("Scelta non valida");
        }

        em.close();
        emf.close();
    }
}
