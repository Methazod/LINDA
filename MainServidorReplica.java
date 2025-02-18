package practica5LINDA;

/**
 * Clase que crea un servidor replica.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda
 */

import java.io.IOException;

public class MainServidorReplica {
  public static void main(String[] args) throws IOException {
	  GetPuertosYHost puertosHost = new GetPuertosYHost();	  
	  	  
	  ServidorSecundario serv = new ServidorSecundario(puertosHost.getPuertos()[3]); //Se crea el servidor replica	  
	  System.out.println("Iniciando servidor replica "+ puertosHost.getPuertos()[3] +"\n");
      serv.startServer(); //Se inicia el servidor      
  }
}