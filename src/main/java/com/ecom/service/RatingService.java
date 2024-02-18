package com.ecom.service;

import java.util.List;

import com.ecom.exceptions.ProductException;
import com.ecom.exceptions.RatingException;
import com.ecom.http.RatingRequest;
import com.ecom.model.Rating;
import com.ecom.model.User;

public interface RatingService {
	public Rating createRating(RatingRequest ratingReq, User user)throws ProductException,RatingException;
	public List<Rating> getProductRatings(Long productId);
	public Rating getByProductAndUser(Long productId ,Long userID)throws RatingException,ProductException;
	

}
