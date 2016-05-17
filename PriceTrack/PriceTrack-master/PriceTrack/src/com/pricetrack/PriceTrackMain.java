package com.pricetrack;

import java.io.IOException;

import com.pricetrack.view.ProductGraphScreenController;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PriceTrackMain extends Application
{
	private Stage primaryStage;
    private BorderPane rootLayout;
    
	private Button button;
	private TextField search;
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	//The button and text field
	@Override 
	public void start(Stage primaryStage) throws IOException    
	{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Price Tracker");
        
        initRootLayout();
        
        showProductGraphScreen();
    }
	
	/**
     * Initializes the root layout.
     */
    private void initRootLayout() 
    {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PriceTrackMain.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showProductGraphScreen()
    {
    	try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PriceTrackMain.class.getResource("view/ProductGraphScreen.fxml"));
            AnchorPane productGraphScreen = (AnchorPane) loader.load();
			
            rootLayout.setCenter(productGraphScreen);
        }
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
    }
    
    @Deprecated
    public void setUpSearchScreen()
    {
    	search = new TextField();
		search.setFont(Font.font("Verdana",20));
		
		button = new Button("Search!");
		//Setting font to Verdana 20 for button and text
		button.setFont(Font.font("Verdana",20));
		button.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				
				try {
					FXMLLoader loader = new FXMLLoader();
		            loader.setLocation(PriceTrackMain.class.getResource("view/ProductGraphScreen.fxml"));
		            AnchorPane productGraphScreen = (AnchorPane) loader.load();
					
		            rootLayout.setCenter(productGraphScreen);
		            
		            ProductGraphScreenController controller = loader.getController();
		            controller.setupProductPricesChart(search.getText());
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}});
		
		
		//Adding text field and button to GridPane
		GridPane searchBox = new GridPane();
		searchBox.add(search,0,1);
		searchBox.add(button,2,1); 
		
		rootLayout.setCenter(searchBox);
    }
}
