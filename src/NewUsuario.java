
import Validaciones.validaciones;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author reyes
 */
public class NewUsuario extends javax.swing.JDialog {

    private DatagramSocket socket;
    Direccion_IP ip = new Direccion_IP();
    private validaciones v;

    public NewUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        v = new validaciones();
        this.setSize(this.getToolkit().getScreenSize());

        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(NewUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jEImagePanel2 = new LIB.JEImagePanel();
        jPanelTransparente3 = new LIB.JPanelTransparente();
        txt_Perfil_New_User = new LIB.JTexfieldPH_FielTex();
        txt_Password = new LIB.JTexfieldPH_Password();
        JPanel_Login1 = new LIB.JPanelRound();
        JLabel_Login1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_Confir_Password = new LIB.JTexfieldPH_Password();
        jLabel9 = new javax.swing.JLabel();
        txt_Nombre_New_User = new LIB.JTexfieldPH_FielTex();
        jLabel10 = new javax.swing.JLabel();
        txt_Ap_New_User = new LIB.JTexfieldPH_FielTex();
        jLabel11 = new javax.swing.JLabel();
        txt_Am_New_User = new LIB.JTexfieldPH_FielTex();
        jLabel12 = new javax.swing.JLabel();
        txt_Telefono_New_User = new LIB.JTexfieldPH_FielTex();
        jLabel13 = new javax.swing.JLabel();
        txt_Email_New_User = new LIB.JTexfieldPH_FielTex();
        txt_Domicilio_New_User = new LIB.JTexfieldPH_FielTex();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jEImagePanel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/city-Paris-Eiffel-Tower-bokeh-Focus-blur-653955-wallhere.com.jpg"))); // NOI18N
        jEImagePanel2.setLayout(new java.awt.GridBagLayout());

        jPanelTransparente3.setColorPrimario(new java.awt.Color(222, 222, 188));
        jPanelTransparente3.setColorSecundario(new java.awt.Color(87, 60, 39));
        jPanelTransparente3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_Perfil_New_User.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Perfil_New_User.setPlaceholder("Perfil:");
        jPanelTransparente3.add(txt_Perfil_New_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        txt_Password.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Password.setPlaceholder("Contraseña:");
        jPanelTransparente3.add(txt_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        JPanel_Login1.setColorPrimario(new java.awt.Color(73, 51, 30));
        JPanel_Login1.setColorSecundario(new java.awt.Color(73, 51, 30));
        JPanel_Login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JLabel_Login1.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        JLabel_Login1.setForeground(new java.awt.Color(255, 255, 255));
        JLabel_Login1.setText("Guardar");
        JLabel_Login1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLabel_Login1MouseClicked(evt);
            }
        });
        JPanel_Login1.add(JLabel_Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 70, 20));

        jPanelTransparente3.add(JPanel_Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 510, 200, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pefil");
        jPanelTransparente3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nuevo Usuario");
        jPanelTransparente3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, 60));

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Contraseña:");
        jPanelTransparente3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, -1, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("x");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanelTransparente3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 20, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Confirmar Contraseña:");
        jPanelTransparente3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, -1, 20));

        txt_Confir_Password.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Confir_Password.setPlaceholder("Confirmar Contraseña:");
        txt_Confir_Password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Confir_PasswordFocusLost(evt);
            }
        });
        jPanelTransparente3.add(txt_Confir_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nombre:");
        jPanelTransparente3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, -1, 30));

        txt_Nombre_New_User.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Nombre_New_User.setPlaceholder("Nombre:");
        jPanelTransparente3.add(txt_Nombre_New_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Apellido Paterno:");
        jPanelTransparente3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, 30));

        txt_Ap_New_User.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Ap_New_User.setPlaceholder("Apellido Paterno:");
        jPanelTransparente3.add(txt_Ap_New_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Apellido Materno:");
        jPanelTransparente3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, -1, 30));

        txt_Am_New_User.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Am_New_User.setPlaceholder("Apellido Materno:");
        jPanelTransparente3.add(txt_Am_New_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Telefono:");
        jPanelTransparente3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 250, -1, 30));

        txt_Telefono_New_User.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Telefono_New_User.setPlaceholder("Telefono:");
        jPanelTransparente3.add(txt_Telefono_New_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Email:");
        jPanelTransparente3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, -1, 30));

        txt_Email_New_User.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Email_New_User.setPlaceholder("Email:");
        jPanelTransparente3.add(txt_Email_New_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, -1, -1));

        txt_Domicilio_New_User.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Domicilio_New_User.setPlaceholder("Domicilio:");
        jPanelTransparente3.add(txt_Domicilio_New_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, 430, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Domicilio:");
        jPanelTransparente3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, -1, 30));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 92;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jEImagePanel2.add(jPanelTransparente3, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jEImagePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jEImagePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JLabel_Login1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLabel_Login1MouseClicked

        try {

            String nombre = v.reemplazar_espacios(txt_Nombre_New_User);
            String perfil = v.reemplazar_espacios(txt_Perfil_New_User);
            String psw = v.reemplazar_espacios(txt_Password);
            String a_paterno = v.reemplazar_espacios(txt_Ap_New_User);
            String a_materno = v.reemplazar_espacios(txt_Am_New_User);
            String telefono = v.reemplazar_espacios(txt_Telefono_New_User);
            String email = v.reemplazar_espacios(txt_Email_New_User);
            String domicilio = v.reemplazar_espacios(txt_Domicilio_New_User);

            String mensaje = "NewUser " + nombre + " " + perfil + " " + psw + " " + a_paterno + " " + a_materno + " " + telefono + " " + email + " " + domicilio + " ";
            byte datos[] = mensaje.getBytes();
            JOptionPane.showMessageDialog(null, mensaje);
            //crear enviarPaquete

            DatagramPacket snd = ip.Direccion(datos);
            socket.send(snd);//enviar paquete
        } catch (IOException exceptionES) {
            exceptionES.printStackTrace();
        }
        try {
            socket = new DatagramSocket();
        } catch (SocketException excepcionSocket) {
            excepcionSocket.printStackTrace();
            System.exit(1);
        }

    }//GEN-LAST:event_JLabel_Login1MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        this.dispose();

    }//GEN-LAST:event_jLabel7MouseClicked

    private void txt_Confir_PasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Confir_PasswordFocusLost

        String pass = txt_Password.getText().trim();
        String conf_pass = txt_Confir_Password.getText().trim();

        if (!pass.equals(conf_pass)) {

            JOptionPane.showMessageDialog(this, "Las Contraseñas no Coinciden!", "Error!", JOptionPane.INFORMATION_MESSAGE);

        }

    }//GEN-LAST:event_txt_Confir_PasswordFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(NewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewUsuario dialog = new NewUsuario(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabel_Login1;
    private LIB.JPanelRound JPanel_Login1;
    private LIB.JEImagePanel jEImagePanel2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private LIB.JPanelTransparente jPanelTransparente3;
    private LIB.JTexfieldPH_FielTex txt_Am_New_User;
    private LIB.JTexfieldPH_FielTex txt_Ap_New_User;
    private LIB.JTexfieldPH_Password txt_Confir_Password;
    private LIB.JTexfieldPH_FielTex txt_Domicilio_New_User;
    private LIB.JTexfieldPH_FielTex txt_Email_New_User;
    private LIB.JTexfieldPH_FielTex txt_Nombre_New_User;
    private LIB.JTexfieldPH_Password txt_Password;
    private LIB.JTexfieldPH_FielTex txt_Perfil_New_User;
    private LIB.JTexfieldPH_FielTex txt_Telefono_New_User;
    // End of variables declaration//GEN-END:variables
}
