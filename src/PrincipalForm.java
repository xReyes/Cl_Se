
import DTO.Clientes_DTO;
import DTO.CuentasDTO;
import Validaciones.validaciones;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author reyes
 */
public class PrincipalForm extends javax.swing.JFrame implements Runnable {

    Clientes_DTO dto;
    Conexion conn;

    String nombre_Cliente;
    String buscar_Clientes;
    String otro_seguro;
    String buscar_Banco;
    String buscar_Seguro;
    String buscar_usuario;
    String buscar_movimiento;

    private DatagramSocket socket;
    Direccion_IP ip = new Direccion_IP();
    validaciones v = new validaciones();

    String hora, minutos, segundos, ampm;
    Calendar calendario;
    Thread h1;

    Date now = new Date(System.currentTimeMillis());

    public PrincipalForm() {
        try {
            initComponents();

            socket = new DatagramSocket();
            dto = new Clientes_DTO();
            conn = new Conexion();

            setLocationRelativeTo(null);
            setTitle("Sistema Bancario");
            setResizable(false);

            Generar_ID();
            Generar_ID_cliente();
            Generar_Datos_Seguros();
            fecha();
            llenarComboNC();

            JLabel_N_Cuenta.setText(Usuario.id_usuario);

            txt_fm.setText(String.valueOf(now));

            groupSexo_Cliente.add(rad_Masculino);
            groupSexo_Cliente.add(rad_Femenino);

            h1 = new Thread(this);
            h1.start();

            txt_id_cliente.setVisible(false);
            JLabel_N_Cuenta.setVisible(false);

            v.validar_Solo_Letras(txt_Nombre);
            v.validar_Solo_Letras(txt_Ap_Paterno);
            v.validar_Solo_Letras(txt_Ap_Materno);
            v.validar_Solo_Numeros(txt_Telefono);
            v.validar_Solo_Numeros(txt_monto_seguro);

            validarMovimientos();

        } catch (SocketException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void validarMovimientos() {

        v.validar_Solo_Letras(txt_tm);
        v.validar_Solo_Numeros(txt_s);
        v.validar_Solo_Numeros(txt_buscar_id);
        v.validar_Punto(txt_s);

    }

    private void llenarComboNC() {

        try {

            consultarNC(conn.Conexion());

        } catch (SQLException e) {
        }
    }

    public void consultarNC(Connection conn) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement("SELECT CONCAT (cuenta.n_cuenta, ' - ', a_paterno, ' ', a_materno, ' ', nombre_cliente ) AS RESULTADOS FROM clientes INNER JOIN cuenta ON clientes.id_clientes = cuenta.id_cliente WHERE estado = 'Activo'");
        rs = ps.executeQuery();

        while (rs.next()) {

            String resultado = (rs.getString(1));

            combo_cd.addItem(resultado);

        }
    }

    public String sexo_Cliente() {

        String sexo = "";

        if (rad_Masculino.isSelected()) {

            sexo = "Masculino";

        } else if (rad_Femenino.isSelected()) {

            sexo = "Femenino";

        }

        return sexo;

    }

    private void eventos(DefaultMutableTreeNode nodo) {

        try {

            if (nodo.getUserObject().equals("Agregar Nueva Cuenta")) {
                JTabbedPrincipal.setSelectedIndex(3);

            }
            if (nodo.getUserObject().equals("Movimientos")) {
                JTabbedPrincipal.setSelectedIndex(4);

            }
            if (nodo.getUserObject().equals("Agregar Nuevo Cliente")) {
                JTabbedPrincipal.setSelectedIndex(1);

            }
            if (nodo.getUserObject().equals("Agregar Nuevo Banco")) {
                JTabbedPrincipal.setSelectedIndex(6);

            }
            if (nodo.getUserObject().equals("Agregar Nueva Empresa")) {
                JTabbedPrincipal.setSelectedIndex(5);

            }
            if (nodo.getUserObject().equals("Ver Nomina")) {
                btn_nominaActionPerformed(null);

            }
            if (nodo.getUserObject().equals("Agregar Nuevo Seguro")) {
                JTabbedPrincipal.setSelectedIndex(8);

            }
        } catch (NullPointerException e) {
        }
    }

    public void limpiar_Campos(JTextField campo) {

        campo.setText("");

    }

    public void limpiar_Seguros() {

        Combo_Empresas_Seguros.setSelectedIndex(0);
        Combo_Cuenta_Seguros.setSelectedIndex(0);
        Combo_Tipo_Seguro.setSelectedIndex(0);

        limpiar_Campos(txt_monto_seguro);
        limpiar_Campos(txt_idSeguro_Buscar_Seguros);

    }

    public void limpar_Cliente() {

        limpiar_Campos(txt_Nombre);
        limpiar_Campos(txt_Ap_Paterno);
        limpiar_Campos(txt_Ap_Materno);
        limpiar_Campos(txt_Direccion);
        limpiar_Campos(txt_Email);
        limpiar_Campos(txt_Telefono);
        limpiar_Campos(txt_nombre_Buscar);
        limpiar_Campos(txt_id_cliente);

        groupSexo_Cliente.clearSelection();
        combo_Pais.setSelectedIndex(0);
        combo_Tipo_Cuenta.setSelectedIndex(0);

    }

    public void limpiar_Movimiento() {

        limpiar_Campos(txt_id);
        limpiar_Campos(txt_tm);
        txt_fm.setText(String.valueOf(now));
        limpiar_Campos(txt_s);
        limpiar_Campos(txt_buscar_id);

        Generar_ID();

    }

    public void limpar_Usuarios() {

        limpiar_Campos(usuario_txt_nombre2);
        limpiar_Campos(usuario_txt_parteno1);
        limpiar_Campos(usuario_txt_materno);
        limpiar_Campos(usuario_txt_telefono1);
        limpiar_Campos(usuario_txt_email1);
        limpiar_Campos(usuario_txt_domicilio);

    }

    public void ID_AUTO(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement("SELECT MAX(id_movimiento) AS id_movimiento " + "FROM movimientos");
        rs = ps.executeQuery();
        if (rs.next()) {
            int id = (rs.getInt(1) + 1);

            txt_id.setText(String.valueOf(id));
        }
    }

    public void Generar_ID_Cliente(Connection conn) throws SQLException {

        PreparedStatement stm1 = conn.prepareStatement("SELECT CONCAT(id_clientes,'_',nombre_Cliente,'_',a_paterno, '_',a_materno, ' ') AS id_nombre FROM clientes WHERE estado = 'Activo';");
        ResultSet rs = null;
        rs = stm1.executeQuery();

        Combo_Cliente_Banco.addItem("Selecciona...");

        while (rs.next()) {

            Combo_Cliente_Banco.addItem(rs.getString("id_nombre"));

        }

    }

    private void Generar_ID() {

        try {

            ID_AUTO(conn.Conexion());

        } catch (SQLException e) {
        }
    }

    private void Generar_ID_cliente() {

        try {
            Generar_ID_Cliente(conn.Conexion());
        } catch (SQLException ex) {
        }

    }

    private void Generar_Datos_Seguros() {

        try {

            Genener_ID_Empresas_Seguros(conn.Conexion());
            Genener_N_Cuenta_Seguros(conn.Conexion());

        } catch (SQLException e) {
        }

    }

    public void Genener_ID_Empresas_Seguros(Connection conn) throws SQLException {

        PreparedStatement stm1 = conn.prepareStatement("SELECT CONCAT(id_empresa, '_', representante, ' ', sucursal, ' ', direccion) AS nombre_Empresa "
                + "FROM empresa INNER JOIN banco ON empresa.id_banco = banco.id_banco;");
        ResultSet rs = null;
        rs = stm1.executeQuery();

        Combo_Empresas_Seguros.addItem("Selecciona...");

        while (rs.next()) {

            Combo_Empresas_Seguros.addItem(rs.getString("nombre_Empresa"));

        }

    }

    public void Genener_N_Cuenta_Seguros(Connection conn) throws SQLException {

        PreparedStatement stm1 = conn.prepareStatement("SELECT CONCAT(n_cuenta, '_', CONCAT(nombre_Cliente,' ', a_paterno, ' ', a_materno)) AS N_Cuenta "
                + "FROM cuenta INNER JOIN clientes ON cuenta.id_cliente = clientes.id_clientes;");
        ResultSet rs = null;
        rs = stm1.executeQuery();

        Combo_Cuenta_Seguros.addItem("Selecciona...");

        while (rs.next()) {

            Combo_Cuenta_Seguros.addItem(rs.getString("N_Cuenta"));

        }

    }

    private void fecha() {

        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");

        txt_fm.setText(String.valueOf(now));
    }

    public void limpiar_Banco() {

        limpiar_Campos(txt_Sucursal_Banco);
        limpiar_Campos(txt_Direccion_Banco);
        limpiar_Campos(txt_Telefono_Banco);
        limpiar_Campos(txt_id_Buscar_Banco);

        Combo_Cliente_Banco.setSelectedIndex(0);

    }

    public String Generar_Correo_Cuenta(Connection conn) throws SQLException {

        PreparedStatement stm1 = conn.prepareStatement("SELECT email "
                + "FROM datos_usuarios INNER JOIN cuenta ON cuenta.id_usuario = datos_usuarios.id_usuarios "
                + "WHERE n_cuenta = '" + JLabel_N_Cuenta.getText() + "';");
        ResultSet rs = null;
        rs = stm1.executeQuery();
        String email = "";

        if (rs.next()) {

            email = rs.getString("email");

        }

        return email;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupSexo_Cliente = new javax.swing.ButtonGroup();
        JTabbedPrincipal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTree_Inicio = new javax.swing.JTree();
        btn_movimientos = new javax.swing.JButton();
        btn_cuentasRegistradas = new javax.swing.JButton();
        btn_nomina = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        JLabel_Hora = new javax.swing.JLabel();
        JLabel_N_Cuenta = new javax.swing.JLabel();
        JPanelClientes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_Nuevo_Cliente = new javax.swing.JButton();
        btn_Guardar_Cliente = new javax.swing.JButton();
        btn_Buscar_Cliente = new javax.swing.JButton();
        btn_Cancelar_Cliente = new javax.swing.JButton();
        btn_Eliminar_Cliente = new javax.swing.JButton();
        txt_Email = new javax.swing.JTextField();
        txt_nombre_Buscar = new javax.swing.JTextField();
        txt_Ap_Paterno = new javax.swing.JTextField();
        txt_Ap_Materno = new javax.swing.JTextField();
        txt_Direccion = new javax.swing.JTextField();
        txt_Telefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        rad_Femenino = new javax.swing.JRadioButton();
        rad_Masculino = new javax.swing.JRadioButton();
        combo_Tipo_Cuenta = new javax.swing.JComboBox<>();
        combo_Pais = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txt_Nombre = new javax.swing.JTextField();
        btn_Editar_Cliente = new javax.swing.JButton();
        txt_id_cliente = new javax.swing.JTextField();
        JPanel_Usuarios = new javax.swing.JPanel();
        usuario_label_id1 = new javax.swing.JLabel();
        usuario_txt_id1 = new javax.swing.JTextField();
        usuario_label_nombre2 = new javax.swing.JLabel();
        usuario_txt_nombre2 = new javax.swing.JTextField();
        usuario_label_paterno1 = new javax.swing.JLabel();
        usuario_txt_parteno1 = new javax.swing.JTextField();
        usuario_label_materno = new javax.swing.JLabel();
        usuario_txt_materno = new javax.swing.JTextField();
        usuario_label_domicilio = new javax.swing.JLabel();
        usuario_txt_domicilio = new javax.swing.JTextField();
        usuario_label_email1 = new javax.swing.JLabel();
        usuario_txt_email1 = new javax.swing.JTextField();
        usuario_txt_telefono1 = new javax.swing.JTextField();
        usuario_label_telefono1 = new javax.swing.JLabel();
        usuario_label_buscar = new javax.swing.JLabel();
        usuario_txt_buscar = new javax.swing.JTextField();
        usuarios_jButton_buscar = new javax.swing.JButton();
        usuarios_jButton_nuevo = new javax.swing.JButton();
        usuarios_jButton_guardar = new javax.swing.JButton();
        usuarios_jButton_cancelar = new javax.swing.JButton();
        usuarios_jButton_editar = new javax.swing.JButton();
        usuarios_jButton_eliminar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldIdClienteCuenta = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldIdUsuarioCuenta = new javax.swing.JTextField();
        jTextFieldBuscarCuenta = new javax.swing.JTextField();
        jButtonBuscarCuenta = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldNoDeCuenta = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldFechaAperturaCuenta = new javax.swing.JTextField();
        jComboBoxTipoCuenta = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldSaldoAperturaCuenta = new javax.swing.JTextField();
        jButtonGuardarCuenta = new javax.swing.JButton();
        jButtonEditarCuenta = new javax.swing.JButton();
        jButtonEliminarCuenta = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txt_buscar_id = new javax.swing.JTextField();
        btn_Buscar_Movimiento = new javax.swing.JButton();
        combo_nc = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        combo_cd = new javax.swing.JComboBox<>();
        txt_tm = new javax.swing.JTextField();
        txt_fm = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txt_s = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        btn_Nuevo_Movimiento = new javax.swing.JButton();
        btn_Guardar_Movimiento = new javax.swing.JButton();
        btn_Cancelar_Movimiento = new javax.swing.JButton();
        btn_Editar_Movimiento = new javax.swing.JButton();
        btn_Eliminar_Movimiento = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txt_id_Buscar_Banco = new javax.swing.JTextField();
        btn_Buscar_Banco = new javax.swing.JButton();
        txt_Sucursal_Banco = new javax.swing.JTextField();
        txt_Direccion_Banco = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_Telefono_Banco = new javax.swing.JTextField();
        Combo_Cliente_Banco = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        btn_Nuevo_Banco = new javax.swing.JButton();
        btn_Guardar_Banco = new javax.swing.JButton();
        btn_Cancelar_Banco = new javax.swing.JButton();
        btn_Editar_Banco = new javax.swing.JButton();
        btn_Eliminar_Banco = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        Combo_Empresas_Seguros = new javax.swing.JComboBox<>();
        Combo_Tipo_Seguro = new javax.swing.JComboBox<>();
        Combo_Cuenta_Seguros = new javax.swing.JComboBox<>();
        txt_monto_seguro = new javax.swing.JFormattedTextField();
        btn_Nuevo_Seguros = new javax.swing.JButton();
        btn_Guardar_Seguros = new javax.swing.JButton();
        btn_Cancelar_Seguros = new javax.swing.JButton();
        btn_Editar_Seguros = new javax.swing.JButton();
        btn_Eliminar_Eliminar = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txt_idSeguro_Buscar_Seguros = new javax.swing.JTextField();
        btn_Buscar_Seguros = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/img/icono2.png")).getImage());

        JTabbedPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel11.setText("Bienvenido(a) a Nuestro Sistema Bancario");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(280, 220, 450, 26);

        JTree_Inicio.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Inicio");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Cuenta");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Agregar Nueva Cuenta");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Movimientos");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Clientes");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Agregar Nuevo Cliente");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Bancos");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Agregar Nuevo Banco");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Empresas");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Agregar Nueva Empresa");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ver Nomina");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Seguros");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Agregar Nuevo Seguro");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        JTree_Inicio.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        JTree_Inicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTree_InicioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTree_Inicio);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(1, 1, 220, 504);

        btn_movimientos.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        btn_movimientos.setText("Movimientos de Cuenta");
        btn_movimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_movimientosActionPerformed(evt);
            }
        });
        jPanel1.add(btn_movimientos);
        btn_movimientos.setBounds(400, 300, 200, 40);

        btn_cuentasRegistradas.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        btn_cuentasRegistradas.setText("Cuentas Registradas");
        btn_cuentasRegistradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cuentasRegistradasActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cuentasRegistradas);
        btn_cuentasRegistradas.setBounds(400, 370, 200, 40);

        btn_nomina.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        btn_nomina.setText("Nomina");
        btn_nomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nominaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_nomina);
        btn_nomina.setBounds(400, 440, 200, 40);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono2 - copia.png"))); // NOI18N
        jPanel1.add(jLabel12);
        jLabel12.setBounds(400, 40, 190, 150);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/une.png"))); // NOI18N
        jPanel1.add(jLabel13);
        jLabel13.setBounds(720, 10, 60, 50);

        JLabel_Hora.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jPanel1.add(JLabel_Hora);
        JLabel_Hora.setBounds(640, 480, 140, 20);
        jPanel1.add(JLabel_N_Cuenta);
        JLabel_N_Cuenta.setBounds(710, 460, 70, 20);

        JTabbedPrincipal.addTab("Menu", jPanel1);

        JPanelClientes.setBackground(new java.awt.Color(255, 255, 255));
        JPanelClientes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        JPanelClientes.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Buscar por Nombre:");
        JPanelClientes.add(jLabel1);
        jLabel1.setBounds(440, 30, 130, 17);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Ap Paterno:");
        JPanelClientes.add(jLabel2);
        jLabel2.setBounds(100, 160, 73, 17);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Ap Materno:");
        JPanelClientes.add(jLabel3);
        jLabel3.setBounds(100, 240, 75, 17);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Direccion:");
        JPanelClientes.add(jLabel4);
        jLabel4.setBounds(100, 290, 61, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Telefono:");
        JPanelClientes.add(jLabel5);
        jLabel5.setBounds(100, 330, 57, 17);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Email:");
        JPanelClientes.add(jLabel6);
        jLabel6.setBounds(440, 200, 36, 17);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Pais:");
        JPanelClientes.add(jLabel7);
        jLabel7.setBounds(440, 120, 28, 17);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tipo de Cliente:");
        JPanelClientes.add(jLabel8);
        jLabel8.setBounds(440, 160, 94, 17);

        btn_Nuevo_Cliente.setText("Nuevo");
        btn_Nuevo_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Nuevo_ClienteActionPerformed(evt);
            }
        });
        JPanelClientes.add(btn_Nuevo_Cliente);
        btn_Nuevo_Cliente.setBounds(120, 420, 63, 23);

        btn_Guardar_Cliente.setText("Guardar");
        btn_Guardar_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_ClienteActionPerformed(evt);
            }
        });
        JPanelClientes.add(btn_Guardar_Cliente);
        btn_Guardar_Cliente.setBounds(240, 420, 71, 23);

        btn_Buscar_Cliente.setText("Buscar");
        btn_Buscar_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Buscar_ClienteActionPerformed(evt);
            }
        });
        JPanelClientes.add(btn_Buscar_Cliente);
        btn_Buscar_Cliente.setBounds(700, 20, 70, 30);

        btn_Cancelar_Cliente.setText("Cancelar");
        btn_Cancelar_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_ClienteActionPerformed(evt);
            }
        });
        JPanelClientes.add(btn_Cancelar_Cliente);
        btn_Cancelar_Cliente.setBounds(360, 420, 80, 23);

        btn_Eliminar_Cliente.setText("Eliminar");
        btn_Eliminar_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Eliminar_ClienteActionPerformed(evt);
            }
        });
        JPanelClientes.add(btn_Eliminar_Cliente);
        btn_Eliminar_Cliente.setBounds(600, 420, 69, 23);

        txt_Email.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanelClientes.add(txt_Email);
        txt_Email.setBounds(540, 190, 190, 30);

        txt_nombre_Buscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanelClientes.add(txt_nombre_Buscar);
        txt_nombre_Buscar.setBounds(570, 20, 120, 30);

        txt_Ap_Paterno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanelClientes.add(txt_Ap_Paterno);
        txt_Ap_Paterno.setBounds(200, 150, 190, 30);

        txt_Ap_Materno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanelClientes.add(txt_Ap_Materno);
        txt_Ap_Materno.setBounds(200, 190, 190, 30);

        txt_Direccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanelClientes.add(txt_Direccion);
        txt_Direccion.setBounds(200, 280, 190, 30);

        txt_Telefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanelClientes.add(txt_Telefono);
        txt_Telefono.setBounds(200, 320, 190, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ap Materno:");
        JPanelClientes.add(jLabel9);
        jLabel9.setBounds(100, 200, 75, 17);

        rad_Femenino.setText("Feminino");
        JPanelClientes.add(rad_Femenino);
        rad_Femenino.setBounds(300, 240, 90, 23);

        rad_Masculino.setText("Masculino");
        JPanelClientes.add(rad_Masculino);
        rad_Masculino.setBounds(200, 240, 71, 23);

        combo_Tipo_Cuenta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_Tipo_Cuenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Premier" }));
        JPanelClientes.add(combo_Tipo_Cuenta);
        combo_Tipo_Cuenta.setBounds(540, 150, 190, 30);

        combo_Pais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_Pais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Afganistan", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyan", "Bahamas", "Banglades", "Barbados", "Barein", "Belgica", "Belice", "Benin", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Butan", "Cabo Verde", "Camboya", "Camerun", "Canada", "Catar", "Chad", "Chile", "China", "Chipre", "Ciudad del Vaticano", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos arabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "Espana", "Estados Unidos", "Estonia", "Etiopaa", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabon", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guyana", "Guinea", "Guinea ecuatorial", "Guinea-Bisau", "Haiti", "Honduras", "Hungria", "India", "Indonesia", "Irak", "Iran", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomon", "Israel", "Italia", "Jamaica", "Japon", "Jordania", "Kazajistan", "Kenia", "Kirguistan", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Libano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Macedonia del Norte", "Madagascar", "Malasia", "Malaui", "Maldivas", "Mali", "Malta", "Marruecos", "Mauricio", "Mauritania", "Mexico", "Micronesia", "Moldavia", "Monaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Niger", "Nigeria", "Noruega", "Nueva Zelanda", "Oman", "Paises Bajos", "Pakistan", "Palaos", "Panama", "Papua Nueva Guinea", "Paraguay", "Peru", "Polonia", "Portugal", "Reino Unido", "Republica Centroafricana", "Republica Checa", "Republica del Congo", "Republica Democrática del Congo", "Republica Dominicana", "Republica Sudafricana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristobal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucia", "Santo Tome y Principe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Suazilandia", "Sudan", "Sudan del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Tanzania", "Tayikistan", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Tunez", "Turkmenistan", "Turquia", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue" }));
        JPanelClientes.add(combo_Pais);
        combo_Pais.setBounds(540, 110, 190, 30);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nombre:");
        JPanelClientes.add(jLabel10);
        jLabel10.setBounds(100, 120, 54, 17);

        txt_Nombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanelClientes.add(txt_Nombre);
        txt_Nombre.setBounds(200, 110, 190, 30);

        btn_Editar_Cliente.setText("Editar");
        btn_Editar_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Editar_ClienteActionPerformed(evt);
            }
        });
        JPanelClientes.add(btn_Editar_Cliente);
        btn_Editar_Cliente.setBounds(490, 420, 61, 23);
        JPanelClientes.add(txt_id_cliente);
        txt_id_cliente.setBounds(200, 70, 100, 30);

        JTabbedPrincipal.addTab("Clientes", JPanelClientes);

        JPanel_Usuarios.setBackground(new java.awt.Color(255, 255, 255));
        JPanel_Usuarios.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        JPanel_Usuarios.setLayout(null);

        usuario_label_id1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_id1.setText("Usuario Id");
        JPanel_Usuarios.add(usuario_label_id1);
        usuario_label_id1.setBounds(70, 90, 61, 17);

        usuario_txt_id1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_id1);
        usuario_txt_id1.setBounds(160, 80, 100, 30);

        usuario_label_nombre2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_nombre2.setText("Nombre");
        JPanel_Usuarios.add(usuario_label_nombre2);
        usuario_label_nombre2.setBounds(70, 140, 49, 17);

        usuario_txt_nombre2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_nombre2);
        usuario_txt_nombre2.setBounds(160, 130, 190, 30);

        usuario_label_paterno1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_paterno1.setText("A.Paterno");
        JPanel_Usuarios.add(usuario_label_paterno1);
        usuario_label_paterno1.setBounds(70, 190, 70, 17);

        usuario_txt_parteno1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_parteno1);
        usuario_txt_parteno1.setBounds(160, 180, 190, 30);

        usuario_label_materno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_materno.setText("A.Materno");
        JPanel_Usuarios.add(usuario_label_materno);
        usuario_label_materno.setBounds(70, 240, 70, 17);

        usuario_txt_materno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_materno);
        usuario_txt_materno.setBounds(160, 230, 190, 30);

        usuario_label_domicilio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_domicilio.setText("Domicilio:");
        JPanel_Usuarios.add(usuario_label_domicilio);
        usuario_label_domicilio.setBounds(380, 290, 70, 17);

        usuario_txt_domicilio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_domicilio);
        usuario_txt_domicilio.setBounds(460, 280, 290, 30);

        usuario_label_email1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_email1.setText("Email:");
        JPanel_Usuarios.add(usuario_label_email1);
        usuario_label_email1.setBounds(470, 190, 36, 17);

        usuario_txt_email1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_email1);
        usuario_txt_email1.setBounds(550, 180, 190, 30);

        usuario_txt_telefono1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_telefono1);
        usuario_txt_telefono1.setBounds(550, 130, 190, 30);

        usuario_label_telefono1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_telefono1.setText("Telefono:");
        JPanel_Usuarios.add(usuario_label_telefono1);
        usuario_label_telefono1.setBounds(470, 140, 57, 17);

        usuario_label_buscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuario_label_buscar.setText("Buscar por A.paterno:");
        JPanel_Usuarios.add(usuario_label_buscar);
        usuario_label_buscar.setBounds(390, 30, 140, 17);

        usuario_txt_buscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        JPanel_Usuarios.add(usuario_txt_buscar);
        usuario_txt_buscar.setBounds(540, 20, 120, 30);

        usuarios_jButton_buscar.setText("Buscar");
        usuarios_jButton_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarios_jButton_buscarActionPerformed(evt);
            }
        });
        JPanel_Usuarios.add(usuarios_jButton_buscar);
        usuarios_jButton_buscar.setBounds(670, 20, 73, 30);

        usuarios_jButton_nuevo.setText("Nuevo");
        usuarios_jButton_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarios_jButton_nuevoActionPerformed(evt);
            }
        });
        JPanel_Usuarios.add(usuarios_jButton_nuevo);
        usuarios_jButton_nuevo.setBounds(100, 420, 80, 23);

        usuarios_jButton_guardar.setText("Guardar");
        usuarios_jButton_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarios_jButton_guardarActionPerformed(evt);
            }
        });
        JPanel_Usuarios.add(usuarios_jButton_guardar);
        usuarios_jButton_guardar.setBounds(200, 420, 80, 23);

        usuarios_jButton_cancelar.setText("Cancelar");
        usuarios_jButton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarios_jButton_cancelarActionPerformed(evt);
            }
        });
        JPanel_Usuarios.add(usuarios_jButton_cancelar);
        usuarios_jButton_cancelar.setBounds(310, 420, 80, 23);

        usuarios_jButton_editar.setText("Editar");
        usuarios_jButton_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarios_jButton_editarActionPerformed(evt);
            }
        });
        JPanel_Usuarios.add(usuarios_jButton_editar);
        usuarios_jButton_editar.setBounds(420, 420, 80, 23);

        usuarios_jButton_eliminar.setText("Eliminar");
        usuarios_jButton_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarios_jButton_eliminarActionPerformed(evt);
            }
        });
        JPanel_Usuarios.add(usuarios_jButton_eliminar);
        usuarios_jButton_eliminar.setBounds(530, 420, 80, 23);

        JTabbedPrincipal.addTab("Usuarios", JPanel_Usuarios);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cuentas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Cliente id");

        jTextFieldIdClienteCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Usuario id");

        jTextFieldIdUsuarioCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldBuscarCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButtonBuscarCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonBuscarCuenta.setText("Buscar");
        jButtonBuscarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarCuentaActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Buscar por No. de cuenta");

        jTextFieldNoDeCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("No. de cuenta");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Fecha apertura");

        jTextFieldFechaAperturaCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBoxTipoCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBoxTipoCuenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nomina", "cheques" }));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Tipo de cuenta");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Saldo apertura");

        jTextFieldSaldoAperturaCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButtonGuardarCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonGuardarCuenta.setText("Guardar");
        jButtonGuardarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarCuentaActionPerformed(evt);
            }
        });

        jButtonEditarCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonEditarCuenta.setText("Editar");
        jButtonEditarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarCuentaActionPerformed(evt);
            }
        });

        jButtonEliminarCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonEliminarCuenta.setText("Eliminar");
        jButtonEliminarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarCuentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldIdClienteCuenta, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBoxTipoCuenta, 0, 135, Short.MAX_VALUE)
                            .addComponent(jTextFieldSaldoAperturaCuenta)
                            .addComponent(jTextFieldFechaAperturaCuenta)
                            .addComponent(jTextFieldNoDeCuenta))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(27, 27, 27)
                                .addComponent(jTextFieldBuscarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButtonBuscarCuenta))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldIdUsuarioCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(166, 166, 166))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jButtonGuardarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jButtonEditarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jButtonEliminarCuenta)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldIdClienteCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jTextFieldIdUsuarioCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jTextFieldNoDeCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jTextFieldBuscarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonBuscarCuenta))))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextFieldFechaAperturaCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextFieldSaldoAperturaCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGuardarCuenta)
                    .addComponent(jButtonEditarCuenta)
                    .addComponent(jButtonEliminarCuenta))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        JTabbedPrincipal.addTab("Cuentas", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Movimientos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Buscar por ID:");

        btn_Buscar_Movimiento.setText("Buscar");
        btn_Buscar_Movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Buscar_MovimientoActionPerformed(evt);
            }
        });

        combo_nc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1" }));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("Numero Cuenta:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Cuenta Destino:");

        combo_cd.setToolTipText("");

        txt_fm.setEnabled(false);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Fecha Movimiento:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Tipo Movimiento:");

        txt_id.setEnabled(false);

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Saldo:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("ID:");

        btn_Nuevo_Movimiento.setText("Nuevo");
        btn_Nuevo_Movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Nuevo_MovimientoActionPerformed(evt);
            }
        });

        btn_Guardar_Movimiento.setText("Guardar");
        btn_Guardar_Movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_MovimientoActionPerformed(evt);
            }
        });

        btn_Cancelar_Movimiento.setText("Cancelar");
        btn_Cancelar_Movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_MovimientoActionPerformed(evt);
            }
        });

        btn_Editar_Movimiento.setText("Editar");
        btn_Editar_Movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Editar_MovimientoActionPerformed(evt);
            }
        });

        btn_Eliminar_Movimiento.setText("Eliminar");
        btn_Eliminar_Movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Eliminar_MovimientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btn_Nuevo_Movimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(btn_Guardar_Movimiento)
                        .addGap(53, 53, 53)
                        .addComponent(btn_Cancelar_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_id))
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_s, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(txt_tm)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txt_fm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(txt_buscar_id, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combo_nc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(btn_Buscar_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_Editar_Movimiento)
                            .addComponent(jLabel31))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(btn_Eliminar_Movimiento))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combo_cd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_buscar_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Buscar_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(combo_nc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(txt_tm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel31))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(combo_cd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_fm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txt_s, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Nuevo_Movimiento)
                    .addComponent(btn_Guardar_Movimiento)
                    .addComponent(btn_Cancelar_Movimiento)
                    .addComponent(btn_Editar_Movimiento)
                    .addComponent(btn_Eliminar_Movimiento))
                .addGap(52, 52, 52))
        );

        JTabbedPrincipal.addTab("Movimientos", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Beth");

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jButton1)))
                .addContainerGap(569, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton1)
                .addContainerGap(377, Short.MAX_VALUE))
        );

        JTabbedPrincipal.addTab("Empresas", jPanel6);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Sucursal:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Buscar por ID:");

        txt_id_Buscar_Banco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btn_Buscar_Banco.setText("Buscar");
        btn_Buscar_Banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Buscar_BancoActionPerformed(evt);
            }
        });

        txt_Sucursal_Banco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txt_Direccion_Banco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_Direccion_Banco.setToolTipText("Ingresa la direccion completa separada por _");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("Direccion:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Telefono:");

        txt_Telefono_Banco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        Combo_Cliente_Banco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("Cliente:");

        btn_Nuevo_Banco.setText("Nuevo");
        btn_Nuevo_Banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Nuevo_BancoActionPerformed(evt);
            }
        });

        btn_Guardar_Banco.setText("Guardar");
        btn_Guardar_Banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_BancoActionPerformed(evt);
            }
        });

        btn_Cancelar_Banco.setText("Cancelar");
        btn_Cancelar_Banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_BancoActionPerformed(evt);
            }
        });

        btn_Editar_Banco.setText("Editar");
        btn_Editar_Banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Editar_BancoActionPerformed(evt);
            }
        });

        btn_Eliminar_Banco.setText("Eliminar");
        btn_Eliminar_Banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Eliminar_BancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_Telefono_Banco)
                            .addComponent(txt_Direccion_Banco)
                            .addComponent(txt_Sucursal_Banco)
                            .addComponent(Combo_Cliente_Banco, 0, 222, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(68, 68, 68))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(btn_Nuevo_Banco)
                .addGap(57, 57, 57)
                .addComponent(btn_Guardar_Banco)
                .addGap(49, 49, 49)
                .addComponent(btn_Cancelar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btn_Editar_Banco)
                .addGap(49, 49, 49)
                .addComponent(btn_Eliminar_Banco)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_id_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34))
                    .addComponent(btn_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel33))
                    .addComponent(txt_Sucursal_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel35))
                    .addComponent(txt_Direccion_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel36))
                    .addComponent(txt_Telefono_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel37))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Combo_Cliente_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(81, 81, 81)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Nuevo_Banco)
                    .addComponent(btn_Guardar_Banco)
                    .addComponent(btn_Cancelar_Banco)
                    .addComponent(btn_Editar_Banco)
                    .addComponent(btn_Eliminar_Banco))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        JTabbedPrincipal.addTab("Banco", jPanel3);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nominas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 776, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );

        JTabbedPrincipal.addTab("Nominas", jPanel7);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seguros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setText("Tipo de Seguro:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("Monto:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setText("Empresa:");

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel41.setText("Cuenta:");

        Combo_Empresas_Seguros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        Combo_Tipo_Seguro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Combo_Tipo_Seguro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona...", "Vida", "Poliza Temporal", "Ordinario de vida o vitalicio", "Seguro Dotal", "Gastos Medicos Mayores", "Salud", "Responsabilidad civil y riesgos profesionales", "Seguros de Auto", "Protección de Hogar", "Otra.." }));
        Combo_Tipo_Seguro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_Tipo_SeguroActionPerformed(evt);
            }
        });

        Combo_Cuenta_Seguros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txt_monto_seguro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        btn_Nuevo_Seguros.setText("Nuevo");
        btn_Nuevo_Seguros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Nuevo_SegurosActionPerformed(evt);
            }
        });

        btn_Guardar_Seguros.setText("Guardar");
        btn_Guardar_Seguros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_SegurosActionPerformed(evt);
            }
        });

        btn_Cancelar_Seguros.setText("Cancelar");
        btn_Cancelar_Seguros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_SegurosActionPerformed(evt);
            }
        });

        btn_Editar_Seguros.setText("Editar");
        btn_Editar_Seguros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Editar_SegurosActionPerformed(evt);
            }
        });

        btn_Eliminar_Eliminar.setText("Eliminar");
        btn_Eliminar_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Eliminar_EliminarActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel43.setText("Buscar por ID:");

        txt_idSeguro_Buscar_Seguros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btn_Buscar_Seguros.setText("Buscar");
        btn_Buscar_Seguros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Buscar_SegurosActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("$");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(btn_Nuevo_Seguros)
                .addGap(57, 57, 57)
                .addComponent(btn_Guardar_Seguros)
                .addGap(49, 49, 49)
                .addComponent(btn_Cancelar_Seguros, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btn_Editar_Seguros)
                .addGap(49, 49, 49)
                .addComponent(btn_Eliminar_Eliminar)
                .addGap(0, 118, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel39)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Combo_Cuenta_Seguros, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_monto_seguro))
                            .addComponent(Combo_Tipo_Seguro, 0, 0, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(49, 49, 49)
                        .addComponent(Combo_Empresas_Seguros, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_idSeguro_Buscar_Seguros, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_Buscar_Seguros, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_idSeguro_Buscar_Seguros, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel43))
                    .addComponent(btn_Buscar_Seguros, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel40)
                            .addComponent(Combo_Empresas_Seguros, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(Combo_Cuenta_Seguros, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_monto_seguro, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15))
                            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(Combo_Tipo_Seguro, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel38))
                .addGap(98, 98, 98)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Nuevo_Seguros)
                    .addComponent(btn_Guardar_Seguros)
                    .addComponent(btn_Cancelar_Seguros)
                    .addComponent(btn_Editar_Seguros)
                    .addComponent(btn_Eliminar_Eliminar))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        JTabbedPrincipal.addTab("Seguros", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 793, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JTabbedPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JTabbedPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTree_InicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTree_InicioMouseClicked

        if (evt.getClickCount() == 2) {

            DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) JTree_Inicio.getLastSelectedPathComponent();
            eventos(nodoSeleccionado);
        }
    }//GEN-LAST:event_JTree_InicioMouseClicked

    private void btn_Nuevo_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Nuevo_ClienteActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Limpiar los Campos?");
        if (JOptionPane.OK_OPTION == a) {
            limpar_Cliente();

        } else {

        }
    }//GEN-LAST:event_btn_Nuevo_ClienteActionPerformed

    private void btn_Guardar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_ClienteActionPerformed

        if (v.estaVacio(txt_Nombre.getText()) || v.estaVacio(txt_Ap_Paterno.getText()) || v.estaVacio(txt_Ap_Materno.getText()) || v.estaVacio(txt_Direccion.getText()) || v.estaVacio(txt_Telefono.getText()) || v.estaVacio(txt_Email.getText())) {

            JOptionPane.showMessageDialog(this, "Algun Campo esta Vacio Verifica!!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            if (rad_Femenino.isSelected() == false && rad_Masculino.isSelected() == false) {

                JOptionPane.showMessageDialog(this, "Selecciona el Sexo", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {

                try {
                    String ap_Paterno;
                    String ap_Materno;
                    String sexo;
                    String direccion;
                    String telefono;
                    String email;
                    String pais;
                    String tipo_cuenta;

                    nombre_Cliente = v.reemplazar_espacios(txt_Nombre);
                    ap_Paterno = v.reemplazar_espacios(txt_Ap_Paterno);
                    ap_Materno = v.reemplazar_espacios(txt_Ap_Materno);
                    sexo = sexo_Cliente();
                    direccion = v.reemplazar_espacios(txt_Direccion);
                    email = v.reemplazar_espacios(txt_Email);
                    telefono = v.reemplazar_espacios(txt_Telefono);
                    pais = v.reemplazar_espacios_combos(combo_Pais);
                    tipo_cuenta = v.reemplazar_espacios_combos(combo_Tipo_Cuenta);

                    String mensaje = "NewCliente " + nombre_Cliente + " " + ap_Paterno + " " + ap_Materno + " " + sexo + " " + direccion + " " + email + " " + telefono + " " + pais + " " + tipo_cuenta + " ";
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

            }
        }

    }//GEN-LAST:event_btn_Guardar_ClienteActionPerformed

    private void btn_Buscar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Buscar_ClienteActionPerformed

        if (txt_nombre_Buscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el Nombre para Buscar!", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            txt_nombre_Buscar.requestFocus();

        } else {

            try {
                //obtener mensaje del campo de texto y convertirlo en arrreglo byte
                buscar_Clientes = txt_nombre_Buscar.getText().trim();
                String mensaje = "SearchCliente" + " " + buscar_Clientes + " ";
                byte datos[] = mensaje.getBytes();
                //          //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);
                //enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                esperarPaquetesClientes();
                socket = new DatagramSocket();

            } catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        }
    }//GEN-LAST:event_btn_Buscar_ClienteActionPerformed

    private void btn_Cancelar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_ClienteActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Cancelar?");
        if (JOptionPane.OK_OPTION == a) {
            limpar_Cliente();
            JTabbedPrincipal.setSelectedIndex(0);

        } else {

        }
    }//GEN-LAST:event_btn_Cancelar_ClienteActionPerformed

    private void btn_Eliminar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Eliminar_ClienteActionPerformed

        if (txt_id_cliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Para Eliminar un Cliente Primero Debes Buscarlo!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Eliminar el Cliente?");
            if (JOptionPane.OK_OPTION == a) {

                try {
                    String id_clientes;
                    id_clientes = txt_id_cliente.getText();
                    String mensaje = "DeleteCliente " + id_clientes + " Registro Borrado";
                    byte datos[] = mensaje.getBytes();
                    //crear enviarPaquete

                    DatagramPacket snd = ip.Direccion(datos);
                    socket.send(snd);//enviar paquete
                } catch (IOException exceptionES) {
                    exceptionES.printStackTrace();
                }
                try {
                    socket = new DatagramSocket();
                } //atrapar los problemas que puedan ocurrir al crear objeto DatagramSocket
                catch (SocketException excepcionSocket) {
                    excepcionSocket.printStackTrace();
                    System.exit(1);
                }

            } else {

            }
        }
    }//GEN-LAST:event_btn_Eliminar_ClienteActionPerformed

    private void btn_Editar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_ClienteActionPerformed

        if (v.estaVacio(txt_Nombre.getText()) || v.estaVacio(txt_Ap_Paterno.getText()) || v.estaVacio(txt_Ap_Materno.getText()) || v.estaVacio(txt_Direccion.getText()) || v.estaVacio(txt_Telefono.getText()) || v.estaVacio(txt_Email.getText())) {

            JOptionPane.showMessageDialog(this, "Algun Campo esta Vacio Verifica!!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            if (rad_Femenino.isSelected() == false && rad_Masculino.isSelected() == false) {

                JOptionPane.showMessageDialog(this, "Selecciona el Sexo", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {

                int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Editar el Cliente?");
                if (JOptionPane.OK_OPTION == a) {

                    try {

                        String id_cliente;
                        String ap_Paterno;
                        String ap_Materno;
                        String sexo;
                        String direccion;
                        String telefono;
                        String email;
                        String pais;
                        String tipo_cuenta;

                        id_cliente = txt_id_cliente.getText().trim();
                        nombre_Cliente = v.reemplazar_espacios(txt_Nombre);
                        ap_Paterno = v.reemplazar_espacios(txt_Ap_Paterno);
                        ap_Materno = v.reemplazar_espacios(txt_Ap_Materno);
                        sexo = sexo_Cliente();
                        direccion = v.reemplazar_espacios(txt_Direccion);
                        email = v.reemplazar_espacios(txt_Email);
                        telefono = v.reemplazar_espacios(txt_Telefono);
                        pais = v.reemplazar_espacios_combos(combo_Pais);
                        tipo_cuenta = v.reemplazar_espacios_combos(combo_Tipo_Cuenta);

                        String mensaje = "EditCliente " + id_cliente + " " + nombre_Cliente + " " + ap_Paterno + " " + ap_Materno + " " + sexo + " " + direccion + " " + email + " " + telefono + " " + pais + " " + tipo_cuenta + " ";
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

                } else {

                }
            }
        }
    }//GEN-LAST:event_btn_Editar_ClienteActionPerformed

    private void jButtonGuardarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarCuentaActionPerformed

        try {

            String idCliente;
            String idUsuario;
            String noDeCuenta;
            String tipoCuenta;
            String fechaApertura;
            String saldoApertura;

            idCliente = v.reemplazar_espacios(jTextFieldIdClienteCuenta);
            idUsuario = v.reemplazar_espacios(jTextFieldIdUsuarioCuenta);
            noDeCuenta = v.reemplazar_espacios(jTextFieldNoDeCuenta);
            tipoCuenta = v.reemplazar_espacios_combos(jComboBoxTipoCuenta);
            fechaApertura = v.reemplazar_espacios(jTextFieldFechaAperturaCuenta);
            saldoApertura = v.reemplazar_espacios(jTextFieldSaldoAperturaCuenta);

            String mensaje = "NewCuenta " + idCliente + " " + idUsuario + " " + noDeCuenta + " " + tipoCuenta + " " + fechaApertura + " " + saldoApertura + " ";
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

    }//GEN-LAST:event_jButtonGuardarCuentaActionPerformed

    private void jButtonEditarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEditarCuentaActionPerformed

    private void jButtonEliminarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarCuentaActionPerformed

    private void btn_Buscar_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Buscar_BancoActionPerformed

        if (txt_id_Buscar_Banco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID para Buscar!", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            txt_id_Buscar_Banco.requestFocus();

        } else {

            try {
                //obtener mensaje del campo de texto y convertirlo en arrreglo byte
                buscar_Banco = txt_id_Buscar_Banco.getText().trim();
                String mensaje = "SearchBanco" + " " + buscar_Banco + " ";
                byte datos[] = mensaje.getBytes();
                //          //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);
                //enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                esperarPaquetesBanco();
                socket = new DatagramSocket();

            } catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        }
    }//GEN-LAST:event_btn_Buscar_BancoActionPerformed

    private void btn_Nuevo_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Nuevo_BancoActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Limpiar los Campos?");
        if (JOptionPane.OK_OPTION == a) {
            limpiar_Banco();

        } else {

        }
    }//GEN-LAST:event_btn_Nuevo_BancoActionPerformed

    private void btn_Guardar_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_BancoActionPerformed

        if (v.estaVacio(txt_Sucursal_Banco.getText()) || v.estaVacio(txt_Telefono_Banco.getText()) || v.estaVacio(txt_Direccion_Banco.getText())) {

            JOptionPane.showMessageDialog(this, "Algun Campo esta Vacio Verifica!!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            if (Combo_Cliente_Banco.getSelectedItem().toString().equals("Selecciona...")) {

                JOptionPane.showMessageDialog(this, "Selecciona el Cliente", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {

                try {
                    String telefono;
                    String direccion;
                    String sucursal;
                    String id_cliente;

                    telefono = txt_Telefono_Banco.getText().trim();
                    direccion = txt_Direccion_Banco.getText().trim();
                    sucursal = txt_Sucursal_Banco.getText().trim();

                    String cliente_id[] = Combo_Cliente_Banco.getSelectedItem().toString().trim().split("_");
                    id_cliente = cliente_id[0];

                    String mensaje = "NewBanco " + telefono + " " + direccion + " " + sucursal + " " + id_cliente + " ";
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
            }

        }
    }//GEN-LAST:event_btn_Guardar_BancoActionPerformed

    private void btn_Cancelar_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_BancoActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Cancelar?");
        if (JOptionPane.OK_OPTION == a) {
            limpiar_Banco();
            JTabbedPrincipal.setSelectedIndex(0);

        } else {

        }
    }//GEN-LAST:event_btn_Cancelar_BancoActionPerformed

    private void btn_Editar_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_BancoActionPerformed

        if (v.estaVacio(txt_Sucursal_Banco.getText()) || v.estaVacio(txt_Telefono_Banco.getText()) || v.estaVacio(txt_Direccion_Banco.getText())) {

            JOptionPane.showMessageDialog(this, "Algun Campo esta Vacio Verifica!!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            if (Combo_Cliente_Banco.getSelectedItem().toString().equals("Selecciona...")) {

                JOptionPane.showMessageDialog(this, "Selecciona el Cliente", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {

                int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Editar el Banco?");
                if (JOptionPane.OK_OPTION == a) {

                    try {

                        String id_banco;
                        String telefono;
                        String direccion;
                        String sucursal;
                        String id_cliente;

                        telefono = txt_Telefono_Banco.getText().trim();
                        direccion = txt_Direccion_Banco.getText().trim();
                        sucursal = txt_Sucursal_Banco.getText().trim();

                        String cliente_id[] = Combo_Cliente_Banco.getSelectedItem().toString().trim().split("_");
                        id_cliente = cliente_id[1];
                        id_banco = txt_id_Buscar_Banco.getText().trim();

                        String mensaje = "EditBanco " + id_banco + " " + telefono + " " + direccion + " " + sucursal + " " + id_cliente + " ";
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

                } else {
                }
            }
        }
    }//GEN-LAST:event_btn_Editar_BancoActionPerformed

    private void btn_Eliminar_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Eliminar_BancoActionPerformed

        if (txt_id_Buscar_Banco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Para Eliminar un Banco Primero Debes Buscarlo!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Eliminar el Banco?");
            if (JOptionPane.OK_OPTION == a) {

                try {
                    String id_banco;
                    id_banco = txt_id_Buscar_Banco.getText();
                    String mensaje = "DeleteBanco " + id_banco + " Registro Borrado";
                    byte datos[] = mensaje.getBytes();
                    //crear enviarPaquete

                    DatagramPacket snd = ip.Direccion(datos);
                    socket.send(snd);//enviar paquete
                } catch (IOException exceptionES) {
                    exceptionES.printStackTrace();
                }
                try {
                    socket = new DatagramSocket();
                } //atrapar los problemas que puedan ocurrir al crear objeto DatagramSocket
                catch (SocketException excepcionSocket) {
                    excepcionSocket.printStackTrace();
                    System.exit(1);
                }

            } else {

            }

        }
    }//GEN-LAST:event_btn_Eliminar_BancoActionPerformed

    private void jButtonBuscarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCuentaActionPerformed
        // TODO add your handling code here:

        String noDeCuenta;

        if (jTextFieldBuscarCuenta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el numero de cuenta para Buscar!", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            jTextFieldBuscarCuenta.requestFocus();

        } else {

            try {
                //obtener mensaje del campo de texto y convertirlo en arrreglo byte
                noDeCuenta = jTextFieldBuscarCuenta.getText().trim();
                String mensaje = "SearchCuenta" + " " + noDeCuenta + " ";
                byte datos[] = mensaje.getBytes();
                //          //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);
                //enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                esperarPaquetesCuentas();
                socket = new DatagramSocket();

            } catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        }


    }//GEN-LAST:event_jButtonBuscarCuentaActionPerformed

    private void btn_movimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_movimientosActionPerformed

        try {
            Map parametros = new HashMap();

            parametros.put("n_cuenta", JLabel_N_Cuenta.getText().trim());

            JasperReport jr = JasperCompileManager.compileReport("src/reportes/report_Movimientos.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, conn.Conexion());

            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Movimientos de Cuenta");
            jv.setExtendedState(PrincipalForm.MAXIMIZED_BOTH);
            jv.setVisible(true);

            jv.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent evt) {

                    int a = JOptionPane.showConfirmDialog(null, "Deseas Enviar los Movimientos a tu Correo Electronico?");
                    if (JOptionPane.OK_OPTION == a) {

                        JFileChooser chooser = new JFileChooser("");
                        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("PDF", "pdf");
                        chooser.setFileFilter(filtroImagen);
                        chooser.setDialogTitle("Cargar PDF");
                        File file = null;
                        if (chooser.showOpenDialog(null) == 0) {
                            file = chooser.getSelectedFile();
                        }

                        try {

                            Properties propiedad = new Properties();
                            propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
                            propiedad.setProperty("mail.smtp.starttls.enable", "true");
                            propiedad.setProperty("mail.smtp.port", "587");
                            propiedad.setProperty("mail.smtp.auth", "true");

                            Session sesion = Session.getDefaultInstance(propiedad);

                            String correoEnvia = "sistema.bancario.une@gmail.com";
                            String contrasena = "sistema12345";
                            String destinatario = Generar_Correo_Cuenta(conn.Conexion());
                            String asunto = "Movimientos de Cuenta PDF";
                            String mensaje = "Gracias por Usar Nuestro Sistema Bancario.!";

                            MimeMessage mail = new MimeMessage(sesion);

                            // Se compone la parte del texto
                            BodyPart texto = new MimeBodyPart();
                            texto.setText("Movimientos de Cuenta PDF");

                            BodyPart adjunto = new MimeBodyPart();
                            adjunto.setDataHandler(new DataHandler(new FileDataSource(file.getAbsolutePath())));
                            adjunto.setFileName("Movimientos_de_Cuenta_PDF.pdf");

                            // Una MultiParte para agrupar texto e imagen.
                            MimeMultipart multiParte = new MimeMultipart();
                            multiParte.addBodyPart(texto);
                            multiParte.addBodyPart(adjunto);

                            try {
                                mail.setFrom(new InternetAddress(correoEnvia));
                                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                                mail.setSubject(asunto);
                                mail.setText(mensaje);
                                mail.setContent(multiParte);

                                Transport transporte = sesion.getTransport("smtp");
                                transporte.connect(correoEnvia, contrasena);
                                transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                                transporte.close();

                                JOptionPane.showMessageDialog(null, "Correo Enviado");

                            } catch (AddressException ex) {
                                Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (MessagingException ex) {
                                Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } catch (SQLException | MessagingException ex) {
                            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                    }
                }
            });
        } catch (JRException ex) {
//            System.out.println(ex.getMessage());
        }

    }//GEN-LAST:event_btn_movimientosActionPerformed

    private void btn_nominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nominaActionPerformed


    }//GEN-LAST:event_btn_nominaActionPerformed

    private void btn_cuentasRegistradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuentasRegistradasActionPerformed

        try {
            Map parametros = new HashMap();

            String mes = JOptionPane.showInputDialog(this, "Ejemplo: Enero = 01..", "Ingrese el Mes con Numero", JOptionPane.INFORMATION_MESSAGE);

            parametros.put("mes", mes);
            parametros.put("n_cuenta", JLabel_N_Cuenta.getText().trim());

            JasperReport jr = JasperCompileManager.compileReport("src/reportes/report_estadoCuenta.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, conn.Conexion());

            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Cuentas Registradas");
            jv.setExtendedState(PrincipalForm.MAXIMIZED_BOTH);
            jv.setVisible(true);

        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }

    }//GEN-LAST:event_btn_cuentasRegistradasActionPerformed

    private void btn_Nuevo_SegurosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Nuevo_SegurosActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Limpiar los Campos?");
        if (JOptionPane.OK_OPTION == a) {
            limpiar_Seguros();

        } else {

        }

    }//GEN-LAST:event_btn_Nuevo_SegurosActionPerformed

    private void btn_Guardar_SegurosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_SegurosActionPerformed

        if (v.estaVacio(txt_monto_seguro.getText()) || Combo_Empresas_Seguros.getSelectedIndex() == 0 || Combo_Tipo_Seguro.getSelectedIndex() == 0 || Combo_Cuenta_Seguros.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(this, "Algun Campo esta Vacio/Incorrecto Verifica!!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            try {
                String monto;
                String empresa;
                String tipo_seguro;
                String cuenta;

                String[] empresa_id = Combo_Empresas_Seguros.getSelectedItem().toString().trim().split("_");
                String[] cuenta_id = Combo_Cuenta_Seguros.getSelectedItem().toString().trim().split("_");

                monto = txt_monto_seguro.getText().trim();
                tipo_seguro = v.reemplazar_espacios_combos(Combo_Tipo_Seguro);
                empresa = empresa_id[0];
                cuenta = cuenta_id[0];

                String mensaje = "NewSure " + empresa + " " + tipo_seguro + " " + monto + " " + cuenta + " ";
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
        }

    }//GEN-LAST:event_btn_Guardar_SegurosActionPerformed

    private void btn_Cancelar_SegurosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_SegurosActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Cancelar?");
        if (JOptionPane.OK_OPTION == a) {
            limpiar_Seguros();
            JTabbedPrincipal.setSelectedIndex(0);

        } else {

        }

    }//GEN-LAST:event_btn_Cancelar_SegurosActionPerformed

    private void btn_Editar_SegurosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_SegurosActionPerformed

        if (v.estaVacio(txt_monto_seguro.getText()) || Combo_Empresas_Seguros.getSelectedIndex() == 0 || Combo_Tipo_Seguro.getSelectedIndex() == 0 || Combo_Cuenta_Seguros.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(this, "Algun Campo esta Vacio/Incorrecto Verifica!!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Editar el Seguro?");
            if (JOptionPane.OK_OPTION == a) {

                try {
                    String id_seguro;
                    String monto;
                    String empresa;
                    String tipo_seguro;
                    String cuenta;

                    String[] empresa_id = Combo_Empresas_Seguros.getSelectedItem().toString().trim().split("_");
                    String[] cuenta_id = Combo_Cuenta_Seguros.getSelectedItem().toString().trim().split("_");

                    id_seguro = txt_idSeguro_Buscar_Seguros.getText().trim();
                    monto = txt_monto_seguro.getText().trim();
                    tipo_seguro = v.reemplazar_espacios_combos(Combo_Tipo_Seguro);
                    empresa = empresa_id[0];
                    cuenta = cuenta_id[0];

                    String mensaje = "EditSure " + id_seguro + " " + empresa + " " + tipo_seguro + " " + monto + " " + cuenta + " ";
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
            } else {

            }
        }

    }//GEN-LAST:event_btn_Editar_SegurosActionPerformed

    private void btn_Eliminar_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Eliminar_EliminarActionPerformed

        if (txt_idSeguro_Buscar_Seguros.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Para Eliminar un Seguro Primero Debes Buscarlo!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Eliminar el Seguro?");
            if (JOptionPane.OK_OPTION == a) {

                try {
                    String id_seguro;
                    id_seguro = txt_idSeguro_Buscar_Seguros.getText();
                    String mensaje = "DeleteSure " + id_seguro + " Registro Borrado";
                    byte datos[] = mensaje.getBytes();
                    //crear enviarPaquete

                    DatagramPacket snd = ip.Direccion(datos);
                    socket.send(snd);//enviar paquete
                } catch (IOException exceptionES) {
                    exceptionES.printStackTrace();
                }
                try {
                    socket = new DatagramSocket();
                } //atrapar los problemas que puedan ocurrir al crear objeto DatagramSocket
                catch (SocketException excepcionSocket) {
                    excepcionSocket.printStackTrace();
                    System.exit(1);
                }

            } else {

            }

        }

    }//GEN-LAST:event_btn_Eliminar_EliminarActionPerformed

    private void btn_Buscar_SegurosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Buscar_SegurosActionPerformed

        if (txt_idSeguro_Buscar_Seguros.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID para Buscar!", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            txt_idSeguro_Buscar_Seguros.requestFocus();

        } else {

            try {
                //obtener mensaje del campo de texto y convertirlo en arrreglo byte
                buscar_Seguro = txt_idSeguro_Buscar_Seguros.getText().trim();
                String mensaje = "SearchSure" + " " + buscar_Seguro + " ";
                byte datos[] = mensaje.getBytes();
                //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);
                //enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                esperarPaquetesSeguros();
                socket = new DatagramSocket();

            } catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        }

    }//GEN-LAST:event_btn_Buscar_SegurosActionPerformed

    private void Combo_Tipo_SeguroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_Tipo_SeguroActionPerformed

        if (Combo_Tipo_Seguro.getSelectedIndex() == 10) {

            otro_seguro = JOptionPane.showInputDialog("Ingrese el Otro Tipo de Seguro!");

        }


    }//GEN-LAST:event_Combo_Tipo_SeguroActionPerformed

    private void usuarios_jButton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarios_jButton_buscarActionPerformed
        if (usuario_txt_buscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID para Buscar!", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            usuario_txt_buscar.requestFocus();

        } else {

            try {
                //obtener mensaje del campo de texto y convertirlo en arrreglo byte
                buscar_usuario = usuario_txt_buscar.getText().trim();
                String mensaje = "SearchUsuario" + " " + buscar_usuario + " ";
                byte datos[] = mensaje.getBytes();
                //          //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);
                //enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                esperarPaquetesUsuarios();
                socket = new DatagramSocket();

            } catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        }
    }//GEN-LAST:event_usuarios_jButton_buscarActionPerformed

    private void usuarios_jButton_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarios_jButton_nuevoActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Limpiar los Campos?");
        if (JOptionPane.OK_OPTION == a) {
            limpar_Usuarios();

        } else {

        }
    }//GEN-LAST:event_usuarios_jButton_nuevoActionPerformed

    private void usuarios_jButton_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarios_jButton_guardarActionPerformed
        try {
            String nombre = v.reemplazar_espacios(usuario_txt_nombre2);
            String paterno = v.reemplazar_espacios(usuario_txt_parteno1);
            String materno = v.reemplazar_espacios(usuario_txt_materno);
            String telefono = v.reemplazar_espacios(usuario_txt_telefono1);
            String email = v.reemplazar_espacios(usuario_txt_email1);
            String domicilio = v.reemplazar_espacios(usuario_txt_domicilio);

            String mensaje = "NuevoUsuario " + nombre + " " + paterno + " " + materno + " " + telefono + " " + email + " " + domicilio + " ";
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
    }//GEN-LAST:event_usuarios_jButton_guardarActionPerformed

    private void usuarios_jButton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarios_jButton_cancelarActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Cancelar?");
        if (JOptionPane.OK_OPTION == a) {
            limpar_Usuarios();
            JTabbedPrincipal.setSelectedIndex(0);

        } else {

        }
    }//GEN-LAST:event_usuarios_jButton_cancelarActionPerformed

    private void usuarios_jButton_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarios_jButton_editarActionPerformed
        try {
            String id = v.reemplazar_espacios(usuario_txt_id1);
            String nombre = v.reemplazar_espacios(usuario_txt_nombre2);
            String paterno = v.reemplazar_espacios(usuario_txt_parteno1);
            String materno = v.reemplazar_espacios(usuario_txt_materno);
            String telefono = v.reemplazar_espacios(usuario_txt_telefono1);
            String email = v.reemplazar_espacios(usuario_txt_email1);
            String domicilio = v.reemplazar_espacios(usuario_txt_domicilio);

            String mensaje = "NuevoUsuarioEditar " + id + " " + nombre + " " + paterno + " " + materno + " " + telefono + " " + email + " " + domicilio + " ";
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
    }//GEN-LAST:event_usuarios_jButton_editarActionPerformed

    private void usuarios_jButton_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarios_jButton_eliminarActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Eliminar el Usuario?");
        if (JOptionPane.OK_OPTION == a) {

            try {
                String id_usuario;
                id_usuario = usuario_txt_id1.getText();
                String mensaje = "DeleteUsuario " + id_usuario + " Registro Borrado";
                byte datos[] = mensaje.getBytes();
                //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);//enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                socket = new DatagramSocket();
            } //atrapar los problemas que puedan ocurrir al crear objeto DatagramSocket
            catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        } else {

        }
    }//GEN-LAST:event_usuarios_jButton_eliminarActionPerformed

    private void btn_Buscar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Buscar_MovimientoActionPerformed

        if (txt_buscar_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID del Movimiento para Buscar!", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            txt_buscar_id.requestFocus();

        } else {

            btn_Editar_Movimiento.setEnabled(true);
            btn_Eliminar_Movimiento.setEnabled(true);
            btn_Guardar_Movimiento.setEnabled(false);

            try {
                //obtener mensaje del campo de texto y convertirlo en arrreglo byte
                buscar_movimiento = txt_buscar_id.getText().trim();
                String mensaje = "SearchMovimiento" + " " + buscar_movimiento + " ";
                byte datos[] = mensaje.getBytes();
                //          //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);
                //enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                esperarPaquetesMovimiento();
                socket = new DatagramSocket();

            } catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        }
    }//GEN-LAST:event_btn_Buscar_MovimientoActionPerformed

    private void btn_Nuevo_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Nuevo_MovimientoActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Limpiar los Campos?");
        if (JOptionPane.OK_OPTION == a) {
            limpiar_Movimiento();

        } else {

        }
    }//GEN-LAST:event_btn_Nuevo_MovimientoActionPerformed

    private void btn_Guardar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_MovimientoActionPerformed

        try {

            String id_movimiento;
            String tipo_movimiento;
            String fecha_movimiento;
            double saldo;
            String n_cuenta;
            String cuenta_destino;

            id_movimiento = txt_id.getText().trim();
            tipo_movimiento = txt_tm.getText().trim();
            fecha_movimiento = String.valueOf(now);
            saldo = Double.parseDouble(txt_s.getText().trim());
            n_cuenta = String.valueOf(combo_nc.getSelectedItem());

            cuenta_destino = String.valueOf(combo_cd.getSelectedItem());

            String[] arreglo_cuenta = cuenta_destino.split(" ");

            String mensaje = "NewMovimiento " + tipo_movimiento + " " + fecha_movimiento + " " + saldo + " " + n_cuenta + " " + arreglo_cuenta[0] + " ";
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

    }//GEN-LAST:event_btn_Guardar_MovimientoActionPerformed

    private void btn_Cancelar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_MovimientoActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Cancelar?");

        if (JOptionPane.OK_OPTION == a) {

            limpiar_Movimiento();

            JTabbedPrincipal.setSelectedIndex(0);

        } else {

        }
    }//GEN-LAST:event_btn_Cancelar_MovimientoActionPerformed

    private void btn_Editar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_MovimientoActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Editar este Movimiento?");
        if (JOptionPane.OK_OPTION == a) {

            try {

                String id_movimiento;
                String tipo_movimiento;
                String fecha_movimiento;
                double saldo;
                String n_cuenta;
                String cuenta_destino;

                id_movimiento = txt_buscar_id.getText().trim();
                tipo_movimiento = txt_tm.getText().trim();
                fecha_movimiento = String.valueOf(now);
                saldo = Double.parseDouble(txt_s.getText().trim());
                n_cuenta = String.valueOf(combo_nc.getSelectedItem());
                cuenta_destino = String.valueOf(combo_cd.getSelectedItem());

                String mensaje = "EditMovimiento " + id_movimiento + " " + tipo_movimiento + " " + fecha_movimiento + " " + saldo + " " + n_cuenta + " " + cuenta_destino + " ";
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

        } else {

        }
    }//GEN-LAST:event_btn_Editar_MovimientoActionPerformed

    private void btn_Eliminar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Eliminar_MovimientoActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Eliminar este Movimiento?");

        if (JOptionPane.OK_OPTION == a) {

            try {
                String id_movimientos;
                id_movimientos = txt_buscar_id.getText();
                String mensaje = "DeleteMovimiento " + id_movimientos + " Registro Borrado";
                byte datos[] = mensaje.getBytes();
                //crear enviarPaquete

                DatagramPacket snd = ip.Direccion(datos);
                socket.send(snd);//enviar paquete
            } catch (IOException exceptionES) {
                exceptionES.printStackTrace();
            }
            try {
                socket = new DatagramSocket();
            } //atrapar los problemas que puedan ocurrir al crear objeto DatagramSocket
            catch (SocketException excepcionSocket) {
                excepcionSocket.printStackTrace();
                System.exit(1);
            }

        } else {

        }
    }//GEN-LAST:event_btn_Eliminar_MovimientoActionPerformed

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

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrincipalForm().setVisible(true);
            }
        });
    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            calcula();
            JLabel_Hora.setText(hora + ":" + minutos + ":" + segundos + " " + ampm);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void calcula() {
        Calendar c = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        c.setTime(fechaHoraActual);
        ampm = c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        if (ampm.equals("PM")) {
            int h = c.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = c.get(Calendar.HOUR_OF_DAY) > 9 ? "" + c.get(Calendar.HOUR_OF_DAY) : "0" + c.get(Calendar.HOUR_OF_DAY);
        }
        minutos = c.get(Calendar.MINUTE) > 9 ? "" + c.get(Calendar.MINUTE) : "0" + c.get(Calendar.MINUTE);
        segundos = c.get(Calendar.SECOND) > 9 ? "" + c.get(Calendar.SECOND) : "0" + c.get(Calendar.SECOND);
    }

    private void esperarPaquetesClientes() {
        try {
            //establecer el paquete
            byte datos[] = new byte[100];
            DatagramPacket recibirPaquete = new DatagramPacket(
                    datos, datos.length);
            socket.receive(recibirPaquete);//esperar un paquete
            String cad = (new String(recibirPaquete.getData(),
                    0, recibirPaquete.getLength()));
            String[] variables;
            variables = cad.split(" ");

            txt_id_cliente.setText(variables[0]);
            txt_Nombre.setText(variables[1]);
            txt_Ap_Paterno.setText(variables[2]);
            txt_Ap_Materno.setText(variables[3]);

            if (variables[4].equals("Masculino")) {

                rad_Masculino.setSelected(true);

            } else {

                rad_Femenino.setSelected(true);

            }

            txt_Direccion.setText(variables[5]);
            txt_Telefono.setText(variables[6]);
            txt_Email.setText(variables[7]);
            combo_Pais.setSelectedItem(v.reemplazar_guion_textos(variables[8]));
            combo_Tipo_Cuenta.setSelectedItem(variables[9]);

        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }//fin del metodo e

    private void esperarPaquetesBanco() {
        try {
            //establecer el paquete
            byte datos[] = new byte[100];
            DatagramPacket recibirPaquete = new DatagramPacket(
                    datos, datos.length);
            socket.receive(recibirPaquete);//esperar un paquete
            String cad = (new String(recibirPaquete.getData(),
                    0, recibirPaquete.getLength()));
            String[] variables;
            variables = cad.split(" ");

            txt_id_Buscar_Banco.setText(variables[0]);
            txt_Telefono_Banco.setText(variables[1]);
            txt_Direccion_Banco.setText(variables[2]);
            txt_Sucursal_Banco.setText(variables[3]);

        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }//fin del metodo e

    private void esperarPaquetesCuentas() {
        try {
            //establecer el paquete
            byte datos[] = new byte[100];
            DatagramPacket recibirPaquete = new DatagramPacket(
                    datos, datos.length);
            socket.receive(recibirPaquete);//esperar un paquete
            String cad = (new String(recibirPaquete.getData(),
                    0, recibirPaquete.getLength()));
            String[] variables;
            variables = cad.split(" ");

            jTextFieldIdClienteCuenta.setText(variables[0]);
            jTextFieldIdUsuarioCuenta.setText(variables[1]);
            jTextFieldNoDeCuenta.setText(variables[2]);
            jTextFieldFechaAperturaCuenta.setText(variables[3]);
            jComboBoxTipoCuenta.setSelectedItem(variables[4]);
            jTextFieldSaldoAperturaCuenta.setText(variables[6]);

        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }//fin del metodo e

    private void esperarPaquetesSeguros() {
        try {
            //establecer el paquete
            byte datos[] = new byte[100];
            DatagramPacket recibirPaquete = new DatagramPacket(
                    datos, datos.length);
            socket.receive(recibirPaquete);//esperar un paquete
            String cad = (new String(recibirPaquete.getData(),
                    0, recibirPaquete.getLength()));
            String[] variables;
            variables = cad.split(" ");

            txt_idSeguro_Buscar_Seguros.setText(variables[0]);
            Combo_Empresas_Seguros.setSelectedItem(variables[1]);
            Combo_Tipo_Seguro.setSelectedItem(variables[2]);
            txt_monto_seguro.setText(variables[3]);
            Combo_Cuenta_Seguros.setSelectedItem(variables[4]);

        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }//fin del metodo e

    private void esperarPaquetesUsuarios() {
        try {
            //establecer el paquete
            byte datos[] = new byte[100];
            DatagramPacket recibirPaquete = new DatagramPacket(
                    datos, datos.length);
            socket.receive(recibirPaquete);//esperar un paquete
            String cad = (new String(recibirPaquete.getData(),
                    0, recibirPaquete.getLength()));
            String[] variables;
            variables = cad.split(" ");

            usuario_txt_id1.setText(variables[0]);
            usuario_txt_nombre2.setText(variables[1]);
            usuario_txt_parteno1.setText(variables[2]);
            usuario_txt_materno.setText(variables[3]);
            usuario_txt_telefono1.setText(variables[4]);
            usuario_txt_email1.setText(variables[5]);
            usuario_txt_domicilio.setText(variables[6]);

        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }//fin del metodo e

    private void esperarPaquetesMovimiento() {
        try {
            //establecer el paquete
            byte datos[] = new byte[100];
            DatagramPacket recibirPaquete = new DatagramPacket(
                    datos, datos.length);
            socket.receive(recibirPaquete);//esperar un paquete
            String cad = (new String(recibirPaquete.getData(),
                    0, recibirPaquete.getLength()));
            String[] variables;
            variables = cad.split(" ");

            if (variables[1].equals("null")) {

                JOptionPane.showMessageDialog(this, "No se encontro este resultado");

            } else {
                txt_id.setText(variables[0]);
                txt_tm.setText(variables[1]);
                txt_fm.setText(variables[2] + " " + variables[3] + " " + variables[4] + " " + variables[5] + " " + variables[6] + " " + variables[7]);
                txt_s.setText(variables[8]);
                combo_cd.setName(variables[9]);
                combo_nc.setName(variables[10]);
            }

        } catch (IOException excepcion) {

            excepcion.printStackTrace();
        }
    }//fin del metodo e


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Combo_Cliente_Banco;
    private javax.swing.JComboBox<String> Combo_Cuenta_Seguros;
    private javax.swing.JComboBox<String> Combo_Empresas_Seguros;
    private javax.swing.JComboBox<String> Combo_Tipo_Seguro;
    private javax.swing.JLabel JLabel_Hora;
    public static javax.swing.JLabel JLabel_N_Cuenta;
    private javax.swing.JPanel JPanelClientes;
    private javax.swing.JPanel JPanel_Usuarios;
    private javax.swing.JTabbedPane JTabbedPrincipal;
    private javax.swing.JTree JTree_Inicio;
    private javax.swing.JButton btn_Buscar_Banco;
    private javax.swing.JButton btn_Buscar_Cliente;
    private javax.swing.JButton btn_Buscar_Movimiento;
    private javax.swing.JButton btn_Buscar_Seguros;
    private javax.swing.JButton btn_Cancelar_Banco;
    private javax.swing.JButton btn_Cancelar_Cliente;
    private javax.swing.JButton btn_Cancelar_Movimiento;
    private javax.swing.JButton btn_Cancelar_Seguros;
    private javax.swing.JButton btn_Editar_Banco;
    private javax.swing.JButton btn_Editar_Cliente;
    private javax.swing.JButton btn_Editar_Movimiento;
    private javax.swing.JButton btn_Editar_Seguros;
    private javax.swing.JButton btn_Eliminar_Banco;
    private javax.swing.JButton btn_Eliminar_Cliente;
    private javax.swing.JButton btn_Eliminar_Eliminar;
    private javax.swing.JButton btn_Eliminar_Movimiento;
    private javax.swing.JButton btn_Guardar_Banco;
    private javax.swing.JButton btn_Guardar_Cliente;
    private javax.swing.JButton btn_Guardar_Movimiento;
    private javax.swing.JButton btn_Guardar_Seguros;
    private javax.swing.JButton btn_Nuevo_Banco;
    private javax.swing.JButton btn_Nuevo_Cliente;
    private javax.swing.JButton btn_Nuevo_Movimiento;
    private javax.swing.JButton btn_Nuevo_Seguros;
    private javax.swing.JButton btn_cuentasRegistradas;
    private javax.swing.JButton btn_movimientos;
    private javax.swing.JButton btn_nomina;
    private javax.swing.JComboBox<String> combo_Pais;
    private javax.swing.JComboBox<String> combo_Tipo_Cuenta;
    private javax.swing.JComboBox<String> combo_cd;
    private javax.swing.JComboBox<String> combo_nc;
    private javax.swing.ButtonGroup groupSexo_Cliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonBuscarCuenta;
    private javax.swing.JButton jButtonEditarCuenta;
    private javax.swing.JButton jButtonEliminarCuenta;
    private javax.swing.JButton jButtonGuardarCuenta;
    private javax.swing.JComboBox<String> jComboBoxTipoCuenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldBuscarCuenta;
    private javax.swing.JTextField jTextFieldFechaAperturaCuenta;
    private javax.swing.JTextField jTextFieldIdClienteCuenta;
    private javax.swing.JTextField jTextFieldIdUsuarioCuenta;
    private javax.swing.JTextField jTextFieldNoDeCuenta;
    private javax.swing.JTextField jTextFieldSaldoAperturaCuenta;
    private javax.swing.JRadioButton rad_Femenino;
    private javax.swing.JRadioButton rad_Masculino;
    private javax.swing.JTextField txt_Ap_Materno;
    private javax.swing.JTextField txt_Ap_Paterno;
    private javax.swing.JTextField txt_Direccion;
    private javax.swing.JTextField txt_Direccion_Banco;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Nombre;
    private javax.swing.JTextField txt_Sucursal_Banco;
    private javax.swing.JTextField txt_Telefono;
    private javax.swing.JTextField txt_Telefono_Banco;
    private javax.swing.JTextField txt_buscar_id;
    private javax.swing.JTextField txt_fm;
    public static javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_idSeguro_Buscar_Seguros;
    private javax.swing.JTextField txt_id_Buscar_Banco;
    private javax.swing.JTextField txt_id_cliente;
    private javax.swing.JFormattedTextField txt_monto_seguro;
    private javax.swing.JTextField txt_nombre_Buscar;
    private javax.swing.JTextField txt_s;
    private javax.swing.JTextField txt_tm;
    private javax.swing.JLabel usuario_label_buscar;
    private javax.swing.JLabel usuario_label_domicilio;
    private javax.swing.JLabel usuario_label_email1;
    private javax.swing.JLabel usuario_label_id1;
    private javax.swing.JLabel usuario_label_materno;
    private javax.swing.JLabel usuario_label_nombre2;
    private javax.swing.JLabel usuario_label_paterno1;
    private javax.swing.JLabel usuario_label_telefono1;
    private javax.swing.JTextField usuario_txt_buscar;
    private javax.swing.JTextField usuario_txt_domicilio;
    private javax.swing.JTextField usuario_txt_email1;
    private javax.swing.JTextField usuario_txt_id1;
    private javax.swing.JTextField usuario_txt_materno;
    private javax.swing.JTextField usuario_txt_nombre2;
    private javax.swing.JTextField usuario_txt_parteno1;
    private javax.swing.JTextField usuario_txt_telefono1;
    private javax.swing.JButton usuarios_jButton_buscar;
    private javax.swing.JButton usuarios_jButton_cancelar;
    private javax.swing.JButton usuarios_jButton_editar;
    private javax.swing.JButton usuarios_jButton_eliminar;
    private javax.swing.JButton usuarios_jButton_guardar;
    private javax.swing.JButton usuarios_jButton_nuevo;
    // End of variables declaration//GEN-END:variables

    /*
    MODIFICACIONES A LA BASE DE DATOS.
    
    Luis - Cambiamos la fecha a Varchar en Movimientos y eliminamos la Llave Foranea de id_movimientos en Cuenta.
    Beth - Cambiamos la fecha a Varchar en Empresas.
    Reyes - Agrege campo de Estado en Banco, Seguros, Cambiamos Llave Foranea de id_cuenta por n_cuenta en Seguros.
    Gus - Agregar estado en datos Usuario.
    
    ----------------
    
    Siempre despues de dar un push hacer Clean and Build
    
    ----------------
    *****PENDIENTES*****
    Remplazar todos los espacios por _ Listo Metodo!.
    Validar campos.
    
     */
}
