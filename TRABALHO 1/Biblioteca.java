import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

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

    public static void cadastrarLivro(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o título do livro");
        var titulo = scanner.nextLine();
        System.out.println("Digite o autor do livro");
        var autor = scanner.nextLine();
        System.out.println("Digite o ISBN do livro");
        var isbn = scanner.nextLine();
        System.out.println("Digite a editora do livro");
        var editora = scanner.nextLine();
        System.out.println("Digite o ano do livro");
        var ano = scanner.nextInt();
        System.out.println("Digite a edição do livro");
        var edicao = scanner.nextInt();
        var id = Biblioteca.getIntIdLivro();
        var livro = new Livro(titulo, autor, isbn, editora, ano, edicao, id);
        var livroDAO = new LivrosDAO();
        livroDAO.create(livro);
    }

    public static void cadastrarUsuario(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o nome do usuário");
        var nome = scanner.nextLine();
        System.out.println("Digite o login do usuário");
        var login = scanner.nextLine();
        System.out.println("O usuário é professor? (Sim ou Não)");
        var professor = scanner.nextLine().equalsIgnoreCase("Sim");
        var usuario = new Usuario(nome, login, professor, 0);
        var usuarioDAO = new UsuarioDAO();
        usuarioDAO.create(usuario);
    }

    public static void realizarEmprestimo(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do livro");
        var idLivro = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o id do usuário");
        var idUsuario = scanner.nextLine();
        var usuarioDAO = new UsuarioDAO();
        var usuarios = usuarioDAO.read();
        var emprestimo = new Emprestimo(getIntIdEmprestimo(), idLivro, idUsuario, LocalDateTime.now());
        var emprestimoDAO = new EmprestimoDAO();
        var livroDAO = new LivrosDAO();
        var livros = livroDAO.read();
        for(Emprestimo emprestimos : emprestimoDAO.read()){
            if(emprestimos.idlivro == idLivro){
                System.out.println("Livro já emprestado");
                return;
            }
        }
        for(Livro livro : livros){
            if(livro.id == idLivro){
                for(Usuario usuario : usuarios){
                    if(Objects.equals(usuario.login, idUsuario)) {
                        if (usuario.professor) {
                            if (usuario.quantidadeLivros == 5) {
                                System.out.println("Usuário já possui 5 livros emprestados");
                                return;
                            }
                            usuario.quantidadeLivros++;
                        }
                        else {
                            if (usuario.quantidadeLivros == 3) {
                                System.out.println("Usuário já possui 3 livros emprestados");
                                return;
                            }
                            usuario.quantidadeLivros++;
                        }
                        usuarioDAO.update(usuario);
                        emprestimoDAO.create(emprestimo);
                        System.out.println("Empréstimo realizado com sucesso");
                        return;
                    }
                    else{
                        System.out.println("Usuário não encontrado");
                        System.out.println("Deseja cadastrar um novo usuário? (Sim ou Não)");
                        var resposta = scanner.nextLine();
                        if(resposta.equalsIgnoreCase("Sim")){
                            cadastrarUsuario();
                            emprestimoDAO.create(emprestimo);
                            System.out.println("Empréstimo realizado com sucesso");
                            return;
                        }
                        else{
                            return;
                        }
                    }
                }
            }
            else{
                System.out.println("Livro não encontrado");
                return;
            }
        }


    }

    public static void renovarEmprestimo(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do empréstimo");
        var idEmprestimo = scanner.nextInt();
        var emprestimoDAO = new EmprestimoDAO();
        var emprestimos = emprestimoDAO.read();
        for(Emprestimo emprestimo : emprestimos){
            if(emprestimo.idemprestimo == idEmprestimo){
                emprestimo.data = LocalDateTime.now();
                emprestimoDAO.update(emprestimo);
                System.out.println("Empréstimo renovado com sucesso");
            }
        }
    }

    public static void realizarDevolucao(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do empréstimo");
        var idEmprestimo = scanner.nextInt();
        var emprestimoDAO = new EmprestimoDAO();
        var emprestimos = emprestimoDAO.read();
        for(Emprestimo emprestimo : emprestimos){
            if(emprestimo.idemprestimo == idEmprestimo){
                var usuarioDAO = new UsuarioDAO();
                var usuarios = usuarioDAO.read();
                for(Usuario usuario : usuarios){
                    if(Objects.equals(usuario.login, emprestimo.loginusuario)){
                        usuario.quantidadeLivros--;
                        usuarioDAO.update(usuario);
                    }
                }
                emprestimoDAO.delete(idEmprestimo);
                System.out.println("Devolução realizada com sucesso");
            }
            else{
                System.out.println("Empréstimo não encontrado");
            }
        }

    }

    public static void realizarReserva(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do livro");
        var idLivro = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o id do usuário");
        var idUsuario = scanner.nextLine();
        var reserva = new Reserva(idLivro, idUsuario);
        var reservaDAO = new ReservaDAO();
        reservaDAO.create(reserva);
    }

    public static void cancelarReserva(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id da reserva");
        var idReserva = scanner.nextInt();
        var reservaDAO = new ReservaDAO();
        reservaDAO.delete(idReserva);
        System.out.println("Reserva cancelada com sucesso");
    }

    public static void listarLivros(){
        var livroDAO = new LivrosDAO();
        var livros = livroDAO.read();
        for (var livro : livros){
            System.out.println(livro);
        }
    }

    public static void listarUsuarios(){
        var usuarioDAO = new UsuarioDAO();
        var usuarios = usuarioDAO.read();
        for (var usuario : usuarios){
            System.out.println(usuario);
        }
    }

    public static void listarEmprestimos(){
        var emprestimoDAO = new EmprestimoDAO();
        var emprestimos = emprestimoDAO.read();
        for (var emprestimo : emprestimos){
            System.out.println(emprestimo);
        }
    }

    public static void listarReservas(){
        var reservaDAO = new ReservaDAO();
        var reservas = reservaDAO.read();
        for (var reserva : reservas){
            System.out.println(reserva);
        }
    }
}


