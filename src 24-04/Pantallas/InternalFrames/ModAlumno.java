/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Clases.Alumnos;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.jDesktopPane1;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Sergio
 */
public class ModAlumno extends javax.swing.JInternalFrame {

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
    
    JFrame jframe;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    String cod_viejo = null;
    
    GregorianCalendar gc = new GregorianCalendar();
    java.util.Date dt = new java.util.Date();
    Integer anio_global = gc.get(Calendar.YEAR);
    Integer mes_global = gc.get(Calendar.MONTH);
    Integer dia_global = gc.get(Calendar.DATE);
    Integer anio_prox = anio_global + 1;  
    
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
    
    
    Clases.Alumnos alumnos = new Clases.Alumnos();
    
    public ModAlumno(String cod) {
        initComponents();
        
        jSplitPane1.setDividerLocation(.2);
        
        
        Object[] info = alumnos.Mostrar_Datos(cod);

        //              VARIABLES ALUMNO
        codigo_alumno.setText(info[1].toString());

        cod_viejo = info[1].toString();

        dni_alumno.setText(info[2].toString());
        apenom_alumno.setText(info[3].toString());

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
        }

        edad_alumno.setText(info[5].toString());
        domicilio_alumno.setText(info[6].toString());
        localidad_alumno.setText(info[7].toString());
        telefono_alumno.setText(info[8].toString());
        mail_alumno.setText(info[9].toString());

        //              VARIABLES PADRE
        dni_padre.setText(info[11].toString());
        apenom_padre.setText(info[10].toString());
        profesion_padre.setText(info[14].toString());

        if (info[12] == "") {
            dia_nac_padre.setText("");
            mes_nac_padre.setText("");
            anio_nac_padre.setText("");
        } else {
            String dia_padre = info[12].toString().substring(7, 9);
            dia_nac_padre.setText(dia_padre);
            String mes_padre = info[12].toString().substring(4, 6);
            mes_nac_padre.setText(mes_padre);
            String anio_padre = info[12].toString().substring(0, 3);
            anio_nac_padre.setText(anio_padre);
        }

        estado_civil_padre.setText(info[13].toString());
        domicilio_padre.setText(info[17].toString());
        localidad_padre.setText(info[18].toString());
        telefono_padre.setText(info[15].toString());
        mail_padre.setText(info[16].toString());
        convive_padre.setSelectedItem(info[19].toString());

        //          VARIABLES MADRE
        dni_madre.setText(info[21].toString());
        apenom_madre.setText(info[20].toString());
        profesion_madre.setText(info[24].toString());

        if (info[22] == "") {
            dia_nac_madre.setText("");
            mes_nac_madre.setText("");
            anio_nac_madre.setText("");
        } else {
            String dia_madre = info[22].toString().substring(8, 10);
            dia_nac_madre.setText(dia_madre);
            String mes_madre = info[22].toString().substring(5, 7);
            mes_nac_madre.setText(mes_madre);
            String anio_madre = info[22].toString().substring(0, 4);
            anio_nac_madre.setText(anio_madre);
        }

        estado_civil_madre.setText(info[23].toString());
        domicilio_madre.setText(info[27].toString());
        localidad_madre.setText(info[28].toString());
        telefono_madre.setText(info[25].toString());
        mail_madre.setText(info[26].toString());
        convive_madre.setSelectedItem(info[29].toString());

        //          VARIABLES TUTOR
        dni_tutor.setText(info[31].toString());
        apenom_tutor.setText(info[30].toString());
        profesion_tutor.setText(info[34].toString());

        if (info[22] == "") {
            dia_nac_tutor.setText("");
            mes_nac_tutor.setText("");
            anio_nac_tutor.setText("");
        } else {
            String dia_tutor = info[32].toString().substring(8, 10);
            dia_nac_tutor.setText(dia_tutor);
            String mes_tutor = info[32].toString().substring(5, 7);
            mes_nac_tutor.setText(mes_tutor);
            String anio_tutor = info[32].toString().substring(0, 4);
            anio_nac_tutor.setText(anio_tutor);
        }

