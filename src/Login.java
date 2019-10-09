
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author reyes
 */
public class Login extends javax.swing.JFrame {

    String usuario;
    String password;
    public Connection conn;

    Usuario user = new Usuario();

    public Login() {
        initComponents();
        setTitle("Sistema Bancario");
        setExtendedState(MAXIMIZED_BOTH);

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
        txt_Usuario1 = new LIB.JTexfieldPH_FielTex();
        txt_Password1 = new LIB.JTexfieldPH_Password();
        JPanel_Login1 = new LIB.JPanelRound();
        JLabel_Login1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanelTransparente4 = new LIB.JPanelTransparente();
        JLabel_Registro1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jEImagePanel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/city-Paris-Eiffel-Tower-bokeh-Focus-blur-653955-wallhere.com.jpg"))); // NOI18N
        jEImagePanel2.setLayout(new java.awt.GridBagLayout());

        jPanelTransparente3.setColorPrimario(new java.awt.Color(222, 222, 188));
        jPanelTransparente3.setColorSecundario(new java.awt.Color(87, 60, 39));
        jPanelTransparente3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_Usuario1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Usuario1.setPlaceholder("Usuario:");
        jPanelTransparente3.add(txt_Usuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        txt_Password1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txt_Password1.setPlaceholder("Contraseña:");
        txt_Password1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_Password1KeyTyped(evt);
            }
        });
        jPanelTransparente3.add(txt_Password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        JPanel_Login1.setColorPrimario(new java.awt.Color(73, 51, 30));
        JPanel_Login1.setColorSecundario(new java.awt.Color(73, 51, 30));
        JPanel_Login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JLabel_Login1.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        JLabel_Login1.setForeground(new java.awt.Color(255, 255, 255));
        JLabel_Login1.setText("Iniciar Sesion");
        JLabel_Login1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLabel_Login1MouseClicked(evt);
            }
        });
        JPanel_Login1.add(JLabel_Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, 20));

        jPanelTransparente3.add(JPanel_Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 200, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Usuario:");
        jPanelTransparente3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sistema Bancario");
        jPanelTransparente3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, 60));

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Contraseña:");
        jPanelTransparente3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, -1, 20));

        jPanelTransparente4.setColorPrimario(new java.awt.Color(73, 51, 30));
        jPanelTransparente4.setColorSecundario(new java.awt.Color(73, 51, 30));

        JLabel_Registro1.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        JLabel_Registro1.setForeground(new java.awt.Color(255, 255, 255));
        JLabel_Registro1.setText("Registrar");
        JLabel_Registro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLabel_Registro1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTransparente4Layout = new javax.swing.GroupLayout(jPanelTransparente4);
        jPanelTransparente4.setLayout(jPanelTransparente4Layout);
        jPanelTransparente4Layout.setHorizontalGroup(
            jPanelTransparente4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
            .addGroup(jPanelTransparente4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTransparente4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JLabel_Registro1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanelTransparente4Layout.setVerticalGroup(
            jPanelTransparente4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanelTransparente4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTransparente4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JLabel_Registro1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanelTransparente3.add(jPanelTransparente4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 80, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("x");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanelTransparente3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 20, 30));

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
            .addComponent(jEImagePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jEImagePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_Password1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Password1KeyTyped

        char tecla = evt.getKeyChar();
        if (KeyEvent.VK_ENTER == tecla) {
            JLabel_Login1MouseClicked(null);
        }
    }//GEN-LAST:event_txt_Password1KeyTyped

    private void JLabel_Login1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLabel_Login1MouseClicked

        try {
            usuario = txt_Usuario1.getText().trim();
            password = txt_Password1.getText().trim();

            user.SetNombre(usuario);
            user.SetPassword(cifrarBase64(password));

            Conexion obj = new Conexion();
            conn = obj.Conexion();

            if (user.Login(user, conn)) {

                JOptionPane.showMessageDialog(this, "Sesion Iniciada", "Bienvenido(a)", JOptionPane.INFORMATION_MESSAGE);

                this.setVisible(false);

                PrincipalForm pf = new PrincipalForm();
                pf.setVisible(true);

            } else {

                JOptionPane.showMessageDialog(this, "Usuario / Contraseña Incorrectos", "Error!", JOptionPane.INFORMATION_MESSAGE);

                Login login = new Login();
                login.setVisible(true);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_JLabel_Login1MouseClicked

    private void JLabel_Registro1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLabel_Registro1MouseClicked

        NewUsuario user = new NewUsuario(this, true);
        user.setVisible(true);


    }//GEN-LAST:event_JLabel_Registro1MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        System.exit(0);
    }//GEN-LAST:event_jLabel7MouseClicked

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

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    public static String cifrarBase64(String a) {
        Base64.Encoder encoder = Base64.getEncoder();
        String b = encoder.encodeToString(a.getBytes(StandardCharsets.UTF_8));
        return b;
    }

    public static String descifrarBase64(String a) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(a);

        String b = new String(decodedByteArray);
        return b;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabel_Login1;
    private javax.swing.JLabel JLabel_Registro1;
    private LIB.JPanelRound JPanel_Login1;
    private LIB.JEImagePanel jEImagePanel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private LIB.JPanelTransparente jPanelTransparente3;
    private LIB.JPanelTransparente jPanelTransparente4;
    private LIB.JTexfieldPH_Password txt_Password1;
    private LIB.JTexfieldPH_FielTex txt_Usuario1;
    // End of variables declaration//GEN-END:variables
}
