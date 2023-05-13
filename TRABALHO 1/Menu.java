import java.util.Scanner;
import java.time.LocalDateTime;

public class Menu{
        public static void opcoesMenu(){
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Cadastrar usuário");
        System.out.println("3 - Emprestar livro");
        System.out.println("4 - Renovar empréstimo");
        System.out.println("5 - Devolver livro");
        System.out.println("6 - Reservar livro");
        System.out.println("7 - Cancelar reserva");
        System.out.println("8 - Listar livros");
        System.out.println("9 - Listar usuários");
        System.out.println("10 - Listar empréstimos");
        System.out.println("11 - Listar reservas");
        System.out.println("12 - Sair");

    }

    public static void selecaoMenu(){
        var scanner = new Scanner(System.in);
        var opcao = 0;
        while (opcao != 11) {
            opcoesMenu();
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    Biblioteca.cadastrarLivro();
                    break;
                case 2:
                    Biblioteca.cadastrarUsuario();
                    break;
                case 3:
                    Biblioteca.realizarEmprestimo();
                    break;
                case 4:
                    Biblioteca.renovarEmprestimo();
                    break;
                case 5:
                    Biblioteca.realizarDevolucao();
                    break;
                case 6:
                    Biblioteca.realizarReserva();
                    break;
                case 7:
                    Biblioteca.cancelarReserva();
                    break;
                case 8:
                    Biblioteca.listarLivros();
                    break;
                case 9:
                    Biblioteca.listarUsuarios();
                    break;
                case 10:
                    Biblioteca.listarEmprestimos();
                    break;
                case 11:
                    Biblioteca.listarReservas();
                    break;
                case 12:
                    sair();
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    public static void sair(){
        System.out.println("Saindo...");
    }
}
