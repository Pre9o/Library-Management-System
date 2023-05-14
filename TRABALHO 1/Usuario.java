public class Usuario{
    String nome;
    String login;
    boolean professor;
    int quantidadeLivros;
    boolean multado;
    int valormulta;
    //alunos so podem retirar 3 livros por semana e professores 5 livros por 15 dias.

    public Usuario(String nome, String login, boolean professor, int quantidadeLivros, boolean multado, int valormulta){
        this.nome = nome;
        this.login = login;
        this.professor = professor;
        this.quantidadeLivros = quantidadeLivros;
        this.multado = false;
        this.valormulta = 0;
    }

    @Override
    public String toString(){
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", professor=" + professor +
                ", quantidadeLivros=" + quantidadeLivros +
                ", multado=" + multado +
                ", valormulta=" + valormulta +
                '}';
    }
}
