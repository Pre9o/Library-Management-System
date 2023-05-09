public class Livro {
    String titulo;
    String autor;
    String isbn;
    String editora;
    int ano;
    int edicao;
    int id;

    public Livro(String titulo, String autor, String isbn, String editora, int ano, int edicao, int id) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editora = editora;
        this.ano = ano;
        this.edicao = edicao;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", editora='" + editora + '\'' +
                ", ano=" + ano +
                ", edicao=" + edicao +
                ", id=" + id +
                '}';
    }
}