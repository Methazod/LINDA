package practica5LINDA;

/**
 * Clase que crea un servidor LINDA.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda.
 */

import java.io.IOException;

public class Servidor extends Conexion { //Se hereda de conexión para hacer uso de los sockets y demás
	
	/**
	 * Construye un servidor
	 * 
	 * @param puertoServidor puerto del servidor
	 * @throws IOException
	 */
	public Servidor(int puertoServidor) throws IOException {
    	super(puertoServidor);    	
    }

	/**
	 * Método para iniciar el servidor
	 */
    public void startServer() {
        try {           	
        	GetPuertosYHost puertosHost = new GetPuertosYHost();
        	while(true) {        		        		
        		//Esperando conexión
        		System.out.println("Linda esta esperando...");	        		
        		//Accept comienza el socket y espera una conexión desde un cliente
        		ThreadServidor s = new ThreadServidor(this.ss.accept(), puertosHost.getPuertos(), puertosHost.getLocalhost());
        		s.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }               
}
