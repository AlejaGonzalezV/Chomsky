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
		String texto =  "S : BD-aB-BBBB /n A : bb-Aa-BD /n B : A-aA-& /n C : aA-bE /n D : aD-D /n E : aaC-&";
				
			
		Gramatica gramatica = new Gramatica(texto);
		
		
		System.out.println(gramatica.darNoTerminables());
		gramatica.eliminarNoTerminables();
		System.out.println(gramatica.darNoAlcanzables());
		gramatica.eliminarNoAlcanzables();
		System.out.println(gramatica.darAnulables());
		System.out.println(gramatica.getReglas().toString());
		gramatica.eliminarProduccionesLambda();
		System.out.println(gramatica.getReglas().toString());
	
	

	
		
		
	}
	
	public static void main (String[] args) {
		launch(args);
		
		
		
	}
		
	
}