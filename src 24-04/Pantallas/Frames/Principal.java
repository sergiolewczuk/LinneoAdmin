/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.Frames;

import Dialogs.AnioEjercicio;
import Dialogs.CambiarPass;
import Pantallas.InternalFrames.ABMAlquileres;
import Pantallas.InternalFrames.ABMAlumnos;
import Pantallas.InternalFrames.ABMCementerio;
import Pantallas.InternalFrames.ABMCuentas;
import Pantallas.InternalFrames.ABMCursos;
import Pantallas.InternalFrames.ABMSocios;
import Pantallas.InternalFrames.AXCAlumnos;
import Pantallas.InternalFrames.AltaAsientos;
import Pantallas.InternalFrames.AlumnosInscriptos;
import Pantallas.InternalFrames.BalanceComprobacion;
import Pantallas.InternalFrames.ConsultaAsientos;
import Pantallas.InternalFrames.Cuotas;
import Pantallas.InternalFrames.Diario;
import Pantallas.InternalFrames.MayorUnaCuenta;
import Pantallas.InternalFrames.Operaciones;
import Pantallas.InternalFrames.SubDiario;
import Pantallas.InternalFrames.TiposPagos;
import com.toedter.calendar.JDayChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.pushingpixels.substance.api.skin.*;;


public class Principal extends javax.swing.JFrame {
    
    int posicion_x = 0;
    int posicion_y = 0;
    int tamanio_x = 0;
    int tamanio_y = 0;
    
    public static String fecha_para_bd;
    public static String fecha_actual;
    public static String hora_actual;
    public static String anio_global;
    public static String anio_usado;
    public static DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(Locale.getDefault());
    
