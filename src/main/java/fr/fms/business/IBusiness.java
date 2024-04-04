package fr.fms.business;

import java.util.List;
import java.util.Optional;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

public interface IBusiness {
	// ARTICLES
	public List<Article> displayAllArticle();
	public boolean createArticle(Article article);
	public Optional<Article> displayArticleById(Long articleId);
	public boolean deleteArticleById(Long articleId);
	public boolean updateArticleById(Long articleId, String articleBrand, String articleDescription, double articlePrice, Long categoryId);
	
	// CATEGORIES
	public List<Category> displayAllCategory();
	public Optional<Category> displayCategoryById(Long categoryId);
	public boolean deleteCategoryById(Long categoryId);
	public boolean updateCategoryById(Long categoryId, String categoryName);
	public boolean createCategory(Category category);
	public List<Article> findArticlesByCategoryId(Long categoryId);
}
