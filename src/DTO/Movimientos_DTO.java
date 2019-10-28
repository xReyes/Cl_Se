package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Luis
 */
public class Movimientos_DTO {

    private String id_movimiento;
    private String tipo_movimiento;
    private String fecha_movimiento;
    private double saldo;
    private String n_cuenta;
    private String cuenta_destino;

    private ResultSet rs;

    Date now = new Date(System.currentTimeMillis());

    public String getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(String id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public String getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(String fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getN_cuenta() {
        return n_cuenta;
    }

    public void setN_cuenta(String n_cuenta) {
        this.n_cuenta = n_cuenta;
    }

    public String getCuenta_destino() {
        return cuenta_destino;
    }

    public void setCuenta_destino(String cuenta_destino) {
        this.cuenta_destino = cuenta_destino;
    }

    @Override
    public String toString() {
        return "Movimientos_DTO{" + "tipo_movimiento=" + tipo_movimiento + ", fecha_movimiento=" + fecha_movimiento + ", saldo=" + saldo + ", n_cuenta=" + n_cuenta + ", cuenta_destino=" + cuenta_destino + ", rs=" + rs + ", now=" + now + '}';
    }

    public void Insert(Movimientos_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO movimientos (tipo_movimiento, fecha_movimiento, saldo, n_cuenta, cuenta_destino, estatus) "
                + "VALUES  (?, ?, ?, ?, ?, ?);");

        ps.setString(1, dto.getTipo_movimiento());
        ps.setString(2, dto.getFecha_movimiento());
        ps.setDouble(3, dto.getSaldo());
        ps.setString(4, dto.getN_cuenta());
        ps.setString(5, dto.getCuenta_destino());
        ps.setString(6, "Activo");

        ps.executeUpdate();
    }

    public void Edit(Movimientos_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE movimientos SET tipo_movimiento = ?, fecha_movimiento = ?, saldo = ?, n_cuenta = ?, cuenta_destino = ?, estatus = ? "
                + "WHERE id_movimiento = '" + this.id_movimiento + "';");

        ps.setString(1, dto.getTipo_movimiento());
        ps.setString(2, dto.getFecha_movimiento());
        ps.setDouble(3, dto.getSaldo());
        ps.setString(4, dto.getN_cuenta());
        ps.setString(5, dto.getCuenta_destino());
        ps.setString(6, "Activo");

        ps.executeUpdate();

    }

    public void Delete(Movimientos_DTO dto, Connection conn) throws SQLException {
        PreparedStatement stmt1 = conn.prepareStatement("UPDATE movimientos SET estatus = 'Inactivo' WHERE id_movimiento = '" + this.id_movimiento + "';");
        stmt1.executeUpdate();

    }

    public void Search(Movimientos_DTO dto, Connection conn) throws SQLException {

        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM movimientos WHERE estatus = 'Activo' and id_movimiento = '" + this.id_movimiento + "';");
        rs = stmt1.executeQuery();

        if (rs.next()) {

            this.id_movimiento = rs.getString("id_movimiento");
            this.tipo_movimiento = rs.getString("tipo_movimiento");
            this.fecha_movimiento = rs.getString("fecha_movimiento");
            this.saldo = rs.getDouble("saldo");
            this.n_cuenta = rs.getString("n_cuenta");
            this.cuenta_destino = rs.getString("cuenta_destino");

        }
    }
}
