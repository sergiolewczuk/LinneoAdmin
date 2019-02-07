/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import static Pantallas.Frames.Principal.df;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

public class Recibo {

    public static void Imprimir (JFrame ventana, String sector, String desde, Integer num_operacion, String inscripto_a){
        
        DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
        JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
                "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");

        /* Array para almacenar las impresoras del sistema */
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);
        if (printService.length
                > 0) {//si existen impresoras
            //se elige la impresora
            
            PrintService predeterminada = PrintServiceLookup.lookupDefaultPrintService();
            String impre = predeterminada.getName();
            
            int encontro = 0;
            
            for (int a = 0; a < printService.length; a++) {
                String impresora_for = printService[a].getName();
                
                if(impresora_for.equals(impre)) {
                    encontro = a;
                    break;
                }
            }
            
            PrintService impresora = (PrintService) JOptionPane.showInputDialog(null, "Eliga impresora:",
                    "Imprimir Reporte", JOptionPane.QUESTION_MESSAGE, null, printService, printService[encontro]);
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    
                    JasperPrint print;
                    //se carga el reporte
                    
                    String consulta_sql = null;
                    
                    String apenom_ = null;
                    String direccion_ = null;
                    String dni_ = null;
                    String iva_ = null;
                    String inscripto_a_ = null;
                    String carrera_ = null;
                    String num_recibo_ = null;
                    
                    BigDecimal total_monto_ = BigDecimal.ZERO;
                    
                    String detalle_ = null;
                    String monto_ = null;
                    
                    if ("Institucional".equals(sector)) {
                        if (desde.equals("Cuotas")) {
                            consulta_sql = "SELECT CONCAT(id_alumno,' - ',UPPER(apenom_alumno)), IFNULL(UPPER(domicilio_alumno),''), UPPER(dni_alumno),\n"
                                    + "'' as iva,\n"
                                    + "operaciones.num_recibo, operaciones_detalle.detalle,operaciones_detalle.monto, operaciones_detalle.tipo\n"
                                    + "FROM operaciones\n"
                                    + "JOIN operaciones_detalle ON operaciones_detalle.id_operacion = operaciones.id\n"
                                    + "JOIN alumnos ON alumnos.id_alumno = operaciones.pk_persona\n"
                                    + "WHERE operaciones.id = " + num_operacion + "";

                            inscripto_a_ = inscripto_a;
                        }
                        if (desde.equals("Inscripción")) {
                            consulta_sql = "SELECT CONCAT(id_alumno,' - ',UPPER(apenom_alumno)), IFNULL(UPPER(domicilio_alumno),''), UPPER(dni_alumno),\n"
                                    + "'' as iva,\n"
                                    + "operaciones.num_recibo, operaciones_detalle.detalle,operaciones_detalle.monto, operaciones_detalle.tipo\n"
                                    + "FROM operaciones\n"
                                    + "JOIN operaciones_detalle ON operaciones.id = operaciones_detalle.id_operacion\n"
                                    + "JOIN alumnos ON alumnos.id_alumno = operaciones.pk_persona\n"
                                    + "WHERE operaciones.id = " + num_operacion + " AND operaciones_detalle.tipo != 'Inscripción Lenguas'";

                            inscripto_a_ = "";
                        }
                    } else if ("Alquileres".equals(sector)) {
                        if (desde.equals("Cuotas")) {
                            consulta_sql = "SELECT CONCAT(apellidos,' ',nombres), IFNULL(UPPER(direccion),''), UPPER(dni_responsable),\n"
                                    + "'' as iva,\n"
                                    + "operaciones.num_recibo, operaciones_detalle.detalle,operaciones_detalle.monto, operaciones_detalle.tipo\n"
                                    + "FROM operaciones\n"
                                    + "JOIN operaciones_detalle ON operaciones_detalle.id_operacion = operaciones.id\n"
                                    + "JOIN lotes_responsables ON lotes_responsables.id= operaciones.pk_persona\n"
                                    + "WHERE operaciones.id = " + num_operacion + "";

                            inscripto_a_ = inscripto_a;
                        }
                    } else if ("Otros".equals(sector)) {
                        if (desde.equals("Cuotas")) {
                            consulta_sql = "SELECT CONCAT(apellidos,' ',nombres), IFNULL(UPPER(direccion),''), UPPER(dni),\n"
                                    + "'' as iva,\n"
                                    + "operaciones.num_recibo, operaciones_detalle.detalle,operaciones_detalle.monto, operaciones_detalle.tipo\n"
                                    + "FROM operaciones\n"
                                    + "JOIN operaciones_detalle ON operaciones_detalle.id_operacion = operaciones.id\n"
                                    + "JOIN personas_terceras ON personas_terceras.persona = operaciones.pk_persona\n"
                                    + "WHERE operaciones.id =  " + num_operacion + "";

                            inscripto_a_ = "";
                        }
                    } else if ("Socios".equals(sector)) {
                        if (desde.equals("Cuotas")) {
                            consulta_sql = "SELECT CONCAT(id_socio,' - ',UPPER(apenom_socio)), IFNULL(UPPER(domicilio_socio),''), UPPER(dni_socio),\n"
                                    + "'' as iva,\n"
                                    + "operaciones.num_recibo, operaciones_detalle.detalle,operaciones_detalle.monto, operaciones_detalle.tipo\n"
                                    + "FROM operaciones\n"
                                    + "JOIN operaciones_detalle ON operaciones_detalle.id_operacion = operaciones.id\n"
                                    + "JOIN socios ON socios.id_socio = operaciones.pk_persona\n"
                                    + "WHERE operaciones.id =  " + num_operacion + "";

                            inscripto_a_ = "";
                        }
                    }
                    
                    try {
                    
                    ResultSet rs;
                    Statement st = linneoadmin.LinneoAdmin.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    rs = st.executeQuery(consulta_sql);
                    
                    rs.last();
                    
                    Integer reg = rs.getRow();
                    
                    apenom_ = rs.getObject(1).toString();
                    direccion_ = rs.getObject(2).toString();
                    dni_ = rs.getObject(3).toString();
                    iva_ = rs.getObject(4).toString();
                    num_recibo_ = rs.getObject(5).toString();
                    
                    Object[][] info = new Object[reg][3];
                    
                    rs.first();
                    
                    for (int z = 0; z < reg; z++ ) {
                        
                        info[z][0] = rs.getObject(8);  // TIPO OPERACION    
                        info[z][1] = rs.getObject(6);  // DETALLE OPERACION    
                        info[z][2] = rs.getObject(7);  // MONTO OPERACION    
                        
                        rs.next();
                    }
                    
                    rs.close();
                    
                    for (int y = 0; y < info.length; y++) {
                        
                        if (y == 0) {
                            detalle_ = info[y][1] + ".";
                            monto_ = "  " + df.format(info[y][2]);
                            total_monto_ = (BigDecimal) info[y][2];
                        } else {
                            detalle_ = detalle_ + "\n" + info[y][1] + ".";
                            if (info[y][1].toString().contains("Bonificacion")) {
                                total_monto_ = total_monto_.subtract((BigDecimal) info[y][2]);
                                monto_ = monto_ + "\n- " + df.format(info[y][2]);
                            } else {
                                total_monto_ = total_monto_.add((BigDecimal) info[y][2]);
                                monto_ = monto_ + "\n  " + df.format(info[y][2]);
                            }
                        }
                    }
                    
                    
                    } catch (Exception ex) {
                    System.out.println("Se han producido excepciones    Recibos: " + ex);
                    }
                    
                    Object monto = total_monto_;
                    
                    //System.out.print(total_monto_ + "\n\n" + total_monto_.toString() + "\n\n" + df.format(monto)+ "\n\n");
                    
                    Map params = new HashMap();
                    params.put("apenom_abonado", apenom_);
                    params.put("domicilio_abonado", direccion_);
                    params.put("dni_abonado", dni_);
                    params.put("iva_abonado", iva_);
                    params.put("curso_abonado", inscripto_a_);
                    params.put("carrera_abonado", "");
                    params.put("num_recibo_sistema", num_recibo_);

                    params.put("detalles_cobros", detalle_);
                    params.put("montos_cobros", monto_);
                    params.put("monto_total_1", df.format(monto));

                    String monto_1 = null;
                    String monto_2 = null;
                    
                    if (total_monto_.toString().contains(".")) {
                        String partes = total_monto_.toString().replace('.', ',');
                        
                        monto_1 = partes.split(",")[0];
                        monto_2 = partes.split(",")[1];
                        if ("00".equals(monto_2)) {
                            monto_2 = null;
                        }
                    } else {
                        monto_1 = total_monto_.toString();
                        monto_2 = null;
                    }
                    
                    String monto_escrito_pesos;
                    String monto_escrito_centavos;
                    String monto_en_letras;

                    if (monto_2 == null) {
                        monto_escrito_pesos = Clases.NumLetrasJ.Convierte(monto_1, Clases.NumLetrasJ.Tipo.DetMasculino).toUpperCase();
                        monto_escrito_centavos = "";
                    } else {
                        monto_escrito_pesos = Clases.NumLetrasJ.Convierte(monto_1, Clases.NumLetrasJ.Tipo.DetMasculino).toUpperCase() + " CON";
                        monto_escrito_centavos = Clases.NumLetrasJ.Convierte(monto_2, Clases.NumLetrasJ.Tipo.DetMasculino).toUpperCase() + " CENTAVOS";
                    }

                    monto_en_letras = monto_escrito_pesos + " \n" + monto_escrito_centavos;

                    params.put("monto_en_letras", monto_en_letras);

                    print = JasperFillManager.fillReport("Reportes/Recibo.jasper", params);
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "No se encontró ninguna Impresora", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
}
