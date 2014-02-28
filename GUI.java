package colorjunction;

import colorjunction.conf.Configuracion;
import colorjunction.conf.FicheroConfiguracion;
import colorjunction.conf.PanelConfiguracion;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Graphical User Interface. Interfaz gráfica para jugar.
 *
 * @author Javier López Medina
 * @version 1.0
 */
public class GUI
        extends JPanel {
	/**
	 * Logger
	 */
    private static final Logger LOGGER = Logger.getLogger("colorjunction.GUI");
    /**
     * Espacio entre el dibujo y los bordes del tablero.
     */
    private static final int MARGEN = 10;
    /**
     * Tamaño de las cendas con fichas.
     */
    private static final int CELDA = 30;
    /**
     * Espacio entre celdas.
     */
    private static final int BORDE = 1;

    /**
     * coordenadas de extremo superior izquierdo (NorthWest)
     */
    private int nwx = MARGEN;
    private int nwy = MARGEN;

    /**
     * Configuración que estamos usando en el juego en curso.
     */
    private Configuracion configuracionActual;
    /**
     * Configuración que usaremos en el siguiente juego.
     */
    private Configuracion configuracionSiguiente;
    /**
     * Semilla que estamos usando en el juego en curso.
     */
    private long semillaActual;
    /**
     * Tablero del juego en curso.
     */
    private Tablero tablero;

    /**
     * Para informar de los puntos conseguidos hasta el momento.
     */
    private JLabel puntosLabel;
    /**
     * Para informar de las fichas que quedan en el tablero.
     */
    private JLabel fichasLabel;
    /**
     * Para informar de los pasos dados hasta el momento.
     */
    private JLabel pasosLabel;
    /**
     * Para iniciar un nuevo juego.
     */
    private Action startAction;
    /**
     * Para repetir el juego en curso desde el principio.
     */
    private Action replayAction;
    /**
     * Para cambiar la configuración del juego.
     * Se aplicará al juego siguiente.
     */
    private Action configurationAction;
    /**
     * Para mostrar la ayuda.
     */
    private Action helpAction;

    /**
     * Constructor.
     */
    private GUI() {
        configuracionSiguiente = new Configuracion();
        configuracionActual = configuracionSiguiente;
        semillaActual = System.currentTimeMillis();
        tablero = new Tablero(semillaActual, configuracionActual);
        LOGGER.info(tablero.getFirma());
        LOGGER.info(tablero.getMapaGrupos());
        startAction = new StartAction();
        replayAction = new ReplayAction();
        configurationAction = new ConfigurationAction();
        helpAction = new HelpAction();
    }

    /**
     * Arranca dentro de un applet.
     *
     * @param applet marco "madre".
     */
    public GUI(JApplet applet) {
        this();
        applet.setJMenuBar(mkMenuBar());
        setContents(applet.getContentPane());
    }

    /**
     * Arranca de una ventana del equipo.
     *
     * @param frame ventana "madre".
     */
    public GUI(JFrame frame) {
        this();
        frame.setJMenuBar(mkMenuBar());
        setContents(frame.getContentPane());
    }

    /**
     * Prepara los menús de la barra superior.
     *
     * @return barra de menús.
     */
    private JMenuBar mkMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("partida");
        menuBar.add(menu1);
        menu1.add(startAction);
        menu1.add(replayAction);

        JMenu menu2 = new JMenu("configuración");
        menu2.add(configurationAction);
        menuBar.add(menu2);

        JMenu menu3 = new JMenu("ayuda");
        menu3.add(helpAction);
        menuBar.add(menu3);

        return menuBar;
    }

    /**
     * Prepara el contenido de la ventana principal.
     *
     * @param container contenedor en donde se coloca.
     */
    private void setContents(Container container) {
        int ANCHO = MARGEN + tablero.getAncho() * CELDA + MARGEN;
        int ALTO = MARGEN + tablero.getAlto() * CELDA + MARGEN;
        setPreferredSize(new Dimension(ANCHO, ALTO));
        container.add(this, BorderLayout.CENTER);
        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(new MyMouseListener());

        Box south = Box.createVerticalBox();
        pasosLabel = new JLabel();
        south.add(pasosLabel);
        puntosLabel = new JLabel();
        south.add(puntosLabel);
        fichasLabel = new JLabel();
        south.add(fichasLabel);
        container.add(south, BorderLayout.SOUTH);
        repaint();
        presentaEstado();
    }

    /**
     * Llamada por java para pintarse en la pantalla.
     *
     * @param g sistema gráfico 2D para dibujarse.
     */
    public void paint(Graphics g) {
        update(g);
    }

    /**
     * Llamada por java para actualizar la pantalla.
     *
     * @param g sistema gráfico 2D para dibujarse.
     */
    public void update(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int x = 0; x < tablero.getAncho(); x++)
            for (int y = 0; y < tablero.getAlto(); y++) {
                Color color = tablero.getCasilla(x, y);
                if (color == null)
                    pintaCasilla(g, x, y, Color.WHITE);
                else
                    pintaCasilla(g, x, y, color);
            }
