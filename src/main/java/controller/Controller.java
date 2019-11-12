package controller;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Gramatica;

/**
 * Clase Controller
 * clase que se encarga de la interaccion directa con la interfaz de usuario
 *
 */
public class Controller implements Initializable{

	Gramatica g;
	
    @FXML
    private Button variableTermBut;

    @FXML
    private TextArea txtGramatica;

    @FXML
    private TextArea txtFnc;

    @FXML
    private TextField prodLambdaTxt;

    @FXML
    private TextField noTermTxt;

    @FXML
    private Button prodUnitBut;

    @FXML
    private Button noAlcBut;

    @FXML
    private Button prodBinBut;

    @FXML
    private Button prodLambdaBut;

    @FXML
    private TextField noAlcTxt;

    @FXML
    private Button noTermBut;

    
    @FXML
    private Button limpiarBut;
    
    @FXML
    void limpiar(ActionEvent event) {
    	
    	noTermTxt.setText("");
		noAlcTxt.setText("");
		prodLambdaTxt.setText("");
		noAlcBut.setDisable(true);
		prodLambdaBut.setDisable(true);
		prodUnitBut.setDisable(true);
		variableTermBut.setDisable(true);
		prodBinBut.setDisable(true);
		noTermBut.setDisable(false);
		txtGramatica.setText("");
		txtFnc.setText("");

    }

    @FXML
    void noTerm(ActionEvent event) {
    	
    	String texto = txtGramatica.getText();
    	g = new Gramatica(texto);
    	noTermTxt.setText(g.darNoTerminables().toString());
    	g.eliminarNoTerminables();
    	txtFnc.setText(g.toString());
    	noAlcBut.setDisable(false);
    	noTermBut.setDisable(true);
    	
    }

    @FXML
    void noAlc(ActionEvent event) {
    	
    	noAlcTxt.setText(g.darNoAlcanzables().toString());
    	g.eliminarNoAlcanzables();
    	txtFnc.setText(g.toString());
    	prodLambdaBut.setDisable(false);
    	noAlcBut.setDisable(true);
    	

    }

    @FXML
    void prodLambda(ActionEvent event) {
    	
    	prodLambdaTxt.setText(g.darAnulables().toString());
    	g.eliminarProduccionesLambda();
    	txtFnc.setText(g.toString());
    	prodUnitBut.setDisable(false);
    	prodLambdaBut.setDisable(true);
    	
    }

    @FXML
    void prodUnit(ActionEvent event) {
    	
    	
    	g.eliminarProduccionesUnitarias();
    	txtFnc.setText(g.toString());
    	prodUnitBut.setDisable(true);
    	variableTermBut.setDisable(false);

    }

    @FXML
    void variableTerm(ActionEvent event) {

    	g.generarVariablesPorCadaTerminal();
    	txtFnc.setText(g.toString());
    	variableTermBut.setDisable(true);
    	prodBinBut.setDisable(false);
    	
    }

    @FXML
    void prodBin(ActionEvent event) {

    	g.generarProduccionesBinarias();
    	txtFnc.setText(g.toString());
    	prodBinBut.setDisable(true);
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		noTermTxt.setEditable(false);
		noAlcTxt.setEditable(false);
		prodLambdaTxt.setEditable(false);
		noAlcBut.setDisable(true);
		prodLambdaBut.setDisable(true);
		prodUnitBut.setDisable(true);
		variableTermBut.setDisable(true);
		prodBinBut.setDisable(true);
		
		
	}

}