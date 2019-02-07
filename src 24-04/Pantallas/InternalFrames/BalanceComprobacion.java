/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Clases.*;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
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
public class BalanceComprobacion extends javax.swing.JInternalFrame {

    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    void Excel(JTable table, String path, String periodo, String debe, String haber, String s_deudor, String s_acreedor) throws FileNotFoundException, IOException {
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
        titulo_datos.createCell(6).setCellValue("Periodo");//Write column name
        titulo_datos.createCell(2).setCellValue("T. Debe");//Write column name
        titulo_datos.createCell(3).setCellValue("T. Haber");//Write column name
        titulo_datos.createCell(4).setCellValue("S. Deudor");//Write column name
        titulo_datos.createCell(5).setCellValue("S. Acreedor");//Write column name
        titulo_datos.createCell(0).setCellValue("Fecha Reporte");//Write column name
        titulo_datos.createCell(1).setCellValue("Hora Reporte");//Write column name
        titulo_datos.getCell(0).setCellStyle(my_style);
        titulo_datos.getCell(1).setCellStyle(my_style);
        titulo_datos.getCell(2).setCellStyle(my_style);
        titulo_datos.getCell(3).setCellStyle(my_style);
        titulo_datos.getCell(4).setCellStyle(my_style);
        titulo_datos.getCell(5).setCellStyle(my_style);
        titulo_datos.getCell(6).setCellStyle(my_style);
        
        Row datos = sheet.createRow(1); //Create row at line 2
        datos.createCell(6).setCellValue(periodo);//Write column name
        Double valor = Double.valueOf(debe.replace(".", "").replace(",", "."));
        datos.createCell(2).setCellValue(valor);//Write column name
        valor = Double.valueOf(haber.replace(".", "").replace(",", "."));
        datos.createCell(3).setCellValue(valor);//Write column name
        valor = Double.valueOf(s_deudor.replace(".", "").replace(",", "."));
        datos.createCell(4).setCellValue(valor);//Write column name
        valor = Double.valueOf(s_acreedor.replace(".", "").replace(",", "."));
        datos.createCell(5).setCellValue(valor);//Write column name
        
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
        
        datos.createCell(0).setCellValue(date);//Write column name
        datos.createCell(1).setCellValue(hour);//Write column name
        
        Row headerRow = sheet.createRow(3); //Create row at line 3
        for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
            headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
            headerRow.getCell(headings).setCellStyle(my_style);
        }

        for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
            for (int cols = 0; cols < table.getColumnCount(); cols++) { //For each table column
                if (model.getValueAt(rows, cols) != null) {
                    if (cols == 0 || cols == 2 || cols == 3 || cols == 4 || cols == 5) {
                        valor = Double.valueOf(model.getValueAt(rows, cols).toString().replace(".", "").replace(",", "."));
                        row.createCell(cols).setCellValue(valor); //Write value
                    } else {
                        row.createCell(cols).setCellValue(model.getValueAt(rows, cols).toString()); //Write value
                    }
                }
            }

