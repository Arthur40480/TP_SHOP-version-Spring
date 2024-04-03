package fr.fms.business;

import java.util.List;
import java.util.Optional;

import fr.fms.entities.Category;

public interface IBusiness {
	// CATEGORIES
	public List<Category> displayAllCategory();
	public Optional<Category> displayCategoryById(Long categoryId);
}
