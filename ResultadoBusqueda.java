package practica5LINDA;

/**
 * Clase que crea el resultado que recibira el cliente.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda.
 */

import java.io.Serializable;

public class ResultadoBusqueda implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * [true] si la tupla existe
	 * [false] si no existe
	 */
	private boolean existe;
	
	/*
	 * Guarda la tupla
	 */
	private String[] tupla;
	
	/**
	 * Construye un resultado
	 * 
	 * @param t la tupla a guardar
	 */
	public ResultadoBusqueda(String[] t) {
		tupla = t;
		existe = (t != null ? true : false);
	}

	/**
	 * Getter de existe
	 * 
	 * @return [true] si existe la tupla,
	 * [false] si no existe la tupla
	 */
	public boolean isExiste() {
		return existe;
	}

	/**
	 * Getter de la tupla
	 * 
	 * @return la tupla
	 */
	public String[] getTupla() {
		return tupla;
	}
	
	/**
	 * Lo que se mostrara al printear un resultado
	 * 
	 * @return la tupla
	 */
	@Override
	public String toString() {
		String cadena = "";
		for(int i = 0; i < tupla.length; i++) cadena += tupla[i] + " ";
		return cadena.trim();
	}
}