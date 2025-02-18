package practica5LINDA;

/**
 * Clase que crea un cliente
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cliente extends Conexion {
	
	/**
	 * InputStream del cliente
	 */
	private ObjectInputStream in;
	
	/**
	 * OutputStream del cliente
	 */
	private ObjectOutputStream out;	
	
	/**
	 * Construye un cliente
	 * 
	 * @param hostServidor el host del servidor a conectar
	 * @param puertoServidor puerto del servidor a conectar
	 * @throws IOException si fallan los stream
	 */
    public Cliente(String hostServidor, int puertoServidor) throws IOException {
    	super(hostServidor, puertoServidor);
    	out = new ObjectOutputStream(this.cs.getOutputStream());
		in = new ObjectInputStream(this.cs.getInputStream()); 
    }  

    /**
     * Metodo que crea un hilo que inicia la conexion
     * con el servidor
     * 
     * @param tupla la tupla a utilizar
     * @param metodo el metodo a utilizar
     * @throws IOException si fallan los stream
     * @throws InterruptedException si falla el hilo
     */
	public void startClient(String[] tupla, String metodo) throws IOException, InterruptedException {//Método para iniciar el cliente
		ThreadCliente hilo = new ThreadCliente(this.out, this.in, tupla, metodo);
		hilo.start();
		hilo.join();
    }
}
