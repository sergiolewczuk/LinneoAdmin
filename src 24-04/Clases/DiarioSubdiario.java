/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author jason
 */
public class DiarioSubdiario {
    public Object[][] datos;
    
    public BigDecimal[] CargarDiario(){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,asientos_detalle.num_cuenta,nombre,detalle,debe,haber FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE YEAR(asientos.fecha) = "+Principal.anio_usado+" AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A') ORDER BY fecha,id_asiento,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];

        int color = 0;
        String ultimo_pk = "";
        
        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
    
    public BigDecimal[] CargarDiario(String desde, String hasta){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,asientos_detalle.num_cuenta,nombre,detalle,debe,haber FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A') ORDER BY fecha,id_asiento,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];

        int color = 0;
        String ultimo_pk = "";
        
        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
    
    public BigDecimal[] CargarDiarioResumido(){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT * FROM (\n"
                + "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL,SUM(debe) as debe,NULL as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE YEAR(asientos.fecha) = "+Principal.anio_usado+" AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(debe) IS NOT NULL\n" +
                            "UNION\n" +
                            "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL,NULL as debe,SUM(haber) as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE YEAR(asientos.fecha) = "+Principal.anio_usado+" AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(haber) IS NOT NULL) as tabla ORDER BY fecha,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];
        
        int color = 0;
        String ultimo_pk = "";

        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
    
    public BigDecimal[] CargarDiarioResumido(String desde, String hasta){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT * FROM(\n"
                + "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL,SUM(debe) as debe,NULL as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(debe) IS NOT NULL\n" +
                            "UNION\n" +
                            "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL,NULL as debe,SUM(haber) as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(haber) IS NOT NULL) as tabla ORDER BY fecha,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];

        int color = 0;
        String ultimo_pk = "";
        
        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
    
    public BigDecimal[] CargarSUBDiario(){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,asientos_detalle.num_cuenta,nombre,detalle,debe,haber FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE (asientos_detalle.num_cuenta = 111001 or asientos_detalle.num_cuenta = 111004 or asientos_detalle.num_cuenta = 112001) and YEAR(asientos.fecha) = "+Principal.anio_usado+" AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A') ORDER BY fecha,id_asiento,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];
        
        int color = 0;
        String ultimo_pk = "";

        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
    
    public BigDecimal[] CargarSUBDiario(String desde, String hasta){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

        rs=st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,asientos_detalle.num_cuenta,nombre,detalle,debe,haber FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE (asientos_detalle.num_cuenta = 111001 or asientos_detalle.num_cuenta = 111004 or asientos_detalle.num_cuenta = 112001) and (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A') ORDER BY fecha,id_asiento,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];

        int color = 0;
        String ultimo_pk = "";
        
        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
    
    public BigDecimal[] CargarSUBDiarioResumido(){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT * FROM (\n"
                + "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL, SUM(debe) as debe, NULL as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE (asientos_detalle.num_cuenta = 111001 or asientos_detalle.num_cuenta = 111004 or asientos_detalle.num_cuenta = 112001)\n" +
                            "and YEAR(asientos.fecha) = "+Principal.anio_usado+" AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(debe) IS NOT NULL\n" +
                            "UNION\n" +
                            "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL, NULL as debe, SUM(haber) as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE (asientos_detalle.num_cuenta = 111001 or asientos_detalle.num_cuenta = 111004 or asientos_detalle.num_cuenta = 112001)\n" +
                            "and YEAR(asientos.fecha) = "+Principal.anio_usado+" AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(haber) IS NOT NULL) as tabla ORDER BY fecha,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];

        int color = 0;
        String ultimo_pk = "";
        
        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
    
    public BigDecimal[] CargarSUBDiarioResumido(String desde, String hasta){
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT * FROM (\n"
                + "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL, SUM(debe) as debe, NULL as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE (asientos_detalle.num_cuenta = 111001 or asientos_detalle.num_cuenta = 111004 or asientos_detalle.num_cuenta = 112001)\n" +
                            "and (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(debe) IS NOT NULL\n" +
                            "UNION\n" +
                            "SELECT asientos.fecha,'',asientos_detalle.num_cuenta,nombre,NULL, NULL as debe, SUM(haber) as haber\n" +
                            "FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta)\n" +
                            "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                            "WHERE (asientos_detalle.num_cuenta = 111001 or asientos_detalle.num_cuenta = 111004 or asientos_detalle.num_cuenta = 112001)\n" +
                            "and (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')\n" +
                            "GROUP BY asientos.fecha,asientos_detalle.num_cuenta\n" +
                            "HAVING SUM(haber) IS NOT NULL) as tabla ORDER BY fecha,debe DESC,haber DESC");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][8];
        
        int color = 0;
        String ultimo_pk = "";

        rs.first();
        for (int fila = 0; fila < registros; fila++) {
            for (int cols = 0; cols < 8; cols++) {
                if (cols == 7) {
                    if (!ultimo_pk.equals(datos[fila][0].toString())) {
                        //cambio el color
                        if (color == 0) {
                            color = 1;
                        } else {
                            color = 0;
                        }
                    }
                    ultimo_pk = datos[fila][0].toString();
                    datos[fila][7] = color;
                } else {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
                //System.out.println(datos[fila][cols]);
                
                if (cols == 5 && datos[fila][cols] != null) {
                    doble[0] = doble[0].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
                if (cols == 6 && datos[fila][cols] != null) {
                    doble[1] = doble[1].add(rs.getBigDecimal(cols + 1));
                    datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                }
            }
        rs.next();
        }
        rs.close();
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones: "+ex);
        }
        return doble;
    }
}