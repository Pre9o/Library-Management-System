import java.util.Scanner;
import java.time.LocalDateTime;

public class Menu{
        public static void opcoesMenu(){
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Cadastrar usuário");
        System.out.println("3 - Emprestar livro");
        System.out.println("4 - Renovar empréstimo");
        System.out.println("5 - Buscar empréstimo");
        System.out.println("6 - Devolver livro");
        System.out.println("7 - Reservar livro");
        System.out.println("8 - Buscar usuário");
        System.out.println("9 - Buscar livro");
        System.out.println("10 - Alterar livro");
        System.out.println("11 - Excluir livro");
        System.out.println("12 - Excluir usuário");
        System.out.println("13 - Cancelar reserva");
        System.out.println("14 - Listar livros");
        System.out.println("15 - Listar usuários");
        System.out.println("16 - Listar empréstimos");
        System.out.println("17 - Listar reservas");
        System.out.println("18 - Pagar multa");
        System.out.println("19 - Sair");
    }

    public static void selecaoMenu(){
        var scanner = new Scanner(System.in);
        var opcao = 0;
        while (opcao != 19) {
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
                    Biblioteca.buscarEmprestimo();
                    break;
                case 6:
                    Biblioteca.realizarDevolucao();
                    break;
                case 7:
                    Biblioteca.realizarReserva();
                    break;
                case 8:
                    Biblioteca.buscarUsuario();
                    break;
                case 9:
                    Biblioteca.buscarLivro();
                    break;
                case 10:
                    Biblioteca.alterarLivro();
                    break;
                case 11:
                    Biblioteca.excluirLivro();
                    break;
                case 12:
                    Biblioteca.excluirUsuario();
                    break;
                case 13:
                    Biblioteca.cancelarReserva();
                    break;
                case 14:
                    Biblioteca.listarLivros();
                    break;
                case 15:
                    Biblioteca.listarUsuarios();
                    break;
                case 16:
                    Biblioteca.listarEmprestimos();
                    break;
                case 17:
                    Biblioteca.listarReservas();
                    break;
                case 18:
                    Biblioteca.pagarMulta();
                    break;
                case 19:
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
