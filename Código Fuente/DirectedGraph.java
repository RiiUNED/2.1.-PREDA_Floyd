import java.util.*;

/*
 * Clase para definir los datos de tipo Grafo dirigido
 */
public class DirectedGraph {
	
	private static final Integer max = 9999;	

	private List<List<Integer>> m;
	public Integer dim;
	
	/*
	 * Devuelve el infinito
	 */
	public static Integer getMax() {
		return max;
	}
	
	/*
	 * Constructor de la clase
	 * @param:	dimension, tamaño del grafo
	 */
	public DirectedGraph(Integer dimension) {
	
		this.m = new ArrayList<List<Integer>>();
		
		for(int i=0; i<dimension; i++) {
			List<Integer> line = new ArrayList<Integer>();
			for(int j=0; j<dimension; j++) {
				line.add(0);
			}
			this.m.add(line);
		}
		
		this.dim = dimension;
	}
	
	/*
	 * Método para colocar los pesos de los arcos
	 * @param:	row, fila donde colocar el peso
	 * 			column, columna donde colocar el peso
	 * 			weight, peso del arco
	 */
	public void setWeight(int row, int column, Integer weight) {
		List<Integer> myList = this.m.get(row-1);
		myList.set(column-1, weight);
		this.m.set(row-1, myList);
	}
	
	/*
	 * Devuelve el peso del arco que está entre dos nodos 
	 * que se pasan por argumento
	 * @param:	row, fila del nodo
	 * 			column, columna del nodo 
	 */
	public Integer getWeight(int row, int column) {
		return this.m.get(row-1).get(column-1);
	}
	
	/*
	 * Devuelve los nodos de la matriz k-ésima que usa el método de 
	 * Floyd
	 * @param: 	k -> matriz del método de Floyd
	 * 			i,j -> origen y destino
	 * @return: mínima distancia entre los nodos i, y para la matirz k
	 */
	public Integer floydSelector(Integer i, Integer j, Integer k) {
		
		Integer compar1 = this.turnVoid(this.getWeight(i, j));
		Integer sum1 = this.turnVoid(this.getWeight(i, k));
		Integer sum2 = this.turnVoid(this.getWeight(k, j));
		Integer compar2 = sum1 + sum2;
		
		return Math.min(compar1, compar2);
	}
	
	/*
	 * Se le pasa por parámetro un número y devuelve infinito si el número
	 * que se pasó por parámetro era -1
	 */
	private Integer turnVoid(Integer n) {
		boolean condition = n==-1;
		return (condition)?max:n;
	}
	
	/*
	 * Método para sacar el grafo por consola
	 */
	public void show() {
		Iterator<List<Integer>> itM = this.m.iterator();
		while(itM.hasNext()) {
			Iterator<Integer> itL = itM.next().iterator();
			while(itL.hasNext()) {
				System.out.print(itL.next()+ " ");
			}
			System.out.println();
		}
	}
}
