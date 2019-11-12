package modelo;

import java.util.ArrayList;
/**
 * Clase Regla
 * Clase que representa una regla de una gramatica
 */
public class Regla {

	/**
	 * Constante de tipo char que representa el simbolo lambda
	 */
	public static char LAMBDA = '&';

	/**
	 * Atributo de tipo char que representa la variable generadora de la regla
	 */
	private char generador;
	/**
	 * Atributo de tipo ArrayList. Arreglo de tipo String que representa las producciones de cada regla
	 */
	private ArrayList<String> producciones;

	/**
	 * Metodo constructor de la clase Regla
	 * @param pgenerador char que representa la variable generadora
	 * @param pproducciones arreglo de tipo String que representa las producciones de cada regla
	 */
	public Regla(char pgenerador, ArrayList<String> pproducciones) {
		generador = pgenerador;
		producciones = pproducciones;
	}

/*
 * Metodos get y set de los atributos de la clase Regla
 */
	public char getGenerador() {
		return generador;
	}

	public void setGenerador(char generador) {
		this.generador = generador;
	}

	public ArrayList<String> getProducciones() {
		return producciones;
	}

	public void setProducciones(ArrayList<String> producciones) {
		this.producciones = producciones;
	}

	/**
	 * Metodo que determina si la regla es terminable por la presencia de una produccion lambda o por una produccion con solo terminales
	 * @return boolean que determina si la regla es terminable o no
	 */
	public boolean esTerminablePorProduccion() {
		boolean respuesta = false;

		for (int i = 0; i < producciones.size() && !respuesta; i++) {
			String produccion = producciones.get(i);
			char pr = produccion.charAt(0);

			if (pr == LAMBDA) {
				respuesta = true;
			} else {

				int contador = 0;
				for (int j = 0; j < produccion.length(); j++) {

					if (Character.isLowerCase(produccion.charAt(j))) {

						contador++;

					}

				}

				if (contador == produccion.length()) {
					respuesta = true;
				}
			}
		}

		return respuesta;
	}



	/**
	 * Metodo que determina si la regla es terminable por el hecho de tener una produccion con una variable terminable
	 * @param terminables. Arreglo de tipo Character que representa las variables terminables
	 * @return boolean que representa si la regla es terminable por variable terminable o no
	 */
	public boolean esTerminablePorVariableTerminable(ArrayList<Character> terminables) {
		boolean respuesta = false;

		for (int i = 0; i < producciones.size() && !respuesta; i++) {
			String produccion = producciones.get(i);

			int contador = 0;
			for (int j = 0; j < produccion.length(); j++) {

				if (Character.isLowerCase(produccion.charAt(j)) || terminables.contains(produccion.charAt(j))) {

					contador++;

				}

			}

			if (contador == produccion.length()) {
				respuesta = true;
			}
		}

		return respuesta;
	}


	/*
	 * Metodo que determina las variables alcanzables directas
	 */
	public ArrayList<Character> variablesAlcanzables() {
		ArrayList<Character> variablesAlcanzables = new ArrayList<Character>();
		ArrayList<Character> lista = new ArrayList<Character>();

		for (int i = 0; i < producciones.size(); i++) {

			for (int j = 0; j < producciones.get(i).length(); j++) {

				if (Character.isUpperCase(producciones.get(i).charAt(j))) {

					lista.add(producciones.get(i).charAt(j));

				}

			}

			variablesAlcanzables.addAll(lista);

		}

		return variablesAlcanzables;
	}


	/**
	 * Metodo que determina si la regla es anulable por tener una produccion lambda
	 * @return boolean que representa si la regla es anulable por produccion o no
	 */
	public boolean esAnuablePorProduccion() {
		boolean respuesta = false;

		for (int i = 0; i < producciones.size() && !respuesta; i++) {
			String produccion = producciones.get(i);
			if (produccion.equals(Character.toString(LAMBDA))) {
				respuesta = true;
			}
		}

		return respuesta;
	}


	/*
	 * Metodo que determina si la regla es anulable por el hecho de tener una produccion con una variable anulable
	 */
	public boolean esAnulablePorVariableAnulable(ArrayList<Character> anulables) {
		boolean respuesta = false;

		for (int i = 0; i < producciones.size() && !respuesta; i++) {
			String produccion = producciones.get(i);

			int contador = 0;
			for (int j = 0; j < produccion.length(); j++) {

				if (anulables.contains(produccion.charAt(j))) {

					contador++;

				}

			}

			if (contador == produccion.length()) {
				respuesta = true;
			}

		}

		return respuesta;
	}

