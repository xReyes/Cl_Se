
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author reyes
 */
public class Direccion_IP {

    public static DatagramPacket Direccion(byte[] datos) {

        DatagramPacket sendPack = null;

        try {
            sendPack = new DatagramPacket(datos, datos.length, InetAddress.getLocalHost(), 5000);
//            sendPack = new DatagramPacket(datos, datos.length, InetAddress.getByName("192.168.0.201"), 5000);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Direccion_IP.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sendPack;

    }

}
