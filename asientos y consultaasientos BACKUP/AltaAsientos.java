/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Dialogs.SeleccionarCuenta;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import static Pantallas.Frames.Principal.jDesktopPane1;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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


public class AltaAsientos extends javax.swing.JInternalFrame {
    
    @Override
    public void dispose() {
        int seleccion = JOptionPane.showOptionDialog(jframe, "Está a punto de salir. Los datos no guardados se perderán\n¿Desea Continuar?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Salir", "Cancelar"}, "Salir");
        if (seleccion == 0) {
            manager.removeKeyEventDispatcher(key);
            super.dispose();
        }
    }
    
    void Excel(JTable table, String path, String fecha, String hora, String num, String debe, String haber) throws FileNotFoundException, IOException {
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
        titulo_datos.createCell(0).setCellValue("Asiento Nº");//Write column name
        titulo_datos.createCell(1).setCellValue("Fecha");//Write column name
        titulo_datos.createCell(2).setCellValue("Hora");//Write column name
        titulo_datos.createCell(3).setCellValue("Total Debe");//Write column name
        titulo_datos.createCell(4).setCellValue("Total Haber");//Write column name
        titulo_datos.getCell(0).setCellStyle(my_style);
        titulo_datos.getCell(1).setCellStyle(my_style);
        titulo_datos.getCell(2).setCellStyle(my_style);
        titulo_datos.getCell(3).setCellStyle(my_style);
        titulo_datos.getCell(4).setCellStyle(my_style);
        
        Row datos = sheet.createRow(1); //Create row at line 2
        Double valor = Double.valueOf(num.replace(".", "").replace(",", "."));
        datos.createCell(0).setCellValue(valor);//Write column name
        datos.createCell(1).setCellValue(fecha);//Write column name
        datos.createCell(2).setCellValue(hora);//Write column name
        valor = Double.valueOf(debe.replace(".", "").replace(",", "."));
        datos.createCell(3).setCellValue(valor);//Write column name
        valor = Double.valueOf(haber.replace(".", "").replace(",", "."));
        datos.createCell(4).setCellValue(valor);//Write column name
        
        Row headerRow = sheet.createRow(3); //Create row at line 3
        for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
            headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
            headerRow.getCell(headings).setCellStyle(my_style);
        }

        for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
            for (int cols = 0; cols < table.getColumnCount(); cols++) { //For each table column
                if (model.getValueAt(rows, cols) != null) {
                    if (cols == 0 || cols == 3 || cols == 4) {
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
        
        wb.write(new FileOutputStream(path));//Save the file 
    }
    
    void totales() {
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        
        BigDecimal dif;
        
        //CALCULO TOTAL
        for (int a = 0; a < jTable1.getRowCount(); a++) {
            if ((jTable1.getValueAt(a, 3) != null || !jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) == null || jTable1.getValueAt(a, 4).toString().equals(""))) {
                doble[0] = doble[0].add(new BigDecimal(jTable1.getValueAt(a, 3).toString()));
            }
            if ((jTable1.getValueAt(a, 3) == null || jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) != null || !jTable1.getValueAt(a, 4).toString().equals(""))) {
                doble[1] = doble[1].add(new BigDecimal(jTable1.getValueAt(a, 4).toString()));
            }
        }
                
        if (doble[0].compareTo(doble[1]) == 1) {
            dif = doble[0].subtract(doble[1]);
        } else {
            dif = doble[1].subtract(doble[0]);
        }
        
        jLabel9.setText("T. DEBE: "+df.format(doble[0]));
        jLabel8.setText("T. HABER: "+df.format(doble[1]));
        
        jLabel10.setText("DIFERENCIA: " + df.format(dif));
    }
    
    JFrame jframe;
    
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    class Editor1 extends DefaultCellEditor {
        
        Editor1() {
            super(new JTextField()); //make the superclass based on a JTextField
            ((JTextField) getComponent()).setBorder(null);
            //getComponent().setBackground(Color.yellow);//make it yellow
        }
        // This is called when editing stops. We check everything is OK.
        // If it is, we return the base class method. If it is not, we
        // return false, and editing continues

        @Override
        public boolean stopCellEditing() {
            String value = ((JTextField) getComponent()).getText();
            if (!"".equals(value)) {
                Integer i;
                // is it a valid integer?
                try {
                    i = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    ((JTextField) getComponent()).setBackground(Color.pink);
                    SeleccionarCuenta dialog = new SeleccionarCuenta(jframe, true);
                    String num = dialog.showDialog();
                    if (num != null) {
                        ((JTextField) getComponent()).setText(num);
                        ((JTextField) getComponent()).setBackground(null);
                        jTable1.setRowSelectionInterval(jTable1.getSelectedRow(), jTable1.getSelectedRow());

                        int col = 1;

                        jTable1.changeSelection(row, col, false, false);
                        jTable1.editCellAt(jTable1.getSelectedRow(), col);
                        return true;
                    } else {
                        return false;
                    }
                }
                // is it in range?
                Clases.Cuentas cuenta = new Clases.Cuentas();
                Boolean encontro = cuenta.ComprobarExistente(value);

                if (encontro == false) {
                    ((JTextField) getComponent()).setBackground(Color.pink);
                    SeleccionarCuenta dialog = new SeleccionarCuenta(jframe, true);
                    String num = dialog.showDialog();
                    if (num != null) {
                        ((JTextField) getComponent()).setText(num);
                        ((JTextField) getComponent()).setBackground(null);
                        jTable1.setRowSelectionInterval(jTable1.getSelectedRow(), jTable1.getSelectedRow());

                        int col = 1;

                        jTable1.changeSelection(row, col, false, false);
                        jTable1.editCellAt(jTable1.getSelectedRow(), col);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    ((JTextField) getComponent()).setBackground(null);
                    
                    jTable1.setValueAt(cuenta.cuenta_encontrada,row,1);
                    return super.stopCellEditing();
                }
            } else {
                ((JTextField) getComponent()).setBackground(null);
                return super.stopCellEditing();
            }
        }
    }
    
    //TECLADO ACCESOS DIRECTOS
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (jframe.isActive()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    //System.out.println(e.getKeyCode());
                    if (e.getKeyCode() == 83 && e.isControlDown()) { //ESTO ES PARA GUARDAR
                        jTable1.getCellEditor(jTable1.getSelectedRow(), 3).stopCellEditing();
                        jTable1.getCellEditor(jTable1.getSelectedRow(), 4).stopCellEditing();
                        jButton3.doClick();
                    }

                    if (e.getKeyCode() == 107) { //PARA AGREGAR 1 REGISTRO A LA TABLA
                        jTable1.getCellEditor(jTable1.getSelectedRow(), 3).stopCellEditing();
                        jTable1.getCellEditor(jTable1.getSelectedRow(), 4).stopCellEditing();
                        jButton1.doClick();
                    }
                    
                    if (e.getKeyCode() == 127) { //PARA ELIMINAR UN REGISTRO DE LA TABLA
                        jTable1.getCellEditor(jTable1.getSelectedRow(), 3).stopCellEditing();
                        jTable1.getCellEditor(jTable1.getSelectedRow(), 4).stopCellEditing();
                        
                        jButton2.doClick();
                    }
                }
            }
            
            //EVITAR INGRESO
            
            if (e.getID() == KeyEvent.KEY_TYPED) {
                if (e.getKeyChar() == '+') {
                    e.consume();
                }
            }
            return false;
        }
    }
    
    void comprobacion_textos(KeyEvent e, String name) {
        if ("numeros".equals(name)) {
            if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
                e.consume();
            }
        } else if ("letras".equals(name)) {
            if (!((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z') || 
                    (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z') || 
                    (e.getKeyChar() >= 'á' && e.getKeyChar() <= 'ú') || 
                    (e.getKeyChar() >= 'Á' && e.getKeyChar() <= 'Ú') || 
                    (e.getKeyChar() == ' ' || e.getKeyChar() == 'ñ' || e.getKeyChar() == 'Ñ'))) {
                e.consume();
            }
        } else if ("double".equals(name)) {
            if (!((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || (e.getKeyChar() == ',') || (e.getKeyChar() == '.'))) {
                e.consume();
            } else {
                JTextField text = (JTextField) e.getSource();
                if (text.getText().contains(",") && (e.getKeyChar() == '.' || e.getKeyChar() == ',')){
                    e.consume();
                } else if (!text.getText().isEmpty() && text.getText().contains(",")){
                    
                    String adfds = text.getText();
                    
                    String[] split = adfds.split(",");
                    
                    if (split.length > 1) {
                        if (split[1].length() > 1) {
                            e.consume();
                        }
                    }
                }
                
                if (e.getKeyChar()=='.' && !e.isConsumed()) {
                    e.setKeyChar(',');
                }
            }
        }
        
        if (e.isConsumed()) {
            if (e.getKeyChar() != '\t' && e.getKeyChar() != '\b' && e.getKeyChar() != '\n') {
                rootPane.getTopLevelAncestor().getToolkit().beep();
            }
        }
        
        if (e.getKeyChar() == '\n') {
            JTextField text = (JTextField) e.getSource();
            text.transferFocus();
        }
    }
    
    public void ConvertirColumnasBigDecimal() {
        Object[][] datos = new Object[jTable1.getRowCount()][5];
        
            for (int a = 0; a < jTable1.getRowCount(); a++) {
                for (int b = 0; b < jTable1.getColumnCount(); b++) {
                    
                    if (b == 0) {
                        datos[a][b] = Integer.valueOf(jTable1.getValueAt(a, b).toString());
                    } else if (b == 1) {
                        datos[a][b] = jTable1.getValueAt(a, b).toString();
                    } else if (b == 2) {
                        datos[a][b] = jTable1.getValueAt(a, b).toString();
                    } else if (b == 3) {
                        if (jTable1.getValueAt(a, b) != null) {
                            datos[a][b] = new BigDecimal (jTable1.getValueAt(a, b).toString());
                        }
                    } else if (b == 4) {
                        if (jTable1.getValueAt(a, b) != null) {
                            datos[a][b] = new BigDecimal (jTable1.getValueAt(a, b).toString());
                        }
                    }
                }
            }
        
            Clases.Class_editar_tabla.main(jTable1, datos);
    }
    
    Integer row = null;
    
    /**
     * Creates new form NewJInternalFrame
     */
    public AltaAsientos() {
        initComponents();
        
        String vacio = "";
        
        Object[][] datos = new Object[1][5];
        datos[0][0] = null;
        datos[0][1] = null;
        datos[0][2] = null;
        datos[0][3] = null;
        datos[0][4] = null;
        
        Clases.Class_editar_tabla.main(jTable1, datos);
        
        Editor1 edit = new Editor1();
        TableColumn firstColumn = jTable1.getColumnModel().getColumn(0);
        firstColumn.setCellEditor(edit);
        
        JTextField decimales = new JTextField("double");
        
        decimales.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                comprobacion_textos(e, "double");
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                
            }
            
        });
        
        TableColumn columnaDebe = jTable1.getColumnModel().getColumn(3);
        columnaDebe.setCellEditor(new DefaultCellEditor(decimales));
        
        TableColumn columnaHaber = jTable1.getColumnModel().getColumn(4);
        columnaHaber.setCellEditor(new DefaultCellEditor(decimales));
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        
        ListSelectionModel cellSelectionModel = jTable1.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jTable1.getSelectedRow() != -1) {
                    if (row == null || row != jTable1.getSelectedRow()) {
                        row = jTable1.getSelectedRow();
                        String texto = "";
                        if (jTable1.getValueAt(row, 0) != "" && jTable1.getValueAt(row, 0) != null) {
                            Clases.Cuentas cuenta = new Clases.Cuentas();
                            String id = jTable1.getValueAt(row, 0).toString();
                            cuenta.ComprobarExistente(id);
                            texto = cuenta.cuenta_encontrada;
                        }
                        jTable1.setValueAt(texto,row,1);
                        //EVENTO ACCIONADO CUANDO SELECCIONO UN NUEVO REGISTRO
                    }
                }
            }
        });
        
        
        /*
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                TableCellListener tcl = (TableCellListener) e.getSource();
                System.out.println("Row   : " + tcl.getRow());
                System.out.println("Column: " + tcl.getColumn());
                System.out.println("Old   : " + tcl.getOldValue());
                System.out.println("New   : " + tcl.getNewValue());
                
                // PERIMITIR SOLO UNA COMA Y SI ESCRIBE "." PONER ","
                String valor;
                BigDecimal value;
                
                if (tcl.getColumn() == 3 && (jTable1.getValueAt(tcl.getRow(), 3) != "" && jTable1.getValueAt(tcl.getRow(), 3) != null)) {
                    value = new BigDecimal(tcl.getNewValue().toString());

                    //System.out.println(df.format(value) + "\n");
                    //System.out.println(value + "\n");
                    
                    jTable1.setValueAt(value, tcl.getRow(), tcl.getColumn());
                } else if (tcl.getColumn() == 4 && (jTable1.getValueAt(tcl.getRow(), 3) != "" && jTable1.getValueAt(tcl.getRow(), 4) != null)) {
                    value = new BigDecimal(tcl.getNewValue().toString());

                    jTable1.setValueAt(value, tcl.getRow(), tcl.getColumn());
                }
                
            }
        };
        
        TableCellListener tcl = new TableCellListener(jTable1, action);     */
        
        
        
        jTable1.getModel().addTableModelListener(
                new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent evt) {
                        /*          REVISAR PARA QUE EL EVENTO NO SEA INFINITO      */

                        /*
                         int colum = jTable1.getSelectedColumn();
                         int fila = jTable1.getSelectedRow();
                         BigDecimal value;
                         String valor;
                         if (colum == 3 && (jTable1.getValueAt(fila, 3) != "" && jTable1.getValueAt(fila, 3) != null)) {
                         System.out.println(jTable1.getValueAt(fila, 3).toString());
                         valor = jTable1.getValueAt(fila, 3).toString();
                            
                         if (valor.contains(".")) {
                         valor = valor.replace(".", "");
                         }
                         if (valor.contains(",")) {
                         valor = valor.replace(",", ".");
                         }
                            
                         value = new BigDecimal (valor);
                            
                         System.out.println(df.format(value));
                         jTable1.setValueAt(df.format(value), fila, colum);
                         } else if (colum == 4 && (jTable1.getValueAt(fila, colum) != "" && jTable1.getValueAt(fila, colum) != null)) {
                         value = new BigDecimal (jTable1.getValueAt(fila, 3).toString());
                            
                         System.out.println(df.format(value));
                         jTable1.setValueAt(df.format(value), fila, colum);
                         }
                        
                         */
                        //totales();
                    }
                });
        
        
        
        Clases.Asientos asiento = new Clases.Asientos();
        Integer num = asiento.IdActual(Principal.anio_usado);
        
        jTextField1.setText(num.toString());
        
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
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();

        setIconifiable(true);
        setTitle("Alta de Asientos");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "N CUENTA *", "CUENTA", "OBSERVACIONES *", "DEBE -*", "HABER -*"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("+"); // NOI18N
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(75);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(75);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setMinWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setMinWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        jLabel1.setText("Ingresa Asiento Nº :");

        jTextField1.setEnabled(false);

        jLabel8.setText("T. HABER: 0");

        jLabel9.setText("T. DEBE: 0");

        jButton1.setText("AGREGAR MOVIMIENTO [+]");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("ELIMINAR MOVIMIENTO [SUPR]");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("GUARDAR [CTRL + S]");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setText("DIFERENCIA: 0");

        jDateChooser1.setToolTipText("");
        jDateChooser1.setDateFormatString("dd-MM-yy");

        jLabel3.setText("[SI SE ENCUENTRA VACIO SE USA LA FECHA ACTUAL!!]");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
        //manager.removeKeyEventDispatcher(key);
    }//GEN-LAST:event_formInternalFrameClosed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        String mensaje = "";
        
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        Integer a = jTable1.getRowCount() - 1;
        Boolean vacio = false;

        if (vacio == false) {
            if ((jTable1.getValueAt(a, 3) == null || jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) != null || !jTable1.getValueAt(a, 4).toString().equals(""))) {
                //PUSO EN EL HABER
            } else if ((jTable1.getValueAt(a, 3) != null || !jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) == null || jTable1.getValueAt(a, 4).toString().equals(""))) {
                //PUSO EN EL DEBE
            } else {
                vacio = true;
                if ((jTable1.getValueAt(a, 3) == null || jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) == null || jTable1.getValueAt(a, 4).toString().equals(""))) {
                    mensaje = "Ingrese un monto en el debe o en el haber";
                } else {
                    mensaje = "El movimiento no puede tener el monto en el debe y en el haber simultaneamente";
                }
            }
        }

        totales();
        
        if (vacio == false) {
            String[] vector = {};
            modelo.addRow(vector);
            int reg = jTable1.getRowCount();
            jTable1.setRowSelectionInterval(reg - 1, reg - 1);

            int col = 0;

            jTable1.changeSelection(row, col, false, false);
            jTable1.editCellAt(reg - 1, col);
        } else {
            JOptionPane.showMessageDialog(jframe, mensaje, "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getRowCount() > 1) {
            jTable1.editCellAt(0, 0);

            if (jTable1.getSelectedRow() == -1) { //SI HAY REGISTRO SELECCIONADO
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.removeRow(jTable1.getRowCount() - 1);
            } else {
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.removeRow(jTable1.getSelectedRow());
            }

            //ME POSICIONO EN EL PRIMER ROW
            jTable1.setRowSelectionInterval(0, 0);
            int col = 0;
            jTable1.changeSelection(0, col, false, false);

            jTable1.editingCanceled(null);
            
            //
            totales();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //if (((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().isEmpty()) {
            //JOptionPane.showMessageDialog(jframe, "Elija una fecha", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        //} else {
        
            totales();
        
            Integer anio;
            if (jDateChooser1.getCalendar() != null) {
                anio = jDateChooser1.getCalendar().get(Calendar.YEAR);
            } else {
                anio = Integer.valueOf(Principal.anio_global);
            }
            
            if (Principal.anio_usado.equals(anio.toString()) || anio == 0) {

            Boolean vacio = false;
            BigDecimal debe = BigDecimal.ZERO;
            BigDecimal haber = BigDecimal.ZERO;
            String mensaje = "";
            
            for (int a = 0; a < jTable1.getRowCount(); a++) {
                if (vacio == false) {
                    //VERIFICAR PRINCIPIO DE PARTIDA DOBLE
                    if ((jTable1.getValueAt(a, 3) == null || jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) != null || !jTable1.getValueAt(a, 4).toString().equals(""))) {
                        //PUSO EN EL HABER
                        haber = haber.add(new BigDecimal(jTable1.getValueAt(a, 4).toString()));
                    } else if ((jTable1.getValueAt(a, 3) != null || !jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) == null || jTable1.getValueAt(a, 4).toString().equals(""))) {
                        //PUSO EN EL DEBE
                        debe = debe.add(new BigDecimal(jTable1.getValueAt(a, 3).toString()));
                    } else {
                        vacio = true;
                        if ((jTable1.getValueAt(a, 3) == null || jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) == null || jTable1.getValueAt(a, 4).toString().equals(""))) {
                            mensaje = "Ingrese un monto en el debe o en el haber";
                        } else {
                            mensaje = "El movimiento no puede tener el monto en el debe y en el haber simultaneamente";
                        }

                        if ((jTable1.getValueAt(a, 3) != null || !jTable1.getValueAt(a, 3).toString().equals("")) && (jTable1.getValueAt(a, 4) != null || !jTable1.getValueAt(a, 4).toString().equals(""))) {
                            debe = debe.add(new BigDecimal(jTable1.getValueAt(a, 3).toString()));
                            haber = haber.add(new BigDecimal(jTable1.getValueAt(a, 4).toString()));
                        }
                    }

                    if (jTable1.getValueAt(a, 0) == null) {
                        mensaje = "Debe Ingresar la Cuenta";
                    } else if (jTable1.getValueAt(a, 2) == null) {
                        mensaje = "Debe Ingresar la Observacion";
                    }
                }
            }

            if ("Debe Ingresar la Observacion".equals(mensaje) || "Debe Ingresar la Cuenta".equals(mensaje)) {
                vacio = true;
                mensaje = "La Cuenta o la Observacion no deben quedar vacios";
            } else {

                if (haber.compareTo(BigDecimal.ZERO) == 0 && debe.compareTo(BigDecimal.ZERO) == 0) {
                    vacio = true;
                    mensaje = "Los totales del debe y del haber tienen que ser mayores a 0";
                }

                if (haber.compareTo(debe) != 0) {
                    vacio = true;
                    mensaje = "El principio de partida doble no se cumple";
                }

            }

            if (!vacio) {
                BigDecimal debe_t = debe;
                BigDecimal haber_t = haber;

                DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                String hora = hourFormat.format(date);
                String fecha = null;

                if (!((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().isEmpty()) {
                    DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
                    fecha = dateFormat.format(jDateChooser1.getDate());
                }

                Clases.Asientos asiento = new Clases.Asientos();
                Integer num = asiento.IdActual(Principal.anio_usado);

                linneoadmin.LinneoAdmin.desactivarAutoCommit();

                asiento.InsertarAsiento(hora,fecha);
                for (int b = 0; b < jTable1.getRowCount(); b++) {
                    if ((jTable1.getValueAt(b, 3) == null || jTable1.getValueAt(b, 3).toString().equals("")) && (jTable1.getValueAt(b, 4) != null || !jTable1.getValueAt(b, 4).toString().equals(""))) {
                        //PUSO EN EL HABER
                        haber = new BigDecimal(jTable1.getValueAt(b, 4).toString());
                        String cuenta = jTable1.getValueAt(b, 0).toString();
                        String detalle = jTable1.getValueAt(b, 2).toString();
                        asiento.InsertarHaber(fecha, cuenta, detalle, haber.toString(), hora, num.toString());
                    } else if ((jTable1.getValueAt(b, 3) != null || !jTable1.getValueAt(b, 3).toString().equals("")) && (jTable1.getValueAt(b, 4) == null || jTable1.getValueAt(b, 4).toString().equals(""))) {
                        //PUSO EN EL DEBE
                        debe = new BigDecimal(jTable1.getValueAt(b, 3).toString());
                        String cuenta = jTable1.getValueAt(b, 0).toString();
                        String detalle = jTable1.getValueAt(b, 2).toString();
                        asiento.InsertarDebe(fecha, cuenta, detalle, debe.toString(), hora, num.toString());
                    }
                }
                linneoadmin.LinneoAdmin.commiteo();
                linneoadmin.LinneoAdmin.activarAutoCommit();
                
                /*----------------------------------*/
                // CONVIERTO LA TABLA PARA LOS REPORTES 
                
                ConvertirColumnasBigDecimal();

                /*---------------------------------*/
                int seleccion = JOptionPane.showOptionDialog(jframe, "Asiento Guardado\n¿Que desea hacer?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Imprimir", "Guardar como PDF", "Guardar como Excel", "No Imprimir ni Guardar como PDF"}, "Imprimir");

                DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
                JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
                        "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");

                if (fecha == null) {
                    fecha = "";
                }

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
                                params.put("debe", debe_t);
                                params.put("haber", haber_t);
                                params.put("n_asiento", num);
                                params.put("fecha", fecha);
                                params.put("hora", hora);
                                JRDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                                print = JasperFillManager.fillReport("Reportes/asiento.jasper", params, dataSource);
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
                    FileDialog fd = new FileDialog(new JDialog(), "Guardar Alta de Asiento", 1);
                    fd.setFile("Alta de Asiento");
                    fd.setVisible(true);

                    if (fd.getDirectory() != null && fd.getFile() != null) {
                        JasperPrint print;
                        try {
                            Map params = new HashMap();
                            params.put("debe", debe_t);
                            params.put("haber", haber_t);
                            params.put("n_asiento", num);
                            params.put("fecha", fecha);
                            params.put("hora", hora);
                            JRDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                            print = JasperFillManager.fillReport("Reportes/asiento.jasper", params, dataSource);

                            JRPdfExporter exporter = new JRPdfExporter();

                            exporter.setExporterInput(new SimpleExporterInput(print));
                            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fd.getDirectory() + fd.getFile() + ".pdf"));
                            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                            exporter.setConfiguration(configuration);

                            exporter.exportReport();

                            JOptionPane.showMessageDialog(jframe, "Guardado Correctamente", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);

                        } catch (JRException ex) {
                            Logger.getLogger(AltaAsientos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (seleccion == 2) {
                    FileDialog fd = new FileDialog(new JDialog(), "Guardar Alta de Asiento", 1);
                    fd.setFile("Alta de Asiento");
                    fd.setVisible(true);

                    if (fd.getDirectory() != null && fd.getFile() != null) {
                        try {
                            if (fecha.equals("")) {
                                GregorianCalendar calendario = new java.util.GregorianCalendar();
                                int dia, mes, año;
                                java.util.Date actual = new java.util.Date();
                                calendario.setTime(actual);
                                dia = calendario.get(Calendar.DAY_OF_MONTH);
                                mes = (calendario.get(Calendar.MONTH) + 1);
                                año = calendario.get(Calendar.YEAR);
                                fecha = String.format("%02d/%02d/%02d", dia, mes, año);
                            }

                            Excel(jTable1, fd.getDirectory() + fd.getFile() + ".xlsx", fecha, hora, num.toString(), df.format(debe_t), df.format(haber_t));
                            JOptionPane.showMessageDialog(jframe, "Guardado Correctamente", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(AltaAsientos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                /*---------------------------------*/
                manager.removeKeyEventDispatcher(key);
                super.dispose();

                AltaAsientos frame = new AltaAsientos();
                frame.setVisible(true);
                jDesktopPane1.add(frame);
                try {
                    frame.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(AltaAsientos.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(jframe, mensaje, "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(jframe, "La fecha no está dentro del ejercicio seleccionado", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        }

        //}
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
