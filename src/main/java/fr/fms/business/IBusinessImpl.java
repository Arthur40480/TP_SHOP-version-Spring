package fr.fms.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Optional<Article> displayArticleById(Long articleId) {
		return articleRepository.findById(articleId);
	}
	
	public boolean deleteArticleById(Long articleId) {
		Optional<Article> articleIdToDelete = articleRepository.findById(articleId);
		if(articleIdToDelete.isPresent()) {
			articleRepository.deleteById(articleId);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean updateArticleById(Long articleId, String articleBrand, String articleDescription, double articlePrice, Long categoryId) {
		Optional<Article> articleIdToUpdate = articleRepository.findById(articleId);
		if(articleIdToUpdate.isPresent()) {
			articleRepository.updateArticle(articleDescription, articleBrand, articlePrice, categoryId, articleId);
			return true;
		}else {
			return false;
		}
	}
	
	// CATEGORY
	public List<Category> displayAllCategory() {
		return categoryRepository.findAll();
	}
	
	public Optional<Category> displayCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId);
	}
	
	public boolean deleteCategoryById(Long categoryId) {
		Optional<Category> categoryIdToDelete = categoryRepository.findById(categoryId);
		if(categoryIdToDelete.isPresent()) {
			categoryRepository.deleteById(categoryId);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean updateCategoryById(Long categoryId, String categoryName) {
		Optional<Category> categoryIdToUpdate = categoryRepository.findById(categoryId);
		if(categoryIdToUpdate.isPresent()) {
			categoryRepository.updateCategoryById(categoryId, categoryName);
			return true;
		}else {
			return false;
		}
	}
	
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
	
	public List<Article> findArticlesByCategoryId(Long categoryId) {
		return categoryRepository.findArticlesByCategoryId(categoryId);
	}
}
