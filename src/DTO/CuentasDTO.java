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
 * @author Job Del Toro
 */
public class CuentasDTO {

    private int idCliente;
    private int noDeCuenta;
    private String fechaApertura;
    private String tipoCuenta;
    private double saldoApertura;
    private int idUsuario;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getNoDeCuenta() {
        return noDeCuenta;
    }

    public void setNoDeCuenta(int noDeCuenta) {
        this.noDeCuenta = noDeCuenta;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public double getSaldoApertura() {
        return saldoApertura;
    }

    public void setSaldoApertura(double saldoApertura) {
        this.saldoApertura = saldoApertura;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "CuentasDTO{" + "idCliente=" + idCliente + ", noDeCuenta=" + noDeCuenta + ", fechaApertura=" + fechaApertura + ", tipoCuenta=" + tipoCuenta + ", saldoApertura=" + saldoApertura + ", idUsuario=" + idUsuario + '}';
    }

    public void insert(CuentasDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO cuenta (n_cuenta, tipo_cuenta, id_movimiento, fecha_apertura, saldo_apertura, id_cliente, id_usuario) "
                + "VALUES  (?, ?, null, ?, ?, ?, ?);");

        ps.setInt(1, dto.getNoDeCuenta());
        ps.setString(2, dto.getTipoCuenta());
        ps.setString(3, dto.getFechaApertura());
        ps.setDouble(4, dto.getSaldoApertura());
        ps.setInt(5, dto.getIdCliente());
        ps.setInt(6, dto.getIdUsuario());
        ps.executeUpdate();
    }

    public void Delete(CuentasDTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("UPDATE clientes SET estado = ? WHERE nombre_Cliente = '" + dto.getTipoCuenta() + "';");
        stmt.setString(1, "Inactivo");
        stmt.executeUpdate();

    }

    public void Edit(Clientes_DTO dto, Connection conn) {

    }

    public CuentasDTO search(CuentasDTO dto, Connection conn) throws SQLException {

        ResultSet rs;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cuenta WHERE n_cuenta = ?;");
        stmt.setInt(1, dto.getNoDeCuenta());
        rs = stmt.executeQuery();

        if (rs.next()) {
            dto.setIdCliente(rs.getInt("id_cliente"));
            dto.setIdUsuario(rs.getInt("id_usuario"));
            dto.setNoDeCuenta(rs.getInt("n_cuenta"));
            dto.setFechaApertura(rs.getString("fecha_apertura"));
            dto.setTipoCuenta(rs.getString("tipo_cuenta"));
            dto.setSaldoApertura(rs.getDouble("saldo_apertura"));

        }
        return dto;
    }
}
