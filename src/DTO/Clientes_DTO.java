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

/**
 *
 * @author reyes
 */
public class Clientes_DTO {

    private String id_clientes;
    private String nombre;
    private String ap_Paterno;
    private String ap_Materno;
    private String sexo;
    private String direccion;
    private String telefono;
    private String email;
    private String pais;
    private String tipo_cuenta;

    private ResultSet rs;

    public String getId_clientes() {
        return id_clientes;
    }

    public void setId_clientes(String id_clientes) {
        this.id_clientes = id_clientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp_Paterno() {
        return ap_Paterno;
    }

    public void setAp_Paterno(String ap_Paterno) {
        this.ap_Paterno = ap_Paterno;
    }

    public String getAp_Materno() {
        return ap_Materno;
    }

    public void setAp_Materno(String ap_Materno) {
        this.ap_Materno = ap_Materno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    @Override
    public String toString() {
        return "Clientes_DTO{" + "nombre=" + nombre + ", ap_Paterno=" + ap_Paterno + ", ap_Materno=" + ap_Materno + ", sexo=" + sexo + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", pais=" + pais + ", tipo_cuenta=" + tipo_cuenta + '}';
    }

    public void Insert(Clientes_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO clientes (nombre_Cliente, a_paterno, a_materno, sexo, direccion, telefono, email, pais, tipo_cliente, estado) "
                + "VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        ps.setString(1, dto.getNombre());
        ps.setString(2, dto.getAp_Paterno());
        ps.setString(3, dto.getAp_Materno());
        ps.setString(4, dto.getSexo());
        ps.setString(5, dto.getDireccion());
        ps.setString(6, dto.getTelefono());
        ps.setString(7, dto.getEmail());
        ps.setString(8, dto.getPais());
        ps.setString(9, dto.getTipo_cuenta());
        ps.setString(10, "Activo");
        ps.executeUpdate();
    }

    public void Delete(Clientes_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("UPDATE clientes SET estado = 'Inactivo' WHERE id_clientes = '" + this.id_clientes + "';");
        stmt1.executeUpdate();

    }

    public void Edit(Clientes_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE clientes SET nombre_Cliente = ?, a_paterno = ?, a_materno = ?, sexo = ?, direccion = ?, telefono = ?, email = ?, pais = ?, tipo_cliente = ?, estado = ? "
                + "WHERE id_clientes = '" + this.id_clientes + "';");

        ps.setString(1, dto.getNombre());
        ps.setString(2, dto.getAp_Paterno());
        ps.setString(3, dto.getAp_Materno());
        ps.setString(4, dto.getSexo());
        ps.setString(5, dto.getDireccion());
        ps.setString(6, dto.getTelefono());
        ps.setString(7, dto.getEmail());
        ps.setString(8, dto.getPais());
        ps.setString(9, dto.getTipo_cuenta());
        ps.setString(10, "Activo");
        ps.executeUpdate();

    }

    public void Search(Clientes_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM clientes WHERE nombre_Cliente = '" + this.nombre + "';");
        rs = stmt1.executeQuery();

        if (rs.next()) {
            this.id_clientes = rs.getString("id_clientes");
            this.nombre = rs.getString("nombre_Cliente");
            this.ap_Paterno = rs.getString("a_Paterno");
            this.ap_Materno = rs.getString("a_Materno");
            this.sexo = rs.getString("sexo");
            this.direccion = rs.getString("direccion");
            this.telefono = rs.getString("telefono");
            this.email = rs.getString("email");
            this.pais = rs.getString("pais");
            this.tipo_cuenta = rs.getString("tipo_cliente");

        }

    }

}
