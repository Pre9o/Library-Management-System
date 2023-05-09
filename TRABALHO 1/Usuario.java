public class Usuario{
    String nome;
    String login;
    boolean professor;
    int quantidadeLivros;

    //alunos so podem retirar 3 livros por semana e professores 5 livros por 15 dias.

    public Usuario(String nome, String login, boolean professor, int quantidadeLivros){
        this.nome = nome;
        this.login = login;
        this.professor = professor;
        this.quantidadeLivros = quantidadeLivros;
    }

    @Override
    public String toString(){
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", professor=" + professor +
                ", quantidadeLivros=" + quantidadeLivros +
                '}';
    }
}
