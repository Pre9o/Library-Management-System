import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("Driver não encontrado");
        }

        var conn = Biblioteca.getConn();
        System.out.println(conn);

        Menu.selecaoMenu();
    }
}
