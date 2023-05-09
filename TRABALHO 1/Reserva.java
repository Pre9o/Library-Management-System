public class Reserva{
    int idreserva;
    int idlivro;
    String loginusuario;

    //usuarios com multa nao podem reservar livros.
    //Não é permitido aos usuários com multas reservarem livros.

    public Reserva(int idlivro, String loginusuario) {
        idreserva = Biblioteca.getIntIdReserva();
        this.idlivro = idlivro;
        this.loginusuario = loginusuario;
    }

    @Override
    public String toString(){
        return "Reserva{" +
                "idreserva='" + idreserva + '\'' +
                ", idlivro='" + idlivro + '\'' +
                ", loginusuario='" + loginusuario + '\'' +
                '}';
    }

}