	/**
	 * Metodo que obtiene todas las producciones unitarias
	 * @return arreglo con todas las producciones unitarias
	 */
	public ArrayList<Character> produccionesUnitarias() {
		ArrayList<Character> respuesta = new ArrayList<Character>();

		for (int i = 0; i < producciones.size(); i++) {
			String prod = producciones.get(i);
			if (prod.length() == 1) {
				char caracter = prod.charAt(0);
				if (Character.isUpperCase(caracter) == true) {
					respuesta.add(caracter);
				}
			}
		}
		return respuesta;
	}

	/*
	 * Metodo que elimina producciones con variables no terminables o producciones con variables no alcanzables
	 * @param Arreglo que contiene las variables 
	 */
	public void eliminarProduccionesConLasVariables(ArrayList<Character> variables) {
		ArrayList<String> eliminar = new ArrayList<String>();

		for (int i = 0; i < variables.size(); i++) {

			for (int j = 0; j < producciones.size(); j++) {
				if (producciones.get(j).contains(variables.get(i).toString()) == true) {
					if (eliminar.contains(producciones.get(j)) == false) {
						eliminar.add(producciones.get(j));
					}
				}
			}
		}

		producciones.removeAll(eliminar);

	}


	/**
	 * Metodo que simula las producciones lambda
	 * @param anulables. Arreglo que contiene las variables que son anulables
	 */
	public void simularProduccionesLambda(ArrayList<Character> anulables) {
		ArrayList<String> nuevas = new ArrayList<String>();
		boolean es = false;

		for (int i = 0; i < producciones.size(); i++) {
			String produccion = producciones.get(i);
			String produccionNueva = produccion;
			ArrayList<Integer> posicion = new ArrayList<Integer>();
			for (int j = 0; j < producciones.get(i).length(); j++) {

				if (anulables.contains(produccion.charAt(j))) {
					produccionNueva = produccionNueva.replace(produccion.charAt(j) + "", "");

				}

			}

			boolean esta = false;
			if (!produccionNueva.equals("") && !produccionNueva.equals(produccion)) {

				for (int j = 0; j < producciones.size(); j++) {

					if (producciones.get(j).equals(produccionNueva)) {
						esta = true;
					}

				}

				if (!esta) {
					nuevas.add(produccionNueva);
				}
			}

			else if (produccionNueva.equals("")) {
				produccionNueva = "&";
				nuevas.add(produccionNueva);

			}

		}

		if (nuevas.size() != 0) {
			producciones.addAll(nuevas);
		}

	}


	/**
	 * Metodo que genera un arreglo de numeros binarios en formato String
	 * @param componentes indica cuantos componenter debe tener cada binario generado
	 * @param limite indica cuantos binarios generar
	 * @return
	 */
	public ArrayList<String> generarArrayListaDeBinarios(int componentes, int limite) {
		ArrayList<String> lista = new ArrayList<String>();

		for (int i = 0; i < limite; i++) {
			String binario = decimalABinario(i, componentes);
			lista.add(binario);
		}

		return lista;

	}

	
	/*
	 * Metodo que convierte un numero en formato decimal a binario
	 * @param numero. Numero decimal
	 * @param componentes. indica cuantos componentes debe tener el binario generado
	 */
	public String decimalABinario(int numero, int componentes) {
		String sal = "";

		int dec = numero;
		int bin = 0;

		while (dec > 0) {
			bin = dec % 2;
			dec = dec / 2;

			sal = bin + sal;
		}

		int tamanio = sal.length();
		while (tamanio != componentes) {
			sal = "0" + sal;
			tamanio = sal.length();
		}

		return sal;
	}

	/**
	 * Metodo que determina cuales son las producciones no unitarias
	 * @return arreglo con las producciones no unitarias
	 */
	public ArrayList<String> darProduccionesNoUnitarias() {
		ArrayList<String> respuesta = new ArrayList<String>();

		for (int i = 0; i < producciones.size(); i++) {
			String prod = producciones.get(i);
			if (prod.length() != 1) {
				respuesta.add(prod);
			} else if (Character.isLowerCase(prod.charAt(0)) == true) {
				respuesta.add(prod);
			} else if (prod.charAt(0) == LAMBDA) {
				respuesta.add(prod);
			}
		}

		return respuesta;
	}

