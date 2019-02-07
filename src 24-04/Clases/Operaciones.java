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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;

/**
 *
 * @author Sergio
 */
public class Operaciones {
    
    public static Object[] Cargar(JComboBox combo) {
        combo.removeAllItems();
        combo.addItem("TODAS");
        
        Object[] datos_return = new Object[6];
        
        Object[][] datos = null;

        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT operaciones.id,fecha,hora,pk_persona,\n"
                    + "CASE sector WHEN 'Institucional' THEN\n"
                    + "(SELECT apenom_alumno FROM alumnos WHERE id_alumno = pk_persona)\n"
                    + "WHEN 'Alquileres' THEN\n"
                    + "(SELECT CONCAT(apellidos,' ',nombres) FROM alquileres_contratos JOIN lotes_responsables ON (alquileres_contratos.id_responsable = lotes_responsables.id) WHERE (pk_persona /*contrato*/ = alquileres_contratos.id))\n"
                    + "WHEN 'Otros' THEN\n"
                    + "(SELECT CONCAT(apellidos,' ',nombres) FROM personas_terceras WHERE persona = pk_persona)\n"
                    + "WHEN 'Socios' THEN\n"
                    + "(SELECT apenom_socio FROM socios WHERE id_socio = pk_persona)\n"
                    + "END,\n"
                    + "sector,IF(num_recibo IS NULL,CONCAT('FACT ',num_factura),CONCAT('REC ',num_recibo)),tipo_pago,\n"
                    + "SUM(monto) - IF((SELECT SUM(monto) FROM operaciones_detalle WHERE id_operacion = operaciones.id AND tipo = 'Cuota Bonificada') IS NULL, 0, (SELECT SUM(monto) FROM operaciones_detalle WHERE id_operacion = operaciones.id AND tipo = 'Cuota Bonificada'))\n"
                    + ",motivo_baja,estado FROM operaciones\n"
                    + "JOIN operaciones_detalle ON (operaciones.id = operaciones_detalle.id_operacion AND operaciones_detalle.tipo != 'Cuota Bonificada')\n"
                    + "GROUP BY id\n"
                    + "ORDER BY id DESC");
            
            rs.last();

            int reg = rs.getRow();
            
            datos = new Object[reg][11];

            rs.first();
            
            String fecha_temp = "";
            
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal efectivo = BigDecimal.ZERO;
            BigDecimal debito = BigDecimal.ZERO;
            BigDecimal credito = BigDecimal.ZERO;
            BigDecimal cheque = BigDecimal.ZERO;
            
            for (int a = 0; a < reg; a++) {
                datos[a][0] = rs.getObject(1);
                datos[a][1] = rs.getObject(2);
                if (!datos[a][1].toString().equals(fecha_temp)) {
                    fecha_temp = datos[a][1].toString();
                    combo.addItem(datos[a][1].toString());
                }
                datos[a][2] = rs.getObject(3);
                datos[a][3] = rs.getObject(4);
                datos[a][4] = rs.getObject(5);
                datos[a][5] = rs.getObject(6);
                datos[a][6] = rs.getObject(7);
                datos[a][7] = rs.getObject(8);
                datos[a][8] = rs.getObject(9);
                datos[a][9] = rs.getObject(10);
                datos[a][10] = rs.getObject(11);
                
                String tipo_pago = rs.getString(8);
                
                if (rs.getString(11).equals("A") || rs.getString(11).equals("C")) {
                    if (tipo_pago.equals("Efectivo")) {
                        efectivo = efectivo.add(rs.getBigDecimal(9));
                    } else if (tipo_pago.equals("Débito")) {
                        debito = debito.add(rs.getBigDecimal(9));
                    } else if (tipo_pago.equals("Crédito")) {
                        credito = credito.add(rs.getBigDecimal(9));
                    } else if (tipo_pago.equals("Cheque")) {
                        cheque = cheque.add(rs.getBigDecimal(9));
                    }
                    
                    total = total.add(rs.getBigDecimal(9));
                }
                
                rs.next();
            }
            
            datos_return[0] = datos;
            datos_return[1] = total;
            datos_return[2] = efectivo;
            datos_return[3] = debito;
            datos_return[4] = credito;
            datos_return[5] = cheque;

