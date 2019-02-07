/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Clases.Cuentas;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import static Pantallas.Frames.Principal.jDesktopPane1;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jason
 */
public class MayorUnaCuenta extends javax.swing.JInternalFrame {
    
    void Excel(JTable table, String path, String periodo, String tipo, String cuenta) throws FileNotFoundException, IOException {
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
        titulo_datos.createCell(0).setCellValue("Cuenta");//Write column name
        titulo_datos.createCell(1).setCellValue("Periodo");//Write column name
        titulo_datos.createCell(2).setCellValue("Tipo");//Write column name
        titulo_datos.createCell(3).setCellValue("Fecha");//Write column name
        titulo_datos.createCell(4).setCellValue("Hora");//Write column name
        titulo_datos.createCell(5).setCellValue("T. Debe");//Write column name
        titulo_datos.createCell(6).setCellValue("T. Haber");//Write column name
        titulo_datos.getCell(0).setCellStyle(my_style);
        titulo_datos.getCell(1).setCellStyle(my_style);
        titulo_datos.getCell(2).setCellStyle(my_style);
        titulo_datos.getCell(3).setCellStyle(my_style);
        titulo_datos.getCell(4).setCellStyle(my_style);
        titulo_datos.getCell(5).setCellStyle(my_style);
        titulo_datos.getCell(6).setCellStyle(my_style);
        
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
        datos.createCell(0).setCellValue(cuenta);//Write column name
        datos.createCell(1).setCellValue(periodo);//Write column name
        datos.createCell(2).setCellValue(tipo);//Write column name
        datos.createCell(3).setCellValue(date);//Write column name
        datos.createCell(4).setCellValue(hour);//Write column name
        
        Row headerRow = sheet.createRow(3); //Create row at line 3
        for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
            headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
            headerRow.getCell(headings).setCellStyle(my_style);
        }

        /*int min_row = 0;
        int max_row = 0;*/
        
        Double debe = 0.0;
        Double haber = 0.0;
        