        estado_civil_tutor.setText(info[33].toString());
        domicilio_tutor.setText(info[37].toString());
        localidad_tutor.setText(info[39].toString());
        telefono_tutor.setText(info[35].toString());
        mail_tutor.setText(info[36].toString());
        convive_tutor.setSelectedItem(info[39].toString());
        
            
        //Hijack the keyboard manager
        manager.addKeyEventDispatcher(key);
        jframe = (JFrame) Principal.jDesktopPane1.getTopLevelAncestor();
        
        
        
        // PANELS
        
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
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dni_alumno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        apenom_alumno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        edad_alumno = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        domicilio_alumno = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        localidad_alumno = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        telefono_alumno = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        mail_alumno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        codigo_alumno = new javax.swing.JTextField();
        dia_nac_alumno = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        mes_nac_alumno = new javax.swing.JTextField();
        anio_nac_alumno = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        mes_nac_madre = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        apenom_madre = new javax.swing.JTextField();
        dia_nac_madre = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        dni_madre = new javax.swing.JTextField();
        convive_madre = new javax.swing.JComboBox();
        anio_nac_madre = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        profesion_madre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        mail_madre = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        localidad_madre = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        telefono_madre = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        estado_civil_madre = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        domicilio_madre = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        dni_padre = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        apenom_padre = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        profesion_padre = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        estado_civil_padre = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        domicilio_padre = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        localidad_padre = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        telefono_padre = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        mail_padre = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        anio_nac_padre = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        mes_nac_padre = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        dia_nac_padre = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        convive_padre = new javax.swing.JComboBox();
        convive_tutor = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        mail_tutor = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        telefono_tutor = new javax.swing.JTextField();
        localidad_tutor = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        domicilio_tutor = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        apenom_tutor = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        profesion_tutor = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        estado_civil_tutor = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        seleccion_tutor = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        mes_nac_tutor = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        anio_nac_tutor = new javax.swing.JTextField();
        dia_nac_tutor = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        dni_tutor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setIconifiable(true);
        setTitle("Modificar Alumnos");
        setToolTipText("");

        jSplitPane1.setDividerLocation(420);
        jSplitPane1.setResizeWeight(0.5);

        jLabel2.setText("DNI *");

        dni_alumno.setName("numeros"); // NOI18N

        jLabel3.setText("Apellido y Nombre *");

        apenom_alumno.setName("string"); // NOI18N

        jLabel5.setText("Nacimiento *");

        jLabel6.setText("Edad");

        edad_alumno.setEditable(false);

        jLabel7.setText("Domicilio");

        domicilio_alumno.setName("string"); // NOI18N

        jLabel8.setText("Localidad");

        localidad_alumno.setName("string"); // NOI18N

        jLabel9.setText("Teléfono");

        telefono_alumno.setName("string"); // NOI18N

        jLabel11.setText("Mail");

        mail_alumno.setName("string"); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Alumnos");

        jLabel16.setText("Código *");

        codigo_alumno.setEditable(false);
        codigo_alumno.setName("numeros"); // NOI18N

