import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CRUDRepository {
	
	private final String createMieterTableSQL = "CREATE TABLE IF NOT EXISTS mieter (\n"
            + "	mieterID bigint auto_increment PRIMARY KEY,\n"
            + "	name text,\n"
            + "	vorname text,\n"
            + "	alter integer,\n"
            + "	telefonnummer integer,\n"
            + "	mietobjektID integer" // FOREIGN KEY
            + ");";
	
	private final String createMietobjektTableSQL = "CREATE TABLE IF NOT EXISTS mietobjekt (\n"
            + "	mietobjektID bigint auto_increment PRIMARY KEY,\n"
            + "	flaecheInQuadratmetern integer,\n"
            + "	monatsmieteInEuro real,\n"
            + "	baujahr integer,\n"
            + "	lage text"
            + ");";
	
	private final String insertIntoMieterSQL = "INSERT INTO mieter(name,vorname,alter,telefonnummer,mietobjektID) VALUES(?,?,?,?,?)";	
	private final String updateMieterSQL = "UPDATE mieter SET name = ?, vorname = ?, alter = ?, telefonnummer = ?, mietobjektID = ? WHERE mieterID = ?";
	private final String deleteMieterSQL = "DELETE FROM mieter WHERE mieterID = ?";
	private final String selectAlleMieterSQL = "SELECT * FROM mieter";
	private final String selectMieterIDByName = "SELECT mieterID FROM mieter WHERE name LIKE '?'";
	private final String findMieterByID = "SELECT * FROM mieter WHERE mieterID = ?";
	
	
	private static Connection connect() {
        Connection conn = null;
        try {
			conn = DBSingletonConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
        return conn;
    }
	
	public void createAllTables() {        
        Connection conn = connect();
        Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute(createMieterTableSQL);
	        stmt.execute(createMietobjektTableSQL);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}        
    }
	
	public long insertIntoMieter(String name, String vorname, int alter, int telefonnummer, long mietobjektID) throws SQLException{
        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(insertIntoMieterSQL);
        pstmt.setString(1, name);
        pstmt.setString(2, vorname);
        pstmt.setInt(3, alter);
        pstmt.setInt(4, telefonnummer);
        pstmt.setLong(5, mietobjektID);
        pstmt.executeUpdate();
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        long id = -1;
        if (generatedKeys.next()) {
            id = generatedKeys.getLong(1);
        } else {
            throw new SQLException("Erstellung des Mieters fehlgeschlagen");
        }
        return id;
	}
	
	public void updateMieter(String name, String vorname, int alter, int telefonnummer, long mietobjektID) throws SQLException{		
		Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(updateMieterSQL);
        pstmt.setString(1, name);
        pstmt.setString(2, vorname);
        pstmt.setInt(3, alter);
        pstmt.setInt(4, telefonnummer);
        pstmt.setLong(5, mietobjektID);
        pstmt.executeUpdate();
	}
	
	public void deleteMieter(int mieterID) throws SQLException{		 
        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(deleteMieterSQL);
        pstmt.setLong(1, mieterID);
        pstmt.executeUpdate();
	}
		
	public void selectAlleMieterTest() throws SQLException{	       
		Connection conn = connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(selectAlleMieterSQL);      
	    while (rs.next()) {
	    	System.out.println(rs.getString("name"));
	    }
    }
	
	public ObservableList<Mieter> selectAlleMieter() throws SQLException{ 
		ObservableList<Mieter> mieterListe = FXCollections.observableArrayList();
		Connection conn = connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(selectAlleMieterSQL);
	    while (rs.next()) {	    	
	    	mieterListe.add(new Mieter(rs.getInt("mieterID"),
	    				rs.getString("name"),
	                       rs.getString("vorname"),
	                       rs.getInt("alter"), 
	                       rs.getInt("telefonnummer"), 
	                       rs.getInt("mietobjektID")));
	    }
	    return mieterListe;
    }
	
	public int selectMieterID(Mieter mieter) throws SQLException{	
		Connection conn = connect();
		PreparedStatement pstmt = conn.prepareStatement(selectMieterIDByName);
		pstmt.setString(1, mieter.getName());
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int id = rs.getInt(1);
	    return id;
    }
	
	private Mieter findMieterByID(int mieterID) throws SQLException{
		Connection conn = connect();
		PreparedStatement pstmt = conn.prepareStatement(findMieterByID);
		pstmt.setInt(1, mieterID);
		ResultSet rs = pstmt.executeQuery();
        Mieter m = null;
	    if(rs.next()){
	    	m = new Mieter(rs.getInt("mieterID"), rs.getString("name"), 
	    			rs.getString("vorname"), rs.getInt("alter"), 
	    			rs.getInt("telefonnummer"), 
	    			rs.getInt("mietobjektID"));
	    }
	    return m;
	}
	
}
