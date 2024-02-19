package com.ecom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.exceptions.ProductException;
import com.ecom.exceptions.RatingException;
import com.ecom.http.RatingRequest;
import com.ecom.model.Product;
import com.ecom.model.Rating;
import com.ecom.model.User;
import com.ecom.repository.RatingRepository;
@Service
public class RatingServiceImplementaion implements RatingService{
	@Autowired
	private RatingRepository ratingRepo;
	@Autowired
	private ProductService productService;
	
	

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException, RatingException {
		Product product=productService.findProductByID(req.getProductId());
		Rating isPresent = ratingRepo.getRatingByProductAndUserId(product.getId(), user.getId());
		if(isPresent!=null) throw new RatingException("Already rated this product");
		Rating rating = new Rating();
		rating.setProduct(product);
		if(req.getRating()>0&&req.getRating()<=5)rating.setRating(req.getRating());
		rating.setUser(user);
		rating.setCreatedAt(LocalDateTime.now());
		Rating savedRating = ratingRepo.save(rating);
		
		
			System.out.println("in The Thread");
			int totalRatings=product.getRatings().size();
			System.out.println("TOTAL RAINGS"+totalRatings);
			int totalRatingsValue = product.getRatings().stream()
	                .mapToInt(Rating::getRating) 
	                .sum();
			System.out.println("TOTAL RAINGS Value"+totalRatingsValue);
			double averageRating =0;
			
			if(totalRatings>0)averageRating=(double)totalRatingsValue/totalRatings;
			System.out.println("Average RAINGS"+averageRating);
			product.setNumRatings(averageRating);
			try {
				productService.updateProduct(product.getId(), product);
			} catch (ProductException e) {
				e.printStackTrace();
			}
		
	
		return savedRating;
	}

	@Override
	public List<Rating> getProductRatings(Long productId) {
		
		return ratingRepo.getAllProductRatings(productId);
	}

	@Override
	public Rating getByProductAndUser(Long productId, Long userID) throws RatingException, ProductException {
		Rating rating= ratingRepo.getRatingByProductAndUserId(productId, userID);
		if(rating==null)throw new RatingException("No Rating Found for user");
		return rating;
	}

}
