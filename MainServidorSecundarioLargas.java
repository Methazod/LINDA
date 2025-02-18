package practica5LINDA;

/**
 * Clase que crea un servidor secundario que guarda tuplas largas.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda
 */

import java.io.IOException;

public class MainServidorSecundarioLargas {
  public static void main(String[] args) throws IOException {
GetPuertosYHost puertosHost = new GetPuertosYHost();	  
	  
	  ServidorSecundario serv = new ServidorSecundario(puertosHost.getPuertos()[2]); //Se crea el servidor secundario	  	  	  	  	  
	  System.out.println("Iniciando servidor secundario largas con puerto "+ puertosHost.getPuertos()[2] +"\n");
      serv.startServer(); //Se inicia el servidor
  }
}