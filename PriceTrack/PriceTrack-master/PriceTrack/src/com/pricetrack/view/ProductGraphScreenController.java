package com.pricetrack.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pricetrack.Controller;
import com.pricetrack.model.ProductData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProductGraphScreenController 
{
	XYChart.Series<String, Double> newPrices;
	
	XYChart.Series<String, Double> usedPrices;
	
	@FXML
	private LineChart<String, Double> chart;
	@FXML
	private Label label;
	@FXML
	private TextField searchTextField;
	
	@FXML
	private void initialize()
	{
		XYChart.Series<String, Double> minPrice = new XYChart.Series<String, Double>();
		minPrice.getData().add(new XYChart.Data<String, Double>("",100.0));
		chart.getData().add(minPrice);
		
		usedPrices = new XYChart.Series<String, Double>();
		usedPrices.setName("Used");
		chart.getData().add(usedPrices);
		
		newPrices = new XYChart.Series<String, Double>();
		newPrices.setName("New");
		chart.getData().add(newPrices);
		
		chart.setCreateSymbols(false);
	}
	
	@FXML
	private void handleSearchButton()
	{
		setupProductPricesChart(searchTextField.getText());
	}
	
	public void setupProductPricesChart(String searchText) 
	{
		try {
			label.setText(searchText);

			ArrayList<ProductData> productPrices = new ArrayList<ProductData>();
			
			//Get the JSON data from tracktor
			JSONObject json = Controller.getHistoricalDataJson(searchText, 10950);
			System.out.println(json);

			//get the prices within the JSON response
			JSONObject priceData = json.getJSONObject("prices");
			System.out.println(priceData);
			
			//get the array of date strings within the JSON object "prices" 
			JSONArray dateEntries = priceData.names();
			System.out.println(dateEntries);
			
			//for every date, create a new ProductData with a Date and a JSONArray of prices
			//put ProductData into productPrices
			for (int i = 0; i < dateEntries.length(); i++) {
				String dateString = dateEntries.getString(i);
				JSONArray prices = priceData.getJSONArray(dateString);
				
				Date date = Controller.convertDateFromMilisAsDate(Double.parseDouble(dateString));
				
				ProductData productData = new ProductData(date, prices);
				productPrices.add(productData);
			}
			
			Collections.sort(productPrices);
			productPrices.remove(0);
			for (ProductData pd : productPrices) System.out.println(pd);
			
			putProductPricesIntoChart(productPrices);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void putProductPricesIntoChart(ArrayList<ProductData> productPrices)
	{
		ObservableList<XYChart.Data<String, Double>> newPricesList = FXCollections.observableArrayList();
		ObservableList<XYChart.Data<String, Double>> usedPricesList = FXCollections.observableArrayList();
		
		Double prevNewPrice = 0.0; 
		Double prevUsedPrice = 0.0;
		
		for (ProductData productData : productPrices) {
			Double newPrice = productData.getNewPrice();
			Double usedPrice = productData.getUsedPrice();
			
			if (newPrice != null) {
				newPricesList.add(new XYChart.Data<String, Double>(productData.getDate().toString(),productData.getNewPrice()));
				prevNewPrice = newPrice;
			}
			else if (prevNewPrice != null) 
				newPricesList.add(new XYChart.Data<String,Double>(productData.getDate().toString(),prevNewPrice));
			
			if (usedPrice != null) {
				usedPricesList.add(new XYChart.Data<String, Double>(productData.getDate().toString(),productData.getUsedPrice()));
				prevUsedPrice = usedPrice;
			}
			else if (prevUsedPrice != null) 
				usedPricesList.add(new XYChart.Data<String, Double>(productData.getDate().toString(),prevUsedPrice));
		}
		
		newPrices.setData(newPricesList);
		usedPrices.setData(usedPricesList);
	}
}
