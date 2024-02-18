package com.ecom.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecom.exceptions.ProductException;
import com.ecom.http.CreateProductRequest;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.Rating;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;

@Service
public class ProductServiceImplentation implements ProductService{
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private UserService userService;
	@Override
	public Product createProduct(CreateProductRequest req) {
			if(	req.getTopLevelCategory()==null ||
				req.getSecondLevelCategory()==null||
				req.getThirdLevelCategory()==null)
				throw new RuntimeException("Invalid Product Request- Product Category Null");
				
				
		//Checking if category existed : Level-1
		System.out.println("Checking if category existed : Level-1");
		Category topLevel= categoryRepo.findByName(req.getTopLevelCategory().toLowerCase());
		if(topLevel==null) {
			//creating new repository if it doesn't exist;
			System.out.println("creating new repository if it doesn't exist -1;");
			Category newTopLevelCategory= new Category();
			newTopLevelCategory.setName(req.getTopLevelCategory().toLowerCase());
			newTopLevelCategory.setLevel(1);
			
			topLevel=categoryRepo.save(newTopLevelCategory);
			System.out.println("created-1");
			
		}
		//Checking if category existed : Level -2
		System.out.println("Checking if category existed : Level-2");
		Category secondLevel= categoryRepo.findByNameAndParent(req.getThirdLevelCategory().toLowerCase(),topLevel.getName());
		if(secondLevel==null) {
			//creating new repository if it doesn't exist;
			System.out.println("creating new repository if it doesn't exist -2;");
			Category newSecondLevelCategory= new Category();
			newSecondLevelCategory.setName(req.getSecondLevelCategory().toLowerCase());
			newSecondLevelCategory.setParentCategory(topLevel);
			newSecondLevelCategory.setLevel(2);
			
			secondLevel=categoryRepo.save(newSecondLevelCategory);	
			System.out.println("created-2");
		}
		//Checking if category existed : Level - 3
		System.out.println("Checking if category existed : Level-3");
		Category thirdLevel= categoryRepo.findByNameAndParent(req.getThirdLevelCategory().toLowerCase(),secondLevel.getName());
		if(thirdLevel==null) {
			//creating new repository if it doesn't exist;
			System.out.println("creating new repository if it doesn't exist -3;");
			Category newThirdLevelCategory= new Category();
			newThirdLevelCategory.setName(req.getThirdLevelCategory().toLowerCase());
			newThirdLevelCategory.setParentCategory(secondLevel);
			newThirdLevelCategory.setLevel(3);
			thirdLevel=categoryRepo.save(newThirdLevelCategory);
			System.out.println("created-3");
			
		}
		
		Product product = new Product();
		product.setBrand(req.getBrand());
		product.setColor(req.getColor());
		product.setCreatedAt(LocalDateTime.now());
		product.setCategory(thirdLevel);
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPersent(calculateDiscountPercent(req.getPrice(), req.getDiscountedPrice()));
		product.setImageUrl(req.getImageUrl());
		product.setPrice(req.getPrice());
		product.setQuantity(req.getQuantity());
		product.setSizes(req.getSize());
		product.setTitle(req.getTitle());
		System.out.println("saving product : ");
		Product savedProduct = productRepo.save(product);
		System.out.println("saved");
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductByID(productId);
		if(product == null)throw new ProductException("no item found with product id -DELETE PRODUCT ");
		productRepo.delete(product);
		return "Product Deleted Sucessfully!";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product = findProductByID(productId);
		if(product == null)throw new ProductException("no item found with product id -UPDATE PRODUCT ");
		
		// Update the product fields if they are not null in the request
	    if (req.getTitle() != null) product.setTitle(req.getTitle());
	    if (req.getDescription() != null) product.setDescription(req.getDescription());
	    if (req.getPrice() != 0) product.setPrice(req.getPrice());
	    if (req.getDiscountedPrice() != 0) product.setDiscountedPrice(req.getDiscountedPrice());
	    if (req.getDiscountPersent() != 0) product.setDiscountPersent(req.getDiscountPersent());
	    if (req.getQuantity() != 0) product.setQuantity(req.getQuantity());
	    if (req.getBrand() != null) product.setBrand(req.getBrand());
	    if (req.getColor() != null) product.setColor(req.getColor());
	    if (req.getSizes() != null) product.setSizes(req.getSizes());
	    if (req.getImageUrl() != null) product.setImageUrl(req.getImageUrl());
	    if (req.getCategory() != null) product.setCategory(req.getCategory());
	    if(req.getNumRatings()!=0) product.setNumRatings(req.getNumRatings());
	   
	    // Save the updated product to the repository
	    product = productRepo.save(product);
	    
	    return product;
	}

	@Override
	public Product findProductByID(Long productId) throws ProductException {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ProductException("No product found with ID - FIND PRODUCT"));
		return product;
	}

	@Override
	public List<Product> findAllProductByCategory(String category) throws ProductException {

		return null;
	}

	@Override
	public Page<Product> getAllProducts(String catogory, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Product> products =productRepo.filterProducts(catogory, minPrice, maxPrice, minDiscount, sort);
		//filter based on color
		if(!colors.isEmpty())
			products= products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
			.collect(Collectors.toList());
		//filter based on stock avalability
		if(stock!=null && stock.equals("in_stock"))
			products=products.stream().filter(p->p.getQuantity()>0).toList();
		else if(stock.equals("out_of_stock"))
			products=products.stream().filter(p->p.getQuantity()<1).toList();

		int startIndex =(int)pageable.getOffset();
		int endIndex =Math.min(startIndex+(int)pageable.getPageSize(),products.size());
		List<Product> pageContent =products.subList(startIndex, endIndex);
		Page<Product> filteredProduct= new PageImpl<Product>(pageContent,pageable,products.size());
		return filteredProduct;
	}
	@Override
	public Page<Product> searchProducts(String query, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products =productRepo.searchProducts(query, minPrice, maxPrice, minDiscount, sort);
		System.out.println("Products SEARCHED"+products);
		//filter based on color
		if(!colors.isEmpty())
			products= products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
			.collect(Collectors.toList());
		//filter based on stock avalability
		if(stock!=null && stock.equals("in_stock"))
			products=products.stream().filter(p->p.getQuantity()>0).toList();
		else if(stock.equals("out_of_stock"))
			products=products.stream().filter(p->p.getQuantity()<1).toList();
		
		int startIndex =(int)pageable.getOffset();
		int endIndex =Math.min(startIndex+(int)pageable.getPageSize(),products.size());
		List<Product> pageContent =products.subList(startIndex, endIndex);
		Page<Product> filteredProduct= new PageImpl<Product>(pageContent,pageable,products.size());
		return filteredProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public List<Product> searchProduct(String query) {
		// TODO Auto-generated method stub
		return null;
	}
//discount calculator
	public int calculateDiscountPercent(int originalPrice, int discountedPrice) {
	    if (originalPrice == 0) {
	        return 0;
	    } else {
	        return (int) ((originalPrice - discountedPrice) * 100 / originalPrice);
	    }
	}


}
