package repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBSingletonConnection {
	
	private static Connection connection;
	
    static {
        try{
        	Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection
            		("jdbc:h2:./data/db");
        } catch(Exception e) { 
        	System.out.println(e.getMessage());
        }
    }
    
    public static Connection getConnection() throws Exception {
        return connection;
    }
    
    private DBSingletonConnection() { }
}
