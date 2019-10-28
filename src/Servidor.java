
import DTO.Bancos_DTO;
import DTO.Clientes_DTO;
import DTO.Movimientos_DTO;
import DTO.Usuarios_DTO;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
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
public class Servidor extends javax.swing.JFrame {

    private JTextArea areaPantalla;
    public static DatagramSocket socket;
    public Connection conn;
    String mensaje;

    public Servidor() {
        super("Servidor");

        areaPantalla = new JTextArea();
        getContentPane().add(new JScrollPane(areaPantalla), BorderLayout.CENTER);
        setSize(400, 300);
        setVisible(true);

        //crar objeto DatagramaSocket para enviar y recivir paquetes
        try {
            socket = new DatagramSocket(5000);

        } //procesar los problemas que puedan ocurrir al crear el objeto DatagramSocket
        catch (SocketException excepcionSocket) {
            excepcionSocket.printStackTrace();
            System.exit(1);
        }

    }//fin del constructor de servidor

    //esperar a que llegen los paquetes, mostrar los datos y repetir el paquete al cliente
    private void esperarPaquetes() throws SQLException {

        while (true) {//iterar infinitamente
            try {
                //esrtablecer el paquete
                byte datos[] = new byte[100];
                DatagramPacket recibirPaquete
                        = new DatagramPacket(datos, datos.length);

                socket.receive(recibirPaquete);//espera al paquete
                if (recibirPaquete.getLength() != 0) {
                    JOptionPane.showMessageDialog(this, "Solicitud de Registro");

                    Conexion obj = new Conexion();
                    conn = obj.Conexion();

                    Clientes_DTO cliente_dto = new Clientes_DTO();
                    Movimientos_DTO movimiento_dto = new Movimientos_DTO();
                    Bancos_DTO banco_dto = new Bancos_DTO();
                    Usuarios_DTO usuariosDTO = new Usuarios_DTO();

                    String cad = (new String(recibirPaquete.getData(), 0, recibirPaquete.getLength()));
                    String[] variables;
                    variables = cad.split(" ");

                    switch (variables[0]) {
                        case "NewCliente":
                            cliente_dto.setNombre(variables[1]);
                            cliente_dto.setAp_Paterno(variables[2]);
                            cliente_dto.setAp_Materno(variables[3]);
                            cliente_dto.setSexo(variables[4]);
                            cliente_dto.setDireccion(variables[5]);
                            cliente_dto.setEmail(variables[6]);
                            cliente_dto.setTelefono(variables[7]);
                            cliente_dto.setPais(variables[8]);
                            cliente_dto.setTipo_cuenta(variables[9]);

                            cliente_dto.Insert(cliente_dto, conn);

                            JOptionPane.showMessageDialog(null, "Cliente Agregado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case "EditCliente":
                            cliente_dto.setId_clientes(variables[1]);
                            cliente_dto.setNombre(variables[2]);
                            cliente_dto.setAp_Paterno(variables[3]);
                            cliente_dto.setAp_Materno(variables[4]);
                            cliente_dto.setSexo(variables[5]);
                            cliente_dto.setDireccion(variables[6]);
                            cliente_dto.setEmail(variables[7]);
                            cliente_dto.setTelefono(variables[8]);
                            cliente_dto.setPais(variables[9]);
                            cliente_dto.setTipo_cuenta(variables[10]);

                            cliente_dto.Edit(cliente_dto, conn);

                            JOptionPane.showMessageDialog(null, "Cliente Editado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case "DeleteCliente":
                            cliente_dto.setId_clientes(variables[1]);

                            cliente_dto.Delete(cliente_dto, conn);

                            JOptionPane.showMessageDialog(null, "Cliente Eliminado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case "SearchCliente":
                            cliente_dto.setNombre(variables[1]);

                            cliente_dto.Search(cliente_dto, conn);

                            mensaje = cliente_dto.getId_clientes() + " " + cliente_dto.getNombre() + " " + cliente_dto.getAp_Paterno() + " " + cliente_dto.getAp_Materno() + " " + cliente_dto.getSexo() + " " + cliente_dto.getDireccion() + " " + cliente_dto.getTelefono() + " " + cliente_dto.getEmail() + " " + cliente_dto.getPais() + " " + cliente_dto.getTipo_cuenta() + " ";
                            break;

                        case "NewMovimiento":
                            Date now = new Date(System.currentTimeMillis());
                            movimiento_dto.setTipo_movimiento(variables[1]);
                            movimiento_dto.setFecha_movimiento(String.valueOf(now));
                            movimiento_dto.setSaldo(Double.parseDouble(variables[8]));
                            movimiento_dto.setN_cuenta(variables[9]);
                            movimiento_dto.setCuenta_destino(variables[10]);

                            movimiento_dto.Insert(movimiento_dto, conn);

                            JOptionPane.showMessageDialog(null, "Movimiento Agreado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case "NewBanco":
                            banco_dto.setTelefono(variables[1]);
                            banco_dto.setDireccion(variables[2]);
                            banco_dto.setSucursal(variables[3]);
                            banco_dto.setId_cliente(variables[4]);

                            banco_dto.Insert(banco_dto, conn);

                            JOptionPane.showMessageDialog(null, "Banco Agregado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case "EditBanco":
                            banco_dto.setId_banco(variables[1]);
                            banco_dto.setTelefono(variables[2]);
                            banco_dto.setDireccion(variables[3]);
                            banco_dto.setSucursal(variables[4]);
                            banco_dto.setId_cliente(variables[5]);

                            banco_dto.Edit(banco_dto, conn);

                            JOptionPane.showMessageDialog(null, "Banco Editado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case "DeleteBanco":
                            banco_dto.setId_banco(variables[1]);

                            banco_dto.Delete(banco_dto, conn);

                            JOptionPane.showMessageDialog(null, "Banco Eliminado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case "SearchBanco":
                            banco_dto.setSucursal(variables[1]);

                            banco_dto.Search(banco_dto, conn);

                            mensaje = banco_dto.getId_banco() + " " + banco_dto.getTelefono() + " " + banco_dto.getDireccion() + " " + banco_dto.getSucursal() + " " + banco_dto.getId_cliente() + " ";
                            break;

                        case "NuevoUsuario":

                            usuariosDTO.setNombre(variables[1]);
                            usuariosDTO.setA_paterno(variables[2]);
                            usuariosDTO.setA_materno(variables[3]);
                            usuariosDTO.setTelefono(variables[4]);
                            usuariosDTO.setEmail(variables[5]);
                            usuariosDTO.setDomicilio(variables[6]);

                            usuariosDTO.Insert(usuariosDTO, conn);

                            JOptionPane.showMessageDialog(this, "Usuario agregado");

                            break;

                        case "NuevoUsuarioEditar":

                            usuariosDTO.setNombre(variables[1]);
                            usuariosDTO.setNombre(variables[2]);
                            usuariosDTO.setA_paterno(variables[3]);
                            usuariosDTO.setA_materno(variables[4]);
                            usuariosDTO.setTelefono(variables[5]);
                            usuariosDTO.setEmail(variables[6]);
                            usuariosDTO.setDomicilio(variables[7]);

                            usuariosDTO.Edit(usuariosDTO, conn);

                            JOptionPane.showMessageDialog(this, "Usuario Editado");

                            break;

                        case "NuevoUsuarioEliminar":

                            usuariosDTO.setId_usuarios(variables[1]);

                            usuariosDTO.Delete(usuariosDTO, conn);

                            JOptionPane.showMessageDialog(null, "Usuario Eliminado con Exito", "Exito!", JOptionPane.INFORMATION_MESSAGE);

                            break;

                        case "SearcUsuario":
                            usuariosDTO.setId_usuarios(variables[1]);

                            usuariosDTO.Search(usuariosDTO, conn);

                            mensaje = usuariosDTO.getId_usuarios() + " " + usuariosDTO.getNombre() + " " + usuariosDTO.getA_paterno() + " " + usuariosDTO.getA_materno() + " " + usuariosDTO.getTelefono() + " " + usuariosDTO.getEmail() + " " + usuariosDTO.getDomicilio() + " ";
                            break;

                        default:
                            break;
                    }

                }
                //mostrar la informacion del paquete recibido
                mostrarMensaje("\nRegistro Ingresado:"
                        + "\nDel host:" + recibirPaquete.getPort()
                        + "\nInformacion almacenada:\n\t" + new String(recibirPaquete.getData(),
                                0, recibirPaquete.getLength()) + datos);

                enviarPaqueteACliente(recibirPaquete);//envida paquete al cliente

            } //procesar los problemas que pu edan ocurrir al manipular el paquete
            catch (IOException excepcionES) {
                mostrarMensaje(excepcionES.toString() + "\n");
                excepcionES.printStackTrace();
            }

        }//fin de intruccion while

    }//fin del metodo esperarPaquetes

    //repetir el paquete al cliente 
    private void enviarPaqueteACliente(DatagramPacket recibirPaquete) throws IOException {
        try {

            mostrarMensaje("\n\nRepitiendo datos al cliente...");
            byte datos[] = new byte[100];
            datos = mensaje.getBytes();
            //crea paquete a enviar
            DatagramPacket enviarPaquete = new DatagramPacket(
                    datos, datos.length,
                    recibirPaquete.getAddress(), recibirPaquete.getPort());

            socket.send(enviarPaquete);//enviar el paquete
            mostrarMensaje("Paquete enviado\n");
        } catch (NullPointerException e) {
        }

    }// fin del metodo enviarPaqueteACliente

    //metodo utilitario que es llamado desde otros subprocesos para manipular a
    //area pantalla en el subproceso despachador de eventos
    private void mostrarMensaje(final String mensajeAMostrar) {
        //mostrar el mensaje del subproceso de ejecucion despachador de eventos
        SwingUtilities.invokeLater(
                new Runnable() {//clase interna para asegurar que la giu se actualice apropiadamente
            public void run()//actualiza areaPantalla 
            {
                areaPantalla.append(mensajeAMostrar);
                areaPantalla.setCaretPosition(
                        areaPantalla.getText().length());

            }
        }//fin de la clase interna
        );//fin de la llamada a SwingUtilities.invokeLater
    }//fin del metodo mostrarMensaje

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws SQLException {

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

        Servidor aplicacion = new Servidor();
        aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplicacion.esperarPaquetes();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
