/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Clases.Class_editar_tabla;
import Clases.Variables;
import Dialogs.OperacionTipoGenerar;
import Dialogs.SeleccionarAlquilerEnCuotas;
import Dialogs.SeleccionarAlumnosEnCuotas;
import Dialogs.SeleccionarOtrosEnCuotas;
import Dialogs.SeleccionarResponsableEnCuotas;
import Dialogs.SeleccionarSociosEnCuotas;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.df;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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



public class Cuotas extends javax.swing.JInternalFrame {

    JFrame jframe;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    Clases.Cuotas cuotas = new Clases.Cuotas();
    Clases.TiposPagos tipo_pagos = new Clases.TiposPagos();
    
    TableModel tabla_institucional;
    
    TableModel tabla_alquileres;
    
    TableModel tabla_otros;
    
    BigDecimal monto_anual;
    
    @Override
    public void dispose() {
        manager.removeKeyEventDispatcher(key);
        super.dispose(); 
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
                        jButton2.doClick();
                    }
                    
                    if (e.getKeyCode() == 114) {
                        jButton3.doClick();
                    }
                    
                    if (e.getKeyCode() == 115) {
                        jButton4.doClick();
                    }
                }
            }
            return false;
        }
    }
    
    void CargarCuotas(String alumno) {
        monto_anual = cuotas.CargarCuotasAlumnos(alumno);
        Class_editar_tabla.main(jTable1, cuotas.datos);
        EditorCheck edit = new EditorCheck();
        TableColumn Column = jTable1.getColumnModel().getColumn(5);
        Column.setCellEditor(edit);
        Column.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocused, int row, int col) {
                JCheckBox rendererComponent = new JCheckBox();
                Boolean val = false;
                if (value != null) {
                    val = (Boolean) value;
                }
                if (val) {
                    rendererComponent.setSelected(true);
                } else {
                    rendererComponent.setSelected(false);
                }
                
                if ((boolean)table.getValueAt(row, 10) == true) {
                    rendererComponent.setSelected(false);
                }
                
                return rendererComponent;
            }
        });
        
        jTextField1.setText("");
        combo_tipo_pago.setSelectedItem("Efectivo");
        jTextField2.setText("");
        jTextField2.setVisible(false);
        
        jLabel6.setText("ADEUDADO ANUAL: "+df.format(monto_anual));
        
        jTextField1.requestFocus();
        
        // PONER LOS TAMAÑOS DE LA TABLA
        
    }
    
    void CargarTablaOperaciones (String alumno, String sector) {
        
        cuotas.CargarTablaOperaciones(alumno, sector);
        if (cuotas.datos!=null){
            Class_editar_tabla.main(jTable2, cuotas.datos);
        }
    }
    
    Object[] datos_alquileres;
    
    void CargarCuotasAlquileres(String alquiler) {
        datos_alquileres = Clases.Cuotas.CargarAlquileresCuotas(alquiler);
        monto_anual = new BigDecimal(datos_alquileres[1].toString());
        Class_editar_tabla.main(jTable1, (Object[][])datos_alquileres[0]);
        jTextField1.setText("");
        combo_tipo_pago.setSelectedItem("Efectivo");
        jTextField2.setText("");
        jTextField2.setVisible(false);
        
        jLabel6.setText("ADEUDADO ANUAL: "+df.format(monto_anual));
        
        jTextField1.requestFocus();
    }
    
    void CargarCuotasSocios(String socio) {
        monto_anual = cuotas.CargarCuotasSocio(socio);
        Clases.Class_editar_tabla.main(jTable1, cuotas.datos);
        jTextField1.setText("");
        combo_tipo_pago.setSelectedItem("Efectivo");
        jTextField2.setText("");
        jTextField2.setVisible(false);
        
        jLabel6.setText("ADEUDADO ANUAL: "+df.format(monto_anual));
        
        jTextField1.requestFocus();
    }
    
    void cargar_tipo_pagos(){
        combo_tipo_pago.removeAllItems();

        tipo_pagos.CargarCombos();
        
        for (int a = 0; a < tipo_pagos.datos2.length; a++) {
            combo_tipo_pago.addItem(tipo_pagos.datos2[a]);
        }
    }
    
    class EditorCheck extends DefaultCellEditor {
        
        EditorCheck() {
            super(new JCheckBox());
            
            //JCheckBox check = (JCheckBox) getComponent();
            addCellEditorListener(new CellEditorListener()
            {
                @Override
                public void editingCanceled(ChangeEvent e) {
                    
                }
                @Override
                public void editingStopped(ChangeEvent e)
                {
                    JCheckBox checkBox = (JCheckBox)((DefaultCellEditor)e.getSource()).getComponent();
                    //System.out.println("isSelected = " + checkBox.isSelected());
                    
                    DefaultCellEditor objeto = (DefaultCellEditor)e.getSource();
                    
                    //Boolean check = checkBox.isSelected();
                    Boolean tabla_beca = (boolean) jTable1.getValueAt(jTable1.getSelectedRow(), 10);
                    //Boolean check = (boolean) jTable1.getValueAt(jTable1.getSelectedRow(), 5);
                    
                    if (tabla_beca) {
                        Object col3 = jTable1.getValueAt(jTable1.getSelectedRow(), 2);
                        if (col3.toString().contains("Deuda")) {
                            JOptionPane.showMessageDialog(jframe, "No puede agregar descuento a una deuda de años anteriores", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                        } else if (col3.toString().contains("Inscripción")) {
                            JOptionPane.showMessageDialog(jframe, "No puede agregar descuento a una Inscripción", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(jframe, "No puede agregar descuento a una cuota becada", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                        }
                        
                        jTable1.setValueAt(false, jTable1.getSelectedRow(), 5);
                    } else {
                        
                        Object col6 = jTable1.getValueAt(jTable1.getSelectedRow(), 6);
                        Object col3 = jTable1.getValueAt(jTable1.getSelectedRow(), 3);

                        /*if (checkBox.isSelected())
                         {
                         //JOptionPane.showMessageDialog(jframe, "true", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                         } else
                         {*/
                        //pasar la var a monto_a_pagar, pasar el monto real
                        jTable1.setValueAt(col6, jTable1.getSelectedRow(), 3);
                        //pasar monto_a_pagar a var, pasar el monto_descuento
                        jTable1.setValueAt(col3, jTable1.getSelectedRow(), 6);

                        //Calculo la deuda
                        BigDecimal a_pagar = new BigDecimal(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString());
                        BigDecimal pagado = new BigDecimal(jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString());
                        BigDecimal deuda = a_pagar.subtract(pagado);

                        jTable1.setValueAt(deuda, jTable1.getSelectedRow(), 7);

                        monto_anual = BigDecimal.ZERO;

                        for (int a = 0; a < jTable1.getRowCount(); a++) {
                            monto_anual = monto_anual.add(new BigDecimal(jTable1.getValueAt(a, 7).toString()));
                        }

                        jLabel6.setText("ADEUDADO ANUAL: " + df.format(monto_anual));

                        //JOptionPane.showMessageDialog(jframe, "false", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                        //}
                        /*if (col6.equals(col3)) {
                            JOptionPane.showMessageDialog(jframe, "No puede Aplicarle ni sacarle el Descuento porque la cuota ya fue pagada", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                        }*/
                    }
                }
            });
        }
    }
    
    String pk_alquiler_resp;
    
    String ObtenerSectorActivo() {
        String sector;
        
        if (combo_institucional.isSelected()) {
            sector = "Institucional";
        } else if (combo_alquileres.isSelected()) {
            sector = "Alquileres";
        } else if (combo_socios.isSelected()) {
            sector = "Socios";
        } else if (combo_cementerio.isSelected()) {
            sector = "Cementerio";
        } else {
            //otros
            sector = "Otros";
        }
        
        return sector;
    }
    
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    Integer row = null;
    public Cuotas() {
        initComponents();
        
        jLabel4.setText("Cursando:");
        text_apenom.setEditable(false);
        text_info.setEditable(false);
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        cargar_tipo_pagos();
        
        if (Principal.tipo.equals("Cajero")) {
            jButton1.setEnabled(true);
        } else {
            jButton1.setEnabled(false);
        }
        
        tabla_institucional = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AÑO", "MES", "SERVICIO", "MONTO A PAGAR", "MONTO PAGADO", "¿COBRO CON DESCUENTO?", "VAR", "DEUDA", "COD_CARRERA", "COD_GRADO", "BECADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
        tabla_alquileres = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AÑO", "MES", "MONTO A PAGAR", "MONTO PAGADO", "VAR", "DEUDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }; 
        
        tabla_otros = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TIPO", "DETALLE", "MONTO A PAGAR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
            
        
        if (combo_institucional.isFocusOwner()) {
            text_cod.requestFocus();
        }
        
        jTable3.setTableHeader(null);
        
        ListSelectionModel cellSelectionModel = jTable3.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jTable3.getSelectedRow() != -1) {
                    if (row == null || row != jTable3.getSelectedRow()) {
                        //AL SELECCONAR OTRO ROW
                    }
                }
            }
        });
        
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        jButton4.setVisible(false);
    }
    
    public Cuotas(String pk_persona, String sectores) {
        initComponents();
        
        text_apenom.setEditable(false);
        text_info.setEditable(false);
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        cargar_tipo_pagos();
        
        if (Principal.tipo.equals("Cajero")) {
            jButton1.setEnabled(true);
        } else {
            jButton1.setEnabled(false);
        }
        
        tabla_institucional = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AÑO", "MES", "SERVICIO", "MONTO A PAGAR", "MONTO PAGADO", "¿COBRO CON DESCUENTO?", "VAR", "DEUDA", "COD_CARRERA", "COD_GRADO", "BECADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
        tabla_alquileres = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AÑO", "MES", "MONTO A PAGAR", "MONTO PAGADO", "VAR", "DEUDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }; 
        
        tabla_otros = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TIPO", "DETALLE", "MONTO A PAGAR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
         
        
        if (sectores.equals("Institucional")) {
            String[] alumno_encontrado = Clases.Alumnos.MostrarInscripcion(pk_persona);

            if (alumno_encontrado != null) {
                text_cod.setText(pk_persona);
                text_apenom.setText(alumno_encontrado[0].toUpperCase());
                text_info.setText(alumno_encontrado[1]);
            }else {
                String[] alumno = Clases.Cuotas.EncontroDeudorCuotas(pk_persona);
                text_cod.setText(pk_persona);
                text_apenom.setText(alumno[1].toUpperCase());
                text_info.setText(alumno[2]);                        
            }
            
            CargarCuotas(pk_persona);
        } else if (sectores.equals("Alquileres")) {
            pk_alquiler_resp = pk_persona;
            
            String[] alquiler_encontrado = Clases.Cuotas.EncontroAlquileresCuotas(pk_persona);

            text_cod.setText(pk_persona);
            text_apenom.setText(alquiler_encontrado[0].toUpperCase());
            text_info.setText(alquiler_encontrado[1]);
            
            CargarCuotasAlquileres(pk_persona);
        } else if (sectores.equals("Socios")) {
            combo_socios.doClick();
            
            String[] socio_encontrado = Clases.Cuotas.EncontroSociosCuotas(pk_persona);

            text_cod.setText(pk_persona);
            text_apenom.setText(socio_encontrado[1].toUpperCase());
            text_info.setText(socio_encontrado[2]);
            
            CargarCuotasSocios(pk_persona);
        } else if (sectores.equals("Cementerio")) {
            

            // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
            CargarTablaOperaciones(pk_persona,"Cementerio");
        } else if (sectores.equals("Otros")) {
            

        }        
        
        // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
        CargarTablaOperaciones(pk_persona,sectores);
        
        jTable3.setTableHeader(null);
        
        ListSelectionModel cellSelectionModel = jTable3.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jTable3.getSelectedRow() != -1) {
                    if (row == null || row != jTable3.getSelectedRow()) {
                        //AL SELECCONAR OTRO ROW
                    }
                }
            }
        });
        
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        jButton4.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        combo_institucional = new javax.swing.JRadioButton();
        combo_alquileres = new javax.swing.JRadioButton();
        combo_socios = new javax.swing.JRadioButton();
        combo_cementerio = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        text_apenom = new javax.swing.JTextField();
        text_cod = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        text_info = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        combo_tipo_pago = new javax.swing.JComboBox();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        combo_otros = new javax.swing.JRadioButton();
        radio_entrada = new javax.swing.JRadioButton();
        radio_salida = new javax.swing.JRadioButton();
        radio_default = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setIconifiable(true);
        setTitle("Nueva Operación");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Sectores");

        buttonGroup1.add(combo_institucional);
        combo_institucional.setSelected(true);
        combo_institucional.setText("Institucional");
        combo_institucional.setRequestFocusEnabled(false);
        combo_institucional.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_institucionalItemStateChanged(evt);
            }
        });
        combo_institucional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_institucionalActionPerformed(evt);
            }
        });

        buttonGroup1.add(combo_alquileres);
        combo_alquileres.setText("Alquileres");
        combo_alquileres.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_alquileresItemStateChanged(evt);
            }
        });
        combo_alquileres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_alquileresActionPerformed(evt);
            }
        });

        buttonGroup1.add(combo_socios);
        combo_socios.setText("Socios");
        combo_socios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_sociosItemStateChanged(evt);
            }
        });
        combo_socios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_sociosActionPerformed(evt);
            }
        });

        buttonGroup1.add(combo_cementerio);
        combo_cementerio.setText("Cementerio");
        combo_cementerio.setEnabled(false);
        combo_cementerio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_cementerioItemStateChanged(evt);
            }
        });
        combo_cementerio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_cementerioActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Código:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Apellido y Nombre:");

        text_cod.setName("enteros"); // NOI18N
        text_cod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_codActionPerformed(evt);
            }
        });
        text_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_codKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_codKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                text_codKeyTyped(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("jLabel4");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AÑO", "MES", "SERVICIO", "MONTO A PAGAR", "MONTO PAGADO", "¿COBRO CON DESCUENTO?", "VAR", "DEUDA", "COD_CARRERA", "COD_GRADO", "BECADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("tabla1"); // NOI18N
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(70);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(1).setMinWidth(80);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(80);
            jTable1.getColumnModel().getColumn(3).setMinWidth(120);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(4).setMinWidth(120);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(5).setMinWidth(180);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(180);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(180);
            jTable1.getColumnModel().getColumn(6).setMinWidth(0);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(7).setMinWidth(100);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMinWidth(0);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(9).setMinWidth(0);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(10).setMinWidth(0);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        jButton1.setText("COBRAR [F1]");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("MONTO RECIBIDO");

        jTextField1.setName("double"); // NOI18N

        jLabel6.setText("ADEUDADO ANUAL:");

        combo_tipo_pago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Efectivo" }));
        combo_tipo_pago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tipo_pagoItemStateChanged(evt);
            }
        });
        combo_tipo_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipo_pagoActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("PRESIONAR ENTER");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID OPERACION", "FECHA", "HORA", "RECIBO", "DETALLE", "MONTO", "MONTO T.", "MOTIVO BAJA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(0);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable2.getColumnModel().getColumn(1).setMinWidth(75);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTable2.getColumnModel().getColumn(1).setMaxWidth(75);
            jTable2.getColumnModel().getColumn(2).setMinWidth(75);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTable2.getColumnModel().getColumn(2).setMaxWidth(75);
            jTable2.getColumnModel().getColumn(3).setMinWidth(120);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTable2.getColumnModel().getColumn(3).setMaxWidth(120);
            jTable2.getColumnModel().getColumn(5).setMinWidth(75);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTable2.getColumnModel().getColumn(5).setMaxWidth(75);
            jTable2.getColumnModel().getColumn(6).setMinWidth(75);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTable2.getColumnModel().getColumn(6).setMaxWidth(75);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(150);
        }

        buttonGroup1.add(combo_otros);
        combo_otros.setText("Otros");
        combo_otros.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_otrosItemStateChanged(evt);
            }
        });
        combo_otros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_otrosActionPerformed(evt);
            }
        });

        buttonGroup2.add(radio_entrada);
        radio_entrada.setText("Operación de Entrada de Dinero");
        radio_entrada.setEnabled(false);
        radio_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_entradaActionPerformed(evt);
            }
        });

        buttonGroup2.add(radio_salida);
        radio_salida.setText("Operación de Salida de Dinero");
        radio_salida.setEnabled(false);

        buttonGroup2.add(radio_default);
        radio_default.setSelected(true);
        radio_default.setText("Cobros Cuotas e Inscripciones");
        radio_default.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_defaultActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo Operación", "Cuenta", "Requiere Detalle"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(1).setMinWidth(0);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(0);
            jTable3.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable3.getColumnModel().getColumn(2).setMinWidth(0);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTable3.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tipos de Operaciones");

        jButton2.setText("AGREGAR [F2]");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("MODIFICAR [F3]");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("ELIMINAR [F4]");
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
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combo_tipo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(text_apenom, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(text_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7))
                            .addComponent(text_info))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(radio_entrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(radio_salida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(radio_default, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(combo_institucional)
                        .addGap(100, 100, 100)
                        .addComponent(combo_alquileres)
                        .addGap(100, 100, 100)
                        .addComponent(combo_socios)
                        .addGap(100, 100, 100)
                        .addComponent(combo_cementerio)
                        .addGap(100, 100, 100)
                        .addComponent(combo_otros)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_institucional)
                    .addComponent(combo_alquileres)
                    .addComponent(combo_socios)
                    .addComponent(combo_cementerio)
                    .addComponent(combo_otros))
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(radio_default)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_apenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(radio_entrada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(radio_salida)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(combo_tipo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_codKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
            if (combo_institucional.isSelected()) {
                SeleccionarAlumnosEnCuotas dialog = new SeleccionarAlumnosEnCuotas(jframe, true);
                String[] alumno_encontrado = dialog.showDialog();

                if (alumno_encontrado != null) {
                    text_cod.setText(alumno_encontrado[0]);
                    text_apenom.setText(alumno_encontrado[1].toUpperCase());
                    
                    if(alumno_encontrado[2].equals("F1 - Ver Inscriptos")){
                        // TRAER SU CURSO
                        String[] alumno = Clases.Cuotas.EncontroDeudorCuotas(text_cod.getText());

                        text_info.setText(alumno[2]);
                        
                        CargarCuotas(text_cod.getText());
                    } else {
                        // TRAER SU CURSO
                        String[] alumno = Clases.Cuotas.EncontroAlumnoCuotas(text_cod.getText());

                        text_info.setText(alumno[2]);
                        
                        CargarCuotas(text_cod.getText());
                    }
                    
                    // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                    
                    CargarTablaOperaciones(text_cod.getText(),"Institucional");
                } 
            }
            
            if (combo_alquileres.isSelected()) {
                // BUSCAR EL LOTE POR EL NUMERO
                SeleccionarAlquilerEnCuotas dialog = new SeleccionarAlquilerEnCuotas(jframe, true);
                String[] alumno_encontrado = dialog.showDialog();
                
                if (alumno_encontrado != null) {
                    text_cod.setText("");
                    text_info.setText(alumno_encontrado[2]);
                    text_apenom.setText(alumno_encontrado[1].toUpperCase());
                    
                    pk_alquiler_resp = alumno_encontrado[0];

                    CargarCuotasAlquileres(text_info.getText());

                    // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                    int rc = model.getRowCount();
                    for (int i = 0; i < rc; i++) {
                        model.removeRow(0);
                    }

                    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                    // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                    CargarTablaOperaciones(pk_alquiler_resp, "Alquileres");
                }
            }
            
            if (combo_socios.isSelected()) {
                SeleccionarSociosEnCuotas dialog = new SeleccionarSociosEnCuotas(jframe, true);
                String[] socio_encontrado = dialog.showDialog();
                
                if (socio_encontrado != null) {
                    
                    text_cod.setText(socio_encontrado[0]);
                    text_apenom.setText(socio_encontrado[1]);
                    text_info.setText(socio_encontrado[2]);
                    
                    CargarCuotasSocios(text_cod.getText());
                 
                    
                    // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                    
                    CargarTablaOperaciones(text_cod.getText(),"Socios");
                }
            }
            
            if (combo_cementerio.isSelected()) {
                SeleccionarResponsableEnCuotas dialog = new SeleccionarResponsableEnCuotas(jframe, true);
                String[] espacio_encontrado = dialog.showDialog();

                if (espacio_encontrado != null) {
                    text_cod.setText(espacio_encontrado[0]);

                    text_apenom.setText(espacio_encontrado[1].toUpperCase());
                    text_info.setText(espacio_encontrado[2]);
                }
            }
            
            if (combo_otros.isSelected()) {
               SeleccionarOtrosEnCuotas dialog = new SeleccionarOtrosEnCuotas(jframe, true);
               String[] persona = dialog.showDialog();
               
               if (persona != null) {
                    text_cod.setText(persona[0]);

                    text_apenom.setText(persona[1].toUpperCase());
                    
                    
                    // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                    
                    CargarTablaOperaciones(text_cod.getText(),"Otros");
                }
            }
        }
    }//GEN-LAST:event_text_codKeyPressed

    private void combo_institucionalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_institucionalItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_institucionalItemStateChanged

    private void combo_alquileresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_alquileresItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_alquileresItemStateChanged

    private void combo_sociosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_sociosItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_sociosItemStateChanged

    private void combo_cementerioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_cementerioItemStateChanged
        // TODO add your handling code here:
        if(combo_cementerio.isSelected()){
            text_info.setVisible(true);
            jLabel4.setVisible(true);
            jLabel4.setText("Espacios: ");
            
            text_cod.setText("");
            text_apenom.setText("");
            text_info.setText("");
        }
    }//GEN-LAST:event_combo_cementerioItemStateChanged

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jTextField1.getText().isEmpty() && jTextField1.isEnabled()) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Inserte un monto para continuar.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
        } else {
            BigDecimal monto_recibido = BigDecimal.ZERO;
            if (jTextField1.isEnabled()) {
                monto_recibido = new BigDecimal(jTextField1.getText().replace(",", "."));
            }

            if (text_apenom.getText().isEmpty()) {
                JOptionPane.showMessageDialog(jframe, "Seleccione a una persona a cobrar.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            } else if (jTextField2.isVisible() && jTextField2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(jframe, "El detalle de pago está vacio.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            } else if (monto_recibido.compareTo(monto_anual) == 1) {
                JOptionPane.showMessageDialog(jframe, "El monto ingresado es mayor a la deuda total.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            } else if (combo_otros.isSelected() && jTable1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(jframe, "Ingrese por lo menos una operacion.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            }else {
                linneoadmin.LinneoAdmin.desactivarAutoCommit();

                String hora = Principal.hora_actual;
                String fecha = Principal.fecha_para_bd;
                
                String info;
                
                String sector;
                //GENERO OPERACIONES
                if (combo_institucional.isSelected()) {
                    sector = "Institucional";
                    info = text_info.getText();
                    Clases.Operaciones.GenerarOperacion(text_cod.getText(), combo_tipo_pago.getSelectedItem().toString(), hora, fecha, jTextField2.getText(),sector);
                } else if (combo_alquileres.isSelected()) {
                    sector = "Alquileres";
                    info = "";
                    Clases.Operaciones.GenerarOperacion(pk_alquiler_resp, combo_tipo_pago.getSelectedItem().toString(), hora, fecha, jTextField2.getText(),sector);
                } else if (combo_socios.isSelected()) {
                    sector = "Socios";
                    Clases.Operaciones.GenerarOperacion(text_cod.getText(), combo_tipo_pago.getSelectedItem().toString(), hora, fecha, jTextField2.getText(),sector);
                    info = "";
                } else if (combo_cementerio.isSelected()) {
                    sector = "Cementerio";
                    info = "";
                } else {
                    sector = "Otros";
                    Clases.Operaciones.GenerarOperacion(text_cod.getText(), combo_tipo_pago.getSelectedItem().toString(), hora, fecha, jTextField2.getText(),sector);
                    info = "";
                }
                
                // PAGOS LAS CUOTAS Y REGISTRO LOS DETALLES DE OPERACIONES
                if (combo_institucional.isSelected()) {
                    cuotas.PagarCuotasAlumnos(text_cod.getText(), jTextField1.getText(), jTable1.getModel());
                } else if (combo_alquileres.isSelected()) {
                    Clases.Cuotas.PagarAlquiler((Object[][])datos_alquileres[0], monto_recibido, text_info.getText());
                } else if (combo_otros.isSelected()) {
                    //codigo que registra el detalle de la operacion
                    String detalle;
                    String monto;
                    String tipo;
                    for (int a = 0; a < jTable1.getRowCount(); a++) {
                        detalle = jTable1.getValueAt(a, 1).toString();
                        monto = jTable1.getValueAt(a, 2).toString();
                        tipo = jTable1.getValueAt(a, 0).toString();
                        Clases.Operaciones.GenerarOperacionDetalles(detalle, monto, tipo, null);
                        
                        monto_recibido = monto_recibido.add(new BigDecimal(monto));
                    }
                } else if (combo_socios.isSelected()) {
                    Clases.Cuotas.PagoCuotasSocio(text_cod.getText(), jTable1.getModel(), new BigDecimal(jTextField1.getText()));
                }
                
                // RECIBO
                int seleccion = JOptionPane.showOptionDialog(jframe, "<html><div style='width:300px;'><center><u><b>OPERACION</b></u></center><div></html>\n\n<html><u><b>Apellido y Nombre</b></u>: " + text_apenom.getText() + ".</html>\n<html><u><b>Monto</b></u>: " + df.format(monto_recibido) + ".</html>\n<html><u><b>Tipo de Pago</b></u>: " + combo_tipo_pago.getSelectedItem().toString() + " " + jTextField2.getText() + "</html>\n\n¿Desea Completar la Operación?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si, Cobrar e Imprimir", "Cancelar"}, "Imprimir");

                if (seleccion == 0) {
                    // SELECCIONAR ULTIMO ID OPERACION PARA MANDAR AL RECIBO

                    Integer id_operacion = Clases.Operaciones.UltimoID();

                    //  FRAME - SECTOR - TIPO OPERACION - NUMERO OPERACION
                    Clases.Recibo.Imprimir(jframe, sector, "Cuotas", id_operacion, info);

                    Variables.AumentarNumRecibo();

                    linneoadmin.LinneoAdmin.commiteo();

                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Operación realizada con Exito.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                }

                linneoadmin.LinneoAdmin.activarAutoCommit();
                
                if (combo_institucional.isSelected()) {
                    // PAGOS
                    CargarCuotas(text_cod.getText());
                    CargarTablaOperaciones(text_cod.getText(),"Institucional");
                } else if (combo_alquileres.isSelected()) {
                    CargarCuotasAlquileres(text_info.getText());
                    CargarTablaOperaciones(text_cod.getText(),"Alquileres");
                } else if (combo_otros.isSelected()) {
                    combo_otros.doClick();
                } else if (combo_socios.isSelected()) {
                    CargarCuotasSocios(text_cod.getText());
                    CargarTablaOperaciones(text_cod.getText(),"Socios");
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void combo_tipo_pagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipo_pagoItemStateChanged
        // TODO add your handling code here:
        if (combo_tipo_pago.getSelectedItem()!= null){
            if (!combo_tipo_pago.getSelectedItem().equals("Efectivo")) {
                jTextField2.setVisible(true);
            } else {
                jTextField2.setVisible(false);
            }   
        }
    }//GEN-LAST:event_combo_tipo_pagoItemStateChanged

    private void text_codKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_codKeyTyped
        // TODO add your handling code here:
        //evt.consume();
    }//GEN-LAST:event_text_codKeyTyped

    private void text_codKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_codKeyReleased
        // TODO add your handling code here:
        if (combo_institucional.isSelected()) {
            if (text_cod.getText().length() > 0){
                String[] alumno_encontrado = Clases.Alumnos.MostrarInscripcion(text_cod.getText());

                if (alumno_encontrado != null) {
                    text_apenom.setText(alumno_encontrado[0].toUpperCase());
                    text_info.setText(alumno_encontrado[1]);
                    
                    CargarCuotas(text_cod.getText());
                    
                    // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                    
                    CargarTablaOperaciones(text_cod.getText(),"Institucional");
                }    
            }
        } else if (combo_alquileres.isSelected()) {
            /*String[] datos = Clases.Cuotas.EncontroAlquileresCuotas(text_cod.getText());

            if (datos != null) {
                text_apenom.setText(datos[1]);
                text_info.setText(datos[0]);
                pk_alquiler_resp = datos[2];

                CargarCuotasAlquileres(text_cod.getText());

                    // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                int rc = model.getRowCount();
                for (int i = 0; i < rc; i++) {
                    model.removeRow(0);
                }

                jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);*/

                // HACER LA FUNCION PARA CARGAR LA 2DA TABLA
                //CargarTablaOperaciones(pk_alquiler_resp, "Alquileres");

            //}
        }
        
    }//GEN-LAST:event_text_codKeyReleased

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void combo_alquileresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_alquileresActionPerformed
        // TODO add your handling code here:
        if(combo_alquileres.isSelected()){
            
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jTable1.setEnabled(true);
            row = null;
            
            DefaultTableModel model=(DefaultTableModel)jTable1.getModel();
            int rc= model.getRowCount();
            for(int i = 0;i<rc;i++){
                model.removeRow(0);
            }
            
            model=(DefaultTableModel)jTable2.getModel();
            rc= model.getRowCount();
            for(int i = 0;i<rc;i++){
                model.removeRow(0);
            }  
            
            jLabel2.setText("Lote N°:");
            text_info.setVisible(true);
            jLabel4.setVisible(true);
            jLabel4.setText("Lotes:");
            
            text_cod.setText("");
            text_apenom.setText("");
            text_info.setText("");
            
            
            jTable1.setModel(tabla_alquileres);
            
            DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
            dtm.fireTableStructureChanged();
            
            // PONER TAMAÑO
            
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(1).setMinWidth(100);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setMinWidth(0);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(0);
            
            radio_default.setEnabled(true);
            radio_default.doClick();
            radio_entrada.setEnabled(false);
            radio_salida.setEnabled(false);
            
            jLabel6.setText("ADEUDADO ANUAL:");
            
            jTextField1.setEnabled(true);
            
            text_cod.requestFocus();
        }
    }//GEN-LAST:event_combo_alquileresActionPerformed

    private void combo_institucionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_institucionalActionPerformed
        // TODO add your handling code here:
        if(combo_institucional.isSelected()){
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jTable1.setEnabled(true);
            row = null;
            
            DefaultTableModel model=(DefaultTableModel)jTable1.getModel();
            int rc= model.getRowCount();
            for(int i = 0;i<rc;i++){
                model.removeRow(0);
            }
            
            model=(DefaultTableModel)jTable2.getModel();
            rc= model.getRowCount();
            for(int i = 0;i<rc;i++){
                model.removeRow(0);
            }  
            
            jLabel2.setText("Código:");            
            text_info.setVisible(true);
            jLabel4.setVisible(true);
            jLabel4.setText("Cursando:");
            
            text_cod.setText("");
            text_apenom.setText("");
            text_info.setText("");
            
            jTable1.setModel(tabla_institucional);
            
            DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
            dtm.fireTableStructureChanged();
            
            /* OCULTAR LAS COLUMNAS NECESARIAS :) */
            jTable1.getColumnModel().getColumn(0).setMinWidth(70);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(1).setMinWidth(80);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(80);
            jTable1.getColumnModel().getColumn(3).setMinWidth(120);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(4).setMinWidth(120);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(5).setMinWidth(180);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(180);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(180);
            jTable1.getColumnModel().getColumn(6).setMinWidth(0);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(7).setMinWidth(100);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMinWidth(0);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(9).setMinWidth(0);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(10).setMinWidth(0);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(0);
            
            // REESTABLESCO EL TAMANIO DE LAS COLUMNAS
            
            radio_default.setEnabled(true);
            radio_default.doClick();
            radio_entrada.setEnabled(false);
            radio_salida.setEnabled(false);
            
            jLabel6.setText("ADEUDADO ANUAL:");
            
            jTextField1.setEnabled(true);
            
            text_cod.requestFocus();
        }
    }//GEN-LAST:event_combo_institucionalActionPerformed

    private void combo_sociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_sociosActionPerformed
        // TODO add your handling code here:
        if(combo_socios.isSelected()) {
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jTable1.setEnabled(true);
            row = null;
            
            DefaultTableModel model=(DefaultTableModel)jTable1.getModel();
            int rc= model.getRowCount();
            for(int i = 0;i<rc;i++){
                model.removeRow(0);
            }
            
            model=(DefaultTableModel)jTable2.getModel();
            rc= model.getRowCount();
            for(int i = 0;i<rc;i++){
                model.removeRow(0);
            }
            
            
            jTable1.setModel(tabla_alquileres);
            
            DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
            dtm.fireTableStructureChanged();
            
            // PONER TAMAÑO
            
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(1).setMinWidth(100);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setMinWidth(0);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(0);
            
            jLabel4.setText("Teléfono:");
            
            jLabel2.setText("Socio N°:");
            
            text_cod.setText("");
            text_apenom.setText("");
            text_info.setText("");
            
            radio_default.setEnabled(true);
            radio_default.doClick();
            radio_entrada.setEnabled(false);
            radio_salida.setEnabled(false);
            
            text_cod.requestFocus();
        }
    }//GEN-LAST:event_combo_sociosActionPerformed

    private void combo_otrosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_otrosItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_otrosItemStateChanged

    private void combo_otrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_otrosActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int rc = model.getRowCount();
        for (int i = 0; i < rc; i++) {
            model.removeRow(0);
        }

        model = (DefaultTableModel) jTable2.getModel();
        rc = model.getRowCount();
        for (int i = 0; i < rc; i++) {
            model.removeRow(0);
        }
        
        row = null;
        jTable1.setEnabled(true);
        
        jLabel2.setText("Persona N°:");
        
        radio_entrada.setEnabled(true);
        radio_entrada.doClick();
        radio_default.setEnabled(false);
        radio_salida.setEnabled(false);
        
        jLabel4.setVisible(false);
        text_info.setVisible(false);
        
        jButton2.setVisible(true);
        jButton3.setVisible(true);
        jButton4.setVisible(true);
        
        //JOptionPane.showMessageDialog(jframe, "Hay q modificar el modelo de la jTable1 para q solo se vean 2 columnas:\n1º Descripción.\n2º Monto.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
        
        jTable1.setModel(tabla_otros);
            
        DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
        dtm.fireTableStructureChanged();
            
        jLabel6.setText("ADEUDADO ANUAL:");
        
        jTextField1.setEnabled(false);
        
        monto_anual = BigDecimal.ZERO;
        
        text_cod.requestFocus();
    }//GEN-LAST:event_combo_otrosActionPerformed

    private void radio_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_entradaActionPerformed
        // TODO add your handling code here:
        Class_editar_tabla.main(jTable3, Clases.TipoOperacion.Operaciones(ObtenerSectorActivo(),1));
    }//GEN-LAST:event_radio_entradaActionPerformed

    private void radio_defaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_defaultActionPerformed
        // TODO add your handling code here:
        Class_editar_tabla.main(jTable3, null);
    }//GEN-LAST:event_radio_defaultActionPerformed

    private void combo_cementerioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_cementerioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_cementerioActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (jTable3.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(jframe, "Seleccione un tipo de operación.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
        } else {
            OperacionTipoGenerar dialog = new OperacionTipoGenerar(jframe,true,jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            String[] registro = dialog.showDialog();
            if (registro != null) {
                DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
                modelo.addRow(registro);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(jframe, "Seleccione una operación.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String[] datos = new String[3];
            datos[0] = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            datos[1] = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
            datos[2] = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString();
            OperacionTipoGenerar dialog = new OperacionTipoGenerar(jframe,true,datos);
            String[] registro = dialog.showDialog();
            if (registro != null) {
                jTable1.setValueAt(registro[0], jTable1.getSelectedRow(), 0);
                jTable1.setValueAt(registro[1], jTable1.getSelectedRow(), 1);
                jTable1.setValueAt(registro[2], jTable1.getSelectedRow(), 2);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(jframe, "Seleccione una operación.", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
        } else {
            DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
            modelo.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void combo_tipo_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipo_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_tipo_pagoActionPerformed

    private void text_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_codActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_codActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton combo_alquileres;
    private javax.swing.JRadioButton combo_cementerio;
    private javax.swing.JRadioButton combo_institucional;
    private javax.swing.JRadioButton combo_otros;
    private javax.swing.JRadioButton combo_socios;
    private javax.swing.JComboBox combo_tipo_pago;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JRadioButton radio_default;
    private javax.swing.JRadioButton radio_entrada;
    private javax.swing.JRadioButton radio_salida;
    private javax.swing.JTextField text_apenom;
    private javax.swing.JTextField text_cod;
    private javax.swing.JTextField text_info;
    // End of variables declaration//GEN-END:variables
}
