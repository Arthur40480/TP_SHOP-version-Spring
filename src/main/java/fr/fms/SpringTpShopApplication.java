package fr.fms;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

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
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(SpringTpShopApplication.class, args);
    }

	@Override
    public void run(String... args) throws Exception {
//		Category smartphone = categoryRepository.save(new Category("Smartphone"));
//		Category pc = categoryRepository.save(new Category("PC"));
//		Category imgAndSound = categoryRepository.save(new Category("Image & Son"));
//		Category tablet = categoryRepository.save(new Category("Tablet"));
//		Category game = categoryRepository.save(new Category("Jeux"));
//		Category consumable = categoryRepository.save(new Category("Consomables"));
//		
//		articleRepository.save(new Article("Samsung", "S10", 250, smartphone));
//		articleRepository.save(new Article("Apple", "Pro18", 800, smartphone));
//		articleRepository.save(new Article("Xiaomi", "MI10", 80, smartphone));
//		articleRepository.save(new Article("Nokia", "6300XY", 150, smartphone));
//		articleRepository.save(new Article("Apple", "Pro19", 1200, smartphone));
//		articleRepository.save(new Article("Samsung", "S12", 400, smartphone));
//		
//		articleRepository.save(new Article("Samsung", "GalaxyTab", 450, tablet));	
//		articleRepository.save(new Article("Apple", "Ipad", 1200, tablet));	
//		
//		articleRepository.save(new Article("MSI", "Modern1515", 630, pc));	
//		articleRepository.save(new Article("MSI", "GF63", 899, pc));
//		articleRepository.save(new Article("ACER", "Aspire3", 399, pc));	
//		articleRepository.save(new Article("ASUS", "Vivobook", 469, pc));
//		articleRepository.save(new Article("Lenovo", "IdeaPad", 783, pc));	
//		articleRepository.save(new Article("Lenovo", "ThinkBook14S", 1299, pc));
//		
//		articleRepository.save(new Article("Samsung", "TV LED 43", 399, imgAndSound));	
//		articleRepository.save(new Article("LG", "TV 32LQ6", 219, imgAndSound));
//		
//		articleRepository.save(new Article("PS5", "MortalKombat 11", 22, game));	
//		articleRepository.save(new Article("PS5", "COD Black Ops ", 32, game));
//		
//		articleRepository.save(new Article("Canon", "Cartouche B-32 Noir", 7, consumable));	
//		articleRepository.save(new Article("Epson", "Cartouche C-54 Couleur ", 19, consumable));
		
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
					default : 
						System.out.println("Choix invalide !");
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
    
    public void displayAllArticleWithoutPagination() {
    	List<Article> articleList = business.displayAllArticle();
    	if(articleList.isEmpty()) {
    		System.out.println();
    		System.out.println("Aucun article pour le moment !");
    	}else {
        	String separator = "+------+----------------------+------------------------------------------+----------------------+--------------------------------+";
        	String header = String.format("| %-4s | %-20s | %-40s | %-20s | %-30s |", COLUMN_ID, COLUMN_BRAND, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_CATEGORIE);
        	System.out.println();
        	System.out.println(separator);
        	System.out.println(header);
        	System.out.println(separator);
        	for(Article article : articleList) {
        		System.out.printf("| %-4s | %-20s | %-40s | %-20s | %-30s |%n", article.getId(), article.getBrand(), article.getDescription(), article.getPrice() + "€", article.getCategory().getName());
        	}
        	System.out.println(separator);
    	}
    }
    
    public void displayAllArticleWithPagination() {
    	int page = 0;
    	int size = 5;
    	boolean exit = false;
    	String separator = "+------+----------------------+------------------------------------------+----------------------+--------------------------------+";
    	String header = String.format("| %-4s | %-20s | %-40s | %-20s | %-30s |", COLUMN_ID, COLUMN_BRAND, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_CATEGORIE);
    	
    	while(!exit) {
    		Page<Article> articlePage = business.getArticlePerPage(page, size);
    		displayPaginationMenu(size);
    		System.out.println();
    		System.out.println(separator);
    		System.out.println(header);
    		System.out.println(separator);

        	for(Article article : articlePage.getContent()) {
        		System.out.printf("| %-4s | %-20s | %-40s | %-20s | %-30s |%n", article.getId(), article.getBrand(), article.getDescription(), article.getPrice() + "€", article.getCategory().getName());
        	}
    		System.out.println(separator);
    		displayPagination(articlePage);
        	String userChoice = scan.nextLine();
        	switch(userChoice.toLowerCase()) {
        		case "exit":
        			exit = true;
        			break;
        		case "prev":
        			if(articlePage.hasPrevious()) {
        				page--;
        				break;
        			}else {
        				System.out.println("Vous êtes déjà sur la première page !");
        				break;
        			}
        		case "next":
        			if(articlePage.hasNext()) {
        				page++;
        				break;
        			}else {
        				System.out.println("Vous êtes déjà à la dernière page !");
        				break;
        			}
        		case "page":
        			if(size == 5) {
        				size = 7;
        				page = 0;
        				break;
        			}else {
        				size = 5;
        				page = 0;
        				break;
        			}
        		default:
        			System.out.println("Choix invalide !");
        	}
    	}
    }
    
    public void displayPaginationMenu(int articlePerPage) {
		System.out.println("EXIT \u2192 sortir de la pagination" );
		System.out.println("PREV \u2192 page precedente" );
		System.out.println("NEXT \u2192 page suivante" );
		System.out.println("PAGE \u2192 afficher " + articlePerPage +" articles par page" );
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
    
    public void deleteArticleById() {
    	System.out.print("Veuillez indiquer l'id de l'article à suprimé: ");
    	Long searchedIdArticle = scanLong();
    	if(business.deleteArticleById(searchedIdArticle)) {
    		System.out.println();
    		System.out.println("Article supprimé !");
    	} else {
    		System.out.println();
    		System.out.println("Article introuvable !");
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
                System.out.println("Article introuvable !");
            }
        }else {
            System.out.println();
            System.out.println("Catégorie introuvable !");
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
    		System.out.println();
    		System.out.println("Catégorie introuvable !");
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
    		System.out.println("Catégorie introuvable !");
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
    			System.out.println();
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
    		System.out.println();
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
