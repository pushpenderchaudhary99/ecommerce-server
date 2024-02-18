package com.ecom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.exceptions.ProductException;
import com.ecom.exceptions.ReviewException;
import com.ecom.http.ReviewRequest;
import com.ecom.model.Product;
import com.ecom.model.Review;
import com.ecom.model.User;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.ReviewRepository;

@Service
public class ReviewServiceImplementation implements ReviewService{
	@Autowired
	private ReviewRepository reviewRepo;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepo;
	

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException, ReviewException {
		Product product = productService.findProductByID(req.getProductId());
		Review isPresent =reviewRepo.getReviewByProductAndUserId(product.getId(), user.getId());
		if(isPresent!=null) throw new ReviewException("Already Reviewd Product");
		Review review = new Review();
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setUser(user);
		review.setCreatedAt(LocalDateTime.now());
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getAllReviews(Long productId) {
		return reviewRepo.getAllProductReviews(productId);
	}

}
