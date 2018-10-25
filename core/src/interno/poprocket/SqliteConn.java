package interno.poprocket;
import java.net.URL;
import java.sql.*;

public class SqliteConn {        
    private String [] tabelas = {"jogador"};
	
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
		return rs;
	}
}