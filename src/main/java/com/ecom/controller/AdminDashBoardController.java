package com.ecom.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.http.MonthlySalesResponse;
import com.ecom.http.SalesPerCategoryResponse;
import com.ecom.model.Category;
import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.model.Product;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;

@RestController
@RequestMapping("api/admin/dashboard")
public class AdminDashBoardController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productsRepository;
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("")
	public ResponseEntity<Map<String,?>> getDashboardAnalysis(){
		Map<String, Long> res = new HashMap<String, Long>();
		res.put("customerCount", userRepository.count());
		res.put("productsCount", productsRepository.count());
		res.put("deliveredOrders", orderRepository.countDeliveredOrders());
		res.put("revenue", orderRepository.getRevanue());
		
		return new ResponseEntity<Map<String,?>>(res,HttpStatus.OK);
	}
	@GetMapping("/category_overview")
	public ResponseEntity<List<SalesPerCategoryResponse>> getCaregoryOverView() {
		List<Order> deliveredOrders = orderRepository.getDeliveredOrders();
		// Flatten the order items, then map to category names and count
        Map<String, Long> countPerCategory = deliveredOrders.stream()
                .flatMap(order -> order.getOrderItems().stream()) // Flatten order items
                .map(OrderItem::getProduct) // Get the product from each order item
                .map(Product::getCategory) // Get the category from each product
                .map(Category::getName) // Get the name of the category
                .collect(Collectors.groupingBy(categoryName -> categoryName, Collectors.counting()));

        // Convert the map to a list of SalesPerCategoryResponse objects
        List<SalesPerCategoryResponse> res = countPerCategory.entrySet().stream()
                .map(entry -> new SalesPerCategoryResponse(null, formatString(entry.getKey()) , entry.getValue()))
                .collect(Collectors.toList());
		  return new ResponseEntity<List<SalesPerCategoryResponse>>(res,HttpStatus.OK);
	}
	@GetMapping("/monthly_sales/{year}")
	public ResponseEntity<List<MonthlySalesResponse>> getMonthlySalesData(@PathVariable int year) {
	    List<Order> deliveredOrders = orderRepository.findDeliveredOrdersByYear(year);
	    Map<Month, Integer> monthlySales = new HashMap<>();
	    System.out.println("Delivered SALES : " + deliveredOrders);
	    
	    // Initialize all months with 0 sales
	    for (Month month : Month.values()) {
	        monthlySales.put(month, 0);
	    }
	    
	    // Update sales for corresponding months
	    for (Order order : deliveredOrders) {
	        Month month = order.getOrderDate().getMonth();
	        monthlySales.put(month, monthlySales.getOrDefault(month, 0) + 1);
	    }

	    // list of response
	    List<MonthlySalesResponse> res = new ArrayList<>();
	    
	    // filling data to res list
	    for (Month month : Month.values()) {
	        Integer sales = monthlySales.get(month);
	        res.add(new MonthlySalesResponse(sales, month.toString().substring(0, 3)));
	    }

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}

	
	//format label 
	public String formatString(String input) {
		StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '_') {
                // Replace underscore with space and capitalize the next character
                result.append(' ');
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
	}

}
