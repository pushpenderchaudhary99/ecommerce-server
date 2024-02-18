package com.ecom.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.exceptions.OrderException;
import com.ecom.model.Address;
import com.ecom.model.Order;
import com.ecom.model.User;
import com.ecom.repository.CartRepository;
import com.ecom.repository.OrderItemRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.UserRepository;
import com.ecom.user.domain.OrderStatus;

import com.ecom.model.Cart;
import com.ecom.model.CartItem;
import com.ecom.model.OrderItem;
import com.ecom.repository.AddressRepository;


@Service
public class OrderServiceImplementation implements OrderService{
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order createOrder(User user, Address shippingAddress) {
	    // Save Shipping Address
	    shippingAddress.setUser(user);
	    Address savedAddress=null;
	    if(shippingAddress.getId()!=null)savedAddress= addressRepository.findById(shippingAddress.getId()).orElse(null);
	   if(savedAddress==null ) { savedAddress = addressRepository.save(shippingAddress);
	    user.getAdresses().add(savedAddress);
	    userRepository.save(user);
	   }
	    // Load user's cart
	    Cart cart = cartService.findUserCart(user.getId());
	    
	    // Fetch cart items from user's cart and create new order items
	    List<OrderItem> orderItems = new ArrayList<>();
	    for (CartItem cartItem : cart.getCartItems()) {
	        OrderItem orderItem = new OrderItem();
	        orderItem.setPrice(cartItem.getPrice());
	        orderItem.setProduct(cartItem.getProduct());
	        orderItem.setQuantity(cartItem.getQuantity());
	        orderItem.setSize(cartItem.getSize());
	        orderItem.setUserId(cartItem.getUserId());
	        orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
	        
	        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
	        orderItems.add(createdOrderItem);
	    }
	    
	    // Create a new order
	    Order createdOrder = new Order();
	    createdOrder.setUser(user);
	    createdOrder.setOrderItems(orderItems);
	    createdOrder.setTotalPrice(cart.getTotalPrice());
	    createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
	    createdOrder.setDisount(cart.getDiscounte());
	    createdOrder.setTotalItems(cart.getTotalItem());
	    createdOrder.setShippingAddress(savedAddress);
	    createdOrder.setOrderDate(LocalDateTime.now());
	    createdOrder.setOrderStatus("PENDING");
	    createdOrder.getPaymentDetails().setStatus("PENDING");
	    createdOrder.setCreatedAt(LocalDateTime.now());
	    createdOrder.setExpectedDelivaryDate(LocalDateTime.now().plusDays(7));
	    System.out.println("SAVING ORDER:::"+createdOrder);
	    Order savedOrder = orderRepository.save(createdOrder);
	    
	    // Save order details (to which order items belong) for each order item
	    for (OrderItem orderItem : orderItems) {
	        orderItem.setOrder(savedOrder);
	        orderItemService.saveUpdateOrderItem(orderItem);
	    }
	    
	    return savedOrder;
	}
	@Override
	public Order findOrderById(Long orderId) throws OrderException {
	Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException("No Order found with orderId : "+orderId));
		return order;
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orderHistory = orderRepository.getUserOrders(userId);
		System.out.println("ORDER HISTORY :::::::::: "+orderHistory);
		return orderHistory;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return orderRepository.save(order);
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setDelivaryDate(LocalDateTime.now());
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CANCELED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		orderRepository.delete(order);
		
	}

}
