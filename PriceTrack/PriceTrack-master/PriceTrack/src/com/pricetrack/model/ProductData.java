package com.pricetrack.model;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

public class ProductData implements Comparable<ProductData>
{
	private Date date;
	private JSONArray prices;
	
	public ProductData(Date date, JSONArray prices)
	{
		this.date = date;
		this.prices = prices;
	}

	public double getNewPrice()
	{
		return getValueAtIndexFromPrices(0);
	}
	
	public double getUsedPrice()
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
		return date + " [New: " + getNewPrice() + "], [Used: " + getUsedPrice() + "]";
	}
	
	private double getValueAtIndexFromPrices(int i) 
	{
		try { return Double.parseDouble((String) prices.get(i));} 
		catch (JSONException e) { e.printStackTrace();}
		
		return 0;
	}
	
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	public JSONArray getPrices() {return prices;}
	public void setPrices(JSONArray prices) {this.prices = prices;}
}
