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
public class Seguros_DTO {

    private String id_seguro;
    private String empresa;
    private String tipo_seguro;
    private String monto;
    private String cuenta;

    private ResultSet rs;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipo_seguro() {
        return tipo_seguro;
    }

    public void setTipo_seguro(String tipo_seguro) {
        this.tipo_seguro = tipo_seguro;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getId_seguro() {
        return id_seguro;
    }

    public void setId_seguro(String id_seguro) {
        this.id_seguro = id_seguro;
    }

    public void Insert(Seguros_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO seguros (id_empresa, tipo_seguro, monto, n_cuenta, estado) "
                + "VALUES  (?, ?, ?, ?, 'Activo');");

        ps.setString(1, dto.getEmpresa());
        ps.setString(2, dto.getTipo_seguro());
        ps.setString(3, dto.getMonto());
        ps.setString(4, dto.getCuenta());
        ps.executeUpdate();
    }

    public void Delete(Seguros_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("UPDATE seguros SET estado = 'Inactivo' WHERE id_seguro = '" + this.id_seguro + "';");
        stmt1.executeUpdate();

    }

    public void Edit(Seguros_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE seguros SET id_empresa = ?, tipo_seguro = ?, monto = ?, n_cuenta = ? "
                + "WHERE id_seguro = '" + this.id_seguro + "';");

        ps.setString(1, dto.getEmpresa());
        ps.setString(2, dto.getTipo_seguro());
        ps.setString(3, dto.getMonto());
        ps.setString(4, dto.getCuenta());
        ps.executeUpdate();

    }

    public void Search(Seguros_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM seguros WHERE id_seguro = '" + this.id_seguro + "';");
        rs = stmt1.executeQuery();

        if (rs.next()) {
            this.id_seguro = rs.getString("id_seguro");
            this.empresa = rs.getString("id_empresa");
            this.tipo_seguro = rs.getString("tipo_seguro");
            this.monto = rs.getString("monto");
            this.cuenta = rs.getString("n_cuenta");

        }

    }

}
