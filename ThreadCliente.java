package practica5LINDA;

/**
 * Clase que crea un hilo cliente
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class ThreadCliente extends Thread{
	
	/**
	 * Cliente input
	 */
	private ObjectInputStream in;
	
	/**
	 * Cliente output
	 */
	private ObjectOutputStream out;
	
	/**
	 * Tupla que mandara el cliente
	 */
	private String[] tupla;
	
	/**
	 * Metodo que mandara el cliente
	 */
	private String metodo;
	
	/**
	 * Construye un hilo cliente
	 * 
	 * @param out output stream del cliente
	 * @param in input stream del cliente
	 * @param tupla la tupla a mandar
	 * @param el metodo a utilizar
	 * @throws IOException 
	 */
	public ThreadCliente(ObjectOutputStream out, ObjectInputStream in, String[] tupla, String metodo) throws IOException {
		this.out = out;
		this.in = in; 
		this.tupla = tupla;
		this.metodo = metodo;
	}
	
	/**
	 * Metodo que manda una busqueda y recibe un resultado,
	 * que despues interpretara y mostrara.
	 */
	public void run() {
		try {									   
	    	out.writeObject(new MensajeBusqueda(metodo, tupla));
	    	out.flush();	    
	    	Object objeto = in.readObject();
	    	if(objeto == null) System.out.println("Hay un error, porfavor, repita la operacion");	    		
	    	else {
	    		if(metodo.equalsIgnoreCase("read")) System.out.println("La tupla" + ((Boolean) objeto?" ":" no ") + "existe");	    	
		    	if(metodo.equalsIgnoreCase("post")) System.out.println("La tupla" + ((Boolean) objeto?" ":" no ") + "se añadio correctamente");
		    	if(metodo.equalsIgnoreCase("remove")) {
		    		ResultadoBusqueda resultado = (ResultadoBusqueda) objeto;
		    		if(resultado.isExiste()) System.out.println("La tupla es " + resultado);
		    		else System.out.println("La tupla no existe");
		    	}
	    	}	    	
		} catch (EOFException e) {}
		catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}