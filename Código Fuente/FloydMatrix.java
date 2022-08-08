import java.io.*;
import java.util.*;

/*
 * Clase de los datos generados por el algoritmo de Floyd
 */
public class FloydMatrix {
	
	private static final Integer EMPTY = -1;
	
	private DirectedGraph graph;
	private RoutesMatrix rm;
	
	/*
	 * Genera la matriz con el valor más corto entre dos nodos
	 * así como la información para generar el camino
	 */
	public FloydMatrix(DirectedGraph graph, boolean steps) {
		
		this.graph = graph;
		rm = new RoutesMatrix(graph.dim);
		Integer ancient;
		
		for (int k=1; k<=graph.dim; k++) {
			for(int i=1; i<=graph.dim; i++) {
				for(int j=1; j<=graph.dim; j++) {
					ancient = this.graph.getWeight(i, j);
					this.graph.setWeight(i, j, this.graph.floydSelector(i, j, k));
					if(ancient!=this.graph.getWeight(i, j) && !(this.graph.getWeight(i, j).equals(DirectedGraph.getMax()))) {
						rm.add(i, j, k);
					}
				}
			}
			if(steps) {
				System.out.println("k= "+k);
				this.graph.show();
				System.out.println();
			}
		}
		rm.setIntermediateNodes();
	}
	
	/**
	 * @return the empty
	 */
	private static Integer getEmpty() {
		return EMPTY;
	}

	/*
	 * Saca por consola toda la información generado por el algoritmo de Floyd
	 * en un grafo dirigido
	 */ 
	public void showRoutes() {
		System.out.println();
		for(int i=1; i<=graph.dim; i++) {
			for(int j=1; j<=graph.dim; j++) {
				System.out.print("["+i+", "+j+"]: ");
				rm.showIN(i,j);
				System.out.print(graph.getWeight(i, j)+" ");
				System.out.println();
			}
		}
	}
	
	/*
	 * Crea un fichero de texto en la ruta que se le pasa por parámetro
	 * con los datos de salida del programa
	 * @param:	ruta de creación del fichero
	 */
	public void writeFile(String route) throws IOException{
		Queue<String> queue = getInfo();
		File file = new File(route);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		while (queue.size()>0) {
			bw.write(queue.poll());
			bw.newLine();
		}
		bw.close();
		
		System.out.println();
		System.out.println("Puede consultar los datos en el fichero: "+route);
		System.out.println();
	}
	
	/* 
	 * Devuelve un pila con las líneas del fichero de salida
	 * @return: pila de String que se corresponden con las líneas
	 * 			del fichero de salida.
	 */
	private Queue<String> getInfo(){
		Queue<String> info = new LinkedList<String>();
		String line = new String();
		
		
		for(int i=1; i<=graph.dim; i++) {
			for(int j=1; j<=graph.dim; j++) {
				line += "["+i+", "+j+"]: ";
				line += rm.getIN(i, j);
				line += graph.getWeight(i, j).toString()+" ";
				info.add(line);
				line = new String();
			}
		}
		return info;
	}
	
	/*
	 * Clase asociada a la matriz de Floyd con las rutas entre los nodos
	 */
	private class RoutesMatrix{
		
		private List<List<Route>> m;
		private IntermediateNodes in;
		private Integer dim;
		
		/*
		 * Inicializa el dato
		*/ 
		private RoutesMatrix(Integer dim) {
			
			this.dim = dim;
			m = new ArrayList<List<Route>>();
			List<Route> row = new ArrayList<Route>();
			
			for(int i=0; i<dim; i++) {
				for(int j=0; j<dim; j++) {
					row.add(new Route());
				}
				m.add(row);
				row = new ArrayList<Route>();
			}
			
			in = new IntermediateNodes(dim);
		}
		
		/*
		 * Muestra por consola los nodos intermedios en la ruta entre i, j
		 * @param:	i, j -> nodo origen, nodo destino
		 */
		private void showIN(Integer i, Integer j) {
			if(!(in.getIntNodes(i, j).isEmpty())) {
				for(int m=0; m<in.getIntNodes(i, j).size(); m++) {
					System.out.print(in.getIntNodes(i, j).get(m));
					if(m!=(in.getIntNodes(i, j).size()-1)) {
						System.out.print(",");
					} else {
						System.out.print(": ");
					}
				}
			}
		}
		
