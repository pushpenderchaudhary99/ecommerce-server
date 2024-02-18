package com.ecom.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="order_id)")
	private String orderId;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList();
	private LocalDateTime orderDate;
	private LocalDateTime expectedDelivaryDate;
	private LocalDateTime delivaryDate;
	@ManyToOne
	@JoinColumn(name="shipping_address")
	private Address shippingAddress;
	@Embedded
	private PaymentDetails paymentDetails= new PaymentDetails();
	private double totalPrice;
	private Integer totalDiscountedPrice;
	private Integer disount;
	private String orderStatus;
	private Integer totalItems;
	private LocalDateTime createdAt;
	
	
	
	
	//Constructor
	public Order() {
		super();
		this.orderId="Invalid Variable : Please Consider 'id' as 'orderId'";
	}
	
	public Order(Long id, String orderId, User user, List<OrderItem> orderItems, LocalDateTime orderDate,
			LocalDateTime delivaryDate, Address shippingAddress, PaymentDetails paymentDetails, double totalPrice,
			Integer totalDiscountedPrice, Integer disount, String orderStatus, Integer totalItems,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.user = user;
		this.orderItems = orderItems;
		this.orderDate = orderDate;
		this.delivaryDate = delivaryDate;
		this.shippingAddress = shippingAddress;
		this.paymentDetails = paymentDetails;
		this.totalPrice = totalPrice;
		this.totalDiscountedPrice = totalDiscountedPrice;
		this.disount = disount;
		this.orderStatus = orderStatus;
		this.totalItems = totalItems;
		this.createdAt = createdAt;
	}

	//Getter Setter
	public Long getId() {
		return id;
	}
	
	

	public LocalDateTime getExpectedDelivaryDate() {
		return expectedDelivaryDate;
	}

	public void setExpectedDelivaryDate(LocalDateTime expectedDelivaryDate) {
		this.expectedDelivaryDate = expectedDelivaryDate;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public LocalDateTime getDelivaryDate() {
		return delivaryDate;
	}
	public void setDelivaryDate(LocalDateTime delivaryDate) {
		this.delivaryDate = delivaryDate;
	}
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getTotalDiscountedPrice() {
		return totalDiscountedPrice;
	}
	public void setTotalDiscountedPrice(Integer totalDiscountedPrice) {
		this.totalDiscountedPrice = totalDiscountedPrice;
	}
	public Integer getDisount() {
		return disount;
	}
	public void setDisount(Integer disount) {
		this.disount = disount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderId=" + orderId + ", user=" + user + ", orderItems=" + orderItems
				+ ", orderDate=" + orderDate + ", delivaryDate=" + delivaryDate + ", shippingAddress=" + shippingAddress
				+ ", paymentDetails=" + paymentDetails + ", totalPrice=" + totalPrice + ", totalDiscountedPrice="
				+ totalDiscountedPrice + ", disount=" + disount + ", orderStatus=" + orderStatus + ", totalItems="
				+ totalItems + ", createdAt=" + createdAt + "]";
	}
	
	

}
