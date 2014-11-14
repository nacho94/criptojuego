import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	private static int numTest = 0;
	private static int numLetras = 0;
	private static int numPalabras = 0;
	private static ArrayList <String> palabras = new ArrayList <String>();
	private static String clave = "";
	private static ArrayList <String> diccionario = new ArrayList <String>();

	public static void main(String [] args) {


	}

	public static void pedirEntrada () {

		Scanner scan = new Scanner(System.in);

		
		numTest = scan.nextInt();

		for(int i=0; i<numTest; i++) {
			pedirTest(scan);
		}

	}

	public static void pedirTest(Scanner scan) {
		numLetras = scan.nextInt();
		numPalabras = scan.nextInt();

		palabras.clear();
		diccionario.clear();

		for (int i=0; i<numPalabras; i++) {
			palabras.add(scan.nextLine());

		}

		clave = scan.nextLine();

		String linea = "";

		do{
			linea = scan.nextLine();
			if(!linea.equals("*")) {
				diccionario.add(linea);
			}

		}while(!linea.equals("*"));

	}
}