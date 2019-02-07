/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Dialogs.AsientosAltaMovimientos;
import Dialogs.AsientosBaja;
import Dialogs.SeleccionarCuenta;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

/**
 *
 * @author jason
 */
public class ConsultaAsientos extends javax.swing.JInternalFrame {
    
    Object[][] datos_alta;
    Object[][] datos_baja;
    
    void eliminarAltas(String dato) {
        if (datos_alta != null) {
            int cont = 0;

            boolean tengo_q_eliminar = false;

            for (int a = 0; a < datos_alta.length; a++) {
                if (datos_alta[a][0].toString().equals(dato)) {
                    tengo_q_eliminar = true;
                    break;
                }
            }

            Object[][] temp;
            if (!tengo_q_eliminar) {
                temp = new Object[datos_alta.length][7];
            } else {
                temp = new Object[datos_alta.length - 1][7];
            }

            for (int a = 0; a < datos_alta.length; a++) {
                if (!datos_alta[a][0].toString().equals(dato)) {
                    temp[cont] = datos_alta[a];
                    cont++;
                }
            }

            datos_alta = temp;
        }
    }
    
    void agregarAltas (Object[] dato) {
        if (datos_alta != null) {
            Object[][] temp = new Object[datos_alta.length + 1][7];
            for (int a = 0; a < datos_alta.length; a++) {
                temp[a] = datos_alta[a];
            }
            temp[datos_alta.length] = dato;
            datos_alta = temp;
        } else {
            datos_alta = new Object[1][7];
            datos_alta[0] = dato;
        }
    }
    
    /*-----------------------------------*/
    
    void eliminarBajas(String dato) {
        if (datos_baja != null) {
            int cont = 0;

            boolean tengo_q_eliminar = false;

            for (int a = 0; a < datos_baja.length; a++) {
                if (datos_baja[a][0].toString().equals(dato)) {
                    tengo_q_eliminar = true;
                    break;
                }
            }

            Object[][] temp;
            if (!tengo_q_eliminar) {
                temp = new Object[datos_baja.length][7];
            } else {
                temp = new Object[datos_baja.length - 1][7];
            }

            for (int a = 0; a < datos_baja.length; a++) {
                if (!datos_baja[a][0].toString().equals(dato)) {
                    temp[cont] = datos_baja[a];
                    cont++;
                }
            }

            datos_baja = temp;
        }
    }
    
    void agregarBajas (Object[] dato) {
        if (datos_baja != null) {
            Object[][] temp = new Object[datos_baja.length + 1][7];
            for (int a = 0; a < datos_baja.length; a++) {
                temp[a] = datos_baja[a];
            }
            temp[datos_baja.length] = dato;
            datos_baja = temp;
        } else {
            datos_baja = new Object[1][7];
            datos_baja[0] = dato;
        }
    }
    
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
        for (int headings = 2; headings < model.getColumnCount(); headings++) { //For each column
            headerRow.createCell(headings - 2).setCellValue(model.getColumnName(headings));//Write column name
            headerRow.getCell(headings - 2).setCellStyle(my_style);
        }
        
        String formateo = null;

        for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
            for (int cols = 2; cols < table.getColumnCount(); cols++) { //For each table column
                if (model.getValueAt(rows, cols) != null) {
                    if (cols == 5 || cols == 6) {
                        // primero formatear a bigdecimal para despues arreglar las comas
                        formateo = df.format(model.getValueAt(rows, cols));
                        
                        valor = Double.valueOf(formateo.replace(".", "").replace(",", "."));
                        row.createCell(cols - 2).setCellValue(valor); //Write value
                    } else {
                        row.createCell(cols - 2).setCellValue(model.getValueAt(rows, cols).toString()); //Write value
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
    
    JFrame jframe;
    
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    //TECLADO ACCESOS DIRECTOS
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (jframe.isActive()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    //System.out.println(e.getKeyCode());
                    if (e.getKeyCode() == 83 && e.isControlDown() && jToggleButton1.getText().equals("GUARDAR ASIENTO [CTRL + S]")) { //ESTO ES PARA GUARDAR
                        jToggleButton1.doClick();
                    }

                    if (e.getKeyCode() == 107) { //PARA AGREGAR 1 REGISTRO A LA TABLA
                        jButton1.doClick();
                    }
                    
                    if (e.getKeyCode() == 109) { //PARA ELIMINAR UN REGISTRO DE LA TABLA
                        jButton2.doClick();
                    }
                    
                    if (e.getKeyCode() == 112) { //F1
                        jButton4.doClick();
                    }
                    
                    if (e.getKeyCode() == 127) { //SUPR
                        jButton3.doClick();
                    }
                }
            }
            
            //EVITAR INGRESO
            
            if (e.getID() == KeyEvent.KEY_TYPED) {
                if (e.getKeyChar() == '+' || e.getKeyChar() == '-') {
                    e.consume();
                }
            }
            return false;
        }
    }
    
