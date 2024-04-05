package fr.fms;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Article;
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
	private static final String SEPARATEUR_MENU = "****************************************** \n";
	private static final String SEPARATEUR_ARTICLE = "+------+----------------------+------------------------------------------+----------------------+--------------------------------+";
	private static final String SEPARATEUR_ARTICLE_WITHOUT_CATEGORY = "+------+----------------------+------------------------------------------+----------------------+";
	private static final String SEPARATEUR_CATEGORY = "+------+----------------------+";
	private static final String FORMAT_ARTICLE = "| %-4s | %-20s | %-40s | %-20s | %-30s |";
	private static final String FORMAT_ARTICLE_WITHOUT_CATEGORY = "| %-4s | %-20s | %-40s | %-20s |";
	private static final String FORMAT_CATEGORY = "| %-4s | %-20s |";
	private static final String CATEGORY_NOT_FOUND_MSG = "Catégorie introuvable !";
	private static final String ARTICLE_NOT_FOUND_MSG = "Article introuvable !";

	private static final Logger logger = LoggerFactory.getLogger(SpringTpShopApplication.class);
	private final IBusinessImpl business;
	
    public SpringTpShopApplication(IBusinessImpl business) {
        this.business = business;
    }
	
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
					case 1 :
						displayAllArticleWithoutPagination();
						waitForEnter();
						break;					
					case 2 : 
						displayAllArticleWithPagination();
						break;					
					case 3 : 
						createNewArticle();
						waitForEnter();
						break;					
					case 4 : 
						displayOneArticleById();
						waitForEnter();
						break;
					case 5 : 
						deleteArticleById();
						waitForEnter();
						break;
					case 6 : 
						updateArticleById();
						waitForEnter();
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
					default : System.out.println("Choix invalide !");
	        	}
	        }
    }

    public static void displayMainMenu() {
        System.out.println(
        		"1: Afficher tous les articles sans pagination \n"
                + "2: Afficher tous les articles avec pagination \n"
                + SEPARATEUR_MENU
                + "3: Ajouter un article \n"
                + "4: Afficher un article \n"
                + "5: Supprimer un article \n"
                + "6: Mettre à jour un article \n"
                + SEPARATEUR_MENU
                + "7: Ajouter une categorie \n"
                + "8: Afficher toute les categories \n"
                + "9: Afficher une categorie \n"
                + "10: Supprimer une categorie \n"
                + "11: Mettre à jour une categorie \n"
                + "12: Afficher tous les articles d'une categorie \n"
                + SEPARATEUR_MENU
                + "13: Sortir du programme");
    }
    
    public void displayAllArticleWithoutPagination() {
    	List<Article> articleList = business.displayAllArticle();
    	if(articleList.isEmpty()) {
    		System.out.println();
    		System.out.println("Aucun article pour le moment !");
    	}else {
        	String header = String.format(FORMAT_ARTICLE, COLUMN_ID, COLUMN_BRAND, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_CATEGORIE);
        	System.out.println();
        	System.out.println(SEPARATEUR_ARTICLE);
        	System.out.println(header);
        	System.out.println(SEPARATEUR_ARTICLE);
        	for(Article article : articleList) {
        		System.out.printf(FORMAT_ARTICLE + "%n", article.getId(), article.getBrand(), article.getDescription(), article.getPrice() + "€", article.getCategory().getName());
        	}
        	System.out.println(SEPARATEUR_ARTICLE);
    	}
    }
    
    public void displayAllArticleWithPagination() {
    	int page = 0;
    	int size = 5;
    	boolean exit = false;
    	String header = String.format(FORMAT_ARTICLE, COLUMN_ID, COLUMN_BRAND, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_CATEGORIE);
    	
    	while(!exit) {
    		Page<Article> articlePage = business.getArticlePerPage(page, size);
    		displayPaginationMenu(size);
    		displayArticlePage(articlePage, header);
    		
        	String userChoice = scan.nextLine();
        	switch(userChoice.toLowerCase()) {
        		case "exit":
        			exit = true;
        			break;
        		case "prev":
        			page = navigatePreviousPage(articlePage, page);
        			break;
        		case "next":
        			page = navigateNextPage(articlePage, page);
        			break;
        		case "page":
        			size = changeSizePage(size);
        			page = 0;
        			break;
        		default:
        			System.out.println("Choix invalide !");
        	}
    	}
    }
    
    public void displayArticlePage(Page<Article> articlePage, String header) {
		System.out.println();
		System.out.println(SEPARATEUR_ARTICLE);
		System.out.println(header);
		System.out.println(SEPARATEUR_ARTICLE);

    	for(Article article : articlePage.getContent()) {
    		System.out.printf(FORMAT_ARTICLE + "%n", article.getId(), article.getBrand(), article.getDescription(), article.getPrice() + "€", article.getCategory().getName());
    	}
    	System.out.println(SEPARATEUR_ARTICLE);
    	displayPagination(articlePage);
    }
    
    public int navigatePreviousPage(Page<Article> articlePage, int page) {
    	if(articlePage.hasPrevious()) {
    		page--;
    		return page;
    	}else {
    		System.out.println("Vous êtes déjà sur la première page !");
    		return page;
    	}
    }
    
    public int navigateNextPage(Page<Article> articlePage, int page) {
    	if(articlePage.hasNext()) {
    		page++;
    		return page;
    	}else {
    		System.out.println("Vous êtes déjà sur la dernière page !");
    		return page;
    	}
    }
    
    public int changeSizePage(int size) {
    	return size == 5 ? 7 : 5;
    }
    
    public void displayPaginationMenu(int articlePerPage) {
    	System.out.println();
		System.out.println("EXIT \u2192 sortir de la pagination" );
		System.out.println("PREV \u2192 page precedente" );
		System.out.println("NEXT \u2192 page suivante" );
	    if (articlePerPage == 5) {
	        System.out.println("PAGE \u2192 afficher 7 articles par page");
	    } else {
	        System.out.println("PAGE \u2192 afficher 5 articles par page");
	    }
    }
    
    public void displayPagination(Page<Article> articlePage) {
    	int totalPages = articlePage.getTotalPages();
    	int currentPage = articlePage.getNumber();
    	System.out.printf("%55s", " PREV \u2190 [");
    	for(int i = 0; i<totalPages; i++) {
    		if(i == currentPage) {
    			System.out.print("{" + i + "}" );
    		}else {
    			System.out.print(" " + i + " ");
    		}
    	}
    	System.out.print("] \u2192 NEXT");
    	System.out.println();
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
            logger.error("Categorie introuvable: {}", CATEGORY_NOT_FOUND_MSG);
        }
    }
    
    public void displayOneArticleById() {
        System.out.print("Veuillez indiquer l'id de l'article recherché: ");
        Long searchedIdArticle = scanLong();
        Article article = business.displayArticleById(searchedIdArticle).orElse(null);
        if(article != null) {
        	String header = String.format(FORMAT_ARTICLE, COLUMN_ID, COLUMN_BRAND, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_CATEGORIE);
        	System.out.println();
        	System.out.println(SEPARATEUR_ARTICLE);
        	System.out.println(header);
        	System.out.println(SEPARATEUR_ARTICLE);
        	System.out.printf(FORMAT_ARTICLE + "%n", article.getId(), article.getBrand(), article.getDescription(), article.getPrice() + "€", article.getCategory().getName());
        	System.out.println(SEPARATEUR_ARTICLE);
        }else {
        	System.out.println();
        	logger.error("Article introuvable: {}", ARTICLE_NOT_FOUND_MSG);
        }   
    }
    
    public void deleteArticleById() {
    	System.out.print("Veuillez indiquer l'id de l'article à suprimé: ");
    	Long searchedIdArticle = scanLong();
    	if(business.deleteArticleById(searchedIdArticle)) {
    		System.out.println();
    		System.out.println("Article supprimé !");
    	} else {
    		System.out.println();
    		logger.error("Article introuvable: {}", ARTICLE_NOT_FOUND_MSG);
    	}
    }
    
    public void updateArticleById() {
    	System.out.print("Veuillez indiquer l'id de l'article à modifié: ");
    	Long searchedIdArticle = scanLong();
    	System.out.print("Veuillez indiquer la nouvelle marque de l'article: ");
    	String newArticleBrand = scan.nextLine();
    	System.out.print("Veuillez indiquer la nouvelle description de l'article: ");
    	String newArticleDescription = scan.nextLine();
    	System.out.print("Veuillez indiquer le nouveau prix de l'article: ");
    	double newArticlePrice = scanDouble();
    	System.out.print("Veuillez indiquer le nouvel id de la catégorie correspondante à l'article: ");
    	Long newArticleCategoryId = scanLong();
    	
        Optional<Category> category = business.displayCategoryById(newArticleCategoryId);
        if (category.isPresent()) {
            
            if (business.updateArticleById(searchedIdArticle, newArticleBrand, newArticleDescription, newArticlePrice, newArticleCategoryId)) {
                System.out.println();
                System.out.println("Mise à jour effectuée ");
            } else {
                System.out.println();
                logger.error("Article introuvable: {}", ARTICLE_NOT_FOUND_MSG);
            }
        }else {
            System.out.println();
            logger.error("Categorie introuvable: {}", CATEGORY_NOT_FOUND_MSG);
        }
    }
    
    public void displayAllCategory() {
		List<Category> categoryList = business.displayAllCategory();
		System.out.println();
		System.out.printf("%20s%n", COLUMN_CATEGORIE);
		String header = String.format(FORMAT_CATEGORY, COLUMN_ID, COLUMN_NAME);
		System.out.println(SEPARATEUR_CATEGORY);
		System.out.println(header);
		System.out.println(SEPARATEUR_CATEGORY);
		for(Category category : categoryList) {
			System.out.printf(FORMAT_CATEGORY+"%n",category.getId(), category.getName());
		}
		System.out.println(SEPARATEUR_CATEGORY);   	 
	}
    
    public void displayOneCategoryById() {
    	System.out.print("Veuillez indiquer l'id de la categorie recherchée: ");
    	Long searchedIdCategory = scanLong();
    	Category category = business.displayCategoryById(searchedIdCategory).orElse(null);
    	if(category != null) {
    		System.out.println();
    		System.out.println(category);
    	}else {
    		System.out.println();
    		logger.error("Categorie introuvable: {}", CATEGORY_NOT_FOUND_MSG);
    	}
    }
    
    public void deleteCategoryById() {
    	System.out.print("Veuillez indiquer l'id de la categorie à suprimée: ");
    	Long searchedIdCategory = scanLong();
    	if(business.deleteCategoryById(searchedIdCategory)) {
    		System.out.println();
    		System.out.println("Categorie supprimée !");
    	} else {
    		System.out.println();
    		logger.error("Categorie introuvable: {}", CATEGORY_NOT_FOUND_MSG);
    	}
    }
    
    public void updateCategoryById() {
    	System.out.print("Veuillez indiquer l'id de la categorie à modifiée: ");
    	Long searchedIdCategory = scanLong();
    	System.out.print("Veuillez indiquer le nouveau nom de la categorie: ");
    	String newCategoryName = scan.nextLine();
    	if(business.updateCategoryById(searchedIdCategory, newCategoryName)) {
    		System.out.println();
    		System.out.println("Mise à jour effectuée ");
    	}else {
    		System.out.println();
    		logger.error("Categorie introuvable: {}", CATEGORY_NOT_FOUND_MSG);
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
    			System.out.println();
    			System.out.println("Aucun article pour cette categorie !");
    		}else {
    			System.out.println();
    			System.out.printf("%45s %s%n", COLUMN_CATEGORIE+":", category.getName().toUpperCase());
    			String header = String.format(FORMAT_ARTICLE_WITHOUT_CATEGORY, COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_BRAND, COLUMN_PRICE);
    			System.out.println(SEPARATEUR_ARTICLE_WITHOUT_CATEGORY);
    			System.out.println(header);
    			System.out.println(SEPARATEUR_ARTICLE_WITHOUT_CATEGORY);
    			for(Article article : articleList) {
    				System.out.printf(FORMAT_ARTICLE_WITHOUT_CATEGORY + "%n", article.getId(), article.getDescription(), article.getBrand(), article.getPrice() + "€");
    			}
    			System.out.println(SEPARATEUR_ARTICLE_WITHOUT_CATEGORY);
    		}
    	}else {
    		System.out.println();
    		logger.error("Categorie introuvable: {}", CATEGORY_NOT_FOUND_MSG);
    	}
    	
    }
    
    public static void waitForEnter() {
    	System.out.println("Appuyer \u2192 ENTRER pour continuer ");
    	scan.nextLine();
    }
    
    // VERIF SCAN
    public static int scanInt() {
        while (!scan.hasNextInt()) {
        	logger.error("Saisissez une valeur entière svp");
            scan.next();
        }
        int value = scan.nextInt();
        scan.nextLine();
        return value;
    }

    public static long scanLong() {
        while (!scan.hasNextLong()) {
        	logger.error("Saisissez une valeur entière svp");
            scan.next();
        }
        long value = scan.nextLong();
        scan.nextLine(); 
        return value;
    }
    
    public static double scanDouble() {
        while (!scan.hasNextDouble()) {
        	logger.error("Saisissez une valeur décimale svp");
            scan.next();
        }
        double value = scan.nextDouble();
        scan.nextLine();
        return value;
    }
	
}
