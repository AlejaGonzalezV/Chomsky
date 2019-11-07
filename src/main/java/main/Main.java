package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import modelo.Gramatica;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/GUI.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chomsky normal form");
		primaryStage.show();
		String texto = "S : aXbX\r\n" + 
				"\r\n" + 
				"X : aY | bY | &\r\n" + 
				"\r\n" + 
				"Y : X | c";
		Gramatica gramatica = new Gramatica(texto);
		
		
	}
	
	public static void main (String[] args) {
		launch(args);
		
		
		
	}
		
	
}