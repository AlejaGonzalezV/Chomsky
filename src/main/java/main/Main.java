package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/GUI.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.getIcons().add(new Image("https://ih0.redbubble.net/image.447257112.7871/flat,1000x1000,075,f.u3.jpg"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chomsky normal form");
		primaryStage.show();


	}
	
	public static void main (String[] args) {
		launch(args);
		
		
		
	}
		
	
}