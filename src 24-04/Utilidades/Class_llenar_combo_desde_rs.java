package Utilidades;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author Jason
 */
public class Class_llenar_combo_desde_rs {
    static public void main (String tabla, String columna, JComboBox combobox) {
        Class_selectpersonalizado.main(tabla, columna);
        combobox.removeAllItems();
        try {
            Class_selectpersonalizado.rs_personalizado.last();
            Integer cantidad = Class_selectpersonalizado.rs_personalizado.getRow();
            Class_selectpersonalizado.rs_personalizado.first();
            
            for (Integer i = 0; i < cantidad; i++) { 
            combobox.addItem(Class_makeobj.main(Class_selectpersonalizado.rs_personalizado.getString(columna)));
            Class_selectpersonalizado.rs_personalizado.next();
            }
            Class_selectpersonalizado.rs_personalizado.close();
            //combobox.setSelectedIndex(0);
        } catch (SQLException ex) {
            Logger.getLogger(Class_llenar_combo_desde_rs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
