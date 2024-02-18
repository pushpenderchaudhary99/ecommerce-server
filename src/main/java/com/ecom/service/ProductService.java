package com.ecom.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecom.exceptions.ProductException;
import com.ecom.http.CreateProductRequest;
import com.ecom.model.Product;


public interface ProductService {
	//Admin
	public Product createProduct(CreateProductRequest req);
	public String deleteProduct(Long productId)throws ProductException;
	public Product updateProduct(Long productId,Product req) throws ProductException;
	public List<Product> getAllProducts();
	//User | Admin | Any
	public List<Product> searchProduct(String query);
	public Product findProductByID(Long productId) throws ProductException;
	public List<Product> findAllProductByCategory(String category) throws ProductException;
	public Page<Product> getAllProducts(String catogory,List<String> colors, List<String> sizes,
			Integer minPrice, Integer maxPrice,Integer minDiscount,String sort, String stock, Integer pageNumber
			,Integer pageSize);
	public Page<Product> searchProducts(String query,List<String> colors, List<String> sizes,
			Integer minPrice, Integer maxPrice,Integer minDiscount,String sort, String stock, Integer pageNumber
			,Integer pageSize);
	

}
