package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.model.Rating;
import com.ecom.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	@Query("SELECT r FROM Review r WHERE r.product.id = :productId")
	public List<Review> getAllProductReviews(@Param("productId") Long productId);
	@Query("SELECT r FROM Review r Where r.product.id = :productId AND r.user.id = :userId")
	public Review getReviewByProductAndUserId(@Param("productId") Long productId,@Param("userId") Long userId);

}
