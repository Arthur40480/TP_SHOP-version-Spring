package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
	public Optional<Category> findById(Long categoryId);
	public boolean deleteCategoryById(Long categoryId);
	@Transactional
    @Modifying
    @Query("update Category c set c.name=:categoryName where c.id=:categoryId")
	public void updateCategoryById(@Param("categoryId") Long categoryId, @Param("categoryName") String categoryName);
	@Query("select a from Article a where a.category.id=:categoryId order by a.brand")
	public List<Article> findArticlesByCategoryId(@Param("categoryId") Long categoryId);
}
