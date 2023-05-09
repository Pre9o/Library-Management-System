import java.sql.*;

public class Biblioteca{

    private static Connection conn = null;
    public static Connection getConn(){
        if(conn != null){
            try{
                if(!conn.isClosed()){
                    return conn;
                }
            }catch (SQLException e) {
                System.out.println("Erro ao conectar com o banco de dados");
            }
        }

        String host = "localhost:3306";
        String db = "cadeiradedados";
        String url = "jdbc:mysql://"+host+"/"+db;
        String user = "root";
        String pwd = "123456";
        try {
            conn = DriverManager.getConnection(url, user, pwd);
        }catch (SQLException e){
            System.out.println("Erro ao conectar com o banco de dados");
            conn = null;
        }
        return conn;
    }

    public static int getIntIdLivro(){
        var conn = Biblioteca.getConn();
        var sql = "SELECT MAX(id) as max FROM livros";
        try {
            var stmt = conn.prepareStatement(sql);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max") + 1;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao ler livro");
        }
        return -1;
    }

    public static int getIntIdEmprestimo(){
        var conn = Biblioteca.getConn();
        var sql = "SELECT MAX(idemprestimo) as max FROM emprestimos";
        try {
            var stmt = conn.prepareStatement(sql);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max") + 1;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao ler livro");
        }
        return -1;
    }

    public static int getIntIdReserva(){
        var conn = Biblioteca.getConn();
        var sql = "SELECT MAX(idreservas) as max FROM reservas";
        try {
            var stmt = conn.prepareStatement(sql);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max") + 1;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao ler livro");
        }
        return -1;
    }
}


