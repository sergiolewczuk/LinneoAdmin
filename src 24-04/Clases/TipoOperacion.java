/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author jason
 */
public class TipoOperacion {
    public static Object[][] Operaciones(String sector, int tipo) {
        Object[][] datos = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT nombre, num_cuenta FROM operaciones_tipos WHERE sector = '"+sector+"' AND entrada = "+tipo+"");

            rs.last();

            int registros = rs.getRow();
            
            rs.first();
            
            datos = new Object[registros][2];
            
            for (int a = 0; a < registros; a++) {
                datos[a][0] = rs.getString(1);
                datos[a][1] = rs.getString(2);
                rs.next();
            }

            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
        return datos;
    }
}