            //Set the row to the next one in the sequence 
            row = sheet.createRow((rows + 5));
        }
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        
        wb.write(new FileOutputStream(path));//Save the file 
    }
    
    @Override
    public void dispose() {
        //int seleccion = JOptionPane.showOptionDialog(jframe, "Está a punto de salir. Los datos no guardados se perderán\n¿Desea Continuar?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Salir", "Cancelar"}, "Salir");
        //if (seleccion == 0) {
            manager.removeKeyEventDispatcher(key);
            super.dispose();
        //}
    }
    
    JFrame jframe;
    
    //TECLADO ACCESOS DIRECTOS
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (jframe.isActive()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 112) {
                        jButton1.doClick();
                    }
                }
            }
            return false;
        }
    }
    
    /**
     * Creates new form BalanceComprobacion
     */
    
    void cargar() {
        if (jComboBox1.getSelectedItem().toString().equals("Histórico")) {
            if (jComboBox3.getSelectedItem().toString().equals("Si")) {
                String fecha = Principal.anio_usado + "-" + (mes_hasta.getSelectedIndex() + 1) + "-" + dia_hasta.getSelectedItem().toString();

                Clases.Mayor balance = new Clases.Mayor();
                BigDecimal[] doble = balance.balance_comprobacion("Histórico", fecha, null);

                Class_editar_tabla.main(jTable1, balance.datos);

                totales(doble);
            } else {
                String fecha = Principal.anio_usado + "-" + (mes_hasta.getSelectedIndex() + 1) + "-" + dia_hasta.getSelectedItem().toString();
                String fecha_desde = Principal.anio_usado + "-" + (mes_desde.getSelectedIndex() + 1) + "-" + dia_desde.getSelectedItem().toString();

                Clases.Mayor balance = new Clases.Mayor();
                BigDecimal[] doble = balance.balance_comprobacion("Histórico", fecha, fecha_desde);

                Class_editar_tabla.main(jTable1, balance.datos);

                totales(doble);
            }
        } else {
            Integer fecha_int = jComboBox2.getSelectedIndex() + 1;

            Clases.Mayor balance = new Clases.Mayor();
            BigDecimal[] doble = balance.balance_comprobacion("Mensual", fecha_int.toString(), null);

            Class_editar_tabla.main(jTable1, balance.datos);

            totales(doble);
        }
    }
    
    void totales(BigDecimal[] doble) {
        BigDecimal[] dif = new BigDecimal[2];
        
        if (doble[0].compareTo(doble[1]) == 1) {
            dif[0] = doble[0].subtract(doble[1]);
        } else {
            dif[0] = doble[1].subtract(doble[0]);
        }
        
        jLabel8.setText("TOTAL DEBE: " + df.format(doble[0]));
        jLabel10.setText("TOTAL HABER: " + df.format(doble[1]));
        
        if (doble[2].compareTo(doble[3]) == 1) {
            dif[1] = doble[2].subtract(doble[3]);
        } else {
            dif[1] = doble[3].subtract(doble[2]);
        }
        
        jLabel9.setText("TOTAL S. DEUDOR: " + df.format(doble[2]));
        jLabel11.setText("TOTAL S. ACREEDOR: " + df.format(doble[3]));
    }
    
    public BalanceComprobacion() {
        initComponents();
        
        Calendar cal = Calendar.getInstance();
        dia_desde.setSelectedItem("1");
        mes_desde.setSelectedIndex(0);

        dia_hasta.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
        mes_hasta.setSelectedIndex(cal.get(Calendar.MONTH));
        
        jComboBox2.setVisible(false);
        
        jLabel3.setVisible(false);
        dia_desde.setVisible(false);
        mes_desde.setVisible(false);
        
        cargar();
        
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
        jTable1 = new javax.swing.JTable(){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                //  Alternate row color

                if (!isRowSelected(row))
                c.setBackground(row % 2 == 0 ? linneoadmin.LinneoAdmin.color1 : linneoadmin.LinneoAdmin.color2);

                return c;
            }
        };
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        mes_hasta = new javax.swing.JComboBox();
        dia_hasta = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dia_desde = new javax.swing.JComboBox();
        mes_desde = new javax.swing.JComboBox();

        setIconifiable(true);
        setTitle("Balance de Comprobación de Sumas y Saldos");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N CUENTA", "CUENTA", "DEBE", "HABER", "S. DEUDOR", "S. ACREEDOR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(75);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(1).setMinWidth(300);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(400);
        }

        jLabel8.setText("0");

        jLabel10.setText("0");

        jLabel11.setText("0");

        jLabel9.setText("0");

        jButton1.setText("REPORTE [F1]");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Histórico", "Mensual" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel1.setText("¿Desde el Comienzo?");

        mes_hasta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        mes_hasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mes_hastaActionPerformed(evt);
            }
        });

        dia_hasta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        dia_hasta.setSelectedItem("31");

        jLabel2.setText("Hasta");

        jLabel3.setText("Desde");

        dia_desde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        mes_desde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        mes_desde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mes_desdeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel10)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dia_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mes_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dia_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mes_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mes_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dia_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mes_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dia_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jButton1)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String desde;
        String hasta = null;
        
        if (jComboBox1.getSelectedItem().toString().equals("Histórico")) {
            if (jComboBox3.getSelectedItem().toString().equals("Si")) {
                desde = "01-01-" + Principal.anio_usado;
            } else {
                String dia_desde_string = dia_desde.getSelectedItem().toString();
                if (dia_desde_string.length() == 1) {
                    dia_desde_string = "0" + dia_desde_string;
                }
                Integer mes_desde_int = mes_desde.getSelectedIndex() + 1;
                String mes_desde_string = mes_desde_int.toString();
                if (mes_desde_int < 10) {
                    mes_desde_string = "0" + mes_desde_string;
                }
                desde = dia_desde_string + "-" + mes_desde_string + "-" + Principal.anio_usado;
            }
            String dia_hasta_string = dia_hasta.getSelectedItem().toString();
            if (dia_hasta_string.length() == 1) {
                dia_hasta_string = "0" + dia_hasta_string;
            }
            Integer mes_hasta_int = mes_hasta.getSelectedIndex() + 1;
            String mes_hasta_string = mes_hasta_int.toString();
            if (mes_hasta_int < 10) {
                mes_hasta_string = "0" + mes_hasta_string;
            }
            hasta = dia_hasta_string + "-" + mes_hasta_string + "-" + Principal.anio_usado;
        } else {
            Integer fecha_int = jComboBox2.getSelectedIndex() + 1;
            String fecha = fecha_int.toString();
            if (fecha_int < 10) {
                fecha = "0" + fecha;
            }
            desde = fecha + "-"+Principal.anio_usado;
        }
        
        String debe = jLabel8.getText().replace("TOTAL DEBE: ", "");
        String haber = jLabel10.getText().replace("TOTAL HABER: ", "");
        String deudor = jLabel9.getText().replace("TOTAL S. DEUDOR: ", "");
        String acreedor = jLabel11.getText().replace("TOTAL S. ACREEDOR: ", "");
        
        DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
            JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
                    "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
        
        int seleccion = JOptionPane.showOptionDialog(jframe, "¿Que desea hacer?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Imprimir", "Guardar como PDF", "Guardar como Excel", "Cancelar"}, "Imprimir");
        
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
                        params.put("debe", debe);
                        params.put("haber", haber);
                        params.put("deudor", deudor);
                        params.put("acreedor", acreedor);
                        if (hasta != null) {
                            params.put("periodo", desde + " al " + hasta);
                        } else {
                            params.put("periodo", desde);
                        }
                        JRDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                        print = JasperFillManager.fillReport("Reportes/balance.jasper", params, dataSource);
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
            FileDialog fd = new FileDialog(new JDialog(), "Guardar Balance de Comprobación", 1);
            fd.setFile("Balance Comprobación");
            fd.setVisible(true);

            if (fd.getDirectory() != null && fd.getFile() != null) {
                JasperPrint print;
                try {
                    Map params = new HashMap();
                    params.put("debe", debe);
                    params.put("haber", haber);
                    params.put("deudor", deudor);
                    params.put("acreedor", acreedor);
                    if (hasta != null) {
                        params.put("periodo", desde + " al " + hasta);
                    } else {
                        params.put("periodo", desde);
                    }
                    JRDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                    print = JasperFillManager.fillReport("Reportes/balance.jasper", params, dataSource);

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
        } else if (seleccion == 2) {
            FileDialog fd = new FileDialog(new JDialog(), "Guardar Balance de Comprobación", 1);
            fd.setFile("Balance Comprobación");
            fd.setVisible(true);

            if (fd.getDirectory() != null && fd.getFile() != null) {
                try {
                    String periodo;
                    if (hasta != null) {
                        periodo = desde + " al " + hasta;
                    } else {
                        periodo = desde;
                    }
                    
                    Excel(jTable1, fd.getDirectory() + fd.getFile() + ".xlsx", periodo, jLabel8.getText().replace("TOTAL DEBE: ", ""), jLabel10.getText().replace("TOTAL HABER: ", ""), jLabel9.getText().replace("TOTAL S. DEUDOR: ", ""), jLabel11.getText().replace("TOTAL S. ACREEDOR: ", ""));
                    JOptionPane.showMessageDialog(jframe, "Guardado Correctamente", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(AltaAsientos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox1.getSelectedItem().equals("Histórico")) {
            jComboBox2.setVisible(false);
            jLabel2.setVisible(true);
            dia_hasta.setVisible(true);
            mes_hasta.setVisible(true);
            jComboBox3.setSelectedItem("Si");
            jLabel1.setVisible(true);
            jComboBox3.setVisible(true);
        } else {
            jComboBox2.setVisible(true);
            jLabel3.setVisible(false);
            dia_desde.setVisible(false);
            mes_desde.setVisible(false);
            jLabel2.setVisible(false);
            dia_hasta.setVisible(false);
            mes_hasta.setVisible(false);
            jLabel1.setVisible(false);
            jComboBox3.setVisible(false);
        }
        cargar();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        cargar();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        if (jComboBox3.getSelectedItem().equals("Si")) {
            jLabel3.setVisible(false);
            dia_desde.setVisible(false);
            mes_desde.setVisible(false);
        } else {
            jLabel3.setVisible(true);
            dia_desde.setVisible(true);
            mes_desde.setVisible(true);
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void mes_hastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mes_hastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mes_hastaActionPerformed

    private void mes_desdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mes_desdeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mes_desdeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox dia_desde;
    private javax.swing.JComboBox dia_hasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox mes_desde;
    private javax.swing.JComboBox mes_hasta;
    // End of variables declaration//GEN-END:variables
}
