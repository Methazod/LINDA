package practica5LINDA;

/**
 * Clase que crea un cliente
 * 
 * @author Jorge Manzano Ancglergues y Jaime Usero Aranda
 */

import java.io.IOException;
import java.util.Scanner;

/**
 * Main del cliente
 */
public class MainCliente {
  public static void main(String[] args) {
	  try {
		  Scanner escaner = new Scanner(System.in);
		  
		  System.out.print("Introduce el puerto del servidor: ");
		  int puertoServidor = escaner.nextInt();	  
		  escaner.nextLine();
		  
		  System.out.print("Introduce el host del servidor: ");
		  String hostServidor = escaner.nextLine();	  
		  
		  Cliente cliente = new Cliente(hostServidor, puertoServidor);
		  	  
		  while (peticion(cliente, escaner)) {}	  	
		  cliente.cs.close();
		  escaner.close();
	  } catch (Exception e) {
		e.printStackTrace();
	}
  }  
  
  /**
   * Metodo que transforma un String a String[]
   * separandolo por las comas
   * 
   * @param texto el texto a transformar
   * @return el texto transformado
   */
  private static String[] stringtoTupla(String texto) {	  	  
	  return texto.split(",");
  }
  
  /**
   * Metodo que hace una peticion al servidor iniciando el cliente
   * 
   * @param cliente el cliente para realizar la peticion
   * @param escaner el escaner para pedir el contenido de la peticion
   * @return [true] si el cliente no termina la conexion,
   * [false] si el cliente termina la conexion
   * @throws IOException si falla el stream
   * @throws InterruptedException si falla el hilo
   */
  private static boolean peticion(Cliente cliente, Scanner escaner) throws IOException, InterruptedException {
	  String metodo = obtenerMetodo(escaner);
	  if(metodo.equalsIgnoreCase("fin")) return false;	  	  
	  cliente.startClient(obtenerTupla(escaner), metodo);
	  return true;
  }
  
  /**
   * Metodo que obtiene un metodo valido 
   * que el cliente proporcionara
   * 
   * @param escaner el escaner para obtener el metodo
   * @return el metodo
   */
  private static String obtenerMetodo(Scanner escaner) {
	  String metodo = "";	  
	  while(!(metodo.equalsIgnoreCase("read") || metodo.equalsIgnoreCase("post") || metodo.equalsIgnoreCase("remove") || metodo.equalsIgnoreCase("fin"))) {
		  if(!metodo.isBlank()) System.out.println("El metodo no es valido, porfavor, introduzca uno valido");
		  System.out.println("Introduce el metodo a utilizar(read para buscar, post para añadir, "
		  		+ "remove para borrar, fin si quiere terminar la conexion): ");
		  metodo = escaner.nextLine();		  
	  }
	  return metodo;
  }
  
  /**
   * Metodo que obtiene una tupla valida
   * que el cliente propocionara
   * 
   * @param escaner el escaner para obtener la tupla
   * @return la tupla
   */
  private static String[] obtenerTupla(Scanner escaner) {
	  String[] tupla = null;	  
	  while(tupla == null || !(tupla.length <= 6)) {
		  if(tupla != null) System.out.println("La tupla no es valida, porfavor, introduzca una valida");
		  System.out.println("Introduce una tupla de tamaño seis maximo(separa cada palabra con comas): ");
		  tupla = stringtoTupla(escaner.nextLine());		  
	  }
	  return tupla;
  }
}