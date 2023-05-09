import java.util.Scanner;
import java.time.LocalDateTime;

public class Menu{
        public static void opcoesMenu(){
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Cadastrar usuário");
        System.out.println("3 - Emprestar livro");
        System.out.println("4 - Devolver livro");
        System.out.println("5 - Reservar livro");
        System.out.println("6 - Cancelar reserva");
        System.out.println("7 - Listar livros");
        System.out.println("8 - Listar usuários");
        System.out.println("9 - Listar empréstimos");
        System.out.println("10 - Listar reservas");
        System.out.println("11 - Sair");

    }

    public static void selecaoMenu(){
        var scanner = new Scanner(System.in);
        var opcao = 0;
        while (opcao != 11) {
            opcoesMenu();
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    cadastrarUsuario();
                    break;
                case 3:
                    realizarEmprestimo();
                    break;
                case 4:
                    realizarDevolucao();
                    break;
                case 5:
                    realizarReserva();
                    break;
                case 6:
                    cancelarReserva();
                    break;
                case 7:
                    listarLivros();
                    break;
                case 8:
                    listarUsuarios();
                    break;
                case 9:
                    listarEmprestimos();
                    break;
                case 10:
                    listarReservas();
                    break;
                case 11:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
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
        System.out.println("Digite o id do usuário");
        var idUsuario = scanner.nextLine();
        var emprestimo = new Emprestimo(idLivro, idUsuario, LocalDateTime.now());
        var emprestimoDAO = new EmprestimoDAO();
        emprestimoDAO.create(emprestimo);
    }

    public static void realizarDevolucao(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do empréstimo");
        var idEmprestimo = scanner.nextInt();
        var emprestimoDAO = new EmprestimoDAO();
        emprestimoDAO.delete(idEmprestimo);
        System.out.println("Devolução realizada com sucesso");
    }

    public static void realizarReserva(){
        var scanner = new Scanner(System.in);
        System.out.println("Digite o id do livro");
        var idLivro = scanner.nextInt();
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

    public static void sair(){
        System.out.println("Saindo...");
    }
}
