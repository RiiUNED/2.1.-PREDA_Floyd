import java.io.IOException;

public class Floyd {

	public static void main(String[] args) throws IOException {
		
		Output output = Parameters.paramManager(args);
		
		if(output.run) {
			if(output.entrada.length()!=0) {
				DirectedGraph graph = Auxiliar.createGraph(output.entrada);
				FloydMatrix mf;
				if(graph.dim>0) {
					if (output.newfile) {
						mf = new FloydMatrix(graph, Parameters.idparam(args)[0]);
						mf.writeFile(output.salida);
					} else {
						if (output.show) {
							mf = new FloydMatrix(graph, Parameters.idparam(args)[0]);
							System.out.println("Datos de salida:");
							mf.showRoutes();
						}
					}
				} else {
					System.out.println("No se han producido datos de salida.");
				}
			}
		}
	}
}
 