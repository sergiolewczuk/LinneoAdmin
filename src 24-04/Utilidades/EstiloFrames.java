package Utilidades;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Jason
 */
public class EstiloFrames {
    
    //static Integer dragX = null;
    //static Integer dragY;
    
    public static void main(JFrame frame) {
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setIconImage(new ImageIcon(frame.getClass().getResource("/Imagenes/icono.png")).getImage());
    }
}
