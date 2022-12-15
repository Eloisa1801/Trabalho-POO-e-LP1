import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/aulaBD";
        String user = "root";
        String pwd = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connected");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("Erro: driver not found");
        } catch (SQLException e) {
            System.out.println("Ero Connection failed");
            e.printStackTrace();
        }
        return null;
    }
}

// =------------------------------
