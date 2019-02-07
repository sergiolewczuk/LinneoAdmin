package Utilidades;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
/**
 *
 * @author Jason
 */
public class Class_selectpersonalizado {
    public static ResultSet rs_personalizado=null;

    public static void main(String tabla, String columnas_sql) {
        
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs_personalizado=st.executeQuery("select " + columnas_sql + " FROM " + tabla);
        rs_personalizado.next();
        
        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }

    }
}
