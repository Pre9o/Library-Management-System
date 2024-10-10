import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO extends DAO<Usuario, String>{
    @Override
    protected String getDeleteSQL() {
        return "DELETE from usuarios where login = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE usuarios SET login = ?, nome = ?, professor = ?, quantidadedelivros = ?, multado = ?, valormulta = ? WHERE login = ?";
    }

    @Override
    protected String getReadSQL() {
        return "SELECT * FROM usuarios;";
    }

    @Override
    protected String getCreateSQL() {
        return "INSERT INTO usuarios (login, nome, professor, quantidadedelivros, multado, valormulta) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setDeleteSQL(PreparedStatement stmt, String usuario) throws SQLException {
        stmt.setString(1, usuario);
    }

    @Override
    protected void setUpdateSQL(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.login);
        stmt.setString(2, usuario.nome);
        stmt.setBoolean(3, usuario.professor);
        stmt.setInt(4, usuario.quantidadeLivros);
        stmt.setString(5, usuario.login);
        stmt.setBoolean(6, usuario.multado);
        stmt.setInt(7, usuario.valormulta);
    }

    @Override
    protected void setCreateSQL(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.login);
        stmt.setString(2, usuario.nome);
        stmt.setBoolean(3, usuario.professor);
        stmt.setInt(4, usuario.quantidadeLivros);
        stmt.setBoolean(5, usuario.multado);
        stmt.setInt(6, usuario.valormulta);
    }

    @Override
    protected Usuario readObject(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getString("nome"),
                rs.getString("login"),
                rs.getBoolean("professor"),
                rs.getInt("quantidadedelivros"),
                rs.getBoolean("multado"),
                rs.getInt("valormulta"));
    }
}
