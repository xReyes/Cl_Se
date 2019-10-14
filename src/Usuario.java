
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {

    private String nombre;
    private String password;
    private String perfil;
    private String a_paterno;
    private String a_materno;
    private String telefono;
    private String email;
    private String domicilio;

    public static String id_usuario;

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

    public void Insert(Connection conn) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        ps = conn.prepareStatement("SELECT MAX(id_usuario) AS id_usuario FROM usuarios;");
        rs = ps.executeQuery();
        if (rs.next()) {
            id = (rs.getInt(1) + 1);

        }

        PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO usuarios (nombre, perfil, password) VALUES  (?, ?, ?);");
        stmt1.setString(1, this.getNombre());
        stmt1.setString(2, this.getPerfil());
        stmt1.setString(3, this.getPassword());
        stmt1.executeUpdate();

        PreparedStatement st = conn.prepareStatement("INSERT INTO datos_usuarios (id_usuarios, nombre, a_paterno, a_materno, telefono, email, domicilio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);");
        st.setInt(1, id);
        st.setString(2, this.getNombre());
        st.setString(3, this.getA_paterno());
        st.setString(4, this.getA_materno());
        st.setString(5, this.getTelefono());
        st.setString(6, this.getEmail());
        st.setString(7, this.getDomicilio());
        st.executeUpdate();

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

        PreparedStatement stm = conn.prepareStatement("SELECT * FROM usuarios WHERE perfil = ? and password = ?; ");
        stm.setString(1, dto.getPerfil());
        stm.setString(2, dto.getPassword());
        rs = stm.executeQuery();

        if (rs.next()) {

            if (this.perfil.equals(rs.getString("perfil")) && this.password.equals(rs.getString("password"))) {
                id_usuario = rs.getString("id_usuario");
                return true;

            }
        } else {

            return false;
        }

        return false;

    }
}
