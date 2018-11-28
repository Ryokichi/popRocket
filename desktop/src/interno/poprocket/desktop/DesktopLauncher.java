package interno.poprocket.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import interno.poprocket.screens.PopRocket;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PopRocket(), config);
		
		config.width  = 1280;
		config.height = 720;
		config.title  = "PopRocket";		
	}
}
