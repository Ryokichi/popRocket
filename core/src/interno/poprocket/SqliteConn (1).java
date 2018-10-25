package interno.poprocket;
import java.net.URL;
import java.sql.*;

public class SqliteConn {        
    //private String [] tabelas = {"jogador", "dadosJogo"};
	
	private Connection conecta() throws SQLException {		
		Connection conn = null;		
		try {                       
            URL path = getClass().getResource("/bancoDados/popRocket.db");
            System.out.println("Acessando path: " + path);

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
	
	public void criaTabelas () {
		System.out.println("Criando tabelas");
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		
		
		String comando = "CREATE TABLE IF NOT EXISTS dadosJogo ( \n"
				+ "id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "dist_total integer \n"				
				+ ");";
		
		try {
			conn = this.conecta();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(comando);
			System.out.println(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}