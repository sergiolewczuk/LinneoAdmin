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
public class Mayor {
    public Object[][] datos; //Variable que alberga los datos de la BD
    
    public BigDecimal[] Cargar(String num, Boolean sin_arrastre, String desde, String hasta) {
        BigDecimal[] dou = {BigDecimal.ZERO,BigDecimal.ZERO};
        if (!"".equals(num)) {
            try {
                ResultSet rs;
                Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                if (sin_arrastre) {
                    if ("".equals(desde) || "".equals(hasta)) {
                        //SIN FECHA
                        rs = st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,detalle,debe,haber\n" +
                                                "FROM asientos_detalle\n" +
                                                "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                                                "WHERE num_cuenta = " + num + " and asientos_detalle.id_asiento != 1 and YEAR(asientos.fecha) = "+Principal.anio_usado+"\n" +
                                                "AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')");
                    } else {
                        //CON FECHA
                        rs = st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,detalle,debe,haber\n" +
                                                "FROM asientos_detalle\n" +
                                                "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                                                "WHERE num_cuenta = " + num + " and asientos_detalle.id_asiento != 1 and (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"')\n" +
                                                "AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')");
                    }
                } else {
                    //CON ARRASTRE
                    if ("".equals(desde) || "".equals(hasta)) {
                        //SIN FECHA
                        rs = st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,detalle,debe,haber\n" +
                                                "FROM asientos_detalle\n" +
                                                "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                                                "WHERE num_cuenta = " + num + " and YEAR(asientos.fecha) = "+Principal.anio_usado+"\n" +
                                                "AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')");
                    } else {
                        //CON FECHA
                        
                        Integer dia_inicial;
                        Integer mes_inicial;
                        
                        dia_inicial = Integer.valueOf(desde.split("-", 3)[2]);
                        mes_inicial = Integer.valueOf(desde.split("-", 3)[1]);
                        
                        if (dia_inicial == 1 && mes_inicial == 1) {
                            //IGUAL EL 1 DEL 1 EN ADELANTE
                            //tengo q tomar como arrastre el saldo de la cuenta en el primer asiento
                            rs = st.executeQuery("SELECT asientos.fecha,asientos_detalle.id_asiento,detalle,debe,haber\n" +
                                                "FROM asientos_detalle\n" +
                                                "JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio)\n" +
                                                "WHERE num_cuenta = " + num + " and (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"')\n" +
                                                "AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A')");
                        } else {
                            //MAYOR AL 1 DEL 1 EN ADELANTE
                            //tengo q tomar como arrastre el saldo total desde (la fecha 1/1 sin el asiento 1) hasta (una fecha antes de la fecha_desde)
                            //¿INCLUYO EL PRIMERO ASIENTO? por ahora SI //para no incluir el: 1 and id_asiento != 1//
                            
                            String desde_arrastre = Principal.anio_usado + "-1-1";
                            
                            rs = st.executeQuery("(SELECT '','','ARRASTRE',SUM(debe),SUM(haber) FROM asientos_detalle JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE num_cuenta = " + num + " and (asientos.fecha >= '"+desde_arrastre+"' and asientos.fecha <= DATE('"+desde+"') - INTERVAL 1 DAY) AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A'))" +
                                    " UNION (SELECT asientos.fecha,asientos_detalle.id_asiento,detalle,debe,haber FROM asientos_detalle JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE num_cuenta = " + num + " and (asientos.fecha >= '"+desde+"' and asientos.fecha <= '"+hasta+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A'))");
                        }
                    }
                }
                rs.last();

                Integer registros = rs.getRow();
                datos = new Object[registros][8];

                BigDecimal total_debe = BigDecimal.ZERO;
                BigDecimal total_haber = BigDecimal.ZERO;
                
                BigDecimal count = BigDecimal.ZERO;
                Boolean empezo_debe = null;
                
                int color = 0;
                String ultimo_pk = "";

                rs.first();
                for (int fila = 0; fila < registros; fila++) {
                    for (int cols = 0; cols < 8; cols++) {
                        if (cols >= 5) {
                            //datos[fila][cols] = null;
                            if (cols == 5 && rs.getString(4) != null) {
                                if (fila == 0) {
                                    empezo_debe = true;
                                }
                                
                                if (empezo_debe) {
                                    count = count.add(rs.getBigDecimal(4));
                                } else {
                                    count = count.subtract(rs.getBigDecimal(5));
                                }
                            } else if (cols == 6 && rs.getString(5) != null) {
                                if (fila == 0) {
                                    empezo_debe = false;
                                }
                                
                                if (empezo_debe) {
                                    count = count.subtract(rs.getBigDecimal(5));
                                } else {
                                    count = count.add(rs.getBigDecimal(5));
                                }
                            } else if (cols == 7) {
                                //columna del cambio de color
                                //compruebo q la variable pk sea diferente
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
                            }
                            
                            if (count.compareTo(BigDecimal.ZERO) == 1) {
                                if (empezo_debe) {
                                    datos[fila][5] = df.format(count);
                                    datos[fila][6] = null;
                                } else {
                                    datos[fila][6] = df.format(count);
                                    datos[fila][5] = null;
                                }
                            } else {
                                BigDecimal count_temp = count.negate();
                                if (!empezo_debe) {
                                    datos[fila][5] = df.format(count_temp);
                                    datos[fila][6] = null;
                                } else {
                                    datos[fila][6] = df.format(count_temp);
                                    datos[fila][5] = null;
                                }
                            }
                        } else {
                            datos[fila][cols] = rs.getObject(cols + 1);
                        }

                        if (cols == 3 && datos[fila][cols] != null) {
                            total_debe = total_debe.add(rs.getBigDecimal(cols + 1));
                            datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                        } else if (cols == 4 && datos[fila][cols] != null) {
                            total_haber = total_haber.add(rs.getBigDecimal(cols + 1));
                            datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                        }

                        //System.out.println(datos[fila][cols]);
                    }
                    rs.next();
                }
                rs.close();
                dou[0] = total_debe;
                dou[1] = total_haber;
                //System.out.println(datos.toString());
            } catch (Exception ex) {
                System.out.println("Se han producido excepciones: " + ex);
            }
        }
        return dou;
    }
    
    public BigDecimal[] balance_comprobacion(String tipo, String fecha, String fecha_desde) {
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO};
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            if (tipo.equals("Histórico")) {
                if (fecha_desde == null) {
                    rs = st.executeQuery("SELECT asientos_detalle.num_cuenta,nombre,IFNULL(SUM(debe),0) AS debe,IFNULL(SUM(haber),0) AS haber,IF(IFNULL(SUM(debe),0) > IFNULL(SUM(haber),0),IFNULL(SUM(debe),0) - IFNULL(SUM(haber),0),0) AS deudor, IF(IFNULL(SUM(haber),0) > IFNULL(SUM(debe),0),IFNULL(SUM(haber),0) - IFNULL(SUM(debe),0),0) as acreedor FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE (asientos.fecha >= '"+Principal.anio_usado+"-01-01' and asientos.fecha <= '"+fecha+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A') GROUP BY asientos_detalle.num_cuenta,nombre");
                } else {
                    rs = st.executeQuery("SELECT asientos_detalle.num_cuenta,nombre,IFNULL(SUM(debe),0) AS debe,IFNULL(SUM(haber),0) AS haber,IF(IFNULL(SUM(debe),0) > IFNULL(SUM(haber),0),IFNULL(SUM(debe),0) - IFNULL(SUM(haber),0),0) AS deudor, IF(IFNULL(SUM(haber),0) > IFNULL(SUM(debe),0),IFNULL(SUM(haber),0) - IFNULL(SUM(debe),0),0) as acreedor FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE (asientos.fecha >= '"+fecha_desde+"' and asientos.fecha <= '"+fecha+"') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A') GROUP BY asientos_detalle.num_cuenta,nombre");
                }
            } else {
                rs = st.executeQuery("SELECT asientos_detalle.num_cuenta,nombre,IFNULL(SUM(debe),0) AS debe,IFNULL(SUM(haber),0) AS haber,IF(IFNULL(SUM(debe),0) > IFNULL(SUM(haber),0),IFNULL(SUM(debe),0) - IFNULL(SUM(haber),0),0) AS deudor, IF(IFNULL(SUM(haber),0) > IFNULL(SUM(debe),0),IFNULL(SUM(haber),0) - IFNULL(SUM(debe),0),0) as acreedor FROM asientos_detalle JOIN cuentas ON (asientos_detalle.num_cuenta = cuentas.num_cuenta) JOIN asientos ON (asientos_detalle.id_asiento = asientos.id_asiento AND asientos.anio = asientos_detalle.anio) WHERE (asientos.fecha >= '"+Principal.anio_usado+"-"+fecha+"-01' and asientos.fecha <= '"+Principal.anio_usado+"-"+fecha+"-31') AND (asientos.estado = 'A' AND asientos_detalle.estado = 'A') GROUP BY asientos_detalle.num_cuenta,nombre");
            }
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][6];
            
            BigDecimal tot_debe = BigDecimal.ZERO;
            BigDecimal tot_haber = BigDecimal.ZERO;
            BigDecimal tot_deudor = BigDecimal.ZERO;
            BigDecimal tot_acreedor = BigDecimal.ZERO;

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 6; cols++) {
                    if (cols >=2) {
                        datos[fila][cols] = df.format(rs.getBigDecimal(cols + 1));
                    } else {
                        datos[fila][cols] = rs.getObject(cols + 1);
                    }
                    
                    if (cols == 2) {
                        tot_debe = tot_debe.add(rs.getBigDecimal(cols + 1));
                    } else if (cols == 3) {
                        tot_haber = tot_haber.add(rs.getBigDecimal(cols + 1));
                    } else if (cols == 4) {
                        tot_deudor = tot_deudor.add(rs.getBigDecimal(cols + 1));
                    } else if (cols == 5) {
                        tot_acreedor = tot_acreedor.add(rs.getBigDecimal(cols + 1));
                    }
                    //System.out.println(datos[fila][cols]);
                }
                rs.next();
            }
            rs.close();
            
            doble[0] = tot_debe;
            doble[1] = tot_haber;
            doble[2] = tot_deudor;
            doble[3] = tot_acreedor;
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
        return doble;
    }
}
