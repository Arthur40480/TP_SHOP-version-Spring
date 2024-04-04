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
import fr.fms.entities.Article;
//import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Category;

@SpringBootApplication
public class SpringTpShopApplication implements CommandLineRunner {
	private static Scanner scan = new Scanner(System.in);
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_NAME = "NOM";
	private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
	private static final String COLUMN_BRAND = "MARQUE";
	private static final String COLUMN_PRICE = "PRIX";
	private static final String COLUMN_CATEGORIE = "CATEGORIE";
	
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
	        while(choice!= 13) {
	        	displayMainMenu();
	        	choice = scanInt();
	        	switch(choice) {
					case 1 : System.out.println("Choix numéro 1");
						break;					
					case 2 : System.out.println("Choix numéro 2");
						break;					
					case 3 : 
						createNewArticle();
						waitForEnter();
						break;					
					case 4 : 
						displayOneArticleById();
						waitForEnter();
						break;
					case 5 : System.out.println("Choix numéro 5");
						break;
					case 6 : System.out.println("Choix numéro 6");
						break;
					case 7 : 
						createNewCategory();
						waitForEnter();
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
					case 11 : 
						updateCategoryById();
						waitForEnter();
						break;	
					case 12 : 
						displayArticleByCategoryId();
						waitForEnter();
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
    
    public void createNewArticle() {
    	System.out.print("Veuillez indiquer la marque de l'article: ");
    	String articleBrand = scan.nextLine();
    	System.out.print("Veuillez indiquer la description de l'article: ");
    	String articleDescription = scan.nextLine();
    	System.out.print("Veuillez indiquer le prix de l'article: ");
    	double articlePrice = scanDouble();
    	System.out.print("Veuillez indiquer l'id de la catégorie correspondant à cet article : ");
    	int categoryId = scanInt();
    	
    	List<Category> categoryList = business.displayAllCategory();
        boolean categoryExists = false;
        
        for (Category category : categoryList) {
            if (category.getId() == categoryId) {
                categoryExists = true;
                Article newArticle = new Article(articleBrand, articleDescription, articlePrice, category);
                if (business.createArticle(newArticle)) {
                    System.out.println();
                    System.out.println("Article ajouté : " + newArticle);
                } else {
                    System.out.println();
                    System.out.println("Cet article existe déjà !");
                }
                break; 
            }
        }
        if(!categoryExists) {
            System.out.println();
            System.out.println("Catégorie introuvable !");
        }
    }
    
    public void displayOneArticleById() {
        System.out.print("Veuillez indiquer l'id de l'article recherché: ");
        Long searchedIdArticle = scanLong();
        Article article = business.displayArticleById(searchedIdArticle).orElse(null);
        if(article != null) {
        	String separator = "+------+----------------------+------------------------------------------+----------------------+--------------------------------+";
        	String header = String.format("| %-4s | %-20s | %-40s | %-20s | %-30s |", COLUMN_ID, COLUMN_BRAND, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_CATEGORIE);
        	System.out.println();
        	System.out.println(separator);
        	System.out.println(header);
        	System.out.println(separator);
        	System.out.printf("| %-4s | %-20s | %-40s | %-20s | %-30s |%n", article.getId(), article.getBrand(), article.getDescription(), article.getPrice() + "€", article.getCategory().getName());
        	System.out.println(separator);
        }else {
        	System.out.println();
        	System.out.println("Article introuvable !");
        }   
    }
    
    public void displayAllCategory() {
		List<Category> categoryList = business.displayAllCategory();
		System.out.println();
		System.out.printf("%20s%n", "CATEGORIE");
		String separator = "+------+----------------------+";
		String header = String.format("| %-4s | %-20s |", COLUMN_ID, COLUMN_NAME);
		System.out.println(separator);
		System.out.println(header);
		System.out.println(separator);
		for(Category category : categoryList) {
			System.out.printf("| %-4s | %-20s |%n",category.getId(), category.getName());
		}
		System.out.println(separator);   	 
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
    
    public void updateCategoryById() {
    	System.out.print("Veuillez indiquer l'id de la categorie à modifiée: ");
    	Long searchedIdCategory = scanLong();
    	System.out.print("Veuillez indiquer le nouveau nom de la categorie: ");
    	String newCategoryName = scan.nextLine();
    	if(business.updateCategoryById(searchedIdCategory, newCategoryName)) {
    		System.out.println("Mise à jour effectuée ");
    	}else {
    		System.out.println("Catégorie introuvable !");
    	}
    	
    }
    
    public void createNewCategory() {
    	System.out.print("Veuillez indiquer le nom de la nouvelle categorie: ");
    	String categoryName = scan.nextLine();
    	Category newCategory = new Category(categoryName);
    	if(business.createCategory(newCategory)) {
    		System.out.println();
    		System.out.println("Catégorie ajoutée : " + newCategory);
    	}else {
    		System.out.println();
    		System.out.println("Cette catégorie éxiste déjà !");
    	}
    }
    
    public void displayArticleByCategoryId() {
    	System.out.print("Veuillez indiquer l'id de la catégorie pour afficher ses articles: ");
    	Long searchedIdCategory = scanLong();
    	Category category = business.displayCategoryById(searchedIdCategory).orElse(null);
    	if(category != null) {
    		List<Article> articleList = business.findArticlesByCategoryId(searchedIdCategory);
    		if(articleList.isEmpty()) {
    			System.out.println("Aucun article pour cette categorie !");
    		}else {
    			System.out.println();
    			System.out.printf("%45s %s%n", "CATEGORIE:", category.getName().toUpperCase());
    			String separator = "+------+----------------------+------------------------------------------+----------------------+";
    			String header = String.format("| %-4s | %-20s | %-40s | %-20s |", COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_BRAND, COLUMN_PRICE);
    			System.out.println(separator);
    			System.out.println(header);
    			System.out.println(separator);
    			for(Article article : articleList) {
    				System.out.printf("| %-4s | %-20s | %-40s | %-20s |%n", article.getId(), article.getDescription(), article.getBrand(), article.getPrice() + "€");
    			}
    			System.out.println(separator);
    		}
    	}else {
    		System.out.println("Catégorie introuvable !");
    	}
    	
    }
    
    public static void waitForEnter() {
    	System.out.println("Appuyer \u2192 ENTRER pour continuer ");
    	scan.nextLine();
    }
    
    // VERIF SCAN
    public static int scanInt() {
        while (!scan.hasNextInt()) {
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
    
    public static double scanDouble() {
        while (!scan.hasNextDouble()) {
            System.out.println("Saisissez une valeur décimale svp");
            scan.next();
        }
        double value = scan.nextDouble();
        scan.nextLine();
        return value;
    }

	
}
