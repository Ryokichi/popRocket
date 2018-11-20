package interno.db;

import java.net.URL;
import java.sql.*;

public class SqliteConn {        
//    private String [] tabelas = {"jogador"};
    
    public void createNewDataBase() {    	
    	URL url = getClass().getResource("");    	
    	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:"+url+"/bancoDados/popRocket.db")) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {        	
            System.out.println("Erro - " + e.getMessage());
        }
	}
	
	private Connection conecta() throws SQLException {
		Connection conn = null;		
		try {                       
            URL path = getClass().getResource("/bancoDados/popRocket.db");           
            conn = DriverManager.getConnection("jdbc:sqlite:" + path);
            System.out.println("Connection to SQLite has been established.");           
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
	}	
	
	public ResultSet consulta(String sql) {
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		
		try {
			conn = this.conecta();
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);			
		} catch (SQLException e) {            
			e.printStackTrace();
		}
		
		System.out.println(rs);
		return rs;
	}
	
	public void createNewTable() {
        // SQLite connection string
       //        String url = "jdbc:sqlite:C://sqlite/db/tests.db";
        URL path = getClass().getResource("/bancoDados/popRocket.db");       
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS pontos (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	slot integer,\n"
                + "	pontos integer,\n"
                + "	distancia real\n"
                + ");";
        
//        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path);
//                 Statement stmt = conn.createStatement()) {
//            stmt.execute(sql);
//            System.out.println(sql);
            
        try {
        	Connection conn = null;
    		Statement  stmt = null;
    		ResultSet  rs   = null;
    		
    		conn = this.conecta();
    		stmt = conn.createStatement();
    		rs   = stmt.executeQuery(sql);
    		
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}