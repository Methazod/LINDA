package practica5LINDA;

/**
 * Clase que crea un servidor secundario que guarda tuplas medias.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda
 */

import java.io.IOException;

public class MainServidorSecundarioMedias {
  public static void main(String[] args) throws IOException {
	  GetPuertosYHost puertosHost = new GetPuertosYHost();	  
	  
	  ServidorSecundario serv = new ServidorSecundario(puertosHost.getPuertos()[1]); //Se crea el servidor secundario	  	  	  	  	  
	  System.out.println("Iniciando servidor secundario medias con puerto "+ puertosHost.getPuertos()[1] +"\n");
      serv.startServer(); //Se inicia el servidor
  }
}