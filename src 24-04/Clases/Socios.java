/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;


public class Socios {
    
    public Object[][] datos;    //Variable que alberga los datos de la BD
    public String excepcion = null;    // Variable para excepciones    
    
    public void Cargar(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT id_socio,apenom_socio,domicilio_socio,telefono_socio,estado_socio FROM socios ORDER BY id_socio");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][5];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 5; cols++) {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Cargar     Socios: " + ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }    
    }
    
    public Integer CodUltimo() {
        Integer num = 0;
        
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT MAX(id_socio) FROM socios");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if (registros > 0) {
            num = rs.getInt(1) + 1;
        } else {
            num = 1;
        }
        
        rs.close();
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  CodUltimo   Socios: "+ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }
        
        return num;
    }
    
    public Object[] Mostrar_datos(String id_socio){
        Object[] info = new String[14];
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT id_socio,dni_socio,apenom_socio,estadocivil_socio,nombre_conyugue_socio,"
                                + "ocupacion_socio,domicilio_socio,nombre_padre_socio,nombre_madre_socio,fecha_nacimiento_socio,"
                                + "lugar_nacimiento_socio,telefono_socio,presentado1_socio,presentado2_socio,estado_socio,"
                                + "monto_socio,cobrar_desde_socio "
                                + "FROM socios "
                                + "WHERE id_socio = '"+id_socio+"'");
            rs.last();
            Integer registros = rs.getRow();
            rs.first();
            info = new Object[45];
            
            if(registros > 0){
                //      ID_SOCIO                   DNI_SOCIO                   APENOM_SOCIO              ESTADO_CIVIL_SOCIO         NOMBRE_CONYUGUE_SOCIO
                info[1] = rs.getObject(1);   info[2] = rs.getObject(2);  info[3] = rs.getObject(3);  info[4] = rs.getObject(4);  info[5] = rs.getObject(5);         
                //      OCUPACION_SOCIO            DOMICILIO_SOCIO          NOMBRE_PADRE_SOCIO          NOMBRE_MADRE_SOCIO          FECHA_NACIMIENTO_SOCIO
                info[6] = rs.getObject(6);   info[7] = rs.getObject(7);  info[8] = rs.getObject(8);  info[9] = rs.getObject(9);  info[10] = rs.getObject(10);
                //      LUGAR_NACIMIENTO_SOCIO      TELEFONO_SOCIO          PRESENTADO1_SOCIO           PRESENTADO2_SOCIO           ESTADO_SOCIO
                info[11] = rs.getObject(11);    info[12] = rs.getObject(12);    info[13] = rs.getObject(13);    info[14] = rs.getObject(14);    info[15] = rs.getObject(15);
                //      MONTO_SOCIO                 COBRAR_DESDE_SOCIO
                info[16] = rs.getObject(16);    info[17] = rs.getObject(17);

            }
            
            for(int a = 0; a < info.length; a++){
                if(info[a]==null){
                    info[a] = "";
                }
            }
            
            rs.close();
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Cargar     Socios: " + ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }   
        return info;
    }
    
    public void FiltrarXApeNom(String buscar){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT id_socio,apenom_socio,domicilio_socio,telefono_socio,estado_socio FROM socios WHERE apenom_socio LIKE '%"+buscar+"%' OR id_socio LIKE '%"+buscar+"%'");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][5];
                
        rs.first();
        
        if(registros > 0){
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 5; cols++) {
                    datos[fila][cols] = rs.getObject(cols + 1);
                    //System.out.println(datos[fila][cols]);
                }
            rs.next();
            } 
        }
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  FiltrarXApeNom   Socios: "+ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }
    }
    
    public void Guardar(String cod_socio,String dni_socio,String apenom_socio,String estadocivil_socio,String conyugue_socio,String ocupacion_socio,String domicilio_socio,
                    String padre_socio,String madre_socio,String fecha_socio,String lugar_nac_socio,String telefono_socio,String presentado1_socio,String presentado2_socio,
                    String monto_socio,String desde_cobro_socio){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO socios (id_socio,dni_socio,apenom_socio,estadocivil_socio,nombre_conyugue_socio,ocupacion_socio,domicilio_socio,"
                        + "nombre_padre_socio,nombre_madre_socio,fecha_nacimiento_socio,lugar_nacimiento_socio,telefono_socio,presentado1_socio,presentado2_socio,"
                        + "estado_socio,monto_socio,cobrar_desde_socio) "
                        + "VALUES "
                        + "('"+cod_socio+"','"+dni_socio+"','"+apenom_socio+"','"+estadocivil_socio+"','"+conyugue_socio+"','"+ocupacion_socio+"','"+domicilio_socio+"',"
                        + "'"+padre_socio+"','"+madre_socio+"','"+fecha_socio+"','"+lugar_nac_socio+"','"+telefono_socio+"','"+presentado1_socio+"','"+presentado2_socio+"',"
                        + "'A','"+monto_socio+"','"+desde_cobro_socio+"')");
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  Guardar   Socios: "+ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }
    }
    
    public void Actualizar(String cod_viejo,
                    String cod_socio,String dni_socio,String apenom_socio,String estadocivil_socio,String conyugue_socio,String ocupacion_socio,String domicilio_socio,
                    String padre_socio,String madre_socio,String fecha_socio,String lugar_nac_socio,String telefono_socio,String presentado1_socio,String presentado2_socio,
                    String monto_socio,String desde_cobro_socio){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE socios SET "
                + "id_socio = '"+cod_socio+"', dni_socio = '"+dni_socio+"', apenom_socio = '"+apenom_socio+"', estadocivil_socio = '"+estadocivil_socio+"', nombre_conyugue_socio = '"+conyugue_socio+"', ocupacion_socio = '"+ocupacion_socio+"', domicilio_socio = '"+domicilio_socio+"', "
                + "nombre_padre_socio = '"+padre_socio+"', nombre_madre_socio = '"+madre_socio+"', fecha_nacimiento_socio = '"+fecha_socio+"', lugar_nacimiento_socio = '"+lugar_nac_socio+"', telefono_socio = '"+telefono_socio+"', presentado1_socio = '"+presentado1_socio+"', presentado2_socio = '"+presentado2_socio+"', "
                + "monto_socio = '"+monto_socio+"', cobrar_desde_socio = '"+desde_cobro_socio+"' "
                + "WHERE id_socio = '"+cod_viejo+"' ");
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  Actualizar   Socios: "+ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }
        
    }
    
    public void darAlta(String cod_socio){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE socios SET estado_socio='A' WHERE id_socio = '"+cod_socio+"' AND estado_socio = 'B'");
              
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  darAlta   Socios: "+ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }
    }
    
    public void darBaja(String cod_socio){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE socios SET estado_socio='B' WHERE id_socio = '"+cod_socio+"' AND estado_socio = 'A'");
              
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  darBaja   Socios: "+ex);
            excepcion = "Se han producido excepciones  Guardar   Socios: "+ex;
        }
    }
    
}