		/*
		 * Devuelve un String con los nodos intermedios en la ruta entre i, j
		 * @param:	i, j -> nodo origen, nodo destino
		 * @return: String con formato y los nodos intermedios entr i, j
		 */
		private String getIN(Integer i, Integer j){
			String nodes = new String();
			
			if(!(in.getIntNodes(i, j).isEmpty())) {
				for(int m=0; m<in.getIntNodes(i, j).size(); m++) {
					nodes += in.getIntNodes(i, j).get(m).toString();
					if(m!=(in.getIntNodes(i, j).size()-1)) {
						nodes += ",";
					} else {
						nodes += ": ";
					}
				}
			}			
			return nodes;
		}
		
		/*
		 * Añade un nodo a la ruta
		 */
		private void add(Integer i, Integer j, Integer node) {
			m.get(i-1).get(j-1).addNode(node);
		}

		/*
		 * Cálculo de la ruta mínima para cada ruta posible del
		 * grafo dirigido
		 */
		private void setIntermediateNodes() {
			List<Integer> nodes = new LinkedList<Integer>();
			for(int i=0; i<dim; i++) {
				for(int j=0; j<dim; j++) {
					
					nodes = generateIN(nodes,i,j);
					this.in.setIntNodes(i+1, j+1, nodes);
					nodes = new LinkedList<Integer>();
				}
			}
			
		}
		
		/*
		 * Imprimir recursivo. Método del libro
		 */
		private List<Integer> generateIN(List<Integer> nodes, Integer i, Integer j) {
			
			Integer k = m.get(i).get(j).isEmpty();
			
			if(!(k.equals(getEmpty()))) {
				generateIN(nodes, i, k-1);
				nodes.add(k);
				generateIN(nodes, k-1, j);
			}			
			return nodes;			
		}
		
		/*
		 * Clase para guardar los nodos intermedios generados por la recursividad
		 */
		private class IntermediateNodes{
			
			private List<List<List<Integer>>> m;
			
			// Constructor
			private IntermediateNodes(Integer dim) {
				
				List<List<Integer>> row = new LinkedList<List<Integer>>();
				m = new LinkedList<List<List<Integer>>>();
				
				for(int i=0; i<dim; i++) {
					for(int j=0; j<dim; j++) {
						row.add(new LinkedList<Integer>());
					}
					m.add(row);
					row = new LinkedList<List<Integer>>();
				}
			}
			
			/*
			 * Devuelve los nodos intermedios de la ruta entre los nodos
			 * i,j empezando en 1,1
			 * @param:	i -> fila
			 * 			j -> columna
			 * @return:	nodos intermedios entre i, j
			 */
			private List<Integer> getIntNodes(Integer i, Integer j){
				return m.get(i-1).get(j-1);
			}
			
			/*
			 * Añade los nodos intermedios en la posicion i, j empezando en 1, 1
			 * @param:	i -> fila
			 * 			j -> columna
			 */
			private void setIntNodes(Integer i, Integer j, List<Integer> nodes) {
				m.get(i-1).set(j-1, nodes);
			}
		}
		
		/*
		 * Clase que almacenará los nodos que se usan en la recursión
		 * para calcular la ruta más corta entre dos nodos
		 */
		private class Route{
			
			private List<Integer> route;
			
			/*
			 * Inicializa el dato
			 */ 
			private Route() {
				route = new LinkedList<Integer>();
			}
			
			/*
			 * Añade un nodo a la ruta
			*/ 
			private void addNode(Integer node) {
				route.clear();
				route.add(node);
			}
			
			/*
			 * Indica si la ruta tiene nodos
			 * @return:	-1 -> está vacía
			 * 			número distinto de -1 -> tiene nodos
			 */
			private Integer isEmpty() {
				return (route.isEmpty())?getEmpty():route.get(0);
			}
		}
	}

}