	/**
	 * Metodo que modifica el atributo producciones, añadiendo nuevos elementos
	 * @param nuevasProducciones arreglo de String con todas las nuevas producciones a añadir
	 */
	public void modificarProducciones(ArrayList<String> nuevasProducciones) {
		producciones.addAll(nuevasProducciones);
	}

	/*
	 * Metodo que elimina las producciones unitarias
	 */
	public void eliminarProduccionesUnitarias() {
		producciones = darProduccionesNoUnitarias();
	}

	/*
	 * Metodo que convierte todas las producciones en producciones binarias
	 * @param g. Referencia al objeto Gramatica al cual pertenece este objeto Regla
	 */
	public void obtenerProduccionesBinarias(Gramatica g) {
		ArrayList<String> nuevas = new ArrayList<String>();
		for (int i = 0; i < producciones.size(); i++) {
			String produccion = producciones.get(i);
			int tamanio = produccion.length();
			if (tamanio > 2) 
			{
				char caracter1 = produccion.charAt(tamanio - 1);
				char caracter2 = produccion.charAt(tamanio - 2);

				String nuevaProduccion = Character.toString(caracter2) + Character.toString(caracter1);

				Regla regla = g.reglaUnitariaConProduccion(nuevaProduccion);

				if (regla != null) {

					produccion = RemoveAt(produccion, produccion.length() - 1);
					produccion = RemoveAt(produccion, produccion.length() - 1);
					produccion = produccion + String.valueOf(regla.getGenerador());
					nuevas.add(produccion);
				} else {

					char variable = g.getVariablesPosibles().get(0);
					g.variablesPosibles.remove(0);
					g.getVariables().add(variable);

					ArrayList<String> temp = new ArrayList<String>();
					temp.add(nuevaProduccion);
					Regla nueva = new Regla(variable, temp);
					g.nuevasReglas.add(nueva);

					produccion = RemoveAt(produccion, produccion.length() - 1);
					produccion = RemoveAt(produccion, produccion.length() - 1);
					produccion = produccion + Character.toString(variable);
					nuevas.add(produccion);

				}
			} else {
				nuevas.add(produccion);
			}

		}

		producciones = nuevas;

	}

	/**
	 * Metodo que determina si la regla es binaria
	 * @return boolean que representa si la regla es binaria o no
	 */
	public boolean esBinaria() {
		boolean respuesta = true;

		for (int i = 0; i < producciones.size() && respuesta; i++) {
			String produccion = producciones.get(i);

			if (produccion.length() == 1) {
				char caracter = produccion.charAt(0);
				if (Character.isLowerCase(caracter)==false && (caracter == LAMBDA)==false) 
					
				{
					respuesta = true;
				}
			} else if (produccion.length() == 2) {
				char caracter1 = produccion.charAt(0);
				char caracter2 = produccion.charAt(1);
				if (Character.isUpperCase(caracter1) == false || Character.isUpperCase(caracter2) == false) 																						// (variables)
				{
					respuesta = false;
				}
			} 
			
			else {
				respuesta = false;
			}
		}

		return respuesta;
	}

	
	/**
	 * Metodo que determina si la regla contiene una produccion pasada como parametro
	 * @param prod String que representa la produccion
	 * @return boolean que representa si la regla contiene o no la produccion
	 */
	public boolean contieneProduccion(String prod) {
		return producciones.contains(prod);
	}


	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String cadena = generador + " -> ";

		int tamanio = producciones.size();

		for (int i = 0; i < tamanio; i++) {
			String prod = producciones.get(i);

			if (i == tamanio - 1) {
				cadena = cadena + prod;
			} else {
				cadena = cadena + prod + " - ";
			}
		}

		return cadena;
	}

	/**
	 * Metodo que elimina un caracter de una cadena
	 * @param str String que representa la cadena
	 * @param pos int que representa la posicion en la cual se encuentra el caracter a eliminar
	 * @return
	 */
	public String RemoveAt(String str, int pos) {
		String cadena = "";

		for (int i = 0; i < str.length(); i++) {
			if (i != pos) {
				cadena = cadena + str.charAt(i);
			}
		}

		return cadena;
	}

}

