package interno.poprocket.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import interno.poprocket.screens.PopRocket;
import interno.db.SqliteConn;

public class DesktopLauncher {
	public static void main (String[] arg) {
		SqliteConn db = new SqliteConn();
//		db.consulta("SELECT * FROM pontuacao ");
		db.createNewDataBase();
		db.createNewTable();
		db.consulta("SELECT * FROM pontos");
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PopRocket(), config);
		
	}
}
