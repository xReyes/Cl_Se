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

    private String tipo_movimiento;
    private String fecha_movimiento;
    private double saldo;
    private String n_cuenta;
    private String cuenta_destino;

    private ResultSet rs;

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
        return "Movimientos_DTO{" + "tipo_movimiento=" + tipo_movimiento + ", fecha_movimiento=" + fecha_movimiento + ", saldo=" + saldo + ", n_cuenta=" + n_cuenta + ", cuenta_destino=" + cuenta_destino + ", rs=" + rs + '}';
    }

    public void Insert(Movimientos_DTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO movimientos (tipo_movimiento, fecha_movimiento, saldo, n_cuenta, cuenta_destino) "
                + "VALUES  (?, ?, ?, ?, ?);");

        Date now = new Date(System.currentTimeMillis());

        ps.setString(1, dto.getTipo_movimiento());
        ps.setString(2, dto.getFecha_movimiento());
        ps.setDouble(3, dto.getSaldo());
        ps.setString(4, dto.getN_cuenta());
        ps.setString(5, dto.getCuenta_destino());

        ps.executeUpdate();
    }

//    public void Delete(Movimientos_DTO dto, Connection conn) throws SQLException {
//
//        PreparedStatement stmt1 = conn.prepareStatement("UPDATE movimientos SET id_movimiento = ? WHERE nombre_Cliente = '" + this.nombre + "';");
//        stmt1.setString(1, "Inactivo");
//        stmt1.executeUpdate();
//
//    }
//
//    public void Edit(Clientes_DTO dto, Connection conn) {
//
//    }
}