    int dia, mes, año, hora, minutos, segundos; 
    void reloj() {
        final GregorianCalendar calendario = new java.util.GregorianCalendar();
        segundos = calendario.get(Calendar.SECOND);
        javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                linneoadmin.LinneoAdmin.DatosActuales();
                String hour = hora_actual;
                String date = fecha_actual;
                jLabel1.setText("<html><center>" + hour + "<br>" + date + "</center></html>");
            }
        });
        timer.start();
    }
    
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    public void dispose(boolean pantalla) {
        if (pantalla) {
            manager.removeKeyEventDispatcher(key);
        }
        
        super.dispose();
        
        if (pantalla) {
            Dialogs.Login login = new Dialogs.Login(new javax.swing.JFrame(), true);
            login.setVisible(true);
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
    
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                //System.out.println(e.getKeyCode());
                if (e.getKeyCode() == 27) {//ESCAPE
                    JFrame jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
                    if (jframe.isActive()) {
                        if (jDesktopPane1.getAllFrames().length == 0) {
                            dispose(true);
                        } else {
                            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
                            frame.dispose();
                        }
                    }
                }
                
                if (e.getKeyCode() == 112) {//F1
                    if (jDesktopPane1.getAllFrames().length == 0) {
                        jButton1.doClick();
                    }
                }
                
                if (e.getKeyCode() == 18) {//F1
                    jMenu1.transferFocus();
                }
                
                if (e.isAltDown() && e.getKeyCode() == 10) { // ALT + ENTER
                    JFrame jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
                    int max = jframe.getExtendedState();
                    if (max == MAXIMIZED_BOTH && jframe.isUndecorated()) {
                        //paso a modo ventana
                        jframe.setResizable(true);
                        dispose(false);
                        
                        jframe.setExtendedState(java.awt.Frame.NORMAL);
                        
                        Toolkit tk = Toolkit.getDefaultToolkit();
                        Dimension tamanio = tk.getScreenSize();
                        jframe.setSize((int)tamanio.getWidth() - (int)(tamanio.getWidth()/4), (int)tamanio.getHeight() - (int)(tamanio.getHeight()/4));
                        
                        jframe.setUndecorated(false);
                        
                        //seteo la posicion
                        jframe.setLocation(posicion_x, posicion_y);
                        //seteo el tamanio
                        jframe.setSize(tamanio_x, tamanio_y);
                        
                        jframe.setVisible(true);
                    } else {
                        //paso a modo fullscreen
                        jframe.setResizable(false);
                        //guardo la posicion
                        posicion_x = jframe.getX();
                        posicion_y = jframe.getY();
                        //guardo el tamanio
                        tamanio_x = jframe.getWidth();
                        tamanio_y = jframe.getHeight();
                        
                        dispose(false);
                        
                        jframe.setExtendedState(MAXIMIZED_BOTH);
                        jframe.setUndecorated(true);
                        jframe.setVisible(true);
                    }
                }
            }
            
            if (e.getID() == KeyEvent.KEY_TYPED) {
                if (jDesktopPane1.getAllFrames().length > 0) {
                    JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
                    if (Principal.this.isActive()) {
                        if ("javax.swing.JTextField".equals(frame.getFocusOwner().getClass().getName())) {
                            //System.out.println("InternalFrame: " + frame.getFocusOwner().getClass().getName());
                            comprobacion_textos(e, frame.getFocusOwner().getName());
                        }
                    } else {
                        JDialog dialog = null;
                        for (Window ownedWindow : Principal.this.getOwnedWindows()) {
                            if (ownedWindow.getClass().getName().contains("Dialogs")) {
                                dialog = (JDialog) ownedWindow;
                            }
                        }
                        if (dialog != null) {
                            if (dialog.isActive()) {
                                //PRIMER DIALOG ABIERTO Y ACTIVO
                                if (dialog.getFocusOwner() != null) {
                                    if ("javax.swing.JTextField".equals(dialog.getFocusOwner().getClass().getName())) {
                                        //System.out.println("Dialog: " + dialog.getFocusOwner().getClass().getName());
                                        comprobacion_textos(e, dialog.getFocusOwner().getName());
                                    }
                                }
                            } else {
                                //SEGUNDO DIALOG ABIERTO Y ACTIVO
                                JDialog dialog2 = null;

                                for (Window ownedWindow : dialog.getOwnedWindows()) {
                                    if (ownedWindow.getClass().getName().contains("Dialogs")) {
                                        dialog2 = (JDialog) ownedWindow;
                                    }
                                }

                                if (dialog2 != null) {
                                    if (dialog2.isActive()) {
                                        if (dialog2.getFocusOwner() != null) {
                                            if ("javax.swing.JTextField".equals(dialog2.getFocusOwner().getClass().getName())) {
                                                //System.out.println("Dialog: " + dialog2.getFocusOwner().getClass().getName());
                                                comprobacion_textos(e, dialog2.getFocusOwner().getName());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
    
    /**
     * Creates new form Principal
     */
    public static String usuario;
    public static String id;
    public static String tipo;
    public Principal(String us, String idu, String tipo) {
        initComponents();
        
        this.usuario = us;
        this.id = idu;
        this.tipo = tipo;
        jLabel3.setText(us);
        
        if (tipo.equals("Cajero")) {
            //ES CAJERO
            //jMenu1.setVisible(false);
            jMenuItem1.setVisible(false);
            jMenu4.setVisible(false);
            jMenu2.setVisible(false);
            jMenu7.setVisible(false);
            
            jButton1.setVisible(false);
        } else {
            //ES CONTADOR
            //jMenuItem14.setVisible(false);
            //jMenuItem10.setVisible(false);
        }
        
        jMenu1.setText("<html><a>A<u>s</u>ientos</a></html>");
        jMenu4.setText("<html><a>Emisión de <u>L</u>istados Varios</a></html>");
        jMenu2.setText("<html><a><u>P</u>lan de Cuentas</a></html>");
        jMenu8.setText("<html><a><u>C</u>aja</a></html>");
        jMenu3.setText("<html><a><u>A</u>lumnos</a></html>");
        jMenu9.setText("<html><a>Alq<u>u</u>ileres</a></html>");
        jMenu5.setText("<html><a>S<u>o</u>cios</a></html>");
        jMenu6.setText("<html><a>C<u>e</u>menterio</a></html>");
        jMenu7.setText("<html><a>Con<u>f</u>iguraciones</a></html>");
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension tamanio = tk.getScreenSize();
        this.setSize((int)tamanio.getWidth() - (int)(10), (int)tamanio.getHeight() - (int)(50));
        
        this.setLocationRelativeTo(null);
        
        posicion_x = this.getX();
        posicion_y = this.getY();
        tamanio_x = this.getWidth();
        tamanio_y = this.getHeight();
        
        Utilidades.EstiloFrames.main(this);
        
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        
        //Clases.Recibo.Imprimir(this, "Institucional", "Cuotas", 336, "");
        
        linneoadmin.LinneoAdmin.DatosActuales();
        reloj();
        
        anio_global = fecha_actual.split("-")[2];
        anio_usado = anio_global;
        
        /*Component[] componentes = jCalendar1.getDayChooser().getComponents();
        for (int a = 0; a < 1; a++) {
            Component comp = componentes[a];
            
            JPanel panel = (JPanel)comp;
            Component[] panel_c = panel.getComponents();
            
            for (int b = 0; b < panel_c.length; b++) {
                Component comp_p = panel_c[b];
                if (comp_p.getClass().toString().contains("JDayChooser$1")) {
                    //DIAS
                    JButton boton = (JButton)comp_p;
                    boton.getText();
                    MouseListener[] listener = boton.getMouseListeners();
                    
                    for (int c = 0; c < listener.length; c++) {
                        MouseListener list = listener[c];
                        boton.removeMouseListener(list);
                    }
                }
            }
        }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        pulsatingLogo1 = new pulse.PulsatingLogo();
        jButton1 = new javax.swing.JButton();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pantalla Principal");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        pulsatingLogo1.setImageName("/Imagenes/icono.png");

        jButton1.setText("CAMBIAR AÑO DEL EJERCICIO [F1]");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCalendar1.setTodayButtonVisible(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setText("USUARIO ACTIVO:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("jLabel3");

        jButton2.setText("CAMBIAR MI CONTRASEÑA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("v. 1.8.1 ");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(pulsatingLogo1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addContainerGap())
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 370, Short.MAX_VALUE)
                        .addComponent(pulsatingLogo1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        jDesktopPane1.setLayer(pulsatingLogo1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jCalendar1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jMenu1.setBackground(new java.awt.Color(204, 204, 204));
        jMenu1.setForeground(new java.awt.Color(204, 204, 204));
        jMenu1.setMnemonic('1');
        jMenu1.setText("Asientos [1]");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem1.setText("Alta de Asiento");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem2.setText("Consulta de Asientos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu4.setBackground(new java.awt.Color(204, 204, 204));
        jMenu4.setForeground(new java.awt.Color(204, 204, 204));
        jMenu4.setMnemonic('2');
        jMenu4.setText("Emisión de Listados Varios [2]");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem5.setText("Diario");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem6.setText("Sub Diario");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem7.setText("Mayor de una Cuenta");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem8.setText("Balance de Comprobación de Sumas y Saldos");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        jMenu2.setBackground(new java.awt.Color(204, 204, 204));
        jMenu2.setForeground(new java.awt.Color(204, 204, 204));
        jMenu2.setMnemonic('3');
        jMenu2.setText("Plan de Cuentas [3]");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem3.setText("Consultar Plan de Cuentas");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu8.setBackground(new java.awt.Color(204, 204, 204));
        jMenu8.setForeground(new java.awt.Color(204, 204, 204));
        jMenu8.setText("Caja [4]");
        jMenu8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jMenuItem14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem14.setText("Nueva Operación");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem14);

        jMenuItem15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem15.setText("Operaciones Caja");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem15);

        jMenuBar1.add(jMenu8);

        jMenu3.setBackground(new java.awt.Color(204, 204, 204));
        jMenu3.setForeground(new java.awt.Color(204, 204, 204));
        jMenu3.setMnemonic('4');
        jMenu3.setText("Alumnos [5]");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem4.setText("Ver Alumnos");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem17.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem17.setText("Ver Alumnos Inscriptos");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem17);

        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem10.setText("Inscribir Alumno");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem9.setText("Ver Cursos");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuBar1.add(Box.createHorizontalGlue());

        jMenuBar1.add(jMenu3);

        jMenu9.setBackground(new java.awt.Color(204, 204, 204));
        jMenu9.setForeground(new java.awt.Color(204, 204, 204));
        jMenu9.setText("Alquileres");
        jMenu9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jMenuItem16.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem16.setText("ABM Alquileres");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem16);

        jMenuBar1.add(jMenu9);

        jMenu5.setBackground(new java.awt.Color(204, 204, 204));
        jMenu5.setForeground(new java.awt.Color(204, 204, 204));
        jMenu5.setText("Socios [6]");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jMenuItem11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem11.setText("Ver Socios");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenuBar1.add(jMenu5);

        jMenu6.setBackground(new java.awt.Color(204, 204, 204));
        jMenu6.setForeground(new java.awt.Color(204, 204, 204));
        jMenu6.setText("Cementerio [7]");
        jMenu6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jMenuItem12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem12.setText("Ver Cementerio");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuBar1.add(jMenu6);

        jMenu7.setBackground(new java.awt.Color(204, 204, 204));
        jMenu7.setForeground(new java.awt.Color(204, 204, 204));
        jMenu7.setText("Configuraciones [8]");
        jMenu7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenu7.setHideActionText(true);

        jMenuItem13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jMenuItem13.setText("Configurar Operaciones");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem13);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyReleased

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            ConsultaAsientos frame = new ConsultaAsientos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            AltaAsientos frame = new AltaAsientos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            ABMCuentas frame = new ABMCuentas();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }

        if (cerro) {
            ABMAlumnos frame = new ABMAlumnos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            Diario frame = new Diario();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            SubDiario frame = new SubDiario();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }

        if (cerro) {
            ABMCursos frame = new ABMCursos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }

        if (cerro) {
            AXCAlumnos frame = new AXCAlumnos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            MayorUnaCuenta frame = new MayorUnaCuenta();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            BalanceComprobacion frame = new BalanceComprobacion();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        AnioEjercicio dialog = new AnioEjercicio(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        
        // PONGO TRUE PARA QUE SE CIERRE SIEMPRE
        
        boolean cerro = true;
        
        
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            ABMSocios frame = new ABMSocios();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        
        // PONGO TRUE PARA QUE SE CIERRE SIEMPRE
        
        boolean cerro = true;
        
        
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            ABMCementerio frame = new ABMCementerio();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            TiposPagos frame = new TiposPagos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            Cuotas frame = new Cuotas();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        CambiarPass dialog = new CambiarPass(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        boolean cerro = false;
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            Operaciones frame = new Operaciones();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        dispose(true);
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        
        // PONGO TRUE PARA QUE SE CIERRE SIEMPRE
        boolean cerro = true;
        
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            ABMAlquileres frame = new ABMAlquileres();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        
        // PONGO TRUE PARA QUE SE CIERRE SIEMPRE
        boolean cerro = true;
        
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
            if (frame.isClosed()) {
                cerro = true;
            }
        } else {
            cerro = true;
        }
        
        if (cerro) {
            AlumnosInscriptos frame = new AlumnosInscriptos();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    /**
     * @param us
     * @param id
     * @param tipo
     */
    public static void main(final String us, final String id, final String tipo) {
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //SubstanceRavenLookAndFeel
                    //SubstanceTwilightLookAndFeel
                    //SubstanceGeminiLookAndFeel
                    //UpperEssentialLookAndFeel()
                    //com.jtattoo.plaf.acryl.AcrylLookAndFeel
                    UIManager.setLookAndFeel(new SubstanceGeminiLookAndFeel());
                } catch (Exception e) {
                    System.out.println("Substance Graphite failed to initialize");
                }
                
                new Principal(us,id,tipo).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JCalendar jCalendar1;
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private pulse.PulsatingLogo pulsatingLogo1;
    // End of variables declaration//GEN-END:variables
}
