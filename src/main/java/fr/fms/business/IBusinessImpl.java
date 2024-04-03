package fr.fms.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Component
public class IBusinessImpl implements IBusiness {
    @Autowired
    private CategoryRepository categoryRepository;
    
	public IBusinessImpl() {}
	
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
	
	public List<Article> findArticlesByCategoryId(Long categoryId) {
		return categoryRepository.findArticlesByCategoryId(categoryId);
	}
}
