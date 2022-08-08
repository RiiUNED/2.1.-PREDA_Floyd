import java.io.*;
import java.util.*;

public class Auxiliar {
	
	/*
	 * Crea un fichero de texto con el nombre que se le pasa por parámetro en la ruta 
	 * que se le pasa por parámetro
	 * @param:	route: String con la ruta donde crear el fichero
	 * 			name: String con el nombre del fichero
	 * @return:	true -> logra crear el fichero
	 * 			false -> no logra crear el fichero
	 */
	public static boolean createFile(String route) {
		BufferedWriter bufWri;
		File file = new File(route);
		
		try {
			if (file.exists()) {
				System.out.println("ERROR");
				System.out.println("El fichero "+route+" ya existe");
				return false;
			} else {
				bufWri = new BufferedWriter(new FileWriter(file));
				bufWri.close();
				return true;
			}
		} catch (Exception e) {return false;}
		
	}
	
	/*
	 * Devuelve una instacia de la clase grafo dirigido con los datos del fichero
	 * del que se le pasa la ruta por parámetro
	 * @param:	route -> String con la ruta del fichero que contiene los datos
	 * @return:	instancia de la clase grafo dirigido
	 */
	public static DirectedGraph createGraph(String route) {
		
		List<List<Integer>> data = getAllWeights(route);
		DirectedGraph graph;
		
		if(!data.isEmpty()) {
			graph = new DirectedGraph(getSize(data));
			for(int i=0; i<getSize(data); i++) {
				for(int j=0; j<getSize(data); j++) {
					graph.setWeight(i+1, j+1, data.get(i).get(j));
				}
			}
		} else {
			graph = new DirectedGraph(0);
		}
		
		return graph;
	}
	
	/*Toma el conjunto ordenado de String del fichero de entrada y lo devuelve
	 * con Integer para que se pueda trabajar matemáticamente con él
	 * @param: datos ordenados de tipo String del fichero de entrada
	 * @return datos ordenados de tipo Integer del fichero de entrada
	 */
	private static List<List<Integer>> getAllWeights(String route){
		
		Iterator<List<String>> itS = Auxiliar.getWeights(route).iterator();
		
		List<List<Integer>> allWeights = new LinkedList<List<Integer>>();
		List<Integer> weights = new LinkedList<Integer>();
		Iterator<String> itW;
		while(itS.hasNext()) {
			itW = itS.next().iterator();
			while(itW.hasNext()) {
				weights.add(Integer.parseInt(itW.next()));
			}
			allWeights.add(weights);
			weights = new LinkedList<Integer>();
		}
		
		return allWeights;
	}

	/*
	 * Método al que se le pasa la ruta de un fichero y
	 * devuelve una lista enlazada con las líneas del fichero 
	 * en forma de String
	 * @param: 	String con la ruta del fichero
	 * @return: lista enlazada de String con las líneas del fichero
	 */
	private static List<String> readFile(String fileRoute){
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		List<String> lineas = new LinkedList<String>();
		
		try {
			archivo = new File(fileRoute);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			
			String linea;
			while ((linea=br.readLine())!=null) {
				lineas.add(linea);
			}
		}
		catch(Exception e) {
			System.out.println();
			System.out.println("No existe el achivo de entrada: "+fileRoute);
			System.out.println();
			System.out.println("Debe introducir los datos por consola");
			lineas = consoleInput();
		} finally {
			try {
				if(null!=fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return lineas;
	}
	
	/*
	 * Método al que se le pasa la ruta a un fichero con un determinado formato
	 * dado en el enunciado de la práctica y devuelve una lista enlazada con los
	 * pesos del grafo en forma de String agrupados en listas enlazadas que se 
	 * correponden con las filas
	 * @param:	String ruta del fichero
	 * @return: filas de pesos en formato String 
	 */
	private static List<List<String>> getWeights(String route){
		
		Iterator<String> itF = Auxiliar.readFile(route).iterator();
		
		final String[] valid = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-"};
		final String infinite = "-";
		List<List<String>> weights = new LinkedList<>();
		
		while(itF.hasNext()) {
			String line = itF.next();
			List<String> weight = new LinkedList<String>();
			int previous = 0;
			for(int i=0; i<line.length(); i++) {
				for(int j=0; j<valid.length; j++) {
					if(Character.toString(line.charAt(i)).equals(valid[j])) {
						if(previous==i-1) {
							weight.add(weight.size()-1, weight.get(weight.size()-1).concat(Character.toString(line.charAt(i))));
							weight.remove(weight.size()-1);
						} else {
							if(Character.toString(line.charAt(i)).equals(infinite)) {
								weight.add("-1");
							} else {
								weight.add(Character.toString(line.charAt(i)));
							}
						}
						previous = i;
					}	
				}
			}
			weights.add(weight);
		}
		return weights;
	}
	
	/*
	 * Devuelve el número de elementos por fila del fichero de entrada
	 * @param:	Lista de datos que se obtiene del fichero de entrada
	 * @return:	tamaño del grafo dirgido
	 */
	private static Integer getSize(List<List<Integer>> data) {
		return data.get(0).size();
	}
	
	/*
	 * Pide por consola los datos del grafo dirigido devolviendo 
	 * Una lista enlazada de ellos en formato String.
	 * @return ->	Lista enlazada con pesos del grafo dirigido
	 */
	private static List<String> consoleInput(){
		
		List<String> lines = new LinkedList<String>();
		String line = new String();
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("* Tamaño del grafo *");
		System.out.println("El sistema valora grafos de la forma nxn");
		System.out.print("Introduzca el tamaño de n: ");
		
		Integer dimension = input.nextInt(); 
		input.nextLine();
		
		System.out.println();
		System.out.println("* Costes de las rutas *");
		System.out.println("Para ausencia de ruta introduzca '-'");
		for (int i=1; i<=dimension; i++) {
			for (int j=1; j<=dimension; j++) {
				
				if (i==j) {
					line += "0";
				} else {
					
					System.out.print("Introduzca el coste de la ruta para ir del nodo ["+i+"] al nodo["+j+"]: ");
					line += input.nextLine();
				}
				
				line += " ";
			}
			lines.add(line);
			line = new String();
		}
		
		input.close();
		
		System.out.println();
		System.out.println("Datos introducidos con éxito");
		System.out.println();
		
		return lines;
		
	}
}
