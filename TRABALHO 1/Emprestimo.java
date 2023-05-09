import java.time.LocalDateTime;

public class Emprestimo{
    int idemprestimo;
    int idlivro;
    String loginusuario;
    LocalDateTime data;

    //alunos so podem retirar 3 livros e professores 5 livros.

    public Emprestimo(int idlivro, String loginusuario, LocalDateTime data) {
        idemprestimo = Biblioteca.getIntIdEmprestimo();
        this.idlivro = idlivro;
        this.loginusuario = loginusuario;
        this.data = data;
    }

    @Override
    public String toString(){
        return "Emprestimo{" +
                "idemprestimo='" + idemprestimo + '\'' +
                ", idlivro='" + idlivro + '\'' +
                ", loginusuario='" + loginusuario + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}