    Object[][] datos_temp;
    
    BigDecimal[] consulta_tabla() {
        BigDecimal[] doble = {BigDecimal.ZERO,BigDecimal.ZERO};
        
        BigDecimal dif;
        
        boolean error = false;
        
        //CALCULO TOTAL
        for (int a = 0; a < jTable1.getRowCount(); a++) {
            if (jTable1.getValueAt(a, 5) != null) {
                doble[0] = doble[0].add(new BigDecimal(jTable1.getValueAt(a, 5).toString()));
            }
            if (jTable1.getValueAt(a, 6) != null) {
                doble[1] = doble[1].add(new BigDecimal(jTable1.getValueAt(a, 6).toString()));
            }
            if ((jTable1.getValueAt(a, 5) != null && jTable1.getValueAt(a, 6) != null) || 
                    (jTable1.getValueAt(a, 5) == null && jTable1.getValueAt(a, 6) == null) || 
                    "".equals(jTable1.getValueAt(a, 2).toString())) {
                error = true;
            }
        }
        
        if (doble[0].compareTo(doble[1]) == 1) {
            dif = doble[0].subtract(doble[1]);
        } else {
            dif = doble[1].subtract(doble[0]);
        }
        
        //jLabel6.setText("T. DEBE: "+new DecimalFormat("#.##").format(doble[0]));
        jLabel6.setText("T. DEBE: "+df.format(doble[0]));
        jLabel5.setText("T. HABER: "+df.format(doble[1]));
        
        jLabel7.setText("DIFERENCIA: " + df.format(dif));
        
        if (dif.compareTo(BigDecimal.ZERO) == 0 && !error) {
            jToggleButton1.setEnabled(true);
        } else {
            jToggleButton1.setEnabled(false);
        }
        
        return doble;
    }
    
    private boolean consulta() {
        boolean bool;
        
        Clases.Asientos asientos = new Clases.Asientos();
        BigDecimal[] dou = asientos.CargarID(jTextField1.getText(), "A");
        
        Clases.Class_editar_tabla.main(jTable1, asientos.datos);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        
        String fecha;
        if (asientos.fecha_asiento == null) {
            fecha = "";
        } else {
            fecha = dateFormat.format(asientos.fecha_asiento);
        }
        jTextField2.setText(fecha);
        
        agregar_eventos_tabla();
        
        jLabel6.setText("T. DEBE: "+df.format(dou[0]));
        jLabel5.setText("T. HABER: "+df.format(dou[0]));
        
        BigDecimal dif;
        
        if (dou[0].compareTo(dou[1]) == 1) {
            dif = dou[0].subtract(dou[1]);
        } else {
            dif = dou[1].subtract(dou[0]);
        }
        
        if (dif.compareTo(BigDecimal.ZERO) == 0) {
            bool = true;
        } else {
            bool = false;
        }
        
        datos_temp = asientos.datos;
        
        jLabel7.setText("DIFERENCIA: " + df.format(dif));
        return bool;
    }

    /**
     * Creates new form NewJInternalFrame
     */
    
