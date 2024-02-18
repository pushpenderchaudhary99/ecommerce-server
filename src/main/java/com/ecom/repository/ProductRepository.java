package com.ecom.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p " +
	        "WHERE (p.category.name = :category OR :category = '') " +
	        "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
		    "AND (:minDiscount IS NULL OR p.discountPersent >= :minDiscount) " +
		    "ORDER BY " +
		    "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
		    "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
	List<Product> filterProducts(
	        @Param("category") String category,
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice,
			@Param("minDiscount") Integer minDiscount,
			@Param("sort") String sort
			);

	@Query("SELECT p FROM Product p " +
		       "WHERE (:myQuery IS NULL OR " +
		       "LOWER(p.title) LIKE LOWER(CONCAT('%', :myQuery, '%')) OR " +
		       "LOWER(p.brand) LIKE LOWER(CONCAT('%', :myQuery, '%')) OR " +
		       "LOWER(p.color) LIKE LOWER(CONCAT('%', :myQuery, '%')) OR " +
		       "LOWER(p.description) LIKE LOWER(CONCAT('%', :myQuery, '%')) OR " +
		       "LOWER(p.category.name) LIKE LOWER(CONCAT('%', :myQuery, '%'))) " + // Moved this line inside the previous OR clause
		       "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
		       "AND (:minDiscount IS NULL OR p.discountPersent >= :minDiscount) " +
		       "ORDER BY " +
		       "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
		       "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
    List<Product> findProductsByCriteria(@Param("myQuery") String myQuery,
                                         @Param("minPrice") Integer minPrice,
                                         @Param("maxPrice") Integer maxPrice,
                                         @Param("minDiscount") Integer minDiscount,
                                         @Param("sort") String sort);

    default List<Product> searchProducts(String myQuery,
                                         Integer minPrice,
                                         Integer maxPrice,
                                         Integer minDiscount,
                                         String sort) {
    	List<Product> products = new ArrayList<>();
        if (myQuery != null && !myQuery.isEmpty()) {
            // Split the search term into individual words
            String[] words = myQuery.split("\\s+");
             for(String queryWord :words)
            products.addAll( findProductsByCriteria(queryWord, minPrice, maxPrice, minDiscount, sort));
        }
        return products;
    }
}
