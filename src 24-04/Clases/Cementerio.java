/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;


public class Cementerio {
    
    public Object[][] datos; //Variable que alberga los datos de la BD
    
    public void Cargar(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT numero_espacio,bis_espacio,zona_espacio,apenom_espacio,apenom_responsable,telefono_responsable,direccion_responsable "
                                + "FROM espacios JOIN espacios_responsables ON espacios.dni_responsable = espacios_responsables.dni_responsable "
                                + "ORDER BY zona_espacio,numero_espacio");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][7];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                datos[fila][0] = rs.getObject(1);
                if("0".equals(rs.getObject(2).toString())){
                    datos[fila][1] = "";
                }else{
                    datos[fila][1] = "BIS";
                }
                datos[fila][2] = rs.getObject(3);
                datos[fila][3] = rs.getObject(4);
                datos[fila][4] = rs.getObject(5);
                datos[fila][5] = rs.getObject(6);
                datos[fila][6] = rs.getObject(7);
                
                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Cargar     Cementerio: " + ex);
        }    
    }
    
    public Object[] CargarDatos_espacios(String num, String bis, String zona){
        Object[] info = null;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
            rs = st.executeQuery("SELECT numero_espacio,bis_espacio,zona_espacio,dni_difunto,num_socio_espacio,"
                    + "apenom_espacio,fecha_nacimiento_espacio,fecha_fallecimiento_espacio,estado_civil_espacio,nacionalidad_espacio,monto_difunto,estado_espacio,anio_espacio"
                    + "espacios.dni_responsable,apenom_responsable,direccion_responsable,telefono_responsable,estado_responsable "
                    + "FROM espacios JOIN espacios_responsables ON espacios.dni_responsable = espacios_responsables.dni_responsable "
                    + "WHERE numero_espacio = '" + num + "' AND bis_espacio = '" + bis + "' AND zona_espacio = '" + zona + "' ");
            
            rs.last();
            
            Integer registros = rs.getRow();
            
            rs.first();
            
            info = new Object[20];

            if (registros > 0) {

                info[0] = rs.getObject(1);  // numero_difunto
                if(rs.getObject(2) == "0"){ // bis_difunto
                    info[1] = "No";  
                }else{
                    info[1] = "Si";
                }
                info[2] = rs.getObject(3);  // zona_difunto
                info[3] = rs.getObject(4);  // dni_difunto
                info[4] = rs.getObject(5);  // num_socio_difunto
                
                info[5] = rs.getObject(6);  // apenom_difunto
                info[6] = rs.getObject(7);  // fecha_nacimiento_difunto
                info[7] = rs.getObject(8);  // fecha_fallecimiento_difunto
                info[8] = rs.getObject(9);  // estado_civil_difunto
                info[9] = rs.getObject(10);  // nacionalidad_difunto
                info[10] = rs.getObject(11);  // monto_difunto
                info[11] = rs.getObject(12);  // estado_difunto
                
                
                info[12] = rs.getObject(13);  // dni_responsable
                info[13] = rs.getObject(14);  // apenom_responsable
                info[14] = rs.getObject(15);  // direccion_responsable
                info[15] = rs.getObject(16);  // telefono_responsable
                info[16] = rs.getObject(17);  // estado_responsable

                info[17] = rs.getObject(18);  // anio_espacio
            }

            for (int a = 0; a < info.length; a++) {
                if (info[a] == null) {
                    info[a] = "";
                }
            }

            rs.close();


        }catch(Exception ex){
            System.out.println("Se han producido excepciones  CargarDatos_espacios   Cementerio: "+ex);
        }
        return info;
    }
    
    public void Cargar_responsables(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT dni_responsable,apenom_responsable,direccion_responsable,telefono_responsable FROM espacios_responsables ORDER BY apenom_responsable");
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

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Cargar_responsables     Cementerio: " + ex);
        }    
    }
    
    public void FiltrarCuenta(String responsable){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT dni_responsable,apenom_responsable,direccion_responsable FROM espacios_responsables WHERE dni_responsable LIKE '%"+ responsable +"%' or apenom_responsable LIKE '%"+ responsable+"%' ORDER BY apenom_responsable");
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
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    FiltrarCuenta     Cementerio: " + ex);
        }        
    }
    
    public void FiltrarXApeNom(String buscar){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT numero_espacio,bis_espacio,zona_espacio,apenom_espacio,apenom_responsable,telefono_responsable,direccion_responsable "
                    + "FROM espacios JOIN espacios_responsables ON espacios.dni_responsable = espacios_responsables.dni_responsable "
                    + "WHERE apenom_responsable LIKE '%" + buscar + "%' OR apenom_espacio LIKE '%" + buscar + "%' "
                    + "ORDER BY zona_espacio,numero_espacio");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][7];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                datos[fila][0] = rs.getObject(1);
                if ("0".equals(rs.getObject(2).toString())) {
                    datos[fila][1] = "";
                } else {
                    datos[fila][1] = "BIS";
                }
                datos[fila][2] = rs.getObject(3);
                datos[fila][3] = rs.getObject(4);
                datos[fila][4] = rs.getObject(5);
                datos[fila][5] = rs.getObject(6);
                datos[fila][6] = rs.getObject(7);

                rs.next();
            }
            rs.close();

        }catch(Exception ex){
            System.out.println("Se han producido excepciones  FiltrarXApeNom   Cementerio: "+ex);
        }
    }
    
    public String[] responsable_encontrado = null;
    public Boolean ComprobarExistente(String responsable){
        Boolean encontro = false;
        if (!"".equals(responsable)) {
            
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT dni_responsable,apenom_responsable,direccion_responsable,telefono_responsable,estado_responsable "
                            + "FROM espacios_responsables "
                            + "WHERE dni_responsable "
                            + "LIKE '%"+responsable+"%' OR apenom_responsable LIKE '%"+responsable+"%'");
        rs.last();
        
        Integer registros = rs.getRow();
        rs.first();
        
        responsable_encontrado = new String[6];
        
        if (registros > 0) {
            encontro = true;
            responsable_encontrado[1] = rs.getString(1);
            responsable_encontrado[2] = rs.getString(2);
            responsable_encontrado[3] = rs.getString(3);
            responsable_encontrado[4] = rs.getString(4);
            responsable_encontrado[5] = rs.getString(5);
            
        }
        
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  ComprobarExistente   Cementerio: "+ex);
        }
        
        }
        return encontro;
    }
    
    public Boolean ComprobarResponsableExistente(String responsable){
        Boolean encontro = false;
        if (!"".equals(responsable)) {
            
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT dni_responsable "
                            + "FROM espacios_responsables "
                            + "WHERE dni_responsable = '"+responsable+"'");
        rs.last();
        
        Integer registros = rs.getRow();
        rs.first();
        
        if (registros > 0) {
            encontro = true;
        }
        
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  ComprobarResponsableExistente   Cementerio: "+ex);
        }
        
        }
        return encontro;
    }
    
    public void InsertarEspacio(String numero_difunto, String combo_bis, String zona_difunto, String dni_difunto, String num_socio_espacio, String apenom_difunto,
                                String fecha_nacimiento, String fecha_fallecimiento, String estadocivil_difunto, String nacionalidad_difunto, String monto_cobrar_espacio, 
                                String combo_estado_espacio, String dni_responsable, String anio_comenzar_cobrar){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("INSERT INTO espacios "
                            + "(numero_espacio,bis_espacio,zona_espacio,dni_responsable,dni_difunto,num_socio_espacio,apenom_espacio,"
                            + "fecha_nacimiento_espacio,fecha_fallecimiento_espacio,estado_civil_espacio,nacionalidad_espacio,monto_espacio,"
                            + "estado_espacio,anio_espacio) "
                            + "VALUES "
                            + "('"+numero_difunto+"','"+combo_bis+"','"+zona_difunto+"','"+dni_responsable+"','"+dni_difunto+"','"+num_socio_espacio+"','"+apenom_difunto+"',"
                            + "'"+fecha_nacimiento+"','"+fecha_fallecimiento+"','"+estadocivil_difunto+"','"+nacionalidad_difunto+"','"+monto_cobrar_espacio+"',"
                            + "'"+combo_estado_espacio+"','A','"+anio_comenzar_cobrar+"')");
        rs.last();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  InsertarEspacio   Cementerio: "+ex);
        }
    }
    
    
    public void InsertarResponsable(String dni, String ape_nom, String direccion, String telefono){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("INSERT INTO espacios_responsables "
                            + "(dni_responsable,apenom_responsable,direccion_responsable,telefono_responsable,estado_responsable) "
                            + "VALUES "
                            + "('"+dni+"','"+ape_nom+"','"+direccion+"','"+telefono+"','A')");
        rs.last();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  InsertarResponsable   Cementerio: "+ex);
        }
    }
    
    public void UpdatearResponsable(String dni_viejo, String dni, String ape_nom, String direccion, String telefono){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("");
        rs.last();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  UpdatearResponsable   Cementerio: "+ex);
        }
    }
    
    public String[] MostrarDatos_Responsable(String dni){
        String[] info = null;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
            rs = st.executeQuery("SELECT dni_responsable,apenom_responsable,direccion_responsable,telefono_responsable,estado_responsable "
                    + "FROM espacios_responsables "
                    + "WHERE dni_responsable = '" + dni + "'");
            
            rs.last();
            
            Integer registros = rs.getRow();
            
            rs.first();
            
            info = new String[6];

            if (registros > 0) {

                info[1] = rs.getString(1);  // DNI_RESPONSABLE
                info[2] = rs.getString(2);  // APENOM_RESPONSABLE
                info[3] = rs.getString(3);  // DIRECCION_RESPONSABLE
                info[4] = rs.getString(4);  // TELEFONO_RESPONSABLE
                info[5] = rs.getString(5);  // ESTADO_RESPONSABLE

            }

            for (int a = 0; a < info.length; a++) {
                if (info[a] == null) {
                    info[a] = "";
                }
            }

            rs.close();


        }catch(Exception ex){
            System.out.println("Se han producido excepciones  MostrarDatos_Responsable   Cementerio: "+ex);
        }
        return info;
    }
    
}
