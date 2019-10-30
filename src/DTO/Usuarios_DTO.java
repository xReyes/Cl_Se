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
public class Usuarios_DTO {

    String id_usuarios;
    String nombre;
    String a_paterno;
    String a_materno;
    String telefono;
    String email;
    String domicilio;

    private ResultSet rs;

    public String getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(String id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getA_paterno() {
        return a_paterno;
    }

    public void setA_paterno(String a_paterno) {
        this.a_paterno = a_paterno;
    }

    public String getA_materno() {
        return a_materno;
    }

    public void setA_materno(String a_materno) {
        this.a_materno = a_materno;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Usuarios_DTO{" + "id_usuarios=" + id_usuarios + ", nombre=" + nombre + ", a_paterno=" + a_paterno + ", a_materno=" + a_materno + ", telefono=" + telefono + ", email=" + email + ", domicilio=" + domicilio + '}';
    }

    public void Insert(Usuarios_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO datos_usuarios (nombre, a_paterno, a_materno, telefono, email, domicilio, estado) "
                + "VALUES  (?, ?, ?, ?, ?, ?, 'Activo');");

        ps.setString(1, dto.getNombre());
        ps.setString(2, dto.getA_paterno());
        ps.setString(3, dto.getA_materno());
        ps.setString(4, dto.getTelefono());
        ps.setString(5, dto.getEmail());
        ps.setString(6, dto.getDomicilio());
        ps.executeUpdate();
    }

    public void Delete(Usuarios_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("UPDATE datos_usuarios SET estado = 'Inactivo' WHERE id_usuarios = '" + this.id_usuarios + "';");
        stmt1.executeUpdate();

    }

    public void Edit(Usuarios_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE datos_usuarios SET telefono = ?, email = ?, domicilio = ? "
                + "WHERE id_usuarios = '" + this.id_usuarios + "';");

        ps.setString(1, dto.getTelefono());
        ps.setString(2, dto.getEmail());
        ps.setString(3, dto.getDomicilio());
        ps.executeUpdate();

    }

    public void Search(Usuarios_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM datos_usuarios WHERE id_usuarios = '" + this.id_usuarios + "';");
        rs = stmt1.executeQuery();

        if (rs.next()) {
            this.id_usuarios = rs.getString("id_usuarios");
            this.nombre = rs.getString("nombre");
            this.a_paterno = rs.getString("a_paterno");
            this.a_materno = rs.getString("a_materno");
            this.telefono = rs.getString("telefono");
            this.email = rs.getString("email");
            this.domicilio = rs.getString("domicilio");

        }

    }

}
