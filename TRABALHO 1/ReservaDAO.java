import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservaDAO extends DAO<Reserva, Integer>{
    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM reservas WHERE idreserva = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE reservas SET idlivro = ?, loginusuario = ?, data = ? WHERE idreserva = ?";
    }

    @Override
    protected String getReadSQL() {
        return "SELECT * FROM reservas";
    }

    @Override
    protected String getCreateSQL() {
        return "INSERT INTO reservas (idreserva, idlivro, loginusuario, data) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected void setDeleteSQL(PreparedStatement stmt, Integer reserva) throws SQLException {
        stmt.setInt(1, reserva);
    }

    @Override
    protected void setUpdateSQL(PreparedStatement stmt, Reserva reserva) throws SQLException {
        stmt.setInt(1, reserva.idreserva);
        stmt.setInt(2, reserva.idlivro);
        stmt.setString(3, reserva.loginusuario);
    }

    @Override
    protected void setCreateSQL(PreparedStatement stmt, Reserva reserva) throws SQLException {
        stmt.setInt(1, reserva.idreserva);
        stmt.setInt(2, reserva.idlivro);
        stmt.setString(3, reserva.loginusuario);
    }

    @Override
    protected Reserva readObject(ResultSet rs) throws SQLException {
        return new Reserva(
                rs.getInt("idlivro"),
                rs.getString("loginusuario"));
    }
}