package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Empresa_DTO {

    private String id_empresa;
    private String fecha_apertura;
    private String monto;
    private String plazo;
    private String taza;
    private String representante;
    private String id_banco;
    private String tipo_cuenta;

    private static ResultSet rs;
    private static Statement sentencia;

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getFecha_apertura() {
        return fecha_apertura;
    }

    public void setFecha_apertura(String fecha_apertura) {
        this.fecha_apertura = fecha_apertura;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getTaza() {
        return taza;
    }

    public void setTaza(String taza) {
        this.taza = taza;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getId_banco() {
        return id_banco;
    }

    public void setId_banco(String id_banco) {
        this.id_banco = id_banco;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public String toString() {
        return "Empresa_DTO{" + " fecha_apertura=" + fecha_apertura + ", monto=" + monto + ", plazo=" + plazo + ", taza=" + taza + ", representante=" + representante + ", id_banco=" + id_banco + ", tipo_cuenta=" + tipo_cuenta + ", rs=" + rs + '}';
    }

    public void Insert(Empresa_DTO dtoE, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO empresa (fecha_apertura, monto, plazo, taza, representante, id_banco, tipo_cuenta) "
                + "VALUES  (?, ?, ?, ?, ?, ?, ?);");

        ps.setString(1, dtoE.getFecha_apertura());
        ps.setString(2, dtoE.getMonto());
        ps.setString(3, dtoE.getPlazo());
        ps.setString(4, dtoE.getTaza());
        ps.setString(5, dtoE.getRepresentante());
        ps.setString(6, dtoE.getId_banco());
        ps.setString(7, dtoE.getTipo_cuenta());
        ps.executeUpdate();
    }

    public void Edit(Empresa_DTO dtoE, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE empresa SET fecha_apertura = ?, monto = ?, plazo = ?, taza = ?, representante = ?, id_banco = ?, tipo_cuenta = ? "
                + "WHERE id_empresa = '" + this.id_empresa + "';");

        ps.setString(1, dtoE.getFecha_apertura());
        ps.setString(2, dtoE.getMonto());
        ps.setString(3, dtoE.getPlazo());
        ps.setString(4, dtoE.getTaza());
        ps.setString(5, dtoE.getRepresentante());
        ps.setString(6, dtoE.getId_banco());
        ps.setString(7, dtoE.getTipo_cuenta());

        ps.executeUpdate();

    }

    public void Search(Empresa_DTO dtoE, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM empresa WHERE id_empresa = '" + this.id_empresa + "';");
        rs = stmt1.executeQuery();

        if (rs.next()) {
            this.id_empresa = rs.getString("id_empresa");
            this.fecha_apertura = rs.getString("fecha_apertura");
            this.monto = rs.getString("monto");
            this.plazo = rs.getString("plazo");
            this.taza = rs.getString("taza");
            this.representante = rs.getString("representante");
            this.id_banco = rs.getString("id_banco");
            this.tipo_cuenta = rs.getString("tipo_cuenta");

        }

    }

}
