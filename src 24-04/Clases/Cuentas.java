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
public class Cuentas {
    
    public Object[][] datos; //Variable que alberga los datos de la BD
    
    public void Cargar(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT * FROM cuentas WHERE nivel_cuenta = 4");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][4];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 4; cols++) {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                rs.next();
            }
            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
    
    public static String[] DatosCuenta(String num_cuenta){
       String[] datos = null; 
        try {
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
        rs=st.executeQuery("SELECT num_cuenta,nombre,suma_en_cta,nivel_cuenta FROM cuentas WHERE num_cuenta = " + num_cuenta);
        rs.last();
        
        datos = new String[4];
        
        if (rs.getRow() > 0) {
            datos[0] = rs.getString(1);
            datos[1] = rs.getString(2);
            datos[2] = rs.getString(3);
            datos[3] = rs.getString(4);
        }    
        
        rs.close();
        
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones        DatosCuenta     Cuentas: " + ex);
        }
        return datos;
    }
    
    public String cuenta_encontrada = "";
    public Boolean ComprobarExistente(String num_cuenta){
        Boolean encontro = false;
        if (!"".equals(num_cuenta)) {
            
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT nombre FROM cuentas WHERE num_cuenta = " + num_cuenta);
        rs.last();
        
        Integer registros = rs.getRow();
        rs.first();
        
        if (registros > 0) {
            encontro = true;
            cuenta_encontrada = rs.getString(1);
        }
        
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        
        }
        return encontro;
    }
    
    public void FiltrarCuenta(String num_o_cuenta){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT num_cuenta,nombre,suma_en_cta,nivel_cuenta FROM cuentas WHERE num_cuenta LIKE '%"+ num_o_cuenta+"%' or nombre LIKE '%"+ num_o_cuenta+"%'");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][4];
        
        rs.first();
        
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 4; cols++) {
                datos[fila][cols] = rs.getObject(cols + 1);
                //System.out.println(datos[fila][cols]);
            }
        rs.next();
        }    
                
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }        
    }
    
    public void Insertar(String num, String nombre, String suma, String nivel) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO cuentas (num_cuenta, nombre, suma_en_cta, nivel_cuenta) VALUES ("+num+",'"+nombre+"',"+suma+",'"+nivel+"')");

        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public void Modificar(String num, String nombre, String suma, String nivel, String num_cuenta_viejo) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE cuentas SET num_cuenta = "+num+", nombre = '"+nombre+"', suma_en_cta = "+suma+", nivel_cuenta = '"+nivel+"' WHERE num_cuenta = "+num_cuenta_viejo+"");

        }catch(Exception ex){
            System.out.println("Se han producido excepciones        Modificar       Cuotas: "+ex);
        }
    }
    
    public static String[][] CuentasCuotasDevengamiento(String nivel) {
        String[][] cuentas = null;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT num_cuenta,haber FROM niveles_cuentas_cuotas WHERE nivel = '"+nivel+"' AND devengamiento = 1");
        rs.last();
        
        Integer registros = rs.getRow();
        cuentas = new String[registros][2];

        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 2; cols++) {
                cuentas[fila][cols] = rs.getString(cols + 1);
                //System.out.println(datos[fila][cols]);
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        
        return cuentas;
    }
}
