/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.jDesktopPane1;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Sergio
 */
public class ModSocio extends javax.swing.JInternalFrame {

    Boolean nuevo_socio = true;
    String cod_socios = null;
    Clases.Socios socios = new Clases.Socios();
    
    @Override
    public void dispose() {
        int seleccion = JOptionPane.showOptionDialog(jframe, "Está a punto de salir. Los datos no guardados se perderán\n¿Desea Continuar?", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Salir", "Cancelar"}, "Salir");
        if (seleccion == 0) {
            manager.removeKeyEventDispatcher(key);
            super.dispose();

            ABMSocios frame = new ABMSocios();
            frame.setVisible(true);
            jDesktopPane1.add(frame);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    GregorianCalendar gc = new GregorianCalendar();
    java.util.Date dt = new java.util.Date();
    Integer anio_global = gc.get(Calendar.YEAR);
    Integer mes_global = gc.get(Calendar.MONTH);
    Integer dia_global = gc.get(Calendar.DATE);
    Integer anio_prox = anio_global + 1;
    
    String cod_viejo = null;
    
    JFrame jframe;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    
    //TECLADO ACCESOS DIRECTOS
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (jframe.isActive()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == 112) { // F1
                        jButton1.doClick();
                    }
                    if (e.getKeyCode() == 113) { // F2
                        jButton2.doClick();
                    }
                    if (e.getKeyCode() == 114) { // F3
                        jButton4.doClick();
                    }
                }
            }
            return false;
        }
    }
    
    void cargar(String nuevo){
        if(!"true".equals(nuevo)){
            // CARGAR DATOS PARA MODIFICAR
            nuevo_socio = false;
            
            Object[] info = socios.Mostrar_datos(nuevo);
            
            cod_viejo = nuevo;
            
            cod_socio.setText(info[1].toString());                          // ID_SOCIO
            dni_socio.setText(info[2].toString());                          // DNI_SOCIO
            apenom_socio.setText(info[3].toString());                       // APENOM_SOCIO
            estadocivil_socio.setSelectedItem(info[4].toString());          // ESTADO_CIVIL_SOCIO
            conyugue_socio.setText(info[5].toString());                     // NOMBRE_CONYUGUE_SOCIO
            ocupacion_socio.setText(info[6].toString());                    // OCUPACION_SOCIO
            domicilio_socio.setText(info[7].toString());                    // DOMICILIO_SOCIO
            padre_socio.setText(info[8].toString());                        // NOMBRE_PADRE_SOCIO
            madre_socio.setText(info[9].toString());                        // NOMBRE_MADRE_SOCIO
            
            // NACIMIENTO_SOCIO
            if (info[10] == "") {
                dia_nac_socio.setText("");
                mes_nac_socio.setText("");
                anio_nac_socio.setText("");
            } else {
                String dia = info[10].toString().substring(8, 10);
                dia_nac_socio.setText(dia);
                String mes = info[10].toString().substring(5, 7);
                mes_nac_socio.setText(mes);
                String anio = info[10].toString().substring(0, 4);
                anio_nac_socio.setText(anio);
            }
            
            lugar_nac_socio.setText(info[11].toString());                   // LUGAR_NACIMIENTO_SOCIO
            telefono_socio.setText(info[12].toString());                    // TELEFONO_SOCIO
            presentado1_socio.setText(info[13].toString());                 // PRESENTADO1_SOCIO
            presentado2_socio.setText(info[14].toString());                 // PRESENTADO2_SOCIO
            
            // ESTADO_SOCIO
            if("A".equals(info[15].toString())){
                jButton4.setText("F3 - DAR BAJA");
            }else{
                jButton4.setText("F3 - DAR ALTA");
            }
            
            monto_socio.setText(info[16].toString());                       // MONTO_SOCIO
            
            // COBRAR_DESDE_SOCIO
            if (info[17] == "") {
                mes_cobro_socio.setText("");
                anio_cobro_socio.setText("");
            } else {
                String mes_cobro = info[17].toString().substring(0, 2);
                mes_cobro_socio.setText(mes_cobro);
                String anio_cobro = info[17].toString().substring(3, 7);
                anio_cobro_socio.setText(anio_cobro);
            }
            
            
        }else{
            // SIGUIENTE CODIGO
            Integer codigo_ultimo = socios.CodUltimo();
            
            cod_socio.setText(String.valueOf(codigo_ultimo));
            cod_socios = String.valueOf(codigo_ultimo); 
            
            cod_socio.setEditable(false);
        }
    }
    
    public ModSocio(String nuevo) {
        initComponents();
        
        lugar_nac_socio.setText("Oberá");
        monto_socio.setText("10.00");
        
        cargar(nuevo);        
        
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

        jTextField17 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cod_socio = new javax.swing.JTextField();
        dni_socio = new javax.swing.JTextField();
        apenom_socio = new javax.swing.JTextField();
        dia_nac_socio = new javax.swing.JTextField();
        mes_nac_socio = new javax.swing.JTextField();
        anio_nac_socio = new javax.swing.JTextField();
        conyugue_socio = new javax.swing.JTextField();
        telefono_socio = new javax.swing.JTextField();
        domicilio_socio = new javax.swing.JTextField();
        lugar_nac_socio = new javax.swing.JTextField();
        estadocivil_socio = new javax.swing.JComboBox();
        padre_socio = new javax.swing.JTextField();
        madre_socio = new javax.swing.JTextField();
        presentado1_socio = new javax.swing.JTextField();
        presentado2_socio = new javax.swing.JTextField();
        ocupacion_socio = new javax.swing.JTextField();
        monto_socio = new javax.swing.JTextField();
        mes_cobro_socio = new javax.swing.JTextField();
        anio_cobro_socio = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jTextField17.setText("jTextField17");

        setIconifiable(true);
        setTitle("Alta Socio");

        jLabel1.setText("Código");

        jLabel2.setText("Apellido y Nombre");

        jLabel3.setText("Estado Civil");

        jLabel4.setText("Nombre Conyugue");

        jLabel5.setText("Ocupación");

        jLabel6.setText("Domicilio");

        jLabel7.setText("Padre");

        jLabel8.setText("Madre");

        jLabel9.setText("Lugar de Nacimiento");

        jLabel10.setText("Teléfono");

        jLabel11.setText("Presentado 1");

        jLabel12.setText("Presentado 2");

        jLabel13.setText("Monto");

        jLabel14.setText("Comienzo de Cobro");

        cod_socio.setNextFocusableComponent(dni_socio);

        dni_socio.setNextFocusableComponent(apenom_socio);

        apenom_socio.setName("letras"); // NOI18N
        apenom_socio.setNextFocusableComponent(dia_nac_socio);

        dia_nac_socio.setName("enteros"); // NOI18N
        dia_nac_socio.setNextFocusableComponent(mes_nac_socio);

        mes_nac_socio.setName("enteros"); // NOI18N
        mes_nac_socio.setNextFocusableComponent(anio_nac_socio);

        anio_nac_socio.setName("enteros"); // NOI18N
        anio_nac_socio.setNextFocusableComponent(conyugue_socio);

        conyugue_socio.setName("letras"); // NOI18N
        conyugue_socio.setNextFocusableComponent(telefono_socio);

        telefono_socio.setNextFocusableComponent(domicilio_socio);

        domicilio_socio.setNextFocusableComponent(lugar_nac_socio);

        lugar_nac_socio.setNextFocusableComponent(estadocivil_socio);

        estadocivil_socio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a" }));
        estadocivil_socio.setNextFocusableComponent(padre_socio);

        padre_socio.setName("letras"); // NOI18N
        padre_socio.setNextFocusableComponent(madre_socio);

        madre_socio.setName("letras"); // NOI18N
        madre_socio.setNextFocusableComponent(presentado1_socio);

        presentado1_socio.setName("letras"); // NOI18N
        presentado1_socio.setNextFocusableComponent(presentado2_socio);

        presentado2_socio.setName("letras"); // NOI18N
        presentado2_socio.setNextFocusableComponent(ocupacion_socio);

        ocupacion_socio.setNextFocusableComponent(monto_socio);

        monto_socio.setName("double"); // NOI18N
        monto_socio.setNextFocusableComponent(mes_cobro_socio);

        mes_cobro_socio.setNextFocusableComponent(anio_cobro_socio);

        jButton1.setText("F1 - GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("F2 - LIMPIAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel15.setText("Fecha  Nacimiento");

        jLabel16.setText("  /  ");

        jLabel17.setText("  /  ");

        jLabel18.setText("  /  ");

        jLabel19.setText("DNI");

        jButton3.setText("ESC - SALIR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("F3 - DAR BAJA");
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
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel4)
                            .addComponent(jLabel19)
                            .addComponent(jLabel1)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(domicilio_socio)
                            .addComponent(conyugue_socio)
                            .addComponent(telefono_socio)
                            .addComponent(lugar_nac_socio)
                            .addComponent(cod_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dni_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apenom_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dia_nac_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mes_nac_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(anio_nac_socio)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(estadocivil_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(padre_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(madre_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(presentado1_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(presentado2_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ocupacion_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(monto_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mes_cobro_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(anio_cobro_socio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cod_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(estadocivil_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(dni_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(padre_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(apenom_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(madre_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(dia_nac_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(mes_nac_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(anio_nac_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(presentado1_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(conyugue_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(presentado2_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(telefono_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ocupacion_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(domicilio_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lugar_nac_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(monto_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(mes_cobro_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(anio_cobro_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(nuevo_socio){
            if(/*padre_socio.getText().isEmpty() || madre_socio.getText().isEmpty() || presentado1_socio.getText().isEmpty() || presentado2_socio.getText().isEmpty() || telefono_socio.getText().isEmpty() || */apenom_socio.getText().isEmpty()
                || cod_socio.getText().isEmpty() /*|| conyugue_socio.getText().isEmpty()*/ || domicilio_socio.getText().isEmpty() || lugar_nac_socio.getText().isEmpty() || monto_socio.getText().isEmpty() || mes_cobro_socio.getText().isEmpty()
                || ocupacion_socio.getText().isEmpty()){
                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Complete los datos para continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
            }else{
                //NUEVO SOCIO
                String fecha_socio = null;
                
                if (dia_nac_socio.getText().isEmpty() || mes_nac_socio.getText().isEmpty() || anio_nac_socio.getText().isEmpty()) {
                    fecha_socio = anio_global + "-" + mes_global + "-" + dia_global;
                } else {
                    String dia_socio = dia_nac_socio.getText();
                    String mes_socio = mes_nac_socio.getText();
                    String anio_socio = anio_nac_socio.getText();
                    fecha_socio = anio_socio + "-" + mes_socio + "-" + dia_socio;
                }
                
                socios.Guardar(cod_socio.getText(),dni_socio.getText(),apenom_socio.getText(),estadocivil_socio.getSelectedItem().toString(),conyugue_socio.getText(),ocupacion_socio.getText(),domicilio_socio.getText(),
                            padre_socio.getText(),madre_socio.getText(),fecha_socio,lugar_nac_socio.getText(),telefono_socio.getText(),presentado1_socio.getText(),presentado2_socio.getText(),
                            monto_socio.getText(),mes_cobro_socio.getText() + "-" + anio_cobro_socio.getText());
                
                if(socios.excepcion!=null){
                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Ocurrio un error. Verifique los datos!", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Guardados correctamente!", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                }
                
                // cargar(cod_socio.getText());    
            }
        }else{
            if(/*padre_socio.getText().isEmpty() || madre_socio.getText().isEmpty() || presentado1_socio.getText().isEmpty() || presentado2_socio.getText().isEmpty() || telefono_socio.getText().isEmpty() || */apenom_socio.getText().isEmpty()
                || cod_socio.getText().isEmpty() /*|| conyugue_socio.getText().isEmpty()*/ || domicilio_socio.getText().isEmpty() || lugar_nac_socio.getText().isEmpty() || monto_socio.getText().isEmpty() || mes_cobro_socio.getText().isEmpty()
                || ocupacion_socio.getText().isEmpty()){
                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Complete los datos para continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
            }else{
                //ACTUALIZAR DATOS
                String fecha_socio = null;
                
                if (dia_nac_socio.getText().isEmpty() || mes_nac_socio.getText().isEmpty() || anio_nac_socio.getText().isEmpty()) {
                    fecha_socio = anio_global + "-" + mes_global + "-" + dia_global;
                } else {
                    String dia_socio = dia_nac_socio.getText();
                    String mes_socio = mes_nac_socio.getText();
                    String anio_socio = anio_nac_socio.getText();
                    fecha_socio = anio_socio + "-" + mes_socio + "-" + dia_socio;
                }
                
                socios.Actualizar(cod_viejo,
                            cod_socio.getText(),dni_socio.getText(),apenom_socio.getText(),estadocivil_socio.getSelectedItem().toString(),conyugue_socio.getText(),ocupacion_socio.getText(),domicilio_socio.getText(),
                            padre_socio.getText(),madre_socio.getText(),fecha_socio,lugar_nac_socio.getText(),telefono_socio.getText(),presentado1_socio.getText(),presentado2_socio.getText(),
                            monto_socio.getText(),mes_cobro_socio.getText() + "-" + anio_cobro_socio.getText());
                
                if(socios.excepcion!=null){
                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Ocurrio un error. Verifique los datos!", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Guardados correctamente!", "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        padre_socio.setText("");
        madre_socio.setText("");
        presentado1_socio.setText("");
        presentado2_socio.setText("");
        telefono_socio.setText("");
        apenom_socio.setText("");
        cod_socio.setText("");
        conyugue_socio.setText("");
        domicilio_socio.setText("");
        lugar_nac_socio.setText("");
        monto_socio.setText("");
        mes_cobro_socio.setText("");
        ocupacion_socio.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if("F3 - DAR BAJA".equals(jButton4.getText())){
            Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Socio N° "+cod_socio.getText()+".Elija la Opción.", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Dar de Baja", "No Guardar y Salir", "Cancelar"}, "Si");

            if(Seleccion == 0){         // DAR DE BAJA
                socios.darBaja(cod_socio.getText());
                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Dado de BAJA correctamente. Socio N° "+cod_socio.getText(), "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            }else if(Seleccion == 1){   // NO GUARDAR NADA Y SALIR
                dispose();
            }     
        }else if("F3 - DAR ALTA".equals(jButton4.getText())){
            Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Socio N° "+cod_socio.getText()+".Elija la Opción.", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Dar de Alta", "No Guardar y Salir", "Cancelar"}, "Si");

            if(Seleccion == 0){         // DAR DE ALTA
                socios.darAlta(cod_socio.getText());
                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Dado de ALTA correctamente. Socio N° "+cod_socio.getText(), "LinneoAdmin", JOptionPane.INFORMATION_MESSAGE);
            }else if(Seleccion == 1){   // NO GUARDAR NADA Y SALIR
                dispose();
            }     
        }
        
        
        cargar(cod_socio.getText()); 
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anio_cobro_socio;
    private javax.swing.JTextField anio_nac_socio;
    private javax.swing.JTextField apenom_socio;
    private javax.swing.JTextField cod_socio;
    private javax.swing.JTextField conyugue_socio;
    private javax.swing.JTextField dia_nac_socio;
    private javax.swing.JTextField dni_socio;
    private javax.swing.JTextField domicilio_socio;
    private javax.swing.JComboBox estadocivil_socio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField lugar_nac_socio;
    private javax.swing.JTextField madre_socio;
    private javax.swing.JTextField mes_cobro_socio;
    private javax.swing.JTextField mes_nac_socio;
    private javax.swing.JTextField monto_socio;
    private javax.swing.JTextField ocupacion_socio;
    private javax.swing.JTextField padre_socio;
    private javax.swing.JTextField presentado1_socio;
    private javax.swing.JTextField presentado2_socio;
    private javax.swing.JTextField telefono_socio;
    // End of variables declaration//GEN-END:variables
}
