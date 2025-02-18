package practica5LINDA;

/**
 * Clase que crea un servidor secundario que guarda tuplas cortas.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda
 */

import java.io.IOException;

public class MainServidorSecundarioCortas {
  public static void main(String[] args) throws IOException {
	  GetPuertosYHost puertosHost = new GetPuertosYHost();	  
	  	  
	  ServidorSecundario serv = new ServidorSecundario(puertosHost.getPuertos()[0]); //Se crea el servidor secundario	  
	  System.out.println("Iniciando servidor secundario cortas con puerto "+ puertosHost.getPuertos()[0] +"\n");
      serv.startServer(); //Se inicia el servidor      
  }
}