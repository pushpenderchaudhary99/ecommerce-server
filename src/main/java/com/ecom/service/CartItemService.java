package com.ecom.service;

import com.ecom.exceptions.CartItemException;
import com.ecom.exceptions.UserException;
import com.ecom.model.Cart;
import com.ecom.model.CartItem;
import com.ecom.model.Product;

public interface CartItemService {
	public CartItem createCartItem(CartItem cartItem);
	public CartItem updateCartItem(Long userId,Long id,CartItem cartItem)throws CartItemException, UserException;
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
	public void removeCartItem(Long userId, Long cartItemId)throws CartItemException, UserException;
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;

}
