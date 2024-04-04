package fr.fms.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.fms.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	public Optional<Article> findById(Long articleId);
	public boolean deleteArticleById(Long articleId);
	@Transactional
    @Modifying
    @Query("update Article a set a.brand=:articleBrand, a.description=:articleDescription, a.price=:articlePrice, a.category.id=:categoryId where a.id=:articleId")
	public void updateArticleById(@Param("articleBrand") String articleBrand, @Param("articleDescription") String articleDescription, 
									@Param("articlePrice") double articlePrice, @Param("categoryId") Long categoryId, 
									@Param("articleId") Long articleId);
	@Query("select a from Article a")
	public Page<Article> getArticlePerPage(Pageable pageable);
}
