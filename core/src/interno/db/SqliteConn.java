package interno.db;

import java.net.URL;
import java.sql.*;

public class SqliteConn {
	private URL url = getClass().getResource("");
	private String path;
	
	public SqliteConn () {
		this.path = url.toString() + "bancoDados/popRocket.db";
		this.path = this.path.replace("file:/", ""); 
				
		System.out.println("Instanciado classe para conexão com banco de dados");
		System.out.println("O caminho dos arquivos é " + this.path);
	}
	
    public void createNewDataBase() {
    	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:"+this.path)) {
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
            conn = DriverManager.getConnection("jdbc:sqlite:" + this.path);
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
			while (rs.next()) {
				System.out.print("-> ");
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getInt("slot") + "\t" +
                                   rs.getInt("pontos") + "\t" +
                                   rs.getDouble("distancia"));
            }        
		
		} catch (SQLException e) {            
			e.printStackTrace();
		}		
		return rs;
	}
	
	public ResultSet atualiza (int slot, int pontos, double distancia) {	
		
		String sql = "UPDATE pontos SET"
                + " pontos=" + pontos
                + ", distancia=" + distancia
                + " WHERE slot=" + slot                
                + ";";
		
		System.out.println(sql);
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		try {    		
    		conn = this.conecta();
    		stmt = conn.createStatement();
    		rs   = stmt.executeQuery(sql);
    		
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		return rs;
	}
	
	public void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS pontos (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	slot integer,\n"
                + "	pontos integer,\n"
                + "	distancia real\n"
                + ");";            
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