        for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
            for (int cols = 0; cols < table.getColumnCount(); cols++) { //For each table column
                if (model.getValueAt(rows, cols) != null) {
                    if (cols == 1 || cols == 3 || cols == 4 || cols == 5 || cols == 6) {
                        Double valor = Double.valueOf(model.getValueAt(rows, cols).toString().replace(".", "").replace(",", "."));
                        row.createCell(cols).setCellValue(valor); //Write value
                        
                        if (cols == 3) {
                            debe = debe + valor;
                        } else if (cols == 4) {
                            haber = haber + valor;
                        }
                    } else {
                        row.createCell(cols).setCellValue(model.getValueAt(rows, cols).toString()); //Write value
                    }
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
        
        //datos.createCell(5).setCellFormula("SUMA(F"+min_row+":F"+max_row+")");
        //datos.createCell(6).setCellFormula("SUMA(G"+min_row+":G"+max_row+")");
        datos.createCell(5).setCellValue(debe);
        datos.createCell(6).setCellValue(haber);
        
        datos.getCell(5).setCellType(0);
        datos.getCell(6).setCellType(0);
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        
        wb.write(new FileOutputStream(path));//Save the file 
    }
    
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    @Override
    public void dispose() {
        manager.removeKeyEventDispatcher(key);
        super.dispose();
    }
    
    JFrame jframe;
    
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (jframe.isActive()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 112) {
                        jButton2.doClick();
                    }
                    
                    if (e.getKeyCode() == 113) {
                        jButton1.doClick();
                    }
                    
                    if (e.getKeyCode() == 114) {
                        jButton3.doClick();
                    }
                }
            }
            return false;
        }
    }

    void totales(BigDecimal[] doble) {
        BigDecimal dif;
        
        if (doble[0].compareTo(doble[1]) == 1) {
            dif = doble[0].subtract(doble[1]);
        } else {
            dif = doble[1].subtract(doble[0]);
        }
        
        jLabel8.setText("T. DEBE: "+df.format(doble[0]));
        jLabel10.setText("T. HABER: "+df.format(doble[1]));
        
        jLabel9.setText("DIFERENCIA: " + df.format(dif));
    }
    
    void Mayor() {
        Boolean sin_arrastre;
        
        if ("SIN ARRASTRE".equals(jComboBox1.getSelectedItem().toString())) {
            sin_arrastre = true;
        } else {
            sin_arrastre = false;
        }
        
        String desde;
        
        if (jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty()) {
            desde = "";
        } else {
            desde = jTextField3.getText() + "-" + jTextField2.getText() + "-" + jTextField1.getText();
        }
        
        String hasta;
        
        if (jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jTextField6.getText().isEmpty()) {
            hasta = "";
        } else {
            hasta = jTextField6.getText() + "-" + jTextField5.getText() + "-" + jTextField4.getText();
        }
        
        Clases.Mayor mayor = new Clases.Mayor();
        BigDecimal[] dou = mayor.Cargar(jTextField.getText(), sin_arrastre, desde, hasta);
        
        Clases.Class_editar_tabla.main(jTable1, mayor.datos);
        
        totales(dou);
    }
    
    /**
     * Creates new form MayorUnaCuenta
     */
    public MayorUnaCuenta() {
        initComponents();
        
        jTextField3.setText(Principal.anio_usado);
        jTextField6.setText(Principal.anio_usado);
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                //  Color row based on a cell value

                if (!isRowSelected(row)) {
                    c.setBackground(getBackground());
                    int modelRow = convertRowIndexToModel(row);
                    String temp = (String)getModel().getValueAt(modelRow, 7).toString();
                    Color color;
                    if (temp.equals("0")) {
                        color = linneoadmin.LinneoAdmin.color2;
                    } else {
                        color = linneoadmin.LinneoAdmin.color1;
                    }
                    c.setBackground(color);
                }

                return c;
            }
        };
        jLabel1 = new javax.swing.JLabel();
        jTextField = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setIconifiable(true);
        setTitle("Mayor de una Cuenta");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FECHA", "ID ASIENTO", "OBSERVACIONES", "DEBE", "HABER", "S. DEUDOR", "S. ACREEDOR", "COLOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(75);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(1).setMinWidth(75);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(3).setMinWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(4).setMinWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(5).setMinWidth(100);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(6).setMinWidth(100);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(7).setMinWidth(0);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        jLabel1.setText("CUENTA:");

        jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldKeyReleased(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIN ARRASTRE", "CON ARRASTRE" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jTextField3.setEnabled(false);
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel3.setText("/");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel4.setText("FECHA HASTA:");

        jLabel2.setText("/");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel5.setText("/");

        jLabel6.setText("FECHA DESDE:");

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel7.setText("/");

        jTextField6.setEnabled(false);
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jLabel9.setText("DIFERENCIA: 0");

        jLabel8.setText("T. DEBE: 0");

        jLabel10.setText("T. HABER: 0");

        jButton1.setText("LIBRO DIARIO [F2]");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("ASIENTO [F1]");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("REPORTE [F3]");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(175, 175, 175)
                        .addComponent(jLabel8)
                        .addGap(140, 140, 140)
                        .addComponent(jLabel10)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldKeyReleased
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jTextFieldKeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        Mayor();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() != -1) {
            dispose();
            
            String fecha = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            Diario frame = new Diario(fecha);
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(jframe, "Debe seleccionar un movimiento", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() != -1) {
            dispose();
            
            String id = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
            ConsultaAsientos frame = new ConsultaAsientos(id);
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(jframe, "Debe seleccionar un movimiento", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
        JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
            "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");

        int seleccion = JOptionPane.showOptionDialog(jframe, "¿Que desea hacer?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Imprimir", "Guardar como PDF", "Guardar como Excel", "Cancelar"}, "Imprimir");

        String periodo;

        if (!"".equals(jTextField1.getText()) && !"".equals(jTextField2.getText()) && !"".equals(jTextField3.getText()) && !"".equals(jTextField4.getText()) && !"".equals(jTextField5.getText()) && !"".equals(jTextField6.getText())) {
            String desde = jTextField1.getText() + "/" + jTextField2.getText() + "/" + jTextField3.getText();
            String hasta = jTextField4.getText() + "/" + jTextField5.getText() + "/" + jTextField6.getText();
            periodo = " (" + desde + " - " + hasta + ")";
        } else {
            periodo = "";
        }
        
        Cuentas c = new Cuentas();
        c.ComprobarExistente(jTextField.getText());
        String cuenta =  c.cuenta_encontrada + " - " + "Nº " + jTextField.getText();

        if (seleccion == 0) {
            /* Array para almacenar las impresoras del sistema */
            PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);
            if (printService.length > 0) {//si existen impresoras
                //se elige la impresora
                PrintService impresora = (PrintService) JOptionPane.showInputDialog(null, "Eliga impresora:",
                    "Imprimir Reporte", JOptionPane.QUESTION_MESSAGE, null, printService, printService[0]);
                if (impresora != null) //Si se selecciono una impresora
                {
                    try {
                        JasperPrint print;
                        //se carga el reporte
                        Map params = new HashMap();
                        params.put("tipo", jComboBox1.getSelectedItem().toString());
                        params.put("periodo", periodo);
                        params.put("cuenta", cuenta);

                        JRDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                        print = JasperFillManager.fillReport("Reportes/mayor.jasper", params, dataSource);
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
                JOptionPane.showMessageDialog(jframe, "No se encontró ninguna Impresora", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (seleccion == 1) {
            FileDialog fd = new FileDialog(new JDialog(), "Guardar Libro Mayor", 1);
            fd.setFile("Libro Mayor");
            fd.setVisible(true);

            if (fd.getDirectory() != null && fd.getFile() != null) {
                JasperPrint print;
                try {
                    Map params = new HashMap();
                    params.put("tipo", jComboBox1.getSelectedItem().toString());
                    params.put("periodo", periodo);
                    params.put("cuenta", cuenta);

                    JRDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                    print = JasperFillManager.fillReport("Reportes/mayor.jasper", params, dataSource);

                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setExporterInput(new SimpleExporterInput(print));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fd.getDirectory() + fd.getFile() + ".pdf"));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    exporter.setConfiguration(configuration);

                    exporter.exportReport();

                    JOptionPane.showMessageDialog(jframe, "Guardado Correctamente", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);

                } catch (JRException ex) {
                    Logger.getLogger(BalanceComprobacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  else if (seleccion == 2) {
            FileDialog fd = new FileDialog(new JDialog(), "Guardar Libro Mayor", 1);
            fd.setFile("Libro Mayor");
            fd.setVisible(true);

            if (periodo.equals("")) {
                periodo = "Ejercicio Actual";
            }

            if (fd.getDirectory() != null && fd.getFile() != null) {
                try {
                    Excel(jTable1, fd.getDirectory() + fd.getFile() + ".xlsx", periodo, jComboBox1.getSelectedItem().toString(), cuenta);
                    JOptionPane.showMessageDialog(jframe, "Guardado Correctamente", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(Diario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
