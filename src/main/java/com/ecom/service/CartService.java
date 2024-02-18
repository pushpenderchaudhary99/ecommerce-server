package com.ecom.service;

import com.ecom.exceptions.CartItemException;
import com.ecom.exceptions.ProductException;
import com.ecom.exceptions.UserException;
import com.ecom.http.AddItemRequest;
import com.ecom.model.Cart;
import com.ecom.model.User;

public interface CartService {
	public Cart createCart(User user);
	public String addCartItem(Long userId,AddItemRequest req)throws ProductException;
	public Cart findUserCart(Long userId);
	

}
