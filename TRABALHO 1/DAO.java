import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<TValue, TId> {
    public void create(TValue t) {
        // Create object in database
        var conn = Biblioteca.getConn();
        var sql = getCreateSQL();
        try {
            var stmt = conn.prepareStatement(sql);
            setCreateSQL(stmt, t);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao criar DAO: "+ex.getMessage());
        }
    }

    public List<TValue> read() {
        // Read object from database
        var conn = Biblioteca.getConn();
        var sql = getReadSQL();
        List<TValue> list = new ArrayList<>();
        try {
            var stmt = conn.prepareStatement(sql);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                TValue t = readObject(rs);
                list.add(t);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao ler DAO");
        }
        return list;
    }

    public void update(TValue t) {
        // Update object in database
        var conn = Biblioteca.getConn();
        var sql = getUpdateSQL();
        try {
            var stmt = conn.prepareStatement(sql);
            setUpdateSQL(stmt, t);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar DAO");
        }
    }

    public void delete(TId t) {
        // Delete object from database
        var conn = Biblioteca.getConn();
        var sql = getDeleteSQL();
        try {
            var stmt = conn.prepareStatement(sql);
            setDeleteSQL(stmt, t);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar DAO");
        }
    }

    protected abstract String getDeleteSQL();
    protected abstract String getUpdateSQL();
    protected abstract String getReadSQL();
    protected abstract String getCreateSQL();

    protected abstract void setDeleteSQL(PreparedStatement stmt, TId t)throws SQLException;
    protected abstract void setUpdateSQL(PreparedStatement stmt, TValue t)throws SQLException;
    protected abstract void setCreateSQL(PreparedStatement stmt, TValue t)throws SQLException;

    
    protected abstract TValue readObject(ResultSet rs) throws SQLException;

}
