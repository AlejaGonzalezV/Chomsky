package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
/*
 * Clase Gramatica
 * Clase que se encarga de simular una gramatica 
 */
public class Gramatica {

	
	/*
	 * Atributo de tipo ArrayList. Arreglo de tipo Regla con todas las reglas contenidas en la gramatica
	 */
	public ArrayList<Regla> reglas;

	/**
	 * Atributo de tipo ArrayList. Arreglo de tipo Character que representa las variables contenidas en la gramatica
	 */
	public ArrayList<Character> variables;

	/**
	 * Atributo de tipo ArrayList. Arreglo de tipo Character que representa las terminales contenidas en la gramatica
	 */
	public ArrayList<Character> terminales;

	/*
	 * Atributo de tipo ArrayList. Arreglo de tipo Character que representa todas las variables posibles presentes en la gramatica
	 */
	public ArrayList<Character> variablesPosibles = new ArrayList<Character>();

	/**
	 * Atributo de tipo Arraylist. Arreglo de tipo Regla que almacena todas las nuevas reglas que puedan agregarse a la gramatica
	 */
	public ArrayList<Regla> nuevasReglas = new ArrayList<Regla>();

	/**
	 * Metodo constructor de la clase Gramatica
	 * @param texto. String que representa la gramatica introducida por el usuario
	 */
	public Gramatica(String texto) {
		reglas = new ArrayList<Regla>();
		variables = new ArrayList<Character>();
		terminales = new ArrayList<Character>();
		agregar();
		try {
			modGramatica(texto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Metodos get y set de los atributos de la clase 
	 */
	public ArrayList<Regla> getReglas() {
		return reglas;
	}

	public void setReglas(ArrayList<Regla> reglas) {
		this.reglas = reglas;
	}

	public ArrayList<Character> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<Character> variables) {
		this.variables = variables;
	}

	public ArrayList<Character> getTerminales() {
		return terminales;
	}

	public void setTerminales(ArrayList<Character> terminales) {
		this.terminales = terminales;
	}

	public ArrayList<Regla> getNuevasReglas() {
		return nuevasReglas;
	}

	public void setNuevasReglas(ArrayList<Regla> nuevasReglas) {
		this.nuevasReglas = nuevasReglas;
	}

	public ArrayList<Character> getVariablesPosibles() {
		return variablesPosibles;
	}

	public void setVariablesPosibles(ArrayList<Character> variablesPosibles) {
		this.variablesPosibles = variablesPosibles;
	}

	
	/*
	 * Metodo que agrega a un arreglo todas las variables posibles que pueden existir en la gramatica
	 */
	public void agregar() {

		variablesPosibles.add('A');
		variablesPosibles.add('B');
		variablesPosibles.add('C');
		variablesPosibles.add('D');
		variablesPosibles.add('E');
		variablesPosibles.add('F');
		variablesPosibles.add('G');
		variablesPosibles.add('H');
		variablesPosibles.add('I');
		variablesPosibles.add('J');
		variablesPosibles.add('K');
		variablesPosibles.add('L');
		variablesPosibles.add('M');
		variablesPosibles.add('N');
		variablesPosibles.add('O');
		variablesPosibles.add('P');
		variablesPosibles.add('Q');
		variablesPosibles.add('R');
		variablesPosibles.add('S');
		variablesPosibles.add('T');
		variablesPosibles.add('U');
		variablesPosibles.add('V');
		variablesPosibles.add('W');
		variablesPosibles.add('X');
		variablesPosibles.add('Y');
		variablesPosibles.add('Z');

	}

	/**
	 * Metodo que recibe la gramatica ingresada por el usuario y la descompone para encontrar las variables y las terminales contenidas en la gramatica
	 * @param texto Gramatica introducida por el usuario
	 * @throws Exception
	 */
	public void modGramatica(String texto) throws Exception {
		String[] lineas = texto.split("\n");
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			if (linea != null && linea.trim().length() != 0) {
				linea = linea.replace(" ", "");

				// VERIFICACION 1
				String[] partes = linea.split(":");
				if (partes.length != 2) {
					throw new Exception("Regla sin el formato : " + linea);
				}

				if (partes[0].length() != 1) {
					throw new Exception("La parte izquierda de una regla debe ser un caracter");
				}

				// VERIFICACION 2
				char generador = partes[0].charAt(0);
				if (generador < 'A' || generador > 'Z' || generador == Regla.LAMBDA) {
					throw new Exception("El generador debe ser una letra mayuscula");
				}

				// VERIFICACION 3
				String[] producciones = partes[1].split("-");
				for (int j = 0; j < producciones.length; j++) {
					String prod = producciones[j].trim(); // quito espacios en blanco que pueden quedar

					// examino cada caracter de la produccion
					for (int z = 0; z < prod.length(); z++) {

						char c = prod.charAt(z);
						if (c == Regla.LAMBDA == false) {
							if (Character.isLetter(c) == false) {
								throw new Exception("el caracter " + c + " no es un terminal, variable o lambda");
							}
							// ---------------------------
							else if (Character.isLowerCase(c)) {
								if (terminales.contains(c) == false) {
									terminales.add(c);
								}
							} else if (Character.isUpperCase(c)) {
								if (variables.contains(c) == false) {
									variables.add(c);
									variablesPosibles.remove(variablesPosibles.indexOf(c));
								}
							}
							// -----------------------------
						}

						else {
							if (prod.length() > 1) {
								throw new Exception("Lambda debe aparecer sola y una unica vez en una produccion");
							}
						}
					}

					producciones[j] = prod;
				}

				// si llego hasta aqui es que no hubo ningun problema con el formato
				Regla nueva = new Regla(generador, new ArrayList<String>(Arrays.asList(producciones)));
				reglas.add(nueva);
				//
				// //-----------------------
				if (variables.contains(generador) == false) {
					variables.add(generador);
				}
				// -------------------------

			}
		}
	}

	/**
	 * Metodo que calcula las variables terminables de la gramatica
	 * @return Arreglo que contiene las variables terminables de la gramatica
	 */
	public ArrayList<Character> darTerminables() {
		ArrayList<Character> terminables = new ArrayList<Character>();

		for (int i = 0; i < reglas.size(); i++) {
			if (reglas.get(i).esTerminablePorProduccion() == true) {
				terminables.add(reglas.get(i).getGenerador());
			}
		}

		boolean cambio = true;
		while (cambio) {
			ArrayList<Character> terminablesIniciales = new ArrayList<Character>(terminables);

			for (int i = 0; i < reglas.size(); i++) {
				Character generador = reglas.get(i).getGenerador();

				if (terminables.contains(generador) == false) {
					if (reglas.get(i).esTerminablePorVariableTerminable(terminables) == true) {
						terminables.add(generador);

					}
				}
			}

			if (terminablesIniciales.size() == terminables.size()) {
				cambio = false;
			}
		}

		return terminables;
	}

	/**
	 * Metodo que calcula las variables no terminables de la gramatica
	 * @return arreglo que contiene las variables no terminables
	 */
	public ArrayList<Character> darNoTerminables() {
		ArrayList<Character> generadores = new ArrayList<Character>(darGeneradores());
		generadores.removeAll(darTerminables());
		return generadores;
	}

	/**
	 * Metodo que calcula las variables alcanzables de la gramatica
	 * @return arreglo que contiene las variables alcanzables
	 */
	public ArrayList<Character> darAlcanzables() {
		
		ArrayList<Character> alcanzables = new ArrayList<Character>();
		alcanzables.add('S');
		ArrayList<Character> yaAnalizadas = new ArrayList<Character>();

		boolean cambio = true;
		while (cambio) {
			ArrayList<Character> alcanzablesIniciales = new ArrayList<Character>(alcanzables);
			
			for (int i = 0; i < alcanzables.size(); i++) {
				if (yaAnalizadas.contains(alcanzables.get(i)) == false) {
					Regla regla = darRegla(alcanzables.get(i));
					if (regla != null) {
						ArrayList<Character> variablesAlcanzables = regla.variablesAlcanzables();
						alcanzables.addAll(variablesAlcanzables);
						

					}

					yaAnalizadas.add(alcanzables.get(i));
				}
			}
			if (alcanzablesIniciales.size() == alcanzables.size()) {
				cambio = false;
			}
		}

		return alcanzables;
	}

	/**
	 * metodo que calcula las variables no alcanzables de la gramatica
	 * @return arreglo que contiene las variables no alcanzables de la gramatica
	 */
	public ArrayList<Character> darNoAlcanzables() {
		ArrayList<Character> generadores = darGeneradores();

	
		generadores.removeAll(darAlcanzables());


		return generadores;
	}

	/**
	 * Metodo que calcula las variables anulables de la gramatica
	 * @return arreglo que contiene las variables anulables de la gramatica
	 */
	public ArrayList<Character> darAnulables() {
		ArrayList<Character> anulables = new ArrayList<Character>();

		for (int i = 0; i < reglas.size(); i++) {
			if (reglas.get(i).esAnuablePorProduccion() == true) 
			{
				anulables.add(reglas.get(i).getGenerador());
			}
		}

		boolean cambio = true;
		while (cambio) {
			ArrayList<Character> anulablesIniciales = new ArrayList<Character>(anulables);

			for (int i = 0; i < reglas.size(); i++) {
					
				if (anulables.contains(reglas.get(i).getGenerador()) == false) {
					if (reglas.get(i).esAnulablePorVariableAnulable(anulables) == true) {
						anulables.add(reglas.get(i).getGenerador());
					}
				}
			}

			if (anulablesIniciales.size() == anulables.size()) {
				cambio = false;
			}
		}

		return anulables;
	}

	/**
	 * Metodo que calcula el conjunto unitario de una variable
	 * @param generador. variable a la cual se le calcula el conjunto unitario
	 * @return Arreglo que contiene las variables del conjunto unitario
	 */
	public ArrayList<Character> darConjuntoUnitario(char generador) {

		ArrayList<Character> conjunto = new ArrayList<Character>();
		conjunto.add(generador);
		ArrayList<Character> yaEstudiado = new ArrayList<Character>();

		boolean cambio = true;
		while (cambio) {
			ArrayList<Character> conjuntoInicial = new ArrayList<Character>(conjunto);

			for (int i = 0; i < conjunto.size(); i++) {
				if (yaEstudiado.contains(conjunto.get(i)) == false) {
					Regla regla = darRegla(conjunto.get(i));
					if (regla != null) {
						
						conjunto.addAll(regla.produccionesUnitarias()); 
					}
					yaEstudiado.add(conjunto.get(i));
				}
			}

			if (conjuntoInicial.size() == conjunto.size()) {
				cambio = false;
			}
		}

		return conjunto;
	}

	/// <summary>
	/// Obtiene todas las variables que actuan como generadores. Una variable actua
	/// como generador si tiene
	/// una regla asociada.
	/// </summary>
	/// <returns>
	/// Una lista con todas las variables de la gramatica que tienen una regla
	/// asociada (actuan como generadores)
	/// </returns>
	/**
	 * Metodo que devuelve las variables que actuan como generadores. Una variable actua como generador si tiene una regla asociada
	 * @return Arreglo que contiene todas las variables generadoras
	 */
	public ArrayList<Character> darGeneradores() {
		ArrayList<Character> respuesta = new ArrayList<Character>();

		for (int i = 0; i < reglas.size(); i++) {
			respuesta.add(reglas.get(i).getGenerador());
		}

		return respuesta;
	}

	/**
	 * Metodo que obtiene la regla asociada a una variable
	 * @param generador Variable a la cual se le quiere encontrar su regla asociada
	 * @return Regla asociada a una variable
	 */
	public Regla darRegla(Character generador) {

		ArrayList<Regla> busqueda = new ArrayList<Regla>();

		for (int i = 0; i < reglas.size(); i++) {

			if (reglas.get(i).getGenerador() == generador) {

				busqueda.add(reglas.get(i));

			}

		}

		if (busqueda.size() == 0) {

			return null;

		} else {

			Regla regla1 = busqueda.get(0);
			return regla1;

		}

	}

	/**
	 * Metodo que elimina todas las variables no terminables de la gramatica
	 */
	public void eliminarNoTerminables() {
		ArrayList<Character> terminables = new ArrayList<Character>(darTerminables());
		ArrayList<Character> generadores = new ArrayList<Character>(darGeneradores());
		ArrayList<Character> noTerminables = new ArrayList<Character>(generadores);
		noTerminables.removeAll(terminables);

		for (int i = 0; i < noTerminables.size(); i++) {

			Regla regla = darRegla(noTerminables.get(i));
			if (regla != null) {
				reglas.remove(regla);
			}

		}

		for (int i = 0; i < reglas.size(); i++) {
			reglas.get(i).eliminarProduccionesConLasVariables(noTerminables);
			if (reglas.get(i).getProducciones().size() == 0) {
				reglas.remove(reglas.get(i));
			}
		}
	}

	/**
	 * Metodo que elimina todas las variables no alcanzables de la gramatica
	 */
	public void eliminarNoAlcanzables() {
		ArrayList<Character> alcanzables = new ArrayList<Character>(darAlcanzables());
		ArrayList<Character> generadores = new ArrayList<Character>(darGeneradores());
		ArrayList<Character> noAlcanzables = new ArrayList<Character>(generadores);
		noAlcanzables.removeAll(alcanzables);

		for (int i = 0; i < noAlcanzables.size(); i++) {
		
			Regla regla = darRegla(noAlcanzables.get(i));
			if (regla != null) {
				reglas.remove(regla);
			}
		}

		for (int i = 0; i < reglas.size(); i++) {
			reglas.get(i).eliminarProduccionesConLasVariables(noAlcanzables);
			if (reglas.get(i).getProducciones().size() == 0) {
				reglas.remove(reglas.get(i));
			}
		}

	}

	/**
	 * Metodo que elimina y simula, a�adiendo nuevas producciones, todas las producciones lambda
	 */
	public void eliminarProduccionesLambda() {
		ArrayList<Character> anulables = new ArrayList<Character>(darAnulables());

		for (int i = 0; i < reglas.size(); i++) {
			reglas.get(i).simularProduccionesLambda(anulables);
			
			for (int j = 0; j < reglas.get(i).getProducciones().size(); j++) {
				
				if(reglas.get(i).getProducciones().get(j).contains("&")&&reglas.get(i).getGenerador()!='S')
				{
					
					reglas.get(i).getProducciones().remove(j);
				}
				
			}

		}
	}

	/**
	 * Metodo que elimina y simula, a�adiendo nuevas producciones, todas las producciones unitarias
	 */
	public void eliminarProduccionesUnitarias() {
		ArrayList<Character> generadores = new ArrayList<Character>(darGeneradores());

		for (int i = 0; i < generadores.size(); i++) {
			ArrayList<Character> conjuntoUnitario = new ArrayList<Character>(darConjuntoUnitario(generadores.get(i)));
			Regla rule = darRegla(generadores.get(i));
			rule.eliminarProduccionesUnitarias();

			for (int j = 0; j < conjuntoUnitario.size(); j++) {

				if ((conjuntoUnitario.get(j) == generadores.get(i)) == false)

				{
					Regla regla = darRegla(conjuntoUnitario.get(j));
					if (regla != null) {
						ArrayList<String> produccionesNuevas = new ArrayList<String>(
								regla.darProduccionesNoUnitarias());
						rule.modificarProducciones(produccionesNuevas);
					}
				}
			}
		}

	}

	/**
	 * Metodo que reemplaza cada terminal por una nueva variable, a�adiendo esta ultima a la gramatica
	 */
	public void generarVariablesPorCadaTerminal() {
		ArrayList<Character> variablesPermitidas = new ArrayList<Character>(variablesPosibles);
		variablesPermitidas.removeAll(variables);
		int numeroDeReglas = reglas.size();

		Hashtable<Character, Character> asignaciones = new Hashtable<Character, Character>();
		for (int i = 0; i < terminales.size(); i++) {
			Character terminal = terminales.get(i);
			Character variableAsignada = variablesPermitidas.get(i);

			asignaciones.put(terminal, variableAsignada);
			variables.add(variableAsignada);
			variablesPosibles.remove(variableAsignada);

		}

		
		for (int y = 0; y < numeroDeReglas; y++) {
			Regla reg = reglas.get(y);
			ArrayList<String> producciones = new ArrayList<String>(reg.getProducciones());

			for (int x = 0; x < producciones.size(); x++) {
				String produccion = producciones.get(x);

				if (produccion.length() >= 1) {
					for (int i = 0; i < produccion.length(); i++) {
						Character caracter = produccion.charAt(i);
						if (terminales.contains(caracter)) {
							Character variableAsignada = asignaciones.get(caracter);
							produccion = produccion.replace(caracter, variableAsignada);

							if (!darGeneradores().contains(variableAsignada)) {
								
								ArrayList<String> nuevo = new ArrayList<String>();
								nuevo.add(Character.toString(caracter));
								Regla regla = new Regla(variableAsignada, nuevo);
								reglas.add(regla);
								nuevasReglas.add(regla);
							}
						}
					}

					producciones.add(x, produccion);
					producciones.remove(x + 1);
				}
			}

			reg.setProducciones(producciones);
		}
	}

	/**
	 * Metodo que convierte cada produccion de una regla en una produccion binaria
	 */
	public void generarProduccionesBinarias() {
		variablesPosibles.removeAll(variables);

		boolean reglasBinarias = todasLasReglasSonBinarias();
		while (!reglasBinarias) {
			
			for (int i = 0; i < reglas.size(); i++) {
				reglas.get(i).obtenerProduccionesBinarias(this);
			}
			
			for (int i = 0; i < nuevasReglas.size(); i++) {
				
				boolean esta=false;
				for (int j = 0; j < reglas.size()&&!esta; j++) {
					
					if(reglas.get(j).getGenerador()==nuevasReglas.get(i).getGenerador())
					{
						esta=true;
					}
					
				}
				
				if(!esta)
				{reglas.add(nuevasReglas.get(i));}
				
				
			}
			

			reglasBinarias = todasLasReglasSonBinarias();
		}

	}

	/// <summary>
	/// Determina si todas las reglas de la gramatica tienen sus producciones en
	/// forma binaria.
	/// </summary>
	/// <returns>
	/// Retorna true si todas las reglas tienen sus producciones en forma binaria.
	/// En caso contrario, retorna false
	// </returns>
	/**
	 * Metodo que determina si todas las reglas de la gramatica tienen sus producciones en forma binaria
	 * @return boolean que representa si todas las reglas tienen sus producciones en forma binaria o no
	 */
	public boolean todasLasReglasSonBinarias() {
		boolean respuesta = true;

		for (int i = 0; i < reglas.size() && respuesta; i++) {
			Regla r = reglas.get(i);
			respuesta = r.esBinaria();
		}

		return respuesta;
	}

	/*
	 * Metodo que busca sobre las nuevas reglas generadas (que son unitarias, en el sentido que solo tienen una produccion), una regla que tenga al parametro como produccion.
	 */
	public Regla reglaUnitariaConProduccion(String prod) {
		Regla buscada = null;

		for (int i = 0; i < nuevasReglas.size() && buscada == null; i++) {
			Regla r = nuevasReglas.get(i);
			List<String> producciones = r.getProducciones(); 

			if (producciones.get(0).equals(prod)) {
				buscada = r;
			}
		}

		return buscada;
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String cadena = "";

		
		for (int i = 0; i < reglas.size(); i++) {
			cadena = cadena + reglas.get(i).toString() + "\n";
		}

		return cadena;
	}

}
