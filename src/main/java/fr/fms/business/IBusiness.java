package fr.fms.business;

import java.util.List;

import fr.fms.entities.Category;

public interface IBusiness {
	// CATEGORIES
	public List<Category> displayAllCategory();
	public Category displayCategoryById(int categoryId);
}
