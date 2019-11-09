package modelo;

import java.util.ArrayList;

public class Regla {

	public static char LAMBDA = '&';

	// ATRIBUTOS ---------------------------------------------------------------
	private char generador;
	private ArrayList<String> producciones;

	// CONSTRUCTOR -----------------------------------------------------------------
	public Regla(char pgenerador, ArrayList<String> pproducciones) {
		generador = pgenerador;
		producciones = pproducciones;
	}

	// METODOS ---------------------------------------------------------------------

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

	/// <summary>
	/// Determina si la regla es terminable por la presencia de una produccion
	/// lambda o por una produccion
	/// con solo terminales
	/// </summary>
	/// <returns>
	/// Retorna true si la regla es terminable por tener una produccion lambda o una
	/// produccion
	/// con solo terminales. En caso contrario, retorna false
	/// </returns>
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

	/// <summary>
	/// Determina si la regla es terminable por el hecho de tener una produccion con
	/// una variable terminable
	/// que la lleva a ser terminable.
	/// </summary>
	/// <param name="terminables">
	/// ArrayLista que contiene todas las variables terminables
	/// </param>
	/// <returns>
	/// Retorna true si la regla es terminable por la presencia de una variable
	/// terminable
	/// que la lleva a ser terminable. En caso contrario, retorna false.
	/// </returns>
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

