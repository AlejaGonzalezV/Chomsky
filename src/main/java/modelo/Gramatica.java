package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Gramatica {

	// ATRIBUTOS
	// --------------------------------------------------------------------------------

	// Reglas de la gramatica
	public ArrayList<Regla> reglas;

	// Representa todas las variables utilizadas.
	public ArrayList<Character> variables;

	// terminales en la gramatica
	public ArrayList<Character> terminales;

	// variables posibles
	public ArrayList<Character> variablesPosibles = new ArrayList<Character>();
	// { 'A','B'};

	// nuevas reglas que se generan en el proceso de obtener producciones binarias
	public ArrayList<Regla> nuevasReglas = new ArrayList<Regla>();

	// //matriz de resultado al aplicar el algoritmo CYK sobre una cadena
	// public List<string>[,] matriz;

	// CONSTRUCTOR
	// --------------------------------------------------------------------------------
	/// <summary>
	/// Genera un objeto Gramatica a partir de una cadena de texto que contiene la
	// gramatica expresada en
	/// un formato como el siguiente:
	/// S : aXbX
	/// X : aY | bY | &
	/// Y : X | c
	/// </summary>
	/// <param name="texto">
	/// Cadena de texto con la gramatica
	/// </param>
	/// <exception cref="System.Exception">
	/// Lanza excepciones en los casos siguientes:
	/// CASO 1: Regla no respeta el formato de separacion por ":"
	/// CASO 2: La parte izquierda de una regla es mas de un caracter (tamaño mayor
	// a 1)
	/// CASO 3: La parte izquierda de una regla no es una letra mayuscula
	/// CASO 4: Una produccion contiene un caracter que no es un terminal, variable
	// o labmda
	/// CASO 5: Una produccion contiene labmda y mas caracteres.
	/// </exception>
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

	// METODOS
	// ------------------------------------------------------------------------------------------

	/// <summary>
	/// Obtiene las variables terminables de la gramatica
	/// </summary>
	/// <returns>
	/// Retorna una lista con las variables terminables
	/// </returns>
	public ArrayList<Character> darTerminables() {
		ArrayList<Character> terminables = new ArrayList<Character>();

		// inicializacion
		// foreach(Regla regla in reglas)
		for (int i = 0; i < reglas.size(); i++) {
			if (reglas.get(i).esTerminablePorProduccion() == true) {
				terminables.add(reglas.get(i).getGenerador());
			}
		}

		// repeticion hasta que no haya cambios
		boolean cambio = true;
		while (cambio) {
			ArrayList<Character> terminablesIniciales = new ArrayList<Character>(terminables);

			// foreach (Regla regla in reglas)
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

	/// <summary>
	/// Obtiene las variables no terminables de la gramatica
	/// </summary>
	/// <returns>
	/// Retorna una lista con las variables no terminables
	/// </returns>
	public ArrayList<Character> darNoTerminables() {
		ArrayList<Character> generadores = new ArrayList<Character>(darGeneradores());
		generadores.removeAll(darTerminables());
		return generadores;
	}

	/// <summary>
	/// Obtiene las variables alcanzables de la gramatica
	/// </summary>
	/// <returns>
	/// Retorna una lista con las variables alcanzables
	/// </returns>
	public List<Character> darAlcanzables() {
		// inicializacion
		ArrayList<Character> alcanzables = new ArrayList<Character>();
		alcanzables.add('S');
		ArrayList<Character> yaAnalizadas = new ArrayList<Character>();

		// repeticion hasta que no haya cambios
		boolean cambio = true;
		while (cambio) {
			ArrayList<Character> alcanzablesIniciales = new ArrayList<Character>(alcanzables);
			// foreach(Character c in alcanzables)
			for (int i = 0; i < alcanzables.size(); i++) {
				if (yaAnalizadas.contains(alcanzables.get(i)) == false) {
					Regla regla = darRegla(alcanzables.get(i));
					if (regla != null) {
						ArrayList<Character> variablesAlcanzables = regla.variablesAlcanzables();
						alcanzables.addAll(variablesAlcanzables);
						// alcanzables = alcanzables.Union(variablesAlcanzables).ToList<Character>();

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

	/// <summary>
	/// Obtiene las variables no alcanzables de la gramatica
	/// </summary>
	/// <returns>
	/// Retorna una lista con las variables no alcanzables
	/// </returns>
	public List<Character> darNoAlcanzables() {
		ArrayList<Character> generadores = darGeneradores();

	
		generadores.removeAll(darAlcanzables());


		return generadores;
	}

	/// <summary>
	/// Obtiene las variables anulables de la gramatica
	/// </summary>
	/// <returns>
	/// Retorna una lista con las variables anulables
	/// </returns>
	public ArrayList<Character> darAnulables() {
		ArrayList<Character> anulables = new ArrayList<Character>();

		// inicializacion
		// foreach(Regla regla in reglas)
		for (int i = 0; i < reglas.size(); i++) {
			if (reglas.get(i).esAnuablePorProduccion() == true) 
			{
				anulables.add(reglas.get(i).getGenerador());
			}
		}

		// repeticion hasta que no haya cambios
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
//		eliminarProduccionesLambda();
		return anulables;
	}

	/// <summary>
	/// Obtiene el conjunto unitario de una variable
	/// </summary>
	/// <param name="generador">
	/// Representa la variable a la cual se calcula el conjunto unitario
	/// </param>
	/// <returns>
	/// Una lista con las variables pertenecientes al conjunto unitario de la
	/// variable pasada como parametro
	/// </returns>
	public ArrayList<Character> darConjuntoUnitario(char generador) {
		// inicializacion
		ArrayList<Character> conjunto = new ArrayList<Character>();
		conjunto.add(generador);
		ArrayList<Character> yaEstudiado = new ArrayList<Character>();

		// repeticion hasta que no haya cambios
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
	public ArrayList<Character> darGeneradores() {
		ArrayList<Character> respuesta = new ArrayList<Character>();

		// foreach (Regla regla in reglas)
		for (int i = 0; i < reglas.size(); i++) {
			respuesta.add(reglas.get(i).getGenerador());
		}

		return respuesta;
	}

	/// <summary>
	/// Obtiene la regla asociada a una variable
	/// </summary>
	/// <param name="generador">
	/// Representa la variable de la cual se quiere obtener su regla
	/// </param>
	/// <returns>
	/// Un objeto Regla que representa la regla asociada a la variable pasada como
	/// parametro
	/// </returns>
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

	/// <summary>
	/// Metodo que elimina todas las variables no terminables de la gramatica.
	/// Al eliminar una variable no terminable se pierde su regla asociada y todas
	/// las producciones que la contenian
	/// </summary>
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

		// En cada regla eliminar producciones que contengan variables NO terminables
		// foreach (Regla rule in reglas)
		for (int i = 0; i < reglas.size(); i++) {
			reglas.get(i).eliminarProduccionesConLasVariables(noTerminables);
			if (reglas.get(i).getProducciones().size() == 0) {
				reglas.remove(reglas.get(i));
			}
		}
	}

	/// <summary>
	/// Metodo que elimina todas las variables no alcanzables de la gramatica.
	/// Al eliminar una variable no alcanzable se pierde su regla asociada y todas
	/// las producciones que la contenian
	/// </summary>
	public void eliminarNoAlcanzables() {
		ArrayList<Character> alcanzables = new ArrayList<Character>(darAlcanzables());
		ArrayList<Character> generadores = new ArrayList<Character>(darGeneradores());
		ArrayList<Character> noAlcanzables = new ArrayList<Character>(generadores);
		noAlcanzables.removeAll(alcanzables);

		// ArrayList<Character> noAlcanzables =
		// generadores.Except(alcanzables).ToList<Character>();

		// foreach (Character noAlc in noAlcanzables)
		for (int i = 0; i < noAlcanzables.size(); i++) {
			// eliminar la regla
			Regla regla = darRegla(noAlcanzables.get(i));
			if (regla != null) {
				reglas.remove(regla);
			}
		}

		// En cada regla eliminar producciones que contengan variables NO Alcanzables
		// foreach (Regla rule in reglas)
		for (int i = 0; i < reglas.size(); i++) {
			reglas.get(i).eliminarProduccionesConLasVariables(noAlcanzables);
			if (reglas.get(i).getProducciones().size() == 0) {
				reglas.remove(reglas.get(i));
			}
		}

	}

	/// <summary>
	/// Metodo que elimina y simula, añadiendo nuevas producciones, todas las
	/// producciones labmda.
	/// </summary>
	public void eliminarProduccionesLambda() {
		ArrayList<Character> anulables = new ArrayList<Character>(darAnulables());

		// foreach(Regla regla in reglas)
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

	/// <summary>
	/// Metodo que elimina y simula, añadiendo nuevas producciones, todas las
	/// producciones unitarias
	/// </summary>
	public void eliminarProduccionesUnitarias() {
		ArrayList<Character> generadores = new ArrayList<Character>(darGeneradores());

		// foreach(Character gen in generadores)
		for (int i = 0; i < generadores.size(); i++) {
			ArrayList<Character> conjuntoUnitario = new ArrayList<Character>(darConjuntoUnitario(generadores.get(i)));
			Regla rule = darRegla(generadores.get(i));
			rule.eliminarProduccionesUnitarias();

			// foreach (Character variable in conjuntoUnitario)
			for (int j = 0; j < conjuntoUnitario.size(); j++) {

				if ((conjuntoUnitario.get(j) == generadores.get(i)) == false) // Duda. == || equals

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

	/// <summary>
	/// Toda produccion con tamaño mayor a 1 y que contenga un terminal, debe
	/// reemplazar dicho terminal por
	/// una variable. Este metodo realiza esta tarea teniendo como recurso las
	/// variables posibles y las variables
	/// hasta el momento utilizadas.
	/// Se añaden nuevas reglas con el formato (variable nueva -> terminal
	/// reemplazado)
	/// </summary>
	public void generarVariablesPorCadaTerminal() {
		ArrayList<Character> variablesPermitidas = new ArrayList<Character>(variablesPosibles);
		variablesPermitidas.removeAll(variables);
		int numeroDeReglas = reglas.size();

		// ASIGNACION
		Hashtable<Character, Character> asignaciones = new Hashtable<Character, Character>();
		for (int i = 0; i < terminales.size(); i++) {
			Character terminal = terminales.get(i);
			Character variableAsignada = variablesPermitidas.get(i);

			// asignacion
			asignaciones.put(terminal, variableAsignada);
			// aumento variables
			variables.add(variableAsignada);
			variablesPosibles.remove(variableAsignada);

			// creo la regla nueva
			// Regla regla = new Regla(variableAsignada, new List<string>() {
			// terminal.ToString() });
			// reglas.Add(regla);
			// nuevasReglas.Add(regla);
		}

		// MODIFICO PRODUCCIONES DE CADA REGLA
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
								// creo la regla nueva
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

	/// <summary>
	/// Convierte cada produccion de una regla en una produccion binaria
	/// </summary>
	public void generarProduccionesBinarias() {
		// variablesPosibles = variablesPosibles.Except(variables).ToList<Character>();
		// //para asegurar
		variablesPosibles.removeAll(variables);

		boolean reglasBinarias = todasLasReglasSonBinarias();
		while (!reglasBinarias) {
			// foreach (Regla regla in reglas)
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
	/// </returns>
	public boolean todasLasReglasSonBinarias() {
		boolean respuesta = true;

		for (int i = 0; i < reglas.size() && respuesta; i++) {
			Regla r = reglas.get(i);
			respuesta = r.esBinaria();
		}

		return respuesta;
	}

	/// <summary>
	/// Busca sobre las nuevas reglas generadas (que son unitarias, en el sentido
	/// que solo tienen una produccion),
	/// una regla que tenga al parametro como produccion.
	/// Las nuevas reglas generadas estan representadas por el atributo
	/// nuevasReglas.
	/// </summary>
	/// <param name="prod">
	/// String que representa una produccion
	/// </param>
	/// <returns>
	/// Retorna la regla que tiene como produccion al parametro. En caso de que no
	/// encuentre ninguna regla,
	/// retorna null.
	/// </returns>
	public Regla reglaUnitariaConProduccion(String prod) {
		Regla buscada = null;

		for (int i = 0; i < nuevasReglas.size() && buscada == null; i++) {
			Regla r = nuevasReglas.get(i);
			List<String> producciones = r.getProducciones(); // una lista de una unica produccion

			if (producciones.get(0).equals(prod)) {
				buscada = r;
			}
		}

		return buscada;
	}

	public String toString() {
		String cadena = "";

		// foreach(Regla regla in reglas)
		for (int i = 0; i < reglas.size(); i++) {
			cadena = cadena + reglas.get(i).toString() + "\n";
		}

		return cadena;
	}

}
