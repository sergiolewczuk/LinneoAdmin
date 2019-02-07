/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Pantallas.Frames.Principal;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author jason
 */
public class Asientos {
    
    public Object[][] datos;
    
    public Date fecha_asiento;
    public BigDecimal[] CargarID(String id){
        BigDecimal[] dou = {BigDecimal.ZERO,BigDecimal.ZERO};
        if (!"".equals(id)) {
        
            try {
                ResultSet rs;
                Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                rs = st.executeQuery("SELECT id_movimiento,asientos_detalle.hora,asientos_detalle.num_cuenta,nombre,detalle,debe,haber,asientos.fecha FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos_detalle.anio = asientos.anio) WHERE (asientos.estado = 'A' AND asientos_detalle.estado = 'A') AND asientos.anio = "+Principal.anio_usado+" AND asientos.id_asiento = " + id);
                rs.last();

                Integer registros = rs.getRow();
                datos = new Object[registros][7];

                rs.first();
                for (int fila = 0; fila < registros; fila++) {
                    for (int cols = 0; cols < 8; cols++) {
                        if (cols == 7) {
                            fecha_asiento = rs.getDate(cols + 1);
                        } else {
                            datos[fila][cols] = rs.getObject(cols + 1);
                        }

                        if (cols == 5 && datos[fila][cols] != null) {
                            dou[0] = dou[0].add(new BigDecimal(datos[fila][cols].toString()));
                        } else if (cols == 6 && datos[fila][cols] != null) {
                            dou[1] = dou[1].add(new BigDecimal(datos[fila][cols].toString()));
                        }
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
        return dou;
    }
    
    public static Object[][] MovimientosBaja(String id) {
        Object[][] dato = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT id_movimiento,asientos_detalle.hora,asientos_detalle.num_cuenta,nombre,detalle,debe,haber,asientos.fecha FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos_detalle.anio = asientos.anio) WHERE (asientos_detalle.estado = 'B') AND asientos.anio = "+Principal.anio_usado+" AND asientos.id_asiento = " + id);
            rs.last();

            Integer registros = rs.getRow();
            dato = new Object[registros][7];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 7; cols++) {
                    dato[fila][cols] = rs.getObject(cols + 1);
                    //System.out.println(datos[fila][cols]);
                }
                rs.next();
            }
            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
        return dato;
    }
    
    /*public static void GenerarAsientoDevengamiento() {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.executeQuery("SELECT id_asiento,fecha,hora,sector FROM asientos WHERE estado = 'B' AND anio = "+Principal.anio_usado+"");
            rs.last();

            Integer registros = rs.getRow();
            dato = new Object[registros][4];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 4; cols++) {
                    dato[fila][cols] = rs.getObject(cols + 1);
                    //System.out.println(datos[fila][cols]);
                }
                rs.next();
            }
            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }

        return dato;
    }*/
    
    public static Object[][] AsientosBaja(){
        Object[][] dato = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT id_asiento,fecha,hora,sector FROM asientos WHERE estado = 'B' AND anio = "+Principal.anio_usado+"");
            rs.last();

            Integer registros = rs.getRow();
            dato = new Object[registros][4];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 4; cols++) {
                    dato[fila][cols] = rs.getObject(cols + 1);
                    //System.out.println(datos[fila][cols]);
                }
                rs.next();
            }
            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }

        return dato;
    }
    
    public static void InsertarAsiento(String hora, String fecha) {
        String fecha_ins;
        String anio;
        if (fecha == null) {
            fecha_ins = "CURDATE()";
            anio = Principal.anio_global;
        } else {
            fecha_ins = "'" + fecha + "'";
            anio = Principal.anio_usado;
        }
        
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO asientos (id_asiento,anio,fecha,hora,usuario_creo) VALUES ("+IdActual(anio)+" ,YEAR("+fecha_ins+") , "+fecha_ins+", '"+hora+"', "+Principal.id+")");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public static void InsertarDebe(String fecha, String num_cuenta, String detalle, String monto, String hora, String id_asiento) {
        String fecha_ins;
        
        if (fecha == null) {
            fecha_ins = "CURDATE()";
        } else {
            fecha_ins = "'" + fecha + "'";
        }
        
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO asientos_detalle (id_asiento, anio, fecha, hora, num_cuenta, detalle, debe) VALUES ("+id_asiento+", YEAR("+fecha_ins+"), "+fecha_ins+", '"+hora+"', " + num_cuenta + ", '"+detalle+"', "+monto+")");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public static void InsertarHaber(String fecha, String num_cuenta, String detalle, String monto, String hora, String id_asiento) {
        String fecha_ins;
        
        if (fecha == null) {
            fecha_ins = "CURDATE()";
        } else {
            fecha_ins = "'" + fecha + "'";
        }
        
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO asientos_detalle (id_asiento, anio, fecha, hora, num_cuenta, detalle, haber) VALUES ("+id_asiento+", YEAR("+fecha_ins+"), "+fecha_ins+", '"+hora+"', " + num_cuenta + ", '"+detalle+"', "+monto+")");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public static void InsertarDetalleDevengamientoHaber(String fecha, String num_cuenta, String detalle, String monto, String hora, String id_asiento, String cuota) {
        String fecha_ins;
        
        if (fecha == null) {
            fecha_ins = "CURDATE()";
        } else {
            fecha_ins = "'" + fecha + "'";
        }
        
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO asientos_detalle (id_asiento, anio, fecha, hora, num_cuenta, detalle, haber, cuota_id) VALUES ("+id_asiento+", YEAR("+fecha_ins+"), "+fecha_ins+", '"+hora+"', " + num_cuenta + ", '"+detalle+"', "+monto+", "+cuota+")");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public static void InsertarDetalleDevengamientoDebe(String fecha, String num_cuenta, String detalle, String monto, String hora, String id_asiento, String cuota) {
        String fecha_ins;
        
        if (fecha == null) {
            fecha_ins = "CURDATE()";
        } else {
            fecha_ins = "'" + fecha + "'";
        }
        
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO asientos_detalle (id_asiento, anio, fecha, hora, num_cuenta, detalle, debe, cuota_id) VALUES ("+id_asiento+", YEAR("+fecha_ins+"), "+fecha_ins+", '"+hora+"', " + num_cuenta + ", '"+detalle+"', "+monto+", "+cuota+")");

        }catch(Exception ex){
    System.out.println("Se han producido excepciones: "+ex);
        }
    }
    
    public void GuardarAsiento(Object[][] mov_eliminados, String[][] datos, String id_asiento, Object[][] datos_alta) {
        //ELIMINO LOS Q ELIMINÓ xD
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            if (mov_eliminados != null) {
                for (int a = 0; a < mov_eliminados.length; a++) {
                    st.executeUpdate("UPDATE asientos_detalle SET estado = 'B' WHERE id_movimiento = " + mov_eliminados[a][0].toString() + "");
                }
            }

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones dasasas: " + ex);
        }
        
        //UPDATEO LOS NECESARIOS E INSERTO LOS NUEVOS
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            int contador = 0;
            
            for (int a = 0; a < datos.length; a++) {
                if (datos[a][0] != null) {
                    if (datos_alta != null) {
                        if (datos[a][0].equals(datos_alta[contador][0].toString())) {
                            String id = datos_alta[contador][0].toString();
                            st.executeUpdate("UPDATE asientos_detalle SET estado = 'A' WHERE id_movimiento = " + id + "");
                            contador++;
                        }
                    } else {
                        st.executeUpdate("UPDATE asientos_detalle SET num_cuenta = " + datos[a][2] + ", detalle = '" + datos[a][4] + "', debe = " + datos[a][5] + ", haber = " + datos[a][6] + " WHERE id_movimiento = " + datos[a][0] + "");
                    }
                } else {
                    if (datos[a][5] != null) {
                        InsertarDebe(null,datos[a][2],datos[a][4],datos[a][5],datos[a][1],id_asiento);
                    } else {
                        InsertarHaber(null,datos[a][2],datos[a][4],datos[a][6],datos[a][1],id_asiento);
                    }
                }
            }

        } catch (Exception ex) {
            
            StackTraceElement l = ex.getStackTrace()[0];
            System.out.println(
                    l.getClassName() + "/" + l.getMethodName() + ":" + l.getLineNumber());
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
    
    public void Eliminar(String id) {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate("UPDATE asientos SET estado = 'B' WHERE id_asiento = " + id + " AND anio = YEAR(CURDATE())");

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
    
    public static void AltaAsiento(String id) {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate("UPDATE asientos SET estado = 'A' WHERE id_asiento = " + id + " AND anio = YEAR(CURDATE())");

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
    
    public static void AltaMovimiento(int id) {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate("UPDATE asientos_detalle SET estado = 'A' WHERE id_movimiento = " + id);

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
    
    public static Integer IdActual(String anio) {
        Integer ids = 0;
        
        try{
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT MAX(id_asiento) FROM asientos WHERE YEAR(fecha) = "+anio+"");
        rs.last();
        
        ids = rs.getInt(1);
        
        if (ids > 0) {
            if (ids + 1 < 3) {
                ids = 3;
            } else {
                ids = ids + 1;
            }
        } else {
            ids = 3;
        }
        
        rs.close();
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        
        return ids;
    }
}
