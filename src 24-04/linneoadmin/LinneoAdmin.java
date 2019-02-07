/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linneoadmin;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jason
 */
public class LinneoAdmin {

    public static double Redondear(double numero, int digitos) {
        int cifras = (int) Math.pow(10, digitos);
        return Math.rint(numero * cifras) / cifras;
    }
    
    public static void desactivarAutoCommit() {
        try {
            conexion.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(LinneoAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void activarAutoCommit() {
        try {
            conexion.rollback();
            conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(LinneoAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void commiteo() {
        try {
            conexion.commit();
        } catch (SQLException ex) {
            Logger.getLogger(LinneoAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void rollback(Savepoint savepoint) {
        try {
            conexion.rollback(savepoint);
        } catch (SQLException ex) {
            Logger.getLogger(LinneoAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String direccion;
    public static String puerto;
    public static String us;
    public static String pass;
    public static String nombre_bd;
    
    public static Connection conexion = null;
    
    public static Color color2 = new Color(204,255,255);
    public static Color color1 = new Color(204,255,204);
    
    public static void DatosActuales(){
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT DATE_FORMAT(CURDATE(), '%d-%m-%Y'), CURTIME(), CURDATE()");
            rs.last();
            
            Pantallas.Frames.Principal.fecha_actual = rs.getString(1);
            Pantallas.Frames.Principal.hora_actual = rs.getString(2);
            Pantallas.Frames.Principal.fecha_para_bd = rs.getString(3);
            
            
        }catch (Exception ex){
            
        }
    }
    
    public static String NombreMes(int mes){
        String nombre_mes = null;
        
        if(mes == 1){
            nombre_mes = "Enero";
        } else if (mes == 2){
            nombre_mes = "Febrero";
        } else if (mes == 3){
            nombre_mes = "Marzo";
        } else if (mes == 4){
            nombre_mes = "Abril";
        } else if (mes == 5){
            nombre_mes = "Mayo";
        } else if (mes == 6){
            nombre_mes = "Junio";
        } else if (mes == 7){
            nombre_mes = "Julio";
        } else if (mes == 8){
            nombre_mes = "Agosto";
        } else if (mes == 9){
            nombre_mes = "Septiembre";
        } else if (mes == 10){
            nombre_mes = "Octubre";
        } else if (mes == 11){
            nombre_mes = "Noviembre";
        } else if (mes == 12){
            nombre_mes = "Diciembre";
        }
        
        return nombre_mes;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // TODO code application logic here
            
            //direccion = "192.168.0.20";
            direccion = "localhost";
            puerto = "3306";
            //String bd = jTextField_bd.getText();
            us = "root";
            pass = "";
            nombre_bd = "Gestion";
            
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conexion=DriverManager.getConnection("jdbc:mysql://"+direccion+":"+puerto+"/"+nombre_bd+"?zeroDateTimeBehavior=convertToNull", us, pass);
                conexion.setAutoCommit(true);
                
                /*----------------------------------------------*/
                /*ResultSet rs;
                Statement st1 = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement st2 = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                
                rs = st1.executeQuery("SELECT fecha, hora, detalle, num_cuenta, \n"
                        + "IF(tipo_cuenta = 'A+' or tipo_cuenta = 'P-' or tipo_cuenta = 'RN+' or tipo_cuenta = 'RP-' or tipo_cuenta = 'PN-',cantidad,NULL) as 'debe',\n"
                        + "IF(tipo_cuenta = 'A-' or tipo_cuenta = 'P+' or tipo_cuenta = 'RN-' or tipo_cuenta = 'RP+' or tipo_cuenta = 'PN+',cantidad,NULL) as 'haber',\n"
                        + "sector,numero_recibo\n"
                        + "\n"
                        + " FROM\n"
                        + " educando.movimientos JOIN gestion.cuentas \n"
                        + " ON (educando.movimientos.cuenta = gestion.cuentas.nombre) ORDER BY fecha,hora");
                rs.last();
                
                Integer reg = rs.getRow();
                rs.first();
                
                Calendar cal = Calendar.getInstance();
                
                Integer num_asiento = 0;
                Object fecha;
                Time hora;
                Object cuenta;
                Object detalle;
                Object debe;
                Object haber;
                Object sector;
                Object recibo;
                
                long duracion;
                long duracion_vieja = 0;
                
                for (int a = 0; a < reg; a++) {
                    fecha = rs.getObject(1);
                    hora = rs.getTime(2);
                    
                    cal.setTime(hora);
                    duracion = cal.getTimeInMillis();
                    
                    if (!(duracion == duracion_vieja || duracion == (duracion_vieja) + 1000 || duracion == (duracion_vieja) + 2000)) {
                        num_asiento++;
                        duracion_vieja = duracion;
                    }
                    cuenta = rs.getObject(4);
                    detalle = rs.getObject(3);
                    debe = rs.getObject(5);
                    haber = rs.getObject(6);
                    sector = rs.getObject(7);
                    recibo = rs.getObject(8);
                    
                    //System.out.println("INSERT INTO asientos (id_asiento,fecha,hora,num_cuenta,detalle,debe,haber,sector,num_recibo) VALUES ("+num_asiento+",'"+fecha+"','"+hora+"',"+cuenta+",'"+detalle+"',"+debe+","+haber+",'"+sector+"','"+recibo+"')");
                    st2.executeUpdate("INSERT INTO asientos (id_asiento,fecha,hora,num_cuenta,detalle,debe,haber,sector,num_recibo) VALUES ("+num_asiento+",'"+fecha+"','"+hora+"',"+cuenta+",'"+detalle+"',"+debe+","+haber+",'"+sector+"','"+recibo+"')");
                    st2.clearBatch();
                    rs.next();
                }
                rs.close();*/
                /*----------------------------------------------*/
                
            } catch (SQLException ex) {
                if (ex.toString().contains("link")) {
                    JOptionPane.showMessageDialog(null, "El servidor MySQL no se está ejecutando.\nEl Programa se cerrará.");
                    System.exit(0);
                } else {
                Logger.getLogger(LinneoAdmin.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "No existe ninguna Base de Datos que el Sistema pueda usar.\nEl Sistema empezará a crear la Base de Datos cuando presione el boton \"Aceptar\".\nEl proceso demorará unos segundos.");
                }
            }
            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LinneoAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            //Pantallas.Frames.Principal.main(null);
            Dialogs.Login.main(null);
    }
    
}
