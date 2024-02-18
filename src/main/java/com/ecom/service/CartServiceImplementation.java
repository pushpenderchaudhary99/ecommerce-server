package com.ecom.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.exceptions.CartItemException;
import com.ecom.exceptions.ProductException;
import com.ecom.exceptions.UserException;
import com.ecom.http.AddItemRequest;
import com.ecom.model.Cart;
import com.ecom.model.CartItem;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.CartItemRepository;
import com.ecom.repository.CartRepository;

@Service
public class CartServiceImplementation implements CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;
	

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest reqItem) throws ProductException {
		Cart cart =cartRepository.findByUserId(userId);
		
		Product product = productService.findProductByID(reqItem.getProductId());
		//checking if item already present or not
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, reqItem.getSize(), userId);
		if(isPresent!=null) isPresent.setQuantity(isPresent.getQuantity()+reqItem.getQuantity());
		else {
			CartItem item = new CartItem();
			item.setCart(cart);
			item.setQuantity(reqItem.getQuantity());
			item.setProduct(product);
			item.setPrice(product.getPrice()*item.getQuantity());
			item.setDiscountedPrice(product.getDiscountedPrice()*item.getQuantity());
			item.setSize(reqItem.getSize());
			item.setUserId(userId);
			cartItemService.createCartItem(item);
		}
		
		return "Item Added To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		
		Cart cart= cartRepository.findByUserId(userId);
		System.out.println("CART ::::::::::::::::::FOUND BY USER ID"+cart);
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItems=0;
		Set<CartItem> cartItems = cart.getCartItems();
		for (CartItem item :cartItems ) {
		    totalPrice =totalPrice+ item.getPrice();
		    totalDiscountedPrice =totalDiscountedPrice+ item.getDiscountedPrice();
		    totalItems=totalItems+item.getQuantity();
		}
		
		cart.setTotalPrice(totalPrice);
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItems);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		return cartRepository.save(cart) ;
	}


}
