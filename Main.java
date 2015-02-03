import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Collections;

public class Main {
	private static boolean logenabled = false;
	private static int numTest = 0;
	private static int numLetras = 0;
	private static int numPalabras = 0;
	private static ArrayList <String> palabras = new ArrayList <String>();
	private static ArrayList <ArrayList <Integer>> palabrasDecodificadas = new ArrayList <ArrayList <Integer>>();
	private static String clave = "";
	private static ArrayList <String> diccionario = new ArrayList <String>();
	private static HashMap <String, ArrayList<Integer>> correspondencias = new HashMap < String,ArrayList<Integer>>();
	private static ArrayList <ArrayList <Integer>> palabrasNoCodificadas = new ArrayList <ArrayList <Integer>>();

	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);

		pedirNumTests(scan);
		
		for(int i=0; i<numTest; i++) {
			limpiar();
			pedirTest(scan);
		
			decodificarPalabras();
			resolverCorrespondencias();
			log("correspondencias= " + correspondencias);
			imprimirCorrespondencias();
		}
	}

	private static void limpiar () {
		numLetras = 0;
		numPalabras = 0;
		palabras.clear();
		palabrasDecodificadas.clear();
		clave = "";
		diccionario.clear();
		correspondencias.clear();
		palabrasNoCodificadas.clear();

	}
	public static void pedirNumTests (Scanner scan) {

		

		
		numTest = scan.nextInt();
		log("numTest= " + Integer.toString(numTest));

		

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
			if(anyadirCorrespondencia(c + "" ,indice)) {
				indice++;
			}

		}
		log("correspondencias= " + correspondencias);
		String linea = "";

		do{
			linea = scan.nextLine();
			if(!linea.equals("*")) {
				diccionario.add(linea);
				inicializarCorrespondencias(linea);
			}

		}while(!linea.equals("*"));
		log("diccionario= " + diccionario);
	}

	public static void inicializarCorrespondencias(String palabra) {
		log("inicializarCorrespondencias= " + palabra);
		for(int i=0; i<palabra.length(); i++) {
			if(!correspondencias.containsKey(palabra.charAt(i) + "")) {
				//log("correspondencia= " + palabra.charAt(i));
				correspondencias.put(palabra.charAt(i) + "",new ArrayList<Integer>());
			}
		}
		log("correspondencias= " + correspondencias);
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
			log("palabrasDecodificadas: " +palabras.get(i) + "----" + palabrasDecodificadas.get(i));
		}

	}
	public static boolean existeValorCorrespondecia(int numero) {
		
		Iterator it = correspondencias.keySet().iterator();
		
		while(it.hasNext()){
		  String key = (String) it.next();
		  
		  	if(correspondencias.get(key).contains(numero) ){
		  		return true;
		 	}

		}
		return false;
	}
	public static boolean anyadirCorrespondencia(String letra ,Integer numero) {

		
		if(!existeValorCorrespondecia(numero) && correspondencias.get(letra).size() <= 0) {

			correspondencias.get(letra).add(numero);
			log("añadircorrespondencia: letra= "+ letra + "  numero= "  + Integer.toString(numero));
			return true;
		}
		log("NO añadircorrespondencia: letra= "+ letra + "  numero= "  + Integer.toString(numero));
		return false;
	}

	public static void anyadirCorrespondencias(String palabra, ArrayList <Integer> numeros) {
		//comprobar que la longitud de palabra y numeros sea la misma
		for ( int i=0; i<palabra.length(); i++){
			anyadirCorrespondencia(palabra.charAt(i) + "",numeros.get(i));
		}
		log("correspondencias=" + correspondencias);
	}

	public static void resolverCorrespondencias() {

		for(ArrayList <Integer> enteros : palabrasDecodificadas ) {
			limpiarDiccionario();
			String s = buscarCorrespondencia(enteros);
			if(s.equals("")) {
				palabrasNoCodificadas.add(enteros);
				continue;
			}
			if(s.equals("e")) {
				
				continue;
			}
			anyadirCorrespondencias(s,enteros);
		}

		int limite = 100;
		while(!comprobar() && limite-->0) {
			log("[PALABRASNOCODIFICADAS]= " + palabrasNoCodificadas);

			for(ArrayList <Integer> enteros1 : palabrasNoCodificadas ) {
				limpiarDiccionario();
				String s = buscarCorrespondencia(enteros1);
				if(s.equals("")) {
					
					continue;
					
				}
				if(s.equals("e")) {
					
					continue;
				}
				anyadirCorrespondencias(s,enteros1);
				
			}

		}
		if(limite<=0) {
			log("ERROR: bucle infinito");
		}

	}
	public static boolean comprobar() {
		int contador = 0;
		Iterator it = correspondencias.keySet().iterator();
		
		while(it.hasNext()){
		  String key = (String) it.next();
		  
		  	if(correspondencias.get(key) != null && correspondencias.get(key).size() > 0) {
			 contador++;
		 	}
		}
		
		return contador == numLetras;
	}

	public static ArrayList <String> devolverPlalabrasNLetras(int n) {
		ArrayList <String> result = new ArrayList <String> ();
		for(String s : diccionario) {
			if(s.length()==n) {
				result.add(s);
			}
		}
		return result;
	}

	public static int[] posicionDeMaximo(ArrayList <Integer> m) {
		return posicionDeMaximo(m,0);
	}

	public static int[] posicionDeMaximo(ArrayList <Integer> m, int offset) {
		int [] resultado = new int [3];
		int posicion = 0;
		int maximo = 0;
		for (int i=0; i<m.size(); i++) {

			int numero = m.get(i);
			
			if(numero >= maximo) {
				maximo = m.get(i);
				resultado[0]=i; // Posicion del maximo
				resultado[1]=maximo; // valor del maximo
			}
		}
		resultado[2]=0; // numero de veces que se repite el maximo
		for (int i=0; i<m.size(); i++) {
			if(m.get(i)==resultado[1]) {
				resultado[2]++;
			}

		}
		return resultado;
	}

	public static void limpiarDiccionario() {
		Iterator <String> it = diccionario.iterator();
		while(it.hasNext()) {
			String p = it.next();
			if(tieneTodasLasLetrasAsignadas(p)) {
				log("borrando palabra " + p);
				it.remove();
			}
		}
		ArrayList<String> lista = new ArrayList<String>();
Iterator <String> itr = diccionario.iterator();
		while(itr.hasNext()) {
			String elemento = itr.next();
			if(!lista.contains(elemento)) {
				lista.add(elemento);
			}
		}
		log("lista= " + lista);
		diccionario.clear();
		diccionario = lista;
		
       
		
		
		log("diccionario= " + diccionario);
	}

	public static boolean tieneTodasLasLetrasAsignadas(String palabra) {
		boolean result = true;
		for(char c : palabra.toCharArray()) {
			result = result && correspondencias.get(c + "") != null && correspondencias.get(c + "").size() > 0;

			
		}
		return result;
	}

	public static String buscarCorrespondencia(ArrayList <Integer> p) {
		log("buscarCorrespondencia: N= " + Integer.toString(p.size()));
		ArrayList <String> pnl = devolverPlalabrasNLetras(p.size());
		log("palabras de n letras= " + pnl);
		if(pnl.isEmpty()) {
			return "e";
		}
		ArrayList <Integer> probabilidades = new ArrayList <Integer>();
		log("pnl= " + pnl);
		for(String s : pnl) {
			int coincidencias = 0;
			for(int i=0; i<p.size(); i++) {
				String letra = s.charAt(i) + "";
				log("LETRA= " + letra);
				log("p.get(" + i +")= " + p.get(i));
				if(p.get(i) != -1) {
							
							
					if(correspondencias.containsKey(letra)) {
						
							if(i>0 && s.charAt(i-1)==(s.charAt(i)) ){
								if(p.get(i-1)!=p.get(i)) {
									coincidencias=(-1);
									break;
								}
						}
						
						if(correspondencias.get(letra) != null && correspondencias.get(letra).size()>0) {

							if(correspondencias.get(letra).get(0)!=p.get(i)){
								coincidencias=-1;
								log("-------------------------");
								break;
							}
						}
						if(correspondencias.get(letra) != null && correspondencias.get(letra).size()>0  && correspondencias.get(letra).get(0)==p.get(i)) {
							coincidencias++;

						}
					}
				}else{
					correspondencias.put(letra,null);
				}
			}
			probabilidades.add(coincidencias);
		}
		log("probabilidades= " + probabilidades);
		int [] posicion = posicionDeMaximo(probabilidades);
		log("posicion Maximo= " + Integer.toString(posicion[0]));
		if(posicion[2]>1) {
			log("palabra con " + Integer.toString(posicion[2]) + " maximos " + pnl.get(posicion[0]));
			
			return "";
		}
		return pnl.get(posicion[0]);
	}
	public static String devolverLetrasComodin() {
		String result = "";
		Iterator it = correspondencias.keySet().iterator();

		while(it.hasNext()){
		  String key = (String) it.next();
		  if(correspondencias.get(key) == null) {
		  	result += key;
		  }
		}
		return result;
	}
	private  static void swap(StringBuffer str, int pos1, int pos2){
	    char t1 = str.charAt(pos1);
	    str.setCharAt(pos1, str.charAt(pos2));
	    str.setCharAt(pos2, t1);
	} 

	private static void doPerm(StringBuffer str, int index, ArrayList<String> lista){

	    if(index <= 0)
	        lista.add(str.toString());          
	    else { //recursively solve this by placing all other chars at current first pos
	        doPerm(str, index-1,lista);
	        int currPos = str.length()-index;
	        for (int i = currPos+1; i < str.length(); i++) {//start swapping all other chars with current first char
	            swap(str,currPos, i);
	            doPerm(str, index-1,lista);
	            swap(str,i, currPos);//restore back my string buffer
	        }
	    }
	}

	public static String devolverLetraEnPosicion(int posicion) {
		Iterator it = correspondencias.keySet().iterator();

		while(it.hasNext()){
		  String key = (String) it.next();
		  if(correspondencias.get(key) != null && correspondencias.get(key).size() >0 && correspondencias.get(key).get(0) == posicion){
		  	return key;
		  }
		}
		return "";
	}

	public static void imprimirCorrespondencias() {
		ArrayList<String> a = new ArrayList<String>();
		String lc = devolverLetrasComodin();
		log("letrascomodin= " + lc);

		ArrayList<String> lista = new ArrayList<String>();
		doPerm(new StringBuffer(lc),lc.length(),lista);
		log("listaPermutaciones= " + lista);
		if(lc.equals("")){

			for(int i =0; i<correspondencias.size(); i++) {

				String letra = devolverLetraEnPosicion(i+1);
				
				System.out.print(letra);
			}
			System.out.println();
		}else{

			ArrayList<String> aux = new ArrayList<String>();
			for(int indicePermutacion = 0; indicePermutacion<lista.size(); indicePermutacion++) {
				String palaAux = "";
				for(int i =0; i<correspondencias.size(); i++) {

					String letra = devolverLetraEnPosicion(i+1);
					if(!letra.equals("")) {
						palaAux += letra;
					}else{
						palaAux += lista.get(indicePermutacion);
						i += lc.length()-1;
					}
				}
				aux.add(palaAux);
			}
			Collections.sort(aux);
			for(String s : aux) {
				System.out.println(s);
				
			}
		}
	}

	public static void log(String msj) {
		if(logenabled) {
			System.out.println(msj);
		}
	}
}
