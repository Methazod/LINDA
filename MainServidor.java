package practica5LINDA;

/**
 * Clase que crea un servidor.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda
 */

import java.io.IOException;
import java.util.Scanner;

public class MainServidor {		
  public static void main(String[] args) throws IOException {
	  Scanner escaner = new Scanner(System.in);
	  System.out.print("Introduce el puerto de Linda: ");
	  Servidor serv = new Servidor(escaner.nextInt()); //Se crea el servidor
	  escaner.close();
	  System.out.println("");
	  
      System.out.println("Iniciando Linda\n");
      serv.startServer(); //Se inicia el servidor
  }    
}