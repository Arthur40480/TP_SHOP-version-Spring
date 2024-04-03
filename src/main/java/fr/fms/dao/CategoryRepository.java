package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Category;



public interface CategoryRepository  extends JpaRepository<Category, Long>{
	public Optional<Category> findById(Long categoryId);
//	public Optional<Category> findByName(String categoryName);
//	public List<Category> findAllByOrderByNameAsc();
//	public List<Category> findAllByOrderByNameDesc();
}
