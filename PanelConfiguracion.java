package colorjunction.conf;

import labelleditem.LabelledItemPanel;

import javax.swing.*;

/**
 * Panel interactivo para ajustar los parámetros de configuración.
 *
 * @author Javier L—pez Medina
 * @version 1.0
 */
public class PanelConfiguracion
        extends LabelledItemPanel {
    private JFormattedTextField fieldAncho;
    private JFormattedTextField fieldAlto;
    private JFormattedTextField fieldColores;

    /**
     * Presenta un panel con las opciones que podemos elegir.
     *
     * @param configuracion par‡metros de la configuraci—n.
     */
    public PanelConfiguracion(Configuracion configuracion) {
        fieldAncho = new JFormattedTextField(configuracion.getAncho());
        addItem("ancho: ", fieldAncho);

        fieldAlto = new JFormattedTextField(configuracion.getAlto());
        addItem("alto: ", fieldAlto);

        fieldColores = new JFormattedTextField(configuracion.getNColores());
        addItem("colores: ", fieldColores);
    }

    /**
     * @return ancho elegido por el usuario.
     */
    public int getAncho() {
        return (Integer) fieldAncho.getValue();
    }

    /**
     * @return alto elegido por el usuario.
     */
    public int getAlto() {
        return (Integer) fieldAlto.getValue();
    }

    /**
     * @return nœmero de colores elegido por el usuario.
     */
    public int getNColores() {
        return (Integer) fieldColores.getValue();
    }
}
