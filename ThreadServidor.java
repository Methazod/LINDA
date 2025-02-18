package practica5LINDA;

/**
 * Clase que crea un hilo servidor LINDA.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda.
 */

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ThreadServidor extends Thread{	
	
	/**
	 * Selecciona el servidor dependiendo de las tuplas
	 */
	private final int TUPLASCORTAS = 0;
	private final int TUPLASMEDIAS = 1;
	private final int TUPLASLARGAS = 2;
	
	/**
	 * InputStream del cliente
	 */
	private ObjectInputStream in;
	
	/**
	 * OutputStream del cliente
	 */
	private ObjectOutputStream out;		
	
	/**
	 * Sockets de los servidores secundarios
	 */
	private Socket[] serversSockets;
	
	/**
	 * InputStream de los servidores
	 */
	private ObjectInputStream[] serversIn;
	
	/**
	 * OutputStream de los servidores.
	 */
	private ObjectOutputStream[] serversOut;
	
	/**
	 * Sockets de las replicas
	 */
	private Socket[] replicasSocket;
	
	/**
	 * InputStream de las replicas
	 */
	private ObjectInputStream[] replicasIn;
	
	/**
	 * OutputStream de las replicas.
	 */
	private ObjectOutputStream[] replicasOut;
	
	/**
	 * Construye un hilo servidor LINDA
	 * 
	 * @param cliente el socket del cliente
	 * @param puertos los puertos de los servidores secundarios y replicas
	 * @param localhost el host de los servidores secundarios y replicas
	 * @throws IOException si el strem falla
	 */
	public ThreadServidor(Socket cliente, int[] puertos, String localhost) throws IOException {
		super();
		// Cargamos los stream de los clientes.
		in = new ObjectInputStream(cliente.getInputStream());		
		out = new ObjectOutputStream(cliente.getOutputStream());		
		
		// Cargamos los socket y los stream de los servidores replica y secundarios.
		serversSockets = new Socket[3];
		serversIn = new ObjectInputStream[3];
		serversOut = new ObjectOutputStream[3];
		
		replicasSocket = new Socket[3];
		replicasIn = new ObjectInputStream[3];
		replicasOut = new ObjectOutputStream[3];
		
		for (int i = 0; i < 3; i++) {			
			if(i+3 < puertos.length) {
				replicasSocket[i] = new Socket(localhost, puertos[i+3]);							
				replicasOut[i] = new ObjectOutputStream(this.replicasSocket[i].getOutputStream());			
				replicasIn[i] = new ObjectInputStream(this.replicasSocket[i].getInputStream());	
			}			
			serversSockets[i] = new Socket(localhost, puertos[i]);
			serversOut[i] = new ObjectOutputStream(this.serversSockets[i].getOutputStream());			
			serversIn[i] = new ObjectInputStream(this.serversSockets[i].getInputStream());
		}						
	}
	
	/**
	 * Metodo que recibe un mensaje y dependiendo del tamaño de
	 * la tupla, lo manda a un servidor o a otro.
	 */
	public void run() {				
		try {					
			System.out.println("Linda conectada");
			while(true) {				
				MensajeBusqueda tupla = (MensajeBusqueda) in.readObject();
				if(tupla.getTupla().length <= 3) {
					try {
						if(serversSockets[TUPLASCORTAS] != null) enviarServer(tupla, TUPLASCORTAS);
					} catch (SocketException | UnknownHostException e) {
						serversSockets[TUPLASCORTAS] = null;
					} 
					if(replicasSocket[TUPLASCORTAS] != null) enviarReplica(tupla, TUPLASCORTAS);
				} else if(tupla.getTupla().length > 3 && tupla.getTupla().length < 6) {
	    			try {
	    				if(serversSockets[TUPLASMEDIAS] != null) enviarServer(tupla, TUPLASMEDIAS);
	    			} catch (SocketException | UnknownHostException e) {
	    				serversSockets[TUPLASMEDIAS] = null;
					}
	    			if(replicasSocket[TUPLASMEDIAS] != null) enviarReplica(tupla, TUPLASMEDIAS);					
	    		} else if(tupla.getTupla().length == 6) {
	    			try {
	    				if(serversSockets[TUPLASLARGAS] != null) enviarServer(tupla, TUPLASLARGAS);
		    		} catch (SocketException | UnknownHostException e) {
		    			serversSockets[TUPLASLARGAS] = null;
					}
	    			if(replicasSocket[TUPLASLARGAS] != null) enviarReplica(tupla, TUPLASLARGAS);					
	    		}			
    		}
		} catch (EOFException e) {
			System.out.println("Conexion terminada");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}       
	}
	
	/**
	 * Metodo que envia una tupla a un servidor secundario
	 * 
	 * @param tupla la tupla a enviar
	 * @param serverEnviar el servidor a enviar
	 * @throws IOException si el stream falla
	 * @throws ClassNotFoundException si la clase no existe
	 */
	private void enviarServer(MensajeBusqueda tupla, int serverEnviar) throws IOException, ClassNotFoundException {		
		String longTupla = "Tupla ";
		if(serverEnviar == TUPLASCORTAS) longTupla += "corta";
		else if(serverEnviar == TUPLASMEDIAS) longTupla += "media";
		else if(serverEnviar == TUPLASLARGAS) longTupla += "larga";		
		System.out.println(longTupla);
		this.serversOut[serverEnviar].writeObject(tupla);
		this.serversOut[serverEnviar].flush();
		out.writeObject(this.serversIn[serverEnviar].readObject());
		out.flush();

		serversSockets[serverEnviar] = new Socket(serversSockets[serverEnviar].getInetAddress(), serversSockets[serverEnviar].getPort());
		serversOut[serverEnviar] = new ObjectOutputStream(this.serversSockets[serverEnviar].getOutputStream());			
		serversIn[serverEnviar] = new ObjectInputStream(this.serversSockets[serverEnviar].getInputStream());
	}
	
	/**
	 * Metodo que envia una tupla a un servidor replica,
	 * despues renueva la conexion
	 * 
	 * @param tupla la tupla a enviar
	 * @param serverEnviar el servidor a enviar
	 * @throws IOException si el stream falla
	 * @throws ClassNotFoundException si la clase no existe
	 */
	private void enviarReplica(MensajeBusqueda tupla, int serverEnviar) throws IOException, ClassNotFoundException {		
		String longTupla = "Tupla ";
		if(serverEnviar == TUPLASCORTAS) longTupla += "corta";
		else if(serverEnviar == TUPLASMEDIAS) longTupla += "media";
		else if(serverEnviar == TUPLASLARGAS) longTupla += "larga";		
		System.out.println(longTupla);
		this.replicasOut[serverEnviar].writeObject(tupla);
		this.replicasOut[serverEnviar].flush();
		out.writeObject(this.replicasIn[serverEnviar].readObject());
		out.flush();
		
		replicasSocket[serverEnviar] = new Socket(replicasSocket[serverEnviar].getInetAddress(), replicasSocket[serverEnviar].getPort());
		replicasOut[serverEnviar] = new ObjectOutputStream(this.replicasSocket[serverEnviar].getOutputStream());			
		replicasIn[serverEnviar] = new ObjectInputStream(this.replicasSocket[serverEnviar].getInputStream());
	}
}