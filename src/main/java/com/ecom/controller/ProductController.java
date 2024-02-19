package com.ecom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.exceptions.ProductException;
import com.ecom.http.ApiResponse;
import com.ecom.model.Product;
import com.ecom.service.ProductService;


import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	
	//Getting Page of products for category
	
	@GetMapping("")
	public ResponseEntity<Page<Product>> findProductsByCategoryHandler(
			@RequestParam String category,
			@RequestParam List<String> color,
			@RequestParam List<String> size,
			@RequestParam Integer minPrice,
			@RequestParam Integer maxPrice,
			@RequestParam Integer minDiscount,
			@RequestParam String sort, 
			@RequestParam String stock,
			@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize){
		logger.info("fetching products by category to page");
		
		Page<Product> page= productService.getAllProducts(category, color, size, minPrice, maxPrice, minDiscount, sort,stock,pageNumber,pageSize);
		
		System.out.println("sending products by category");
		return new ResponseEntity<Page<Product>>(page,HttpStatus.OK);
	}
	@GetMapping("/search")
	public ResponseEntity<Page<Product>> findProductsByQueryHandler(
			@RequestParam String query,
			@RequestParam List<String> color,
			@RequestParam List<String> size,
			@RequestParam Integer minPrice,
			@RequestParam Integer maxPrice,
			@RequestParam Integer minDiscount,
			@RequestParam String sort, 
			@RequestParam String stock,
			@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize){
		logger.info("fetching products by category to page");
		System.out.println("IN SEARCH :"+query);
		Page<Product> page= productService.searchProducts(query, color, size, minPrice, maxPrice, minDiscount, sort,stock,pageNumber,pageSize);
		
		
		return new ResponseEntity<Page<Product>>(page,HttpStatus.OK);
	}
	
	
	//Fetching product by id
	
	@GetMapping("/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
		logger.info("fetching product by id");
		Product product = productService.findProductByID(productId);
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	};

	
	
	
	

}