            rs.close();
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    GenerarOperacionDetallesInscripcion    Operaciones: " + ex);
        }

        return datos_return;
    }
    
    public static Object[] Cargar(String fecha) {
        Object[][] datos = null;
        Object[] datos_return = new Object[6];
        
        String where = "";
        
        if (!fecha.equals("TODAS")) {
            where = "WHERE fecha = '"+fecha+"'\n";
        }

        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT operaciones.id,fecha,hora,pk_persona,\n"
                    + "CASE sector WHEN 'Institucional' THEN\n"
                    + "(SELECT apenom_alumno FROM alumnos WHERE id_alumno = pk_persona)\n"
                    + "WHEN 'Alquileres' THEN\n"
                    + "(SELECT CONCAT(apellidos,' ',nombres) FROM alquileres_contratos JOIN lotes_responsables ON (alquileres_contratos.id_responsable = lotes_responsables.id) WHERE (pk_persona /*contrato*/ = alquileres_contratos.id))\n"
                    + "WHEN 'Otros' THEN\n"
                    + "(SELECT CONCAT(apellidos,' ',nombres) FROM personas_terceras WHERE persona = pk_persona)\n"
                    + "WHEN 'Socios' THEN\n"
                    + "(SELECT apenom_socio FROM socios WHERE id_socio = pk_persona)\n"
                    + "END,\n"
                    + "sector,IF(num_recibo IS NULL,CONCAT('FACT ',num_factura),CONCAT('REC ',num_recibo)),tipo_pago,\n"
                    + "SUM(monto) - IF((SELECT SUM(monto) FROM operaciones_detalle WHERE id_operacion = operaciones.id AND tipo = 'Cuota Bonificada') IS NULL, 0, (SELECT SUM(monto) FROM operaciones_detalle WHERE id_operacion = operaciones.id AND tipo = 'Cuota Bonificada'))\n"
                    + ", motivo_baja, estado FROM operaciones\n"
                    + "JOIN operaciones_detalle ON (operaciones.id = operaciones_detalle.id_operacion AND operaciones_detalle.tipo != 'Cuota Bonificada')\n"
                    + where
                    + "GROUP BY id "
                    + "ORDER BY id DESC");
            
            rs.last();

            int reg = rs.getRow();
            
            datos = new Object[reg][11];

            rs.first();
            
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal efectivo = BigDecimal.ZERO;
            BigDecimal debito = BigDecimal.ZERO;
            BigDecimal credito = BigDecimal.ZERO;
            BigDecimal cheque = BigDecimal.ZERO;
            
            for (int a = 0; a < reg; a++) {
                datos[a][0] = rs.getObject(1);
                datos[a][1] = rs.getObject(2);
                datos[a][2] = rs.getObject(3);
                datos[a][3] = rs.getObject(4);
                datos[a][4] = rs.getObject(5);
                datos[a][5] = rs.getObject(6);
                datos[a][6] = rs.getObject(7);//COMPROBANTE
                datos[a][7] = rs.getObject(8);//TIPO PAGO
                datos[a][8] = rs.getObject(9);
                datos[a][9] = rs.getObject(10);
                datos[a][10] = rs.getObject(11);
                
                String tipo_pago = rs.getString(8);
                
                if (rs.getString(11).equals("A") || rs.getString(11).equals("C")) {
                    if (tipo_pago.equals("Efectivo")) {
                        efectivo = efectivo.add(rs.getBigDecimal(9));
                    } else if (tipo_pago.equals("Débito")) {
                        debito = debito.add(rs.getBigDecimal(9));
                    } else if (tipo_pago.equals("Crédito")) {
                        credito = credito.add(rs.getBigDecimal(9));
                    } else if (tipo_pago.equals("Cheque")) {
                        cheque = cheque.add(rs.getBigDecimal(9));
                    }
                    
                    total = total.add(rs.getBigDecimal(9));
                }
                
                rs.next();
            }
            
            datos_return[0] = datos;
            datos_return[1] = total;
            datos_return[2] = efectivo;
            datos_return[3] = debito;
            datos_return[4] = credito;
            datos_return[5] = cheque;

            rs.close();
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    GenerarOperacionDetallesInscripcion    Operaciones: " + ex);
        }

        return datos_return;
    }
    
    public static boolean PermisoAnularSocio(String persona, String sector, String operacion) {
        boolean permiso = false;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String consulta = "SELECT estado FROM operaciones WHERE pk_persona = "+persona+" AND sector = '"+sector+"' AND id >= " + operacion + " AND estado != 'B' ORDER BY id ASC";
            rs = st.executeQuery(consulta);
            
            if (rs.next()) {
                rs.last();
                int reg = rs.getRow();
                if (reg - 1 == 0) {
                    //estoy consultando el ultimo
                    String estado = rs.getString(1);
                    if(estado.equals("A")) {
                        permiso = true;
                    }
                }
            }
            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
        return permiso;
    }
    
    public static Object[][] CargarDetalle(String id) {
        Object[][] datos = null;

        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT detalle,IF(LOWER(tipo) LIKE '%bonifica%', CONCAT('-',monto), monto),tipo FROM operaciones_detalle WHERE id_operacion = "+id+" ORDER BY id");
            
            rs.last();

            int reg = rs.getRow();
            
            datos = new Object[reg][3];

            rs.first();
            
            for (int a = 0; a < reg; a++) {
                datos[a][0] = rs.getObject(1);
                datos[a][1] = rs.getObject(2);
                datos[a][2] = rs.getObject(3);
                
                rs.next();
            }

            rs.close();
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    GenerarOperacionDetallesInscripcion    Operaciones: " + ex);
        }

        return datos;
    }
    
    public static void GenerarOperacion(String pk_persona, String tipo_pago, String hora, String fecha, String detalle, String sector) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        String num_recibo = Variables.CargarNumRecibo();
        
        if (!"".equals(detalle)) {
            st.executeUpdate("INSERT INTO operaciones "
                    + "(fecha, hora, estado, pk_persona, interno, sector, num_recibo, detalle, tipo_pago) "
                    + "VALUES "
                    + "('"+fecha+"', '"+hora+"', 'A', "+pk_persona+", 1, '"+sector+"', '"+num_recibo+"', '"+detalle+"','"+tipo_pago+"')");
        } else {
            st.executeUpdate("INSERT INTO operaciones "
                    + "(fecha, hora, estado, pk_persona, interno, sector, num_recibo, detalle, tipo_pago) "
                    + "VALUES "
                    + "('"+fecha+"', '"+hora+"', 'A', "+pk_persona+", 1, '"+sector+"', '"+num_recibo+"', NULL,'"+tipo_pago+"')");
        }
        
        
        }catch(Exception ex){
    System.out.println("Se han producido excepciones    GenerarOperacionInscripcion    Operaciones: "+ex);
        }
    }
    
    public static void GenerarOperacionConReciboInterno(String pk_persona, String tipo_pago, String hora, String fecha, String detalle, String sector, String num_recibo) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        if (!"".equals(detalle)) {
            st.executeUpdate("INSERT INTO operaciones "
                    + "(fecha, hora, estado, pk_persona, interno, sector, num_recibo, detalle, tipo_pago) "
                    + "VALUES "
                    + "('"+fecha+"', '"+hora+"', 'A', "+pk_persona+", 1, '"+sector+"', '"+num_recibo+"', '"+detalle+"','"+tipo_pago+"')");
        } else {
            st.executeUpdate("INSERT INTO operaciones "
                    + "(fecha, hora, estado, pk_persona, interno, sector, num_recibo, detalle, tipo_pago) "
                    + "VALUES "
                    + "('"+fecha+"', '"+hora+"', 'A', "+pk_persona+", 1, '"+sector+"', '"+num_recibo+"', NULL,'"+tipo_pago+"')");
        }
        
        
        }catch(Exception ex){
    System.out.println("Se han producido excepciones    GenerarOperacionInscripcion    Operaciones: "+ex);
        }
    }
    
    public static void GenerarOperacionCuotasAlumnos(String pk_persona, String tipo_pago, String hora, String fecha) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        String num_recibo = "NULL";
        
        st.executeUpdate("INSERT INTO operaciones "
                    + "(fecha, hora, estado, pk_persona, interno, sector, num_recibo, tipo_pago) "
                    + "VALUES "
                    + "('"+fecha+"', '"+hora+"', 'A', "+pk_persona+", 1, 'Institucional', "+num_recibo+", '"+tipo_pago+"')");
        
        }catch(Exception ex){
    System.out.println("Se han producido excepciones    GenerarOperacionInscripcion    Operaciones: "+ex);
        }
    }
    
    public static void GenerarOperacionDetalles(String detalle, String monto_pagado, String que_es, String relacion) {
        try{
        ResultSet rs;    
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs = st.executeQuery("SELECT MAX(id) AS ultimo_id FROM operaciones");
        rs.last();

        Integer ultimo_id = rs.getInt(1);

        rs.close();
        
        if (relacion == null) {
            st.executeUpdate("INSERT INTO operaciones_detalle "
                    + "(id_operacion, detalle, monto, tipo, relacion) "
                    + "VALUES "
                    + "("+ultimo_id+",'"+detalle+"',"+monto_pagado+",'"+que_es+"',NULL)");
        } else {
            st.executeUpdate("INSERT INTO operaciones_detalle "
                    + "(id_operacion, detalle, monto, tipo, relacion) "
                    + "VALUES "
                    + "("+ultimo_id+",'"+detalle+"',"+monto_pagado+",'"+que_es+"',"+relacion+")");
        }
       
        }catch(Exception ex){
    System.out.println("Se han producido excepciones    GenerarOperacionDetallesInscripcion    Operaciones: "+ex);
        }
    }
    
    public static String Anular(String id, String motivo) {
        String mensaje = null;
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement st_for = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            st.executeUpdate("UPDATE operaciones SET motivo_baja = '"+motivo+"', estado = 'B', usuario_baja = "+Principal.id+", fecha_baja = CURDATE() WHERE id = "+id+"");
            ResultSet rs = st.executeQuery("SELECT sector,operaciones_detalle.tipo,operaciones_detalle.detalle,pk_persona,monto,relacion FROM operaciones JOIN operaciones_detalle ON (operaciones.id = operaciones_detalle.id_operacion) WHERE operaciones.id = "+id+"");
            rs.last();
            int registros = rs.getRow();
            rs.first();
            
            String sector = rs.getString(1);
            
            //SABER A Q SECTOR PERTENECE LA OPERACION
            if (sector.equals("Institucional")) {
                //SECTOR ALUMNADO

                //RECORRIDO SOBRE EL DETALLE DE LA OPERACION
                for (int a = 0; a < registros; a++) {
                    //CUOTA, INSCRIPCION
                    String tipo = rs.getString(2);
                    String detalle = rs.getString(3);
                    String id_alumno = rs.getString(4);
                    String monto = rs.getString(5);
                    String relacion = rs.getString(6);
                    
                    if (relacion != null) {

                        if (tipo.equals("Deuda 2013")) {
                        //DEUDA 2013 (deudores_temp) no sabemos q son exactamente
                            //redusco el monto_pagado por el del detalle
                            st_for.executeUpdate("UPDATE deudores_temp SET pagado_deuda_2013 = pagado_deuda_2013 - " + monto + " WHERE id_alumno = " + id_alumno + "");
                        } else if (detalle.contains("Deuda 2014")) {
                            //DEUDA 2014 (deudores_temp) CUOTAS
                            String mes = detalle.replace("Completada ", "").replace("Parcial ", "").substring(detalle.indexOf(" ") + 1, detalle.indexOf("2014") - 1);
                            mes = mes.substring(0, mes.indexOf(" ")).toLowerCase();
                            //redusco el monto_pagado por el del detalle
                            st_for.executeUpdate("UPDATE deudores_temp SET pagado_" + mes + "_2014 = pagado_" + mes + "_2014 - " + monto + " WHERE id_alumno = " + id_alumno + "");
                        } else if (tipo.equals("Cuota")) {
                        //CUOTA DESPUES del 2014
                            //redusco el monto_pagado por el del detalle
                            //si queda 0 seteo en null el monto_pagado y el monto_total
                            st_for.executeUpdate("UPDATE cuotas_alumnado SET monto_pagado = monto_pagado - " + monto + " WHERE id = " + relacion + "");
                            ResultSet consulta = st_for.executeQuery("SELECT monto_pagado FROM cuotas_alumnado WHERE id = " + relacion + "");
                            consulta.last();
                            BigDecimal monto_pagado = consulta.getBigDecimal(1);
                            consulta.close();
                            st_for.clearBatch();
                            if (monto_pagado.compareTo(BigDecimal.ZERO) == 0) {
                                //seteo en null el monto_pagado y el monto_total
                                st_for.executeUpdate("UPDATE cuotas_alumnado SET monto_a_pagar = NULL, monto_pagado = NULL WHERE id = " + relacion + "");
                            }
                        } else if (tipo.equals("Cuota Bonificada")) {
                        //CUOTA BONIFICADA (despues del 2014)
                            //seteo en null la bonificacion de la cuota
                            st_for.executeUpdate("UPDATE cuotas_alumnado SET monto_descuento = NULL WHERE id = " + relacion + "");
                        } else if (tipo.equals("Inscripción")) {
                        //INSCRIPCION
                            //elimino su inscripcion al curso
                            mensaje = "Todavia no se puede Anular las Operaciones relacionadas con Inscripciones de Institucional.";
                            st_for.executeUpdate("UPDATE operaciones SET motivo_baja = NULL, estado = 'A', usuario_baja = NULL, fecha_baja = NULL WHERE id = " + id + "");
                        }

                        st_for.clearBatch();
                        rs.next();

                    } else {
                        
                        mensaje = "No puede Anular esta Operación porque\nen la versión del Sistema en la que fue creada no existía la Anulación de Operaciones.";
                        st.executeUpdate("UPDATE operaciones SET motivo_baja = NULL, estado = 'A', usuario_baja = NULL, fecha_baja = NULL WHERE id = " + id + "");
                        
                    }
                }
            } else if (sector.equals("Alquileres")) {
                //ALQUILERES
                //RECORRIDO SOBRE EL DETALLE DE LA OPERACION
                for (int a = 0; a < registros; a++) {
                    //CUOTA, INSCRIPCION
                    //String tipo = rs.getString(2);
                    //String detalle = rs.getString(3);
                    //String id_contrato = rs.getString(4);
                    String monto = rs.getString(5);
                    String relacion = rs.getString(6);
                    
                    //redusco el monto_pagado por el del detalle
                    //si queda 0 seteo en null el monto_pagado y el monto_total
                    st_for.executeUpdate("UPDATE cuotas_alquileres SET monto_pagado = monto_pagado - " + monto + " WHERE id = " + relacion + "");
                    ResultSet consulta = st_for.executeQuery("SELECT monto_pagado FROM cuotas_alquileres WHERE id = " + relacion + "");
                    consulta.last();
                    BigDecimal monto_pagado = consulta.getBigDecimal(1);
                    consulta.close();
                    st_for.clearBatch();
                    if (monto_pagado.compareTo(BigDecimal.ZERO) == 0) {
                        //seteo en null el monto_pagado y el monto_total
                        st_for.executeUpdate("DELETE FROM cuotas_alquileres WHERE id = " + relacion + "");
                    }
                    
                    st_for.clearBatch();
                    rs.next();
                }
            } else if (sector.equals("Socios")) {
                //SOCIOS
                //RECORRIDO SOBRE EL DETALLE DE LA OPERACION
                for (int a = 0; a < registros; a++) {
                    //CUOTA
                    //String tipo = rs.getString(2);
                    //String detalle = rs.getString(3);
                    //String id_contrato = rs.getString(4);
                    String monto = rs.getString(5);
                    String relacion = rs.getString(6);
                    
                    //redusco el monto_pagado por el del detalle
                    //si queda 0 seteo en null el monto_pagado y el monto_total
                    st_for.executeUpdate("UPDATE cuotas_socios SET monto_pagado = monto_pagado - " + monto + " WHERE id = " + relacion + "");
                    ResultSet consulta = st_for.executeQuery("SELECT monto_pagado FROM cuotas_socios WHERE id = " + relacion + "");
                    consulta.last();
                    BigDecimal monto_pagado = consulta.getBigDecimal(1);
                    consulta.close();
                    st_for.clearBatch();
                    if (monto_pagado.compareTo(BigDecimal.ZERO) == 0) {
                        //seteo en null el monto_pagado y el monto_total
                        st_for.executeUpdate("DELETE FROM cuotas_socios WHERE id = " + relacion + "");
                    }
                    
                    st_for.clearBatch();
                    rs.next();
                }
            }
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        } return mensaje;
    }
    
    
    public static Integer UltimoID () {
        Integer ultimo_id = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT MAX(id) AS ultimo_id FROM operaciones");
            rs.last();

            ultimo_id = rs.getInt(1);

            rs.close();
        }catch (Exception ex){
            System.out.println("Se han producido excepciones    UltimoID    Operaciones: "+ex);
        }
        return ultimo_id;
    }
    
    public static void PonerNullNumRecibo(Integer id_operacion){
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate("UPDATE operaciones SET num_recibo = NULL WHERE id = "+id_operacion+"");
            
        }catch (Exception ex){
            System.out.println("Se han producido excepciones    PonerNullNumRecibo    Operaciones: "+ex);
        }
    }
    
    public static void CierreOperaciones(String fecha) {
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement st2 = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hora = hourFormat.format(date);
            
            String asiento = Clases.Asientos.IdActual(Principal.anio_usado).toString();
            Clases.Asientos.InsertarAsiento(hora, null);
            
            /*RS SOLO DE SOCIOS*/
            rs = st.executeQuery("SELECT operaciones.tipo_pago, operaciones_detalle.tipo,\n"
                    + "SUM(operaciones_detalle.monto), tipo_pago_global.num_cuenta\n"
                    + "FROM operaciones JOIN operaciones_detalle ON\n"
                    + "(operaciones.id = operaciones_detalle.id_operacion)\n"
                    + "JOIN tipo_pago_global ON (operaciones.tipo_pago = tipo_pago_global.tipo_pago)\n"
                    + "WHERE operaciones.sector = 'Socios' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'\n"
                    + "GROUP BY tipo_pago, tipo");
            
            int reg = 0;
            
            if (rs.next()) {
                rs.last();
                reg = rs.getRow();
                rs.first();
            }
            
            BigDecimal monto_total = BigDecimal.ZERO;

            for (int a = 0; a < reg; a++) {
                //por ahora solo cuotas :)
                BigDecimal monto = rs.getBigDecimal(3);
                String cuenta = rs.getString(4);
                
                monto_total = monto_total.add(monto);
                
                Clases.Asientos.InsertarDebe(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                rs.next();
            }
            
            rs.close();
            
            if (reg > 0) {
                rs = st.executeQuery("SELECT valor FROM variables WHERE nombre = 'cuenta_socios'");
                rs.next();
                String cuenta_principal = rs.getString(1);
                rs.close();

                Clases.Asientos.InsertarHaber(fecha, cuenta_principal, "Cierre de Caja", monto_total.toString(), hora, asiento);
                st.executeUpdate("UPDATE operaciones SET estado = 'C' WHERE operaciones.sector = 'Socios' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'");
            }
            
            //RS OTROS
            rs = st.executeQuery("SELECT operaciones.tipo_pago,\n"
                    + "SUM(operaciones_detalle.monto), tipo_pago_global.num_cuenta\n"
                    + "FROM operaciones JOIN operaciones_detalle ON\n"
                    + "(operaciones.id = operaciones_detalle.id_operacion)\n"
                    + "JOIN tipo_pago_global ON (operaciones.tipo_pago = tipo_pago_global.tipo_pago)\n"
                    + "WHERE operaciones.sector = 'Otros' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'\n"
                    + "GROUP BY tipo_pago");
            
            reg = 0;
            
            if (rs.next()) {
                rs.last();
                reg = rs.getRow();
                rs.first();
            }

            for (int a = 0; a < reg; a++) {
                //esto es para el tipo de pago = "efectivo", etc
                BigDecimal monto = rs.getBigDecimal(2);
                String cuenta = rs.getString(3);
                
                Clases.Asientos.InsertarDebe(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                rs.next();
            }
            
            rs.close();
            
            rs = st.executeQuery("SELECT operaciones_detalle.tipo,\n"
                    + "SUM(operaciones_detalle.monto), operaciones_tipos.num_cuenta\n"
                    + "FROM operaciones JOIN operaciones_detalle ON\n"
                    + "(operaciones.id = operaciones_detalle.id_operacion)\n"
                    + "JOIN operaciones_tipos ON (operaciones_detalle.tipo = operaciones_tipos.nombre)\n"
                    + "WHERE operaciones.sector = 'Otros' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'\n"
                    + "GROUP BY tipo");
            
            reg = 0;
            
            if (rs.next()) {
                rs.last();
                reg = rs.getRow();
                rs.first();
            }
            
            for (int a = 0; a < reg; a++) {
                //esto es para el tipo de operacion = "ofrenda", etc
                BigDecimal monto = rs.getBigDecimal(2);
                String cuenta = rs.getString(3);
                
                Clases.Asientos.InsertarHaber(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                rs.next();
            }
            rs.close();
            
            if (reg > 0) {
                st.executeUpdate("UPDATE operaciones SET estado = 'C' WHERE operaciones.sector = 'Otros' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'");
            }
            
            //RS DE INSTITUCIONAL de entrada
            rs = st.executeQuery("SELECT operaciones.tipo_pago,\n"
                    + "                    SUM(operaciones_detalle.monto), tipo_pago_global.num_cuenta\n"
                    + "                    FROM operaciones\n"
                    + "                    JOIN operaciones_detalle\n"
                    + "                    ON (operaciones.id = operaciones_detalle.id_operacion)\n"
                    + "                    JOIN tipo_pago_global ON (operaciones.tipo_pago = tipo_pago_global.tipo_pago)\n"
                    + "                    WHERE operaciones.sector = 'Institucional' AND operaciones.estado = 'A' AND operaciones.fecha = '"+fecha+"' AND operaciones.num_recibo IS NOT NULL AND operaciones_detalle.tipo != 'Cuota Bonificada'\n"
                    + "                    GROUP BY tipo_pago\n"
                    + "                    ORDER BY num_cuenta");
            
            reg = 0;
            
            if (rs.next()) {
                rs.last();
                reg = rs.getRow();
                rs.first();
            }

            for (int a = 0; a < reg; a++) {
                //esto es para el tipo de pago = "efectivo", etc
                BigDecimal monto = rs.getBigDecimal(2);
                String cuenta = rs.getString(3);
                
                Clases.Asientos.InsertarDebe(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                rs.next();
            }

            rs.close();
            
            rs = st.executeQuery("SELECT operaciones_detalle.id AS detalle_operacion,operaciones_detalle.tipo,\n"
                    + "         SUM(operaciones_detalle.monto),\n"
                    + "         CASE operaciones_detalle.tipo \n"
                    + "             WHEN 'Cuota'\n"
                    + "             THEN\n"
                    + "			CASE  \n"
                    + "                     WHEN operaciones_detalle.relacion IS NULL\n"
                    + "                     THEN /*DEUDAS 2014*/\n"
                    + "                         (SELECT nivel FROM deudores_temp WHERE id_alumno = operaciones.pk_persona)\n"
                    + "                     WHEN operaciones_detalle.relacion IS NOT NULL\n"
                    + "                     THEN\n"
                    + "                         (SELECT nivel FROM carrera JOIN cuotas_alumnado ON (carrera.id = cuotas_alumnado.id_carrera) JOIN operaciones_detalle ON (cuotas_alumnado.id = operaciones_detalle.relacion) WHERE operaciones_detalle.id = detalle_operacion)\n"
                    + "                 END\n"
                    + "             WHEN 'Cuota Bonificada'\n"
                    + "             THEN\n"
                    + "                 (SELECT nivel FROM carrera JOIN cuotas_alumnado ON (carrera.id = cuotas_alumnado.id_carrera) JOIN operaciones_detalle ON (cuotas_alumnado.id = operaciones_detalle.relacion) WHERE operaciones_detalle.id = detalle_operacion)\n"
                    + "             WHEN 'Deuda 2013'\n"
                    + "             THEN\n"
                    + "                 (SELECT nivel FROM deudores_temp WHERE id_alumno = operaciones.pk_persona)\n"
                    + "             WHEN 'Inscripción' OR 'Inscripción Lenguas'\n"
                    + "             THEN\n"
                    + "			(SELECT nivel FROM carrera JOIN inscripciones_carreras ON (carrera.id = inscripciones_carreras.id_carrera) JOIN operaciones_detalle ON (inscripciones_carreras.id = operaciones_detalle.relacion) WHERE operaciones_detalle.id = detalle_operacion)	 \n"
                    + "         END AS niveles, \n"
                    + "         CASE operaciones_detalle.tipo\n"
                    + "             WHEN 'Cuota'\n"
                    + "             THEN\n"
                    + "                 (SELECT num_cuenta FROM niveles_cuentas_cuotas WHERE nivel = niveles AND devengamiento = 0)\n"
                    + "             WHEN 'Inscripción' OR 'Inscripción Lenguas'\n"
                    + "             THEN\n"
                    + "			(SELECT num_cuenta FROM niveles_cuentas_inscripcion WHERE nivel = niveles AND devengamiento = 0)\n"
                    + "             WHEN 'Deuda 2013'\n"
                    + "             THEN\n"
                    + "                 (SELECT IF(niveles = 'Ex Alumnos',(SELECT valor FROM variables WHERE nombre = 'cuenta_ex_alumnos'),(SELECT num_cuenta FROM niveles_cuentas_cuotas WHERE nivel = niveles AND devengamiento = 0)))\n"
                    + "             WHEN 'Cuota Bonificada'\n"
                    + "             THEN\n"
                    + "			(SELECT \n"
                    + "                     CASE niveles\n"
                    + "                         WHEN 'Nivel Inicial'\n"
                    + "				THEN\n"
                    + "                             (SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_inicial')\n"
                    + "				WHEN 'Nivel Primario'\n"
                    + "				THEN\n"
                    + "                             (SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_primario')\n"
                    + "				WHEN 'Nivel Secundario'\n"
                    + "				THEN\n"
                    + "                             (SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_secundario')\n"
                    + "				WHEN 'Nivel Terciario'\n"
                    + "				THEN\n"
                    + "                             (SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_terciario')\n"
                    + "				WHEN 'Nivel Lenguas'\n"
                    + "				THEN\n"
                    + "                             (SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_lenguas')\n"
                    + "                     END\n"
                    + "			)\n"
                    + "         END AS numero_cuenta					  \n"
                    + "     FROM operaciones\n"
                    + "     JOIN operaciones_detalle\n"
                    + "     ON (operaciones.id = operaciones_detalle.id_operacion)\n"
                    + "     WHERE operaciones.sector = 'Institucional' AND operaciones.estado = 'A' AND operaciones.fecha = '" + fecha + "'\n"
                    + "     GROUP BY tipo, niveles");
            
            reg = 0;
            
            if (rs.next()) {
                rs.last();
                reg = rs.getRow();
                rs.first();
            }
            
            for (int a = 0; a < reg; a++) {
                //esto es para el tipo de operacion = "Cuota" e "Inscripcion", etc
                String tipo = rs.getString(1);
                BigDecimal monto = rs.getBigDecimal(2);
                String nivel = rs.getString(3);
                
                if (tipo.toLowerCase().equals("cuota")) {
                    ResultSet rs2 = st2.executeQuery("SELECT num_cuenta,haber FROM niveles_cuentas_cuotas WHERE nivel = '"+nivel+"' AND devengamiento = 0");
                    
                    int reg2 = 0;

                    if (rs2.next()) {
                        rs2.last();
                        reg2 = rs2.getRow();
                        rs2.first();
                    }
                    
                    for (int b = 0; b < reg2; b++) {
                        String cuenta = rs2.getString(1);
                        int haber = rs2.getInt(2);
                        if (haber == 1) {
                            Clases.Asientos.InsertarHaber(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                        } else {
                            Clases.Asientos.InsertarDebe(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                        }
                        
                        rs2.next();
                    }
                    rs2.close();
                } else if (tipo.toLowerCase().equals("inscripción")) {
                    ResultSet rs2 = st2.executeQuery("SELECT num_cuenta,haber FROM niveles_cuentas_inscripcion WHERE nivel = '"+nivel+"' AND devengamiento = 0");
                    
                    int reg2 = 0;

                    if (rs2.next()) {
                        rs2.last();
                        reg2 = rs2.getRow();
                        rs2.first();
                    }
                    
                    for (int b = 0; b < reg2; b++) {
                        String cuenta = rs2.getString(1);
                        int haber = rs2.getInt(2);
                        if (haber == 1) {
                            Clases.Asientos.InsertarHaber(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                        } else {
                            Clases.Asientos.InsertarDebe(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                        }
                        
                        rs2.next();
                    }
                    rs2.close();
                } else if (tipo.toLowerCase().equals("cuota bonificada")) {
                    ResultSet rs2 = st2.executeQuery("SELECT valor FROM variables WHERE nombre = 'asda'");
                    
                    int reg2 = 0;

                    if (rs2.next()) {
                        rs2.last();
                        reg2 = rs2.getRow();
                        rs2.first();
                    }
                    
                    for (int b = 0; b < reg2; b++) {
                        String cuenta = rs2.getString(1);
                        Clases.Asientos.InsertarHaber(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                        rs2.next();
                    }
                    rs2.close();
                }
                
                rs.next();
            }
            rs.close();
            
            if (reg > 0) {
                st.executeUpdate("UPDATE operaciones SET estado = 'C' WHERE operaciones.sector = 'Institucional' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'");
            }
            
            //RS DE ALQUILERES
            rs = st.executeQuery("SELECT operaciones.tipo_pago,\n"
                    + "SUM(operaciones_detalle.monto), tipo_pago_global.num_cuenta\n"
                    + "FROM operaciones JOIN operaciones_detalle ON\n"
                    + "(operaciones.id = operaciones_detalle.id_operacion)\n"
                    + "JOIN tipo_pago_global ON (operaciones.tipo_pago = tipo_pago_global.tipo_pago)\n"
                    + "WHERE operaciones.sector = 'Alquileres' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'\n"
                    + "GROUP BY tipo_pago");
            
            reg = 0;
            
            if (rs.next()) {
                rs.last();
                reg = rs.getRow();
                rs.first();
            }
            
            monto_total = BigDecimal.ZERO;

            for (int a = 0; a < reg; a++) {
                //para registrar el pago del activo (efectivo etc.)
                BigDecimal monto = rs.getBigDecimal(2);
                String cuenta = rs.getString(3);
                
                monto_total = monto_total.add(monto);
                
                Clases.Asientos.InsertarDebe(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                rs.next();
            }
            
            rs.close();
            
            //para registrar el asiento del haber con su cuenta correspondiente
            //NECESITO SABER QUE CONTRATO ES EL QUE SE ESTA PAGANDO Y NO LA PERSONA RESPONSABLE DEL ALQUILER
            
            //EN OTRAS PALABRAS, CUANDO PAGO LA CUOTA EN VEZ DE MANDAR LA PK RESPONSABLE TENGO QUE MANDAR LA PK DEL CONTRATO
            
            //PARA MANDAR LA PK DEL CONTRATO TENGO QUE CAMBIAR EL DIALOG SeleccionarAlquilerEnCuotas Y
            //LA CLASE QUE HACE EL SELECT DE LOS LOTES
            rs = st.executeQuery("SELECT \n"
                    + "SUM(operaciones_detalle.monto), alquileres_contratos.cuenta\n"
                    + "FROM operaciones JOIN operaciones_detalle ON\n"
                    + "(operaciones.id = operaciones_detalle.id_operacion)\n"
                    + "JOIN alquileres_contratos ON (operaciones.pk_persona = alquileres_contratos.id)\n"
                    + "WHERE operaciones.sector = 'Alquileres' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'\n"
                    + "GROUP BY cuenta");
            
            reg = 0;
            
            if (rs.next()) {
                rs.last();
                reg = rs.getRow();
                rs.first();
            }
            
            for (int a = 0; a < reg; a++) {
                BigDecimal monto = rs.getBigDecimal(1);
                String cuenta = rs.getString(2);
                
                Clases.Asientos.InsertarHaber(fecha, cuenta, "Cierre de Caja", monto.toString(), hora, asiento);
                rs.next();
            }
            
            if (reg > 0) {
                st.executeUpdate("UPDATE operaciones SET estado = 'C' WHERE operaciones.sector = 'Alquileres' AND operaciones.fecha = '"+fecha+"' AND operaciones.estado = 'A'");
            }
            
            rs.close();
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
}
