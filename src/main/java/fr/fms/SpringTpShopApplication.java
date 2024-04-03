package fr.fms;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.fms.business.IBusinessImpl;
import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
//import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Category;

@SpringBootApplication
public class SpringTpShopApplication implements CommandLineRunner {
	private static Scanner scan = new Scanner(System.in);
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_NAME = "NAME";
	
	@Autowired
	private IBusinessImpl business;
	
    @Autowired
    private ArticleRepository articleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringTpShopApplication.class, args);
    }

	@Override
    public void run(String... args) throws Exception {
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
					case 8 : 
						displayAllCategory();
						waitForEnter();
						break;
					case 9 : 
						displayOneCategoryById();
						waitForEnter();
						break;
					case 10 : 
						deleteCategoryById();
						waitForEnter();
						break;
					case 11 : System.out.println("Choix numéro 11");
						break;	
					case 12 : System.out.println("A bientôt !");
						break;
					case 13 : System.out.println("A bientôt !");
						break;
	        	}
	        }
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
                + "8: Afficher toute les categories \n"
                + "9: Afficher une categorie \n"
                + "10: Supprimer une categorie \n"
                + "11: Mettre à jour une categorie \n"
                + "12: Afficher tous les articles d'une categorie \n"
                + "****************************************** \n"
                + "13: Sortir du programme");
    }
    
    public void displayAllCategory() {
		List<Category> categoryList = business.displayAllCategory();
		System.out.println();
		String header = String.format("| %-4s | %-20s |", COLUMN_ID, COLUMN_NAME);
		System.out.println(header);
		for(Category category : categoryList) {
			System.out.printf("| %-4s | %-20s |%n",category.getId(), category.getName());
		}
		System.out.println();
    }
    
    public void displayOneCategoryById() {
    	System.out.print("Veuillez indiquer l'id de la categorie recherchée: ");
    	Long searchedIdCategory = scanLong();
    	Category category = business.displayCategoryById(searchedIdCategory).orElse(null);
    	if(category != null) {
    		System.out.println();
    		System.out.println(category);
    	}else {
    		System.out.println("Catégorie introuvable !");
    	}
    }
    
    public void deleteCategoryById() {
    	System.out.print("Veuillez indiquer l'id de la categorie à suprimée: ");
    	Long searchedIdCategory = scanLong();
    	if(business.deleteCategoryById(searchedIdCategory)) {
    		System.out.println("Categorie supprimée !");
    	} else {
    		System.out.println("Catégorie introuvable !");
    	}
    }
    
    public static void waitForEnter() {
    	System.out.println("Appuyer \u2192 ENTRER pour continuer ");
    	scan.nextLine();
    }
    
    // VERIF SCAN
	public static int scanInt() {
		while(!scan.hasNextInt()) {
			System.out.println("Saisissez une valeur entière svp");
			scan.next();
		}
		int value = scan.nextInt();
	    scan.nextLine(); 
	    return value;
	}
	
	public static long scanLong() {
	    while (!scan.hasNextLong()) {
	        System.out.println("Saisissez une valeur entière svp");
	        scan.next();
	    }
	    long value = scan.nextLong();
	    scan.nextLine();
	    return value;
	}
	
	
}
