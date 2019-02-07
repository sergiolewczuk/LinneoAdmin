/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;


public class Personas {
    
    public static String Insertar(String dni, String apellidos, String nombres, String direccion) {
        String id_persona = null;
        try{
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO personas_terceras (dni, apellidos, nombres, direccion) VALUES ('"+dni+"','"+apellidos+"','"+nombres+"','"+direccion+"')");
        
        rs=st.executeQuery("SELECT MAX(persona) FROM personas_terceras");
        rs.last();
        
        id_persona = rs.getString(1);
        
        rs.close();

        }catch(Exception ex){
            System.out.println("Se han producido excepciones        Insertar        Personas: "+ex);
        }
        return id_persona;
    }
    
}
