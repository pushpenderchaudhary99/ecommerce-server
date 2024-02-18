package com.ecom.http;

public class AddItemRequest {
	private Long productId;
	private int quantity;
	private String size;
	private Integer price;
	
	//Constructors
	public AddItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddItemRequest(Long productId, int quantity, String size, Integer price) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.size = size;
		this.price = price;
	}
	//Getter Setter
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	

}
