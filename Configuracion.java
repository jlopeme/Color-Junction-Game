package colorjunction.conf;

import java.awt.*;

/**
 * Parámetros que configuran el juego.
 *
 * @author Javier López Medina
 * @version 1.0
 */
public class Configuracion {
    /**
     *  colores para las fichas
     */
    private static final Color[] colores;

    /**
     *  límites inferior y superior de casillas a lo ancho
     */
    private static final int MIN_ANCHO = 3;
    private static final int MAX_ANCHO = 20;

    /**
     *  límites inferior y superior de casillas a lo alto
     */
    private static final int MIN_ALTO = 3;
    private static final int MAX_ALTO = 20;

    /**
     * límites inferior y superior del número de colores
     **/
    private static final int MIN_COLORES = 2;
    private static final int MAX_COLORES;

    /**
     * casillas a lo ancho
     */
    private int ancho;
    /**
     * casillas a lo alto
     */
    private int alto;
    /**
     * número de colores disponibles
     */
    private int nColores;

    static {
        colores = new Color[]{
                Color.RED,
                Color.BLUE,
                Color.GREEN,
                Color.CYAN,
                Color.ORANGE,
                Color.BLACK,
                Color.YELLOW,
                Color.MAGENTA,
                Color.PINK,
                Color.LIGHT_GRAY,
//                Color.GRAY,
//                Color.DARK_GRAY,
//                Color.WHITE,
        };
        MAX_COLORES = colores.length;
    }

    /**
     * Constructor.
     * Pone una serie de valores por defecto:
     * tablero de 5 por 5, con 4 colores.
     * A continuación carga el fichero de configuración, si existe.
     * El fichero de configuración puede modificar los valores.
     */
    public Configuracion() {
        ancho = 5;
        alto = 5;
        nColores = 4;
        FicheroConfiguracion.carga(this);
    }

    /**
     * Constructor. Marca los valores de los campos internos.
     *
     * @param ancho    número de casillas a lo ancho.
     * @param alto     número de casillas a lo alto.
     * @param nColores número de colores para las fichas.
     * @throws IllegalArgumentException si algún valor se sale de rango.
     */
    public Configuracion(int ancho, int alto, int nColores) {
        setAncho(ancho);
        setAlto(alto);
        setNColores(nColores);
    }

    /**
     * @return número de casillas a lo ancho.
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @return número de casillas a lo alto.
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @return número de colores para las fichas.
     */
    public int getNColores() {
        return nColores;
    }

    /**
     * Los colores se numeran entre 0 y nColores.
     *
     * @param indice número de color.
     * @return color correspondiente a ese índice.
     */
    public Color getColor(int indice) {
        return colores[indice];
    }

    /**
     * Los colores se numeran entre 0 y nColores.
     * Si el color no existe, devuelve -1.
     *
     * @param color color que buscamos.
     * @return índice correspondiente a ese color.
     */
    public static int getIndice(Color color) {
        if (color == null)
            return 0;
        for (int indice = 0; indice < colores.length; indice++)
            if (colores[indice] == color)
                return indice;
        return -1;
    }

    /**
     * Marca el ancho dentro del rango posible.
     * Si el valor se sale de rango, queda el ancho anterior.
     *
     * @param ancho ancho que deseamos.
     * @throws IllegalArgumentException si el valor se sale de rango.
     */
    public void setAncho(int ancho) {
        if (ancho < MIN_ANCHO || ancho > MAX_ANCHO)
            throw new IllegalArgumentException(String.format("ancho = [%d .. %d]", MIN_ANCHO, MAX_ANCHO));
        this.ancho = ancho;
    }

    /**
     * Marca el alto dentro del rango posible.
     * Si el valor se sale de rango, queda el alto anterior.
     *
     * @param alto alto que deseamos.
     * @throws IllegalArgumentException si el valor se sale de rango.
     */
    public void setAlto(int alto) {
        if (alto < MIN_ALTO || alto > MAX_ALTO)
            throw new IllegalArgumentException(String.format("alto = [%d .. %d]", MIN_ALTO, MAX_ALTO));
        this.alto = alto;
    }

    /**
     * Marca el número de colores con que trabajamos.
     * Si el valor se sale de rango, queda el número anterior.
     *
     * @param nColores número de colores para las fichas.
     * @throws IllegalArgumentException si el valor se sale de rango.
     */
    public void setNColores(int nColores) {
        if (nColores < MIN_COLORES || nColores > MAX_COLORES)
            throw new IllegalArgumentException(String.format("nColores = [%d .. %d]", MIN_COLORES, MAX_COLORES));
        this.nColores = nColores;
    }
}
