/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas.InternalFrames;

import Dialogs.SeleccionarResponsableCementerio;
import Pantallas.Frames.Principal;
import static Pantallas.Frames.Principal.jDesktopPane1;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;


public class ModEspacios extends javax.swing.JInternalFrame {

    @Override
    public void dispose() {
        manager.removeKeyEventDispatcher(key);
        super.dispose();
        
        ABMCementerio frame = new ABMCementerio();
        frame.setVisible(true);
        jDesktopPane1.add(frame);
        try {
            frame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    Clases.Cementerio cementerio = new Clases.Cementerio();
    
    JFrame jframe;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    KeyDispatcher key = new KeyDispatcher();
    
    Boolean nuevo_espacio = null;
    String dni_difunto_viejo = null;
    
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
                        
                    }
                    if (e.getKeyCode() == 115) {
                        
                    }
                }
            }
            return false;
        }
    }
    
    
    void cargar(String[] nuevo){
        if(!"true".equals(nuevo[0])){
            //  CARGAR DATOS
            nuevo_espacio = false;
            
            dni_difunto_viejo = nuevo[0];
            
            Object[] info = cementerio.CargarDatos_espacios(nuevo[0],nuevo[1],nuevo[2]);

            for (int a = 0; a < info.length; a++) {
                if (info[a] == null) {
                    info[a] = "";
                }
            }
            
            numero_difunto.setText(info[0].toString());
            combo_bis.setSelectedItem(info[1].toString());
            zona_difunto.setText(info[2].toString());
            dni_difunto.setText(info[3].toString());
            num_socio_espacio.setText(info[4].toString());
            apenom_difunto.setText(info[5].toString());
            
            if (info[6] == "") {
                dia_nac_difunto.setText("");
                mes_nac_difunto.setText("");
                anio_nac_difunto.setText("");
            } else {
                String dia = info[6].toString().substring(8, 10);
                dia_nac_difunto.setText(dia);
                String mes = info[6].toString().substring(5, 7);
                mes_nac_difunto.setText(mes);
                String anio = info[6].toString().substring(0, 4);
                anio_nac_difunto.setText(anio);
            }
            
            if (info[7] == "") {
                dia_fall_difunto.setText("");
                mes_fall_difunto.setText("");
                anio_fall_difunto.setText("");
            } else {
                String dia = info[7].toString().substring(8, 10);
                dia_fall_difunto.setText(dia);
                String mes = info[7].toString().substring(5, 7);
                mes_fall_difunto.setText(mes);
                String anio = info[7].toString().substring(0, 4);
                anio_fall_difunto.setText(anio);
            }
            
            estadocivil_difunto.setSelectedItem(info[8].toString());
            nacionalidad_difunto.setText(info[9].toString());
            monto_cobrar_espacio.setText(info[10].toString());
            combo_estado_espacio.setSelectedItem(info[11].toString());
            
            cobrar_desde_espacio.setText(info[17].toString());
            
            // RESPONSABLE
            
            dni_responsable.setText(info[12].toString());
            apenom_responsable.setText(info[13].toString());
            direccion_responsable.setText(info[14].toString());
            telefono_responsable.setText(info[15].toString());
            estado_responsable.setText(info[16].toString());
            
        }else if("true".equals(nuevo[0])){
            //  NUEVO
            nuevo_espacio = true;
            
            
        }
    }
    
    public ModEspacios(String[] nuevo) {
        initComponents();
        
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        dni_responsable = new javax.swing.JTextField();
        apenom_responsable = new javax.swing.JTextField();
        numero_difunto = new javax.swing.JTextField();
        zona_difunto = new javax.swing.JTextField();
        dni_difunto = new javax.swing.JTextField();
        apenom_difunto = new javax.swing.JTextField();
        dia_nac_difunto = new javax.swing.JTextField();
        dia_fall_difunto = new javax.swing.JTextField();
        nacionalidad_difunto = new javax.swing.JTextField();
        mes_nac_difunto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        anio_nac_difunto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        mes_fall_difunto = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        anio_fall_difunto = new javax.swing.JTextField();
        estadocivil_difunto = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        combo_bis = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        direccion_responsable = new javax.swing.JTextField();
        telefono_responsable = new javax.swing.JTextField();
        estado_responsable = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        num_socio_espacio = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        combo_estado_espacio = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        monto_cobrar_espacio = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cobrar_desde_espacio = new javax.swing.JTextField();

        setIconifiable(true);
        setTitle("Espacios");

        jLabel1.setText("Número");

        jLabel2.setText("Zona");

        jLabel3.setText("DNI");

        jLabel4.setText("Apellido y Nombre");

        jLabel5.setText("Fecha Nacimiento");

        jLabel6.setText("Datos del Difunto:");

        jLabel7.setText("Datos del Responsable:");

        jLabel8.setText("Fecha Fallecimiento");

        jLabel9.setText("Estado Civil");

        jLabel11.setText("Nacionalidad");

        jLabel12.setText("DNI");

        jLabel13.setText("Apellido y Nombre");

        dni_responsable.setNextFocusableComponent(apenom_responsable);
        dni_responsable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dni_responsableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dni_responsableKeyReleased(evt);
            }
        });

        apenom_responsable.setEditable(false);
        apenom_responsable.setNextFocusableComponent(jButton1);

        numero_difunto.setNextFocusableComponent(combo_bis);

        zona_difunto.setNextFocusableComponent(dni_difunto);

        dni_difunto.setNextFocusableComponent(apenom_difunto);

        apenom_difunto.setNextFocusableComponent(dia_nac_difunto);

        dia_nac_difunto.setNextFocusableComponent(mes_nac_difunto);

        dia_fall_difunto.setNextFocusableComponent(mes_fall_difunto);

        nacionalidad_difunto.setNextFocusableComponent(dni_responsable);

        mes_nac_difunto.setNextFocusableComponent(anio_nac_difunto);

        jLabel10.setText("  /  ");

        jLabel15.setText("  /  ");

        anio_nac_difunto.setNextFocusableComponent(dia_fall_difunto);

        jLabel16.setText("  /  ");

        mes_fall_difunto.setNextFocusableComponent(anio_fall_difunto);

        jLabel17.setText("  /  ");

        anio_fall_difunto.setNextFocusableComponent(estadocivil_difunto);

        estadocivil_difunto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a" }));
        estadocivil_difunto.setNextFocusableComponent(nacionalidad_difunto);

        jButton2.setText("F2 - VOLVER AL LISTADO ESPACIOS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("F1 - GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel14.setText("Bis");

        combo_bis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Si" }));
        combo_bis.setNextFocusableComponent(zona_difunto);

        jLabel18.setText("Dirección");

        jLabel19.setText("Teléfono");

        jLabel20.setText("Estado");

        direccion_responsable.setEditable(false);

        telefono_responsable.setEditable(false);

        estado_responsable.setEditable(false);

        jLabel21.setText("Num Socio");

        jLabel22.setText("Estado");

        combo_estado_espacio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B" }));

        jLabel23.setText("Monto a Cobrar");

        jLabel24.setText("Cobrar desde");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel8)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel14)
                                .addComponent(jLabel1)
                                .addComponent(jLabel9)
                                .addComponent(jLabel11))
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(256, 256, 256))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combo_bis, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numero_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(dni_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel20))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(apenom_responsable)
                                            .addComponent(direccion_responsable)
                                            .addComponent(telefono_responsable, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                            .addComponent(estado_responsable, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(apenom_difunto)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dia_nac_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(mes_nac_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(anio_nac_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dia_fall_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(mes_fall_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(anio_fall_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(estadocivil_difunto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nacionalidad_difunto)
                                    .addComponent(zona_difunto)
                                    .addComponent(dni_difunto))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(num_socio_espacio)
                            .addComponent(combo_estado_espacio, 0, 100, Short.MAX_VALUE)
                            .addComponent(monto_cobrar_espacio)
                            .addComponent(cobrar_desde_espacio))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(numero_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(combo_bis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dni_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(apenom_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(zona_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(direccion_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dni_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(telefono_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apenom_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel20)
                    .addComponent(estado_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dia_nac_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(mes_nac_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(anio_nac_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(dia_fall_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(mes_fall_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(anio_fall_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(estadocivil_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(nacionalidad_difunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(num_socio_espacio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(combo_estado_espacio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(monto_cobrar_espacio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(cobrar_desde_espacio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(nuevo_espacio){
            // INSERTAR
            if(numero_difunto.getText().isEmpty() || zona_difunto.getText().isEmpty() || dni_difunto.getText().isEmpty() || num_socio_espacio.getText().isEmpty() || apenom_difunto.getText().isEmpty() || dia_nac_difunto.getText().isEmpty()
                || mes_nac_difunto.getText().isEmpty() || anio_nac_difunto.getText().isEmpty() || dia_fall_difunto.getText().isEmpty() || mes_fall_difunto.getText().isEmpty() || anio_fall_difunto.getText().isEmpty() || nacionalidad_difunto.getText().isEmpty()
                || dni_responsable.getText().isEmpty() || apenom_responsable.getText().isEmpty() || direccion_responsable.getText().isEmpty() || telefono_responsable.getText().isEmpty() || estado_responsable.getText().isEmpty() || cobrar_desde_espacio.getText().isEmpty()){
                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Complete los datos para continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
            }else{
                //NUEVO ESPACIO
                String fecha_nacimiento = null;
                String fecha_fallecimiento = null;
                
                String dia_nacimiento = dia_nac_difunto.getText();
                String mes_nacimiento = mes_nac_difunto.getText();
                String anio_nacimiento = anio_nac_difunto.getText();
                
                fecha_nacimiento = anio_nacimiento + "-" + mes_nacimiento + "-" + dia_nacimiento;
                
                String dia_fallecimiento = dia_fall_difunto.getText();
                String mes_fallecimiento = mes_fall_difunto.getText();
                String anio_fallecimiento = anio_fall_difunto.getText();
                
                fecha_fallecimiento = anio_fallecimiento + "-" + mes_fallecimiento + "-" + dia_fallecimiento;
                
                String bis = combo_bis.getSelectedItem().toString();
                
                if("No".equals(bis)){
                    bis = "0";
                }else if("Si".equals(bis)){
                    bis = "1";
                }
                
                cementerio.InsertarEspacio(numero_difunto.getText(),bis,zona_difunto.getText(),dni_difunto.getText(),num_socio_espacio.getText(),apenom_difunto.getText(),fecha_nacimiento,fecha_fallecimiento,
                            estadocivil_difunto.getSelectedItem().toString(),nacionalidad_difunto.getText(),monto_cobrar_espacio.getText(),combo_estado_espacio.getSelectedItem().toString(),
                            dni_responsable.getText(),cobrar_desde_espacio.getText());
                
                
                Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Guardados correctamente!", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Agregar Nuevo", "Salir"}, "Si");

                if (Seleccion == 0) { // AGREGAR NUEVO   

                    nuevo_espacio = true;

                    numero_difunto.setText("");
                    combo_bis.setSelectedItem("No");
                    zona_difunto.setText("");
                    dni_difunto.setText("");
                    num_socio_espacio.setText("");
                    apenom_difunto.setText("");

                    dia_nac_difunto.setText("");
                    mes_nac_difunto.setText("");
                    anio_nac_difunto.setText("");

                    dia_fall_difunto.setText("");
                    mes_fall_difunto.setText("");
                    anio_fall_difunto.setText("");

                    estadocivil_difunto.setSelectedItem("Soltero/a");
                    nacionalidad_difunto.setText("");
                    monto_cobrar_espacio.setText("");
                    combo_estado_espacio.setSelectedItem("A");
                    
                    cobrar_desde_espacio.setText("");

                    // RESPONSABLE
                    dni_responsable.setText("");
                    apenom_responsable.setText("");
                    direccion_responsable.setText("");
                    telefono_responsable.setText("");
                    estado_responsable.setText("");
                }
                if (Seleccion == 1) { // VOLVER LISTA DE ESPACIOS
                    dispose();
                }
            }            
        }else{
            // MODIFICAR 
            if(numero_difunto.getText().isEmpty() || zona_difunto.getText().isEmpty() || dni_difunto.getText().isEmpty() || num_socio_espacio.getText().isEmpty() || apenom_difunto.getText().isEmpty() || dia_nac_difunto.getText().isEmpty()
                || mes_nac_difunto.getText().isEmpty() || anio_nac_difunto.getText().isEmpty() || dia_fall_difunto.getText().isEmpty() || mes_fall_difunto.getText().isEmpty() || anio_fall_difunto.getText().isEmpty() || nacionalidad_difunto.getText().isEmpty()
                || dni_responsable.getText().isEmpty() || apenom_responsable.getText().isEmpty() || direccion_responsable.getText().isEmpty() || telefono_responsable.getText().isEmpty() || estado_responsable.getText().isEmpty() || cobrar_desde_espacio.getText().isEmpty()){
                JOptionPane.showMessageDialog(Principal.jDesktopPane1.getSelectedFrame(), "Complete los datos para continuar.", "LinneoAdmin", JOptionPane.WARNING_MESSAGE);
            }else{
                //NUEVO ESPACIO
                String fecha_nacimiento = null;
                String fecha_fallecimiento = null;
                
                String dia_nacimiento = dia_nac_difunto.getText();
                String mes_nacimiento = mes_nac_difunto.getText();
                String anio_nacimiento = anio_nac_difunto.getText();
                
                fecha_nacimiento = anio_nacimiento + "-" + mes_nacimiento + "-" + dia_nacimiento;
                
                String dia_fallecimiento = dia_fall_difunto.getText();
                String mes_fallecimiento = mes_fall_difunto.getText();
                String anio_fallecimiento = anio_fall_difunto.getText();
                
                fecha_fallecimiento = anio_fallecimiento + "-" + mes_fallecimiento + "-" + dia_fallecimiento;
                
                String bis = combo_bis.getSelectedItem().toString();
                
                if("No".equals(bis)){
                    bis = "0";
                }else if("Si".equals(bis)){
                    bis = "1";
                }
                
                cementerio.InsertarEspacio(numero_difunto.getText(),bis,zona_difunto.getText(),dni_difunto.getText(),num_socio_espacio.getText(),apenom_difunto.getText(),fecha_nacimiento,fecha_fallecimiento,
                            estadocivil_difunto.getSelectedItem().toString(),nacionalidad_difunto.getText(),monto_cobrar_espacio.getText(),combo_estado_espacio.getSelectedItem().toString(),
                            dni_responsable.getText(),cobrar_desde_espacio.getText());
                
                
                Integer Seleccion = JOptionPane.showOptionDialog(Principal.jDesktopPane1.getSelectedFrame(), "Guardados correctamente!", "LinneoAdmin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Agregar Nuevo", "Salir"}, "Si");

                if (Seleccion == 0) { // AGREGAR NUEVO   

                    nuevo_espacio = true;

                    numero_difunto.setText("");
                    combo_bis.setSelectedItem("No");
                    zona_difunto.setText("");
                    dni_difunto.setText("");
                    num_socio_espacio.setText("");
                    apenom_difunto.setText("");

                    dia_nac_difunto.setText("");
                    mes_nac_difunto.setText("");
                    anio_nac_difunto.setText("");

                    dia_fall_difunto.setText("");
                    mes_fall_difunto.setText("");
                    anio_fall_difunto.setText("");

                    estadocivil_difunto.setSelectedItem("Soltero/a");
                    nacionalidad_difunto.setText("");
                    monto_cobrar_espacio.setText("");
                    combo_estado_espacio.setSelectedItem("A");
                    
                    cobrar_desde_espacio.setText("");

                    // RESPONSABLE
                    dni_responsable.setText("");
                    apenom_responsable.setText("");
                    direccion_responsable.setText("");
                    telefono_responsable.setText("");
                    estado_responsable.setText("");
                }
                if (Seleccion == 1) { // VOLVER LISTA DE ESPACIOS
                    dispose();
                }
            }
        }        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dni_responsableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dni_responsableKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_dni_responsableKeyReleased

    private void dni_responsableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dni_responsableKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
            String num_cuenta = dni_responsable.getText();

            // SI ENCONTRO LA CUENTA O NO
            Boolean encontro = cementerio.ComprobarExistente(num_cuenta);

            if (encontro == false) {
                SeleccionarResponsableCementerio dialog = new SeleccionarResponsableCementerio(jframe, true);
                String[] responsable_encontrada = dialog.showDialog();

                if (responsable_encontrada != null) {
                    dni_responsable.setText(responsable_encontrada[0]);
                    
                    String info[] = cementerio.MostrarDatos_Responsable(responsable_encontrada[0]);
                    
                    apenom_responsable.setText(info[2]);
                    direccion_responsable.setText(info[3]);
                    telefono_responsable.setText(info[4]);
                    estado_responsable.setText(info[5]);
                }else{
                    evt.consume();    
                }
            }else{
                dni_responsable.setText(cementerio.responsable_encontrado[1]);
                apenom_responsable.setText(cementerio.responsable_encontrado[2]);
                direccion_responsable.setText(cementerio.responsable_encontrado[3]);
                telefono_responsable.setText(cementerio.responsable_encontrado[4]);
                estado_responsable.setText(cementerio.responsable_encontrado[5]);
            }
        }
    }//GEN-LAST:event_dni_responsableKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anio_fall_difunto;
    private javax.swing.JTextField anio_nac_difunto;
    private javax.swing.JTextField apenom_difunto;
    private javax.swing.JTextField apenom_responsable;
    private javax.swing.JTextField cobrar_desde_espacio;
    private javax.swing.JComboBox combo_bis;
    private javax.swing.JComboBox combo_estado_espacio;
    private javax.swing.JTextField dia_fall_difunto;
    private javax.swing.JTextField dia_nac_difunto;
    private javax.swing.JTextField direccion_responsable;
    private javax.swing.JTextField dni_difunto;
    private javax.swing.JTextField dni_responsable;
    private javax.swing.JTextField estado_responsable;
    private javax.swing.JComboBox estadocivil_difunto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField mes_fall_difunto;
    private javax.swing.JTextField mes_nac_difunto;
    private javax.swing.JTextField monto_cobrar_espacio;
    private javax.swing.JTextField nacionalidad_difunto;
    private javax.swing.JTextField num_socio_espacio;
    private javax.swing.JTextField numero_difunto;
    private javax.swing.JTextField telefono_responsable;
    private javax.swing.JTextField zona_difunto;
    // End of variables declaration//GEN-END:variables
}
