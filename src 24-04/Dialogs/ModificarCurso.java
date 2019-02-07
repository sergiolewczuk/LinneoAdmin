/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import Dialogs.*;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ModificarCurso extends javax.swing.JDialog {

    JFrame jframe;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    Clases.Cuotas cuotas = new Clases.Cuotas();
    Clases.Cursos cursos = new Clases.Cursos();
    Clases.TiposPagos tipo_pagos = new Clases.TiposPagos();
    
    BigDecimal monto_anual;
    BigDecimal monto_pagado_inscripcion_anterior = BigDecimal.ZERO;
    Integer mes_actual;
    String id_inscripcion = null;
    BigDecimal monto_pagado_total = BigDecimal.ZERO;
    
    Object id_carrera_seleccionado = null;
    Object id_grado_seleccionado = null;
    
    Object id_carrera_viejo = null;
    Object id_grado_viejo = null;
    
    String cod_curso = null;
    String nombre_curso = null;
    String nivel_curso = null;
    String tipo_curso = null;
    
    String id_alumno;
    
    @Override
    public void dispose() {
        manager.removeKeyEventDispatcher(key);
        super.dispose(); 
    }
    
    public void showDialog() {
        setVisible(true);
    }
    
    //TECLADO ACCESOS DIRECTOS
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (ModificarCurso.this.isActive()) {
                if (e.getKeyCode() == 27) {//ESCAPE
                    jButton2.doClick();
                }

                if (e.getKeyCode() == 112) {
                    jButton1.doClick();
                }
            }
            return false;
        }
    }
    
    void tipo_cursos(){
        // TRAER TODOS LOS TIPO CURSOS : CURSADO, CURSILLO, LENGUAS O LOS QUE HAYA
        Object[][] info = cursos.SeleccionarTipoCursos();
        
        combo_tipo.removeAllItems();
        
        for (int a = 0; a < info.length; a++) {
            combo_tipo.addItem(info[a][0]);            
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
                jLabel11.setVisible(true);
                combo_curso.setVisible(true);
            }else{
                jLabel11.setVisible(false);
                combo_curso.setVisible(false);
                combo_curso.removeAllItems();
                combo_curso.addItem("");
            }
        }
    }
    
    void cargar_combo (String id_alumno) {
        jComboBox1.removeAllItems();

        //" - "
        String[][] cant_cursos = Clases.Alumnos.InscripcionesPorAlumno(id_alumno);
        for (int a = 0; a < cant_cursos.length; a++) {
            jComboBox1.addItem(cant_cursos[a][0]);
        }
    }
    
    public ModificarCurso(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        initComponents();
        
        id_alumno = id;
        
        cargar_combo(id_alumno);
        
        tipo_cursos();
        
        Object[] datos = Clases.Alumnos.Mostrar_Datos(id_alumno);
        
        info_alumno.setText("ID: " + datos[1] + "  -  DNI: " + datos[2] + "  -  APELLIDO Y NOMBRE: " + datos[3].toString().toUpperCase());
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        getRootPane().setBorder( BorderFactory.createLineBorder(Color.RED));
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocationRelativeTo(null);
        
        
        mes_actual = Integer.valueOf(Principal.fecha_actual.split("-")[1]);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        info_alumno = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        combo_tipo = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        combo_nivel = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        combo_curso = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setText("Modificar Curso");

        jButton1.setText("F1 - GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("ESC - SALIR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Curso a modificar");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel5.setText("Alumno");

        info_alumno.setEditable(false);

        jLabel2.setText("Datos de la Inscripción Anterior");

        jLabel3.setText("Cuota");

        jTextField1.setEditable(false);

        jLabel6.setText("Inscripción");

        jTextField2.setEditable(false);

        jLabel8.setText("Datos con el Nuevo Curso");

        jLabel9.setText("Cuota");

        jTextField4.setEditable(false);

        jLabel10.setText("Inscripción");

        jTextField5.setEditable(false);

        jLabel12.setText("Pagado en Cuota");

        jTextField7.setEditable(false);

        jLabel13.setText("Pagado en Inscripción");

        jTextField3.setEditable(false);

        jScrollPane1.setAutoscrolls(true);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(1);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel14.setText("Pagado en Cuota");

        jTextField8.setEditable(false);

        jLabel15.setText("Pagado en Inscripción");

        jTextField6.setEditable(false);

        jScrollPane2.setAutoscrolls(true);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(1);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel7.setText("Curso Nuevo");

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

        jLabel11.setText("Curso");

        combo_curso.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_cursoItemStateChanged(evt);
            }
        });

        jButton3.setText("Calcular");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(info_alumno)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                            .addComponent(jTextField8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jScrollPane2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jTextField5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(combo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(combo_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(combo_curso, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(0, 75, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(info_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(combo_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(combo_curso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        // CONFIRMAR ACCIÓN
        Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Desea confirmar el cambio de Curso?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Sí", "Cancelar"}, "Si");
        if (Seleccion == 0) {

            Object[] datos = Clases.Alumnos.MontosDelCurso(combo_nivel.getSelectedItem().toString(), combo_curso.getSelectedItem().toString());

            BigDecimal monto_cuota = BigDecimal.ZERO;
            BigDecimal monto_cuota_anticipado = BigDecimal.ZERO;
            BigDecimal monto_inscripcion = BigDecimal.ZERO;

            BigDecimal monto_total_ingresado_x_alumno = monto_pagado_total;

            if (monto_pagado_inscripcion_anterior.compareTo(BigDecimal.ZERO) != 0) {
                if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == 1) {
                    jTextField6.setText(jTextField5.getText());
                    monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_pagado_inscripcion_anterior);
                    jTextField8.setText(monto_total_ingresado_x_alumno.toString());

                    // UPDATEAR INSCRIPCION
                    Clases.Alumnos.ActualizarInscripcionCambioCurso(id_inscripcion, monto_pagado_inscripcion_anterior, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, id_alumno);

                } else if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == 0) {
                    jTextField6.setText(jTextField5.getText());
                    monto_total_ingresado_x_alumno = BigDecimal.ZERO;

                    // UPDATEAR INSCRIPCION
                    Clases.Alumnos.ActualizarInscripcionCambioCurso(id_inscripcion, monto_pagado_inscripcion_anterior, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, id_alumno);

                } else if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == -1) {
                    jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                    monto_total_ingresado_x_alumno = BigDecimal.ZERO;

                    BigDecimal debe_insc = monto_pagado_inscripcion_anterior;

                    // UPDATEAR INSCRIPCION
                    Clases.Alumnos.ActualizarInscripcionCambioCurso(id_inscripcion, monto_total_ingresado_x_alumno, debe_insc.subtract(monto_total_ingresado_x_alumno), id_carrera_seleccionado, id_grado_seleccionado, id_alumno);

                }
            } else {
                if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == 1) {
                    jTextField6.setText(jTextField5.getText());
                    monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract((BigDecimal) datos[0]);
                    jTextField8.setText(monto_total_ingresado_x_alumno.toString());

                    // UPDATEAR INSCRIPCION
                    Clases.Alumnos.ActualizarInscripcionCambioCurso(id_inscripcion, (BigDecimal) datos[0], BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, id_alumno);

                } else if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == 0) {
                    jTextField6.setText(jTextField5.getText());
                    monto_total_ingresado_x_alumno = BigDecimal.ZERO;

                    // UPDATEAR INSCRIPCION
                    Clases.Alumnos.ActualizarInscripcionCambioCurso(id_inscripcion, (BigDecimal) datos[0], BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, id_alumno);

                } else if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == -1) {
                    jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                    monto_total_ingresado_x_alumno = BigDecimal.ZERO;

                    BigDecimal debe_insc = (BigDecimal) datos[0];

                    // UPDATEAR INSCRIPCION
                    Clases.Alumnos.ActualizarInscripcionCambioCurso(id_inscripcion, monto_total_ingresado_x_alumno, debe_insc.subtract(monto_total_ingresado_x_alumno), id_carrera_seleccionado, id_grado_seleccionado, id_alumno);

                }
            }

            if (monto_total_ingresado_x_alumno.compareTo(BigDecimal.ZERO) == 1) {
                // PRE VISUALIZAR LAS CUOTAS QUE PAGARIA CON LA "PLATA" QUE SOBRAs
                // AGARRAR EL MES DESDE DE LA INSCRIPCION

                Object[] dat = Clases.Alumnos.DatosInscripcion(id_inscripcion);

                Integer mes_insc_desde = Integer.valueOf(dat[0].toString().split("-")[0]);
                Integer anio_insc_desde = Integer.valueOf(dat[0].toString().split("-")[1]);
                Integer mes_insc_hasta = Integer.valueOf(dat[1].toString().split("-")[0]);

                Integer mes_beca_desde = Integer.valueOf(dat[2].toString().split("-")[0]);

                Integer mes_beca_hasta = Integer.valueOf(dat[3].toString().split("-")[0]);

                Boolean becado = false;

                if (dat[2] != null) {
                    // ES BECADO
                    becado = true;
                }

                monto_cuota = (BigDecimal) datos[1];
                monto_cuota_anticipado = (BigDecimal) datos[2];
                monto_inscripcion = (BigDecimal) datos[0];

                BigDecimal monto_beca = (BigDecimal) dat[4];

                String[] desc = null;

                for (int mes = mes_insc_desde; mes <= mes_insc_hasta; mes++) {
                    if (becado == true && ((mes >= mes_beca_desde) && (mes <= mes_beca_hasta))) {
                        // OBTENER EL BECA_DESDE Y BECA_HASTA
                        if (monto_beca.compareTo(monto_total_ingresado_x_alumno) == 1) {
                            Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_beca, monto_total_ingresado_x_alumno, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);
                            monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_total_ingresado_x_alumno);
                        } else {
                            Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_beca, monto_beca, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);
                            monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_beca);
                        }
                    } else {
                        if (monto_total_ingresado_x_alumno.compareTo(BigDecimal.ZERO) == 0) {
                            // PONER NULL EL RESTO DE LAS CUOTAS
                            Clases.Alumnos.PonerNullCuotasCursoNuevo(id_alumno, id_carrera_viejo, id_carrera_seleccionado, id_grado_seleccionado, mes);

                        } else if (monto_total_ingresado_x_alumno.compareTo(BigDecimal.ZERO) == 1) {
                            desc = Clases.Alumnos.PagoDescuento(id_alumno, anio_insc_desde, mes, id_carrera_viejo);
                            if ((desc[0].equals("si")) && (desc[1].equals("si"))) { // PAGO CON DESCUENTO, SE LE ACREDITA EL DESCUENTO AL ACTUALIZARLE EL CURSO
                                if (monto_cuota_anticipado.compareTo(monto_total_ingresado_x_alumno) == 1) {
                                    // UPDATEAR PARCIAL CON DESCUENTO

                                    Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_total_ingresado_x_alumno, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);

                                    //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                    monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_total_ingresado_x_alumno);
                                } else {
                                    // UPDATEAR PAGANDO CON DESCUENTO

                                    Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_cuota_anticipado, monto_cuota.subtract(monto_cuota_anticipado), id_carrera_seleccionado, id_grado_seleccionado, true);

                                    //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota_anticipado + "\n";
                                    monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_cuota_anticipado);
                                }
                            } else if ((desc[0].equals("si")) && (desc[1].equals("no"))) {
                                if (monto_cuota.compareTo(monto_total_ingresado_x_alumno) == 1) {
                                    // UPDATEAR PARCIAL SIN DESCUENTO

                                    Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_total_ingresado_x_alumno, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);

                                    //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                    monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_total_ingresado_x_alumno);
                                } else {
                                    // UPDATEAR PAGANDO SIN DESCUENTO

                                    Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_cuota, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);

                                    //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota + "\n";
                                    monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_cuota);
                                }
                            } else {
                                //  PREGUNTAR EN QUE MES ESTAMOS PARA ACREDITAR LAS CUOTAS CON DESC O NO
                                if (mes_actual >= mes) { // APLICO DESCUENTO POR QUE EL MES_ACTUAL ES MAYOR O IGUAL AL MES
                                    if (monto_cuota_anticipado.compareTo(monto_total_ingresado_x_alumno) == 1) {
                                        // UPDATEAR PARCIAL CON DESCUENTO

                                        Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_total_ingresado_x_alumno, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);

                                        //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                        monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_total_ingresado_x_alumno);
                                    } else {
                                        // UPDATEAR PAGANDO CON DESCUENTO

                                        Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_cuota_anticipado, monto_cuota.subtract(monto_cuota_anticipado), id_carrera_seleccionado, id_grado_seleccionado, true);

                                        //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota_anticipado + "\n";
                                        monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_total_ingresado_x_alumno);
                                    }
                                } else {
                                    if (monto_cuota.compareTo(monto_total_ingresado_x_alumno) == 1) {
                                        // UPDATEAR PARCIAL SIN DESCUENTO

                                        Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_total_ingresado_x_alumno, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);

                                        //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                        monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_total_ingresado_x_alumno);
                                    } else {
                                        // UPDATEAR PAGANDO SIN DESCUENTO

                                        Clases.Alumnos.ActualizarCuotasCambioCurso(id_alumno, anio_insc_desde, mes, id_carrera_viejo, monto_cuota, monto_cuota, BigDecimal.ZERO, id_carrera_seleccionado, id_grado_seleccionado, false);

                                        //detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota + "\n";
                                        monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_cuota);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (monto_total_ingresado_x_alumno.compareTo(BigDecimal.ZERO) == 0) {
                // UPDATEAR LAS CUOTAS AL NUEVO CURSO
                Clases.Alumnos.CambiarCarreraCuotasCursoNuevo(id_alumno, id_carrera_viejo, id_carrera_seleccionado, id_grado_seleccionado);
            }

            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con Exito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);

            cargar_combo(id_alumno);

            tipo_cursos();

            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField8.setText("");
            jTextArea2.setText("");
        }        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        if (jComboBox1.getItemCount() > 0) {           
            // TRAER LOS DATOS
            String seleccionado = jComboBox1.getSelectedItem().toString();
            
            id_inscripcion = seleccionado.split("-")[0];
            
            Object[] datos = Clases.Alumnos.MontosInscripcionActual(id_inscripcion);
            
            jTextField1.setText("Sin DESC.:  " + datos[1].toString() + ". Con DESC.:  " + datos[2].toString());
            jTextField2.setText(datos[0].toString());          
            
            id_carrera_viejo = datos[3];
            id_grado_viejo = datos[4];
            
            // TRAER EL INGRESO TOTAL
            
            Object[] montos = Clases.Alumnos.MontosPagadoPorAlumno(id_inscripcion);
            
            monto_pagado_total = monto_pagado_total.add((BigDecimal) montos[0]);
            monto_pagado_total = monto_pagado_total.add((BigDecimal) montos[1]);
            
            BigDecimal mm = (BigDecimal) montos[3];
            
            if (mm.compareTo(BigDecimal.ZERO) == 0) {
                // SI NO DEBE NADA, TOMAR ESTE MONTO DE INSCRIPCION, PORQUE EL MONTO AL DIA ACTUAL FUE ACTUALIZADO
                monto_pagado_inscripcion_anterior = (BigDecimal) montos[0];
                jTextField2.setText(monto_pagado_inscripcion_anterior.toString());  
                
            }
            
            jTextField3.setText(montos[0].toString() + ". Debe: "+ montos[3]);
            jTextField7.setText(montos[1].toString());
            
            jTextArea1.setText(montos[2].toString());
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void combo_tipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipoItemStateChanged
        // TODO add your handling code here:
        niveles_carreras();
    }//GEN-LAST:event_combo_tipoItemStateChanged

    private void combo_nivelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_nivelItemStateChanged
        // TODO add your handling code here:
        cargar_cursos();
    }//GEN-LAST:event_combo_nivelItemStateChanged

    private void combo_cursoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_cursoItemStateChanged
        // TODO add your handling code here:
        if(combo_curso.getItemCount() > 0){
            nombre_curso = combo_curso.getSelectedItem().toString();
        }

        nivel_curso = combo_nivel.getSelectedItem().toString();
        tipo_curso = combo_tipo.getSelectedItem().toString();
        
        if(nombre_curso!=null && nivel_curso!=null && tipo_curso!=null){
            if(combo_curso.getItemCount() > 0){
                
                // MOSTRAR CUANTO VALE LA CUOTA ACTUAL DE INSCRIPCION Y CUANTO VALE LA INSCRIPCION ACTUAL
                Object[] datos = Clases.Alumnos.MontosDelCurso(combo_nivel.getSelectedItem().toString(),combo_curso.getSelectedItem().toString());
                
                jTextField4.setText("Sin DESC.:  " + datos[1].toString() + ". Con DESC.:  " + datos[2].toString());
                
                if (monto_pagado_inscripcion_anterior.compareTo(BigDecimal.ZERO) != 0) {
                    jTextField5.setText(monto_pagado_inscripcion_anterior.toString());
                } else {
                    jTextField5.setText(datos[0].toString());
                }
                
            
                id_carrera_seleccionado = datos[3];
                id_grado_seleccionado = datos[4];
                
                jButton1.setEnabled(false);
            }
        }
    }//GEN-LAST:event_combo_cursoItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        // CALCULAR LA PLATA QUE TENIA EL ALUMNO Y VER COMO QUEDAN CON LAS CUOTAS NUEVAS
        // GASTAR TODO EN LA INSCRIPCION Y DSP LO QUE QUEDA EN LAS CUOTAS
        
        /*jButton1.setEnabled(true);
        
        Object[] datos = Clases.Alumnos.MontosDelCurso(combo_nivel.getSelectedItem().toString(),combo_curso.getSelectedItem().toString());        
        
        BigDecimal monto_cuota = BigDecimal.ZERO;
        BigDecimal monto_cuota_anticipado = BigDecimal.ZERO;
        BigDecimal monto_inscripcion = BigDecimal.ZERO;
        
        BigDecimal monto_total_ingresado_x_alumno = monto_pagado_total;

        if (monto_pagado_inscripcion_anterior.compareTo(BigDecimal.ZERO) != 0) {
            if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == 1) {
                jTextField6.setText(jTextField5.getText());
                monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_pagado_inscripcion_anterior);
                jTextField8.setText(monto_total_ingresado_x_alumno.toString());
            } else if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == 0) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            } else if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == -1) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            }
        } else {
            if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == 1) {
                jTextField6.setText(jTextField5.getText());
                monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract((BigDecimal) datos[0]);
                jTextField8.setText(monto_total_ingresado_x_alumno.toString());
            } else if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == 0) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            } else if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == -1) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            }
        }
        
        if (monto_total_ingresado_x_alumno.compareTo(BigDecimal.ZERO) == 1) {
                    // PRE VISUALIZAR LAS CUOTAS QUE PAGARIA CON LA "PLATA" QUE SOBRAs
            // AGARRAR EL MES DESDE DE LA INSCRIPCION

            Object[] dat = Clases.Alumnos.DatosInscripcion(id_inscripcion);

            Integer mes_insc_desde = Integer.valueOf(dat[0].toString().split("-")[0]);
            Integer anio_insc_desde = Integer.valueOf(dat[0].toString().split("-")[1]);
            Integer mes_insc_hasta = Integer.valueOf(dat[1].toString().split("-")[0]);
            Integer anio_insc_hasta = Integer.valueOf(dat[1].toString().split("-")[1]);
            
            Integer mes_beca_desde = Integer.valueOf(dat[2].toString().split("-")[0]);
            Integer anio_beca_desde = Integer.valueOf(dat[2].toString().split("-")[1]);
            
            Integer mes_beca_hasta = Integer.valueOf(dat[3].toString().split("-")[0]);
            Integer anio_beca_hasta = Integer.valueOf(dat[3].toString().split("-")[1]);
            
            BigDecimal monto_beca = (BigDecimal) dat[4];

            Boolean becado = false;

            if (dat[2] != null) {
                // ES BECADO
                becado = true;
            }
        
            monto_cuota = (BigDecimal) datos[1];
            monto_cuota_anticipado = (BigDecimal) datos[2];
            monto_inscripcion = (BigDecimal) datos[0];
            
            String[] desc = null;
            String detalle_cuotas_nuevas = "";
            BigDecimal monto_a_favor_total = monto_total_ingresado_x_alumno;

            for (int mes = mes_insc_desde; mes <= mes_insc_hasta; mes++) {
                if (becado == true && ((mes >= mes_beca_desde) && (mes <= mes_beca_hasta))) {
                    // OBTENER EL BECA_DESDE Y BECA_HASTA
                    if (monto_beca.compareTo(monto_a_favor_total) == 1) {
                        detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total +" (Cuota Becada)\n";
                        monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                    } else {
                        detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_beca +" (Cuota Becada)\n";
                        monto_a_favor_total = monto_a_favor_total.subtract(monto_beca);
                    }                   
                } else {
                    desc = Clases.Alumnos.PagoDescuento(id_alumno, anio_insc_desde, mes, dat[5]);
                    if ((desc[0].equals("si")) && (desc[1].equals("si"))) { // PAGO CON DESCUENTO, SE LE ACREDITA EL DESCUENTO AL ACTUALIZARLE EL CURSO
                        if (monto_cuota_anticipado.compareTo(monto_a_favor_total) == 1) {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                        } else {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota_anticipado + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota_anticipado);
                        }
                    } else if ((desc[0].equals("si")) && (desc[1].equals("no"))) {
                        if (monto_cuota.compareTo(monto_a_favor_total) == 1) {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                        } else {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota);
                        }
                    } else {
                        //  PREGUNTAR EN QUE MES ESTAMOS PARA ACREDITAR LAS CUOTAS CON DESC O NO
                        if (mes_actual >= mes) { // APLICO DESCUENTO POR QUE EL MES_ACTUAL ES MAYOR O IGUAL AL MES
                            if (monto_cuota_anticipado.compareTo(monto_a_favor_total) == 1) {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                            } else {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota_anticipado + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota_anticipado);
                            }
                        } else {
                            if (monto_cuota.compareTo(monto_a_favor_total) == 1) {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                            } else {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota);
                            }
                        }
                    }
                }

                if (monto_a_favor_total.compareTo(BigDecimal.ZERO) != 1) {
                    break;
                }
            }

            jTextArea2.setText(detalle_cuotas_nuevas);
        }*/
        
        // CALCULAR LA PLATA QUE TENIA EL ALUMNO Y VER COMO QUEDAN CON LAS CUOTAS NUEVAS
        // GASTAR TODO EN LA INSCRIPCION Y DSP LO QUE QUEDA EN LAS CUOTAS
        
        jButton1.setEnabled(true);
        
        Object[] datos = Clases.Alumnos.MontosDelCurso(combo_nivel.getSelectedItem().toString(),combo_curso.getSelectedItem().toString());        
        
        BigDecimal monto_cuota = BigDecimal.ZERO;
        BigDecimal monto_cuota_anticipado = BigDecimal.ZERO;
        BigDecimal monto_inscripcion = BigDecimal.ZERO;
        
        BigDecimal monto_total_ingresado_x_alumno = monto_pagado_total;

        if (monto_pagado_inscripcion_anterior.compareTo(BigDecimal.ZERO) != 0) {
            if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == 1) {
                jTextField6.setText(jTextField5.getText());
                monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract(monto_pagado_inscripcion_anterior);
                jTextField8.setText(monto_total_ingresado_x_alumno.toString());
            } else if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == 0) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            } else if (monto_total_ingresado_x_alumno.compareTo(monto_pagado_inscripcion_anterior) == -1) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            }
        } else {
            if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == 1) {
                jTextField6.setText(jTextField5.getText());
                monto_total_ingresado_x_alumno = monto_total_ingresado_x_alumno.subtract((BigDecimal) datos[0]);
                jTextField8.setText(monto_total_ingresado_x_alumno.toString());
            } else if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == 0) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            } else if (monto_total_ingresado_x_alumno.compareTo((BigDecimal) datos[0]) == -1) {
                jTextField6.setText(monto_total_ingresado_x_alumno.toString());
                monto_total_ingresado_x_alumno = BigDecimal.ZERO;
            }
        }
        
        if (monto_total_ingresado_x_alumno.compareTo(BigDecimal.ZERO) == 1) {
                    // PRE VISUALIZAR LAS CUOTAS QUE PAGARIA CON LA "PLATA" QUE SOBRAs
            // AGARRAR EL MES DESDE DE LA INSCRIPCION

            Object[] dat = Clases.Alumnos.DatosInscripcion(id_inscripcion);

            Integer mes_insc_desde = Integer.valueOf(dat[0].toString().split("-")[0]);
            Integer anio_insc_desde = Integer.valueOf(dat[0].toString().split("-")[1]);
            Integer mes_insc_hasta = Integer.valueOf(dat[1].toString().split("-")[0]);
            Integer anio_insc_hasta = Integer.valueOf(dat[1].toString().split("-")[1]);
            
            Integer mes_beca_desde = Integer.valueOf(dat[2].toString().split("-")[0]);
            Integer anio_beca_desde = Integer.valueOf(dat[2].toString().split("-")[1]);
            
            Integer mes_beca_hasta = Integer.valueOf(dat[3].toString().split("-")[0]);
            Integer anio_beca_hasta = Integer.valueOf(dat[3].toString().split("-")[1]);
            
            BigDecimal monto_beca = (BigDecimal) dat[4];

            Boolean becado = false;

            if (dat[2] != null) {
                // ES BECADO
                becado = true;
            }
            
            monto_cuota = (BigDecimal) datos[1];
            monto_cuota_anticipado = (BigDecimal) datos[2];
            monto_inscripcion = (BigDecimal) datos[0];
            

            String[] desc = null;
            String detalle_cuotas_nuevas = "";
            BigDecimal monto_a_favor_total = monto_total_ingresado_x_alumno;

            for (int mes = mes_insc_desde; mes <= mes_insc_hasta; mes++) {
                if (becado == true && ((mes >= mes_beca_desde) && (mes <= mes_beca_hasta))) {
                    // OBTENER EL BECA_DESDE Y BECA_HASTA
                    if (monto_beca.compareTo(monto_a_favor_total) == 1) {
                        detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total +" (Cuota Becada)\n";
                        monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                    } else {
                        detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_beca +" (Cuota Becada)\n";
                        monto_a_favor_total = monto_a_favor_total.subtract(monto_beca);
                    }                   
                } else {
                    desc = Clases.Alumnos.PagoDescuento(id_alumno, anio_insc_desde, mes, dat[5]);
                    if ((desc[0].equals("si")) && (desc[1].equals("si"))) { // PAGO CON DESCUENTO, SE LE ACREDITA EL DESCUENTO AL ACTUALIZARLE EL CURSO
                        if (monto_cuota_anticipado.compareTo(monto_a_favor_total) == 1) {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                        } else {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota_anticipado + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota_anticipado);
                        }
                    } else if ((desc[0].equals("si")) && (desc[1].equals("no"))) {
                        if (monto_cuota.compareTo(monto_a_favor_total) == 1) {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                        } else {
                            detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota + "\n";
                            monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota);
                        }
                    } else {
                        //  PREGUNTAR EN QUE MES ESTAMOS PARA ACREDITAR LAS CUOTAS CON DESC O NO
                        if (mes_actual >= mes) { // APLICO DESCUENTO POR QUE EL MES_ACTUAL ES MAYOR O IGUAL AL MES
                            if (monto_cuota_anticipado.compareTo(monto_a_favor_total) == 1) {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                            } else {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota_anticipado + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota_anticipado);
                            }
                        } else {
                            if (monto_cuota.compareTo(monto_a_favor_total) == 1) {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_a_favor_total + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_a_favor_total);
                            } else {
                                detalle_cuotas_nuevas = detalle_cuotas_nuevas + "Mes: " + mes + ". Año: " + anio_insc_desde + ". Pagado: " + monto_cuota + "\n";
                                monto_a_favor_total = monto_a_favor_total.subtract(monto_cuota);
                            }
                        }
                    }
                }

                if (monto_a_favor_total.compareTo(BigDecimal.ZERO) != 1) {
                    break;
                }
            }

            jTextArea2.setText(detalle_cuotas_nuevas);
        }        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JTextField textfield = new JTextField();
        
        String seleccion = JOptionPane.showInputDialog(
                textfield,
                "Ingrese el monto",
                JOptionPane.QUESTION_MESSAGE);  // el icono sera un iterrogante

        jTextField5.setText(df.format(seleccion));
        monto_pagado_inscripcion_anterior = new BigDecimal(seleccion);
        jButton3.doClick();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModificarCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ModificarCurso dialog = new ModificarCurso(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox combo_curso;
    private javax.swing.JComboBox combo_nivel;
    private javax.swing.JComboBox combo_tipo;
    private javax.swing.JTextField info_alumno;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
