import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class Main extends Application{
	Button btn;
	public static void main(String[] args) {
		launch(args);
	}
		//The button and text field
		@Override public void start(Stage primaryStage) throws IOException    {
		btn=new Button();
		TextField search=new TextField();
		//Setting font to Verdana 20 for button and text
		btn.setFont(Font.font("Verdana",20));
		btn.setText("Search!");
		search.setFont(Font.font("Verdana",20));
		//Adding text field and button to GridPane
		GridPane pane = new GridPane();
		pane.add(search,0,1);
		pane.add(btn,2,1); 
		//Adding pane to scene, scene to stage
		Scene scene=new Scene(pane);
		primaryStage.setScene(scene);        
		primaryStage.setTitle("Price Tracker");        
		primaryStage.show();  
		}
}
