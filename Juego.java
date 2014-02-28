package colorjunction;

import javax.swing.*;

/**
 * Lanza un juego, bien como programa, bien como applet.
 *
 * @author Javier López Medina
 * @version 1.0
 */
public class Juego
        extends JApplet {
    /**
     * Versión del juego.
     */
    public static final String TITULO = "ColorJunction";

    /**
     * Arranca como applet.
     */
    public void init() {
        new GUI(this);
    }

    /**
     * Arranca en consola.
     *
     * @param args no se usan.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(TITULO);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new GUI(frame);
        frame.pack();
        frame.setVisible(true);
    }
}
