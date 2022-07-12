
import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* Tabelas Criadas
    create table usuarios(
        cdUsuario int not null primary key auto_increment,
        nomeUsuario char(30) not null,
        enderecoUsuario char(50) not null,
        administrador bit not null,
        senhaUsuario char(16) not null
    );

    drop table livros;
    create table livros(
        cdLivro int not null primary key auto_increment,
        nomeLivro char(30) not null,
        disponibilidade bit not null
        );
        
        drop table emprestimos;
    create table emprestimos(
        cdEmprestimo int not null primary key auto_increment,
        dataInicial date not null,
        dataFinalLimite date not null,
        dataEntrega date,
        cdUsuario int not null,
        cdLivro int not null,
        constraint fkUsuario foreign key(cdUsuario) references usuarios(cdUsuario),
        constraint fkLivro foreign key(cdLivro) references livros(cdLivro)
    );


    drop table usuarios;
*/

public class App {

    public static void main(String[] args) throws Exception {
        new App();

    }

    Server server;
    Connection conn = null;
    Statement stmt = null;

    App() {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/organizador_bibliotecas";
        String USER = "root";
        String PASS = "";

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();

            server = new Server(stmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// String sql;
// sql = "create table teste(numero int primary key not null auto_increment);";
// stmt.execute(sql);

// String sqlQuery = "SELECT * FROM book";
// ResultSet rs = stmt.executeQuery(sqlQuery);

// // STEP 5: Extract data from result set
// while (rs.next()) {
// // Retrieve by column name
// int idBook = rs.getInt("idBook");
// String nameBook = rs.getString("nameBook");
// float priceBook = rs.getFloat("priceBook");

// // Display values
// System.out.print("ID: " + idBook);
// System.out.print(", name: " + nameBook);
// System.out.print(", Price: " + priceBook);
// }
// // STEP 6: Clean-up environment
// rs.close();