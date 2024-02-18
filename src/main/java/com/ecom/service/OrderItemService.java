package com.ecom.service;

import com.ecom.model.OrderItem;

public interface OrderItemService {
	public OrderItem createOrderItem(OrderItem orderItem);
	public OrderItem saveUpdateOrderItem(OrderItem orderItem);
}
