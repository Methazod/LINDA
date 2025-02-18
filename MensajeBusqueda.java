package practica5LINDA;

/**
 * Clase que crea el mensaje que recibira el servidor.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda.
 */

import java.io.Serializable;

public class MensajeBusqueda implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * La tupla que se envia al servidor 
	 */
	private String[] tupla;
	
	/*
	 * El metodo que se utilizara en el servidor
	 */
	private String metodo;
	
	/**
	 * Construye un mensaje
	 * 
	 * @param metodo el metodo a utilizar
	 * @param tupla la tupla a utilizar
	 */
	public MensajeBusqueda(String metodo, String[] tupla) {
		this.metodo = metodo;
		this.tupla = tupla;
	}
	
	/**
	 * Getter del metodo
	 * 
	 * @return el metodo
	 */
	public String getMetodo() {
		return metodo;
	}
	
	/**
	 * Getter de la tupla
	 * 
	 * @return la tupla
	 */
	public String[] getTupla() {
		return tupla;
	}
}