        dia_nac_alumno.setName("numeros"); // NOI18N
        dia_nac_alumno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dia_nac_alumnoKeyReleased(evt);
            }
        });

        jLabel17.setText(" / ");

        mes_nac_alumno.setName("numeros"); // NOI18N
        mes_nac_alumno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mes_nac_alumnoKeyReleased(evt);
            }
        });

        anio_nac_alumno.setToolTipText("");
        anio_nac_alumno.setMinimumSize(new java.awt.Dimension(50, 20));
        anio_nac_alumno.setName("numeros"); // NOI18N
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

        jLabel18.setText(" / ");

        jButton3.setText("Corregir Codigo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        mes_nac_madre.setName("numeros"); // NOI18N
        mes_nac_madre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mes_nac_madreKeyReleased(evt);
            }
        });

        jLabel33.setText("Apellido y Nombre");

        jLabel50.setText(" / ");

        apenom_madre.setName("string"); // NOI18N

        dia_nac_madre.setName("numeros"); // NOI18N
        dia_nac_madre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dia_nac_madreKeyReleased(evt);
            }
        });

        jLabel32.setText("DNI");

        jLabel40.setText(" / ");

        dni_madre.setName("numeros"); // NOI18N

        convive_madre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        anio_nac_madre.setMinimumSize(new java.awt.Dimension(50, 20));
        anio_nac_madre.setName("numeros"); // NOI18N
        anio_nac_madre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                anio_nac_madreKeyReleased(evt);
            }
        });

        jLabel52.setText("Convive");

        jLabel34.setText("Profesión");

        profesion_madre.setName("string"); // NOI18N

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Madre");

        mail_madre.setName("string"); // NOI18N

        jLabel38.setText("Localidad");

        localidad_madre.setName("string"); // NOI18N

        jLabel39.setText("Teléfono");

        telefono_madre.setName("string"); // NOI18N

        jLabel36.setText("Estado Civíl");

        estado_civil_madre.setName("string"); // NOI18N

        jLabel37.setText("Domicilio");

        domicilio_madre.setName("string"); // NOI18N

        jLabel41.setText("Mail");

        jLabel35.setText("Nacimiento");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel9))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(dni_alumno)
                                    .addComponent(domicilio_alumno, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(edad_alumno, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(apenom_alumno, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(dia_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(mes_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(anio_nac_alumno, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                                    .addComponent(localidad_alumno)
                                    .addComponent(telefono_alumno)
                                    .addComponent(mail_alumno)
                                    .addComponent(codigo_alumno))))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel52))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(profesion_madre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dni_madre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(estado_civil_madre)
                                    .addComponent(domicilio_madre)
                                    .addComponent(localidad_madre)
                                    .addComponent(telefono_madre)
                                    .addComponent(mail_madre)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(dia_nac_madre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(mes_nac_madre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel50)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(anio_nac_madre, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                                    .addComponent(convive_madre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(apenom_madre))))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dni_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apenom_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(anio_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dia_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(mes_nac_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(edad_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(domicilio_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(localidad_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(telefono_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(mail_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(codigo_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(dni_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(apenom_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(profesion_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(anio_nac_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dia_nac_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel50)
                    .addComponent(mes_nac_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(estado_civil_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(domicilio_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(localidad_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(telefono_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(mail_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(convive_madre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jLabel22.setText("DNI");

        dni_padre.setName("numeros"); // NOI18N

        jLabel23.setText("Apellido y Nombre");

        apenom_padre.setName("string"); // NOI18N

        jLabel24.setText("Profesión");

        profesion_padre.setName("string"); // NOI18N

        jLabel25.setText("Nacimiento");

        jLabel26.setText("Estado Civíl");

        estado_civil_padre.setName("string"); // NOI18N

        jLabel27.setText("Domicilio");

        domicilio_padre.setName("string"); // NOI18N

        jLabel28.setText("Localidad");

        localidad_padre.setName("string"); // NOI18N

        jLabel29.setText("Teléfono");

        telefono_padre.setName("string"); // NOI18N

        jLabel31.setText("Mail");

        mail_padre.setName("string"); // NOI18N

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Padre");

        anio_nac_padre.setMinimumSize(new java.awt.Dimension(50, 20));
        anio_nac_padre.setName("numeros"); // NOI18N
        anio_nac_padre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                anio_nac_padreKeyReleased(evt);
            }
        });

        jLabel19.setText(" / ");

        mes_nac_padre.setName("numeros"); // NOI18N
        mes_nac_padre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mes_nac_padreKeyReleased(evt);
            }
        });

        jLabel20.setText(" / ");

        dia_nac_padre.setName("numeros"); // NOI18N
        dia_nac_padre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dia_nac_padreKeyReleased(evt);
            }
        });

        jLabel53.setText("Convive");

        convive_padre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        convive_tutor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        jLabel12.setText("Convive");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Tutor");

        mail_tutor.setEditable(false);
        mail_tutor.setName("string"); // NOI18N

        jLabel51.setText("Mail");

        telefono_tutor.setEditable(false);
        telefono_tutor.setName("string"); // NOI18N

        localidad_tutor.setEditable(false);
        localidad_tutor.setName("string"); // NOI18N

        jLabel49.setText("Teléfono");

        domicilio_tutor.setEditable(false);
        domicilio_tutor.setName("string"); // NOI18N

        jLabel48.setText("Localidad");

        apenom_tutor.setEditable(false);
        apenom_tutor.setName("string"); // NOI18N

        jLabel44.setText("Profesión");

        profesion_tutor.setEditable(false);
        profesion_tutor.setName("string"); // NOI18N

        jLabel45.setText("Nacimiento");

        jLabel46.setText("Estado Civíl");

        estado_civil_tutor.setEditable(false);
        estado_civil_tutor.setName("string"); // NOI18N

        jLabel47.setText("Domicilio");

        seleccion_tutor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Padre", "Madre", "Otro" }));
        seleccion_tutor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                seleccion_tutorItemStateChanged(evt);
            }
        });

        jLabel1.setText("Tutor");

        jLabel21.setText(" / ");

        mes_nac_tutor.setEditable(false);
        mes_nac_tutor.setName("numeros"); // NOI18N
        mes_nac_tutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mes_nac_tutorKeyReleased(evt);
            }
        });

        jLabel30.setText(" / ");

        anio_nac_tutor.setEditable(false);
        anio_nac_tutor.setMinimumSize(new java.awt.Dimension(50, 20));
        anio_nac_tutor.setName("numeros"); // NOI18N
        anio_nac_tutor.setPreferredSize(new java.awt.Dimension(50, 20));
        anio_nac_tutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                anio_nac_tutorKeyReleased(evt);
            }
        });

        dia_nac_tutor.setEditable(false);
        dia_nac_tutor.setName("numeros"); // NOI18N
        dia_nac_tutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dia_nac_tutorKeyReleased(evt);
            }
        });

        jLabel42.setText("DNI");

        jLabel43.setText("Apellido y Nombre");

        dni_tutor.setEditable(false);
        dni_tutor.setName("numeros"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(telefono_padre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(localidad_padre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(domicilio_padre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(estado_civil_padre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(profesion_padre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dni_padre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(apenom_padre)
                                    .addComponent(mail_padre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(dia_nac_padre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(mes_nac_padre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(anio_nac_padre, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                                    .addComponent(convive_padre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dni_tutor)
                                    .addComponent(apenom_tutor, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(profesion_tutor)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(dia_nac_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(mes_nac_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(anio_nac_tutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(estado_civil_tutor)
                                    .addComponent(domicilio_tutor)
                                    .addComponent(localidad_tutor)
                                    .addComponent(telefono_tutor)
                                    .addComponent(mail_tutor)
                                    .addComponent(convive_tutor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(seleccion_tutor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(dni_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(apenom_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(profesion_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(anio_nac_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dia_nac_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addComponent(mes_nac_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(estado_civil_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(domicilio_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(localidad_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefono_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(mail_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(convive_padre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(seleccion_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(dni_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apenom_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(profesion_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(anio_nac_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dia_nac_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel30)
                    .addComponent(mes_nac_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(estado_civil_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(domicilio_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(localidad_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(telefono_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(mail_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(convive_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel3);

        jScrollPane1.setViewportView(jSplitPane1);

        jButton1.setText("F1 - GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("F2 - VOLVER AL LISTADO ALUMNOS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dia_nac_alumnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dia_nac_alumnoKeyReleased
        // TODO add your handling code here:
        if (dia_nac_alumno.getText().length() <= 2) {
            if (dia_nac_alumno.getText().length() > 0) {
                Integer dia = Integer.valueOf(dia_nac_alumno.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 31) {

                    } else {
                        dia_nac_alumno.setText(dia_global.toString());
                    }
                }
            }
        } else {
            dia_nac_alumno.setText(dia_nac_alumno.getText().substring(0, 2));
        }
    }//GEN-LAST:event_dia_nac_alumnoKeyReleased

    private void mes_nac_alumnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mes_nac_alumnoKeyReleased
        // TODO add your handling code here:
        if (mes_nac_alumno.getText().length() <= 2) {
            if (mes_nac_alumno.getText().length() > 0) {
                Integer dia = Integer.valueOf(mes_nac_alumno.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 12) {

                    } else {
                        mes_nac_alumno.setText(mes_global.toString());
                    }
                }
            }
        } else {
            mes_nac_alumno.setText(mes_nac_alumno.getText().substring(0, 2));
        } 
    }//GEN-LAST:event_mes_nac_alumnoKeyReleased

    private void anio_nac_alumnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_anio_nac_alumnoFocusLost
        // TODO add your handling code here:
        if(mes_nac_alumno.getText().isEmpty() || dia_nac_alumno.getText().isEmpty()){
            
        }else{
            Integer anio_text = Integer.parseInt(anio_nac_alumno.getText());

            if(anio_global >= anio_text){
                Integer anio_fechan_alum = Integer.parseInt(anio_nac_alumno.getText());   
                Integer mes_fechan_alum = Integer.parseInt(mes_nac_alumno.getText());  
                Integer dia_fechan_alum = Integer.parseInt(dia_nac_alumno.getText());

                Integer mostrar_fecha = calcularEdad(anio_fechan_alum,mes_fechan_alum,dia_fechan_alum);
                edad_alumno.setText(mostrar_fecha.toString()); 
            }else{
                anio_nac_alumno.setText(anio_global.toString());
            }
        }
    }//GEN-LAST:event_anio_nac_alumnoFocusLost

    private void anio_nac_alumnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anio_nac_alumnoKeyReleased
        // TODO add your handling code here:
        if (anio_nac_alumno.getText().length() <= 4) {
            if (anio_nac_alumno.getText().length() > 0) {
                Integer dia = Integer.valueOf(anio_nac_alumno.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 2018) {

                    } else {
                        anio_nac_alumno.setText(anio_global.toString());
                    }
                }
            }
        } else {
            anio_nac_alumno.setText(anio_nac_alumno.getText().substring(0, 4));
        } 
    }//GEN-LAST:event_anio_nac_alumnoKeyReleased

    private void dia_nac_padreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dia_nac_padreKeyReleased
        // TODO add your handling code here:
        if (dia_nac_padre.getText().length() <= 2) {
            if (dia_nac_padre.getText().length() > 0) {
                Integer dia = Integer.valueOf(dia_nac_padre.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 31) {

                    } else {
                        dia_nac_padre.setText(dia_global.toString());
                    }
                }
            }
        } else {
            dia_nac_padre.setText(dia_nac_padre.getText().substring(0, 2));
        }
    }//GEN-LAST:event_dia_nac_padreKeyReleased

    private void mes_nac_padreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mes_nac_padreKeyReleased
        // TODO add your handling code here:
        if (mes_nac_padre.getText().length() <= 2) {
            if (mes_nac_padre.getText().length() > 0) {
                Integer dia = Integer.valueOf(mes_nac_padre.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 12) {

                    } else {
                        mes_nac_padre.setText(mes_global.toString());
                    }
                }
            }
        } else {
            mes_nac_padre.setText(mes_nac_padre.getText().substring(0, 2));
        }
    }//GEN-LAST:event_mes_nac_padreKeyReleased

    private void anio_nac_padreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anio_nac_padreKeyReleased
        // TODO add your handling code here:
        if (anio_nac_padre.getText().length() <= 4) {
            if (anio_nac_padre.getText().length() > 0) {
                Integer dia = Integer.valueOf(anio_nac_padre.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 2018) {

                    } else {
                        anio_nac_padre.setText(anio_global.toString());
                    }
                }
            }
        } else {
            anio_nac_padre.setText(anio_nac_padre.getText().substring(0, 4));
        }
    }//GEN-LAST:event_anio_nac_padreKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (jDesktopPane1.getAllFrames().length != 0) {
            JInternalFrame frame = jDesktopPane1.getAllFrames()[0];
            frame.dispose();
        }
        
        ABMAlumnos frame = new ABMAlumnos();
        frame.setVisible(true);
        jDesktopPane1.add(frame);
        try {
            frame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (dni_alumno.getText().isEmpty() || apenom_alumno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Complete los Datos PRINCIPALES para continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
        } else {
            Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Elija la Opción.", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Guardar Cambios", "No Guardar y Salir", "Cancelar"}, "Si");

            if(Seleccion == 0){ // GUARDAR CAMBIOS
                
                String fecha_alumno = null;
                String fecha_padre = null;
                String fecha_madre = null;  
                String fecha_tutor = null;       
                
                if (dia_nac_alumno.getText().isEmpty() || mes_nac_alumno.getText().isEmpty() || anio_nac_alumno.getText().isEmpty()) {
                    fecha_alumno = anio_global + "-" + mes_global + "-" + dia_global;
                } else {
                    String dia_alumno = dia_nac_alumno.getText();
                    String mes_alumno = mes_nac_alumno.getText();
                    String anio_alumno = anio_nac_alumno.getText();
                    fecha_alumno = anio_alumno + "-" + mes_alumno + "-" + dia_alumno;
                }

                if (dia_nac_padre.getText().isEmpty() || mes_nac_padre.getText().isEmpty() || anio_nac_padre.getText().isEmpty()) {
                    fecha_padre = anio_global + "-" + mes_global + "-" + dia_global;
                } else {
                    String dia_padre = dia_nac_padre.getText();
                    String mes_padre = mes_nac_padre.getText();
                    String anio_padre = anio_nac_padre.getText();
                    fecha_padre = anio_padre + "-" + mes_padre + "-" + dia_padre;
                }

                if (dia_nac_madre.getText().isEmpty() || mes_nac_madre.getText().isEmpty() || anio_nac_madre.getText().isEmpty()) {
                    fecha_madre = anio_global + "-" + mes_global + "-" + dia_global;
                } else {
                    String dia_madre = dia_nac_madre.getText();
                    String mes_madre = mes_nac_madre.getText();
                    String anio_madre = anio_nac_madre.getText();
                    fecha_madre = anio_madre + "-" + mes_madre + "-" + dia_madre;
                }

                if (dia_nac_tutor.getText().isEmpty() || mes_nac_tutor.getText().isEmpty() || anio_nac_tutor.getText().isEmpty()) {
                    fecha_tutor = anio_global + "-" + mes_global + "-" + dia_global;
                } else {
                    String dia_tutor = dia_nac_tutor.getText();
                    String mes_tutor = mes_nac_tutor.getText();
                    String anio_tutor = anio_nac_tutor.getText();
                    fecha_tutor = anio_tutor + "-" + mes_tutor + "-" + dia_tutor;
                }
                
                
                alumnos.Update(cod_viejo,
                                /*VARIABLES ALUMNO*/ 
                    dni_alumno.getText(), apenom_alumno.getText(), fecha_alumno, domicilio_alumno.getText(), localidad_alumno.getText(), telefono_alumno.getText(), mail_alumno.getText(), codigo_alumno.getText(), 
                                /* VARIABLES PADRE */
                    dni_padre.getText(), apenom_padre.getText(), profesion_padre.getText(), fecha_padre, estado_civil_padre.getText(), domicilio_padre.getText(), localidad_padre.getText(), telefono_padre.getText(), mail_padre.getText(), convive_padre.getSelectedItem().toString(),
                                /* VARIABLES MADRE */
                    dni_madre.getText(), apenom_madre.getText(), profesion_madre.getText(), fecha_madre, estado_civil_madre.getText(), domicilio_madre.getText(), localidad_madre.getText(), telefono_madre.getText(), mail_madre.getText(), convive_madre.getSelectedItem().toString(),
                                /* VARIABLES TUTOR */
                    dni_tutor.getText(), apenom_tutor.getText(), profesion_tutor.getText(), fecha_tutor, estado_civil_tutor.getText(), domicilio_tutor.getText(), localidad_tutor.getText(), telefono_tutor.getText(), mail_tutor.getText(), convive_tutor.getSelectedItem().toString());
                
                if(Alumnos.excepcion != null){
                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "ERROR. Corrija los datos para poder modificar!", "LinneoAdmin", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Cambios Guardados correctamente!", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }else if(Seleccion == 1){ // NO GUARDAR CAMBIOS Y VOLVER A ALUMNOS
                jButton2.doClick();
            }            
        }        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //SI EL USUARIO ES VIRGINIA PERMITIR CORREGIR
        if (Principal.usuario.equals("Virginia")) {
            codigo_alumno.setEditable(true);
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mes_nac_madreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mes_nac_madreKeyReleased
        // TODO add your handling code here:
        if (mes_nac_madre.getText().length() <= 2) {
            if (mes_nac_madre.getText().length() > 0) {
                Integer dia = Integer.valueOf(mes_nac_madre.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 12) {

                    } else {
                        mes_nac_madre.setText(mes_global.toString());
                    }
                }
            }
        } else {
            mes_nac_madre.setText(mes_nac_madre.getText().substring(0, 2));
        }
    }//GEN-LAST:event_mes_nac_madreKeyReleased

    private void dia_nac_madreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dia_nac_madreKeyReleased
        // TODO add your handling code here:
        if (dia_nac_madre.getText().length() <= 2) {
            if (dia_nac_madre.getText().length() > 0) {
                Integer dia = Integer.valueOf(dia_nac_madre.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 31) {

                    } else {
                        dia_nac_madre.setText(dia_global.toString());
                    }
                }
            }
        } else {
            dia_nac_madre.setText(dia_nac_madre.getText().substring(0, 2));
        }
    }//GEN-LAST:event_dia_nac_madreKeyReleased

    private void anio_nac_madreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anio_nac_madreKeyReleased
        // TODO add your handling code here:
        if (anio_nac_madre.getText().length() <= 4) {
            if (anio_nac_alumno.getText().length() > 0) {
                Integer dia = Integer.valueOf(anio_nac_madre.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 2018) {

                    } else {
                        anio_nac_madre.setText(anio_global.toString());
                    }
                }
            }
        } else {
            anio_nac_madre.setText(anio_nac_madre.getText().substring(0, 4));
        }
    }//GEN-LAST:event_anio_nac_madreKeyReleased

    private void seleccion_tutorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_seleccion_tutorItemStateChanged
        // TODO add your handling code here:
        switch (seleccion_tutor.getSelectedItem().toString()) {
            case "Padre":

            dni_tutor.setText(dni_padre.getText());
            apenom_tutor.setText(apenom_padre.getText());
            profesion_tutor.setText(profesion_padre.getText());

            dia_nac_tutor.setText(dia_nac_padre.getText());
            mes_nac_tutor.setText(mes_nac_padre.getText());
            anio_nac_tutor.setText(anio_nac_padre.getText());

            estado_civil_tutor.setText(estado_civil_padre.getText());
            domicilio_tutor.setText(domicilio_padre.getText());
            localidad_tutor.setText(localidad_padre.getText());
            telefono_tutor.setText(telefono_padre.getText());
            mail_tutor.setText(mail_padre.getText());

            convive_tutor.setSelectedItem(convive_padre.getSelectedItem());

            break;
            case "Madre":

            dni_tutor.setText(dni_madre.getText());
            apenom_tutor.setText(apenom_madre.getText());
            profesion_tutor.setText(profesion_madre.getText());

            dia_nac_tutor.setText(dia_nac_madre.getText());
            mes_nac_tutor.setText(mes_nac_madre.getText());
            anio_nac_tutor.setText(anio_nac_madre.getText());

            estado_civil_tutor.setText(estado_civil_madre.getText());
            domicilio_tutor.setText(domicilio_madre.getText());
            localidad_tutor.setText(localidad_madre.getText());
            telefono_tutor.setText(telefono_madre.getText());
            mail_tutor.setText(mail_madre.getText());

            convive_tutor.setSelectedItem(convive_madre.getSelectedItem());

            break;
            case "Otro":

            dni_tutor.setEditable(true);
            apenom_tutor.setEditable(true);
            profesion_tutor.setEditable(true);

            dia_nac_tutor.setEditable(true);
            mes_nac_tutor.setEditable(true);
            anio_nac_tutor.setEditable(true);

            estado_civil_tutor.setEditable(true);
            domicilio_tutor.setEditable(true);
            localidad_tutor.setEditable(true);
            telefono_tutor.setEditable(true);
            mail_tutor.setEditable(true);
            convive_tutor.setEditable(true);

            break;
        }
    }//GEN-LAST:event_seleccion_tutorItemStateChanged

    private void mes_nac_tutorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mes_nac_tutorKeyReleased
        // TODO add your handling code here:
        if (mes_nac_tutor.getText().length() <= 2) {
            if (mes_nac_tutor.getText().length() > 0) {
                Integer dia = Integer.valueOf(mes_nac_tutor.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 12) {

                    } else {
                        mes_nac_tutor.setText(mes_global.toString());
                    }
                }
            }
        } else {
            mes_nac_tutor.setText(mes_nac_tutor.getText().substring(0, 2));
        }
    }//GEN-LAST:event_mes_nac_tutorKeyReleased

    private void anio_nac_tutorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anio_nac_tutorKeyReleased
        // TODO add your handling code here:
        if (anio_nac_tutor.getText().length() <= 4) {
            if (anio_nac_tutor.getText().length() > 0) {
                Integer dia = Integer.valueOf(anio_nac_tutor.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 2018) {

                    } else {
                        anio_nac_tutor.setText(anio_global.toString());
                    }
                }
            }
        } else {
            anio_nac_tutor.setText(anio_nac_tutor.getText().substring(0, 4));
        }
    }//GEN-LAST:event_anio_nac_tutorKeyReleased

    private void dia_nac_tutorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dia_nac_tutorKeyReleased
        // TODO add your handling code here:
        if (dia_nac_tutor.getText().length() <= 2) {
            if (dia_nac_tutor.getText().length() > 0) {
                Integer dia = Integer.valueOf(dia_nac_tutor.getText());

                if (dia.toString().length() > 0) {
                    if (dia <= 31) {

                    } else {
                        dia_nac_tutor.setText(dia_global.toString());
                    }
                }
            }
        } else {
            dia_nac_tutor.setText(dia_nac_tutor.getText().substring(0, 2));
        }
    }//GEN-LAST:event_dia_nac_tutorKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anio_nac_alumno;
    private javax.swing.JTextField anio_nac_madre;
    private javax.swing.JTextField anio_nac_padre;
    private javax.swing.JTextField anio_nac_tutor;
    private javax.swing.JTextField apenom_alumno;
    private javax.swing.JTextField apenom_madre;
    private javax.swing.JTextField apenom_padre;
    private javax.swing.JTextField apenom_tutor;
    private javax.swing.JTextField codigo_alumno;
    private javax.swing.JComboBox convive_madre;
    private javax.swing.JComboBox convive_padre;
    private javax.swing.JComboBox convive_tutor;
    private javax.swing.JTextField dia_nac_alumno;
    private javax.swing.JTextField dia_nac_madre;
    private javax.swing.JTextField dia_nac_padre;
    private javax.swing.JTextField dia_nac_tutor;
    private javax.swing.JTextField dni_alumno;
    private javax.swing.JTextField dni_madre;
    private javax.swing.JTextField dni_padre;
    private javax.swing.JTextField dni_tutor;
    private javax.swing.JTextField domicilio_alumno;
    private javax.swing.JTextField domicilio_madre;
    private javax.swing.JTextField domicilio_padre;
    private javax.swing.JTextField domicilio_tutor;
    private javax.swing.JTextField edad_alumno;
    private javax.swing.JTextField estado_civil_madre;
    private javax.swing.JTextField estado_civil_padre;
    private javax.swing.JTextField estado_civil_tutor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField localidad_alumno;
    private javax.swing.JTextField localidad_madre;
    private javax.swing.JTextField localidad_padre;
    private javax.swing.JTextField localidad_tutor;
    private javax.swing.JTextField mail_alumno;
    private javax.swing.JTextField mail_madre;
    private javax.swing.JTextField mail_padre;
    private javax.swing.JTextField mail_tutor;
    private javax.swing.JTextField mes_nac_alumno;
    private javax.swing.JTextField mes_nac_madre;
    private javax.swing.JTextField mes_nac_padre;
    private javax.swing.JTextField mes_nac_tutor;
    private javax.swing.JTextField profesion_madre;
    private javax.swing.JTextField profesion_padre;
    private javax.swing.JTextField profesion_tutor;
    private javax.swing.JComboBox seleccion_tutor;
    private javax.swing.JTextField telefono_alumno;
    private javax.swing.JTextField telefono_madre;
    private javax.swing.JTextField telefono_padre;
    private javax.swing.JTextField telefono_tutor;
    // End of variables declaration//GEN-END:variables
}
