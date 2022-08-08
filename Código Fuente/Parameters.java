/*
 * Clase con funciones para manejar los par�metros de entrada
 * al llamar al programa 
 */
public class Parameters {
	
	private final static String paramT = "-t";
	private final static String paramH = "-h";
	
	/*Eval�a si el usuario introdujo o no par�metros seg�n indicaciones del 
	 * enunciado de la pr�ctica
	 */
	public static boolean[] idparam(String[] args) {
		
		boolean[] exit = {false, false};
		
		for(int i=0; i<args.length; i++) {
			if(args[i].equals(paramT)) {
				exit[0] = true;
			}
			if(args[i].equals(paramH)) {
				exit[1] = true;
			}
		}
		
		return exit;
	}
	
	/*
	 * Escribe la ayuda
	 */
	public static void help() {
		System.out.println("FLOYD");
		System.out.println();
		System.out.println("NOMBRE");
		System.out.println("	Floyd - Aplica el algoritmo de Floyd en un grafo dirigido");
		System.out.println();
		System.out.println("SINTAXIS");
		System.out.println("	java -jar floyd.jar [-t] [-h] [fichero de entrada] [fichero de salida]");
		System.out.println();
		System.out.println("DESCRIPCI�N");
		System.out.println("	Aplicando m�todos de programaci�n din�mica, calcula los camino m�s cortos entre dos nodos");
		System.out.println("	Resuelve tanto la longitud de la ruta m�s corta como el itinerario a seguir para tener esa longitud");
		System.out.println();
		System.out.println("OPCIONES");
		System.out.println("	-t	muestra la traza de cada llamada recursiva describiendo la parametrizaci�n");
		System.out.println("	-h	muestra la ayuda y sintaxis del comando");
		System.out.println("	fichero de entrada");
		System.out.println("		nombre del fichero del que se leen los datos, la representaci�n del grafo");
		System.out.println("	fichero de salida");
		System.out.println("		nombre del fichero que se crea para almacenar la salida");
		System.out.println("		Si el fichero ya existe, el comando dar� un error");
		System.out.println("		Si falta el argumento, se da el resultado por pantalla");
		System.out.println();
	}
	
	/*
	 * Genera la informaci�n con las distintas combinaciones de par�metros posibles
	 * para que la funci�n main de los resultados como pide el enunciado de 
	 * la pr�ctica
	 * @param:	String[] args -> par�metros de entrada por consola
	 * @return: datos en forma Output
	 */
	public static Output paramManager(String[] args) {
		
		int tam = args.length;
		
		String entrada = new String();
		String salida = new String();
		
		boolean run = false;
		boolean newfile = false;
		boolean show = false;
		
		switch(tam) {
		case 0:
			System.out.println("ERROR");
			System.out.println("No introdujo ning�n par�metro de entrada");
			break;
		case 1:
			if(Parameters.idparam(args)[1]) {
				Parameters.help();
			} else {
				entrada = args[0];
				run = !run;
				show = true;
			}
			break;
		case 2:
			if(Parameters.idparam(args)[1]) {
				Parameters.help();
				System.out.println("Introduzca el resto de par�metros sin invocar la ayuda");
			} else {
				if(!Parameters.idparam(args)[0]) {
					salida = args[1];
					newfile = Auxiliar.createFile(salida);
					if(newfile) {entrada=args[0];}
					run=!run;
				} else {
					entrada = args[1];
					run = !run;
					show = true;
				}
			}
			break;
		case 3:
			if(Parameters.idparam(args)[1]) {
				Parameters.help();
				System.out.println("Introduzca el resto de par�metros sin invocar la ayuda");
			} else {
				salida = args[2];
				newfile = Auxiliar.createFile(salida);
				if(newfile) {entrada = args[1];}
				run=!run;
			}
			break;
		case 4:
			if(Parameters.idparam(args)[1]) {
				Parameters.help();
				System.out.println("Introduzca el resto de par�metros sin invocar la ayuda");
			} else {
				System.out.println("ERROR");
				System.out.println("Revise los par�metros de entrada");
			}
			break;
		default:
			if(Parameters.idparam(args)[1]) {
				Parameters.help();
				System.out.println("Introduzca el resto de par�metros sin invocar la ayuda");
			} else {
				System.out.println("ERROR");
				System.out.println("Revise los par�metros de entrada");
			}
			break;
		}	
	return new Output(run, newfile, show, entrada, salida);
	}
}
