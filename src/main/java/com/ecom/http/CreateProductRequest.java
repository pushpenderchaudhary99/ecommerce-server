package com.ecom.http;

import java.util.HashSet;
import java.util.Set;

import com.ecom.model.Size;

public class CreateProductRequest {
	private String title;
	private String description;
	private int price;
	private int discountedPrice;
	private int discountPercent;
	private int quantity;
	private String brand;
	private String color;
	private Set<Size> size = new HashSet();
	private String imageUrl;
	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;
	
	
	//constructors & getter/ setters
	
	public CreateProductRequest() {
		super();
	
	}
	public CreateProductRequest(String title, String discription, int price, int discountedPrice, int discountPercent,
			int quantity, String brand, String color, Set<Size> size, String imageURL, String topLevelCategory,
			String secondLevelCategory, String thirdLevelCategory) {
		super();
		this.title = title;
		this.description = discription;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.discountPercent = discountPercent;
		this.quantity = quantity;
		this.brand = brand;
		this.color = color;
		this.size = size;
		this.imageUrl = imageURL;
		this.topLevelCategory = topLevelCategory;
		this.secondLevelCategory = secondLevelCategory;
		this.thirdLevelCategory = thirdLevelCategory;
		
	}
	
	
	


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String discription) {
		this.description = discription;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public int getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Set<Size> getSize() {
		return size;
	}
	public void setSize(Set<Size> size) {
		this.size = size;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageURL(String imageURL) {
		this.imageUrl = imageURL;
	}
	public String getTopLevelCategory() {
		return topLevelCategory;
	}
	public void setTopLevelCategory(String topLevelCategory) {
		this.topLevelCategory = topLevelCategory;
	}
	public String getSecondLevelCategory() {
		return secondLevelCategory;
	}
	public void setSecondLevelCategory(String secondLevelCategory) {
		this.secondLevelCategory = secondLevelCategory;
	}
	public String getThirdLevelCategory() {
		return thirdLevelCategory;
	}
	public void setThirdLevelCategory(String thirdLevelCategory) {
		this.thirdLevelCategory = thirdLevelCategory;
	}
	@Override
	public String toString() {
		return "CreateProductRequest [title=" + title + ", description=" + description + ", price=" + price
				+ ", discountedPrice=" + discountedPrice + ", discountPercent=" + discountPercent + ", quantity="
				+ quantity + ", brand=" + brand + ", color=" + color + ", size=" + size + ", imageUrl=" + imageUrl
				+ ", topLevelCategory=" + topLevelCategory + ", secondLevelCategory=" + secondLevelCategory
				+ ", thirdLevelCategory=" + thirdLevelCategory + "]";
	}
	
	
	

}
