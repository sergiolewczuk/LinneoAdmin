/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Clases.Inscriptos;
import Pantallas.Frames.Principal;
import java.awt.FileDialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AlumnosInscriptos extends javax.swing.JInternalFrame {

    
    void Excel(JTable table, String path, String periodo) throws FileNotFoundException, IOException {
        Workbook wb = new XSSFWorkbook(); //Excell workbook
        Sheet sheet = wb.createSheet("Hoja1"); //WorkSheet
        Row row = sheet.createRow(4); //Row created at line 3
        TableModel model = table.getModel(); //Table model
        
        /* Get access to HSSFCellStyle */
        CellStyle my_style = wb.createCellStyle();
        /* Create HSSFFont object from the workbook */
        Font my_font = wb.createFont();
        /* set the weight of the font */
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        /* attach the font to the style created earlier */
        my_style.setFont(my_font);
        /* At this stage, we have a bold style created which we can attach to a cell */

        Row titulo_datos = sheet.createRow(0); //Create row at line 0
        titulo_datos.createCell(2).setCellValue("Fecha");//Write column name
        titulo_datos.createCell(3).setCellValue("Hora");//Write column name
        titulo_datos.getCell(2).setCellStyle(my_style);
        titulo_datos.getCell(3).setCellStyle(my_style);
        
        GregorianCalendar calendario = new java.util.GregorianCalendar();
        java.util.Date actual = new java.util.Date();
        calendario.setTime(actual);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = (calendario.get(Calendar.MONTH) + 1);
        int año = calendario.get(Calendar.YEAR);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        String hour = String.format("%02d:%02d:%02d", hora, minutos, segundos);
        String date = String.format("%02d/%02d/%02d", dia, mes, año);
        
        Row datos = sheet.createRow(1); //Create row at line 2
        datos.createCell(2).setCellValue(date);//Write column name
        datos.createCell(3).setCellValue(hour);//Write column name
        
        Row headerRow = sheet.createRow(3); //Create row at line 3
        for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
            headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
            headerRow.getCell(headings).setCellStyle(my_style);
        }

        
        
        for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
            for (int cols = 0; cols < table.getColumnCount(); cols++) { //For each table column
                if (model.getValueAt(rows, cols) != null) {
                    row.createCell(cols).setCellValue(model.getValueAt(rows, cols).toString()); //Write value
                }
            }

            //Set the row to the next one in the sequence 
            row = sheet.createRow((rows + 5));
            
            /*if (rows == 0) {
                min_row = rows + 5;
            } else {
                max_row = rows + 5;
            }*/
        }
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        
        wb.write(new FileOutputStream(path));//Save the file 
    }
    
    
    JFrame jframe;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    Clases.Cursos cursos = new Clases.Cursos();
    
    @Override
    public void dispose() {
        manager.removeKeyEventDispatcher(key);
        super.dispose();
    }
    
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (jframe.isActive()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 112) {
                        jButton1.doClick();
                    }
                }
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 113) {
                        jButton4.doClick();
                    }
                }
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 114) {
                        jButton5.doClick();
                    }
                }
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 115) {
                        jButton6.doClick();
                    }
                }
            }
            return false;
        }
    }
    
    private String[] datos = null;
    Integer row = null;
    
    public String[] showDialog() {
        setVisible(true); // when this is called the dialog will show up
        return datos;
    }
    
    void tipo_cursos(){
        // TRAER TODOS LOS TIPO CURSOS : CURSADO, CURSILLO, LENGUAS O LOS QUE HAYA
        Object[][] info = cursos.SeleccionarTipoCursos();
        
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
            
            if (!combo_tipo.getSelectedItem().toString().equals("Todos")) {
                combo_nivel.addItem("Todos");
            }

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
            combo_curso.addItem("Todos");

            if(info.length > 0){
                for (int a = 0; a < info.length; a++) {
                    combo_curso.addItem(info[a][0]);
                }
                jLabel6.setVisible(true);
                combo_curso.setVisible(true);
            }else{
                jLabel6.setVisible(false);
                combo_curso.setVisible(false);
                combo_curso.removeAllItems();
                combo_curso.addItem("");
            }
        }
    }
    
    void CargarInscriptos(String combo_ti, String combo_niv, String combo_cur) {
        Inscriptos.CargarAlumnosInscriptos(combo_ti, combo_niv, combo_cur);
        Clases.Class_editar_tabla.main(jTable1, Inscriptos.datos);
    }
    
    public AlumnosInscriptos() {
        initComponents();
        
        tipo_cursos();
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        jLabel1.setVisible(false);
        jTextField1.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        combo_tipo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        combo_nivel = new javax.swing.JComboBox();
        combo_curso = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        jLabel4.setText("jLabel4");

        setTitle("Alumnos Inscriptos");

        jLabel1.setText("FILTRAR");

        jTextField1.setName("string"); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("LISTADOS DE INSCRIPTOS");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD.", "APELLIDO Y NOMBRE", "CURSO", "FECHA INSCRIPCION", "ESTADO", "COD_INSCRIPCION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(4).setMinWidth(75);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(75);
            jTable1.getColumnModel().getColumn(5).setMinWidth(0);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jButton1.setText("F1 - REPORTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("CANCELAR [ESC]");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo");

        combo_tipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos" }));
        combo_tipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tipoItemStateChanged(evt);
            }
        });

        jLabel5.setText("Nivel");

        jLabel6.setText("Grado");

        combo_nivel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_nivelItemStateChanged(evt);
            }
        });

        jButton3.setText("Filtrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("F2 - Aprobar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("F3 - Desaprobar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("F4 - Cursando");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(combo_tipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(combo_nivel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(combo_curso, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(combo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(combo_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(combo_curso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        // CARGAR INSCRIPTOS
        if (jTextField1.getText().isEmpty()) {
            //CargarInscriptos();
        } else {
            Inscriptos.FiltrarAlumnosInscriptos(jTextField1.getText());

            Clases.Class_editar_tabla.main(jTable1, Inscriptos.datos);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() != -1) {
            
        } else {
            String titulo = "";
            
            FileDialog fd = new FileDialog(new JDialog(), "Informe Alumnos", 1);
            fd.setFile("Informe Alumnos");
            fd.setVisible(true);
            
            titulo = "Listado Alumnos";
            
            if (fd.getDirectory() != null && fd.getFile() != null) {
                try {
                    Excel(jTable1, fd.getDirectory() + fd.getFile() + ".xlsx", titulo);
                } catch (IOException ex) {
                    Logger.getLogger(AlumnosInscriptos.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(jframe, "Guardado Correctamente", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void combo_tipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipoItemStateChanged
        // TODO add your handling code here:
        if (combo_tipo.getSelectedItem().toString().equals("Todos")) {
            jLabel5.setVisible(false);
            combo_nivel.setVisible(false);
            jLabel6.setVisible(false);
            combo_curso.setVisible(false);
        } else {
            jLabel5.setVisible(true);
            combo_nivel.setVisible(true);
            
            
            niveles_carreras();
        }
    }//GEN-LAST:event_combo_tipoItemStateChanged

    private void combo_nivelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_nivelItemStateChanged
        // TODO add your handling code here:
        cargar_cursos();
    }//GEN-LAST:event_combo_nivelItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (combo_tipo.getSelectedItem().toString().equals("Todos")){
            CargarInscriptos("Todos","","");
        } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals(""))) { // ES CARRERA
            CargarInscriptos(combo_tipo.getSelectedItem().toString(),combo_nivel.getSelectedItem().toString(),"");
        } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_nivel.getSelectedItem().toString().equals("Todos"))) { // ES CARRERA
            CargarInscriptos(combo_tipo.getSelectedItem().toString(),"Todos","");
        } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (!combo_curso.getSelectedItem().toString().equals(""))) { // ES GRADO
            CargarInscriptos(combo_tipo.getSelectedItem().toString(),combo_nivel.getSelectedItem().toString(),combo_curso.getSelectedItem().toString());
        } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals("Todos"))) { // ES GRADO
            CargarInscriptos(combo_tipo.getSelectedItem().toString(),combo_nivel.getSelectedItem().toString(),"Todos");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Seleccione un Alumno.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        } else {
           Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Está seguro?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "Cancelar"}, "Si"); 
        
           if (Seleccion == 0) {
               // UPDATEAR ESTADO A "A"
               String inscripcion = jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString();
               
               Clases.Inscriptos.UpdatearEstado("A", inscripcion);
               
               JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con éxito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
           
               if (combo_tipo.getSelectedItem().toString().equals("Todos")) {
                   CargarInscriptos("Todos", "", "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals(""))) { // ES CARRERA
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_nivel.getSelectedItem().toString().equals("Todos"))) { // ES CARRERA
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), "Todos", "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (!combo_curso.getSelectedItem().toString().equals(""))) { // ES GRADO
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), combo_curso.getSelectedItem().toString());
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals("Todos"))) { // ES GRADO
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), "Todos");
               }
           }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Seleccione un Alumno.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        } else {
           Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Está seguro?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "Cancelar"}, "Si"); 
        
           if (Seleccion == 0) {
               // UPDATEAR ESTADO A "A"
               String inscripcion = jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString();
               
               Clases.Inscriptos.UpdatearEstado("D", inscripcion);
               
               JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con éxito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
           
               if (combo_tipo.getSelectedItem().toString().equals("Todos")) {
                   CargarInscriptos("Todos", "", "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals(""))) { // ES CARRERA
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_nivel.getSelectedItem().toString().equals("Todos"))) { // ES CARRERA
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), "Todos", "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (!combo_curso.getSelectedItem().toString().equals(""))) { // ES GRADO
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), combo_curso.getSelectedItem().toString());
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals("Todos"))) { // ES GRADO
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), "Todos");
               }
           }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Seleccione un Alumno.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        } else {
           Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Está seguro?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "Cancelar"}, "Si"); 
        
           if (Seleccion == 0) {
               // UPDATEAR ESTADO A "A"
               String inscripcion = jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString();
               
               Clases.Inscriptos.UpdatearEstado("C", inscripcion);
               
               JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con éxito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
               
               if (combo_tipo.getSelectedItem().toString().equals("Todos")) {
                   CargarInscriptos("Todos", "", "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals(""))) { // ES CARRERA
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_nivel.getSelectedItem().toString().equals("Todos"))) { // ES CARRERA
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), "Todos", "");
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (!combo_curso.getSelectedItem().toString().equals(""))) { // ES GRADO
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), combo_curso.getSelectedItem().toString());
               } else if ((!combo_tipo.getSelectedItem().toString().equals("Todos")) && (combo_curso.getSelectedItem().toString().equals("Todos"))) { // ES GRADO
                   CargarInscriptos(combo_tipo.getSelectedItem().toString(), combo_nivel.getSelectedItem().toString(), "Todos");
               }
           }
        }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox combo_curso;
    private javax.swing.JComboBox combo_nivel;
    private javax.swing.JComboBox combo_tipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
