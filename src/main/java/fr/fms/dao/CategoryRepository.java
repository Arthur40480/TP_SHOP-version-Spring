package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.fms.entities.Category;



public interface CategoryRepository  extends JpaRepository<Category, Long>{
	public Optional<Category> findById(Long categoryId);
	public boolean deleteCategoryById(Long categoryId);
	@Transactional
    @Modifying
    @Query("update Category c set c.name=:categoryName where c.id=:categoryId")
	public void updateCategoryById(@Param("categoryId") Long categoryId, @Param("categoryName") String categoryName);
//	public Optional<Category> findByName(String categoryName);
//	public List<Category> findAllByOrderByNameAsc();
//	public List<Category> findAllByOrderByNameDesc();
}
