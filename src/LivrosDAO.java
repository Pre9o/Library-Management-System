import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivrosDAO extends DAO<Livro, Integer>{
    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM livros WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE livros SET titulo = ?, autor = ?, edicao = ?, ano = ?, editora = ?, isbn = ? WHERE id = ?";
    }

    @Override
    protected String getReadSQL() {
        return "SELECT * FROM livros";
    }

    @Override
    protected String getCreateSQL() {
        return "INSERT INTO livros (id, titulo, autor, edicao, ano, editora, isbn) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setDeleteSQL(PreparedStatement stmt, Integer livro) throws SQLException {
        stmt.setInt(1, livro);
    }

    @Override
    protected void setUpdateSQL(PreparedStatement stmt, Livro livro) throws SQLException {
        stmt.setString(1, livro.titulo);
        stmt.setString(2, livro.autor);
        stmt.setInt(3, livro.edicao);
        stmt.setInt(4, livro.ano);
        stmt.setString(5, livro.editora);
        stmt.setString(6, livro.isbn);
        stmt.setInt(7, livro.id);
    }

    @Override
    protected void setCreateSQL(PreparedStatement stmt, Livro livro) throws SQLException {
        stmt.setInt(1, livro.id);
        stmt.setString(2, livro.titulo);
        stmt.setString(3, livro.autor);
        stmt.setInt(4, livro.edicao);
        stmt.setInt(5, livro.ano);
        stmt.setString(6, livro.editora);
        stmt.setString(7, livro.isbn);
    }

    @Override
    protected Livro readObject(ResultSet rs) throws SQLException {
        return new Livro(
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("isbn"),
                rs.getString("editora"),
                rs.getInt("ano"),
                rs.getInt("edicao"),
                rs.getInt("id"));
    }
}

