import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	private static boolean logenabled = true;
	private static int numTest = 0;
	private static int numLetras = 0;
	private static int numPalabras = 0;
	private static ArrayList <String> palabras = new ArrayList <String>();
	private static ArrayList <ArrayList <Integer>> palabrasDecodificadas = new ArrayList <ArrayList <Integer>>();
	private static String clave = "";
	private static ArrayList <String> diccionario = new ArrayList <String>();
	private static HashMap <String, Integer> correspondencias = new HashMap < String,Integer>();

	public static void main(String [] args) {
		pedirEntrada();
		
		decodificarPalabras();
		resolverCorrespondencias();

	}

	public static void pedirEntrada () {

		Scanner scan = new Scanner(System.in);

		
		numTest = scan.nextInt();
		log("numTest= " + Integer.toString(numTest));

		for(int i=0; i<numTest; i++) {
			pedirTest(scan);
		}

	}

	public static void pedirTest(Scanner scan) {
		numLetras = scan.nextInt();
		numPalabras = scan.nextInt();
		log("numletras= " + Integer.toString(numLetras));
		log("numPalabras= " + Integer.toString(numPalabras));

		palabras.clear();
		diccionario.clear();

		scan.nextLine(); // evitar la primera linea vacia
		for (int i=0; i<numPalabras; i++) {
			String str = scan.nextLine();
			palabras.add(str);

		log("palabras[" + Integer.toString(i) + "]=" + str);

		}

		clave = scan.nextLine();
		inicializarCorrespondencias(clave);
		log("clave= " + clave);
		int indice = 1;
		for(char c : clave.toCharArray()) {
			if(anyadirCorrespondencias(c + "" ,indice)) {
				indice++;
			}

		}

		String linea = "";

		do{
			linea = scan.nextLine();
			if(!linea.equals("*")) {
				diccionario.add(linea);
				inicializarCorrespondencias(linea);
			}

		}while(!linea.equals("*"));

	}

	public static void inicializarCorrespondencias(String palabra) {
		for(int i=0; i<palabra.length(); i++) {
			correspondencias.put(palabra.charAt(i) + "",0);
		}
	}

	public static void decodificarPalabras() {
		for (int i=0; i<palabras.size(); i++) {
			ArrayList <Integer> a = new ArrayList <Integer>();
			for(String s : palabras.get(i).split(" ")) {
				if(!s.equals("0")) {
					a.add(Integer.parseInt(s));
				}
			}
			palabrasDecodificadas.add(a);
		}

	}

	public static boolean anyadirCorrespondencias(String letra ,Integer numero) {

		log("añadircorrespondencias: letra= "+ letra + "  numero= "  + Integer.toString(numero));
		if(correspondencias.get(letra)<=0) {
			correspondencias.put(letra,numero);
			return true;
		}
		return false;
	}

	public static void resolverCorrespondencias() {

	}

	public static void log(String msj) {
		if(logenabled) {
			System.out.println(msj);
		}
	}
}
