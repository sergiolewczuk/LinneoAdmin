/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Pantallas.Frames.Principal;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;


public class Cuotas {
    
    public Object[][] datos; //Variable que alberga los datos de la BD
    
    
    public void CargarTablaOperaciones(String pk_persona, String sector) {
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT DISTINCT operaciones.id\n"
                    + "FROM operaciones\n"
                    + "WHERE pk_persona = "+pk_persona+" AND sector = '"+sector+"'");
            
            rs.last();
        
            if (rs.getRow() > 0) {
                Integer cant_operaciones = rs.getRow();

                rs.close();

                rs = st.executeQuery("SELECT IF(estado='A','Activo','Anulado'), operaciones.id, operaciones.fecha, operaciones.hora, IF(operaciones.num_recibo='null','',operaciones.num_recibo), "
                        + "operaciones_detalle.detalle, operaciones_detalle.monto, operaciones_detalle.tipo, IFNULL(operaciones.motivo_baja,'') \n"
                        + "FROM operaciones\n"
                        + "JOIN operaciones_detalle ON (operaciones_detalle.id_operacion = operaciones.id)\n"
                        + "WHERE pk_persona = " + pk_persona + " AND sector = '" + sector + "'");

                rs.last();

                Integer registros = rs.getRow();

                datos = new Object[registros + cant_operaciones + cant_operaciones + cant_operaciones - 1][8];

                Integer operacion = 0;
                BigDecimal monto_total_operacion = BigDecimal.ZERO;
                Integer cant_row_rs = 1;

                rs.first();

                for (int a = 0; a < datos.length; a++) {
                    if (operacion.equals(rs.getInt(2))) {
                        datos[a][0] = "";
                        datos[a][1] = "";
                        datos[a][2] = "";
                        datos[a][3] = "";
                        if (rs.getString(6).contains("Bonificacion")) {
                            datos[a][4] = rs.getString(6);
                            datos[a][5] = "-" + rs.getString(7);

                            monto_total_operacion = monto_total_operacion.subtract(rs.getBigDecimal(7));
                        } else {
                            datos[a][4] = rs.getString(6);
                            datos[a][5] = rs.getString(7);
                            monto_total_operacion = monto_total_operacion.add(rs.getBigDecimal(7));
                        }

                        datos[a][6] = "";

                        if (Objects.equals(cant_row_rs, registros)) {
                            // ES ULTIMO ROW
                            a++;
                            datos[a][0] = "";
                            datos[a][1] = "";
                            datos[a][2] = "";
                            datos[a][3] = "";
                            datos[a][4] = "";
                            datos[a][5] = "";
                            datos[a][6] = monto_total_operacion;

                            break;
                        } else {
                            cant_row_rs++;
                            rs.next();

                            if (!operacion.equals(rs.getInt(2))) {  // LA SIGUIENTE OPERACION ES DIFERENTE, VOLVER EL RS. Y PONER LA LINEA SOLO CON EL MONTO_TOTAL
                                a++;
                                datos[a][0] = "";
                                datos[a][1] = "";
                                datos[a][2] = "";
                                datos[a][3] = "";
                                datos[a][4] = "";
                                datos[a][5] = "";
                                datos[a][6] = monto_total_operacion;
                                datos[a][7] = "";

                                a++;
                                datos[a][0] = "";
                                datos[a][1] = "xxxxxxxxxx";
                                datos[a][2] = "xxxxxxxxxx";
                                datos[a][3] = "xxxxxxxxxxxxxxxxx";
                                datos[a][4] = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
                                datos[a][5] = "xxxxxxxxxx";
                                datos[a][6] = "xxxxxxxxxx";
                                datos[a][7] = "";

                            }
                        }
                    } else {// ES NUEVA OPERACION, AGREGAR LA PRIMER LINEA EN BLANCO Y LA SEGUNDA PONER SOLO ID_OPERACION, FECHA, HORA, NUM_RECIBO

                        operacion = rs.getInt(2);

                        monto_total_operacion = BigDecimal.ZERO;

                        datos[a][0] = rs.getString(2);
                        datos[a][1] = rs.getString(3);
                        datos[a][2] = rs.getString(4);
                        datos[a][3] = rs.getString(5);
                        datos[a][4] = "";
                        datos[a][5] = "";
                        datos[a][6] = "";

                        if (rs.getString(1).equals("Anulado")) {
                            datos[a][7] = rs.getString(9);
                        }
                    }
                }
            } else {
                datos = null;
            }
            
            
            
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarTablaOperaciones    Cuotas: " + ex);
        }
    }
    
    // ESTA FUNCION VA EN OPERACIONES DESPUES DE QUE ME LO PASE JASON!!!!!!
    
    public static String ComprobarOperacion(Integer id_operacion) {
        String info = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT operaciones_detalle.tipo, operaciones.sector \n"
                    + "FROM operaciones_detalle \n"
                    + "JOIN operaciones ON operaciones.id = operaciones_detalle.id\n"
                    + "WHERE operaciones.id = "+id_operacion+"");
            
            rs.last();
            
            Integer registros = rs.getRow();
            
            for (int a = 0; a < registros; a++) {
                if (rs.getString(2).equals("Institucional")) {
                    if (rs.getString(1).contains("Inscripción")) {
                        info = "Inscripción";
                    }
                    if (rs.getString(1).contains("Cuota")) {
                        info = "Cuotas";
                    }
                }
                if (rs.getString(2).equals("Alquileres")) {
                    if (rs.getString(1).contains("Inscripción")) {
                        info = "Inscripción";
                    }
                    if (rs.getString(1).contains("Cuota")) {
                        info = "Cuotas";
                    }
                }
                if (rs.getString(2).equals("Socios")) {
                    if (rs.getString(1).contains("Inscripción")) {
                        info = "Inscripción";
                    }
                    if (rs.getString(1).contains("Cuota")) {
                        info = "Cuotas";
                    }
                }
                if (rs.getString(2).equals("Cementerio")) {
                    if (rs.getString(1).contains("Inscripción")) {
                        info = "Inscripción";
                    }
                    if (rs.getString(1).contains("Cuota")) {
                        info = "Cuotas";
                    }
                }
                if (rs.getString(2).equals("Otros")) {
                    if (rs.getString(1).contains("Inscripción")) {
                        info = "Inscripción";
                    }
                    if (rs.getString(1).contains("Cuota")) {
                        info = "Cuotas";
                    }
                }
            }
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    ComprobarOperacion    Operaciones(cuotas por ahora): " + ex);
        }
        return info;
    }
    
    //          ALUMNADO
    
    public static void GenerarCuotasAlumnos(String id_alu, String id_carr, Object id_grado, String anio_t, String tipo, int mes_desde, int mes_hasta, String fecha, String hora, String monto_beca) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        Integer mes_actual = Integer.valueOf(Principal.fecha_actual.split("-")[1]);
        Integer anio_actual = Integer.valueOf(Principal.fecha_actual.split("-")[2]);
        
        String id_actual = null; 
        if (Objects.equals(Integer.valueOf(anio_t), anio_actual) && mes_desde <= mes_actual) {
            //GENERAR EL ASIENTO PADRE
            id_actual = Clases.Asientos.IdActual(anio_t).toString();
            
            Clases.Asientos.InsertarAsiento(hora, fecha);
        }
        
        for (int z = mes_desde; z <= mes_hasta; z++) {
            if (id_grado != null) {
                st.executeUpdate("INSERT INTO cuotas_alumnado (id_alumno, id_carrera, id_grado, anio_t, mes_t, tipo) VALUES ("+id_alu+","+id_carr+","+id_grado.toString()+","+anio_t+","+z+",'"+tipo+"')");
            } else {
                st.executeUpdate("INSERT INTO cuotas_alumnado (id_alumno, id_carrera, anio_t, mes_t, tipo) VALUES ("+id_alu+","+id_carr+","+anio_t+","+z+",'"+tipo+"')");
            }
            
            // COMPARA MES ACTUAL CON MES DESDE, Y AÑO PARA DEVENGAMIENTO
            if (Objects.equals(Integer.valueOf(anio_t), anio_actual) && z <= mes_actual) {
                // DEVENGA LA CUOTA
                //obtengo el nivel de la carrera
                
                Object[] datos = Clases.Cursos.MostrarDatosCarreras(id_carr);
                String nivel = datos[8].toString();
                //AGARRO EL MONTO ACTUAL
                BigDecimal monto;
                
                //AGARRO EL MONTO VIEJO
                //tabla de periodos montos de cuotas
               
                if (id_grado != null && monto_beca.isEmpty()) {
                    datos = Clases.Cursos.MostrarDatosGrados(id_grado.toString());
                    //AGARRO EL MONTO ACTUAL
                    monto = new BigDecimal(datos[10].toString());
                    //AGARRO EL MONTO VIEJO
                    //tabla de periodos montos de cuotas
                } else if (id_grado == null && monto_beca.isEmpty()) {
                    monto = new BigDecimal(datos[5].toString());
                } else {
                    // ES BECADO
                    monto = new BigDecimal(monto_beca);
                }
                
               
                
                //obtengo las cuentas a usar
                String[][] cuentas = Clases.Cuentas.CuentasCuotasDevengamiento(nivel);
                for (int a = 0; a < cuentas.length; a++) {
                    //generar LOS detalles
                    //preguntar si la cuenta a ingresar es en el debe o haber
                    String cuenta = cuentas[a][0];
                    String haber = cuentas[a][1];
                    
                    //obtengo el id de la cuota del mes insertada
                    String cuota = DatosCuota(anio_t, String.valueOf(z), id_alu, id_carr);
                    
                    if (haber.equals("1")) {
                        //inserto en el haber
                        Clases.Asientos.InsertarDetalleDevengamientoHaber(fecha, cuenta, "Devengamiento", monto.toString(), hora, id_actual, cuota);
                    } else {
                        //inserto en el debe
                        Clases.Asientos.InsertarDetalleDevengamientoDebe(fecha, cuenta, "Devengamiento", monto.toString(), hora, id_actual, cuota);
                    }
                }
            }
        }
       
        }catch(Exception ex){
            //System.out.println("Se han producido excepciones        GenerarCuotasAlumnos     Cuotas: "+ex);
    
            StackTraceElement l = ex.getStackTrace()[0];
            System.out.println(
                    l.getClassName() + "/" + l.getMethodName() + ":" + l.getLineNumber());
        }
    }
    
    public static String DatosCuota(String anio, String mes, String alumno, String carrera) {
        String id = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT id FROM cuotas_alumnado WHERE id_alumno = "+alumno+" AND id_carrera = "+carrera+" AND anio_t = "+anio+" AND mes_t = "+mes+"");

            rs.first();
            
            id = rs.getString(1);
            
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarAlumnosCuotas    Cuotas: " + ex);
        }
        
        return id;
    }
    
    public void CargarAlumnosCuotas(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, COUNT(inscripciones_carreras.id_alumno) "
                        + "FROM alumnos "
                        + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno "
                        + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id "
                        + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera "
                        + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado "
                        + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C'"
                        + "GROUP BY id_alumno");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][2];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);  // ID_ALUMNO
                datos[fila][1] = rs.getObject(2);  // APENOM_ALUMNO

                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarAlumnosCuotas    Cuotas: " + ex);
        }    
    }
    
    public void CargarDeudoresCuotas(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, deudores_temp.curso "
                            + "FROM alumnos "
                            + "INNER JOIN deudores_temp ON deudores_temp.id_alumno = alumnos.id_alumno "
                            + "GROUP BY id_alumno");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][2];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);   // ID_ALUMNO
                datos[fila][1] = rs.getObject(2);   // APENOM_ALUMNO
                
                
                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarAlumnosCuotas    Cuotas: " + ex);
        }    
    }
    
    public static String[] EncontroAlumnoCuotas(String cod){
        String[] Info = null;
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, COUNT(inscripciones_carreras.id_alumno) "
                        + "FROM alumnos "
                        + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno "
                        + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id "
                        + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera "
                        + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado "
                        + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND alumnos.id_alumno = "+cod+"");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if(registros > 0){
            Info = new String[3];
                
            Info[0] = rs.getString(1);  // DNI_ALUMNO
            Info[1] = rs.getString(2);  // APENOM_ALUMNO
            
            Integer cantidad = rs.getInt(3);
            
            rs.close();
            
            String cant_cursos = null;
            if (cantidad > 0) {    // CANT_CURSOS
                rs = st.executeQuery("SELECT IFNULL(IFNULL(CONCAT(carrera_grados.num_curso,'-',carrera_grados.div_curso, ' ', carrera.nombre),CONCAT(carrera_grados.div_curso,' ',carrera.nombre)), carrera.nombre) "
                        + "FROM alumnos "
                        + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno "
                        + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id "
                        + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera "
                        + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado "
                        + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C 'AND alumnos.id_alumno = '" + Info[0] + "'");
                rs.last();

                Integer registros2 = rs.getRow();
                rs.first();

                for (int fi = 0; fi < registros2; fi++) {
                    if (fi == 0) {
                        cant_cursos = rs.getString(1);  // NOMBRE_CURSADO
                    } else {
                        cant_cursos = cant_cursos + " - " + rs.getString(1);  // NOMBRE_CURSADO
                    }
                    rs.next();
                }
                rs.close();
            }
            Info[2] = cant_cursos;  // NOMBRE_CARRERA
            
            for (int a = 0; a < Info.length; a++) {
                if (Info[a] == null) {
                    Info[a] = "";
                }
            }
        }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    EncontroAlumnoCuotas     Cuotas: "+ex);
        }
        return Info;
    }
    
    public static String[] EncontroDeudorCuotas(String cod) {
        String[] Info = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, deudores_temp.curso "
                            + "FROM alumnos "
                            + "INNER JOIN deudores_temp ON deudores_temp.id_alumno = alumnos.id_alumno "
                            + "WHERE deudores_temp.id_alumno = '" + cod + "'");
            rs.last();

            Integer registros = rs.getRow();
            
            rs.first();
            
            if (registros > 0) {
                Info = new String[3];
                
                for (int fila = 0; fila < registros; fila++) {

                    Info[0] = rs.getString(1);      // ID_ALUMNO
                    Info[1] = rs.getString(2);      // APENOM_ALUMNO
                    Info[2] = rs.getString(3);      // CURSO 2014
                    
                    rs.next();
                }
            }
            
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    EncontroDeudorCuotas    Cuotas: " + ex);
        }
        return Info;
    }
    
    public void FiltrarAlumnoCuotas(String alumno){
        try{
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, COUNT(inscripciones_carreras.id_alumno) "
                        + "FROM alumnos "
                        + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno "
                        + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id "
                        + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera "
                        + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado "
                        + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' "
                        + "AND alumnos.id_alumno LIKE '%"+alumno+"%' OR alumnos.apenom_alumno LIKE '%"+alumno+"%'"
                        + "GROUP BY id_alumno");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][2];
        
        rs.first();
        
        for (int fila = 0; fila < registros; fila++) {
            
            datos[fila][0] = rs.getObject(1);  // DNI_ALUMNO
            datos[fila][1] = rs.getObject(2);  // APENOM_ALUMNO
            
            
        rs.next();
        }    
                
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    FiltrarAlumnoCuotas     Cuotas: " + ex);
        }        
    }
    
    public void FiltrarDeudoresCuotas(String alumno){
        try{
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, deudores_temp.curso "
                            + "FROM alumnos "
                            + "INNER JOIN deudores_temp ON deudores_temp.id_alumno = alumnos.id_alumno "
                            + "WHERE alumnos.id_alumno LIKE '%"+alumno+"%' OR alumnos.apenom_alumno LIKE '%"+alumno+"%'"
                            + "GROUP BY id_alumno");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][2];
        
        rs.first();
        
        for (int fila = 0; fila < registros; fila++) {
            
            datos[fila][0] = rs.getObject(1);  // DNI_ALUMNO
            datos[fila][1] = rs.getObject(2);  // APENOM_ALUMNO
            
        rs.next();
        }    
                
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    FiltrarAlumnoCuotas     Cuotas: " + ex);
        }        
    }
    
    void update_pago_2014(String cod_alumno, String mes, BigDecimal calculo) {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            switch (mes) {
                case "3":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_marzo_2014 = pagado_marzo_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "4":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_abril_2014 = pagado_abril_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "5":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_mayo_2014 = pagado_mayo_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "6":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_junio_2014 = pagado_junio_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "7":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_julio_2014 = pagado_julio_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "8":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_agosto_2014 = pagado_agosto_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "9":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_septiembre_2014 = pagado_septiembre_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "10":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_octubre_2014 = pagado_octubre_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "11":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_noviembre_2014 = pagado_noviembre_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
                case "12":
                    st.executeUpdate("UPDATE deudores_temp SET pagado_diciembre_2014 = pagado_diciembre_2014 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                    break;
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Cuotas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getIdCuotaAlumnos(String alumno, String carrera, String anio_t, String mes_t) {
        String id = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT id FROM cuotas_alumnado WHERE id_alumno = "+alumno+" AND id_carrera = "+carrera+" AND anio_t = "+anio_t+" AND mes_t = "+mes_t+"");
            rs.last();
            
            id = rs.getString(1); 
            
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Cuotas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public void PagarCuotasAlumnos (String cod_alumno, String monto_recibido, TableModel modelo) {
        try {
            //ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            BigDecimal recibido = new BigDecimal(monto_recibido.replace(',', '.'));
            
            for (int a = 0; a < modelo.getRowCount(); a++) {
                if (recibido.compareTo(BigDecimal.ZERO) == 1) {
                    BigDecimal deuda = new BigDecimal(modelo.getValueAt(a, 7).toString());//UPDATE
                    
                    BigDecimal monto_a_pagar;
                    BigDecimal monto_con_descuento;
                    
                    String id_cuota = null;
                    
                    BigDecimal monto_descontado;//UPDATE
                    
                    if ((Boolean)modelo.getValueAt(a, 5)) {
                        monto_a_pagar = new BigDecimal(modelo.getValueAt(a, 6).toString());//UPDATE
                        monto_con_descuento = new BigDecimal(modelo.getValueAt(a, 3).toString());
                        
                        if (monto_a_pagar.compareTo(monto_con_descuento) == 1) {
                            monto_descontado = monto_a_pagar.subtract(monto_con_descuento);
                        } else {
                            monto_descontado = monto_con_descuento.subtract(monto_a_pagar);
                        }
                    } else {
                        monto_a_pagar = new BigDecimal(modelo.getValueAt(a, 3).toString());//UPDATE
                        monto_descontado= BigDecimal.ZERO;
                    }

                    String anio = modelo.getValueAt(a, 0).toString();//UPDATE
                    String mes = modelo.getValueAt(a, 1).toString();//UPDATE
                    String carrera = null;
                    if (modelo.getValueAt(a, 8) != null) {
                        carrera = modelo.getValueAt(a, 8).toString();
                    }
                    
                    if (!modelo.getValueAt(a, 2).toString().contains("Deuda")) {
                        id_cuota = getIdCuotaAlumnos(cod_alumno, carrera, anio, mes);
                    }
                    
                    
                    //UPDATE
                    BigDecimal monto_pagado = new BigDecimal(modelo.getValueAt(a, 4).toString());

                    BigDecimal calculo;
                    if (deuda.compareTo(recibido) == -1 || deuda.compareTo(recibido) == 0) {
                        //cubre la deuda
                        calculo = deuda;
                    } else {
                        //cubre parcial
                        calculo = recibido;
                    }
                    recibido = recibido.subtract(calculo);
                    
                    String mes_nombre = linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes));
                    
                    if (monto_pagado.compareTo(BigDecimal.ZERO) == 0) {
                        //pago por primera vez
                        // saber si completa la cuota o no
                        if (monto_a_pagar.compareTo(calculo.add(monto_descontado)) == 0) {
                            // EL PAGO ES COMPLETO, AVERIGUAR DESCUENTO SI O NO
                            if ((Boolean)modelo.getValueAt(a, 5)) {
                                // CUOTA COMPLETA CON BONIFICACION
                                Clases.Operaciones.GenerarOperacionDetalles("Cuota " + mes_nombre + " de " + anio + ". De " + modelo.getValueAt(a, 2).toString(), monto_a_pagar.toString(),"Cuota",id_cuota);
                                Clases.Operaciones.GenerarOperacionDetalles("Bonificacion " + mes_nombre + " de " + anio + ". De " + modelo.getValueAt(a, 2).toString(), monto_descontado.toString(),"Cuota Bonificada",id_cuota);
                                
                                st.executeUpdate("UPDATE cuotas_alumnado SET monto_a_pagar = " + monto_a_pagar + ", monto_pagado = " + calculo + ", monto_descuento = " + monto_descontado + " WHERE id_alumno = " + cod_alumno + " AND id_carrera = " + carrera + " AND anio_t = " + anio + " AND mes_t = " + mes + "");

                            } else {
                                //  PAGO COMPLETO SIN BONIFICACION
                                String detalle;
                                String tipo;
                                
                                if (modelo.getValueAt(a, 2).toString().contains("2013")) {
                                    // es del 2013 (deuda)
                                    detalle = "Deuda del " + anio + ". De " + modelo.getValueAt(a, 2).toString();
                                    tipo = "Deuda 2013";
                                } else {
                                    //normal
                                    detalle = "Cuota " + mes_nombre + " del " + anio + ". De " + modelo.getValueAt(a, 2).toString();
                                    tipo = "Cuota";
                                }
                                
                                Clases.Operaciones.GenerarOperacionDetalles(detalle, calculo.toString(), tipo, id_cuota);
                                
                                if (modelo.getValueAt(a, 2).toString().contains("Deuda")) {
                                    //Es Deuda
                                    if (modelo.getValueAt(a, 2).toString().contains("2013")) {
                                        //Del 2013
                                        st.executeUpdate("UPDATE deudores_temp SET pagado_deuda_2013 = pagado_deuda_2013 + " + calculo + " WHERE id_alumno = "+cod_alumno);
                                    } else {
                                        //Del 2014
                                        //pregunto el mes (marzo a diciembre)
                                        update_pago_2014(cod_alumno, mes, calculo);
                                    }
                                } else {
                                    st.executeUpdate("UPDATE cuotas_alumnado SET monto_a_pagar = " + monto_a_pagar + ", monto_pagado = "+calculo+", monto_descuento = NULL WHERE id_alumno = "+cod_alumno+" AND id_carrera = "+carrera+" AND anio_t = "+anio+" AND mes_t = "+mes+"");
                                }
                            }                            
                        } else {
                            // EL PAGO ES PARCIAL, NO VA BONIFICACIONES
                            String detalle;
                            String tipo;

                            if (modelo.getValueAt(a, 2).toString().contains("2013")) {
                                // es del 2013 (deuda)
                                detalle = "Deuda Parcial del " + anio + ". De " + modelo.getValueAt(a, 2).toString();
                                tipo = "Deuda 2013";
                            } else {
                                //normal
                                detalle = "Cuota Parcial " + mes_nombre + " del " + anio + ". De " + modelo.getValueAt(a, 2).toString() +". (Debe "+monto_a_pagar.subtract(calculo)+")";
                                tipo = "Cuota";
                            }

                            Clases.Operaciones.GenerarOperacionDetalles(detalle, calculo.toString(),tipo,id_cuota);
                            
                            if (modelo.getValueAt(a, 2).toString().contains("Deuda")) {
                                //Es Deuda
                                if (modelo.getValueAt(a, 2).toString().contains("2013")) {
                                    //Del 2013
                                    st.executeUpdate("UPDATE deudores_temp SET pagado_deuda_2013 = pagado_deuda_2013 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                                } else {
                                    //Del 2014
                                    //pregunto el mes (marzo a diciembre)
                                    update_pago_2014(cod_alumno, mes, calculo);
                                }
                            } else {
                                st.executeUpdate("UPDATE cuotas_alumnado SET monto_a_pagar = " + monto_a_pagar + ", monto_pagado = "+calculo+", monto_descuento = NULL WHERE id_alumno = "+cod_alumno+" AND id_carrera = "+carrera+" AND anio_t = "+anio+" AND mes_t = "+mes+"");
                            }
                        }
                        
                    } else {
                        //no pago por primera vez
                        if (modelo.getValueAt(a, 2).toString().contains("Deuda")) {
                            //Es Deuda
                            if (modelo.getValueAt(a, 2).toString().contains("2013")) {
                                //Del 2013
                                st.executeUpdate("UPDATE deudores_temp SET pagado_deuda_2013 = pagado_deuda_2013 + " + calculo + " WHERE id_alumno = " + cod_alumno);
                            } else {
                                //Del 2014
                                //pregunto el mes (marzo a diciembre)
                                update_pago_2014(cod_alumno, mes, calculo);
                            }
                        } else {
                            //PREGUNTO SI ES INSCRIPCION
                            if (modelo.getValueAt(a, 2).toString().contains("Inscripción")) {
                                //ES INSCRIPCION
                                st.executeUpdate("UPDATE inscripciones_carreras SET inscripcion_pagado = inscripcion_pagado + " + calculo + ", inscripcion_debe = inscripcion_debe - " + calculo + " WHERE id = " + modelo.getValueAt(a, 9).toString());
                            } else {
                                st.executeUpdate("UPDATE cuotas_alumnado SET monto_pagado = monto_pagado + " + calculo + ", monto_descuento = " + monto_descontado + " WHERE id_alumno = " + cod_alumno + " AND id_carrera = " + carrera + " AND anio_t = " + anio + " AND mes_t = " + mes + "");
                            }
                        }
                            
                        // saber si completa la cuota o no
                        if ((deuda.compareTo(calculo) == 0) && (Boolean)modelo.getValueAt(a, 5)) {
                            // PAGO COMPLETO Y EN DIA DE BONIFICACION
                            // CUOTA COMPLETA CON BONIFICACION
                            Clases.Operaciones.GenerarOperacionDetalles("Cuota Completada " + mes_nombre + " de " + anio + ". De " + modelo.getValueAt(a, 2).toString(), calculo.toString(), "Cuota",id_cuota);
                            Clases.Operaciones.GenerarOperacionDetalles("Bonificacion " + mes_nombre + " de " + anio + ". De " + modelo.getValueAt(a, 2).toString(), monto_descontado.toString(), "Cuota Bonificada",id_cuota);

                        } else if ((deuda.compareTo(calculo) == 0) && !(Boolean)modelo.getValueAt(a, 5)) {
                            // PAGO COMPLETO FUERA DIA BONIFICACION
                            //  PAGO COMPLETO SIN BONIFICACION
                            
                            //PREGUNTAR SI ES INSCRIPCION
                            if (modelo.getValueAt(a, 2).toString().contains("2013")) {
                                Clases.Operaciones.GenerarOperacionDetalles("Deuda Completada del " + anio + ". De " + modelo.getValueAt(a, 2).toString(), calculo.toString(), "Deuda 2013",id_cuota);
                            } else {
                                if (modelo.getValueAt(a, 2).toString().contains("Inscripción")) {
                                    Clases.Operaciones.GenerarOperacionDetalles("Pago Completado del " + anio + ". De " + modelo.getValueAt(a, 2).toString(), calculo.toString(), "Inscripcion",id_cuota);
                                } else {
                                    Clases.Operaciones.GenerarOperacionDetalles("Cuota Completada " + mes_nombre + " de " + anio + ". De " + modelo.getValueAt(a, 2).toString(), calculo.toString(), "Cuota",id_cuota);
                                }
                            }
                        } else {
                            // PAGO UN PARCIAL
                            // EL PAGO ES PARCIAL DE UN PARCIAL ANTERIOR
                            String detalle;
                            String tipo;

                            if (modelo.getValueAt(a, 2).toString().contains("2013")) {
                                // es del 2013 (deuda)
                                detalle = "Deuda Parcial del " + anio + ". De " + modelo.getValueAt(a, 2).toString();
                                tipo = "Deuda 2013";
                            } else {
                                //normal
                                if (modelo.getValueAt(a, 2).toString().contains("Inscripción")) {
                                    detalle = "Pago Parcial del " + anio + ". De " + modelo.getValueAt(a, 2).toString() + ". (Debe " + monto_a_pagar.subtract(calculo.add(monto_pagado)) + ")";
                                    tipo = "Inscripcion";
                                } else {
                                    detalle = "Cuota Parcial " + mes_nombre + " del " + anio + ". De " + modelo.getValueAt(a, 2).toString() + ". (Debe " + monto_a_pagar.subtract(calculo.add(monto_pagado)) + ")";
                                    tipo = "Cuota";
                                }
                            }
                            
                            Clases.Operaciones.GenerarOperacionDetalles(detalle, calculo.toString(), tipo, id_cuota);
                        }
                    }
                    st.clearBatch();
                }
            }
            st.close();
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    PagarCuotas     Cuotas: " + ex);
        }
    }
    
    public BigDecimal CargarCuotasAlumnos(String cod_alumno) {
        BigDecimal total_deuda_anual = BigDecimal.ZERO;
        
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*rs = st.executeQuery("SELECT anio_t,mes_t,id_carrera,id_grado,CONCAT(carrera.nombre, IF(id_grado IS NULL,'',CONCAT(' ' ,carrera_grados.num_curso , ' ' , carrera_grados.div_curso))),monto_a_pagar,monto_pagado,IF(monto_descuento IS NULL,0,monto_descuento) FROM cuotas_alumnado "
                    + "JOIN carrera ON (cuotas_alumnado.id_carrera = carrera.id) "
                    + "LEFT JOIN carrera_grados ON (carrera_grados.id = cuotas_alumnado.id_grado) "
                    + "WHERE id_alumno = " + cod_alumno + " AND monto_a_pagar != (monto_pagado + IF(monto_descuento IS NULL,0,monto_descuento)) OR (monto_a_pagar IS NULL AND id_alumno = " + cod_alumno + ") ORDER BY anio_t,mes_t,id_carrera");*/
            
            String consulta = "SELECT * FROM ((SELECT 2013 as anio,null as mes,null as col3,null as col4,'Deuda 2013' as col5,\n"
                    + "deudores_temp.monto_deuda_2013 as col6,pagado_deuda_2013 as col7,0 as col8\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.monto_deuda_2013 != deudores_temp.pagado_deuda_2013) \n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,3,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.marzo_2014,pagado_marzo_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.marzo_2014 != deudores_temp.pagado_marzo_2014) \n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,4,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.abril_2014,pagado_abril_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.abril_2014 != deudores_temp.pagado_abril_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,5,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.mayo_2014,pagado_mayo_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.mayo_2014 != deudores_temp.pagado_mayo_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,6,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.junio_2014,pagado_junio_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.junio_2014 != deudores_temp.pagado_junio_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,7,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.julio_2014,pagado_julio_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.julio_2014 != deudores_temp.pagado_julio_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,8,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.agosto_2014,pagado_agosto_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.agosto_2014 != deudores_temp.pagado_agosto_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,9,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.septiembre_2014,pagado_septiembre_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.septiembre_2014 != deudores_temp.pagado_septiembre_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,10,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.octubre_2014,pagado_octubre_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.octubre_2014 != deudores_temp.pagado_octubre_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,11,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.noviembre_2014,pagado_noviembre_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.noviembre_2014 != deudores_temp.pagado_noviembre_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT 2014,12,null as col3,null as col4,CONCAT('Deuda 2014','-',deudores_temp.curso) as col5,\n"
                    + "deudores_temp.diciembre_2014,pagado_diciembre_2014,0\n"
                    + "FROM deudores_temp\n"
                    + "LEFT JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = deudores_temp.id_alumno\n"
                    + "WHERE deudores_temp.id_alumno = " + cod_alumno + " AND deudores_temp.diciembre_2014 != deudores_temp.pagado_diciembre_2014)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT anio_t,'',id_carrera,inscripciones_carreras.id,CONCAT('Inscripción ', carrera.nombre),inscripcion_pagado + inscripcion_debe, inscripcion_pagado,0\n"
                    + "FROM inscripciones_carreras\n"
                    + "JOIN carrera ON (inscripciones_carreras.id_carrera = carrera.id)\n"
                    + "WHERE id_alumno = " + cod_alumno + " AND inscripciones_carreras.estado = 'C' AND inscripcion_debe != 0)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "(SELECT cuotas_alumnado.anio_t,mes_t,cuotas_alumnado.id_carrera,cuotas_alumnado.id_grado,\n"
                    + "CONCAT(carrera.nombre, IF(id_grado IS NULL,'',CONCAT(' ' ,IF(carrera_grados.num_curso IS NULL,'',carrera_grados.num_curso) , ' ' , carrera_grados.div_curso))),\n"
                    + "monto_a_pagar,monto_pagado,IF(monto_descuento IS NULL,0,monto_descuento) \n"
                    + "FROM cuotas_alumnado \n"
                    + "JOIN carrera ON (cuotas_alumnado.id_carrera = carrera.id) \n"
                    + "LEFT JOIN carrera_grados ON (carrera_grados.id = cuotas_alumnado.id_grado)\n"
                    + "JOIN inscripciones_carreras ON (inscripciones_carreras.id_alumno = cuotas_alumnado.id_alumno AND inscripciones_carreras.id_carrera = cuotas_alumnado.id_carrera AND inscripciones_carreras.estado = 'C')\n"
                    + "WHERE \n"
                    + "cuotas_alumnado.id_alumno = " + cod_alumno + "\n"
                    + "AND \n"
                    + "monto_a_pagar != (monto_pagado + IF(monto_descuento IS NULL,0,monto_descuento)) OR (monto_a_pagar IS NULL \n"
                    + "AND \n"
                    + "cuotas_alumnado.id_alumno = " + cod_alumno + "))) AS consultota ORDER BY anio,FIELD(mes,'1','2','3','4','5','6','7','8','9','10','11','12')";
            
            //System.out.println(consulta + "\n\n");
            
            rs = st.executeQuery(consulta);
            
            rs.last();

            int reg = rs.getRow();
            rs.first();

            datos = new Object[reg][11];

            for (int a = 0; a < reg; a++) {
                
                String anio = rs.getString(1).split("-")[0];
                int mes = rs.getInt(2);
                String servicio = rs.getString(5);
                
                String id_carrera = rs.getString(3);
                String id_grado = rs.getString(4);
                String monto_a_pagar_rs = rs.getString(6);
                
                if (mes==0) { // ES INSCRIPCION
                    
                    datos[a][0] = anio;
                    datos[a][1] = mes;
                    datos[a][2] = servicio;
                    datos[a][3] = rs.getBigDecimal(6);
                    datos[a][4] = rs.getBigDecimal(7);
                    datos[a][5] = false;
                    datos[a][6] = rs.getBigDecimal(6);
                    datos[a][7] = rs.getBigDecimal(6).subtract(rs.getBigDecimal(7));
                    datos[a][8] = id_carrera;
                    datos[a][9] = id_grado;
                    datos[a][10] = true;
                    
                    total_deuda_anual = total_deuda_anual.add(rs.getBigDecimal(6).subtract(rs.getBigDecimal(7)));
                } else {
                    BigDecimal monto_pagado;
                    BigDecimal boolean_becado;

                    BigDecimal[] montos;
                    if (!anio.equals(Principal.anio_global)) {
                        //deuda anual
                        boolean_becado = BigDecimal.ONE;
                        montos = new BigDecimal[3];
                        montos[0] = null;
                        montos[1] = BigDecimal.ZERO;
                        montos[2] = BigDecimal.ZERO;
                    } else {
                        //año actual
                        montos = MontoCuotaAlumno(anio, mes, cod_alumno, id_carrera, id_grado);
                        boolean_becado = montos[3];
                        boolean_becado = boolean_becado;
                    }
                    BigDecimal monto_a_pagar;
                    BigDecimal monto_anticipado;
                    BigDecimal desc;
                    BigDecimal monto_deuda;

                    if (monto_a_pagar_rs == null) {
                        //no pago previamente
                        monto_a_pagar = montos[0];
                        monto_pagado = BigDecimal.ZERO;
                        monto_anticipado = montos[1];
                        desc = montos[2];
                        if (desc.compareTo(BigDecimal.ONE) == 0) {
                            //con descuento
                            datos[a][6] = monto_a_pagar;
                            monto_a_pagar = monto_anticipado;
                            datos[a][5] = true;
                            monto_deuda = monto_a_pagar;
                        } else {
                            //sin descuento
                            datos[a][6] = monto_anticipado;
                            datos[a][5] = false;
                            monto_deuda = monto_a_pagar;
                        }
                        datos[a][3] = monto_a_pagar;
                    } else {
                        //pagado previamente
                        monto_a_pagar = new BigDecimal(monto_a_pagar_rs);
                        monto_pagado = rs.getBigDecimal(7);
                        //monto_anticipado = rs.getBigDecimal(8);

                        monto_anticipado = montos[1];
                        
                        desc = montos[2];
                        if (desc.compareTo(BigDecimal.ONE) != 0) {
                            //sin descuento
                            datos[a][5] = false;
                            datos[a][6] = monto_anticipado;
                            
                            if (rs.getString(5).contains("Deuda")) {
                                datos[a][3] = monto_a_pagar;
                                monto_deuda = monto_a_pagar.subtract(monto_pagado);
                            } else {
                                datos[a][3] = monto_a_pagar;
                                monto_deuda = monto_a_pagar.subtract(monto_pagado);
                            }
                            
                        } else {
                            //con descuento
                            datos[a][5] = true;
                            datos[a][6] = montos[0];
                            
                            if (rs.getString(5).contains("Deuda")) {
                                datos[a][3] = monto_a_pagar;
                                monto_deuda = monto_a_pagar.subtract(monto_pagado);
                            } else {
                                datos[a][3] = monto_a_pagar.subtract(monto_a_pagar.subtract(monto_anticipado));
                                monto_deuda = monto_a_pagar.subtract(monto_a_pagar.subtract(monto_anticipado)).subtract(monto_pagado);
                            }
                        }
                        
                        
                    }

                    datos[a][0] = anio;
                    datos[a][1] = mes;
                    datos[a][2] = servicio;

                    datos[a][4] = monto_pagado;
                    datos[a][7] = monto_deuda;
                    datos[a][8] = id_carrera;
                    datos[a][9] = id_grado;
                    if (boolean_becado.compareTo(BigDecimal.ZERO) == 0) {
                        datos[a][10] = false;
                    } else {
                        datos[a][10] = true;
                    }

                    total_deuda_anual = total_deuda_anual.add(monto_deuda);
                }
                
                rs.next();
            }

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarCuotasAlumnos     Cuotas: " + ex);
        }
        
        return total_deuda_anual;
    }
    
    public static BigDecimal[] MontoCuotaAlumno(String anio, Integer mes, String alumno, String carrera, String grado) {
        BigDecimal monto = BigDecimal.ZERO;
        BigDecimal monto_anticipado = BigDecimal.ZERO;

        BigDecimal[] montos = null;

        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT beca_monto,beca_hasta,beca_desde FROM inscripciones_carreras WHERE id_carrera = " + carrera + " AND id_alumno = " + alumno + " AND anio_t = " + anio + " AND estado = 'C'");
            rs.next();

            BigDecimal beca = rs.getBigDecimal(1);
            String mes_beca_hasta = "0";
            String mes_beca_desde = "0";
            if (beca != null) {
                mes_beca_hasta = rs.getString(2).split("-")[0];
                mes_beca_desde = rs.getString(3).split("-")[0];
            }
            Integer mes_actual = Integer.valueOf(Principal.fecha_actual.split("-")[1]);
            Integer dia_actual = Integer.valueOf(Principal.fecha_actual.split("-")[0]);
            rs.close();

            BigDecimal desc = BigDecimal.ZERO;
            BigDecimal beca_boolean = BigDecimal.ZERO;

            //BECAS
            //SI ES BECADO
            if (Integer.valueOf(mes_beca_hasta) >= mes && Integer.valueOf(mes_beca_desde) <= mes) {
                //MONTO BASE = BECA
                monto = beca;
                beca_boolean = BigDecimal.ONE;
            } else {
            //SI NO ES BECADO
                //obtener el MONTO BASE

                if (grado == null) {
                //SI GRADO = NULL
                    //OBTENGO EL MONTO DE LA CARRERA
                    rs = st.executeQuery("SELECT monto_cuota,monto_cuota_anticipado FROM carrera WHERE id = " + carrera + "");
                    rs.next();

                    monto = rs.getBigDecimal(1);
                    monto_anticipado = rs.getBigDecimal(2);
                    rs.close();
                } else {
                //SI GRADO != NULL
                    //OBTENGO EL MONTO DEL GRADO xD
                    rs = st.executeQuery("SELECT monto_cuota,monto_cuota_anticipado FROM carrera_grados WHERE id = " + grado + "");
                    rs.next();

                    monto = rs.getBigDecimal(1);
                    monto_anticipado = rs.getBigDecimal(2);
                    rs.close();
                }

                //DESCUENTO preguntar si la cuota q paga es menor al plazo de fecha de descuentos!!
                int dia_descuento_hasta = 10;
                if (Objects.equals(mes_actual, mes)) {
                    //pregunto el dia
                    if (dia_actual <= dia_descuento_hasta) {
                    //ya descuento de una
                        //monto = monto_anticipado;
                        desc = BigDecimal.ONE;
                    }
                } else if (mes_actual < mes) {
                //ya descuento de una
                    //monto = monto_anticipado;
                    desc = BigDecimal.ONE;
                }
            }

            montos = new BigDecimal[4];
            montos[0] = monto;
            montos[1] = monto_anticipado;
            montos[2] = desc; //booleano
            montos[3] = beca_boolean; //booleano

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CalcularMonto     Cuotas: " + ex);
        }
        return montos;
    }
    
    
    //          CEMENTERIO
    
    public void GenerarCuotasResponsable(){
        
    }    
    
    public void CargarResponsableCuotas(){
        try {
            ResultSet rs;   ResultSet rs2;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement st2 = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT espacios_responsables.dni_responsable, espacios_responsables.apenom_responsable, "
                            + "(SELECT COUNT(espacios.dni_responsable) FROM espacios WHERE espacios.dni_responsable = espacios_responsables.dni_responsable) as 'Cant' /*,"
                            + "CONCAT(espacios.zona_espacio,' ',espacios.numero_espacio,' ',IF(espacios.bis_espacio = '0','','BIS'))*/"
                            + "FROM espacios_responsables "
                            + "LEFT JOIN espacios ON espacios.dni_responsable = espacios_responsables.dni_responsable "
                            + "GROUP BY dni_responsable");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][3];
            
            String cant_espacios = "";

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);       // DNI_RESPONSABLE
                datos[fila][1] = rs.getObject(2);       // APENOM_RESPONSABLE

                if (rs.getInt(3) > 0 ) {                // CANT_ESPACIOS
                
                    rs2=st2.executeQuery("SELECT CONCAT(zona_espacio,' ',numero_espacio,' ',IF(bis_espacio = '0','','BIS')) FROM espacios WHERE dni_responsable = "+rs.getObject(1)+"");
                    rs2.last();
                    
                    Integer registros2 = rs2.getRow();
                    rs2.first();
                    
                    for(int fi = 0; fi < registros2; fi++){
                        if(fi==0){
                            cant_espacios = rs2.getObject(1).toString();
                        }else{
                            cant_espacios = cant_espacios + " - " + rs2.getObject(1);
                        }
                        
                        rs2.next();
                    }
                    
                    rs2.close();
                    
                }
                datos[fila][2] = cant_espacios;
                
                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarResponsableCuotas    Cuotas: " + ex);
        }  
    }
    
    public static Object[] EncontroResponsableCuotas(String responsable){
        Object[] Info = null;
        try {
            ResultSet rs;   ResultSet rs2;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement st2 = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT espacios_responsables.dni_responsable, espacios_responsables.apenom_responsable, "
                            + "(SELECT COUNT(espacios.dni_responsable) FROM espacios WHERE espacios.dni_responsable = espacios_responsables.dni_responsable) as 'Cant' "
                            + "FROM espacios_responsables "
                            + "LEFT JOIN espacios ON espacios.dni_responsable = espacios_responsables.dni_responsable "
                            + "WHERE espacios_responsables.dni_responsable = '"+responsable+"'"
                            + "GROUP BY dni_responsable");
            rs.last();
        
            Integer registros = rs.getRow();

            String cant_espacios = "";

            if (registros > 0) {
                Info = new Object[3];

                Info[0] = rs.getObject(1);  // DNI_RESPONSABLE
                Info[1] = rs.getObject(2);  // APENOM_RESPONSABLE

                if (rs.getInt(3) > 0 ) {    // CANT_ESPACIOS
                
                    rs2=st2.executeQuery("SELECT CONCAT(zona_espacio,' ',numero_espacio,' ',IF(bis_espacio = '0','','BIS')) FROM espacios WHERE dni_responsable = '"+rs.getObject(1)+"'");
                    rs2.last();
                    
                    Integer registros2 = rs2.getRow();
                    rs2.first();
                    
                    for(int fi = 0; fi < registros2; fi++){
                        if(fi==0){
                            cant_espacios = rs2.getObject(1).toString();
                        }else{
                            cant_espacios = cant_espacios + " - " + rs2.getObject(1);
                        }
                        rs2.next();
                    }
                    rs2.close();
                }
                Info[2] = cant_espacios;

                for (int a = 0; a < Info.length; a++) {
                    if (Info[a] == null) {
                        Info[a] = "";
                    }
                }
            }
            
            rs.close();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    EncontroResponsableCuotas     Cuotas: "+ex);
        }
        return Info;
    }
    
    public void FiltrarResponsablesCuotas(String responsable){
        try {
            ResultSet rs;   ResultSet rs2;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement st2 = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT espacios_responsables.dni_responsable, espacios_responsables.apenom_responsable, "
                            + "(SELECT COUNT(espacios.dni_responsable) FROM espacios WHERE espacios.dni_responsable = espacios_responsables.dni_responsable) as 'Cant' "
                            + "FROM espacios_responsables "
                            + "LEFT JOIN espacios ON espacios.dni_responsable = espacios_responsables.dni_responsable "
                            + "WHERE espacios_responsables.dni_responsable LIKE '%"+responsable+"%' OR espacios_responsables.apenom_responsable LIKE '%"+responsable+"%'"
                            + "GROUP BY dni_responsable");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][3];
            
            String cant_espacios = "";

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);       // DNI_RESPONSABLE
                datos[fila][1] = rs.getObject(2);       // APENOM_RESPONSABLE

                if (rs.getInt(3) > 0 ) {                // CANT_ESPACIOS
                
                    rs2=st2.executeQuery("SELECT CONCAT(zona_espacio,' ',numero_espacio,' ',IF(bis_espacio = '0','','BIS')) FROM espacios WHERE dni_responsable = '"+rs.getObject(1)+"'");
                    rs2.last();
                    
                    Integer registros2 = rs2.getRow();
                    rs2.first();
                    
                    for(int fi = 0; fi < registros2; fi++){
                        if(fi==0){
                            cant_espacios = rs2.getObject(1).toString();
                        }else{
                            cant_espacios = cant_espacios + " - " + rs2.getObject(1);
                        }
                        
                        rs2.next();
                    }
                    
                    rs2.close();
                    
                }
                datos[fila][2] = cant_espacios;
                
                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarResponsableCuotas    Cuotas: " + ex);
        }
    }
    
    public static void CargarResponsableAlumnos(String cod_alumno){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        
        
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    CargarResponsableAlumnos     Cuotas: " + ex);
        }
    }
    
    
    //          ALQUILERES
    
    public void CargarAlquileresDialogCuotas(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT lotes.nombre,CONCAT(lotes_responsables.apellidos,' ',lotes_responsables.nombres),lotes_responsables.id\n"
                        + "FROM alquileres_contratos\n"
                        + "JOIN lotes ON lotes.id = alquileres_contratos.lote AND alquileres_contratos.estado = 'A'\n"
                        + "JOIN lotes_responsables ON alquileres_contratos.id_responsable = lotes_responsables.id");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][3];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(3);  // PK PERSONA
                datos[fila][1] = rs.getObject(2);  // APENOM_ALUMNO
                datos[fila][2] = rs.getObject(1);  // NOMBRE LOTE

                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarAlquileresDialogCuotas    Cuotas: " + ex);
        }    
    }
    
    public static Object[] CargarAlquileresCuotas(String lote) {
        Object[] retorno = new Object[2]; //linea agregada para retornar el total de la deuda anual.
                                          //en el lugar 1 van a ir los datos y en el 2 el total
        BigDecimal total_deuda = BigDecimal.ZERO;
        
        Object[][] operacion_detalle = null;
        Object[][] temp_detalles = null;
        
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT lotes.nombre,alquileres_contratos.id,alquileres_contratos_tiempos.desde,alquileres_contratos_tiempos.hasta,alquileres_contratos_tiempos.monto\n"
                    + "FROM alquileres_contratos\n"
                    + "JOIN lotes ON lotes.id = alquileres_contratos.lote AND alquileres_contratos.estado = 'A'\n"
                    + "JOIN alquileres_contratos_tiempos ON alquileres_contratos_tiempos.id_contrato = alquileres_contratos.id\n"
                    + "WHERE lotes.nombre LIKE '%"+lote+"%'");
            
            rs.last();
            
            int contador_detalle = 0;

            if (rs.getRow() > 0) {
                Integer registros = rs.getRow();

                Object[][] datos_rs = new Object[registros][5];

                rs.first();

                for (int fila = 0; fila < registros; fila++) {

                    datos_rs[fila][0] = rs.getObject(1);
                    datos_rs[fila][1] = rs.getObject(2);
                    datos_rs[fila][2] = rs.getObject(3);
                    datos_rs[fila][3] = rs.getObject(4);
                    datos_rs[fila][4] = rs.getObject(5);

                    rs.next();
                }

                rs.close();

                Integer anio_desde = null;
                Integer anio_hasta_contrato = null;
                Integer mes_desde = null;
                Integer mes_hasta_contrato = null;

                Boolean parcial = false;
                Integer ultima_cuota_mes = null;
                Integer ultima_cuota_anio = null;

                boolean tiene_pagos = false;
                
                for (int x = 0; x < datos_rs.length; x++) {
                    
                    mes_desde = Integer.valueOf(datos_rs[x][2].toString().split("-")[0]);
                    anio_desde = Integer.valueOf(datos_rs[x][2].toString().split("-")[1]);
                    
                    rs = st.executeQuery("SELECT monto_total,monto_pagado,monto_total - monto_pagado,mes,anio\n"
                            + "FROM cuotas_alquileres\n"
                            + "WHERE id_contrato = " + datos_rs[x][1] + " AND mes >= " + mes_desde + " AND anio >= " + anio_desde + " \n"
                            + "ORDER BY anio,mes DESC\n"
                            + "LIMIT 1");

                    rs.last();

                    Integer registros2 = rs.getRow();

                    if (registros2 > 0) {

                        tiene_pagos = true;

                        if (rs.getBigDecimal(3).compareTo(BigDecimal.ZERO) != 0) {
                            parcial = true;
                        }

                        ultima_cuota_mes = rs.getInt(4);
                        ultima_cuota_anio = rs.getInt(5);

                        if (parcial) {
                            //mes_desde va a ser el del rs
                            //contador aumenta en 1

                            //cant_cuotas++;
                        } else {
                            //mes_desde aumenta 1
                            //contador no aumenta

                            if (ultima_cuota_mes == 12) {
                                ultima_cuota_mes = 1;
                                ultima_cuota_anio++;
                            } else {
                                ultima_cuota_mes++;
                            }
                        }
                    } else {
                        tiene_pagos = false;
                        parcial = false;
                    }

                    anio_hasta_contrato = Integer.valueOf(datos_rs[x][3].toString().split("-")[1]);
                    mes_hasta_contrato = Integer.valueOf(datos_rs[x][3].toString().split("-")[0]);

                    if (tiene_pagos) {
                        anio_desde = ultima_cuota_anio;
                        mes_desde = ultima_cuota_mes;
                    }

                    int mes_contador = mes_desde;

                    // si el contrato ya se termino, salir del for
                    if (!((mes_contador > mes_hasta_contrato) && (anio_desde.equals(anio_hasta_contrato)))) {

                        for (int anio = anio_desde; anio <= anio_hasta_contrato; anio++) {
                            // SELECCIONAR LA ULTIMA CUOTA PAGADA, Y SI NO HAY NINGUNA TRAER COMO MES_DESDE EL MES DEL CONTRATO, SINO ACTUALIZAR MES_DESDE A ULTIMA CUOTA PAGADA + 1
                            while (1 == 1) {
                                if (parcial) {
                                    if (mes_contador == ultima_cuota_mes && anio == ultima_cuota_anio) {
                                        contador_detalle++;
                                    // AÑO  MES  MONTO_TOTAL  MONTO_PAGADO  MONTO_DEBE
                                        //Object[] datos = {ultima_cuota_anio, ultima_cuota_mes, rs.getBigDecimal(1),rs.getBigDecimal(2), rs.getBigDecimal(3)};

                                        temp_detalles = operacion_detalle;

                                        operacion_detalle = new Object[contador_detalle][6];

                                        if (operacion_detalle != null) {
                                            for (int z = 0; z < contador_detalle - 1; z++) {
                                                for (int columna = 0; columna < 6; columna++) {
                                                    operacion_detalle[z][columna] = temp_detalles[z][columna];
                                                }
                                            }
                                        }

                                        operacion_detalle[contador_detalle - 1][0] = ultima_cuota_anio;
                                        operacion_detalle[contador_detalle - 1][1] = ultima_cuota_mes;
                                        operacion_detalle[contador_detalle - 1][2] = rs.getBigDecimal(1);
                                        operacion_detalle[contador_detalle - 1][3] = rs.getBigDecimal(2);
                                        operacion_detalle[contador_detalle - 1][4] = datos_rs[x][1];
                                        //correccion de deudas y acumular total de deudas
                                        BigDecimal total = new BigDecimal(operacion_detalle[contador_detalle - 1][2].toString());
                                        BigDecimal pagado = new BigDecimal(operacion_detalle[contador_detalle - 1][3].toString());
                                        BigDecimal deuda = total.subtract(pagado);
                                        total_deuda = total_deuda.add(deuda);
                                        operacion_detalle[contador_detalle - 1][5] = deuda;
                                    //operacion_detalle[contador_detalle - 1][5] = rs.getBigDecimal(3);

                                        parcial = false;

                                    }
                                } else {
                                    contador_detalle++;

                                // AÑO  MES  LOTE  MONTO_TOTAL  MONTO_PAGADO  MONTO_DEBE
                                    //Object[] datos = {anio,mes,datos_rs[x][4],BigDecimal.ZERO,BigDecimal.ZERO};
                                    temp_detalles = operacion_detalle;

                                    operacion_detalle = new Object[contador_detalle][6];

                                    if (operacion_detalle != null) {
                                        for (int z = 0; z < contador_detalle - 1; z++) {
                                            for (int columna = 0; columna < 6; columna++) {
                                                operacion_detalle[z][columna] = temp_detalles[z][columna];
                                            }
                                        }
                                    }

                                    operacion_detalle[contador_detalle - 1][0] = anio;
                                    operacion_detalle[contador_detalle - 1][1] = mes_contador;
                                    operacion_detalle[contador_detalle - 1][2] = datos_rs[x][4];
                                    operacion_detalle[contador_detalle - 1][3] = BigDecimal.ZERO;
                                    operacion_detalle[contador_detalle - 1][4] = datos_rs[x][1];
                                    //correccion de deudas y acumular total de deudas
                                    BigDecimal total = new BigDecimal(operacion_detalle[contador_detalle - 1][2].toString());
                                    BigDecimal pagado = new BigDecimal(operacion_detalle[contador_detalle - 1][3].toString());
                                    BigDecimal deuda = total.subtract(pagado);
                                    total_deuda = total_deuda.add(deuda);
                                    operacion_detalle[contador_detalle - 1][5] = deuda;
                                    //operacion_detalle[contador_detalle - 1][5] = BigDecimal.ZERO;
                                }

                                if (anio < anio_hasta_contrato) {
                                    //si el mes es igual a 12
                                    if (mes_contador == 12) {
                                        mes_contador = 1;
                                        break;
                                    } else {
                                        mes_contador++;
                                    }
                                } else {
                                //en caso de q el año sea el ultimo
                                    //si el mes es el ultimo
                                    if (mes_contador == mes_hasta_contrato) {
                                        break;
                                    } else {
                                        mes_contador++;
                                    }
                                }
                            }
                        }

                    }
                }
            }
            
            rs.close();
            
            retorno[0] = operacion_detalle;
            retorno[1] = total_deuda;
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarAlquileresCuotas    Cuotas: " + ex);
        }
        return retorno;
    }
    
    public static String[] EncontroAlquileresCuotas(String pk){
        String[] Info = null;
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT lotes.nombre,CONCAT(lotes_responsables.apellidos,' ',lotes_responsables.nombres),lotes_responsables.id\n"
                + "FROM alquileres_contratos\n"
                + "JOIN lotes ON lotes.id = alquileres_contratos.lote AND alquileres_contratos.estado = 'A'\n"
                + "JOIN lotes_responsables ON alquileres_contratos.id_responsable = lotes_responsables.id\n"
                + "WHERE lotes_responsables.id = '"+pk+"'");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if(registros > 0){
            Info = new String[3];
                
            Info[0] = rs.getString(1);  // NOMBRE_LOTE
            Info[1] = rs.getString(2);  // APENOM_ALQUILERES
            Info[2] = rs.getString(3);  // pk responsable (para ver el nombre en la operacion)
            
        }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    EncontroAlquileresCuotas     Cuotas: "+ex);
        }
        return Info;
    }
    
    public static String getIdCuotaAlquileres(String contrato, String mes, String anio) {
        String id = null;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT id FROM cuotas_alquileres WHERE id_contrato = "+contrato+" AND mes = "+mes+" AND anio = "+anio+"");
            rs.last();
            
            if (rs.getRow() > 0) {
                id = rs.getString(1);    
            }             
            
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Cuotas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public static void PagarAlquiler(Object[][] cuotas, BigDecimal monto_cobrado, String lote) {
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //SABER SI NO ES EL PRIMER PAGO
            
            int registros = cuotas.length;
            
            for (int a = 0; a < registros; a++) {
                String id_contrato = cuotas[a][4].toString();
                String anio = cuotas[a][0].toString();
                String mes = cuotas[a][1].toString();
                String monto_total = cuotas[a][2].toString();
                
                String cuota = getIdCuotaAlquileres(id_contrato,mes,anio);
                
                BigDecimal deuda = new BigDecimal(cuotas[a][5].toString());
                //saber si lo q posee es capás de cubrir la deuda
                boolean cubre = false;
                BigDecimal monto_pagado;
                if (monto_cobrado.compareTo(deuda) != -1) {
                    //cubre la deuda
                    cubre = true;
                    //el monto q paga es la deuda completa
                    monto_pagado = deuda;
                    //el disponible de plata disminuye por pagar la deuda
                    monto_cobrado = monto_cobrado.subtract(deuda);
                } else {
                    //no cubre la deuda
                    //el monto q paga es el disponible de plata
                    monto_pagado = monto_cobrado;
                    //el disponible de plata es cero
                    monto_cobrado = BigDecimal.ZERO;
                }
                
                if (a == 0) {
                    if (new BigDecimal(cuotas[a][3].toString()).compareTo(BigDecimal.ZERO) != 0) {
                        //SI YA PAGÓ
                        //hay q updatear la cuota
                        st.executeUpdate("UPDATE cuotas_alquileres SET monto_pagado = monto_pagado + " + monto_pagado + " WHERE id_contrato = " + id_contrato + " AND mes = " + mes + " AND anio = " + anio + "");
                    } else {
                        //SI NUNCA PAGO
                        //hay q insertar la cuota
                        st.executeUpdate("INSERT INTO cuotas_alquileres (id_contrato,monto_total,monto_pagado,mes,anio) VALUES (" + id_contrato + "," + monto_total + "," + monto_pagado + "," + mes + "," + anio + ")");
                    }
                } else {
                    //insertar la cuota
                    st.executeUpdate("INSERT INTO cuotas_alquileres (id_contrato,monto_total,monto_pagado,mes,anio) VALUES (" + id_contrato + "," + monto_total + "," + monto_pagado + "," + mes + "," + anio + ")");
                }
                
                if (a != 0) {
                    //INSERTO LA OPERACION
                    //pregunto si es parcial o no
                    if (cubre) {
                        //es completo
                        Clases.Operaciones.GenerarOperacionDetalles("Cuota Completa de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Del " + lote, monto_pagado.toString(),"Cuota",cuota);
                    } else {
                        //es parcial
                        Clases.Operaciones.GenerarOperacionDetalles("Cuota Parcial de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Del " + lote, monto_pagado.toString(),"Cuota",cuota);
                    }
                } else {
                    //INSERTO LA OPERACION
                    //pregunto si es parcial o no
                    if (new BigDecimal(cuotas[a][3].toString()).compareTo(BigDecimal.ZERO) != 0) {
                        //SI YA PAGÓ
                        if (cubre) {
                            Clases.Operaciones.GenerarOperacionDetalles("Cuota Completada de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Del " + lote, monto_pagado.toString(),"Cuota",cuota);
                        } else {
                            Clases.Operaciones.GenerarOperacionDetalles("Cuota Parcial de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Del " + lote, monto_pagado.toString(),"Cuota",cuota);
                        }
                    } else {
                        //SI NO PAGÓ
                        if (cubre) {
                            Clases.Operaciones.GenerarOperacionDetalles("Cuota Completa de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Del " + lote, monto_pagado.toString(),"Cuota",cuota);
                        } else {
                            Clases.Operaciones.GenerarOperacionDetalles("Cuota Parcial de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Del " + lote, monto_pagado.toString(),"Cuota",cuota);
                        }
                    }
                }
                
                if (monto_cobrado.compareTo(BigDecimal.ZERO) == 0) {
                    //no tengo mas plata
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Cuotas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //          SOCIOS
    
    public void CargarSociosDialogCuotas(){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT id_socio, apenom_socio, telefono_socio \n"
                        + "FROM socios\n"
                        + "WHERE estado_socio = 'A'");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][3];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);  // ID SOCIO
                datos[fila][1] = rs.getObject(2);  // APENOM SOCIO
                datos[fila][2] = rs.getObject(3);  // TELEFONO

                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarSociosDialogCuotas    Cuotas: " + ex);
        }    
    }
    
    public static String[] EncontroSociosCuotas(String pk){
        String[] Info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT id_socio, apenom_socio, telefono_socio \n"
                        + "FROM socios\n"
                        + "WHERE estado_socio = 'A' AND id_socio = '"+pk+"'");
            rs.last();

            Integer registros = rs.getRow();

            if (registros > 0) {
                Info = new String[3];

                Info[0] = rs.getString(1);  // ID SOCIO
                Info[1] = rs.getString(2);  // APENOM_SOCIOS
                Info[2] = rs.getString(3);  // TELEFONO_SOCIO

            }
            
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    EncontroSociosCuotas    Cuotas: " + ex);
        }
        return Info;    
    }
    
    public BigDecimal CargarCuotasSocio(String socio) {
        BigDecimal monto_anual = BigDecimal.ZERO;
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //SELECT CON LA PAGADA PARCIAL
            rs = st.executeQuery("SELECT anio,mes,monto_total,monto_pagado,monto_total - monto_pagado AS deuda,id FROM cuotas_socios WHERE socio = "+socio+" ORDER BY anio DESC, mes DESC LIMIT 1");
            rs.next();

            Integer registros = rs.getRow();
            
            boolean primer_parcial = false;
            
            int anio;
            int mes;
            BigDecimal monto_total = null;
            BigDecimal monto_pagado = null;
            BigDecimal monto_deuda = null;
            int id_cuota = 0;
            
            if (registros > 0) {
                anio = rs.getInt(1);
                mes = rs.getInt(2);
                
                if (rs.getBigDecimal(5).compareTo(BigDecimal.ZERO) == 0) {
                    //es completa. Tengo q aumentar un mes
                    if (mes == 12) {
                        mes = 1;
                        anio++;
                    } else {
                        mes++;
                    }
                    primer_parcial = false;
                } else {
                    //no es completa
                    primer_parcial = true;
                    monto_total = rs.getBigDecimal(3);
                    monto_pagado = rs.getBigDecimal(4);
                    monto_deuda = rs.getBigDecimal(5);
                    id_cuota = rs.getInt(6);
                }
                
                rs.close();
            } else {
                //TENGO Q OBTENER LA PRIMER CUOTA A PAGAR
                rs.close();
                
                rs = st.executeQuery("SELECT cobrar_desde_socio FROM socios WHERE id_socio = "+socio+"");
                rs.next();
                String comienzo = rs.getString(1);
                
                rs.close();
                
                anio = Integer.valueOf(comienzo.split("-")[1]);
                mes = Integer.valueOf(comienzo.split("-")[0]);
            }

            rs = st.executeQuery("SELECT period_diff(date_format(CURDATE(),'%Y%m'),date_format('"+anio+"-"+mes+"-01','%Y%m')) AS meses");
            rs.next();
            Integer meses = rs.getInt(1) + 12;
            rs.close();
            
            datos = new Object[meses][6]; //"AÑO", "MES", "MONTO A PAGAR", "MONTO PAGADO", "VAR", "DEUDA"
            
            for (int fila = 0; fila < meses; fila++) {
                if (fila == 0 && primer_parcial) {
                    datos[fila][0] = anio;
                    datos[fila][1] = mes;
                    datos[fila][2] = monto_total;
                    datos[fila][3] = monto_pagado;
                    datos[fila][4] = id_cuota;
                    datos[fila][5] = monto_deuda;
                    monto_anual = monto_anual.add(monto_deuda);
                } else {
                    datos[fila][0] = anio;
                    datos[fila][1] = mes;
                    /*DATOS OBTENIDOS DE MANERA DIFERENTE*/
                    rs = st.executeQuery("SELECT monto, momento FROM periodos_montos_socios WHERE momento <= '"+anio+"-"+mes+"-01' ORDER BY momento DESC LIMIT 1");
                    rs.next();
                    monto_total = rs.getBigDecimal(1);
                    rs.close();
                    datos[fila][2] = monto_total;
                    datos[fila][3] = 0;
                    datos[fila][4] = null;
                    datos[fila][5] = monto_total;
                    monto_anual = monto_anual.add(monto_total);
                }
                
                /*AUMENTO UN MES*/
                if (mes == 12) {
                    mes = 1;
                    anio++;
                } else {
                    mes++;
                }
            }
            rs.close();
            //System.out.println(datos.toString());
        } catch (Exception ex) {
            System.out.println("Se han producido excepcionesddddddddddddddd: " + ex + "\n");
            StackTraceElement l = ex.getStackTrace()[0];
            System.out.println(
                    l.getClassName() + "/" + l.getMethodName() + ":" + l.getLineNumber());
        }
        
        return monto_anual;
    }
    
    public static void PagoCuotasSocio(String socio, TableModel modelo, BigDecimal monto_abonado) {
        try {
            PreparedStatement st;
            
            BigDecimal monto_a_pagar = null;
            
            //"AÑO", "MES", "MONTO A PAGAR", "MONTO PAGADO", "VAR", "DEUDA"
            for (int a = 0; a < modelo.getRowCount(); a++) {
                BigDecimal deuda = new BigDecimal(modelo.getValueAt(a, 5).toString());
                    
                //VEO SI LO QUE ABONA ALCANZA PARA CUBRIR ESTA CUOTA
                if (monto_abonado.compareTo(deuda) == -1) {
                    //no alcanza (monto abonado es menor a la deuda)
                    monto_a_pagar = monto_abonado;
                    monto_abonado = BigDecimal.ZERO;
                } else {
                    //alcanza (monto abonado no es menor a la deuda)
                    monto_a_pagar = deuda;
                    monto_abonado = monto_abonado.subtract(deuda);
                }
                Object var = modelo.getValueAt(a, 4);
                if (var != null) {
                    //UPDATE
                    String anio = modelo.getValueAt(a, 0).toString();
                    String mes = modelo.getValueAt(a, 1).toString();
                    String id = modelo.getValueAt(a, 4).toString();
                    st = linneoadmin.LinneoAdmin.conexion.prepareStatement("UPDATE cuotas_socios SET monto_pagado = monto_pagado + "+monto_a_pagar+" WHERE id = "+id+"", Statement.RETURN_GENERATED_KEYS);
                    st.executeUpdate();
                    //ES PARCIAL
                    if (monto_abonado.compareTo(deuda) != -1) {
                        //sigue parcial
                        BigDecimal debe = deuda.subtract(monto_a_pagar);
                        Clases.Operaciones.GenerarOperacionDetalles("Cuota Parcial de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Deuda: " + Pantallas.Frames.Principal.df.format(debe), monto_a_pagar.toString(),"Cuota",id);
                    } else {
                        //completó
                        Clases.Operaciones.GenerarOperacionDetalles("Cuota Parcial Completada de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ".", monto_a_pagar.toString(),"Cuota",id);
                    }
                } else {
                    //INSERT
                    String anio = modelo.getValueAt(a, 0).toString();
                    String mes = modelo.getValueAt(a, 1).toString();
                    BigDecimal monto_total = new BigDecimal(modelo.getValueAt(a, 2).toString());
                    st = linneoadmin.LinneoAdmin.conexion.prepareStatement("INSERT INTO cuotas_socios (socio, anio, mes, monto_total, monto_pagado) VALUES ("+socio+","+anio+","+mes+","+monto_total+","+monto_a_pagar+")", Statement.RETURN_GENERATED_KEYS);
                    st.executeUpdate();
                    ResultSet rs = st.getGeneratedKeys();
                    String id = null;
                    if (rs.next()) {
                        id = rs.getString(1);
                    }
                    
                    //PAGOS NUEVOS
                    if (monto_total.compareTo(monto_a_pagar) != 0) {
                        //es parcial
                        BigDecimal debe = monto_total.subtract(monto_a_pagar);
                        Clases.Operaciones.GenerarOperacionDetalles("Cuota Parcial de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ". Deuda: " + Pantallas.Frames.Principal.df.format(debe), monto_a_pagar.toString(),"Cuota",id);
                    } else{
                        //es completa
                        Clases.Operaciones.GenerarOperacionDetalles("Cuota Completada de " + linneoadmin.LinneoAdmin.NombreMes(Integer.valueOf(mes)) + " del " + anio + ".", monto_a_pagar.toString(),"Cuota",id);
                    }
                }
                
                if (monto_abonado.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones: " + ex);
        }
    }
    
    public void FiltrarSociosCuotas(String socio){
        try{
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT id_socio, apenom_socio, telefono_socio \n"
                        + "FROM socios\n"
                        + "WHERE estado_socio = 'A' AND id_socio LIKE '%"+socio+"%' OR estado_socio = 'A' AND apenom_socio LIKE '%"+ socio +"%'");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][3];

        rs.first();
        for (int fila = 0; fila < registros; fila++) {

            datos[fila][0] = rs.getObject(1);  // ID SOCIO
            datos[fila][1] = rs.getObject(2);  // APENOM SOCIO
            datos[fila][2] = rs.getObject(3);  // TELEFONO

            rs.next();
        }    
                
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    FiltrarSociosCuotas     Cuotas: " + ex);
        }        
    }
    
    
    //          OTROS    PERSONAS
    
    public void CargarPersonasCuotas() {
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT persona,dni,CONCAT(apellidos,' ',nombres) \n"
                    + "FROM personas_terceras \n");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][3];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {

                datos[fila][0] = rs.getObject(1);  // PK PERSONA
                datos[fila][1] = rs.getObject(2);  // DNI
                datos[fila][2] = rs.getObject(3);  // APENOM_ALUMNO

                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarPersonasCuotas    Cuotas: " + ex);
        }
    }

    public void FiltrarPersonasCuotas(String persona){
        try{
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT persona,dni,CONCAT(apellidos,' ',nombres) \n"
                        + "FROM personas_terceras\n"
                        + "WHERE apellidos LIKE '%"+persona+"%' OR nombres LIKE '%"+persona+"%' OR persona LIKE '%"+persona+"%'");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][3];

        rs.first();
        for (int fila = 0; fila < registros; fila++) {

            datos[fila][0] = rs.getObject(1);  // PK PERSONA
            datos[fila][1] = rs.getObject(2);  // DNI
            datos[fila][2] = rs.getObject(3);  // APENOM_ALUMNO

            rs.next();
        }    
                
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    FiltrarPersonasCuotas     Cuotas: " + ex);
        }        
    }
    
}