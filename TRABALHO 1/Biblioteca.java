import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        return tryCatch(conn, sql);
    }

    public static int tryCatch(Connection conn, String sql) {
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
        return tryCatch(conn, sql);
    }

    public static int getIntIdReserva(){
        var conn = Biblioteca.getConn();
        var sql = "SELECT MAX(idreservas) as max FROM reservas";
        return tryCatch(conn, sql);
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
        var usuario = new Usuario(nome, login, professor, 0, false, 0);
        var usuarioDAO = new UsuarioDAO();
        usuarioDAO.create(usuario);
        System.out.println("Usuário cadastrado com sucesso");
    }

    public static void realizarEmprestimo(){
        calcularMulta();
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do livro");
        var idLivro = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o login do usuário");
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
                            else if(usuario.multado){
                                System.out.println("Usuário está multado");
                                return;
                            }
                        }
                        else {
                            if (usuario.quantidadeLivros == 3) {
                                System.out.println("Usuário já possui 3 livros emprestados");
                                return;
                            }
                            else if(usuario.multado){
                                System.out.println("Usuário está multado");
                                return;
                            }
                        }
                        usuario.quantidadeLivros++;
                        usuarioDAO.update(usuario);
                        emprestimoDAO.create(emprestimo);
                        System.out.println("Empréstimo realizado com sucesso");
                    }
                    else{
                        System.out.println("Usuário não encontrado");
                        System.out.println("Deseja cadastrar um novo usuário? (Sim ou Não)");
                        var resposta = scanner.nextLine();
                        if(resposta.equalsIgnoreCase("Sim")){
                            cadastrarUsuario();
                            usuarioDAO = new UsuarioDAO();
                            usuarios = usuarioDAO.read();
                            for(Usuario usuario2 : usuarios){
                                if(Objects.equals(usuario2.login, idUsuario)) {
                                    usuario2.quantidadeLivros++;
                                    usuarioDAO.update(usuario2);
                                    emprestimoDAO.create(emprestimo);
                                    System.out.println("Empréstimo realizado com sucesso");
                                    return;
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
        System.out.println("Livro não encontrado");
    }

    public static void renovarEmprestimo(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do empréstimo");
        var idEmprestimo = scanner.nextInt();
        var emprestimoDAO = new EmprestimoDAO();
        var emprestimos = emprestimoDAO.read();
        var reservasDAO = new ReservaDAO();
        var reservas = reservasDAO.read();
        for(Emprestimo emprestimo : emprestimos){
            for(Reserva reserva : reservas){
                if(reserva.idlivro == emprestimo.idlivro){
                    System.out.println("O livro já está reservado");
                    return;
                }
            }
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
        calcularMulta();
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do livro");
        var idLivro = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o id do usuário");
        var idUsuario = scanner.nextLine();
        var usuarioDAO = new UsuarioDAO();
        var usuarios = usuarioDAO.read();
        for(Usuario usuario : usuarios){
            if(usuario.multado){
                System.out.println("Usuário está multado");
                return;
            }
        }
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

    public static void alterarLivro(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do livro");
        var idLivro = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o novo título do livro");
        var titulo = scanner.nextLine();
        System.out.println("Digite o novo autor do livro");
        var autor = scanner.nextLine();
        System.out.println("Digite o novo isbn do livro");
        var isbn = scanner.nextLine();
        System.out.println("Digite o novo editora do livro");
        var editora = scanner.nextLine();
        System.out.println("Digite o novo ano do livro");
        var ano = scanner.nextInt();
        System.out.println("Digite a nova edição do livro");
        var edicao = scanner.nextInt();
        scanner.nextLine();
        var livro = new Livro(titulo, autor, isbn, editora, ano, edicao, idLivro);
        var livroDAO = new LivrosDAO();
        livroDAO.update(livro);
        System.out.println("Livro alterado com sucesso");
    }

    public static void excluirLivro(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do livro");
        var idLivro = scanner.nextInt();
        var livroDAO = new LivrosDAO();
        livroDAO.delete(idLivro);
        System.out.println("Livro excluído com sucesso");
    }

    public static void excluirUsuario(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o login do usuário");
        var loginUsuario = scanner.nextLine();
        var usuarioDAO = new UsuarioDAO();
        usuarioDAO.delete(loginUsuario);
        System.out.println("Usuário excluído com sucesso");
    }

    public static void listarLivros(){
        var livroDAO = new LivrosDAO();
        var livros = livroDAO.read();
        for (var livro : livros){
            System.out.println(livro);
        }
    }

    public static void buscarLivro(){
        var scanner = new Scanner(System.in);
        System.out.println("Selecione uma opção de busca");
        System.out.println("1 - Por parte do Título");
        System.out.println("2 - Por editora");
        System.out.println("3 - Por isbn");
        var opcao = scanner.nextInt();
        scanner.nextLine();
        var livroDAO = new LivrosDAO();
        var livros = livroDAO.read();
        switch (opcao) {
            case 1 -> {
                System.out.println("Digite parte do título");
                var titulo = scanner.nextLine();
                livroDAO = new LivrosDAO();
                livros = livroDAO.read();
                for (var livro : livros) {
                    if (livro.titulo.contains(titulo)) {
                        System.out.println(livro);
                    }
                }
            }
            case 2 -> {
                System.out.println("Digite o nome da editora");
                var editora = scanner.nextLine();
                livroDAO = new LivrosDAO();
                livros = livroDAO.read();
                for (var livro : livros) {
                    if (livro.editora.contains(editora)) {
                        System.out.println(livro);
                    }
                }
            }
            case 3 -> {
                System.out.println("Digite o isbn");
                var isbn = scanner.nextLine();
                livroDAO = new LivrosDAO();
                livros = livroDAO.read();
                for (var livro : livros) {
                    if (livro.isbn.contains(isbn)) {
                        System.out.println(livro);
                    }
                }
            }
        }
    }

    public static void buscarUsuario(){
        var scanner = new Scanner(System.in);
        System.out.println("Selecione uma opção de busca");
        System.out.println("1 - Por parte do nome");
        System.out.println("2 - Por login");
        var opcao = scanner.nextInt();
        scanner.nextLine();
        var usuarioDAO = new UsuarioDAO();
        var usuarios = usuarioDAO.read();
        switch (opcao) {
            case 1 -> {
                System.out.println("Digite parte do nome");
                var nome = scanner.nextLine();
                usuarioDAO = new UsuarioDAO();
                usuarios = usuarioDAO.read();
                for (var usuario : usuarios) {
                    if (usuario.nome.contains(nome)) {
                        System.out.println(usuario);
                    }
                }
            }
            case 2 -> {
                System.out.println("Digite o login");
                var login = scanner.nextLine();
                usuarioDAO = new UsuarioDAO();
                usuarios = usuarioDAO.read();
                for (var usuario : usuarios) {
                    if (usuario.login.contains(login)) {
                        System.out.println(usuario);
                    }
                }
            }
        }
    }

    public static void buscarEmprestimo(){
        var scanner = new Scanner(System.in);
        System.out.println("Selecione uma opção de busca");
        System.out.println("1 - Por isbn do livro");
        System.out.println("2 - Por parte do título do livro");
        System.out.println("3 - Por id do livro");
        System.out.println("4 - Por login do usuário");
        var opcao = scanner.nextInt();
        scanner.nextLine();
        var livrosDAO = new LivrosDAO();
        var livro = livrosDAO.read();
        var emprestimoDAO = new EmprestimoDAO();
        var emprestimos = emprestimoDAO.read();
        switch (opcao) {
            case 1 -> {
                System.out.println("Digite o isbn do livro");
                var isbn = scanner.nextLine();
                livrosDAO = new LivrosDAO();
                livro = livrosDAO.read();
                emprestimoDAO = new EmprestimoDAO();
                emprestimos = emprestimoDAO.read();
                for (Livro livros : livro) {
                    if (livros.isbn.contains(isbn)) {
                        var idLivro = livros.id;
                        for (var emprestimo : emprestimos) {
                            if (emprestimo.idlivro == idLivro) {
                                System.out.println(emprestimo);
                            }
                        }
                    }
                }
            }
            case 2 -> {
                System.out.println("Digite parte do título do livro");
                var titulo = scanner.nextLine();
                livrosDAO = new LivrosDAO();
                livro = livrosDAO.read();
                emprestimoDAO = new EmprestimoDAO();
                emprestimos = emprestimoDAO.read();
                for (Livro livros : livro) {
                    if (livros.titulo.contains(titulo)) {
                        var idLivro = livros.id;
                        for (var emprestimo : emprestimos) {
                            if (emprestimo.idlivro == idLivro) {
                                System.out.println(emprestimo);
                            }
                        }
                    }
                }
            }
            case 3 -> {
                System.out.println("Digite o id do livro");
                var idLivro = scanner.nextInt();
                scanner.nextLine();
                livrosDAO = new LivrosDAO();
                livro = livrosDAO.read();
                emprestimoDAO = new EmprestimoDAO();
                emprestimos = emprestimoDAO.read();
                for (Livro livros : livro) {
                    if (livros.id == idLivro) {
                        for (var emprestimo : emprestimos) {
                            if (emprestimo.idlivro == idLivro) {
                                System.out.println(emprestimo);
                            }
                        }
                    }
                }
            }
            case 4 -> {
                System.out.println("Digite o login do usuário");
                var login = scanner.nextLine();
                var usuarioDAO = new UsuarioDAO();
                var usuario = usuarioDAO.read();
                emprestimoDAO = new EmprestimoDAO();
                emprestimos = emprestimoDAO.read();
                for (Usuario usuarios : usuario) {
                    if (usuarios.login.contains(login)) {
                        var loginUsuario = usuarios.login;
                        for (var emprestimo : emprestimos) {
                            if (Objects.equals(emprestimo.loginusuario, loginUsuario)) {
                                System.out.println(emprestimo);
                            }
                        }
                    }
                }
            }
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

    public static void calcularMulta(){
        //comparar a data do emprestimo com a data de agora, se a data for a mais de 7 dias, aplicar uma multa de 1 real por dia
        var emprestimoDAO = new EmprestimoDAO();
        var emprestimos = emprestimoDAO.read();
        for (var emprestimo : emprestimos){
            var dataEmprestimo = emprestimo.data;
            var dataAtual = LocalDateTime.now();
            var diferenca = ChronoUnit.DAYS.between(dataEmprestimo, dataAtual);
            var usuarioDAO = new UsuarioDAO();
            var usuarios = usuarioDAO.read();
            for(var usuario : usuarios){
                if(usuario.professor){
                    if(diferenca > 15){
                        usuario.multado = true;
                        usuario.valormulta = (int) (diferenca - 15);
                        usuarioDAO.update(usuario);
                    }
                }
                else{
                    if(diferenca > 7){
                        usuario.multado = true;
                        usuario.valormulta = (int) (diferenca - 7);
                        usuarioDAO.update(usuario);
                    }
                }
            }
        }
    }

    public static void pagarMulta(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o login do usuário");
        var login = scanner.nextLine();
        var usuarioDAO = new UsuarioDAO();
        var usuarios = usuarioDAO.read();
        for (var usuario : usuarios){
            if(usuario.login.contains(login)){
                if(usuario.multado) {
                    usuario.multado = false;
                    usuario.valormulta = 0;
                    usuarioDAO.update(usuario);
                    System.out.println("Multa paga com sucesso");
                    return;
                }
                else{
                    System.out.println("Usuário não está multado");
                    return;
                }
            }
        }
        System.out.println("Usuário não encontrado");
    }
}


