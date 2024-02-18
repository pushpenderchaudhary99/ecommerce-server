package com.ecom.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.exceptions.ProductException;
import com.ecom.exceptions.ReviewException;
import com.ecom.http.ReviewRequest;
import com.ecom.model.Review;
import com.ecom.model.User;

public interface ReviewService {
	public Review createReview(ReviewRequest req,User user)throws ProductException,ReviewException;
	public List<Review> getAllReviews(Long productId);

}