//        pintaCasilla(g, 0, 0, Color.BLACK);
//        pintaCasilla(g, 0, 1, Color.LIGHT_GRAY);
    }

    /**
     * LLeva el contenido de una casilla a la ventana.
     *
     * @param g     entorno gráfico en el que se dibuja.
     * @param x     coordenada X de la casilla (arriba a la izquierda).
     * @param y     coordenada Y de la casilla (arriba a la izquierda).
     * @param color color de la casilla.
     */
    private void pintaCasilla(Graphics g, int x, int y, Color color) {
        int gx = nwx + x * CELDA + BORDE;
        int gy = nwy + y * CELDA + BORDE;
        g.setColor(color);
        g.fillRoundRect(gx + BORDE, gy + BORDE,
                CELDA - 2 * BORDE, CELDA - 2 * BORDE,
                10, 10);
    }

    /**
     * Presenta el estado del juego: puntos, fichas y pasos.
     */
    private void presentaEstado() {
        puntosLabel.setText(String.format("puntos: %d", tablero.getPuntos()));
        fichasLabel.setText(String.format("fichas: %d", tablero.getNFichas()));
        pasosLabel.setText(String.format("pasos: %d", tablero.getPasos()));
    }

    /**
     * Comienza un nuevo juego.
     */
    private class StartAction
            extends AbstractAction {
        StartAction() {
            super("nuevo juego");
            setEnabled(true);
        }

        /**
         * Comienza un nuevo juego.
         *
         * @param ae evento de disparo.
         */
        public void actionPerformed(ActionEvent ae) {
            semillaActual = System.currentTimeMillis();
            configuracionActual = configuracionSiguiente;
            tablero = new Tablero(semillaActual, configuracionActual);
            repaint();
            LOGGER.info(tablero.getFirma());
            LOGGER.info(tablero.getMapaGrupos());
            presentaEstado();
        }
    }

    /**
     * Volvemos a empezar el mismo juego.
     */
    private class ReplayAction
            extends AbstractAction {
        ReplayAction() {
            super("replay");
            setEnabled(true);
        }

        /**
         * Volvemos a empezar el mismo juego.
         *
         * @param ae evento de disparo.
         */
        public void actionPerformed(ActionEvent ae) {
            tablero = new Tablero(semillaActual, configuracionActual);
            repaint();
            LOGGER.info(tablero.getFirma());
            LOGGER.info(tablero.getMapaGrupos());
            presentaEstado();
        }
    }

    /**
     * Cambiamos los parámetros del juego.
     */
    private class ConfigurationAction
            extends AbstractAction {
        ConfigurationAction() {
            super("configuración");
            setEnabled(true);
        }

        /**
         * Cambiamos los parámetros del juego.
         *
         * @param ae evento de disparo.
         */
        public void actionPerformed(ActionEvent ae) {
            try {
                PanelConfiguracion datos = new PanelConfiguracion(configuracionSiguiente);
                int resultado = JOptionPane.showConfirmDialog(GUI.this,
                        datos,
                        "configuración",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (resultado == JOptionPane.OK_OPTION) {
                    configuracionSiguiente = new Configuracion(datos.getAncho(), datos.getAlto(),
                            datos.getNColores());
                    FicheroConfiguracion.guarda(configuracionSiguiente);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "configuración", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Presenta ayuda en línea.
     */
    private class HelpAction
            extends AbstractAction {
        HelpAction() {
            super("ayuda");
            setEnabled(true);
        }

        /**
         * Presenta ayuda en línea.
         *
         * @param ae evento de disparo.
         */
        public void actionPerformed(ActionEvent ae) {
            String msg = "<html><div style=\"width:15cm\">" +
                    "<p>El juego consiste en ir eliminando grupos de fichas contiguas del mismo color." +
                    "No se pueden eliminar fichas de un color aislado." +
                    "En cada jugada se consiguen n(n-1) puntos," +
                    "siendo n el número de fichas eliminadas." +
                    "<p>Se puede jugar con alguno de los siguientes objetivos:" +
                    "<ol>" +
                    "<li>alcanzar el máximo de puntos en el menor número de pasos" +
                    "<li>eliminar el máximo de fichas en el menor número de pasos" +
                    "</ol>" +
                    "</div></html>";
            JEditorPane text = new JEditorPane("text/html", msg);
            text.setEditable(false);
            JOptionPane.showMessageDialog(GUI.this,
                    text,
                    "ayuda",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Clic del ratón.
     */
    private class MyMouseListener
            extends MouseAdapter {

        /**
         * Clic del ratón.
         *
         * @param e evento de disparo.
         */
        public void mouseClicked(MouseEvent e) {
            requestFocusInWindow();

            if (e.getX() < nwx || e.getY() < nwy)
                return;
            int x = (e.getX() - MARGEN) / CELDA;
            int y = (e.getY() - MARGEN) / CELDA;

            if (x >= 0 && x < tablero.getAncho() && y >= 0 && y < tablero.getAlto()) {
                tablero.clic(x, y);
                repaint();
                LOGGER.info(tablero.getFirma());
                LOGGER.info(tablero.getMapaGrupos());
                presentaEstado();
            }
        }
    }
}
