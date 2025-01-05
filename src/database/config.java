package PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.database;

import java.sql.*;

public class config {
    private final static String url = "jdbc:mysql://localhost:3306/pp2-tubes";
    private final static String username = "root";
    private final static String password = "";
            
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
        
    }
}
