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
 * @author Sergio
 */
public class Variables {
    public static String CargarNumRecibo(){
        String dato = null;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT valor FROM variables WHERE nombre = 'recibo'");

        rs.first();
        
        dato = rs.getString(1);
        
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        
        return dato;
    }
    
    public static void AumentarNumRecibo() {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        String num = CargarNumRecibo();
        Integer serie = Integer.valueOf(num.split("-")[0]);
        Integer numero = Integer.valueOf(num.split("-")[1]);
        
        String Sserie = num.split("-")[0];
        
        numero++;
        String Snumero = numero.toString();
        
        if (numero.toString().length() < 8) {
            //no estan los 8 numeros
            //tengo q agregar 0 a la izquierda
            int faltantes = 8 - numero.toString().length();
            for (int a=0; a < faltantes; a++) {
                Snumero = "0" + Snumero;
            }
        } else {
            //aca aumenta serie
            if (numero > 99999999) {
                //aumenta serie
                serie++;
                Sserie = serie.toString();
                
                int faltantes = 4 - serie.toString().length();
                for (int a = 0; a < faltantes; a++) {
                    Sserie = "0" + Sserie;
                }
                
                //inicializar numeros
                Snumero = "00000001";
            }
        }
        
        num = Sserie + "-" + Snumero;
        
        st.executeUpdate("UPDATE variables SET valor = '"+num+"' WHERE nombre = 'recibo'");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public static void ActualizarNumRecibo(String serie, String numero) {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate("UPDATE variables SET valor = '" + serie + "-" + numero + "' WHERE nombre = 'recibo'");

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }

    }
    
    public static void AumentarNumSerie() {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            String num = CargarNumRecibo();
            Integer serie = Integer.valueOf(num.split("-")[0]);
            serie++;
            
            String Sserie = serie.toString();
            int faltantes = 4 - serie.toString().length();
            for (int a = 0; a < faltantes; a++) {
                Sserie = "0" + Sserie;
            }

            st.executeUpdate("UPDATE variables SET valor = '" + Sserie + "00000001' WHERE nombre = 'recibo'");

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }

    }
}
