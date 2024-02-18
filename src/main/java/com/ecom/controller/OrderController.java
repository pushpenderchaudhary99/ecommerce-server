package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.exceptions.OrderException;
import com.ecom.exceptions.UserException;
import com.ecom.model.Address;
import com.ecom.model.Order;
import com.ecom.model.User;
import com.ecom.service.OrderService;
import com.ecom.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<Order> createOrderHandler(@RequestBody Address spippingAddress,
			@RequestHeader("Authorization")String jwt) throws UserException{
		System.out.println("SHIPPING ADDRESS::::::::::::::::::"+spippingAddress);
		
		User user=userService.findUserByJwt(jwt);
		Order order =orderService.createOrder(user, spippingAddress);
		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity< List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization") 
	String jwt) throws OrderException, UserException{
		
		User user=userService.findUserByJwt(jwt);
		List<Order> orders=orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") 
	String jwt) throws OrderException, UserException{
		
		User user=userService.findUserByJwt(jwt);
		Order order=orderService.findOrderById(orderId);
		if(user.getId()!=order.getUser().getId())throw new OrderException("Order doesnot Belong to user");
		return new ResponseEntity<>(order,HttpStatus.OK);
	}

}
