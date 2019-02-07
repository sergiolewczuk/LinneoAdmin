/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;


public class Alquileres {
 
    public Object[][] datos; //Variable que alberga los datos de la BD
    
    public void Cargar(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT lotes.id,lotes_responsables.id,lotes.nombre,CONCAT(lotes_responsables.apellidos,' ',lotes_responsables.nombres),lotes_responsables.telefono,lotes_responsables.direccion,alquileres_contratos.apenom_garante "
                            + "FROM lotes "
                            + "LEFT JOIN alquileres_contratos ON alquileres_contratos.lote = lotes.id "
                            + "LEFT JOIN lotes_responsables ON lotes_responsables.id = alquileres_contratos.id_responsable "
                            + "WHERE alquileres_contratos.estado = 'A' "
                            + "ORDER BY lotes.nombre");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][7];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);    //  COD_LOTE
                datos[fila][1] = rs.getObject(2);    //  COD_RESP
                datos[fila][2] = rs.getObject(3);    //  LOCAL
                datos[fila][3] = rs.getObject(4);    //  APENOM RESPONSABLE
                datos[fila][4] = rs.getObject(5);    //  TELEFONO
                datos[fila][5] = rs.getObject(6);    //  DIRECCION
                datos[fila][6] = rs.getObject(7);    //  APENOM_GARANTE
                
                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Cargar     Alquileres: " + ex);
        }    
    }
    
    public void FiltrarXApeNom(String buscar){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT lotes.id,lotes_responsables.id,lotes.nombre,lotes_responsables.apenom,lotes_responsables.direccion,lotes_responsables.telefono,lotes_responsables.apenom_garante "
                                + "FROM lotes "
                                + "LEFT JOIN lotes ON lotes.id_responsable = lotes_responsables.id_responsable "
                                + "WHERE lotes.nombre LIKE '%"+buscar+"%' OR lotes_responsables.apenom LIKE '%"+buscar+"%'"
                                + "ORDER BY lotes.nombre");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][7];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);    //  COD_LOTE
                datos[fila][1] = rs.getObject(2);    //  COD_RESP
                datos[fila][2] = rs.getObject(3);    //  LOCAL
                datos[fila][3] = rs.getObject(4);    //  APENOM RESPONSABLE
                datos[fila][4] = rs.getObject(5);    //  TELEFONO
                datos[fila][5] = rs.getObject(6);    //  DIRECCION
                datos[fila][6] = rs.getObject(7);    //  APENOM_GARANTE
                
                rs.next();
            }
            rs.close();

        }catch(Exception ex){
            System.out.println("Se han producido excepciones  FiltrarXApeNom   Alquileres: "+ex);
        }
    }
}
