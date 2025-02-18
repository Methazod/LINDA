package practica5LINDA;

/**
 * Clase que crea un hilo servidor secundario o replica
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda.
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ThreadServidorSecundario extends Thread{		
	
	/**
	 * InputStream del cliente
	 */
	private ObjectInputStream in;
	
	/**
	 * OutputStream del cliente
	 */
	private ObjectOutputStream out;	
	
	/**
	 * Tuplas de los clientes.
	 */
	private ArrayList<String[]> tuplas;	
	
	/**
	 * Semaforo que gestiona la lista de tuplas
	 */
	private Semaphore mutex;
	
	/**
	 * Semaforo que gestiona la lista de tuplas
	 * para los lectores
	 */
	private Semaphore lectores;
	
	/**
	 * Cantidad de lectores que hay en el momento.
	 */
	private int numLectores;
	
	/**
	 * Construye un hilo servidor secundario
	 * 
	 * @param cliente el socket del cliente
	 * @param t la lista de tuplas
	 * @param mutex el semaforo que gestiona la lista
	 * @param lectores el semaforo que gestiona los clientes lectores
	 * @param numLectores entero que guarda la cantidad de lectores que hay entre todos los hilos
	 * @throws IOException si el stream falla
	 */
	public ThreadServidorSecundario(Socket cliente, ArrayList<String[]> t, Semaphore mutex, Semaphore lector, int numLectores) throws IOException {
		super();
		this.tuplas = t;				
		this.mutex = mutex;		
		this.lectores = lector;
		this.numLectores = numLectores;
		
		// Cargamos los stream de los clientes.
		in = new ObjectInputStream(cliente.getInputStream());
		out = new ObjectOutputStream(cliente.getOutputStream());
	}
	
	/**
	 * Metodo que recibe una tupla y metodo
	 * y devuelve el resultado
	 */
	public void run() {				
		try {			
			MensajeBusqueda mensaje = (MensajeBusqueda) in.readObject();
			if(mensaje.getMetodo().equalsIgnoreCase("read")) out.writeObject(readNote(mensaje.getTupla()));				
			if(mensaje.getMetodo().equalsIgnoreCase("remove")) out.writeObject(removeNote(mensaje.getTupla()));
			if(mensaje.getMetodo().equalsIgnoreCase("post")) out.writeObject(postNote(mensaje.getTupla()));
			out.flush();
			System.out.println("Tamaño actual de la lista: "+ tuplas.size());
			if(mensaje.getMetodo().equalsIgnoreCase("read")) gestionLecturaOut();
			else mutex.release();
			try {
				out.close();
				in.close();
			} catch (SocketException e) {}			
		} catch (ClassNotFoundException | IOException | InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Recibe una tupla y la guarda al final de la lista.
	 * @param tupla a guardar.
	 * @throws InterruptedException si el hilo falla
	 */
	public Boolean postNote(String[] tupla) throws InterruptedException {    								
		mutex.acquire();		
		return tuplas.add(tupla);
    }
    
    /**
     * Recibe una tupla, si existe la borra.
     * 
     * @param tupla a buscar
     * @return un objeto de la clase ResultadoBusqueda
     * que guarda una tupla si existe, si no guarda [null] 
     * y un booleano, [true] si existe, [false] si no existe
     * @throws InterruptedException si el hilo falla
     */
    public ResultadoBusqueda removeNote(String[] tupla) throws InterruptedException {    	
		mutex.acquire();
    	ResultadoBusqueda busqueda = busqueda(tupla);
    	if(busqueda.isExiste()) {
    		tuplas.remove(busqueda.getTupla());
    	}
    	return busqueda;
    }
    
    /**
     * Recibe una tupla y devuelve si existe o no.
     * 
     * @param tupla a buscar
     * @return [true] si existe, si no, [false]
     * @throws InterruptedException si el hilo falla
     */
    public Boolean readNote(String[] tupla) throws InterruptedException {
    	gestionLecturaIn();
    	return busqueda(tupla).isExiste();
    }
    
    /**
     * Recibe una tupla y la busca.
     * 
     * @param tupla a buscar
     * @return objeto de la clase ResultadoBusqueda.
     * Guarda la tupla si existe, si no, guarda null.
     * Guarda [true] si existe, si no, guarda [false]
     */
    private ResultadoBusqueda busqueda(String[] tupla) {
    	ArrayList<Integer> posicionesABuscar = new ArrayList<Integer>();
    	for(int i = 0; i < tupla.length; i++) {
    		if(tupla[i].length() == 2 && tupla[i].charAt(0) == '?') continue;
    		posicionesABuscar.add(i);
    	}
    	for(String[] t : tuplas) {
    		if(t.length != tupla.length) continue;
    		int cont = 0;
    		for(Integer pos : posicionesABuscar) {
    			if(tupla[pos].equalsIgnoreCase(t[pos])) cont++;
    		}
    		if(cont == posicionesABuscar.size()) return new ResultadoBusqueda(t);
    	}
    	return new ResultadoBusqueda(null);
    }
    
    /**
     * Metodo que gestiona cuando un hilo
     * entra a leer la lista.
     */
    private void gestionLecturaIn() {
    	try {
			lectores.acquire();
			if(numLectores++ == 0) mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lectores.release();
		}
    }
    
    /**
     * Metodo que gestiona cuando un hilo
     * sale de leer la lista.
     */
    private void gestionLecturaOut() {
    	try {
			lectores.acquire();
			if(--numLectores == 0) mutex.release();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		} finally {
			lectores.release();
		}
    }
}