    void agregar_eventos_tabla() {
        ListSelectionModel cellSelectionModel = jTable1.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jTable1.getSelectedRow() != -1) {
                    if (row == null || row != jTable1.getSelectedRow()) {
                        row = jTable1.getSelectedRow();
                        String texto = "";
                        if (jTable1.getValueAt(row, 2) != "" && jTable1.getValueAt(row, 2) != null) {
                            Clases.Cuentas cuenta = new Clases.Cuentas();
                            String id = jTable1.getValueAt(row, 2).toString();
                            cuenta.ComprobarExistente(id);
                            texto = cuenta.cuenta_encontrada;
                        }
                        //jLabel8.setText("Cuenta: " + texto);
                        jTable1.setValueAt(texto, row, 3);
                        //EVENTO ACCIONADO CUANDO SELECCIONO UN NUEVO REGISTRO
                    }
                }
            }
        });
        
        jTable1.getModel().addTableModelListener(
                new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent evt) {
                        // NO ACEPTAR LA COMA!
                        
                        
                        consulta_tabla();
                    }
                });
        
        Editor1 edit = new Editor1();
        TableColumn firstColumn = jTable1.getColumnModel().getColumn(2);
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
        
        TableColumn columnaDebe = jTable1.getColumnModel().getColumn(5);
        columnaDebe.setCellEditor(new DefaultCellEditor(decimales));
        
        TableColumn columnaHaber = jTable1.getColumnModel().getColumn(6);
        columnaHaber.setCellEditor(new DefaultCellEditor(decimales));
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

                        int col = 4;

                        jTable1.changeSelection(row, col, false, false);
                        jTable1.editCellAt(jTable1.getSelectedRow(), col);
                        jTable1.requestFocus();
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

                        int col = 4;

                        jTable1.changeSelection(row, col, false, false);
                        jTable1.editCellAt(jTable1.getSelectedRow(), col);
                        jTable1.requestFocus();
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    ((JTextField) getComponent()).setBackground(null);
                    
                    //jLabel8.setText("Cuenta: " + cuenta.cuenta_encontrada);
                    jTable1.setValueAt(cuenta.cuenta_encontrada, row, 3);
                    return super.stopCellEditing();
                }
            } else {
                ((JTextField) getComponent()).setBackground(null);
                jTable1.setValueAt("", row, 3);
                return super.stopCellEditing();
            }
        }
    }
    
    void comunes() {
        agregar_eventos_tabla();
        
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        
        Integer num = Clases.Asientos.IdActual(Principal.anio_usado) - 1;
        jLabel3.setText("ULTIMO ASIENTO GENERADO: " + num);
    }
    
    public ConsultaAsientos() {
        initComponents();
        
        // USUARIOS
        if (!(Pantallas.Frames.Principal.tipo).equals("Contador")) {
            jToggleButton1.setVisible(false);
            jButton1.setVisible(false);
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jButton5.setVisible(false);
            jButton6.setVisible(false);
            jButton7.setVisible(false);
        }
        
        comunes();
    }
    
    Integer row = null;
    
    public ConsultaAsientos(String id) {
        initComponents();
        
        jTextField1.setText(id);
        
        consulta();
        
        comunes();
        
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
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setIconifiable(true);
        setTitle("Consulta de Asientos");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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

            },
            new String [] {
                "N MOV.", "HORA", "N CUENTA *", "CUENTA", "OBSERVACIONES *", "DEBE -*", "HABER -*"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(60);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(1).setMinWidth(70);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(2).setMinWidth(75);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(75);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setMinWidth(100);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(6).setMinWidth(100);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jLabel1.setText("Asiento Nº :");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel2.setText("Fecha del Asiento :");

        jTextField2.setEnabled(false);

        jLabel5.setText("T HABER: 0");

        jLabel6.setText("T DEBE: 0");

        jLabel7.setText("DIFERENCIA: 0");

        jToggleButton1.setText("MODIFICAR ASIENTO");
        jToggleButton1.setEnabled(false);
        jToggleButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton1ItemStateChanged(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("NUEVO MOVIMIENTO [+]");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("ELIMINAR MOVIMIENTO [-]");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("ELIMINAR ASIENTO COMPLETO!! [SUPR]");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("EXPORTAR ASIENTO [F1]");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("CANCELAR MODIFICACIÓN");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("ASIENTOS DADOS DE BAJA");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("ALTA DE MOVIMIENTOS");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel3.setText("dasad");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jToggleButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        consulta();
        if (jTable1.getRowCount() > 0) {
            jToggleButton1.setEnabled(true);
            jButton3.setEnabled(true);
            jButton4.setEnabled(true);
        } else {
            jToggleButton1.setEnabled(false);
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        //if(evt.getStateChange()==ItemEvent.SELECTED){
        if ("MODIFICAR ASIENTO".equals(jToggleButton1.getText())) {
            //codigo cuando voy a modificar un asiento
            jTextField1.setEnabled(false);
            jTable1.setEnabled(true);
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
            jButton7.setEnabled(true);
            jToggleButton1.setText("GUARDAR ASIENTO [CTRL + S]");
            boolean bool = consulta();
            if (!bool) {
                jToggleButton1.setEnabled(false);
            }
            jButton4.setEnabled(false);
            jButton5.setEnabled(true);
        } else {
            String[][] datos = new String[jTable1.getRowCount()][7];
            for (int a = 0; a < jTable1.getRowCount(); a++) {
                for (int b = 0; b < 7; b++) {
                    if (jTable1.getValueAt(a, b) != null) {
                        datos[a][b] = jTable1.getValueAt(a, b).toString();
                    }
                }
            }
            
            //codigo cuando voy a guardar un asiento
            Clases.Asientos asientos = new Clases.Asientos();
            linneoadmin.LinneoAdmin.desactivarAutoCommit();
            asientos.GuardarAsiento(datos_baja, datos, jTextField1.getText(), datos_alta);
            datos_alta = null;
            datos_baja = null;
            linneoadmin.LinneoAdmin.commiteo();
            linneoadmin.LinneoAdmin.activarAutoCommit();
            consulta();
            
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jButton7.setEnabled(false);
            jTable1.setEnabled(false);
            jTextField1.setEnabled(true);
            jToggleButton1.setText("MODIFICAR ASIENTO");
            jButton4.setEnabled(true);
            jButton5.setEnabled(false);
            consulta_tabla();
            jTextField1.requestFocus();
            
            JOptionPane.showMessageDialog(jframe, "Asiento Guardado", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String mensaje = "";

        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        Integer a = jTable1.getRowCount() - 1;
        Boolean vacio = false;

        if (vacio == false) {
            if (jTable1.getValueAt(a, 5) == null && jTable1.getValueAt(a, 6) != null) {
                //PUSO EN EL HABER
            } else if (jTable1.getValueAt(a, 5) != null && jTable1.getValueAt(a, 6) == null) {
                //PUSO EN EL DEBE
            } else {
                vacio = true;
                if (jTable1.getValueAt(a, 5) == null && jTable1.getValueAt(a, 6) == null) {
                    mensaje = "Ingrese un monto en el debe o en el haber";
                } else {
                    mensaje = "El movimiento no puede tener el monto en el debe y en el haber simultaneamente";
                }
            }
        }

        if (vacio == false) {
            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hora = hourFormat.format(date);
                
            String[] vector = {null,hora,null,null,null,null,null};
            modelo.addRow(vector);
            int reg = jTable1.getRowCount();
            jTable1.setRowSelectionInterval(reg - 1, reg - 1);

            int col = 2;

            jTable1.changeSelection(jTable1.getSelectedRow(), col, false, false);
            jTable1.editCellAt(reg - 1, col);
            jTable1.requestFocus();
        } else {
            JOptionPane.showMessageDialog(jframe, mensaje, "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //Cuando doy de baja pasan 2 cosas
        //agrego un registro en bajas Y elimino un registro en altas
        if (jTable1.getRowCount() > 1) {
            jTable1.editCellAt(0, 0);

            if (jTable1.getSelectedRow() == -1) { //SI HAY REGISTRO SELECCIONADO
                JOptionPane.showMessageDialog(jframe, "Seleccione el movimiento a eliminar", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
            } else {
                if (jTable1.getValueAt(jTable1.getSelectedRow(), 0) != null) {
                    
                    String mov = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                    Object[] reg = new Object[7];
                    reg[0] = jTable1.getValueAt(jTable1.getSelectedRow(), 0);
                    reg[1] = jTable1.getValueAt(jTable1.getSelectedRow(), 1);
                    reg[2] = jTable1.getValueAt(jTable1.getSelectedRow(), 2);
                    reg[3] = jTable1.getValueAt(jTable1.getSelectedRow(), 3);
                    reg[4] = jTable1.getValueAt(jTable1.getSelectedRow(), 4);
                    reg[5] = jTable1.getValueAt(jTable1.getSelectedRow(), 5);
                    reg[6] = jTable1.getValueAt(jTable1.getSelectedRow(), 6);
                    eliminarAltas(mov);
                    agregarBajas(reg);
                }
                
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.removeRow(jTable1.getSelectedRow());
            }

            //ME POSICIONO EN EL PRIMER ROW
            jTable1.setRowSelectionInterval(0, 0);
            int col = 2;
            jTable1.changeSelection(0, col, false, false);

            jTable1.editingCanceled(null);
            jTable1.requestFocus();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Clases.Asientos asientos = new Clases.Asientos();
        asientos.Eliminar(jTextField1.getText());
        
        jTextField1.setText("");
        
        consulta();

        jLabel7.setText("DIFERENCIA: 0");
        jLabel6.setText("T DEBE: 0");
        jLabel5.setText("T HABER: 0");
        
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton7.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
        jButton5.setEnabled(false);
        jTable1.setEnabled(false);
        jTextField1.setEnabled(true);
        jToggleButton1.setSelected(false);
        jToggleButton1.setText("MODIFICAR ASIENTO");
        jToggleButton1.setEnabled(false);
        
        jTextField1.requestFocus();
        
        JOptionPane.showMessageDialog(jframe, "Asiento Eliminado", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        BigDecimal[] big = consulta_tabla();
        String fecha = jTextField2.getText();
        String hora = jTable1.getValueAt(0, 1).toString();
        String num = jTextField1.getText();
        BigDecimal debe_t = big[0];
        BigDecimal haber_t = big[1];

        int seleccion = JOptionPane.showOptionDialog(jframe, "¿Que desea hacer?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Imprimir", "Guardar como PDF", "Guardar como Excel", "Cancelar"}, "Imprimir");

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
            FileDialog fd = new FileDialog(new JDialog(), "Guardar Asiento", 1);
            fd.setFile("Asiento");
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
            FileDialog fd = new FileDialog(new JDialog(), "Guardar Asiento", 1);
            fd.setFile("Asiento");
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
                    Logger.getLogger(ConsultaAsientos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if ("MODIFICAR ASIENTO".equals(jToggleButton1.getText())) {
            //codigo cuando voy a modificar un asiento
            jTextField1.setEnabled(false);
            jTable1.setEnabled(true);
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
            jButton7.setEnabled(true);
            jToggleButton1.setText("GUARDAR ASIENTO [CTRL + S]");
            boolean bool = consulta();
            if (!bool) {
                jToggleButton1.setEnabled(false);
            }
            jButton4.setEnabled(false);
            jButton5.setEnabled(true);
        } else {
            String[][] datos = new String[jTable1.getRowCount()][7];
            for (int a = 0; a < jTable1.getRowCount(); a++) {
                for (int b = 0; b < 7; b++) {
                    if (jTable1.getValueAt(a, b) != null) {
                        datos[a][b] = jTable1.getValueAt(a, b).toString();
                    }
                }
            }
            
            consulta();
            
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jButton7.setEnabled(false);
            jTable1.setEnabled(false);
            jTextField1.setEnabled(true);
            jToggleButton1.setText("MODIFICAR ASIENTO");
            jButton4.setEnabled(true);
            jButton5.setEnabled(false);
            consulta_tabla();
            jTextField1.requestFocus();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        AsientosBaja dialog = new AsientosBaja(jframe, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
            String id = jTextField1.getText();
            
            Object[][] datos_tabla = new Object[jTable1.getRowCount()][7];
            
            for (int b = 0; b < jTable1.getRowCount(); b++) {
                for (int c = 0; c < jTable1.getColumnCount(); c++) {
                    datos_tabla[b][c] = jTable1.getValueAt(b, c);
                }
            }
            
            AsientosAltaMovimientos dialog = new AsientosAltaMovimientos(jframe, true, id, datos_tabla, datos_baja);
            datos_temp = dialog.showDialog();
            
            //agrego registros en altas
            //elimino registros en bajas
            
            if (datos_temp != null) {
                DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
                for (int a = 0; a < datos_temp.length; a++) {
                    modelo.addRow(datos_temp[a]);
                    agregarAltas(datos_temp[a]);
                    eliminarBajas(datos_temp[a][0].toString());
                }
            }
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
