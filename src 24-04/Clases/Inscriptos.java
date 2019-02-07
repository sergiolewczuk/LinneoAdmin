/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;


public class Inscriptos {
    
    public static Object[][] datos; //Variable que alberga los datos de la BD
    
    public static void CargarAlumnosInscriptos(String tipos, String niveles, String cursos){
        try {
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            String where = "";
            String num_curso = "";
            String div_curso = "";
            
            if ("Todos".equals(tipos)){
                where = "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C'";
            } else if ((!"Todos".equals(tipos)) && (!niveles.equals("Todos")) && (cursos.equals(""))) {  //  ES UNA CARRERA
                where = "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND carrera.nombre = '"+niveles+"'";
            } else if ((!"Todos".equals(tipos)) && (niveles.equals("Todos"))) {  //  ES UNA CARRERA
                where = "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND carrera.tipo = '"+tipos+"'";
            } else if ((!"Todos".equals(tipos)) && (cursos.equals("Todos"))) { //  ES UN GRADO
                where = "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND carrera.nombre = '"+niveles+"'";
            } else if ((!"Todos".equals(tipos)) && (!cursos.equals(""))) { //  ES UN GRADO
                
                if (cursos.contains("-")) {
                    num_curso = cursos.split("-")[0];
                    div_curso = cursos.split("-")[1];
                    
                    where = "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND carrera.nombre = '"+niveles+"' AND num_curso = "+num_curso+" AND div_curso = '"+div_curso+"'";
                } else {
                    num_curso = "NULL";
                    div_curso = cursos;
                    
                    where = "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND carrera.nombre = '"+niveles+"' AND num_curso IS NULL AND div_curso = '"+div_curso+"'";
                }
            }
            
            rs = st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, IFNULL(IFNULL(CONCAT(carrera_grados.num_curso,'-',carrera_grados.div_curso,' ',carrera.nombre),CONCAT(carrera_grados.div_curso,' ',carrera.nombre)), carrera.nombre), \n"
                    + "IF(inscripciones_carreras.fecha_inscripcion='2015-01-01','Se Inscribió el año pasado',inscripciones_carreras.fecha_inscripcion),"
                    + "inscripciones_carreras.estado, inscripciones_carreras.id\n"
                    + "FROM alumnos \n"
                    + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno \n"
                    + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id \n"
                    + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera \n"
                    + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado \n"
                    + where + "\n"
                    + "ORDER BY alumnos.id_alumno");
            rs.last();

            Integer registros = rs.getRow();
            datos = new Object[registros][6];

            rs.first();
            for (int fila = 0; fila < registros; fila++) {
                
                datos[fila][0] = rs.getObject(1);  // ID_ALUMNO
                datos[fila][1] = rs.getObject(2);  // APENOM_ALUMNO
                datos[fila][2] = rs.getObject(3);  // NOMBRE_CARRERA 
                datos[fila][3] = rs.getObject(4);  // FECHA INSCRIPCION
                
                switch (rs.getString(5)) {
                    case "C" :
                        datos[fila][4] = "Cursando";
                    break; 
                    case "A" :
                        datos[fila][4] = "Aprobado";
                    break;
                    case "D" :
                        datos[fila][4] = "Desaprobado";
                    break;   
                }
                
                datos[fila][5] = rs.getObject(6);
                

                rs.next();
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    CargarAlumnosInscriptos    Inscriptos: " + ex);
        }    
    }
    
    
    public static void FiltrarAlumnosInscriptos(String alumno){
        try{
        ResultSet rs;
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT alumnos.id_alumno, apenom_alumno, CONCAT(carrera_grados.num_curso,' ',carrera_grados.div_curso) "
                        + "FROM alumnos "
                        + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno "
                        + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id "
                        + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera "
                        + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado "
                        + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' "
                        + "AND alumnos.id_alumno LIKE '%"+alumno+"%' OR alumnos.apenom_alumno LIKE '%"+alumno+"%'"
                        + "GROUP BY inscripciones_carreras.id");
        rs.last();
        
        Integer registros = rs.getRow();
        datos = new Object[registros][3];
        
        rs.first();
        
        for (int fila = 0; fila < registros; fila++) {
            
            datos[fila][0] = rs.getObject(1);  // DNI_ALUMNO
            datos[fila][1] = rs.getObject(2);  // APENOM_ALUMNO
            datos[fila][2] = rs.getObject(3);  // NOMBRE_CARRERA             
            
        rs.next();
        }    
                
        rs.close();
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    FiltrarAlumnosInscriptos     Inscriptos: " + ex);
        }        
    }
    
    
    public static void UpdatearEstado(String estado, String id) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE inscripciones_carreras SET estado = '"+estado+"' WHERE id = "+id+" ");
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    UpdatearEstado     Inscriptos: " + ex);
        }
    }
}
