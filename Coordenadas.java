package colorjunction;

/**
 * Coordenadas de una casilla del tablero de juego.
 *
 * @author Javier López Medina
 * @version 1.0
 */
public class Coordenadas {
    /**
     * Abascisa.
     */
    private final int x;
    /**
     * Ordenada.
     */
    private final int y;

    /**
     * Constructor.
     *
     * @param x posición a lo ancho, contando desde la izquierda.
     * @param y posición a lo alto, contando desde arriba.
     */
    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter.
     *
     * @return posición a lo ancho, contando desde la izquierda.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter.
     *
     * @return posición a lo alto, contando desde arriba.
     */
    public int getY() {
        return y;
    }

    /**
     * @return Devuelve las coordenadas en formato imprimible.
     */
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
