package com.ecom.http;

public class SalesPerCategoryResponse {

	private String label;
	private Long value;
	

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public SalesPerCategoryResponse(Integer id, String label, Long value) {
		super();
		
		this.label = label;
		this.value = value;
	}
	public SalesPerCategoryResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
