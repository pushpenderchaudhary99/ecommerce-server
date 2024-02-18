package com.ecom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.exceptions.OrderException;
import com.ecom.http.ApiResponse;
import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.model.Product;
import com.ecom.model.Size;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.OrderService;
@RestController
@RequestMapping("/payment")
public class DummyPaymentController {
	//Dummy payment Controller! Do not use
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Map<String, String>> createPayment(@PathVariable Long orderId) throws OrderException{
		Order order = orderService.findOrderById(orderId);
		Map<String, String> res = new HashMap<String, String>();
		String paymentId= generateRandomString(16);
		res.put("payment_amount", ""+order.getTotalDiscountedPrice());
		res.put("payment_id", ""+paymentId);
		
		return new ResponseEntity<Map<String,String>>(res,HttpStatus.OK);
	}
	
	@PostMapping("/success")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id") String paymentID,
			@RequestParam(name="order_id") Long orderId) throws OrderException{
		AtomicBoolean anyOutOfStock = new AtomicBoolean(false); // Flag to track if any item is out of stock
		Order order = orderService.findOrderById(orderId);
		//verify payment status 
		if(true) {
			order.getPaymentDetails().setPaymentId(paymentID);
			order.getPaymentDetails().setPaymentMethod("CARD");
			order.getPaymentDetails().setStatus("CONFIRMED");
			order.setOrderStatus("PLACED");
			
			for (OrderItem orderItem : order.getOrderItems()) {
			    Product product = orderItem.getProduct();
			    Set<Size> updatedSizes = product.getSizes().stream().map((size) -> {
			        if (orderItem.getSize().equals(size.getName())) {
			            if (size.getQuantity() - orderItem.getQuantity() >= 0) {
			                size.setQuantity(size.getQuantity() - orderItem.getQuantity());
			                return size; // Return the updated size
			            } else {
			            	 anyOutOfStock.set(true); // Update the flag
			                 return null; // Indicate that this size is out of stocks
			            }
			        }
			        return size; // Return size without modification for other sizes
			    }).filter(Objects::nonNull) // Filter out null sizes (out of stock)
			      .collect(Collectors.toSet());
			    product.setSizes(updatedSizes);
			    productRepository.save(product);
			}

			// After processing all items, check if any were out of stock
			if (anyOutOfStock.get()) {
			    // Handle Payment reversal or any other necessary action
			    throw new OrderException("Product out of stock! Refund Initiated");
			}
			
			
			
			orderRepository.save(order);
			
		}
		ApiResponse res =new ApiResponse("Order Succesfully Placed ",true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK) ;
	}
	
	public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
	

}
