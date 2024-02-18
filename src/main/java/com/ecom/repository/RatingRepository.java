package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{
	@Query("SELECT r FROM Rating r WHERE r.product.id = :productId")
	public List<Rating> getAllProductRatings(@Param("productId") Long productId);
	@Query("SELECT r FROM Rating r Where r.product.id = :productId AND r.user.id = :userId")
	public Rating getRatingByProductAndUserId(@Param("productId") Long productId,@Param("userId") Long userId);

}