	/// <summary>
	/// Determina las variables alcanzables directas, es decir, aquellas variables
	/// que estan en las producciones
	/// </summary>
	/// <returns>
	/// Retorna una ArrayLista con todas las variables en las producciones.
	/// </returns>
	public ArrayList<Character> variablesAlcanzables() {
		ArrayList<Character> variablesAlcanzables = new ArrayList<Character>();
		ArrayList<Character> lista = new ArrayList<Character>();

		// foreach(String produccion in producciones)
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

	/// <summary>
	/// Determina si la regla es anulable por tener una produccion lambda.
	/// </summary>
	/// <returns>
	/// Retorna true si la regla contiene una produccion lambda. En caso contrario,
	/// retorna false.
	/// </returns>
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

	/// <summary>
	/// Determina si la regla es anulable por el hecho de tener una produccion con
	/// una variable anulable que
	/// la lleva a ser anulable
	/// </summary>
	/// <param name="anulables">
	/// ArrayLista con todas las variables anulables
	/// </param>
	/// <returns>
	/// Retorna true en caso de que la regla sea anulable por tener una produccion
	/// con una variable anulable que la
	/// lleva a ser anulable. En caso contrario, retorna false.
	/// </returns>
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

	/// <summary>
	/// Obtiene todas las producciones unitarias
	/// </summary>
	/// <returns>
	/// ArrayLista con todas las producciones unitarias.
	/// </returns>
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

	/// <summary>
	/// metodo utilizado para:
	/// 1. eliminar producciones con variables no terminables
	/// 2. eliminar producciones con variables no alcanzables
	/// </summary>
	/// <param name="variables">
	/// ArrayLista con las variables
	/// </param>
	public void eliminarProduccionesConLasVariables(ArrayList<Character> variables) {
		ArrayList<String> eliminar = new ArrayList<String>();

		// foreach(char variable in variables)
		for (int i = 0; i < variables.size(); i++) {
			// foreach (String produccion in producciones)
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

	// aASb
	/// <summary>
	/// Metodo utilizado para simular las producciones lambda
	/// </summary>
	/// <param name="anulables">
	/// ArrayLista con las variables anulables
	/// </param>
	public void simularProduccionesLambda(ArrayList<Character> anulables) {
		ArrayList<String> nuevas = new ArrayList<String>();
		boolean es = false;

		// se evalua cada produccion
		// foreach(String produccion in producciones)
		for (int i = 0; i < producciones.size(); i++) {
			String produccion = producciones.get(i);
			String produccionNueva = produccion;
			ArrayList<Integer> posicion = new ArrayList<Integer>();
			for (int j = 0; j < producciones.get(i).length(); j++) {

				if (anulables.contains(produccion.charAt(j))) {
					produccionNueva = produccionNueva.replace(produccion.charAt(j) + "", "");

				}

			}

			if (!produccionNueva.equals("") && !produccionNueva.equals(produccion)) {
				nuevas.add(produccionNueva);
			}

		}

		if (nuevas.size() != 0) {
			producciones.addAll(nuevas);
		}

	}

	/// <summary>
	/// Genera una ArrayLista de numeros binarios en formato String
	/// </summary>
	/// <param name="componentes">
	/// indica cuantos componentes(tamaño) debe tener cada binario generado
	/// </param>
	/// <param name="limite">
	/// indica cuantos binarios generar
	/// </param>
	/// <returns>
	/// ArrayLista de numeros binarios en formato String
	/// </returns>
	public ArrayList<String> generarArrayListaDeBinarios(int componentes, int limite) {
		ArrayList<String> lista = new ArrayList<String>();

		for (int i = 0; i < limite; i++) {
			String binario = decimalABinario(i, componentes);
			lista.add(binario);
		}

		return lista;

	}

	/// <summary>
	/// Convierte un numero en formato decimal a su formato en binario
	/// </summary>
	/// <param name="numero">
	/// Numero decimal
	/// </param>
	/// <param name="componentes">
	/// indica cuantos componentes (tamaño) debe tener el binario generado
	/// </param>
	/// <returns>
	/// String que representa el numero binario
	/// </returns>
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

	/// <summary>
	/// Determina cuales son las producciones no unitarias
	/// </summary>
	/// <returns>
	/// ArrayLista con las producciones no unitarias
	/// </returns>
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

	/// <summary>
	/// Modifica el atributo producciones añadiendo nuevas producciones
	/// </summary>
	/// <param name="nuevasProducciones">
	/// ArrayLista de String con las nuevas producciones a añadir.
	/// </param>
	public void modificarProducciones(ArrayList<String> nuevasProducciones) {
		producciones.addAll(nuevasProducciones);
	}

	/// <summary>
	/// Elimina las producciones unitarias
	/// </summary>
	public void eliminarProduccionesUnitarias() {
		producciones = darProduccionesNoUnitarias();
	}

	/// <summary>
	/// Convierte todas las producciones en producciones binarias
	/// </summary>
	/// <param name="g">
	/// Referencia al Objeto Gramatica al cual pertenece este objeto Regla
	/// </param>
	public void obtenerProduccionesBinarias(Gramatica g) {
		ArrayList<String> nuevas = new ArrayList<String>();
		for (int i = 0; i < producciones.size(); i++) {
			String produccion = producciones.get(i);
			String produccion2 = "";
			int tamanio = produccion.length();
			if (tamanio > 2) // produccion no binaria
			{
				char caracter1 = produccion.charAt(tamanio - 1);
				char caracter2 = produccion.charAt(tamanio - 2);

				String nuevaProduccion = Character.toString(caracter2) + Character.toString(caracter1);

				Regla regla = g.reglaUnitariaConProduccion(nuevaProduccion);

				if (regla != null) {
					// Reemplazo esos caracteres por la variable que los genera

					char busco = produccion.charAt(produccion.length() - 1);

					for (int j = 0; j < produccion.length(); j++) {

						if (produccion.charAt(j) != busco) {

							produccion2.concat(Character.toString(produccion.charAt(j)));
							produccion = "";

						}

					}

					busco = produccion2.charAt(produccion.length() - 1);

					for (int j = 0; j < produccion.length(); j++) {

						if (produccion2.charAt(j) != busco) {

							produccion.concat(Character.toString(produccion.charAt(j)));
							produccion2 = "";

						}

					}

					// regla.generador.ToString()
					produccion = produccion + Character.toString(regla.getGenerador());
					nuevas.add(produccion);
				} else {
					// creo la produccion nueva

					char variable = g.getVariablesPosibles().get(0);
					ArrayList<Character> a = new ArrayList<Character>(g.getVariablesPosibles());
					a.remove(0); // menos variables
					g.setVariablesPosibles(a);
					ArrayList<Character> temp = new ArrayList<Character>(g.getVariables());
					temp.add(variable);
					g.setVariables(temp);

					// agrego regla
					ArrayList<String> m = new ArrayList<String>();
					m.add(nuevaProduccion);
					Regla nueva = new Regla(variable, new ArrayList<String>(m));
					ArrayList<Regla> b = new ArrayList<Regla>(g.getNuevasReglas());
					b.add(nueva);
					g.setNuevasReglas(b);

					// modifico produccion

					char busco = produccion.charAt(produccion.length() - 1);

					for (int j = 0; j < produccion.length(); j++) {

						if (produccion.charAt(j) != busco) {

							produccion2.concat(Character.toString(produccion.charAt(j)));

						}

					}

					produccion = produccion2;
					produccion2 = "";
					busco = produccion.charAt(produccion.length() - 1);

					for (int j = 0; j < produccion.length(); j++) {

						if (produccion.charAt(j) != busco) {

							produccion2.concat(Character.toString(produccion.charAt(j)));

						}

					}

					produccion = produccion2;

					produccion = produccion + Character.toString(variable);
					nuevas.add(produccion);
				}
			} else {
				nuevas.add(produccion);
			}

		}

		producciones = nuevas;

	}

	/// <summary>
	/// Determina si la regla es binaria.
	/// Una regla es binaria si todas sus producciones constan de dos variables o 1
	/// terminal
	/// </summary>
	/// <returns>
	/// Retorna true si la regla es binaria. En caso contrario, retorna false
	/// </returns>
	public boolean esBinaria() {
		boolean respuesta = true;

		for (int i = 0; i < producciones.size() && respuesta; i++) {
			String produccion = producciones.get(i);

			if (produccion.length() == 1) {
				char caracter = produccion.charAt(0);
				if (Character.isLowerCase(caracter) == false && (caracter == LAMBDA) == false) // si es una letra
																								// minuscula o lambda
				{
					respuesta = false;
				}
			} else if (produccion.length() == 2) {
				char caracter1 = produccion.charAt(0);
				char caracter2 = produccion.charAt(1);
				if (Character.isUpperCase(caracter1) == false || Character.isUpperCase(caracter2) == false) // si ambas
																											// son
																											// mayusculas
																											// (variables)
				{
					respuesta = false;
				}
			} else {
				respuesta = false;
			}
		}

		return respuesta;
	}

	/// <summary>
	/// Determina si la regla contiene una produccion pasada como parametro
	/// </summary>
	/// <param name="prod">
	/// String que representa la produccion
	/// </param>
	/// <returns>
	/// Retorna true si la regla contiene la produccion. En caso contrario, retorna
	/// false.
	/// </returns>
	public boolean contieneProduccion(String prod) {
		return producciones.contains(prod);
	}

	/// <summary>
	/// Genera una cadena de texto que representa este objeto Regla
	/// </summary>
	/// <returns>
	/// String que representa este objeto Regla.
	/// </returns>
	@Override
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

/// <summary>
/// Extensiones
/// </summary>
// public static class MisExtensiones
// {
// /// <summary>
// /// Metodo que permite eliminar un caracter de una cadena dando su posicion
// /// </summary>
// /// <param name="str">
// /// cadena de texto
// /// </param>
// /// <param name="pos">
// /// entero que representa la posicion en la cual se encuentra el caracter a
/// eliminar
// /// </param>
// /// <returns>
// /// cadena de texto sin el caracter.
// /// </returns>
// public static String RemoveAt(this String str, int pos)
// {
// String cadena = "";
//
// for(int i = 0; i < str.Count(); i++)
// {
// if(i != pos)
// {
// cadena = cadena + str.ElementAt(i);
// }
// }
//
// return cadena;
// }
// }
