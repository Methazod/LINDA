package practica5LINDA;

/**
 * Clase que crea un objeto que lee un fichero config.properties
 * que guarda el puerto y host de los servidores.
 * 
 * @author Jorge Manzano Anchelergues y Jaime Usero Aranda.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetPuertosYHost {
	
	/**
     * Puertos de los servidores secundarios 
     */
    private int[] puertos;
    
    /**
     * Direccion de los servidores secundarios
     */
    private String localhost;
    
    /**
     * Construye un GetPuertosYHost
     * 
     * @throws FileNotFoundException si el fichero no existe
     * @throws IOException si un stream falla
     */
	public GetPuertosYHost() throws FileNotFoundException, IOException {
		puertos = new int[4];
		localhost = "";
		cargarVariables(localhost, puertos);
	}
	
	/**
	 * Metodo que lee el fichero config.properties
	 * y carga las variables con su informacion.
	 * 
	 * @param localhost el host de los servidores
	 * @param puertos los puertos de los servidores.
	 * @throws FileNotFoundException si el fichero no existe
	 * @throws IOException si el stream falla.
	 */
	public void cargarVariables(String localhost, int[] puertos) throws FileNotFoundException, IOException {     	
        BufferedReader b = new BufferedReader(new FileReader("D://Eclipse/ejerciciosPSP/src/practica5LINDA/config.properties")); 
        String linea = b.readLine();
        localhost = linea.split("=")[1].replaceAll("\"", "");        
        linea = b.readLine();
        int i = 0;
        while(linea != null) {         	
        	String[] lineaPartida = linea.replaceAll("\"", "").split("=");        	
        	if(lineaPartida[0].split("\\.")[1].equalsIgnoreCase("port")) puertos[i++] = Integer.valueOf(lineaPartida[1].replaceAll("\"", ""));          	
        	linea = b.readLine();
        } 
        b.close(); 
	} 
    
	/**
	 * Getter de los puertos
	 * 
	 * @return los puertos
	 */
    public int[] getPuertos() {
		return puertos;
	}
    
    /**
     * Getter del host
     * 
     * @return el host
     */
    public String getLocalhost() {
		return localhost;
	}
}