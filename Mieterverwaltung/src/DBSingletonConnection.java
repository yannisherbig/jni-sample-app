import java.sql.Connection;
import java.sql.DriverManager;

public class DBSingletonConnection {
	
	private static Connection connection;
	
    static {
        try{
        	Class.forName("org.h2.Driver");
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection
            		("jdbc:h2:./data/db");
        } catch(Exception ignored) { ignored.printStackTrace();}
    }
    
    public static Connection getConnection() throws Exception {
        return connection;
    }
    
    private DBSingletonConnection() { }
}
