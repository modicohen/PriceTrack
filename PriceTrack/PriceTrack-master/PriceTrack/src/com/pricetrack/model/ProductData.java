package com.pricetrack.model;

import java.util.Date;

import org.json.JSONArray;

public class ProductData implements Comparable<ProductData>
{
	private Date date;
	private JSONArray prices;
	
	public ProductData(Date date, JSONArray prices)
	{
		this.date = date;
		this.prices = prices;
	}

	public Double getNewPrice()
	{
		return getValueAtIndexFromPrices(0);
	}
	
	public Double getUsedPrice()
	{
		return getValueAtIndexFromPrices(1);
	}
	
	@Override
	public int compareTo(ProductData productData) 
	{
		return date.compareTo(productData.getDate());
	}

	@Override
	public String toString()
	{
		Double newPriceDouble = getNewPrice();
		String newPrice = "NULL";
		if (newPriceDouble != null) {
			newPrice = Double.toString(newPriceDouble);
		}
		
		Double usedPriceDouble = getUsedPrice();
		String usedPrice = "NULL";
		if (usedPriceDouble != null) {
			usedPrice = Double.toString(usedPriceDouble);
		}
		
		return date + " [New: " + newPrice + "], [Used: " + usedPrice + "]";
	}
	
	private Double getValueAtIndexFromPrices(int i) 
	{
		try {
			Object priceObj = prices.get(i);
			if (priceObj == null) return null;
			
			return Double.parseDouble((String) priceObj);
		} 
		catch (Exception e) { 
			e.getMessage();
			return (Double) null;
		}
	}
	
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	public JSONArray getPrices() {return prices;}
	public void setPrices(JSONArray prices) {this.prices = prices;}
}
