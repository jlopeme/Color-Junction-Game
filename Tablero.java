package colorjunction;
import java.util.*;
import java.util.List;
import java.awt.*;

import colorjunction.conf.*;
import log.Logger;


/**
 * Tablero del juego.
 *
 * @author Javier López Medina
 * @version 1.0
 */

public class Tablero {
    /**
     * el color de la ficha en cada casilla; o NULL si no hay ficha
     */
	private Color[][] casillas;
    
    /**
     * a que grupo pertenece cada casilla en el tablero
     */
	private int[][] grupos;
    
    /**
     * número de grupos
     */
	private int nGrupos;
    
    /**
     * número de pasos realizados en este juego
     */
	private int pasos;
    
    /**
     * número de puntos acumulados 
     */
	private int puntos;
	/**
	 * número de colores totales en el tablero
	 */
	 private int nColores;
	/**
	 * número de fichas a lo ancho
	 */
	 private final int ANCHO;
	/**
	 * número de fichas a lo alto
	 */
     private final int ALTO;
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger("colorjunction.Tablero");
	/**
	 * Constructor.
	 * Las fichas se determinan aleatoriamente; pero siempre igual si la semilla es la misma
	 * @param semilla - fija el resultado de la elección aleatoria de fichas.
	 * @param configuracion - parmetros de configuración.
	 */
	public Tablero(long semilla, Configuracion configuracion){
		LOGGER.fine("Se ha creado un tablero aleatorio de semilla " + semilla);
		ANCHO = configuracion.getAncho();
		ALTO = configuracion.getAlto();
		nColores = configuracion.getNColores();
		casillas = new Color [ALTO][ANCHO];
		Random random = new Random(semilla);
		for (int x=0; x < ANCHO; x++){
			for(int y=0; y < ALTO; y++){
				int idx = random.nextInt(nColores);
				Color color = configuracion.getColor(idx);
				casillas[y][x] = color;
			}
		}		
		LOGGER.fine("Se ha creado un tablero aleatorio de semilla " + semilla);
		getMapaGrupos();
		getNGrupos();
	}
	/**
	 * Constructor. Las fichas se determinan aleatoriamente.
	 * @param configuracion - parámetros de configuración.
	 */
	public Tablero(Configuracion configuracion){
		this(System.currentTimeMillis(),configuracion);
		getMapaGrupos();
		getNGrupos();
	}
	/**
	 * Constructor. Genera un tablero igual al que se pasa como argumento: lo clona. 
	 * @param tablero - que se da como argumento para clonarlo
	 */
	public Tablero(Tablero tablero){
		ANCHO = tablero.getAncho();
		ALTO= tablero.getAlto();
		casillas = new Color [ALTO][ANCHO];
		for(int x = 0;x<getAncho();x++){
			for(int y = 0;y<getAlto();y++){
				this.casillas[x][y]= tablero.casillas[x][y];
			}
		}
		getMapaGrupos();
		getNGrupos();
	}
	/**
	 * Constructor. Genera un tablero usando los colores que le llegan como argumento en forma de matriz.
	 * Si algún término de la matriz es NULL, en el tablero queda como "sin ficha". 
	 * @param colores - mapa de colores: fichas en las casillas.
	 */
	public Tablero(Color[][] colores){
		ANCHO = colores.length;
		ALTO= colores[0].length;
		casillas = new Color [ALTO][ANCHO];
		for(int x = 0; x<colores.length;x++){
			for(int y=0;y<colores[0].length;y++){
				this.casillas[x][y]= colores[x][y];
			}
		}
		getMapaGrupos();
		getNGrupos();
	}
	/**
	 * Constructor. Genera un tablero utilizando unos parámetros dados por configuración
	 * y con la firma del tablero que se quiera crear.
	 * @param configuracion -parámetros de configuración.
	 * @param firma - que se da como argumento para crear un tablero de esas características
	 */
	public Tablero (Configuracion configuracion, String firma) {
		ANCHO = configuracion.getAncho();
	    ALTO = configuracion.getAlto();
	    casillas = new Color[ANCHO][ALTO];
        if (ANCHO * ALTO != firma.length())
            throw new IllegalArgumentException("mkTablero");
        for (int y = 0; y < ALTO; y++)
            for (int x = 0; x < ANCHO; x++) {
                int i = y * ANCHO + x;
                char ch = firma.charAt(i);
                if (ch != '-') {
                    int idx = ch - 'a';
                     casillas[x][y] = configuracion.getColor(idx);
                }
            }
        getMapaGrupos();
        getNGrupos();
    }
	/**
	 * Getter: devuelve el color de la ficha en la casilla indicada.
	 * @param x - posicin a lo ancho, contando desde la izquierda.
	 * @param y - posicin a lo alto, contando desde arriba. 
	 * @return - color de la ficha en esa posicin. NULL si la casilla est vaca.
	 */
	public Color getCasilla(int x, int y){
		Color color=null;
		for(int i = 0;i< ANCHO;i++){
			for(int j =0;j< ALTO;j++ ){
				if( (i==x) && (j==y))
					color = casillas[x][y]; 
			}
		}
		return color;
	}
	/**
	 * Mtodo auxiliar que utiliza getMapaGrupos() para adjudicar los grupos
	 * al tablero, mirando si la casilla de la izquierda o la de encima tiene
	 * el mismo color.
	 *
	 */
	private void addGrupos(){
		grupos =new int[ANCHO][ALTO];
		Color color;
		nGrupos = 1;
		for (int x = 0; x < ANCHO; x++){
	        for (int y = 0; y < ALTO; y++){
	        	grupos[x][y] = 0;
	        }
		}
		for (int x = 0 ; x<ANCHO ; x++){
			for (int y = ALTO-1 ; y >=0; y--){
	        	color = casillas[x][y];
	        		if((x>0) && (casillas[x-1][y]==color && grupos[x][y] == 0 && casillas [x][y] != null)){
	        			marca(x,y,color,nGrupos);
	        			nGrupos++;
	        		}else if((y>0) && (casillas[x][y-1]==color && grupos[x][y] == 0 && casillas[x][y]!=null)){
	        			marca(x,y,color,nGrupos);
	        			nGrupos++;
	        		}
	        	}
	        }
		nGrupos = nGrupos - 1;
		LOGGER.info("Grupos " + nGrupos);
	}
	/**
	 * Mtodo auxiliar recursivo que utiliza addGrupos() para dar un nmero de grupo
	 * a todas las casillas juntas con el mismo color.
	 * @param x - posicin a lo ancho, contando desde la izquierda.
	 * @param y - posicin a lo alto, contando desde arriba. 
	 * @param color - color que se da como argumento para comparar
	 * @param grupo - grupo que se va a adjudicar a las casillas
	 */
	private void marca(int x, int y, Color color, int grupo) {
		grupos[x][y] = grupo;
		if(x>0 && (casillas[x-1][y] == color && grupos[x-1][y] == 0))
			marca(x-1,y,color,grupo);
		if(x+1<ANCHO && (casillas[x+1][y] == color && grupos[x+1][y] == 0))
			marca(x+1,y,color,grupo);
		if(y >0 && (casillas[x][y-1] == color && grupos[x][y-1] == 0))
			marca(x,y-1,color,grupo);
		if(y +1 <ALTO && (casillas[x][y+1] == color && grupos[x][y+1] == 0))
			marca(x,y+1,color,grupo);
	}
	/**
	 * Devuelve en forma de String el tablero mostrando a qu 
	 * grupo pertenece cada casilla. Las casillas vacas o con 
	 * fichas independientes aparecen como '0'. Las dems posiciones, 
	 * con el nmero del grupo.Adjudicndose los grupos mediante los 
	 * mtodos addGrupos() y marca().
	 * @return mapa de grupos
	 */
	public String getMapaGrupos(){
		grupos =new int[ANCHO][ALTO];
		addGrupos();
		String mapa = "Mapa de grupos:";
		String valor;
		mapa += "\n";
		for(int j=0;j< ALTO;j++){
			for(int i=0;i< ANCHO; i++){
				if((i+1 == ANCHO) && (j+1 == ALTO)){
					valor=String.valueOf(grupos[i][j]);
					mapa += valor;
					break;
				}
				if((i+1) == ANCHO){
					valor=String.valueOf(grupos[i][j]);
					mapa += valor + "\n";
					break;
				}
				if(i < ANCHO){
					valor=String.valueOf(grupos[i][j]);
					mapa += valor + " ";
				}
			}
		}
		return mapa;
	}
	 /**
	  * Mtodo auxiliar recursivo que utiliza clic() para hacer
	  * caer las fichas cuando se hace "click" en una casilla que
	  * pertenece a un grupo.
	  * @param x - posicin a lo ancho, contando desde la izquierda.
	  * @param y - posicin a lo alto, contando desde arriba. 
	  */
	private void caer(int x,int y){
		if ( y-1 >= 0 && casillas[x][y] == null){
		casillas[x][y] = casillas [x][y-1];
		casillas[x][y-1] = null;
		caer(x,y-1);
		}else{
			casillas[x][y] = casillas[x][y];
		}
	}
	/**
	 * Mtodo auxiliar que utiliza clic() para desplazar
	 * las fichas horizontalmente hacia la izquierda cuando
	 * se hace "click" en una casilla que pertenece a un grupo.
	 * @param x - posicin a lo ancho, contando desde la izquierda.
	 * @param y - posicin a lo alto, contando desde arriba. 
	 */
	private void desplazar (int x,int y){
		if(x+1<ANCHO && casillas[x][y]==null){
			for(int j =0;j<ALTO;j++){
				casillas[x][j] = casillas [x+1][j];
				casillas [x+1][j] = null;
			}
		}
		x--;
		LOGGER.info("X VALE "  + x);
		if(x >=0 )
			desplazar(x,ALTO-1);
	}
	/**
	 * Getter. Devuelve el ancho del tablero.
	 * @return - ancho del tablero.
	 */
	public int getAncho(){
		return ANCHO;
	}
	/**
	 * Getter. Devuelve el alto del tablero.
	 * @return - alto del tablero.
	 */
	public int getAlto(){
		return ALTO;
	}
	/**
	 * Cuenta el nmero de fichas que quedan en el tablero.
	 * @return - nmero de fichas que quedan en el tablero.
	 */
	public int getNFichas(){
		Color color = null;
		int nFichas = 0;
		for(int x=0;x<ANCHO;x++){
			for(int y=0;y<ALTO;y++){
				color = casillas[x][y];
				if(color != null)
					nFichas++;
		}
	}
		return nFichas;
	}
	/**
	 * Regla para calcular los puntos sabiendo las fichas que eliminamos.
	 * Concretamente, usaremos "n * (n-1)" siendo "n" el nmero de fichas que se eliminan. 
	 * @param eliminadas - fichas que eliminamos.
	 */
	public void puntuacion(int eliminadas){
		puntos = eliminadas*(eliminadas-1);
	}
	/**
	 * Averigua las posiciones de las fichas que pertenecen a un cierto grupo.
	 * @param grupo - grupo que nos interesa.
	 * @return lista de posiciones pertenecientes al grupo.
	 */
	public java.util.List<Coordenadas> getFichasDelGrupo(int grupo){
		List<Coordenadas> fichas = new ArrayList<Coordenadas>();
		for(int x=0;x<ANCHO;x++){
			for(int y=0;y<ALTO;y++){
				if( grupos[x][y] == grupo)
					fichas.add(new Coordenadas(x,y));
			}
		}
		return fichas;
	}
	/**
	 * Averigua a qu grupo pertenece una cierta posicin. 
	 * @param x - Coordenada X de la posicin que nos interesa.
	 * @param y - Coordenada Y de la posicin que nos interesa.
	 * @return - grupo al que pertenece.
	 */
	public int getGrupo(int x, int y){
		int grupo;
		grupo = grupos[x][y];
		return grupo;
	}
	int eliminadas = 0;
	/**
	 * Hace "click" en una casilla y se eliminan las fichas del grupo. 
	 * @param x - posicin x de la casilla en la que hacemos click.
	 * @param y - posicin y de la casilla en la que hacemos click.
	 */
	public void clic(int x,int y){
		LOGGER.fine("Se ha hecho clic en la posicin( " + x + "," + y + ")");
		int g = getGrupo(x,y);
		if (grupos [x][y] != 0){
			casillas[x][y]= null;
			LOGGER.info("grupo" + grupos[x][y]);
			for(int a=0;a<ANCHO;a++){
				for(int b=0;b<ALTO;b++){
					if( grupos[a][b] == g){
						casillas[a][b]=null;
						eliminadas++;
					}
				}
			}
			for(int i = 0;i<ANCHO;i++){
				for ( int j = 0 ; j<ALTO;j++){
					if(casillas[i][j]==null && j>0)
						caer(i,j);
				}
			}
			for(int i = 0;i<ANCHO;i++){
				if (casillas[i][ALTO-1]== null)
					desplazar(i,ALTO-1);
			}
			if (casillas[x][y] != null || grupos[x][y] != 0)
				pasos++;
			getMapaGrupos();
			puntuacion(eliminadas);
		}
	}
	/**
	 * Averigua a qu grupo pertenece una cierta posicin.
	 * @param coordenadas - Coordenadas de la posicin que nos interesa.
	 * @return grupo al que pertenece.
	 */
	public int getGrupo(Coordenadas coordenadas){
		int grupo;
		int x =coordenadas.getX();
		int y = coordenadas.getY();
		grupo = grupos[x][y];
		return grupo;
	}
	/**
	 * Hace "click" en una casilla y se eliminan las fichas del grupo.
	 * @param coordenadas - casilla en la que hacemos "click".
	 */
	public void clic(Coordenadas coordenadas){
		int x = coordenadas.getX();
		int y = coordenadas.getY();
		LOGGER.fine("Se ha hecho clic en la posicin(" + x + "," + y + ")");
		int g = getGrupo(x,y);
		if (grupos [x][y] != 0){
			casillas[x][y]= null;
			LOGGER.info("grupo" + grupos[x][y]);
			for(int a=0;a<ANCHO;a++){
				for(int b=0;b<ALTO;b++){
					if( grupos[a][b] == g){
						casillas[a][b]=null;
						eliminadas++;
					}
				}
			}
			for(int i = 0;i<ANCHO;i++){
				for ( int j = 0 ; j<ALTO;j++){
					if(casillas[i][j]==null && j>0)
						caer(i,j);
				}
			}
			for(int i = 0;i<ANCHO;i++){
				if (casillas[i][ALTO-1]== null)
					desplazar(i,ALTO-1);
			}
			if (casillas[x][y] != null || grupos[x][y] != 0)
				pasos++;
			getMapaGrupos();
			puntuacion(eliminadas);
		}
	}
	/**
	 * Genera una cadena de caracteres que es una representacin supercompacta
	 * de las fichas en el tablero. Concretamente, la cadena de caracteres:
     *  -tiene tantos caracteres como casillas: un caracter por cada casilla
     *  -el carcter asociado a una casilla sin ficha ser '-'
     *  -el carcter asociado a una casilla coloreada ser 'a' para el primer color, 
     *  'b' para el segundo, y as sucesivamente; el orden de los colores es el ndice 
     *  devuelto por Configuracion.getIndice(color). 
	 * @return cadena nica que representa al tablero a todos los efectos.
	 */
	public String getFirma(){
		String firma = "";
		Color colour;
		String letra;
		String [] letras = {"a","b","c","d","e","f","g","h","i","j"};
		for(int y=0;y<ALTO;y++){
			for(int x = 0;x<ANCHO;x++){
				colour = casillas[x][y];
				if(casillas[x][y]==null){
					firma += "-";
				}else{
					letra = letras[Configuracion.getIndice(colour)];
					firma += letra;
				}
			}
		}
		return firma;
	}
	/**
	 * Devuelve el nmero de grupos
	 * Nmero de grupos. Un grupo es un conjunto de fichas del mismo color 
	 * que se iran si se hace click en cualquiera de ellas. 
	 * @return - nmero de grupos.
	 */
	public int getNGrupos(){
		return nGrupos;
	}
	/**
	 * Devuelve los pasos que damos jugando.
	 * Cada vez que hacemos click y se elimina un grupo
	 * es una jugada y por lo tanto un paso.
	 * @return nmero de pasos dados = jugadas realizadas.
	 */
	public int getPasos(){
		return pasos;
	}
	/**
	 * Devuelve los puntos acumulados.
	 * Los puntos se calculan mediante el mtodo 
	 * puntuacion().
	 * @return nmero de puntos acumulados hasta el momento.
	 */
	public int getPuntos(){
		return puntos;
	}
}