/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;

/**
 *
 * @author reyes
 */
public class Bancos_DTO {

    private String id_banco;
    private String telefono;
    private String direccion;
    private String sucursal;
    private String id_cliente;

    //DATOS CLIENTE
    private String cliente_completo;

    private ResultSet rs;

    public String getId_banco() {
        return id_banco;
    }

    public void setId_banco(String id_banco) {
        this.id_banco = id_banco;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCliente_completo() {
        return cliente_completo;
    }

    public void setCliente_completo(String cliente_completo) {
        this.cliente_completo = cliente_completo;
    }

    @Override
    public String toString() {
        return "Bancos_DTO{" + "id_banco=" + id_banco + ", telefono=" + telefono + ", direccion=" + direccion + ", sucursal=" + sucursal + ", id_cliente=" + id_cliente + '}';
    }

    public void Insert(Bancos_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO banco (telefono, direccion, sucursal, id_cliente, estado) "
                + "VALUES  (?, ?, ?, ?, 'Activo');");

        ps.setString(1, dto.getTelefono());
        ps.setString(2, dto.getDireccion());
        ps.setString(3, dto.getSucursal());
        ps.setString(4, dto.getId_cliente());
        ps.executeUpdate();
    }

    public void Delete(Bancos_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("UPDATE banco SET estado = 'Inactivo' WHERE id_banco = '" + this.id_banco + "';");
        stmt1.executeUpdate();

    }

    public void Edit(Bancos_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE banco SET telefono = ?, direccion = ?, sucursal = ?, id_cliente = ? "
                + "WHERE id_banco = '" + this.id_banco + "';");

        ps.setString(1, dto.getTelefono());
        ps.setString(2, dto.getDireccion());
        ps.setString(3, dto.getSucursal());
        ps.setString(4, dto.getId_cliente());
        ps.executeUpdate();

    }

    public void Search(Bancos_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM banco WHERE sucursal = '" + this.sucursal + "';");
        rs = stmt1.executeQuery();

        if (rs.next()) {
            this.id_banco = rs.getString("id_banco");
            this.telefono = rs.getString("telefono");
            this.direccion = rs.getString("direccion");
            this.sucursal = rs.getString("sucursal");
            this.id_cliente = rs.getString("id_cliente");

        }

    }

    public void Obtener_ID_Cliente(Connection conn) throws SQLException {

        PreparedStatement stm1 = conn.prepareStatement("SELECT CONCAT('_',id_clientes,'_',nombre_Cliente,'_',a_paterno, '_',a_materno, ' ') AS id_nombre FROM clientes WHERE estado = 'Activo';");
        rs = stm1.executeQuery();

        while (rs.next()) {
            list_id.add(rs.getString("id_nombre"));

        }
    }

    ArrayList<String> list_id = new ArrayList<>();

    public ArrayList<String> getList_id() {
        return list_id;
    }

    public void setList_id(ArrayList<String> list_id) {
        this.list_id = list_id;
    }

}
