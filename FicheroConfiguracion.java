package colorjunction.conf;

import java.io.*;
import java.util.Properties;

import log.Logger;
/**
 * Lectura y escritura del fichero de configuración.
 * Para cargar y guardar los ficheros, he utilizado 
 * los métodos de la biblioteca java.util.Properties.
 * 
 * @author Javier L—pez Medina
 * @version 1.0
 */
public class FicheroConfiguracion {
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger("colorjunction.conf.FicheroConfiguracion");
	/**
	 * Fichero que se utiliza para cargar y guardar los par‡metros.
	 */
	private static final String CONF_FILE = "colorjunction.conf";
	
    String home = System.getProperty("user.home");
    File homeFile = new File(home);
    File confFile = new File(homeFile, CONF_FILE);
    /**
     * Lee los par‡metros del juego del fichero de configuraci—n.
     * @param configuracion - donde se deben cargar los par‡metros le’dos.
     */
    public static void carga(Configuracion configuracion){
    	Properties conf= new Properties();
    	try{
   	 	FileInputStream fich = new FileInputStream(CONF_FILE);
   	 	conf.load(fich);
   	 	LOGGER.fine("Se ha cargado el fichero " + CONF_FILE);
   	 	int alto = Integer.parseInt(conf.getProperty("alto"));
   	 	int ancho = Integer.parseInt(conf.getProperty("ancho"));
   	 	int colores = Integer.parseInt(conf.getProperty("colores"));
   	 	configuracion.setAlto(alto);
   	 	configuracion.setAncho(ancho);
   	 	configuracion.setNColores(colores);
    	}catch(IOException e){
    		LOGGER.config("Problema con el fichero de configuración");
    	}
    }
    /**
     * Guarda los par‡metros del juego en el fichero de configuraci—n.
     * @param configuracion - par‡metros a guardar.
     */
    public static void guarda(Configuracion configuracion){
    	Properties conf;
    	try{
   	 	FileOutputStream fich = new FileOutputStream(CONF_FILE);
   	 	conf = new Properties ();
   	 	String alto = String.valueOf(configuracion.getAlto());
   	 	String ancho = String.valueOf(configuracion.getAncho());
   	 	String nColores = String.valueOf(configuracion.getNColores());
   	 	conf.setProperty("alto",alto);
   	 	conf.setProperty("ancho", ancho);
   	 	conf.setProperty("colores", nColores);
   	 	conf.store(fich,CONF_FILE);
   	 	LOGGER.fine("Se ha guardado en el fichero " + CONF_FILE);
    }catch(IOException e){
    	LOGGER.config("Problema con el fichero de configuración");
    }
    }
}
