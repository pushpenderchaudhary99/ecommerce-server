package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.OrderItem;
import com.ecom.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService{
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}
	@Override
	public OrderItem saveUpdateOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}
	

}
