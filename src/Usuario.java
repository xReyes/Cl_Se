
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Usuario {

    private String nombre;
    private String password;
    private String perfil;
    ResultSet rs;

    public void SetNombre(String nombre) {
        this.nombre = nombre;
    }

    public void SetPassword(String password) {
        this.password = password;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public void Insert(Connection conn) throws SQLException {
        PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO usuarios (nombre, perfil, password) " + "VALUES  (?, ?, ?)");
        stmt1.setString(1, this.getNombre());
        stmt1.setString(2, this.getPerfil());
        stmt1.setString(3, this.getPassword());
        stmt1.executeUpdate();
    }

    public void Delete(Connection conn) throws SQLException {
        PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM usuarios where nombre = '" + this.nombre + "'");
        stmt1.executeUpdate();

    }

    public void Select(Connection conn) throws SQLException {
        PreparedStatement stmt1 = conn.prepareStatement("select * from  usuarios where nombre = '" + this.nombre + "'");
        rs = stmt1.executeQuery();
        if (rs.next()) {
            this.perfil = rs.getString("perfil");
            this.password = rs.getString("password");
        }

    }

    public boolean Login(Usuario dto, Connection conn) throws SQLException {

        PreparedStatement stm = conn.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? and password = ?; ");
        stm.setString(1, dto.getNombre());
        stm.setString(2, dto.getPassword());
        rs = stm.executeQuery();

        if (rs.next()) {

            if (this.nombre.equals(rs.getString("nombre")) && this.password.equals(rs.getString("password"))) {

                return true;

            }
        } else {

            return false;
        }

        return false;

    }
}
