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
public class Usuarios {
    
    public Object[][] datos; //Variable que alberga los datos de la BD
    
    public void Cargar() {
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT * FROM usuarios");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][3];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 3; cols++) {
                    datos[fila][cols] = rs.getObject(cols + 1);
                    //System.out.println(datos[fila][cols]);
                }
                rs.next();
            }
            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
    
    public static String getTipo(String id) {
        String tipo = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT tipo FROM usuarios WHERE id = " + id);
            rs.next();

            tipo = rs.getString(1);

            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
        return tipo;
    }
    
    public static int[] getUsuario(String nombre, String pass) {
        int[] var = {0,0};
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT id FROM usuarios WHERE nombre = '" + nombre + "' AND pass = '"+pass+"'");

            rs.next();
            int reg = rs.getRow();
            
            if (reg == 1) {
                var[0] = 1;
                var[1] = rs.getInt(1);
            }

            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
        return var;
    }
    
    public void Insertar(String usuario, String tipo, String pass) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO usuarios (nombre, tipo, pass) VALUES ('"+usuario+"','"+tipo+"','"+pass+"')");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public static void CambiarPass(String id, String pass_nueva) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE usuarios SET pass = '"+pass_nueva+"' WHERE id = " + id);

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public void DarBaja(String id) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE usuarios SET estado = 1 WHERE id = "+id+")");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
}
