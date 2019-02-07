/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;


public class TiposPagos {
    
    public Object[][] datos; //Variable que alberga los datos de la BD
    public Object[] datos2;
     
    public Object[] cargar_cuentas() {
        Object[] info = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT tipo_pago,num_cuenta FROM tipo_pago_global");
            rs.last();

            Integer registros = rs.getRow();

            info = new Object[13];
            
            rs.first();

            for (int fila = 0; fila < registros; fila++) {

                switch (rs.getString(1)) {
                    case "Efectivo":

                        info[0] = rs.getString(2);

                        break;
                    case "Débito":

                        info[1] = rs.getString(2);

                        break;
                    case "Crédito":

                        info[2] = rs.getString(2);

                        break;
                    case "Cheque":

                        info[3] = rs.getString(2);

                        break;
                    case "Tranf. Bancaria":

                        info[4] = rs.getString(2);

                        break;
                    case "Depo. Bancario":

                        info[5] = rs.getString(2);

                        break;
                        
                    case "Titulo Superior":

                        info[6] = rs.getString(2);

                        break;
                    case "Libreta":

                        info[7] = rs.getString(2);

                        break;
                    case "Analitico":

                        info[8] = rs.getString(2);

                        break;
                    case "Examen Escrito Lengua":

                        info[9] = rs.getString(2);

                        break;
                    case "Examen Oral Lengua":

                        info[10] = rs.getString(2);

                        break;
                    case "Titulo Secundaria":

                        info[11] = rs.getString(2);

                        break;
                    case "Distintivos":

                        info[12] = rs.getString(2);

                        break;
                }
                
                rs.next();
            }

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    cargar_cuentas     TiposPagos: " + ex);
        }
        return info;
    }
    
    
    public void Insert(String tipo_pago, String num_cuenta) {
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        
            switch (tipo_pago) {
                case "Efectivo":
                    
                    st.executeUpdate("INSERT INTO tipo_pago_global (tipo_pago,num_cuenta) VALUES ('Efectivo','"+num_cuenta+"')");
                    
                    break;
                case "Débito":
                    
                    st.executeUpdate("UPDATE tipo_pago_global SET num_cuenta = '"+num_cuenta+"' WHERE tipo_pago = 'Débito'");
                    
                    break;
                case "Crédito":
                    
                    st.executeUpdate("INSERT INTO tipo_pago_global (tipo_pago,num_cuenta) VALUES ('Crédito','"+num_cuenta+"')");
                    
                    break;
                case "Cheque":
                    
                    st.executeUpdate("INSERT INTO tipo_pago_global (tipo_pago,num_cuenta) VALUES ('Cheque','"+num_cuenta+"')");
                    
                    break;
                case "Tranf. Bancaria":
                    
                    st.executeUpdate("INSERT INTO tipo_pago_global (tipo_pago,num_cuenta) VALUES ('Tranf. Bancaria','"+num_cuenta+"')");
                    
                    break;
                case "Depo. Bancaria":
                    
                    st.executeUpdate("INSERT INTO tipo_pago_global (tipo_pago,num_cuenta) VALUES ('Depo. Bancaria','"+num_cuenta+"')");
                    
                    break;
            }                
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Insert     TiposPagos: " + ex);
        }
    }
    
    public void Update(String tipo_pago, String num_cuenta) {
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            boolean permitir = true;
            
            if (num_cuenta.isEmpty()) {
                num_cuenta = "NULL";
                
                // SELECCIONAR SI EN EL DIA HUBO OPERACIONES CON LA CUENTA ANTES DE PONER NULL
                rs=st.executeQuery("SELECT fecha_cierre FROM operaciones \n"
                        + "WHERE fecha = CURDATE() AND fecha_cierre IS NOT NULL");
                rs.last();
                
                if (rs.getRow() > 0) {
                    permitir = true;
                } else {
                    permitir = false;
                }
                
                rs.close();
            }
            
            
            if (permitir) {
                switch (tipo_pago) {
                    case "Efectivo":

                        st.executeUpdate("UPDATE tipo_pago_global SET num_cuenta = " + num_cuenta + " WHERE tipo_pago = 'Efectivo'");

                        break;
                    case "Débito":

                        st.executeUpdate("UPDATE tipo_pago_global SET num_cuenta = " + num_cuenta + " WHERE tipo_pago = 'Débito'");

                        break;
                    case "Crédito":

                        st.executeUpdate("UPDATE tipo_pago_global SET num_cuenta = " + num_cuenta + " WHERE tipo_pago = 'Crédito'");

                        break;
                    case "Cheque":

                        st.executeUpdate("UPDATE tipo_pago_global SET num_cuenta = " + num_cuenta + " WHERE tipo_pago = 'Cheque'");

                        break;
                    case "Tranf. Bancaria":

                        st.executeUpdate("UPDATE tipo_pago_global SET num_cuenta = " + num_cuenta + " WHERE tipo_pago = 'Tranf. Bancaria'");

                        break;
                    case "Depo. Bancaria":

                        st.executeUpdate("UPDATE tipo_pago_global SET num_cuenta = " + num_cuenta + " WHERE tipo_pago = 'Depo. Bancaria'");

                        break;
                }    
            }
                            
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Update     TiposPagos: " + ex);
        }
    }
    
    public void CargarCombos(){
        Object[] info = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT tipo_pago FROM tipo_pago_global");
            rs.last();

            Integer registros = rs.getRow();

            datos2 = new Object[registros];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                datos2[fila] = rs.getObject(1);
                rs.next();
            }
            rs.close();
            
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarCombos     TiposPagos: " + ex);
        }
    }
    
    public static Boolean ComprobarExistente (String tipo_pago) {
        Boolean existe = false;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT num_cuenta FROM tipo_pago_global WHERE tipo_pago = '"+tipo_pago+"'");
            rs.last();
            
            if (rs.getRow() > 0) {
                if (rs.getString(1).equals("")) {
                    existe = false;
                } else {
                    existe = true;
                }
            }
            
            rs.close();
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    ComprobarExistente     TiposPagos: " + ex);
        } 
        return existe;
    }
    
}
