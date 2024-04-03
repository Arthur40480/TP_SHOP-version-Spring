package fr.fms.business;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.fms.entities.Category;

public interface IBusiness {
	// CATEGORIES
	public List<Category> displayAllCategory();
	public Optional<Category> displayCategoryById(Long categoryId);
	public boolean deleteCategoryById(Long categoryId);
	public boolean updateCategoryById(Long categoryId, String categoryName);
}
