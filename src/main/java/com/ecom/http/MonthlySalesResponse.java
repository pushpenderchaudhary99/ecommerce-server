package com.ecom.http;

public class MonthlySalesResponse {
	private int sales;
	private String month;
	
	
	
	public MonthlySalesResponse(int sales, String month) {
		super();
		this.sales = sales;
		this.month = month;
	}
	
	public MonthlySalesResponse() {
		super();
	}

	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	

}
