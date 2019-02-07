/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Clases.Variables;
import Dialogs.CursosInscripto;
import Dialogs.Recibo;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import Pantallas.InternalFrames.ABMAlumnos;
import static Pantallas.Frames.Principal.jDesktopPane1;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author Sergio
 */
public class AXCAlumnos extends javax.swing.JInternalFrame {

    @Override
    public void dispose() {
        int seleccion = JOptionPane.showOptionDialog(jframe, "Está a punto de salir. Los datos no guardados se perderán\n¿Desea Continuar?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Salir", "Cancelar"}, "Salir");
        if (seleccion == 0) {
            manager.removeKeyEventDispatcher(key);
            super.dispose();

            ABMAlumnos frame = new ABMAlumnos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    Clases.Cursos cursos = new Clases.Cursos();
    Clases.TiposPagos tipo_pagos = new Clases.TiposPagos();
    Clases.Alumnos alumnos = new Clases.Alumnos();
    
    public static BigDecimal monto_final_para_recibo = BigDecimal.ZERO;
    public static String monto_final = null;
    String nombre_curso = null;
    String nivel_curso = null;
    String tipo_curso = null;
    Boolean nuevo = true;
    String bloquear_tipo_curso = "ninguno";
    
    
    GregorianCalendar gc = new GregorianCalendar();
    java.util.Date dt = new java.util.Date();
    Integer año = gc.get(Calendar.YEAR);
    Integer año_prox = año + 1;
    
    Integer cant_cursos = 0;
    String cod_curso = null;
    String[] datos_alumno = new String[5];
    String[][] datos_inscripto = new String[1][14];
    String CODIGO = null;
    JFrame jframe;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    
    void limpiar_datos_cursos(){
        insc_desde_mes.setText("");
        insc_hasta_mes.setText("");
        beca_monto.setText("");
        beca_hasta_mes.setText("");
        inscripcion_pagada.setText("");
    }
    
    void cargar_anios(){
        combo_anio.removeAllItems();
        
        combo_anio.addItem(año);
        combo_anio.addItem(año_prox);
    }
    
    void tipo_cursos(){
        // TRAER TODOS LOS TIPO CURSOS : CURSADO, CURSILLO, LENGUAS O LOS QUE HAYA
        Object[][] info = cursos.SeleccionarTipoCursos();
        combo_tipo.removeAllItems();
        
        for (int a = 0; a < info.length; a++) {
            if (bloquear_tipo_curso.equals(info[a][0])) {

            } else {
                combo_tipo.addItem(info[a][0]);
            }
        } 
    }
    
    void niveles_carreras(){
        // TRAER TODOS LOS LAS CARRERAS DEL TIPO_CURSO
        if (combo_tipo.getItemCount() > 0){
            
            String tipoos = combo_tipo.getSelectedItem().toString();
            
            Object[][] info = cursos.SeleccionarCarreras(tipoos);
            combo_nivel.removeAllItems();

            for (int a = 0; a < info.length; a++) {
                combo_nivel.addItem(info[a][0]);
            }   
        }
    }
    
    void cargar_cursos() {
        if (combo_nivel.getItemCount() > 0) {
            // SELECCIONAR TIPO Y TRAER LOS NIVELES
            String nivel = combo_nivel.getSelectedItem().toString();
            
            Object[][] info = cursos.SeleccionarCursosCarrera(nivel);

            combo_curso.removeAllItems();

            if(info.length > 0){
                for (int a = 0; a < info.length; a++) {
                    combo_curso.addItem(info[a][0]);
                }    
            }else{
                combo_curso.addItem(combo_nivel.getSelectedItem().toString());
            }
        }
    }
    
    void cargar_tipo_pagos(){
        combo_tipo_pago.removeAllItems();

        tipo_pagos.CargarCombos();
        
        for (int a = 0; a < tipo_pagos.datos2.length; a++) {
            combo_tipo_pago.addItem(tipo_pagos.datos2[a]);
        }
    }
    
    void guardar_datos_en_array(){
        
        datos_inscripto[cant_cursos][0] = cod_curso;                                // COD_CURSO
        datos_inscripto[cant_cursos][1] = combo_tipo.getSelectedItem().toString();  // TIPO_CURSO
        datos_inscripto[cant_cursos][2] = combo_anio.getSelectedItem().toString();  // AÑO_CURSO
        datos_inscripto[cant_cursos][3] = inscripcion_pagada.getText().replace(',', '.');             // INSCRIPCION_PAGADO

        BigDecimal pagado = new BigDecimal(inscripcion_pagada.getText().replace(',', '.'));
        BigDecimal total = new BigDecimal(inscripcion_total.getText().replace(',', '.'));
        BigDecimal debe = total.subtract(pagado);

        datos_inscripto[cant_cursos][4] = debe.toString();     // INSCRIPCION_DEBE
        datos_inscripto[cant_cursos][5] = insc_desde_mes.getText();                 // INSCRIPCION_DESDE_MES
        datos_inscripto[cant_cursos][6] = insc_desde_anio.getText();                // INSCRIPCION_DESDE_ANIO
        datos_inscripto[cant_cursos][7] = insc_hasta_mes.getText();                 // INSCRIPCION_HASTA_MES
        datos_inscripto[cant_cursos][8] = insc_hasta_anio.getText();                // INSCRIPCION_HASTA_ANIO
        
        if (beca_hasta_mes.getText().isEmpty()) {
            datos_inscripto[cant_cursos][9] = null;
            datos_inscripto[cant_cursos][10] = null;
            datos_inscripto[cant_cursos][11] = null;
        } else {
            datos_inscripto[cant_cursos][9] = beca_monto.getText();                 // BECA_MONTO
            datos_inscripto[cant_cursos][10] = beca_hasta_mes.getText();            // BECA_HASTA MES
            datos_inscripto[cant_cursos][11] = beca_hasta_anio.getText();           // BECA_HASTA ANIO
        }
        
        datos_inscripto[cant_cursos][12] = combo_nivel.getSelectedItem().toString(); // NOMBRE_CARRERA
        datos_inscripto[cant_cursos][13] = combo_curso.getSelectedItem().toString(); // NOMBRE_CURSO
    }
    
    void volver_inscripcion_anterior(){
        cod_curso = datos_inscripto[cant_cursos - 1][0];                     // COD_CURSO
        
        combo_tipo.setSelectedItem(datos_inscripto[cant_cursos - 1][1]);     // TIPO_CURSO
        combo_anio.setSelectedItem(datos_inscripto[cant_cursos - 1][2]);     // AÑO_CURSO
        inscripcion_pagada.setText(datos_inscripto[cant_cursos - 1][3]);     // INSCRIPCION_PAGADO

        Double pagado = Double.valueOf(datos_inscripto[cant_cursos - 1][3]);
        Double debe = Double.valueOf(datos_inscripto[cant_cursos - 1][4]);
        Double total = debe + pagado;

        inscripcion_total.setText(new BigDecimal(total).toPlainString());   // MONTO_INSCRIPCION TOTAL

        insc_desde_mes.setText(datos_inscripto[cant_cursos - 1][5]);        // INSCRIPCION_DESDE_MES
        insc_desde_anio.setText(datos_inscripto[cant_cursos - 1][6]);       // INSCRIPCION_DESDE_ANIO
        
        insc_hasta_mes.setText(datos_inscripto[cant_cursos - 1][7]);        // INSCRIPCION_HASTA_MES
        insc_hasta_anio.setText(datos_inscripto[cant_cursos - 1][8]);       // INSCRIPCION_HASTA_ANIO
        
        if (beca_monto == null || beca_hasta_mes == null) {
            beca_monto.setText("");
            beca_hasta_mes.setText("");
            beca_hasta_anio.setText("");
        } else {
            beca_monto.setText(datos_inscripto[cant_cursos - 1][9]);        // BECA_MONTO
            beca_hasta_mes.setText(datos_inscripto[cant_cursos - 1][10]);   // BECA_HASTA_MES
            beca_hasta_anio.setText(datos_inscripto[cant_cursos - 1][11]);  // BECA_HASTA_ANIO
        }
        
        combo_nivel.setSelectedItem(datos_inscripto[cant_cursos - 1][12]);  // NOMBRE_CARRERA
        combo_curso.setSelectedItem(datos_inscripto[cant_cursos - 1][13]);  // NOMBRE_CURSO
        
        cant_cursos--;

        if (cant_cursos == 0) {
            jButton3.setEnabled(false);
        } else {
            jButton3.setEnabled(true);
        }
        
        String[][] temp_inscriptos = datos_inscripto;

        datos_inscripto = new String[cant_cursos + 1][14];

        for (int a = 0; a < cant_cursos; a++) {
            for (int columna = 0; columna < 14; columna++) {
                datos_inscripto[a][columna] = temp_inscriptos[a][columna];
            }
        }
        
    }
    
    
    void limpiar_datos_alumnos(){
        apenom_alumno.setText("");
        dni_alumno.setText("");
        edad_alumno.setText("");
        anio_nac_alumno.setText("");
        mes_nac_alumno.setText("");
        dia_nac_alumno.setText("");
        tel_tutor.setText("");
        beca_monto.setText("");
        beca_hasta_mes.setText("");
        beca_hasta_anio.setText("");
    }
    
    //TECLADO ACCESOS DIRECTOS
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (jframe.isActive()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 112) {
                        jButton1.doClick();
                    }
                    if (e.getKeyCode() == 113) {
                        
                    }
                    if (e.getKeyCode() == 114) {
                        //CursosInscripto dialog = new CursosInscripto(jframe, true, CODIGO);
                        //dialog.showDialog();
                    }
                    if (e.getKeyCode() == 115) {
                        jButton3.doClick();
                    }
                }
            }
            return false;
        }
    }
    
    public static int calcularEdad(Integer anno, Integer mes, Integer dia) {
    int annos, meses, dias, tm_year, tm_mon, tm_mday;
            int[] dias_del_mes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
            Calendar hoy = Calendar.getInstance();
            tm_year = hoy.get(Calendar.YEAR);
            tm_mon = hoy.get(Calendar.MONTH) + 1;
            tm_mday = hoy.get(Calendar.DAY_OF_MONTH);
            Scanner in = new Scanner(System.in);
            
            do{
                if (anno < 1900 || anno > tm_year)
                    System.out.printf("Año incorrecto");
                }while(anno < 1900 || anno > tm_year);
            do{
                if (mes < 1 || mes > 12)
                    System.out.printf("Mes incorrecto");
                }while(mes < 1 || mes > 12);
            do{
                if (dia < 1 || dia > dias_del_mes[mes])
                    System.out.printf("Dia incorrecto");
                }while(dia < 1 || dia > dias_del_mes[mes]);
            
                annos = tm_year - anno;
                meses = tm_mon - mes;
                dias = tm_mday - dia;
                if(dias < 0){
                    dias += dias_del_mes[tm_mon];
                    meses--;
                }
                if(meses < 0){
                    meses += 12;
                    annos --;
                }
            
               
                return annos;
    }
            
    public AXCAlumnos() {
        initComponents();
        
        
        //TRAER EL ULTIMO COD_ALUMNO  + 1
        Integer codigo_ultimo = alumnos.CodUltimo();

        cod_alumno.setText(String.valueOf(codigo_ultimo));
        CODIGO = String.valueOf(codigo_ultimo);

        cod_alumno.setEditable(false);

        insc_desde_anio.setFocusable(false);
        insc_hasta_anio.setFocusable(false);
        
        beca_hasta_mes.setEditable(false);
        beca_hasta_anio.setEditable(false);
        beca_hasta_mes.setFocusable(false);
        beca_hasta_anio.setFocusable(false);
        
        jButton3.setEnabled(false);
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        
        cargar_tipo_pagos();
        
        tipo_cursos();
        
        cargar_anios();
        
        jTextField1.setVisible(false);
        jLabel22.setVisible(false);
    }
    
    
    public AXCAlumnos(String cod, String bloquear_inscribir_en) {
        initComponents();
        
        if (!"".equals(cod)) {
            nuevo = false;
            
            cod_alumno.setText(cod);
            CODIGO = cod;

            Object[] info = alumnos.Mostrar_Datos(cod);

            for (int a = 0; a < info.length; a++) {
                if (info[a] == null) {
                    info[a] = "";
                }
            }

            if (info[4] == "") {
                dia_nac_alumno.setText("");
                mes_nac_alumno.setText("");
                anio_nac_alumno.setText("");
            } else {
                String dia = info[4].toString().substring(8, 10);
                dia_nac_alumno.setText(dia);
                String mes = info[4].toString().substring(5, 7);
                mes_nac_alumno.setText(mes);
                String anio = info[4].toString().substring(0, 4);
                anio_nac_alumno.setText(anio);
            
                Integer mostrar_fecha = calcularEdad(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
                edad_alumno.setText(mostrar_fecha.toString());
            }

            dni_alumno.setText(info[2].toString());
            apenom_alumno.setText(info[3].toString());
            tel_tutor.setText(info[35].toString());
            
            cod_alumno.setFocusable(false);
            dni_alumno.setFocusable(false);
            apenom_alumno.setFocusable(false);
            dia_nac_alumno.setFocusable(false);
            mes_nac_alumno.setFocusable(false);
            anio_nac_alumno.setFocusable(false);
            tel_tutor.setFocusable(false);
            
            
            // VER EL CURSO ACTUAL O EL ULTIMO APROBADO Y DEJAR ELEGIR SOLO LOS SIGUIENTES
            
        }
        
        
        insc_desde_anio.setFocusable(false);
        insc_hasta_anio.setFocusable(false);
        
        beca_hasta_mes.setEditable(false);
        beca_hasta_anio.setEditable(false);
        beca_hasta_mes.setFocusable(false);
        beca_hasta_anio.setFocusable(false);
        
        jButton3.setEnabled(false);
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        
        bloquear_tipo_curso = bloquear_inscribir_en;
        
        cargar_tipo_pagos();
        
        tipo_cursos();
        
        cargar_anios();
        
        jTextField1.setVisible(false);
        jLabel22.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        combo_curso = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        inscripcion_total = new javax.swing.JTextField();
        inscripcion_pagada = new javax.swing.JTextField();
        apenom_alumno = new javax.swing.JTextField();
        dni_alumno = new javax.swing.JTextField();
        cod_alumno = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        edad_alumno = new javax.swing.JTextField();
        anio_nac_alumno = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        mes_nac_alumno = new javax.swing.JTextField();
        dia_nac_alumno = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        beca_monto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        combo_tipo = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        combo_nivel = new javax.swing.JComboBox();
        insc_desde_mes = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        insc_desde_anio = new javax.swing.JTextField();
        insc_hasta_mes = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        insc_hasta_anio = new javax.swing.JTextField();
        beca_hasta_mes = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        beca_hasta_anio = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        combo_tipo_pago = new javax.swing.JComboBox();
        combo_anio = new javax.swing.JComboBox();
        tel_tutor = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        label_detalle = new javax.swing.JLabel();
        text_detalle = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();

        setIconifiable(true);
        setTitle("Inscripción");

        jLabel1.setText("Año Cursado");

        jLabel2.setText("Curso");

        combo_curso.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_cursoItemStateChanged(evt);
            }
        });

        jLabel3.setText("Inscripción Desde");

        jLabel4.setText("Inscripción Hasta");

        jLabel5.setText("Inscripción Monto");

        jLabel6.setText("Inscripción Monto Pagado");

        inscripcion_total.setEditable(false);
        inscripcion_total.setFocusable(false);

        inscripcion_pagada.setName("double"); // NOI18N
        inscripcion_pagada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inscripcion_pagadaKeyReleased(evt);
            }
        });

        apenom_alumno.setName("string"); // NOI18N
        apenom_alumno.setNextFocusableComponent(dia_nac_alumno);

        dni_alumno.setName("numeros"); // NOI18N

        cod_alumno.setName("numeros"); // NOI18N
        cod_alumno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cod_alumnoKeyReleased(evt);
            }
        });

        jLabel8.setText("Registro Nuevo");

        jLabel9.setText("Apellido y Nombre");

        jLabel10.setText("DNI");

        jLabel11.setText("COD. Alumnos");

        edad_alumno.setEditable(false);
        edad_alumno.setFocusable(false);

        anio_nac_alumno.setName("numeros"); // NOI18N
        anio_nac_alumno.setNextFocusableComponent(tel_tutor);
        anio_nac_alumno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                anio_nac_alumnoFocusLost(evt);
            }
        });
        anio_nac_alumno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                anio_nac_alumnoKeyReleased(evt);
            }
        });

        jLabel7.setText("Fecha Nacimiento");

        mes_nac_alumno.setName("numeros"); // NOI18N
        mes_nac_alumno.setNextFocusableComponent(anio_nac_alumno);

        dia_nac_alumno.setName("numeros"); // NOI18N
        dia_nac_alumno.setNextFocusableComponent(mes_nac_alumno);

        jLabel12.setText("Edad");

        jLabel13.setText(" / ");

        jLabel14.setText(" / ");

        jLabel15.setText("Paga $ Cuota Becada");

        beca_monto.setName("double"); // NOI18N
        beca_monto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                beca_montoFocusLost(evt);
            }
        });

        jLabel16.setText("Beca Hasta");

        jLabel24.setText("Tipo Curso");

        combo_tipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Cursado", "Lenguas" }));
        combo_tipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tipoItemStateChanged(evt);
            }
        });

        jLabel25.setText("Nivel");

        combo_nivel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_nivelItemStateChanged(evt);
            }
        });

        insc_desde_mes.setName("numeros"); // NOI18N
        insc_desde_mes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                insc_desde_mesKeyReleased(evt);
            }
        });

        jLabel18.setText(" / ");

        insc_desde_anio.setName("numeros"); // NOI18N
        insc_desde_anio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                insc_desde_anioKeyReleased(evt);
            }
        });

        insc_hasta_mes.setName("numeros"); // NOI18N
        insc_hasta_mes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                insc_hasta_mesKeyReleased(evt);
            }
        });

        jLabel19.setText(" / ");

        insc_hasta_anio.setName("numeros"); // NOI18N
        insc_hasta_anio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                insc_hasta_anioKeyReleased(evt);
            }
        });

        beca_hasta_mes.setName("numeros"); // NOI18N

        jLabel20.setText(" / ");

        beca_hasta_anio.setName("numeros"); // NOI18N

        jButton1.setText("F1 - GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("F2 - VER DETALLES DE PAGOS");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("F4 - CORREGIR ANTERIOR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel17.setText("Tipo de Pago");

        combo_tipo_pago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Efectivo" }));
        combo_tipo_pago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tipo_pagoItemStateChanged(evt);
            }
        });

        combo_anio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2015" }));
        combo_anio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_anioItemStateChanged(evt);
            }
        });

        tel_tutor.setNextFocusableComponent(combo_tipo);

        jLabel21.setText("Teléfono Tutor");

        label_detalle.setText("Detalle");

        jLabel22.setText("Num. Recibo Interno");

        jLabel23.setText("Acreditar con Recibo Interno");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Si" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cod_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(combo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(combo_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(combo_curso, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(apenom_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dni_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(insc_desde_mes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel18)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(insc_desde_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(beca_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel16)
                                                    .addComponent(jLabel4))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(insc_hasta_mes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel19)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(insc_hasta_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(beca_hasta_mes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel20)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(beca_hasta_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addGap(18, 18, 18)
                                                .addComponent(edad_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel21))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(tel_tutor, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(dia_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel13)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(mes_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel14)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(anio_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(combo_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(label_detalle)
                    .addComponent(jLabel17)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(inscripcion_total, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(combo_tipo_pago, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inscripcion_pagada))
                    .addComponent(text_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cod_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(dni_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(apenom_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(combo_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(combo_curso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(combo_anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(anio_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(dia_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(mes_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edad_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tel_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(insc_hasta_anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(insc_hasta_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(beca_hasta_anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(beca_hasta_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(insc_desde_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(insc_desde_anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(beca_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(combo_tipo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_detalle)
                    .addComponent(text_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inscripcion_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inscripcion_pagada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void combo_cursoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_cursoItemStateChanged
        // TODO add your handling code here:
        if(combo_curso.getItemCount() > 0){
            nombre_curso = combo_curso.getSelectedItem().toString();
        }
        
        nivel_curso = combo_nivel.getSelectedItem().toString();
        tipo_curso = combo_tipo.getSelectedItem().toString();
        
        if(nombre_curso!=null && nivel_curso!=null && tipo_curso!=null){
            if(combo_curso.getItemCount() > 0){
                // CARGAR MONTO INSCRIPCION
                String[] info = cursos.Seleccionar_monto_inscripción(nombre_curso, nivel_curso, tipo_curso, cod_alumno.getText(), combo_anio.getSelectedItem().toString());
                
                inscripcion_total.setText(info[0]);
                
                if (info[0].equals("0.00")) {
                    inscripcion_pagada.setText("0.00");
                }
                
                cod_curso = info[1];
            }
        }
    }//GEN-LAST:event_combo_cursoItemStateChanged

    private void anio_nac_alumnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anio_nac_alumnoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_anio_nac_alumnoKeyReleased

    private void anio_nac_alumnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_anio_nac_alumnoFocusLost
        // TODO add your handling code here:
        if(mes_nac_alumno.getText().isEmpty() && dia_nac_alumno.getText().isEmpty()){
            
        }else{
            Integer anio_text = Integer.valueOf(anio_nac_alumno.getText());
            if(anio_nac_alumno.getText().length() == 4){
                if (año >= anio_text) {
                    Integer anio_fechan_alum = Integer.valueOf(anio_nac_alumno.getText());
                    Integer mes_fechan_alum = Integer.valueOf(mes_nac_alumno.getText());
                    Integer dia_fechan_alum = Integer.valueOf(dia_nac_alumno.getText());

                    Integer mostrar_fecha = calcularEdad(anio_fechan_alum, mes_fechan_alum, dia_fechan_alum);
                    edad_alumno.setText(mostrar_fecha.toString());
                } else {
                    anio_nac_alumno.setText(año.toString());
                }
            } else if (anio_nac_alumno.getText().length() == 2){
                // SI EL AÑO PUESTO ES MAYOR A 15
                String anio_puesto = anio_nac_alumno.getText();
                String anio_actual = año.toString();
                
                anio_actual = anio_actual.substring(2,3);
                
                
                if (Integer.valueOf(anio_puesto) > Integer.valueOf(anio_actual)) {
                    anio_nac_alumno.setText("19"+anio_puesto);
                    
                    Integer anio_fechan_alum = Integer.valueOf(anio_nac_alumno.getText());
                    Integer mes_fechan_alum = Integer.valueOf(mes_nac_alumno.getText());
                    Integer dia_fechan_alum = Integer.valueOf(dia_nac_alumno.getText());

                    Integer mostrar_fecha = calcularEdad(anio_fechan_alum, mes_fechan_alum, dia_fechan_alum);
                    edad_alumno.setText(mostrar_fecha.toString());
                } else {
                    anio_nac_alumno.setText("20"+anio_puesto);
                    
                    Integer anio_fechan_alum = Integer.valueOf(anio_nac_alumno.getText());
                    Integer mes_fechan_alum = Integer.valueOf(mes_nac_alumno.getText());
                    Integer dia_fechan_alum = Integer.valueOf(dia_nac_alumno.getText());

                    Integer mostrar_fecha = calcularEdad(anio_fechan_alum, mes_fechan_alum, dia_fechan_alum);
                    edad_alumno.setText(mostrar_fecha.toString());
                }
            } else {
                anio_nac_alumno.requestFocus();
            }
        }
    }//GEN-LAST:event_anio_nac_alumnoFocusLost

    private void cod_alumnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cod_alumnoKeyReleased
        // TODO add your handling code here:
        String COD_ALUMNO = cod_alumno.getText();
        
        String dia = null;
        String mes = null;
        String anio = null;
                
        if(COD_ALUMNO.length() > 0){
            Boolean encontro = alumnos.ComprobarExistente(COD_ALUMNO);

            if (encontro == true) {
                // RELLENAR CON LOS DATOS DEL ALUMNO
                Object[] info = alumnos.Mostrar_Datos(COD_ALUMNO);
                
                CODIGO = COD_ALUMNO;
                
                dni_alumno.setText(info[2].toString());
                apenom_alumno.setText(info[3].toString());
                
                // FECHA NACIMIENTO
                if(info[4]==""){
                    dia_nac_alumno.setText("");
                    mes_nac_alumno.setText("");
                    anio_nac_alumno.setText("");
                }else{
                    dia = info[4].toString().substring(8,10);    dia_nac_alumno.setText(dia);
                    mes = info[4].toString().substring(5,7);     mes_nac_alumno.setText(mes);
                    anio = info[4].toString().substring(0,4);    anio_nac_alumno.setText(anio); 
                    
                    Integer mostrar_fecha = calcularEdad(Integer.parseInt(anio),Integer.parseInt(mes),Integer.parseInt(dia));
                    edad_alumno.setText(mostrar_fecha.toString()); 
                }
                
                
                    //cod_alumno.setEnabled(false);
                    dni_alumno.setFocusable(false);
                    apenom_alumno.setFocusable(false);
                    dia_nac_alumno.setFocusable(false);
                    mes_nac_alumno.setFocusable(false);
                    anio_nac_alumno.setFocusable(false);
                
            }else{
                // LIMPIAR JTEXTS
                limpiar_datos_alumnos();
            }      
        }else{
            
            limpiar_datos_alumnos();
            
            dni_alumno.setFocusable(true);
            apenom_alumno.setFocusable(true);
            dia_nac_alumno.setFocusable(true);
            mes_nac_alumno.setFocusable(true);
            anio_nac_alumno.setFocusable(true);
        }
    }//GEN-LAST:event_cod_alumnoKeyReleased

    private void combo_tipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipoItemStateChanged
        // TODO add your handling code here:
        niveles_carreras();
    }//GEN-LAST:event_combo_tipoItemStateChanged

    private void combo_nivelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_nivelItemStateChanged
        // TODO add your handling code here:
        cargar_cursos();
    }//GEN-LAST:event_combo_nivelItemStateChanged

    private void insc_desde_mesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_insc_desde_mesKeyReleased
        // TODO add your handling code here:
        if (insc_desde_mes.getText().length() <= 2) {
            if (insc_desde_mes.getText().length() > 0) {
                Integer dia = Integer.valueOf(insc_desde_mes.getText());
                if (dia > 13) {
                    insc_desde_mes.setText("01");
                }
            }
        } else {
            insc_desde_mes.setText("01");
        }
    }//GEN-LAST:event_insc_desde_mesKeyReleased

    private void insc_desde_anioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_insc_desde_anioKeyReleased
        // TODO add your handling code here:
        if (insc_desde_anio.getText().length() <= 4) {
            if (insc_desde_anio.getText().length() > 0) {
                Integer dia = Integer.valueOf(insc_desde_anio.getText());
                if (dia > año_prox) {
                    insc_desde_anio.setText(año.toString());
                }
            }
        } else {
            insc_desde_anio.setText(año.toString());
        }
    }//GEN-LAST:event_insc_desde_anioKeyReleased

    private void insc_hasta_mesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_insc_hasta_mesKeyReleased
        // TODO add your handling code here:
        if (insc_hasta_mes.getText().length() <= 2) {
            if (insc_hasta_mes.getText().length() > 0) {
                Integer dia = Integer.valueOf(insc_hasta_mes.getText());

                if (dia > 13) {
                    insc_hasta_mes.setText("01");
                }
            }
        } else {
            insc_hasta_mes.setText("01");
        }
    }//GEN-LAST:event_insc_hasta_mesKeyReleased

    private void insc_hasta_anioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_insc_hasta_anioKeyReleased
        // TODO add your handling code here:
        if (insc_hasta_anio.getText().length() <= 4) {
            if (insc_hasta_anio.getText().length() > 0) {
                Integer dia = Integer.valueOf(insc_hasta_anio.getText());

                if (dia > año_prox) {
                    insc_hasta_anio.setText(año.toString());
                }
            }
        } else {
            insc_hasta_anio.setText(año.toString());
        }
    }//GEN-LAST:event_insc_hasta_anioKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // SELECCIONAR SI ELIGIO BIEN EL CURSO O PREGUNTAR SI ESTA SEGURO
        
        Integer mes_desde_ = Integer.valueOf(insc_desde_mes.getText());
        Integer mes_hasta_ = Integer.valueOf(insc_hasta_mes.getText());
        
        if (inscripcion_total.getText().isEmpty() || inscripcion_pagada.getText().isEmpty() || cod_alumno.getText().isEmpty() || dni_alumno.getText().isEmpty() || tel_tutor.getText().isEmpty() || apenom_alumno.getText().isEmpty() || (text_detalle.isVisible() && text_detalle.getText().isEmpty())) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Complete los Datos correctamente para continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        } else if ((insc_desde_anio.getText().equals(insc_hasta_anio.getText())) && mes_desde_ > mes_hasta_) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "No se puede inscribir cuando el Inscripción DESDE es MAYOR que el HASTA", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        } else if (jComboBox1.getSelectedItem().equals("Si") && jTextField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Si se acredita con Recibos Internos, debe imputar el Número de Recibo correspondiente.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        } else {
            Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Desea inscribir al alumno en otro curso?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"No. Completar Operación", "Si", "Cancelar"}, "Si");

            datos_alumno[0] = cod_alumno.getText();
            datos_alumno[1] = dni_alumno.getText();
            datos_alumno[2] = apenom_alumno.getText();
            datos_alumno[3] = anio_nac_alumno.getText() + "-" + mes_nac_alumno.getText() + "-" + dia_nac_alumno.getText();
            datos_alumno[4] = tel_tutor.getText();

            if (Seleccion == 1) {// //GUARDAR EL ULTIMO CURSO Y CONTINUAR

                guardar_datos_en_array();

                cant_cursos++;
                
                jButton3.setEnabled(true);

                String[][] temp_inscriptos = datos_inscripto;

                datos_inscripto = new String[cant_cursos + 1][14];

                for (int a = 0; a < cant_cursos; a++) {
                    for (int columna = 0; columna < 14; columna++) {
                        datos_inscripto[a][columna] = temp_inscriptos[a][columna];
                    }
                }

                // LIMPIAR CAMPOS
                cod_alumno.setEditable(false);
                apenom_alumno.setEditable(false);
                dni_alumno.setEditable(false);
                dia_nac_alumno.setEditable(false);
                mes_nac_alumno.setEditable(false);
                anio_nac_alumno.setEditable(false);
                tel_tutor.setEditable(false);

                limpiar_datos_cursos();

            } else if (Seleccion == 0) {//TERMINAR OPERACION Y GUARDAR EL ULTIMO CURSO

                guardar_datos_en_array();

                Recibo dialog = new Recibo(jframe, true, datos_alumno, datos_inscripto);
                String corregir = dialog.showDialog();

                switch (corregir) {
                    case "true":

                        cant_cursos++;
                        volver_inscripcion_anterior();

                        break;
                    case "cuotas":

                        break;
                    case "false":

                        Savepoint savepoint1 = null;
                        try {
                            savepoint1 = linneoadmin.LinneoAdmin.conexion.setSavepoint("Savepoint1");
                        } catch (SQLException ex) {
                            Logger.getLogger(AXCAlumnos.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        linneoadmin.LinneoAdmin.desactivarAutoCommit();

                        Boolean es_grado = false;

                        String hora = Principal.hora_actual;
                        String fecha = Principal.fecha_para_bd;
                        String cod_carrera;
                        String cod_grado = null;

                        Object[] cod_c;
                        
                        if (jComboBox1.getSelectedItem().equals("No")) { // ES CON RECIBO NORMAL 
                            // SI ES VIRGINIA INSCRIBIR CON 0 PESOS

                            if ("2".equals(Principal.id)) {

                                for (int a = 0; a < datos_inscripto.length; a++) {

                                    int mes_desde = Integer.valueOf(datos_inscripto[a][5]);
                                    int mes_hasta = Integer.valueOf(datos_inscripto[a][7]);

                                    if (nuevo) {
                                        // INSERT ALUMNO cod, dni, apenom, fecha_nac, tel_tutor
                                        alumnos.Nuevo(cod_alumno.getText(), dni_alumno.getText(), apenom_alumno.getText(), anio_nac_alumno.getText() + "-" + mes_nac_alumno.getText() + "-" + dia_nac_alumno.getText(), tel_tutor.getText());
                                    }

                                    if (datos_inscripto[a][12].equals(datos_inscripto[a][13])) {// INSCRCIPCION_CARRERA
                                        cod_c = Clases.Cursos.MostrarDatosCarreras(datos_inscripto[a][12]);
                                        cod_carrera = cod_c[1].toString();

                                        es_grado = false;
                                    } else {
                                        cod_c = Clases.Cursos.MostrarDatosGrados(datos_inscripto[a][0]);
                                        cod_grado = cod_c[1].toString();
                                        cod_carrera = cod_c[6].toString();

                                        es_grado = true;
                                    }

                                //String id_alu, String id_carr, String id_grado, String anio_t, String tipo, int mes_desde, int mes_hasta
                                    // SI ES CURSILLO NO GENERAR CUOTAS
                                    if (!datos_inscripto[a][1].equals("Cursillo")) {
                                        Clases.Cuotas.GenerarCuotasAlumnos(datos_alumno[0], cod_carrera, cod_grado, datos_inscripto[a][2], datos_inscripto[a][1], mes_desde, mes_hasta, fecha, hora, beca_monto.getText());
                                    }

                                    if (!es_grado) {// INSCRCIPCION_CARRERA

                                    // String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe,
                                        // String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta
                                        if (datos_inscripto[a][9] == null || datos_inscripto[a][10] == null || datos_inscripto[a][11] == null) {
                                            alumnos.InscribirCarrera(cod_c[1].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), "0.00", "0.00",
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    null, null,
                                                    hora, fecha);
                                        } else {
                                            alumnos.InscribirCarrera(cod_c[1].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), "0.00", "0.00",
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    datos_inscripto[a][9], datos_inscripto[a][10] + "-" + datos_inscripto[a][11],
                                                    hora, fecha);
                                        }
                                    } else {// INSCRCIPCION_GRADO
                                        // String grado, String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe,
                                        // String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta
                                        if (datos_inscripto[a][9] == null || datos_inscripto[a][10] == null || datos_inscripto[a][11] == null) {
                                            alumnos.InscribirGrado(cod_c[1].toString(), datos_inscripto[a][13], cod_c[6].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), "0.00", "0.00",
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    null, null,
                                                    hora, fecha);
                                        } else {
                                            alumnos.InscribirGrado(cod_c[1].toString(), datos_inscripto[a][13], cod_c[6].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), "0.00", "0.00",
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    datos_inscripto[a][9], datos_inscripto[a][10] + "-" + datos_inscripto[a][11],
                                                    hora, fecha);
                                        }
                                    }
                                }

                                if (Clases.Alumnos.excepcion != null) {
                                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Ocurrio un error al inscribir, Compruebe los datos antes de continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                                    linneoadmin.LinneoAdmin.rollback(savepoint1);
                                    linneoadmin.LinneoAdmin.activarAutoCommit();
                                    return;
                                } else {
                                    linneoadmin.LinneoAdmin.commiteo();
                                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con Exito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                                }

                            } else {
                                if (nuevo) {
                                    // INSERT ALUMNO cod, dni, apenom, fecha_nac, tel_tutor
                                    alumnos.Nuevo(cod_alumno.getText(), dni_alumno.getText(), apenom_alumno.getText(), anio_nac_alumno.getText() + "-" + mes_nac_alumno.getText() + "-" + dia_nac_alumno.getText(), tel_tutor.getText());
                                }

                                // INSERT OPERACIONES   String pk_persona, String tipo_pago
                                Clases.Operaciones.GenerarOperacion(datos_alumno[0], combo_tipo_pago.getSelectedItem().toString(), hora, fecha, text_detalle.getText(), "Institucional");

                                for (int a = 0; a < datos_inscripto.length; a++) {

                                // Primero generar la operación, y los detalles... Preguntar si desea pagar Cuota o no, si pone que si Generar Tabla con Cuotas Desde - Hasta
                                    // Traer las cuotas seleccionadas y agregar al array que pasa a Recibo.dialog
                                    // Hacer update en las cuotas pagadas después de insertarlas
                                    // VARIABLES PARA RECIBO!
                                    // INSERT DE CUOTAS
                                    int mes_desde = Integer.valueOf(datos_inscripto[a][5]);
                                    int mes_hasta = Integer.valueOf(datos_inscripto[a][7]);

                                    // INSERT OPERACIONES
                                    if (datos_inscripto[a][12].equals(datos_inscripto[a][13])) {// INSCRCIPCION_CARRERA
                                        cod_c = cursos.MostrarDatosCarreras(datos_inscripto[a][12]);
                                        cod_carrera = cod_c[1].toString();

                                        es_grado = false;
                                    } else {
                                        cod_c = cursos.MostrarDatosGrados(datos_inscripto[a][0]);
                                        cod_grado = cod_c[1].toString();
                                        cod_carrera = cod_c[6].toString();

                                        es_grado = true;
                                    }

                                    Integer id_inscripcion = null;

                                    //String id_alu, String id_carr, String id_grado, String anio_t, String tipo, int mes_desde, int mes_hasta
                                    Clases.Cuotas.GenerarCuotasAlumnos(datos_alumno[0], cod_carrera, cod_grado, datos_inscripto[a][2], datos_inscripto[a][1], mes_desde, mes_hasta, fecha, hora, beca_monto.getText());

                                    // COD_CURSO, COD_ALUMNO, TIPO_CURSO, AÑO_CURSADO, INSC_PAGADA, INSC_DEBE, INSC_DESDE, INSC_HASTA, BECA, BECA_HASTA, ESTADO
                                    if (!es_grado) {// INSCRCIPCION_CARRERA
                                        // String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe,
                                        // String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta
                                        if (datos_inscripto[a][9] == null || datos_inscripto[a][10] == null || datos_inscripto[a][11] == null) {
                                            id_inscripcion = alumnos.InscribirCarrera(cod_c[1].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    null, null,
                                                    hora, fecha);
                                        } else {
                                            id_inscripcion = alumnos.InscribirCarrera(cod_c[1].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    datos_inscripto[a][9], datos_inscripto[a][10] + "-" + datos_inscripto[a][11],
                                                    hora, fecha);
                                        }
                                    } else {// INSCRCIPCION_GRADO
                                        // String grado, String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe,
                                        // String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta
                                        if (datos_inscripto[a][9] == null || datos_inscripto[a][10] == null || datos_inscripto[a][11] == null) {
                                            id_inscripcion = alumnos.InscribirGrado(cod_c[1].toString(), datos_inscripto[a][13], cod_c[6].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    null, null,
                                                    hora, fecha);
                                        } else {
                                            id_inscripcion = alumnos.InscribirGrado(cod_c[1].toString(), datos_inscripto[a][13], cod_c[6].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                    datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                    datos_inscripto[a][9], datos_inscripto[a][10] + "-" + datos_inscripto[a][11],
                                                    hora, fecha);
                                        }
                                    }

                                    if (es_grado) {
                                        // VERIFICAR SI ES LENGUAS O NO 
                                        if (datos_inscripto[a][1].equals("Lenguas")) {  // ES LENGUAS INSETAR COMO INSCRIPCION LENGUAS                                    
                                            Clases.Operaciones.GenerarOperacionDetalles("Inscripción " + datos_inscripto[a][13] + " de " + datos_inscripto[a][12] + ". Desde " + datos_inscripto[a][5] + "-" + datos_inscripto[a][6] + " hasta " + datos_inscripto[a][7] + "-" + datos_inscripto[a][8], datos_inscripto[a][3], "Inscripción Lenguas", id_inscripcion.toString());
                                        } else {
                                            Clases.Operaciones.GenerarOperacionDetalles("Inscripción " + datos_inscripto[a][13] + " de " + datos_inscripto[a][12] + ". Desde " + datos_inscripto[a][5] + "-" + datos_inscripto[a][6] + " hasta " + datos_inscripto[a][7] + "-" + datos_inscripto[a][8], datos_inscripto[a][3], "Inscripción", id_inscripcion.toString());
                                        }
                                    } else {
                                        Clases.Operaciones.GenerarOperacionDetalles("Inscripción de " + datos_inscripto[a][12] + ". Desde " + datos_inscripto[a][5] + "-" + datos_inscripto[a][6] + " hasta " + datos_inscripto[a][7] + "-" + datos_inscripto[a][8], datos_inscripto[a][3], "Inscripción", id_inscripcion.toString());
                                    }
                                }

                                if (alumnos.excepcion != null) {
                                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Ocurrio un error al inscribir, Compruebe los datos antes de continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                                    linneoadmin.LinneoAdmin.rollback(savepoint1);
                                    linneoadmin.LinneoAdmin.activarAutoCommit();
                                    return;
                                } else {
                                    if (datos_inscripto.length == 1 && datos_inscripto[0][1].equals("Lenguas")) {
                                // YA QUE ES SOLO LENGUAS, EN LA OPERACION PONER NUM_RECIBO = NULL

                                        // SELECCIONAR ULTIMO ID OPERACION PARA MANDAR AL RECIBO
                                        Integer id_operacion = Clases.Operaciones.UltimoID();

                                        Clases.Operaciones.PonerNullNumRecibo(id_operacion);

                                    } else {
                                        int seleccion = JOptionPane.showOptionDialog(jframe, "¿Que desea hacer?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Imprimir", "Cancelar"}, "Imprimir");

                                        if (seleccion == 0) {
                                            // SELECCIONAR ULTIMO ID OPERACION PARA MANDAR AL RECIBO
                                            Integer id_operacion = Clases.Operaciones.UltimoID();

                                            //  FRAME - SECTOR - TIPO OPERACION - NUMERO OPERACION
                                            Clases.Recibo.Imprimir(jframe, "Institucional", "Inscripción", id_operacion, "");
                                        }
                                        Variables.AumentarNumRecibo();
                                    }
                                    linneoadmin.LinneoAdmin.commiteo();
                                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con Exito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        } else { // ES CON RECIBO INTERNO
                            
                            if (nuevo) {
                                // INSERT ALUMNO cod, dni, apenom, fecha_nac, tel_tutor
                                alumnos.Nuevo(cod_alumno.getText(), dni_alumno.getText(), apenom_alumno.getText(), anio_nac_alumno.getText() + "-" + mes_nac_alumno.getText() + "-" + dia_nac_alumno.getText(), tel_tutor.getText());
                            }

                            // INSERT OPERACIONES   String pk_persona, String tipo_pago
                            Clases.Operaciones.GenerarOperacionConReciboInterno(datos_alumno[0], combo_tipo_pago.getSelectedItem().toString(), hora, fecha, text_detalle.getText(), "Institucional","Interno: " + jTextField1.getText());

                            for (int a = 0; a < datos_inscripto.length; a++) {

                                // Primero generar la operación, y los detalles... Preguntar si desea pagar Cuota o no, si pone que si Generar Tabla con Cuotas Desde - Hasta
                                // Traer las cuotas seleccionadas y agregar al array que pasa a Recibo.dialog
                                // Hacer update en las cuotas pagadas después de insertarlas
                                // VARIABLES PARA RECIBO!
                                // INSERT DE CUOTAS
                                int mes_desde = Integer.valueOf(datos_inscripto[a][5]);
                                int mes_hasta = Integer.valueOf(datos_inscripto[a][7]);

                                // INSERT OPERACIONES
                                if (datos_inscripto[a][12].equals(datos_inscripto[a][13])) {// INSCRCIPCION_CARRERA
                                    cod_c = cursos.MostrarDatosCarreras(datos_inscripto[a][12]);
                                    cod_carrera = cod_c[1].toString();

                                    es_grado = false;
                                } else {
                                    cod_c = cursos.MostrarDatosGrados(datos_inscripto[a][0]);
                                    cod_grado = cod_c[1].toString();
                                    cod_carrera = cod_c[6].toString();

                                    es_grado = true;
                                }

                                Integer id_inscripcion = null;

                                //String id_alu, String id_carr, String id_grado, String anio_t, String tipo, int mes_desde, int mes_hasta
                                Clases.Cuotas.GenerarCuotasAlumnos(datos_alumno[0], cod_carrera, cod_grado, datos_inscripto[a][2], datos_inscripto[a][1], mes_desde, mes_hasta, fecha, hora, beca_monto.getText());

                                // COD_CURSO, COD_ALUMNO, TIPO_CURSO, AÑO_CURSADO, INSC_PAGADA, INSC_DEBE, INSC_DESDE, INSC_HASTA, BECA, BECA_HASTA, ESTADO
                                if (!es_grado) {// INSCRCIPCION_CARRERA
                                    // String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe,
                                    // String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta
                                    if (datos_inscripto[a][9] == null || datos_inscripto[a][10] == null || datos_inscripto[a][11] == null) {
                                        id_inscripcion = alumnos.InscribirCarrera(cod_c[1].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                null, null,
                                                hora, fecha);
                                    } else {
                                        id_inscripcion = alumnos.InscribirCarrera(cod_c[1].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                datos_inscripto[a][9], datos_inscripto[a][10] + "-" + datos_inscripto[a][11],
                                                hora, fecha);
                                    }
                                } else {// INSCRCIPCION_GRADO
                                    // String grado, String carrera, String alumno, Integer anio_t, String inscripcion_pagado, String inscripcion_debe,
                                    // String inscripcion_desde, String inscripcion_hasta, String beca, String beca_hasta
                                    if (datos_inscripto[a][9] == null || datos_inscripto[a][10] == null || datos_inscripto[a][11] == null) {
                                        id_inscripcion = alumnos.InscribirGrado(cod_c[1].toString(), datos_inscripto[a][13], cod_c[6].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                null, null,
                                                hora, fecha);
                                    } else {
                                        id_inscripcion = alumnos.InscribirGrado(cod_c[1].toString(), datos_inscripto[a][13], cod_c[6].toString(), datos_alumno[0], Integer.valueOf(datos_inscripto[a][2]), datos_inscripto[a][3], datos_inscripto[a][4],
                                                datos_inscripto[a][5] + "-" + datos_inscripto[a][6], datos_inscripto[a][7] + "-" + datos_inscripto[a][8],
                                                datos_inscripto[a][9], datos_inscripto[a][10] + "-" + datos_inscripto[a][11],
                                                hora, fecha);
                                    }
                                }

                                if (es_grado) {
                                    // VERIFICAR SI ES LENGUAS O NO 
                                    if (datos_inscripto[a][1].equals("Lenguas")) {  // ES LENGUAS INSETAR COMO INSCRIPCION LENGUAS                                    
                                        Clases.Operaciones.GenerarOperacionDetalles("Inscripción " + datos_inscripto[a][13] + " de " + datos_inscripto[a][12] + ". Desde " + datos_inscripto[a][5] + "-" + datos_inscripto[a][6] + " hasta " + datos_inscripto[a][7] + "-" + datos_inscripto[a][8], datos_inscripto[a][3], "Inscripción Lenguas", id_inscripcion.toString());
                                    } else {
                                        Clases.Operaciones.GenerarOperacionDetalles("Inscripción " + datos_inscripto[a][13] + " de " + datos_inscripto[a][12] + ". Desde " + datos_inscripto[a][5] + "-" + datos_inscripto[a][6] + " hasta " + datos_inscripto[a][7] + "-" + datos_inscripto[a][8], datos_inscripto[a][3], "Inscripción", id_inscripcion.toString());
                                    }
                                } else {
                                    Clases.Operaciones.GenerarOperacionDetalles("Inscripción de " + datos_inscripto[a][12] + ". Desde " + datos_inscripto[a][5] + "-" + datos_inscripto[a][6] + " hasta " + datos_inscripto[a][7] + "-" + datos_inscripto[a][8], datos_inscripto[a][3], "Inscripción", id_inscripcion.toString());
                                }
                            }

                            if (alumnos.excepcion != null) {
                                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Ocurrio un error al inscribir, Compruebe los datos antes de continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                                linneoadmin.LinneoAdmin.rollback(savepoint1);
                                linneoadmin.LinneoAdmin.activarAutoCommit();
                                return;
                            } else {
                                if (datos_inscripto.length == 1 && datos_inscripto[0][1].equals("Lenguas")) {
                                // YA QUE ES SOLO LENGUAS, EN LA OPERACION PONER NUM_RECIBO = NULL

                                    // SELECCIONAR ULTIMO ID OPERACION PARA MANDAR AL RECIBO
                                    Integer id_operacion = Clases.Operaciones.UltimoID();

                                    Clases.Operaciones.PonerNullNumRecibo(id_operacion);

                                }
                                
                                linneoadmin.LinneoAdmin.commiteo();
                                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con Exito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        
                        linneoadmin.LinneoAdmin.activarAutoCommit();
                        
                        datos_alumno = new String[4];
                        datos_inscripto = new String[1][14];

                        super.dispose();
                        ABMAlumnos frame = new ABMAlumnos();
                        frame.setVisible(true);
                        jDesktopPane1.add(frame);
                        try {
                            frame.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Desea corregir la inscripción anterior?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

        if (Seleccion == 0) { // CARGAR TODOS LOS TEXT Y COMBOS DEL ULTIMO CURSO_INSCRIPTO

            if (cant_cursos > 0) {

                volver_inscripcion_anterior();

            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void combo_tipo_pagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipo_pagoItemStateChanged
        // TODO add your handling code here:
        if (combo_tipo_pago.getSelectedItem() != null) {
            if (!combo_tipo_pago.getSelectedItem().equals("Efectivo")) {
                text_detalle.setVisible(true);
                label_detalle.setVisible(true);
            } else {
                text_detalle.setVisible(false);
                label_detalle.setVisible(false);
            }
        }
    }//GEN-LAST:event_combo_tipo_pagoItemStateChanged

    private void combo_anioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_anioItemStateChanged
        // TODO add your handling code here:
        if (combo_anio.getItemCount() > 0) {
            insc_desde_anio.setText(combo_anio.getSelectedItem().toString());
            insc_hasta_anio.setText(combo_anio.getSelectedItem().toString());

            insc_desde_anio.setEditable(false);
            insc_hasta_anio.setEditable(false);
        }
    }//GEN-LAST:event_combo_anioItemStateChanged

    private void beca_montoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_beca_montoFocusLost
        // TODO add your handling code here:
        Integer beca = beca_monto.getText().length();
        
        if(beca > 0 || !beca_monto.getText().isEmpty()){
            beca_hasta_mes.setEditable(true);
            beca_hasta_anio.setEditable(true);
            beca_hasta_mes.setFocusable(true);
            beca_hasta_anio.setFocusable(true);
        }else{
            beca_hasta_mes.setEditable(false);
            beca_hasta_anio.setEditable(false);
            beca_hasta_mes.setFocusable(false);
            beca_hasta_anio.setFocusable(false);
        }
    }//GEN-LAST:event_beca_montoFocusLost

    private void inscripcion_pagadaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inscripcion_pagadaKeyReleased
        // TODO add your handling code here:
        if (!inscripcion_pagada.getText().isEmpty()) {
            BigDecimal monto_recibido = new BigDecimal(inscripcion_pagada.getText());

            BigDecimal monto_inscripcion = new BigDecimal(inscripcion_total.getText());

            if (monto_recibido.compareTo(monto_inscripcion) == 1) {
                inscripcion_pagada.setText(monto_inscripcion.toString().replace('.', ','));
            }
        }
    }//GEN-LAST:event_inscripcion_pagadaKeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        if (jComboBox1.getSelectedItem().equals("No")) {
            jTextField1.setVisible(false);
            jLabel22.setVisible(false);
        } else {
            jTextField1.setVisible(true);
            jLabel22.setVisible(true);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anio_nac_alumno;
    private javax.swing.JTextField apenom_alumno;
    private javax.swing.JTextField beca_hasta_anio;
    private javax.swing.JTextField beca_hasta_mes;
    private javax.swing.JTextField beca_monto;
    private javax.swing.JTextField cod_alumno;
    private javax.swing.JComboBox combo_anio;
    private javax.swing.JComboBox combo_curso;
    private javax.swing.JComboBox combo_nivel;
    private javax.swing.JComboBox combo_tipo;
    private javax.swing.JComboBox combo_tipo_pago;
    private javax.swing.JTextField dia_nac_alumno;
    private javax.swing.JTextField dni_alumno;
    private javax.swing.JTextField edad_alumno;
    private javax.swing.JTextField insc_desde_anio;
    private javax.swing.JTextField insc_desde_mes;
    private javax.swing.JTextField insc_hasta_anio;
    private javax.swing.JTextField insc_hasta_mes;
    private javax.swing.JTextField inscripcion_pagada;
    private javax.swing.JTextField inscripcion_total;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel label_detalle;
    private javax.swing.JTextField mes_nac_alumno;
    private javax.swing.JTextField tel_tutor;
    private javax.swing.JTextField text_detalle;
    // End of variables declaration//GEN-END:variables
}
