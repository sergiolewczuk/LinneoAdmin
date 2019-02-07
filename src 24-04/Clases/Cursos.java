/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Sergio
 */
public class Cursos {
    
    public Object[][] datos; //Variable que alberga los datos de la BD
    
    public void Cargar(){
        try{
            ResultSet rs;   
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            rs=st.executeQuery("(SELECT carrera_grados.id as dd,CONCAT(IFNULL(carrera_grados.num_curso,''),' ',carrera_grados.div_curso),carrera.id,carrera.nombre,carrera.tipo,\n"
                    + "CONCAT(CONCAT('Altas ',(SELECT COUNT(*) FROM inscripciones_grado JOIN carrera_grados ON inscripciones_grado.id_grado = carrera_grados.id WHERE inscripciones_grado.id_grado = dd AND inscripciones_grado.estado = 'C')),'  -  ',\n"
                    + "CONCAT('Bajas ',(SELECT COUNT(*) FROM inscripciones_grado JOIN carrera_grados ON inscripciones_grado.id_grado = carrera_grados.id WHERE inscripciones_grado.id_grado = dd AND inscripciones_grado.estado = 'B'))),\n"
                    + "\n"
                    + "carrera.estado,carrera.nivel\n"
                    + "FROM carrera JOIN carrera_grados ON carrera_grados.carreras = carrera.id)\n"
                    + "UNION\n"
                    + "(SELECT '' AS carrera_grados_id, '' AS carrera_grados_curso,carrera.id as dd,carrera.nombre,carrera.tipo,\n"
                    + "CONCAT(CONCAT('Altas ',(SELECT COUNT(*) FROM inscripciones_carreras JOIN carrera ON inscripciones_carreras.id_carrera = carrera.id WHERE inscripciones_carreras.id_carrera = dd AND inscripciones_carreras.estado = 'C')),'  -  ',\n"
                    + "CONCAT('Bajas ',(SELECT COUNT(*) FROM inscripciones_carreras JOIN carrera ON inscripciones_carreras.id_carrera = carrera.id WHERE inscripciones_carreras.id_carrera = dd AND inscripciones_carreras.estado = 'B'))),\n"
                    + "carrera.estado,carrera.nivel\n"
                    + "FROM carrera WHERE NOT EXISTS (SELECT * FROM carrera_grados WHERE carrera_grados.carreras = carrera.id))");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][8];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getString(2);
                datos[fila][1] = rs.getString(4);
                datos[fila][2] = rs.getString(5);
                datos[fila][3] = rs.getString(6);
                datos[fila][4] = rs.getString(7);
                datos[fila][5] = rs.getString(3);
                datos[fila][6] = rs.getString(1);
                datos[fila][7] = rs.getString(8);
                
            rs.next();
            }
            rs.close();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    Cargar     Cursos: "+ex);
        }
    }
    
    public void CargarCuentasCuotas(String CODIGO){
        try{
            
            ResultSet rs;   
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            rs=st.executeQuery("SELECT nivel, " 
                                + "num_cuenta, "
                                + "(SELECT nombre FROM cuentas WHERE num_cuenta = niveles_cuentas_cuotas.num_cuenta) AS nomCuenta, "
                                + "haber "
                                + "FROM niveles_cuentas_cuotas "
                                + "WHERE nivel = '"+CODIGO+"'");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][4];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                datos[fila][0] = rs.getObject(1);
                datos[fila][1] = rs.getObject(2);
                datos[fila][2] = rs.getObject(3);
                if("0".equals(rs.getObject(4).toString())){
                    datos[fila][3] = "Debe";
                }else{
                    datos[fila][3] = "Haber";
                }          
            rs.next();
            }
            rs.close();
             
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    CargarCuentasCuotas     Cursos: "+ex);
        }
    }
    
    public void InsertarCuentasCuotas(String CODIGO, String CUENTA, String DEBE){
        try{
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if("Debe".equals(DEBE)){
                st.executeUpdate("INSERT INTO niveles_cuentas_cuotas (nivel,num_cuenta,haber) VALUES ('"+CODIGO+"',"+CUENTA+",0)");
            }else{
                st.executeUpdate("INSERT INTO niveles_cuentas_cuotas (nivel,num_cuenta,haber) VALUES ('"+CODIGO+"',"+CUENTA+",1)");
            }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    InsertarCuentasCuotas     Cursos: "+ex);
        }
    }
    
    public void EliminarCuentasCuotas(String niveles, String num_cuenta, String haber){
        try{
            ResultSet rs;   
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            st.executeUpdate("DELETE FROM niveles_cuentas_cuotas WHERE nivel = '"+niveles+"' AND num_cuenta = "+num_cuenta+" AND haber = "+haber+"");
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    EliminarCuentasCuotas     Cursos: "+ex);
        }
    }
    
    
    public void CargarCuentasInscripcion(String Nivel){
        try{
            ResultSet rs;   
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            rs=st.executeQuery("SELECT nivel,\n"
                            + "num_cuenta,\n"
                            + "(SELECT nombre FROM cuentas WHERE num_cuenta = niveles_cuentas_inscripcion.num_cuenta) AS nomCuenta,\n"
                            + "haber\n"
                            + "FROM niveles_cuentas_inscripcion\n"
                            + "WHERE nivel = '"+Nivel+"'");
            rs.last();

            if (rs.getRow() > 0){
                Integer registros = rs.getRow();
                datos = new Object[registros][4];

                rs.first();
                for (int fila = 0; fila < registros; fila++) {
                    datos[fila][0] = rs.getObject(1);
                    datos[fila][1] = rs.getObject(2);
                    datos[fila][2] = rs.getObject(3);
                    if ("0".equals(rs.getObject(4).toString())) {
                        datos[fila][3] = "Debe";
                    } else {
                        datos[fila][3] = "Haber";
                    }
                    rs.next();
                } 
            }
            
            rs.close();
             
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    CargarCuentasInscripcion     Cursos: "+ex);
        }
    }
    
    public void InsertarCuentasInscripcion(String niveles, String cuenta, String debe){
        try{
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if("Debe".equals(debe)){
                st.executeUpdate("INSERT INTO niveles_cuentas_inscripcion (nivel,num_cuenta,haber) VALUES ('"+niveles+"',"+cuenta+",0)");
            }else{
                st.executeUpdate("INSERT INTO niveles_cuentas_inscripcion (nivel,num_cuenta,haber) VALUES ('"+niveles+"',"+cuenta+",1)");
            }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    InsertarCuentasInscripcion     Cursos: "+ex);
        }
    }
    
    public void EliminarCuentasInscripcion(String niveles, String num_cuenta, String haber){
        try{
            ResultSet rs;   
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            st.executeUpdate("DELETE FROM niveles_cuentas_inscripcion WHERE nivel = '"+niveles+"' AND num_cuenta = "+num_cuenta+" AND haber = "+haber+"");
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    EliminarCuentasInscripcion     Cursos: "+ex);
        }
    }
    
    public static Object[] MostrarDatosGrados(String GRADO){
        Object[] Info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT carrera_grados.id, IFNULL(carrera_grados.num_curso,''), carrera_grados.div_curso, IFNULL(carrera_grados.turno_curso,''), carrera_grados.cant_alumnos, "
                            + "carrera.id, carrera.nombre,carrera.tipo, "
                            + "IFNULL(carrera_grados.monto_inscripcion,carrera.monto_inscripcion), "
                            + "IFNULL(carrera_grados.monto_cuota,carrera.monto_cuota), "
                            + "IFNULL(carrera_grados.monto_cuota_anticipado,carrera.monto_cuota_anticipado), "
                            + "(SELECT COUNT(*) FROM inscripciones_grado WHERE id_grado = carrera_grados.id) AS cant "
                            + "FROM carrera_grados JOIN carrera ON carrera.id = carrera_grados.carreras "
                            + "WHERE carrera_grados.id = "+GRADO+"");
            rs.last();
            Info = new Object[15];
            
            Info[1] = rs.getObject(1);      // ID_CURSO
            Info[2] = rs.getObject(2);      // NUM_CURSO
            Info[3] = rs.getObject(3);      // DIV_CURSO
            Info[4] = rs.getObject(4);      // TURNO_CURSO
            Info[5] = rs.getObject(5);      // CANT_ALUMNOS
            Info[6] = rs.getObject(6);      // ID_CARRERA
            Info[7] = rs.getObject(7);      // NOMBRE_CARRERA
            Info[8] = rs.getObject(8);      // TIPO_CARRERA
            Info[9] = rs.getObject(9);      // MONTO_INSCRIPCION
            Info[10] = rs.getObject(10);    // MONTO_CUOTA
            Info[11] = rs.getObject(11);    // MONTO_CUOTA_ANTICIPADO
            Info[12] = rs.getObject(12);    // TOTAL_ALUMNOS_INSCRIPTOS
            
            rs.close();
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    MostrarDatosGrados     Cursos: "+ex);
        }
        return Info;
    }
    
    public static Object[] MostrarDatosCarreras(String carrera){
        Object[] Info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT carrera.id, carrera.nombre,carrera.tipo, "
                            + "carrera.monto_inscripcion, "
                            + "carrera.monto_cuota, "
                            + "carrera.monto_cuota_anticipado, "
                            + "(SELECT COUNT(*) FROM inscripciones_carreras WHERE id_carrera = carrera.id) AS cant, carrera.nivel "
                            + "FROM carrera "
                            + "WHERE carrera.id = '"+carrera+"' or carrera.nombre = '"+carrera+"'");
            rs.last();
            Info = new Object[15];
            
            Info[1] = rs.getObject(1);      // ID_CARRERA
            Info[2] = rs.getObject(2);      // NOMBRE_CURSO
            Info[3] = rs.getObject(3);      // TIPO
            Info[4] = rs.getObject(4);      // MONTO_INSCRIPCION
            Info[5] = rs.getObject(5);      // MONTO_CUOTA
            Info[6] = rs.getObject(6);      // MONTO_COUTA_ANTICIPADO
            Info[7] = rs.getObject(7);      // CANT_ALUMNO_INSCRIPTO
            Info[8] = rs.getObject(8);      // NIVEL
            
            rs.close();
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    MostrarDatosCarreras     Cursos: "+ex);
        }
        return Info;
    }
    
    public void ModificarImporteCursoIndividual(BigDecimal monto_cuota,BigDecimal monto_anticipado,BigDecimal inscripcion,BigDecimal cant_alumnos,String grado, String carrera){
        try{
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(grado==null){    // ES CARRERA
                
                st.executeUpdate("UPDATE carrera SET monto_cuota="+monto_cuota+", monto_cuota_anticipado="+monto_anticipado+", "
                            + "monto_inscripcion="+inscripcion+""
                            + "WHERE nombre = '"+carrera+"'");   
                
            }else{              // ES GRADO
                
                st.executeUpdate("UPDATE carrera_grados SET monto_cuota="+monto_cuota+", monto_cuota_anticipado="+monto_anticipado+", "
                            + "monto_inscripcion="+inscripcion+""
                            + "WHERE id = '"+grado+"' AND carreras = '"+carrera+"'");
                
            }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    ModificarImporteCursoIndividual     Cursos: "+ex);
        }
    }
    
    public void ModificarImporteCuotasCarrera(Double monto_cuota, Double monto_anticipado, String grado, String carrera){
        try{
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(grado==null){    // ES CARRERA
                st.executeUpdate("UPDATE carrera SET monto_cuota="+monto_cuota+", monto_cuota_anticipad="+monto_anticipado+" "
                                + "WHERE id = '"+carrera+"'");
            }else{              // ES GRADO
                st.executeUpdate("UPDATE carrera_grados SET monto_cuota="+monto_cuota+", monto_cuota_anticipad="+monto_anticipado+" "
                                + "WHERE carreras = '"+carrera+"' AND id = '"+grado+"'");
            }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    ModificarImporteCuotasCarrera     Cursos: "+ex);
        }
    }
    
    public void ModificarImporteInscripcionCarrera(Double inscripcion, String grado, String carrera){
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(grado==null){    // ES CARRERA
                st.executeUpdate("UPDATE carrera SET monto_inscripcion = '"+inscripcion+"' "
                                + "WHERE id = '"+carrera+"'");
            }else{              // ES GRADO
                st.executeUpdate("UPDATE carrera_grados SET monto_inscripcion = '"+inscripcion+"' "
                                + "WHERE carreras = '"+carrera+"' AND id = '"+grado+"'");
            }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    ModificarImporteInscripcionCarrera     Cursos: "+ex);
        }
    }
    
    public Object[][] Seleccionar_Nivel_x_Tipo(String Tipo){
        Object[][] info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT DISTINCT nivel_curso FROM cursos WHERE tipo_curso = '"+Tipo+"'");
            rs.last();
                    
            Integer registros = rs.getRow();
            info = new Object[registros][1];
                    
            rs.first();
            
            for(int a = 0; a < registros; a++){
                info[a][0] =  rs.getObject(1);
                rs.next();
            }
            
            rs.close();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    Seleccionar_Cursos_x_Tipo     Cursos: "+ex);
        }
        return info;
    }
    
    public Object[][] SeleccionarCursosCarrera(String carrera){
        Object[][] info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT IFNULL(IFNULL(CONCAT(carrera_grados.num_curso,'-',carrera_grados.div_curso),carrera_grados.div_curso), carrera.nombre) \n"
                    + "FROM carrera_grados \n"
                    + "JOIN carrera ON carrera_grados.carreras = carrera.id \n"
                    + "WHERE carrera.nombre = '"+carrera+"'");
            
            rs.last();
        
            //System.out.printf("SELECT DISTINCT nombre_curso FROM cursos JOIN cursos_cuentas_inscripcion ON cursos.nivel_curso = cursos_cuentas_inscripcion.nivel_curso WHERE cursos_cuentas_inscripcion.nivel_curso = '"+Nivel+"' AND tipo_curso = '"+Tipo+"' \n");
            
            Integer registros = rs.getRow();
            info = new Object[registros][1];
                    
            rs.first();
            
            if(registros > 0){
                for (int a = 0; a < registros; a++) {
                    info[a][0] = rs.getObject(1);
                    rs.next();
                }    
            }           
            
            rs.close();            
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    SeleccionarCursosCarrera     Cursos: "+ex);
        }
        return info;
    }
    
    public Object[][] SeleccionarTipoCursos(){
        Object[][] info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT DISTINCT tipo FROM carrera");
            rs.last();
        
            //System.out.printf("SELECT DISTINCT nombre_curso FROM cursos JOIN cursos_cuentas_inscripcion ON cursos.nivel_curso = cursos_cuentas_inscripcion.nivel_curso WHERE cursos_cuentas_inscripcion.nivel_curso = '"+Nivel+"' AND tipo_curso = '"+Tipo+"' \n");
            
            Integer registros = rs.getRow();
            info = new Object[registros][1];
                    
            rs.first();
            
            if(registros > 0){
                for (int a = 0; a < registros; a++) {
                    info[a][0] = rs.getObject(1);
                    rs.next();
                }    
            }           
            
            rs.close();
                        
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    SeleccionarTipoCursos     Cursos: "+ex);
        }
        return info;
    }
    
    public Object[][] SeleccionarCarreras(String tipo){
        Object[][] info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT DISTINCT nombre FROM carrera JOIN niveles_cuentas_inscripcion ON niveles_cuentas_inscripcion.nivel = carrera.nivel WHERE carrera.tipo = '"+ tipo +"'");
            rs.last();
        
            Integer registros = rs.getRow();
            info = new Object[registros][1];
                    
            rs.first();
            
            if(registros > 0){
                for (int a = 0; a < registros; a++) {
                    info[a][0] = rs.getObject(1);
                    rs.next();
                }    
            }           
            
            rs.close();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    SeleccionarCarreras     Cursos: "+ex);
        }
        return info;
    }
    
    public static boolean AproboCorrelativas(String alumno, String carrera, String anio_cursada) {
        boolean aprobo = true;

        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = st.executeQuery("SELECT carrera FROM carreras_correlatividades WHERE correlativa = "+carrera+"");
            rs.last();
            
            int registros = rs.getRow();
            
            rs.first();
            
            ResultSet rs2;
            Statement st2 = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            for (int a = 0; a < registros; a++) {
                //POR CARRERA VEO SI APROBO
                String carrera_requisito = rs.getString(1);
                rs2 = st2.executeQuery("SELECT * FROM inscripciones_carreras WHERE id_carrera = "+carrera_requisito+" AND id_alumno = "+alumno+" AND anio_t = "+anio_cursada+" AND estado = 'A'");
                rs2.last();
                
                int encontro = rs2.getRow();
                
                rs2.close();
                //VEO SI ENCONTRO CARRERAS APROBADAS
                if (encontro == 0) {
                    //no encontro
                    aprobo = false;
                    break;
                }
                
                rs.next();
            }

            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Seleccionar_monto_inscripción     Cursos: " + ex);
        }

        return aprobo;
    }
    
    public String[] Seleccionar_monto_inscripción(String nombre_curso, String carrera, String tipo, String alumno, String anio_t){
        String[] info = new String[2];
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if (nombre_curso.equals(carrera)) { // ES CARRERA
                
                rs=st.executeQuery("SELECT monto_inscripcion,id FROM carrera WHERE nombre = '"+carrera+"' AND tipo = '"+tipo+"'");
                rs.last();
                
                if(rs.getRow() > 0) {
                     // MONTO_INSCRIPCION - ID_GRADO o ID_CARRERA

                    info[0] = rs.getObject(1).toString();
                    info[1] = rs.getObject(2).toString();
                    
                }
                
                rs.close();
                
                if (AproboCorrelativas(alumno,info[1],anio_t)) {
                    rs=st.executeQuery("SELECT monto_inscripcion_primero FROM carrera WHERE id = "+info[1]+"");
                    rs.last();
                    
                    info[0] = rs.getObject(1).toString();
                    
                    rs.close();
                }
                 
            } else { // ES GRADO
                String num_curso = null;
                String div_curso = null;

                String consulta = null;
                
                if (nombre_curso.contains("-")) {
                    num_curso = nombre_curso.split("-")[0];
                    div_curso = nombre_curso.split("-")[1];
                    
                    consulta = "SELECT carrera_grados.monto_inscripcion,carrera_grados.id FROM carrera_grados  JOIN carrera ON carrera.id = carrera_grados.carreras WHERE num_curso = "+num_curso+" AND div_curso = '"+div_curso+"' AND carrera.nombre = '"+carrera+"'";
                } else {
                    num_curso = "NULL";
                    div_curso = nombre_curso;
                    
                    consulta = "SELECT carrera_grados.monto_inscripcion,carrera_grados.id FROM carrera_grados  JOIN carrera ON carrera.id = carrera_grados.carreras WHERE num_curso IS NULL AND div_curso = '"+div_curso+"' AND carrera.nombre = '"+carrera+"'";
                } 
                
                rs=st.executeQuery(consulta);
                rs.last();
                
                if(rs.getRow() > 0){
                     // MONTO_INSCRIPCION - ID_GRADO o ID_CARRERA

                    info[0] = rs.getObject(1).toString();
                    info[1] = rs.getObject(2).toString();
                    
                }
                
                rs.close();
            }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    Seleccionar_monto_inscripción     Cursos: "+ex);
        }
        return info;
    }
    
    
    public void Seleccionar_cursos_Inscriptos(String CODIGO){
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT id_curso,"
                            + "(SELECT CONCAT(nombre_curso,' ',nivel_curso) FROM cursos WHERE id_curso = axc.id_curso) AS nombre_curso,"
                           + "inscripcion_desde,inscripcion_hasta,beca_monto,beca_hasta,estado "
                            + "FROM axc "
                            + "WHERE id_alumno='"+CODIGO+"'");
            rs.last();

            //      {"COD. CURSO","NOM. CURSO","INSC. DESDE","INSC. HASTA","BECA MONTO","BECA HASTA","ESTADO"}
            
            Integer registros = rs.getRow();
            datos = new Object[registros][7];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                for (int cols = 0; cols < 7; cols++) {
                    datos[fila][cols] = rs.getObject(cols + 1);
                }
            rs.next();
            }
            rs.close();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    Seleccionar_cursos_Inscriptos     Cursos: "+ex);
        }
    }
}
