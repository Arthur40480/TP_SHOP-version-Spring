package fr.fms;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;

@SpringBootApplication
public class SpringTpShopApplication implements CommandLineRunner {
	private static Scanner scan = new Scanner(System.in);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringTpShopApplication.class, args);
        System.out.println("Bienvenue dans notre application de gestion d'articles ! \n");
        int choice = 0;
        while(choice!= 12) {
        	displayMainMenu();
        	choice = scanInt();
        	switch(choice) {
				case 1 : System.out.println("Choix numéro 1");				
					break;					
				case 2 : System.out.println("Choix numéro 2");
					break;					
				case 3 : System.out.println("Choix numéro 3");
					break;					
				case 4 : System.out.println("Choix numéro 4");
					break;
				case 5 : System.out.println("Choix numéro 5");
					break;
				case 6 : System.out.println("Choix numéro 6");
					break;
				case 7 : System.out.println("Choix numéro 7");
					break;
				case 8 : System.out.println("Choix numéro 8");
					break;
				case 9 : System.out.println("Choix numéro 9");
					break;
				case 10 : System.out.println("Choix numéro 10");
					break;
				case 11 : System.out.println("Choix numéro 11");
					break;	
				case 12 : System.out.println("A bientôt !");
					break;
        	}
        }
    }

	@Override
    public void run(String... args) throws Exception {
    }

    public static void displayMainMenu() {
        System.out.println(
        		"1: Afficher tous les articles sans pagination \n"
                + "2: Afficher tous les articles avec pagination \n"
                + "****************************************** \n"
                + "3: Ajouter un article \n"
                + "4: Afficher un article \n"
                + "5: Supprimer un article \n"
                + "6: Mettre à jour un article \n"
                + "****************************************** \n"
                + "7: Ajouter une categorie \n"
                + "8: Afficher une categorie \n"
                + "9: Supprimer une categorie \n"
                + "10: Mettre à jour une categorie \n"
                + "11: Afficher tous les articles d'une categorie \n"
                + "****************************************** \n"
                + "12: Sortir du programme");
    }
    
    // VERIF SCAN
	public static int scanInt() {
		while(!scan.hasNextInt()) {
			System.out.println("Saisissez une valeur entière svp");
			scan.next();
		}
		return scan.nextInt();
	}
}
