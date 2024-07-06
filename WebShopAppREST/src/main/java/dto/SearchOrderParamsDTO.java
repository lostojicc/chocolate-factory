package dto;

import java.time.LocalDate;

public class SearchOrderParamsDTO {
	private String factoryName;
	private String minPrice;
	private String maxPrice;
	private String minDate;
	private String maxDate;
	
	public SearchOrderParamsDTO() {
		
	}
	
	

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}



	public String getMinPrice() {
		return minPrice;
	}



	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}



	public String getMaxPrice() {
		return maxPrice;
	}



	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}



	public String getMinDate() {
		return minDate;
	}



	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}



	public String getMaxDate() {
		return maxDate;
	}



	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
}