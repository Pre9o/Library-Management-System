import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class EmprestimoDAO extends DAO<Emprestimo, Integer> {
    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM emprestimos WHERE idemprestimo = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE emprestimos SET idlivro = ?, loginusuario = ?, data = ? WHERE idemprestimo = ?";
    }

    @Override
    protected String getReadSQL() {
        return "SELECT * FROM emprestimos";
    }

    @Override
    protected String getCreateSQL() {
        return "INSERT INTO emprestimos (idemprestimo, idlivro, loginusuario, data) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected void setDeleteSQL(PreparedStatement stmt, Integer emprestimo) throws SQLException {
        stmt.setInt(1, emprestimo);
    }

    @Override
    protected void setUpdateSQL(PreparedStatement stmt, Emprestimo emprestimo) throws SQLException {
        stmt.setInt(1, emprestimo.idemprestimo);
        stmt.setInt(2, emprestimo.idlivro);
        stmt.setString(3, emprestimo.loginusuario);
        stmt.setTimestamp(4, java.sql.Timestamp.valueOf(emprestimo.data));
    }

    @Override
    protected void setCreateSQL(PreparedStatement stmt, Emprestimo emprestimo) throws SQLException {
        stmt.setInt(1, emprestimo.idemprestimo);
        stmt.setInt(2, emprestimo.idlivro);
        stmt.setString(3, emprestimo.loginusuario);
        stmt.setTimestamp(4, java.sql.Timestamp.valueOf(emprestimo.data));
    }

    @Override
    protected Emprestimo readObject(ResultSet rs) throws SQLException {
        return new Emprestimo(
                rs.getInt("idemprestimo"),
                rs.getInt("idlivro"),
                rs.getString("loginusuario"),
                rs.getTimestamp("data").toLocalDateTime());
    }
}
