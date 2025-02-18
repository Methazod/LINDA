package practica5LINDA;

/**
 * Clase que crea un servidor secundario
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ServidorSecundario extends Conexion { //Se hereda de conexión para hacer uso de los sockets y demás
	
	/**
	 * Lista que guardara tuplas.
	 */
	private ArrayList<String[]> tuplas;
	
	/*
	 * Semaforo que no dejara que se interactue con la lista
	 * de manera simultanea
	 */
	private Semaphore mutex;	
	
	/*
	 * Semaforo que servira para que la operacion de lectura
	 * se puede hacer simultaneamente con otras operacion de 
	 * lectura
	 */
	private Semaphore lectores;
	
	/*
	 * Entero que guarda cuantos lectores hay en el momento.
	 */
	private int numLectores;
	
	/**
	 * Construye un servidor
	 * 
	 * @param puertoServidor puerto del servidor
	 * @throws IOException si el stream falla
	 */
	public ServidorSecundario(int puertoServidor) throws IOException {
    	super(puertoServidor);    	
    	tuplas = new ArrayList<String[]>();    	
    	mutex = new Semaphore(1);   
    	lectores = new Semaphore(1);   
    	numLectores = 0;
    }

	/**
	 * Método para iniciar el servidor
	 */
    public void startServer() {
        try {
        	while(true) {        			    				
    			//Esperando conexión
        		System.out.println("Servidor secundario esperando...");	        		
        		//Accept comienza el socket y espera una conexión desde un cliente
        		ThreadServidorSecundario hilo = new ThreadServidorSecundario(this.ss.accept(), tuplas, mutex, lectores, numLectores);	        			
        		hilo.start();	    		
        	}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
