package Clases;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;

public class Alumnos {
    
    public Object[][] datos; //Variable que alberga los datos de la BD
    public static String excepcion = null;
    
    public void Cargar(){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT id_alumno,dni_alumno,apenom_alumno,domicilio_alumno,telefono_alumno FROM alumnos");
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
        //System.out.println(datos.toString());
        }catch(Exception ex){
            System.out.println("Se han producido excepciones Cargar     Alumnos: "+ex);
            excepcion = "Se han producido excepciones   Cargar     Alumnos: "+ex;
        }
    }
    
    public void Nuevo(String cod_alumno, String dni_alumno, String apenom_alumno, String fecha_nac, String tel_tutor){
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("INSERT INTO alumnos (id_alumno,dni_alumno,apenom_alumno,nacimiento_alumno,telefono_tutor) VALUES ("+cod_alumno+","+dni_alumno+",'"+apenom_alumno+"','"+fecha_nac+"','"+tel_tutor+"')");
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones Nuevo     Alumnos: "+ex);
            excepcion = "Se han producido excepciones    Nuevo     Alumnos: "+ex;
        }
    }
    
    public String Update(
                    String cod_viejo,
                                /*VARIABLES ALUMNO*/ 
                    String dni_alumno, String apenom_alumno, String fecha_alumno, String domicilio_alumno, String localidad_alumno, String telefono_alumno, String mail_alumno, String codigo_alumno,
                                /* VARIABLES PADRE */
                    String dni_padre, String apenom_padre, String profesion_padre, String fecha_padre, String estado_civil_padre, String domicilio_padre, String localidad_padre, String telefono_padre, String mail_padre, String convive_padre,
                                /* VARIABLES MADRE */
                    String dni_madre, String apenom_madre, String profesion_madre, String fecha_madre, String estado_civil_madre, String domicilio_madre, String localidad_madre, String telefono_madre, String mail_madre, String convive_madre,
                                /* VARIABLES TUTOR */
                    String dni_tutor, String apenom_tutor, String profesion_tutor, String fecha_tutor, String estado_civil_tutor, String domicilio_tutor, String localidad_tutor, String telefono_tutor, String mail_tutor, String convive_tutor) {
        
        String info = null;
        
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            st.executeUpdate("UPDATE alumnos SET id_alumno = '"+codigo_alumno+"', dni_alumno = '"+dni_alumno+"', apenom_alumno = '"+apenom_alumno+"', nacimiento_alumno = '"+fecha_alumno+"', domicilio_alumno = '"+domicilio_alumno+"', "
                                            + "localidad_alumno = '"+localidad_alumno+"', telefono_alumno = '"+telefono_alumno+"', mail_alumno = '"+mail_alumno+"', "
                                            /* VARIABLES PADRE */
                                            + "apenom_padre = '"+apenom_padre+"', dni_padre = '"+dni_padre+"', nacimiento_padre = '"+fecha_padre+"', estadocivil_padre = '"+estado_civil_padre+"', profesion_padre = '"+profesion_padre+"', "
                                            + "telefono_padre = '"+telefono_padre+"', mail_padre = '"+mail_padre+"', domicilio_padre = '"+domicilio_padre+"', localidad_padre = '"+localidad_padre+"', convive_padre = '"+convive_padre+"', "
                                            /* VARIABLES MADRE */
                                            + "apenom_madre = '"+apenom_madre+"', dni_madre = '"+dni_madre+"', nacimiento_madre = '"+fecha_madre+"', estadocivil_madre = '"+estado_civil_madre+"', profesion_madre = '"+profesion_madre+"', "
                                            + "telefono_madre = '"+telefono_madre+"', mail_madre = '"+mail_madre+"', domicilio_madre = '"+domicilio_madre+"', localidad_madre = '"+localidad_madre+"', convive_madre = '"+convive_madre+"', "
                                            /* VARIABLES TUTOR */
                                            + "apenom_tutor = '"+apenom_tutor+"', dni_tutor = '"+dni_tutor+"', nacimiento_tutor = '"+fecha_tutor+"', estadocivil_tutor = '"+estado_civil_tutor+"', profesion_tutor = '"+profesion_tutor+"', "
                                            + "telefono_tutor = '"+telefono_tutor+"', mail_tutor = '"+mail_tutor+"', domicilio_tutor = '"+domicilio_tutor+"', localidad_tutor = '"+localidad_tutor+"', convive_tutor = '"+convive_tutor+"'"
                                            + " WHERE "
                                            + "id_alumno = '"+cod_viejo+"'");
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Update   Alumnos: " + ex);
            excepcion = "Se han producido excepciones   Update     Alumnos: "+ex;
            info = ex.toString();
        }
        return info;
    }
    
    public void Guardar(        /*VARIABLES ALUMNO*/ 
                    String dni_alumno, String apenom_alumno, String fecha_alumno, String domicilio_alumno, String localidad_alumno, String telefono_alumno, String mail_alumno, String codigo_alumno,
                                /* VARIABLES PADRE */
                    String dni_padre, String apenom_padre, String profesion_padre, String fecha_padre, String estado_civil_padre, String domicilio_padre, String localidad_padre, String telefono_padre, String mail_padre, String convive_padre,
                                /* VARIABLES MADRE */
                    String dni_madre, String apenom_madre, String profesion_madre, String fecha_madre, String estado_civil_madre, String domicilio_madre, String localidad_madre, String telefono_madre, String mail_madre, String convive_madre,
                                /* VARIABLES TUTOR */
                    String dni_tutor, String apenom_tutor, String profesion_tutor, String fecha_tutor, String estado_civil_tutor, String domicilio_tutor, String localidad_tutor, String telefono_tutor, String mail_tutor, String convive_tutor) {
        
        try {
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            st.executeUpdate("INSERT INTO alumnos (id_alumno, dni_alumno, apenom_alumno, nacimiento_alumno, domicilio_alumno, localidad_alumno, telefono_alumno, mail_alumno, "
                                            /* VARIABLES PADRE */
                                            + "apenom_padre, dni_padre, nacimiento_padre, estadocivil_padre, profesion_padre, telefono_padre, mail_padre, domicilio_padre, localidad_padre, convive_padre, "
                                            /* VARIABLES MADRE */
                                            + "apenom_madre, dni_madre, nacimiento_madre, estadocivil_madre, profesion_madre, telefono_madre, mail_madre, domicilio_madre, localidad_madre, convive_madre, "
                                            /* VARIABLES TUTOR */
                                            + "apenom_tutor, dni_tutor, nacimiento_tutor, estadocivil_tutor, profesion_tutor, telefono_tutor, mail_tutor, domicilio_tutor, localidad_tutor, convive_tutor)"
                                            + " VALUES ('"+codigo_alumno+"', '"+dni_alumno+"', '"+apenom_alumno+"', '"+fecha_alumno+"', '"+domicilio_alumno+"', '"+localidad_alumno+"', '"+telefono_alumno+"', '"+mail_alumno+"', "
                                            /* VARIABLES PADRE */    
                                            + "'"+apenom_padre+"', '"+dni_padre+"', '"+fecha_padre+"', '"+estado_civil_padre+"', '"+profesion_padre+"', '"+telefono_padre+"', '"+mail_padre+"', '"+domicilio_padre+"', '"+localidad_padre+"', '"+convive_padre+"', "
                                            /* VARIABLES MADRE */
                                            + "'"+apenom_madre+"', '"+dni_madre+"', '"+fecha_madre+"', '"+estado_civil_madre+"', '"+profesion_madre+"', '"+telefono_madre+"', '"+mail_madre+"', '"+domicilio_madre+"', '"+localidad_madre+"', '"+convive_madre+"', "
                                            /* VARIABLES TUTOR */
                                            + "'"+apenom_tutor+"', '"+dni_tutor+"', '"+fecha_tutor+"', '"+estado_civil_tutor+"', '"+profesion_tutor+"', '"+telefono_tutor+"', '"+mail_tutor+"', '"+domicilio_tutor+"', '"+localidad_tutor+"', '"+convive_tutor+"')");
            
        } catch (Exception ex) {
            System.out.println("Se han producido excepciones    Guardar   Alumnos: " + ex);
            excepcion = "Se han producido excepciones   Guardar     Alumnos: "+ex;
        }
    }
    
    public static Object[] Mostrar_Datos(String DNI){
        Object[] Info = null;
        try{
            ResultSet rs;
            Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=st.executeQuery("SELECT id_alumno,dni_alumno,apenom_alumno,nacimiento_alumno,TIMESTAMPDIFF(YEAR, nacimiento_alumno, CURDATE()) AS Edad,domicilio_alumno,localidad_alumno,telefono_alumno,mail_alumno,"
                                              + "apenom_padre,dni_padre,nacimiento_padre,estadocivil_padre,profesion_padre,telefono_padre,mail_padre,domicilio_padre,localidad_padre,convive_padre,"
                                              + "apenom_madre,dni_madre,nacimiento_madre,estadocivil_madre,profesion_madre,telefono_madre,mail_madre,domicilio_madre,localidad_madre,convive_madre,"
                                              + "apenom_tutor,dni_tutor,nacimiento_tutor,estadocivil_tutor,profesion_tutor,telefono_tutor,mail_tutor,domicilio_tutor,localidad_tutor,convive_tutor "
                                              + "FROM alumnos "
                                              + "WHERE id_alumno = '"+DNI+"'");
            rs.last();
            Integer registros = rs.getRow();
            rs.first();
            Info = new Object[45];
            
            if(registros > 0){
                //      ID_ALUMNO                   DNI_ALUMNO                   APENOM_ALUMNO              NACIMIENTO_ALUMNO               EDAD_ALUMNO
                Info[1] = rs.getObject(1);   Info[2] = rs.getObject(2);  Info[3] = rs.getObject(3);  Info[4] = rs.getObject(4);  Info[5] = rs.getObject(5);         // VARIABLES ALUMNOS
                //      DOMICILIO_ALUMNO            LOCALIDAD_ALUMNO             TELEFONO_ALUMNO             MAIL_ALUMNO
                Info[6] = rs.getObject(6);   Info[7] = rs.getObject(7);  Info[8] = rs.getObject(8);  Info[9] = rs.getObject(9);                                     // VARIABLES ALUMNOS

                //      APENOM_PADRE                DNI_PADRE                   NACIMIENTO_PADRE            ESTADO CIVIL_PADRE              PROFESION_PADRE
                Info[10] = rs.getObject(10); Info[11] = rs.getObject(11); Info[12] = rs.getObject(12); Info[13] = rs.getObject(13);  Info[14] = rs.getObject(14);   // VARIABLES PADRE 
                //      TELEFONO_PADRE              MAIL_PADRE                  DOMICILIO_PADRE             LOCALIDAD_PADRE                 CONVIVE_PADRE
                Info[15] = rs.getObject(15); Info[16] = rs.getObject(16); Info[17] = rs.getObject(17);  Info[18] = rs.getObject(18); Info[19] = rs.getObject(19);   // VARIABLES PADRE

                //      APENOM_MADRE                DNI_MADRE                   NACIMIENTO_MADRE            ESTADO CIVIL_MADRE              PROFESION_MADRE
                Info[20] = rs.getObject(20); Info[21] = rs.getObject(21); Info[22] = rs.getObject(22); Info[23] = rs.getObject(23);  Info[24] = rs.getObject(24);   // VARIABLES MADRE
                //      TELEFONO_MADRE               MAIL_MADRE                  DOMICILIO_MADRE             LOCALIDAD_MADRE                CONVIVE_MADRE
                Info[25] = rs.getObject(25); Info[26] = rs.getObject(26); Info[27] = rs.getObject(27); Info[28] = rs.getObject(28);  Info[29] = rs.getObject(29);   // VARIABLES MADRE

                //      APENOM_TUTOR                DNI_TUTOR                   NACIMIENTO_TUTOR            ESTADO CIVIL_TUTOR              PROFESION_TUTOR
                Info[30] = rs.getObject(30);  Info[31] = rs.getObject(31); Info[32] = rs.getObject(32); Info[33] = rs.getObject(33); Info[34] = rs.getObject(34);   // VARIABLES TUTOR
                //      TELEFONO_TUTOR              MAIL_TUTOR                  DOMICILIO_TUTOR             LOCALIDAD_TUTOR                 CONVIVE_TUTOR
                Info[35] = rs.getObject(35);  Info[36] = rs.getObject(36); Info[37] = rs.getObject(37); Info[38] = rs.getObject(38); Info[39] = rs.getObject(39);   // VARIABLES TUTOR

            }
            
            for(int a = 0; a < Info.length; a++){
                if(Info[a]==null){
                    Info[a] = "";
                }
            }
            
            rs.close();
        }catch (Exception ex){
            System.out.println("Se han producido excepciones    Mostrar_Datos   Alumnos: "+ex);
            excepcion = "Se han producido excepciones    Mostrar_Datos     Alumnos: "+ex;
        }
        return Info;
    }
    
    public Boolean ComprobarExistente(String alumno){
        Boolean encontro = false;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT id_alumno FROM alumnos WHERE id_alumno = " + alumno);
        rs.last();
        
        Integer registros = rs.getRow();
        rs.first();
        
        rs.close();
        
        if (registros > 0) {
            encontro = true;
        }
        }catch(Exception ex){
            System.out.println("Se han producido excepciones   ComprobarExistente   Alumnos: "+ex);
            excepcion = "Se han producido excepciones    ComprobarExistente     Alumnos: "+ex;
        }
        return encontro;
    }
    
    
    public void FiltrarXApeNom(String buscar){
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT id_alumno,dni_alumno,apenom_alumno,domicilio_alumno,telefono_alumno FROM alumnos WHERE apenom_alumno LIKE '%"+buscar+"%' OR id_alumno LIKE '%"+buscar+"%'");
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
            System.out.println("Se han producido excepciones  FiltrarXApeNom   Alumnos: "+ex);
            excepcion = "Se han producido excepciones    FiltrarXApeNom     Alumnos: "+ex;
        }
    }
    
    public Integer CodUltimo() {
        Integer num = 0;
        
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT MAX(id_alumno) FROM alumnos");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if (registros > 0) {
            num = rs.getInt(1) + 1;
        } else {
            num = 1;
        }
        
        rs.close();
        }catch(Exception ex){
            System.out.println("Se han producido excepciones  CodUltimo   Alumnos: "+ex);
            excepcion = "Se han producido excepciones   CodUltimo     Alumnos: "+ex;
        }
        
        return num;
    }
    
    public Integer InscribirCarrera(String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe, 
                                String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta,
                                String hora, String fecha){
        // CARRERA, ALUMNO, ANIO_T, ANIO_CURSADO (PRIMER AÑO, ETC), ESTADO, FEC_INSC, HORA_INSC, INSC_PAG, INSC_DEB, INSC_DESD, INSC_HAST, BECA, BECA_HAST
        
        Integer id = null;
        
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=st.executeQuery("SELECT anio_t, anio_c, id_carrera, estado FROM inscripciones_carreras WHERE id_alumno = '"+alumno+"' AND id_carrera = '"+carrera+"' ORDER BY anio_t");
        
        rs.last();
        
        Integer registros = rs.getRow();
        
        Integer anio_c = null;   String estado = null; 
        Boolean puede = false;
        
        if(registros > 0){  // TRAER ANIO_T, ANIO_C, Y ESTADO Posibles: Aprobado, Desaprobado, Pendiente y Cursado
            
            Integer anio_t_viejo = rs.getInt(1);
            Integer anio_c_viejo = rs.getInt(2);
            Integer id_carrera_viejo = rs.getInt(3);
            String estado_viejo = rs.getString(4);

            rs.close();
            
            if(anio_t_viejo < anio_t){
                //  SELECCIONAR ESTADO
                switch (estado_viejo) {
                    case "A":
                        anio_c = anio_c_viejo + 1;
                        estado = "C";
                        
                        puede = true;
                        break;
                    case "D":
                        anio_c = anio_c_viejo;
                        estado = "C";
                        
                        puede = true;
                        break;
                    case "P":
                        
                        break;
                    case "C":
                        anio_c = anio_c_viejo + 1;
                        estado = "P";
                        
                        puede = true;
                        break;
                }
            }else if(anio_t_viejo.equals(anio_t)){
                // SELECCIONAR TIPO DE INSCRIPCION ( CURSILLO, CURSADO, LENGUAS ) Y BLOQUEAR.
                rs=st.executeQuery("SELECT tipo,(SELECT tipo AS tipo_nuevo FROM carrera WHERE id = '"+carrera+"')  FROM carrera WHERE id = '"+id_carrera_viejo+"'");
                rs.last();
                
                String tipo_cursado_viejo = rs.getString(1);
                String tipo_cursado_nuevo = rs.getString(2);
                
                rs.close();
                
                // SELECCIONAR NUEVO TIPO Y PERMITIR O NO LA INSCRIPCION
                if(tipo_cursado_viejo.equals(tipo_cursado_nuevo)){
                    switch (estado_viejo) {
                        case "A":   // PERMITIR
                            anio_c = anio_c_viejo + 1;
                            estado = "C";
                            
                            puede = true;
                            break;
                        case "D":   // PERMITIR
                            anio_c = anio_c_viejo;
                            estado = "C";
                            
                            puede = true;
                            break;
                        case "P":   // NO PERMITIR

                            puede = false;
                            break;
                        case "C":   // NO PERMITIR
                            anio_c = anio_c_viejo + 1;
                            estado = "P";
                            
                            puede = false;
                            break;
                    }
                }else{  //PERMITIR
                    
                    // PERMITIR PORQUE EL TIPO DE CURSO EN EL MISMO AÑO ES DIFERENTE
                    
                    estado = "C";
                    puede = true;
                    
                }
                
            }            
        }else{  // NUEVO
            anio_c = 1;
            estado = "C";
            
            puede = true;
        }
        
        if(puede){
            if(beca == null || beca_hasta == null){
                rs=st.executeQuery("SELECT MAX(id) AS ultima_operacion FROM operaciones_detalle");
                
                rs.last();
                
                Integer ultima_operacion = rs.getInt(1);
                
                rs.close();
                
                st.executeUpdate("INSERT INTO inscripciones_carreras (id_detalle_operacion, id_carrera, id_alumno, anio_t, anio_c, estado, fecha_inscripcion, hora_inscripcion, "
                    + "inscripcion_pagado, inscripcion_debe, inscripcion_desde, inscripcion_hasta, beca_monto, beca_hasta) "
                    + "VALUES "
                    + "(" + ultima_operacion + ", '" + carrera + "','" + alumno + "','" + anio_t + "','" + anio_c + "','" + estado + "','"+fecha+"','"+hora+"',"
                    + "'" + inscripcion_pagado + "','" + inscripcion_debe + "','" + inscripcion_desde + "','" + inscripcion_hasta + "',NULL,NULL)");
                
                rs=st.executeQuery("SELECT inscripciones_carreras.id FROM inscripciones_carreras "
                        + "WHERE "
                        + "id_detalle_operacion = "+ultima_operacion+"");
                rs.last();
                
                id = rs.getInt(1);
                
                rs.close();
                
            }else{
                rs=st.executeQuery("SELECT MAX(id) AS ultima_operacion FROM operaciones_detalle");
                rs.last();
                
                Integer ultima_operacion = rs.getInt(1);
                
                rs.close();
                
                st.executeUpdate("INSERT INTO inscripciones_carreras (id_detalle_operacion, id_carrera, id_alumno, anio_t, anio_c, estado, fecha_inscripcion, hora_inscripcion, "
                    + "inscripcion_pagado, inscripcion_debe, inscripcion_desde, inscripcion_hasta, beca_monto, beca_hasta) "
                    + "VALUES "
                    + "(" + ultima_operacion + ", '" + carrera + "','" + alumno + "','" + anio_t + "','" + anio_c + "','" + estado + "','"+fecha+"','"+hora+"',"
                    + "'" + inscripcion_pagado + "','" + inscripcion_debe + "','" + inscripcion_desde + "','" + inscripcion_hasta + "','" + beca + "','" + beca_hasta + "')"); 
                
                
                rs=st.executeQuery("SELECT inscripciones_carreras.id FROM inscripciones_carreras "
                        + "WHERE "
                        + "id_detalle_operacion = "+ultima_operacion+"");
                rs.last();
                
                id = rs.getInt(1);
                
                rs.close();
            }
        }
        
        }catch (Exception ex){
            System.out.println("Se han producido excepciones  InscribirCarrera   Alumnos: "+ex);
            excepcion = "Se han producido excepciones    InscribirCarrera     Alumnos: "+ex;
        }
        return id;
    }
    
    
    public Integer InscribirGrado(String grado, String nombre_grado, String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe, 
                                String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta,
                                String hora, String fecha){
        // CARRERA, ALUMNO, ANIO_T, ANIO_CURSADO (PRIMER AÑO, ETC), ESTADO, FEC_INSC, HORA_INSC, INSC_PAG, INSC_DEB, INSC_DESD, INSC_HAST, BECA, BECA_HAST
        
        Integer id = null;
        
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=st.executeQuery("SELECT inscripciones_carreras.anio_t, inscripciones_carreras.anio_c, inscripciones_carreras.id_carrera, inscripciones_grado.id_grado, inscripciones_carreras.estado " 
                        + "FROM inscripciones_carreras "
                        + "JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id " 
                        + "WHERE inscripciones_carreras.id_alumno = '"+alumno+"' ORDER BY inscripciones_carreras.anio_t");
        
        rs.last();
        
        Integer registros = rs.getRow();
        
        Integer anio_c = null;   String estado = null; 
        Boolean puede = false;
        
        if(registros > 0){  // TRAER ANIO_T, ANIO_C, Y ESTADO Posibles: Aprobado, Desaprobado, Pendiente y Cursado
            
            Integer anio_t_viejo = rs.getInt(1);
            Integer anio_c_viejo = rs.getInt(2);
            Integer id_carrera_viejo = rs.getInt(3);
            String estado_viejo = rs.getString(5);

            rs.close();
            
            rs = st.executeQuery("SELECT anio_c FROM carrera_grados WHERE id = '" + grado + "'");
            rs.last();
            anio_c = rs.getInt(1);
            rs.close();
            
            if(anio_t_viejo < anio_t){
                //  SELECCIONAR ESTADO
                switch (estado_viejo) {
                    case "A":
                        estado = "C";
                        puede = true;
                        break;
                    case "D":
                        estado = "C";
                        puede = true;
                        break;
                    case "P":
                        
                        break;
                    case "C":
                        estado = "P";
                        puede = true;
                        break;
                }
            }else if(anio_t_viejo.equals(anio_t)){
                // SELECCIONAR TIPO DE INSCRIPCION ( CURSILLO, CURSADO, LENGUAS ) Y BLOQUEAR.
                rs=st.executeQuery("SELECT tipo,(SELECT tipo AS tipo_nuevo FROM carrera WHERE id = '"+carrera+"')  FROM carrera WHERE id = '"+id_carrera_viejo+"'");
                rs.last();
                
                String tipo_cursado_viejo = rs.getString(1);
                String tipo_cursado_nuevo = rs.getString(2);
                
                rs.close();
                
                // SELECCIONAR NUEVO TIPO Y PERMITIR O NO LA INSCRIPCION
                if(tipo_cursado_viejo.equals(tipo_cursado_nuevo)){
                    switch (estado_viejo) {
                        case "A":   // PERMITIR
                            estado = "C";
                            puede = true;
                            break;
                        case "D":   // PERMITIR
                            estado = "C";
                            puede = true;
                            break;
                        case "P":   // NO PERMITIR

                            puede = false;
                            break;
                        case "C":   // NO PERMITIR
                            estado = "P";
                            puede = false;
                            break;
                    }
                }else{  //PERMITIR
                    
                    // PERMITIR PORQUE EL TIPO DE CURSO EN EL MISMO AÑO ES DIFERENTE
                    
                    estado = "C";
                    puede = true;
                    
                }
                
            }            
        }else{  // NUEVO
            rs.close();
            
            rs=st.executeQuery("SELECT anio_c FROM carrera_grados WHERE id = '"+grado+"'");
            rs.last();
            
            anio_c = rs.getInt(1);
            estado = "C";
            
            puede = true;
            
            rs.close();
        }
        
        if(puede){
            if(beca == null || beca_hasta == null){
                
                rs=st.executeQuery("SELECT MAX(id) AS ultima_operacion FROM operaciones_detalle");
                rs.last();
                
                Integer ultima_operacion = rs.getInt(1);
                
                rs.close();
                
                st.executeUpdate("INSERT INTO inscripciones_carreras (id_detalle_operacion, id_carrera, id_alumno, anio_t, anio_c, estado, fecha_inscripcion, hora_inscripcion, "
                    + "inscripcion_pagado, inscripcion_debe, inscripcion_desde, inscripcion_hasta, beca_monto, beca_hasta) "
                    + "VALUES "
                    + "(" + ultima_operacion + ", '" + carrera + "','" + alumno + "','" + anio_t + "','" + anio_c + "','" + estado + "','"+fecha+"','"+hora+"',"
                    + "'" + inscripcion_pagado + "','" + inscripcion_debe + "','" + inscripcion_desde + "','" + inscripcion_hasta + "',NULL,NULL)");
                    
                rs=st.executeQuery("SELECT MAX(id) AS ultimo_id FROM inscripciones_carreras");
                rs.last();
                
                Integer ultimo_id = rs.getInt(1);
                
                rs.close();
                        
                st.executeUpdate("INSERT INTO inscripciones_grado (id_inscripcion, id_grado, id_alumno, anio_t, estado) "
                    + "VALUES "
                    + "("+ultimo_id+", '"+grado+"', '"+alumno+"', '"+anio_t+"', 'A');");
                
                
                id = ultimo_id;
                
            }else{
                
                rs=st.executeQuery("SELECT MAX(id) AS ultima_operacion FROM operaciones_detalle");
                rs.last();
                
                Integer ultima_operacion = rs.getInt(1);
                
                rs.close();
                
                st.executeUpdate("INSERT INTO inscripciones_carreras (id_detalle_operacion, id_carrera, id_alumno, anio_t, anio_c, estado, fecha_inscripcion, hora_inscripcion, "
                    + "inscripcion_pagado, inscripcion_debe, inscripcion_desde, inscripcion_hasta, beca_monto, beca_hasta) "
                    + "VALUES "
                    + "(" + ultima_operacion + ", '" + carrera + "','" + alumno + "','" + anio_t + "','" + anio_c + "','" + estado + "','"+fecha+"','"+hora+"',"
                    + "'" + inscripcion_pagado + ",'" + inscripcion_debe + "','" + inscripcion_desde + "','" + inscripcion_hasta + "','" + beca + "','" + beca_hasta + "')");
                    
                rs=st.executeQuery("SELECT MAX(id) AS ultimo_id FROM inscripciones_carreras");
                rs.last();
                
                Integer ultimo_id = rs.getInt(1);
                
                rs.close();
                        
                st.executeUpdate("INSERT INTO inscripciones_grado (id_inscripcion, id_grado, id_alumno, anio_t, estado) "
                    + "VALUES "
                    + "("+ultimo_id+", '"+grado+"', '"+alumno+"', '"+anio_t+"', 'A');");
                
                id = ultimo_id;
                
            }
        }
        
        }catch (Exception ex){
            System.out.println("Se han producido excepciones  InscribirGrado   Alumnos: "+ex);
            excepcion = "Se han producido excepciones   InscribirGrado        Alumnos: "+ex;
        }
        return id;
    }
    
    public static String[] MostrarInscripcion(String cod_alumno) {
        String[] info = null;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs = st.executeQuery("SELECT alumnos.apenom_alumno,IFNULL(IFNULL(CONCAT(carrera_grados.num_curso,'-',carrera_grados.div_curso, ' ', carrera.nombre),CONCAT(carrera_grados.div_curso,' ',carrera.nombre)), carrera.nombre),carrera.tipo "
                + "FROM alumnos "
                + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno "
                + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id "
                + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera "
                + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado "
                + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C 'AND alumnos.id_alumno = '" + cod_alumno + "'");
        
        rs.last();
        
        Integer registros = rs.getRow();
        
        rs.first();
        
        if (registros > 0) {
            
            info = new String[3];
            
            info[0] = rs.getString(1);
            
            for (int a = 0; a < registros; a++) {
                if (a == 0) {
                    info[1] = rs.getString(2);  // NOMBRE_CURSADO   
                    info[2] = rs.getString(3);
                } else {
                    info[1] = info[1] + " - " + rs.getString(2);  // NOMBRE_CURSADO      
                    info[2] = info[2] + " - " + rs.getString(3);
                }
                rs.next();
            }
        }
        
        rs.close();
        
        }catch (Exception ex){
            System.out.println("Se han producido excepciones    MostrarInscripcion      Alumnos: "+ex);
            excepcion = "Se han producido excepciones       MostrarInscripcion        Alumnos: "+ex;
        }
        return info;
    }
    
    public static String[][] MostrarBecas (String id_inscripcion) {
        String[][] info = null;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs = st.executeQuery("SELECT inscripciones_carreras.beca_desde,inscripciones_carreras.beca_hasta,inscripciones_carreras.beca_monto\n"
                + "FROM alumnos\n"
                + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno\n"
                + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C ' AND inscripciones_carreras.id = '"+id_inscripcion+"'");
        
        rs.last();
        
        Integer registros = rs.getRow();
        
        if (registros > 0) {
            
            info = new String[registros][3];

            rs.first();
            for (int a = 0; a < registros; a++) {
                
                info[a][0] = rs.getString(1);
                info[a][1] = rs.getString(2);
                info[a][2] = rs.getString(3);

                rs.next();
            }
            
            for (int a = 0; a < info.length; a++) {
                if (info[a][0] == null) {
                    info[a][0] = "";
                }
                if (info[a][1] == null) {
                    info[a][1] = "";
                }
                if (info[a][2] == null) {
                    info[a][2] = "";
                }
            }
        }
        
        
        }catch (Exception ex){
            System.out.println("Se han producido excepciones    MostrarBecas      Alumnos: "+ex);
            excepcion = "Se han producido excepciones       MostrarBecas        Alumnos: "+ex;
        }
        return info;
    }
    
    public static void UpdatearBeca(String beca_desde, String beca_hasta, BigDecimal monto, String id_inscripcion) {
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        st.executeUpdate("UPDATE inscripciones_carreras SET beca_desde = '"+beca_desde+"', beca_hasta = '"+beca_hasta+"', beca_monto = "+monto+" WHERE id = '"+id_inscripcion+"'");
        
        if (monto.compareTo(BigDecimal.ZERO) == 0) {
            
            rs = st.executeQuery("SELECT inscripcion_desde, inscripcion_hasta"
                    + "FROM inscripciones_carreras "
                    + "WHERE id = '"+id_inscripcion+"'");
            
        }
        
        
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    EncontroAlumnoCuotas     Cuotas: "+ex);
        }
    }
    
    public static String[][] InscripcionesPorAlumno(String cod){
        String[][] Info = null;
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT CONCAT(inscripciones_carreras.id,'-',IFNULL(IFNULL(CONCAT(carrera_grados.num_curso,'-',carrera_grados.div_curso, ' ', carrera.nombre),CONCAT(carrera_grados.div_curso,' ',carrera.nombre)), carrera.nombre))\n"
                + "FROM alumnos\n"
                + "INNER JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = alumnos.id_alumno\n"
                + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id\n"
                + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera\n"
                + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado\n"
                + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND alumnos.id_alumno = "+cod+"");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if(registros > 0){
            Info = new String[registros][3];
            
            rs.first();
            
            for (int a = 0; a < registros; a++) {
                Info[a][0] = rs.getString(1);
                
                rs.next();
            }
        }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    EncontroAlumnoCuotas     Cuotas: "+ex);
        }
        return Info;
    }
    
    
    public static BigDecimal AlumnoDeudor(String cod) {
        BigDecimal info = BigDecimal.ZERO;
        try{
        ResultSet rs;   
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs = st.executeQuery("SELECT (deudores_temp.monto_deuda_2013 + deudores_temp.marzo_2014 + deudores_temp.abril_2014 + deudores_temp.mayo_2014 + deudores_temp.junio_2014 + deudores_temp.julio_2014 + "
                        + "deudores_temp.agosto_2014 + deudores_temp.septiembre_2014 + deudores_temp.octubre_2014 + deudores_temp.noviembre_2014 + deudores_temp.diciembre_2014) as 'SUMA', "
                        + "(deudores_temp.pagado_deuda_2013 + deudores_temp.pagado_marzo_2014 + deudores_temp.pagado_abril_2014 + deudores_temp.pagado_mayo_2014 + deudores_temp.pagado_junio_2014 + deudores_temp.pagado_julio_2014 + "
                        + "deudores_temp.pagado_agosto_2014 + deudores_temp.pagado_septiembre_2014 + deudores_temp.pagado_octubre_2014 + deudores_temp.pagado_noviembre_2014 + deudores_temp.pagado_diciembre_2014) as 'RESTA' "
                        + "FROM deudores_temp "
                        + "WHERE id_alumno = '"+cod+"'");
        
        rs.last();
        
        if (rs.getRow() > 0) {
            BigDecimal a_pagar = rs.getBigDecimal(1);
            BigDecimal pagado = rs.getBigDecimal(2);
            
            info = a_pagar.subtract(pagado);;
            
        }
        
        }catch (Exception ex){
            System.out.println("Se han producido excepciones    AlumnoDeudor      Alumnos: "+ex);
            excepcion = "Se han producido excepciones       AlumnoDeudor        Alumnos: "+ex;
        }
        return info;
    }
    
    public static Object[] MontosInscripcionActualKK(String id_operacion){
        Object[] Info = null;
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs = st.executeQuery("SELECT IFNULL(carrera.monto_inscripcion,carrera_grados.monto_inscripcion), \n"
                + "IFNULL(carrera.monto_cuota,carrera_grados.monto_cuota), \n"
                + "IFNULL(carrera.monto_cuota_anticipado,carrera_grados.monto_cuota_anticipado),"
                + "inscripciones_carreras.id_carrera, inscripciones_grado.id_grado\n"
                + "FROM inscripciones_carreras\n"
                + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id\n"
                + "INNER JOIN carrera ON carrera.id = inscripciones_carreras.id_carrera\n"
                + "LEFT JOIN carrera_grados ON carrera_grados.id = inscripciones_grado.id_grado\n"
                + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND inscripciones_carreras.id = " + id_operacion + "");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if(registros > 0){
            Info = new Object[5];
            
            rs.first();
            
            Info[0] = rs.getBigDecimal(1);   // MONTO INSCRIPCION
            Info[1] = rs.getBigDecimal(2);   // MONTO CUOTA
            Info[2] = rs.getBigDecimal(3);   // MONTO CUOTA ANTICIPADO  
            
            Info[3] = rs.getObject(4);       // ID CARRERA
            Info[4] = rs.getObject(5);       // ID GRADO
                                
            rs.close();
        }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    MontosInscripcionActual     Alumnos: "+ex);
        }
        return Info;
    }
    
    public static Object[] MontosDelCurso(String carrera, String curso){
        Object[] Info = null;
        try{
        
        String where = "";
        
        if (curso.equals("")) {
            where = "WHERE carrera.nombre = '"+carrera+"'";
        } else {
            if (curso.contains("-")) {
                where = "WHERE carrera.nombre = '"+carrera+"' AND num_curso = "+curso.split("-")[0]+" AND div_curso = '"+curso.split("-")[1]+"'";
            } else {
                where = "WHERE carrera.nombre = '"+carrera+"' AND num_curso IS NULL AND div_curso = '"+curso+"'";
            }
        }
        
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT IFNULL(carrera.monto_inscripcion,carrera_grados.monto_inscripcion), "
                    + "IFNULL(carrera.monto_cuota,carrera_grados.monto_cuota), "
                    + "IFNULL(carrera.monto_cuota_anticipado,carrera_grados.monto_cuota_anticipado),"
                    + "carrera.id,carrera_grados.id\n"
                    + "FROM carrera\n"
                    + "LEFT JOIN carrera_grados ON (carrera_grados.carreras = carrera.id)\n"
                    + where);
        
        rs.last();
        
        Integer registros = rs.getRow();
        
        if(registros > 0){
            Info = new Object[5];
            
            rs.first();
            
            Info[0] = rs.getBigDecimal(1);   // MONTO INSCRIPCION
            Info[1] = rs.getBigDecimal(2);   // MONTO CUOTA
            Info[2] = rs.getBigDecimal(3);   // MONTO CUOTA ANTICIPADO          
            Info[3] = rs.getString(4);       // ID CARRERA
            Info[4] = rs.getString(5);       // ID GRADO
                                
            rs.close();
        }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    MontosDelCurso     Alumnos: "+ex);
        }
        return Info;
    }
    
    public static Object[] MontosPagadoPorAlumno(String id_operacion){
        Object[] Info = null;
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs = st.executeQuery("SELECT inscripcion_pagado, inscripcion_debe, inscripcion_desde, inscripcion_hasta\n"
                + "FROM inscripciones_carreras\n"
                + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id\n"
                + "WHERE inscripciones_carreras.anio_t = YEAR(CURDATE()) AND inscripciones_carreras.estado = 'C' AND inscripciones_carreras.id = " + id_operacion + "");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if(registros > 0){
            Info = new Object[4];
            
            rs.first();
            
            Info[0] = rs.getBigDecimal(1);   // MONTO QUE PAGO DE INSCRIPCION
            Info[3] = rs.getBigDecimal(2);
            
            //  ROMPER MES Y ANIO
            
            Integer mes_desde_insc = Integer.valueOf(rs.getString(3).split("-")[0]);
            Integer anio_desde_insc = Integer.valueOf(rs.getString(3).split("-")[1]);
            
            Integer mes_hasta_insc = Integer.valueOf(rs.getString(4).split("-")[0]);
            Integer anio_hasta_insc = Integer.valueOf(rs.getString(4).split("-")[1]);
            
            rs.close();
            
            //  A PARTIR DE LA FECHA TRAER TODO LO QUE PAGO
            
            rs = st.executeQuery("SELECT cuotas_alumnado.monto_pagado,cuotas_alumnado.mes_t,cuotas_alumnado.anio_t\n"
                    + "FROM cuotas_alumnado\n"
                    + "JOIN inscripciones_carreras ON inscripciones_carreras.id_alumno = cuotas_alumnado.id_alumno\n"
                    + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id\n"
                    + "WHERE inscripciones_carreras.id = " + id_operacion + " AND (cuotas_alumnado.mes_t BETWEEN "+mes_desde_insc+" AND "+anio_desde_insc+") AND (cuotas_alumnado.anio_t BETWEEN "+mes_hasta_insc+" AND "+anio_hasta_insc+") AND monto_pagado IS NOT NULL\n"
                    + "ORDER BY cuotas_alumnado.anio_t,cuotas_alumnado.mes_t");
            
            rs.last();
            
            BigDecimal total_pagado_cuotas = BigDecimal.ZERO;
            String detalle_pagos_cuotas = "";
            Integer reg = rs.getRow();
            
            rs.first();
            
            for (int a = 0; a < reg; a++) {
                total_pagado_cuotas = total_pagado_cuotas.add(rs.getBigDecimal(1));
                
                if (a==0) {
                    detalle_pagos_cuotas = "Mes: " + linneoadmin.LinneoAdmin.NombreMes(rs.getInt(2)) + ". Año: " + rs.getString(3).split("-")[0] +". Pagado: " + rs.getBigDecimal(1) + "\n";
                } else {
                    detalle_pagos_cuotas = detalle_pagos_cuotas + "Mes: " + linneoadmin.LinneoAdmin.NombreMes(rs.getInt(2)) + ". Año: " + rs.getString(3).split("-")[0] +". Pagado: " + rs.getBigDecimal(1) + "\n";
                }
                
                rs.next();
            }
            
            Info[1] = total_pagado_cuotas;
            Info[2] = detalle_pagos_cuotas;
            
        }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    MontosPagadoPorAlumno     Alumnos: "+ex);
        }
        return Info;
    }
    
    public static Object[] DatosInscripcion (String id_operacion) {
        Object[] Info = null;
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT inscripcion_desde, inscripcion_hasta, beca_desde, beca_hasta, beca_monto, inscripciones_carreras.id_carrera, inscripciones_grado.id_grado\n"
                    + "FROM inscripciones_carreras\n"
                    + "LEFT JOIN inscripciones_grado ON (inscripciones_grado.id_inscripcion = inscripciones_carreras.id)\n"
                    + "WHERE inscripciones_carreras.id = " + id_operacion + "");
        rs.last();
        
        Integer registros = rs.getRow();
        
        if(registros > 0){
            Info = new Object[7];
            
            rs.first();
            
            Info[0] = rs.getObject(1);  // INSCRIPCION DESDE
            Info[1] = rs.getObject(2);  // INSCRIPCION HASTA
            
            // TRAER BECA
            Info[2] = rs.getObject(3);  // BECA DESDE
            Info[3] = rs.getObject(4);  // BECA HASTA
            Info[4] = rs.getObject(5);  // BECA MONTO
        }
        
        rs.close();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    DatosInscripcion     Alumnos: "+ex);
        }
        return Info;
    }
    
    public static String[] PagoDescuento(String id_alumno,Integer anio, Integer mes, Object id_carrera) {
        String[] Info = null;
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        rs=st.executeQuery("SELECT IFNULL(monto_descuento,0) \n"
                + "FROM cuotas_alumnado\n"
                + "WHERE id_alumno = "+id_alumno+" AND mes_t = "+mes+" AND anio_t = "+anio+" AND id_carrera = "+id_carrera+"");
        rs.last();
        
        Integer registros = rs.getRow();
        
        Info = new String[2];
        
        if(registros > 0){
            rs.first();
            
            Info[0] = "si";
            
            BigDecimal monto_desc = rs.getBigDecimal(1);
            if (monto_desc.compareTo(BigDecimal.ZERO) == 1) {
                Info[1] = "si";
            } else {
                Info[1] = "no";
            }
            
        } else {
            Info[0] = "no";
        }
        
        rs.close();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    PagoDescuento     Alumnos: "+ex);
        }
        return Info;
    }
    
    public static void ActualizarCuotasCambioCurso(String id_alumno,Integer anio, Integer mes, Object id_carrera, BigDecimal monto_a_pagar, BigDecimal monto_pagado, BigDecimal monto_desc, Object carrera, Object grado, Boolean desc) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        String consulta = null;
        
        if (desc) {
            if (grado != null) {
                consulta = "UPDATE cuotas_alumnado SET monto_a_pagar = " + monto_a_pagar + ", monto_pagado = " + monto_pagado + ", monto_descuento = " + monto_desc + ","
                        + "id_grado = " + grado + ", id_carrera = " + carrera + " "
                        + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + " AND anio_t = " + anio + " AND mes_t = " + mes + "";
            } else {
                consulta = "UPDATE cuotas_alumnado SET monto_a_pagar = " + monto_a_pagar + ", monto_pagado = " + monto_pagado + ", monto_descuento = " + monto_desc + ","
                        + "id_grado = NULL, id_carrera = " + carrera + " "
                        + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + " AND anio_t = " + anio + " AND mes_t = " + mes + "";
            }
        } else {
            if (grado != null) {
                consulta = "UPDATE cuotas_alumnado SET monto_a_pagar = " + monto_a_pagar + ", monto_pagado = " + monto_pagado + ", monto_descuento = NULL,"
                        + "id_grado = " + grado + ", id_carrera = " + carrera + " "
                        + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + " AND anio_t = " + anio + " AND mes_t = " + mes + "";
            } else {
                consulta = "UPDATE cuotas_alumnado SET monto_a_pagar = " + monto_a_pagar + ", monto_pagado = " + monto_pagado + ", monto_descuento = NULL,"
                        + "id_grado = NULL, id_carrera = " + carrera + " "
                        + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + " AND anio_t = " + anio + " AND mes_t = " + mes + "";
            }
        }
        
        if (consulta != null){
            st.executeUpdate(consulta);
        }
        
        
        st.clearBatch();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    ActualizarCuotasCambioCurso     Alumnos: "+ex);
        }
    }
    
    public static void ActualizarInscripcionCambioCurso(String id_inscripcion, BigDecimal pagado, BigDecimal debe, Object carrera, Object grado, String id_alumno) {
        try{
        ResultSet rs; 
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        if (grado != null) {
            st.executeUpdate("UPDATE inscripciones_carreras SET id_carrera = " + carrera + ", inscripcion_pagado = " + pagado + ", inscripcion_debe = " + debe + " "
                    + "WHERE id = " + id_inscripcion + "");
            
            st.clearBatch();
            
            rs = st.executeQuery("SELECT IFNULL(inscripciones_grado.id_grado,''), inscripciones_carreras.anio_t\n"
                    + "FROM inscripciones_carreras\n"
                    + "LEFT JOIN inscripciones_grado ON inscripciones_grado.id_inscripcion = inscripciones_carreras.id\n"
                    + "WHERE inscripciones_carreras.id = " + id_inscripcion + "");
            
            rs.last();
            
            if (!"".equals(rs.getString(1))) {
                st.executeUpdate("UPDATE inscripciones_grado SET id_grado = " + grado + " "
                        + "WHERE id_inscripcion = " + id_inscripcion + "");

                st.clearBatch();
            } else {
                st.executeUpdate("INSERT INTO inscripciones_grado (id_inscripcion,id_grado,id_alumno,anio_t,estado) "
                        + "VALUES (" + id_inscripcion + "," + grado + "," + id_alumno + "," + rs.getInt(2) + ",'A')");

                st.clearBatch();
            }
            
            rs.close();
            
        } else {
            st.executeUpdate("UPDATE inscripciones_carreras SET id_carrera = " + carrera + ", inscripcion_pagado = " + pagado + ", inscripcion_debe = " + debe + " "
                    + "WHERE id = " + id_inscripcion + "");
            
            st.clearBatch();        
        }
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    ActualizarInscripcionCambioCurso     Alumnos: "+ex);
        }
    }
    
    public static void CambiarCarreraCuotasCursoNuevo(String id_alumno,Object id_carrera, Object carrera, Object grado) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        String consulta = null;
        
        if (grado != null) {
            consulta = "UPDATE cuotas_alumnado SET id_grado = " + grado + ", id_carrera = " + carrera + " "
                + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + "";
        } else {
            consulta = "UPDATE cuotas_alumnado SET id_grado = NULL, id_carrera = " + carrera + " "
                + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + "";
        }

        
        if (consulta != null){
            st.executeUpdate(consulta);
        }
        
        
        st.clearBatch();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    CambiarCarreraCuotasCursoNuevo     Alumnos: "+ex);
        }
    }
    
    public static void PonerNullCuotasCursoNuevo(String id_alumno,Object id_carrera, Object carrera, Object grado, int mes) {
        try{
        Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
        String consulta = null;
        
        if (grado != null) {
            consulta = "UPDATE cuotas_alumnado SET id_grado = " + grado + ", id_carrera = " + carrera + ", monto_a_pagar = NULL, monto_pagado = NULL, monto_descuento = NULL "
                + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + " AND mes_t = " + mes + "";
        } else {
            consulta = "UPDATE cuotas_alumnado SET id_grado = NULL, id_carrera = " + carrera + ", monto_a_pagar = NULL, monto_pagado = NULL, monto_descuento = NULL "
                + "WHERE id_alumno = " + id_alumno + " AND id_carrera = " + id_carrera + " AND mes_t = " + mes + "";
        }

        
        if (consulta != null){
            st.executeUpdate(consulta);
        }
        
        
        st.clearBatch();
            
        }catch(Exception ex){
            System.out.println("Se han producido excepciones    PonerNullCuotasCursoNuevo     Alumnos: "+ex);
        }
    }
    
    
    
}
