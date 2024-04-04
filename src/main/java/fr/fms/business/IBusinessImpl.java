package fr.fms.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Component
public class IBusinessImpl implements IBusiness {
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
	public IBusinessImpl() {}
	
	// ARTICLE
	
    /**
     * Affiche tous les articles.
     * 
     * @return Liste de tous les articles
     */
	public List<Article> displayAllArticle() {
		return articleRepository.findAll();
	}
	
    /**
     * Crée un nouvel article.
     * 
     * @param newArticle article à créer
     * @return true si l'article a été créé avec succès, false sinon
     */
	public boolean createArticle(Article newArticle) {
		List<Article> articleList = articleRepository.findAll();
		for(Article article : articleList) {
			if(article.getDescription().equals(newArticle.getDescription()) && article.getBrand().equals(newArticle.getBrand())) {
				return false;
			}
		}
		articleRepository.save(newArticle);
		return true;
	}
	
    /**
     * Affiche un article par son identifiant.
     * 
     * @param articleId Identifiant de l'article à afficher
     * @return Article correspondant à l'identifiant
     */
	public Optional<Article> displayArticleById(Long articleId) {
		return articleRepository.findById(articleId);
	}
	
    /**
     * Supprime un article par son identifiant.
     * 
     * @param articleId Identifiant de l'article à supprimer
     * @return true si l'article a été supprimé avec succès, false sinon
     */
	public boolean deleteArticleById(Long articleId) {
		Optional<Article> articleIdToDelete = articleRepository.findById(articleId);
		if(articleIdToDelete.isPresent()) {
			articleRepository.deleteById(articleId);
			return true;
		}else {
			return false;
		}
	}
	
    /**
     * Met à jour un article par son identifiant.
     * 
     * @param articleId Identifiant de l'article à mettre à jour
     * @param articleBrand Nouvelle marque de l'article
     * @param articleDescription Nouvelle description de l'article
     * @param articlePrice Nouveau prix de l'article
     * @param categoryId Identifiant de la catégorie de l'article
     * @return true si l'article a été mis à jour avec succès, false sinon
     */
	public boolean updateArticleById(Long articleId, String articleBrand, String articleDescription, double articlePrice, Long categoryId) {
		Optional<Article> articleIdToUpdate = articleRepository.findById(articleId);
		if(articleIdToUpdate.isPresent()) {
			articleRepository.updateArticleById(articleDescription, articleBrand, articlePrice, categoryId, articleId);
			return true;
		}else {
			return false;
		}
	}
	
    /**
     * Récupère une page d'articles.
     * 
     * @param page Numéro de la page
     * @param size Taille de la page
     * @return Page d'articles
     */
	public Page<Article> getArticlePerPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
	    return articleRepository.findAll(pageable);
	}
	
	// CATEGORY
	
    /**
     * Affiche toutes les catégories.
     * 
     * @return Liste de toutes les catégories
     */
	public List<Category> displayAllCategory() {
		return categoryRepository.findAll();
	}
	
    /**
     * Affiche une catégorie par son identifiant.
     * 
     * @param categoryId Identifiant de la catégorie à afficher
     * @return Catégorie correspondant à l'identifiant
     */
	public Optional<Category> displayCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId);
	}
	
    /**
     * Supprime une catégorie par son identifiant.
     * 
     * @param categoryId Identifiant de la catégorie à supprimer
     * @return true si la catégorie a été supprimée avec succès, false sinon
     */
	public boolean deleteCategoryById(Long categoryId) {
		Optional<Category> categoryIdToDelete = categoryRepository.findById(categoryId);
		if(categoryIdToDelete.isPresent()) {
			categoryRepository.deleteById(categoryId);
			return true;
		}else {
			return false;
		}
	}
	
    /**
     * Met à jour une catégorie par son identifiant.
     * 
     * @param categoryId Identifiant de la catégorie à mettre à jour
     * @param categoryName Nouveau nom de la catégorie
     * @return true si la catégorie a été mise à jour avec succès, false sinon
     */
	public boolean updateCategoryById(Long categoryId, String categoryName) {
		Optional<Category> categoryIdToUpdate = categoryRepository.findById(categoryId);
		if(categoryIdToUpdate.isPresent()) {
			categoryRepository.updateCategoryById(categoryId, categoryName);
			return true;
		}else {
			return false;
		}
	}
	
    /**
     * Crée une nouvelle catégorie.
     * 
     * @param newCategory Nouvelle catégorie à créer
     * @return true si la catégorie a été créée avec succès, false sinon
     */
	public boolean createCategory(Category newCategory) {
		List<Category> categoryList = categoryRepository.findAll();
		for(Category category : categoryList) {
			if(category.getName().equals(newCategory.getName())) {
				return false;
			}
		}
		categoryRepository.save(newCategory);
		return true;	
	}
	
    /**
     * Trouve les articles par l'identifiant de la catégorie.
     * 
     * @param categoryId Identifiant de la catégorie
     * @return Liste d'articles appartenant à la catégorie donnée
     */
	public List<Article> findArticlesByCategoryId(Long categoryId) {
		return categoryRepository.findArticlesByCategoryId(categoryId);
	}
}
