
import DTO.Clientes_DTO;
import DTO.CuentasDTO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;

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

    String buscar_Banco;

    private DatagramSocket socket;
    Direccion_IP ip = new Direccion_IP();

    String hora, minutos, segundos, ampm;
    Calendar calendario;
    Thread h1;

    //hey
    
    //REYES
    public PrincipalForm() {
        try {
            initComponents();

            socket = new DatagramSocket();
            dto = new Clientes_DTO();
            conn = new Conexion();

            setLocationRelativeTo(null);
            setTitle("Sistema Bancario");

            Generar_ID();
            fecha();

            groupSexo_Cliente.add(rad_Masculino);
            groupSexo_Cliente.add(rad_Femenino);

            h1 = new Thread(this);
            h1.start();

            txt_id_cliente.setVisible(false);

            btn_Editar_Cliente.setEnabled(false);
            btn_Eliminar_Cliente.setEnabled(false);

            btn_Editar_Movimiento.setEnabled(false);
            btn_Eliminar_Movimiento.setEnabled(false);

        } catch (SocketException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
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

            if (nodo.getUserObject().equals("Cuenta")) {

            }
            if (nodo.getUserObject().equals("Agregar Nuevo Cliente")) {

                JTabbedPrincipal.setSelectedIndex(1);

            }
            if (nodo.getUserObject().equals("Bancos")) {

            }
            if (nodo.getUserObject().equals("Empresas")) {

            }
            if (nodo.getUserObject().equals("Seguros")) {

            }
        } catch (NullPointerException e) {
        }
    }

    public void limpiar_Campos(JTextField campo) {

        campo.setText("");

    }

    public void limpar_Cliente() {

        limpiar_Campos(txt_Nombre);
        limpiar_Campos(txt_Ap_Paterno);
        limpiar_Campos(txt_Ap_Materno);
        limpiar_Campos(txt_Direccion);
        limpiar_Campos(txt_Email);
        limpiar_Campos(txt_Telefono);
        limpiar_Campos(txt_nombre_Buscar);

        groupSexo_Cliente.clearSelection();
        combo_Pais.setSelectedIndex(0);
        combo_Tipo_Cuenta.setSelectedIndex(0);

    }

    public void limpiar_Movimiento() {

        limpiar_Campos(txt_id);
        limpiar_Campos(txt_tm);
        limpiar_Campos(txt_fm);
        limpiar_Campos(txt_s);
        limpiar_Campos(txt_nc);
        limpiar_Campos(txt_cd);
        limpiar_Campos(txt_buscar_id);

        System.out.println(JPanelClientes.getSize());

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

    private void Generar_ID() {

        try {

            ID_AUTO(conn.Conexion());

        } catch (SQLException e) {
        }
    }

    private void fecha() {

        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");

        //System.out.println(date.format(now));
        //System.out.println(hour.format(now));
        //System.out.println(now);
        txt_fm.setText(String.valueOf(now));
    }

    public void limpiar_Banco() {

        limpiar_Campos(txt_Sucursal_Banco);
        limpiar_Campos(txt_Direccion_Banco);
        limpiar_Campos(txt_Telefono_Banco);
        limpiar_Campos(txt_nombre_Buscar_Banco);

        Combo_Cliente_Banco.setSelectedIndex(0);

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
        btn_estadoCuenta = new javax.swing.JButton();
        btn_nomina = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        JLabel_Hora = new javax.swing.JLabel();
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
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
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
        txt_id = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_tm = new javax.swing.JTextField();
        txt_fm = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_s = new javax.swing.JTextField();
        btn_Nuevo_Movimiento = new javax.swing.JButton();
        btn_Guardar_Movimiento = new javax.swing.JButton();
        btn_Cancelar_Movimiento = new javax.swing.JButton();
        btn_Editar_Movimiento = new javax.swing.JButton();
        btn_Eliminar_Movimiento = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        txt_cd = new javax.swing.JTextField();
        txt_nc = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_buscar_id = new javax.swing.JTextField();
        btn_Buscar_Movimiento = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txt_id_banco = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txt_nombre_Buscar_Banco = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ver Estado de Cuenta");
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
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ver Seguros");
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
        jPanel1.add(btn_movimientos);
        btn_movimientos.setBounds(400, 300, 200, 40);

        btn_estadoCuenta.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        btn_estadoCuenta.setText("Estado de Cuenta");
        jPanel1.add(btn_estadoCuenta);
        btn_estadoCuenta.setBounds(400, 370, 200, 40);

        btn_nomina.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        btn_nomina.setText("Nomina");
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
        combo_Pais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Barbados", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Ciudad del Vaticano", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guyana", "Guinea", "Guinea ecuatorial", "Guinea-Bisáu", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Macedonia del Norte", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "República Sudafricana", "Ruanda", "Rumanía", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Suazilandia", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue" }));
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

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Gustavo");
        JPanel_Usuarios.add(jLabel14);
        jLabel14.setBounds(16, 30, 51, 17);

        jLabel17.setText("ID");
        JPanel_Usuarios.add(jLabel17);
        jLabel17.setBounds(27, 73, 11, 14);
        JPanel_Usuarios.add(jTextField1);
        jTextField1.setBounds(90, 70, 140, 20);

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

        txt_id.setEnabled(false);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("ID:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Tipo Movimiento:");

        txt_fm.setEnabled(false);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Fecha Movimiento:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Saldo:");

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

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Cuenta Destino:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("Numero Cuenta:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Buscar por ID:");

        btn_Buscar_Movimiento.setText("Buscar");
        btn_Buscar_Movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Buscar_MovimientoActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nc, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_Editar_Movimiento)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Eliminar_Movimiento, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txt_buscar_id, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Buscar_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(txt_nc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txt_tm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(txt_cd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_fm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txt_s, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
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
        jLabel34.setText("Buscar por Nombre:");

        txt_nombre_Buscar_Banco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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
                .addGap(68, 68, 68)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txt_nombre_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btn_Nuevo_Banco)
                                .addGap(57, 57, 57)
                                .addComponent(btn_Guardar_Banco)
                                .addGap(49, 49, 49)
                                .addComponent(btn_Cancelar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btn_Editar_Banco)
                                .addGap(49, 49, 49)
                                .addComponent(btn_Eliminar_Banco))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel37))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_id_banco, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_Telefono_Banco)
                                        .addComponent(txt_Direccion_Banco)
                                        .addComponent(txt_Sucursal_Banco)
                                        .addComponent(Combo_Cliente_Banco, 0, 222, Short.MAX_VALUE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(68, 68, 68))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel34))
                    .addComponent(txt_nombre_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Buscar_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(txt_id_banco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Nuevo_Banco)
                    .addComponent(btn_Guardar_Banco)
                    .addComponent(btn_Cancelar_Banco)
                    .addComponent(btn_Editar_Banco)
                    .addComponent(btn_Eliminar_Banco))
                .addGap(50, 50, 50))
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 776, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
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

        try {

            String ap_Paterno;
            String ap_Materno;
            String sexo;
            String direccion;
            String telefono;
            String email;
            String pais;
            String tipo_cuenta;

            nombre_Cliente = txt_Nombre.getText().trim();
            ap_Paterno = txt_Ap_Paterno.getText().trim();
            ap_Materno = txt_Ap_Materno.getText().trim();
            sexo = sexo_Cliente();
            direccion = txt_Direccion.getText().trim();
            email = txt_Email.getText().trim();
            telefono = txt_Telefono.getText().trim();
            pais = combo_Pais.getSelectedItem().toString();
            tipo_cuenta = combo_Tipo_Cuenta.getSelectedItem().toString();

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
    }//GEN-LAST:event_btn_Eliminar_ClienteActionPerformed

    private void btn_Editar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_ClienteActionPerformed

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
                nombre_Cliente = txt_Nombre.getText().trim();
                ap_Paterno = txt_Ap_Paterno.getText().trim();
                ap_Materno = txt_Ap_Materno.getText().trim();
                sexo = sexo_Cliente();
                direccion = txt_Direccion.getText().trim();
                email = txt_Email.getText().trim();
                telefono = txt_Telefono.getText().trim();
                pais = combo_Pais.getSelectedItem().toString();
                tipo_cuenta = combo_Tipo_Cuenta.getSelectedItem().toString();

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
    }//GEN-LAST:event_btn_Editar_ClienteActionPerformed

    private void jButtonGuardarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarCuentaActionPerformed
        try {
            // TODO add your handling code here:

            CuentasDTO cuentasDTO = new CuentasDTO();

            cuentasDTO.setIdCliente(Integer.parseInt(jTextFieldIdClienteCuenta.getText()));
            cuentasDTO.setIdUsuario(Integer.parseInt(jTextFieldIdUsuarioCuenta.getText()));
            cuentasDTO.setNoDeCuenta(Integer.parseInt(jTextFieldNoDeCuenta.getText()));
            cuentasDTO.setTipoCuenta(jComboBoxTipoCuenta.getSelectedItem().toString());
            cuentasDTO.setFechaApertura(jTextFieldFechaAperturaCuenta.getText());
            cuentasDTO.setSaldoApertura(Double.parseDouble(jTextFieldSaldoAperturaCuenta.getText()));

            cuentasDTO.insert(cuentasDTO, conn.Conexion());

            JOptionPane.showMessageDialog(this, "cuenta agregada");
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonGuardarCuentaActionPerformed

    private void jButtonEditarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEditarCuentaActionPerformed

    private void jButtonEliminarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarCuentaActionPerformed

    private void btn_Nuevo_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Nuevo_MovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Nuevo_MovimientoActionPerformed

    private void btn_Guardar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_MovimientoActionPerformed

        try {

            String id_movimiento;
            String tipo_movimiento;
            String fecha_movimiento;
            double saldo;
            String n_cuenta;
            String cuenta_destino;

            Date now = new Date(System.currentTimeMillis());

            id_movimiento = txt_id.getText().trim();
            tipo_movimiento = txt_tm.getText().trim();
            fecha_movimiento = String.valueOf(now);
            saldo = Double.parseDouble(txt_s.getText().trim());
            n_cuenta = txt_nc.getText().trim();
            cuenta_destino = txt_cd.getText().trim();

            String mensaje = "NewMovimiento " + tipo_movimiento + " " + fecha_movimiento + " " + saldo + " " + n_cuenta + " " + cuenta_destino + " ";
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
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Cancelar_MovimientoActionPerformed

    private void btn_Editar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_MovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Editar_MovimientoActionPerformed

    private void btn_Eliminar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Eliminar_MovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Eliminar_MovimientoActionPerformed

    private void btn_Buscar_MovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Buscar_MovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Buscar_MovimientoActionPerformed

    private void btn_Buscar_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Buscar_BancoActionPerformed

        if (txt_nombre_Buscar_Banco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el Nombre para Buscar!", "Campo Vacio", JOptionPane.WARNING_MESSAGE);
            txt_nombre_Buscar.requestFocus();

        } else {

            try {
                //obtener mensaje del campo de texto y convertirlo en arrreglo byte
                buscar_Banco = txt_nombre_Buscar_Banco.getText().trim();
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

        try {

            String telefono;
            String direccion;
            String sucursal;
            String id_cliente;

            telefono = txt_Telefono_Banco.getText().trim();
            direccion = txt_Direccion_Banco.getText().trim();
            sucursal = txt_Sucursal_Banco.getText().trim();

            String cliente_id[] = Combo_Cliente_Banco.getSelectedItem().toString().trim().split("_");
            id_cliente = cliente_id[1];

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
                id_banco = txt_id_banco.getText().trim();

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
    }//GEN-LAST:event_btn_Editar_BancoActionPerformed

    private void btn_Eliminar_BancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Eliminar_BancoActionPerformed

        int a = JOptionPane.showConfirmDialog(this, "Estas Seguro de Eliminar el Banco?");
        if (JOptionPane.OK_OPTION == a) {

            try {
                String id_banco;
                id_banco = txt_id_banco.getText();
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
    }//GEN-LAST:event_btn_Eliminar_BancoActionPerformed

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
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalForm().setVisible(true);
            }
        });
    }

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
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
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
            combo_Pais.setSelectedItem(variables[8]);
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

            txt_id_banco.setText(variables[0]);
            txt_Telefono_Banco.setText(variables[1]);
            txt_Direccion_Banco.setText(variables[2]);
            txt_Sucursal_Banco.setText(variables[3]);
            Combo_Cliente_Banco.setSelectedItem(variables[4]);

        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }//fin del metodo e

    private void esperarPaquetesID_Cliente_Combo() {
        try {
            //establecer el paquete
            byte datos[] = new byte[100];
            DatagramPacket recibirPaquete = new DatagramPacket(
                    datos, datos.length);
            socket.receive(recibirPaquete);//esperar un paquete
            String cad = (new String(recibirPaquete.getData(),
                    0, recibirPaquete.getLength()));
            String[] variables;
            variables = cad.split(",");

            Combo_Cliente_Banco.addItem("Seleccionar....");

            for (Object objeto : variables) {

                Combo_Cliente_Banco.addItem(objeto.toString());
            }

        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }//fin del metodo e

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Combo_Cliente_Banco;
    private javax.swing.JLabel JLabel_Hora;
    private javax.swing.JPanel JPanelClientes;
    private javax.swing.JPanel JPanel_Usuarios;
    private javax.swing.JTabbedPane JTabbedPrincipal;
    private javax.swing.JTree JTree_Inicio;
    private javax.swing.JButton btn_Buscar_Banco;
    private javax.swing.JButton btn_Buscar_Cliente;
    private javax.swing.JButton btn_Buscar_Movimiento;
    private javax.swing.JButton btn_Cancelar_Banco;
    private javax.swing.JButton btn_Cancelar_Cliente;
    private javax.swing.JButton btn_Cancelar_Movimiento;
    private javax.swing.JButton btn_Editar_Banco;
    private javax.swing.JButton btn_Editar_Cliente;
    private javax.swing.JButton btn_Editar_Movimiento;
    private javax.swing.JButton btn_Eliminar_Banco;
    private javax.swing.JButton btn_Eliminar_Cliente;
    private javax.swing.JButton btn_Eliminar_Movimiento;
    private javax.swing.JButton btn_Guardar_Banco;
    private javax.swing.JButton btn_Guardar_Cliente;
    private javax.swing.JButton btn_Guardar_Movimiento;
    private javax.swing.JButton btn_Nuevo_Banco;
    private javax.swing.JButton btn_Nuevo_Cliente;
    private javax.swing.JButton btn_Nuevo_Movimiento;
    private javax.swing.JButton btn_estadoCuenta;
    private javax.swing.JButton btn_movimientos;
    private javax.swing.JButton btn_nomina;
    private javax.swing.JComboBox<String> combo_Pais;
    private javax.swing.JComboBox<String> combo_Tipo_Cuenta;
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JTextField jTextField1;
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
    private javax.swing.JTextField txt_cd;
    private javax.swing.JTextField txt_fm;
    public static javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_id_banco;
    private javax.swing.JTextField txt_id_cliente;
    private javax.swing.JTextField txt_nc;
    private javax.swing.JTextField txt_nombre_Buscar;
    private javax.swing.JTextField txt_nombre_Buscar_Banco;
    private javax.swing.JTextField txt_s;
    private javax.swing.JTextField txt_tm;
    // End of variables declaration//GEN-END:variables

    /*
    MODIFICACIONES A LA BASE DE DATOS.
    
    Luis - Cambiamos la fecha a Varchar en Movimientos y eliminamos la Llave Foranea de id_movimientos en Cuenta.
    Beth - Cambiamos la fecha a Varchar en Empresas.
    Reyes - Agrege campo de Estado en Banco.
    
    
     */
}
