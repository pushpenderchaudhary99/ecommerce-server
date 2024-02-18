package com.ecom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.exceptions.ProductException;
import com.ecom.exceptions.RatingException;
import com.ecom.exceptions.UserException;
import com.ecom.model.Rating;
import com.ecom.model.Review;
import com.ecom.model.User;
import com.ecom.http.RatingRequest;
import com.ecom.http.RatingStatesticsResponse;
import com.ecom.service.RatingService;
import com.ecom.service.UserService;
@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RatingService ratingServices;
	
	

	@PostMapping("/create")
	public ResponseEntity<Rating> createRatingHandler(@RequestBody RatingRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException, RatingException{
		User user=userService.findUserByJwt(jwt);
		Rating rating=ratingServices.createRating(req, user);
		return new ResponseEntity<>(rating,HttpStatus.OK);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsReviewHandler(@PathVariable Long productId){
	
		List<Rating> ratings=ratingServices.getProductRatings(productId);
		return new ResponseEntity<>(ratings,HttpStatus.OK);
	}
	@GetMapping("/rating/{productId}/{userId}")
	public ResponseEntity<Rating> getUserProductRating(@PathVariable Long productId, @PathVariable Long userId) throws UserException, ProductException, RatingException{
		Rating rating = ratingServices.getByProductAndUser(productId,userId);
		return new ResponseEntity<>(rating,HttpStatus.ACCEPTED);
	}
	@GetMapping("/stats/{productId}")
	public ResponseEntity<RatingStatesticsResponse> getIndividualRating(@PathVariable Long productId){
		List<Rating> ratings = ratingServices.getProductRatings(productId);
		int fiveStar = 0;
		int fourStar = 0;
		int threeStar = 0;
		int twoStar = 0;
		int oneStar = 0;

		for (Rating rating : ratings) {
		    switch (rating.getRating()) {
		        case 5:
		            fiveStar++;
		            break;
		        case 4:
		            fourStar++;
		            break;
		        case 3:
		            threeStar++;
		            break;
		        case 2:
		            twoStar++;
		            break;
		        case 1:
		            oneStar++;
		            break;
		        default:
		            // Handle invalid ratings or other cases
		            break;
		    }
		}

		int totalRatings = ratings.size();

		int oneStarPercent = (int) ((oneStar / (double) totalRatings) * 100);
		int twoStarPercent = (int) ((twoStar / (double) totalRatings) * 100);
		int threeStarPercent = (int) ((threeStar / (double) totalRatings) * 100);
		int fourStarPercent = (int) ((fourStar / (double) totalRatings) * 100);
		int fiveStarPercent = (int) ((fiveStar / (double) totalRatings) * 100);
		
		//Setting up response 
		Map<String, Integer> starCounts = new HashMap<>();
		starCounts.put("oneStar", oneStar);
		starCounts.put("twoStar", twoStar);
		starCounts.put("threeStar", threeStar);
		starCounts.put("fourStar", fourStar);
		starCounts.put("fiveStar", fiveStar);

		Map<String, Integer> starPercentages = new HashMap<>();
		starPercentages.put("oneStar", oneStarPercent);
		starPercentages.put("twoStar", twoStarPercent);
		starPercentages.put("threeStar", threeStarPercent);
		starPercentages.put("fourStar", fourStarPercent);
		starPercentages.put("fiveStar", fiveStarPercent);

		RatingStatesticsResponse res = new RatingStatesticsResponse(starCounts, starPercentages);
		return new ResponseEntity<RatingStatesticsResponse>(res,HttpStatus.OK);
	}
}
